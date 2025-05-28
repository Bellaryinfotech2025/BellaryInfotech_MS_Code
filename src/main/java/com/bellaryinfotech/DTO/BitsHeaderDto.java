package com.bellaryinfotech.DTO;


import java.time.LocalDate;

public class BitsHeaderDto {
    
    private Long orderId;
    private String workOrder;
    private String plantLocation;
    private String department;
    private String workLocation;
    private LocalDate workOrderDate;
    private LocalDate completionDate;
    private Boolean ldApplicable;

    // Default constructor
    public BitsHeaderDto() {}

    // Constructor with parameters
    public BitsHeaderDto(String workOrder, String plantLocation, String department, 
                        String workLocation, LocalDate workOrderDate, 
                        LocalDate completionDate, Boolean ldApplicable) {
        this.workOrder = workOrder;
        this.plantLocation = plantLocation;
        this.department = department;
        this.workLocation = workLocation;
        this.workOrderDate = workOrderDate;
        this.completionDate = completionDate;
        this.ldApplicable = ldApplicable;
    }

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder;
    }

    public String getPlantLocation() {
        return plantLocation;
    }

    public void setPlantLocation(String plantLocation) {
        this.plantLocation = plantLocation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public LocalDate getWorkOrderDate() {
        return workOrderDate;
    }

    public void setWorkOrderDate(LocalDate workOrderDate) {
        this.workOrderDate = workOrderDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public Boolean getLdApplicable() {
        return ldApplicable;
    }

    public void setLdApplicable(Boolean ldApplicable) {
        this.ldApplicable = ldApplicable;
    }
}

