package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Consumer {

    public static void main(String[] args) throws JMSException, IOException, InterruptedException {
        // 创建一个ConnectionFactory对象连接MQ服务器
        //jms.prefetchPolicy.all=2限制预读后，不再出现oom
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616?jms.prefetchPolicy.all=2");
        // 创建一个连接对象
        Connection connection;
        connection = connectionFactory.createConnection();
        connection.setClientID("111");
        // 开启连接
        connection.start();
        // 使用Connection对象创建一个Session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建一个Destination对象。topic对象
        Topic topic = session.createTopic("control.parameter.topic");
//        // 使用Session对象创建一个消费者对象。
        MessageConsumer consumer = session.createConsumer(topic);

        //持久消息订阅者会导致消息进入dlq，在磁盘积压
//        TopicSubscriber consumer = session.createDurableSubscriber(topic, "any-name");

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                // 打印结果
                TextMessage textMessage = (TextMessage) message;
                String text;
                try {
                    text = textMessage.getText();
                    System.out.println("这是接收到的消息：" + text);
                } catch (JMSException je ) {
                    je.printStackTrace();
                }

            }

        });
        System.out.println("topic消费者启动。。。。");
        // 等待接收消息
        System.in.read();
        // 关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
