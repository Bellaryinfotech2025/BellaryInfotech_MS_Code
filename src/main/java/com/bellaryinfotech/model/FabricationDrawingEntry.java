package com.bellaryinfotech.model;
 

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fabrication_drawing_entry")
public class FabricationDrawingEntry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "work_order")
    private String workOrder;
    
    @Column(name = "building_name")
    private String buildingName;
    
    @Column(name = "drawing_no")
    private String drawingNo;
    
    @Column(name = "mark_no")
    private String markNo;
    
    @Column(name = "ra_no")
    private String raNo;
    
    @Column(name = "order_id")
    private Long orderId;
    
    @Column(name = "line_id")
    private Long lineId;
    
    @Column(name = "session_code")
    private String sessionCode;
    
    @Column(name = "session_name")
    private String sessionName;
    
    @Column(name = "marked_qty")
    private Integer markedQty;
    
    @Column(name = "total_marked_wgt")
    private Double totalMarkedWgt;

    @Column(name = "session_weight")
    private Double sessionWeight;

    @Column(name = "width")
    private Double width;

    @Column(name = "length")
    private Double length;

    @Column(name = "item_qty")
    private Integer itemQty;

    @Column(name = "item_weight")
    private Double itemWeight;

    @Column(name = "total_item_weight")
    private Double totalItemWeight;

    @Column(name = "cutting_stage")
    private String cuttingStage;

    @Column(name = "fit_up_stage")
    private String fitUpStage;

    @Column(name = "welding_stage")
    private String weldingStage;

    @Column(name = "finishing_stage")
    private String finishingStage;
    
    @Column(name = "created_by")
    private String createdBy;
    
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;
    
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
    
    @Column(name = "item_no")
    private String itemNo;
    
    // Constructors
    public FabricationDrawingEntry() {
        this.creationDate = LocalDateTime.now();
        this.lastUpdateDate = LocalDateTime.now();
    }
    
    public FabricationDrawingEntry(String workOrder, String buildingName, String drawingNo, 
                                 String markNo, String raNo, Long orderId, Long lineId,
                                 String sessionCode, String sessionName, Integer markedQty,
                                 Double totalMarkedWgt, Double sessionWeight, Double width,
                                 Double length, Integer itemQty, Double itemWeight,
                                 Double totalItemWeight, String cuttingStage, String fitUpStage,
                                 String weldingStage, String finishingStage, String createdBy, String lastUpdatedBy) {
        this();
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
    
    
    
    public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	@PreUpdate
    public void preUpdate() {
        this.lastUpdateDate = LocalDateTime.now();
    }
}
