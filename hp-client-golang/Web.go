package main

import (
	"container/list"
	"embed"
	"github.com/gin-gonic/gin"
	"hp-client-golang/tcp"
	"io/ioutil"
	"log"
	"net/http"
	"net/url"
	"strconv"
	"strings"
	"sync"
	"time"
)

//go:embed web/*
var fs embed.FS

//var API="http://127.0.0.1:9090"
var API = "http://ksweb.club:9090"

var MessageGroup = list.New()

var ConnGroup = sync.Map{}

type ServerInfo struct {
	Domain      string
	Server      string
	ProxyServer string
	Status      bool
}

type Log struct {
	Domain string
	Msg    string
}

func Post(uri string, data url.Values) string {
	resp, err := http.PostForm(API+uri, data)
	if err != nil {
		// handle error
	}
	defer resp.Body.Close()
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		return "nil"
	}
	return string(body)
}
func Get(uri string) string {
	resp, err := http.Get(API + uri)
	if err != nil {
		// handle error
	}
	defer resp.Body.Close()
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		return "nil"
	}
	return string(body)
}

func Proxy(server_ip string, server_port int, username string, password string, remote_port int, ip string, port int) bool {
	_, ok := ConnGroup.Load(username)
	if ok {
		return false
	}
	hpClient := tcp.NewHpClient(func(message string) {
		log.Printf(message)
		if MessageGroup.Len() > 100 {
			front := MessageGroup.Front()
			if front != nil {
				MessageGroup.Remove(front)
			}
		}
		log.Println("添加数据 大小：", MessageGroup.Len())
		MessageGroup.PushFront(Log{Domain: username, Msg: message})
	})
	hpClient.Connect(server_ip, server_port, username, password, remote_port, ip, port)
	go func() {
		for {
			if !hpClient.GetStatus() {
				if hpClient.IsKill() {
					return
				}
				hpClient.Connect(server_ip, server_port, username, password, remote_port, ip, port)
				MessageGroup.PushFront(Log{Domain: username, Msg: "正在重连"})
			}
			time.Sleep(time.Duration(5) * time.Second)
		}
	}()
	ConnGroup.Store(username, hpClient)
	return true
}

func StartWeb(port int) {
	e := gin.Default()
	e.StaticFS("/static", http.FS(fs))
	e.POST("/user/login", func(context *gin.Context) {
		username := context.PostForm("username")
		password := context.PostForm("password")
		post := Post("/user/login", url.Values{"username": {username}, "password": {password}})
		context.String(http.StatusOK, post)
	})
	e.POST("/user/reg", func(context *gin.Context) {
		username := context.PostForm("username")
		password := context.PostForm("password")
		post := Post("/user/reg", url.Values{"username": {username}, "password": {password}})
		context.String(http.StatusOK, post)
	})
	e.GET("/load/data", func(context *gin.Context) {
		post := Get("/load/data")
		context.String(http.StatusOK, post)
	})
	e.POST("/server/proxy", func(context *gin.Context) {
		ip := context.PostForm("ip")
		port := context.PostForm("port")
		server_info := context.PostForm("server_info")
		domain := context.PostForm("domain")
		remote_port := context.PostForm("remote_port")
		password := context.PostForm("password")
		split := strings.Split(server_info, ":")
		ato1, _ := strconv.Atoi(split[1])
		ato2, _ := strconv.Atoi(remote_port)
		ato3, _ := strconv.Atoi(port)
		Proxy(split[0], ato1, domain, password, ato2, ip, ato3)
		context.Redirect(http.StatusMovedPermanently, "/static/web/center.html")
	})

	e.GET("/server/log", func(context *gin.Context) {
		front := MessageGroup.Front()
		if front != nil {
			value := front.Value
			MessageGroup.Remove(front)
			context.JSON(http.StatusOK, value.(Log))
		} else {
			context.JSON(http.StatusOK, nil)
		}
	})

	e.GET("/server/info", func(context *gin.Context) {
		ret := make([]*ServerInfo, 0)
		ConnGroup.Range(func(key, value interface{}) bool {
			client := value.(*tcp.HpClient)
			ret = append(ret, &ServerInfo{
				Domain:      key.(string),
				Server:      client.GetServer(),
				ProxyServer: client.GetProxyServer(),
				Status:      client.GetStatus(),
			})
			return true
		})
		context.JSON(http.StatusOK, ret)
	})
	e.GET("/server/stop", func(context *gin.Context) {
		domain := context.Query("domain")
		load, ok := ConnGroup.Load(domain)
		if ok {
			client := load.(*tcp.HpClient)
			client.Close()
			ConnGroup.Delete(domain)
		}
		context.Redirect(http.StatusMovedPermanently, "/static/web/center.html")
	})
	e.GET("/", func(c *gin.Context) {
		c.Redirect(http.StatusMovedPermanently, "/static/web/login.html")
	})
	if port <= 0 {
		port = 10240
	}
	e.Run(":" + strconv.Itoa(port))
}
