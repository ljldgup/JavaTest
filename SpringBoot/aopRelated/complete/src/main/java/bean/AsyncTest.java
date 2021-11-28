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
import java.util.concurrent.atomic.AtomicInteger;

@Component("debugAsyncTest")
public class AsyncTest {
	@Autowired
	ApplicationContext applicationContext;

	AtomicInteger atomicInteger = new AtomicInteger(1);

	@Async
	public void asyncTest1() throws InterruptedException {
		TimeUnit.SECONDS.sleep(10);
		System.out.println("async with no return "+ atomicInteger.getAndAdd(1));
	}

	//直接报错
	@Async
	public Future<Integer> asyncTest2() throws InterruptedException {
		TimeUnit.SECONDS.sleep(10);
		return new AsyncResult<>(atomicInteger.getAndAdd(1));
	}

	@Scheduled(cron = "*/1 * * * * *")
	public void test() throws InterruptedException, ExecutionException {
		//被代理的类内调用
		AsyncTest asyncTest = (AsyncTest) applicationContext.getBean(this.getClass());
		System.out.println("call asyncTest1");
		asyncTest.asyncTest1();
		System.out.println("call asyncTest2");
		Future<Integer> asyncTest2 = asyncTest.asyncTest2();
		System.out.println("async with return " + asyncTest2.get());
	}
}
