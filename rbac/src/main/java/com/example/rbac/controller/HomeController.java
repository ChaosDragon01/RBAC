package com.example.rbac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String root() {
        return "home"; // Serves home.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Serves login.html
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // Optional: you can redirect / after login
    }
}
