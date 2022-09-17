set CGO_ENABLED=0
set GOOS=darwin
set GOARCH=arm64
go build -o hp-client-golang-apple-arm64 main.go