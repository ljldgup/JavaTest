package mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class KafkaService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private KafkaSender<String, String> kafkaSender;
    private KafkaReceiver<String, String> kafkaReceiver;

    @PostConstruct
    public void init() {
        //sender
        final Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        final SenderOptions<String, String> producerOptions = SenderOptions.create(producerProps);
        this.kafkaSender = KafkaSender.create(producerOptions);

        //receiver
        final Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.CLIENT_ID_CONFIG, "payment-validator-1");
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-validator");
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        ReceiverOptions<String, String> consumerOptions = ReceiverOptions.<String, String>create(consumerProps)
                .subscription(Collections.singleton("demo"))
                .addAssignListener(partitions -> System.out.println("onPartitionsAssigned " + partitions))
                .addRevokeListener(partitions -> System.out.println("onPartitionsRevoked " + partitions));
        KafkaReceiver<String, String> kafkaReceiver = KafkaReceiver.create(consumerOptions);
        kafkaReceiver.receive().doOnNext(r -> {
            System.out.println("receive " + r.value());
            r.receiverOffset().acknowledge();
        }).subscribe();
    }


    public Mono<?> send() {
        SenderRecord<String, String, Object> senderRecord = SenderRecord.create(new ProducerRecord<>("demo", value()), 1);
        return kafkaSender.send(Mono.just(senderRecord)).next();
    }

    private String value() {
        Map<String, String> map = new HashMap<>();
        map.put("name", UUID.randomUUID().toString());
        try {
            return OBJECT_MAPPER.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }


}
