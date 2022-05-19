package Concurrency;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicTest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        Task task = new Task();

        //数据量小不明显,要设大一些
        for(int i = 0;i<10000;i++){
            pool.submit(()->task.setAmount(task.getAmount()+1));
        }
        pool.awaitTermination(1, TimeUnit.SECONDS);
        task.print();

        task.reset();
        task.print();
        for(int i = 0;i<10000;i++){
            //++不是线程安全的
            pool.submit(task::inc);
        }
        pool.awaitTermination(1, TimeUnit.SECONDS);
        task.print();

        task.printAtomic();
        for(int i = 0;i<10000;i++){
            //++不是线程安全的
            pool.submit(task::incAtomic);
        }
        pool.awaitTermination(1, TimeUnit.SECONDS);
        task.printAtomic();
    }

    public static class Task {

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public void inc(){
            //++不是线程安全的
            this.amount++;
        }

        public void incAtomic(){
            //++不是线程安全的
            int amount = atomicAmount.get();
            atomicAmount.compareAndSet(amount, ++amount);
        }

        public void reset() {
            this.amount = 0;
        }

        public void print(){
            System.out.println(amount);
        }

        public void printAtomic(){
            System.out.println(atomicAmount.get());
        }

        int amount = 0;
        AtomicReference<Integer> atomicAmount = new AtomicReference(0);
    }
}
