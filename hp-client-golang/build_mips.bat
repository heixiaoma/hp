set CGO_ENABLED=0
set GOOS=linux
set GOARCH=mipsle
go build -o hp-client-golang-mips main.go