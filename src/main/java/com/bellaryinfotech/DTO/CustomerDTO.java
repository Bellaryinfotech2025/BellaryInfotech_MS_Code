package com.bellaryinfotech.DTO;
import java.time.LocalDate;

import jakarta.persistence.Column;

public class CustomerDTO {
    private Long id;
    private String name;
    private String purchaseOrder;
    private String telNo;
    private String faxNo;
    private String poNo;
    private LocalDate poDate;
    private String type;
    @Column(name = "ld_applicable")
    private String ldApplicable;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPurchaseOrder() { return purchaseOrder; }
    public void setPurchaseOrder(String purchaseOrder) { this.purchaseOrder = purchaseOrder; }

    public String getTelNo() { return telNo; }
    public void setTelNo(String telNo) { this.telNo = telNo; }

    public String getFaxNo() { return faxNo; }
    public void setFaxNo(String faxNo) { this.faxNo = faxNo; }

    public String getPoNo() { return poNo; }
    public void setPoNo(String poNo) { this.poNo = poNo; }

    public LocalDate getPoDate() { return poDate; }
    public void setPoDate(LocalDate poDate) { this.poDate = poDate; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getLdApplicable() { return ldApplicable; }
    public void setLdApplicable(String ldApplicable) { this.ldApplicable = ldApplicable; }

}

