package com.bellaryinfotech.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "square_meter_fab_module")
public class SquareMeterFabModule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "work_order", length = 100)
    private String workOrder;

    @Column(name = "building_name", length = 200)
    private String buildingName;

    @Column(name = "drawing_no", length = 100)
    private String drawingNo;

    @Column(name = "mark_no", length = 100)
    private String markNo;

    @Column(name = "mark_qty")
    private Integer markQty;

    @Column(name = "each_mark_length", precision = 10, scale = 3)
    private BigDecimal eachMarkLength;

    @Column(name = "total_mark_length", precision = 10, scale = 3)
    private BigDecimal totalMarkLength;

    @Column(name = "ra_no", length = 100)
    private String raNo;

    @Column(name = "item_no", length = 100)
    private String itemNo;

    @Column(name = "section", length = 100)
    private String section;

    @Column(name = "length_mm", precision = 10, scale = 3)
    private BigDecimal lengthMm;

    @Column(name = "width_mm", precision = 10, scale = 3)
    private BigDecimal widthMm;

    @Column(name = "item_qty")
    private Integer itemQty;

    @Column(name = "total_area", precision = 10, scale = 3)
    private BigDecimal totalArea;

    @Column(name = "remarks", length = 500)
    private String remarks;

    @Column(name = "cutting_stage", length = 1, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
    private String cuttingStage = "N";

    @Column(name = "fit_up_stage", length = 1, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
    private String fitUpStage = "N";

    @Column(name = "welding_stage", length = 1, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
    private String weldingStage = "N";

    @Column(name = "finishing_stage", length = 1, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
    private String finishingStage = "N";

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_updated_by", length = 100)
    private String lastUpdatedBy;

    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    @Column(name = "status", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'ACTIVE'")
    private String status = "ACTIVE";

    @Column(name = "service_description", length = 255)
    private String serviceDescription;

    @Column(name = "uom", length = 50)
    private String uom;

    @Column(name = "data_module", length = 100)
    private String dataModule;

    // Default constructor
    public SquareMeterFabModule() {
        this.createdDate = LocalDateTime.now();
        this.lastUpdatedDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder;
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

    public BigDecimal getEachMarkLength() {
        return eachMarkLength;
    }

    public void setEachMarkLength(BigDecimal eachMarkLength) {
        this.eachMarkLength = eachMarkLength;
    }

    public BigDecimal getTotalMarkLength() {
        return totalMarkLength;
    }

    public void setTotalMarkLength(BigDecimal totalMarkLength) {
        this.totalMarkLength = totalMarkLength;
    }

    public String getRaNo() {
        return raNo;
    }

    public void setRaNo(String raNo) {
        this.raNo = raNo;
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

    public BigDecimal getLengthMm() {
        return lengthMm;
    }

    public void setLengthMm(BigDecimal lengthMm) {
        this.lengthMm = lengthMm;
    }

    public BigDecimal getWidthMm() {
        return widthMm;
    }

    public void setWidthMm(BigDecimal widthMm) {
        this.widthMm = widthMm;
    }

    public Integer getItemQty() {
        return itemQty;
    }

    public void setItemQty(Integer itemQty) {
        this.itemQty = itemQty;
    }

    public BigDecimal getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(BigDecimal totalArea) {
        this.totalArea = totalArea;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCuttingStage() {
        return cuttingStage;
    }

    public void setCuttingStage(String cuttingStage) {
        this.cuttingStage = cuttingStage;
    }

    public String getFitUpStage() {
        return fitUpStage;
    }

    public void setFitUpStage(String fitUpStage) {
        this.fitUpStage = fitUpStage;
    }

    public String getWeldingStage() {
        return weldingStage;
    }

    public void setWeldingStage(String weldingStage) {
        this.weldingStage = weldingStage;
    }

    public String getFinishingStage() {
        return finishingStage;
    }

    public void setFinishingStage(String finishingStage) {
        this.finishingStage = finishingStage;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @PreUpdate
    public void preUpdate() {
        this.lastUpdatedDate = LocalDateTime.now();
    }
}
