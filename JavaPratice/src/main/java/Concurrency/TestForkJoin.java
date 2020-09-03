package Concurrency;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class TestForkJoin {

	private static class ForkJoinCalculate extends RecursiveTask<Long> {

		private static final long serialVersionUID = 1234567890L;// ���к�

		private long start;
		private long end;
		private static final long THRESHOLD = 2500000000L;// �ٽ�ֵ

		public ForkJoinCalculate(long start, long end) {
			this.start = start;
			this.end = end;
		}

		@Override
		protected Long compute() {
			long length = end - start;
			//length С��һ������ֵ��ֱ�Ӽ��㣬����ݹ�����Լ�
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
		//��������ForkJoin�죬�������Ӻ�ParallelStreamsʱ���������ԣ�ForkJoin������Ҫ���зָ����
		//ParallelStreams����������ForkJoinPoolʵ�ֵ�
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
		System.out.println("����ʱ��" + Duration.between(start, end).toMillis() + "ms");// ����ʱ��3409ms
	}

	private static void test2() {
		Instant start = Instant.now();

		Long sum = LongStream.rangeClosed(0L, 1000000000L).parallel().reduce(0, Long::sum);
		System.out.println(sum);

		Instant end = Instant.now();
		System.out.println("����ʱ��" + Duration.between(start, end).toMillis() + "ms");// ����ʱ��2418ms
	}
}
