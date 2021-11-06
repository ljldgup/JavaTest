package hello.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BeanTest2 {

	//循环依赖测试

	private BeanTest beanTest;

	@Autowired
	public void setBeanTest(BeanTest beanTest) {
		this.beanTest = beanTest;
	}



	public BeanTest getBeanTest() {
		return this.beanTest;
	}
}
