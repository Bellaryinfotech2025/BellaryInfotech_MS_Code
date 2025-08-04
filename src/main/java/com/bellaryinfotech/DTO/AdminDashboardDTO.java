package com.bellaryinfotech.DTO;

import java.time.LocalDateTime;

public class AdminDashboardDTO {
    private Long id;
    private Long userId;
    private String email;
    private LocalDateTime datetime;
    private String status;
    private String fullname;
    private String username;
    private String role;
    
    // Constructors
    public AdminDashboardDTO() {}
    
    public AdminDashboardDTO(Long id, Long userId, String email, LocalDateTime datetime, 
                           String status, String fullname, String username, String role) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.datetime = datetime;
        this.status = status;
        this.fullname = fullname;
        this.username = username;
        this.role = role;
    }
    
   
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDateTime getDatetime() { return datetime; }
    public void setDatetime(LocalDateTime datetime) { this.datetime = datetime; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
