package com.example.journalApp.repository;

import com.example.journalApp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Object> {
    Optional<User> findByName(String name);

    void deleteByName(String name);
}