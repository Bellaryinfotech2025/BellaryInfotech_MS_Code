package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.ErectionDrawingEntryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ErectionDrawingEntryService {

    // Create operations
    List<ErectionDrawingEntryDto> createErectionEntry(ErectionDrawingEntryDto erectionEntryDto);
    List<ErectionDrawingEntryDto> createErectionEntries(List<ErectionDrawingEntryDto> erectionEntryDtos);

    // Read operations
    Optional<ErectionDrawingEntryDto> getErectionEntryById(String lineId);
    Page<ErectionDrawingEntryDto> getAllErectionEntries(Pageable pageable);
    List<ErectionDrawingEntryDto> getAllErectionEntries();
    List<ErectionDrawingEntryDto> getErectionEntriesByDrawingNo(String drawingNo);
    List<ErectionDrawingEntryDto> getErectionEntriesByMarkNo(String markNo);
    List<ErectionDrawingEntryDto> getErectionEntriesBySessionCode(String sessionCode);
    Page<ErectionDrawingEntryDto> getErectionEntriesByTenantId(String tenantId, Pageable pageable);
    List<ErectionDrawingEntryDto> getErectionEntriesByStatus(String status);
    Page<ErectionDrawingEntryDto> getErectionEntriesByStatus(String status, Pageable pageable);

    // Search operations
    Page<ErectionDrawingEntryDto> searchErectionEntries(String drawingNo, String markNo, String sessionCode, 
                                                       String tenantId, String status, Pageable pageable);
    List<ErectionDrawingEntryDto> getErectionEntriesByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<ErectionDrawingEntryDto> getErectionEntriesByMarkedQtyGreaterThan(BigDecimal markedQty);

    // Update operations
    ErectionDrawingEntryDto updateErectionEntry(String lineId, ErectionDrawingEntryDto erectionEntryDto);

    // Delete operations
    void deleteErectionEntry(String lineId);
    void deleteErectionEntriesByDrawingNo(String drawingNo);
    void deleteErectionEntriesByMarkNo(String markNo);
    void deleteErectionEntriesByStatus(String status);
    void bulkDeleteErectionEntries(List<String> lineIds);

    // Count operations
    Long getCountByDrawingNo(String drawingNo);
    Long getCountByMarkNo(String markNo);
    Long getCountByStatus(String status);
    long getTotalCount();

    // Sum operations
    BigDecimal getSumMarkedQtyByDrawingNo(String drawingNo);
    BigDecimal getSumTotalMarkedWgtByDrawingNo(String drawingNo);

    // Distinct values
    List<String> getDistinctDrawingNumbers();
    List<String> getDistinctMarkNumbers();
    List<String> getDistinctSessionCodes();
    List<String> getDistinctStatuses();

    // Existence checks
    boolean existsById(String lineId);
    boolean existsByDrawingNoAndMarkNo(String drawingNo, String markNo);
    boolean existsByDrawingNoAndMarkNoAndStatus(String drawingNo, String markNo, String status);

    // Latest entries
    Optional<ErectionDrawingEntryDto> getLatestByDrawingNo(String drawingNo);
    Optional<ErectionDrawingEntryDto> getLatestByMarkNo(String markNo);
    Optional<ErectionDrawingEntryDto> getLatestByStatus(String status);

    // Unique combinations
    List<ErectionDrawingEntryDto> getUniqueDrawingMarkCombinations();
    
    
    //newly added requested fields
 // ADD these new methods to your existing ErectionDrawingEntryService.java

    /**
     * Get distinct departments (attribute3V) filtered by work order and building name
     */
    List<String> getDistinctDepartmentsByWorkOrderAndBuildingName(String workOrder, String buildingName);

    /**
     * Get erection entries by work order, building name, and department
     */
    List<ErectionDrawingEntryDto> getErectionEntriesByWorkOrderAndBuildingNameAndDepartment(String workOrder, String buildingName, String department);
}
