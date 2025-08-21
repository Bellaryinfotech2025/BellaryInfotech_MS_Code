package com.bellaryinfotech.DTO;
 

import java.util.List;

public class WorkOrderOutDetailsDTO {
    
    private WorkOrderOutEntryDTO workOrderDetails;
    private List<WorkOrderOutEntryDTO> serviceLines;
    
    // Constructors
    public WorkOrderOutDetailsDTO() {}
    
    public WorkOrderOutDetailsDTO(WorkOrderOutEntryDTO workOrderDetails, List<WorkOrderOutEntryDTO> serviceLines) {
        this.workOrderDetails = workOrderDetails;
        this.serviceLines = serviceLines;
    }
    
    // Getters and Setters
    public WorkOrderOutEntryDTO getWorkOrderDetails() {
        return workOrderDetails;
    }
    
    public void setWorkOrderDetails(WorkOrderOutEntryDTO workOrderDetails) {
        this.workOrderDetails = workOrderDetails;
    }
    
    public List<WorkOrderOutEntryDTO> getServiceLines() {
        return serviceLines;
    }
    
    public void setServiceLines(List<WorkOrderOutEntryDTO> serviceLines) {
        this.serviceLines = serviceLines;
    }
}
