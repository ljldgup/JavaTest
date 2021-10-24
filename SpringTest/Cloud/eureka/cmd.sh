# 服务注册中心，访问http://127.0.0.1:8761/ http://127.0.0.1:8762/

java -Xss108k -Xmx256m -Xmn80m -jar build/libs/*.jar --spring.profiles.active=peer2 &
java -Xss108k -Xmx256m -Xmn80m -jar build/libs/*.jar --spring.profiles.active=peer1 &