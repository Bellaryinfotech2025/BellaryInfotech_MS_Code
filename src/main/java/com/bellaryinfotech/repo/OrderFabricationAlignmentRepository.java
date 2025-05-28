package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.OrderFabricationAlignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderFabricationAlignmentRepository extends JpaRepository<OrderFabricationAlignment, Long> {
    
    // Find all records ordered by ID descending
    List<OrderFabricationAlignment> findAllByOrderByIdDesc();
    
    // Find only the latest stored records
    List<OrderFabricationAlignment> findByIsLatestStoredTrueOrderByIdDesc();
    
    // Find records by erection mkds
    List<OrderFabricationAlignment> findByErectionMkdInOrderByIdDesc(List<String> erectionMkds);
    
    // NEW: Get all distinct erection mkds from alignment table - Updated query
    @Query("SELECT DISTINCT a.erectionMkd FROM OrderFabricationAlignment a WHERE a.erectionMkd IS NOT NULL AND TRIM(a.erectionMkd) != '' ORDER BY a.erectionMkd")
    List<String> findDistinctErectionMkds();
    
    // Alternative query if the above doesn't work
    @Query(value = "SELECT DISTINCT erection_mkd FROM order_fabrication_alignment WHERE erection_mkd IS NOT NULL AND TRIM(erection_mkd) != '' ORDER BY erection_mkd", nativeQuery = true)
    List<String> findDistinctErectionMkdsNative();
    
    // Clear all latest stored flags
    @Modifying
    @Query("UPDATE OrderFabricationAlignment SET isLatestStored = false")
    void clearAllLatestStoredFlags();

	List<OrderFabricationAlignment> findByErectionMkdIn(List<String> erectionMkds);
}
