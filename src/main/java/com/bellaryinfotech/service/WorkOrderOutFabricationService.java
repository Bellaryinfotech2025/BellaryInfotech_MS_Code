package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.WorkOrderOutFabricationDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WorkOrderOutFabricationService {
    
    // ============ EXISTING SERVICE METHODS ============
    
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
    
    // ============ NEW: WORK ORDER OUT RESULT SERVICE METHODS ============
    
    List<String> getDistinctClientNames();
    
    List<String> getDistinctWorkOrdersByClientName(String clientName);
    
    List<String> getDistinctServiceDescriptionsByClientAndWorkOrder(String clientName, String workOrder);
    
    List<String> getDistinctRaNosByClientWorkOrderAndService(String clientName, String workOrder, String serviceDescription);
    
    List<WorkOrderOutFabricationDto> searchByClientWorkOrderServiceAndRaNo(String clientName, String workOrder, String serviceDescription, String raNo);

    // ============ NEW: SUB AGENCY NAME SERVICE METHODS ============
    
    List<String> getDistinctSubAgencyNamesByClientWorkOrderAndService(String clientName, String workOrder, String serviceDescription);
    
    boolean isServiceDescriptionFromWorkOrderOutFabrication(String clientName, String workOrder, String serviceDescription);
    
    List<String> getDistinctRaNosByAllFiltersWithSubAgency(String clientName, String workOrder, String serviceDescription, String subAgencyName);
    
    List<WorkOrderOutFabricationDto> searchByAllFiltersWithSubAgency(String clientName, String workOrder, String serviceDescription, String raNo, String subAgencyName);
    
    List<WorkOrderOutFabricationDto> searchByAllFiltersWithoutSubAgency(String clientName, String workOrder, String serviceDescription, String raNo);






 //new to get the production
    List<String> getDistinctSubAgencyRaNosByClientWorkOrderAndService(String clientName, String workOrder, String serviceDescription);
    List<String> getDistinctSubAgencyNamesByRaNo(String clientName, String workOrder, String serviceDescription, String subAgencyRaNo);
    List<WorkOrderOutFabricationDto> searchBySubAgencyRaNoAndSubAgencyName(String clientName, String workOrder, String serviceDescription, String subAgencyRaNo, String subAgencyName);

}
