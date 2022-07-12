package reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class CombineTest {
	public static void main(String[] args) throws InterruptedException {
		Flux<String> flux1 = Flux.just("a", "b");
		Flux<Integer> flux2 = Flux.just(1, 2);

		Flux.concat(flux1, flux2)
				.subscribe(s->System.out.println("flux concat " + s));
		TimeUnit.SECONDS.sleep(1);
		//flatmap能够从嵌套的拿到具体数据，map并不能使嵌套Flux求值
		flux1.map(CombineTest::genFlux).subscribe(s -> System.out.println(s + " mapFlux"));
		flux1.flatMap(CombineTest::genFlux).subscribe(s -> System.out.println(s + " flatmapFlux"));
		TimeUnit.SECONDS.sleep(1);

		//thenMany也能处理嵌套情况
		flux1.doOnNext(s -> System.out.println(s + " on next1"))
				.doOnNext(s -> System.out.println(s + " on next1"))
				.thenMany(flux2).subscribe(s -> System.out.println(s + " then many"));
		TimeUnit.SECONDS.sleep(1);

		//即使filter全部过滤掉也能执行下面，一批数据只执行一次
		flux1.filter(x -> x.length() > 10).doOnNext(System.out::println).then(Mono.just(true))
				.subscribe(s -> System.out.println("filter then"));
		TimeUnit.SECONDS.sleep(1);

		//会等所有delay结束后再执行then
		flux1.filter(x -> x.length() > 10).doOnNext(System.out::println).then(Mono.just(true))
				.subscribe(s -> System.out.println("filter then"));
		TimeUnit.SECONDS.sleep(1);
		flux1.flatMap(
				x -> flux2.delayElements(Duration.ofSeconds(1))
						.doOnNext(s -> System.out.println(s + " after delay")))
				.then(Mono.just(true)).subscribe(s -> System.out.println("delay then"));
		TimeUnit.SECONDS.sleep(3);
	}

	static Flux<String> genFlux(String ignore) {
		return Flux.just("FF");
	}
}
