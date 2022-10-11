package android

import (
	"hp-client-golang/web"
)

func Start(port int, api string) {
	web.StartWeb(port, api)
}
