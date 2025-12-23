package com.example.journalApp;

import com.example.journalApp.repository.UserRepository;
import com.example.journalApp.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.example.journalApp.entity.User;

@SpringBootTest
public class JournalAppApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


	@ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
	public void testSaveUserName(User user) {
        assertTrue(userService.saveNewUser(user));
	}

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1, 1, 2",
            "10, 2, 12",
            "3, 5, 15"
    })
    public void test(int a , int b, int expected) {
        assertEquals(expected, a + b);
    }

}
