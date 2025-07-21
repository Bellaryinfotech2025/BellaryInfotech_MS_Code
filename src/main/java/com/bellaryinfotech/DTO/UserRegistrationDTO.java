package com.bellaryinfotech.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {
    
    @NotBlank(message = "Full name is required")
    private String fullname;
    
    @NotBlank(message = "Username is required")
    private String username;
    
    @NotBlank(message = "Role is required")
    private String role;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    
    @NotNull(message = "Phone number is required")
    private Long phoneNumber;

    // Constructors
    public UserRegistrationDTO() {}

    public UserRegistrationDTO(String fullname, String username, String role, String email, String password, Long phoneNumber) {
        this.fullname = fullname;
        this.username = username;
        this.role = role;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
