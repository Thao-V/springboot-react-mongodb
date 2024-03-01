package com.example.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "users")
public class User {
  @Id
  private String id;
  private String email;
  private String password;
  private String name;
  public User(){

  }
  public User(String _email, String _password){
    email = _email;
    password = _password;
    name = "test";
  }
  public User(String _id, String _email, String _password){
    email = _email;
    password = _password;
    id = _id;
    name = "test";
  }
  public String getEmail(){
    return email;
  }
  public String getId(){
    return id;
  }
  public void setPassword(String _password){
    password = _password;
  }
  public String getPassword(){
    return password;
  }
  public void setName(String _name){
    name = _name;
  }
  public String getName(){
    return name;
  }
}
