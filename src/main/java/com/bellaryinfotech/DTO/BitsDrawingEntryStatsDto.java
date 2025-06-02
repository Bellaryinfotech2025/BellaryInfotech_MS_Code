package com.bellaryinfotech.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BitsDrawingEntryStatsDto {

    @JsonProperty("drawingNo")
    private String drawingNo;

    @JsonProperty("totalEntries")
    private Long totalEntries;

    @JsonProperty("totalMarkedQty")
    private BigDecimal totalMarkedQty;

    @JsonProperty("totalMarkedWeight")
    private BigDecimal totalMarkedWeight;

    // Default constructor
    public BitsDrawingEntryStatsDto() {}

    // Constructor with all fields
    public BitsDrawingEntryStatsDto(String drawingNo, Long totalEntries, BigDecimal totalMarkedQty, BigDecimal totalMarkedWeight) {
        this.drawingNo = drawingNo;
        this.totalEntries = totalEntries;
        this.totalMarkedQty = totalMarkedQty;
        this.totalMarkedWeight = totalMarkedWeight;
    }

    // Getters and Setters
    public String getDrawingNo() {
        return drawingNo;
    }

    public void setDrawingNo(String drawingNo) {
        this.drawingNo = drawingNo;
    }

    public Long getTotalEntries() {
        return totalEntries;
    }

    public void setTotalEntries(Long totalEntries) {
        this.totalEntries = totalEntries;
    }

    public BigDecimal getTotalMarkedQty() {
        return totalMarkedQty;
    }

    public void setTotalMarkedQty(BigDecimal totalMarkedQty) {
        this.totalMarkedQty = totalMarkedQty;
    }

    public BigDecimal getTotalMarkedWeight() {
        return totalMarkedWeight;
    }

    public void setTotalMarkedWeight(BigDecimal totalMarkedWeight) {
        this.totalMarkedWeight = totalMarkedWeight;
    }

    @Override
    public String toString() {
        return "BitsDrawingEntryStatsDto{" +
                "drawingNo='" + drawingNo + '\'' +
                ", totalEntries=" + totalEntries +
                ", totalMarkedQty=" + totalMarkedQty +
                ", totalMarkedWeight=" + totalMarkedWeight +
                '}';
    }
}
