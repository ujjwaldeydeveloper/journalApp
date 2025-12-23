package com.example.journalApp;

import com.example.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class JournalAppApplicationTests {

    @Autowired
    private UserRepository userRepository;


	@ParameterizedTest
    @ValueSource(strings = {
            "ram",
            "ujjwal",
            "dey"
    })
    @ArgumentsSource(UserArgumentsProvider.class)
	public void testFindByUserName(String name) {
        assertTrue(userRepository.findByName(name).isPresent());
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
