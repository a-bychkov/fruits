package ru.fruits.client.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class OrdersController {
    @GetMapping
    public ResponseEntity<?> getOrders(){
        return ResponseEntity.ok("ok!");
    }
}
