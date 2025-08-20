package com.bellaryinfotech.repo;
 

 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.WorkOrderOutEntry;

import java.util.List;

@Repository
public interface WorkOrderOutEntryRepository extends JpaRepository<WorkOrderOutEntry, Long> {
    
     
    List<WorkOrderOutEntry> findByClientNameContainingIgnoreCase(String clientName);
    
   
    List<WorkOrderOutEntry> findByReferenceWorkOrderContainingIgnoreCase(String referenceWorkOrder);
    
     
    List<WorkOrderOutEntry> findByWorkLocationContainingIgnoreCase(String workLocation);
     
    List<WorkOrderOutEntry> findBySubAgencyNameContainingIgnoreCase(String subAgencyName);
     
    List<WorkOrderOutEntry> findByOrderId(Long orderId);
    
     
    @Query("SELECT DISTINCT w.referenceWorkOrder, w.clientName, w.workLocation, w.completionDate, w.ldApplicable, w.totalAmount, w.orderId FROM WorkOrderOutEntry w ORDER BY w.id DESC")
    List<Object[]> findDistinctWorkOrderSummary();
    
     
    void deleteByReferenceWorkOrder(String referenceWorkOrder);
     
    List<WorkOrderOutEntry> findByReferenceWorkOrder(String referenceWorkOrder);
     
    @Query("SELECT w.referenceWorkOrder, w.clientName, w.workLocation, w.completionDate, w.ldApplicable, MAX(w.totalAmount) as totalAmount, w.orderId, MAX(w.createdDate) as createdDate " +
           "FROM WorkOrderOutEntry w " +
           "GROUP BY w.referenceWorkOrder, w.clientName, w.workLocation, w.completionDate, w.ldApplicable, w.orderId " +
           "ORDER BY MAX(w.id) DESC")
    List<Object[]> findUniqueWorkOrdersWithTotals();
}
