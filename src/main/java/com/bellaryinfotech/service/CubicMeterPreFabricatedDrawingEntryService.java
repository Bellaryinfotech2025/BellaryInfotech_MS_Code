package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.CubicMeterPreFabricatedDrawingEntryDTO;
import com.bellaryinfotech.model.CubicMeterPreFabricatedDrawingEntry;

import java.util.List;

public interface CubicMeterPreFabricatedDrawingEntryService {
    
    List<CubicMeterPreFabricatedDrawingEntry> saveCubicMeterPreFabricatedEntries(CubicMeterPreFabricatedDrawingEntryDTO dto);
    
    List<CubicMeterPreFabricatedDrawingEntry> getCubicMeterPreFabricatedEntriesByWorkOrder(String workOrder);
    
    List<CubicMeterPreFabricatedDrawingEntry> getCubicMeterPreFabricatedEntriesByMarkNo(String markNo);
    
    List<CubicMeterPreFabricatedDrawingEntry> getCubicMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber(String workOrder, String vehicleNumber);
    
    List<CubicMeterPreFabricatedDrawingEntry> getCubicMeterPreFabricatedEntriesByWorkOrderAndLoadNumber(String workOrder, String loadNumber);
    
    List<CubicMeterPreFabricatedDrawingEntry> getAllCubicMeterPreFabricatedEntries();
    
    CubicMeterPreFabricatedDrawingEntry updateCubicMeterPreFabricatedEntry(Long id, CubicMeterPreFabricatedDrawingEntry entry);
    
    void deleteCubicMeterPreFabricatedEntry(Long id);
    
    void deleteCubicMeterPreFabricatedEntriesByWorkOrder(String workOrder);
    
    void deleteCubicMeterPreFabricatedEntriesByMarkNo(String markNo);
    
    List<String> getDistinctWorkOrders();
}
