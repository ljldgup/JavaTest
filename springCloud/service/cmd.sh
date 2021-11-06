java -Xss108k -Xmx256m -Xmn80m -jar build/libs/*.jar --server.port=8081 --eureka.client.serviceUrl.defaultZone=http://127.0.0.1:8761/eureka/ &
java -Xss108k -Xmx256m -Xmn80m -jar build/libs/*.jar --server.port=8082 --eureka.client.serviceUrl.defaultZone=http://127.0.0.1:8761/eureka/ &
java -Xss108k -Xmx256m -Xmn80m -jar build/libs/*.jar --server.port=8083 --eureka.client.serviceUrl.defaultZone=http://127.0.0.1:8761/eureka/ --test.blockTime=4000 &
#java -Xss108k -Xmx256m -Xmn80m -jar build/libs/*.jar --server.port=8084 --eureka.client.serviceUrl.defaultZone=http://127.0.0.1:8761/eureka/ &