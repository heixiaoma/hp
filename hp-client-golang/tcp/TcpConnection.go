package tcp

import (
	"bufio"
	"hp-client-golang/Protol"
	"io"
	"net"
	"strconv"
)

type TcpConnection struct {
}

func NewTcpConnection() *TcpConnection {
	return &TcpConnection{}
}

func (connection *TcpConnection) Connect(host string, port int, redType bool, handler Handler, call func(mgs string)) net.Conn {
	conn, err := net.Dial("tcp", host+":"+strconv.Itoa(port))
	if err != nil {
		if redType {
			call("不能能连到穿透服务器：" + host + ":" + strconv.Itoa(port) + " 原因：" + err.Error())
		} else {
			call("不能能连到内网服务器：" + host + ":" + strconv.Itoa(port) + " 原因：" + err.Error())
		}
		return nil
	}
	handler.ChannelActive(conn)
	//设置读
	go func() {
		reader := bufio.NewReader(conn)
		for {
			//尝试读检查连接激活
			_, err := reader.Peek(1)
			if err != nil {
				handler.ChannelInactive(conn)
				return
			}
			if redType {
				decode, e := Protol.Decode(reader)
				if e != nil {
					call(e.Error())
					handler.ChannelInactive(conn)
					return
				}
				if decode != nil {
					handler.ChannelRead(conn, decode)
				}

			} else {
				if reader.Buffered() > 0 {
					data := make([]byte, reader.Buffered())
					io.ReadFull(reader, data)
					handler.ChannelRead(conn, data)
				}
			}
		}
	}()
	return conn
}
