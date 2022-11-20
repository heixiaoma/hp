package web

import (
	"embed"
	"github.com/gin-gonic/gin"
	"github.com/gorilla/websocket"
	HpMessage "hp-client-golang/hpMessage"
	"hp-client-golang/tcp"
	"io"
	"log"
	"net/http"
	"net/url"
	"strconv"
	"strings"
	"sync"
	"time"
)

//go:embed *
var staticFs embed.FS
var API = "http://ksweb.club:9090"

// 创建一个以域名为主得map
var ConnGroup = sync.Map{}

// 创建一个ws得链接map
var ConnWsGroup = sync.Map{}

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

type Res struct {
	Code int
	Msg  string
}

func Post(uri string, data url.Values) string {
	resp, err := http.PostForm(API+uri, data)
	if err != nil {
		// handle error
	}
	defer resp.Body.Close()
	body, err := io.ReadAll(resp.Body)
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
	body, err := io.ReadAll(resp.Body)
	if err != nil {
		return "nil"
	}
	return string(body)
}

func Proxy(messageType HpMessage.HpMessage_MessageType, server_ip string, server_port int, username string, password string, domain string, remote_port int, ip string, port int) bool {
	_, ok := ConnGroup.Load(domain)
	if ok {
		return false
	}
	hpClient := tcp.NewHpClient(func(message string) {
		log.Printf(message)
		wsSend(Log{Domain: domain, Msg: message})
	})
	hpClient.Connect(messageType, server_ip, server_port, username, password, domain, remote_port, ip, port)
	go func() {
		for {
			if hpClient.IsKill() {
				ConnGroup.Delete(domain)
				return
			}
			if !hpClient.GetStatus() {
				hpClient.Connect(messageType, server_ip, server_port, username, password, domain, remote_port, ip, port)
				wsSend(Log{Domain: domain, Msg: "正在重连"})
			}
			time.Sleep(time.Duration(5) * time.Second)
		}
	}()
	ConnGroup.Store(domain, hpClient)
	return true
}

func StartWeb(webPort int, api string) {
	API = api
	gin.SetMode(gin.ReleaseMode)
	gin.DefaultWriter = io.Discard
	e := gin.Default()
	e.StaticFS("/static", http.FS(staticFs))
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
		username := context.PostForm("username")
		domain := context.PostForm("domain")
		remote_port := context.PostForm("remote_port")
		password := context.PostForm("password")
		proxyType := context.PostForm("type")
		messageType := HpMessage.HpMessage_TCP
		if proxyType == "TCP" {
			messageType = HpMessage.HpMessage_TCP
		} else if proxyType == "UDP" {
			messageType = HpMessage.HpMessage_UDP
		} else {
			messageType = HpMessage.HpMessage_TCP_UDP
		}

		if len(domain) == 0 {
			context.JSON(http.StatusOK, &Res{
				Code: -1,
				Msg:  "域名不能为空，如果还没有添加，请菜单里添加域名，然后刷新配置后重试",
			})
			return
		}

		if len(server_info) == 0 {
			context.JSON(http.StatusOK, &Res{
				Code: -1,
				Msg:  "未选择穿透的服务器，请选择后重试",
			})
			return
		}

		if len(ip) == 0 || len(port) == 0 {
			context.JSON(http.StatusOK, &Res{
				Code: -1,
				Msg:  "要穿透的内网服务，未正确填写信息，请认真填写",
			})
			return
		}
		split := strings.Split(server_info, ":")
		ato1, _ := strconv.Atoi(split[1])
		ato2, _ := strconv.Atoi(remote_port)
		ato3, _ := strconv.Atoi(port)
		re := Proxy(messageType, split[0], ato1, username, password, domain, ato2, ip, ato3)
		if re {
			context.JSON(http.StatusOK, &Res{
				Code: 200,
				Msg:  "添加成功",
			})
		} else {
			context.JSON(http.StatusOK, &Res{
				Code: -1,
				Msg:  "添加失败！检查域名是否已经被使用。",
			})
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
			client.Kill()
			ConnGroup.Delete(domain)
			context.JSON(http.StatusOK, &Res{
				Code: 200,
				Msg:  "成功",
			})
		} else {
			context.JSON(http.StatusOK, &Res{
				Code: 200,
				Msg:  "失败",
			})
		}
	})

	e.POST("/server/portAdd", func(context *gin.Context) {
		userId := context.PostForm("userId")
		port := context.PostForm("port")
		post := Post("/server/portAdd", url.Values{"userId": {userId}, "port": {port}})
		println(post)
		context.String(http.StatusOK, post)
	})
	e.GET("/server/portList", func(context *gin.Context) {
		userId := context.Query("userId")
		post := Get("/server/portList?userId=" + userId)
		context.String(http.StatusOK, post)
	})
	e.GET("/server/portRemove", func(context *gin.Context) {
		userId := context.Query("userId")
		port := context.Query("port")
		post := Post("/server/portRemove", url.Values{"userId": {userId}, "port": {port}})
		context.String(http.StatusOK, post)
	})

	e.POST("/server/domainAdd", func(context *gin.Context) {
		userId := context.PostForm("userId")
		domain := context.PostForm("domain")
		post := Post("/server/domainAdd", url.Values{"userId": {userId}, "domain": {domain}})
		context.String(http.StatusOK, post)
	})

	e.GET("/server/domainList", func(context *gin.Context) {
		userId := context.Query("userId")
		post := Get("/server/domainList?userId=" + userId)
		context.String(http.StatusOK, post)
	})
	e.GET("/server/domainRemove", func(context *gin.Context) {
		userId := context.Query("userId")
		domain := context.Query("domain")
		post := Post("/server/domainRemove", url.Values{"userId": {userId}, "domain": {domain}})
		context.String(http.StatusOK, post)
	})

	e.GET("/server/pay", func(context *gin.Context) {
		post := Get("/server/pay")
		context.String(http.StatusOK, post)
	})

	e.GET("/server/logList", func(context *gin.Context) {
		username := context.Query("username")
		page := context.Query("page")
		post := Get("/statistics/getMyInfo?page=" + page + "&username=" + username)
		context.String(http.StatusOK, post)
	})

	e.GET("/", func(context *gin.Context) {
		context.Redirect(http.StatusMovedPermanently, "/static/login.html")
	})

	e.GET("/ws", ws)
	if webPort <= 0 {
		webPort = 10240
	}
	e.Run(":" + strconv.Itoa(webPort))
}

var upGrader = websocket.Upgrader{
	CheckOrigin: func(r *http.Request) bool {
		return true
	},
}

func wsSend(log Log) {
	ConnWsGroup.Range(func(key, value interface{}) bool {
		WS := key.(*websocket.Conn)
		err := WS.WriteJSON(log)
		if err != nil {
			return false
		}
		return true
	})
}

func ws(c *gin.Context) {
	//升级get请求为webSocket协议
	ws, err := upGrader.Upgrade(c.Writer, c.Request, nil)
	if err != nil {
		return
	}
	ConnWsGroup.Store(ws, nil)
	defer func() {
		ConnWsGroup.Delete(ws)
		ws.Close()
	}()
	for {
		//读取ws中的数据
		mt, message, err := ws.ReadMessage()
		if err != nil {
			break
		}
		if string(message) == "ping" {
			message = []byte("pong")
		}
		//写入ws数据
		err = ws.WriteMessage(mt, message)
		if err != nil {
			break
		}
	}
}
