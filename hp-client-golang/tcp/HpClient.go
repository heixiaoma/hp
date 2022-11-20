package tcp

import (
	HpMessage "hp-client-golang/hpMessage"
	"net"
	"strconv"
)

type HpClient struct {
	CallMsg       func(message string)
	conn          net.Conn
	serverAddress string
	serverPort    int
	isKill        bool
	handler       *HpClientHandler
}

func NewHpClient(callMsg func(message string)) *HpClient {
	return &HpClient{
		CallMsg: callMsg,
	}
}

func (hpClient *HpClient) Connect(messageType HpMessage.HpMessage_MessageType, serverAddress string, serverPort int, username string, password string, domain string, remotePort int, proxyAddress string, proxyPort int) {
	if hpClient.conn != nil {
		hpClient.conn.Close()
	}
	connection := NewConnection()
	handler := &HpClientHandler{
		Port:         remotePort,
		Password:     password,
		Username:     username,
		Domain:       domain,
		MessageType:  messageType,
		ProxyAddress: proxyAddress,
		ProxyPort:    proxyPort,
		CallMsg:      hpClient.CallMsg,
	}
	hpClient.serverAddress = serverAddress
	hpClient.serverPort = serverPort
	hpClient.handler = handler
	hpClient.conn = connection.Connect(serverAddress, serverPort, true, handler, hpClient.CallMsg)
}

func (hpClient *HpClient) GetStatus() bool {
	if hpClient.handler != nil {
		return hpClient.handler.Active
	} else {
		return false
	}
}

func (hpClient *HpClient) IsKill() bool {
	return hpClient.isKill
}

func (hpClient *HpClient) GetProxyServer() string {
	return hpClient.handler.ProxyAddress + ":" + strconv.Itoa(hpClient.handler.ProxyPort)
}

func (hpClient *HpClient) GetServer() string {
	return hpClient.serverAddress + ":" + strconv.Itoa(hpClient.serverPort)
}

func (hpClient *HpClient) Kill() {
	hpClient.isKill = true
	hpClient.Close()
}

func (hpClient *HpClient) Close() {
	if hpClient.conn != nil {
		hpClient.conn.Close()
	}
	if hpClient.handler != nil {
		hpClient.handler.CloseAll()
	}
}
