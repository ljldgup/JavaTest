# 负载均衡 访问 http://127.0.0.1:9001/ http://127.0.0.1:9002/

java -Xss108k -Xmx256m -Xmn80m -jar build/libs/*.jar --server.port=9001 --eureka.client.serviceUrl.defaultZone=http://127.0.0.1:8761/eureka/ &
java -Xss108k -Xmx256m -Xmn80m -jar build/libs/*.jar --server.port=9002 --eureka.client.serviceUrl.defaultZone=http://127.0.0.1:8762/eureka/ &