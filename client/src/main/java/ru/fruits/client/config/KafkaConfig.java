package ru.fruits.client.config;

import lombok.Getter;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@ConditionalOnProperty(prefix = "spring", name = "kafka")
@Getter
public class KafkaConfig {
    @Value("${spring.kafka.topics}")
    public String[] topics;

    // create service kafka topic
    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(topics[0])
                .partitions(1)
                .replicas(1)
                .build();
    }
}
