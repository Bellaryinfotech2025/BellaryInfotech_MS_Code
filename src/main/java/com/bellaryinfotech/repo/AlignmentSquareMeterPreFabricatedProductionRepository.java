package com.bellaryinfotech.repo;
 
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.AlignmentSquareMeterPreFabricatedProduction;

import java.util.List;

@Repository
public interface AlignmentSquareMeterPreFabricatedProductionRepository extends JpaRepository<AlignmentSquareMeterPreFabricatedProduction, Long> {

 @Query("SELECT DISTINCT p.workOrder FROM AlignmentSquareMeterPreFabricatedProduction p WHERE p.workOrder IS NOT NULL")
 List<String> findDistinctWorkOrders();

 List<AlignmentSquareMeterPreFabricatedProduction> findByWorkOrder(String workOrder);

 List<AlignmentSquareMeterPreFabricatedProduction> findByWorkOrderAndServiceDescription(String workOrder, String serviceDescription);

 @Query("SELECT p FROM AlignmentSquareMeterPreFabricatedProduction p WHERE p.workOrder = :workOrder " +
        "AND (:vehicleNumber IS NULL OR p.vehicleNumber = :vehicleNumber) " +
        "AND (:loadNumber IS NULL OR p.loadNumber = :loadNumber) " +
        "AND (:plotNumber IS NULL OR p.plotNumber = :plotNumber)")
 List<AlignmentSquareMeterPreFabricatedProduction> findByWorkOrderAndFilters(
         @Param("workOrder") String workOrder,
         @Param("vehicleNumber") String vehicleNumber,
         @Param("loadNumber") String loadNumber,
         @Param("plotNumber") String plotNumber);
}