package ThinkIntoJava;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class outterClass {
	//���ⳤ�ȵ�������ʮ������
	private BigInteger BInt = new BigInteger("999999999999999999");
	private BigDecimal BDec = new BigDecimal("9.9999999999999999");
	
	static {
		System.out.println("static initate InnerClass1");
	}
	
	{
		//�ò���ÿ��new �ڲ��඼������
		System.out.println("non static initate InnerClass1");
	}
	
	private void testPrivateFunc() {
		for(Method m:outterClass.class.getMethods()) {
			System.out.println(m);
		}
	}
	public class InnerClass1  extends outterClass implements kcuf{
		
		public InnerClass1() {
			// TODO Auto-generated constructor stub
			System.out.println(this.getClass() + "initate");
			BInt = BInt.add(new BigInteger("10000"));
		}
		
		@Override
		public void info() {
			// TODO Auto-generated method stub
			System.out.println(" BInt:" + BInt);
			
		}
	}
	
	public class InnerClass2  extends outterClass implements kcuf{
		
		public InnerClass2() {
			// TODO Auto-generated constructor stub
			System.out.println(this.getClass() + "initate");
			BDec = BDec.add(new BigDecimal("0.21347788"));
		}
		
		@Override
		public void info() {
			// TODO Auto-generated method stub
			System.out.println(" BDec:" + BDec);
		}
	}
	
	public class InnerClass3  extends outterClass implements kcuf{
		public InnerClass3() {
			// TODO Auto-generated constructor stub
			System.out.println(this.getClass() + "initate");
		}
		@Override
		public void info() {
			// TODO Auto-generated method stub
			//�����ⲿ�࣬��������ȥ��
			outterClass.this.testPrivateFunc();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<Class<? extends Base>> getInnerClassList(){
		List rst = new ArrayList<Class<? extends Base>>();
		rst.add(InnerClass1.class);
		rst.add(InnerClass2.class);
		rst.add(InnerClass3.class);
		return rst;
	}
	/*��Ϊ�ڲ����Ƕ�̬�ģ���static�ؼ������Σ�����main�����Ǿ�̬�ģ���ͨ���ڲ�����������ر�����һ�����ã�ָ�򴴽�������Χ�����
	����Ҫ��static�����������ʱ�Ѿ���ʼ���������ڲ���ı����ȴ����ⲿ�ࡣ��Ӧ����������
	DanymicTest test = new StaticCallDynamic().new DanymicTest();*/
	
	@SuppressWarnings("unchecked")
	public static List<kcuf> getInnerClassInstanceList(){
		outterClass outter = new outterClass();
		List rst = new ArrayList<outterClass>();
		rst.add(outter.new InnerClass1());
		rst.add(outter.new InnerClass2());
		rst.add(outter.new InnerClass3());
		return rst;
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		
		for(Class c:outterClass.getInnerClassList()) {
			System.out.println(c.getName());
		}
		
		System.out.println("\n\n");
		
		for( kcuf f:outterClass.getInnerClassInstanceList()) {
			f.info();
		}

	}
}
