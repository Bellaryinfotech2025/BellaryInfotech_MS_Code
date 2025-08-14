package com.bellaryinfotech.repo;
 
 

import com.bellaryinfotech.model.AlignmentInchMeterPreFabricatedProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlignmentInchMeterPreFabricatedProductionRepository
        extends JpaRepository<AlignmentInchMeterPreFabricatedProduction, Long> {

    List<AlignmentInchMeterPreFabricatedProduction> findByWorkOrder(String workOrder);

    List<AlignmentInchMeterPreFabricatedProduction> findByRaNo(String raNo);

    List<AlignmentInchMeterPreFabricatedProduction> findByVehicleNumber(String vehicleNumber);

    @Query("SELECT DISTINCT p.workOrder FROM AlignmentInchMeterPreFabricatedProduction p")
    List<String> findDistinctWorkOrders();

    @Query("SELECT DISTINCT p.vehicleNumber FROM AlignmentInchMeterPreFabricatedProduction p WHERE p.workOrder = :workOrder")
    List<String> findDistinctVehicleNumbersByWorkOrder(@Param("workOrder") String workOrder);

    @Query("SELECT DISTINCT p.loadNumber FROM AlignmentInchMeterPreFabricatedProduction p WHERE p.workOrder = :workOrder")
    List<String> findDistinctLoadNumbersByWorkOrder(@Param("workOrder") String workOrder);

    @Query("SELECT DISTINCT p.plotNumber FROM AlignmentInchMeterPreFabricatedProduction p WHERE p.workOrder = :workOrder")
    List<String> findDistinctPlotNumbersByWorkOrder(@Param("workOrder") String workOrder);

    @Query("SELECT DISTINCT p.raNo FROM AlignmentInchMeterPreFabricatedProduction p WHERE p.workOrder = :workOrder")
    List<String> findDistinctRaNosByWorkOrder(@Param("workOrder") String workOrder);
}