package reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

public class BasicTest {
    public static void main(String[] args) throws InterruptedException {
        Flux.create(sink -> {
            //向下游发布元素
            sink.next("helloword");
            sink.next("helloword2");
            //结束发布元素
            sink.complete();
        })
                .doOnNext(System.out::println)
                .subscribe(System.out::println);

        Flux.generate(t -> {
            t.next("generate");
            //注意generate中next只能调用1次
            t.complete();
        }).subscribe(System.out::println);

        //单个元素
        Flux.just("just").subscribe(System.out::println);
        //多个元素
        Flux.just("just", "just1", "just2").subscribe(System.out::println);


        //Flux->Flux
        Flux.from(Flux.just("fromjust", "fromjust1", "fromjust2")).subscribe(System.out::println);
        //Mono->Mono
        Flux.from(Mono.just("fromjust")).subscribe(System.out::println);


        Mono.fromRunnable(() -> System.out.println("mono from runnable")).subscribe();
        Mono.empty().switchIfEmpty(Mono.just(1)).subscribe(s -> {
            System.out.println("switchIfEmpty " + s);
        });


        //zip
        Mono.zip(Mono.just(1), Mono.just(1)).subscribe(entity -> entity.forEach(System.out::println));
        Mono<List<Tuple3<String, String, String>>> result = Flux.zip(Flux.just("fromjust"), Flux.just("fromjust1"), Flux.just("fromjust2")).collectList();
        result.subscribe(System.out::println);
        List<Tuple3<String, String, String>> results = result.block();

        //消费是异步执行的，不sleep主进程直接结束了。。。。
        Flux.fromStream(Stream.generate(Math::random)).delayElements(Duration.ofSeconds(1)).subscribe(System.out::println);

        Thread.sleep(5000);
    }
}
