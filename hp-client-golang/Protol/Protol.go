package Protol

import (
	"bufio"
	"bytes"
	"encoding/binary"
	"github.com/golang/protobuf/proto"
	"hp-client-golang/HpMessage"
	"log"
)

func Encode(message *HpMessage.HpMessage) []byte {
	d, _ := proto.Marshal(message)
	i, _ := encode(d)
	return i
}

func Decode(reader *bufio.Reader) (*HpMessage.HpMessage, error) {
	d, err := decode(reader)
	if err != nil {
		return nil, err
	}
	message := &HpMessage.HpMessage{}
	err2 := proto.Unmarshal(d, message)
	if err2 != nil {
		return nil, err2
	}
	return message, nil
}

// encode 将数据包编码（即加上包头再转为二进制）
func encode(mes []byte) ([]byte, error) {
	//获取发送数据的长度，并转换为四个字节的长度，即int32
	len := int8(len(mes))
	//创建数据包
	dataPackage := new(bytes.Buffer) //使用字节缓冲区，一步步写入性能更高

	//先向缓冲区写入包头
	//大小端口诀：大端：尾端在高位，小端：尾端在低位
	//编码用小端写入，解码也要从小端读取，要保持一致
	err := binary.Write(dataPackage, binary.LittleEndian, len) //往存储空间小端写入数据
	if err != nil {
		return nil, err
	}
	//写入消息
	err = binary.Write(dataPackage, binary.LittleEndian, mes)
	if err != nil {
		return nil, err
	}
	return dataPackage.Bytes(), nil
}

// decode 解码数据包
func decode(reader *bufio.Reader) ([]byte, error) {
	//读取数据包的长度（从包头获取）
	lenHeader := 2
	lenByte, err := reader.Peek(lenHeader) //读取前2个字节的数据
	if err != nil {
		return []byte{}, err
	}
	//转成Buffer对象,设置为从小端读取
	buff := bytes.NewBuffer(lenByte)
	var lenth uint8                                      //读取的数据大小，初始化为0
	err = binary.Read(buff, binary.LittleEndian, &lenth) //从小端读取
	if err != nil {
		return []byte{}, err
	}
	//读取消息
	pkg := make([]byte, int(lenth)+lenHeader)
	//Buffered返回缓冲区中现有的可读取的字节数
	if reader.Buffered() < int(lenth)+lenHeader { //如果读取的包头的数据大小和读取到的不符合
		hr := 0
		for hr < int(lenth)+lenHeader {
			l, err := reader.Read(pkg[hr:])
			if err != nil {
				return []byte{}, err
			}
			hr += l
		}
	} else {
		_, err := reader.Read(pkg)
		if err != nil {
			return []byte{}, err
		}
	}

	log.Printf("粘包处理 实际大小： %d需求大小：%d", len(pkg), int(lenth)+lenHeader)

	return pkg[lenHeader:], nil
}
