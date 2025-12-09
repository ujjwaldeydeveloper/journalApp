package com.example.journalApp.service;

import com.example.journalApp.entity.User;
import com.example.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /*
    Security Filter Intercepts: Before the request reaches your controller
    (UserController, PublicController, etc.), it's intercepted by Spring
    Security's filter chain.

    Authentication Attempt: The filter chain's primary job is to establish
    the identity of the caller. It looks for authentication details,
    typically a JWT in an Authorization header or a session cookie.

    Username Extraction: If a token is found, Spring Security
    validates it and extracts the username.

    User Details Loading: It then calls the loadUserByUsername method of your
    UserDetailsServiceImpl. This is the crucial step. Spring Security needs
    to get the user's latest details (especially roles and password) from the
    database to build a complete Authentication object. This object represents
    the currently logged-in user for this specific request.

    Security Context: This Authentication object is placed in the SecurityContextHolder,
    making it available throughout your application (which is why SecurityContextHolder.
    getContext().getAuthentication() works in your controllers).
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByName(username);
        if(user.isPresent()) {
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(user.get().getName())
                    .password(user.get().getPassword())
                    .roles(user.get().getRoles().toArray(new String[0]))
                    .build();

            return userDetails;
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
