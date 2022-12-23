package ru.fruits.client.controller;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.fruits.client.config.ConfigProperties;
import ru.fruits.client.entity.Order;
import ru.fruits.client.service.OrderService;

import java.net.URI;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrdersController {
    private final ConfigProperties properties;
    private final OrderService orderService;
    private final RestTemplate restTemplate;
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

    @Timed("gettingOrders")
    @GetMapping
    public ResponseEntity<?> getOrders() {
        log.info("Getting orders");

        List<Order> orders = orderService.getOrders();

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
