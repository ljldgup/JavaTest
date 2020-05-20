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

//		  //ʹ��ScheduledThreadPoolExecutor ����timer 
		//500�ǳ�ʼ�ӳ٣�1000ÿ���ӳ�
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
				// 1��lock(), �ò���lock�Ͳ����ݣ���Ȼ�߳̾�һֱblock�� �Ƚ�������������
				// 2��tryLock()�����Ϸ��أ��õ�lock�ͷ���true����Ȼ����false�� �Ƚ�������������
				// ��ʱ�����Ƶ�tryLock()���ò���lock���͵�һ��ʱ�䣬��ʱ����false���Ƚϴ�����������
				// ǰ���Ǳ��������������ֹ���
				boolean rst = rLock.tryLock(5, TimeUnit.SECONDS);
				System.out.println("timedAcquire:" + rst);

				// �ɹ��������������
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
		// ReentrantLock������ʵ��
		try {
			while (rLock.getHoldCount() < 20) {
				rLock.lock();
				usedNums++;
				System.out.println("reentrantLock used " + rLock.getHoldCount() + " times");
			}
			// �ɹ��������������
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			rLock.unlock();
		}
	}

	private void synReentrant() {
		// synchronized������ʵ��,
		while (usedNums < 20) {
			synchronized (this) {
				usedNums++;
				System.out.println("synchronized lock used " + usedNums + " times");
			}
		}
		usedNums = 0;
	}

	private void unReentrant() {
		// ReentrantLock������ʵ��
		try {
			int i = 0;
			while (i++ < 10) {
				if (!rLock.isLocked()) {
					rLock.lock();
					System.out.println("reentrantLock lock used " + rLock.getHoldCount() + " times");
				}
			}

			// �ɹ��������������
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}

	public static void main(String[] args) {
		ReentrantLockTest rLockTest = new ReentrantLockTest();
		// try��ʱʵ��
		// new Thread(() -> rLockTest.untimedAcquire()).start();
		// new Thread(() -> rLockTest.timedAcquire()).start();

		// �����������̲߳�ֹһ�ε�������Դ
		// ReentrantLock������ʵ��
		// new Thread(() -> rLockTest.reentrant()).start();
		// synchronized������ʵ��
		// new Thread(() -> rLockTest.synReentrant()).start();

		// ������������ʵ�������ֶ���һ���ж�synchronized����һ��
		new Thread(() -> rLockTest.unReentrant()).start();
	}
}
