package reactor;

import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TransferTest {
	public static void main(String[] args) throws InterruptedException {
		Flux.fromStream(Stream.of(1,2,3,4,5)).collectList().subscribe(s-> System.out.println(s.size()));
		Thread.sleep(1000);


		EmitterProcessor<Integer> emitterProcessor =EmitterProcessor.create();
		FluxSink<Integer> sink = emitterProcessor.sink(FluxSink.OverflowStrategy.BUFFER);
		Flux<Integer> flux = emitterProcessor.map(integer -> integer++);
		IntStream.range(0,10).forEach(i->emitterProcessor.onNext(i));

		flux.collectList().subscribe(s-> System.out.println(s.size()));
		Thread.sleep(1000);
	}
}
