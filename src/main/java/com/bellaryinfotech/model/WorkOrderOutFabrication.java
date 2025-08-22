package com.bellaryinfotech.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "work_order_out_fabrication")
public class WorkOrderOutFabrication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
     
    @Column(name = "client_name")
    private String clientName;
    
    @Column(name = "work_order")
    private String workOrder;
    
    @Column(name = "service_description")
    private String serviceDescription;
    
    @Column(name = "uom")
    private String uom;
    
    @Column(name = "data_module")
    private String dataModule;
    
    @Column(name = "drawing_no")
    private String drawingNo;
    
    @Column(name = "building_name")
    private String buildingName;
    
    @Column(name = "mark_no")
    private String markNo;
    
    @Column(name = "sub_agency_name")
    private String subAgencyName;
    
    @Column(name = "sub_agency_work_order_name")
    private String subAgencyWorkOrderName;
    
    
    @Column(name = "ra_no")
    private String raNo;
    
    @Column(name = "sub_agency_ra_no")
    private String subAgencyRaNo;
    
    
    @Column(name = "original_entry_id")
    private Long originalEntryId;
    
    @Column(name = "order_id")
    private Long orderId;
    
     
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
    
    @Column(name = "item_qty")
    private Integer itemQty;
    
    @Column(name = "item_weight", precision = 10, scale = 3)
    private BigDecimal itemWeight;
    
    @Column(name = "total_item_weight", precision = 10, scale = 3)
    private BigDecimal totalItemWeight;
    
     
    @Column(name = "cutting_stage")
    private Boolean cuttingStage = false;
    
    @Column(name = "fit_up_stage")
    private Boolean fitUpStage = false;
    
    @Column(name = "welding_stage")
    private Boolean weldingStage = false;
    
    @Column(name = "finishing_stage")
    private Boolean finishingStage = false;
    
    // Remarks and Status
    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;
    
    @Column(name = "status")
    private String status = "In Progress";
    
    // Edit Tracking
    @Column(name = "editable_enable")
    private String editableEnable = "original";
    
    // Audit Fields
    @Column(name = "tenant_id")
    private Integer tenantId = 1;
    
    @Column(name = "creation_date")
    private Timestamp creationDate;
    
    @Column(name = "created_by")
    private String createdBy = "system";
    
    @Column(name = "last_update_date")
    private Timestamp lastUpdateDate;
    
    @Column(name = "last_updated_by")
    private String lastUpdatedBy = "system";
    
    
    public WorkOrderOutFabrication() {}
    
    
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
        return "WorkOrderOutFabrication{" +
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
