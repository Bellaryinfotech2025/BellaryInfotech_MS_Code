package com.bellaryinfotech.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bits_po_entry_header")
public class BitsHeaderAll {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    
    @Column(name = "work_order")
    private String workOrder;
    
    @Column(name = "plant_location")
    private String plantLocation;
    
    @Column(name = "department")
    private String department;
    
    @Column(name = "work_location")
    private String workLocation;
    
    @Column(name = "work_order_date")
    private LocalDate workOrderDate;
    
    @Column(name = "completion_date")
    private LocalDate completionDate;
    
    @Column(name = "ld_applicable")
    private Boolean ldApplicable;
    
    // New columns
    @Column(name = "scrap_allowance_visible_percent")
    private String scrapAllowanceVisiblePercent;
    
    @Column(name = "scrap_allowance_invisible_percent")
    private String scrapAllowanceInvisiblePercent;
    
    @Column(name = "material_issue_type")
    private String materialIssueType;
    
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
    
    // NEW: Customer ID field
    @Column(name = "customer_id")
    private Long customerId;

    // Default constructor
    public BitsHeaderAll() {}

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder;
    }

    public String getPlantLocation() {
        return plantLocation;
    }

    public void setPlantLocation(String plantLocation) {
        this.plantLocation = plantLocation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
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

    public Boolean getLdApplicable() {
        return ldApplicable;
    }

    public void setLdApplicable(Boolean ldApplicable) {
        this.ldApplicable = ldApplicable;
    }
    
    // New getters and setters for the new fields
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

    public String getMaterialIssueType() {
        return materialIssueType;
    }

    public void setMaterialIssueType(String materialIssueType) {
        this.materialIssueType = materialIssueType;
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
    
    

    public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	// Helper method to convert LocalDateTime to Timestamp
    public void setCreationDateFromLocalDateTime(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            this.creationDate = Timestamp.valueOf(localDateTime);
        }
    }

    // Helper method to convert LocalDateTime to Timestamp
    public void setLastUpdateDateFromLocalDateTime(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            this.lastUpdateDate = Timestamp.valueOf(localDateTime);
        }
    }

    // Helper method to set createdBy from String
    public void setCreatedByFromString(String createdBy) {
        if (createdBy != null && !createdBy.isEmpty()) {
            try {
                this.createdBy = Long.parseLong(createdBy);
            } catch (NumberFormatException e) {
                this.createdBy = 1L; // Default value
            }
        } else {
            this.createdBy = 1L; // Default value
        }
    }

    // Helper method to set lastUpdatedBy from String
    public void setLastUpdatedByFromString(String lastUpdatedBy) {
        if (lastUpdatedBy != null && !lastUpdatedBy.isEmpty()) {
            try {
                this.lastUpdatedBy = Long.parseLong(lastUpdatedBy);
            } catch (NumberFormatException e) {
                this.lastUpdatedBy = 1L; // Default value
            }
        } else {
            this.lastUpdatedBy = 1L; // Default value
        }
    }
}