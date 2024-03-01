package com.example.backend.config;

import static com.example.backend.config.GlobalConstants.LOGIN_ENDPOINT;
import static com.example.backend.config.GlobalConstants.REGISTER_ENDPOINT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.backend.filter.JwtAuthenticationFilter;
import com.example.backend.helper.JwtUtil;
import com.example.backend.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Autowired
  private UserService userService;
  @Autowired
  private JwtUtil jwtUtil;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(
            authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers(LOGIN_ENDPOINT, REGISTER_ENDPOINT).permitAll()
                .anyRequest().authenticated())
        .csrf(csrf -> csrf.disable())
        .addFilterBefore(new JwtAuthenticationFilter(userService, jwtUtil), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(userService)
        .passwordEncoder(new BCryptPasswordEncoder());
  }

  // @Bean
  // public JwtAuthenticationFilter jwtAuthenticaionFilter() {
  // return new JwtAuthenticationFilter(userService, jwtUtil);
  // }
}
