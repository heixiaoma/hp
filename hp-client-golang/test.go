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
	hpClient.Connect(hpMessage.HpMessage_TCP, "127.0.0.1", 9092, "666666", "666666", "heixiaoma", 11111, "127.0.0.1", 7777)
	for {
		println(hpClient.GetStatus())
		time.Sleep(time.Duration(5) * time.Second)
	}
}
