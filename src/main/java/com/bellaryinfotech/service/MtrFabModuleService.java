package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.MtrFabModuleDto;
import java.util.List;
import java.util.Optional;

public interface MtrFabModuleService {

    // Create operations
    MtrFabModuleDto createMtrFabModule(MtrFabModuleDto mtrFabModuleDto);
    List<MtrFabModuleDto> createMultipleMtrFabModules(List<MtrFabModuleDto> mtrFabModuleDtos);

    // Read operations
    List<MtrFabModuleDto> getAllMtrFabModules();
    Optional<MtrFabModuleDto> getMtrFabModuleById(Long id);
    List<MtrFabModuleDto> getMtrFabModulesByWorkOrder(String workOrder);
    List<MtrFabModuleDto> getMtrFabModulesByWorkOrderAndBuildingName(String workOrder, String buildingName);
    List<MtrFabModuleDto> getMtrFabModulesByWorkOrderAndBuildingNameAndDrawingNo(String workOrder, String buildingName, String drawingNo);
    List<MtrFabModuleDto> getMtrFabModulesByWorkOrderAndBuildingNameAndDrawingNoAndMarkNo(String workOrder, String buildingName, String drawingNo, String markNo);
    List<MtrFabModuleDto> getMtrFabModulesByRaNo(String raNo);
    List<MtrFabModuleDto> searchMtrFabModules(String workOrder, String buildingName, String drawingNo, String markNo, String raNo);

    // Update operations
    MtrFabModuleDto updateMtrFabModule(Long id, MtrFabModuleDto mtrFabModuleDto);
    MtrFabModuleDto updateMtrFabModuleStages(Long id, String cuttingStage, String fitUpStage, String weldingStage, String finishingStage);

    // Delete operations
    void deleteMtrFabModule(Long id);
    void softDeleteMtrFabModule(Long id);

    // Utility operations
    List<String> getDistinctWorkOrders();
    List<String> getDistinctBuildingNamesByWorkOrder(String workOrder);
    List<String> getDistinctDrawingNosByWorkOrderAndBuildingName(String workOrder, String buildingName);
    List<String> getDistinctMarkNosByWorkOrderAndBuildingNameAndDrawingNo(String workOrder, String buildingName, String drawingNo);
    List<String> getDistinctRaNumbers();
    
    // Statistics
    long getTotalRecordsCount();
    long getRecordsCountByWorkOrder(String workOrder);
    long getRecordsCountByRaNo(String raNo);
    
    // Validation
    boolean recordExists(String workOrder, String buildingName, String drawingNo, String markNo, String itemNo);
    
    
    
    
    
    List<String> getDistinctClientNames();
    List<String> getDistinctWorkOrdersByClientName(String clientName);
    List<String> getDistinctServiceDescriptionsByClientNameAndWorkOrder(String clientName, String workOrder);
    List<String> getDistinctRaNumbersByClientNameAndWorkOrderAndServiceDescription(String clientName, String workOrder, String serviceDescription);
    List<MtrFabModuleDto> searchMtrFabDataByFilters(String clientName, String workOrder, String serviceDescription, String raNumber);

    
    
}