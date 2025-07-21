package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.CoreRolesDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CoreRolesService {
    
    /**
     * Create a new role
     */
    CoreRolesDto createRole(CoreRolesDto coreRolesDto);
    
    /**
     * Get role by ID
     */
    Optional<CoreRolesDto> getRoleById(Long roleId);
    
    /**
     * Get all roles
     */
    List<CoreRolesDto> getAllRoles();
    
    /**
     * Update role
     */
    CoreRolesDto updateRole(Long roleId, CoreRolesDto coreRolesDto);
    
    /**
     * Delete role
     */
    void deleteRole(Long roleId);
    
    /**
     * Get roles by role name
     */
    List<CoreRolesDto> getRolesByName(String roleName);
    
    /**
     * Get roles by employee ID
     */
    List<CoreRolesDto> getRolesByEmployeeId(Long employeeId);
    
    /**
     * Get active roles
     */
    List<CoreRolesDto> getActiveRoles();
    
    /**
     * Get roles by date range
     */
    List<CoreRolesDto> getRolesByDateRange(LocalDate startDate, LocalDate endDate);
    
    /**
     * Get roles by tenant ID
     */
    List<CoreRolesDto> getRolesByTenantId(Integer tenantId);
    
    /**
     * Get read-only roles
     */
    List<CoreRolesDto> getReadOnlyRoles(String readOnly);
    
    /**
     * Check if role name exists
     */
    boolean roleNameExists(String roleName);
    
    /**
     * Search roles by multiple criteria
     */
    List<CoreRolesDto> searchRoles(String roleName, Long employeeId, Integer tenantId, String readOnly);
    
    /**
     * Get total count of roles
     */
    long getTotalRolesCount();
}
