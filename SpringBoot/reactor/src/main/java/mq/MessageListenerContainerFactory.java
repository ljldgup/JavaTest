package mq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListenerContainerFactory {
    @Autowired
    private CachingConnectionFactory connectionFactory;
    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private Exchange exchange;

    public SimpleMessageListenerContainer create(String queueName) {
        Queue queue = QueueBuilder.nonDurable(queueName).maxLength(10).autoDelete().exclusive().build();
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("").noargs());

        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        //在容器中放入刚创建好的队列
        simpleMessageListenerContainer.setQueueNames(queue.getName());
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        /*
        //设置当前的消费者数量
        simpleMessageListenerContainer.setConcurrentConsumers(1);
        simpleMessageListenerContainer.setMaxConcurrentConsumers(1);
        //设置消息是否重回队列
        simpleMessageListenerContainer.setDefaultRequeueRejected(false);
        //设置自动确认消息simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        //设置暴露监听器通道
        simpleMessageListenerContainer.setExposeListenerChannel(true);
        */

        return simpleMessageListenerContainer;
    }
    public SimpleMessageListenerContainer create(){
        Queue queue = QueueBuilder.nonDurable("demo").maxLength(10).autoDelete().exclusive().build();
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("").noargs());

        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        //在容器中放入刚创建好的队列
        simpleMessageListenerContainer.setQueueNames(queue.getName());
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);

        return simpleMessageListenerContainer;
    }

    public MessageListenerContainer createMessageListenerContainer(String queueName) {
        Queue queue = QueueBuilder.nonDurable("demo").maxLength(10).autoDelete().exclusive().build();
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("").noargs());

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.addQueueNames(queueName);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return container;
    }
}
