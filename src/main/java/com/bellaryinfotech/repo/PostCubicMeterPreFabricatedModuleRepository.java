package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.PostCubicMeterPreFabricatedModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCubicMeterPreFabricatedModuleRepository extends JpaRepository<PostCubicMeterPreFabricatedModule, Long> {

    List<PostCubicMeterPreFabricatedModule> findByWorkOrder(String workOrder);

    List<PostCubicMeterPreFabricatedModule> findByWorkOrderAndRaNo(String workOrder, String raNo);

    List<PostCubicMeterPreFabricatedModule> findByWorkOrderAndServiceDescription(@Param("workOrder") String workOrder, @Param("serviceDescription") String serviceDescription);

    List<PostCubicMeterPreFabricatedModule> findByRaNo(String raNo);

    List<PostCubicMeterPreFabricatedModule> findByMarkNo(String markNo);

    @Query("SELECT DISTINCT p.workOrder FROM PostCubicMeterPreFabricatedModule p ORDER BY p.workOrder")
    List<String> findDistinctWorkOrders();

    @Query("SELECT DISTINCT p.raNo FROM PostCubicMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.raNo")
    List<String> findDistinctRaNosByWorkOrder(@Param("workOrder") String workOrder);

    @Query("SELECT DISTINCT p.serviceDescription FROM PostCubicMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.serviceDescription")
    List<String> findDistinctServiceDescriptionsByWorkOrder(@Param("workOrder") String workOrder);

    @Query("SELECT DISTINCT p.vehicleNumber FROM PostCubicMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.vehicleNumber")
    List<String> findDistinctVehicleNumbersByWorkOrder(@Param("workOrder") String workOrder);

    @Query("SELECT DISTINCT p.loadNumber FROM PostCubicMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.loadNumber")
    List<String> findDistinctLoadNumbersByWorkOrder(@Param("workOrder") String workOrder);

    @Query("SELECT DISTINCT p.plotNumber FROM PostCubicMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.plotNumber")
    List<String> findDistinctPlotNumbersByWorkOrder(@Param("workOrder") String workOrder);

    void deleteByWorkOrder(String workOrder);

    void deleteByWorkOrderAndRaNo(String workOrder, String raNo);

    @Query("SELECT p FROM PostCubicMeterPreFabricatedModule p WHERE p.workOrder = :workOrder AND p.serviceDescription = :serviceDescription AND p.raNo = :raNo")
    List<PostCubicMeterPreFabricatedModule> findByWorkOrderAndServiceDescriptionAndRaNo(
            @Param("workOrder") String workOrder,
            @Param("serviceDescription") String serviceDescription,
            @Param("raNo") String raNo);
    
    
    
    
    
    
    
    
    
    
 // new query to get the cubic pre fabriacted 
    @Query("SELECT DISTINCT p.clientName FROM PostCubicMeterPreFabricatedModule p ORDER BY p.clientName")
    List<String> findDistinctClientNames();

    @Query("SELECT DISTINCT p.workOrder FROM PostCubicMeterPreFabricatedModule p WHERE p.clientName = :clientName ORDER BY p.workOrder")
    List<String> findDistinctWorkOrdersByClientName(@Param("clientName") String clientName);

    @Query("SELECT DISTINCT p.serviceDescription FROM PostCubicMeterPreFabricatedModule p WHERE p.clientName = :clientName AND p.workOrder = :workOrder ORDER BY p.serviceDescription")
    List<String> findDistinctServiceDescriptionsByClientAndWorkOrder(@Param("clientName") String clientName, @Param("workOrder") String workOrder);

    @Query("SELECT DISTINCT p.raNo FROM PostCubicMeterPreFabricatedModule p WHERE p.clientName = :clientName AND p.workOrder = :workOrder AND p.serviceDescription = :serviceDescription ORDER BY p.raNo")
    List<String> findDistinctRaNumbersByClientWorkOrderAndService(@Param("clientName") String clientName, @Param("workOrder") String workOrder, @Param("serviceDescription") String serviceDescription);

    // NEW search method
    @Query("SELECT p FROM PostCubicMeterPreFabricatedModule p WHERE p.clientName = :clientName AND p.workOrder = :workOrder AND p.serviceDescription = :serviceDescription AND p.raNo = :raNo")
    List<PostCubicMeterPreFabricatedModule> findByClientNameAndWorkOrderAndServiceDescriptionAndRaNo(
            @Param("clientName") String clientName,
            @Param("workOrder") String workOrder,
            @Param("serviceDescription") String serviceDescription,
            @Param("raNo") String raNo);
}