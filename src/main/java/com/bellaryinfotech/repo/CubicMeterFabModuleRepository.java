package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.CubicMeterFabModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CubicMeterFabModuleRepository extends JpaRepository<CubicMeterFabModule, Long> {

    
    List<CubicMeterFabModule> findByWorkOrderAndBuildingName(String workOrder, String buildingName);

     
    List<CubicMeterFabModule> findByWorkOrderAndBuildingNameAndDrawingNoAndMarkNo(
            String workOrder, String buildingName, String drawingNo, String markNo);

     
    List<CubicMeterFabModule> findByRaNo(String raNo);

     
    List<CubicMeterFabModule> findByWorkOrder(String workOrder);

     
    @Query("SELECT DISTINCT c.workOrder FROM CubicMeterFabModule c WHERE c.workOrder IS NOT NULL AND c.workOrder <> ''")
    List<String> findDistinctWorkOrders();

     
    @Query("SELECT DISTINCT c.buildingName FROM CubicMeterFabModule c WHERE c.workOrder = :workOrder AND c.buildingName IS NOT NULL AND c.buildingName <> ''")
    List<String> findDistinctBuildingNamesByWorkOrder(@Param("workOrder") String workOrder);

     
    @Query("SELECT DISTINCT c.raNo FROM CubicMeterFabModule c WHERE c.raNo IS NOT NULL AND c.raNo <> '' AND c.raNo <> 'Not assigned yet'")
    List<String> findDistinctRaNos();

     
    @Query("SELECT c FROM CubicMeterFabModule c WHERE " +
           "(:workOrder IS NULL OR c.workOrder = :workOrder) AND " +
           "(:buildingName IS NULL OR c.buildingName = :buildingName) AND " +
           "(:drawingNo IS NULL OR c.drawingNo = :drawingNo) AND " +
           "(:markNo IS NULL OR c.markNo = :markNo) AND " +
           "(:raNo IS NULL OR c.raNo = :raNo)")
    List<CubicMeterFabModule> searchCubicMeterFabModules(@Param("workOrder") String workOrder,
                                                         @Param("buildingName") String buildingName,
                                                         @Param("drawingNo") String drawingNo,
                                                         @Param("markNo") String markNo,
                                                         @Param("raNo") String raNo);

     
    @Query("SELECT COUNT(c) FROM CubicMeterFabModule c WHERE c.status = 'ACTIVE'")
    Long countActiveCubicMeterFabModules();
    
    
    
    
    
    
    
    
    
    
 // new for prodcution cubic

 
 @Query("SELECT DISTINCT c.clientName FROM CubicMeterFabModule c WHERE c.clientName IS NOT NULL AND c.clientName <> '' AND c.status = 'ACTIVE' ORDER BY c.clientName")
 List<String> findDistinctClientNames();

  
 @Query("SELECT DISTINCT c.workOrder FROM CubicMeterFabModule c WHERE c.clientName = :clientName AND c.workOrder IS NOT NULL AND c.workOrder <> '' AND c.status = 'ACTIVE' ORDER BY c.workOrder")
 List<String> findDistinctWorkOrdersByClientName(@Param("clientName") String clientName);

  
 @Query("SELECT DISTINCT c.serviceDescription FROM CubicMeterFabModule c WHERE c.clientName = :clientName AND c.workOrder = :workOrder AND c.serviceDescription IS NOT NULL AND c.serviceDescription <> '' AND c.status = 'ACTIVE' ORDER BY c.serviceDescription")
 List<String> findDistinctServiceDescriptionsByClientAndWorkOrder(@Param("clientName") String clientName, @Param("workOrder") String workOrder);

 
 @Query("SELECT DISTINCT c.raNo FROM CubicMeterFabModule c WHERE c.clientName = :clientName AND c.workOrder = :workOrder AND c.serviceDescription = :serviceDescription AND c.raNo IS NOT NULL AND c.raNo <> '' AND c.raNo <> 'Not assigned yet' AND c.status = 'ACTIVE' ORDER BY c.raNo")
 List<String> findDistinctRaNumbersByClientWorkOrderAndService(@Param("clientName") String clientName, @Param("workOrder") String workOrder, @Param("serviceDescription") String serviceDescription);

  
 List<CubicMeterFabModule> findByClientNameAndWorkOrderAndServiceDescriptionAndRaNo(String clientName, String workOrder, String serviceDescription, String raNo);

    
    
}
