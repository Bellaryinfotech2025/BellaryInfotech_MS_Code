package com.bellaryinfotech.DTO;

import java.math.BigDecimal;

public class BitsLinesDto {
    
    private Long lineId;
    private String serNo;
    private String serviceCode;
    private String serviceDesc;
    private BigDecimal qty;
    private String uom;
    private BigDecimal rate;
    private BigDecimal amount;
    
    // Add work order reference field
    private String workOrderRef;

    // Default constructor
    public BitsLinesDto() {}

    // Getters and Setters
    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public String getSerNo() {
        return serNo;
    }

    public void setSerNo(String serNo) {
        this.serNo = serNo;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getWorkOrderRef() {
        return workOrderRef;
    }

    public void setWorkOrderRef(String workOrderRef) {
        this.workOrderRef = workOrderRef;
    }

    @Override
    public String toString() {
        return "BitsLinesDto{" +
                "lineId=" + lineId +
                ", serNo='" + serNo + '\'' +
                ", serviceCode='" + serviceCode + '\'' +
                ", serviceDesc='" + serviceDesc + '\'' +
                ", qty=" + qty +
                ", uom='" + uom + '\'' +
                ", rate=" + rate +
                ", amount=" + amount +
                ", workOrderRef='" + workOrderRef + '\'' +
                '}';
    }
}