package com.bellaryinfotech.repo;
 

 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.MtrModule;

import java.util.List;

@Repository
public interface MtrModuleRepository extends JpaRepository<MtrModule, Long> {
    
    List<MtrModule> findByMarkNo(String markNo);
    
    List<MtrModule> findByWorkOrder(String workOrder);
    
    List<MtrModule> findByWorkOrderAndBuildingName(String workOrder, String buildingName);
    
    @Query("SELECT DISTINCT m.workOrder FROM MtrModule m WHERE m.workOrder IS NOT NULL")
    List<String> findDistinctWorkOrders();
    
    @Query("SELECT DISTINCT m.buildingName FROM MtrModule m WHERE m.workOrder = :workOrder AND m.buildingName IS NOT NULL")
    List<String> findDistinctBuildingNamesByWorkOrder(@Param("workOrder") String workOrder);
    
    void deleteByMarkNo(String markNo);
    
    
}

