package com.example.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalAppApplication {
    // in Environment file you can also keep .....spring.profiles.active=dev
    // to make Jar command .........  .\mvn clean package -D spring.profiles.active=dev [you are setting JVM system properties]
    // java -jar .\journalApp-0.0.1-SNAPSHOT.jar --spring.profile.active=dev
	public static void main(String[] args)   {
        ConfigurableApplicationContext context = SpringApplication.run(JournalAppApplication.class, args);
        // print the profile mode
        System.out.println(context.getEnvironment().getActiveProfiles()[0]);

	}

    @Bean
    public PlatformTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

}

// precedence order
// command line arguments in edit configuration > application.properties > application.yml
