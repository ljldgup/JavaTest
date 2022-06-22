package ljl.runner;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RabbitmqTest {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    TopicExchangeProducer producer;


    @Scheduled(cron = "*/5 * * * * ?")
    public void myTest(){
        producer.send("123","swl.queue1");
    }


    @RabbitListener(queues = "topic*")
    public void routingThree(String in) {
        System.out.println("queue.routing.three：" + in);
    }
}
