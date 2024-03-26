package com.example.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.model.User;
import com.example.backend.respository.UserRepository;


@Service
public class UserService implements UserDetailsService{
  @Autowired
  private UserRepository userRepository;

  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
    User user = userRepository.findByEmail(email);
    
    if(user == null){
      throw new UsernameNotFoundException("User not found with email: "+ email);
    }
    
    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
  }
  
  public User registerUser(String email, String password){
    try {
      String hashedPassword = passwordEncoder.encode(password);
      System.out.println("thao " + hashedPassword);
      User user = new User(email, hashedPassword);
      return userRepository.save(user);  
    } catch (Exception e) {
      System.err.println("Error registering user: " + e.getMessage());
      return null;
    }
    
  }
  public List<User> getAllUsers(){
    return userRepository.findAll();
  }
  public User getUserById(String id){
    return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
  }

  public User updateUser(String id, String password){
    User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    user.setPassword(password);
    return userRepository.save(user);
  }

  public void deleteUser(String id){
    User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    userRepository.delete(user);
  }
}
