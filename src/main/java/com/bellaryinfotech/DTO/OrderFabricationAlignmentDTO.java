package com.bellaryinfotech.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderFabricationAlignmentDTO {
    private Long id;
    private String buildingName;
    private String drawingNo;
    private String drawingDescription;
    private String orderNumber;
    private Long orderId;
    private Long origLineNumber;
    private Long origLineId;
    private BigDecimal lineNumber;
    private Long lineId;
    private String erectionMkd;
    private String itemNo;
    private String section;
    private BigDecimal length;
    private String lengthUom;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private String unitPriceUom;
    private BigDecimal totalQuantity;
    private String totalQuantityUom;
    private BigDecimal originalQuantity;
    private BigDecimal repeatedQty;
    private String remark;
    private Integer tenantId;
    private LocalDate creationDate;
    private Long createdBy;
    private LocalDate lastUpdateDate;
    private Long lastUpdatedBy;
    private Long orgId;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
    private Long version;
    private String status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getDrawingDescription() {
		return drawingDescription;
	}
	public void setDrawingDescription(String drawingDescription) {
		this.drawingDescription = drawingDescription;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getOrigLineNumber() {
		return origLineNumber;
	}
	public void setOrigLineNumber(Long origLineNumber) {
		this.origLineNumber = origLineNumber;
	}
	public Long getOrigLineId() {
		return origLineId;
	}
	public void setOrigLineId(Long origLineId) {
		this.origLineId = origLineId;
	}
	public BigDecimal getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(BigDecimal lineNumber) {
		this.lineNumber = lineNumber;
	}
	public Long getLineId() {
		return lineId;
	}
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}
	public String getErectionMkd() {
		return erectionMkd;
	}
	public void setErectionMkd(String erectionMkd) {
		this.erectionMkd = erectionMkd;
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
	public BigDecimal getLength() {
		return length;
	}
	public void setLength(BigDecimal length) {
		this.length = length;
	}
	public String getLengthUom() {
		return lengthUom;
	}
	public void setLengthUom(String lengthUom) {
		this.lengthUom = lengthUom;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getUnitPriceUom() {
		return unitPriceUom;
	}
	public void setUnitPriceUom(String unitPriceUom) {
		this.unitPriceUom = unitPriceUom;
	}
	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public String getTotalQuantityUom() {
		return totalQuantityUom;
	}
	public void setTotalQuantityUom(String totalQuantityUom) {
		this.totalQuantityUom = totalQuantityUom;
	}
	public BigDecimal getOriginalQuantity() {
		return originalQuantity;
	}
	public void setOriginalQuantity(BigDecimal originalQuantity) {
		this.originalQuantity = originalQuantity;
	}
	public BigDecimal getRepeatedQty() {
		return repeatedQty;
	}
	public void setRepeatedQty(BigDecimal repeatedQty) {
		this.repeatedQty = repeatedQty;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getTenantId() {
		return tenantId;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
	public LocalDate getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public LocalDate getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(LocalDate lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setIsLatestStored(Object isLatestStored) {
		// TODO Auto-generated method stub
		
	}
	public void setStoredAt(Object storedAt) {
		// TODO Auto-generated method stub
		
	}

     
}
