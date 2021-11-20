package job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Job {
	@Autowired
	private ApplicationContext applicationContext;

	@Scheduled(cron = "0/5 * * * * ?")
	public void job() {
		try {
			//注意这里直接调用send是不会有@Transaction效果的，因为本身没被代理
			Publisher publisherProxy = applicationContext.getBean(Publisher.class);
			publisherProxy.send();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
