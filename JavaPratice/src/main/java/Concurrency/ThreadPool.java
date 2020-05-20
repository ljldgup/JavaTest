package Concurrency;

import java.util.concurrent.*;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;

public class ThreadPool {
	public static void threadOutPut(String info, Integer num){
		int i = 0;
		try {
			while(i++ < 2) {
				System.out.println(info + "," + num + ",ThreadId:" + Thread.currentThread().getId());
				Thread.sleep(1000);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	private void futureTest() {
		// TODO Auto-generated method stub
		//Callable allows to return and throw exception
		//Future can get the return and exception with the thread
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		Future<Object> future1 = executorService.submit(() -> "return from call");
		
		Future<Object> future2 = executorService.submit(new Callable<Object>() {
	        @Override
	        public Object call() throws Exception {
	            throw new RuntimeException("exception in call~");// 该异常会在调用Future.get()时传递给调用者
	        }
	    });
		    
		try {
			System.out.println(future1.get());
			future2.get();
			//Object result1 = future1.get();
		} catch (InterruptedException e) {
		  // interrupt
		} catch (ExecutionException e) {
		  // exception in Callable.call()
		  e.printStackTrace();
		}
	
	}
	
	private void executorsTest() {
		// TODO Auto-generated method stub
		//newSingleThreadExecutor 使用的是同一个thread id
		//newFixedThreadPool 使用的也是同一个范围内的
		//线程池应该是等原来的线程结束后，用同样的id建立了新的进程，没有浪费资源
		int i = 0;
		ExecutorService singleES = Executors.newSingleThreadExecutor();
		while(i++ < 5) {
			Integer t = new Integer(i);
			singleES.execute(()->threadOutPut("newSingleThreadExecutor", t));
		}
		//shutdown() is sending a order, showdown immediately use showdownNow()
		singleES.shutdown();
		
		i = 0;
		ExecutorService fixedES = Executors.newFixedThreadPool(3);
		while(i++ < 5) {
			Integer t = new Integer(i);
			fixedES.execute(()->threadOutPut("newFixedThreadPool", t));
		}
		fixedES.shutdown();
		
		i = 0;
		ExecutorService cachedES = Executors.newCachedThreadPool();
		while(i++ < 5) {
			Integer t = new Integer(i);
			cachedES.execute(()->threadOutPut("newCachedThreadPool", t));
		}
		cachedES.shutdown();
	}
	
	private void policyAndBlockQueueTest() {
		// TODO Auto-generated method stub
		int i = 0;

		//DiscardOledestPolicy策略：该策略会丢弃任务队列中最老的一个任务，也就是当前任务队列中最先被添加进去的，马上要被执行的那个任务，并尝试再次提交
		//ArrayBlockingQueue有界任务队列,创建的线程数量达到corePoolSize时，则会将新的任务加入到等待队列中。若等待队列已满，即超过ArrayBlockingQueue初始化的容量
		//继续创建线程，直到线程数量达到maximumPoolSize设置的最大线程数量，若大于maximumPoolSize，则执行拒绝策略。
		ExecutorService discardPolicyES = new ThreadPoolExecutor(2, 3, 
                0, TimeUnit.SECONDS, 
                new ArrayBlockingQueue<>(3), // 使用有界队列，避免OOM
                new ThreadPoolExecutor.DiscardPolicy());
		
		i = 0;
		//only remain 6 (3 + 3)
		while(i++ < 15) {
			Integer t = new Integer(i);
			discardPolicyES.execute(()->threadOutPut("discardPolicy", t));
		}
		discardPolicyES.shutdown();
		
		//无界队列LinkedBlockingQueue 下 maximumPoolSize（2）不再有效，拒绝策略也无效
		ExecutorService discardOldestPolicyES = new ThreadPoolExecutor(2, 3, 
                0, TimeUnit.SECONDS, 
                new LinkedBlockingQueue<Runnable>(), 
                new DiscardOldestPolicy());
		
		i = 0;
		while(i++ < 15) {
			Integer t = new Integer(i);
			discardOldestPolicyES.execute(()->threadOutPut("discardOldestPolicy", t));
		}
		discardOldestPolicyES.shutdown();
		
		
		//AbortPolicy策略：该策略会直接抛出异常，阻止系统正常工作；
		//直接提交队列：SynchronousQueue是一个特殊的BlockingQueue，它没有容量
		//执行一个插入操作就会阻塞，需要再执行一个删除操作才会被唤醒，反之每一个删除操作也都要等待对应的插入操作。
		ExecutorService abortPolicyES = new ThreadPoolExecutor(1, 2, 
                0, TimeUnit.SECONDS, 
                new SynchronousQueue<Runnable>(), 
                new ThreadPoolExecutor.AbortPolicy());
		
		//catch exception here
		i = 0;
		while(i++ < 15) {
			Integer t = new Integer(i);
			abortPolicyES.execute(()->threadOutPut("abortPolicy", t));
		}
		abortPolicyES.shutdown();
		
	}
	
	public static void main(String[] args) {
		ThreadPool test = new ThreadPool();
		//test.executorsTest();
		//test.futureTest();
		test.policyAndBlockQueueTest();
	}
}
