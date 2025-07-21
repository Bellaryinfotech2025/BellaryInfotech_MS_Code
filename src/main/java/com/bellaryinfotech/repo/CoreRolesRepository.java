package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.CoreRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CoreRolesRepository extends JpaRepository<CoreRoles, Long> {
    
    /**
     * Find all roles by role name
     */
    List<CoreRoles> findByRoleNameContainingIgnoreCaseOrderByCreationDateDesc(String roleName);
    
    /**
     * Find all roles by employee ID
     */
    List<CoreRoles> findByEmployeeIdOrderByCreationDateDesc(Long employeeId);
    
    /**
     * Find all active roles (where DATE_TO is null or in future)
     */
    @Query("SELECT cr FROM CoreRoles cr WHERE cr.dateTo IS NULL OR cr.dateTo >= :currentDate ORDER BY cr.creationDate DESC")
    List<CoreRoles> findActiveRoles(@Param("currentDate") LocalDate currentDate);
    
    /**
     * Find roles by date range
     */
    @Query("SELECT cr FROM CoreRoles cr WHERE cr.dateFrom >= :startDate AND (cr.dateTo IS NULL OR cr.dateTo <= :endDate) ORDER BY cr.creationDate DESC")
    List<CoreRoles> findRolesByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    /**
     * Find roles by tenant ID
     */
    List<CoreRoles> findByTenantIdOrderByCreationDateDesc(Integer tenantId);
    
    /**
     * Find read-only roles
     */
    List<CoreRoles> findByReadOnlyOrderByCreationDateDesc(String readOnly);
    
    /**
     * Check if role name exists
     */
    boolean existsByRoleNameIgnoreCase(String roleName);
    
    /**
     * Find roles created by specific user
     */
    List<CoreRoles> findByCreatedByOrderByCreationDateDesc(Long createdBy);
    
    /**
     * Find roles by multiple criteria
     */
    @Query("SELECT cr FROM CoreRoles cr WHERE " +
           "(:roleName IS NULL OR LOWER(cr.roleName) LIKE LOWER(CONCAT('%', :roleName, '%'))) AND " +
           "(:employeeId IS NULL OR cr.employeeId = :employeeId) AND " +
           "(:tenantId IS NULL OR cr.tenantId = :tenantId) AND " +
           "(:readOnly IS NULL OR cr.readOnly = :readOnly) " +
           "ORDER BY cr.creationDate DESC")
    List<CoreRoles> findByMultipleCriteria(
            @Param("roleName") String roleName,
            @Param("employeeId") Long employeeId,
            @Param("tenantId") Integer tenantId,
            @Param("readOnly") String readOnly);
}
