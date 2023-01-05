package ru.fruits.client.service;

import com.querydsl.core.types.Predicate;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fruits.client.dto.OrderFilter;
import ru.fruits.client.entity.Order;
import ru.fruits.client.repository.OrderHashRepository;
import ru.fruits.client.repository.OrderRepository;
import ru.fruits.client.util.QPredicates;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.fruits.client.entity.QOrder.order;

@Service
@Slf4j
public class OrderService {
    private OrderRepository ordersRepository;
    private AtomicInteger invocationCount;
    private OrderHashRepository orderHashRepository;

    public OrderService(OrderRepository ordersRepository, MeterRegistry meterRegistry, OrderHashRepository orderHashRepository) {
        this.ordersRepository = ordersRepository;
        invocationCount = new AtomicInteger();
        meterRegistry.gauge("invocationCount", invocationCount);
        this.orderHashRepository = orderHashRepository;
    }

    public List<Order> getOrders() {
        invocationCount.set(invocationCount.incrementAndGet());
        return ordersRepository.findAll();
    }

    public List<Order> getOrders(OrderFilter filter) {
        List<Order> returnValue = new ArrayList<>();

        Predicate predicate = QPredicates.builder()
                .add(filter.getName(), order.name::containsIgnoreCase)
                .add(filter.getPrice(), order.price::goe)
                .buildAnd();

        Iterable<Order> result = ordersRepository.findAll(predicate);
        result.forEach(returnValue::add);

        return returnValue;
    }

    @Cacheable(value = "orders", key = "#name")
    public Order getOrderByName(String name) {
        return ordersRepository.findByName(name);
    }

    @Transactional
    public Order saveOrder(Order order) {
        // save new order to db
        ordersRepository.save(order);

        // save order to cache
        saveOrderIntoCache(order);

        return order;
    }

    /**
     * Put entity into cache.
     *
     * @param order order itself
     */
    @Transactional
    private Order saveOrderIntoCache(Order order) {
        return orderHashRepository.save(order);
    }

    @Transactional(timeout = 5)
    @CacheEvict(value = "orders", key = "#name")
    public void deleteOrderByName(String name) {
        ordersRepository.deleteByName(name);
    }

    @CacheEvict(value = "orders", allEntries = true)
    public void evictAllCacheValues() {
    }
}
