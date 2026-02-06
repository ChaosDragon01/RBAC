package com.example.rbac.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.rbac.model.User;
import com.example.rbac.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository repo, PasswordEncoder encoder) {
        return args -> {

            if (repo.findByUsername("admin").isEmpty()) {
                repo.save(new User(
                        "admin",
                        encoder.encode("admin123"),
                        Set.of("ADMIN")
                ));
            }

            if (repo.findByUsername("user").isEmpty()) {
                repo.save(new User(
                        "user",
                        encoder.encode("user123"),
                        Set.of("USER")
                ));
            }
        };
    }
}
