import hello.GreetingClient;
import mq.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Mono;

@ComponentScan(value = "config,hello,websocket,mq")
@EnableScheduling
@EnableAsync
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application {
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(Application.class, args);

        // We need to block for the content here or the JVM might exit before the message is logged

    }

    @Autowired
    KafkaService kafkaService;

//    @Scheduled(cron = "0/30 * * * * * ")
    public void pubMessageKafka() {
        Mono.just(context.getBean(GreetingClient.class)).subscribe(greetingClient ->
                System.out.println(">> message = " + greetingClient.getMessage().block()));
    }
}
