package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.MeterPreFabricatedDrawingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeterPreFabricatedDrawingEntryRepository extends JpaRepository<MeterPreFabricatedDrawingEntry, Long> {
    
    List<MeterPreFabricatedDrawingEntry> findByWorkOrder(String workOrder);
    
    List<MeterPreFabricatedDrawingEntry> findByWorkOrderAndVehicleNumber(String workOrder, String vehicleNumber);
    
    List<MeterPreFabricatedDrawingEntry> findByWorkOrderAndLoadNumber(String workOrder, String loadNumber);
    
    List<MeterPreFabricatedDrawingEntry> findByWorkOrderAndPlotNumber(String workOrder, String plotNumber);
    
    List<MeterPreFabricatedDrawingEntry> findByWorkOrderAndVehicleNumberAndLoadNumber(String workOrder, String vehicleNumber, String loadNumber);
    
    List<MeterPreFabricatedDrawingEntry> findByWorkOrderAndVehicleNumberAndPlotNumber(String workOrder, String vehicleNumber, String plotNumber);
    
    List<MeterPreFabricatedDrawingEntry> findByWorkOrderAndLoadNumberAndPlotNumber(String workOrder, String loadNumber, String plotNumber);
    
    List<MeterPreFabricatedDrawingEntry> findByWorkOrderAndVehicleNumberAndLoadNumberAndPlotNumber(String workOrder, String vehicleNumber, String loadNumber, String plotNumber);
    
    List<MeterPreFabricatedDrawingEntry> findByMarkNo(String markNo);
    
    @Query("SELECT DISTINCT m.workOrder FROM MeterPreFabricatedDrawingEntry m ORDER BY m.workOrder")
    List<String> findDistinctWorkOrders();
    
    @Query("SELECT DISTINCT m.vehicleNumber FROM MeterPreFabricatedDrawingEntry m WHERE m.workOrder = :workOrder ORDER BY m.vehicleNumber")
    List<String> findDistinctVehicleNumbersByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT m.loadNumber FROM MeterPreFabricatedDrawingEntry m WHERE m.workOrder = :workOrder ORDER BY m.loadNumber")
    List<String> findDistinctLoadNumbersByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT m.plotNumber FROM MeterPreFabricatedDrawingEntry m WHERE m.workOrder = :workOrder ORDER BY m.plotNumber")
    List<String> findDistinctPlotNumbersByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT m.serviceDescription FROM MeterPreFabricatedDrawingEntry m WHERE m.workOrder = :workOrder ORDER BY m.serviceDescription")
    List<String> findDistinctServiceDescriptionsByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT m.clientName FROM MeterPreFabricatedDrawingEntry m WHERE m.workOrder = :workOrder ORDER BY m.clientName")
    List<String> findDistinctClientNamesByWorkOrder(@Param("workOrder") String workOrder);
    
    void deleteByWorkOrder(String workOrder);
    
    void deleteByWorkOrderAndVehicleNumberAndLoadNumberAndPlotNumber(String workOrder, String vehicleNumber, String loadNumber, String plotNumber);
}
