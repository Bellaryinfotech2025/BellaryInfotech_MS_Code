package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.BitsDrawingEntryDto;
import com.bellaryinfotech.DTO.BitsDrawingEntryStatsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BitsDrawingEntryService {
    /**
     * Create a new drawing entry with marked quantity logic
     * If markedQty is n, create n entries in the database
     */
    List<BitsDrawingEntryDto> createDrawingEntry(BitsDrawingEntryDto drawingEntryDto);

    /**
     * Create multiple drawing entries
     */
    List<BitsDrawingEntryDto> createDrawingEntries(List<BitsDrawingEntryDto> drawingEntryDtos);

    /**
     * Get drawing entry by line ID
     */
    Optional<BitsDrawingEntryDto> getDrawingEntryById(String lineId);

    /**
     * Get all drawing entries with pagination
     */
    Page<BitsDrawingEntryDto> getAllDrawingEntries(Pageable pageable);

    /**
     * Get all drawing entries
     */
    List<BitsDrawingEntryDto> getAllDrawingEntries();

    /**
     * Get drawing entries by drawing number
     */
    List<BitsDrawingEntryDto> getDrawingEntriesByDrawingNo(String drawingNo);

    /**
     * Get drawing entries by mark number
     */
    List<BitsDrawingEntryDto> getDrawingEntriesByMarkNo(String markNo);

    /**
     * Get drawing entries by session code
     */
    List<BitsDrawingEntryDto> getDrawingEntriesBySessionCode(String sessionCode);

    /**
     * Get drawing entries by tenant ID
     */
    Page<BitsDrawingEntryDto> getDrawingEntriesByTenantId(String tenantId, Pageable pageable);

    /**
     * Search drawing entries by multiple criteria
     */
    Page<BitsDrawingEntryDto> searchDrawingEntries(String drawingNo, String markNo,
                                                    String sessionCode, String tenantId,
                                                    Pageable pageable);

    /**
     * Get drawing entries created between dates
     */
    List<BitsDrawingEntryDto> getDrawingEntriesByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Get drawing entries with marked quantity greater than specified value
     */
    List<BitsDrawingEntryDto> getDrawingEntriesByMarkedQtyGreaterThan(BigDecimal markedQty);

    /**
     * Update drawing entry
     */
    BitsDrawingEntryDto updateDrawingEntry(String lineId, BitsDrawingEntryDto drawingEntryDto);

    /**
     * Delete drawing entry by line ID
     */
    void deleteDrawingEntry(String lineId);

    /**
     * Delete drawing entries by drawing number
     */
    void deleteDrawingEntriesByDrawingNo(String drawingNo);

    /**
     * Delete drawing entries by mark number
     */
    void deleteDrawingEntriesByMarkNo(String markNo);

    /**
     * Get count of entries by drawing number
     */
    Long getCountByDrawingNo(String drawingNo);

    /**
     * Get sum of marked quantities by drawing number
     */
    BigDecimal getSumMarkedQtyByDrawingNo(String drawingNo);

    /**
     * Get sum of total marked weight by drawing number
     */
    BigDecimal getSumTotalMarkedWgtByDrawingNo(String drawingNo);

    /**
     * Get distinct drawing numbers
     */
    List<String> getDistinctDrawingNumbers();

    /**
     * Get distinct mark numbers
     */
    List<String> getDistinctMarkNumbers();

    /**
     * Get distinct session codes
     */
    List<String> getDistinctSessionCodes();

    /**
     * Check if drawing entry exists by line ID
     */
    boolean existsById(String lineId);

    /**
     * Check if drawing entry exists by drawing number and mark number
     */
    boolean existsByDrawingNoAndMarkNo(String drawingNo, String markNo);

    /**
     * Get total count of all entries
     */
    long getTotalCount();

    /**
     * Get latest entry by drawing number
     */
    Optional<BitsDrawingEntryDto> getLatestByDrawingNo(String drawingNo);

    /**
     * Get latest entry by mark number
     */
    Optional<BitsDrawingEntryDto> getLatestByMarkNo(String markNo);

    /**
     * Bulk delete drawing entries by line IDs
     */
    void bulkDeleteDrawingEntries(List<String> lineIds);

    /**
     * Get drawing entry statistics by drawing number
     */
    BitsDrawingEntryStatsDto getDrawingEntryStats(String drawingNo);
                /**
     * Get distinct RA numbers
     */
    List<String> getDistinctRaNumbers();
                    //newly added request
    // ADD these new methods to your existing BitsDrawingEntryService.java interface
    /**
     * Get distinct work orders (attribute1V) from bits_drawing_entry
     */
    List<String> getDistinctWorkOrdersFromDrawingEntry();

    /**
     * Get distinct building names (attribute2V) filtered by work order
     */
    List<String> getDistinctBuildingNamesByWorkOrder(String workOrder);

    /**
     * Get distinct drawing numbers filtered by work order and building name
     */
    List<String> getDistinctDrawingNumbersByAttributes(String workOrder, String buildingName);

    /**
     * Get distinct mark numbers filtered by work order and building name
     */
    List<String> getDistinctMarkNumbersByAttributes(String workOrder, String buildingName, String drawingNo);

    /**
     * Get a single drawing entry by work order, building name, drawing number, and mark number
     */
    Optional<BitsDrawingEntryDto> getDrawingEntryDetailsByAttributes(String workOrder, String buildingName, String drawingNo, String markNo);

	void deleteByMarkNo(String markNo);
}
