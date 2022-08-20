package tcp

import (
	"hp-client-golang/HpMessage"
	"hp-client-golang/Protol"
	"log"
	"net"
	"sync"
)

type HpClientHandler struct {
	Port         int
	Password     string
	Username     string
	ProxyAddress string
	ProxyPort    int
	CallMsg      func(message string)
	Conn         net.Conn
	Active       bool
}

var ConnGroup = sync.Map{}

// ChannelActive 连接激活时，发送注册信息给云端
func (h *HpClientHandler) ChannelActive(conn net.Conn) {
	h.Conn = conn
	h.Active = true
	message := &hpMessage.HpMessage{
		Type: hpMessage.HpMessage_REGISTER,
		MetaData: &hpMessage.HpMessage_MetaData{
			Port:     int32(h.Port),
			Username: h.Username,
			Password: h.Password,
		},
	}
	conn.Write(protol.Encode(message))
	h.CallMsg("HpClientHandler-ChannelActive:注册信息已发送")
}

func (h *HpClientHandler) ChannelRead(conn net.Conn, data interface{}) {
	message := data.(*hpMessage.HpMessage)
	log.Printf("消息类型：%s,消息内容：%s\n", message.Type.String(), message.String())
	switch message.Type {
	case hpMessage.HpMessage_REGISTER_RESULT:
		h.CallMsg(message.MetaData.Reason)
		break
	case hpMessage.HpMessage_CONNECTED:
		h.connected(message)
		break
	case hpMessage.HpMessage_DISCONNECTED:
		h.Close(message.MetaData.ChannelId)
		break
	case hpMessage.HpMessage_DATA:
		h.writeData(message)
		break
	case hpMessage.HpMessage_KEEPALIVE:
		h.CallMsg("服务器端返回心跳数据")
		conn.Write(protol.Encode(&hpMessage.HpMessage{Type: hpMessage.HpMessage_KEEPALIVE}))
		break
	default:
		h.CallMsg("未知类型数据：" + message.String())
	}

}

func (h *HpClientHandler) ChannelInactive(conn net.Conn) {
	h.Active = false
}

func (h *HpClientHandler) connected(message *hpMessage.HpMessage) {
	NewConnection().Connect(h.ProxyAddress, h.ProxyPort, false, &LocalProxyHandler{
		HpClientHandler: h,
		RemoteChannelId: message.MetaData.ChannelId,
	})
}
func (h *HpClientHandler) Add(channelId string, conn net.Conn) {
	ConnGroup.Store(channelId, conn)
}

func (h *HpClientHandler) Close(channelId string) {
	load, ok := ConnGroup.Load(channelId)
	if ok {
		conn := load.(net.Conn)
		if conn != nil {
			conn.Close()
			ConnGroup.Delete(channelId)
		}
	}

}

func (h *HpClientHandler) writeData(message *hpMessage.HpMessage) {
	load, ok := ConnGroup.Load(message.MetaData.ChannelId)
	if ok {
		conn := load.(net.Conn)
		if conn != nil {
			log.Printf("写数据到本地")
			conn.Write(message.Data)
			log.Printf("写数据到本地完成")
		}
	}
}
