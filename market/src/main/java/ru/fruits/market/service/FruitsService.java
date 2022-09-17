package ru.fruits.market.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fruits.market.model.Fruit;
import ru.fruits.market.repository.FruitsRepository;

@Service
@Slf4j
public class FruitsService {

    @Autowired
    private FruitsRepository fruitsRepository;

    public Fruit save(Fruit fruit){
        return fruitsRepository.save(fruit);
    }
}
