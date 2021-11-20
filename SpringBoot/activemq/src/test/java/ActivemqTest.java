import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.*;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ActivemqTest {
	@Autowired
	private JmsMessagingTemplate jms;

	@Autowired
	private Queue queue;

	@Autowired
	private Topic topic;



	@Test
	public void topicTest1() throws JMSException, InterruptedException {
		//topic消费到是消息是实时的，过时不候
		for (int i = 0; i < 100; i++) {
			String message =  jms.receiveAndConvert(topic, String.class);
			System.out.println("test consumer：" + LocalTime.now() + " receive " + message);
			TimeUnit.SECONDS.sleep(20);
		}
	}

	@Test
	public void QueenTest1() throws JMSException, InterruptedException {
		//queue消费到是消息不是实时的，过期的消费不到
		for (int i = 0; i < 100; i++) {
			String message =  jms.receiveAndConvert(queue, String.class);
			System.out.println("test consumer：" + LocalTime.now() + " receive " + message);
			TimeUnit.SECONDS.sleep(20);
		}
	}

}
