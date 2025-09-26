package com.example.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class JournalAppApplication {

	public static void main(String[] args)   {
		SpringApplication.run(JournalAppApplication.class, args);
	}

}
