package ru.fruits.client.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
public class FooController {
    @GetMapping
    public ResponseEntity<?> getOrders(){
        return ResponseEntity.ok("Foo!");
    }

    @GetMapping("/bar")
    public ResponseEntity<?> getOrdersBar(){
        return ResponseEntity.ok("Foo/Bar!");
    }

    @GetMapping("/bar/goo")
    public ResponseEntity<?> getOrdersBarGoo(){
        return ResponseEntity.ok("Foo/Bar/Goo!");
    }
}

