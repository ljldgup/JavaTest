package mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RabbitListenerTest {

    @RabbitListener(queues = "demo")
    public void receiveMessage(String message) {
        System.out.println("Received Message:" + message);
        System.out.println();
    }

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public RabbitListenerTest(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Scheduled(cron = "0/5 * * * * * ")
    public void sendMessage() {
        amqpTemplate.convertAndSend("", "demo", UUID.randomUUID());
    }
}
