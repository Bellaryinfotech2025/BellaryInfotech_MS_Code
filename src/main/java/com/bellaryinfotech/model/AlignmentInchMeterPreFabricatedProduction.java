package com.bellaryinfotech.model;
 

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alignment_inch_meter_pre_fabricated_production")
public class AlignmentInchMeterPreFabricatedProduction {
    private Long id;
    private String workOrder;
    private Long orderId;
    private String clientName;
    private String serviceDescription;
    private String uom;
    private String dataModule;
    private String department;
    private String workLocation;
    private String vehicleNumber;
    private String loadNumber;
    private String plotNumber;
    private String raNo;
    private String drawingNo;
    private String markNo;
    private Double eachMarkLength;
    private Integer markQty;
    private Double totalMarkLength;
    private String remarks;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "work_order", nullable = false)
    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder;
    }

    @Column(name = "order_id")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Column(name = "client_name")
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Column(name = "service_description")
    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    @Column(name = "uom")
    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    @Column(name = "data_module")
    public String getDataModule() {
        return dataModule;
    }

    public void setDataModule(String dataModule) {
        this.dataModule = dataModule;
    }

    @Column(name = "department")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Column(name = "work_location")
    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    @Column(name = "vehicle_number")
    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    @Column(name = "load_number")
    public String getLoadNumber() {
        return loadNumber;
    }

    public void setLoadNumber(String loadNumber) {
        this.loadNumber = loadNumber;
    }

    @Column(name = "plot_number")
    public String getPlotNumber() {
        return plotNumber;
    }

    public void setPlotNumber(String plotNumber) {
        this.plotNumber = plotNumber;
    }

    @Column(name = "ra_no")
    public String getRaNo() {
        return raNo;
    }

    public void setRaNo(String raNo) {
        this.raNo = raNo;
    }

    @Column(name = "drawing_no")
    public String getDrawingNo() {
        return drawingNo;
    }

    public void setDrawingNo(String drawingNo) {
        this.drawingNo = drawingNo;
    }

    @Column(name = "mark_no")
    public String getMarkNo() {
        return markNo;
    }

    public void setMarkNo(String markNo) {
        this.markNo = markNo;
    }

    @Column(name = "each_mark_length")
    public Double getEachMarkLength() {
        return eachMarkLength;
    }

    public void setEachMarkLength(Double eachMarkLength) {
        this.eachMarkLength = eachMarkLength;
    }

    @Column(name = "mark_qty")
    public Integer getMarkQty() {
        return markQty;
    }

    public void setMarkQty(Integer markQty) {
        this.markQty = markQty;
    }

    @Column(name = "total_mark_length")
    public Double getTotalMarkLength() {
        return totalMarkLength;
    }

    public void setTotalMarkLength(Double totalMarkLength) {
        this.totalMarkLength = totalMarkLength;
    }

    @Column(name = "remarks")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name = "created_date")
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "updated_date")
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}