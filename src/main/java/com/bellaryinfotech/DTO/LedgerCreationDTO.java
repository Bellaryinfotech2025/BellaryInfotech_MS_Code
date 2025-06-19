package com.bellaryinfotech.DTO;

import jakarta.validation.constraints.*;

public class LedgerCreationDTO {
    
    private Long id;
    
    @NotBlank(message = "Ledger name is required")
    private String ledgerName;
    
    private String groupName;
    private String debtorCreditor;
    private String housePlotNo;
    private String street;
    private String villagePost;
    private String mandalTaluq;
    private String district;
    private String state;
    
    @Pattern(regexp = "^$|^[0-9]{6}$", message = "Pin code must be 6 digits or empty")
    private String pinCode;
    
    private String contactPersonName;
    
    @Pattern(regexp = "^$|^[0-9]{10,15}$", message = "Mobile number must be 10-15 digits or empty")
    private String mobileNo;
    
    @Pattern(regexp = "^$|^[\\w._%+-]+@[\\w.-]+\\.[A-Z]{2,}$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Please enter a valid email address or leave empty")
    private String email;
    
    private String website;
    
    @Pattern(regexp = "^$|^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$", message = "Please enter a valid GSTIN (15 characters) or leave empty")
    private String gstin;
    
    @Pattern(regexp = "^$|^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Please enter a valid PAN (10 characters) or leave empty")
    private String pan;
    
    private String bankAccountNo;
    
    // Fixed IFSC validation - allows empty string or valid IFSC format
    @Pattern(regexp = "^$|^[A-Z]{4}0[A-Z0-9]{6}$", message = "Please enter a valid IFSC code (11 characters) or leave empty")
    private String ifscCode;
    
    private String branchName;
    private String serviceType;
    private String status;
    
    // Constructors
    public LedgerCreationDTO() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getLedgerName() { return ledgerName; }
    public void setLedgerName(String ledgerName) { this.ledgerName = ledgerName; }
    
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    
    public String getDebtorCreditor() { return debtorCreditor; }
    public void setDebtorCreditor(String debtorCreditor) { this.debtorCreditor = debtorCreditor; }
    
    public String getHousePlotNo() { return housePlotNo; }
    public void setHousePlotNo(String housePlotNo) { this.housePlotNo = housePlotNo; }
    
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    
    public String getVillagePost() { return villagePost; }
    public void setVillagePost(String villagePost) { this.villagePost = villagePost; }
    
    public String getMandalTaluq() { return mandalTaluq; }
    public void setMandalTaluq(String mandalTaluq) { this.mandalTaluq = mandalTaluq; }
    
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    
    public String getPinCode() { return pinCode; }
    public void setPinCode(String pinCode) { this.pinCode = pinCode; }
    
    public String getContactPersonName() { return contactPersonName; }
    public void setContactPersonName(String contactPersonName) { this.contactPersonName = contactPersonName; }
    
    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    
    public String getGstin() { return gstin; }
    public void setGstin(String gstin) { this.gstin = gstin; }
    
    public String getPan() { return pan; }
    public void setPan(String pan) { this.pan = pan; }
    
    public String getBankAccountNo() { return bankAccountNo; }
    public void setBankAccountNo(String bankAccountNo) { this.bankAccountNo = bankAccountNo; }
    
    public String getIfscCode() { return ifscCode; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }
    
    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }
    
    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
