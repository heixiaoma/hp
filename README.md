# 官网
官网线上地址：http://ksweb.club:9090

#### 介绍
我们采用的是数据转发实现 稳定性可靠性是有保证的即便是极端的环境只要能上网就能实现穿透。
我们支持TCP和UDP协议，数据传输上我们使用了Snappy 进行数据压缩。针对 http/https ws/wss 协议做了大量的优化工作可以更加灵活的控制。让用户使用更佳舒服简单。

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
docker run -P -d -p 10240:10240  registry.cn-shenzhen.aliyuncs.com/hserver/hp:latest
docker run -P -d -p 10240:10240  registry.cn-shenzhen.aliyuncs.com/hserver/hp:latest-arm64
```



# 二进制文件运行
运行方式
```shell
chmod -R 777 ./hp-client-golang-amd64
./hp-client-golang-amd64
然后访问 127.0.0.1:10240配置穿透
```

[![HServer/hp-android-client](https://gitee.com/HServer/hp-android-client/widgets/widget_card.svg?colors=4183c4,ffffff,ffffff,e3e9ed,666666,9b9b9b)](https://gitee.com/HServer/hp-android-client)
