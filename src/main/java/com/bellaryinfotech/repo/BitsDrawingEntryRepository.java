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

     
    List<BitsDrawingEntry> findByDrawingNoOrderByCreationDateDesc(String drawingNo);

     
    List<BitsDrawingEntry> findByMarkNoOrderByCreationDateDesc(String markNo);

    
    List<BitsDrawingEntry> findByDrawingNoAndMarkNoOrderByCreationDateDesc(String drawingNo, String markNo);
 
    List<BitsDrawingEntry> findBySessionCodeOrderByCreationDateDesc(String sessionCode);

     
    Page<BitsDrawingEntry> findByTenantIdOrderByCreationDateDesc(String tenantId, Pageable pageable);
 
    List<BitsDrawingEntry> findByOrderIdOrderByCreationDateDesc(Long orderId);

     
    List<BitsDrawingEntry> findByCreatedByOrderByCreationDateDesc(String createdBy);
 
    List<BitsDrawingEntry> findByCreationDateBetweenOrderByCreationDateDesc(
            LocalDateTime startDate, LocalDateTime endDate);

     
    List<BitsDrawingEntry> findByMarkedQtyGreaterThanOrderByMarkedQtyDesc(BigDecimal markedQty);

    
    List<BitsDrawingEntry> findByTotalMarkedWgtGreaterThanOrderByTotalMarkedWgtDesc(BigDecimal totalMarkedWgt);
 
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

     
    @Query("SELECT COUNT(bde) FROM BitsDrawingEntry bde WHERE bde.drawingNo = :drawingNo")
    Long countByDrawingNo(@Param("drawingNo") String drawingNo);

     
    @Query("SELECT COUNT(bde) FROM BitsDrawingEntry bde WHERE bde.orderId = :orderId")
    Long countByOrderId(@Param("orderId") Long orderId);

     
    @Query("SELECT SUM(bde.markedQty) FROM BitsDrawingEntry bde WHERE bde.drawingNo = :drawingNo")
    BigDecimal sumMarkedQtyByDrawingNo(@Param("drawingNo") String drawingNo);

     
    @Query("SELECT SUM(bde.totalMarkedWgt) FROM BitsDrawingEntry bde WHERE bde.drawingNo = :drawingNo")
    BigDecimal sumTotalMarkedWgtByDrawingNo(@Param("drawingNo") String drawingNo);

     
    @Query("SELECT SUM(bde.markedQty) FROM BitsDrawingEntry bde WHERE bde.orderId = :orderId")
    BigDecimal sumMarkedQtyByOrderId(@Param("orderId") Long orderId);

    
    @Query("SELECT DISTINCT bde.drawingNo FROM BitsDrawingEntry bde WHERE bde.drawingNo IS NOT NULL ORDER BY bde.drawingNo")
    List<String> findDistinctDrawingNumbers();

     
    @Query("SELECT DISTINCT bde.markNo FROM BitsDrawingEntry bde WHERE bde.markNo IS NOT NULL ORDER BY bde.markNo")
    List<String> findDistinctMarkNumbers();

     
    @Query("SELECT DISTINCT bde.sessionCode FROM BitsDrawingEntry bde WHERE bde.sessionCode IS NOT NULL ORDER BY bde.sessionCode")
    List<String> findDistinctSessionCodes();
 
    boolean existsByDrawingNoAndMarkNo(String drawingNo, String markNo);

     
    boolean existsByOrderId(Long orderId);

     
    void deleteByDrawingNo(String drawingNo);
 
    void deleteByMarkNo(String markNo);
 
    void deleteByOrderId(Long orderId);

    
    @Query("SELECT bde FROM BitsDrawingEntry bde WHERE bde.sessionCode IS NULL OR bde.sessionCode = '' ORDER BY bde.creationDate DESC")
    List<BitsDrawingEntry> findEntriesWithoutSessionCode();

    
    Optional<BitsDrawingEntry> findTopByDrawingNoOrderByCreationDateDesc(String drawingNo);

    
    Optional<BitsDrawingEntry> findTopByMarkNoOrderByCreationDateDesc(String markNo);

    
    Optional<BitsDrawingEntry> findTopByOrderIdOrderByCreationDateDesc(Long orderId);
     
    @Query("SELECT DISTINCT bde.raNo FROM BitsDrawingEntry bde WHERE bde.raNo IS NOT NULL AND bde.raNo != '' ORDER BY bde.raNo")
    List<String> findDistinctRaNumbers();
 
    //newly functionaity from requested
  

    
    @Query("SELECT DISTINCT bde.attribute1V FROM BitsDrawingEntry bde WHERE bde.attribute1V IS NOT NULL AND bde.attribute1V != '' ORDER BY bde.attribute1V")
    List<String> findDistinctAttribute1V();

     
    @Query("SELECT DISTINCT bde.attribute2V FROM BitsDrawingEntry bde WHERE bde.attribute1V = :attribute1V AND bde.attribute2V IS NOT NULL AND bde.attribute2V != '' ORDER BY bde.attribute2V")
    List<String> findDistinctAttribute2VByAttribute1V(@Param("attribute1V") String attribute1V);
 
    @Query("SELECT DISTINCT bde.drawingNo FROM BitsDrawingEntry bde WHERE bde.attribute1V = :attribute1V AND bde.attribute2V = :attribute2V AND bde.drawingNo IS NOT NULL AND bde.drawingNo != '' ORDER BY bde.drawingNo")
    List<String> findDistinctDrawingNoByAttributes(@Param("attribute1V") String attribute1V, @Param("attribute2V") String attribute2V);
 
    @Query("SELECT DISTINCT bde.markNo FROM BitsDrawingEntry bde WHERE bde.attribute1V = :attribute1V AND bde.attribute2V = :attribute2V AND bde.markNo IS NOT NULL AND bde.markNo != '' ORDER BY bde.markNo")
    List<String> findDistinctMarkNoByAttributes(@Param("attribute1V") String attribute1V, @Param("attribute2V") String attribute2V);
    
  
}
