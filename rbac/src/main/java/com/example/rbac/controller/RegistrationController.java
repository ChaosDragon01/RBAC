package com.example.rbac.controller;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rbac.model.User;
import com.example.rbac.repository.UserRepository;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username, 
            @RequestParam String password,
            @RequestParam String email) { // <--- 1. Add Email Parameter

        if (userRepository.findByUsername(username).isPresent()) {
            return "redirect:/register?error";
        }

        // 2. Use the NEW Constructor (Username, Password, Email, Roles)
        User newUser = new User(
                username,
                passwordEncoder.encode(password),
                email,  // <--- Pass the email here
                Set.of("USER")
        );

        userRepository.save(newUser);

        return "redirect:/login?success";
    }
}