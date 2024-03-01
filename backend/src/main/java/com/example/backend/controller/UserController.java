package com.example.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.backend.helper.JwtUtil;
import com.example.backend.model.User;
import com.example.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/users")
public class UserController {
  @Autowired
  private UserService userService;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtUtil jwtUtil;

  @PostMapping("/register")
  public ResponseEntity<User> registerUser(@RequestBody User user) {
    User registeredUser = userService.registerUser(user.getEmail(), user.getPassword());
    return ResponseEntity.ok(registeredUser);
  }
  @PostMapping("/login")
  public String login(@RequestBody User user) {
    // Authentication
    Authentication auth = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
    );
    //Keep the auth for entire application
    SecurityContextHolder.getContext().setAuthentication(auth);
    //generate JWT
    String jwt = jwtUtil.generateToken(auth);
    return jwt;
  }
  

  @GetMapping("/hello")
  public String hello() {
    return "Hello my friend!";
  }

}
