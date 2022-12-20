package ru.fruits.client.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {

  private String email;
  private String password;
}
