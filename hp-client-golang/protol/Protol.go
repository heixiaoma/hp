package protol

import (
	"bufio"
	"bytes"
	"encoding/binary"
	"github.com/golang/protobuf/proto"
	"github.com/golang/snappy"
	"hp-client-golang/hpMessage"
	"io"
)

func Encode(message *hpMessage.HpMessage) []byte {
	d, _ := proto.Marshal(message)
	i, _ := encode(d)
	return i
}

func Decode(reader *bufio.Reader) (*hpMessage.HpMessage, error) {
	d, err := decode(reader)
	if err != nil {
		return nil, err
	}
	if d == nil {
		return nil, err
	}
	message := &hpMessage.HpMessage{}
	proto.Unmarshal(d, message)
	return message, nil
}

// 将数据包编码（即加上包头再转为二进制）
func encode(mes []byte) ([]byte, error) {
	//创建数据包
	dataPackage := new(bytes.Buffer) //使用字节缓冲区，一步步写入性能更高
	//写消息头
	err := binary.Write(dataPackage, binary.BigEndian, int32(9999))
	if err != nil {
		return nil, err
	}
	//真实消息进行zstd压缩发送
	compress := snappy.Encode(nil, mes)
	if len(compress) < len(mes) {
		//开启压缩编码
		err = binary.Write(dataPackage, binary.BigEndian, byte(1))
		//写长度
		err = binary.Write(dataPackage, binary.BigEndian, int32(len(compress)))
		if err != nil {
			return nil, err
		}
		//写入消息
		err = binary.Write(dataPackage, binary.BigEndian, compress)
		if err != nil {
			return nil, err
		}
	} else {
		//不开启压缩编码
		err = binary.Write(dataPackage, binary.BigEndian, byte(0))
		//写长度
		err = binary.Write(dataPackage, binary.BigEndian, int32(len(mes)))
		if err != nil {
			return nil, err
		}
		//写入消息
		err = binary.Write(dataPackage, binary.BigEndian, mes)
		if err != nil {
			return nil, err
		}
	}
	return dataPackage.Bytes(), nil
}

// 解码数据包
func decode(reader *bufio.Reader) ([]byte, error) {
	// 可读小于9 不够格 4+1+4=头加长度=9字节
	//读取数据包的开头 int =9999 等4 字节
	//是否解压 byte 等1字节
	//长度 int 等4字节
	headerAndLength, err := reader.Peek(9)
	if err != nil {
		return []byte{}, err
	}
	header := bytesToInt(headerAndLength[0:4])
	isCompress := headerAndLength[4:5]
	length := bytesToInt(headerAndLength[5:])
	if header == 9999 {
		//读取 header+长度
		data := make([]byte, 9+length)
		//直接读完，不够的直接等待
		_, err := io.ReadFull(reader, data)
		//读取出来的字节流进行解压操作
		bytes := data[9:]
		if isCompress[0] == 1 {
			decompress, err := snappy.Decode(nil, bytes)
			return decompress, err
		} else {
			return bytes, err
		}
	} else {
		return nil, nil
	}
}

func bytesToInt(bys []byte) int {
	bytebuffer := bytes.NewBuffer(bys)
	var data int32
	binary.Read(bytebuffer, binary.BigEndian, &data)
	return int(data)
}
