package com.bellaryinfotech.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class WorkOrderOutEntryDTO {
    
    private Long id;
    private Long orderId; // From bits_po_entry_header
    private String clientName;
    private String referenceWorkOrder;
    private String workLocation;
    private String subAgencyName;
    private String subAgencyNumber;
    private String subAgencyWorkOrderName;
    private LocalDate workOrderDate;
    private LocalDate completionDate;
    private String materialType;
    private String scrapAllowanceVisiblePercent;
    private String scrapAllowanceInvisiblePercent;
    private Boolean ldApplicable;
    private String gstSelection;
    private Boolean rcmApplicable;
    private Double subtotal;
    private Double cgstRate;
    private Double sgstRate;
    private Double igstRate;
    private Double cgstAmount;
    private Double sgstAmount;
    private Double igstAmount;
    private Double totalAmount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    
    // Service orders as generic list for frontend compatibility
    private List<Object> serviceOrders;
    
    // Constructors
    public WorkOrderOutEntryDTO() {}
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    public String getClientName() {
        return clientName;
    }
    
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    
    public String getReferenceWorkOrder() {
        return referenceWorkOrder;
    }
    
    public void setReferenceWorkOrder(String referenceWorkOrder) {
        this.referenceWorkOrder = referenceWorkOrder;
    }
    
    public String getWorkLocation() {
        return workLocation;
    }
    
    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }
    
    public String getSubAgencyName() {
        return subAgencyName;
    }
    
    public void setSubAgencyName(String subAgencyName) {
        this.subAgencyName = subAgencyName;
    }
    
    public String getSubAgencyNumber() {
        return subAgencyNumber;
    }
    
    public void setSubAgencyNumber(String subAgencyNumber) {
        this.subAgencyNumber = subAgencyNumber;
    }
    
    public String getSubAgencyWorkOrderName() {
        return subAgencyWorkOrderName;
    }
    
    public void setSubAgencyWorkOrderName(String subAgencyWorkOrderName) {
        this.subAgencyWorkOrderName = subAgencyWorkOrderName;
    }
    
    public LocalDate getWorkOrderDate() {
        return workOrderDate;
    }
    
    public void setWorkOrderDate(LocalDate workOrderDate) {
        this.workOrderDate = workOrderDate;
    }
    
    public LocalDate getCompletionDate() {
        return completionDate;
    }
    
    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }
    
    public String getMaterialType() {
        return materialType;
    }
    
    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }
    
    public String getScrapAllowanceVisiblePercent() {
        return scrapAllowanceVisiblePercent;
    }
    
    public void setScrapAllowanceVisiblePercent(String scrapAllowanceVisiblePercent) {
        this.scrapAllowanceVisiblePercent = scrapAllowanceVisiblePercent;
    }
    
    public String getScrapAllowanceInvisiblePercent() {
        return scrapAllowanceInvisiblePercent;
    }
    
    public void setScrapAllowanceInvisiblePercent(String scrapAllowanceInvisiblePercent) {
        this.scrapAllowanceInvisiblePercent = scrapAllowanceInvisiblePercent;
    }
    
    public Boolean getLdApplicable() {
        return ldApplicable;
    }
    
    public void setLdApplicable(Boolean ldApplicable) {
        this.ldApplicable = ldApplicable;
    }
    
    public String getGstSelection() {
        return gstSelection;
    }
    
    public void setGstSelection(String gstSelection) {
        this.gstSelection = gstSelection;
    }
    
    public Boolean getRcmApplicable() {
        return rcmApplicable;
    }
    
    public void setRcmApplicable(Boolean rcmApplicable) {
        this.rcmApplicable = rcmApplicable;
    }
    
    public Double getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
    
    public Double getCgstRate() {
        return cgstRate;
    }
    
    public void setCgstRate(Double cgstRate) {
        this.cgstRate = cgstRate;
    }
    
    public Double getSgstRate() {
        return sgstRate;
    }
    
    public void setSgstRate(Double sgstRate) {
        this.sgstRate = sgstRate;
    }
    
    public Double getIgstRate() {
        return igstRate;
    }
    
    public void setIgstRate(Double igstRate) {
        this.igstRate = igstRate;
    }
    
    public Double getCgstAmount() {
        return cgstAmount;
    }
    
    public void setCgstAmount(Double cgstAmount) {
        this.cgstAmount = cgstAmount;
    }
    
    public Double getSgstAmount() {
        return sgstAmount;
    }
    
    public void setSgstAmount(Double sgstAmount) {
        this.sgstAmount = sgstAmount;
    }
    
    public Double getIgstAmount() {
        return igstAmount;
    }
    
    public void setIgstAmount(Double igstAmount) {
        this.igstAmount = igstAmount;
    }
    
    public Double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }
    
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
    
    public List<Object> getServiceOrders() {
        return serviceOrders;
    }
    
    public void setServiceOrders(List<Object> serviceOrders) {
        this.serviceOrders = serviceOrders;
    }
}
