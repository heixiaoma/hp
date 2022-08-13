package main

import (
	"github.com/golang/protobuf/proto"
	"hp-client-golang/HpMessage"
	"hp-client-golang/Tcp"
)

func main() {
	client := Tcp.New("127.0.0.1", 9091, true)
	message := &HpMessage.HpMessage{
		Type: HpMessage.HpMessage_REGISTER,
		MetaData: &HpMessage.HpMessage_MetaData{
			Port:     *proto.Int32(12000),
			Username: *proto.String("test"),
			Password: *proto.String("123456"),
		},
	}
	client.ReadHpMessage(read)
	client.WriteHpMessage(message)

	for true {

	}
}

func read(message *HpMessage.HpMessage) {
	println(message.String())
}
