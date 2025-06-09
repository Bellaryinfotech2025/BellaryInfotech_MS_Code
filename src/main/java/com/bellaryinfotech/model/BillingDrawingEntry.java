package com.bellaryinfotech.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Table(name = "billing_drawing_entry")
public class BillingDrawingEntry {

    @Id
    @Column(name = "line_id", nullable = false)
    private String lineId;

    @Version
    @Column(name = "version", nullable = false)
    private Long version = 0L;

    @Column(name = "drawing_no")
    private String drawingNo;

    @Column(name = "mark_no")
    private String markNo;

    @Column(name = "marked_qty", precision = 19, scale = 4)
    private BigDecimal markedQty;

    @Column(name = "total_marked_wgt", precision = 19, scale = 4)
    private BigDecimal totalMarkedWgt;

    @Column(name = "session_code")
    private String sessionCode;

    @Column(name = "session_name")
    private String sessionName;

    @Column(name = "session_weight", precision = 19, scale = 4)
    private BigDecimal sessionWeight;

    @Column(name = "width", precision = 19, scale = 4)
    private BigDecimal width;

    @Column(name = "length", precision = 19, scale = 4)
    private BigDecimal length;

    @Column(name = "item_qty", precision = 19, scale = 4)
    private BigDecimal itemQty;

    @Column(name = "item_weight", precision = 19, scale = 4)
    private BigDecimal itemWeight;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_updating_date")
    private LocalDateTime lastUpdatingDate;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "attribute1_v")
    private String attribute1V;

    @Column(name = "attribute2_v")
    private String attribute2V;

    @Column(name = "attribute3_v")
    private String attribute3V;

    @Column(name = "attribute4_v")
    private String attribute4V;

    @Column(name = "attribute5_v")
    private String attribute5V;

    @Column(name = "attribute1_n", precision = 19, scale = 4)
    private BigDecimal attribute1N;

    @Column(name = "attribute2_n", precision = 19, scale = 4)
    private BigDecimal attribute2N;

    @Column(name = "attribute3_n", precision = 19, scale = 4)
    private BigDecimal attribute3N;

    @Column(name = "attribute4_n", precision = 19, scale = 4)
    private BigDecimal attribute4N;

    @Column(name = "attribute5_n", precision = 19, scale = 4)
    private BigDecimal attribute5N;

    @Column(name = "attribute1_d")
    private LocalDate attribute1D;

    @Column(name = "attribute2_d")
    private LocalDate attribute2D;

    @Column(name = "attribute3_d")
    private LocalDate attribute3D;

    @Column(name = "attribute4_d")
    private LocalDate attribute4D;

    @Column(name = "attribute5_d")
    private LocalDate attribute5D;

    @Column(name = "po_line_reference_id")
    private BigDecimal poLineReferenceId;

    @Column(name = "status", length = 50)
    private String status;

    // NEW ENHANCED FIELDS - Add the new fields for enhanced data
    @Column(name = "drawing_weight", precision = 19, scale = 4)
    private BigDecimal drawingWeight;

    @Column(name = "mark_weight", precision = 19, scale = 4)
    private BigDecimal markWeight;

    @Column(name = "drawing_received_date")
    private LocalDate drawingReceivedDate;

    @Column(name = "target_date")
    private LocalDate targetDate;

    // NEW ENHANCED FIELDS - Add fabrication stage fields
    @Column(name = "cutting_stage", length = 1)
    private String cuttingStage = "N";

    @Column(name = "fit_up_stage", length = 1)
    private String fitUpStage = "N";

    @Column(name = "welding_stage", length = 1)
    private String weldingStage = "N";

    @Column(name = "finishing_stage", length = 1)
    private String finishingStage = "N";

    // Default constructor
    public BillingDrawingEntry() {}

    // Constructor with essential fields
    public BillingDrawingEntry(String lineId, String drawingNo, String markNo, BigDecimal markedQty) {
        this.lineId = lineId;
        this.drawingNo = drawingNo;
        this.markNo = markNo;
        this.markedQty = markedQty;
        this.creationDate = LocalDateTime.now();
        this.lastUpdatingDate = LocalDateTime.now();
        this.status = "billing";
        // Initialize fabrication stages to "N"
        this.cuttingStage = "N";
        this.fitUpStage = "N";
        this.weldingStage = "N";
        this.finishingStage = "N";
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

    // NEW ENHANCED FIELDS - Getters and Setters for new fields
    public BigDecimal getDrawingWeight() { return drawingWeight; }
    public void setDrawingWeight(BigDecimal drawingWeight) { this.drawingWeight = drawingWeight; }

    public BigDecimal getMarkWeight() { return markWeight; }
    public void setMarkWeight(BigDecimal markWeight) { this.markWeight = markWeight; }

    public LocalDate getDrawingReceivedDate() { return drawingReceivedDate; }
    public void setDrawingReceivedDate(LocalDate drawingReceivedDate) { this.drawingReceivedDate = drawingReceivedDate; }

    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }

    // NEW ENHANCED FIELDS - Getters and Setters for fabrication stage fields
    public String getCuttingStage() { return cuttingStage; }
    public void setCuttingStage(String cuttingStage) { this.cuttingStage = cuttingStage; }

    public String getFitUpStage() { return fitUpStage; }
    public void setFitUpStage(String fitUpStage) { this.fitUpStage = fitUpStage; }

    public String getWeldingStage() { return weldingStage; }
    public void setWeldingStage(String weldingStage) { this.weldingStage = weldingStage; }

    public String getFinishingStage() { return finishingStage; }
    public void setFinishingStage(String finishingStage) { this.finishingStage = finishingStage; }

    @Override
    public String toString() {
        return "BillingDrawingEntry{" +
                "lineId='" + lineId + '\'' +
                ", version=" + version +
                ", drawingNo='" + drawingNo + '\'' +
                ", markNo='" + markNo + '\'' +
                ", markedQty=" + markedQty +
                ", drawingWeight=" + drawingWeight +
                ", markWeight=" + markWeight +
                ", cuttingStage='" + cuttingStage + '\'' +
                ", fitUpStage='" + fitUpStage + '\'' +
                ", weldingStage='" + weldingStage + '\'' +
                ", finishingStage='" + finishingStage + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
