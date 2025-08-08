package com.bellaryinfotech.service;

import java.util.List;
import com.bellaryinfotech.DTO.InchMeterModuleDTO;

public interface InchMeterModuleService {
    
    // Basic CRUD operations
    InchMeterModuleDTO createInchMeterModule(InchMeterModuleDTO inchMeterModuleDTO);
    InchMeterModuleDTO updateInchMeterModule(Long id, InchMeterModuleDTO inchMeterModuleDTO);
    void deleteInchMeterModule(Long id);
    InchMeterModuleDTO getInchMeterModuleById(Long id);
    List<InchMeterModuleDTO> getAllInchMeterModules();
    List<InchMeterModuleDTO> getInchMeterModulesByMarkNo(String markNo);
    List<InchMeterModuleDTO> getInchMeterModulesByWorkOrder(String workOrder);
    List<InchMeterModuleDTO> getInchMeterModulesByWorkOrderAndBuildingName(String workOrder, String buildingName);
    void deleteInchMeterModulesByMarkNo(String markNo);

    // Required methods for FabWrapper integration
    List<String> getDistinctWorkOrders();
    List<String> getDistinctBuildingNamesByWorkOrder(String workOrder);
    List<String> getDistinctDrawingNosByWorkOrderAndBuildingName(String workOrder, String buildingName);
    List<String> getDistinctMarkNosByWorkOrderAndBuildingNameAndDrawingNo(String workOrder, String buildingName, String drawingNo);
    List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder);
    List<InchMeterModuleDTO> getInchMeterModulesByWorkOrderBuildingDrawingMark(String workOrder, String buildingName, String drawingNo, String markNo);
}
