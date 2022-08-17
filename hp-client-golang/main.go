package main

import (
	"hp-client-golang/tcp"
	"log"
)

func main() {

	hpClient := tcp.NewHpClient(func(message string) {
		log.Printf(message)
	})
	hpClient.Connect("127.0.0.1", 9091, "test", "123456", 1200, "127.0.0.1", 8888)

	//client := Tcp.NewHpClient("127.0.0.1", 9091)
	//message := &HpMessage.HpMessage{
	//	Type: HpMessage.HpMessage_REGISTER,
	//	MetaData: &HpMessage.HpMessage_MetaData{
	//		Port:     *proto.Int32(12000),
	//		Username: *proto.String("test"),
	//		Password: *proto.String("123456"),
	//	},
	//}
	//handler := Tcp.NewRemoteHandler("127.0.0.1", 777, client, func(data string) {
	//	log.Printf(data)
	//})
	//
	//client.ReadHpMessage(handler.Read)
	//client.WriteHpMessage(message)
	for true {

	}
}
