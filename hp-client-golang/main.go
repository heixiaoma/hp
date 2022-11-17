package main

import (
	"embed"
	"hp-client-golang/web"
	"log"
)

//go:embed static/*
var staticFs embed.FS

func main() {
	log.Printf("请访问 http://127.0.0.1:10240/ 进行穿透配置")
	web.StartWeb(0, "http://ksweb.club:9090", staticFs)
}
