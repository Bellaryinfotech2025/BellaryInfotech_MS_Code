package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.BillingDrawingEntryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BillingDrawingEntryService {

    // Create operations
    List<BillingDrawingEntryDto> createBillingEntry(BillingDrawingEntryDto billingEntryDto);
    List<BillingDrawingEntryDto> createBillingEntries(List<BillingDrawingEntryDto> billingEntryDtos);

    // Read operations
    Optional<BillingDrawingEntryDto> getBillingEntryById(String lineId);
    Page<BillingDrawingEntryDto> getAllBillingEntries(Pageable pageable);
    List<BillingDrawingEntryDto> getAllBillingEntries();
    List<BillingDrawingEntryDto> getBillingEntriesByDrawingNo(String drawingNo);
    List<BillingDrawingEntryDto> getBillingEntriesByMarkNo(String markNo);
    List<BillingDrawingEntryDto> getBillingEntriesBySessionCode(String sessionCode);
    Page<BillingDrawingEntryDto> getBillingEntriesByTenantId(String tenantId, Pageable pageable);
    List<BillingDrawingEntryDto> getBillingEntriesByStatus(String status);
    Page<BillingDrawingEntryDto> getBillingEntriesByStatus(String status, Pageable pageable);

    // Search operations
    Page<BillingDrawingEntryDto> searchBillingEntries(String drawingNo, String markNo, String sessionCode, 
                                                     String tenantId, String status, Pageable pageable);
    List<BillingDrawingEntryDto> getBillingEntriesByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<BillingDrawingEntryDto> getBillingEntriesByMarkedQtyGreaterThan(BigDecimal markedQty);

    // Update operations
    BillingDrawingEntryDto updateBillingEntry(String lineId, BillingDrawingEntryDto billingEntryDto);

    // Delete operations
    void deleteBillingEntry(String lineId);
    void deleteBillingEntriesByDrawingNo(String drawingNo);
    void deleteBillingEntriesByMarkNo(String markNo);
    void deleteBillingEntriesByStatus(String status);
    void bulkDeleteBillingEntries(List<String> lineIds);

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
    Optional<BillingDrawingEntryDto> getLatestByDrawingNo(String drawingNo);
    Optional<BillingDrawingEntryDto> getLatestByMarkNo(String markNo);
    Optional<BillingDrawingEntryDto> getLatestByStatus(String status);

    // Unique combinations
    List<BillingDrawingEntryDto> getUniqueDrawingMarkCombinations();
}
