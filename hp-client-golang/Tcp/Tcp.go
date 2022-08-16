package Tcp

import (
	"bufio"
	"fmt"
	"hp-client-golang/HpMessage"
	"hp-client-golang/Protol"
	"net"
	"os"
	"strconv"
	"time"
)

type HpClient struct {
	host string
	port int
	conn net.Conn
}

// clientHandleError 链接失败错误
func (client *HpClient) clientHandleError(err error, when string) {
	if err != nil {
		fmt.Println(err, when)
		os.Exit(1)
	}
}

// New 创建实体
func New(Host string, Port int, isHpServer bool) *HpClient {
	client := &HpClient{
		host: Host,
		port: Port,
	}
	connect := client.connect(isHpServer)
	client.conn = connect
	return client
}

// connect TCP连接
func (client *HpClient) connect(isHpServer bool) net.Conn {
	//拨号远程地址，简历tcp连接
	conn, err := net.Dial("tcp", client.host+":"+strconv.Itoa(client.port))
	client.clientHandleError(err, "client conn error")
	if isHpServer {
		//开启心跳处理
		go func() {
			for {
				time.Sleep(3 * time.Second)
				client.WriteHpMessage(
					&HpMessage.HpMessage{
						Type: HpMessage.HpMessage_KEEPALIVE,
					},
				)
			}
		}()
	}
	return conn
}

// ReadHpMessage 读取HP通信数据
func (client *HpClient) ReadHpMessage(f func(message *HpMessage.HpMessage)) {
	go func() {
		reader := bufio.NewReader(client.conn)
		//读消息
		for {
			decode, err := Protol.Decode(reader)
			if err != nil {
				continue
			}
			f(decode)
		}
	}()
}

// ReadLocalMessage 读取本地代理通信数据
func (client *HpClient) ReadLocalMessage(remote *HpClient, message *HpMessage.HpMessage) {
	go func() {
		reader := bufio.NewReader(client.conn)
		//读消息
		for {
			size := reader.Size()
			if size > 0 {
				pkg := make([]byte, size)
				reader.Read(pkg)
				hpMessage := &HpMessage.HpMessage{
					Type:     HpMessage.HpMessage_DATA,
					Data:     pkg,
					MetaData: &HpMessage.HpMessage_MetaData{ChannelId: message.MetaData.ChannelId},
				}
				remote.WriteHpMessage(hpMessage)
			}
		}
	}()
}

// WriteHpMessage 写HP 通信数据
func (client *HpClient) WriteHpMessage(h *HpMessage.HpMessage) {
	client.conn.Write(Protol.Encode(h))
}

// Write 写原始数据
func (client *HpClient) Write(b []byte) {
	client.conn.Write(b)
}
