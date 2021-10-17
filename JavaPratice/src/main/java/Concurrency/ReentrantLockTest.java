package Concurrency;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class ReentrantLockTest {
	ReentrantLock rLock;
	int usedNums;
	boolean isLocked;

	public ReentrantLockTest() {
		// TODO Auto-generated constructor stub
		// create a ReentrantLock
		// use a time to print seconds
		rLock = new ReentrantLock();
		usedNums = 0;
		isLocked = false;
		ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(1);
		// task1
		pool.scheduleWithFixedDelay(new Runnable() {
			int i = 0;

			public void run() {
				if (i > 15)
					System.exit(0);
				System.out.println("time:" + i + " seconds");
				i++;
			}
		}, 500, 1000, TimeUnit.MILLISECONDS);

//		  //使用ScheduledThreadPoolExecutor 代替timer 
		//500是初始延迟，1000每次延迟
//		  Timer timer = new Timer(); timer.schedule(new TimerTask() 
//		  { int i = 0; public
//		 void run() { if(i > 15) System.exit(0); 
//		 System.out.println("time:" + i +
//		 " seconds"); i++;} 
//		 }, 500 , 1000);

	}

	public void timedAcquire() {
		try {
			while (true) {
				// 1）lock(), 拿不到lock就不罢休，不然线程就一直block。 比较无赖的做法。
				// 2）tryLock()，马上返回，拿到lock就返回true，不然返回false。 比较潇洒的做法。
				// 带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false。比较聪明的做法。
				// 前者是悲观锁，后者是乐观锁
				boolean rst = rLock.tryLock(5, TimeUnit.SECONDS);
				System.out.println("timedAcquire:" + rst);

				// 成功结束，否则继续
				if (rst)
					break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			rLock.unlock();
			System.out.println("timedAcquire:leave");
		}
	}

	public void untimedAcquire() {
		try {
			boolean rst = rLock.tryLock();
			System.out.println("untimedAcquire:" + rst);
			Thread.sleep(6500);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			rLock.unlock();
			System.out.println("untimedAcquire:unlock");
		}
	}

	private void reentrant() {
		// ReentrantLock重入锁实验
		try {
			while (rLock.getHoldCount() < 20) {
				rLock.lock();
				usedNums++;
				System.out.println("reentrantLock used " + rLock.getHoldCount() + " times");
			}
			// 成功结束，否则继续
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			rLock.unlock();
		}
	}

	private void synReentrant() {
		// synchronized重入锁实验,
		while (usedNums < 20) {
			synchronized (this) {
				usedNums++;
				System.out.println("synchronized lock used " + usedNums + " times");
			}
		}
		usedNums = 0;
	}

	private void unReentrant() {
		// ReentrantLock重入锁实验
		try {
			int i = 0;
			while (i++ < 10) {
				if (!rLock.isLocked()) {
					rLock.lock();
					System.out.println("reentrantLock lock used " + rLock.getHoldCount() + " times");
				}
			}

			// 成功结束，否则继续
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}

	public static void main(String[] args) {
		ReentrantLockTest rLockTest = new ReentrantLockTest();
		// try超时实验
		// new Thread(() -> rLockTest.untimedAcquire()).start();
		// new Thread(() -> rLockTest.timedAcquire()).start();

		// 重入锁允许线程不止一次地锁定资源
		// ReentrantLock重入锁实验
		// new Thread(() -> rLockTest.reentrant()).start();
		// synchronized重入锁实验
		// new Thread(() -> rLockTest.synReentrant()).start();

		// 不可重入锁，实际上是手动加一个判断synchronized方法一样
		new Thread(() -> rLockTest.unReentrant()).start();
	}
}
