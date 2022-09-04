package main

import (
	"github.com/spf13/viper"
	"hp-client-golang/tcp"
	"log"
	"time"
)

func main() {

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
	}
	username := config.GetString("hp.username")
	password := config.GetString("hp.password")
	remote_port := config.GetInt("hp.remote_port")
	log.Printf("用户名:%s 密码:%s ,外部访问的端口：%d", username, password, remote_port)
	ip := config.GetString("proxy.ip")
	port := config.GetInt("proxy.port")
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
