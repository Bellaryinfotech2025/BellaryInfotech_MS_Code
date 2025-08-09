package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.CubicMeterFabModuleDTO;
import com.bellaryinfotech.model.CubicMeterFabModule;

import java.util.List;
import java.util.Optional;

public interface CubicMeterFabModuleService {

    // Create operations
    CubicMeterFabModuleDTO createCubicMeterFabModule(CubicMeterFabModuleDTO cubicMeterFabModuleDTO);
    List<CubicMeterFabModuleDTO> createCubicMeterFabModuleEntries(List<CubicMeterFabModuleDTO> cubicMeterFabModuleDTOs);

    // Read operations
    List<CubicMeterFabModuleDTO> getAllCubicMeterFabModules();
    Optional<CubicMeterFabModuleDTO> getCubicMeterFabModuleById(Long id);
    List<String> getDistinctWorkOrders();
    List<String> getDistinctBuildingNamesByWorkOrder(String workOrder);
    List<CubicMeterFabModuleDTO> getCubicMeterFabModulesByWorkOrderAndBuildingName(String workOrder, String buildingName);
    List<CubicMeterFabModuleDTO> getCubicMeterFabModulesByWorkOrderAndBuildingNameAndDrawingNoAndMarkNo(
            String workOrder, String buildingName, String drawingNo, String markNo);
    List<CubicMeterFabModuleDTO> getCubicMeterFabModulesByRaNo(String raNo);
    List<String> getDistinctRaNos();
    // NEW: Get CubicMeterFabModules by work order only
    List<CubicMeterFabModuleDTO> getCubicMeterFabModulesByWorkOrder(String workOrder);


    // Update operations
    CubicMeterFabModuleDTO updateCubicMeterFabModule(Long id, CubicMeterFabModuleDTO cubicMeterFabModuleDTO);
    CubicMeterFabModuleDTO updateCubicMeterFabModuleStatus(Long id, String status);

    // Delete operations
    boolean deleteCubicMeterFabModule(Long id);
    boolean softDeleteCubicMeterFabModule(Long id);

    // Search operations
    List<CubicMeterFabModuleDTO> searchCubicMeterFabModules(String workOrder, String buildingName, 
                                                           String drawingNo, String markNo, String raNo);

    // Statistics
    Long getActiveCubicMeterFabModulesCount();

    // Utility methods
    CubicMeterFabModuleDTO convertToDTO(CubicMeterFabModule cubicMeterFabModule);
    CubicMeterFabModule convertToEntity(CubicMeterFabModuleDTO cubicMeterFabModuleDTO);
}
