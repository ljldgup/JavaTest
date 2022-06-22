package reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicInteger;

public class RetryTest {
    public static void main(String[] args) {
        //输出onComplete
        Mono.empty()
                .doOnEach(signal -> System.out.println(signal.getType()))
                .defaultIfEmpty("DEFAULT")
                .doOnNext(System.out::println)
                .subscribe();
        //Mono.never永远不会分发数据
        Mono.never()
                .doOnEach(signal -> System.out.println(signal.getType()))
                .defaultIfEmpty("DEFAULT")
                .doOnNext(System.out::println)
                .subscribe();
        Flux.range(1, 10).retry(2).subscribe(System.out::println);

        AtomicInteger i = new AtomicInteger();
        StepVerifier.create(Flux.just("test", "test2", "test3")
                .doOnNext(d -> {
                    if (i.getAndIncrement() < 2) {
                        throw new RuntimeException("test");
                    }
                })
                .retry(e -> i.get() <= 2)
                .count())
                .expectNext(3L)
                .expectComplete()
                .verify();
        System.out.println(i.get());
    }


}
