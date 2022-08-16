package Protol

import (
	"bytes"
	"encoding/binary"
	"errors"
	"fmt"
	"github.com/panjf2000/gnet"
	"google.golang.org/protobuf/proto"
	"hp-client-golang/HpMessage"
	"io"
	"net"
)

type ProtocalData struct {
	DataLength int8
	Data       []byte

	//headDecode bool
	//Lock       sync.Mutex
}

type ProtobufProtocal struct {
	HeadLength int
	Conn       gnet.Conn
}

//new protocal
func NewProtobufProtocal() *ProtobufProtocal {
	return &ProtobufProtocal{HeadLength: DefaultHeadLength}
}

func (p *ProtobufProtocal) SetGnetConnection(gnetConn gnet.Conn) {
	p.Conn = gnetConn
}

// server端 gnet input 数据 decode
func (p *ProtobufProtocal) ServerDecode(frame []byte) (*ProtocalData, error) {

	curConContext := p.Conn.Context()

	if curConContext == nil {
		//解析协议 header
		headData := frame[:p.HeadLength]

		newConContext := ProtocalData{}
		//数据长度
		bytesBuffer := bytes.NewBuffer(headData)

		err := binary.Read(bytesBuffer, binary.BigEndian, &newConContext.DataLength)
		if err != nil {
			return nil, err
		}

		//if  {
		//	//非正常协议数据 重置buffer
		//	p.Conn.ResetBuffer()
		//	return nil, errors.New("not normal protocal data, reset buffer")
		//}

		p.Conn.SetContext(newConContext)

	}

	//解析协议数据
	if protocalData, ok := p.Conn.Context().(ProtocalData); !ok {
		p.Conn.SetContext(nil)
		return nil, errors.New("context 数据异常")
	} else {
		dataLength := int(protocalData.DataLength)

		if dataLength < 1 {
			p.Conn.SetContext(nil)
			return &protocalData, nil
		}

		data := frame[p.HeadLength:]
		if len(data) != int(protocalData.DataLength) {
			p.Conn.SetContext(nil)
			return &protocalData, nil
		}

		protocalData.Data = data
		p.Conn.SetContext(nil)
		return &protocalData, nil
	}
}

// client 端获取解包后的数据
func (p *ProtobufProtocal) ClientDecode(rawConn net.Conn) (*ProtocalData, error) {

	if p.HeadLength < 1 {
		return nil, nil
	}
	newPackage := ProtocalData{}
	headData := make([]byte, p.HeadLength)
	n, err := io.ReadFull(rawConn, headData)
	if n != p.HeadLength {
		return nil, err
	}


	newPackage.DataLength=int8(headData[0]+headData[1])
	println(headData[0])
	println(newPackage.DataLength)

	if newPackage.DataLength < 1 {
		return &newPackage, errors.New("No Data")
	}

	data := make([]byte, newPackage.DataLength)
	dataNum, err2 := io.ReadFull(rawConn, data)
	if int8(dataNum) != newPackage.DataLength {
		return nil, errors.New(fmt.Sprintf("read data error, %v", err2))
	}

	newPackage.Data = data

	return &newPackage, nil
}

//output 数据编码
func (p *ProtobufProtocal) EncodeWrite(actionType uint16, pro *HpMessage.HpMessage, conn net.Conn) error {

	if conn == nil {
		return errors.New("con 为空")
	}

	pdata := ProtocalData{}
	data, err := proto.Marshal(pro)
	if err != nil {
		return err
	}
	pdata.DataLength = int8(len(data))
	pdata.Data = data


	if err = binary.Write(conn, binary.BigEndian, &pdata.DataLength); err != nil {
		return errors.New(fmt.Sprintf("encodeWrite datalength error , %v", err))
	}

	if pdata.DataLength > 0 {
		if err = binary.Write(conn, binary.BigEndian, &pdata.Data); err != nil {
			return errors.New(fmt.Sprintf("encodeWrite data error , %v", err))
		}
	}

	return nil
}

//数据编码
func (p *ProtobufProtocal) Encode( pro *HpMessage.HpMessage) ([]byte, error) {

	pdata := ProtocalData{}
	data, err := proto.Marshal(pro)
	if err != nil {
		return nil, err
	}
	pdata.DataLength = int8(len(data))
	pdata.Data = data

	result := make([]byte, 0)

	buffer := bytes.NewBuffer(result)

	if err = binary.Write(buffer, binary.BigEndian, &pdata.DataLength); err != nil {
		return nil, errors.New(fmt.Sprintf("encode datalength error , %v", err))
	}

	if pdata.DataLength > 0 {
		if err = binary.Write(buffer, binary.BigEndian, &pdata.Data); err != nil {
			return nil, errors.New(fmt.Sprintf("encode data error , %v", err))
		}
	}

	return buffer.Bytes(), nil
}