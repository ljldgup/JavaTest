package Concurrency;

public class SynchronizedTest {
	public String name;
	private  void func1() {
		try {
			//�Ե�ǰ��������
			synchronized(name) {
				
				for(int i = 1; i < 3; i++) {
					
					Thread.sleep(500);
					//��ӡ��ǰid
					System.out.println(name + " func1 " + Thread.currentThread().getId());
				}
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void func2() {
		try {
			//name�Ƕ�̬����ͬ�� �ǶԵ�ǰ��������
			synchronized(name) {
				for(int i = 1; i < 3; i++) {
					
					Thread.sleep(500);
					//��ӡ��ǰid
					System.out.println(name + " func2 " + Thread.currentThread().getId());
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void func3() {
		try {
			//��SynchronizedTest���ж������ã��������������static����һ��Ҳ�����ж���
			synchronized(SynchronizedTest.class) {
				for(int i = 1; i < 3; i++) {
					 Thread.yield();
					Thread.sleep(500);				
					//��ӡ��ǰid
					System.out.println(name + " func3 " + Thread.currentThread().getId());
				}
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public SynchronizedTest(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}
	
	public static void main(String[] args) throws InterruptedException {
		SynchronizedTest test1 = new SynchronizedTest("test 1 ");
		SynchronizedTest test2 = new SynchronizedTest("test 2 ");
		SynchronizedTest test3 = new SynchronizedTest("test 3 ");
		
		//��д���μ�lambda���հ��������ӿ�Functional interface ����
		
		//�������ס���ж���
		new Thread(()->{test1.func3();}).start();
		new Thread(()->{test2.func3();}).start();
		new Thread(()->{test3.func3();}).start();
		
		//ֻʣ���̺߳��ӡ�ָ���
		while(Thread.activeCount() > 1) Thread.sleep(2000);
		System.out.println("-----------------------------------------------");
		
		//��name��������ס����,ǰ2���޷�ͬʱrun
		new Thread(()->{test3.func2();}).start();
		new Thread(()->{test3.func1();}).start();
		new Thread(()->{test2.func1();}).start();
		
		//��̬�������
	}
	
}
