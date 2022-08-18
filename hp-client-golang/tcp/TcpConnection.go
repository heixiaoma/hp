package tcp

import (
	"bufio"
	"hp-client-golang/Protol"
	"net"
	"strconv"
)

type Connection struct {
}

func (connection *Connection) Connect(host string, port int, redType bool, handler TcpHandler) net.Conn {
	conn, err := net.Dial("tcp", host+":"+strconv.Itoa(port))
	if err != nil {
		panic(err)
	}
	handler.ChannelActive(conn)
	//设置读
	go func() {
		reader := bufio.NewReader(conn)
		for {
			if redType {
				//hp读
				decode, err := protol.Decode(reader)
				if err != nil {
					handler.ChannelInactive(conn)
				}
				if decode != nil {
					handler.ChannelRead(conn, decode)
				}
			} else {
				//字节读
				if reader.Size() > 0 {
					testData, err := reader.Peek(1)
					if err != nil {
						handler.ChannelInactive(conn)
					}
					if testData != nil {
						if reader.Buffered() > 0 {
							data := make([]byte, reader.Buffered())
							reader.Read(data)
							handler.ChannelRead(conn, data)
						}
					}
				}
			}
		}
	}()
	return conn
}
