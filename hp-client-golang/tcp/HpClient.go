package tcp

import (
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

func (hpClient *HpClient) Connect(serverAddress string, serverPort int, username string, password string, remotePort int, proxyAddress string, proxyPort int) {
	if hpClient.conn != nil {
		hpClient.conn.Close()
	}
	connection := NewConnection()
	handler := &HpClientHandler{
		Port:         remotePort,
		Password:     password,
		Username:     username,
		ProxyAddress: proxyAddress,
		ProxyPort:    proxyPort,
		CallMsg:      hpClient.CallMsg,
	}
	hpClient.serverAddress = serverAddress
	hpClient.serverPort = serverPort
	hpClient.handler = handler
	hpClient.isKill = false
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

func (hpClient *HpClient) Close() {
	if hpClient.conn != nil {
		hpClient.isKill = true
		hpClient.conn.Close()
	}
}
