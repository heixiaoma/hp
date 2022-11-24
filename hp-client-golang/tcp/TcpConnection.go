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
		call("不能能连到服务器：" + host + ":" + strconv.Itoa(port) + " 原因：" + err.Error())
		return nil
	}
	handler.ChannelActive(conn)
	//设置读
	go func() {
		reader := bufio.NewReader(conn)
		for {
			//尝试读检查连接激活
			switch handler.(type) {
			case *HpClientHandler:

				hp := handler.(*HpClientHandler)
				if !hp.Active {
					return
				}
				decode, e := protol.Decode(reader)
				if e != nil {
					call(e.Error())
					handler.ChannelInactive(conn)
					return
				}
				if decode != nil {
					handler.ChannelRead(conn, decode)
				}
			case *LocalProxyHandler:
				proxyHandler := handler.(*LocalProxyHandler)
				if !proxyHandler.Active {
					return
				}
				_, err2 := reader.Peek(1)
				if err2 != nil {
					call("连接异常进行关闭:" + err2.Error())
					handler.ChannelInactive(conn)
					return
				}
				if reader.Buffered() > 0 {
					data := make([]byte, reader.Buffered())
					_, err2 := io.ReadFull(reader, data)
					if err2 != nil {
						call("读取异常：" + err2.Error())
					}
					handler.ChannelRead(conn, data)
				}
			}
		}
	}()
	return conn
}
