package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.MeterPreFabricatedDrawingEntryDTO;
import com.bellaryinfotech.model.MeterPreFabricatedDrawingEntry;

import java.util.List;

public interface MeterPreFabricatedDrawingEntryService {
    
    List<MeterPreFabricatedDrawingEntry> saveMeterPreFabricatedEntries(MeterPreFabricatedDrawingEntryDTO dto);
    
    List<MeterPreFabricatedDrawingEntry> getMeterPreFabricatedEntriesByWorkOrder(String workOrder);
    
    List<MeterPreFabricatedDrawingEntry> getMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber(String workOrder, String vehicleNumber);
    
    List<MeterPreFabricatedDrawingEntry> getMeterPreFabricatedEntriesByWorkOrderAndLoadNumber(String workOrder, String loadNumber);
    
    List<MeterPreFabricatedDrawingEntry> getMeterPreFabricatedEntriesByWorkOrderAndPlotNumber(String workOrder, String plotNumber);
    
    List<MeterPreFabricatedDrawingEntry> getMeterPreFabricatedEntriesByWorkOrderAndVehicleAndLoad(String workOrder, String vehicleNumber, String loadNumber);
    
    List<MeterPreFabricatedDrawingEntry> getMeterPreFabricatedEntriesByWorkOrderAndVehicleAndPlot(String workOrder, String vehicleNumber, String plotNumber);
    
    List<MeterPreFabricatedDrawingEntry> getMeterPreFabricatedEntriesByWorkOrderAndLoadAndPlot(String workOrder, String loadNumber, String plotNumber);
    
    List<MeterPreFabricatedDrawingEntry> getMeterPreFabricatedEntriesByWorkOrderAndVehicleAndLoadAndPlot(String workOrder, String vehicleNumber, String loadNumber, String plotNumber);
    
    List<String> getDistinctWorkOrders();
    
    List<String> getDistinctVehicleNumbersByWorkOrder(String workOrder);
    
    List<String> getDistinctLoadNumbersByWorkOrder(String workOrder);
    
    List<String> getDistinctPlotNumbersByWorkOrder(String workOrder);
    
    List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder);
    
    List<String> getDistinctClientNamesByWorkOrder(String workOrder);
    
    List<MeterPreFabricatedDrawingEntry> getMeterPreFabricatedEntriesByMarkNo(String markNo);
    
    MeterPreFabricatedDrawingEntry updateMeterPreFabricatedEntry(Long id, MeterPreFabricatedDrawingEntry updatedEntry);
    
    void deleteMeterPreFabricatedEntry(Long id);
    
    void deleteMeterPreFabricatedEntriesByWorkOrder(String workOrder);
    
    void deleteMeterPreFabricatedEntriesByWorkOrderAndVehicleAndLoadAndPlot(String workOrder, String vehicleNumber, String loadNumber, String plotNumber);
}
