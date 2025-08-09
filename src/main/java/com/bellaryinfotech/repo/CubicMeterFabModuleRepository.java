package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.CubicMeterFabModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CubicMeterFabModuleRepository extends JpaRepository<CubicMeterFabModule, Long> {

    // Find by work order and building name
    List<CubicMeterFabModule> findByWorkOrderAndBuildingName(String workOrder, String buildingName);

    // Find by work order, building name, drawing no, and mark no
    List<CubicMeterFabModule> findByWorkOrderAndBuildingNameAndDrawingNoAndMarkNo(
            String workOrder, String buildingName, String drawingNo, String markNo);

    // Find by RA NO
    List<CubicMeterFabModule> findByRaNo(String raNo);

    // NEW: Find by work order only
    List<CubicMeterFabModule> findByWorkOrder(String workOrder);

    // Get distinct work orders
    @Query("SELECT DISTINCT c.workOrder FROM CubicMeterFabModule c WHERE c.workOrder IS NOT NULL AND c.workOrder <> ''")
    List<String> findDistinctWorkOrders();

    // Get distinct building names by work order
    @Query("SELECT DISTINCT c.buildingName FROM CubicMeterFabModule c WHERE c.workOrder = :workOrder AND c.buildingName IS NOT NULL AND c.buildingName <> ''")
    List<String> findDistinctBuildingNamesByWorkOrder(@Param("workOrder") String workOrder);

    // Get distinct RA NOs
    @Query("SELECT DISTINCT c.raNo FROM CubicMeterFabModule c WHERE c.raNo IS NOT NULL AND c.raNo <> '' AND c.raNo <> 'Not assigned yet'")
    List<String> findDistinctRaNos();

    // Search query for CubicMeterFabModules
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

    // Count active CubicMeterFabModules
    @Query("SELECT COUNT(c) FROM CubicMeterFabModule c WHERE c.status = 'ACTIVE'")
    Long countActiveCubicMeterFabModules();
}
