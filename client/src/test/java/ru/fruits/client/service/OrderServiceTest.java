package ru.fruits.client.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.fruits.client.IntegrationTestBase;
import ru.fruits.client.entity.Order;

@Sql("/sql/data.sql")
class OrderServiceTest extends IntegrationTestBase {
    @Autowired
    OrderService orderService;

    @Test
    void getOrders() {
        List<Order> orders = orderService.getOrders();
        assertEquals(2, orders.size());
    }
}