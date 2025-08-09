package com.bellaryinfotech.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "square_meter_module")
public class SquareMeterModule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "client_name", length = 255)
	private String clientName;

	@Column(name = "work_order", length = 255)
	private String workOrder;

	@Column(name = "service_description", length = 500)
	private String serviceDescription;

	@Column(name = "uom", length = 50)
	private String uom;

	@Column(name = "department", length = 255)
	private String department;

	@Column(name = "work_location", length = 255)
	private String workLocation;

	@Column(name = "data_module", length = 100)
	private String dataModule;

	@Column(name = "building_name", length = 255)
	private String buildingName;

	@Column(name = "drawing_no", length = 255)
	private String drawingNo;

	@Column(name = "drawing_received_date")
	private LocalDate drawingReceivedDate;

	@Column(name = "target_date")
	private LocalDate targetDate;

	@Column(name = "mark_no", length = 255)
	private String markNo;

	@Column(name = "mark_qty")
	private Integer markQty;

	@Column(name = "each_mark_length")
	private Double eachMarkLength;

	@Column(name = "total_mark_length")
	private Double totalMarkLength;

	@Column(name = "item_no", length = 255)
	private String itemNo;

	@Column(name = "section", length = 255)
	private String section;

	@Column(name = "length_mm")
	private Double lengthMm;

	@Column(name = "width_mm")
	private Double widthMm;

	@Column(name = "item_qty")
	private Integer itemQty;

	@Column(name = "total_area")
	private Double totalArea;

	@Column(name = "remarks", length = 500)
	private String remarks;

	@Column(name = "tenant_id", length = 100)
	private String tenantId;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	@Column(name = "created_by", length = 255)
	private String createdBy;

	@Column(name = "updated_by", length = 255)
	private String updatedBy;

    public SquareMeterModule() {
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

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
