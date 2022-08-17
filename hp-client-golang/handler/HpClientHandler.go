package handler

import (
	"hp-client-golang/HpMessage"
	"hp-client-golang/Protol"
	"net"
)

type HpClientHandler struct {
	Port         int
	Password     string
	Username     string
	ProxyAddress string
	ProxyPort    int
	CallMsg      func(message string)
}

// ChannelActive 连接激活时，发送注册信息给云端
func (h *HpClientHandler) ChannelActive(conn net.Conn) {
	message := &HpMessage.HpMessage{
		Type: HpMessage.HpMessage_REGISTER,
		MetaData: &HpMessage.HpMessage_MetaData{
			Port:     int32(h.Port),
			Username: h.Username,
			Password: h.Password,
		},
	}
	conn.Write(Protol.Encode(message))
	h.CallMsg("HpClientHandler-ChannelActive:注册信息已发送")
}

func (h *HpClientHandler) ChannelRead(conn net.Conn, data interface{}) {

}

func (h *HpClientHandler) ChannelInactive(conn net.Conn) {

}
