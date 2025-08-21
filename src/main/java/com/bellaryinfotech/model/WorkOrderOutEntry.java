package com.bellaryinfotech.model;
 
 

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_order_out_entry")
public class WorkOrderOutEntry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Header Information (repeated for each service line)
    @Column(name = "order_id")
    private Long orderId; // From bits_po_entry_header
    
    @Column(name = "client_name")
    private String clientName;
    
    @Column(name = "reference_work_order")
    private String referenceWorkOrder;
    
    @Column(name = "work_location")
    private String workLocation;
    
    @Column(name = "sub_agency_name")
    private String subAgencyName;
    
    @Column(name = "sub_agency_number")
    private String subAgencyNumber;
    
    @Column(name = "sub_agency_work_order_name")
    private String subAgencyWorkOrderName;
    
    @Column(name = "work_order_date")
    private LocalDate workOrderDate;
    
    @Column(name = "completion_date")
    private LocalDate completionDate;
    
    @Column(name = "material_type")
    private String materialType;
    
    @Column(name = "scrap_allowance_visible_percent")
    private String scrapAllowanceVisiblePercent;
    
    @Column(name = "scrap_allowance_invisible_percent")
    private String scrapAllowanceInvisiblePercent;
    
    @Column(name = "ld_applicable")
    private Boolean ldApplicable;
    
    @Column(name = "gst_selection")
    private String gstSelection;
    
    @Column(name = "rcm_applicable")
    private Boolean rcmApplicable;
    
    // Service Line Information (specific to each row)
    @Column(name = "ser_no")
    private String serNo;
    
    @Column(name = "service_code")
    private String serviceCode;
    
    @Column(name = "service_desc")
    private String serviceDesc;
    
    @Column(name = "qty")
    private String qty;
    
    @Column(name = "uom")
    private String uom;
    
    @Column(name = "unit_price")
    private String unitPrice;
    
    @Column(name = "total_price")
    private String totalPrice;
    
    // GST Calculation (same for all rows of same work order)
    @Column(name = "subtotal")
    private Double subtotal;
    
    @Column(name = "cgst_rate")
    private Double cgstRate;
    
    @Column(name = "sgst_rate")
    private Double sgstRate;
    
    @Column(name = "igst_rate")
    private Double igstRate;
    
    @Column(name = "cgst_amount")
    private Double cgstAmount;
    
    @Column(name = "sgst_amount")
    private Double sgstAmount;
    
    @Column(name = "igst_amount")
    private Double igstAmount;
    
    @Column(name = "total_amount")
    private Double totalAmount;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
    
    // Constructors
    public WorkOrderOutEntry() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }
    
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
    
    public String getSerNo() {
        return serNo;
    }
    
    public void setSerNo(String serNo) {
        this.serNo = serNo;
    }
    
    public String getServiceCode() {
        return serviceCode;
    }
    
    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
    
    public String getServiceDesc() {
        return serviceDesc;
    }
    
    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }
    
    public String getQty() {
        return qty;
    }
    
    public void setQty(String qty) {
        this.qty = qty;
    }
    
    public String getUom() {
        return uom;
    }
    
    public void setUom(String uom) {
        this.uom = uom;
    }
    
    public String getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public String getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
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
    
    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

	public Boolean isLdApplicable() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean isRcmApplicable() {
		// TODO Auto-generated method stub
		return null;
	}
 
}
