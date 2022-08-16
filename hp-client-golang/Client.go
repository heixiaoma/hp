package main

import (
	"fmt"
	"google.golang.org/protobuf/proto"
	"hp-client-golang/HpMessage"
	"hp-client-golang/Protol"
	"log"
	"net"
	"time"
)

//测试 client
func TcpClient(port int) {

	time.Sleep(time.Second * 3)

	conn, err := net.Dial("tcp", fmt.Sprintf("localhost:%d", port))

	if err != nil {
		log.Printf("tcpFHTestClient, Dail error:%v\n", err)
	}

	protocal := Protol.NewProtobufProtocal()

	go getData(protocal, conn)

	data  := &HpMessage.HpMessage{
		Type: HpMessage.HpMessage_REGISTER,
		MetaData: &HpMessage.HpMessage_MetaData{
			Port:    12000,
			Username: "test",
			Password: "123456",
		},
	}
	//返回encode 的数据， 然后发送
	encodedData, _ := protocal.Encode( data)
	fmt.Println("发送数据：", data)
	_, err2 := conn.Write(encodedData)
	if err2 != nil {
		return
	}

}

func getData(protocal *Protol.ProtobufProtocal, conn net.Conn) {

	for {
		response, err := protocal.ClientDecode(conn)
		if err != nil {
			continue
		}
		data := &HpMessage.HpMessage{}
		err = proto.Unmarshal(response.Data, data)
		if err != nil {
			return
		}
		fmt.Println("客户端收到返回数据", data)

	}
}
func main()  {
	TcpClient(9091)

	for true {

	}
}