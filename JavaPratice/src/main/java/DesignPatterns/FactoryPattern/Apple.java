package DesignPatterns.FactoryPattern;

public class Apple extends PhoneStore {

	public Apple() {
		super();
		// TODO Auto-generated constructor stub
		core = ProcessorFactory.createProcessor("Apple");
	}

	@Override
	public String CreateOrder() {
		// TODO Auto-generated method stub
		return "IPhone XMAX";
	}

	public static void main(String[] args) {
		Apple a = new Apple();
		System.out.println(a.CreateOrder());
		System.out.println(a.core.GetProcessor());

	}
}
