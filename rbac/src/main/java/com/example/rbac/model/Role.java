package com.example.rbac.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
public class Role {
    @Id
    private String id;
    private String name;        // e.g., "MANAGER"
    private String parentRole;  // e.g., "ADMIN" (The hierarchy)

    public Role() {}

    public Role(String name, String parentRole) {
        this.name = name;
        this.parentRole = parentRole;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getParentRole() { return parentRole; }
    public void setParentRole(String parentRole) { this.parentRole = parentRole; }
}