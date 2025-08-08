package com.bellaryinfotech.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.bellaryinfotech.model.MtrModule;
import java.util.List;

@Repository
public interface MtrModuleRepository extends JpaRepository<MtrModule, Long> {
    
    // Existing methods
    List<MtrModule> findByMarkNo(String markNo);
    List<MtrModule> findByWorkOrder(String workOrder);
    List<MtrModule> findByWorkOrderAndBuildingName(String workOrder, String buildingName);
    void deleteByMarkNo(String markNo);

    // NEW: Required methods for FabWrapper integration
    @Query("SELECT DISTINCT m.workOrder FROM MtrModule m WHERE m.workOrder IS NOT NULL AND m.workOrder != ''")
    List<String> findDistinctWorkOrders();

    @Query("SELECT DISTINCT m.buildingName FROM MtrModule m WHERE m.workOrder = :workOrder AND m.buildingName IS NOT NULL AND m.buildingName != ''")
    List<String> findDistinctBuildingNamesByWorkOrder(@Param("workOrder") String workOrder);

    @Query("SELECT DISTINCT m.drawingNo FROM MtrModule m WHERE m.workOrder = :workOrder AND m.buildingName = :buildingName AND m.drawingNo IS NOT NULL AND m.drawingNo != ''")
    List<String> findDistinctDrawingNosByWorkOrderAndBuildingName(@Param("workOrder") String workOrder, @Param("buildingName") String buildingName);

    @Query("SELECT DISTINCT m.markNo FROM MtrModule m WHERE m.workOrder = :workOrder AND m.buildingName = :buildingName AND m.drawingNo = :drawingNo AND m.markNo IS NOT NULL AND m.markNo != ''")
    List<String> findDistinctMarkNosByWorkOrderAndBuildingNameAndDrawingNo(@Param("workOrder") String workOrder, @Param("buildingName") String buildingName, @Param("drawingNo") String drawingNo);

    @Query("SELECT DISTINCT m.serviceDescription FROM MtrModule m WHERE m.workOrder = :workOrder AND m.serviceDescription IS NOT NULL AND m.serviceDescription != ''")
    List<String> findDistinctServiceDescriptionsByWorkOrder(@Param("workOrder") String workOrder);

    // Find by multiple criteria for MeterFabrication
    List<MtrModule> findByWorkOrderAndBuildingNameAndDrawingNoAndMarkNo(String workOrder, String buildingName, String drawingNo, String markNo);
}
