package mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class MqService {

    @Autowired
    KafkaService kafkaService;

    @Scheduled(cron = "0/10 * * * * * ")
    public void pubMessageKafka() {
        kafkaService.send().subscribe();
    }
}
