package com.bellaryinfotech.service;
 
 
import java.util.List;

import com.bellaryinfotech.DTO.FabricationDrawingEntryDto;

public interface FabricationDrawingEntryService {
    
    List<FabricationDrawingEntryDto> createFabricationDrawingEntries(List<FabricationDrawingEntryDto> fabricationEntries);
    
    List<FabricationDrawingEntryDto> getFabricationEntriesByWorkOrderAndBuilding(String workOrder, String buildingName);
    
    List<FabricationDrawingEntryDto> getFabricationEntriesByDrawingAndMark(String drawingNo, String markNo);
    
    List<FabricationDrawingEntryDto> getFabricationEntriesByOrderId(Long orderId);
    
    List<FabricationDrawingEntryDto> getAllFabricationEntries();
    
    FabricationDrawingEntryDto getFabricationEntryById(Long id);
    
    void deleteFabricationEntry(Long id);
    
    List<FabricationDrawingEntryDto> getFabricationEntriesByMultipleFilters(String workOrder, String buildingName, String drawingNo, String markNo);
    
    Long getCountByWorkOrder(String workOrder);
    
    Long getCountByBuildingName(String buildingName);
    
    Long getCountByDrawingAndMark(String drawingNo, String markNo);
    
    boolean existsByLineId(Long lineId);
    
    void deleteByLineId(Long lineId);
}
