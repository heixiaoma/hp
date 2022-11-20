package tcp

import (
	"hp-client-golang/Protol"
	"hp-client-golang/hpMessage"
	"net"
)

type LocalProxyUdpHandler struct {
	HpClientHandler *HpClientHandler
	RemoteChannelId string
	Active          bool
}

// ChannelActive 连接激活时，发送注册信息给云端
func (l *LocalProxyUdpHandler) ChannelActive(conn net.Conn) {
	l.Active = true
	l.HpClientHandler.Add(l.RemoteChannelId, conn)
}

func (l *LocalProxyUdpHandler) ChannelRead(conn net.Conn, data interface{}) {
	bytes := data.([]byte)
	message := &hpMessage.HpMessage{
		Type: hpMessage.HpMessage_DATA,
		Data: bytes,
		MetaData: &hpMessage.HpMessage_MetaData{
			Type:      hpMessage.HpMessage_UDP,
			ChannelId: l.RemoteChannelId,
		},
	}
	l.HpClientHandler.Conn.Write(protol.Encode(message))
}

func (l *LocalProxyUdpHandler) ChannelInactive(conn net.Conn) {
	l.Active = false
	l.HpClientHandler.Close(l.RemoteChannelId)
}
