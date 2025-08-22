package com.bellaryinfotech.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "work_order_out_drawing_entry")
public class WorkOrderOutDrawingEntry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "order_id")
    private Long orderId;
    
    @Column(name = "work_order")
    private String workOrder;
    
    @Column(name = "sub_agency_name")
    private String subAgencyName;
    
    @Column(name = "sub_agency_work_order_name")
    private String subAgencyWorkOrderName;
    
    @Column(name = "plant_location")
    private String plantLocation;
    
    @Column(name = "department")
    private String department;
    
    @Column(name = "work_location")
    private String workLocation;
    
    @Column(name = "line_number")
    private String lineNumber;
    
    @Column(name = "drawing_no")
    private String drawingNo;
    
    @Column(name = "drawing_received_date")
    private LocalDate drawingReceivedDate;
    
    @Column(name = "target_date")
    private LocalDate targetDate;
    
    @Column(name = "mark_no")
    private String markNo;
    
    @Column(name = "mark_weight", precision = 10, scale = 3)
    private BigDecimal markWeight;
    
    @Column(name = "mark_qty")
    private Integer markQty;
    
    @Column(name = "total_mark_weight", precision = 10, scale = 3)
    private BigDecimal totalMarkWeight;
    
    // BOM Entry fields - duplicated for each BOM row
    @Column(name = "item_no")
    private String itemNo;
    
    @Column(name = "section_code")
    private String sectionCode;
    
    @Column(name = "section_name")
    private String sectionName;
    
    @Column(name = "width", precision = 10, scale = 2)
    private BigDecimal width;
    
    @Column(name = "length", precision = 10, scale = 2)
    private BigDecimal length;
    
    @Column(name = "sec_weight", precision = 10, scale = 3)
    private BigDecimal secWeight;
    
    @Column(name = "item_weight", precision = 10, scale = 3)
    private BigDecimal itemWeight;
    
    @Column(name = "item_qty")
    private Integer itemQty;
    
    @Column(name = "total_item_weight", precision = 10, scale = 3)
    private BigDecimal totalItemWeight;
    
    // Audit fields
    @Column(name = "tenant_id")
    private Integer tenantId = 1;
    
    @Column(name = "creation_date")
    private Timestamp creationDate;
    
    @Column(name = "created_by")
    private String createdBy;
    
    @Column(name = "last_update_date")
    private Timestamp lastUpdateDate;
    
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;
    
    @Column(name="status")
    private String status;
    
    // Default constructor
    public WorkOrderOutDrawingEntry() {}
    
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
    
    public String getWorkOrder() {
        return workOrder;
    }
    
    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder;
    }
    
    public String getSubAgencyName() {
        return subAgencyName;
    }
    
    public void setSubAgencyName(String subAgencyName) {
        this.subAgencyName = subAgencyName;
    }
    
    public String getSubAgencyWorkOrderName() {
        return subAgencyWorkOrderName;
    }
    
    public void setSubAgencyWorkOrderName(String subAgencyWorkOrderName) {
        this.subAgencyWorkOrderName = subAgencyWorkOrderName;
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
    
    public String getLineNumber() {
        return lineNumber;
    }
    
    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }
    
    public String getDrawingNo() {
        return drawingNo;
    }
    
    public void setDrawingNo(String drawingNo) {
        this.drawingNo = drawingNo;
    }
    
    public LocalDate getDrawingReceivedDate() {
        return drawingReceivedDate;
    }
    
    public void setDrawingReceivedDate(LocalDate drawingReceivedDate) {
        this.drawingReceivedDate = drawingReceivedDate;
    }
    
    public LocalDate getTargetDate() {
        return targetDate;
    }
    
    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }
    
    public String getMarkNo() {
        return markNo;
    }
    
    public void setMarkNo(String markNo) {
        this.markNo = markNo;
    }
    
    public BigDecimal getMarkWeight() {
        return markWeight;
    }
    
    public void setMarkWeight(BigDecimal markWeight) {
        this.markWeight = markWeight;
    }
    
    public Integer getMarkQty() {
        return markQty;
    }
    
    public void setMarkQty(Integer markQty) {
        this.markQty = markQty;
    }
    
    public BigDecimal getTotalMarkWeight() {
        return totalMarkWeight;
    }
    
    public void setTotalMarkWeight(BigDecimal totalMarkWeight) {
        this.totalMarkWeight = totalMarkWeight;
    }
    
    public String getItemNo() {
        return itemNo;
    }
    
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }
    
    public String getSectionCode() {
        return sectionCode;
    }
    
    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }
    
    public String getSectionName() {
        return sectionName;
    }
    
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
    
    public BigDecimal getWidth() {
        return width;
    }
    
    public void setWidth(BigDecimal width) {
        this.width = width;
    }
    
    public BigDecimal getLength() {
        return length;
    }
    
    public void setLength(BigDecimal length) {
        this.length = length;
    }
    
    public BigDecimal getSecWeight() {
        return secWeight;
    }
    
    public void setSecWeight(BigDecimal secWeight) {
        this.secWeight = secWeight;
    }
    
    public BigDecimal getItemWeight() {
        return itemWeight;
    }
    
    public void setItemWeight(BigDecimal itemWeight) {
        this.itemWeight = itemWeight;
    }
    
    public Integer getItemQty() {
        return itemQty;
    }
    
    public void setItemQty(Integer itemQty) {
        this.itemQty = itemQty;
    }
    
    public BigDecimal getTotalItemWeight() {
        return totalItemWeight;
    }
    
    public void setTotalItemWeight(BigDecimal totalItemWeight) {
        this.totalItemWeight = totalItemWeight;
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
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }
    
    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
