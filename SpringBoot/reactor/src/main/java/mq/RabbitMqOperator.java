package mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

@Controller
public class RabbitMqOperator {
    //我们自己写的工厂
    @Autowired
    MessageListenerContainerFactory containerFactory;

    @PostConstruct()
    public void init() {
        createContainer();
//        receiveMessagesFromQueue();
    }

    public void createContainer() {
        //用自己写的工厂创建一个监听容器
        SimpleMessageListenerContainer container = containerFactory.create();

        Flux.create(sink -> {
            //容器中设置监听器用于接收到消息后使用sink发送给客户端
            container.setupMessageListener((ChannelAwareMessageListener) (Message message, Channel channel) -> {
                if (sink.isCancelled()) {
                    container.stop();
                    return;
                }
                String msg = new String(message.getBody());
                sink.next(msg);
            });

            //启动容器和停止容器
            sink.onRequest(r -> container.start());
            sink.onDispose(container::stop);
        }).subscribe();

    }


    public void receiveMessagesFromQueue() {
        MessageListenerContainer container = containerFactory.createMessageListenerContainer("demo");
        Flux.create(emitter -> {
            container.setupMessageListener((ChannelAwareMessageListener) (message, channle) -> {
                if (emitter.isCancelled()) {
                    container.stop();
                    return;
                }
                String payload = new String(message.getBody());
                System.out.println("收到来自" + "demo" + "的消息：" + payload);
                emitter.next(payload);
                channle.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            });
            emitter.onRequest(v -> container.start());
            emitter.onDispose(() -> {
                container.stop();
            });
        }).subscribe();
    }


}
