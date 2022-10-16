package ru.fruits.client.service;

import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fruits.client.dto.OrderFilter;
import ru.fruits.client.entity.Order;
import ru.fruits.client.repository.OrdersRepository;
import ru.fruits.client.util.QPredicates;

import java.util.ArrayList;
import java.util.List;

import static ru.fruits.client.entity.QOrder.order;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrdersRepository ordersRepository;

    public List<Order> getOrders() {
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
