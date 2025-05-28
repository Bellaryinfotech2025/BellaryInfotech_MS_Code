package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.OrderFabricationBilling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderFabricationBillingRepository extends JpaRepository<OrderFabricationBilling, Long> {
    
    // Find all records ordered by ID descending
    List<OrderFabricationBilling> findAllByOrderByIdDesc();
    
    // Find only the latest stored records
    List<OrderFabricationBilling> findByIsLatestStoredTrueOrderByIdDesc();
    
    // Find records by erection mkds
    List<OrderFabricationBilling> findByErectionMkdInOrderByIdDesc(List<String> erectionMkds);
    
    // Clear all latest stored flags
    @Modifying
    @Query("UPDATE OrderFabricationBilling SET isLatestStored = false")
    void clearAllLatestStoredFlags();
}
