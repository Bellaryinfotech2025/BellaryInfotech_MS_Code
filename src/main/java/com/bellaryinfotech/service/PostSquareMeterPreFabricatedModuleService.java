package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.PostSquareMeterPreFabricatedModuleDTO;
import com.bellaryinfotech.model.PostSquareMeterPreFabricatedModule;

import java.util.List;

public interface PostSquareMeterPreFabricatedModuleService {

    List<PostSquareMeterPreFabricatedModule> savePostSquareMeterPreFabricatedModule(PostSquareMeterPreFabricatedModuleDTO dto);

    List<PostSquareMeterPreFabricatedModule> getPostSquareMeterPreFabricatedModulesByWorkOrder(String workOrder);

    List<PostSquareMeterPreFabricatedModule> getPostSquareMeterPreFabricatedByWorkOrderAndServiceDesc(String workOrder, String serviceDescription);

    List<PostSquareMeterPreFabricatedModule> getPostSquareMeterPreFabricatedModulesByWorkOrderAndRaNo(String workOrder, String raNo);

    List<PostSquareMeterPreFabricatedModule> getPostSquareMeterPreFabricatedModulesByRaNo(String raNo);

    List<PostSquareMeterPreFabricatedModule> getPostSquareMeterPreFabricatedModulesByMarkNo(String markNo);

    List<String> getDistinctWorkOrders();

    List<String> getDistinctRaNosByWorkOrder(String workOrder);

    List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder);

    List<String> getDistinctVehicleNumbersByWorkOrder(String workOrder);

    List<String> getDistinctLoadNumbersByWorkOrder(String workOrder);

    List<String> getDistinctPlotNumbersByWorkOrder(String workOrder);

    PostSquareMeterPreFabricatedModule updatePostSquareMeterPreFabricatedModule(Long id, PostSquareMeterPreFabricatedModule updatedModule);

    void deletePostSquareMeterPreFabricatedModule(Long id);

    void deletePostSquareMeterPreFabricatedModulesByWorkOrder(String workOrder);

    void deletePostSquareMeterPreFabricatedModulesByWorkOrderAndRaNo(String workOrder, String raNo);

    List<PostSquareMeterPreFabricatedModule> searchPostSquareMeterPreFabricatedModules(String workOrder, String serviceDescription, String raNo);
}