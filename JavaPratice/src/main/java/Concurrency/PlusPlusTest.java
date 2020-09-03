package Concurrency;

public class PlusPlusTest {
    static int num = 0;

    public void plusplus(int count) {
        for (int i = 0; i < count; i++) {
            num++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PlusPlusTest test = new PlusPlusTest();
        // ++并不是原子操作，不线程安全，
        // 其操作过程是取出值，修改，放回， 两个线程同时执行++, 有可能只+了一次

        //第一次执行次数少，所以没有冲突，第二次就很明显
        for (int i = 0; i < 2; i++) new Thread(() -> test.plusplus(1000)).start();
        Thread.sleep(1000);
        System.out.println(PlusPlusTest.num);
        for (int i = 0; i < 2; i++) new Thread(() -> test.plusplus(10000)).start();
        Thread.sleep(1000);
        System.out.println(PlusPlusTest.num);

        /*
        1) ++i 原理,等价于

　　　　i = i+1;
　　　　return i;
        2) i++ 原理,用代码分析表示如下：
　　　　int temp = i;
　　　　i = i + 1;
　　　　return temp;

        c = c++;
        这里c在自增之后返回了之前的数值，所以c被重置了
         */
        int c = 0;
        for (int i = 0; i < 10; i++) {
            c = c++;
        }
        System.out.println(c);
    }
}
