package com.bellaryinfotech.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BitsDrawingEntryDto {

@JsonProperty("lineId")
private String lineId;

@JsonProperty("version")
private Long version;

@JsonProperty("drawingNo")
@NotBlank(message = "Drawing number is required")
private String drawingNo;

@JsonProperty("markNo")
@NotBlank(message = "Mark number is required")
private String markNo;

@JsonProperty("markedQty")
@NotNull(message = "Marked quantity is required")
@Positive(message = "Marked quantity must be positive")
private BigDecimal markedQty;

@JsonProperty("totalMarkedWgt")
private BigDecimal totalMarkedWgt;

@JsonProperty("sessionCode")
private String sessionCode;

@JsonProperty("sessionName")
private String sessionName;

@JsonProperty("sessionWeight")
private BigDecimal sessionWeight;

@JsonProperty("width")
private BigDecimal width;

@JsonProperty("length")
private BigDecimal length;

@JsonProperty("itemQty")
private BigDecimal itemQty;

@JsonProperty("itemWeight")
private BigDecimal itemWeight;

@JsonProperty("tenantId")
private String tenantId;

@JsonProperty("creationDate")
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
private LocalDateTime creationDate;

@JsonProperty("createdBy")
private String createdBy;

@JsonProperty("lastUpdatingDate")
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
private LocalDateTime lastUpdatingDate;

@JsonProperty("lastUpdatedBy")
private String lastUpdatedBy;

// NEW FIELD: Reference to bits_po_entry_lines.line_id
@JsonProperty("poLineReferenceId")
private BigDecimal poLineReferenceId;

@JsonProperty("attribute1V")
private String attribute1V;

@JsonProperty("attribute2V")
private String attribute2V;

@JsonProperty("attribute3V")
private String attribute3V;

@JsonProperty("attribute4V")
private String attribute4V;

@JsonProperty("attribute5V")
private String attribute5V;

@JsonProperty("attribute1N")
private BigDecimal attribute1N;

@JsonProperty("attribute2N")
private BigDecimal attribute2N;

@JsonProperty("attribute3N")
private BigDecimal attribute3N;

@JsonProperty("attribute4N")
private BigDecimal attribute4N;

@JsonProperty("attribute5N")
private BigDecimal attribute5N;

@JsonProperty("attribute1D")
@JsonFormat(pattern = "yyyy-MM-dd")
private LocalDate attribute1D;

@JsonProperty("attribute2D")
@JsonFormat(pattern = "yyyy-MM-dd")
private LocalDate attribute2D;

@JsonProperty("attribute3D")
@JsonFormat(pattern = "yyyy-MM-dd")
private LocalDate attribute3D;

@JsonProperty("attribute4D")
@JsonFormat(pattern = "yyyy-MM-dd")
private LocalDate attribute4D;

@JsonProperty("attribute5D")
@JsonFormat(pattern = "yyyy-MM-dd")
private LocalDate attribute5D;

@JsonProperty("drawingWeight")
private BigDecimal drawingWeight;

@JsonProperty("markWeight")
private BigDecimal markWeight;

@JsonProperty("drawingReceivedDate")
@JsonFormat(pattern = "yyyy-MM-dd")
private LocalDate drawingReceivedDate;

@JsonProperty("targetDate")
@JsonFormat(pattern = "yyyy-MM-dd")
private LocalDate targetDate;

// Default constructor
public BitsDrawingEntryDto() {}

// Constructor with essential fields
public BitsDrawingEntryDto(String drawingNo, String markNo, BigDecimal markedQty) {
    this.drawingNo = drawingNo;
    this.markNo = markNo;
    this.markedQty = markedQty;
}

// Getters and Setters
public String getLineId() {
    return lineId;
}

public void setLineId(String lineId) {
    this.lineId = lineId;
}

public Long getVersion() {
    return version;
}

public void setVersion(Long version) {
    this.version = version;
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

public BigDecimal getMarkedQty() {
    return markedQty;
}

public void setMarkedQty(BigDecimal markedQty) {
    this.markedQty = markedQty;
}

public BigDecimal getTotalMarkedWgt() {
    return totalMarkedWgt;
}

public void setTotalMarkedWgt(BigDecimal totalMarkedWgt) {
    this.totalMarkedWgt = totalMarkedWgt;
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

public BigDecimal getSessionWeight() {
    return sessionWeight;
}

public void setSessionWeight(BigDecimal sessionWeight) {
    this.sessionWeight = sessionWeight;
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

public BigDecimal getItemQty() {
    return itemQty;
}

public void setItemQty(BigDecimal itemQty) {
    this.itemQty = itemQty;
}

public BigDecimal getItemWeight() {
    return itemWeight;
}

public void setItemWeight(BigDecimal itemWeight) {
    this.itemWeight = itemWeight;
}

public String getTenantId() {
    return tenantId;
}

public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
}

public LocalDateTime getCreationDate() {
    return creationDate;
}

public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
}

public String getCreatedBy() {
    return createdBy;
}

public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
}

public LocalDateTime getLastUpdatingDate() {
    return lastUpdatingDate;
}

public void setLastUpdatingDate(LocalDateTime lastUpdatingDate) {
    this.lastUpdatingDate = lastUpdatingDate;
}

public String getLastUpdatedBy() {
    return lastUpdatedBy;
}

public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
}

// NEW GETTER/SETTER for PO Line Reference ID
public BigDecimal getPoLineReferenceId() {
    return poLineReferenceId;
}

public void setPoLineReferenceId(BigDecimal poLineReferenceId) {
    this.poLineReferenceId = poLineReferenceId;
}

public String getAttribute1V() {
    return attribute1V;
}

public void setAttribute1V(String attribute1V) {
    this.attribute1V = attribute1V;
}

public String getAttribute2V() {
    return attribute2V;
}

public void setAttribute2V(String attribute2V) {
    this.attribute2V = attribute2V;
}

public String getAttribute3V() {
    return attribute3V;
}

public void setAttribute3V(String attribute3V) {
    this.attribute3V = attribute3V;
}

public String getAttribute4V() {
    return attribute4V;
}

public void setAttribute4V(String attribute4V) {
    this.attribute4V = attribute4V;
}

public String getAttribute5V() {
    return attribute5V;
}

public void setAttribute5V(String attribute5V) {
    this.attribute5V = attribute5V;
}

public BigDecimal getAttribute1N() {
    return attribute1N;
}

public void setAttribute1N(BigDecimal attribute1N) {
    this.attribute1N = attribute1N;
}

public BigDecimal getAttribute2N() {
    return attribute2N;
}

public void setAttribute2N(BigDecimal attribute2N) {
    this.attribute2N = attribute2N;
}

public BigDecimal getAttribute3N() {
    return attribute3N;
}

public void setAttribute3N(BigDecimal attribute3N) {
    this.attribute3N = attribute3N;
}

public BigDecimal getAttribute4N() {
    return attribute4N;
}

public void setAttribute4N(BigDecimal attribute4N) {
    this.attribute4N = attribute4N;
}

public BigDecimal getAttribute5N() {
    return attribute5N;
}

public void setAttribute5N(BigDecimal attribute5N) {
    this.attribute5N = attribute5N;
}

public LocalDate getAttribute1D() {
    return attribute1D;
}

public void setAttribute1D(LocalDate attribute1D) {
    this.attribute1D = attribute1D;
}

public LocalDate getAttribute2D() {
    return attribute2D;
}

 

public void setAttribute2D(LocalDate attribute2d) {
	attribute2D = attribute2d;
}

public LocalDate getAttribute3D() {
    return attribute3D;
}

public void setAttribute3D(LocalDate attribute3D) {
    this.attribute3D = attribute3D;
}

public LocalDate getAttribute4D() {
    return attribute4D;
}

public void setAttribute4D(LocalDate attribute4D) {
    this.attribute4D = attribute4D;
}

public LocalDate getAttribute5D() {
    return attribute5D;
}

public void setAttribute5D(LocalDate attribute5D) {
    this.attribute5D = attribute5D;
}

public BigDecimal getDrawingWeight() {
	return drawingWeight;
}

public void setDrawingWeight(BigDecimal drawingWeight) {
	this.drawingWeight = drawingWeight;
}

public BigDecimal getMarkWeight() {
	return markWeight;
}

public void setMarkWeight(BigDecimal markWeight) {
	this.markWeight = markWeight;
}

public LocalDate getDrawingReceivedDate() {
	return drawingReceivedDate;
}

public void setDrawingReceivedDate(LocalDate drawingReceivedDate) {
	this.drawingReceivedDate = drawingReceivedDate;
}

public LocalDate getTargetDate() {
	return targetDate;
}

public void setTargetDate(LocalDate targetDate) {
	this.targetDate = targetDate;
}


}
