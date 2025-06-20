package com.bellaryinfotech.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.BitsHeaderAll;

import java.util.List;
import java.util.Optional;

@Repository
public interface BitsHeaderRepository extends JpaRepository<BitsHeaderAll, Long> {
    
    List<BitsHeaderAll> findByWorkOrderContainingIgnoreCase(String workOrder);
    List<BitsHeaderAll> findByPlantLocationContainingIgnoreCase(String plantLocation);
    List<BitsHeaderAll> findByDepartmentContainingIgnoreCase(String department);
    List<BitsHeaderAll> findByWorkLocationContainingIgnoreCase(String workLocation);
    List<BitsHeaderAll> findByLdApplicable(Boolean ldApplicable);
	Optional<BitsHeaderAll> findByWorkOrder(String workOrderNo);
}