package ru.fruits.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fruits.market.model.Bucket;

@Repository
public interface BucketsRepository extends JpaRepository<Bucket, Long> {
}
