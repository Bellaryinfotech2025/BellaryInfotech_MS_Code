package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.AlignmentCubicMeterPreFabricatedProductionDTO;
import com.bellaryinfotech.model.AlignmentCubicMeterPreFabricatedProduction;

import java.util.List;

public interface AlignmentCubicMeterPreFabricatedProductionService {
    List<AlignmentCubicMeterPreFabricatedProduction> saveAlignmentCubicMeterPreFabricatedProduction(AlignmentCubicMeterPreFabricatedProductionDTO dto);

    List<AlignmentCubicMeterPreFabricatedProduction> getAlignmentCubicMeterPreFabricatedProductionByWorkOrder(String workOrder);

    List<AlignmentCubicMeterPreFabricatedProduction> getAlignmentCubicMeterPreFabricatedByWorkOrderAndServiceDesc(String workOrder, String serviceDescription);

    List<AlignmentCubicMeterPreFabricatedProduction> getAlignmentCubicMeterPreFabricatedProductionByWorkOrderAndRaNo(String workOrder, String raNo);

    List<AlignmentCubicMeterPreFabricatedProduction> getAlignmentCubicMeterPreFabricatedProductionByRaNo(String raNo);

    List<AlignmentCubicMeterPreFabricatedProduction> getAlignmentCubicMeterPreFabricatedProductionByMarkNo(String markNo);

    List<String> getDistinctWorkOrders();

    List<String> getDistinctRaNosByWorkOrder(String workOrder);

    List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder);

    List<String> getDistinctVehicleNumbersByWorkOrder(String workOrder);

    List<String> getDistinctLoadNumbersByWorkOrder(String workOrder);

    List<String> getDistinctPlotNumbersByWorkOrder(String workOrder);

    AlignmentCubicMeterPreFabricatedProduction updateAlignmentCubicMeterPreFabricatedProduction(Long id, AlignmentCubicMeterPreFabricatedProduction updatedModule);

    void deleteAlignmentCubicMeterPreFabricatedProduction(Long id);

    void deleteAlignmentCubicMeterPreFabricatedProductionByWorkOrder(String workOrder);

    void deleteAlignmentCubicMeterPreFabricatedProductionByWorkOrderAndRaNo(String workOrder, String raNo);

    List<AlignmentCubicMeterPreFabricatedProduction> searchAlignmentCubicMeterPreFabricatedProduction(String workOrder, String serviceDescription, String raNo);
}