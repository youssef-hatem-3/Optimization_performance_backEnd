package com.performancelab.backend.config;

import com.performancelab.backend.entity.User;
import com.performancelab.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedUsers(UserRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(
                        new User(
                                "Ahmed", "Ali",
                                "ahmed@example.com",
                                25, "admin", "Egypt"
                        )
                );

                repository.save(
                        new User(
                                "Omar", "Hassan",
                                "omar@example.com",
                                28, "user", "Egypt"
                        )
                );
            }

            for (int i = 3; i <= 32; i++) {
                String email = "user" + i + "@example.com";

                if (!repository.existsByEmail(email)) {
                    repository.save(
                            new User(
                                    "FirstName" + i,
                                    "LastName" + i,
                                    email,
                                    20 + (i % 30),
                                    "user",
                                    "Egypt"
                            )
                    );
                }
            }
        };
    }
}
