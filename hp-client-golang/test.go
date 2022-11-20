package main

import (
	"hp-client-golang/hpMessage"
	"hp-client-golang/tcp"
	"log"
	"time"
)

func main() {
	hpClient := tcp.NewHpClient(func(message string) {
		log.Printf(message)
	})
	hpClient.Connect(hpMessage.HpMessage_TCP_UDP, "127.0.0.1", 9091, "hxm", "2", "aaa", 11111, "127.0.0.1", 7777)
	for {
		time.Sleep(time.Duration(5) * time.Second)
	}
}
