package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.SquareMeterFabModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SquareMeterFabModuleRepository extends JpaRepository<SquareMeterFabModule, Long> {
    
    // Find by work order
    List<SquareMeterFabModule> findByWorkOrderAndStatus(String workOrder, String status);
    List<SquareMeterFabModule> findByWorkOrder(String workOrder);
    
     
    List<SquareMeterFabModule> findByWorkOrderAndBuildingNameAndStatus(String workOrder, String buildingName, String status);
    List<SquareMeterFabModule> findByWorkOrderAndBuildingName(String workOrder, String buildingName);
    
     
    List<SquareMeterFabModule> findByWorkOrderAndBuildingNameAndDrawingNoAndStatus(String workOrder, String buildingName, String drawingNo, String status);
    List<SquareMeterFabModule> findByWorkOrderAndBuildingNameAndDrawingNo(String workOrder, String buildingName, String drawingNo);
    
     
    List<SquareMeterFabModule> findByWorkOrderAndBuildingNameAndDrawingNoAndMarkNoAndStatus(String workOrder, String buildingName, String drawingNo, String markNo, String status);
    List<SquareMeterFabModule> findByWorkOrderAndBuildingNameAndDrawingNoAndMarkNo(String workOrder, String buildingName, String drawingNo, String markNo);
    
     
    List<SquareMeterFabModule> findByRaNoAndStatus(String raNo, String status);
    List<SquareMeterFabModule> findByRaNo(String raNo);
    
    
    List<SquareMeterFabModule> findByStatus(String status);
    
     
    @Query("SELECT s FROM SquareMeterFabModule s WHERE " +
           "(:workOrder IS NULL OR s.workOrder = :workOrder) AND " +
           "(:buildingName IS NULL OR s.buildingName = :buildingName) AND " +
           "(:drawingNo IS NULL OR s.drawingNo = :drawingNo) AND " +
           "(:markNo IS NULL OR s.markNo = :markNo) AND " +
           "(:raNo IS NULL OR s.raNo = :raNo) AND " +
           "s.status = 'ACTIVE'")
    List<SquareMeterFabModule> searchSquareMeterFabModules(@Param("workOrder") String workOrder,
                                                           @Param("buildingName") String buildingName,
                                                           @Param("drawingNo") String drawingNo,
                                                           @Param("markNo") String markNo,
                                                           @Param("raNo") String raNo);
    
     
    @Query("SELECT DISTINCT s.workOrder FROM SquareMeterFabModule s WHERE s.status = 'ACTIVE' ORDER BY s.workOrder")
    List<String> findDistinctWorkOrders();
    
    @Query("SELECT DISTINCT s.buildingName FROM SquareMeterFabModule s WHERE s.workOrder = :workOrder AND s.status = 'ACTIVE' ORDER BY s.buildingName")
    List<String> findDistinctBuildingNamesByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT s.drawingNo FROM SquareMeterFabModule s WHERE s.workOrder = :workOrder AND s.buildingName = :buildingName AND s.status = 'ACTIVE' ORDER BY s.drawingNo")
    List<String> findDistinctDrawingNosByWorkOrderAndBuildingName(@Param("workOrder") String workOrder, @Param("buildingName") String buildingName);
    
    @Query("SELECT DISTINCT s.markNo FROM SquareMeterFabModule s WHERE s.workOrder = :workOrder AND s.buildingName = :buildingName AND s.drawingNo = :drawingNo AND s.status = 'ACTIVE' ORDER BY s.markNo")
    List<String> findDistinctMarkNosByWorkOrderBuildingNameAndDrawingNo(@Param("workOrder") String workOrder, @Param("buildingName") String buildingName, @Param("drawingNo") String drawingNo);
    
    @Query("SELECT DISTINCT s.raNo FROM SquareMeterFabModule s WHERE s.raNo IS NOT NULL AND s.status = 'ACTIVE' ORDER BY s.raNo")
    List<String> findDistinctRaNumbers();
    
    @Query("SELECT DISTINCT s.serviceDescription FROM SquareMeterFabModule s WHERE s.workOrder = :workOrder AND s.status = 'ACTIVE' ORDER BY s.serviceDescription")
    List<String> findDistinctServiceDescriptionsByWorkOrder(@Param("workOrder") String workOrder);
    
    // Statistics queries
    @Query("SELECT COUNT(s) FROM SquareMeterFabModule s WHERE s.status = 'ACTIVE'")
    Long countActiveRecords();
    
    @Query("SELECT COUNT(s) FROM SquareMeterFabModule s WHERE s.cuttingStage = 'Y' AND s.status = 'ACTIVE'")
    Long countCuttingCompleted();
    
    @Query("SELECT COUNT(s) FROM SquareMeterFabModule s WHERE s.fitUpStage = 'Y' AND s.status = 'ACTIVE'")
    Long countFitUpCompleted();
    
    @Query("SELECT COUNT(s) FROM SquareMeterFabModule s WHERE s.weldingStage = 'Y' AND s.status = 'ACTIVE'")
    Long countWeldingCompleted();
    
    @Query("SELECT COUNT(s) FROM SquareMeterFabModule s WHERE s.finishingStage = 'Y' AND s.status = 'ACTIVE'")
    Long countFinishingCompleted();
    
    
    
    
    
    
    
    
    
    
    
    
    //production level for square 
    
     
    @Query("SELECT DISTINCT s.clientName FROM SquareMeterFabModule s WHERE s.clientName IS NOT NULL AND s.clientName <> '' AND s.status = 'ACTIVE' ORDER BY s.clientName")
    List<String> findDistinctClientNames();

     
    @Query("SELECT DISTINCT s.workOrder FROM SquareMeterFabModule s WHERE s.clientName = :clientName AND s.workOrder IS NOT NULL AND s.workOrder <> '' AND s.status = 'ACTIVE' ORDER BY s.workOrder")
    List<String> findDistinctWorkOrdersByClientName(@Param("clientName") String clientName);

     
    @Query("SELECT DISTINCT s.serviceDescription FROM SquareMeterFabModule s WHERE s.clientName = :clientName AND s.workOrder = :workOrder AND s.serviceDescription IS NOT NULL AND s.serviceDescription <> '' AND s.status = 'ACTIVE' ORDER BY s.serviceDescription")
    List<String> findDistinctServiceDescriptionsByClientAndWorkOrder(@Param("clientName") String clientName, @Param("workOrder") String workOrder);

    
    @Query("SELECT DISTINCT s.raNo FROM SquareMeterFabModule s WHERE s.clientName = :clientName AND s.workOrder = :workOrder AND s.serviceDescription = :serviceDescription AND s.raNo IS NOT NULL AND s.raNo <> '' AND s.raNo <> 'Not assigned yet' AND s.status = 'ACTIVE' ORDER BY s.raNo")
    List<String> findDistinctRaNumbersByClientWorkOrderAndService(@Param("clientName") String clientName, @Param("workOrder") String workOrder, @Param("serviceDescription") String serviceDescription);

    
    List<SquareMeterFabModule> findByClientNameAndWorkOrderAndServiceDescriptionAndRaNo(String clientName, String workOrder, String serviceDescription, String raNo);

    
    
    
}
