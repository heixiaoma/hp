package main

import (
	"flag"
	"hp-client-golang/web"
	"log"
	"os"
)

func main() {
	var deviceId string
	//命令行参数模式
	flag.StringVar(&deviceId, "deviceId", "NO_ID", "设备ID")
	flag.Parse()
	//默认命令行参数大于环境变量参数
	e := os.Getenv("deviceId")
	if deviceId == "NO_ID" && e != "" {
		deviceId = e
	}
	web.InitCloudDevice("http://hpproxy.cn", deviceId)
	log.Printf("请访问 http://127.0.0.1:10240/ 进行穿透配置")
	web.StartWeb(0, "15.0")
}
