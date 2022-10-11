set CGO_ENABLED=0
set GOOS=linux
set GOARCH=arm
set GOARM=7
go build -o hp-client-golang-armv7 main.go