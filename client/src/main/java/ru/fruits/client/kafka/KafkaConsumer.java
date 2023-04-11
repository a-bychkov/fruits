package ru.fruits.client.kafka;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.fruits.client.config.KafkaConfig;

@Component
@ConditionalOnBean(KafkaConfig.class)
@Log4j2
public class KafkaConsumer {
    @Value("${spring.kafka.topics}")
    private String[] topics;

    @KafkaListener(topics = "#{'${spring.kafka.topics}'.split(',')}", groupId = "group_id")
    public void consume(ConsumerRecord<String, String> payload) {
        String topic = topics[0];

        log.info("Received message from kafka topic: {}", topic);
        log.info("Key: {}", payload.key());
        log.info("Headers: {}", payload.headers());
        log.info("Partition: {}", payload.partition());
        log.info("Message: {}", payload.value());
    }
}
