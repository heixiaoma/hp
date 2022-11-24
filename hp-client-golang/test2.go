package main

import (
	"hp-client-golang/hpMessage"
	"hp-client-golang/network"
	"log"
	"time"
)

func main() {
	hpClient := network.NewHpClient(func(message string) {
		log.Printf(message)
	})
	hpClient.Connect(hpMessage.HpMessage_TCP, "hp.nsjiasu.com", 9091, "666666", "666666", "heixiaoma", -1, "127.0.0.1", 7777)
	for {
		println(hpClient.GetStatus())
		time.Sleep(time.Duration(5) * time.Second)
	}
}
