package com.example.journalApp.controller;


import com.example.journalApp.entity.User;
import com.example.journalApp.repository.UserRepository;
import com.example.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    // only admin can visit users so removing get Users

    // Shifting post method to create user to Public controller
    // Enabled Authorization in User Controller

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        // Getting User name rom Security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Optional<User> userInDb = userService.findByName(userName);
        if(userInDb.isPresent()) {
            userInDb.get().setName(user.getName());
            if(user.getPassword() != null && !user.getPassword().isEmpty()) {
                userInDb.get().setPassword(user.getPassword());
            }
            userService.saveEntry(userInDb.get());
            return new ResponseEntity<>(userInDb, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
