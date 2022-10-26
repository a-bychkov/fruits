package ru.fruits.client.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    @Value("${spring.kafka.topics}")
    private String[] topics;

    @KafkaListener(topics = "#{'${spring.kafka.topics}'.split(',')}", groupId = "group_id")
    public void consume(ConsumerRecord<String, String> payload){
        String topic = topics[0];

        log.info("Received message from kafka topic: {}", topic);
        log.info("Key: {}", payload.key());
        log.info("Headers: {}", payload.headers());
        log.info("Partition: {}", payload.partition());
        log.info("Message: {}", payload.value());
    }
}
