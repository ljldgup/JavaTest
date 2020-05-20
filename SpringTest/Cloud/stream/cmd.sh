目前来看程序中没有明显的绑定kafka的设置，应该是根据依赖进行自动配置的

bin/windows/zookeeper-server-start.bat  config/zookeeper.properties
bin/windows/kafka-server-start.bat  config/server.properties

kafka-topics.bat --list --zookeeper localhost:2181

bin/windows/kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic usage-detail
bin/windows/kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic usage-cost

java -jar usage-detail-sender/target/*.jar --server.port=9001
java -jar usage-detail-sender/target/*.jar --server.port=9002
java -jar usage-cost-logger/target/*.jar --server.port=9003


kafka-console-producer.bat --broker-list localhost:9092 --topic output < echo message

