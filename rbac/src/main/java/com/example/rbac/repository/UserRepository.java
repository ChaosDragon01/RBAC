package com.example.rbac.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rbac.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
