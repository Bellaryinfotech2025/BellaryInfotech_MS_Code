package com.bellaryinfotech.repo;
 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.ServiceCodeEntry;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceCodeEntryRepository extends JpaRepository<ServiceCodeEntry, String> {
    
    Optional<ServiceCodeEntry> findByCode(String code);
    List<ServiceCodeEntry> findByCodeContainingIgnoreCase(String code);
    List<ServiceCodeEntry> findByNameContainingIgnoreCase(String name);
}
