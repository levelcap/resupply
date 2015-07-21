package com.brave.resupply.repository;

import com.brave.resupply.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByEmailIgnoreCase(String email);
    List<User> findByUsernameIgnoreCase(String username);
}