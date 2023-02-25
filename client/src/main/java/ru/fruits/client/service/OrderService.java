package ru.fruits.client.service;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fruits.client.dto.OrderFilter;
import ru.fruits.client.entity.Order;
import ru.fruits.client.entity.OrderSpecification;
import ru.fruits.client.repository.OrderHashRepository;
import ru.fruits.client.repository.OrderRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
        Specification<Order> spec = Specification
                .where(OrderSpecification.likeName(filter.getName()))
                .and(OrderSpecification.equalsPrice(filter.getPrice()));

        return ordersRepository.findAll(spec);
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
