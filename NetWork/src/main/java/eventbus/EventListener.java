package eventbus;

import com.google.common.eventbus.Subscribe;

public class EventListener {


	@Subscribe
	public void processEvent(TestEvent event) {
		System.out.println("process TestEvent, class name:" + event.getClass().getName());
	}


	@Subscribe
	public void processWorldEvent(TestTestEvent event) {
		System.out.println("process TestTestEvent, class name:" + event.getClass().getName());	}

	@Subscribe
	public void processObject(Object object) {
		System.out.println("process Object, class:" + object.getClass().getSimpleName());
	}

}
