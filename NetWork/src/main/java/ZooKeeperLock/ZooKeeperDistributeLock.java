package ZooKeeperLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ZooKeeperDistributeLock {
	
	final static String LOCK_PATH = "/lock";
	
	private ZooKeeperOperation zoo;
	private boolean locked = false;
	
	public ZooKeeperDistributeLock() {
		// TODO Auto-generated constructor stub
		zoo = new ZooKeeperOperation("localhost", 2181);
	}
	
	public void lock(byte[] data) {
		// TODO Auto-generated method stub
		while(true) {
			try {
				zoo.create(LOCK_PATH, data);
				locked = true;
				break;
			} catch (Exception e) {
				// TODO: handle exception
				try {
					Thread.sleep(888);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	public void unlock() {
		// TODO Auto-generated method stub
		try {
			if(locked) zoo.delete(LOCK_PATH);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void main(String[] args) {
		ExecutorService tPool = Executors.newFixedThreadPool(4);
		ZooKeeperDistributeLock zdLock = new ZooKeeperDistributeLock();
		for (int i = 0; i < 10; i++) {
			tPool.execute(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					zdLock.lock(this.toString().getBytes());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(this.toString());
					zdLock.unlock();
				}
			});
		}
		tPool.shutdown();
	}
}
