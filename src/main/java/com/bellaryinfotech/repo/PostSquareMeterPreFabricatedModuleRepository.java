package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.PostSquareMeterPreFabricatedModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostSquareMeterPreFabricatedModuleRepository extends JpaRepository<PostSquareMeterPreFabricatedModule, Long> {

    List<PostSquareMeterPreFabricatedModule> findByWorkOrder(String workOrder);

    List<PostSquareMeterPreFabricatedModule> findByWorkOrderAndRaNo(String workOrder, String raNo);

    List<PostSquareMeterPreFabricatedModule> findByWorkOrderAndServiceDescription(@Param("workOrder") String workOrder, @Param("serviceDescription") String serviceDescription);

    List<PostSquareMeterPreFabricatedModule> findByRaNo(String raNo);

    List<PostSquareMeterPreFabricatedModule> findByMarkNo(String markNo);

    @Query("SELECT DISTINCT p.workOrder FROM PostSquareMeterPreFabricatedModule p ORDER BY p.workOrder")
    List<String> findDistinctWorkOrders();

    @Query("SELECT DISTINCT p.raNo FROM PostSquareMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.raNo")
    List<String> findDistinctRaNosByWorkOrder(@Param("workOrder") String workOrder);

    @Query("SELECT DISTINCT p.serviceDescription FROM PostSquareMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.serviceDescription")
    List<String> findDistinctServiceDescriptionsByWorkOrder(@Param("workOrder") String workOrder);

    @Query("SELECT DISTINCT p.vehicleNumber FROM PostSquareMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.vehicleNumber")
    List<String> findDistinctVehicleNumbersByWorkOrder(@Param("workOrder") String workOrder);

    @Query("SELECT DISTINCT p.loadNumber FROM PostSquareMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.loadNumber")
    List<String> findDistinctLoadNumbersByWorkOrder(@Param("workOrder") String workOrder);

    @Query("SELECT DISTINCT p.plotNumber FROM PostSquareMeterPreFabricatedModule p WHERE p.workOrder = :workOrder ORDER BY p.plotNumber")
    List<String> findDistinctPlotNumbersByWorkOrder(@Param("workOrder") String workOrder);

    void deleteByWorkOrder(String workOrder);

    void deleteByWorkOrderAndRaNo(String workOrder, String raNo);

    @Query("SELECT p FROM PostSquareMeterPreFabricatedModule p WHERE p.workOrder = :workOrder AND p.serviceDescription = :serviceDescription AND p.raNo = :raNo")
    List<PostSquareMeterPreFabricatedModule> findByWorkOrderAndServiceDescriptionAndRaNo(
            @Param("workOrder") String workOrder,
            @Param("serviceDescription") String serviceDescription,
            @Param("raNo") String raNo);
}