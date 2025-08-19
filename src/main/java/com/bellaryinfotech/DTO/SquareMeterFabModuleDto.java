package com.bellaryinfotech.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SquareMeterFabModuleDto {
    
    private Long id;
    private Long orderId;
    private String clientName;
    private String workOrder;
    private String buildingName;
    private String drawingNo;
    private String markNo;
    private Integer markQty;
    private BigDecimal eachMarkLength;
    private BigDecimal totalMarkLength;
    private String raNo;
    private String itemNo;
    private String section;
    private BigDecimal lengthMm;
    private BigDecimal widthMm;
    private Integer itemQty;
    private BigDecimal totalArea;
    private String remarks;
    private String cuttingStage;
    private String fitUpStage;
    private String weldingStage;
    private String finishingStage;
    private String createdBy;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    
    private String lastUpdatedBy;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdatedDate;
    
    private String status;
    private String serviceDescription;
    private String uom;
    private String dataModule;

    // Default constructor
    public SquareMeterFabModuleDto() {}

    public SquareMeterFabModuleDto(Long id, Long orderId, String clientName, String workOrder, String buildingName,
            String drawingNo, String markNo, Integer markQty, BigDecimal eachMarkLength,
            BigDecimal totalMarkLength, String raNo, String itemNo, String section,
            BigDecimal lengthMm, BigDecimal widthMm, Integer itemQty, BigDecimal totalArea,
            String remarks, String cuttingStage, String fitUpStage, String weldingStage,
            String finishingStage, String createdBy, LocalDateTime createdDate,
            String lastUpdatedBy, LocalDateTime lastUpdatedDate, String status,
            String serviceDescription, String uom, String dataModule) {
this.id = id;
this.orderId = orderId;
this.clientName = clientName;
this.workOrder = workOrder;
this.buildingName = buildingName;
this.drawingNo = drawingNo;
this.markNo = markNo;
this.markQty = markQty;
this.eachMarkLength = eachMarkLength;
this.totalMarkLength = totalMarkLength;
this.raNo = raNo;
this.itemNo = itemNo;
this.section = section;
this.lengthMm = lengthMm;
this.widthMm = widthMm;
this.itemQty = itemQty;
this.totalArea = totalArea;
this.remarks = remarks;
this.cuttingStage = cuttingStage;
this.fitUpStage = fitUpStage;
this.weldingStage = weldingStage;
this.finishingStage = finishingStage;
this.createdBy = createdBy;
this.createdDate = createdDate;
this.lastUpdatedBy = lastUpdatedBy;
this.lastUpdatedDate = lastUpdatedDate;
this.status = status;
this.serviceDescription = serviceDescription;
this.uom = uom;
this.dataModule = dataModule;
}

    
    public Long getId() {
        return id;
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

    @Override
    public String toString() {
        return "SquareMeterFabModuleDto{" +
                "id=" + id +
                ", workOrder='" + workOrder + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", drawingNo='" + drawingNo + '\'' +
                ", markNo='" + markNo + '\'' +
                ", markQty=" + markQty +
                ", eachMarkLength=" + eachMarkLength +
                ", totalMarkLength=" + totalMarkLength +
                ", raNo='" + raNo + '\'' +
                ", itemNo='" + itemNo + '\'' +
                ", section='" + section + '\'' +
                ", lengthMm=" + lengthMm +
                ", widthMm=" + widthMm +
                ", itemQty=" + itemQty +
                ", totalArea=" + totalArea +
                ", remarks='" + remarks + '\'' +
                ", cuttingStage='" + cuttingStage + '\'' +
                ", fitUpStage='" + fitUpStage + '\'' +
                ", weldingStage='" + weldingStage + '\'' +
                ", finishingStage='" + finishingStage + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastUpdatedBy='" + lastUpdatedBy + '\'' +
                ", lastUpdatedDate=" + lastUpdatedDate +
                ", status='" + status + '\'' +
                ", serviceDescription='" + serviceDescription + '\'' +
                ", uom='" + uom + '\'' +
                ", dataModule='" + dataModule + '\'' +
                '}';
    }
}
