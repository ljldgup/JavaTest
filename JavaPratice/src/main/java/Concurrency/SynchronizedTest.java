package Concurrency;

public class SynchronizedTest {
	public String name;
	private  void func1() {
		try {
			//对当前对象有用
			synchronized(name) {
				
				for(int i = 1; i < 3; i++) {
					
					Thread.sleep(500);
					//打印当前id
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
			//name是动态对象，同样 是对当前对象有用
			synchronized(name) {
				for(int i = 1; i < 3; i++) {
					
					Thread.sleep(500);
					//打印当前id
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
			//对SynchronizedTest所有对象有用，如果里面锁的是static对象，一样也是所有对象
			synchronized(SynchronizedTest.class) {
				for(int i = 1; i < 3; i++) {
					 Thread.yield();
					Thread.sleep(500);				
					//打印当前id
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
		
		//该写法参见lambda，闭包，函数接口Functional interface 声明
		
		//类的锁锁住所有对象
		new Thread(()->{test1.func3();}).start();
		new Thread(()->{test2.func3();}).start();
		new Thread(()->{test3.func3();}).start();
		
		//只剩主线程后打印分割线
		while(Thread.activeCount() > 1) Thread.sleep(2000);
		System.out.println("-----------------------------------------------");
		
		//锁name，单独锁住对象,前2个无法同时run
		new Thread(()->{test3.func2();}).start();
		new Thread(()->{test3.func1();}).start();
		new Thread(()->{test2.func1();}).start();
		
		//静态对象的锁
	}
	
}
