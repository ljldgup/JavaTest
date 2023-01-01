package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Component
public class GreetingHandler {

	Random random = new Random();

	@Autowired
	ReactiveRedisTemplate reactiveRedisTemplate;

	public Mono<ServerResponse> hello(ServerRequest request) {
		return
				ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromValue(new Greeting("Hello, Spring!")));
	}

	public void setSink(FluxSink sink) {
		this.sink = sink;
	}

	FluxSink sink;
	List<Object> elements = new ArrayList<>();
	volatile List<Object> result = new ArrayList<>();

	@PostConstruct
	public void init() {


		IntStream.range(0, 2500).forEach(i -> elements.add(i));
		publishWithSinkScheduledInsert();
//		insertWithSink();
//		insertDirectly(elements);
	}

	private void publishWithSinkScheduledInsert() {

		Flux.create(this::setSink).doOnNext(ignore -> result.add(getData())).subscribeOn(Schedulers.parallel()).subscribe();

		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
		executorService.scheduleAtFixedRate(() -> {
			IntStream.range(0, 2500).forEach(i -> sink.next(i));
			printFreeMemory();
		}, 1, 1, TimeUnit.SECONDS);

		Flux.interval(Duration.ofSeconds(1)).doOnNext(s -> {
			List<Object> oldResult = this.result;
			this.result = new ArrayList<>();
			Flux.fromIterable(oldResult).flatMap(d -> reactiveRedisTemplate.opsForHash().put(getRandomIntString(200),
					getRandomIntString(200), d)).subscribeOn(Schedulers.parallel()).subscribe();
		}).subscribeOn(Schedulers.parallel()).subscribe();
	}

	private void insertWithSink() {
		Flux.create(this::setSink).flatMap(ignore -> reactiveRedisTemplate.opsForHash().put(getRandomIntString(200),
				getRandomIntString(200), getData())).subscribeOn(Schedulers.parallel()).subscribe();

		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
		executorService.scheduleAtFixedRate(() -> {
			IntStream.range(0, 2500).forEach(i -> sink.next(i));
			printFreeMemory();
		}, 1, 1, TimeUnit.SECONDS);
	}

	private void insertDirectly(List<Object> elements) {
		Flux.interval(Duration.ofMillis(1000))
				.doOnNext(ignore -> {
					elements.clear();
//					IntStream.range(0, 2500).forEach(i -> elements.add(i));
					printFreeMemory();
				})
				.flatMap(ignore ->
						Flux.fromIterable(elements)
//								.map(s -> {
//									//在redis操作后阻塞都会导致后来的命令等待超时，最终使得tps远小于实际承载
//									//在redis前方，则回到导致实际数据就是按照睡眠时间来的，因为只有一个线程
//									try {
//										TimeUnit.SECONDS.sleep(1);
//									} catch (InterruptedException e) {
//										throw new RuntimeException(e);
//									}
//									return s;
//								})
								.flatMap(ignore1 -> reactiveRedisTemplate.opsForHash().put(getRandomIntString(200),
										getRandomIntString(200), getData()))).collectList()

				.subscribeOn(Schedulers.parallel()).subscribe();
	}

	private static void printFreeMemory() {
		System.out.println(Runtime.getRuntime().freeMemory() / 1024. / 1024.);
	}

	private String getRandomIntString(Integer bound) {
		return String.valueOf(random.nextInt(bound));
	}

	public Map<String, Object> getData() {
		HashMap<String, Object> result = new HashMap<>();
		IntStream.range(0, 5).forEach(i -> {
			HashMap<String, Object> data1 = new HashMap<>();
			data1.put("time" + i, System.currentTimeMillis());
			data1.put("value", getRandomIntString(2000));
			data1.put("property", getRandomIntString(2000));
			data1.put("time", System.currentTimeMillis());
			result.put("data" + i, data1);
		});


		return result;
	}

}
