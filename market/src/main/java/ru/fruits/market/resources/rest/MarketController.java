package ru.fruits.market.resources.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.fruits.market.model.Fruit;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/buy")
public class MarketController {

    @PostMapping(value = "/fruits", produces = "application/json")
    public ResponseEntity<?> buyFruits(@RequestParam String name, @RequestParam int amount) {
        List<Fruit> fruits = new LinkedList<>();

        for (int i = 0; i < amount; i++) {
            fruits.add(new Fruit(name));
        }

        return ResponseEntity.ok(fruits);
    }
}
