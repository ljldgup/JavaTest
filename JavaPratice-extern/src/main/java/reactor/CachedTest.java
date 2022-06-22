package reactor;

import reactor.core.publisher.Flux;

import java.util.stream.Stream;

public class CachedTest {
    public static void main(String[] args) {
        //这里缓存对stream有效，对于Flux.just之类无效,因为Flux.just的数据是静态数据始终都存在
        Flux<String> nonCache = Flux.fromStream(Stream.of("data0", "data1", "data2"));

        //cache() 是.replay(max).autoConnect(1)的别名，只要第一个订阅者进来，它就会执行connect()。
        Flux<String> cache0 = Flux.fromStream(Stream.of("data0", "data1", "data2")).cache();
        //replay() 会将整个历史重播给第二个订阅者，尽管它已经晚了
        Flux<String> cache1 = Flux.fromStream(Stream.of("data0", "data1", "data2")).replay(2).autoConnect();
        Flux.range(0, 5).subscribe(i -> {
            //第二轮报错
            nonCache.subscribe(s -> {
                System.out.println(i + "  " + s);
            });
        });

//        Flux.range(0, 5).subscribe(i -> {
//            //可以重复读取，多个订阅这都能正常工作
//            cache0.subscribe(s -> {
//                System.out.println(i + "  " + s);
//            });
//        });

//        Flux.range(0, 5).subscribe(i -> {
//            //第二轮开始data0没了
//            cache1.subscribe(s -> {
//                System.out.println(i + "  " + s);
//            });
//        });
    }
}
