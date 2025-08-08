package com.bellaryinfotech.service;

import java.util.List;
import com.bellaryinfotech.DTO.MtrModuleDTO;

public interface MtrModuleService {
    
    // Existing methods
    MtrModuleDTO createMeterModule(MtrModuleDTO mtrModuleDTO);
    MtrModuleDTO updateMeterModule(Long id, MtrModuleDTO mtrModuleDTO);
    void deleteMeterModule(Long id);
    MtrModuleDTO getMeterModuleById(Long id);
    List<MtrModuleDTO> getAllMeterModules();
    List<MtrModuleDTO> getMeterModulesByMarkNo(String markNo);
    List<MtrModuleDTO> getMeterModulesByWorkOrder(String workOrder);
    List<MtrModuleDTO> getMeterModulesByWorkOrderAndBuildingName(String workOrder, String buildingName);
    void deleteMeterModulesByMarkNo(String markNo);

    // NEW: Required methods for FabWrapper integration
    List<String> getDistinctWorkOrders();
    List<String> getDistinctBuildingNamesByWorkOrder(String workOrder);
    List<String> getDistinctDrawingNosByWorkOrderAndBuildingName(String workOrder, String buildingName);
    List<String> getDistinctMarkNosByWorkOrderAndBuildingNameAndDrawingNo(String workOrder, String buildingName, String drawingNo);
    List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder);
    List<MtrModuleDTO> getMeterModulesByWorkOrderBuildingDrawingMark(String workOrder, String buildingName, String drawingNo, String markNo);
}
