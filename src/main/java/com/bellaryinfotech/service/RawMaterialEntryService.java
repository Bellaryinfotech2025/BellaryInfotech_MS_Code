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
    
    // NEW: Get entries by section code
    List<RawMaterialEntry> getRawMaterialEntriesBySectionCode(String sectionCode);
    
    RawMaterialEntry updateRawMaterialEntry(Long id, RawMaterialEntry rawMaterialEntry);
    
    void deleteRawMaterialEntry(Long id);
    
    List<String> getDistinctWorkOrders();
    
    List<String> getDistinctSections();
    
    // NEW: Get distinct section codes
    List<String> getDistinctSectionCodes();
    
    List<String> getDistinctUoms();
}
