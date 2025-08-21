package com.bellaryinfotech.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.WorkOrderOutDrawingEntry;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkOrderOutDrawingEntryRepository extends JpaRepository<WorkOrderOutDrawingEntry, Long> {
    
    List<WorkOrderOutDrawingEntry> findByWorkOrderOrderByIdAsc(String workOrder);
    
     
    List<WorkOrderOutDrawingEntry> findByWorkOrderAndPlantLocationOrderByIdAsc(String workOrder, String plantLocation);
    
    
    List<WorkOrderOutDrawingEntry> findByDrawingNoOrderByIdAsc(String drawingNo);
    
   
    List<WorkOrderOutDrawingEntry> findByMarkNoOrderByIdAsc(String markNo);
    
     
    List<WorkOrderOutDrawingEntry> findByDrawingNoAndMarkNoOrderByIdAsc(String drawingNo, String markNo);
    
    
    List<WorkOrderOutDrawingEntry> findByOrderIdOrderByIdAsc(Long orderId);
    
    
    @Query("SELECT DISTINCT w.workOrder FROM WorkOrderOutDrawingEntry w WHERE w.workOrder IS NOT NULL ORDER BY w.workOrder")
    List<String> findDistinctWorkOrders();
    
    
    @Query("SELECT DISTINCT w.plantLocation FROM WorkOrderOutDrawingEntry w WHERE w.workOrder = :workOrder AND w.plantLocation IS NOT NULL ORDER BY w.plantLocation")
    List<String> findDistinctPlantLocationsByWorkOrder(@Param("workOrder") String workOrder);
    
   
    @Query("SELECT DISTINCT w.drawingNo FROM WorkOrderOutDrawingEntry w WHERE w.workOrder = :workOrder AND w.plantLocation = :plantLocation AND w.drawingNo IS NOT NULL ORDER BY w.drawingNo")
    List<String> findDistinctDrawingNumbersByWorkOrderAndPlantLocation(@Param("workOrder") String workOrder, @Param("plantLocation") String plantLocation);
    
    
    @Query("SELECT DISTINCT w.markNo FROM WorkOrderOutDrawingEntry w WHERE w.workOrder = :workOrder AND w.plantLocation = :plantLocation AND w.markNo IS NOT NULL ORDER BY w.markNo")
    List<String> findDistinctMarkNumbersByWorkOrderAndPlantLocation(@Param("workOrder") String workOrder, @Param("plantLocation") String plantLocation);
    
     
    @Query("SELECT w FROM WorkOrderOutDrawingEntry w WHERE " +
           "(:workOrder IS NULL OR w.workOrder = :workOrder) AND " +
           "(:plantLocation IS NULL OR w.plantLocation = :plantLocation) AND " +
           "(:drawingNo IS NULL OR w.drawingNo = :drawingNo) AND " +
           "(:markNo IS NULL OR w.markNo = :markNo) " +
           "ORDER BY w.id ASC")
    List<WorkOrderOutDrawingEntry> findByMultipleCriteria(
            @Param("workOrder") String workOrder,
            @Param("plantLocation") String plantLocation,
            @Param("drawingNo") String drawingNo,
            @Param("markNo") String markNo);
    
    
    boolean existsByDrawingNoAndMarkNo(String drawingNo, String markNo);
    
    
    void deleteByDrawingNoAndMarkNo(String drawingNo, String markNo);
    
    
    @Query("SELECT w FROM WorkOrderOutDrawingEntry w WHERE w.markNo = :markNo ORDER BY w.id ASC")
    List<WorkOrderOutDrawingEntry> findEntriesForEditingByMarkNo(@Param("markNo") String markNo);
    
     
    @Query("SELECT w FROM WorkOrderOutDrawingEntry w WHERE w.markNo = :markNo ORDER BY w.id ASC LIMIT 1")
    Optional<WorkOrderOutDrawingEntry> findDrawingEntryByMarkNo(@Param("markNo") String markNo);
}
