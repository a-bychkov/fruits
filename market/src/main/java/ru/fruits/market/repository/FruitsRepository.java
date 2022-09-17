package ru.fruits.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fruits.market.model.Fruit;

@Repository
public interface FruitsRepository extends JpaRepository<Fruit, Long> {
}
