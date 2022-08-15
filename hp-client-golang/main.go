package main

import (
	"github.com/golang/protobuf/proto"
	"hp-client-golang/HpMessage"
	"hp-client-golang/Tcp"
	"log"
)

func main() {
	client := Tcp.NewHpClient("127.0.0.1", 9091, true)
	message := &HpMessage.HpMessage{
		Type: HpMessage.HpMessage_REGISTER,
		MetaData: &HpMessage.HpMessage_MetaData{
			Port:     *proto.Int32(12000),
			Username: *proto.String("test"),
			Password: *proto.String("123456"),
		},
	}

	handler := Tcp.NewRemoteHandler("127.0.0.1", 8888, client, func(string2 string) {
		log.Printf(string2)
	})

	client.ReadHpMessage(handler.Read)
	client.WriteHpMessage(message)
	for true {

	}
}
