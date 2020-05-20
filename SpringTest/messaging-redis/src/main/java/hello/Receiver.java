package hello;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Receiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

	private CountDownLatch latch;

    //这里Autowired放在构造函数前 是构造器注入
	@Autowired
	public Receiver(CountDownLatch latch) {
		this.latch = latch;
	}

	public void receiveMessage(String message) {
		LOGGER.info("Received <" + message + ">");
		latch.countDown();
	}
}
