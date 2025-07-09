package com.bellaryinfotech.repo;

 
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.FabricationDrawingEntry;

import java.util.List;

@Repository
public interface FabricationDrawingEntryRepository extends JpaRepository<FabricationDrawingEntry, Long> {
    
    List<FabricationDrawingEntry> findByWorkOrderAndBuildingName(String workOrder, String buildingName);
    
    List<FabricationDrawingEntry> findByDrawingNoAndMarkNo(String drawingNo, String markNo);
    
    List<FabricationDrawingEntry> findByOrderId(Long orderId);
    
    List<FabricationDrawingEntry> findByWorkOrder(String workOrder);
    
    List<FabricationDrawingEntry> findByBuildingName(String buildingName);
    
    List<FabricationDrawingEntry> findByDrawingNo(String drawingNo);
    
    List<FabricationDrawingEntry> findByMarkNo(String markNo);
    
    List<FabricationDrawingEntry> findByRaNo(String raNo);
    
    @Query("SELECT f FROM FabricationDrawingEntry f WHERE " +
           "(:workOrder IS NULL OR f.workOrder = :workOrder) AND " +
           "(:buildingName IS NULL OR f.buildingName = :buildingName) AND " +
           "(:drawingNo IS NULL OR f.drawingNo = :drawingNo) AND " +
           "(:markNo IS NULL OR f.markNo = :markNo)")
    List<FabricationDrawingEntry> findByMultipleFilters(@Param("workOrder") String workOrder,
                                                       @Param("buildingName") String buildingName,
                                                       @Param("drawingNo") String drawingNo,
                                                       @Param("markNo") String markNo);
    
    @Query("SELECT COUNT(f) FROM FabricationDrawingEntry f WHERE f.workOrder = :workOrder")
    Long countByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT COUNT(f) FROM FabricationDrawingEntry f WHERE f.buildingName = :buildingName")
    Long countByBuildingName(@Param("buildingName") String buildingName);
    
    @Query("SELECT COUNT(f) FROM FabricationDrawingEntry f WHERE f.drawingNo = :drawingNo AND f.markNo = :markNo")
    Long countByDrawingNoAndMarkNo(@Param("drawingNo") String drawingNo, @Param("markNo") String markNo);
    
    boolean existsByLineId(Long lineId);
    
    void deleteByLineId(Long lineId);
}
