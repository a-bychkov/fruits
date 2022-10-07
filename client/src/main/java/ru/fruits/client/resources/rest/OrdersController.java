package ru.fruits.client.resources.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.fruits.client.config.ConfigProperties;
import ru.fruits.client.entity.Order;
import ru.fruits.client.service.OrderService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@Slf4j
public class OrdersController {
    @Autowired
    private ConfigProperties properties;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

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
