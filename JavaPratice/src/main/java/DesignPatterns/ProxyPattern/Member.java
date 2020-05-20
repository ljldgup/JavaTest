package DesignPatterns.ProxyPattern;

import java.lang.reflect.Proxy;

public class Member implements People{
	private int rate;
	private int age;
	private String gender;
	
	public void setRate(int rate) {
		this.rate = rate;
		System.out.println("rate:" + rate);
	}
	
	public void setAge(int age) {
		this.age = age;
		System.out.println("age:" + age);
	}
	
	public void setGender(String gender) {
		this.gender = gender;
		System.out.println("gender:" + gender);
	}
	
	public void display() {
		System.out.println("age:" + age);
		System.out.println("gender:" + gender);
		System.out.println("rate:" + rate);
	}
	
	public static void main(String[] args) {
		People member = new Member();
		myInvocationHandler handler = new myInvocationHandler(member);
		//newProxyInstance动态生成了一个和People类有相同函数结构的类
		//但调用函数时,实际上调用的是InvocationHandler中的重写的invoke函数
		//myInvocationHandler可以用lambda表达式子替代,但对象要外部传入,效果不好
		People proxy1 = (People)Proxy.newProxyInstance(handler.getClass().getClassLoader(), member.getClass().getInterfaces(), handler);


		proxy1.setAge(15);
		proxy1.setRate(5);
	}
}
