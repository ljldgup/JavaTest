package Concurrency;

public class ThreadLocalTest {

    private ThreadLocal<Integer> localInt;

    public ThreadLocalTest() {
        // TODO Auto-generated constructor stub
        localInt = new ThreadLocal<Integer>();
    }

    public void func1(String name) {
        System.out.println(localInt);
        // TODO Auto-generated method stub
        if (localInt.get() == null) {
            localInt.set((int) (1 + Math.random() * (10 - 1 + 1)));
        }

        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(name + " func3 " + localInt.get());
        }
    }

	/*
	ThreadLocal 通过ThreadLocalMap 保存， ThreadLocalMap key 时ThreadLocal实例
	ThreadLocalMap 每个线程都有一份，ThreadLocal实例只有一个，通过这两个组合实现每个线程都有一份副本
	 */

    public static void main(String[] args) {
        ThreadLocalTest test1 = new ThreadLocalTest();

        //每个本地变量的值各不相同，各自有一份拷贝。
        new Thread(() -> {
            test1.func1("Thread 1");
        }).start();
        new Thread(() -> {
            test1.func1("Thread 2");
        }).start();
        test1.func1("Main Thread");
    }


}
