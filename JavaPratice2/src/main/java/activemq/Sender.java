package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Sender {
    /**
     * 发送消息到activemq的实现方法
     *
     * @param msg //发送消息的内容，为字符串类型
     */
    public void sendMessage(String msg) {

    }

    /**
     * 主方法
     *
     * @param args
     */
    public static void main(String[] args) throws JMSException, InterruptedException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("queue");
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        int i = 0;
        while (i < Integer.MAX_VALUE) {
            TextMessage textMessage = session.createTextMessage();
            String sendMessage ="发送 " + LocalDateTime.now();
            textMessage.setText(sendMessage);
            System.out.println(sendMessage);
            producer.send(textMessage);
            TimeUnit.SECONDS.sleep(10);
        }
        if (connection != null) {
            connection.close();
        }
    }

}