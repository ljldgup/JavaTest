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
	            throw new RuntimeException("exception in call~");// ���쳣���ڵ���Future.get()ʱ���ݸ�������
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
		//newSingleThreadExecutor ʹ�õ���ͬһ��thread id
		//newFixedThreadPool ʹ�õ�Ҳ��ͬһ����Χ�ڵ�
		//�̳߳�Ӧ���ǵ�ԭ�����߳̽�������ͬ����id�������µĽ��̣�û���˷���Դ
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

		//DiscardOledestPolicy���ԣ��ò��Իᶪ��������������ϵ�һ������Ҳ���ǵ�ǰ������������ȱ���ӽ�ȥ�ģ�����Ҫ��ִ�е��Ǹ����񣬲������ٴ��ύ
		//ArrayBlockingQueue�н��������,�������߳������ﵽcorePoolSizeʱ����Ὣ�µ�������뵽�ȴ������С����ȴ�����������������ArrayBlockingQueue��ʼ��������
		//���������̣߳�ֱ���߳������ﵽmaximumPoolSize���õ�����߳�������������maximumPoolSize����ִ�оܾ����ԡ�
		ExecutorService discardPolicyES = new ThreadPoolExecutor(2, 3, 
                0, TimeUnit.SECONDS, 
                new ArrayBlockingQueue<>(3), // ʹ���н���У�����OOM
                new ThreadPoolExecutor.DiscardPolicy());
		
		i = 0;
		//only remain 6 (3 + 3)
		while(i++ < 15) {
			Integer t = new Integer(i);
			discardPolicyES.execute(()->threadOutPut("discardPolicy", t));
		}
		discardPolicyES.shutdown();
		
		//�޽����LinkedBlockingQueue �� maximumPoolSize��2��������Ч���ܾ�����Ҳ��Ч
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
		
		
		//AbortPolicy���ԣ��ò��Ի�ֱ���׳��쳣����ֹϵͳ����������
		//ֱ���ύ���У�SynchronousQueue��һ�������BlockingQueue����û������
		//ִ��һ����������ͻ���������Ҫ��ִ��һ��ɾ�������Żᱻ���ѣ���֮ÿһ��ɾ������Ҳ��Ҫ�ȴ���Ӧ�Ĳ��������
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
