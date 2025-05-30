package com.bellaryinfotech.DTO;

import java.math.BigDecimal;

public class BitsDrawingEntryStatsDto {
    private String drawingNo;
    private Long totalEntries;
    private BigDecimal totalMarkedQty;
    private BigDecimal totalMarkedWgt;

    // Default constructor
    public BitsDrawingEntryStatsDto() {
    }

    // Constructor with fields
    public BitsDrawingEntryStatsDto(String drawingNo, Long totalEntries, BigDecimal totalMarkedQty, BigDecimal totalMarkedWgt) {
        this.drawingNo = drawingNo;
        this.totalEntries = totalEntries;
        this.totalMarkedQty = totalMarkedQty;
        this.totalMarkedWgt = totalMarkedWgt;
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

    public BigDecimal getTotalMarkedWgt() {
        return totalMarkedWgt;
    }

    public void setTotalMarkedWgt(BigDecimal totalMarkedWgt) {
        this.totalMarkedWgt = totalMarkedWgt;
    }

    @Override
    public String toString() {
        return "BitsDrawingEntryStatsDto{" +
                "drawingNo='" + drawingNo + '\'' +
                ", totalEntries=" + totalEntries +
                ", totalMarkedQty=" + totalMarkedQty +
                ", totalMarkedWgt=" + totalMarkedWgt +
                '}';
    }
}