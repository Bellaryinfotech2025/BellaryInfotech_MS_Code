package com.bellaryinfotech.service;

import java.util.List;
import java.util.Optional;

import com.bellaryinfotech.DTO.WorkOrderOutDrawingEntryDto;

public interface WorkOrderOutDrawingEntryService {
    
    // CRUD operations
    List<WorkOrderOutDrawingEntryDto> getAllEntries();
    Optional<WorkOrderOutDrawingEntryDto> getEntryById(Long id);
    List<WorkOrderOutDrawingEntryDto> createBulkEntries(List<WorkOrderOutDrawingEntryDto> entryDtos);
    WorkOrderOutDrawingEntryDto updateEntry(Long id, WorkOrderOutDrawingEntryDto entryDto);
    void deleteEntry(Long id);
    void deleteByDrawingAndMark(String drawingNo, String markNo);
    
    // Search operations
    List<WorkOrderOutDrawingEntryDto> searchByWorkOrder(String workOrder);
    List<WorkOrderOutDrawingEntryDto> searchByWorkOrderAndPlantLocation(String workOrder, String plantLocation);
    List<WorkOrderOutDrawingEntryDto> searchByDrawingNo(String drawingNo);
    List<WorkOrderOutDrawingEntryDto> searchByMarkNo(String markNo);
    List<WorkOrderOutDrawingEntryDto> searchByDrawingAndMark(String drawingNo, String markNo);
    List<WorkOrderOutDrawingEntryDto> searchByOrderId(Long orderId);
    List<WorkOrderOutDrawingEntryDto> searchByMultipleCriteria(String workOrder, String plantLocation, String drawingNo, String markNo);
    
    // Dropdown data
    List<String> getDistinctWorkOrders();
    List<String> getDistinctPlantLocationsByWorkOrder(String workOrder);
    List<String> getDistinctDrawingNumbersByWorkOrderAndPlantLocation(String workOrder, String plantLocation);
    List<String> getDistinctMarkNumbersByWorkOrderAndPlantLocation(String workOrder, String plantLocation);
    
    // Edit operations
    List<WorkOrderOutDrawingEntryDto> getEntriesForEditingByMarkNo(String markNo);
    Optional<WorkOrderOutDrawingEntryDto> getDrawingEntryByMarkNo(String markNo);
    
    // Utility
    boolean existsByDrawingAndMark(String drawingNo, String markNo);
}
