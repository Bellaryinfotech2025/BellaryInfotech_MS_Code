package com.bellaryinfotech.DTO;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "PurchaseOrders")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlExportDto {
    
    @XmlElement(name = "PurchaseOrder")
    private List<PurchaseOrderXml> purchaseOrders;
    
    public XmlExportDto() {}
    
    public XmlExportDto(List<PurchaseOrderXml> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }
    
    public List<PurchaseOrderXml> getPurchaseOrders() {
        return purchaseOrders;
    }
    
    public void setPurchaseOrders(List<PurchaseOrderXml> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class PurchaseOrderXml {
        
        // Header Information - ONLY EXISTING FIELDS
        @XmlElement(name = "OrderId")
        private Long orderId;
        
        @XmlElement(name = "WorkOrder")
        private String workOrder;
        
        @XmlElement(name = "PlantLocation")
        private String plantLocation;
        
        @XmlElement(name = "Department")
        private String department;
        
        @XmlElement(name = "WorkLocation")
        private String workLocation;
        
        @XmlElement(name = "WorkOrderDate")
        private String workOrderDate;
        
        @XmlElement(name = "CompletionDate")
        private String completionDate;
        
        @XmlElement(name = "LdApplicable")
        private Boolean ldApplicable;
        
        @XmlElement(name = "ScrapAllowanceVisiblePercent")
        private String scrapAllowanceVisiblePercent;
        
        @XmlElement(name = "ScrapAllowanceInvisiblePercent")
        private String scrapAllowanceInvisiblePercent;
        
        @XmlElement(name = "MaterialIssueType")
        private String materialIssueType;
        
        // Lines Information
        @XmlElementWrapper(name = "OrderLines")
        @XmlElement(name = "OrderLine")
        private List<OrderLineXml> orderLines;
        
        // Constructors
        public PurchaseOrderXml() {}
        
        // Getters and Setters - ONLY FOR EXISTING FIELDS
        public Long getOrderId() { return orderId; }
        public void setOrderId(Long orderId) { this.orderId = orderId; }
        
        public String getWorkOrder() { return workOrder; }
        public void setWorkOrder(String workOrder) { this.workOrder = workOrder; }
        
        public String getPlantLocation() { return plantLocation; }
        public void setPlantLocation(String plantLocation) { this.plantLocation = plantLocation; }
        
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
        
        public String getWorkLocation() { return workLocation; }
        public void setWorkLocation(String workLocation) { this.workLocation = workLocation; }
        
        public String getWorkOrderDate() { return workOrderDate; }
        public void setWorkOrderDate(String workOrderDate) { this.workOrderDate = workOrderDate; }
        
        public String getCompletionDate() { return completionDate; }
        public void setCompletionDate(String completionDate) { this.completionDate = completionDate; }
        
        public Boolean getLdApplicable() { return ldApplicable; }
        public void setLdApplicable(Boolean ldApplicable) { this.ldApplicable = ldApplicable; }
        
        public String getScrapAllowanceVisiblePercent() { return scrapAllowanceVisiblePercent; }
        public void setScrapAllowanceVisiblePercent(String scrapAllowanceVisiblePercent) { 
            this.scrapAllowanceVisiblePercent = scrapAllowanceVisiblePercent; 
        }
        
        public String getScrapAllowanceInvisiblePercent() { return scrapAllowanceInvisiblePercent; }
        public void setScrapAllowanceInvisiblePercent(String scrapAllowanceInvisiblePercent) { 
            this.scrapAllowanceInvisiblePercent = scrapAllowanceInvisiblePercent; 
        }
        
        public String getMaterialIssueType() { return materialIssueType; }
        public void setMaterialIssueType(String materialIssueType) { this.materialIssueType = materialIssueType; }
        
        public List<OrderLineXml> getOrderLines() { return orderLines; }
        public void setOrderLines(List<OrderLineXml> orderLines) { this.orderLines = orderLines; }
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class OrderLineXml {
        
        // Line Information - ONLY EXISTING FIELDS
        @XmlElement(name = "LineId")
        private Long lineId;
        
        @XmlElement(name = "OrderId")
        private Long orderId;
        
        @XmlElement(name = "LineNumber")
        private String lineNumber;
        
        @XmlElement(name = "SerialNumber")
        private String serNo;
        
        @XmlElement(name = "ServiceCode")
        private String serviceCode;
        
        @XmlElement(name = "ServiceDescription")
        private String serviceDesc;
        
        @XmlElement(name = "Quantity")
        private String qty;
        
        @XmlElement(name = "UnitOfMeasure")
        private String uom;
        
        @XmlElement(name = "UnitPrice")
        private String unitPrice;
        
        @XmlElement(name = "TotalPrice")
        private String totalPrice;
        
        // Constructors
        public OrderLineXml() {}
        
        // Getters and Setters - ONLY FOR EXISTING FIELDS
        public Long getLineId() { return lineId; }
        public void setLineId(Long lineId) { this.lineId = lineId; }
        
        public Long getOrderId() { return orderId; }
        public void setOrderId(Long orderId) { this.orderId = orderId; }
        
        public String getLineNumber() { return lineNumber; }
        public void setLineNumber(String lineNumber) { this.lineNumber = lineNumber; }
        
        public String getSerNo() { return serNo; }
        public void setSerNo(String serNo) { this.serNo = serNo; }
        
        public String getServiceCode() { return serviceCode; }
        public void setServiceCode(String serviceCode) { this.serviceCode = serviceCode; }
        
        public String getServiceDesc() { return serviceDesc; }
        public void setServiceDesc(String serviceDesc) { this.serviceDesc = serviceDesc; }
        
        public String getQty() { return qty; }
        public void setQty(String qty) { this.qty = qty; }
        
        public String getUom() { return uom; }
        public void setUom(String uom) { this.uom = uom; }
        
        public String getUnitPrice() { return unitPrice; }
        public void setUnitPrice(String unitPrice) { this.unitPrice = unitPrice; }
        
        public String getTotalPrice() { return totalPrice; }
        public void setTotalPrice(String totalPrice) { this.totalPrice = totalPrice; }
    }
}
