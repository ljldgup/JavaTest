package reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

@Slf4j
public class TestCase {

    public static void main(String[] args) {
        Flux<Integer> integerFlux = Flux.range(1,5);
        integerFlux.subscribe(x ->System.out.print("->"+x));
        StepVerifier.create(integerFlux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3,4,5)
                .expectComplete()
                .verify();

        StepVerifier.create(Flux.just(1, 2, 3)
                .replay()
                .refCount(1, Duration.ofSeconds(1)))
                .expectFusion()
                .expectNext(1, 2, 3)
                .verifyComplete();
    }
}