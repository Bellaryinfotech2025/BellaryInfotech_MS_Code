package com.bellaryinfotech.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class WorkOrderOutDrawingEntryDto {
    
    private Long id;
    private Long orderId;
    private String workOrder;
    private String subAgencyName;
    private String subAgencyWorkOrderName;
    private String plantLocation;
    private String department;
    private String workLocation;
    private String lineNumber;
    private String drawingNo;
    private LocalDate drawingReceivedDate;
    private LocalDate targetDate;
    private String markNo;
    private BigDecimal markWeight;
    private Integer markQty;
    private BigDecimal totalMarkWeight;
    
    // BOM Entry fields
    private String itemNo;
    private String sectionCode;
    private String sectionName;
    private BigDecimal width;
    private BigDecimal length;
    private BigDecimal secWeight;
    private BigDecimal itemWeight;
    private Integer itemQty;
    private BigDecimal totalItemWeight;
    
    // Default constructor
    public WorkOrderOutDrawingEntryDto() {}
    
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
}
