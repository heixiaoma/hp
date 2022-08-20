package tcp

import (
	"bufio"
	"hp-client-golang/Protol"
	"io"
	"time"

	"net"
	"strconv"
)

type Connection struct {
}

func NewConnection() *Connection {
	return &Connection{}
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
			//尝试读检查连接激活
			_, err := reader.Peek(1)
			if err != nil {
				handler.ChannelInactive(conn)
				return
			}
			if redType {
				decode, e := protol.Decode(reader)
				if e != nil {
					println(e.Error())
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
			time.Sleep(time.Duration(1) * time.Millisecond)
		}
	}()
	return conn
}
