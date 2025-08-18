package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.InchMeterFabModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InchMeterFabModuleRepository extends JpaRepository<InchMeterFabModule, Long> {

    // Find by work order
    List<InchMeterFabModule> findByWorkOrderAndStatus(String workOrder, String status);

    // Find by work order and building name
    List<InchMeterFabModule> findByWorkOrderAndBuildingNameAndStatus(String workOrder, String buildingName, String status);

    // Find by work order, building name, and drawing number
    List<InchMeterFabModule> findByWorkOrderAndBuildingNameAndDrawingNoAndStatus(
            String workOrder, String buildingName, String drawingNo, String status);

    // Find by work order, building name, drawing number, and mark number
    List<InchMeterFabModule> findByWorkOrderAndBuildingNameAndDrawingNoAndMarkNoAndStatus(
            String workOrder, String buildingName, String drawingNo, String markNo, String status);

    // Find by RA Number
    List<InchMeterFabModule> findByRaNoAndStatus(String raNo, String status);

    // Find by multiple criteria with custom query
    @Query("SELECT i FROM InchMeterFabModule i WHERE " +
           "(:workOrder IS NULL OR i.workOrder = :workOrder) AND " +
           "(:buildingName IS NULL OR i.buildingName = :buildingName) AND " +
           "(:drawingNo IS NULL OR i.drawingNo = :drawingNo) AND " +
           "(:markNo IS NULL OR i.markNo = :markNo) AND " +
           "(:raNo IS NULL OR i.raNo = :raNo) AND " +
           "i.status = :status " +
           "ORDER BY i.createdDate DESC")
    List<InchMeterFabModule> findByMultipleCriteria(
            @Param("workOrder") String workOrder,
            @Param("buildingName") String buildingName,
            @Param("drawingNo") String drawingNo,
            @Param("markNo") String markNo,
            @Param("raNo") String raNo,
            @Param("status") String status);

    // Get distinct work orders
    @Query("SELECT DISTINCT i.workOrder FROM InchMeterFabModule i WHERE i.status = :status ORDER BY i.workOrder")
    List<String> findDistinctWorkOrdersByStatus(@Param("status") String status);

    // Get distinct building names by work order
    @Query("SELECT DISTINCT i.buildingName FROM InchMeterFabModule i WHERE i.workOrder = :workOrder AND i.status = :status ORDER BY i.buildingName")
    List<String> findDistinctBuildingNamesByWorkOrderAndStatus(@Param("workOrder") String workOrder, @Param("status") String status);

    // Get distinct drawing numbers by work order and building name
    @Query("SELECT DISTINCT i.drawingNo FROM InchMeterFabModule i WHERE i.workOrder = :workOrder AND i.buildingName = :buildingName AND i.status = :status ORDER BY i.drawingNo")
    List<String> findDistinctDrawingNosByWorkOrderAndBuildingNameAndStatus(
            @Param("workOrder") String workOrder, 
            @Param("buildingName") String buildingName, 
            @Param("status") String status);

    // Get distinct mark numbers by work order, building name, and drawing number
    @Query("SELECT DISTINCT i.markNo FROM InchMeterFabModule i WHERE i.workOrder = :workOrder AND i.buildingName = :buildingName AND i.drawingNo = :drawingNo AND i.status = :status ORDER BY i.markNo")
    List<String> findDistinctMarkNosByWorkOrderAndBuildingNameAndDrawingNoAndStatus(
            @Param("workOrder") String workOrder, 
            @Param("buildingName") String buildingName, 
            @Param("drawingNo") String drawingNo, 
            @Param("status") String status);

    // Get distinct RA Numbers
    @Query("SELECT DISTINCT i.raNo FROM InchMeterFabModule i WHERE i.status = :status ORDER BY i.raNo")
    List<String> findDistinctRaNosByStatus(@Param("status") String status);

    // NEW: Get distinct service descriptions by work order
    @Query("SELECT DISTINCT i.serviceDescription FROM InchMeterFabModule i WHERE i.workOrder = :workOrder AND i.status = :status ORDER BY i.serviceDescription")
    List<String> findDistinctServiceDescriptionsByWorkOrderAndStatus(@Param("workOrder") String workOrder, @Param("status") String status);

    // Check if record exists with same criteria
    @Query("SELECT COUNT(i) > 0 FROM InchMeterFabModule i WHERE " +
           "i.workOrder = :workOrder AND " +
           "i.buildingName = :buildingName AND " +
           "i.drawingNo = :drawingNo AND " +
           "i.markNo = :markNo AND " +
           "i.itemNo = :itemNo AND " +
           "i.status = :status")
    boolean existsByWorkOrderAndBuildingNameAndDrawingNoAndMarkNoAndItemNoAndStatus(
            @Param("workOrder") String workOrder,
            @Param("buildingName") String buildingName,
            @Param("drawingNo") String drawingNo,
            @Param("markNo") String markNo,
            @Param("itemNo") String itemNo,
            @Param("status") String status);

    // Find active records only
    List<InchMeterFabModule> findByStatusOrderByCreatedDateDesc(String status);

    // Find by ID and status
    Optional<InchMeterFabModule> findByIdAndStatus(Long id, String status);

    // Count records by status
    long countByStatus(String status);

    // Count records by work order and status
    long countByWorkOrderAndStatus(String workOrder, String status);

    // Count records by RA Number and status
    long countByRaNoAndStatus(String raNo, String status);
}