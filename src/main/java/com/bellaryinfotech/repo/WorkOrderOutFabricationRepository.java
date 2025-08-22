package com.bellaryinfotech.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.WorkOrderOutFabrication;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkOrderOutFabricationRepository extends JpaRepository<WorkOrderOutFabrication, Long> {
    
    // ============ EXISTING REPOSITORY METHODS ============
    
    List<WorkOrderOutFabrication> findByWorkOrderOrderByIdAsc(String workOrder);
    
    List<WorkOrderOutFabrication> findByWorkOrderAndClientNameOrderByIdAsc(String workOrder, String clientName);
    
    List<WorkOrderOutFabrication> findByWorkOrderAndDrawingNoOrderByIdAsc(String workOrder, String drawingNo);
    
    List<WorkOrderOutFabrication> findByWorkOrderAndMarkNoOrderByIdAsc(String workOrder, String markNo);
    
    List<WorkOrderOutFabrication> findByWorkOrderAndBuildingNameOrderByIdAsc(String workOrder, String buildingName);
    
    @Query("SELECT w FROM WorkOrderOutFabrication w WHERE " +
           "(:workOrder IS NULL OR w.workOrder = :workOrder) AND " +
           "(:clientName IS NULL OR w.clientName = :clientName) AND " +
           "(:drawingNo IS NULL OR w.drawingNo = :drawingNo) AND " +
           "(:markNo IS NULL OR w.markNo = :markNo) AND " +
           "(:buildingName IS NULL OR w.buildingName = :buildingName) " +
           "ORDER BY w.id ASC")
    List<WorkOrderOutFabrication> findByMultipleCriteria(
            @Param("workOrder") String workOrder,
            @Param("clientName") String clientName,
            @Param("drawingNo") String drawingNo,
            @Param("markNo") String markNo,
            @Param("buildingName") String buildingName);
    
    @Query("SELECT w FROM WorkOrderOutFabrication w WHERE w.workOrder = :workOrder AND w.raNo IS NOT NULL ORDER BY w.id ASC LIMIT 1")
    Optional<WorkOrderOutFabrication> findRaNoByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT w FROM WorkOrderOutFabrication w WHERE w.workOrder = :workOrder AND w.subAgencyRaNo IS NOT NULL ORDER BY w.id ASC LIMIT 1")
    Optional<WorkOrderOutFabrication> findSubAgencyRaNoByWorkOrder(@Param("workOrder") String workOrder);
    
    void deleteByWorkOrderAndDrawingNoAndMarkNo(String workOrder, String drawingNo, String markNo);
    
    void deleteByWorkOrder(String workOrder);
    
    boolean existsByWorkOrderAndDrawingNoAndMarkNo(String workOrder, String drawingNo, String markNo);
    
    List<WorkOrderOutFabrication> findByOriginalEntryIdOrderByIdAsc(Long originalEntryId);
    
    // ============ NEW: WORK ORDER OUT RESULT REPOSITORY METHODS ============
    
    @Query("SELECT DISTINCT w.clientName FROM WorkOrderOutFabrication w WHERE w.clientName IS NOT NULL ORDER BY w.clientName")
    List<String> findDistinctClientNames();
    
    @Query("SELECT DISTINCT w.workOrder FROM WorkOrderOutFabrication w WHERE w.clientName = :clientName AND w.workOrder IS NOT NULL ORDER BY w.workOrder")
    List<String> findDistinctWorkOrdersByClientName(@Param("clientName") String clientName);
    
    @Query("SELECT DISTINCT w.serviceDescription FROM WorkOrderOutFabrication w WHERE w.clientName = :clientName AND w.workOrder = :workOrder AND w.serviceDescription IS NOT NULL ORDER BY w.serviceDescription")
    List<String> findDistinctServiceDescriptionsByClientAndWorkOrder(@Param("clientName") String clientName, @Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT w.raNo FROM WorkOrderOutFabrication w WHERE w.clientName = :clientName AND w.workOrder = :workOrder AND w.serviceDescription = :serviceDescription AND w.raNo IS NOT NULL ORDER BY w.raNo")
    List<String> findDistinctRaNosByClientWorkOrderAndService(@Param("clientName") String clientName, @Param("workOrder") String workOrder, @Param("serviceDescription") String serviceDescription);
    
    @Query("SELECT w FROM WorkOrderOutFabrication w WHERE w.clientName = :clientName AND w.workOrder = :workOrder AND w.serviceDescription = :serviceDescription AND w.raNo = :raNo ORDER BY w.id ASC")
    List<WorkOrderOutFabrication> findByClientWorkOrderServiceAndRaNo(@Param("clientName") String clientName, @Param("workOrder") String workOrder, @Param("serviceDescription") String serviceDescription, @Param("raNo") String raNo);

    // ============ NEW: SUB AGENCY NAME REPOSITORY METHODS ============
    
    @Query("SELECT DISTINCT w.subAgencyName FROM WorkOrderOutFabrication w WHERE w.clientName = :clientName AND w.workOrder = :workOrder AND w.serviceDescription = :serviceDescription AND w.subAgencyName IS NOT NULL ORDER BY w.subAgencyName")
    List<String> findDistinctSubAgencyNamesByClientWorkOrderAndService(@Param("clientName") String clientName, @Param("workOrder") String workOrder, @Param("serviceDescription") String serviceDescription);
    
    @Query("SELECT COUNT(w) FROM WorkOrderOutFabrication w WHERE w.clientName = :clientName AND w.workOrder = :workOrder AND w.serviceDescription = :serviceDescription")
    Long countByClientNameAndWorkOrderAndServiceDescription(@Param("clientName") String clientName, @Param("workOrder") String workOrder, @Param("serviceDescription") String serviceDescription);
    
    @Query("SELECT DISTINCT w.raNo FROM WorkOrderOutFabrication w WHERE w.clientName = :clientName AND w.workOrder = :workOrder AND w.serviceDescription = :serviceDescription AND w.subAgencyName = :subAgencyName AND w.raNo IS NOT NULL ORDER BY w.raNo")
    List<String> findDistinctRaNosByAllFiltersWithSubAgency(@Param("clientName") String clientName, @Param("workOrder") String workOrder, @Param("serviceDescription") String serviceDescription, @Param("subAgencyName") String subAgencyName);
    
    @Query("SELECT w FROM WorkOrderOutFabrication w WHERE w.clientName = :clientName AND w.workOrder = :workOrder AND w.serviceDescription = :serviceDescription AND w.raNo = :raNo AND w.subAgencyName = :subAgencyName ORDER BY w.id ASC")
    List<WorkOrderOutFabrication> searchByAllFiltersWithSubAgency(@Param("clientName") String clientName, @Param("workOrder") String workOrder, @Param("serviceDescription") String serviceDescription, @Param("raNo") String raNo, @Param("subAgencyName") String subAgencyName);
    
    @Query("SELECT w FROM WorkOrderOutFabrication w WHERE w.clientName = :clientName AND w.workOrder = :workOrder AND w.serviceDescription = :serviceDescription AND w.raNo = :raNo ORDER BY w.id ASC")
    List<WorkOrderOutFabrication> searchByAllFiltersWithoutSubAgency(@Param("clientName") String clientName, @Param("workOrder") String workOrder, @Param("serviceDescription") String serviceDescription, @Param("raNo") String raNo);
}
