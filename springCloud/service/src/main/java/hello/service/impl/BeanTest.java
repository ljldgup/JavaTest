package hello.service.impl;

import hello.service.TestInterface1;
import hello.service.TestInterface2;
import hello.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanTest implements TestInterface1, TestInterface2 {

//	public BeanTest(BeanTest2 beanTest2){
//		System.out.println("BeanTest 初始化");
//		this.beanTest2 = beanTest2;
//	}

	@Bean
	@Value("#{ T(java.lang.Math).random() * 100.0 }")
	public User userGenerate(double n) {
		System.out.println(n);
		return new User();
	}

	//循环依赖测试
	@Autowired
	BeanTest2 beanTest2;

	@Value("#{ T(java.lang.Math).random() * 100.0 }")
	private double number;

	@Override
	public void print1() {
		System.out.println("test interface11111");
	}

	@Override
	public void print2() {
		System.out.println("test interface22222");
	}

	public void setNumber(double number){
		this.number = number;
	}
}
