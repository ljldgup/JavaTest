package DesignPatterns.FactoryPattern;

public class HuaWei extends PhoneStore {

	public HuaWei() {
		super();
		// TODO Auto-generated constructor stub
		//抽象工厂生产，我用了静态方法。
		core = ProcessorFactory.createProcessor("HuaWei");
	}

	@Override
	public String CreateOrder() {
		// TODO Auto-generated method stub
		//工厂方法生产，没有在新建产品类，直接用了字符串。
		return "MATE 20";
	}
	
	public static void main(String[] args) {
		HuaWei hw = new HuaWei();
		System.out.println(hw.CreateOrder());
		System.out.println(hw.core.GetProcessor());
	}

	
}
