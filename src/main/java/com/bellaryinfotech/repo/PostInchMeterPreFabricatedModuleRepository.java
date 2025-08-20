package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.PostInchMeterPreFabricatedModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostInchMeterPreFabricatedModuleRepository extends JpaRepository<PostInchMeterPreFabricatedModule, Long> {
    
    List<PostInchMeterPreFabricatedModule> findByWorkOrder(String workOrder);
    
    List<PostInchMeterPreFabricatedModule> findByWorkOrderAndRaNo(String workOrder, String raNo);
    
    List<PostInchMeterPreFabricatedModule> findByWorkOrderAndServiceDescription(@Param("workOrder") String workOrder, @Param("serviceDescription") String serviceDescription);
    
    List<PostInchMeterPreFabricatedModule> findByRaNo(String raNo);
    
    List<PostInchMeterPreFabricatedModule> findByMarkNo(String markNo);
    
    @Query("SELECT DISTINCT p.workOrder FROM PostInchMeterPreFabricatedModule p ORDER BY p.workOrder")
    List<String> findDistinctWorkOrders();
    
    @Query("SELECT DISTINCT p.raNo FROM PostInchMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.raNo")
    List<String> findDistinctRaNosByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT p.serviceDescription FROM PostInchMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.serviceDescription")
    List<String> findDistinctServiceDescriptionsByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT p.vehicleNumber FROM PostInchMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.vehicleNumber")
    List<String> findDistinctVehicleNumbersByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT p.loadNumber FROM PostInchMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.loadNumber")
    List<String> findDistinctLoadNumbersByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT p.plotNumber FROM PostInchMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.plotNumber")
    List<String> findDistinctPlotNumbersByWorkOrder(@Param("workOrder") String workOrder);
    
    void deleteByWorkOrder(String workOrder);
    
    void deleteByWorkOrderAndRaNo(String workOrder, String raNo);
    
    // NEW: Query for searching by workOrder, serviceDescription, and raNo
    @Query("SELECT p FROM PostInchMeterPreFabricatedModule p WHERE p.workOrder = :workOrder AND p.serviceDescription = :serviceDescription AND p.raNo = :raNo")
    List<PostInchMeterPreFabricatedModule> findByWorkOrderAndServiceDescriptionAndRaNo(
            @Param("workOrder") String workOrder, 
            @Param("serviceDescription") String serviceDescription, 
            @Param("raNo") String raNo);
    
    
    
    
    
    
    
    
    
    
    
    @Query("SELECT DISTINCT p.clientName FROM PostInchMeterPreFabricatedModule p WHERE p.clientName IS NOT NULL ORDER BY p.clientName")
    List<String> findDistinctClientNames();

    @Query("SELECT DISTINCT p.workOrder FROM PostInchMeterPreFabricatedModule p WHERE p.clientName = :clientName AND p.workOrder IS NOT NULL ORDER BY p.workOrder")
    List<String> findDistinctWorkOrdersByClientName(@Param("clientName") String clientName);

    @Query("SELECT DISTINCT p.serviceDescription FROM PostInchMeterPreFabricatedModule p WHERE p.clientName = :clientName AND p.workOrder = :workOrder AND p.serviceDescription IS NOT NULL ORDER BY p.serviceDescription")
    List<String> findDistinctServiceDescriptionsByClientAndWorkOrder(@Param("clientName") String clientName, @Param("workOrder") String workOrder);

    @Query("SELECT DISTINCT p.raNo FROM PostInchMeterPreFabricatedModule p WHERE p.clientName = :clientName AND p.workOrder = :workOrder AND p.serviceDescription = :serviceDescription AND p.raNo IS NOT NULL ORDER BY p.raNo")
    List<String> findDistinctRaNumbersByAllCriteria(@Param("clientName") String clientName, @Param("workOrder") String workOrder, @Param("serviceDescription") String serviceDescription);

    @Query("SELECT p FROM PostInchMeterPreFabricatedModule p WHERE p.clientName = :clientName AND p.workOrder = :workOrder AND p.serviceDescription = :serviceDescription AND p.raNo = :raNumber ORDER BY p.id")
    List<PostInchMeterPreFabricatedModule> findByAllCriteria(@Param("clientName") String clientName, @Param("workOrder") String workOrder, @Param("serviceDescription") String serviceDescription, @Param("raNumber") String raNumber);
}