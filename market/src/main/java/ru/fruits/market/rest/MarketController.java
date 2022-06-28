package ru.fruits.market.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class MarketController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<?> getExample(){
        return ResponseEntity.ok("Market snuff");
    }

    @GetMapping("/market")
    public ResponseEntity<?> getExample1(){
        String response = restTemplate.getForObject("http://client-app-service/foo/bar", String.class);
        return ResponseEntity.ok("Market response! " + response);
    }
}
