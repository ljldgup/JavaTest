package DesignPatterns.FactoryPattern;

public class HuaWei extends PhoneStore {

	public HuaWei() {
		super();
		// TODO Auto-generated constructor stub
		//���󹤳������������˾�̬������
		core = ProcessorFactory.createProcessor("HuaWei");
	}

	@Override
	public String CreateOrder() {
		// TODO Auto-generated method stub
		//��������������û�����½���Ʒ�ֱ࣬�������ַ�����
		return "MATE 20";
	}
	
	public static void main(String[] args) {
		HuaWei hw = new HuaWei();
		System.out.println(hw.CreateOrder());
		System.out.println(hw.core.GetProcessor());
	}

	
}
