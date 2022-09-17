package ru.fruits.market.resources.rest;

import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.fruits.market.model.Bucket;
import ru.fruits.market.service.BucketsService;
import ru.fruits.market.service.FruitsService;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
@Slf4j
public class MarketController {
    private final FruitsService fruitsService;
    private final BucketsService bucketsService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${spring.kafka.topics}")
    private String[] topics;

    @GetMapping(value = "/produce")
    public ResponseEntity<?> sendToKafka(@RequestParam("message") String message) {
        Stream.of(topics).forEach(topic -> {
            log.info("Send message to kafka topic {}", topic);
            kafkaTemplate.send(topic, message);
        });
        return ResponseEntity.ok("Ok");
    }

    @PostMapping(value = "/save", produces = "application/json")
    public ResponseEntity<?> saveFruit(@RequestBody Bucket bucket) {
        //save main entity
        Bucket persistedBucket = bucketsService.save(bucket);

        //save attached entities
        bucket.getFruits().forEach(fruitsService::save);

        return ResponseEntity.ok(persistedBucket);
    }
}
