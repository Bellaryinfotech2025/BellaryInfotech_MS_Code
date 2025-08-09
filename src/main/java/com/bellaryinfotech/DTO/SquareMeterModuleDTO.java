package com.bellaryinfotech.DTO;
 

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SquareMeterModuleDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("workOrder")
    private String workOrder;

    @JsonProperty("serviceDescription")
    private String serviceDescription;

    @JsonProperty("uom")
    private String uom;

    @JsonProperty("department")
    private String department;

    @JsonProperty("workLocation")
    private String workLocation;

    @JsonProperty("dataModule")
    private String dataModule;

    @JsonProperty("buildingName")
    private String buildingName;

    @JsonProperty("drawingNo")
    private String drawingNo;

    @JsonProperty("drawingReceivedDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate drawingReceivedDate;

    @JsonProperty("targetDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;

    @JsonProperty("markNo")
    private String markNo;

    @JsonProperty("markQty")
    private Integer markQty;

    @JsonProperty("eachMarkLength")
    private Double eachMarkLength;

    @JsonProperty("totalMarkLength")
    private Double totalMarkLength;

    @JsonProperty("itemNo")
    private String itemNo;

    @JsonProperty("section")
    private String section;

    @JsonProperty("lengthMm")
    private Double lengthMm;

    @JsonProperty("widthMm")
    private Double widthMm;

    @JsonProperty("itemQty")
    private Integer itemQty;

    @JsonProperty("totalArea")
    private Double totalArea;

    @JsonProperty("remarks")
    private String remarks;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("createdDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonProperty("updatedDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDate;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;

    // Default constructor
    public SquareMeterModuleDTO() {}

    // Constructor with all fields
    public SquareMeterModuleDTO(Long id, Long orderId, String clientName, String workOrder,
                               String serviceDescription, String uom, String department,
                               String workLocation, String dataModule, String buildingName,
                               String drawingNo, LocalDate drawingReceivedDate, LocalDate targetDate,
                               String markNo, Integer markQty, Double eachMarkLength,
                               Double totalMarkLength, String itemNo, String section,
                               Double lengthMm, Double widthMm, Integer itemQty,
                               Double totalArea, String remarks, String tenantId,
                               LocalDateTime createdDate, LocalDateTime updatedDate,
                               String createdBy, String updatedBy) {
        this.id = id;
        this.orderId = orderId;
        this.clientName = clientName;
        this.workOrder = workOrder;
        this.serviceDescription = serviceDescription;
        this.uom = uom;
        this.department = department;
        this.workLocation = workLocation;
        this.dataModule = dataModule;
        this.buildingName = buildingName;
        this.drawingNo = drawingNo;
        this.drawingReceivedDate = drawingReceivedDate;
        this.targetDate = targetDate;
        this.markNo = markNo;
        this.markQty = markQty;
        this.eachMarkLength = eachMarkLength;
        this.totalMarkLength = totalMarkLength;
        this.itemNo = itemNo;
        this.section = section;
        this.lengthMm = lengthMm;
        this.widthMm = widthMm;
        this.itemQty = itemQty;
        this.totalArea = totalArea;
        this.remarks = remarks;
        this.tenantId = tenantId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
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

    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
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

    public String getDataModule() {
        return dataModule;
    }

    public void setDataModule(String dataModule) {
        this.dataModule = dataModule;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
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

    public Integer getMarkQty() {
        return markQty;
    }

    public void setMarkQty(Integer markQty) {
        this.markQty = markQty;
    }

    public Double getEachMarkLength() {
        return eachMarkLength;
    }

    public void setEachMarkLength(Double eachMarkLength) {
        this.eachMarkLength = eachMarkLength;
    }

    public Double getTotalMarkLength() {
        return totalMarkLength;
    }

    public void setTotalMarkLength(Double totalMarkLength) {
        this.totalMarkLength = totalMarkLength;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Double getLengthMm() {
        return lengthMm;
    }

    public void setLengthMm(Double lengthMm) {
        this.lengthMm = lengthMm;
    }

    public Double getWidthMm() {
        return widthMm;
    }

    public void setWidthMm(Double widthMm) {
        this.widthMm = widthMm;
    }

    public Integer getItemQty() {
        return itemQty;
    }

    public void setItemQty(Integer itemQty) {
        this.itemQty = itemQty;
    }

    public Double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Double totalArea) {
        this.totalArea = totalArea;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "SquareMeterModuleDTO{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", clientName='" + clientName + '\'' +
                ", workOrder='" + workOrder + '\'' +
                ", serviceDescription='" + serviceDescription + '\'' +
                ", uom='" + uom + '\'' +
                ", department='" + department + '\'' +
                ", workLocation='" + workLocation + '\'' +
                ", dataModule='" + dataModule + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", drawingNo='" + drawingNo + '\'' +
                ", drawingReceivedDate=" + drawingReceivedDate +
                ", targetDate=" + targetDate +
                ", markNo='" + markNo + '\'' +
                ", markQty=" + markQty +
                ", eachMarkLength=" + eachMarkLength +
                ", totalMarkLength=" + totalMarkLength +
                ", itemNo='" + itemNo + '\'' +
                ", section='" + section + '\'' +
                ", lengthMm=" + lengthMm +
                ", widthMm=" + widthMm +
                ", itemQty=" + itemQty +
                ", totalArea=" + totalArea +
                ", remarks='" + remarks + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
