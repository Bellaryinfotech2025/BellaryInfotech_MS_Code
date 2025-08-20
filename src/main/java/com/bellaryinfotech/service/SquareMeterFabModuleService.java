package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.SquareMeterFabModuleDto;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SquareMeterFabModuleService {
    
     
    SquareMeterFabModuleDto createSquareMeterFabModule(SquareMeterFabModuleDto squareMeterFabModuleDto);
    List<SquareMeterFabModuleDto> createMultipleSquareMeterFabModules(List<SquareMeterFabModuleDto> squareMeterFabModuleDtos);
    
    
    List<SquareMeterFabModuleDto> getAllSquareMeterFabModules();
    Optional<SquareMeterFabModuleDto> getSquareMeterFabModuleById(Long id);
    List<SquareMeterFabModuleDto> getSquareMeterFabModulesByWorkOrder(String workOrder);
    List<SquareMeterFabModuleDto> getSquareMeterFabModulesByWorkOrderAndBuilding(String workOrder, String buildingName);
    List<SquareMeterFabModuleDto> getSquareMeterFabModulesByWorkOrderBuildingDrawing(String workOrder, String buildingName, String drawingNo);
    List<SquareMeterFabModuleDto> getSquareMeterFabModulesByWorkOrderBuildingDrawingMark(String workOrder, String buildingName, String drawingNo, String markNo);
    List<SquareMeterFabModuleDto> getSquareMeterFabModulesByRaNo(String raNo);
    
     
    List<SquareMeterFabModuleDto> searchSquareMeterFabModules(String workOrder, String buildingName, String drawingNo, String markNo, String raNo);
    
     
    SquareMeterFabModuleDto updateSquareMeterFabModule(Long id, SquareMeterFabModuleDto squareMeterFabModuleDto);
    SquareMeterFabModuleDto updateSquareMeterFabModuleStages(Long id, String cuttingStage, String fitUpStage, String weldingStage, String finishingStage);
    
     
    boolean deleteSquareMeterFabModule(Long id);
    boolean softDeleteSquareMeterFabModule(Long id);
    
     
    List<String> getDistinctWorkOrders();
    List<String> getDistinctBuildingNamesByWorkOrder(String workOrder);
    List<String> getDistinctDrawingNosByWorkOrderAndBuildingName(String workOrder, String buildingName);
    List<String> getDistinctMarkNosByWorkOrderBuildingNameAndDrawingNo(String workOrder, String buildingName, String drawingNo);
    List<String> getDistinctRaNumbers();
    List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder);
    
     
    Map<String, Long> getSquareMeterFabModuleStatistics();
    
    
    
    
    
    
    
    
    
    
    
 // NEW methods to add to square 

  
 List<String> getDistinctClientNames();

 
 List<String> getDistinctWorkOrdersByClientName(String clientName);

 
 List<String> getDistinctServiceDescriptionsByClientAndWorkOrder(String clientName, String workOrder);

  
 List<String> getDistinctRaNumbersByClientWorkOrderAndService(String clientName, String workOrder, String serviceDescription);

  
 List<SquareMeterFabModuleDto> searchByClientWorkOrderServiceAndRa(String clientName, String workOrder, String serviceDescription, String raNumber);

}
