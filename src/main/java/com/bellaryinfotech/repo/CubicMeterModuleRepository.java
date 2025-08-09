package com.bellaryinfotech.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.bellaryinfotech.model.CubicMeterModule;
import java.util.List;

@Repository
public interface CubicMeterModuleRepository extends JpaRepository<CubicMeterModule, Long> {

    // Find by work order
    List<CubicMeterModule> findByWorkOrder(String workOrder);

    // Find by mark number
    List<CubicMeterModule> findByMarkNo(String markNo);

    // Find by client name
    List<CubicMeterModule> findByClientName(String clientName);

    // Find by building name
    List<CubicMeterModule> findByBuildingName(String buildingName);

    // Find by work order and building name
    List<CubicMeterModule> findByWorkOrderAndBuildingName(String workOrder, String buildingName);

    // Find by work order and mark number
    List<CubicMeterModule> findByWorkOrderAndMarkNo(String workOrder, String markNo);

    // Get distinct work orders
    @Query("SELECT DISTINCT c.workOrder FROM CubicMeterModule c WHERE c.workOrder IS NOT NULL ORDER BY c.workOrder")
    List<String> findDistinctWorkOrders();

    // Get distinct building names by work order
    @Query("SELECT DISTINCT c.buildingName FROM CubicMeterModule c WHERE c.workOrder = :workOrder AND c.buildingName IS NOT NULL ORDER BY c.buildingName")
    List<String> findDistinctBuildingNamesByWorkOrder(@Param("workOrder") String workOrder);

    // Get distinct mark numbers by work order
    @Query("SELECT DISTINCT c.markNo FROM CubicMeterModule c WHERE c.workOrder = :workOrder AND c.markNo IS NOT NULL ORDER BY c.markNo")
    List<String> findDistinctMarkNosByWorkOrder(@Param("workOrder") String workOrder);

    // Find by tenant ID
    List<CubicMeterModule> findByTenantId(String tenantId);

    // Custom query to search by multiple criteria
    @Query("SELECT c FROM CubicMeterModule c WHERE " +
           "(:workOrder IS NULL OR c.workOrder = :workOrder) AND " +
           "(:buildingName IS NULL OR c.buildingName = :buildingName) AND " +
           "(:markNo IS NULL OR c.markNo = :markNo) AND " +
           "(:clientName IS NULL OR c.clientName = :clientName)")
    List<CubicMeterModule> findByMultipleCriteria(
            @Param("workOrder") String workOrder,
            @Param("buildingName") String buildingName,
            @Param("markNo") String markNo,
            @Param("clientName") String clientName
    );

    // Delete by work order
    void deleteByWorkOrder(String workOrder);

    // Delete by mark number
    void deleteByMarkNo(String markNo);

    // Count by work order
    long countByWorkOrder(String workOrder);

    // Find by drawing number
    List<CubicMeterModule> findByDrawingNo(String drawingNo);

    // Get sum of total volume by work order
    @Query("SELECT SUM(c.totalVolume) FROM CubicMeterModule c WHERE c.workOrder = :workOrder")
    Double getTotalVolumeByWorkOrder(@Param("workOrder") String workOrder);

    // Get sum of total volume by mark number
    @Query("SELECT SUM(c.totalVolume) FROM CubicMeterModule c WHERE c.markNo = :markNo")
    Double getTotalVolumeByMarkNo(@Param("markNo") String markNo);
}
