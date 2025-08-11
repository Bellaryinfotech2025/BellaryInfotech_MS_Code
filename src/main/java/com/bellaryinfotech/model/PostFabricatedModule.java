package com.bellaryinfotech.model;
 

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "post_fabricated_module")
public class PostFabricatedModule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "work_order")
    private String workOrder;
    
    @Column(name = "client_name")
    private String clientName;
    
    @Column(name = "service_description")
    private String serviceDescription;
    
    @Column(name = "uom")
    private String uom;
    
    @Column(name = "data_module")
    private String dataModule;
    
    @Column(name = "building_name")
    private String buildingName;
    
    @Column(name = "ra_number")
    private String raNumber;
    
    @Column(name = "vehicle_number")
    private String vehicleNumber;
    
    @Column(name = "load_number")
    private String loadNumber;
    
    @Column(name = "plot_number")
    private String plotNumber;
    
    @Column(name = "drawing_no")
    private String drawingNo;
    
    @Column(name = "mark_no")
    private String markNo;
    
    @Column(name = "mark_weight")
    private Double markWeight;
    
    @Column(name = "mark_qty")
    private Integer markQty;
    
    @Column(name = "total_mark_weight")
    private Double totalMarkWeight;
    
    @Column(name = "remarks")
    private String remarks;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
    
    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
    }
    
    // Constructors
    public PostFabricatedModule() {}
    
    public PostFabricatedModule(String workOrder, String clientName, String serviceDescription, 
                               String uom, String dataModule, String buildingName, String raNumber,
                               String vehicleNumber, String loadNumber, String plotNumber, 
                               String drawingNo, String markNo, Double markWeight, 
                               Integer markQty, Double totalMarkWeight, String remarks) {
        this.workOrder = workOrder;
        this.clientName = clientName;
        this.serviceDescription = serviceDescription;
        this.uom = uom;
        this.dataModule = dataModule;
        this.buildingName = buildingName;
        this.raNumber = raNumber;
        this.vehicleNumber = vehicleNumber;
        this.loadNumber = loadNumber;
        this.plotNumber = plotNumber;
        this.drawingNo = drawingNo;
        this.markNo = markNo;
        this.markWeight = markWeight;
        this.markQty = markQty;
        this.totalMarkWeight = totalMarkWeight;
        this.remarks = remarks;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getWorkOrder() { return workOrder; }
    public void setWorkOrder(String workOrder) { this.workOrder = workOrder; }
    
    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    
    public String getServiceDescription() { return serviceDescription; }
    public void setServiceDescription(String serviceDescription) { this.serviceDescription = serviceDescription; }
    
    public String getUom() { return uom; }
    public void setUom(String uom) { this.uom = uom; }
    
    public String getDataModule() { return dataModule; }
    public void setDataModule(String dataModule) { this.dataModule = dataModule; }
    
    public String getBuildingName() { return buildingName; }
    public void setBuildingName(String buildingName) { this.buildingName = buildingName; }
    
    public String getRaNumber() { return raNumber; }
    public void setRaNumber(String raNumber) { this.raNumber = raNumber; }
    
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    
    public String getLoadNumber() { return loadNumber; }
    public void setLoadNumber(String loadNumber) { this.loadNumber = loadNumber; }
    
    public String getPlotNumber() { return plotNumber; }
    public void setPlotNumber(String plotNumber) { this.plotNumber = plotNumber; }
    
    public String getDrawingNo() { return drawingNo; }
    public void setDrawingNo(String drawingNo) { this.drawingNo = drawingNo; }
    
    public String getMarkNo() { return markNo; }
    public void setMarkNo(String markNo) { this.markNo = markNo; }
    
    public Double getMarkWeight() { return markWeight; }
    public void setMarkWeight(Double markWeight) { this.markWeight = markWeight; }
    
    public Integer getMarkQty() { return markQty; }
    public void setMarkQty(Integer markQty) { this.markQty = markQty; }
    
    public Double getTotalMarkWeight() { return totalMarkWeight; }
    public void setTotalMarkWeight(Double totalMarkWeight) { this.totalMarkWeight = totalMarkWeight; }
    
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    
    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }
}
