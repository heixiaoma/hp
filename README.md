#### 介绍
    内网穿透工具.
    
## 启动
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

指定端口号和 password :
```
java -jar hp-server.jar -port 9000 -password password123
```
默认情况下 hp 服务端未指定 password ，任何客户端都可以直接连接并使用 hp 服务器，这样很不安全，建议使用 hp 服务端时指定一个 password 作为连接服务端的密码。

### 客户端启动
在没有公网 IP 的机器上执行 Java 命令:
```
java -jar hp-client.jar -server_addr 211.161.xxx.xxx -server_port 7731 -password password123 -proxy_addr localhost -proxy_port 8080 -remote_port 10000
```

参数说明:
- `server_addr` hp 服务端的网络地址，即 hp 服务端运行的服务器外网 IP 或 hostname
- `server_port` hp 服务端的端口
- `password` hp 服务端的 password
- `proxy_addr` 被代理的应用网络地址
- `proxy_port` 被代理的应用端口号
- `remote_port` hp 服务端对外访问该应用的端口

启动成功后可以通过 server_addr:remote_port 访问被代理的应用，如果被代理的应用是 HTTP 应用，可以通过 http://211.161.xxx.xxx:10000 在外网访问。
