set CGO_ENABLED=0
set GOOS=linux
set GOARCH=arm64
go build -o hp-client-golang-arm64 main.go