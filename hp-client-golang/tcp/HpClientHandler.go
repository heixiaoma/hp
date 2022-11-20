package tcp

import (
	"hp-client-golang/HpMessage"
	"hp-client-golang/Protol"
	"net"
	"sync"
)

type HpClientHandler struct {
	Port         int
	Password     string
	Username     string
	Domain       string
	MessageType  HpMessage.HpMessage_MessageType
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
	message := &HpMessage.HpMessage{
		Type: HpMessage.HpMessage_REGISTER,
		MetaData: &HpMessage.HpMessage_MetaData{
			Port:     int32(h.Port),
			Username: h.Username,
			Password: h.Password,
			Domain:   h.Domain,
		},
	}
	message.MetaData.Type = h.MessageType
	conn.Write(protol.Encode(message))
}

func (h *HpClientHandler) ChannelRead(conn net.Conn, data interface{}) {
	message := data.(*HpMessage.HpMessage)
	switch message.Type {
	case HpMessage.HpMessage_REGISTER_RESULT:
		h.CallMsg(message.MetaData.Reason)
		break
	case HpMessage.HpMessage_CONNECTED:
		h.connected(message)
		break
	case HpMessage.HpMessage_DISCONNECTED:
		h.Close(message.MetaData.ChannelId)
		break
	case HpMessage.HpMessage_DATA:
		h.writeData(message)
		break
	case HpMessage.HpMessage_KEEPALIVE:
		h.CallMsg("服务器端返回心跳数据")
		conn.Write(protol.Encode(&HpMessage.HpMessage{Type: HpMessage.HpMessage_KEEPALIVE}))
		break
	default:
		h.CallMsg("未知类型数据：" + message.String())
	}

}

func (h *HpClientHandler) ChannelInactive(conn net.Conn) {
	h.Active = false
}

func (h *HpClientHandler) connected(message *HpMessage.HpMessage) {
	NewConnection().Connect(h.ProxyAddress, h.ProxyPort, false, &LocalProxyHandler{
		HpClientHandler: h,
		RemoteChannelId: message.MetaData.ChannelId,
	}, h.CallMsg)
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

func (h *HpClientHandler) CloseAll() {
	ConnGroup.Range(func(key, value interface{}) bool {
		conn := value.(net.Conn)
		if conn != nil {
			conn.Close()
			ConnGroup.Delete(key)
		}
		return true
	})
}

func (h *HpClientHandler) writeData(message *HpMessage.HpMessage) {
	if message.MetaData.Type == HpMessage.HpMessage_TCP {
		load, ok := ConnGroup.Load(message.MetaData.ChannelId)
		if ok {
			conn := load.(net.Conn)
			if conn != nil {
				conn.Write(message.Data)
			}
		}
	} else if message.MetaData.Type == HpMessage.HpMessage_UDP {
		//todo 需要转发给内网的UDPServer
	}
}
