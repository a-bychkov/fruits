package ru.fruits.client.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fruits.client.entity.Order;
import ru.fruits.client.repository.OrdersRepository;

import java.util.List;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrdersRepository ordersRepository;

    public List<Order> getOrders() {
        return ordersRepository.findAll();
    }

    @Cacheable(value = "orders", key="#name")
    public Order getOrderByName(String name) {
        return ordersRepository.findByName(name);
    }

    public Order saveOrder(Order order) {
        return ordersRepository.save(order);
    }

    @Transactional(timeout = 5)
    @CacheEvict(value = "orders", key = "#name")
    public void deleteOrderByName(String name) {
        ordersRepository.deleteByName(name);
    }

    @CacheEvict(value = "orders", allEntries = true)
    public void evictAllCacheValues() {}
}
