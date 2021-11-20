package job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSConsumer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Topic;
import java.time.LocalTime;

@Component
public class Publisher {
	@Autowired
	private JmsMessagingTemplate jms;

	@Autowired
	private Queue queue;

	@Autowired
	private Topic topic;

	@Scheduled(cron = "0/5 * * * * ?")
	public String send() {
		jms.convertAndSend(queue, "queue " + LocalTime.now());
		jms.convertAndSend(topic, "topic " + LocalTime.now());
		return "queue 发送成功";
	}

	//destination为topic需要把spring.jms.pub-sub-domain=true 注释放开。
	@JmsListener(destination = "out.queue")
	public void consumerQueue(String msg) {
		System.out.println("JmsListener queue:" + LocalTime.now() + " receive "+ msg);
	}

	//destination为topic需要把spring.jms.pub-sub-domain=true 注释放开。
	@JmsListener(destination = "publish.topic")
	public void consumerTopic(String msg) {
		System.out.println("JmsListener topic:" + LocalTime.now() + " receive " + msg);
	}
}
