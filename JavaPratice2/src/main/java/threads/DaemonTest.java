package threads;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DaemonTest {
    private Random random = new Random();

    private void daemonFunction(Thread thread) {
        while (true) {
            int num = random.nextInt();
            if (num % 111111 == 0) {
                thread.interrupt();
                break;
            }
        }
    }

    public static void main(String[] args) {
        DaemonTest test = new DaemonTest();
        Thread thread = Thread.currentThread();
        //这种写法传入的是自己的线程id
//        new Thread(() -> test.daemonFunction(Thread.currentThread()), "线程1").start();
        new Thread(() -> test.daemonFunction(thread), "线程1").start();
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
                int num = test.random.nextInt();
                System.out.println(num);
            } catch (InterruptedException e) {
                System.out.println("作业终止");
                break;
            }
        }
    }
}
