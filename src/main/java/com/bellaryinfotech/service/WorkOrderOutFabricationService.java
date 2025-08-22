package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.WorkOrderOutFabricationDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WorkOrderOutFabricationService {
    
    List<WorkOrderOutFabricationDto> getAllFabrications();
    
    Optional<WorkOrderOutFabricationDto> getFabricationById(Long id);
    
    List<WorkOrderOutFabricationDto> createBulkFabrications(List<WorkOrderOutFabricationDto> fabricationDtos);
    
    WorkOrderOutFabricationDto updateFabrication(Long id, WorkOrderOutFabricationDto fabricationDto);
    
    void deleteFabrication(Long id);
    
    void deleteFabricationsByWorkOrder(String workOrder);
    
    List<WorkOrderOutFabricationDto> searchByWorkOrder(String workOrder);
    
    List<WorkOrderOutFabricationDto> searchByMultipleCriteria(String workOrder, String clientName, String drawingNo, String markNo, String buildingName);
    
    Map<String, String> getRaNosByWorkOrder(String workOrder);
    
    void updateRaNoForWorkOrder(String workOrder, String raNo, String subAgencyRaNo);
    
    boolean existsByWorkOrderAndDrawingAndMark(String workOrder, String drawingNo, String markNo);
    
    
    List<String> getDistinctClientNames();
    
    List<String> getDistinctWorkOrdersByClientName(String clientName);
    
    List<String> getDistinctServiceDescriptionsByClientAndWorkOrder(String clientName, String workOrder);
    
    List<String> getDistinctRaNosByClientWorkOrderAndService(String clientName, String workOrder, String serviceDescription);
    
    List<WorkOrderOutFabricationDto> searchByClientWorkOrderServiceAndRaNo(String clientName, String workOrder, String serviceDescription, String raNo);








 

}
