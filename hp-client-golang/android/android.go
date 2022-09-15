package android

import (
	"container/list"
	"hp-client-golang/tcp"
)

var Hp *tcp.HpClient
var MessageGroup = list.New()

func Start() {
	Hp = tcp.NewHpClient(func(message string) {
		MessageGroup.PushFront(message)
	})
}

func Connect(serverAddress string, serverPort int, username string, password string, remotePort int, proxyAddress string, proxyPort int) {
	Hp.Connect(serverAddress, serverPort, username, password, remotePort, proxyAddress, proxyPort)
}

func GetMessage() string {
	front := MessageGroup.Front()
	if front != nil {
		value := front.Value
		MessageGroup.Remove(front)
		return value.(string)
	}
	return ""
}

func GetStatus() bool {
	return Hp.GetStatus()
}

func Close() {
	Hp.Close()
}
