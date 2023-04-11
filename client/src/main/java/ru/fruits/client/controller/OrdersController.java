package ru.fruits.client.controller;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.fruits.client.config.ConfigProperties;
import ru.fruits.client.config.KafkaConfig;
import ru.fruits.client.dto.OrderFilter;
import ru.fruits.client.entity.Order;
import ru.fruits.client.service.OrderService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Log4j2
public class OrdersController {
    private final ConfigProperties properties;
    private final OrderService orderService;
    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Timed("gettingOrders")
    @GetMapping
    public ResponseEntity<?> getOrders() {
        log.info("Getting orders");

        List<Order> orders = orderService.getOrders();

        return ResponseEntity.ok(orders);
    }

    @Timed("gettingOrdersWithFilter")
    @PostMapping("/filter")
    public ResponseEntity<?> getOrdersWithFilter(@RequestBody OrderFilter filter) {
        log.info("Getting orders with filter");

        List<Order> orders = orderService.getOrders(filter);

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/fts/{name}")
    public ResponseEntity<?> getOrderFullText(@PathVariable String name) {
        log.info("Getting orders with full text search by {}", name);

        // todo: replace with pageable
        final int limit = 20;
        final int offset = 0;

        List<Order> orders = orderService.getOrderFullText(name, limit, offset);

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getOrderByName(@PathVariable String name) {
        log.info("Getting order {}", name);

        Order order = orderService.getOrderByName(name);

        return ResponseEntity.ok(order);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestBody Order order) {
        log.info("Saving order");

        Order savedOrder = orderService.saveOrder(order);

        return ResponseEntity.ok(savedOrder);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteOrderByName(@PathVariable String name) {
        log.info("Deleting order {}", name);

        orderService.deleteOrderByName(name);

        return ResponseEntity.ok("Deleted");
    }

    @GetMapping(value = "/kafka/produce")
    public ResponseEntity<?> sendToKafka(@RequestParam("message") String message, Optional<KafkaConfig> config) {
        if (config.isPresent()) {
            Stream.of(config.get().getTopics()).forEach(topic -> {
                log.info("Send message to kafka topic {}", topic);
                kafkaTemplate.send(topic, message);
            });
            return ResponseEntity.ok("Successes send messages to Kafka");
        } else {
            return ResponseEntity.unprocessableEntity().body("Kafka topics not provided");
        }
    }

    /**
     * Call to external Market service.
     *
     * @return response as String
     */
    @GetMapping("/external")
    private String externalCallToMarketService() {
        String restResource = "produce";
        String queryParam = "some";

        String marketServiceUrl = "http://" + properties.getMarketServiceUrl() + "/api/v1/catalog/" + restResource;

        //Add query parameters
        URI uri = UriComponentsBuilder.fromUriString(marketServiceUrl)
                .queryParam("message", queryParam)
                .buildAndExpand()
                .toUri();

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);
        return response.getBody();
    }
}
