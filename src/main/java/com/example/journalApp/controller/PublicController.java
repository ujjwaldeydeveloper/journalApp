package com.example.journalApp.controller;

import com.example.journalApp.dto.UserDTO;
import com.example.journalApp.entity.User;
import com.example.journalApp.service.UserDetailsServiceImpl;
import com.example.journalApp.service.UserService;
import com.example.journalApp.utills.JwtUtill;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
@Tag(name = "Public APIs")
public class PublicController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtill jwtUtill;

    @GetMapping("/health-check")
    public String healthcheck() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (authentication != null && authentication.isAuthenticated() 
                && !"anonymousUser".equals(authentication.getName())) 
                ? authentication.getName() 
                : "Anonymous";
        return "Ok - User: " + userName;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO user) {
        try {
            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setName(user.getName());
            newUser.setPassword(user.getPassword());
            newUser.setSentimentAnalysis(user.isSentimentAnalysis());

            boolean saved = userService.saveNewUser(newUser);
            if (!saved) {
                return new ResponseEntity<>("User already exists (duplicate username or email)", HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating new user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());
            String jwt = jwtUtill.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Exception while login and creating AuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect Username and Password" + e.toString(), HttpStatus.BAD_REQUEST);
        }
    }


}
