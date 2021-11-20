package job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Random;

@Component
public class Consumer {
	@Autowired
	private Random random;
	@Autowired
	private ApplicationContext applicationContext;

	//destination为topic需要把spring.jms.pub-sub-domain=true 注释放开。
	@JmsListener(destination = "publish.queue")
	public void consumerQueue(String msg) throws Exception {
		LocalTime localTime = LocalTime.now();
		System.out.println("JmsListener queue:" + localTime + " receive "+ msg);
		if(random.nextInt() % 2 == 1) {
			//JmsListener接收队列时，抛出异常可以回滚，会再次收到一样的消息，queue，topic都可以
			throw new Exception(msg + " receive failed");
		}

		//没有被代理，JmsListener是自己接收信息的，应该是注册实现的，不需要代理生效
		Consumer consumer = applicationContext.getBean(this.getClass());
	}

	//destination为topic需要把spring.jms.pub-sub-domain=true 注释放开。
	@JmsListener(destination = "publish.topic")
	public void consumerTopic(String msg) throws Exception {
		LocalTime localTime = LocalTime.now();
		System.out.println("JmsListener topic:" + localTime + " receive " + msg);
		if(random.nextInt() % 2 == 1) {
			//JmsListener接收队列时，抛出异常可以回滚，会再次收到一样的消息，queue，topic都可以
			throw new Exception(msg + " receive failed");
		}
	}
}
