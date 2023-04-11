package ru.fruits.client;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.fruits.client.repository.IndexingService;

@SpringBootApplication
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    public ApplicationRunner buildIndex(IndexingService indexer) throws Exception {
        return (ApplicationArguments args) -> {
            indexer.initiateIndexing();
        };
    }
}
