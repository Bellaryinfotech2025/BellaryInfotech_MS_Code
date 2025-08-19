package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.MtrFabModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MtrFabModuleRepository extends JpaRepository<MtrFabModule, Long> {

    // Find by work order
    List<MtrFabModule> findByWorkOrderAndStatus(String workOrder, String status);

    // Find by work order and building name
    List<MtrFabModule> findByWorkOrderAndBuildingNameAndStatus(String workOrder, String buildingName, String status);

    // Find by work order, building name, and drawing number
    List<MtrFabModule> findByWorkOrderAndBuildingNameAndDrawingNoAndStatus(
            String workOrder, String buildingName, String drawingNo, String status);

    // Find by work order, building name, drawing number, and mark number
    List<MtrFabModule> findByWorkOrderAndBuildingNameAndDrawingNoAndMarkNoAndStatus(
            String workOrder, String buildingName, String drawingNo, String markNo, String status);

    // Find by RA Number
    List<MtrFabModule> findByRaNoAndStatus(String raNo, String status);

    // Find by multiple criteria with custom query
    @Query("SELECT m FROM MtrFabModule m WHERE " +
           "(:workOrder IS NULL OR m.workOrder = :workOrder) AND " +
           "(:buildingName IS NULL OR m.buildingName = :buildingName) AND " +
           "(:drawingNo IS NULL OR m.drawingNo = :drawingNo) AND " +
           "(:markNo IS NULL OR m.markNo = :markNo) AND " +
           "(:raNo IS NULL OR m.raNo = :raNo) AND " +
           "m.status = :status " +
           "ORDER BY m.createdDate DESC")
    List<MtrFabModule> findByMultipleCriteria(
            @Param("workOrder") String workOrder,
            @Param("buildingName") String buildingName,
            @Param("drawingNo") String drawingNo,
            @Param("markNo") String markNo,
            @Param("raNo") String raNo,
            @Param("status") String status);

    // Get distinct work orders
    @Query("SELECT DISTINCT m.workOrder FROM MtrFabModule m WHERE m.status = :status ORDER BY m.workOrder")
    List<String> findDistinctWorkOrdersByStatus(@Param("status") String status);

    // Get distinct building names by work order
    @Query("SELECT DISTINCT m.buildingName FROM MtrFabModule m WHERE m.workOrder = :workOrder AND m.status = :status ORDER BY m.buildingName")
    List<String> findDistinctBuildingNamesByWorkOrderAndStatus(@Param("workOrder") String workOrder, @Param("status") String status);

    // Get distinct drawing numbers by work order and building name
    @Query("SELECT DISTINCT m.drawingNo FROM MtrFabModule m WHERE m.workOrder = :workOrder AND m.buildingName = :buildingName AND m.status = :status ORDER BY m.drawingNo")
    List<String> findDistinctDrawingNosByWorkOrderAndBuildingNameAndStatus(
            @Param("workOrder") String workOrder, 
            @Param("buildingName") String buildingName, 
            @Param("status") String status);

    // Get distinct mark numbers by work order, building name, and drawing number
    @Query("SELECT DISTINCT m.markNo FROM MtrFabModule m WHERE m.workOrder = :workOrder AND m.buildingName = :buildingName AND m.drawingNo = :drawingNo AND m.status = :status ORDER BY m.markNo")
    List<String> findDistinctMarkNosByWorkOrderAndBuildingNameAndDrawingNoAndStatus(
            @Param("workOrder") String workOrder, 
            @Param("buildingName") String buildingName, 
            @Param("drawingNo") String drawingNo, 
            @Param("status") String status);

    // Get distinct RA Numbers
    @Query("SELECT DISTINCT m.raNo FROM MtrFabModule m WHERE m.status = :status ORDER BY m.raNo")
    List<String> findDistinctRaNosByStatus(@Param("status") String status);

    // Check if record exists with same criteria
    @Query("SELECT COUNT(m) > 0 FROM MtrFabModule m WHERE " +
           "m.workOrder = :workOrder AND " +
           "m.buildingName = :buildingName AND " +
           "m.drawingNo = :drawingNo AND " +
           "m.markNo = :markNo AND " +
           "m.itemNo = :itemNo AND " +
           "m.status = :status")
    boolean existsByWorkOrderAndBuildingNameAndDrawingNoAndMarkNoAndItemNoAndStatus(
            @Param("workOrder") String workOrder,
            @Param("buildingName") String buildingName,
            @Param("drawingNo") String drawingNo,
            @Param("markNo") String markNo,
            @Param("itemNo") String itemNo,
            @Param("status") String status);

    // Find active records only
    List<MtrFabModule> findByStatusOrderByCreatedDateDesc(String status);

    // Find by ID and status
    Optional<MtrFabModule> findByIdAndStatus(Long id, String status);

    // Count records by status
    long countByStatus(String status);

    // Count records by work order and status
    long countByWorkOrderAndStatus(String workOrder, String status);

    // Count records by RA Number and status
    long countByRaNoAndStatus(String raNo, String status);
    
    
    
    
    
    
    @Query("SELECT DISTINCT m.clientName FROM MtrFabModule m WHERE m.status = :status AND m.clientName IS NOT NULL ORDER BY m.clientName")
    List<String> findDistinctClientNamesByStatus(@Param("status") String status);

    @Query("SELECT DISTINCT m.workOrder FROM MtrFabModule m WHERE m.clientName = :clientName AND m.status = :status AND m.workOrder IS NOT NULL ORDER BY m.workOrder")
    List<String> findDistinctWorkOrdersByClientNameAndStatus(@Param("clientName") String clientName, @Param("status") String status);

    @Query("SELECT DISTINCT m.serviceDescription FROM MtrFabModule m WHERE m.clientName = :clientName AND m.workOrder = :workOrder AND m.status = :status AND m.serviceDescription IS NOT NULL ORDER BY m.serviceDescription")
    List<String> findDistinctServiceDescriptionsByClientNameAndWorkOrderAndStatus(@Param("clientName") String clientName, @Param("workOrder") String workOrder, @Param("status") String status);

    @Query("SELECT DISTINCT m.raNo FROM MtrFabModule m WHERE m.clientName = :clientName AND m.workOrder = :workOrder AND m.serviceDescription = :serviceDescription AND m.status = :status AND m.raNo IS NOT NULL ORDER BY m.raNo")
    List<String> findDistinctRaNumbersByClientNameAndWorkOrderAndServiceDescriptionAndStatus(@Param("clientName") String clientName, @Param("workOrder") String workOrder, @Param("serviceDescription") String serviceDescription, @Param("status") String status);

    List<MtrFabModule> findByClientNameAndWorkOrderAndServiceDescriptionAndRaNoAndStatus(String clientName, String workOrder, String serviceDescription, String raNo, String status);
    
    
    
    
    
    
    
    
    
}