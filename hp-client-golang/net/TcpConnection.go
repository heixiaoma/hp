package net

import (
	"hp-client-golang/handler"
	"net"
	"strconv"
)

type TcpConnection struct {
}

func (tcpConnection *TcpConnection) connect(host string, port int, handler handler.TcpHandler) net.Conn {
	conn, err := net.Dial("tcp", host+":"+strconv.Itoa(port))
	if err != nil {
		panic(err)
	}
	handler.ChannelActive(conn)
	return conn
}
