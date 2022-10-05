package ru.fruits.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fruits.client.entity.Order;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long>{
    Order findByName(String orderName);

    void deleteByName(String orderName);
}
