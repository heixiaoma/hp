package main

import (
	"hp-client-golang/web"
	"log"
)

func main() {
	web.InitCloudDevice("http://ksweb.club:9090")
	log.Printf("请访问 http://127.0.0.1:10240/ 进行穿透配置")
	web.StartWeb(0)

}
