package com.bellaryinfotech.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alignment_cubic_meter_pre_fabricated_production")
public class AlignmentCubicMeterPreFabricatedProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "work_order")
    private String workOrder;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "service_description")
    private String serviceDescription;

    @Column(name = "uom")
    private String uom;

    @Column(name = "department")
    private String department;

    @Column(name = "work_location")
    private String workLocation;

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "load_number")
    private String loadNumber;

    @Column(name = "plot_number")
    private String plotNumber;

    @Column(name = "ra_no")
    private String raNo;

    @Column(name = "drawing_no")
    private String drawingNo;

    @Column(name = "mark_no")
    private String markNo;

    @Column(name = "each_mark_length")
    private Double eachMarkLength;

    @Column(name = "mark_qty")
    private Integer markQty;

    @Column(name = "total_mark_length")
    private Double totalMarkLength;

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
    public AlignmentCubicMeterPreFabricatedProduction() {}

    public AlignmentCubicMeterPreFabricatedProduction(String workOrder, Long orderId, String clientName, 
                                                     String serviceDescription, String uom, String department, 
                                                     String workLocation, String vehicleNumber, String loadNumber, 
                                                     String plotNumber, String raNo, String drawingNo, String markNo, 
                                                     Double eachMarkLength, Integer markQty, Double totalMarkLength, 
                                                     String remarks) {
        this.workOrder = workOrder;
        this.orderId = orderId;
        this.clientName = clientName;
        this.serviceDescription = serviceDescription;
        this.uom = uom;
        this.department = department;
        this.workLocation = workLocation;
        this.vehicleNumber = vehicleNumber;
        this.loadNumber = loadNumber;
        this.plotNumber = plotNumber;
        this.raNo = raNo;
        this.drawingNo = drawingNo;
        this.markNo = markNo;
        this.eachMarkLength = eachMarkLength;
        this.markQty = markQty;
        this.totalMarkLength = totalMarkLength;
        this.remarks = remarks;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getWorkOrder() { return workOrder; }
    public void setWorkOrder(String workOrder) { this.workOrder = workOrder; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getServiceDescription() { return serviceDescription; }
    public void setServiceDescription(String serviceDescription) { this.serviceDescription = serviceDescription; }

    public String getUom() { return uom; }
    public void setUom(String uom) { this.uom = uom; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getWorkLocation() { return workLocation; }
    public void setWorkLocation(String workLocation) { this.workLocation = workLocation; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public String getLoadNumber() { return loadNumber; }
    public void setLoadNumber(String loadNumber) { this.loadNumber = loadNumber; }

    public String getPlotNumber() { return plotNumber; }
    public void setPlotNumber(String plotNumber) { this.plotNumber = plotNumber; }

    public String getRaNo() { return raNo; }
    public void setRaNo(String raNo) { this.raNo = raNo; }

    public String getDrawingNo() { return drawingNo; }
    public void setDrawingNo(String drawingNo) { this.drawingNo = drawingNo; }

    public String getMarkNo() { return markNo; }
    public void setMarkNo(String markNo) { this.markNo = markNo; }

    public Double getEachMarkLength() { return eachMarkLength; }
    public void setEachMarkLength(Double eachMarkLength) { this.eachMarkLength = eachMarkLength; }

    public Integer getMarkQty() { return markQty; }
    public void setMarkQty(Integer markQty) { this.markQty = markQty; }

    public Double getTotalMarkLength() { return totalMarkLength; }
    public void setTotalMarkLength(Double totalMarkLength) { this.totalMarkLength = totalMarkLength; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }
}