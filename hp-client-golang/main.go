package main

import (
	"bufio"
	"fmt"
	"github.com/golang/protobuf/proto"
	"hp-client-golang/HpMessage"
	"hp-client-golang/Protol"
	"net"
	"os"
)

func ClientHandleError(err error, when string) {
	if err != nil {
		fmt.Println(err, when)
		os.Exit(1)
	}
}

func main() {
	//拨号远程地址，简历tcp连接
	conn, err := net.Dial("tcp", "127.0.0.1:9091")
	ClientHandleError(err, "client conn error")
	message := &HpMessage.HpMessage{
		Type: HpMessage.HpMessage_REGISTER,
		MetaData: &HpMessage.HpMessage_MetaData{
			Port:     *proto.Int32(12000),
			Username: *proto.String("test"),
			Password: *proto.String("123456"),
		},
	}

	conn.Write(Protol.Encode(message))
	readMessage(conn)
}

//接收消息
func readMessage(conn net.Conn) {
	defer conn.Close()
	reader := bufio.NewReader(conn)
	//读消息
	for {
		decode, err := Protol.Decode(reader)
		if err != nil {
			panic(err)
		}
		fmt.Println(decode)
	}
}
