package com.bellaryinfotech.service;
 

 
import java.util.List;
import java.util.Optional;

import com.bellaryinfotech.DTO.SquareMeterModuleDTO;
import com.bellaryinfotech.model.SquareMeterModule;

public interface SquareMeterModuleService {

    // Create operations
    SquareMeterModuleDTO createSquareMeterModule(SquareMeterModuleDTO squareMeterModuleDTO);
    List<SquareMeterModuleDTO> createSquareMeterModules(List<SquareMeterModuleDTO> squareMeterModuleDTOs);

    // Read operations
    List<SquareMeterModuleDTO> getAllSquareMeterModules();
    Optional<SquareMeterModuleDTO> getSquareMeterModuleById(Long id);
    List<SquareMeterModuleDTO> getSquareMeterModulesByWorkOrder(String workOrder);
    List<SquareMeterModuleDTO> getSquareMeterModulesByMarkNo(String markNo);
    List<SquareMeterModuleDTO> getSquareMeterModulesByClientName(String clientName);
    List<SquareMeterModuleDTO> getSquareMeterModulesByBuildingName(String buildingName);
    List<SquareMeterModuleDTO> getSquareMeterModulesByWorkOrderAndBuildingName(String workOrder, String buildingName);
    List<SquareMeterModuleDTO> getSquareMeterModulesByWorkOrderAndMarkNo(String workOrder, String markNo);

    // Update operations
    SquareMeterModuleDTO updateSquareMeterModule(Long id, SquareMeterModuleDTO squareMeterModuleDTO);
    List<SquareMeterModuleDTO> updateSquareMeterModules(List<SquareMeterModuleDTO> squareMeterModuleDTOs);

    // Delete operations
    boolean deleteSquareMeterModule(Long id);
    boolean deleteSquareMeterModulesByWorkOrder(String workOrder);
    boolean deleteSquareMeterModulesByMarkNo(String markNo);

    // Search operations
    List<SquareMeterModuleDTO> searchSquareMeterModules(String workOrder, String buildingName, String markNo, String clientName);
    List<SquareMeterModuleDTO> searchSquareMeterModulesByMarkNo(String markNo);

    // Utility operations
    List<String> getDistinctWorkOrders();
    List<String> getDistinctBuildingNamesByWorkOrder(String workOrder);
    List<String> getDistinctMarkNosByWorkOrder(String workOrder);
    long countSquareMeterModulesByWorkOrder(String workOrder);
    Double getTotalAreaByWorkOrder(String workOrder);
    Double getTotalAreaByMarkNo(String markNo);

    // Validation operations
    boolean existsById(Long id);
    boolean existsByWorkOrder(String workOrder);
    boolean existsByMarkNo(String markNo);

    // Bulk operations
    List<SquareMeterModuleDTO> bulkInsertSquareMeterModules(List<SquareMeterModuleDTO> squareMeterModuleDTOs);
    boolean bulkDeleteSquareMeterModules(List<Long> ids);

    // Conversion methods
    SquareMeterModuleDTO convertToDTO(SquareMeterModule squareMeterModule);
    SquareMeterModule convertToEntity(SquareMeterModuleDTO squareMeterModuleDTO);
    List<SquareMeterModuleDTO> convertToDTOList(List<SquareMeterModule> squareMeterModules);
    List<SquareMeterModule> convertToEntityList(List<SquareMeterModuleDTO> squareMeterModuleDTOs);
}
