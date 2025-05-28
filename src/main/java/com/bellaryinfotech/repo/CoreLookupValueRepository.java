package com.bellaryinfotech.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bellaryinfotech.model.CoreLookupValue;

public interface CoreLookupValueRepository extends JpaRepository<CoreLookupValue, Long> {
    Page<CoreLookupValue> findByLookupTypeContainingIgnoreCase(String lookupType, Pageable pageable);
}
