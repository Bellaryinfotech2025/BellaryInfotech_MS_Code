package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.AlignmentPreFabricatedProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlignmentPreFabricatedProductionRepository extends JpaRepository<AlignmentPreFabricatedProduction, Long> {

    List<AlignmentPreFabricatedProduction> findByWorkOrder(String workOrder);

    List<AlignmentPreFabricatedProduction> findByRaNo(String raNo);

    @Query("SELECT a.raNo FROM AlignmentPreFabricatedProduction a WHERE a.workOrder = :workOrder AND a.serviceDescription = :serviceDescription AND a.raNo IS NOT NULL ORDER BY a.id DESC LIMIT 1")
    String findLatestRaNoByWorkOrderAndServiceDescription(@Param("workOrder") String workOrder, @Param("serviceDescription") String serviceDescription);
}