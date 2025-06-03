package com.bellaryinfotech.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;

public class BillingDrawingEntryDto {

    private String lineId;
    private Long version;
    private String drawingNo;
    private String markNo;
    private BigDecimal markedQty;
    private BigDecimal totalMarkedWgt;
    private String sessionCode;
    private String sessionName;
    private BigDecimal sessionWeight;
    private BigDecimal width;
    private BigDecimal length;
    private BigDecimal itemQty;
    private BigDecimal itemWeight;
    private String tenantId;
    private LocalDateTime creationDate;
    private String createdBy;
    private LocalDateTime lastUpdatingDate;
    private String lastUpdatedBy;
    private String attribute1V;
    private String attribute2V;
    private String attribute3V;
    private String attribute4V;
    private String attribute5V;
    private BigDecimal attribute1N;
    private BigDecimal attribute2N;
    private BigDecimal attribute3N;
    private BigDecimal attribute4N;
    private BigDecimal attribute5N;
    private LocalDate attribute1D;
    private LocalDate attribute2D;
    private LocalDate attribute3D;
    private LocalDate attribute4D;
    private LocalDate attribute5D;
    private BigDecimal poLineReferenceId;
    private String status;

    // Default constructor
    public BillingDrawingEntryDto() {}

    // Constructor with essential fields
    public BillingDrawingEntryDto(String lineId, String drawingNo, String markNo, BigDecimal markedQty) {
        this.lineId = lineId;
        this.drawingNo = drawingNo;
        this.markNo = markNo;
        this.markedQty = markedQty;
        this.status = "billing";
    }

    // Getters and Setters
    public String getLineId() { return lineId; }
    public void setLineId(String lineId) { this.lineId = lineId; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public String getDrawingNo() { return drawingNo; }
    public void setDrawingNo(String drawingNo) { this.drawingNo = drawingNo; }

    public String getMarkNo() { return markNo; }
    public void setMarkNo(String markNo) { this.markNo = markNo; }

    public BigDecimal getMarkedQty() { return markedQty; }
    public void setMarkedQty(BigDecimal markedQty) { this.markedQty = markedQty; }

    public BigDecimal getTotalMarkedWgt() { return totalMarkedWgt; }
    public void setTotalMarkedWgt(BigDecimal totalMarkedWgt) { this.totalMarkedWgt = totalMarkedWgt; }

    public String getSessionCode() { return sessionCode; }
    public void setSessionCode(String sessionCode) { this.sessionCode = sessionCode; }

    public String getSessionName() { return sessionName; }
    public void setSessionName(String sessionName) { this.sessionName = sessionName; }

    public BigDecimal getSessionWeight() { return sessionWeight; }
    public void setSessionWeight(BigDecimal sessionWeight) { this.sessionWeight = sessionWeight; }

    public BigDecimal getWidth() { return width; }
    public void setWidth(BigDecimal width) { this.width = width; }

    public BigDecimal getLength() { return length; }
    public void setLength(BigDecimal length) { this.length = length; }

    public BigDecimal getItemQty() { return itemQty; }
    public void setItemQty(BigDecimal itemQty) { this.itemQty = itemQty; }

    public BigDecimal getItemWeight() { return itemWeight; }
    public void setItemWeight(BigDecimal itemWeight) { this.itemWeight = itemWeight; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getLastUpdatingDate() { return lastUpdatingDate; }
    public void setLastUpdatingDate(LocalDateTime lastUpdatingDate) { this.lastUpdatingDate = lastUpdatingDate; }

    public String getLastUpdatedBy() { return lastUpdatedBy; }
    public void setLastUpdatedBy(String lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }

    public String getAttribute1V() { return attribute1V; }
    public void setAttribute1V(String attribute1V) { this.attribute1V = attribute1V; }

    public String getAttribute2V() { return attribute2V; }
    public void setAttribute2V(String attribute2V) { this.attribute2V = attribute2V; }

    public String getAttribute3V() { return attribute3V; }
    public void setAttribute3V(String attribute3V) { this.attribute3V = attribute3V; }

    public String getAttribute4V() { return attribute4V; }
    public void setAttribute4V(String attribute4V) { this.attribute4V = attribute4V; }

    public String getAttribute5V() { return attribute5V; }
    public void setAttribute5V(String attribute5V) { this.attribute5V = attribute5V; }

    public BigDecimal getAttribute1N() { return attribute1N; }
    public void setAttribute1N(BigDecimal attribute1N) { this.attribute1N = attribute1N; }

    public BigDecimal getAttribute2N() { return attribute2N; }
    public void setAttribute2N(BigDecimal attribute2N) { this.attribute2N = attribute2N; }

    public BigDecimal getAttribute3N() { return attribute3N; }
    public void setAttribute3N(BigDecimal attribute3N) { this.attribute3N = attribute3N; }

    public BigDecimal getAttribute4N() { return attribute4N; }
    public void setAttribute4N(BigDecimal attribute4N) { this.attribute4N = attribute4N; }

    public BigDecimal getAttribute5N() { return attribute5N; }
    public void setAttribute5N(BigDecimal attribute5N) { this.attribute5N = attribute5N; }

    public LocalDate getAttribute1D() { return attribute1D; }
    public void setAttribute1D(LocalDate attribute1D) { this.attribute1D = attribute1D; }

    public LocalDate getAttribute2D() { return attribute2D; }
    public void setAttribute2D(LocalDate attribute2D) { this.attribute2D = attribute2D; }

    public LocalDate getAttribute3D() { return attribute3D; }
    public void setAttribute3D(LocalDate attribute3D) { this.attribute3D = attribute3D; }

    public LocalDate getAttribute4D() { return attribute4D; }
    public void setAttribute4D(LocalDate attribute4D) { this.attribute4D = attribute4D; }

    public LocalDate getAttribute5D() { return attribute5D; }
    public void setAttribute5D(LocalDate attribute5D) { this.attribute5D = attribute5D; }

    public BigDecimal getPoLineReferenceId() { return poLineReferenceId; }
    public void setPoLineReferenceId(BigDecimal poLineReferenceId) { this.poLineReferenceId = poLineReferenceId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "BillingDrawingEntryDto{" +
                "lineId='" + lineId + '\'' +
                ", drawingNo='" + drawingNo + '\'' +
                ", markNo='" + markNo + '\'' +
                ", markedQty=" + markedQty +
                ", status='" + status + '\'' +
                '}';
    }
}
