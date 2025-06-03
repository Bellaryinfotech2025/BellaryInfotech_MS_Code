package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.BillingDrawingEntry;
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
public interface BillingDrawingEntryRepository extends JpaRepository<BillingDrawingEntry, String> {

    // Find by drawing number
    List<BillingDrawingEntry> findByDrawingNoOrderByCreationDateDesc(String drawingNo);

    // Find by mark number
    List<BillingDrawingEntry> findByMarkNoOrderByCreationDateDesc(String markNo);

    // Find by session code
    List<BillingDrawingEntry> findBySessionCodeOrderByCreationDateDesc(String sessionCode);

    // Find by tenant ID with pagination
    Page<BillingDrawingEntry> findByTenantIdOrderByCreationDateDesc(String tenantId, Pageable pageable);

    // Find by status
    List<BillingDrawingEntry> findByStatusOrderByCreationDateDesc(String status);

    // Find by multiple criteria with pagination
    @Query("SELECT b FROM BillingDrawingEntry b WHERE " +
           "(:drawingNo IS NULL OR b.drawingNo = :drawingNo) AND " +
           "(:markNo IS NULL OR b.markNo = :markNo) AND " +
           "(:sessionCode IS NULL OR b.sessionCode = :sessionCode) AND " +
           "(:tenantId IS NULL OR b.tenantId = :tenantId) AND " +
           "(:status IS NULL OR b.status = :status)")
    Page<BillingDrawingEntry> findByMultipleCriteria(
            @Param("drawingNo") String drawingNo,
            @Param("markNo") String markNo,
            @Param("sessionCode") String sessionCode,
            @Param("tenantId") String tenantId,
            @Param("status") String status,
            Pageable pageable);

    // Find by date range
    List<BillingDrawingEntry> findByCreationDateBetweenOrderByCreationDateDesc(
            LocalDateTime startDate, LocalDateTime endDate);

    // Find by marked quantity greater than
    List<BillingDrawingEntry> findByMarkedQtyGreaterThanOrderByMarkedQtyDesc(BigDecimal markedQty);

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

    // Count operations
    Long countByDrawingNo(String drawingNo);
    Long countByMarkNo(String markNo);
    Long countByStatus(String status);

    // Sum operations
    @Query("SELECT SUM(b.markedQty) FROM BillingDrawingEntry b WHERE b.drawingNo = :drawingNo")
    BigDecimal sumMarkedQtyByDrawingNo(@Param("drawingNo") String drawingNo);

    @Query("SELECT SUM(b.totalMarkedWgt) FROM BillingDrawingEntry b WHERE b.drawingNo = :drawingNo")
    BigDecimal sumTotalMarkedWgtByDrawingNo(@Param("drawingNo") String drawingNo);

    // Distinct values
    @Query("SELECT DISTINCT b.drawingNo FROM BillingDrawingEntry b WHERE b.drawingNo IS NOT NULL ORDER BY b.drawingNo")
    List<String> findDistinctDrawingNumbers();

    @Query("SELECT DISTINCT b.markNo FROM BillingDrawingEntry b WHERE b.markNo IS NOT NULL ORDER BY b.markNo")
    List<String> findDistinctMarkNumbers();

    @Query("SELECT DISTINCT b.sessionCode FROM BillingDrawingEntry b WHERE b.sessionCode IS NOT NULL ORDER BY b.sessionCode")
    List<String> findDistinctSessionCodes();

    @Query("SELECT DISTINCT b.status FROM BillingDrawingEntry b WHERE b.status IS NOT NULL ORDER BY b.status")
    List<String> findDistinctStatuses();

    // Existence checks
    boolean existsByDrawingNoAndMarkNo(String drawingNo, String markNo);
    boolean existsByDrawingNoAndMarkNoAndStatus(String drawingNo, String markNo, String status);

    // Latest entries
    Optional<BillingDrawingEntry> findTopByDrawingNoOrderByCreationDateDesc(String drawingNo);
    Optional<BillingDrawingEntry> findTopByMarkNoOrderByCreationDateDesc(String markNo);
    Optional<BillingDrawingEntry> findTopByStatusOrderByCreationDateDesc(String status);

    // Find entries by drawing and mark combination
    List<BillingDrawingEntry> findByDrawingNoAndMarkNoOrderByCreationDateDesc(String drawingNo, String markNo);

    // Find entries by status and date range
    List<BillingDrawingEntry> findByStatusAndCreationDateBetweenOrderByCreationDateDesc(
            String status, LocalDateTime startDate, LocalDateTime endDate);

    // Custom query to get unique drawing-mark combinations
    @Query("SELECT DISTINCT new BillingDrawingEntry(b.lineId, b.drawingNo, b.markNo, b.markedQty) " +
           "FROM BillingDrawingEntry b " +
           "WHERE b.drawingNo IS NOT NULL AND b.markNo IS NOT NULL " +
           "ORDER BY b.drawingNo, b.markNo")
    List<BillingDrawingEntry> findUniqueDrawingMarkCombinations();

    // Find all entries with specific status
    Page<BillingDrawingEntry> findByStatusOrderByCreationDateDesc(String status, Pageable pageable);
}
