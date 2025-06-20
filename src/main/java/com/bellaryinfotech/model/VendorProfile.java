package com.bellaryinfotech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendor_profile")
public class VendorProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Company Information
    @Column(name = "company_name", nullable = false)
    @NotBlank(message = "Company name is required")
    @Size(max = 255, message = "Company name must not exceed 255 characters")
    private String companyName;
    
    @Column(name = "house_plot_no")
    @Size(max = 100, message = "House/Plot number must not exceed 100 characters")
    private String housePlotNo;
    
    @Column(name = "floor")
    @Size(max = 50, message = "Floor must not exceed 50 characters")
    private String floor;
    
    @Column(name = "building_name")
    @Size(max = 255, message = "Building name must not exceed 255 characters")
    private String buildingName;
    
    @Column(name = "street")
    @Size(max = 255, message = "Street must not exceed 255 characters")
    private String street;
    
    @Column(name = "area")
    @Size(max = 255, message = "Area must not exceed 255 characters")
    private String area;
    
    @Column(name = "village_post")
    @Size(max = 255, message = "Village/Post must not exceed 255 characters")
    private String villagePost;
    
    @Column(name = "mandal_tq")
    @Size(max = 255, message = "Mandal/Tq must not exceed 255 characters")
    private String mandalTq;
    
    @Column(name = "district")
    @Size(max = 255, message = "District must not exceed 255 characters")
    private String district;
    
    @Column(name = "state")
    @Size(max = 255, message = "State must not exceed 255 characters")
    private String state;
    
    @Column(name = "pin_code")
    @Pattern(regexp = "^$|^[0-9]{6}$", message = "PIN code must be 6 digits or empty")
    private String pinCode;
    
    // Contact Information
    @Column(name = "contact_person")
    @Size(max = 255, message = "Contact person must not exceed 255 characters")
    private String contactPerson;
    
    @Column(name = "contact_number")
    @Pattern(regexp = "^$|^[0-9+\\-\\s()]*$", message = "Invalid contact number format or empty")
    private String contactNumber;
    
    @Column(name = "email")
    @Pattern(regexp = "^$|^[\\w._%+-]+@[\\w.-]+\\.[A-Z]{2,}$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid email format or empty")
    private String email;
    
    // Logo and Letter Head - FIXED for PostgreSQL
    @Column(name = "logo_data")
    private byte[] logoData;
    
    @Column(name = "logo_filename")
    private String logoFilename;
    
    @Column(name = "logo_content_type")
    private String logoContentType;
    
    @Column(name = "letter_head_data")
    private byte[] letterHeadData;
    
    @Column(name = "letter_head_filename")
    private String letterHeadFilename;
    
    @Column(name = "letter_head_content_type")
    private String letterHeadContentType;
    
    // Banking Information
    @Column(name = "bank_account")
    @Size(max = 50, message = "Bank account must not exceed 50 characters")
    private String bankAccount;
    
    @Column(name = "ifsc_code")
    @Pattern(regexp = "^$|^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code format or empty")
    private String ifscCode;
    
    @Column(name = "branch_name")
    @Size(max = 255, message = "Branch name must not exceed 255 characters")
    private String branchName;
    
    // Standard Information
    @Column(name = "gst_no")
    @Pattern(regexp = "^$|^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$", message = "Invalid GST format or empty")
    private String gstNo;
    
    @Column(name = "pan_no")
    @Pattern(regexp = "^$|^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Invalid PAN format or empty")
    private String panNo;
    
    // Audit Fields
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfileStatus status = ProfileStatus.ACTIVE;
    
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
    public VendorProfile() {}
    
    public VendorProfile(String companyName) {
        this.companyName = companyName;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    
    public String getHousePlotNo() { return housePlotNo; }
    public void setHousePlotNo(String housePlotNo) { this.housePlotNo = housePlotNo; }
    
    public String getFloor() { return floor; }
    public void setFloor(String floor) { this.floor = floor; }
    
    public String getBuildingName() { return buildingName; }
    public void setBuildingName(String buildingName) { this.buildingName = buildingName; }
    
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    
    public String getVillagePost() { return villagePost; }
    public void setVillagePost(String villagePost) { this.villagePost = villagePost; }
    
    public String getMandalTq() { return mandalTq; }
    public void setMandalTq(String mandalTq) { this.mandalTq = mandalTq; }
    
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    
    public String getPinCode() { return pinCode; }
    public void setPinCode(String pinCode) { this.pinCode = pinCode; }
    
    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }
    
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public byte[] getLogoData() { return logoData; }
    public void setLogoData(byte[] logoData) { this.logoData = logoData; }
    
    public String getLogoFilename() { return logoFilename; }
    public void setLogoFilename(String logoFilename) { this.logoFilename = logoFilename; }
    
    public String getLogoContentType() { return logoContentType; }
    public void setLogoContentType(String logoContentType) { this.logoContentType = logoContentType; }
    
    public byte[] getLetterHeadData() { return letterHeadData; }
    public void setLetterHeadData(byte[] letterHeadData) { this.letterHeadData = letterHeadData; }
    
    public String getLetterHeadFilename() { return letterHeadFilename; }
    public void setLetterHeadFilename(String letterHeadFilename) { this.letterHeadFilename = letterHeadFilename; }
    
    public String getLetterHeadContentType() { return letterHeadContentType; }
    public void setLetterHeadContentType(String letterHeadContentType) { this.letterHeadContentType = letterHeadContentType; }
    
    public String getBankAccount() { return bankAccount; }
    public void setBankAccount(String bankAccount) { this.bankAccount = bankAccount; }
    
    public String getIfscCode() { return ifscCode; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }
    
    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }
    
    public String getGstNo() { return gstNo; }
    public void setGstNo(String gstNo) { this.gstNo = gstNo; }
    
    public String getPanNo() { return panNo; }
    public void setPanNo(String panNo) { this.panNo = panNo; }
    
    public ProfileStatus getStatus() { return status; }
    public void setStatus(ProfileStatus status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public enum ProfileStatus {
        ACTIVE, INACTIVE, SUSPENDED
    }
}
