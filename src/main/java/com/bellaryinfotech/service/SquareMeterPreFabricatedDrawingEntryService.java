package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.SquareMeterPreFabricatedDrawingEntryDTO;
import com.bellaryinfotech.model.SquareMeterPreFabricatedDrawingEntry;

import java.util.List;

public interface SquareMeterPreFabricatedDrawingEntryService {
    
    List<SquareMeterPreFabricatedDrawingEntry> saveSquareMeterPreFabricatedEntries(SquareMeterPreFabricatedDrawingEntryDTO dto);
    
    List<SquareMeterPreFabricatedDrawingEntry> getSquareMeterPreFabricatedEntriesByWorkOrder(String workOrder);
    
    List<SquareMeterPreFabricatedDrawingEntry> getSquareMeterPreFabricatedEntriesByMarkNo(String markNo);
    
    List<SquareMeterPreFabricatedDrawingEntry> getSquareMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber(String workOrder, String vehicleNumber);
    
    List<SquareMeterPreFabricatedDrawingEntry> getSquareMeterPreFabricatedEntriesByWorkOrderAndLoadNumber(String workOrder, String loadNumber);
    
    List<SquareMeterPreFabricatedDrawingEntry> getAllSquareMeterPreFabricatedEntries();
    
    SquareMeterPreFabricatedDrawingEntry updateSquareMeterPreFabricatedEntry(Long id, SquareMeterPreFabricatedDrawingEntry entry);
    
    void deleteSquareMeterPreFabricatedEntry(Long id);
    
    void deleteSquareMeterPreFabricatedEntriesByWorkOrder(String workOrder);
    
    void deleteSquareMeterPreFabricatedEntriesByMarkNo(String markNo);
    
    List<String> getDistinctWorkOrders();
}
