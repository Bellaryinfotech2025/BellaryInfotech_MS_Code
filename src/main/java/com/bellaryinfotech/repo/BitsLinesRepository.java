package com.bellaryinfotech.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.BitsLinesAll;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BitsLinesRepository extends JpaRepository<BitsLinesAll, Long> {
    
    
    List<BitsLinesAll> findBySerNoContainingIgnoreCase(String serNo);
    List<BitsLinesAll> findByServiceCodeContainingIgnoreCase(String serviceCode);
    List<BitsLinesAll> findByServiceDescContainingIgnoreCase(String serviceDesc);
    
    
    List<BitsLinesAll> findByAttribute1VContainingIgnoreCase(String workOrder);
    List<BitsLinesAll> findByAttribute1V(String workOrder);
    
     
    List<BitsLinesAll> findByOrderIdOrderByLineNumber(Long orderId);
    List<BitsLinesAll> findByOrderId(Long orderId);
    
    
    @Query("SELECT COALESCE(MAX(l.lineNumber), 0) + 1 FROM BitsLinesAll l WHERE l.orderId = :orderId")
    BigDecimal getNextLineNumber(@Param("orderId") Long orderId);
    
     
    @Query("SELECT l FROM BitsLinesAll l JOIN BitsHeaderAll h ON l.orderId = h.orderId WHERE h.workOrder = :workOrder ORDER BY l.lineNumber")
    List<BitsLinesAll> findByWorkOrderNumber(@Param("workOrder") String workOrder);
    
    
    @Query("SELECT COUNT(l) FROM BitsLinesAll l WHERE l.orderId = :orderId")
    Long countByOrderId(@Param("orderId") Long orderId);
    
   
    @Query("SELECT COUNT(l) FROM BitsLinesAll l WHERE l.orderId = :orderId AND l.lineNumber = :lineNumber AND l.lineId != :lineId")
    Long countByOrderIdAndLineNumberExcludingId(@Param("orderId") Long orderId, @Param("lineNumber") BigDecimal lineNumber, @Param("lineId") Long lineId);
    
     
    @Query("SELECT DISTINCT l.serNo FROM BitsLinesAll l WHERE l.serNo IS NOT NULL AND l.serNo != '' ORDER BY l.serNo")
    List<String> findDistinctSerialNumbers();
    
    
 
    @Query("SELECT l FROM BitsLinesAll l WHERE l.assignedLevel IS NOT NULL AND l.assignedLevel != ''")
    List<BitsLinesAll> findLinesWithAssignedLevels();
}
