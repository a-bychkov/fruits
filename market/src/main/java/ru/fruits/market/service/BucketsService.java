package ru.fruits.market.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.fruits.market.model.Bucket;

@Service
@Slf4j
public class BucketsService {

  public List<Bucket> getBuckets() {
    return List.of(new Bucket(1L, "Bucket with fruits"),
        new Bucket(2L, "Bucket with fruits"),
        new Bucket(3L, "Bucket with fruits")
    );
  }
}
