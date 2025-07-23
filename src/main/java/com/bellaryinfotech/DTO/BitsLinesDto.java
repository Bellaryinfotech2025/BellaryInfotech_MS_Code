package com.bellaryinfotech.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BitsLinesDto {
    private Long lineId;
    
    // NEW: Order ID for foreign key relationship
    private Long orderId;
    
    private BigDecimal lineNumber;
    private String serNo;
    private String serviceCode;
    private String serviceDesc;
    private BigDecimal qty;
    private String uom;
    
    @JsonProperty("rate")
    private BigDecimal unitPrice;
    
    @JsonProperty("amount")
    private BigDecimal totalPrice;
    
    // Attribute fields
    private String workOrderRef;
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
    
 // NEW: GST-related fields
    private String gstType;
    private BigDecimal subTotal;
    private BigDecimal cgstTotal;
    private BigDecimal sgstTotal;
    private BigDecimal totalIncGst;

    // Default constructor
    public BitsLinesDto() {}

    // Constructor with orderId and lineNumber
    public BitsLinesDto(Long orderId, BigDecimal lineNumber) {
        this.orderId = orderId;
        this.lineNumber = lineNumber;
    }

    // Getters and Setters
    public Long getLineId() { return lineId; }
    public void setLineId(Long lineId) { this.lineId = lineId; }
    
    // NEW: Order ID getter/setter
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    
    public BigDecimal getLineNumber() { return lineNumber; }
    public void setLineNumber(BigDecimal lineNumber) { this.lineNumber = lineNumber; }
    
    public String getSerNo() { return serNo; }
    public void setSerNo(String serNo) { this.serNo = serNo; }
    
    public String getServiceCode() { return serviceCode; }
    public void setServiceCode(String serviceCode) { this.serviceCode = serviceCode; }
    
    public String getServiceDesc() { return serviceDesc; }
    public void setServiceDesc(String serviceDesc) { this.serviceDesc = serviceDesc; }
    
    public BigDecimal getQty() { return qty; }
    public void setQty(BigDecimal qty) { this.qty = qty; }
    
    public String getUom() { return uom; }
    public void setUom(String uom) { this.uom = uom; }
    
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    
    public String getWorkOrderRef() { return workOrderRef; }
    public void setWorkOrderRef(String workOrderRef) { this.workOrderRef = workOrderRef; }

    // All other attribute getters/setters remain the same...
    public String getAttribute2V() { return attribute2V; }
    public void setAttribute2V(String attribute2v) { attribute2V = attribute2v; }
    
    public String getAttribute3V() { return attribute3V; }
    public void setAttribute3V(String attribute3v) { attribute3V = attribute3v; }
    
    public String getAttribute4V() { return attribute4V; }
    public void setAttribute4V(String attribute4v) { attribute4V = attribute4v; }
    
    public String getAttribute5V() { return attribute5V; }
    public void setAttribute5V(String attribute5v) { attribute5V = attribute5v; }
    
    public BigDecimal getAttribute1N() { return attribute1N; }
    public void setAttribute1N(BigDecimal attribute1n) { attribute1N = attribute1n; }
    
    public BigDecimal getAttribute2N() { return attribute2N; }
    public void setAttribute2N(BigDecimal attribute2n) { attribute2N = attribute2n; }
    
    public BigDecimal getAttribute3N() { return attribute3N; }
    public void setAttribute3N(BigDecimal attribute3n) { attribute3N = attribute3n; }
    
    public BigDecimal getAttribute4N() { return attribute4N; }
    public void setAttribute4N(BigDecimal attribute4n) { attribute4N = attribute4n; }
    
    public BigDecimal getAttribute5N() { return attribute5N; }
    public void setAttribute5N(BigDecimal attribute5n) { attribute5N = attribute5n; }
    
    public LocalDate getAttribute1D() { return attribute1D; }
    public void setAttribute1D(LocalDate attribute1d) { attribute1D = attribute1d; }
    
    public LocalDate getAttribute2D() { return attribute2D; }
    public void setAttribute2D(LocalDate attribute2d) { attribute2D = attribute2d; }
    
    public LocalDate getAttribute3D() { return attribute3D; }
    public void setAttribute3D(LocalDate attribute3d) { attribute3D = attribute3d; }
    
    public LocalDate getAttribute4D() { return attribute4D; }
    public void setAttribute4D(LocalDate attribute4d) { attribute4D = attribute4d; }
    
    public LocalDate getAttribute5D() { return attribute5D; }
    public void setAttribute5D(LocalDate attribute5d) { attribute5D = attribute5d; }

	public String getGstType() {
		return gstType;
	}

	public void setGstType(String gstType) {
		this.gstType = gstType;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getCgstTotal() {
		return cgstTotal;
	}

	public void setCgstTotal(BigDecimal cgstTotal) {
		this.cgstTotal = cgstTotal;
	}

	public BigDecimal getSgstTotal() {
		return sgstTotal;
	}

	public void setSgstTotal(BigDecimal sgstTotal) {
		this.sgstTotal = sgstTotal;
	}

	public BigDecimal getTotalIncGst() {
		return totalIncGst;
	}

	public void setTotalIncGst(BigDecimal totalIncGst) {
		this.totalIncGst = totalIncGst;
	}
    
    
}