package com.bellaryinfotech.DTO;

import java.util.List;

public class WorkOrderDetailsResponse {
    private WorkOrderOutEntryDTO workOrderDetails;
    private List<WorkOrderOutServiceLineDTO> serviceLines;

    // Getters and setters
    public WorkOrderOutEntryDTO getWorkOrderDetails() {
        return workOrderDetails;
    }

    public void setWorkOrderDetails(WorkOrderOutEntryDTO workOrderDetails) {
        this.workOrderDetails = workOrderDetails;
    }

    public List<WorkOrderOutServiceLineDTO> getServiceLines() {
        return serviceLines;
    }

    public void setServiceLines(List<WorkOrderOutServiceLineDTO> serviceLines) {
        this.serviceLines = serviceLines;
    }
}

 
 