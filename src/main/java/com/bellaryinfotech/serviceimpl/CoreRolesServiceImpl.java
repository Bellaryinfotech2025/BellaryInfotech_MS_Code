package com.bellaryinfotech.serviceimpl;
 

import com.bellaryinfotech.DTO.CoreRolesDto;
import com.bellaryinfotech.model.CoreRoles;
import com.bellaryinfotech.repo.CoreRolesRepository;
import com.bellaryinfotech.service.CoreRolesService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CoreRolesServiceImpl implements CoreRolesService {
    
    private static final Logger logger = LoggerFactory.getLogger(CoreRolesServiceImpl.class);
    
    @Autowired
    private CoreRolesRepository coreRolesRepository;
    
    @Override
    public CoreRolesDto createRole(CoreRolesDto coreRolesDto) {
        try {
            logger.info("Creating new role: {}", coreRolesDto.getRoleName());
            
            // Validate required fields
            if (coreRolesDto.getRoleName() == null || coreRolesDto.getRoleName().trim().isEmpty()) {
                throw new IllegalArgumentException("Role name is required");
            }
            
            if (coreRolesDto.getDateFrom() == null) {
                throw new IllegalArgumentException("Date From is required");
            }
            
            CoreRoles coreRoles = convertDtoToEntity(coreRolesDto);
            coreRoles.setRoleId(null); // Ensure new entity
            
            CoreRoles savedRole = coreRolesRepository.save(coreRoles);
            logger.info("Successfully created role with ID: {}", savedRole.getRoleId());
            
            return convertEntityToDto(savedRole);
        } catch (Exception e) {
            logger.error("Error creating role", e);
            throw new RuntimeException("Failed to create role: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Optional<CoreRolesDto> getRoleById(Long roleId) {
        try {
            logger.info("Fetching role by ID: {}", roleId);
            Optional<CoreRoles> role = coreRolesRepository.findById(roleId);
            return role.map(this::convertEntityToDto);
        } catch (Exception e) {
            logger.error("Error fetching role by ID: {}", roleId, e);
            throw new RuntimeException("Failed to fetch role: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<CoreRolesDto> getAllRoles() {
        try {
            logger.info("Fetching all roles");
            List<CoreRoles> roles = coreRolesRepository.findAll();
            return roles.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching all roles", e);
            throw new RuntimeException("Failed to fetch roles: " + e.getMessage(), e);
        }
    }
    
    @Override
    public CoreRolesDto updateRole(Long roleId, CoreRolesDto coreRolesDto) {
        try {
            logger.info("Updating role with ID: {}", roleId);
            
            Optional<CoreRoles> existingRoleOpt = coreRolesRepository.findById(roleId);
            if (!existingRoleOpt.isPresent()) {
                throw new RuntimeException("Role not found with ID: " + roleId);
            }
            
            CoreRoles existingRole = existingRoleOpt.get();
            updateEntityFromDto(existingRole, coreRolesDto);
            
            CoreRoles updatedRole = coreRolesRepository.save(existingRole);
            logger.info("Successfully updated role with ID: {}", roleId);
            
            return convertEntityToDto(updatedRole);
        } catch (Exception e) {
            logger.error("Error updating role with ID: {}", roleId, e);
            throw new RuntimeException("Failed to update role: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void deleteRole(Long roleId) {
        try {
            logger.info("Deleting role with ID: {}", roleId);
            
            if (!coreRolesRepository.existsById(roleId)) {
                throw new RuntimeException("Role not found with ID: " + roleId);
            }
            
            coreRolesRepository.deleteById(roleId);
            logger.info("Successfully deleted role with ID: {}", roleId);
        } catch (Exception e) {
            logger.error("Error deleting role with ID: {}", roleId, e);
            throw new RuntimeException("Failed to delete role: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<CoreRolesDto> getRolesByName(String roleName) {
        try {
            logger.info("Fetching roles by name: {}", roleName);
            List<CoreRoles> roles = coreRolesRepository.findByRoleNameContainingIgnoreCaseOrderByCreationDateDesc(roleName);
            return roles.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching roles by name: {}", roleName, e);
            throw new RuntimeException("Failed to fetch roles: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<CoreRolesDto> getRolesByEmployeeId(Long employeeId) {
        try {
            logger.info("Fetching roles by employee ID: {}", employeeId);
            List<CoreRoles> roles = coreRolesRepository.findByEmployeeIdOrderByCreationDateDesc(employeeId);
            return roles.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching roles by employee ID: {}", employeeId, e);
            throw new RuntimeException("Failed to fetch roles: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<CoreRolesDto> getActiveRoles() {
        try {
            logger.info("Fetching active roles");
            List<CoreRoles> roles = coreRolesRepository.findActiveRoles(LocalDate.now());
            return roles.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching active roles", e);
            throw new RuntimeException("Failed to fetch active roles: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<CoreRolesDto> getRolesByDateRange(LocalDate startDate, LocalDate endDate) {
        try {
            logger.info("Fetching roles by date range: {} to {}", startDate, endDate);
            List<CoreRoles> roles = coreRolesRepository.findRolesByDateRange(startDate, endDate);
            return roles.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching roles by date range", e);
            throw new RuntimeException("Failed to fetch roles: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<CoreRolesDto> getRolesByTenantId(Integer tenantId) {
        try {
            logger.info("Fetching roles by tenant ID: {}", tenantId);
            List<CoreRoles> roles = coreRolesRepository.findByTenantIdOrderByCreationDateDesc(tenantId);
            return roles.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching roles by tenant ID: {}", tenantId, e);
            throw new RuntimeException("Failed to fetch roles: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<CoreRolesDto> getReadOnlyRoles(String readOnly) {
        try {
            logger.info("Fetching read-only roles: {}", readOnly);
            List<CoreRoles> roles = coreRolesRepository.findByReadOnlyOrderByCreationDateDesc(readOnly);
            return roles.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching read-only roles", e);
            throw new RuntimeException("Failed to fetch roles: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean roleNameExists(String roleName) {
        try {
            return coreRolesRepository.existsByRoleNameIgnoreCase(roleName);
        } catch (Exception e) {
            logger.error("Error checking if role name exists: {}", roleName, e);
            throw new RuntimeException("Failed to check role name: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<CoreRolesDto> searchRoles(String roleName, Long employeeId, Integer tenantId, String readOnly) {
        try {
            logger.info("Searching roles with criteria");
            List<CoreRoles> roles = coreRolesRepository.findByMultipleCriteria(roleName, employeeId, tenantId, readOnly);
            return roles.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error searching roles", e);
            throw new RuntimeException("Failed to search roles: " + e.getMessage(), e);
        }
    }
    
    @Override
    public long getTotalRolesCount() {
        try {
            return coreRolesRepository.count();
        } catch (Exception e) {
            logger.error("Error getting total roles count", e);
            throw new RuntimeException("Failed to get count: " + e.getMessage(), e);
        }
    }
    
    // Helper methods for conversion
    private CoreRoles convertDtoToEntity(CoreRolesDto dto) {
        CoreRoles entity = new CoreRoles();
        entity.setRoleId(dto.getRoleId());
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setDateFrom(dto.getDateFrom());
        entity.setDateTo(dto.getDateTo());
        entity.setRoleName(dto.getRoleName());
        entity.setAttribute1(dto.getAttribute1());
        entity.setAttribute2(dto.getAttribute2());
        entity.setAttribute3(dto.getAttribute3());
        entity.setAttribute4(dto.getAttribute4());
        entity.setAttribute5(dto.getAttribute5());
        entity.setAttribute6(dto.getAttribute6());
        entity.setAttribute7(dto.getAttribute7());
        entity.setAttribute8(dto.getAttribute8());
        entity.setAttribute9(dto.getAttribute9());
        entity.setAttribute10(dto.getAttribute10());
        entity.setLastUpdateDate(dto.getLastUpdateDate());
        entity.setLastUpdatedBy(dto.getLastUpdatedBy());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreationDate(dto.getCreationDate());
        entity.setRoleInformation1(dto.getRoleInformation1());
        entity.setRoleInformation2(dto.getRoleInformation2());
        entity.setRoleInformation3(dto.getRoleInformation3());
        entity.setApprovalAuthority(dto.getApprovalAuthority());
        entity.setComments(dto.getComments());
        entity.setTenantId(dto.getTenantId());
        entity.setReadOnly(dto.getReadOnly());
        return entity;
    }
    
    private CoreRolesDto convertEntityToDto(CoreRoles entity) {
        CoreRolesDto dto = new CoreRolesDto();
        dto.setRoleId(entity.getRoleId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setDateFrom(entity.getDateFrom());
        dto.setDateTo(entity.getDateTo());
        dto.setRoleName(entity.getRoleName());
        dto.setAttribute1(entity.getAttribute1());
        dto.setAttribute2(entity.getAttribute2());
        dto.setAttribute3(entity.getAttribute3());
        dto.setAttribute4(entity.getAttribute4());
        dto.setAttribute5(entity.getAttribute5());
        dto.setAttribute6(entity.getAttribute6());
        dto.setAttribute7(entity.getAttribute7());
        dto.setAttribute8(entity.getAttribute8());
        dto.setAttribute9(entity.getAttribute9());
        dto.setAttribute10(entity.getAttribute10());
        dto.setLastUpdateDate(entity.getLastUpdateDate());
        dto.setLastUpdatedBy(entity.getLastUpdatedBy());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreationDate(entity.getCreationDate());
        dto.setRoleInformation1(entity.getRoleInformation1());
        dto.setRoleInformation2(entity.getRoleInformation2());
        dto.setRoleInformation3(entity.getRoleInformation3());
        dto.setApprovalAuthority(entity.getApprovalAuthority());
        dto.setComments(entity.getComments());
        dto.setTenantId(entity.getTenantId());
        dto.setReadOnly(entity.getReadOnly());
        return dto;
    }
    
    private void updateEntityFromDto(CoreRoles entity, CoreRolesDto dto) {
        if (dto.getEmployeeId() != null) entity.setEmployeeId(dto.getEmployeeId());
        if (dto.getDateFrom() != null) entity.setDateFrom(dto.getDateFrom());
        if (dto.getDateTo() != null) entity.setDateTo(dto.getDateTo());
        if (dto.getRoleName() != null) entity.setRoleName(dto.getRoleName());
        if (dto.getAttribute1() != null) entity.setAttribute1(dto.getAttribute1());
        if (dto.getAttribute2() != null) entity.setAttribute2(dto.getAttribute2());
        if (dto.getAttribute3() != null) entity.setAttribute3(dto.getAttribute3());
        if (dto.getAttribute4() != null) entity.setAttribute4(dto.getAttribute4());
        if (dto.getAttribute5() != null) entity.setAttribute5(dto.getAttribute5());
        if (dto.getAttribute6() != null) entity.setAttribute6(dto.getAttribute6());
        if (dto.getAttribute7() != null) entity.setAttribute7(dto.getAttribute7());
        if (dto.getAttribute8() != null) entity.setAttribute8(dto.getAttribute8());
        if (dto.getAttribute9() != null) entity.setAttribute9(dto.getAttribute9());
        if (dto.getAttribute10() != null) entity.setAttribute10(dto.getAttribute10());
        if (dto.getLastUpdatedBy() != null) entity.setLastUpdatedBy(dto.getLastUpdatedBy());
        if (dto.getCreatedBy() != null) entity.setCreatedBy(dto.getCreatedBy());
        if (dto.getRoleInformation1() != null) entity.setRoleInformation1(dto.getRoleInformation1());
        if (dto.getRoleInformation2() != null) entity.setRoleInformation2(dto.getRoleInformation2());
        if (dto.getRoleInformation3() != null) entity.setRoleInformation3(dto.getRoleInformation3());
        if (dto.getApprovalAuthority() != null) entity.setApprovalAuthority(dto.getApprovalAuthority());
        if (dto.getComments() != null) entity.setComments(dto.getComments());
        if (dto.getTenantId() != null) entity.setTenantId(dto.getTenantId());
        if (dto.getReadOnly() != null) entity.setReadOnly(dto.getReadOnly());
    }
}
