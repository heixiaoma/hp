#### 介绍
我们采用的是数据转发实现 稳定性可靠性是有保证的即便是极端的环境只要能上网就能实现穿透。我们支持TCP级别的所有协议，针对HTTP协议做了大量的优化工作， 可以实现 http/https ws/wss 协议。同时还免费赠送一个二级域名，不仅如此，我们还支持 自定义域名，支持本地配置https证书，同时我们传输协议使用proto协议，如果你是一个程序员开发者，可以关注或者参与或者码云提交PR， 我们提供了 java 和 golang 的客服端的实现 有了这样级别的语言支持，目前我们在主流平台上都可以进行穿透，比如 电脑 手机 openwrt nas docker 等设备，未来期望实现硬件级别的设备，只需家里给一个小设备供电就能实现 穿透功能.

### 原理图

<img src="https://gitee.com/HServer/hp/raw/master/doc/img_1.png" width="500" />


## 云后台管理web

<img src="https://gitee.com/HServer/hp/raw/master/doc/img_2.png" width="500" />


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

### 安卓客服端
<img src="https://gitee.com/HServer/hp/raw/master/doc/a.jpg" width="500" />
<img src="https://gitee.com/HServer/hp/raw/master/doc/b.jpg" width="500" />

### Golang客服端
为了跨平台我们提供golang的实现
<img src="https://gitee.com/HServer/hp/raw/master/doc/c.png" width="500" />

# docker 方式运行

仓库地址：registry.cn-shenzhen.aliyuncs.com/hserver/hp

```shell
docker pull registry.cn-shenzhen.aliyuncs.com/hserver/hp:v4
docker run -P -d -p 5000:5000 -e -server_ip=ksweb.club -e server_port=9091 -e username=heixiaoma -e password=123456 -e remote_port=5000 -e ip=127.0.0.1 -e port=5000 hp:v4
```



# 二进制文件运行
运行方式
```shell
./hp-client-golang-amd64 -server_ip ksweb.club -server_port 9091 -username xx -password 123 -remote_port 5000 -ip 127.0.0.1 -port 5000
#或者添加config.ini
[hp]
#HP账号
username='heixiaoma'
#HP密码
password='123456'
#外部端口如果没有，服务会随机分配
remote_port=8080
#穿透服务的IP
server_ip='ksweb.club'
#穿透服务的端口
server_port=9091

[proxy]
#本地代理的IP
ip='192.168.123.85'
#本地代理的端口
port=8080
```

[![HServer/hp-android-client](https://gitee.com/HServer/hp-android-client/widgets/widget_card.svg?colors=4183c4,ffffff,ffffff,e3e9ed,666666,9b9b9b)](https://gitee.com/HServer/hp-android-client)
