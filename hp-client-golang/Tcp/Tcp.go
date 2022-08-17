package Tcp

import (
	"bufio"
	"fmt"
	"hp-client-golang/HpMessage"
	"hp-client-golang/Protol"
	"io/ioutil"
	"log"
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

// NewHpClient 创建实体
func NewHpClient(Host string, Port int) *HpClient {
	client := &HpClient{
		host: Host,
		port: Port,
	}
	connect := client.connect()
	client.conn = connect
	return client
}

// connect TCP连接
func (client *HpClient) connect() net.Conn {
	//拨号远程地址，简历tcp连接
	conn, err := net.Dial("tcp", client.host+":"+strconv.Itoa(client.port))
	client.clientHandleError(err, "client conn error")
	return conn
}

// WriteHpMessage 写HP 通信数据
func (client *HpClient) WriteHpMessage(h *HpMessage.HpMessage) {
	client.conn.Write(Protol.Encode(h))
}

// ReadHpMessage 读取HP通信数据
func (client *HpClient) ReadHpMessage(f func(client *HpClient, message *HpMessage.HpMessage)) {
	go func() {
		reader := bufio.NewReader(client.conn)
		//读消息
		for {
			decode, err := Protol.Decode(reader)
			if err != nil {
				panic(err)
			}
			if decode != nil {
				f(client, decode)
			}
		}
	}()
}

// ReadLocalMessage 读取本地代理通信数据
func (client *HpClient) ReadLocalMessage(remote *HpClient) {
	reader := bufio.NewReader(client.conn)
	//读消息
	for {
		all, err := ioutil.ReadAll(reader)
		if err != nil {
			panic(err)
		}
		time.Sleep(time.Second * 2)
		log.Printf("本地响应：字节大小：%d---内容：%s\n", len(all), string(all))
	}
}

// Write 写原始数据
func (client *HpClient) Write(b []byte) {
	client.conn.Write(b)
}

func (client *HpClient) Close() {
	client.conn.Close()
}
