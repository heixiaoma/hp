package android

import (
	"hp-client-golang/web"
)

func Start(port int) {
	web.InitCloudDevice()
	web.StartWeb(port)
}
