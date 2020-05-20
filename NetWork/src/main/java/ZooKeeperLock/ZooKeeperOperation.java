package ZooKeeperLock;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZooKeeperOperation {

	// create static instance for zookeeper class.
	private static ZooKeeper zk;

	// ±ÕËøÓÃÓÚµÈ´ý¼à¿Ø
	final CountDownLatch connectedSignal = new CountDownLatch(1);

	public ZooKeeperOperation(String host, int port) {
		// TODO Auto-generated constructor stub
		try {
			zk = connect(host, port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ZooKeeper connect(String host, int port) throws IOException {

		ZooKeeper zoo = null;
		zoo = new ZooKeeper(host, port, new Watcher() {
			public void process(WatchedEvent we) {
				if (we.getState() == KeeperState.SyncConnected) {
					connectedSignal.countDown();
				}
			}
		});
		
		return zoo;
	}

	public Stat znode_exists(String path) throws KeeperException, InterruptedException {
		return zk.exists(path, true);
	}
	
	public String create(String path, byte[] data) throws KeeperException, InterruptedException {
		return zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}


	
	public Stat update(String path, byte[] data) throws KeeperException, InterruptedException {
		
		
		Stat st = znode_exists(path);
		if(st != null) {
			return zk.setData(path, data, znode_exists(path).getVersion());
		}
		
		return null;
	}
	

	public void delete(String path) throws KeeperException, InterruptedException {
		Stat st = znode_exists(path);
		if(st != null) {
			zk.delete(path, st.getVersion());
		}
		
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		zk.close();
		super.finalize();
	}
	
	public static void main(String[] args) {
		ZooKeeperOperation zo = new ZooKeeperOperation("localhost", 1281);
		try {
			zo.delete("/java");
			zo.create("/java", null);
			zo.update("/java", "updated".getBytes());
			System.out.println(zo.znode_exists("/java").toString());
			zo.delete("/java");
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

}
