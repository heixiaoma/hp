# docker 方式运行

仓库地址：registry.cn-shenzhen.aliyuncs.com/hserver/hp

```shell
docker pull registry.cn-shenzhen.aliyuncs.com/hserver/hp:v3
docker run -P -d -p 5000:5000 -e username=heixiaoma -e password=123456 -e remote_port=5000 -e ip=127.0.0.1 -e port=5000 hp:v3
```

启动设置命令行
```shell
cmd:./hp-client-golang-amd64 -username xx -password 123 -remote_port 5000 -ip 127.0.0.1 -port 5000
```


# 二进制文件运行
运行方式
```shell
./hp-client-golang-amd64 -username xx -password 123 -remote_port 5000 -ip 127.0.0.1 -port 5000
#或者添加config.ini
[hp]
#HP账号
username='heixiaoma'
#HP密码
password='123456'
#外部端口如果没有，服务会随机分配
remote_port=5000


[proxy]
#本地代理的IP
ip='192.168.5.214'
#本地代理的端口
port=5000
```