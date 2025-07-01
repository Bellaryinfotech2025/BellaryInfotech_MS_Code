package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.ErectionDrawingEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ErectionDrawingEntryRepository extends JpaRepository<ErectionDrawingEntry, Long> { // Changed from String to Long

    // Find by drawing number
    List<ErectionDrawingEntry> findByDrawingNoOrderByCreationDateDesc(String drawingNo);

    // Find by mark number
    List<ErectionDrawingEntry> findByMarkNoOrderByCreationDateDesc(String markNo);

    // Find by session code
    List<ErectionDrawingEntry> findBySessionCodeOrderByCreationDateDesc(String sessionCode);

    // Find by tenant ID with pagination
    Page<ErectionDrawingEntry> findByTenantIdOrderByCreationDateDesc(String tenantId, Pageable pageable);

    // Find by status
    List<ErectionDrawingEntry> findByStatusOrderByCreationDateDesc(String status);

    // Find by order ID
    List<ErectionDrawingEntry> findByOrderIdOrderByCreationDateDesc(Long orderId);

    // Find by RA NO
    List<ErectionDrawingEntry> findByRaNoOrderByCreationDateDesc(String raNo);

    // Find by multiple criteria with pagination
    @Query("SELECT e FROM ErectionDrawingEntry e WHERE " +
           "(:drawingNo IS NULL OR e.drawingNo = :drawingNo) AND " +
           "(:markNo IS NULL OR e.markNo = :markNo) AND " +
           "(:sessionCode IS NULL OR e.sessionCode = :sessionCode) AND " +
           "(:tenantId IS NULL OR e.tenantId = :tenantId) AND " +
           "(:status IS NULL OR e.status = :status) AND " +
           "(:orderId IS NULL OR e.orderId = :orderId) AND " +
           "(:raNo IS NULL OR e.raNo = :raNo)")
    Page<ErectionDrawingEntry> findByMultipleCriteria(
            @Param("drawingNo") String drawingNo,
            @Param("markNo") String markNo,
            @Param("sessionCode") String sessionCode,
            @Param("tenantId") String tenantId,
            @Param("status") String status,
            @Param("orderId") Long orderId,
            @Param("raNo") String raNo,
            Pageable pageable);

    // Find by date range
    List<ErectionDrawingEntry> findByCreationDateBetweenOrderByCreationDateDesc(
            LocalDateTime startDate, LocalDateTime endDate);

    // Find by marked quantity greater than
    List<ErectionDrawingEntry> findByMarkedQtyGreaterThanOrderByMarkedQtyDesc(BigDecimal markedQty);

    // Delete operations
    @Modifying
    @Transactional
    void deleteByDrawingNo(String drawingNo);

    @Modifying
    @Transactional
    void deleteByMarkNo(String markNo);

    @Modifying
    @Transactional
    void deleteByStatus(String status);

    @Modifying
    @Transactional
    void deleteByOrderId(Long orderId);

    // Count operations
    Long countByDrawingNo(String drawingNo);

    Long countByMarkNo(String markNo);

    Long countByStatus(String status);

    Long countByOrderId(Long orderId);

    Long countByRaNo(String raNo);

    // Sum operations
    @Query("SELECT SUM(e.markedQty) FROM ErectionDrawingEntry e WHERE e.drawingNo = :drawingNo")
    BigDecimal sumMarkedQtyByDrawingNo(@Param("drawingNo") String drawingNo);

    @Query("SELECT SUM(e.totalMarkedWgt) FROM ErectionDrawingEntry e WHERE e.drawingNo = :drawingNo")
    BigDecimal sumTotalMarkedWgtByDrawingNo(@Param("drawingNo") String drawingNo);

    // Distinct values
    @Query("SELECT DISTINCT e.drawingNo FROM ErectionDrawingEntry e WHERE e.drawingNo IS NOT NULL ORDER BY e.drawingNo")
    List<String> findDistinctDrawingNumbers();

    @Query("SELECT DISTINCT e.markNo FROM ErectionDrawingEntry e WHERE e.markNo IS NOT NULL ORDER BY e.markNo")
    List<String> findDistinctMarkNumbers();

    @Query("SELECT DISTINCT e.sessionCode FROM ErectionDrawingEntry e WHERE e.sessionCode IS NOT NULL ORDER BY e.sessionCode")
    List<String> findDistinctSessionCodes();

    @Query("SELECT DISTINCT e.status FROM ErectionDrawingEntry e WHERE e.status IS NOT NULL ORDER BY e.status")
    List<String> findDistinctStatuses();

    @Query("SELECT DISTINCT e.orderId FROM ErectionDrawingEntry e WHERE e.orderId IS NOT NULL ORDER BY e.orderId")
    List<Long> findDistinctOrderIds();

    @Query("SELECT DISTINCT e.raNo FROM ErectionDrawingEntry e WHERE e.raNo IS NOT NULL ORDER BY e.raNo")
    List<String> findDistinctRaNos();

    // Existence checks
    boolean existsByDrawingNoAndMarkNo(String drawingNo, String markNo);

    boolean existsByDrawingNoAndMarkNoAndStatus(String drawingNo, String markNo, String status);

    boolean existsByOrderId(Long orderId);

    boolean existsByRaNo(String raNo);

    // Latest entries
    Optional<ErectionDrawingEntry> findTopByDrawingNoOrderByCreationDateDesc(String drawingNo);

    Optional<ErectionDrawingEntry> findTopByMarkNoOrderByCreationDateDesc(String markNo);

    Optional<ErectionDrawingEntry> findTopByStatusOrderByCreationDateDesc(String status);

    Optional<ErectionDrawingEntry> findTopByOrderIdOrderByCreationDateDesc(Long orderId);

    // Find entries by drawing and mark combination
    List<ErectionDrawingEntry> findByDrawingNoAndMarkNoOrderByCreationDateDesc(String drawingNo, String markNo);

    // Find entries by status and date range
    List<ErectionDrawingEntry> findByStatusAndCreationDateBetweenOrderByCreationDateDesc(
            String status, LocalDateTime startDate, LocalDateTime endDate);

    // Custom query to get unique drawing-mark combinations
    @Query("SELECT DISTINCT new ErectionDrawingEntry(e.lineId, e.drawingNo, e.markNo, e.markedQty) " +
           "FROM ErectionDrawingEntry e " +
           "WHERE e.drawingNo IS NOT NULL AND e.markNo IS NOT NULL " +
           "ORDER BY e.drawingNo, e.markNo")
    List<ErectionDrawingEntry> findUniqueDrawingMarkCombinations();

    // Find all entries with specific status
    Page<ErectionDrawingEntry> findByStatusOrderByCreationDateDesc(String status, Pageable pageable);

    // Get max line ID for generating sequential IDs
    @Query("SELECT MAX(e.lineId) FROM ErectionDrawingEntry e")
    Long findMaxLineId();
    
    
    
    //newly added requested fields
    
 // ADD these new methods to your existing ErectionDrawingEntryRepository.java

    /**
     * Get distinct attribute3V (Department) filtered by attribute1V and attribute2V
     */
    @Query("SELECT DISTINCT e.attribute3V FROM ErectionDrawingEntry e WHERE e.attribute1V = :attribute1V AND e.attribute2V = :attribute2V AND e.attribute3V IS NOT NULL AND e.attribute3V != '' ORDER BY e.attribute3V")
    List<String> findDistinctAttribute3VByAttribute1VAndAttribute2V(@Param("attribute1V") String attribute1V, @Param("attribute2V") String attribute2V);

    /**
     * Find entries by work order, building name, and department
     */
    @Query("SELECT e FROM ErectionDrawingEntry e WHERE e.attribute1V = :workOrder AND e.attribute2V = :buildingName AND e.attribute3V = :department ORDER BY e.creationDate DESC")
    List<ErectionDrawingEntry> findByWorkOrderAndBuildingNameAndDepartment(@Param("workOrder") String workOrder, @Param("buildingName") String buildingName, @Param("department") String department);
    
}
