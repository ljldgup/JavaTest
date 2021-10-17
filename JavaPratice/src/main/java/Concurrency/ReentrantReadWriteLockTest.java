package Concurrency;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.StreamSupport;

public class ReentrantReadWriteLockTest {
    volatile int t;
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    Random random = new Random();

    public void test() {
        //readLock可以锁很多次，是共享的，而writeLock只能锁一次
        // read锁被获得时，无法write避免脏读，程序所有的readlock都被运行完之后，writelock才能获的锁
        try {
            rwLock.readLock().lock();
            System.out.println("readLock: " + String.valueOf(Thread.currentThread().getId()) + ":" + t);
            rwLock.readLock().unlock();

            //TimeUnit.SECONDS.sleep(random.nextInt() % 3 + 6);

            rwLock.writeLock().lock();
            t += 100;
            System.out.println("writeLock: " + String.valueOf(Thread.currentThread().getId()) + ":" + t);
            TimeUnit.SECONDS.sleep(random.nextInt() % 1 + 2);
            rwLock.writeLock().unlock();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ReentrantReadWriteLockTest rwLockTest = new ReentrantReadWriteLockTest();
        for (int i = 0; i < 6; i++) {
            new Thread(() -> rwLockTest.test()).start();
        }
    }

}
