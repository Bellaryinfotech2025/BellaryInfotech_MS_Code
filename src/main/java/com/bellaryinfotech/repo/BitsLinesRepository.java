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
    
    // Existing search methods
    List<BitsLinesAll> findBySerNoContainingIgnoreCase(String serNo);
    List<BitsLinesAll> findByServiceCodeContainingIgnoreCase(String serviceCode);
    List<BitsLinesAll> findByServiceDescContainingIgnoreCase(String serviceDesc);
    
    // Legacy methods for backward compatibility
    List<BitsLinesAll> findByAttribute1VContainingIgnoreCase(String workOrder);
    List<BitsLinesAll> findByAttribute1V(String workOrder);
    
    // Enhanced methods using proper foreign key relationship
    List<BitsLinesAll> findByOrderIdOrderByLineNumber(Long orderId);
    List<BitsLinesAll> findByOrderId(Long orderId);
    
    // Method to get the next line number for a work order
    @Query("SELECT COALESCE(MAX(l.lineNumber), 0) + 1 FROM BitsLinesAll l WHERE l.orderId = :orderId")
    BigDecimal getNextLineNumber(@Param("orderId") Long orderId);
    
    // Method to get lines by work order number (joining with header table)
    @Query("SELECT l FROM BitsLinesAll l JOIN BitsHeaderAll h ON l.orderId = h.orderId WHERE h.workOrder = :workOrder ORDER BY l.lineNumber")
    List<BitsLinesAll> findByWorkOrderNumber(@Param("workOrder") String workOrder);
    
    // Method to count lines for a specific work order
    @Query("SELECT COUNT(l) FROM BitsLinesAll l WHERE l.orderId = :orderId")
    Long countByOrderId(@Param("orderId") Long orderId);
    
    // Method to validate line number uniqueness within a work order
    @Query("SELECT COUNT(l) FROM BitsLinesAll l WHERE l.orderId = :orderId AND l.lineNumber = :lineNumber AND l.lineId != :lineId")
    Long countByOrderIdAndLineNumberExcludingId(@Param("orderId") Long orderId, @Param("lineNumber") BigDecimal lineNumber, @Param("lineId") Long lineId);
    
    // NEW: Method to get distinct serial numbers
    @Query("SELECT DISTINCT l.serNo FROM BitsLinesAll l WHERE l.serNo IS NOT NULL AND l.serNo != '' ORDER BY l.serNo")
    List<String> findDistinctSerialNumbers();
}
