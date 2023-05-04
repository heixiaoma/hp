package android

import (
	"hp-client-golang/web"
)

func Start(apiAddress string, port int, coreVersion string, deviceId string) {
	web.InitCloudDevice(apiAddress, deviceId)
	web.StartWeb(port, coreVersion)
}
