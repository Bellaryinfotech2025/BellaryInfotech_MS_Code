package com.bellaryinfotech.DTO;
 

import java.math.BigDecimal;

public class ServiceCodeEntryDto {
    
    private String code;
    private String name;
    private BigDecimal wgt;
    private BigDecimal area;
    
    // Default constructor
    public ServiceCodeEntryDto() {}
    
    // Constructor with parameters
    public ServiceCodeEntryDto(String code, String name, BigDecimal wgt, BigDecimal area) {
        this.code = code;
        this.name = name;
        this.wgt = wgt;
        this.area = area;
    }
    
    // Getters and Setters
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public BigDecimal getWgt() {
        return wgt;
    }
    
    public void setWgt(BigDecimal wgt) {
        this.wgt = wgt;
    }
    
    public BigDecimal getArea() {
        return area;
    }
    
    public void setArea(BigDecimal area) {
        this.area = area;
    }
}
