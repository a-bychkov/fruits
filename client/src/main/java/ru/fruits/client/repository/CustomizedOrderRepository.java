package ru.fruits.client.repository;

import ru.fruits.client.entity.Order;

import java.util.List;

public interface CustomizedOrderRepository {
    List<Order> search(String terms, int limit, int offset);
}
