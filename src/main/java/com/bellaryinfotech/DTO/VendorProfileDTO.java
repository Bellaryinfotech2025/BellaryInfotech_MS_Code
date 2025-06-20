package com.bellaryinfotech.DTO;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class VendorProfileDTO {
    
    private Long id;
    
    @NotBlank(message = "Company name is required")
    @Size(max = 255, message = "Company name must not exceed 255 characters")
    private String companyName;
    
    @Size(max = 100, message = "House/Plot number must not exceed 100 characters")
    private String housePlotNo;
    
    @Size(max = 50, message = "Floor must not exceed 50 characters")
    private String floor;
    
    @Size(max = 255, message = "Building name must not exceed 255 characters")
    private String buildingName;
    
    @Size(max = 255, message = "Street must not exceed 255 characters")
    private String street;
    
    @Size(max = 255, message = "Area must not exceed 255 characters")
    private String area;
    
    @Size(max = 255, message = "Village/Post must not exceed 255 characters")
    private String villagePost;
    
    @Size(max = 255, message = "Mandal/Tq must not exceed 255 characters")
    private String mandalTq;
    
    @Size(max = 255, message = "District must not exceed 255 characters")
    private String district;
    
    @Size(max = 255, message = "State must not exceed 255 characters")
    private String state;
    
    @Pattern(regexp = "^$|^[0-9]{6}$", message = "PIN code must be 6 digits or empty")
    private String pinCode;
    
    @Size(max = 255, message = "Contact person must not exceed 255 characters")
    private String contactPerson;
    
    @Pattern(regexp = "^$|^[0-9+\\-\\s()]*$", message = "Invalid contact number format or empty")
    private String contactNumber;
    
    @Pattern(regexp = "^$|^[\\w._%+-]+@[\\w.-]+\\.[A-Z]{2,}$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid email format or empty")
    private String email;
    
    // Logo information (not the actual data for DTO)
    private String logoFilename;
    private String logoContentType;
    private boolean hasLogo;
    
    // Letter head information
    private String letterHeadFilename;
    private String letterHeadContentType;
    private boolean hasLetterHead;
    
    @Size(max = 50, message = "Bank account must not exceed 50 characters")
    private String bankAccount;
    
    @Pattern(regexp = "^$|^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code format or empty")
    private String ifscCode;
    
    @Size(max = 255, message = "Branch name must not exceed 255 characters")
    private String branchName;
    
    @Pattern(regexp = "^$|^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$", message = "Invalid GST format or empty")
    private String gstNo;
    
    @Pattern(regexp = "^$|^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Invalid PAN format or empty")
    private String panNo;
    
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public VendorProfileDTO() {}
    
    public VendorProfileDTO(String companyName) {
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
    
    public String getLogoFilename() { return logoFilename; }
    public void setLogoFilename(String logoFilename) { this.logoFilename = logoFilename; }
    
    public String getLogoContentType() { return logoContentType; }
    public void setLogoContentType(String logoContentType) { this.logoContentType = logoContentType; }
    
    public boolean isHasLogo() { return hasLogo; }
    public void setHasLogo(boolean hasLogo) { this.hasLogo = hasLogo; }
    
    public String getLetterHeadFilename() { return letterHeadFilename; }
    public void setLetterHeadFilename(String letterHeadFilename) { this.letterHeadFilename = letterHeadFilename; }
    
    public String getLetterHeadContentType() { return letterHeadContentType; }
    public void setLetterHeadContentType(String letterHeadContentType) { this.letterHeadContentType = letterHeadContentType; }
    
    public boolean isHasLetterHead() { return hasLetterHead; }
    public void setHasLetterHead(boolean hasLetterHead) { this.hasLetterHead = hasLetterHead; }
    
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
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
