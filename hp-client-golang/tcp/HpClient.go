package tcp

import (
	"net"
)

type HpClient struct {
	CallMsg func(message string)
	conn    net.Conn
	handler *HpClientHandler
}

func NewHpClient(callMsg func(message string)) *HpClient {
	return &HpClient{
		CallMsg: callMsg,
	}
}

func (hpClient *HpClient) Connect(serverAddress string, serverPort int, username string, password string, remotePort int, proxyAddress string, proxyPort int) {
	if hpClient.conn != nil {
		hpClient.conn.Close()
	}
	connection := Connection{}
	handler := &HpClientHandler{
		Port:         remotePort,
		Password:     password,
		Username:     username,
		ProxyAddress: proxyAddress,
		ProxyPort:    proxyPort,
		CallMsg:      hpClient.CallMsg,
	}
	hpClient.handler = handler
	hpClient.conn = connection.Connect(serverAddress, serverPort, true, handler)
}

func (hpClient *HpClient) GetStatus() bool {
	if hpClient.handler != nil {
		return hpClient.handler.Active
	} else {
		return false
	}
}

func (hpClient *HpClient) Close() {
	if hpClient.conn != nil {
		hpClient.conn.Close()
	}
}
