package com.bellaryinfotech.service;

import java.util.List;

import com.bellaryinfotech.DTO.PostFabricatedModuleDTO;
import com.bellaryinfotech.model.PostFabricatedModule;

public interface PostFabricatedModuleService {
    
    List<PostFabricatedModule> savePostFabricatedModule(PostFabricatedModuleDTO dto);
    
    List<PostFabricatedModule> getPostFabricatedModulesByWorkOrder(String workOrder);
    
    List<PostFabricatedModule> getPostFabricatedModulesByRaNumber(String raNumber);
    
    List<PostFabricatedModule> getPostFabricatedModulesByWorkOrderAndRaNumber(String workOrder, String raNumber);
    
    List<String> getDistinctWorkOrders();
    
    List<String> getDistinctRaNumbers();
    
    List<String> getDistinctRaNumbersByWorkOrder(String workOrder);
    
    PostFabricatedModule updatePostFabricatedModule(Long id, PostFabricatedModule updatedModule);
    
    void deletePostFabricatedModule(Long id);
    
    void deletePostFabricatedModulesByWorkOrderAndRaNumber(String workOrder, String raNumber);
}
