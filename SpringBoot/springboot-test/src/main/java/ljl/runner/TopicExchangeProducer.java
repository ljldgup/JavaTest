package ljl.runner;

import ljl.config.TopicExchangeRabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicExchangeProducer {
    @Autowired
    private AmqpTemplate rabbitMQTemplate;

    public void send(String msg, String routingKey) {
        rabbitMQTemplate.convertAndSend(TopicExchangeRabbitMQConfig.topicExchangeName, routingKey, msg);
    }
}