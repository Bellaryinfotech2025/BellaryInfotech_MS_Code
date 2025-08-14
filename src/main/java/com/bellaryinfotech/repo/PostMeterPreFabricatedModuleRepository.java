package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.PostMeterPreFabricatedModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMeterPreFabricatedModuleRepository extends JpaRepository<PostMeterPreFabricatedModule, Long> {
    
    List<PostMeterPreFabricatedModule> findByWorkOrder(String workOrder);
    
    List<PostMeterPreFabricatedModule> findByWorkOrderAndRaNo(String workOrder, String raNo);
    
    List<PostMeterPreFabricatedModule> findByRaNo(String raNo);
    
    List<PostMeterPreFabricatedModule> findByMarkNo(String markNo);
    
    @Query("SELECT DISTINCT p.workOrder FROM PostMeterPreFabricatedModule p ORDER BY p.workOrder")
    List<String> findDistinctWorkOrders();
    
    @Query("SELECT DISTINCT p.raNo FROM PostMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.raNo")
    List<String> findDistinctRaNosByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT p.serviceDescription FROM PostMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.serviceDescription")
    List<String> findDistinctServiceDescriptionsByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT p.vehicleNumber FROM PostMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.vehicleNumber")
    List<String> findDistinctVehicleNumbersByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT p.loadNumber FROM PostMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.loadNumber")
    List<String> findDistinctLoadNumbersByWorkOrder(@Param("workOrder") String workOrder);
    
    @Query("SELECT DISTINCT p.plotNumber FROM PostMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.plotNumber")
    List<String> findDistinctPlotNumbersByWorkOrder(@Param("workOrder") String workOrder);
    
    void deleteByWorkOrder(String workOrder);
    
    void deleteByWorkOrderAndRaNo(String workOrder, String raNo);
    
    
    
    

    

    @Query("SELECT DISTINCT p.raNo FROM PostMeterPreFabricatedModule p WHERE p.workOrder = :workOrder AND p.serviceDescription = :serviceDescription")
    String findRaNoByWorkOrderAndServiceDescription(String workOrder, String serviceDescription);
}
