package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.InchMeterFabModuleDto;
import java.util.List;
import java.util.Optional;

public interface InchMeterFabModuleService {

    // Create operations
    InchMeterFabModuleDto createInchMeterFabModule(InchMeterFabModuleDto inchMeterFabModuleDto);
    List<InchMeterFabModuleDto> createMultipleInchMeterFabModules(List<InchMeterFabModuleDto> inchMeterFabModuleDtos);

    // Read operations
    List<InchMeterFabModuleDto> getAllInchMeterFabModules();
    Optional<InchMeterFabModuleDto> getInchMeterFabModuleById(Long id);
    List<InchMeterFabModuleDto> getInchMeterFabModulesByWorkOrder(String workOrder);
    List<InchMeterFabModuleDto> getInchMeterFabModulesByWorkOrderAndBuildingName(String workOrder, String buildingName);
    List<InchMeterFabModuleDto> getInchMeterFabModulesByWorkOrderAndBuildingNameAndDrawingNo(String workOrder, String buildingName, String drawingNo);
    List<InchMeterFabModuleDto> getInchMeterFabModulesByWorkOrderAndBuildingNameAndDrawingNoAndMarkNo(String workOrder, String buildingName, String drawingNo, String markNo);
    List<InchMeterFabModuleDto> getInchMeterFabModulesByRaNo(String raNo);
    List<InchMeterFabModuleDto> searchInchMeterFabModules(String workOrder, String buildingName, String drawingNo, String markNo, String raNo);

    // Update operations
    InchMeterFabModuleDto updateInchMeterFabModule(Long id, InchMeterFabModuleDto inchMeterFabModuleDto);
    InchMeterFabModuleDto updateInchMeterFabModuleStages(Long id, String cuttingStage, String fitUpStage, String weldingStage, String finishingStage);

    // Delete operations
    void deleteInchMeterFabModule(Long id);
    void softDeleteInchMeterFabModule(Long id);

    // Utility operations
    List<String> getDistinctWorkOrders();
    List<String> getDistinctBuildingNamesByWorkOrder(String workOrder);
    List<String> getDistinctDrawingNosByWorkOrderAndBuildingName(String workOrder, String buildingName);
    List<String> getDistinctMarkNosByWorkOrderAndBuildingNameAndDrawingNo(String workOrder, String buildingName, String drawingNo);
    List<String> getDistinctRaNumbers();
    List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder);
        
    // Statistics
    long getTotalRecordsCount();
    long getRecordsCountByWorkOrder(String workOrder);
    long getRecordsCountByRaNo(String raNo);
        
    // Validation
    boolean recordExists(String workOrder, String buildingName, String drawingNo, String markNo, String itemNo);
    
    
    
    
    
    
 // NEW CLIENT-BASED METHODS - Similar to MTR Fab Module
    List<String> getDistinctClientNames();
    List<String> getDistinctWorkOrdersByClientName(String clientName);
    List<String> getDistinctServiceDescriptionsByClientNameAndWorkOrder(String clientName, String workOrder);
    List<String> getDistinctRaNumbersByClientNameAndWorkOrderAndServiceDescription(String clientName, String workOrder, String serviceDescription);
    List<InchMeterFabModuleDto> searchInchMeterFabDataByFilters(String clientName, String workOrder, String serviceDescription, String raNumber);
    
    
    
}