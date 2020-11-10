# zookeeper 要设置admin.serverPort=8888，默认8080会冲突
D:\tools\zookeeper\apache-zookeeper-3.5.5-bin\apache-zookeeper-3.5.5-bin\bin\zkServer.cmd

D:\tools\kafka_2.12-2.6.0\bin\windows\kafka-server-start.bat  D:\tools\kafka_2.12-2.6.0\config\server.properties

D:\tools\kafka_2.12-2.6.0\bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic test

java -jar build\libs\messaging-stomp-websocket-0.0.1-SNAPSHOT.jar --server.port=8081 --kafka.id=a --kafka.topic=test

java -jar build\libs\messaging-stomp-websocket-0.0.1-SNAPSHOT.jar --server.port=8082 --kafka.id=b --kafka.topic=test
