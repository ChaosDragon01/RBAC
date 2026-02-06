package com.example.rbac.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.rbac.model.Role;
import com.example.rbac.model.User;
import com.example.rbac.repository.RoleRepository;
import com.example.rbac.repository.UserRepository;

@Component
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
        RoleRepository roleRepo,
        UserRepository userRepo,
        PasswordEncoder encoder
    ) {
        return args -> {

            Role adminRole = roleRepo.findByName("ADMIN")
                .orElseGet(() -> roleRepo.save(new Role("ADMIN")));

            Role userRole = roleRepo.findByName("USER")
                .orElseGet(() -> roleRepo.save(new Role("USER")));

            if (userRepo.findByUsername("admin").isEmpty()) {
                userRepo.save(
                    new User(
                        "admin",
                        encoder.encode("admin123"),
                        Set.of(adminRole)
                    )
                );
            }

            if (userRepo.findByUsername("user").isEmpty()) {
                userRepo.save(
                    new User(
                        "user",
                        encoder.encode("user123"),
                        Set.of(userRole)
                    )
                );
            }
        };
    }
}
