package ru.fruits.market.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fruits.market.model.Bucket;
import ru.fruits.market.repository.BucketsRepository;

@Service
@Slf4j
public class BucketsService {

    @Autowired
    private BucketsRepository bucketsRepository;

    public Bucket save(Bucket bucket){
        return bucketsRepository.save(bucket);
    }
}
