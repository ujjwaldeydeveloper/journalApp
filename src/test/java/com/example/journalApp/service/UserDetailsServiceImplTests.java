package com.example.journalApp.service;


import com.example.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import static org.mockito.Mockito.*;
import com.example.journalApp.entity.User;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.Optional;

// When we need some context or data that should be coming from the Database
// then we uer SpringBootText context
//@SpringBootTest  // removing Application context
public class UserDetailsServiceImplTests {

//    @Autowired
    @InjectMocks  // InjectMock will create an instance of the userDetailsService
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;
    // userRepository will still come null unless we ake sure once
    // instance been created for UserRepository
    // so we make sure the mock instance setup is ready

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTest() {
        when(userRepository.findByName(ArgumentMatchers.anyString())).thenReturn(Optional.ofNullable(User.builder().name("dey").password("Ujjwal@123").roles(new ArrayList<>()).build()));
        UserDetails user = userDetailsService.loadUserByUsername("dey");
        Assertions.assertNotNull((user));
    }
}



