package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Producer {
    public static void main(String[] args) throws JMSException, InterruptedException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        // 第一个参数：是否开启事务。如果true开启事务，第二个参数无意义。一般不开启事务false。
        // 第二个参数：应答模式。自动应答或者手动应答。一般自动应答。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("control.parameter.topic");
        MessageProducer producer = session.createProducer(topic);
//        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        // 7、创建一个Message对象，可以使用TextMessage。
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) sb.append("一个ActiveMQ队列目的地");
        String text = sb.toString();
        ExecutorService pool = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 500000000; i++) {
            TimeUnit.MILLISECONDS.sleep(10000);
            //这里用Executors.newCachedThreadPool,或者没有sleep会瞬间就会卡死, 该作业属于io密集型，多线程的上传速度远高于当个线程
            if (i % 1000 == 0) System.out.println(i);
            TextMessage textMessage = session.createTextMessage(i + text);
            // 8、发送消息
            pool.execute(() -> {
                try {
                    producer.send(textMessage);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });
//            TimeUnit.MILLISECONDS.sleep(20);
        }
        // 9、关闭资源
        producer.close();
        session.close();
        connection.close();
    }
}
