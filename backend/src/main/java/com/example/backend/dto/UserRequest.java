package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
  @NotBlank(message="Email is required and cannot be null or empty string or blank space")
  String email,
  @NotBlank(message="Password is required and cannot be null or empty string or blank space")
  String password
) {}
