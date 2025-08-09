package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.CubicMeterModuleDTO;
import java.util.List;
import java.util.Optional;

public interface CubicMeterModuleService {

    // Create operations
    CubicMeterModuleDTO createCubicMeterModule(CubicMeterModuleDTO cubicMeterModuleDTO);
    List<CubicMeterModuleDTO> createCubicMeterModules(List<CubicMeterModuleDTO> cubicMeterModuleDTOs);

    // Read operations
    List<CubicMeterModuleDTO> getAllCubicMeterModules();
    Optional<CubicMeterModuleDTO> getCubicMeterModuleById(Long id);
    List<CubicMeterModuleDTO> getCubicMeterModulesByWorkOrder(String workOrder);
    List<CubicMeterModuleDTO> searchCubicMeterModulesByMarkNo(String markNo);
    List<CubicMeterModuleDTO> getCubicMeterModulesByClientName(String clientName);
    List<CubicMeterModuleDTO> getCubicMeterModulesByBuildingName(String buildingName);
    List<CubicMeterModuleDTO> getCubicMeterModulesByWorkOrderAndBuildingName(String workOrder, String buildingName);

    // Update operations
    CubicMeterModuleDTO updateCubicMeterModule(Long id, CubicMeterModuleDTO cubicMeterModuleDTO);
    List<CubicMeterModuleDTO> updateCubicMeterModules(List<CubicMeterModuleDTO> cubicMeterModuleDTOs);

    // Delete operations
    boolean deleteCubicMeterModule(Long id);
    boolean deleteCubicMeterModulesByWorkOrder(String workOrder);
    boolean deleteCubicMeterModulesByMarkNo(String markNo);

    // Search operations
    List<CubicMeterModuleDTO> searchCubicMeterModules(String workOrder, String buildingName, String markNo, String clientName);

    // Utility operations
    List<String> getDistinctWorkOrders();
    List<String> getDistinctBuildingNamesByWorkOrder(String workOrder);
    List<String> getDistinctMarkNosByWorkOrder(String workOrder);
    long countCubicMeterModulesByWorkOrder(String workOrder);
    Double getTotalVolumeByWorkOrder(String workOrder);
    Double getTotalVolumeByMarkNo(String markNo);

    // Bulk operations
    List<CubicMeterModuleDTO> bulkInsertCubicMeterModules(List<CubicMeterModuleDTO> cubicMeterModuleDTOs);
    boolean bulkDeleteCubicMeterModules(List<Long> ids);

    // Validation operations
    boolean existsById(Long id);
    boolean existsByWorkOrder(String workOrder);
    boolean existsByMarkNo(String markNo);
}
