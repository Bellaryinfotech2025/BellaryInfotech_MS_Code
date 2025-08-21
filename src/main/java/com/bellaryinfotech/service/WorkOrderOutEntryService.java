package com.bellaryinfotech.service;
 
import java.util.List;

import com.bellaryinfotech.DTO.WorkOrderOutEntryDTO;

public interface WorkOrderOutEntryService {
    
    WorkOrderOutEntryDTO saveWorkOrderOutEntry(WorkOrderOutEntryDTO workOrderOutEntryDTO);
    
    WorkOrderOutEntryDTO updateWorkOrderOutEntry(Long id, WorkOrderOutEntryDTO workOrderOutEntryDTO);
    
    WorkOrderOutEntryDTO getWorkOrderOutEntryById(Long id);
    
    List<WorkOrderOutEntryDTO> getAllWorkOrderOutEntries();
    
    void deleteWorkOrderOutEntry(Long id);
    
    List<WorkOrderOutEntryDTO> searchByClientName(String clientName);
    
    List<WorkOrderOutEntryDTO> searchByReferenceWorkOrder(String referenceWorkOrder);
    
    List<WorkOrderOutEntryDTO> searchByWorkLocation(String workLocation);
    
    List<WorkOrderOutEntryDTO> searchBySubAgencyName(String subAgencyName);
}
