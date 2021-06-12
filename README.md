#### 介绍
    内网穿透工具.
    
## 云后台管理web

<img src="https://gitee.com/HServer/hp/raw/master/doc/log.png" width="500" />


### 服务端启动
在带有公网 IP 的服务器上执行 Java 命令:
```
java -jar hp-server.jar
```
看到输出表示启动成功:
```
hp server started on port 7731
```
默认服务端端口号是7731。注意这个端口号是 hp 客户端连接 hp 服务器的端口号，并非对外网提供访问的端口号。

### 客户端启动
在没有公网 IP 的机器上执行 Java 命令:
```
java -jar hp-client.jar -server_addr 127.0.0.1 -server_port 7731 -username jishunan  -password 123456 -proxy_addr localhost -proxy_port 3306 -remote_port 10000
```

参数说明:
- `server_addr` hp 服务端的网络地址，即 hp 服务端运行的服务器外网 IP 或 hostname
- `server_port` hp 服务端的端口
- `password` hp 服务端的 password
- `username` hp 服务端的 username
- `proxy_addr` 被代理的应用网络地址
- `proxy_port` 被代理的应用端口号
- `remote_port` hp 服务端对外访问该应用的端口

启动成功后可以通过 server_addr:remote_port 访问被代理的应用，如果被代理的应用是 HTTP 应用，可以通过 http://211.161.xxx.xxx:10000 在外网访问。


### 安卓客服端

<img src="https://gitee.com/HServer/hp/raw/master/doc/a.jpg" width="500" />
<img src="https://gitee.com/HServer/hp/raw/master/doc/b.jpg" width="500" />


[![HServer/hp-android-client](https://gitee.com/HServer/hp-android-client/widgets/widget_card.svg?colors=4183c4,ffffff,ffffff,e3e9ed,666666,9b9b9b)](https://gitee.com/HServer/hp-android-client)
