package com.bellaryinfotech.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "order_fabrication_erection")
public class OrderFabricationErection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "drawing_no")
    private String drawingNo;

    @Column(name = "drawing_description")
    private String drawingDescription;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "orig_line_number")
    private Long origLineNumber;

    @Column(name = "orig_line_id")
    private Long origLineId;

    @Column(name = "line_number", precision = 10, scale = 2)
    private BigDecimal lineNumber;

    @Column(name = "line_id")
    private Long lineId;

    @Column(name = "erection_mkd")
    private String erectionMkd;

    @Column(name = "item_no")
    private String itemNo;

    @Column(name = "section")
    private String section;

    @Column(name = "length", precision = 38, scale = 2)
    private BigDecimal length;

    @Column(name = "length_uom")
    private String lengthUom;

    @Column(name = "quantity", precision = 38, scale = 2)
    private BigDecimal quantity;

    @Column(name = "unit_price", precision = 38, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "unit_price_uom")
    private String unitPriceUom;

    @Column(name = "total_quantity", precision = 38, scale = 2)
    private BigDecimal totalQuantity;

    @Column(name = "total_quantity_uom")
    private String totalQuantityUom;

    @Column(name = "original_quantity", precision = 38, scale = 2)
    private BigDecimal originalQuantity;

    @Column(name = "repeated_qty", precision = 38, scale = 2)
    private BigDecimal repeatedQty;

    @Column(name = "remark")
    private String remark;

    @Column(name = "tenant_id")
    private Integer tenantId;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "last_update_date")
    private LocalDate lastUpdateDate;

    @Column(name = "last_updated_by")
    private Long lastUpdatedBy;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "version")
    private Long version;

    @Column(name = "status")
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

	public void setTenantId(Long tenantId2) {
		// TODO Auto-generated method stub
		
	}

	public void setCreationDate(Date creationDate2) {
		// TODO Auto-generated method stub
		
	}

	public void setLastUpdateDate(Date lastUpdateDate2) {
		// TODO Auto-generated method stub
		
	}
    
    
}
