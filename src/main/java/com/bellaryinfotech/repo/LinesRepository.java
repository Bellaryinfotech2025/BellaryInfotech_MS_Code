package com.bellaryinfotech.repo;

 

 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.LinesModel;

import java.util.List;

@Repository
public interface LinesRepository extends JpaRepository<LinesModel, Long> {
    
    List<LinesModel> findByOrderIdOrderByIsParentDescLineNumber(Long orderId);
    
    List<LinesModel> findByOrderIdAndIsParentTrue(Long orderId);
}
