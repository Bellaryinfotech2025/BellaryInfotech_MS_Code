package com.bellaryinfotech.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MtrFabModuleDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("workOrder")
    private String workOrder;
    

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("buildingName")
    private String buildingName;

    @JsonProperty("drawingNo")
    private String drawingNo;

    @JsonProperty("markNo")
    private String markNo;

    @JsonProperty("markQty")
    private Integer markQty;

    @JsonProperty("eachMarkLength")
    private BigDecimal eachMarkLength;

    @JsonProperty("totalMarkLength")
    private BigDecimal totalMarkLength;

    @JsonProperty("raNo")
    private String raNo;

    @JsonProperty("itemNo")
    private String itemNo;

    @JsonProperty("section")
    private String section;

    @JsonProperty("lengthMm")
    private BigDecimal lengthMm;

    @JsonProperty("itemQty")
    private Integer itemQty;

    @JsonProperty("totalLength")
    private BigDecimal totalLength;

    @JsonProperty("remarks")
    private String remarks;

    @JsonProperty("cuttingStage")
    private String cuttingStage;

    @JsonProperty("fitUpStage")
    private String fitUpStage;

    @JsonProperty("weldingStage")
    private String weldingStage;

    @JsonProperty("finishingStage")
    private String finishingStage;

    // NEW: Add serviceDescription, uom, dataModule properties
    @JsonProperty("serviceDescription")
    private String serviceDescription;

    @JsonProperty("uom")
    private String uom;

    @JsonProperty("dataModule")
    private String dataModule;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("createdDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonProperty("lastUpdatedBy")
    private String lastUpdatedBy;

    @JsonProperty("lastUpdatedDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdatedDate;

    @JsonProperty("status")
    private String status;

    // Default constructor
    public MtrFabModuleDto() {}

    // Constructor with essential fields
    public MtrFabModuleDto(String workOrder, String buildingName, String drawingNo, String markNo, String raNo) {
        this.workOrder = workOrder;
        this.buildingName = buildingName;
        this.drawingNo = drawingNo;
        this.markNo = markNo;
        this.raNo = raNo;
        this.status = "ACTIVE";
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

    

    public Integer getItemQty() {
        return itemQty;
    }

    public void setItemQty(Integer itemQty) {
        this.itemQty = itemQty;
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

    // NEW: Getters and Setters for serviceDescription, uom, dataModule
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

	public BigDecimal getLengthMm() {
		return lengthMm;
	}

	public void setLengthMm(BigDecimal lengthMm) {
		this.lengthMm = lengthMm;
	}

	public BigDecimal getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(BigDecimal totalLength) {
		this.totalLength = totalLength;
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

	@Override
    public String toString() {
        return "MtrFabModuleDto{" +
                "id=" + id +
                ", workOrder='" + workOrder + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", drawingNo='" + drawingNo + '\'' +
                ", markNo='" + markNo + '\'' +
                ", raNo='" + raNo + '\'' +
                ", itemNo='" + itemNo + '\'' +
                ", section='" + section + '\'' +
                ", serviceDescription='" + serviceDescription + '\'' +
                ", uom='" + uom + '\'' +
                ", dataModule='" + dataModule + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
