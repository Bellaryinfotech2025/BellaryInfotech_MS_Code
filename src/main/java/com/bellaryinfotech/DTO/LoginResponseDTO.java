package com.bellaryinfotech.DTO;

public class LoginResponseDTO {
    
    private String token;
    private String email;
    private String username;
    private String fullname;
    private String role;
    private String message;

    // Constructors
    public LoginResponseDTO() {}

    public LoginResponseDTO(String token, String email, String username, String fullname, String role, String message) {
        this.token = token;
        this.email = email;
        this.username = username;
        this.fullname = fullname;
        this.role = role;
        this.message = message;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
