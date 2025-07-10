package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.RawMaterialEntryDTO;
import com.bellaryinfotech.model.RawMaterialEntry;

import java.util.List;

public interface RawMaterialEntryService {
    
    List<RawMaterialEntry> saveRawMaterialEntry(RawMaterialEntryDTO rawMaterialEntryDTO);
    
    List<RawMaterialEntry> getAllRawMaterialEntries();
    
    RawMaterialEntry getRawMaterialEntryById(Long id);
    
    List<RawMaterialEntry> getRawMaterialEntriesByWorkOrder(String workOrder);
    
    List<RawMaterialEntry> getRawMaterialEntriesBySection(String section);
    
    RawMaterialEntry updateRawMaterialEntry(Long id, RawMaterialEntry rawMaterialEntry);
    
    void deleteRawMaterialEntry(Long id);
    
    List<String> getDistinctWorkOrders();
    
    List<String> getDistinctSections();
    
    List<String> getDistinctUoms();
}