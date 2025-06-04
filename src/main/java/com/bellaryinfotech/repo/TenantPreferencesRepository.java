package com.bellaryinfotech.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.TenantPreferences;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantPreferencesRepository extends JpaRepository<TenantPreferences, Long> {
    
    @Query("SELECT tp FROM TenantPreferences tp WHERE " +
           "LOWER(tp.appUrl) LIKE LOWER(CONCAT('%', :url, '%')) AND " +
           "LOWER(tp.tenantEnvIdentifier) IN ('dev', 'qa', 'mega_prod', 'mega_dev')")
    List<TenantPreferences> findByAppUrlContainingAndEnvironment(@Param("url") String url);
    
    @Query("SELECT tp FROM TenantPreferences tp WHERE " +
           "LOWER(tp.appUrl) LIKE LOWER(CONCAT('%', :url, '%')) AND " +
           "LOWER(tp.tenantEnvIdentifier) = LOWER(:env)")
    Optional<TenantPreferences> findByAppUrlContainingAndSpecificEnvironment(
            @Param("url") String url, 
            @Param("env") String environment);
}