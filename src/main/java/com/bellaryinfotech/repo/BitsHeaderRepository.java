package com.bellaryinfotech.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.BitsHeaderAll;

import java.util.List;
import java.util.Optional;

@Repository
public interface BitsHeaderRepository extends JpaRepository<BitsHeaderAll, Long> {
    
    // YOUR EXISTING METHODS - KEEPING EXACTLY AS THEY ARE
    List<BitsHeaderAll> findByWorkOrderContainingIgnoreCase(String workOrder);
    List<BitsHeaderAll> findByPlantLocationContainingIgnoreCase(String plantLocation);
    List<BitsHeaderAll> findByDepartmentContainingIgnoreCase(String department);
    List<BitsHeaderAll> findByWorkLocationContainingIgnoreCase(String workLocation);
    List<BitsHeaderAll> findByLdApplicable(Boolean ldApplicable);
    Optional<BitsHeaderAll> findByWorkOrder(String workOrderNo);
    
    // NEW METHODS FOR ORDER_ID MAPPING - ADDED BELOW YOUR EXISTING CODE
    Optional<BitsHeaderAll> findByOrderId(Long orderId);
    boolean existsByWorkOrder(String workOrder);
    boolean existsByOrderId(Long orderId);
    
    @Query("SELECT DISTINCT bh.workOrder FROM BitsHeaderAll bh WHERE bh.workOrder IS NOT NULL ORDER BY bh.workOrder")
    List<String> findDistinctWorkOrders();
    
    @Query("SELECT bh FROM BitsHeaderAll bh WHERE " +
           "(:workOrder IS NULL OR bh.workOrder LIKE %:workOrder%) AND " +
           "(:plantLocation IS NULL OR bh.plantLocation LIKE %:plantLocation%) AND " +
           "(:department IS NULL OR bh.department LIKE %:department%) AND " +
           "(:workLocation IS NULL OR bh.workLocation LIKE %:workLocation%)")
    List<BitsHeaderAll> findByMultipleCriteria(
            @Param("workOrder") String workOrder,
            @Param("plantLocation") String plantLocation,
            @Param("department") String department,
            @Param("workLocation") String workLocation);
}
