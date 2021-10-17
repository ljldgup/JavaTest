package Concurrency;

import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class RedBlackTree {
	private static class thread implements Runnable,Comparable<thread>{
		int value;
		public thread(int val) {
			// TODO Auto-generated constructor stub
			value = val;
		}
		
		@Override
		public int compareTo(thread o) {
			// TODO Auto-generated method stub
			if(o.value > this.value) return 1;
			if(o.value == this.value) return 0;
			else return -1;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int i = 0;
			try {
				while(i++ < 3) {
					Thread.sleep(500);
					System.out.println(value);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "thread" + value;
		}
	}
	
	public static void main(String[] args) {
		TreeSet<thread> treeThread = new TreeSet<thread>();
        ThreadPoolExecutor priorityPool = new ThreadPoolExecutor(1, 2, 1000, TimeUnit.MILLISECONDS, 
        		new PriorityBlockingQueue<Runnable>(2),Executors.defaultThreadFactory(),
        		new ThreadPoolExecutor.AbortPolicy());
        
        IntStream.range(0, 8).forEach(i -> treeThread.add(new RedBlackTree.thread(i)));
		//貌似红黑树返回的pollLast值得是最小的
		//第一个进程直接运行，后面队列中的按优先级执行
		//拒绝策略和容量无效
		treeThread.parallelStream().forEach(t ->priorityPool.execute(t));
		priorityPool.shutdown();
	}
}
