package com.bellaryinfotech.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class RawMaterialEntryDTO {
        private List<WorkOrderDTO> workOrders;
    private List<ServiceEntryDTO> serviceEntries;
    private String createdBy;
    private String createdDate;

        // Constructors
    public RawMaterialEntryDTO() {}

        public RawMaterialEntryDTO(List<WorkOrderDTO> workOrders, List<ServiceEntryDTO> serviceEntries,
                               String createdBy, String createdDate) {
        this.workOrders = workOrders;
        this.serviceEntries = serviceEntries;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

        // Getters and Setters
    public List<WorkOrderDTO> getWorkOrders() { return workOrders; }
    public void setWorkOrders(List<WorkOrderDTO> workOrders) { this.workOrders = workOrders; }

        public List<ServiceEntryDTO> getServiceEntries() { return serviceEntries; }
    public void setServiceEntries(List<ServiceEntryDTO> serviceEntries) { this.serviceEntries = serviceEntries; }

        public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

        public String getCreatedDate() { return createdDate; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }

        // Inner classes for nested DTOs
    public static class WorkOrderDTO {
        private String id;
        private String workOrder;
        // NEW: Added orderId to map with bits_po_entry_header
        private Long orderId;

                public WorkOrderDTO() {}

                public WorkOrderDTO(String id, String workOrder, Long orderId) {
            this.id = id;
            this.workOrder = workOrder;
            this.orderId = orderId;
        }

                public String getId() { return id; }
        public void setId(String id) { this.id = id; }

                public String getWorkOrder() { return workOrder; }
        public void setWorkOrder(String workOrder) { this.workOrder = workOrder; }

                public Long getOrderId() { return orderId; }
        public void setOrderId(Long orderId) { this.orderId = orderId; }
    }

        public static class ServiceEntryDTO {
        private String section;
        private String sectionCode; // NEW: Added sectionCode field
        private String materialCode;
        private String width;
        private String length;
        private String qty;
        private String uom;
        private String totalWeight;
        private String totalReceived;
        private String totalTransferredToOther;
        private String receiverName;
        private String remarks; // NEW: Added remarks field
        private String vehicleNumber;
        private String documentNo;
        private String documentDate;
        private String receivedDate;
        private String receiptType; // NEW: Added receiptType field

                public ServiceEntryDTO() {}

                public ServiceEntryDTO(String section, String sectionCode, String materialCode, String width,
                               String length, String qty, String uom, String totalWeight, String totalReceived,
                               String totalTransferredToOther, String receiverName, String remarks,
                               String vehicleNumber, String documentNo, String documentDate, String receivedDate, String receiptType) { // NEW: Added receiptType
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
            this.receiptType = receiptType; // NEW: Set receiptType
        }

                // Getters and Setters
        public String getSection() { return section; }
        public void setSection(String section) { this.section = section; }

                // NEW: Getter and Setter for sectionCode
        public String getSectionCode() { return sectionCode; }
        public void setSectionCode(String sectionCode) { this.sectionCode = sectionCode; }

                public String getMaterialCode() { return materialCode; }
        public void setMaterialCode(String materialCode) { this.materialCode = materialCode; }

                public String getWidth() { return width; }
        public void setWidth(String width) { this.width = width; }

                public String getLength() { return length; }
        public void setLength(String length) { this.length = length; }

                public String getQty() { return qty; }
        public void setQty(String qty) { this.qty = qty; }

                public String getUom() { return uom; }
        public void setUom(String uom) { this.uom = uom; }

                public String getTotalWeight() { return totalWeight; }
        public void setTotalWeight(String totalWeight) { this.totalWeight = totalWeight; }

                public String getTotalReceived() { return totalReceived; }
        public void setTotalReceived(String totalReceived) { this.totalReceived = totalReceived; }

                public String getTotalTransferredToOther() { return totalTransferredToOther; }
        public void setTotalTransferredToOther(String totalTransferredToOther) { this.totalTransferredToOther = totalTransferredToOther; }

                public String getReceiverName() { return receiverName; }
        public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

                // NEW: Getter and Setter for remarks
        public String getRemarks() { return remarks; }
        public void setRemarks(String remarks) { this.remarks = remarks; }

                public String getVehicleNumber() { return vehicleNumber; }
        public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
        public String getDocumentNo() { return documentNo; }
        public void setDocumentNo(String documentNo) { this.documentNo = documentNo; }
        public String getDocumentDate() { return documentDate; }
        public void setDocumentDate(String documentDate) { this.documentDate = documentDate; }
        public String getReceivedDate() { return receivedDate; }
        public void setReceivedDate(String receivedDate) { this.receivedDate = receivedDate; }
        public String getReceiptType() { return receiptType; } // NEW: Getter for receiptType
        public void setReceiptType(String receiptType) { this.receiptType = receiptType; } // NEW: Setter for receiptType
    }

        @Override
    public String toString() {
        return "RawMaterialEntryDTO{" +
                "workOrders=" + workOrders +
                ", serviceEntries=" + serviceEntries +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}