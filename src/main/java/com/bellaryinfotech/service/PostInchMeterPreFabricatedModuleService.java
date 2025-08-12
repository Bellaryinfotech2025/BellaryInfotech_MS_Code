package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.PostInchMeterPreFabricatedModuleDTO;
import com.bellaryinfotech.model.PostInchMeterPreFabricatedModule;

import java.util.List;

public interface PostInchMeterPreFabricatedModuleService {
    
    List<PostInchMeterPreFabricatedModule> savePostInchMeterPreFabricatedModule(PostInchMeterPreFabricatedModuleDTO dto);
    
    List<PostInchMeterPreFabricatedModule> getPostInchMeterPreFabricatedModulesByWorkOrder(String workOrder);
    
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
}