#### 介绍

    内网穿透工具.

## 云后台管理web

<img src="https://gitee.com/HServer/hp/raw/master/doc/log.png" width="500" />

### 服务端启动

在带有公网 IP 的服务器上执行 Java 命令:

```
java -jar hp-server.jar
```

默认服务端端口号是9090

如果自己需要修改配置，可以和jar同目录建立一个配置文件 app.properties 内容如下

```properties
#内网穿透端口和web管理后台都是这个端口
ports=9090
#提示域名，没有域名写IP也可以
host=ksweb.club
#后台密码
password=123456
```

### 客户端启动

在没有公网 IP 的机器上执行 Java 命令:

```
java -jar hp-client.jar -server_addr 127.0.0.1 -server_port 9090 -username jishunan  -password 123456 -proxy_addr localhost -proxy_port 3306 -remote_port 10000
```

参数说明:

- `server_addr` hp 服务端的网络地址，即 hp 服务端运行的服务器外网 IP 或 hostname
- `server_port` hp 服务端的端口
- `password` hp 服务端的 password
- `username` hp 服务端的 username
- `proxy_addr` 被代理的应用网络地址
- `proxy_port` 被代理的应用端口号
- `remote_port` hp 服务端对外访问该应用的端口

启动成功后可以通过 server_addr:remote_port 访问被代理的应用，如果被代理的应用是 HTTP 应用，可以通过 http://xxx.xxx.xxx.xxx:10000 在外网访问。 如果账号没有分配10000
端口给他，那么服务器会随机一个端口

### hp-windows 模块是一个可视化的客服端，可以在支持java的电脑上运行

<img src="https://gitee.com/HServer/hp/raw/master/doc/img.png" width="500" />
<img src="https://gitee.com/HServer/hp/raw/master/doc/img1.png" width="500" />

### 安卓客服端

<img src="https://gitee.com/HServer/hp/raw/master/doc/a.jpg" width="500" />
<img src="https://gitee.com/HServer/hp/raw/master/doc/b.jpg" width="500" />


[![HServer/hp-android-client](https://gitee.com/HServer/hp-android-client/widgets/widget_card.svg?colors=4183c4,ffffff,ffffff,e3e9ed,666666,9b9b9b)](https://gitee.com/HServer/hp-android-client)
