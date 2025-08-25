package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.BitsDrawingEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BitsDrawingEntryRepository extends JpaRepository<BitsDrawingEntry, Long> { // Changed from String to Long

    /**
     * Find all entries by drawing number
     */
    List<BitsDrawingEntry> findByDrawingNoOrderByCreationDateDesc(String drawingNo);

    /**
     * Find all entries by mark number
     */
    List<BitsDrawingEntry> findByMarkNoOrderByCreationDateDesc(String markNo);

    /**
     * Find all entries by drawing number and mark number
     */
    List<BitsDrawingEntry> findByDrawingNoAndMarkNoOrderByCreationDateDesc(String drawingNo, String markNo);

    /**
     * Find all entries by session code
     */
    List<BitsDrawingEntry> findBySessionCodeOrderByCreationDateDesc(String sessionCode);

    /**
     * Find all entries by tenant ID
     */
    Page<BitsDrawingEntry> findByTenantIdOrderByCreationDateDesc(String tenantId, Pageable pageable);

    /**
     * Find all entries by order ID
     */
    List<BitsDrawingEntry> findByOrderIdOrderByCreationDateDesc(Long orderId);

    /**
     * Find all entries created by a specific user
     */
    List<BitsDrawingEntry> findByCreatedByOrderByCreationDateDesc(String createdBy);

    /**
     * Find all entries created between dates
     */
    List<BitsDrawingEntry> findByCreationDateBetweenOrderByCreationDateDesc(
            LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find all entries with marked quantity greater than specified value
     */
    List<BitsDrawingEntry> findByMarkedQtyGreaterThanOrderByMarkedQtyDesc(BigDecimal markedQty);

    /**
     * Find all entries with total marked weight greater than specified value
     */
    List<BitsDrawingEntry> findByTotalMarkedWgtGreaterThanOrderByTotalMarkedWgtDesc(BigDecimal totalMarkedWgt);

    /**
     * Custom query to find entries by multiple criteria
     */
    @Query("SELECT bde FROM BitsDrawingEntry bde WHERE " +
           "(:drawingNo IS NULL OR bde.drawingNo LIKE %:drawingNo%) AND " +
           "(:markNo IS NULL OR bde.markNo LIKE %:markNo%) AND " +
           "(:sessionCode IS NULL OR bde.sessionCode = :sessionCode) AND " +
           "(:tenantId IS NULL OR bde.tenantId = :tenantId) AND " +
           "(:orderId IS NULL OR bde.orderId = :orderId) " +
           "ORDER BY bde.creationDate DESC")
    Page<BitsDrawingEntry> findByMultipleCriteria(
            @Param("drawingNo") String drawingNo,
            @Param("markNo") String markNo,
            @Param("sessionCode") String sessionCode,
            @Param("tenantId") String tenantId,
            @Param("orderId") Long orderId,
            Pageable pageable);

    /**
     * Get total count of entries by drawing number
     */
    @Query("SELECT COUNT(bde) FROM BitsDrawingEntry bde WHERE bde.drawingNo = :drawingNo")
    Long countByDrawingNo(@Param("drawingNo") String drawingNo);

    /**
     * Get total count of entries by order ID
     */
    @Query("SELECT COUNT(bde) FROM BitsDrawingEntry bde WHERE bde.orderId = :orderId")
    Long countByOrderId(@Param("orderId") Long orderId);

    /**
     * Get sum of marked quantities by drawing number
     */
    @Query("SELECT SUM(bde.markedQty) FROM BitsDrawingEntry bde WHERE bde.drawingNo = :drawingNo")
    BigDecimal sumMarkedQtyByDrawingNo(@Param("drawingNo") String drawingNo);

    /**
     * Get sum of total marked weight by drawing number
     */
    @Query("SELECT SUM(bde.totalMarkedWgt) FROM BitsDrawingEntry bde WHERE bde.drawingNo = :drawingNo")
    BigDecimal sumTotalMarkedWgtByDrawingNo(@Param("drawingNo") String drawingNo);

    /**
     * Get sum of marked quantities by order ID
     */
    @Query("SELECT SUM(bde.markedQty) FROM BitsDrawingEntry bde WHERE bde.orderId = :orderId")
    BigDecimal sumMarkedQtyByOrderId(@Param("orderId") Long orderId);

    /**
     * Find distinct drawing numbers
     */
    @Query("SELECT DISTINCT bde.drawingNo FROM BitsDrawingEntry bde WHERE bde.drawingNo IS NOT NULL ORDER BY bde.drawingNo")
    List<String> findDistinctDrawingNumbers();

    /**
     * Find distinct mark numbers
     */
    @Query("SELECT DISTINCT bde.markNo FROM BitsDrawingEntry bde WHERE bde.markNo IS NOT NULL ORDER BY bde.markNo")
    List<String> findDistinctMarkNumbers();

    /**
     * Find distinct session codes
     */
    @Query("SELECT DISTINCT bde.sessionCode FROM BitsDrawingEntry bde WHERE bde.sessionCode IS NOT NULL ORDER BY bde.sessionCode")
    List<String> findDistinctSessionCodes();

    /**
     * Check if entry exists by drawing number and mark number
     */
    boolean existsByDrawingNoAndMarkNo(String drawingNo, String markNo);

    /**
     * Check if entry exists by order ID
     */
    boolean existsByOrderId(Long orderId);

    /**
     * Delete all entries by drawing number
     */
    void deleteByDrawingNo(String drawingNo);

    /**
     * Delete all entries by mark number
     */
    void deleteByMarkNo(String markNo);

    /**
     * Delete all entries by order ID
     */
    void deleteByOrderId(Long orderId);

    /**
     * Find entries with null or empty session code
     */
    @Query("SELECT bde FROM BitsDrawingEntry bde WHERE bde.sessionCode IS NULL OR bde.sessionCode = '' ORDER BY bde.creationDate DESC")
    List<BitsDrawingEntry> findEntriesWithoutSessionCode();

    /**
     * Find latest entry by drawing number
     */
    Optional<BitsDrawingEntry> findTopByDrawingNoOrderByCreationDateDesc(String drawingNo);

    /**
     * Find latest entry by mark number
     */
    Optional<BitsDrawingEntry> findTopByMarkNoOrderByCreationDateDesc(String markNo);

    /**
     * Find latest entry by order ID
     */
    Optional<BitsDrawingEntry> findTopByOrderIdOrderByCreationDateDesc(Long orderId);
    
    
    
    /**
     * Find distinct RA numbers
     */
    @Query("SELECT DISTINCT bde.raNo FROM BitsDrawingEntry bde WHERE bde.raNo IS NOT NULL AND bde.raNo != '' ORDER BY bde.raNo")
    List<String> findDistinctRaNumbers();
    
    
    
    


    
    
    
    
    
    
    
    //newly functionaity from requested
 // ADD these new methods to your existing BitsDrawingEntryRepository.java

    /**
     * Get distinct attribute1V (Work Orders) from bits_drawing_entry
     */
    @Query("SELECT DISTINCT bde.attribute1V FROM BitsDrawingEntry bde WHERE bde.attribute1V IS NOT NULL AND bde.attribute1V != '' ORDER BY bde.attribute1V")
    List<String> findDistinctAttribute1V();

    /**
     * Get distinct attribute2V (Building Names) filtered by attribute1V
     */
    @Query("SELECT DISTINCT bde.attribute2V FROM BitsDrawingEntry bde WHERE bde.attribute1V = :attribute1V AND bde.attribute2V IS NOT NULL AND bde.attribute2V != '' ORDER BY bde.attribute2V")
    List<String> findDistinctAttribute2VByAttribute1V(@Param("attribute1V") String attribute1V);

    /**
     * Get distinct drawing numbers filtered by attribute1V and attribute2V
     */
    @Query("SELECT DISTINCT bde.drawingNo FROM BitsDrawingEntry bde WHERE bde.attribute1V = :attribute1V AND bde.attribute2V = :attribute2V AND bde.drawingNo IS NOT NULL AND bde.drawingNo != '' ORDER BY bde.drawingNo")
    List<String> findDistinctDrawingNoByAttributes(@Param("attribute1V") String attribute1V, @Param("attribute2V") String attribute2V);

    /**
     * Get distinct mark numbers filtered by attribute1V and attribute2V
     */
    @Query("SELECT DISTINCT bde.markNo FROM BitsDrawingEntry bde WHERE bde.attribute1V = :attribute1V AND bde.attribute2V = :attribute2V AND bde.markNo IS NOT NULL AND bde.markNo != '' ORDER BY bde.markNo")
    List<String> findDistinctMarkNoByAttributes(@Param("attribute1V") String attribute1V, @Param("attribute2V") String attribute2V);
    
    // NEW: Session name comparison queries
    /**
     * Get session names from bits_drawing_entry by work order (attribute1V) and RA number
     */
    @Query("SELECT bde.drawingNo, bde.markNo, bde.sessionName, bde.orderId FROM BitsDrawingEntry bde WHERE bde.attribute1V = :workOrder AND bde.raNo = :raNo AND bde.sessionName IS NOT NULL ORDER BY bde.drawingNo, bde.markNo")
    List<Object[]> findSessionNamesByWorkOrderAndRaNo(@Param("workOrder") String workOrder, @Param("raNo") String raNo);

    /**
     * Get session names from bits_drawing_entry by order ID
     */
    @Query("SELECT bde.drawingNo, bde.markNo, bde.sessionName, bde.orderId FROM BitsDrawingEntry bde WHERE bde.orderId = :orderId AND bde.sessionName IS NOT NULL ORDER BY bde.drawingNo, bde.markNo")
    List<Object[]> findSessionNamesByOrderId(@Param("orderId") Long orderId);

    /**
     * Get entries by work order (attribute1V) and RA number for session comparison
     */
    @Query("SELECT bde FROM BitsDrawingEntry bde WHERE bde.attribute1V = :workOrder AND bde.raNo = :raNo ORDER BY bde.drawingNo, bde.markNo")
    List<BitsDrawingEntry> findByWorkOrderAndRaNoForComparison(@Param("workOrder") String workOrder, @Param("raNo") String raNo);
}
