package com.example.rbac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rbac.repository.UserRepository;

@Controller
public class ForgotPasswordController {

    private final UserRepository userRepository;

    public ForgotPasswordController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/forgot-password")
    public String showForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgot(@RequestParam String email) {
        // SIMULATION: In real life, send an email here.
        System.out.println("------------------------------------------------");
        System.out.println("PASSWORD RESET REQUEST FOR: " + email);
        System.out.println("SENDING EMAIL TO: " + email);
        System.out.println("------------------------------------------------");
        
        return "redirect:/login?resetSent";
    }
}