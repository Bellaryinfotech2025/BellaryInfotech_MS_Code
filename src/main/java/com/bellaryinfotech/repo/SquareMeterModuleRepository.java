package com.bellaryinfotech.repo;
 

 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.SquareMeterModule;

import java.util.List;

@Repository
public interface SquareMeterModuleRepository extends JpaRepository<SquareMeterModule, Long> {

    // Find by work order
    List<SquareMeterModule> findByWorkOrder(String workOrder);

    // Find by mark number
    List<SquareMeterModule> findByMarkNo(String markNo);

    // Find by client name
    List<SquareMeterModule> findByClientName(String clientName);

    // Find by building name
    List<SquareMeterModule> findByBuildingName(String buildingName);

    // Find by work order and building name
    List<SquareMeterModule> findByWorkOrderAndBuildingName(String workOrder, String buildingName);

    // Find by work order and mark number
    List<SquareMeterModule> findByWorkOrderAndMarkNo(String workOrder, String markNo);

    // Get distinct work orders
    @Query("SELECT DISTINCT s.workOrder FROM SquareMeterModule s WHERE s.workOrder IS NOT NULL ORDER BY s.workOrder")
    List<String> findDistinctWorkOrders();

    // Get distinct building names by work order
    @Query("SELECT DISTINCT s.buildingName FROM SquareMeterModule s WHERE s.workOrder = :workOrder AND s.buildingName IS NOT NULL ORDER BY s.buildingName")
    List<String> findDistinctBuildingNamesByWorkOrder(@Param("workOrder") String workOrder);

    // Get distinct mark numbers by work order
    @Query("SELECT DISTINCT s.markNo FROM SquareMeterModule s WHERE s.workOrder = :workOrder AND s.markNo IS NOT NULL ORDER BY s.markNo")
    List<String> findDistinctMarkNosByWorkOrder(@Param("workOrder") String workOrder);

    // Find by tenant ID
    List<SquareMeterModule> findByTenantId(String tenantId);

    // Custom query to search by multiple criteria
    @Query("SELECT s FROM SquareMeterModule s WHERE " +
           "(:workOrder IS NULL OR s.workOrder = :workOrder) AND " +
           "(:buildingName IS NULL OR s.buildingName = :buildingName) AND " +
           "(:markNo IS NULL OR s.markNo = :markNo) AND " +
           "(:clientName IS NULL OR s.clientName = :clientName)")
    List<SquareMeterModule> findByMultipleCriteria(
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
    List<SquareMeterModule> findByDrawingNo(String drawingNo);

    // Get sum of total area by work order
    @Query("SELECT SUM(s.totalArea) FROM SquareMeterModule s WHERE s.workOrder = :workOrder")
    Double getTotalAreaByWorkOrder(@Param("workOrder") String workOrder);

    // Get sum of total area by mark number
    @Query("SELECT SUM(s.totalArea) FROM SquareMeterModule s WHERE s.markNo = :markNo")
    Double getTotalAreaByMarkNo(@Param("markNo") String markNo);
}
