package job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

	@Scheduled(cron="0/5 * * * * ?")
	public String send() {
		jms.convertAndSend(queue, "queue" + LocalTime.now());
		jms.convertAndSend(topic, "topic" + LocalTime.now());
		return "queue 发送成功";
	}

	@JmsListener(destination = "out.queue")
	public void consumerMsg(String msg) {
		System.out.println(msg);
	}
}
