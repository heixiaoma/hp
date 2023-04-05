package web

import (
	"crypto/md5"
	"embed"
	"encoding/hex"
	"encoding/json"
	"github.com/gin-gonic/gin"
	"github.com/gorilla/websocket"
	HpMessage "hp-client-golang/hpMessage"
	"hp-client-golang/tcp"
	"io"
	"log"
	"net"
	"net/http"
	"net/http/httputil"
	"net/url"
	"strconv"
	"strings"
	"sync"
	"time"
)

//go:embed *
var staticFs embed.FS

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

type DeviceInfo struct {
	Username   string `json:"username"`
	Password   string `json:"password"`
	UserHost   string `json:"userHost"`
	ServerHost string `json:"serverHost"`
	Type       string `json:"type"`
	Domain     string `json:"domain"`
	Port       string `json:"port"`
}

type DeviceData struct {
	Code int           `json:"code"`
	Msg  string        `json:"msg"`
	Data []*DeviceInfo `json:"data"`
}

type CoreVersion struct {
	Id            string `json:"id"`
	VersionCode   string `json:"versionCode"`
	UpdateContent string `json:"updateContent"`
	CreateTime    string `json:"createTime"`
}

type CoreData struct {
	Code int          `json:"code"`
	Msg  string       `json:"msg"`
	Data *CoreVersion `json:"data"`
}

var ApiUrl = ""

var CORE_VERSION = "1.0"

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

func StartWeb(webPort int, coreVersion string) {
	CORE_VERSION = coreVersion
	gin.SetMode(gin.ReleaseMode)
	gin.DefaultWriter = io.Discard
	e := gin.Default()
	e.StaticFS("/static", http.FS(staticFs))

	/**
	添加穿透
	*/
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

		if proxyType != "UDP" {
			if len(domain) == 0 {
				context.JSON(http.StatusOK, &Res{
					Code: -1,
					Msg:  "域名不能为空，如果还没有添加，请菜单里添加域名，然后刷新配置后重试",
				})
				return
			}
		} else {
			domain = "udp:" + ip + ":" + port
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

	/**
	当前穿透列表
	*/
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

	/**
	停止穿透服务
	*/
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

	/**
	内核版本
	*/
	e.GET("/core/version", func(context *gin.Context) {
		resp, err := http.Get(ApiUrl + "/app/getCoreVersion")
		if err != nil {
			log.Println(err)
		}
		defer func(Body io.ReadCloser) {
			err := Body.Close()
			if err != nil {
				log.Println(err)
			}
		}(resp.Body)
		body, err := io.ReadAll(resp.Body)
		if err != nil {
			context.JSON(http.StatusOK, &Res{
				Code: -1,
				Msg:  "检查更新失败",
			})
			return
		}
		data := &CoreData{}
		err = json.Unmarshal(body, data)
		if err != nil || data.Data == nil {
			context.JSON(http.StatusOK, &Res{
				Code: -1,
				Msg:  "检查更新失败",
			})
			return
		}
		//如果相等
		if strings.Compare(data.Data.VersionCode, CORE_VERSION) == 0 {
			context.JSON(http.StatusOK, &Res{
				Code: 200,
				Msg:  "<font color='green'>不需要更新</font><br>当前版本:" + CORE_VERSION + "<br>你的HP内核已经是最新版",
			})
		} else {
			context.JSON(http.StatusOK, &Res{
				Code: 200,
				Msg:  "<font color='red'>需要更新</font><br>当前版本:" + CORE_VERSION + "<br>最新版本:" + data.Data.VersionCode + "<br>更新时间:" + data.Data.CreateTime + "<br>更新内容:" + data.Data.UpdateContent,
			})
		}
	})

	/**
	查询设备ID
	*/
	e.GET("/device/info", func(context *gin.Context) {
		context.JSON(http.StatusOK, deviceID())
	})

	/**
	网页端请求前缀配置
	*/
	e.GET("/api.js", func(context *gin.Context) {
		context.String(http.StatusOK, "var apiAddress = \"/hp\"")
	})
	/**
	反向代理，前缀解析然后反向代理
	*/
	e.Any("/hp/*url", func(c *gin.Context) {
		remote, err := url.Parse(ApiUrl)
		if err != nil {
			panic(err)
		}
		proxy := httputil.NewSingleHostReverseProxy(remote)
		proxy.Director = func(req *http.Request) {
			req.Header = c.Request.Header
			req.Host = remote.Host
			req.URL.Scheme = remote.Scheme
			req.URL.Host = remote.Host
			req.URL.Path = c.Param("url")
		}
		proxy.ServeHTTP(c.Writer, c.Request)
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

func wsSend(msg Log) {
	defer func() {
		recover()
	}()
	ConnWsGroup.Range(func(key, value interface{}) bool {
		WS := key.(*websocket.Conn)
		err := WS.WriteJSON(msg)
		if err != nil {
			return false
		}
		return true
	})
}

func deviceID() string {
	netInterfaces, err := net.Interfaces()
	if err != nil {
		return "NO_ID"
	}
	for _, netInterface := range netInterfaces {
		if netInterface.Flags&net.FlagUp == 0 {
			continue // 排除掉不存在、未启用的网口
		}
		if netInterface.Flags&net.FlagLoopback != 0 {
			continue // 排除掉本地回环的网口
		}
		macAddr := netInterface.HardwareAddr.String()
		if len(macAddr) != 0 {
			h := md5.New()
			h.Write([]byte(macAddr))
			res := hex.EncodeToString(h.Sum(nil))
			return res
		}
	}
	return "NO_ID"
}

// InitCloudDevice /**
func InitCloudDevice(apiAddress string) {
	ApiUrl = apiAddress
	defer func() {
		err := recover()
		if err != nil {
			log.Println("云端资源读取失败", err)
		}
	}()

	id := deviceID()
	if id == "NO_ID" {
		log.Println("未获取道设备ID，不能加载云端资源")
		return
	}
	resp, err := http.Get(ApiUrl + "/config/listDevice?deviceId=" + id)
	if err != nil {
		log.Println(err)
	}
	defer func(Body io.ReadCloser) {
		err := Body.Close()
		if err != nil {
			log.Println(err)
		}
	}(resp.Body)
	body, err := io.ReadAll(resp.Body)
	if err != nil {
		log.Println("获取云端资源失败")
	}
	data := &DeviceData{}
	err = json.Unmarshal(body, data)
	if err == nil {
		//启动穿透的配置调用
		for i := range data.Data {
			info := data.Data[i]
			var hpType HpMessage.HpMessage_MessageType
			if info.Type == "TCP" {
				hpType = HpMessage.HpMessage_TCP
			} else if info.Type == "UDP" {
				hpType = HpMessage.HpMessage_UDP
			} else if info.Type == "TCP_UDP" {
				hpType = HpMessage.HpMessage_TCP_UDP
			} else {
				log.Println("穿透类型未知：" + info.Type)
				return
			}

			split1 := strings.Split(info.ServerHost, ":")
			serverIp := split1[0]
			serverPort, _ := strconv.Atoi(split1[1])
			port, _ := strconv.Atoi(info.Port)

			split2 := strings.Split(info.UserHost, ":")
			userIp := split2[0]
			userPort, _ := strconv.Atoi(split2[1])
			re := Proxy(hpType, serverIp, serverPort, info.Username, info.Password, info.Domain, port, userIp, userPort)
			if re {
				log.Println("内网服务：" + info.UserHost + " 启动成功")
			} else {
				log.Println("内网服务：" + info.UserHost + " 启动失败")
			}
		}

	}
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
