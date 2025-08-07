package com.bellaryinfotech.service;
 

 
import java.util.List;

import com.bellaryinfotech.DTO.MtrModuleDTO;

public interface MtrModuleService {
    
    MtrModuleDTO createMeterModule(MtrModuleDTO mtrModuleDTO);
    
    MtrModuleDTO updateMeterModule(Long id, MtrModuleDTO mtrModuleDTO);
    
    void deleteMeterModule(Long id);
    
    MtrModuleDTO getMeterModuleById(Long id);
    
    List<MtrModuleDTO> getAllMeterModules();
    
    List<MtrModuleDTO> getMeterModulesByMarkNo(String markNo);
    
    List<MtrModuleDTO> getMeterModulesByWorkOrder(String workOrder);
    
    List<MtrModuleDTO> getMeterModulesByWorkOrderAndBuildingName(String workOrder, String buildingName);
    
    List<String> getDistinctWorkOrders();
    
    List<String> getDistinctBuildingNamesByWorkOrder(String workOrder);
    
    void deleteMeterModulesByMarkNo(String markNo);
}
