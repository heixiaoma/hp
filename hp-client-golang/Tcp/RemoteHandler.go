package Tcp

import (
	"hp-client-golang/HpMessage"
	"log"
)

type RemoteHandler struct {
	host     string
	port     int
	hpClient *HpClient
	meClient *HpClient
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

func (handler *RemoteHandler) Read(client *HpClient, message *HpMessage.HpMessage) {
	log.Printf("消息类型：%s,消息内容：%s\n", message.Type.String(), message.String())
	switch message.Type {
	case HpMessage.HpMessage_REGISTER_RESULT:
		handler.processRegisterResult(message)
		break
	case HpMessage.HpMessage_CONNECTED:
		go handler.processConnected(message)
		break
	case HpMessage.HpMessage_DISCONNECTED:
	case HpMessage.HpMessage_DATA:
		handler.writeLocal(message.Data)
		break
	case HpMessage.HpMessage_KEEPALIVE:
		client.WriteHpMessage(&HpMessage.HpMessage{Type: HpMessage.HpMessage_KEEPALIVE})
		break
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
	client := NewHpClient(handler.host, handler.port)
	handler.meClient = client
	go client.ReadLocalMessage(handler.hpClient)
	println("------设置本地自定读----------")
}

func (handler *RemoteHandler) writeLocal(data []byte) {
	client := handler.meClient
	if client != nil {
		client.Write(data)
	}
}
