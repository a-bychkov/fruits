package ru.fruits.client.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import ru.fruits.client.entity.Order;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class OrderHashRepository {
    public final String HASH_KEY = "Order";
    private final RedisTemplate redisTemplate;
    @Value("${spring.redis.ttl}")
    private long ttl;

    public Order save(Order order) {
        redisTemplate.opsForHash().put(HASH_KEY, order.getName(), order);
        redisTemplate.expire(HASH_KEY, ttl, TimeUnit.MILLISECONDS);// ttl
        return order;
    }

    public List<Order> findAll() {
        return redisTemplate.opsForHash().values(HASH_KEY);
    }

    public Order findOrderByName(String name) {
        return (Order) redisTemplate.opsForHash().get(HASH_KEY, name);
    }


    public void deleteOrder(String name) {
        redisTemplate.opsForHash().delete(HASH_KEY, name);
    }
}
