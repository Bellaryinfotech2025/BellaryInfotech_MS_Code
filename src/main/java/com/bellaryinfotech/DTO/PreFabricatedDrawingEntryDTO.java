package com.bellaryinfotech.DTO;

import java.util.List;

public class PreFabricatedDrawingEntryDTO {
    
    private String workOrder;
    private Long orderId;
    private String clientName;
    private String serviceDescription;
    private String uom;
    private String department;
    private String workLocation;
    private String vehicleNumber;
    private String loadNumber;
    private String plotNumber;
    private List<PreFabricatedEntryRowDTO> entries;
    
    // Constructors
    public PreFabricatedDrawingEntryDTO() {}
    
    public PreFabricatedDrawingEntryDTO(String workOrder, Long orderId, String clientName, 
                                      String serviceDescription, String uom, String department, 
                                      String workLocation, String vehicleNumber, String loadNumber, 
                                      String plotNumber, List<PreFabricatedEntryRowDTO> entries) {
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
        this.entries = entries;
    }
    
    // Getters and Setters
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
    
    public List<PreFabricatedEntryRowDTO> getEntries() { return entries; }
    public void setEntries(List<PreFabricatedEntryRowDTO> entries) { this.entries = entries; }
    
    // Inner class for entry rows
    public static class PreFabricatedEntryRowDTO {
        private String drawingNo;
        private String markNo;
        private Double markWeight;
        private Integer markQty;
        private Double totalMarkWeight;
        private String remarks;
        
        // Constructors
        public PreFabricatedEntryRowDTO() {}
        
        public PreFabricatedEntryRowDTO(String drawingNo, String markNo, Double markWeight, 
                                      Integer markQty, Double totalMarkWeight, String remarks) {
            this.drawingNo = drawingNo;
            this.markNo = markNo;
            this.markWeight = markWeight;
            this.markQty = markQty;
            this.totalMarkWeight = totalMarkWeight;
            this.remarks = remarks;
        }
        
        // Getters and Setters
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
    }
}
