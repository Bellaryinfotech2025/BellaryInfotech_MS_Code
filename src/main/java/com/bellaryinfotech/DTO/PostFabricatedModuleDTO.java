package com.bellaryinfotech.DTO;

import java.util.List;

public class PostFabricatedModuleDTO {
    
    private String workOrder;
    private String clientName;
    private String serviceDescription;
    private String uom;
    private String dataModule;
    private String buildingName;
    private String raNumber;
    private List<PostFabricatedEntryRowDTO> entries;
    
    // Constructors
    public PostFabricatedModuleDTO() {}
    
    public PostFabricatedModuleDTO(String workOrder, String clientName, String serviceDescription,
                                  String uom, String dataModule, String buildingName, String raNumber,
                                  List<PostFabricatedEntryRowDTO> entries) {
        this.workOrder = workOrder;
        this.clientName = clientName;
        this.serviceDescription = serviceDescription;
        this.uom = uom;
        this.dataModule = dataModule;
        this.buildingName = buildingName;
        this.raNumber = raNumber;
        this.entries = entries;
    }
    
    // Getters and Setters
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
    
    public List<PostFabricatedEntryRowDTO> getEntries() { return entries; }
    public void setEntries(List<PostFabricatedEntryRowDTO> entries) { this.entries = entries; }
    
    // Inner class for entry rows
    public static class PostFabricatedEntryRowDTO {
        private String vehicleNumber;
        private String loadNumber;
        private String plotNumber;
        private String drawingNo;
        private String markNo;
        private Double markWeight;
        private Integer markQty;
        private Double totalMarkWeight;
        private String remarks;
        
        // Constructors
        public PostFabricatedEntryRowDTO() {}
        
        public PostFabricatedEntryRowDTO(String vehicleNumber, String loadNumber, String plotNumber,
                                        String drawingNo, String markNo, Double markWeight, 
                                        Integer markQty, Double totalMarkWeight, String remarks) {
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
    }
}
