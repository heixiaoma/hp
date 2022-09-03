docker 方式运行

仓库地址：registry.cn-shenzhen.aliyuncs.com/hserver/hp

```shell
docker pull registry.cn-shenzhen.aliyuncs.com/hserver/hp:v1
```

- 1.设置映射目录：/hp-client-golang-amd64
- 2.配置文件放在映射目录里：config.ini
```properties
[hp]
#HP账号
username='heixiaoma'
#HP密码
password='123456'
#外部端口如果没有，服务会随机分配
remote_port=8080


[proxy]
#本地代理的IP
ip='192.168.123.85'
#本地代理的端口
port=8080
```
