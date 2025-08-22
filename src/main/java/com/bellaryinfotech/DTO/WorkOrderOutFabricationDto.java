package com.bellaryinfotech.DTO;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class WorkOrderOutFabricationDto {
    
    private Long id;
    
   
    private String clientName;
    private String workOrder;
    private String serviceDescription;
    private String uom;
    private String dataModule;
    private String drawingNo;
    private String buildingName;
    private String markNo;
    private String subAgencyName;
    private String subAgencyWorkOrderName;
    
     
    private String raNo;
    private String subAgencyRaNo;
    
     
    private Long originalEntryId;
    private Long orderId;
    
    
    private String itemNo;
    private String sectionCode;
    private String sectionName;
    private BigDecimal width;
    private BigDecimal length;
    private Integer itemQty;
    private BigDecimal itemWeight;
    private BigDecimal totalItemWeight;
    
    
    private Boolean cuttingStage = false;
    private Boolean fitUpStage = false;
    private Boolean weldingStage = false;
    private Boolean finishingStage = false;
    
    
    private String remarks;
    private String status = "In Progress";
    
    
    private String editableEnable = "original";
    
    
    private Integer tenantId = 1;
    private Timestamp creationDate;
    private String createdBy = "system";
    private Timestamp lastUpdateDate;
    private String lastUpdatedBy = "system";
    
   
    public WorkOrderOutFabricationDto() {}
    
   
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public String getDataModule() {
        return dataModule;
    }
    
    public void setDataModule(String dataModule) {
        this.dataModule = dataModule;
    }
    
    public String getDrawingNo() {
        return drawingNo;
    }
    
    public void setDrawingNo(String drawingNo) {
        this.drawingNo = drawingNo;
    }
    
    public String getBuildingName() {
        return buildingName;
    }
    
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
    
    public String getMarkNo() {
        return markNo;
    }
    
    public void setMarkNo(String markNo) {
        this.markNo = markNo;
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
    
    public String getRaNo() {
        return raNo;
    }
    
    public void setRaNo(String raNo) {
        this.raNo = raNo;
    }
    
    public String getSubAgencyRaNo() {
        return subAgencyRaNo;
    }
    
    public void setSubAgencyRaNo(String subAgencyRaNo) {
        this.subAgencyRaNo = subAgencyRaNo;
    }
    
    public Long getOriginalEntryId() {
        return originalEntryId;
    }
    
    public void setOriginalEntryId(Long originalEntryId) {
        this.originalEntryId = originalEntryId;
    }
    
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
    
    public Integer getItemQty() {
        return itemQty;
    }
    
    public void setItemQty(Integer itemQty) {
        this.itemQty = itemQty;
    }
    
    public BigDecimal getItemWeight() {
        return itemWeight;
    }
    
    public void setItemWeight(BigDecimal itemWeight) {
        this.itemWeight = itemWeight;
    }
    
    public BigDecimal getTotalItemWeight() {
        return totalItemWeight;
    }
    
    public void setTotalItemWeight(BigDecimal totalItemWeight) {
        this.totalItemWeight = totalItemWeight;
    }
    
    public Boolean getCuttingStage() {
        return cuttingStage;
    }
    
    public void setCuttingStage(Boolean cuttingStage) {
        this.cuttingStage = cuttingStage;
    }
    
    public Boolean getFitUpStage() {
        return fitUpStage;
    }
    
    public void setFitUpStage(Boolean fitUpStage) {
        this.fitUpStage = fitUpStage;
    }
    
    public Boolean getWeldingStage() {
        return weldingStage;
    }
    
    public void setWeldingStage(Boolean weldingStage) {
        this.weldingStage = weldingStage;
    }
    
    public Boolean getFinishingStage() {
        return finishingStage;
    }
    
    public void setFinishingStage(Boolean finishingStage) {
        this.finishingStage = finishingStage;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getEditableEnable() {
        return editableEnable;
    }
    
    public void setEditableEnable(String editableEnable) {
        this.editableEnable = editableEnable;
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
    
    @Override
    public String toString() {
        return "WorkOrderOutFabricationDto{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", workOrder='" + workOrder + '\'' +
                ", orderId=" + orderId +
                ", drawingNo='" + drawingNo + '\'' +
                ", markNo='" + markNo + '\'' +
                ", raNo='" + raNo + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
