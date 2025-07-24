package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.CoreRolePermissionsDto;
import java.util.List;
import java.util.Optional;

public interface CoreRolePermissionsService {
    
    /**
     * Create a new role permission
     */
    CoreRolePermissionsDto createRolePermission(CoreRolePermissionsDto coreRolePermissionsDto);
    
    /**
     * Get role permission by ID
     */
    Optional<CoreRolePermissionsDto> getRolePermissionById(Long rpId);
    
    /**
     * Get all role permissions
     */
    List<CoreRolePermissionsDto> getAllRolePermissions();
    
    /**
     * Update role permission
     */
    CoreRolePermissionsDto updateRolePermission(Long rpId, CoreRolePermissionsDto coreRolePermissionsDto);
    
    /**
     * Delete role permission
     */
    void deleteRolePermission(Long rpId);
    
    /**
     * Get permissions by role ID
     */
    List<CoreRolePermissionsDto> getPermissionsByRoleId(Long roleId);
    
    /**
     * Get permissions by permission ID
     */
    List<CoreRolePermissionsDto> getPermissionsByPerId(Long perId);
    
    /**
     * Get permissions by tenant ID
     */
    List<CoreRolePermissionsDto> getPermissionsByTenantId(Integer tenantId);
    
    /**
     * Check if permission exists for role
     */
    boolean permissionExistsForRole(Long roleId, Long perId);
    
    /**
     * Search permissions by multiple criteria
     */
    List<CoreRolePermissionsDto> searchPermissions(Long roleId, Long perId, Integer tenantId);
    
    /**
     * Get permissions with role details
     */
    List<CoreRolePermissionsDto> getPermissionsWithRoleDetails(Long roleId);
    
    /**
     * Delete permissions by role ID
     */
    void deletePermissionsByRoleId(Long roleId);
    
    /**
     * Get total count of permissions
     */
    long getTotalPermissionsCount();
}
