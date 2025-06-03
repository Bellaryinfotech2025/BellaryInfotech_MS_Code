package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.AlignmentDrawingEntry;
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
public interface AlignmentDrawingEntryRepository extends JpaRepository<AlignmentDrawingEntry, String> {

    // Find by drawing number
    List<AlignmentDrawingEntry> findByDrawingNoOrderByCreationDateDesc(String drawingNo);

    // Find by mark number
    List<AlignmentDrawingEntry> findByMarkNoOrderByCreationDateDesc(String markNo);

    // Find by session code
    List<AlignmentDrawingEntry> findBySessionCodeOrderByCreationDateDesc(String sessionCode);

    // Find by tenant ID with pagination
    Page<AlignmentDrawingEntry> findByTenantIdOrderByCreationDateDesc(String tenantId, Pageable pageable);

    // Find by status
    List<AlignmentDrawingEntry> findByStatusOrderByCreationDateDesc(String status);

    // Find by multiple criteria with pagination
    @Query("SELECT a FROM AlignmentDrawingEntry a WHERE " +
           "(:drawingNo IS NULL OR a.drawingNo = :drawingNo) AND " +
           "(:markNo IS NULL OR a.markNo = :markNo) AND " +
           "(:sessionCode IS NULL OR a.sessionCode = :sessionCode) AND " +
           "(:tenantId IS NULL OR a.tenantId = :tenantId) AND " +
           "(:status IS NULL OR a.status = :status)")
    Page<AlignmentDrawingEntry> findByMultipleCriteria(
            @Param("drawingNo") String drawingNo,
            @Param("markNo") String markNo,
            @Param("sessionCode") String sessionCode,
            @Param("tenantId") String tenantId,
            @Param("status") String status,
            Pageable pageable);

    // Find by date range
    List<AlignmentDrawingEntry> findByCreationDateBetweenOrderByCreationDateDesc(
            LocalDateTime startDate, LocalDateTime endDate);

    // Find by marked quantity greater than
    List<AlignmentDrawingEntry> findByMarkedQtyGreaterThanOrderByMarkedQtyDesc(BigDecimal markedQty);

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
    @Query("SELECT SUM(a.markedQty) FROM AlignmentDrawingEntry a WHERE a.drawingNo = :drawingNo")
    BigDecimal sumMarkedQtyByDrawingNo(@Param("drawingNo") String drawingNo);

    @Query("SELECT SUM(a.totalMarkedWgt) FROM AlignmentDrawingEntry a WHERE a.drawingNo = :drawingNo")
    BigDecimal sumTotalMarkedWgtByDrawingNo(@Param("drawingNo") String drawingNo);

    // Distinct values
    @Query("SELECT DISTINCT a.drawingNo FROM AlignmentDrawingEntry a WHERE a.drawingNo IS NOT NULL ORDER BY a.drawingNo")
    List<String> findDistinctDrawingNumbers();

    @Query("SELECT DISTINCT a.markNo FROM AlignmentDrawingEntry a WHERE a.markNo IS NOT NULL ORDER BY a.markNo")
    List<String> findDistinctMarkNumbers();

    @Query("SELECT DISTINCT a.sessionCode FROM AlignmentDrawingEntry a WHERE a.sessionCode IS NOT NULL ORDER BY a.sessionCode")
    List<String> findDistinctSessionCodes();

    @Query("SELECT DISTINCT a.status FROM AlignmentDrawingEntry a WHERE a.status IS NOT NULL ORDER BY a.status")
    List<String> findDistinctStatuses();

    // Existence checks
    boolean existsByDrawingNoAndMarkNo(String drawingNo, String markNo);
    boolean existsByDrawingNoAndMarkNoAndStatus(String drawingNo, String markNo, String status);

    // Latest entries
    Optional<AlignmentDrawingEntry> findTopByDrawingNoOrderByCreationDateDesc(String drawingNo);
    Optional<AlignmentDrawingEntry> findTopByMarkNoOrderByCreationDateDesc(String markNo);
    Optional<AlignmentDrawingEntry> findTopByStatusOrderByCreationDateDesc(String status);

    // Find entries by drawing and mark combination
    List<AlignmentDrawingEntry> findByDrawingNoAndMarkNoOrderByCreationDateDesc(String drawingNo, String markNo);

    // Find entries by status and date range
    List<AlignmentDrawingEntry> findByStatusAndCreationDateBetweenOrderByCreationDateDesc(
            String status, LocalDateTime startDate, LocalDateTime endDate);

    // Custom query to get unique drawing-mark combinations
    @Query("SELECT DISTINCT new AlignmentDrawingEntry(a.lineId, a.drawingNo, a.markNo, a.markedQty) " +
           "FROM AlignmentDrawingEntry a " +
           "WHERE a.drawingNo IS NOT NULL AND a.markNo IS NOT NULL " +
           "ORDER BY a.drawingNo, a.markNo")
    List<AlignmentDrawingEntry> findUniqueDrawingMarkCombinations();

    // Find all entries with specific status
    Page<AlignmentDrawingEntry> findByStatusOrderByCreationDateDesc(String status, Pageable pageable);
}
