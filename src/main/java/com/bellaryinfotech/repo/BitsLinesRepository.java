package com.bellaryinfotech.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.BitsLinesAll;

import java.util.List;

@Repository
public interface BitsLinesRepository extends JpaRepository<BitsLinesAll, Long> {
    
    List<BitsLinesAll> findBySerNoContainingIgnoreCase(String serNo);
    List<BitsLinesAll> findByServiceCodeContainingIgnoreCase(String serviceCode);
    List<BitsLinesAll> findByServiceDescContainingIgnoreCase(String serviceDesc);
    
    // Methods to find by work order stored in attribute1V
    List<BitsLinesAll> findByAttribute1VContainingIgnoreCase(String workOrder);
    List<BitsLinesAll> findByAttribute1V(String workOrder); // Exact match
}