package main

import (
	"embed"
	"hp-client-golang/web"
)

//go:embed static/*
var staticAndroidFs embed.FS

func Start(port int, api string) {
	web.StartWeb(port, api, staticAndroidFs)
}
