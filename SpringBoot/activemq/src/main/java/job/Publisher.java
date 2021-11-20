package job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSConsumer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Topic;
import java.time.LocalTime;
import java.util.Random;

@Service
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

	//两个注解都没有使事务生效,貌似Transactional没有被代理
	//生成JmsTransactionManager事务管理器后生效，单一数据库，springboot可以自动识别，但是mq貌似不行
	@Transactional(transactionManager = "JmsTransactionManager", rollbackFor = Exception.class)
//	@JmsListener(destination = "fake")
	public void send() throws Exception {
		LocalTime localTime = LocalTime.now();
		jms.convertAndSend(queue, "queue " + localTime);
		jms.convertAndSend(topic, "topic " + localTime);
		if(random.nextInt() % 2 == 1) {
			throw new Exception(localTime + " send failed");
		}
		Publisher publisher = applicationContext.getBean(this.getClass());
	}


}
