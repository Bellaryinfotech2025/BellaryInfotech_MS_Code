package com.bellaryinfotech.DTO;
 
 

import java.time.LocalDateTime;

public class FabricationDrawingEntryDto {
    
    private Long id;
    private String workOrder;
    private String buildingName;
    private String drawingNo;
    private String markNo;
    private String raNo;
    private Long orderId;
    private Long lineId;
    private String sessionCode;
    private String sessionName;
    private Integer markedQty;
    private Double totalMarkedWgt;
    private Double sessionWeight;
    private Double width;
    private Double length;
    private Integer itemQty;
    private Double itemWeight;
    private Double totalItemWeight;
    private String cuttingStage;
    private String fitUpStage;
    private String weldingStage;
    private String finishingStage;
    private String createdBy;
    private String lastUpdatedBy;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;
    
    // Constructors
    public FabricationDrawingEntryDto() {}
    
    public FabricationDrawingEntryDto(String workOrder, String buildingName, String drawingNo, 
                                    String markNo, String raNo, Long orderId, Long lineId,
                                    String sessionCode, String sessionName, Integer markedQty,
                                    Double totalMarkedWgt, Double sessionWeight, Double width,
                                    Double length, Integer itemQty, Double itemWeight, 
                                    Double totalItemWeight, String cuttingStage, String fitUpStage,
                                    String weldingStage, String finishingStage, String createdBy, String lastUpdatedBy) {
        this.workOrder = workOrder;
        this.buildingName = buildingName;
        this.drawingNo = drawingNo;
        this.markNo = markNo;
        this.raNo = raNo;
        this.orderId = orderId;
        this.lineId = lineId;
        this.sessionCode = sessionCode;
        this.sessionName = sessionName;
        this.markedQty = markedQty;
        this.totalMarkedWgt = totalMarkedWgt;
        this.sessionWeight = sessionWeight;
        this.width = width;
        this.length = length;
        this.itemQty = itemQty;
        this.itemWeight = itemWeight;
        this.totalItemWeight = totalItemWeight;
        this.cuttingStage = cuttingStage;
        this.fitUpStage = fitUpStage;
        this.weldingStage = weldingStage;
        this.finishingStage = finishingStage;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
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
    
    public String getRaNo() {
        return raNo;
    }
    
    public void setRaNo(String raNo) {
        this.raNo = raNo;
    }
    
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    public Long getLineId() {
        return lineId;
    }
    
    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }
    
    public String getSessionCode() {
        return sessionCode;
    }
    
    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }
    
    public String getSessionName() {
        return sessionName;
    }
    
    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }
    
    public Integer getMarkedQty() {
        return markedQty;
    }
    
    public void setMarkedQty(Integer markedQty) {
        this.markedQty = markedQty;
    }
    
    public Double getTotalMarkedWgt() {
        return totalMarkedWgt;
    }
    
    public void setTotalMarkedWgt(Double totalMarkedWgt) {
        this.totalMarkedWgt = totalMarkedWgt;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    
    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }
    
    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Double getSessionWeight() {
        return sessionWeight;
    }

    public void setSessionWeight(Double sessionWeight) {
        this.sessionWeight = sessionWeight;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Integer getItemQty() {
        return itemQty;
    }

    public void setItemQty(Integer itemQty) {
        this.itemQty = itemQty;
    }

    public Double getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(Double itemWeight) {
        this.itemWeight = itemWeight;
    }

    public Double getTotalItemWeight() {
        return totalItemWeight;
    }

    public void setTotalItemWeight(Double totalItemWeight) {
        this.totalItemWeight = totalItemWeight;
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
}
