package com.example.Shop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(min = 2, max = 255, message = "Name min: 2, max: 255")
    @Column(nullable = false, length = 255)
    private String username;
    @Size(min = 2, max = 512, message = "Password min: 2, max: 512")
    @Column(nullable = false, length = 255)
    private String password;
    @Email
    @Size(min = 2, max = 255, message = "Email min: 2, max: 255")
    @Column(nullable = false, length = 255)
    private String email;
    @OneToMany(mappedBy = "user")
    private List<OrderDemo> orders;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
