package Concurrency;

import java.util.concurrent.CountDownLatch;

public class LatchTest {
	
	public static class Player implements Runnable{

	    private CountDownLatch begin;

	    private CountDownLatch end;

	    Player(CountDownLatch begin,CountDownLatch end){
	        this.begin = begin;
	        this.end = end;
	    }

	    public void run() {
	        
	        try {
	            begin.await();
	            System.out.println(Thread.currentThread().getName() + " arrived !");;
	            end.countDown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	    }
	}
    public static void main(String[] args) {
		//CountDownLatch 值降为1时执行
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(2);

        for(int i=0; i<2; i++){
            Thread thread = new Thread(new Player(begin,end));
            thread.start();
        }

        try{
            System.out.println("the race begin");
            begin.countDown();
            end.await();
            System.out.println("the race end");
        }catch(Exception e){
             e.printStackTrace();
        }

     }
}
