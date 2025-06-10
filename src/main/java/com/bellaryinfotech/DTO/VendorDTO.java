package com.bellaryinfotech.DTO;
 
 

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class VendorDTO {
    
    private Long id;
    
    @NotBlank(message = "Vendor code is required")
    @Size(max = 50, message = "Vendor code must not exceed 50 characters")
    private String vendorCode;
    
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;
    
    private String address;
    
    @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "Invalid phone number format")
    private String phone;
    
    @Email(message = "Invalid email format")
    private String email;
    
    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Invalid PAN format")
    private String panNo;
    
    @Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$", message = "Invalid GST format")
    private String gstNo;
    
    private String bankAccount;
    
    private String status;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    // Constructors
    public VendorDTO() {}
    
    public VendorDTO(String vendorCode, String name) {
        this.vendorCode = vendorCode;
        this.name = name;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getVendorCode() {
        return vendorCode;
    }
    
    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPanNo() {
        return panNo;
    }
    
    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }
    
    public String getGstNo() {
        return gstNo;
    }
    
    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }
    
    public String getBankAccount() {
        return bankAccount;
    }
    
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
