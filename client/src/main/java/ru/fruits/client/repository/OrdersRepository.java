package ru.fruits.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.fruits.client.entity.Order;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<Order> {

    Order findByName(String orderName);

    void deleteByName(String orderName);
}