package tcp

import (
	"hp-client-golang/HpMessage"
	"hp-client-golang/Protol"
	"log"
	"net"
)

type LocalProxyHandler struct {
	HpClientHandler *HpClientHandler
	RemoteChannelId string
}

// ChannelActive 连接激活时，发送注册信息给云端
func (l *LocalProxyHandler) ChannelActive(conn net.Conn) {
	log.Printf("成功连接到本地服务")
	l.HpClientHandler.Add(l.RemoteChannelId, conn)
}

func (l *LocalProxyHandler) ChannelRead(conn net.Conn, data interface{}) {
	bytes := data.([]byte)
	message := &hpMessage.HpMessage{
		Type: hpMessage.HpMessage_DATA,
		Data: bytes,
		MetaData: &hpMessage.HpMessage_MetaData{
			ChannelId: l.RemoteChannelId,
		},
	}
	l.HpClientHandler.Conn.Write(protol.Encode(message))
}

func (l *LocalProxyHandler) ChannelInactive(conn net.Conn) {
	l.HpClientHandler.Close(l.RemoteChannelId)
}
