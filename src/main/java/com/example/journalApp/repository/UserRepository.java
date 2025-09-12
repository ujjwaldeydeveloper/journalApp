package com.example.journalApp.repository;

import com.example.journalApp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Object> {
    User findByUserName(String username);
}