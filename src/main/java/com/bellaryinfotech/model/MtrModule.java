package com.bellaryinfotech.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mtr_module")
public class MtrModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "work_order")
    private String workOrder;

    @Column(name = "service_description")
    private String serviceDescription;

    @Column(name = "uom")
    private String uom;

    @Column(name = "department")
    private String department;

    @Column(name = "work_location")
    private String workLocation;

    @Column(name = "data_module")
    private String dataModule;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "drawing_no")
    private String drawingNo;

    @Column(name = "mark_no")
    private String markNo;

    @Column(name = "mark_qty")
    private String markQty;

    @Column(name = "each_mark_length")
    private String eachMarkLength;

    @Column(name = "total_mark_length")
    private String totalMarkLength;

    @Column(name = "item_no")
    private String itemNo;

    @Column(name = "section")
    private String section;

    @Column(name = "length_mm")
    private String lengthMm;

    @Column(name = "item_qty")
    private String itemQty;

    @Column(name = "total_length")
    private String totalLength;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "tenant_id")
    private String tenantId;

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

    // Default constructor
    public MtrModule() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getWorkOrder() { return workOrder; }
    public void setWorkOrder(String workOrder) { this.workOrder = workOrder; }

    public String getServiceDescription() { return serviceDescription; }
    public void setServiceDescription(String serviceDescription) { this.serviceDescription = serviceDescription; }

    public String getUom() { return uom; }
    public void setUom(String uom) { this.uom = uom; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getWorkLocation() { return workLocation; }
    public void setWorkLocation(String workLocation) { this.workLocation = workLocation; }

    public String getDataModule() { return dataModule; }
    public void setDataModule(String dataModule) { this.dataModule = dataModule; }

    public String getBuildingName() { return buildingName; }
    public void setBuildingName(String buildingName) { this.buildingName = buildingName; }

    public String getDrawingNo() { return drawingNo; }
    public void setDrawingNo(String drawingNo) { this.drawingNo = drawingNo; }

    public String getMarkNo() { return markNo; }
    public void setMarkNo(String markNo) { this.markNo = markNo; }

    public String getMarkQty() { return markQty; }
    public void setMarkQty(String markQty) { this.markQty = markQty; }

    public String getEachMarkLength() { return eachMarkLength; }
    public void setEachMarkLength(String eachMarkLength) { this.eachMarkLength = eachMarkLength; }

    public String getTotalMarkLength() { return totalMarkLength; }
    public void setTotalMarkLength(String totalMarkLength) { this.totalMarkLength = totalMarkLength; }

    public String getItemNo() { return itemNo; }
    public void setItemNo(String itemNo) { this.itemNo = itemNo; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getLengthMm() { return lengthMm; }
    public void setLengthMm(String lengthMm) { this.lengthMm = lengthMm; }

    public String getItemQty() { return itemQty; }
    public void setItemQty(String itemQty) { this.itemQty = itemQty; }

    public String getTotalLength() { return totalLength; }
    public void setTotalLength(String totalLength) { this.totalLength = totalLength; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

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
}
