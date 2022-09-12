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