package com.bellaryinfotech.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "bits_po_entry_lines")
public class BitsLinesAll {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_id")
    private Long lineId;
    
    // NEW: Foreign key reference to bits_po_entry_header
    @Column(name = "order_id", nullable = false)
    private Long orderId;
    
    // NEW: Sequential line number within each work order
    @Column(name = "line_number", nullable = false)
    private BigDecimal lineNumber;
    
    @Column(name = "ser_no")
    private String serNo;
    
    @Column(name = "service_code")
    private String serviceCode;
    
    @Column(name = "service_desc")
    private String serviceDesc;
    
    @Column(name = "qty")
    private BigDecimal qty;
    
    @Column(name = "uom")
    private String uom;
    
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    
    @Column(name = "tenant_id")
    private Integer tenantId;
    
    @Column(name = "creation_date")
    private Timestamp creationDate;
    
    @Column(name = "created_by")
    private Long createdBy;
    
    @Column(name = "last_update_date")
    private Timestamp lastUpdateDate;
    
    @Column(name = "last_updated_by")
    private Long lastUpdatedBy;
    
    @Column(name = "attribute1_v")
    private String attribute1V;
    
    @Column(name = "attribute2_v")
    private String attribute2V;
    
    @Column(name = "attribute3_v")
    private String attribute3V;
    
    @Column(name = "attribute4_v")
    private String attribute4V;
    
    @Column(name = "attribute5_v")
    private String attribute5V;
    
    @Column(name = "attribute1_n")
    private BigDecimal attribute1N;
    
    @Column(name = "attribute2_n")
    private BigDecimal attribute2N;
    
    @Column(name = "attribute3_n")
    private BigDecimal attribute3N;
    
    @Column(name = "attribute4_n")
    private BigDecimal attribute4N;
    
    @Column(name = "attribute5_n")
    private BigDecimal attribute5N;
    
    @Column(name = "attribute1_d")
    private LocalDate attribute1D;
    
    @Column(name = "attribute2_d")
    private LocalDate attribute2D;
    
    @Column(name = "attribute3_d")
    private LocalDate attribute3D;
    
    @Column(name = "attribute4_d")
    private LocalDate attribute4D;
    
    @Column(name = "attribute5_d")
    private LocalDate attribute5D;

    // Default constructor
    public BitsLinesAll() {}

    // NEW: Constructor with orderId and lineNumber
    public BitsLinesAll(Long orderId, BigDecimal lineNumber) {
        this.orderId = orderId;
        this.lineNumber = lineNumber;
    }

    // Getters and Setters
    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    // NEW: Order ID getter/setter
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(BigDecimal lineNumber) {
        this.lineNumber = lineNumber;
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

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getAttribute1V() {
        return attribute1V;
    }

    public void setAttribute1V(String attribute1V) {
        this.attribute1V = attribute1V;
    }

    public String getAttribute2V() {
        return attribute2V;
    }

    public void setAttribute2V(String attribute2V) {
        this.attribute2V = attribute2V;
    }

    public String getAttribute3V() {
        return attribute3V;
    }

    public void setAttribute3V(String attribute3V) {
        this.attribute3V = attribute3V;
    }

    public String getAttribute4V() {
        return attribute4V;
    }

    public void setAttribute4V(String attribute4V) {
        this.attribute4V = attribute4V;
    }

    public String getAttribute5V() {
        return attribute5V;
    }

    public void setAttribute5V(String attribute5V) {
        this.attribute5V = attribute5V;
    }

    public BigDecimal getAttribute1N() {
        return attribute1N;
    }

    public void setAttribute1N(BigDecimal attribute1N) {
        this.attribute1N = attribute1N;
    }

    public BigDecimal getAttribute2N() {
        return attribute2N;
    }

    public void setAttribute2N(BigDecimal attribute2N) {
        this.attribute2N = attribute2N;
    }

    public BigDecimal getAttribute3N() {
        return attribute3N;
    }

    public void setAttribute3N(BigDecimal attribute3N) {
        this.attribute3N = attribute3N;
    }

    public BigDecimal getAttribute4N() {
        return attribute4N;
    }

    public void setAttribute4N(BigDecimal attribute4N) {
        this.attribute4N = attribute4N;
    }

    public BigDecimal getAttribute5N() {
        return attribute5N;
    }

    public void setAttribute5N(BigDecimal attribute5N) {
        this.attribute5N = attribute5N;
    }

    public LocalDate getAttribute1D() {
        return attribute1D;
    }

    public void setAttribute1D(LocalDate attribute1D) {
        this.attribute1D = attribute1D;
    }

    public LocalDate getAttribute2D() {
        return attribute2D;
    }

    public void setAttribute2D(LocalDate attribute2D) {
        this.attribute2D = attribute2D;
    }

    public LocalDate getAttribute3D() {
        return attribute3D;
    }

    public void setAttribute3D(LocalDate attribute3D) {
        this.attribute3D = attribute3D;
    }

    public LocalDate getAttribute4D() {
        return attribute4D;
    }

    public void setAttribute4D(LocalDate attribute4D) {
        this.attribute4D = attribute4D;
    }

    public LocalDate getAttribute5D() {
        return attribute5D;
    }

    public void setAttribute5D(LocalDate attribute5D) {
        this.attribute5D = attribute5D;
    }
}