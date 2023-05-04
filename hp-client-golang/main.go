package main

import (
	"flag"
	"hp-client-golang/web"
	"log"
)

func main() {
	var deviceId string
	//命令行参数模式
	flag.StringVar(&deviceId, "deviceId", "NO_ID", "设备ID")
	flag.Parse()
	web.InitCloudDevice("http://ksweb.club:9090", deviceId)
	log.Printf("请访问 http://127.0.0.1:10240/ 进行穿透配置")
	web.StartWeb(0, "15.0")
}
