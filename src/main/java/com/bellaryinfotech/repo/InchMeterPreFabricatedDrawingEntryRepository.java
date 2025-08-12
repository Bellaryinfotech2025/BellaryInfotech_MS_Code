package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.InchMeterPreFabricatedDrawingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InchMeterPreFabricatedDrawingEntryRepository extends JpaRepository<InchMeterPreFabricatedDrawingEntry, Long> {
    
    List<InchMeterPreFabricatedDrawingEntry> findByWorkOrder(String workOrder);
    
    List<InchMeterPreFabricatedDrawingEntry> findByWorkOrderAndVehicleNumber(String workOrder, String vehicleNumber);
    
    List<InchMeterPreFabricatedDrawingEntry> findByWorkOrderAndLoadNumber(String workOrder, String loadNumber);
    
    List<InchMeterPreFabricatedDrawingEntry> findByWorkOrderAndPlotNumber(String workOrder, String plotNumber);
    
    List<InchMeterPreFabricatedDrawingEntry> findByWorkOrderAndVehicleNumberAndLoadNumber(String workOrder, String vehicleNumber, String loadNumber);
    
    List<InchMeterPreFabricatedDrawingEntry> findByWorkOrderAndVehicleNumberAndPlotNumber(String workOrder, String vehicleNumber, String plotNumber);
    
    List<InchMeterPreFabricatedDrawingEntry> findByWorkOrderAndLoadNumberAndPlotNumber(String workOrder, String loadNumber, String plotNumber);
    
    List<InchMeterPreFabricatedDrawingEntry> findByWorkOrderAndVehicleNumberAndLoadNumberAndPlotNumber(String workOrder, String vehicleNumber, String loadNumber, String plotNumber);
    
    List<InchMeterPreFabricatedDrawingEntry> findByMarkNo(String markNo);
    
    @Query("SELECT DISTINCT i.workOrder FROM InchMeterPreFabricatedDrawingEntry i ORDER BY i.workOrder")
    List<String> findDistinctWorkOrders();
    
    @Query("SELECT DISTINCT i.vehicleNumber FROM InchMeterPreFabricatedDrawingEntry i WHERE i.workOrder = :workOrder ORDER BY i.vehicleNumber")
    List<String> findDistinctVehicleNumbersByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT i.loadNumber FROM InchMeterPreFabricatedDrawingEntry i WHERE i.workOrder = :workOrder ORDER BY i.loadNumber")
    List<String> findDistinctLoadNumbersByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT i.plotNumber FROM InchMeterPreFabricatedDrawingEntry i WHERE i.workOrder = :workOrder ORDER BY i.plotNumber")
    List<String> findDistinctPlotNumbersByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT i.serviceDescription FROM InchMeterPreFabricatedDrawingEntry i WHERE i.workOrder = :workOrder ORDER BY i.serviceDescription")
    List<String> findDistinctServiceDescriptionsByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT i.clientName FROM InchMeterPreFabricatedDrawingEntry i WHERE i.workOrder = :workOrder ORDER BY i.clientName")
    List<String> findDistinctClientNamesByWorkOrder(@Param("workOrder") String workOrder);
    
    void deleteByWorkOrder(String workOrder);
    
    void deleteByWorkOrderAndVehicleNumberAndLoadNumberAndPlotNumber(String workOrder, String vehicleNumber, String loadNumber, String plotNumber);
}
