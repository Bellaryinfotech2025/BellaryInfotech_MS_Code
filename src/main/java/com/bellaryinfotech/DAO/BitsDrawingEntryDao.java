package com.bellaryinfotech.DAO;

import com.bellaryinfotech.model.BitsDrawingEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BitsDrawingEntryDao {
 
    BitsDrawingEntry save(BitsDrawingEntry bitsDrawingEntry);

  
    List<BitsDrawingEntry> saveAll(List<BitsDrawingEntry> bitsDrawingEntries);

   
    Optional<BitsDrawingEntry> findById(Long lineId); // Changed from String to Long
 
    Page<BitsDrawingEntry> findAll(Pageable pageable);

   
    List<BitsDrawingEntry> findAll();
 
    List<BitsDrawingEntry> findByDrawingNo(String drawingNo);

  
    List<BitsDrawingEntry> findByMarkNo(String markNo);
 
    List<BitsDrawingEntry> findBySessionCode(String sessionCode);
 
    Page<BitsDrawingEntry> findByTenantId(String tenantId, Pageable pageable);
 
    Page<BitsDrawingEntry> findByMultipleCriteria(String drawingNo, String markNo, 
                                                  String sessionCode, String tenantId, 
                                                  Pageable pageable);
 
    List<BitsDrawingEntry> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate);
 
    List<BitsDrawingEntry> findByMarkedQtyGreaterThan(BigDecimal markedQty);
 
    Long countByDrawingNo(String drawingNo);
 
    BigDecimal sumMarkedQtyByDrawingNo(String drawingNo);
 
    BigDecimal sumTotalMarkedWgtByDrawingNo(String drawingNo);
 
    List<String> findDistinctDrawingNumbers();

     
    List<String> findDistinctMarkNumbers();
 
    List<String> findDistinctSessionCodes();
 
    boolean existsById(Long lineId); // Changed from String to Long
 
    boolean existsByDrawingNoAndMarkNo(String drawingNo, String markNo);

    
    BitsDrawingEntry update(BitsDrawingEntry bitsDrawingEntry);
 
    void deleteById(Long lineId); // Changed from String to Long
 
    void delete(BitsDrawingEntry bitsDrawingEntry);
 
    void deleteByDrawingNo(String drawingNo);
 
    void deleteByMarkNo(String markNo);
 
    long count();

    
    Optional<BitsDrawingEntry> findLatestByDrawingNo(String drawingNo);
 
    Optional<BitsDrawingEntry> findLatestByMarkNo(String markNo);
}
