package main

import (
	"flag"
	"github.com/spf13/viper"
	"hp-client-golang/tcp"
	"log"
	"time"
)

func main() {

	// 定义几个变量，用于接收命令行的参数值
	var username string
	var password string
	var remote_port int
	var ip string
	var port int

	//命令行参数模式
	flag.StringVar(&username, "username", "root", "穿透账号")
	flag.StringVar(&password, "password", "", "穿透密码")
	flag.IntVar(&remote_port, "remote_port", 0, "外网端口如果没有会随机分配当前是可选参数")
	flag.StringVar(&ip, "ip", "127.0.0.1", "内网IP")
	flag.IntVar(&port, "port", 0, "内网端口")
	// 解析命令行参数写入注册的flag里
	flag.Parse()

	//配置文件模式
	config := viper.New()
	config.AddConfigPath("./")     // 文件所在目录
	config.SetConfigName("config") // 文件名
	config.SetConfigType("ini")    // 文件类型
	if err := config.ReadInConfig(); err != nil {
		if _, ok := err.(viper.ConfigFileNotFoundError); ok {
			log.Printf("找不到配置文件..")
		} else {
			log.Printf("配置文件出错..")
		}
	} else {
		username = config.GetString("hp.username")
		password = config.GetString("hp.password")
		remote_port = config.GetInt("hp.remote_port")
		ip = config.GetString("proxy.ip")
		port = config.GetInt("proxy.port")
	}

	log.Printf("用户名:%s 密码:%s ,外部访问的端口：%d", username, password, remote_port)
	log.Printf("本地IP:%s 本地端口：%d", ip, port)
	hpClient := tcp.NewHpClient(func(message string) {
		log.Printf(message)
	})
	hpClient.Connect("ksweb.club", 9091, username, password, remote_port, ip, port)

	go func() {
		for {
			if !hpClient.GetStatus() {
				hpClient.Connect("ksweb.club", 9091, username, password, remote_port, ip, port)
				log.Printf("重连")
			}
			time.Sleep(time.Duration(5) * time.Second)
		}
	}()
	for {
		time.Sleep(time.Duration(5) * time.Second)
	}
}
