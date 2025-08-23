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

    // NEW: Method to fetch distinct RA numbers
    @Query("SELECT DISTINCT f.raNo FROM FabricationDrawingEntry f WHERE f.raNo IS NOT NULL AND f.raNo != ''")
    List<String> findDistinctRaNo();

    // NEW: Method to fetch distinct drawing numbers by order ID
    @Query("SELECT DISTINCT f.drawingNo FROM FabricationDrawingEntry f WHERE f.orderId = :orderId AND f.drawingNo IS NOT NULL AND f.drawingNo != '' ORDER BY f.drawingNo")
    List<String> findDistinctDrawingNoByOrderId(@Param("orderId") Long orderId);

    // NEW: Method to fetch distinct mark numbers by order ID
    @Query("SELECT DISTINCT f.markNo FROM FabricationDrawingEntry f WHERE f.orderId = :orderId AND f.markNo IS NOT NULL AND f.markNo != '' ORDER BY f.markNo")
    List<String> findDistinctMarkNoByOrderId(@Param("orderId") Long orderId);

    // NEW: Method to sum total_marked_wgt by orderId, drawingNo, and markNo
    @Query("SELECT SUM(f.totalMarkedWgt) FROM FabricationDrawingEntry f WHERE f.orderId = :orderId AND f.drawingNo = :drawingNo AND f.markNo = :markNo")
    Double sumTotalMarkedWgtByOrderIdAndDrawingNoAndMarkNo(@Param("orderId") Long orderId, @Param("drawingNo") String drawingNo, @Param("markNo") String markNo);


    
    @Query("SELECT DISTINCT f.itemNo FROM FabricationDrawingEntry f WHERE f.itemNo IS NOT NULL AND f.itemNo != ''")
    List<String> findDistinctItemNo();
}
