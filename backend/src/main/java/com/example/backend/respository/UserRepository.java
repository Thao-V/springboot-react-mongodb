package com.example.backend.respository;

import com.example.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>{
  User findByEmail(String email);
}
