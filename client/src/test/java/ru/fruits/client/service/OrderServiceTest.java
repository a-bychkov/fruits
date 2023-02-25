package ru.fruits.client.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.fruits.client.IntegrationTestBase;
import ru.fruits.client.dto.OrderFilter;
import ru.fruits.client.entity.Order;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql("/sql/data.sql")
class OrderServiceTest extends IntegrationTestBase {
    @Autowired
    OrderService orderService;

    @Test
    void getOrders() {
        List<Order> orders = orderService.getOrders();
        assertEquals(2, orders.size());
    }

    @Test
    void getOrdersWithFilter() {
        OrderFilter filter = OrderFilter.builder()
                .name("est_oRdeR")
                .price(40)
                .build();

        List<Order> orders = orderService.getOrders(filter);

        assertEquals(1, orders.size());
        assertEquals("test_order_1", orders.get(0).getName());
    }
}