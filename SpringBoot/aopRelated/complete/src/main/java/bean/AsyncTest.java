package bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

@Component("debugAsyncTest")
public class AsyncTest {
	@Autowired
	ApplicationContext applicationContext;

	AtomicInteger atomicInteger = new AtomicInteger(1);

	@Async
	public void asyncTest1() throws InterruptedException {
		TimeUnit.SECONDS.sleep(10);
		System.out.println("async with no return " + atomicInteger.getAndAdd(1));
	}

	//直接报错
	@Async
	public Future<Integer> asyncTest2() throws InterruptedException {
		TimeUnit.SECONDS.sleep(10);
		return new AsyncResult<>(atomicInteger.getAndAdd(1));
	}

//	@Scheduled(cron = "*/1 * * * * *")
	public void test() throws InterruptedException, ExecutionException {
		//抛出异常后仍然能够继续执行,线程池会自动创建新线程
		if (asyncTest2().get() % 2 == 0) throw new RuntimeException();

		//被代理的类内调用
		AsyncTest asyncTest = (AsyncTest) applicationContext.getBean(this.getClass());
		System.out.println("call asyncTest1");
		asyncTest.asyncTest1();
		System.out.println("call asyncTest2");
		Future<Integer> asyncTest2 = asyncTest.asyncTest2();
		try {
			//如果线程池使用Discardpolicy 拒绝策略，使用带超时时间的get方法
			System.out.println("async with return " + asyncTest2.get(1,TimeUnit.SECONDS));
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
}
