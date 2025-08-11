package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.PreFabricatedDrawingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreFabricatedDrawingEntryRepository extends JpaRepository<PreFabricatedDrawingEntry, Long> {
    
    List<PreFabricatedDrawingEntry> findByWorkOrder(String workOrder);
    
    List<PreFabricatedDrawingEntry> findByWorkOrderAndVehicleNumber(String workOrder, String vehicleNumber);
    
    List<PreFabricatedDrawingEntry> findByWorkOrderAndLoadNumber(String workOrder, String loadNumber);
    
    List<PreFabricatedDrawingEntry> findByWorkOrderAndPlotNumber(String workOrder, String plotNumber);
    
    List<PreFabricatedDrawingEntry> findByWorkOrderAndVehicleNumberAndLoadNumber(String workOrder, String vehicleNumber, String loadNumber);
    
    List<PreFabricatedDrawingEntry> findByWorkOrderAndVehicleNumberAndPlotNumber(String workOrder, String vehicleNumber, String plotNumber);
    
    List<PreFabricatedDrawingEntry> findByWorkOrderAndLoadNumberAndPlotNumber(String workOrder, String loadNumber, String plotNumber);
    
    List<PreFabricatedDrawingEntry> findByWorkOrderAndVehicleNumberAndLoadNumberAndPlotNumber(String workOrder, String vehicleNumber, String loadNumber, String plotNumber);
    
    @Query("SELECT DISTINCT p.workOrder FROM PreFabricatedDrawingEntry p ORDER BY p.workOrder")
    List<String> findDistinctWorkOrders();
    
    @Query("SELECT DISTINCT p.vehicleNumber FROM PreFabricatedDrawingEntry p WHERE p.workOrder = :workOrder AND p.vehicleNumber IS NOT NULL ORDER BY p.vehicleNumber")
    List<String> findDistinctVehicleNumbersByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT p.loadNumber FROM PreFabricatedDrawingEntry p WHERE p.workOrder = :workOrder AND p.loadNumber IS NOT NULL ORDER BY p.loadNumber")
    List<String> findDistinctLoadNumbersByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT p.plotNumber FROM PreFabricatedDrawingEntry p WHERE p.workOrder = :workOrder AND p.plotNumber IS NOT NULL ORDER BY p.plotNumber")
    List<String> findDistinctPlotNumbersByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT p.serviceDescription FROM PreFabricatedDrawingEntry p WHERE p.workOrder = :workOrder AND p.serviceDescription IS NOT NULL ORDER BY p.serviceDescription")
    List<String> findDistinctServiceDescriptionsByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT p.clientName FROM PreFabricatedDrawingEntry p WHERE p.workOrder = :workOrder AND p.clientName IS NOT NULL ORDER BY p.clientName")
    List<String> findDistinctClientNamesByWorkOrder(@Param("workOrder") String workOrder);
    
    List<PreFabricatedDrawingEntry> findByMarkNo(String markNo);
    
    @Query("SELECT p FROM PreFabricatedDrawingEntry p WHERE p.workOrder = :workOrder AND p.vehicleNumber = :vehicleNumber AND p.loadNumber = :loadNumber AND p.plotNumber = :plotNumber")
    List<PreFabricatedDrawingEntry> findByWorkOrderAndVehicleAndLoadAndPlot(@Param("workOrder") String workOrder, 
                                                                           @Param("vehicleNumber") String vehicleNumber, 
                                                                           @Param("loadNumber") String loadNumber,
                                                                           @Param("plotNumber") String plotNumber);
    
    void deleteByWorkOrder(String workOrder);
    
    void deleteByWorkOrderAndVehicleNumberAndLoadNumberAndPlotNumber(String workOrder, String vehicleNumber, String loadNumber, String plotNumber);
}
