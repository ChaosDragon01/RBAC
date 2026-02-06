package com.example.rbac.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public Role() {} // REQUIRED by JPA

    public Role(String name) {
        this.name = name;
    }

    // getters
    public Long getId() { return id; }
    public String getName() { return name; }
}
