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
    
    
    List<BitsHeaderAll> findByWorkOrderContainingIgnoreCase(String workOrder);
    List<BitsHeaderAll> findByPlantLocationContainingIgnoreCase(String plantLocation);
    List<BitsHeaderAll> findByDepartmentContainingIgnoreCase(String department);
    List<BitsHeaderAll> findByWorkLocationContainingIgnoreCase(String workLocation);
    List<BitsHeaderAll> findByLdApplicable(Boolean ldApplicable);
    
    
    
    
    Optional<BitsHeaderAll> findByOrderId(Long orderId);
    boolean existsByWorkOrder(String workOrder);
    boolean existsByOrderId(Long orderId);
    
    @Query("SELECT DISTINCT bh.workOrder FROM BitsHeaderAll bh WHERE bh.workOrder IS NOT NULL ORDER BY bh.workOrder")
    List<String> findDistinctWorkOrders();
    
    @Query("SELECT DISTINCT bh.plantLocation FROM BitsHeaderAll bh WHERE bh.plantLocation IS NOT NULL ORDER BY bh.plantLocation")
    List<String> findDistinctPlantLocations();
    
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
    
    
    
 // Find work orders by plant location (client name)
    List<BitsHeaderAll> findByPlantLocation(String plantLocation);
    
    // Find work order by work order number
    Optional<BitsHeaderAll> findByWorkOrder(String workOrder);
    
    // Custom query for better performance
    @Query("SELECT b FROM BitsHeaderAll b WHERE b.plantLocation = :plantLocation ORDER BY b.workOrder ASC")
    List<BitsHeaderAll> findWorkOrdersByPlantLocationSorted(@Param("plantLocation") String plantLocation);
    
    // Fixed query to return only the order_id as Long
    @Query("SELECT b.orderId FROM  BitsHeaderAll b WHERE b.workOrder = :workOrder")
    Long findOrderIdByWorkOrder(@Param("workOrder") String workOrder);
    
    // Alternative native query if the above doesn't work
    @Query(value = "SELECT order_id FROM bits_po_entry_header WHERE work_order = :workOrder LIMIT 1", nativeQuery = true)
    Long findOrderIdByWorkOrderNative(@Param("workOrder") String workOrder);
    
    

}
