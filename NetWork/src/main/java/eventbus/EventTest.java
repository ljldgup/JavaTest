package eventbus;

import com.google.common.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

public class EventTest {
	public static void main(String[] args) throws InterruptedException {
		EventBus eventBus = new EventBus();
		EventListener listener = new EventListener();
		eventBus.register(listener);

		//是按照入参类类型投递的，基类能收到继承类的消息
		eventBus.post(new TestEvent());
		System.out.println("---------------------------------------");
		TimeUnit.SECONDS.sleep(1);
		eventBus.post(new TestTestEvent());
		TimeUnit.SECONDS.sleep(1);
		System.out.println("---------------------------------------");
		eventBus.post(new String(""));
	}
}
