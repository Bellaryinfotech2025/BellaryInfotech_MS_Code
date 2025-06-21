package com.bellaryinfotech.DAO;

import com.bellaryinfotech.model.BitsDrawingEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BitsDrawingEntryDao {

    /**
     * Save a single drawing entry
     */
    BitsDrawingEntry save(BitsDrawingEntry bitsDrawingEntry);

    /**
     * Save multiple drawing entries
     */
    List<BitsDrawingEntry> saveAll(List<BitsDrawingEntry> bitsDrawingEntries);

    /**
     * Find drawing entry by line ID
     */
    Optional<BitsDrawingEntry> findById(Long lineId); // Changed from String to Long

    /**
     * Find all drawing entries with pagination
     */
    Page<BitsDrawingEntry> findAll(Pageable pageable);

    /**
     * Find all drawing entries
     */
    List<BitsDrawingEntry> findAll();

    /**
     * Find drawing entries by drawing number
     */
    List<BitsDrawingEntry> findByDrawingNo(String drawingNo);

    /**
     * Find drawing entries by mark number
     */
    List<BitsDrawingEntry> findByMarkNo(String markNo);

    /**
     * Find drawing entries by session code
     */
    List<BitsDrawingEntry> findBySessionCode(String sessionCode);

    /**
     * Find drawing entries by tenant ID
     */
    Page<BitsDrawingEntry> findByTenantId(String tenantId, Pageable pageable);

    /**
     * Find drawing entries by multiple criteria
     */
    Page<BitsDrawingEntry> findByMultipleCriteria(String drawingNo, String markNo, 
                                                  String sessionCode, String tenantId, 
                                                  Pageable pageable);

    /**
     * Find drawing entries created between dates
     */
    List<BitsDrawingEntry> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find drawing entries with marked quantity greater than specified value
     */
    List<BitsDrawingEntry> findByMarkedQtyGreaterThan(BigDecimal markedQty);

    /**
     * Get count of entries by drawing number
     */
    Long countByDrawingNo(String drawingNo);

    /**
     * Get sum of marked quantities by drawing number
     */
    BigDecimal sumMarkedQtyByDrawingNo(String drawingNo);

    /**
     * Get sum of total marked weight by drawing number
     */
    BigDecimal sumTotalMarkedWgtByDrawingNo(String drawingNo);

    /**
     * Find distinct drawing numbers
     */
    List<String> findDistinctDrawingNumbers();

    /**
     * Find distinct mark numbers
     */
    List<String> findDistinctMarkNumbers();

    /**
     * Find distinct session codes
     */
    List<String> findDistinctSessionCodes();

    /**
     * Check if entry exists by ID
     */
    boolean existsById(Long lineId); // Changed from String to Long

    /**
     * Check if entry exists by drawing number and mark number
     */
    boolean existsByDrawingNoAndMarkNo(String drawingNo, String markNo);

    /**
     * Update drawing entry
     */
    BitsDrawingEntry update(BitsDrawingEntry bitsDrawingEntry);

    /**
     * Delete drawing entry by ID
     */
    void deleteById(Long lineId); // Changed from String to Long

    /**
     * Delete drawing entry
     */
    void delete(BitsDrawingEntry bitsDrawingEntry);

    /**
     * Delete all drawing entries by drawing number
     */
    void deleteByDrawingNo(String drawingNo);

    /**
     * Delete all drawing entries by mark number
     */
    void deleteByMarkNo(String markNo);

    /**
     * Get total count of all entries
     */
    long count();

    /**
     * Find latest entry by drawing number
     */
    Optional<BitsDrawingEntry> findLatestByDrawingNo(String drawingNo);

    /**
     * Find latest entry by mark number
     */
    Optional<BitsDrawingEntry> findLatestByMarkNo(String markNo);
}
