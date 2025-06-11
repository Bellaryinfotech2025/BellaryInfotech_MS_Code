package com.bellaryinfotech.model;
 

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ledger_creation")
public class LedgerCreation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ledger_name", nullable = false)
    private String ledgerName;
    
    @Column(name = "group_name")
    private String groupName;
    
    @Column(name = "debtor_creditor")
    private String debtorCreditor;
    
    @Column(name = "house_plot_no")
    private String housePlotNo;
    
    @Column(name = "street")
    private String street;
    
    @Column(name = "village_post")
    private String villagePost;
    
    @Column(name = "mandal_taluq")
    private String mandalTaluq;
    
    @Column(name = "district")
    private String district;
    
    @Column(name = "state")
    private String state;
    
    @Column(name = "pin_code")
    private String pinCode;
    
    @Column(name = "contact_person_name")
    private String contactPersonName;
    
    @Column(name = "mobile_no")
    private String mobileNo;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "website")
    private String website;
    
    @Column(name = "gstin")
    private String gstin;
    
    @Column(name = "pan")
    private String pan;
    
    @Column(name = "bank_account_no")
    private String bankAccountNo;
    
    @Column(name = "ifsc_code")
    private String ifscCode;
    
    @Column(name = "branch_name")
    private String branchName;
    
    @Column(name = "service_type")
    private String serviceType;
    
    @Column(name = "status")
    private String status = "ACTIVE";
    
    @Column(name = "created_at")
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
    public LedgerCreation() {}
    
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
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
