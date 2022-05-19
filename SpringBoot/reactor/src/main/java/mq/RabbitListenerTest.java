package mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tools.TimeUtils;

import java.util.UUID;

@Component
public class RabbitListenerTest {

    @RabbitListener(queues = "demo")
    public void receiveMessage(String message) {
        TimeUtils.printTime("rabbit receive ");
        System.out.println("Rabbit Received Message:" + message);
    }

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public RabbitListenerTest(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

//    @Scheduled(cron = "0/5 * * * * * ")
    public void sendMessage() {
        TimeUtils.printTime("rabbit send ");
        amqpTemplate.convertAndSend("", "demo", UUID.randomUUID());
    }
}
