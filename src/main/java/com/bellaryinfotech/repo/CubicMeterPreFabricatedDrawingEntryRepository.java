package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.CubicMeterPreFabricatedDrawingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CubicMeterPreFabricatedDrawingEntryRepository extends JpaRepository<CubicMeterPreFabricatedDrawingEntry, Long> {
    
    List<CubicMeterPreFabricatedDrawingEntry> findByWorkOrder(String workOrder);
    
    List<CubicMeterPreFabricatedDrawingEntry> findByMarkNo(String markNo);
    
    @Query("SELECT c FROM CubicMeterPreFabricatedDrawingEntry c WHERE c.workOrder = :workOrder AND c.vehicleNumber = :vehicleNumber")
    List<CubicMeterPreFabricatedDrawingEntry> findByWorkOrderAndVehicleNumber(@Param("workOrder") String workOrder, @Param("vehicleNumber") String vehicleNumber);
    
    @Query("SELECT c FROM CubicMeterPreFabricatedDrawingEntry c WHERE c.workOrder = :workOrder AND c.loadNumber = :loadNumber")
    List<CubicMeterPreFabricatedDrawingEntry> findByWorkOrderAndLoadNumber(@Param("workOrder") String workOrder, @Param("loadNumber") String loadNumber);
    
    @Query("SELECT DISTINCT c.workOrder FROM CubicMeterPreFabricatedDrawingEntry c")
    List<String> findDistinctWorkOrders();
    
    void deleteByWorkOrder(String workOrder);
    
    void deleteByMarkNo(String markNo);
}
