package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.PostCubicMeterPreFabricatedModuleDTO;
import com.bellaryinfotech.model.PostCubicMeterPreFabricatedModule;

import java.util.List;

public interface PostCubicMeterPreFabricatedModuleService {

    List<PostCubicMeterPreFabricatedModule> savePostCubicMeterPreFabricatedModule(PostCubicMeterPreFabricatedModuleDTO dto);

    List<PostCubicMeterPreFabricatedModule> getPostCubicMeterPreFabricatedModulesByWorkOrder(String workOrder);

    List<PostCubicMeterPreFabricatedModule> getPostCubicMeterPreFabricatedByWorkOrderAndServiceDesc(String workOrder, String serviceDescription);

    List<PostCubicMeterPreFabricatedModule> getPostCubicMeterPreFabricatedModulesByWorkOrderAndRaNo(String workOrder, String raNo);

    List<PostCubicMeterPreFabricatedModule> getPostCubicMeterPreFabricatedModulesByRaNo(String raNo);

    List<PostCubicMeterPreFabricatedModule> getPostCubicMeterPreFabricatedModulesByMarkNo(String markNo);

    List<String> getDistinctWorkOrders();

    List<String> getDistinctRaNosByWorkOrder(String workOrder);

    List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder);

    List<String> getDistinctVehicleNumbersByWorkOrder(String workOrder);

    List<String> getDistinctLoadNumbersByWorkOrder(String workOrder);

    List<String> getDistinctPlotNumbersByWorkOrder(String workOrder);

    PostCubicMeterPreFabricatedModule updatePostCubicMeterPreFabricatedModule(Long id, PostCubicMeterPreFabricatedModule updatedModule);

    void deletePostCubicMeterPreFabricatedModule(Long id);

    void deletePostCubicMeterPreFabricatedModulesByWorkOrder(String workOrder);

    void deletePostCubicMeterPreFabricatedModulesByWorkOrderAndRaNo(String workOrder, String raNo);

    List<PostCubicMeterPreFabricatedModule> searchPostCubicMeterPreFabricatedModules(String workOrder, String serviceDescription, String raNo);



    
    // new mmethods to get the cubic prefabriacted 
    List<PostCubicMeterPreFabricatedModule> getAllPostCubicMeterPreFabricatedModules();
    
    List<String> getDistinctClientNames();
    
    List<String> getDistinctWorkOrdersByClientName(String clientName);
    
    List<String> getDistinctServiceDescriptionsByClientAndWorkOrder(String clientName, String workOrder);
    
    List<String> getDistinctRaNumbersByClientWorkOrderAndService(String clientName, String workOrder, String serviceDescription);
    
    List<PostCubicMeterPreFabricatedModule> searchByAllCriteria(String clientName, String workOrder, String serviceDescription, String raNumber);
}