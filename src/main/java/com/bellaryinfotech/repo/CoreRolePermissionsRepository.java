package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.CoreRolePermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CoreRolePermissionsRepository extends JpaRepository<CoreRolePermissions, Long> {
    
    /**
     * Find all permissions by role ID
     */
    List<CoreRolePermissions> findByRoleIdOrderByCreationDateDesc(Long roleId);
    
    /**
     * Find all permissions by permission ID
     */
    List<CoreRolePermissions> findByPerIdOrderByCreationDateDesc(Long perId);
    
    /**
     * Find permissions by tenant ID
     */
    List<CoreRolePermissions> findByTenantIdOrderByCreationDateDesc(Integer tenantId);
    
    /**
     * Find permissions by role ID and permission ID
     */
    List<CoreRolePermissions> findByRoleIdAndPerId(Long roleId, Long perId);
    
    /**
     * Check if permission exists for role
     */
    boolean existsByRoleIdAndPerId(Long roleId, Long perId);
    
    /**
     * Find permissions created by specific user
     */
    List<CoreRolePermissions> findByCreatedByOrderByCreationDateDesc(Long createdBy);
    
    /**
     * Delete permissions by role ID
     */
    void deleteByRoleId(Long roleId);
    
    /**
     * Delete permissions by permission ID
     */
    void deleteByPerId(Long perId);
    
    /**
     * Find permissions with role details
     */
    @Query("SELECT crp FROM CoreRolePermissions crp WHERE " +
           "(:roleId IS NULL OR crp.roleId = :roleId) AND " +
           "(:perId IS NULL OR crp.perId = :perId) AND " +
           "(:tenantId IS NULL OR crp.tenantId = :tenantId) " +
           "ORDER BY crp.creationDate DESC")
    List<CoreRolePermissions> findByMultipleCriteria(
            @Param("roleId") Long roleId,
            @Param("perId") Long perId,
            @Param("tenantId") Integer tenantId);
}
