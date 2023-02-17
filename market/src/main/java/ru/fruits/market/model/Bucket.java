package ru.fruits.market.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Bucket {
  private final long id;

  private final String name;
}
