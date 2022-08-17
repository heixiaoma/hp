package net

import (
	"hp-client-golang/handler"
	"io"
	"net"
)

type HpClient struct {
	CallMsg func(message string)
	conn    net.Conn
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
	connection := TcpConnection{}
	hpClient.conn = connection.connect(serverAddress, serverPort, &handler.HpClientHandler{
		Port:         remotePort,
		Password:     password,
		Username:     username,
		ProxyAddress: proxyAddress,
		ProxyPort:    proxyPort,
		CallMsg:      hpClient.CallMsg,
	})
}

func (hpClient *HpClient) GetStatus() bool {
	if hpClient.conn != nil {
		var one []byte
		_, err := hpClient.conn.Read(one)
		if err != nil {
			return false
		}
		if err == io.EOF {
			return false
		}
		return true
	} else {
		return false
	}
}

func (hpClient *HpClient) Close() {
	if hpClient.conn != nil {
		hpClient.conn.Close()
	}
}
