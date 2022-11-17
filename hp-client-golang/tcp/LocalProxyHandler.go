package tcp

import (
	"hp-client-golang/HpMessage"
	"hp-client-golang/Protol"
	"net"
)

type LocalProxyHandler struct {
	HpClientHandler *HpClientHandler
	RemoteChannelId string
	Active          bool
}

// ChannelActive 连接激活时，发送注册信息给云端
func (l *LocalProxyHandler) ChannelActive(conn net.Conn) {
	l.Active = true
	l.HpClientHandler.Add(l.RemoteChannelId, conn)
}

func (l *LocalProxyHandler) ChannelRead(conn net.Conn, data interface{}) {
	bytes := data.([]byte)
	message := &HpMessage.HpMessage{
		Type: HpMessage.HpMessage_DATA,
		Data: bytes,
		MetaData: &HpMessage.HpMessage_MetaData{
			ChannelId: l.RemoteChannelId,
		},
	}
	l.HpClientHandler.Conn.Write(protol.Encode(message))
}

func (l *LocalProxyHandler) ChannelInactive(conn net.Conn) {
	l.Active = false
	l.HpClientHandler.Close(l.RemoteChannelId)
}
