package com.bellaryinfotech.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "service_code_entry")
public class ServiceCodeEntry {
    
    @Id
    @Column(name = "code")
    private String code;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "wgt", precision = 10, scale = 4)
    private BigDecimal wgt;
    
    @Column(name = "area", precision = 10, scale = 4)
    private BigDecimal area;
    
    // Default constructor
    public ServiceCodeEntry() {}
    
    // Constructor with parameters
    public ServiceCodeEntry(String code, String name, BigDecimal wgt, BigDecimal area) {
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