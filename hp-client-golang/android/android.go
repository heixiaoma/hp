package android

import (
	"hp-client-golang/web"
)

func Start(apiAddress string, port int, coreVersion string) {
	web.InitCloudDevice(apiAddress)
	web.StartWeb(port, coreVersion)
}
