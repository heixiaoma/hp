package android

import (
	"hp-client-golang/web"
)

func Start(apiAddress string, port int) {
	web.InitCloudDevice(apiAddress)
	web.StartWeb(port)
}
