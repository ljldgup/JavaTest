package job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSConsumer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Topic;
import java.time.LocalTime;
import java.util.Random;

@Component
public class Publisher {
	@Autowired
	private JmsMessagingTemplate jms;

	@Autowired
	private Queue queue;

	@Autowired
	private Topic topic;

	@Autowired
	private Random random;

	@Autowired
	private ApplicationContext applicationContext;


	@Scheduled(cron = "0/5 * * * * ?")
	public void job() {
		try {
			//注意这里直接调用send是不会有@Transaction效果的，因为本身没被代理
			Publisher publisherProxy = applicationContext.getBean(this.getClass());
			publisherProxy.send();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	//两个注解都没有使事务生效
//	@Transactional(rollbackFor = Exception.class)
//	@JmsListener(destination = "fake")
	public  void send() throws Exception {
		LocalTime localTime = LocalTime.now();
		jms.convertAndSend(queue, "queue " + localTime);
		jms.convertAndSend(topic, "topic " + localTime);
		if(random.nextInt() % 2 == 1) {
			throw new Exception(localTime + " send failed");
		}
	}


}
