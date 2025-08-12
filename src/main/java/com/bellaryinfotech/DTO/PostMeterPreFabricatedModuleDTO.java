package com.bellaryinfotech.DTO;

import java.util.List;

public class PostMeterPreFabricatedModuleDTO {
    
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
    private String raNo;
    private List<PostMeterPreFabricatedEntryRowDTO> entries;
    
    // Constructors
    public PostMeterPreFabricatedModuleDTO() {}
    
    public PostMeterPreFabricatedModuleDTO(String workOrder, Long orderId, String clientName, 
                                         String serviceDescription, String uom, String department, 
                                         String workLocation, String vehicleNumber, String loadNumber, 
                                         String plotNumber, String raNo, List<PostMeterPreFabricatedEntryRowDTO> entries) {
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
    
    public String getRaNo() { return raNo; }
    public void setRaNo(String raNo) { this.raNo = raNo; }
    
    public List<PostMeterPreFabricatedEntryRowDTO> getEntries() { return entries; }
    public void setEntries(List<PostMeterPreFabricatedEntryRowDTO> entries) { this.entries = entries; }
    
    // Inner class for entry rows
    public static class PostMeterPreFabricatedEntryRowDTO {
        private String drawingNo;
        private String markNo;
        private Double eachMarkLength;
        private Integer markQty;
        private Double totalMarkLength;
        private String remarks;
        
        // Constructors
        public PostMeterPreFabricatedEntryRowDTO() {}
        
        public PostMeterPreFabricatedEntryRowDTO(String drawingNo, String markNo, Double eachMarkLength, 
                                               Integer markQty, Double totalMarkLength, String remarks) {
            this.drawingNo = drawingNo;
            this.markNo = markNo;
            this.eachMarkLength = eachMarkLength;
            this.markQty = markQty;
            this.totalMarkLength = totalMarkLength;
            this.remarks = remarks;
        }
        
        // Getters and Setters
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
    }
}
