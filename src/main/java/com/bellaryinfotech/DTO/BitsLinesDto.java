package com.bellaryinfotech.DTO;

import java.math.BigDecimal;

import java.math.BigDecimal;

public class BitsLinesDto {
    
    private Long lineId;
    private String serNo;
    private String serviceCode;
    private String serviceDesc;
    private String sacHsnCode;
    private String uom;
    private BigDecimal qty;
    private BigDecimal rate;
    private BigDecimal amount;

    // Default constructor
    public BitsLinesDto() {}

    // Constructor with parameters
    public BitsLinesDto(String serNo, String serviceCode, String serviceDesc, 
                       String sacHsnCode, String uom, BigDecimal qty, 
                       BigDecimal rate, BigDecimal amount) {
        this.serNo = serNo;
        this.serviceCode = serviceCode;
        this.serviceDesc = serviceDesc;
        this.sacHsnCode = sacHsnCode;
        this.uom = uom;
        this.qty = qty;
        this.rate = rate;
        this.amount = amount;
    }

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

    public String getSacHsnCode() {
        return sacHsnCode;
    }

    public void setSacHsnCode(String sacHsnCode) {
        this.sacHsnCode = sacHsnCode;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
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

    // Legacy methods for backward compatibility (mapping to rate and amount)
    public BigDecimal getUnitPrice() {
        return this.rate;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.rate = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return this.amount;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.amount = totalPrice;
    }
}
