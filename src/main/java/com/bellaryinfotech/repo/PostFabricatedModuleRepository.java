package com.bellaryinfotech.repo;
 
import com.bellaryinfotech.model.PostFabricatedModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostFabricatedModuleRepository extends JpaRepository<PostFabricatedModule, Long> {
    
    List<PostFabricatedModule> findByWorkOrder(String workOrder);
    
    List<PostFabricatedModule> findByRaNumber(String raNumber);
    
    List<PostFabricatedModule> findByWorkOrderAndRaNumber(String workOrder, String raNumber);
    
    @Query("SELECT DISTINCT p.workOrder FROM PostFabricatedModule p ORDER BY p.workOrder")
    List<String> findDistinctWorkOrders();
    
    @Query("SELECT DISTINCT p.raNumber FROM PostFabricatedModule p ORDER BY p.raNumber")
    List<String> findDistinctRaNumbers();
    
    @Query("SELECT DISTINCT p.raNumber FROM PostFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.raNumber")
    List<String> findDistinctRaNumbersByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT p FROM PostFabricatedModule p WHERE p.workOrder = :workOrder AND p.raNumber = :raNumber")
    List<PostFabricatedModule> findByWorkOrderAndRaNumberDetails(@Param("workOrder") String workOrder, @Param("raNumber") String raNumber);
    
    void deleteByWorkOrderAndRaNumber(String workOrder, String raNumber);
}
