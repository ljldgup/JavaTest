package reactor;

import reactor.core.publisher.Flux;

import java.util.Random;

public class DoOnTest {
    public static void main(String[] args) {

        Flux.range(1, 10).doOnSubscribe(s -> {
            //注意这里是在订阅的时候执行，不是数据过来的时候运行的
            System.out.println("doOnSubscribe " + s);
        }).doOnEach(signal -> {
            System.out.println("doOnEach " + signal.getType());
        }).doOnNext(s -> {
            //这里的s不是数字，而是FluxReply
            System.out.println("doOnSubscribe s " + s);
            //cancel以后不会被消费
            //s.cancel();
            //没有触发成功，直接调用可以取消，但不触发doOnCancel
            int num = (new Random()).nextInt() % 4;
            //在这里抛错无法成功
            if (num == 0) throw new RuntimeException("random error");

//            s.request(1);
            System.out.println("doOnNext s " + s);
        }).doOnError(error -> {
            //一旦抛出错误就终止
            System.out.println("doOnError s " + error);
        }).doOnCancel(() -> {
            System.out.println("doOnCancel ");
        }).doOnComplete(() -> {
            System.out.println("doOnComplete");
        }).doFinally(signalType -> {
            //这里传的是SignalType类型，告知是如何结束的
            System.out.println("doFinally signalType " + signalType);
        }).subscribe(System.out::println);
    }
}
