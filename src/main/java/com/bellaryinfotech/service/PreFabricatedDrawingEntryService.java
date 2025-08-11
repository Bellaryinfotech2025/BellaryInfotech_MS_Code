package com.bellaryinfotech.service;

import java.util.List;

import com.bellaryinfotech.DTO.PreFabricatedDrawingEntryDTO;
import com.bellaryinfotech.model.PreFabricatedDrawingEntry;

public interface PreFabricatedDrawingEntryService {
    
    List<PreFabricatedDrawingEntry> savePreFabricatedEntries(PreFabricatedDrawingEntryDTO dto);
    
    List<PreFabricatedDrawingEntry> getPreFabricatedEntriesByWorkOrder(String workOrder);
    
    List<PreFabricatedDrawingEntry> getPreFabricatedEntriesByWorkOrderAndVehicleNumber(String workOrder, String vehicleNumber);
    
    List<PreFabricatedDrawingEntry> getPreFabricatedEntriesByWorkOrderAndLoadNumber(String workOrder, String loadNumber);
    
    List<PreFabricatedDrawingEntry> getPreFabricatedEntriesByWorkOrderAndPlotNumber(String workOrder, String plotNumber);
    
    List<PreFabricatedDrawingEntry> getPreFabricatedEntriesByWorkOrderAndVehicleAndLoad(String workOrder, String vehicleNumber, String loadNumber);
    
    List<PreFabricatedDrawingEntry> getPreFabricatedEntriesByWorkOrderAndVehicleAndPlot(String workOrder, String vehicleNumber, String plotNumber);
    
    List<PreFabricatedDrawingEntry> getPreFabricatedEntriesByWorkOrderAndLoadAndPlot(String workOrder, String loadNumber, String plotNumber);
    
    List<PreFabricatedDrawingEntry> getPreFabricatedEntriesByWorkOrderAndVehicleAndLoadAndPlot(String workOrder, String vehicleNumber, String loadNumber, String plotNumber);
    
    List<String> getDistinctWorkOrders();
    
    List<String> getDistinctVehicleNumbersByWorkOrder(String workOrder);
    
    List<String> getDistinctLoadNumbersByWorkOrder(String workOrder);
    
    List<String> getDistinctPlotNumbersByWorkOrder(String workOrder);
    
    List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder);
    
    List<String> getDistinctClientNamesByWorkOrder(String workOrder);
    
    List<PreFabricatedDrawingEntry> getPreFabricatedEntriesByMarkNo(String markNo);
    
    PreFabricatedDrawingEntry updatePreFabricatedEntry(Long id, PreFabricatedDrawingEntry updatedEntry);
    
    void deletePreFabricatedEntry(Long id);
    
    void deletePreFabricatedEntriesByWorkOrder(String workOrder);
    
    void deletePreFabricatedEntriesByWorkOrderAndVehicleAndLoadAndPlot(String workOrder, String vehicleNumber, String loadNumber, String plotNumber);
}
