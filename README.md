# 官网
官网线上地址：http://ksweb.club:9090

#### 介绍
我们采用的是数据转发实现 稳定性可靠性是有保证的即便是极端的环境只要能上网就能实现穿透。我们支持TCP级别的所有协议，针对HTTP协议做了大量的优化工作， 可以实现 http/https ws/wss 协议。同时还免费赠送一个二级域名，不仅如此，我们还支持 自定义域名，支持本地配置https证书，同时我们传输协议使用proto协议，如果你是一个程序员开发者，可以关注或者参与或者码云提交PR， 我们提供了 java 和 golang 的客服端的实现 有了这样级别的语言支持，目前我们在主流平台上都可以进行穿透，比如 电脑 手机 openwrt nas docker 等设备，未来期望实现硬件级别的设备，只需家里给一个小设备供电就能实现 穿透功能.

### 原理图

<img src="https://gitee.com/HServer/hp/raw/master/doc/img_1.png" width="500" />


## 云后台管理web

<img src="https://gitee.com/HServer/hp/raw/master/doc/img_2.png" width="500" />



### 安卓客服端
<img src="https://gitee.com/HServer/hp/raw/master/doc/d.jpg" width="500" />
<img src="https://gitee.com/HServer/hp/raw/master/doc/e.jpg" width="500" />
<img src="https://gitee.com/HServer/hp/raw/master/doc/f.jpg" width="500" />

### Golang客服端
为了跨平台我们提供golang的实现
<img src="https://gitee.com/HServer/hp/raw/master/doc/c.png" width="500" />

# docker 方式运行

```shell
docker run -P -d -p 10240:10240  registry.cn-shenzhen.aliyuncs.com/hserver/hp:v7
docker run -P -d -p 10240:10240  registry.cn-shenzhen.aliyuncs.com/hserver/hp:v7-arm64
```



# 二进制文件运行
运行方式
```shell
chmod -R 777 ./hp-client-golang-amd64
./hp-client-golang-amd64
然后访问 127.0.0.1:10240配置穿透
```

[![HServer/hp-android-client](https://gitee.com/HServer/hp-android-client/widgets/widget_card.svg?colors=4183c4,ffffff,ffffff,e3e9ed,666666,9b9b9b)](https://gitee.com/HServer/hp-android-client)
