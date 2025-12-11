package com.example.journalApp.service;

import com.example.journalApp.entity.User;
import com.example.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void saveEntry(User user) {
        userRepository.save(user);
    }

    public void saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("User"));
        userRepository.save(user);
    }

    public void saveAdmin(User user) {
        user.setId(new ObjectId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("User","ADMIN"));
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getByID(Object id) {
        return userRepository.findById(id);
    }

    public void deleteByID(Object id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

}
