FROM golang:1.17-alpine

# 在容器内设置 /hello 为当前工作目录
WORKDIR /hp-client-golang-amd64
ADD hp-client-golang-amd64  /hp-client-golang-amd64/hp-client-golang-amd64
RUN chmod -R 777 /hp-client-golang-amd64
ARG username=''
ARG password=''
ARG remote_port=0
ARG server_port=0
ARG server_ip=''
ARG ip=''
ARG port=0
ENV GO111MODULE=on \
    GOPROXY=https://goproxy.cn,direct \
    CGO_ENABLED=0 \
    GOOS=linux \
    GOARCH=amd64 \
    username=${username} \
    password=${password} \
    remote_port=${remote_port} \
    server_ip=${server_ip} \
    server_port=${server_port} \
    ip=${ip} \
    port=${port}

CMD ./hp-client-golang-amd64  -server_ip ${server_ip}  -server_port ${server_port} -username ${username} -password ${password} -remote_port ${remote_port} -ip ${ip} -port ${port}

#docker build -t hp-client .