package com.bellaryinfotech.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.bellaryinfotech.model.InchMeterModule;
import java.util.List;

@Repository
public interface InchMeterModuleRepository extends JpaRepository<InchMeterModule, Long> {
    
    // Basic CRUD operations
    List<InchMeterModule> findByMarkNo(String markNo);
    List<InchMeterModule> findByWorkOrder(String workOrder);
    List<InchMeterModule> findByWorkOrderAndBuildingName(String workOrder, String buildingName);
    void deleteByMarkNo(String markNo);

    // Required methods for FabWrapper integration
    @Query("SELECT DISTINCT i.workOrder FROM InchMeterModule i WHERE i.workOrder IS NOT NULL AND i.workOrder != ''")
    List<String> findDistinctWorkOrders();

    @Query("SELECT DISTINCT i.buildingName FROM InchMeterModule i WHERE i.workOrder = :workOrder AND i.buildingName IS NOT NULL AND i.buildingName != ''")
    List<String> findDistinctBuildingNamesByWorkOrder(@Param("workOrder") String workOrder);

    @Query("SELECT DISTINCT i.drawingNo FROM InchMeterModule i WHERE i.workOrder = :workOrder AND i.buildingName = :buildingName AND i.drawingNo IS NOT NULL AND i.drawingNo != ''")
    List<String> findDistinctDrawingNosByWorkOrderAndBuildingName(@Param("workOrder") String workOrder, @Param("buildingName") String buildingName);

    @Query("SELECT DISTINCT i.markNo FROM InchMeterModule i WHERE i.workOrder = :workOrder AND i.buildingName = :buildingName AND i.drawingNo = :drawingNo AND i.markNo IS NOT NULL AND i.markNo != ''")
    List<String> findDistinctMarkNosByWorkOrderAndBuildingNameAndDrawingNo(@Param("workOrder") String workOrder, @Param("buildingName") String buildingName, @Param("drawingNo") String drawingNo);

    @Query("SELECT DISTINCT i.serviceDescription FROM InchMeterModule i WHERE i.workOrder = :workOrder AND i.serviceDescription IS NOT NULL AND i.serviceDescription != ''")
    List<String> findDistinctServiceDescriptionsByWorkOrder(@Param("workOrder") String workOrder);

    // Find by multiple criteria for InchMeterFabrication
    List<InchMeterModule> findByWorkOrderAndBuildingNameAndDrawingNoAndMarkNo(String workOrder, String buildingName, String drawingNo, String markNo);
}
