package main

import (
	"bytes"
	"encoding/binary"
	"fmt"
	"github.com/golang/protobuf/proto"
	"hp-client-golang/HpMessage"
	"net"
	"os"
)

func ClientHandleError(err error, when string) {
	if err != nil {
		fmt.Println(err, when)
		os.Exit(1)
	}
}

func IntToBytes(n int) []byte {
	data := int8(n)
	bytebuf := bytes.NewBuffer([]byte{})
	binary.Write(bytebuf, binary.BigEndian, data)
	return bytebuf.Bytes()
}

func BytesToInt(bys []byte) int {
	bytebuff := bytes.NewBuffer(bys)
	var data int64
	binary.Read(bytebuff, binary.BigEndian, &data)
	return int(data)
}

func main() {
	//拨号远程地址，简历tcp连接
	conn, err := net.Dial("tcp", "127.0.0.1:9091")
	ClientHandleError(err, "client conn error")
	//HpMessageData.HpMessage.Builder messageBuild = HpMessageData.HpMessage.newBuilder();
	//messageBuild.setType(HpMessageData.HpMessage.HpMessageType.REGISTER);
	//HpMessageData.HpMessage.MetaData.Builder metaDataBuild = HpMessageData.HpMessage.MetaData.newBuilder();
	//metaDataBuild.setPort(port);
	//metaDataBuild.setUsername(username);
	//metaDataBuild.setPassword(password);
	//messageBuild.setMetaData(metaDataBuild.build());
	//            client.connect("127.0.0.1",9091, "test","123456", 12000, "127.0.0.1", 8888);
	message := &HpMessage.HpMessage{
		Type: HpMessage.HpMessage_REGISTER,
		MetaData: &HpMessage.HpMessage_MetaData{
			Port:     *proto.Int32(12000),
			Username: *proto.String("test"),
			Password: *proto.String("123456"),
		},
	}
	data, _ := proto.Marshal(message)
	conn.Write(IntToBytes(len(data)))
	conn.Write(data)
	//buffer := make([]byte, 1024)
	for {
		//n, _ := conn.Read(buffer)
		//hpMessage := &HpMessage.HpMessage{}
		//proto.Unmarshal(buffer[:n], hpMessage)
		//print(hpMessage.MetaData.Reason)
	}
}
