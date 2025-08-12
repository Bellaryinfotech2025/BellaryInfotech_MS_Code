package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.SquareMeterPreFabricatedDrawingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SquareMeterPreFabricatedDrawingEntryRepository extends JpaRepository<SquareMeterPreFabricatedDrawingEntry, Long> {
    
    List<SquareMeterPreFabricatedDrawingEntry> findByWorkOrder(String workOrder);
    
    List<SquareMeterPreFabricatedDrawingEntry> findByMarkNo(String markNo);
    
    @Query("SELECT s FROM SquareMeterPreFabricatedDrawingEntry s WHERE s.workOrder = :workOrder AND s.vehicleNumber = :vehicleNumber")
    List<SquareMeterPreFabricatedDrawingEntry> findByWorkOrderAndVehicleNumber(@Param("workOrder") String workOrder, @Param("vehicleNumber") String vehicleNumber);
    
    @Query("SELECT s FROM SquareMeterPreFabricatedDrawingEntry s WHERE s.workOrder = :workOrder AND s.loadNumber = :loadNumber")
    List<SquareMeterPreFabricatedDrawingEntry> findByWorkOrderAndLoadNumber(@Param("workOrder") String workOrder, @Param("loadNumber") String loadNumber);
    
    @Query("SELECT DISTINCT s.workOrder FROM SquareMeterPreFabricatedDrawingEntry s")
    List<String> findDistinctWorkOrders();
    
    void deleteByWorkOrder(String workOrder);
    
    void deleteByMarkNo(String markNo);
}
