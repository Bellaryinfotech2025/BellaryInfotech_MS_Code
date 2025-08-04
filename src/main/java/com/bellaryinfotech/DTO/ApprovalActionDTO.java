package com.bellaryinfotech.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ApprovalActionDTO {
    @NotNull(message = "Request ID is required")
    private Long requestId;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "Action is required")
    private String action; // "approve" or "reject"
    
    // Constructors
    public ApprovalActionDTO() {}
    
    public ApprovalActionDTO(Long requestId, String email, String action) {
        this.requestId = requestId;
        this.email = email;
        this.action = action;
    }
    
    // Getters and Setters
    public Long getRequestId() { return requestId; }
    public void setRequestId(Long requestId) { this.requestId = requestId; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
}
