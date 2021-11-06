package Concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class CompareAndSetTest implements Runnable {
	private static AtomicBoolean exists = new AtomicBoolean(false);


	private String name;

	public CompareAndSetTest(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		if (exists.compareAndSet(false, true)) {

			System.out.println(name + " enter");
			try {
				System.out.println(name + " working");
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// do nothing
			}
			System.out.println(name + " leave");
			exists.set(false);
		} else {
			System.out.println(name + " give up");
		}

	}


	//compareAndSet关键在于比较设值得过程一次性完成，这样别的操作就不会成功
	// 可以作为乐观锁（比较是否是上次的版本号），自旋锁之类的实现。
	public static void main(String[] args) {
		CompareAndSetTest test1 = new CompareAndSetTest("bar1");
		CompareAndSetTest test2 = new CompareAndSetTest("bar2");
		new Thread(test1).start();
		new Thread(test2).start();
	}
}
