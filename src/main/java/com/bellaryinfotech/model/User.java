package com.bellaryinfotech.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Table(name = "billing_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String fullname;
    
    @Column(nullable = false)
    private Long PhoneNumber;
    
    @Column(nullable = false)
    private String role;
    
    @Column(nullable = false)
    private LocalDateTime registerTime;
    
    @Column(nullable = false)
    private LocalDate registerDate;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Long getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getName() {
        return fullname;
    }

    public void setName(String name) {
        this.fullname = fullname;
    }
}
