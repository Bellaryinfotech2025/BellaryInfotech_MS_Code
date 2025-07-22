package com.bellaryinfotech.DTO;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class UserResponseDTO {
    
    private Long id;
    private String fullname;
    private String username;
    private String role;
    private String email;
    private Long phoneNumber;
    private LocalDateTime registerTime;
    private LocalDate registerDate;
    private Boolean verified;

    // Constructors
    public UserResponseDTO() {}

    public UserResponseDTO(Long id, String fullname, String username, String role, String email, Long phoneNumber, LocalDateTime registerTime, LocalDate registerDate, Boolean verified) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.registerTime = registerTime;
        this.registerDate = registerDate;
        this.verified = verified;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}
