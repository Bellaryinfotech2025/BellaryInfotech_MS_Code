package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.CoreRolesDto;
import com.bellaryinfotech.service.CoreRolesService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class CoreRolesController {
    
    private static final Logger logger = LoggerFactory.getLogger(CoreRolesController.class);
    
    @Autowired
    private CoreRolesService coreRolesService;
    
    // API endpoint constants
    public static final String GET_ALL_CORE_ROLES = "/getAllCoreRoles/details";
    public static final String GET_CORE_ROLE_BY_ID = "/getCoreRoleById/details";
    public static final String CREATE_CORE_ROLE = "/createCoreRole/details";
    public static final String UPDATE_CORE_ROLE = "/updateCoreRole/details";
    public static final String DELETE_CORE_ROLE = "/deleteCoreRole/details";
    public static final String GET_ROLES_BY_NAME = "/getCoreRolesByName/details";
    public static final String GET_ROLES_BY_EMPLOYEE_ID = "/getCoreRolesByEmployeeId/details";
    public static final String GET_ACTIVE_ROLES = "/getActiveCoreRoles/details";
    public static final String GET_ROLES_BY_DATE_RANGE = "/getCoreRolesByDateRange/details";
    public static final String GET_ROLES_BY_TENANT_ID = "/getCoreRolesByTenantId/details";
    public static final String GET_READ_ONLY_ROLES = "/getReadOnlyCoreRoles/details";
    public static final String CHECK_ROLE_NAME_EXISTS = "/checkCoreRoleNameExists/details";
    public static final String SEARCH_CORE_ROLES = "/searchCoreRoles/details";
    public static final String GET_TOTAL_ROLES_COUNT = "/getTotalCoreRolesCount/details";
    
    /**
     * Get all roles
     */
    @GetMapping(value = GET_ALL_CORE_ROLES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllRoles() {
        try {
            logger.info("Fetching all core roles");
            List<CoreRolesDto> roles = coreRolesService.getAllRoles();
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            logger.error("Error getting all roles", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get roles: " + e.getMessage());
        }
    }
    
    /**
     * Get role by ID
     */
    @GetMapping(value = GET_CORE_ROLE_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRoleById(@RequestParam Long roleId) {
        try {
            logger.info("Fetching core role by ID: {}", roleId);
            Optional<CoreRolesDto> role = coreRolesService.getRoleById(roleId);
            
            if (role.isPresent()) {
                return ResponseEntity.ok(role.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error getting role by ID: {}", roleId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get role: " + e.getMessage());
        }
    }
    
    /**
     * Create a new role
     */
    @PostMapping(value = CREATE_CORE_ROLE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRole(@Valid @RequestBody CoreRolesDto coreRolesDto) {
        try {
            logger.info("Creating new core role: {}", coreRolesDto.getRoleName());
            CoreRolesDto createdRole = coreRolesService.createRole(coreRolesDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input for creating role: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error creating role", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create role: " + e.getMessage());
        }
    }
    
    /**
     * Update role
     */
    @PutMapping(value = UPDATE_CORE_ROLE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRole(@RequestParam Long roleId, @Valid @RequestBody CoreRolesDto coreRolesDto) {
        try {
            logger.info("Updating core role with ID: {}", roleId);
            CoreRolesDto updatedRole = coreRolesService.updateRole(roleId, coreRolesDto);
            return ResponseEntity.ok(updatedRole);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input for updating role: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            logger.error("Error updating role", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update role: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error updating role", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update role: " + e.getMessage());
        }
    }
    
    /**
     * Delete role
     */
    @DeleteMapping(value = DELETE_CORE_ROLE)
    public ResponseEntity<?> deleteRole(@RequestParam Long roleId) {
        try {
            logger.info("Deleting core role with ID: {}", roleId);
            coreRolesService.deleteRole(roleId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            logger.error("Error deleting role", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete role: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error deleting role", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete role: " + e.getMessage());
        }
    }
    
    /**
     * Get roles by name
     */
    @GetMapping(value = GET_ROLES_BY_NAME, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRolesByName(@RequestParam String roleName) {
        try {
            logger.info("Fetching roles by name: {}", roleName);
            List<CoreRolesDto> roles = coreRolesService.getRolesByName(roleName);
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            logger.error("Error getting roles by name: {}", roleName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get roles: " + e.getMessage());
        }
    }
    
    /**
     * Get roles by employee ID
     */
    @GetMapping(value = GET_ROLES_BY_EMPLOYEE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRolesByEmployeeId(@RequestParam Long employeeId) {
        try {
            logger.info("Fetching roles by employee ID: {}", employeeId);
            List<CoreRolesDto> roles = coreRolesService.getRolesByEmployeeId(employeeId);
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            logger.error("Error getting roles by employee ID: {}", employeeId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get roles: " + e.getMessage());
        }
    }
    
    /**
     * Get active roles
     */
    @GetMapping(value = GET_ACTIVE_ROLES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getActiveRoles() {
        try {
            logger.info("Fetching active roles");
            List<CoreRolesDto> roles = coreRolesService.getActiveRoles();
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            logger.error("Error getting active roles", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get active roles: " + e.getMessage());
        }
    }
    
    /**
     * Get roles by date range
     */
    @GetMapping(value = GET_ROLES_BY_DATE_RANGE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRolesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            logger.info("Fetching roles by date range: {} to {}", startDate, endDate);
            List<CoreRolesDto> roles = coreRolesService.getRolesByDateRange(startDate, endDate);
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            logger.error("Error getting roles by date range", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get roles: " + e.getMessage());
        }
    }
    
    /**
     * Get roles by tenant ID
     */
    @GetMapping(value = GET_ROLES_BY_TENANT_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRolesByTenantId(@RequestParam Integer tenantId) {
        try {
            logger.info("Fetching roles by tenant ID: {}", tenantId);
            List<CoreRolesDto> roles = coreRolesService.getRolesByTenantId(tenantId);
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            logger.error("Error getting roles by tenant ID: {}", tenantId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get roles: " + e.getMessage());
        }
    }
    
    /**
     * Get read-only roles
     */
    @GetMapping(value = GET_READ_ONLY_ROLES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReadOnlyRoles(@RequestParam String readOnly) {
        try {
            logger.info("Fetching read-only roles: {}", readOnly);
            List<CoreRolesDto> roles = coreRolesService.getReadOnlyRoles(readOnly);
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            logger.error("Error getting read-only roles", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get roles: " + e.getMessage());
        }
    }
    
    /**
     * Check if role name exists
     */
    @GetMapping(value = CHECK_ROLE_NAME_EXISTS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkRoleNameExists(@RequestParam String roleName) {
        try {
            logger.info("Checking if role name exists: {}", roleName);
            boolean exists = coreRolesService.roleNameExists(roleName);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            logger.error("Error checking role name existence: {}", roleName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to check role name: " + e.getMessage());
        }
    }
    
    /**
     * Search roles by multiple criteria
     */
    @GetMapping(value = SEARCH_CORE_ROLES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchRoles(
            @RequestParam(required = false) String roleName,
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) Integer tenantId,
            @RequestParam(required = false) String readOnly) {
        try {
            logger.info("Searching roles with criteria");
            List<CoreRolesDto> roles = coreRolesService.searchRoles(roleName, employeeId, tenantId, readOnly);
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            logger.error("Error searching roles", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search roles: " + e.getMessage());
        }
    }
    
    /**
     * Get total roles count
     */
    @GetMapping(value = GET_TOTAL_ROLES_COUNT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTotalRolesCount() {
        try {
            logger.info("Getting total roles count");
            long count = coreRolesService.getTotalRolesCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            logger.error("Error getting total roles count", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get count: " + e.getMessage());
        }
    }
}
