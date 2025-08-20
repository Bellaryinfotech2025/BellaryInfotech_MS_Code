package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.PostInchMeterPreFabricatedModuleDTO;
import com.bellaryinfotech.model.PostInchMeterPreFabricatedModule;

import java.util.List;

public interface PostInchMeterPreFabricatedModuleService {
    
    List<PostInchMeterPreFabricatedModule> savePostInchMeterPreFabricatedModule(PostInchMeterPreFabricatedModuleDTO dto);
    
    List<PostInchMeterPreFabricatedModule> getPostInchMeterPreFabricatedModulesByWorkOrder(String workOrder);
    
    List<PostInchMeterPreFabricatedModule> getPostInchMeterPreFabricatedByWorkOrderAndServiceDesc(String workOrder, String serviceDescription);
    
    List<PostInchMeterPreFabricatedModule> getPostInchMeterPreFabricatedModulesByWorkOrderAndRaNo(String workOrder, String raNo);
    
    List<PostInchMeterPreFabricatedModule> getPostInchMeterPreFabricatedModulesByRaNo(String raNo);
    
    List<PostInchMeterPreFabricatedModule> getPostInchMeterPreFabricatedModulesByMarkNo(String markNo);
    
    List<String> getDistinctWorkOrders();
    
    List<String> getDistinctRaNosByWorkOrder(String workOrder);
    
    List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder);
    
    List<String> getDistinctVehicleNumbersByWorkOrder(String workOrder);
    
    List<String> getDistinctLoadNumbersByWorkOrder(String workOrder);
    
    List<String> getDistinctPlotNumbersByWorkOrder(String workOrder);
    
    PostInchMeterPreFabricatedModule updatePostInchMeterPreFabricatedModule(Long id, PostInchMeterPreFabricatedModule updatedModule);
    
    void deletePostInchMeterPreFabricatedModule(Long id);
    
    void deletePostInchMeterPreFabricatedModulesByWorkOrder(String workOrder);
    
    void deletePostInchMeterPreFabricatedModulesByWorkOrderAndRaNo(String workOrder, String raNo);
    
    // NEW: Search method for workOrder, serviceDescription, and optional raNo
    List<PostInchMeterPreFabricatedModule> searchPostInchMeterPreFabricatedModules(String workOrder, String serviceDescription, String raNo);


    
    
    
    
    
    
    
 // Get all records
    List<PostInchMeterPreFabricatedModule> getAllRecords();
    
    // Get distinct client names
    List<String> getDistinctClientNames();
    
    // Get distinct work orders by client name
    List<String> getDistinctWorkOrdersByClientName(String clientName);
    
    // Get distinct service descriptions by client and work order
    List<String> getDistinctServiceDescriptionsByClientAndWorkOrder(String clientName, String workOrder);
    
    // Get distinct RA numbers by all criteria
    List<String> getDistinctRaNumbersByAllCriteria(String clientName, String workOrder, String serviceDescription);
    
    // Search by all criteria
    List<PostInchMeterPreFabricatedModule> searchByAllCriteria(String clientName, String workOrder, String serviceDescription, String raNumber);

}