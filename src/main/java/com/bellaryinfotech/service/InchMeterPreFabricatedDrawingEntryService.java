package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.InchMeterPreFabricatedDrawingEntryDTO;
import com.bellaryinfotech.model.InchMeterPreFabricatedDrawingEntry;

import java.util.List;

public interface InchMeterPreFabricatedDrawingEntryService {
    
    List<InchMeterPreFabricatedDrawingEntry> saveInchMeterPreFabricatedEntries(InchMeterPreFabricatedDrawingEntryDTO dto);
    
    List<InchMeterPreFabricatedDrawingEntry> getInchMeterPreFabricatedEntriesByWorkOrder(String workOrder);
    
    List<InchMeterPreFabricatedDrawingEntry> getInchMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber(String workOrder, String vehicleNumber);
    
    List<InchMeterPreFabricatedDrawingEntry> getInchMeterPreFabricatedEntriesByWorkOrderAndLoadNumber(String workOrder, String loadNumber);
    
    List<InchMeterPreFabricatedDrawingEntry> getInchMeterPreFabricatedEntriesByWorkOrderAndPlotNumber(String workOrder, String plotNumber);
    
    List<InchMeterPreFabricatedDrawingEntry> getInchMeterPreFabricatedEntriesByWorkOrderAndVehicleAndLoad(String workOrder, String vehicleNumber, String loadNumber);
    
    List<InchMeterPreFabricatedDrawingEntry> getInchMeterPreFabricatedEntriesByWorkOrderAndVehicleAndPlot(String workOrder, String vehicleNumber, String plotNumber);
    
    List<InchMeterPreFabricatedDrawingEntry> getInchMeterPreFabricatedEntriesByWorkOrderAndLoadAndPlot(String workOrder, String loadNumber, String plotNumber);
    
    List<InchMeterPreFabricatedDrawingEntry> getInchMeterPreFabricatedEntriesByWorkOrderAndVehicleAndLoadAndPlot(String workOrder, String vehicleNumber, String loadNumber, String plotNumber);
    
    List<String> getDistinctWorkOrders();
    
    List<String> getDistinctVehicleNumbersByWorkOrder(String workOrder);
    
    List<String> getDistinctLoadNumbersByWorkOrder(String workOrder);
    
    List<String> getDistinctPlotNumbersByWorkOrder(String workOrder);
    
    List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder);
    
    List<String> getDistinctClientNamesByWorkOrder(String workOrder);
    
    List<InchMeterPreFabricatedDrawingEntry> getInchMeterPreFabricatedEntriesByMarkNo(String markNo);
    
    InchMeterPreFabricatedDrawingEntry updateInchMeterPreFabricatedEntry(Long id, InchMeterPreFabricatedDrawingEntry updatedEntry);
    
    void deleteInchMeterPreFabricatedEntry(Long id);
    
    void deleteInchMeterPreFabricatedEntriesByWorkOrder(String workOrder);
    
    void deleteInchMeterPreFabricatedEntriesByWorkOrderAndVehicleAndLoadAndPlot(String workOrder, String vehicleNumber, String loadNumber, String plotNumber);
}
