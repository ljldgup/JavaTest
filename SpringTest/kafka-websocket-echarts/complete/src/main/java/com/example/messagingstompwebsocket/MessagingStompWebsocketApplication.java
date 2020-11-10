package com.example.messagingstompwebsocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class MessagingStompWebsocketApplication {
    private static final Logger log = LoggerFactory.getLogger(MessagingStompWebsocketApplication.class);

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MessagingStompWebsocketApplication.class, args);
    }
    
    //这里无法启动多个spring应用，因为同一个id只能消费以此
    // 通过${}读取配置，注解里貌似不需要value
    @KafkaListener(id = "${kafka.id:ljl}", topics = "${kafka.topic:test}")
    public void kListen(String in) {
        log.info(in);
        messagingTemplate.convertAndSend("/topic/greetings", new Greeting(in));
    }
}
