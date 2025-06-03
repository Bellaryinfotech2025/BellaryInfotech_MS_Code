package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.AlignmentDrawingEntryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AlignmentDrawingEntryService {

    // Create operations
    List<AlignmentDrawingEntryDto> createAlignmentEntry(AlignmentDrawingEntryDto alignmentEntryDto);
    List<AlignmentDrawingEntryDto> createAlignmentEntries(List<AlignmentDrawingEntryDto> alignmentEntryDtos);

    // Read operations
    Optional<AlignmentDrawingEntryDto> getAlignmentEntryById(String lineId);
    Page<AlignmentDrawingEntryDto> getAllAlignmentEntries(Pageable pageable);
    List<AlignmentDrawingEntryDto> getAllAlignmentEntries();
    List<AlignmentDrawingEntryDto> getAlignmentEntriesByDrawingNo(String drawingNo);
    List<AlignmentDrawingEntryDto> getAlignmentEntriesByMarkNo(String markNo);
    List<AlignmentDrawingEntryDto> getAlignmentEntriesBySessionCode(String sessionCode);
    Page<AlignmentDrawingEntryDto> getAlignmentEntriesByTenantId(String tenantId, Pageable pageable);
    List<AlignmentDrawingEntryDto> getAlignmentEntriesByStatus(String status);
    Page<AlignmentDrawingEntryDto> getAlignmentEntriesByStatus(String status, Pageable pageable);

    // Search operations
    Page<AlignmentDrawingEntryDto> searchAlignmentEntries(String drawingNo, String markNo, String sessionCode, 
                                                         String tenantId, String status, Pageable pageable);
    List<AlignmentDrawingEntryDto> getAlignmentEntriesByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<AlignmentDrawingEntryDto> getAlignmentEntriesByMarkedQtyGreaterThan(BigDecimal markedQty);

    // Update operations
    AlignmentDrawingEntryDto updateAlignmentEntry(String lineId, AlignmentDrawingEntryDto alignmentEntryDto);

    // Delete operations
    void deleteAlignmentEntry(String lineId);
    void deleteAlignmentEntriesByDrawingNo(String drawingNo);
    void deleteAlignmentEntriesByMarkNo(String markNo);
    void deleteAlignmentEntriesByStatus(String status);
    void bulkDeleteAlignmentEntries(List<String> lineIds);

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
    Optional<AlignmentDrawingEntryDto> getLatestByDrawingNo(String drawingNo);
    Optional<AlignmentDrawingEntryDto> getLatestByMarkNo(String markNo);
    Optional<AlignmentDrawingEntryDto> getLatestByStatus(String status);

    // Unique combinations
    List<AlignmentDrawingEntryDto> getUniqueDrawingMarkCombinations();
}
