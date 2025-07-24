package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.CoreRolePermissionsDto;
import com.bellaryinfotech.DTO.CoreRolesDto;
import com.bellaryinfotech.model.CoreRolePermissions;
import com.bellaryinfotech.repo.CoreRolePermissionsRepository;
import com.bellaryinfotech.service.CoreRolePermissionsService;
import com.bellaryinfotech.service.CoreRolesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CoreRolePermissionsServiceImpl implements CoreRolePermissionsService {
    
    private static final Logger logger = LoggerFactory.getLogger(CoreRolePermissionsServiceImpl.class);
    
    @Autowired
    private CoreRolePermissionsRepository coreRolePermissionsRepository;
    
    @Autowired
    private CoreRolesService coreRolesService;
    
    @Override
    public CoreRolePermissionsDto createRolePermission(CoreRolePermissionsDto coreRolePermissionsDto) {
        try {
            logger.info("Creating new role permission for role ID: {}", coreRolePermissionsDto.getRoleId());
            
            // Validate required fields
            if (coreRolePermissionsDto.getRoleId() == null) {
                throw new IllegalArgumentException("Role ID is required");
            }
            
            if (coreRolePermissionsDto.getPerId() == null) {
                throw new IllegalArgumentException("Permission ID is required");
            }
            
            CoreRolePermissions coreRolePermissions = convertDtoToEntity(coreRolePermissionsDto);
            coreRolePermissions.setRpId(null); // Ensure new entity
            
            CoreRolePermissions savedPermission = coreRolePermissionsRepository.save(coreRolePermissions);
            logger.info("Successfully created role permission with ID: {}", savedPermission.getRpId());
            
            return convertEntityToDto(savedPermission);
        } catch (Exception e) {
            logger.error("Error creating role permission", e);
            throw new RuntimeException("Failed to create role permission: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Optional<CoreRolePermissionsDto> getRolePermissionById(Long rpId) {
        try {
            logger.info("Fetching role permission by ID: {}", rpId);
            Optional<CoreRolePermissions> permission = coreRolePermissionsRepository.findById(rpId);
            return permission.map(this::convertEntityToDto);
        } catch (Exception e) {
            logger.error("Error fetching role permission by ID: {}", rpId, e);
            throw new RuntimeException("Failed to fetch role permission: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<CoreRolePermissionsDto> getAllRolePermissions() {
        try {
            logger.info("Fetching all role permissions");
            List<CoreRolePermissions> permissions = coreRolePermissionsRepository.findAll();
            return permissions.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching all role permissions", e);
            throw new RuntimeException("Failed to fetch role permissions: " + e.getMessage(), e);
        }
    }
    
    @Override
    public CoreRolePermissionsDto updateRolePermission(Long rpId, CoreRolePermissionsDto coreRolePermissionsDto) {
        try {
            logger.info("Updating role permission with ID: {}", rpId);
            
            Optional<CoreRolePermissions> existingPermissionOpt = coreRolePermissionsRepository.findById(rpId);
            if (!existingPermissionOpt.isPresent()) {
                throw new RuntimeException("Role permission not found with ID: " + rpId);
            }
            
            CoreRolePermissions existingPermission = existingPermissionOpt.get();
            updateEntityFromDto(existingPermission, coreRolePermissionsDto);
            
            CoreRolePermissions updatedPermission = coreRolePermissionsRepository.save(existingPermission);
            logger.info("Successfully updated role permission with ID: {}", rpId);
            
            return convertEntityToDto(updatedPermission);
        } catch (Exception e) {
            logger.error("Error updating role permission with ID: {}", rpId, e);
            throw new RuntimeException("Failed to update role permission: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void deleteRolePermission(Long rpId) {
        try {
            logger.info("Deleting role permission with ID: {}", rpId);
            
            if (!coreRolePermissionsRepository.existsById(rpId)) {
                throw new RuntimeException("Role permission not found with ID: " + rpId);
            }
            
            coreRolePermissionsRepository.deleteById(rpId);
            logger.info("Successfully deleted role permission with ID: {}", rpId);
        } catch (Exception e) {
            logger.error("Error deleting role permission with ID: {}", rpId, e);
            throw new RuntimeException("Failed to delete role permission: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<CoreRolePermissionsDto> getPermissionsByRoleId(Long roleId) {
        try {
            logger.info("Fetching permissions by role ID: {}", roleId);
            List<CoreRolePermissions> permissions = coreRolePermissionsRepository.findByRoleIdOrderByCreationDateDesc(roleId);
            return permissions.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching permissions by role ID: {}", roleId, e);
            throw new RuntimeException("Failed to fetch permissions: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<CoreRolePermissionsDto> getPermissionsByPerId(Long perId) {
        try {
            logger.info("Fetching permissions by permission ID: {}", perId);
            List<CoreRolePermissions> permissions = coreRolePermissionsRepository.findByPerIdOrderByCreationDateDesc(perId);
            return permissions.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching permissions by permission ID: {}", perId, e);
            throw new RuntimeException("Failed to fetch permissions: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<CoreRolePermissionsDto> getPermissionsByTenantId(Integer tenantId) {
        try {
            logger.info("Fetching permissions by tenant ID: {}", tenantId);
            List<CoreRolePermissions> permissions = coreRolePermissionsRepository.findByTenantIdOrderByCreationDateDesc(tenantId);
            return permissions.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching permissions by tenant ID: {}", tenantId, e);
            throw new RuntimeException("Failed to fetch permissions: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean permissionExistsForRole(Long roleId, Long perId) {
        try {
            return coreRolePermissionsRepository.existsByRoleIdAndPerId(roleId, perId);
        } catch (Exception e) {
            logger.error("Error checking if permission exists for role: {}", roleId, e);
            throw new RuntimeException("Failed to check permission: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<CoreRolePermissionsDto> searchPermissions(Long roleId, Long perId, Integer tenantId) {
        try {
            logger.info("Searching permissions with criteria");
            List<CoreRolePermissions> permissions = coreRolePermissionsRepository.findByMultipleCriteria(roleId, perId, tenantId);
            return permissions.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error searching permissions", e);
            throw new RuntimeException("Failed to search permissions: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<CoreRolePermissionsDto> getPermissionsWithRoleDetails(Long roleId) {
        try {
            logger.info("Fetching permissions with role details for role ID: {}", roleId);
            List<CoreRolePermissions> permissions = coreRolePermissionsRepository.findByRoleIdOrderByCreationDateDesc(roleId);
            
            return permissions.stream()
                    .map(permission -> {
                        CoreRolePermissionsDto dto = convertEntityToDto(permission);
                        // Get role details
                        Optional<CoreRolesDto> roleOpt = coreRolesService.getRoleById(permission.getRoleId());
                        if (roleOpt.isPresent()) {
                            CoreRolesDto role = roleOpt.get();
                            dto.setRoleName(role.getRoleName());
                            dto.setRoleComments(role.getComments());
                            dto.setRoleReadOnly(role.getReadOnly());
                        }
                        return dto;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching permissions with role details", e);
            throw new RuntimeException("Failed to fetch permissions: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void deletePermissionsByRoleId(Long roleId) {
        try {
            logger.info("Deleting permissions by role ID: {}", roleId);
            coreRolePermissionsRepository.deleteByRoleId(roleId);
            logger.info("Successfully deleted permissions for role ID: {}", roleId);
        } catch (Exception e) {
            logger.error("Error deleting permissions by role ID: {}", roleId, e);
            throw new RuntimeException("Failed to delete permissions: " + e.getMessage(), e);
        }
    }
    
    @Override
    public long getTotalPermissionsCount() {
        try {
            return coreRolePermissionsRepository.count();
        } catch (Exception e) {
            logger.error("Error getting total permissions count", e);
            throw new RuntimeException("Failed to get count: " + e.getMessage(), e);
        }
    }
    
    // Helper methods for conversion
    private CoreRolePermissions convertDtoToEntity(CoreRolePermissionsDto dto) {
        CoreRolePermissions entity = new CoreRolePermissions();
        entity.setRpId(dto.getRpId());
        entity.setRoleId(dto.getRoleId());
        entity.setPerId(dto.getPerId());
        entity.setTenantId(dto.getTenantId());
        entity.setCreationDate(dto.getCreationDate());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setLastUpdateDate(dto.getLastUpdateDate());
        entity.setLastUpdatedBy(dto.getLastUpdatedBy());
        return entity;
    }
    
    private CoreRolePermissionsDto convertEntityToDto(CoreRolePermissions entity) {
        CoreRolePermissionsDto dto = new CoreRolePermissionsDto();
        dto.setRpId(entity.getRpId());
        dto.setRoleId(entity.getRoleId());
        dto.setPerId(entity.getPerId());
        dto.setTenantId(entity.getTenantId());
        dto.setCreationDate(entity.getCreationDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setLastUpdateDate(entity.getLastUpdateDate());
        dto.setLastUpdatedBy(entity.getLastUpdatedBy());
        return dto;
    }
    
    private void updateEntityFromDto(CoreRolePermissions entity, CoreRolePermissionsDto dto) {
        if (dto.getRoleId() != null) entity.setRoleId(dto.getRoleId());
        if (dto.getPerId() != null) entity.setPerId(dto.getPerId());
        if (dto.getTenantId() != null) entity.setTenantId(dto.getTenantId());
        if (dto.getCreatedBy() != null) entity.setCreatedBy(dto.getCreatedBy());
        if (dto.getLastUpdatedBy() != null) entity.setLastUpdatedBy(dto.getLastUpdatedBy());
    }
}
