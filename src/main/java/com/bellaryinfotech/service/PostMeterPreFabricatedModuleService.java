package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.PostMeterPreFabricatedModuleDTO;
import com.bellaryinfotech.model.PostMeterPreFabricatedModule;

import java.util.List;

public interface PostMeterPreFabricatedModuleService {
    
    List<PostMeterPreFabricatedModule> savePostMeterPreFabricatedModule(PostMeterPreFabricatedModuleDTO dto);
    
    List<PostMeterPreFabricatedModule> getPostMeterPreFabricatedModulesByWorkOrder(String workOrder);
    
    List<PostMeterPreFabricatedModule> getPostMeterPreFabricatedModulesByWorkOrderAndRaNo(String workOrder, String raNo);
    
    List<PostMeterPreFabricatedModule> getPostMeterPreFabricatedModulesByRaNo(String raNo);
    
    List<PostMeterPreFabricatedModule> getPostMeterPreFabricatedModulesByMarkNo(String markNo);
    
    List<String> getDistinctWorkOrders();
    
    List<String> getDistinctRaNosByWorkOrder(String workOrder);
    
    List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder);
    
    List<String> getDistinctVehicleNumbersByWorkOrder(String workOrder);
    
    List<String> getDistinctLoadNumbersByWorkOrder(String workOrder);
    
    List<String> getDistinctPlotNumbersByWorkOrder(String workOrder);
    
    PostMeterPreFabricatedModule updatePostMeterPreFabricatedModule(Long id, PostMeterPreFabricatedModule updatedModule);
    
    void deletePostMeterPreFabricatedModule(Long id);
    
    void deletePostMeterPreFabricatedModulesByWorkOrder(String workOrder);
    
    void deletePostMeterPreFabricatedModulesByWorkOrderAndRaNo(String workOrder, String raNo);
}
