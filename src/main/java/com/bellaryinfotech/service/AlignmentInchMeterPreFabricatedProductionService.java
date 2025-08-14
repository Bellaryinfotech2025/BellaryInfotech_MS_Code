package com.bellaryinfotech.service;
 

import com.bellaryinfotech.DTO.AlignmentInchMeterPreFabricatedProductionDTO;
import com.bellaryinfotech.model.AlignmentInchMeterPreFabricatedProduction;

import java.util.List;

public interface AlignmentInchMeterPreFabricatedProductionService {
    List<AlignmentInchMeterPreFabricatedProduction> getByWorkOrder(String workOrder);
    List<AlignmentInchMeterPreFabricatedProduction> getByRaNo(String raNo);
    List<AlignmentInchMeterPreFabricatedProduction> getByVehicleNumber(String vehicleNumber);
    List<String> getDistinctWorkOrders();
    List<String> getDistinctVehicleNumbersByWorkOrder(String workOrder);
    List<String> getDistinctLoadNumbersByWorkOrder(String workOrder);
    List<String> getDistinctPlotNumbersByWorkOrder(String workOrder);
    List<String> getDistinctRaNosByWorkOrder(String workOrder);
    List<AlignmentInchMeterPreFabricatedProduction> save(AlignmentInchMeterPreFabricatedProductionDTO dto);
    AlignmentInchMeterPreFabricatedProduction update(Long id, AlignmentInchMeterPreFabricatedProductionDTO.EntryDTO entryDTO);
    void delete(Long id);
}