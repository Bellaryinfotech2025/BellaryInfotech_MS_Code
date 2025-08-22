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
    
    // Work Order Out Fabrication dropdown queries
    @Query("SELECT DISTINCT w.clientName FROM WorkOrderOutDrawingEntry w WHERE w.clientName IS NOT NULL ORDER BY w.clientName")
    List<String> findDistinctClientNames();
    
    @Query("SELECT DISTINCT w.workOrder FROM WorkOrderOutDrawingEntry w WHERE w.clientName = :clientName AND w.workOrder IS NOT NULL ORDER BY w.workOrder")
    List<String> findDistinctWorkOrdersByClient(@Param("clientName") String clientName);
    
    // NEW: Enhanced queries for cascading dropdowns using reference work order
    @Query("SELECT DISTINCT w.workOrder FROM WorkOrderOutDrawingEntry w WHERE w.clientName = :clientName AND w.workOrder IS NOT NULL ORDER BY w.workOrder")
    List<String> findDistinctReferenceWorkOrdersByClient(@Param("clientName") String clientName);
    
    @Query("SELECT DISTINCT w.serviceDescription FROM WorkOrderOutDrawingEntry w WHERE w.workOrder = :referenceWorkOrder AND w.serviceDescription IS NOT NULL ORDER BY w.serviceDescription")
    List<String> findDistinctServiceDescByReferenceWorkOrder(@Param("referenceWorkOrder") String referenceWorkOrder);
    
    @Query("SELECT DISTINCT w.uom FROM WorkOrderOutDrawingEntry w WHERE w.workOrder = :referenceWorkOrder AND w.serviceDescription = :serviceDescription AND w.uom IS NOT NULL ORDER BY w.uom")
    List<String> findDistinctUOMByReferenceWorkOrderAndService(@Param("referenceWorkOrder") String referenceWorkOrder, @Param("serviceDescription") String serviceDescription);
    
    @Query("SELECT DISTINCT w.dataModule FROM WorkOrderOutDrawingEntry w WHERE w.workOrder = :referenceWorkOrder AND w.serviceDescription = :serviceDescription AND w.uom = :uom AND w.dataModule IS NOT NULL ORDER BY w.dataModule")
    List<String> findDistinctDataModulesByReferenceWorkOrderAndServiceAndUOM(@Param("referenceWorkOrder") String referenceWorkOrder, @Param("serviceDescription") String serviceDescription, @Param("uom") String uom);
    
    @Query("SELECT DISTINCT w.subAgencyName FROM WorkOrderOutDrawingEntry w WHERE w.workOrder = :referenceWorkOrder AND w.subAgencyName IS NOT NULL ORDER BY w.subAgencyName")
    List<String> findDistinctSubAgencyNamesByReferenceWorkOrder(@Param("referenceWorkOrder") String referenceWorkOrder);
    
    // Enhanced queries for service description, UOM, and data module
    @Query("SELECT DISTINCT w.serviceDescription FROM WorkOrderOutDrawingEntry w WHERE w.workOrder = :workOrder AND w.serviceDescription IS NOT NULL ORDER BY w.serviceDescription")
    List<String> findDistinctServiceDescByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT w.uom FROM WorkOrderOutDrawingEntry w WHERE w.workOrder = :workOrder AND w.serviceDescription = :serviceDescription AND w.uom IS NOT NULL ORDER BY w.uom")
    List<String> findDistinctUOMByWorkOrderAndService(@Param("workOrder") String workOrder, @Param("serviceDescription") String serviceDescription);
    
    @Query("SELECT DISTINCT w.dataModule FROM WorkOrderOutDrawingEntry w WHERE w.workOrder = :workOrder AND w.dataModule IS NOT NULL ORDER BY w.dataModule")
    List<String> findDistinctDataModulesByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT w.subAgencyName, w.subAgencyWorkOrderName FROM WorkOrderOutDrawingEntry w WHERE w.workOrder = :workOrder AND w.subAgencyName IS NOT NULL AND w.subAgencyWorkOrderName IS NOT NULL ORDER BY w.id ASC LIMIT 1")
    List<Object[]> findSubAgencyDetailsByWorkOrder(@Param("workOrder") String workOrder);
    
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
