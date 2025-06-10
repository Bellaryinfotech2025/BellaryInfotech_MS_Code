package com.bellaryinfotech.model;

 
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "vendors")
public class Vendor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "vendor_code", unique = true, nullable = false, length = 50)
    @NotBlank(message = "Vendor code is required")
    @Size(max = 50, message = "Vendor code must not exceed 50 characters")
    private String vendorCode;
    
    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;
    
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;
    
    @Column(name = "phone", length = 15)
    @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "Invalid phone number format")
    private String phone;
    
    @Column(name = "email", length = 100)
    @Email(message = "Invalid email format")
    private String email;
    
    @Column(name = "pan_no", length = 10)
    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Invalid PAN format")
    private String panNo;
    
    @Column(name = "gst_no", length = 15)
    @Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$", message = "Invalid GST format")
    private String gstNo;
    
    @Column(name = "bank_account", length = 20)
    private String bankAccount;
    
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private VendorStatus status = VendorStatus.ACTIVE;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Vendor() {}
    
    public Vendor(String vendorCode, String name) {
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
    
    public VendorStatus getStatus() {
        return status;
    }
    
    public void setStatus(VendorStatus status) {
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
    
    public enum VendorStatus {
        ACTIVE, INACTIVE, SUSPENDED
    }
}
