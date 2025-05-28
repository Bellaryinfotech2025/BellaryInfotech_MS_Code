package com.bellaryinfotech.model;
 

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "m_order_line_details_all")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lineId;

    private BigDecimal orderId;
    private BigDecimal origLineId;
    private BigDecimal batchId;
    private String buildingName;
    private String drawingNo;
    private String description;
    private String erectionMkd;
    private BigDecimal itemNo;
    private String section;
    private BigDecimal length;
    private String lengthUom;
    private BigDecimal unit;
    private String unitUom;
    private BigDecimal totalWt;
    private String totalWtUom;
    private BigDecimal qtyReqd;
    private BigDecimal erecMkdWt;
    private String erecMkdWtUom;
    private String remarks;
    private Date creationDate;
    private BigDecimal createdBy;
    private Date lastUpdateDate;
    private BigDecimal lastUpdatedBy;
    private String sourceId;
    private BigDecimal orgId;
    private BigDecimal tenantId;
    private String attributeContext;
	public Integer getLineId() {
		return lineId;
	}
	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}
	public BigDecimal getOrderId() {
		return orderId;
	}
	public void setOrderId(BigDecimal orderId) {
		this.orderId = orderId;
	}
	public BigDecimal getOrigLineId() {
		return origLineId;
	}
	public void setOrigLineId(BigDecimal origLineId) {
		this.origLineId = origLineId;
	}
	public BigDecimal getBatchId() {
		return batchId;
	}
	public void setBatchId(BigDecimal batchId) {
		this.batchId = batchId;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getErectionMkd() {
		return erectionMkd;
	}
	public void setErectionMkd(String erectionMkd) {
		this.erectionMkd = erectionMkd;
	}
	public BigDecimal getItemNo() {
		return itemNo;
	}
	public void setItemNo(BigDecimal itemNo) {
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
	public BigDecimal getUnit() {
		return unit;
	}
	public void setUnit(BigDecimal unit) {
		this.unit = unit;
	}
	public String getUnitUom() {
		return unitUom;
	}
	public void setUnitUom(String unitUom) {
		this.unitUom = unitUom;
	}
	public BigDecimal getTotalWt() {
		return totalWt;
	}
	public void setTotalWt(BigDecimal totalWt) {
		this.totalWt = totalWt;
	}
	public String getTotalWtUom() {
		return totalWtUom;
	}
	public void setTotalWtUom(String totalWtUom) {
		this.totalWtUom = totalWtUom;
	}
	public BigDecimal getQtyReqd() {
		return qtyReqd;
	}
	public void setQtyReqd(BigDecimal qtyReqd) {
		this.qtyReqd = qtyReqd;
	}
	public BigDecimal getErecMkdWt() {
		return erecMkdWt;
	}
	public void setErecMkdWt(BigDecimal erecMkdWt) {
		this.erecMkdWt = erecMkdWt;
	}
	public String getErecMkdWtUom() {
		return erecMkdWtUom;
	}
	public void setErecMkdWtUom(String erecMkdWtUom) {
		this.erecMkdWtUom = erecMkdWtUom;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public BigDecimal getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public BigDecimal getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(BigDecimal lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public BigDecimal getOrgId() {
		return orgId;
	}
	public void setOrgId(BigDecimal orgId) {
		this.orgId = orgId;
	}
	public BigDecimal getTenantId() {
		return tenantId;
	}
	public void setTenantId(BigDecimal tenantId) {
		this.tenantId = tenantId;
	}
	public String getAttributeContext() {
		return attributeContext;
	}
	public void setAttributeContext(String attributeContext) {
		this.attributeContext = attributeContext;
	}
    
    
}

