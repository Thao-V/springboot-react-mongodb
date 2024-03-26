package com.example.backend.config;

public final class GlobalConstants {
  // Prevent instantiation
  private GlobalConstants() {
  }

  // API Endpoints
  public static final String LOGIN_ENDPOINT = "/api/users/login";
  public static final String REGISTER_ENDPOINT = "/api/users/register";

  public static final String HEALTH_CHECKING_ENDPOINT = "/actuator/health";

  // JWT Constants
  public static final String JWT_SECRET_KEY = "your_secret_key";
  public static final long JWT_EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours
}
