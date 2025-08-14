package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.AlignmentPreFabricatedProductionDTO;
import com.bellaryinfotech.model.AlignmentPreFabricatedProduction;

import java.util.List;

public interface AlignmentPreFabricatedProductionService {

    List<AlignmentPreFabricatedProduction> saveAlignmentPreFabricatedProduction(AlignmentPreFabricatedProductionDTO dto);

    List<AlignmentPreFabricatedProduction> getAlignmentPreFabricatedProductionsByWorkOrder(String workOrder);

    List<AlignmentPreFabricatedProduction> getAlignmentPreFabricatedProductionsByRaNo(String raNo);

    String getLatestRaNoByWorkOrderAndServiceDescription(String workOrder, String serviceDescription);

    AlignmentPreFabricatedProduction updateAlignmentPreFabricatedProduction(Long id, AlignmentPreFabricatedProduction updatedProduction);

    void deleteAlignmentPreFabricatedProduction(Long id);
}