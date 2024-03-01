package com.example.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
  @Override
  public void addCorsMappings(CorsRegistry registry){
    // registry.addMapping("/api/**")
    //         .allowedOrigins("http://example.com", "https://another-example.com") // Allow specific origins
    //         .allowedMethods("GET", "POST", "PUT") // Allow specific methods
    //         .allowedHeaders("*"); // Allow all headers
    registry
    .addMapping("/api/**") //apply CORS for the path /api/** 
    .allowedOrigins("*") //allow all origins
    .allowedMethods("*"); //allow all methods
  }
}
