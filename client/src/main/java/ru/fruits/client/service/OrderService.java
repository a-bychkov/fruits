package ru.fruits.client.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Order getOrderByName(String name) {
        return ordersRepository.findByName(name);
    }

    public Order saveOrder(Order order) {
        return ordersRepository.save(order);
    }

    @Transactional(timeout = 5)
    public void deleteOrderByName(String name) {
        ordersRepository.deleteByName(name);
    }
}
