package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.CubicMeterFabModuleDTO;
import com.bellaryinfotech.model.CubicMeterFabModule;

import java.util.List;
import java.util.Optional;

public interface CubicMeterFabModuleService {

     
    CubicMeterFabModuleDTO createCubicMeterFabModule(CubicMeterFabModuleDTO cubicMeterFabModuleDTO);
    List<CubicMeterFabModuleDTO> createCubicMeterFabModuleEntries(List<CubicMeterFabModuleDTO> cubicMeterFabModuleDTOs);

     
    List<CubicMeterFabModuleDTO> getAllCubicMeterFabModules();
    Optional<CubicMeterFabModuleDTO> getCubicMeterFabModuleById(Long id);
    List<String> getDistinctWorkOrders();
    List<String> getDistinctBuildingNamesByWorkOrder(String workOrder);
    List<CubicMeterFabModuleDTO> getCubicMeterFabModulesByWorkOrderAndBuildingName(String workOrder, String buildingName);
    List<CubicMeterFabModuleDTO> getCubicMeterFabModulesByWorkOrderAndBuildingNameAndDrawingNoAndMarkNo(
            String workOrder, String buildingName, String drawingNo, String markNo);
    List<CubicMeterFabModuleDTO> getCubicMeterFabModulesByRaNo(String raNo);
    List<String> getDistinctRaNos();
     
    List<CubicMeterFabModuleDTO> getCubicMeterFabModulesByWorkOrder(String workOrder);


     
    CubicMeterFabModuleDTO updateCubicMeterFabModule(Long id, CubicMeterFabModuleDTO cubicMeterFabModuleDTO);
    CubicMeterFabModuleDTO updateCubicMeterFabModuleStatus(Long id, String status);

    
    boolean deleteCubicMeterFabModule(Long id);
    boolean softDeleteCubicMeterFabModule(Long id);

     
    List<CubicMeterFabModuleDTO> searchCubicMeterFabModules(String workOrder, String buildingName, 
                                                           String drawingNo, String markNo, String raNo);

     
    Long getActiveCubicMeterFabModulesCount();

     
    CubicMeterFabModuleDTO convertToDTO(CubicMeterFabModule cubicMeterFabModule);
    CubicMeterFabModule convertToEntity(CubicMeterFabModuleDTO cubicMeterFabModuleDTO);
    
    
    
    
    
    
    
    
    
    
    
    
 // NEW methods to add to cubic

  
 List<String> getDistinctClientNames();

  
 List<String> getDistinctWorkOrdersByClientName(String clientName);

 
 List<String> getDistinctServiceDescriptionsByClientAndWorkOrder(String clientName, String workOrder);

  
 List<String> getDistinctRaNumbersByClientWorkOrderAndService(String clientName, String workOrder, String serviceDescription);

  
 List<CubicMeterFabModuleDTO> searchByClientWorkOrderServiceAndRa(String clientName, String workOrder, String serviceDescription, String raNumber);

}
