package Tcp

import (
	"hp-client-golang/HpMessage"
)

type RemoteHandler struct {
	host     string
	port     int
	hpClient *HpClient
	CallMsg  func(data string)
}

func NewRemoteHandler(host string, port int, hpClient *HpClient, f func(string2 string)) *RemoteHandler {
	return &RemoteHandler{
		host:     host,
		port:     port,
		hpClient: hpClient,
		CallMsg:  f,
	}
}

func (handler *RemoteHandler) Read(message *HpMessage.HpMessage) {
	switch message.Type {
	case HpMessage.HpMessage_REGISTER_RESULT:
		handler.processRegisterResult(message)
		break
	case HpMessage.HpMessage_CONNECTED:
		handler.processConnected(message)
		break
	case HpMessage.HpMessage_DISCONNECTED:
	case HpMessage.HpMessage_DATA:
	case HpMessage.HpMessage_KEEPALIVE:
	default:

	}
}

//登录结果 回调
func (handler *RemoteHandler) processRegisterResult(message *HpMessage.HpMessage) {
	if message.GetMetaData().GetSuccess() {
		handler.CallMsg(message.MetaData.Reason)
	} else {
		handler.hpClient.Close()
	}
}

//远端发送连接指令
func (handler *RemoteHandler) processConnected(message *HpMessage.HpMessage) {
	//连接本地的服务器
	NewHpClient(handler.host, handler.port, false)

}
