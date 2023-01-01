package Concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;

public class AtomicTest2 {

	interface Valuer {
		public Number getNumber();
	}

	interface Adder {
		public void addOne();
	}

	private static int number;
	static AtomicInteger atomicInteger = new AtomicInteger();
	static AtomicLong atomicLong = new AtomicLong();
	static LongAdder longAdder = new LongAdder();
	static LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);

	static Map<Object, Adder> adderMap = new HashMap<>();

	static {
		adderMap.put(number, () -> number = number + 1);
		adderMap.put(atomicInteger, () -> atomicInteger.incrementAndGet());
		adderMap.put(atomicLong, () -> atomicLong.incrementAndGet());
		adderMap.put(longAdder, () -> longAdder.increment());
		adderMap.put(longAccumulator, () -> longAccumulator.accumulate(1));
	}

	public static final int SIZE_THREAD = 50;
	public static final int _1w = 10000;

	public static void main(String[] args) throws InterruptedException {
		long startTime;
		for (Object key : adderMap.keySet()) {
			startTime = System.currentTimeMillis();
			CountDownLatch latchSynchronized = new CountDownLatch(SIZE_THREAD);

			for (int i = 1; i <= SIZE_THREAD; i++) {
				new Thread(() -> {
					try {
						for (int j = 1; j <= 100 * _1w; j++) {
							adderMap.get(key).addOne();
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						latchSynchronized.countDown();
					}
				}, String.valueOf(i)).start();
			}
			latchSynchronized.await();
			long endTime = System.currentTimeMillis();
			System.out.println("花费时间：" + (endTime - startTime));
			//这些类型toString都直接是数值
			System.out.println("值" + key + "," + key.getClass());
		}
	}
}
