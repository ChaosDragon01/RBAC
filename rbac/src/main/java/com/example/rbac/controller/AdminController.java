package com.example.rbac.controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rbac.model.Role;
import com.example.rbac.model.User;
import com.example.rbac.repository.RoleRepository;
import com.example.rbac.repository.UserRepository;

@Controller
public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // --- MAIN DASHBOARD ---
    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin";
    }

    // --- ROLE MANAGEMENT ---
    @PostMapping("/admin/create-role")
    public String createRole(@RequestParam String roleName, @RequestParam String parentRole) {
        // Ensure role name is uppercase for consistency
        String normalizedName = roleName.toUpperCase();
        if (roleRepository.findByName(normalizedName).isEmpty()) {
            roleRepository.save(new Role(normalizedName, parentRole));
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete-role")
    public String deleteRole(@RequestParam String id) {
        roleRepository.deleteById(id);
        return "redirect:/admin";
    }

    // --- USER MANAGEMENT: CREATE ---
    @PostMapping("/admin/create-user")
    public String createUser(@RequestParam String username, 
                             @RequestParam String password,
                             @RequestParam String email,
                             @RequestParam(required = false) String initialRole) {
        
        // Simple check to prevent duplicate usernames
        if (userRepository.findByUsername(username).isPresent()) {
            return "redirect:/admin?error=UsernameExists";
        }

        Set<String> roles = new HashSet<>();
        if (initialRole != null && !initialRole.isEmpty()) {
            roles.add(initialRole);
        } else {
            roles.add("USER");
        }

        User newUser = new User(
                username,
                passwordEncoder.encode(password),
                email,
                roles
        );
        
        userRepository.save(newUser);
        return "redirect:/admin?success=UserCreated";
    }

    // --- USER MANAGEMENT: DELETE ---
    @PostMapping("/admin/delete-user")
    public String deleteUser(@RequestParam String id) {
        userRepository.deleteById(id);
        return "redirect:/admin?success=UserDeleted";
    }

    // --- USER MANAGEMENT: EDIT SCREEN ---
    @GetMapping("/admin/edit-user/{id}")
    public String showEditUserForm(@PathVariable("id") String id, Model model) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleRepository.findAll());
        return "user-edit";
    }

    // --- USER MANAGEMENT: UPDATE ACTION ---
    @PostMapping("/admin/update-user")
    public String updateUser(@RequestParam String id, 
                             @RequestParam String username, 
                             @RequestParam String email,
                             @RequestParam(required = false) List<String> roles) {
        
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        user.setUsername(username);
        user.setEmail(email);
        
        if (roles != null) {
            user.setRoles(new HashSet<>(roles));
        } else {
            user.setRoles(Collections.emptySet());
        }

        userRepository.save(user);
        return "redirect:/admin?success=UserUpdated";
    }

    // --- PASSWORD MANAGEMENT (Admin Override) ---
    @PostMapping("/admin/reset-password")
    public String resetUserPassword(@RequestParam String userId, @RequestParam String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "redirect:/admin?success=PasswordReset";
    }

    // --- DATABASE VIEWER ---
    @GetMapping("/admin/database")
    public String databaseViewer(Model model) {
        model.addAttribute("rawUsers", userRepository.findAll());
        model.addAttribute("rawRoles", roleRepository.findAll());
        return "database";
    }
}