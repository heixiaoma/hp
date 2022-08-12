protoc -I E:\java\hp\hp-common\src\main\proto  --java_out=E:\java\hp\hp-common\src\main\proto E:\java\hp\hp-common\src\main\proto\HpMessage.proto
protoc --go_out=. HpMessage.proto
protoc --python_out=. HpMessage.proto

