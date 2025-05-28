package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.BitsLinesAll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BitsLinesRepository extends JpaRepository<BitsLinesAll, Long> {
    
    List<BitsLinesAll> findBySerNoContainingIgnoreCase(String serNo);
    List<BitsLinesAll> findByServiceCodeContainingIgnoreCase(String serviceCode);
    List<BitsLinesAll> findByServiceDescContainingIgnoreCase(String serviceDesc);
    List<BitsLinesAll> findByUomContainingIgnoreCase(String uom);
}
