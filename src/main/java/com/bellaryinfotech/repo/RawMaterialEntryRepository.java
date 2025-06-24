package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.RawMaterialEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RawMaterialEntryRepository extends JpaRepository<RawMaterialEntry, Long> {
    
    List<RawMaterialEntry> findByWorkOrder(String workOrder);
    
    // NEW: Find by orderId
    List<RawMaterialEntry> findByOrderId(Long orderId);
    
    List<RawMaterialEntry> findBySection(String section);
    
    List<RawMaterialEntry> findByCreatedBy(String createdBy);
    
    List<RawMaterialEntry> findByTenantId(Integer tenantId);
    
    @Query("SELECT r FROM RawMaterialEntry r WHERE r.totalWeight > :minWeight")
    List<RawMaterialEntry> findByTotalWeightGreaterThan(@Param("minWeight") java.math.BigDecimal minWeight);
    
    @Query("SELECT DISTINCT r.workOrder FROM RawMaterialEntry r WHERE r.workOrder IS NOT NULL")
    List<String> findDistinctWorkOrders();
    
    @Query("SELECT DISTINCT r.section FROM RawMaterialEntry r WHERE r.section IS NOT NULL")
    List<String> findDistinctSections();
    
    @Query("SELECT DISTINCT r.uom FROM RawMaterialEntry r WHERE r.uom IS NOT NULL")
    List<String> findDistinctUoms();
    
    @Query("SELECT r FROM RawMaterialEntry r ORDER BY r.createdDate DESC")
    List<RawMaterialEntry> findAllOrderByCreatedDateDesc();
    
    @Query("SELECT r FROM RawMaterialEntry r WHERE r.workOrder IN :workOrders")
    List<RawMaterialEntry> findByWorkOrderIn(@Param("workOrders") List<String> workOrders);
}
