package hello;

import hello.service.impl.BeanTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
//需要在spring.factories 中指定来初始化

public class TestApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
	public static BeanTest beanTest;

	@Autowired
	public void setBeanTest(BeanTest beanTest){
		this.beanTest = beanTest;
	}

	public TestApplicationListener(){
		System.out.println("TestApplicationListener 初始化");
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		beanTest.print1();
	}
}