package ljl.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicExchangeRabbitMQConfig {

    public static final String topicExchangeName = "topicExchange1";

    private static final String queue1BindingKey1 = "black.big.*";
    private static final String queue1BindingKey2 = "black.*.cat";
    private static final String queue2BindingKey = "*.small.*";
    private static final String queue3BindingKey = "#";

    // 声明主题交换机
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(topicExchangeName);
    }

    // 声明消息队列
    @Bean
    public Queue messageQueue1() {
        return new Queue("queue1");
    }

    @Bean
    public Queue messageQueue2() {
        return new Queue("queue2");
    }

    @Bean
    public Queue messageQueue3() {
        return new Queue("queue3");
    }

    // 向主题交换机上绑定队列
    @Bean
    Binding bindingQueue1Exchange1(Queue messageQueue1, TopicExchange topicExchange) {
        return BindingBuilder.bind(messageQueue1)
                .to(topicExchange)
                .with(queue1BindingKey1);
    }

    @Bean
    Binding bindingQueue1Exchange2(Queue messageQueue1, TopicExchange topicExchange) {
        return BindingBuilder.bind(messageQueue1)
                .to(topicExchange)
                .with(queue1BindingKey2);
    }

    @Bean
    Binding bindingQueue2Exchange(Queue messageQueue2, TopicExchange topicExchange) {
        return BindingBuilder.bind(messageQueue2)
                .to(topicExchange)
                .with(queue2BindingKey);
    }

    @Bean
    Binding bindingQueue3Exchange(Queue messageQueue3, TopicExchange topicExchange) {
        return BindingBuilder.bind(messageQueue3)
                .to(topicExchange)
                .with(queue3BindingKey);
    }
}