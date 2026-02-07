package com.example.rbac.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rbac.model.User;
import com.example.rbac.repository.UserRepository;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/profile/password")
    public String showChangePassword() {
        return "password-change";
    }

    @PostMapping("/profile/update-password")
    public String updatePassword(@AuthenticationPrincipal UserDetails userDetails, 
                                 @RequestParam String newPassword) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "redirect:/home?success=Password Changed";
    }
}