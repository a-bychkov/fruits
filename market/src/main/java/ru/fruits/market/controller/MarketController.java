package ru.fruits.market.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fruits.market.service.BucketsService;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
@Slf4j
public class MarketController {

  private final BucketsService bucketsService;

  @GetMapping
  public ResponseEntity<?> getFruitBucket() {
    return ResponseEntity.ok(bucketsService.getBuckets());
  }
}
