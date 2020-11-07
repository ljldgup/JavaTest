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

    @KafkaListener(id = "dltGroup", topics = "test")
    public void kListen(String in) {
        log.info(in);
        messagingTemplate.convertAndSend("/topic/greetings", new Greeting(in));
    }
}
