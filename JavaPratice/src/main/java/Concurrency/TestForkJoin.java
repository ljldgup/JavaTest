package Concurrency;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class TestForkJoin {

	private static class ForkJoinCalculate extends RecursiveTask<Long> {

		private static final long serialVersionUID = 1234567890L;// 序列号

		private long start;
		private long end;
		private static final long THRESHOLD = 2500000000L;// 临界值

		public ForkJoinCalculate(long start, long end) {
			this.start = start;
			this.end = end;
		}

		@Override
		protected Long compute() {
			long length = end - start;
			//length 小于一定的阈值就直接计算，否则递归调用自己
			if (length <= THRESHOLD) {
				long sum = 0;
				for (long i = start; i <= end; i++) {
					sum += i;
				}
				return sum;
			} else {
				long middle = (start + end) / 2;
				ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
				left.fork();

				ForkJoinCalculate right = new ForkJoinCalculate(middle + 1, end);
				right.fork();

				return left.join() + right.join();
			}
		}

	}

	public static void main(String[] xx) {
		//数量较少ForkJoin快，数量增加后ParallelStreams时间优势明显，ForkJoin本身需要进行分割操作
		//ParallelStreams本质上是用ForkJoinPool实现的
		test1();
		test2();
	}

	private static void test1() {
		Instant start = Instant.now();
		ForkJoinPool pool = new ForkJoinPool(50);
		ForkJoinTask<Long> task = new ForkJoinCalculate(0L, 1000000000L);
		long sum = pool.invoke(task);
		System.out.println(sum);

		Instant end = Instant.now();
		System.out.println("消耗时间" + Duration.between(start, end).toMillis() + "ms");// 消耗时间3409ms
	}

	private static void test2() {
		Instant start = Instant.now();

		Long sum = LongStream.rangeClosed(0L, 1000000000L).parallel().reduce(0, Long::sum);
		System.out.println(sum);

		Instant end = Instant.now();
		System.out.println("消耗时间" + Duration.between(start, end).toMillis() + "ms");// 消耗时间2418ms
	}
}
