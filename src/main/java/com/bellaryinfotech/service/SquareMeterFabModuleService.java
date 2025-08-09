package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.SquareMeterFabModuleDto;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SquareMeterFabModuleService {
    
    // Create operations
    SquareMeterFabModuleDto createSquareMeterFabModule(SquareMeterFabModuleDto squareMeterFabModuleDto);
    List<SquareMeterFabModuleDto> createMultipleSquareMeterFabModules(List<SquareMeterFabModuleDto> squareMeterFabModuleDtos);
    
    // Read operations
    List<SquareMeterFabModuleDto> getAllSquareMeterFabModules();
    Optional<SquareMeterFabModuleDto> getSquareMeterFabModuleById(Long id);
    List<SquareMeterFabModuleDto> getSquareMeterFabModulesByWorkOrder(String workOrder);
    List<SquareMeterFabModuleDto> getSquareMeterFabModulesByWorkOrderAndBuilding(String workOrder, String buildingName);
    List<SquareMeterFabModuleDto> getSquareMeterFabModulesByWorkOrderBuildingDrawing(String workOrder, String buildingName, String drawingNo);
    List<SquareMeterFabModuleDto> getSquareMeterFabModulesByWorkOrderBuildingDrawingMark(String workOrder, String buildingName, String drawingNo, String markNo);
    List<SquareMeterFabModuleDto> getSquareMeterFabModulesByRaNo(String raNo);
    
    // Search operations
    List<SquareMeterFabModuleDto> searchSquareMeterFabModules(String workOrder, String buildingName, String drawingNo, String markNo, String raNo);
    
    // Update operations
    SquareMeterFabModuleDto updateSquareMeterFabModule(Long id, SquareMeterFabModuleDto squareMeterFabModuleDto);
    SquareMeterFabModuleDto updateSquareMeterFabModuleStages(Long id, String cuttingStage, String fitUpStage, String weldingStage, String finishingStage);
    
    // Delete operations
    boolean deleteSquareMeterFabModule(Long id);
    boolean softDeleteSquareMeterFabModule(Long id);
    
    // Utility operations
    List<String> getDistinctWorkOrders();
    List<String> getDistinctBuildingNamesByWorkOrder(String workOrder);
    List<String> getDistinctDrawingNosByWorkOrderAndBuildingName(String workOrder, String buildingName);
    List<String> getDistinctMarkNosByWorkOrderBuildingNameAndDrawingNo(String workOrder, String buildingName, String drawingNo);
    List<String> getDistinctRaNumbers();
    List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder);
    
    // Statistics
    Map<String, Long> getSquareMeterFabModuleStatistics();
}
