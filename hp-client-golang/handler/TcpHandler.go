package handler

import "net"

type TcpHandler interface {
	// ChannelActive 连接激活
	ChannelActive(conn net.Conn)
	// ChannelRead 连接有数据时
	ChannelRead(conn net.Conn, data interface{})
	// ChannelInactive 连接断开
	ChannelInactive(conn net.Conn)
}
