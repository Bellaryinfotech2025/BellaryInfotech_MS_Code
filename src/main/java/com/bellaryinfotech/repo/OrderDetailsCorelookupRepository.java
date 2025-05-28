package com.bellaryinfotech.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.OrderDetailsLookup;

@Repository
public interface OrderDetailsCorelookupRepository extends JpaRepository<OrderDetailsLookup, String> {
    
    List<OrderDetailsLookup> findByLookupTypeAndEnabledFlag(String lookupType, String enabledFlag);
}