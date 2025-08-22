package com.bellaryinfotech.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.bellaryinfotech.DTO.WorkOrderOutDrawingEntryDto;

public interface WorkOrderOutDrawingEntryService {
    
     
    List<WorkOrderOutDrawingEntryDto> getAllEntries();
    Optional<WorkOrderOutDrawingEntryDto> getEntryById(Long id);
    List<WorkOrderOutDrawingEntryDto> createBulkEntries(List<WorkOrderOutDrawingEntryDto> entryDtos);
    WorkOrderOutDrawingEntryDto updateEntry(Long id, WorkOrderOutDrawingEntryDto entryDto);
    void deleteEntry(Long id);
    void deleteByDrawingAndMark(String drawingNo, String markNo);
    
     
    List<WorkOrderOutDrawingEntryDto> searchByWorkOrder(String workOrder);
    List<WorkOrderOutDrawingEntryDto> searchByWorkOrderAndPlantLocation(String workOrder, String plantLocation);
    List<WorkOrderOutDrawingEntryDto> searchByDrawingNo(String drawingNo);
    List<WorkOrderOutDrawingEntryDto> searchByMarkNo(String markNo);
    List<WorkOrderOutDrawingEntryDto> searchByDrawingAndMark(String drawingNo, String markNo);
    List<WorkOrderOutDrawingEntryDto> searchByOrderId(Long orderId);
    List<WorkOrderOutDrawingEntryDto> searchByMultipleCriteria(String workOrder, String plantLocation, String drawingNo, String markNo);
    
     
    List<String> getDistinctClientNames();
    List<String> getDistinctWorkOrdersByClient(String clientName);
    List<String> getDistinctServiceDescByWorkOrder(String workOrder);
    List<String> getDistinctUOMByWorkOrderAndService(String workOrder, String serviceDescription);
    List<String> getDistinctDataModulesByWorkOrder(String workOrder);
    Map<String, String> getSubAgencyDetailsByWorkOrder(String workOrder);
    
    
    List<String> getDistinctReferenceWorkOrdersByClient(String clientName);
    List<String> getDistinctServiceDescByReferenceWorkOrder(String referenceWorkOrder);
    List<String> getDistinctUOMByReferenceWorkOrderAndService(String referenceWorkOrder, String serviceDescription);
    List<String> getDistinctDataModulesByReferenceWorkOrderAndServiceAndUOM(String referenceWorkOrder, String serviceDescription, String uom);
    List<String> getDistinctSubAgencyNamesByReferenceWorkOrder(String referenceWorkOrder);
    
     
    List<String> getDistinctWorkOrders();
    List<String> getDistinctPlantLocationsByWorkOrder(String workOrder);
    List<String> getDistinctDrawingNumbersByWorkOrderAndPlantLocation(String workOrder, String plantLocation);
    List<String> getDistinctMarkNumbersByWorkOrderAndPlantLocation(String workOrder, String plantLocation);
    
     
    List<WorkOrderOutDrawingEntryDto> getEntriesForEditingByMarkNo(String markNo);
    Optional<WorkOrderOutDrawingEntryDto> getDrawingEntryByMarkNo(String markNo);
    
    
    boolean existsByDrawingAndMark(String drawingNo, String markNo);
}
