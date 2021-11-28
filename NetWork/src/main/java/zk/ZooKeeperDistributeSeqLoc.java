package zk;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZooKeeperDistributeSeqLoc implements Lock, Watcher {

	// 超时时间
	private static final int SESSION_TIMEOUT = 5000;
	// zookeeper server列表
	private String hosts;
	private String groupNode = "locks";
	private String subNode = "sub";

	private String lockName;
	private ZooKeeper zk;
	// 当前client创建的子节点
	private String thisPath;
	// 当前client等待的子节点
	private String waitPath;
	private List<Exception> exceptionList = new ArrayList<>();
	private CountDownLatch latch = new CountDownLatch(1);

	public ZooKeeperDistributeSeqLoc(String hosts, String lockName) {
		this.hosts = hosts;
		this.lockName = lockName;
		try {
			// 连接zookeeper
			zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
			Stat stat = zk.exists("/" + groupNode, false);
			if (stat == null) {
				// 如果根节点不存在，则创建根节点
				zk.create("/" +groupNode, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
		} catch (IOException e) {
			System.out.println("Zk连接异常" + e);
			exceptionList.add(e);
		} catch (InterruptedException e) {
			System.out.println("Zk连接异常 " + e);
			exceptionList.add(e);
		} catch (KeeperException e) {
			System.out.println("Zk连接异常" + e);
			exceptionList.add(e);
		}
	}

	@Override
	public void lock() {
		if (exceptionList.size() > 0) {
			throw new LockException(exceptionList.get(0));
		}
		try {
			if (this.tryLock()) {
				System.out.println("------------>线程：{},锁：{}，获得" + Thread.currentThread().getName() + lockName);
				return;
			} else {
				// 等待锁
				this.latch.await();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {

	}

	@Override
	public boolean tryLock() {
		try {
			String splitStr = "_lock_";
			if (lockName.contains(splitStr)) {
				throw new ZooKeeperDistributeSeqLoc.LockException("锁名有误");
			}
			// 创建子节点
			thisPath = zk.create("/" + groupNode + "/" + lockName + subNode + splitStr, null, Ids.OPEN_ACL_UNSAFE,
					CreateMode.EPHEMERAL_SEQUENTIAL);

			// 注意, 没有必要监听"/locks"的子节点的变化情况
			List<String> childrenNodes = zk.getChildren("/" + groupNode, false);

			// 取出所有lockName的锁
			List<String> lockObjects = new ArrayList<String>();
			for (String node : childrenNodes) {
				String _node = node.split(splitStr)[0];
				if (_node.equals(lockName + subNode)) {
					lockObjects.add(node);
				}
			}

			// 列表中只有一个子节点, 那肯定就是thisPath, 说明client获得锁
			if (lockObjects.size() == 1) {
				return true;
			} else {
				String thisNode = thisPath.substring(("/" + groupNode + "/").length());
				// 排序
				Collections.sort(lockObjects);
				int index = lockObjects.indexOf(thisNode);
				if (index == -1) {
					// never happened
				} else if (index == 0) {
					// inddx == 0, 说明thisNode在列表中最小, 获得锁
					return true;
				} else {
					// 获得排名比thisPath前1位的节点
					this.waitPath = "/" + groupNode + "/" + lockObjects.get(index - 1);
					// 在waitPath上注册监听器, 当waitPath被删除时, zookeeper会回调监听器的process方法
					zk.getData(waitPath, true, new Stat());
				}
			}
		} catch (Exception e) {

		}
		return false;

	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return tryLock();
	}

	@Override
	public void unlock() {
		try {
			System.out.println("释放锁 {}" + thisPath);
			zk.delete(thisPath, -1);
			thisPath = null;
			zk.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Condition newCondition() {
		return null;
	}

	//watcher 监视器， 删除锁后唤醒
	@Override
	public void process(WatchedEvent event) {
		try {
			// 发生了waitPath的删除事件
			if (event.getType() == EventType.NodeDeleted && event.getPath().equals(waitPath)) {
				this.latch.countDown();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public class LockException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public LockException(String e) {
			super(e);
		}

		public LockException(Exception e) {
			super(e);
		}
	}
	
	public static void main(String[] args) {
		ExecutorService tPool = Executors.newFixedThreadPool(10);
		ZooKeeperDistributeSeqLoc zdsLock = new ZooKeeperDistributeSeqLoc("127.0.0.1:2181", "Sqe");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

		zdsLock.lock();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Get lock at" + df.format(new Date()));
		zdsLock.unlock();
		tPool.shutdown();
		
        
	}
}
