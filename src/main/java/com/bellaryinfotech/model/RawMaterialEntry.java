package com.bellaryinfotech.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "raw_material_entry")
public class RawMaterialEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "work_order")
    private String workOrder;

     
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "section")
    private String section;

    
    @Column(name = "section_code")
    private String sectionCode;

    
    @Column(name = "material_code")
    private String materialCode;

    @Column(name = "width", precision = 10, scale = 3)
    private BigDecimal width;

    @Column(name = "length", precision = 10, scale = 3)
    private BigDecimal length;

    @Column(name = "qty", precision = 10, scale = 3)
    private BigDecimal qty;

    @Column(name = "uom")
    private String uom;

    @Column(name = "total_weight", precision = 10, scale = 3)
    private BigDecimal totalWeight;

     
    @Column(name = "total_received", precision = 10, scale = 3)
    private BigDecimal totalReceived;

    
    @Column(name = "total_transferred_to_other")
    private String totalTransferredToOther;

     
    @Column(name = "receiver_name")
    private String receiverName;

   
    @Column(name = "remarks")
    private String remarks;

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "document_no")
    private String documentNo;

    @Column(name = "document_date")
    private LocalDate documentDate;

    @Column(name = "received_date")
    private LocalDate receivedDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    @Column(name = "tenant_id")
    private Integer tenantId;

     
    @Column(name = "receipt_type")
    private String receiptType;

     
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

    @Column(name = "attribute1_n", precision = 15, scale = 3)
    private BigDecimal attribute1N;
    @Column(name = "attribute2_n", precision = 15, scale = 3)
    private BigDecimal attribute2N;
    @Column(name = "attribute3_n", precision = 15, scale = 3)
    private BigDecimal attribute3N;
    @Column(name = "attribute4_n", precision = 15, scale = 3)
    private BigDecimal attribute4N;
    @Column(name = "attribute5_n", precision = 15, scale = 3)
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

    
    public RawMaterialEntry() {}

    public RawMaterialEntry(String workOrder, Long orderId, String section, String sectionCode, String materialCode,
                            BigDecimal width, BigDecimal length, BigDecimal qty, String uom,
                            BigDecimal totalWeight, BigDecimal totalReceived, String totalTransferredToOther,
                            String receiverName, String remarks, String vehicleNumber, String documentNo,
                            LocalDate documentDate, LocalDate receivedDate, String createdBy, String receiptType) { // NEW: Added receiptType
        this.workOrder = workOrder;
        this.orderId = orderId;
        this.section = section;
        this.sectionCode = sectionCode;
        this.materialCode = materialCode;
        this.width = width;
        this.length = length;
        this.qty = qty;
        this.uom = uom;
        this.totalWeight = totalWeight;
        this.totalReceived = totalReceived;
        this.totalTransferredToOther = totalTransferredToOther;
        this.receiverName = receiverName;
        this.remarks = remarks;
        this.vehicleNumber = vehicleNumber;
        this.documentNo = documentNo;
        this.documentDate = documentDate;
        this.receivedDate = receivedDate;
        this.createdBy = createdBy;
        this.createdDate = LocalDateTime.now();
        this.lastUpdatedBy = createdBy;
        this.lastUpdatedDate = LocalDateTime.now();
        this.tenantId = 1;
        this.receiptType = receiptType;  
    }

     
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getWorkOrder() { return workOrder; }
    public void setWorkOrder(String workOrder) { this.workOrder = workOrder; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }
    public String getSectionCode() { return sectionCode; }
    public void setSectionCode(String sectionCode) { this.sectionCode = sectionCode; }
    public String getMaterialCode() { return materialCode; }
    public void setMaterialCode(String materialCode) { this.materialCode = materialCode; }
    public BigDecimal getWidth() { return width; }
    public void setWidth(BigDecimal width) { this.width = width; }
    public BigDecimal getLength() { return length; }
    public void setLength(BigDecimal length) { this.length = length; }
    public BigDecimal getQty() { return qty; }
    public void setQty(BigDecimal qty) { this.qty = qty; }
    public String getUom() { return uom; }
    public void setUom(String uom) { this.uom = uom; }
    public BigDecimal getTotalWeight() { return totalWeight; }
    public void setTotalWeight(BigDecimal totalWeight) { this.totalWeight = totalWeight; }
    public BigDecimal getTotalReceived() { return totalReceived; }
    public void setTotalReceived(BigDecimal totalReceived) { this.totalReceived = totalReceived; }
    public String getTotalTransferredToOther() { return totalTransferredToOther; }
    public void setTotalTransferredToOther(String totalTransferredToOther) { this.totalTransferredToOther = totalTransferredToOther; }
    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public String getLastUpdatedBy() { return lastUpdatedBy; }
    public void setLastUpdatedBy(String lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }
    public LocalDateTime getLastUpdatedDate() { return lastUpdatedDate; }
    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }
    public Integer getTenantId() { return tenantId; }
    public void setTenantId(Integer tenantId) { this.tenantId = tenantId; }
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
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    public String getDocumentNo() { return documentNo; }
    public void setDocumentNo(String documentNo) { this.documentNo = documentNo; }
    public LocalDate getDocumentDate() { return documentDate; }
    public void setDocumentDate(LocalDate documentDate) { this.documentDate = documentDate; }
    public LocalDate getReceivedDate() { return receivedDate; }
    public void setReceivedDate(LocalDate receivedDate) { this.receivedDate = receivedDate; }
    public String getReceiptType() { return receiptType; }  
    public void setReceiptType(String receiptType) { this.receiptType = receiptType; }  
}