package com.bellaryinfotech.service;
 

import java.util.List;

import com.bellaryinfotech.DTO.AlignmentSquareMeterPreFabricatedDTO;
import com.bellaryinfotech.model.AlignmentSquareMeterPreFabricatedProduction;

public interface AlignmentSquareMeterPreFabricatedProductionService {
 AlignmentSquareMeterPreFabricatedDTO saveAlignmentSquareMeterPreFabricatedModule(AlignmentSquareMeterPreFabricatedDTO dto);
 List<AlignmentSquareMeterPreFabricatedProduction> getByWorkOrderAndServiceDescription(String workOrder, String serviceDescription);
 List<AlignmentSquareMeterPreFabricatedProduction> getEntriesByWorkOrder(String workOrder, String vehicleNumber, String loadNumber, String plotNumber);
 AlignmentSquareMeterPreFabricatedProduction updateEntry(Long id, AlignmentSquareMeterPreFabricatedProduction entry);
 void deleteEntry(Long id);
 List<String> getDistinctWorkOrders();
}