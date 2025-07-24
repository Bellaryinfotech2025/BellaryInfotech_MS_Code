package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.CoreRolePermissionsDto;
import com.bellaryinfotech.service.CoreRolePermissionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class CoreRolePermissionsController {
    
    private static final Logger logger = LoggerFactory.getLogger(CoreRolePermissionsController.class);
    
    @Autowired
    private CoreRolePermissionsService coreRolePermissionsService;
    
    // API endpoint constants
    public static final String GET_ALL_ROLE_PERMISSIONS = "/getAllCoreRolePermissions/details";
    public static final String GET_ROLE_PERMISSION_BY_ID = "/getCoreRolePermissionById/details";
    public static final String CREATE_ROLE_PERMISSION = "/createCoreRolePermission/details";
    public static final String UPDATE_ROLE_PERMISSION = "/updateCoreRolePermission/details";
    public static final String DELETE_ROLE_PERMISSION = "/deleteCoreRolePermission/details";
    public static final String GET_PERMISSIONS_BY_ROLE_ID = "/getCoreRolePermissionsByRoleId/details";
    public static final String GET_PERMISSIONS_BY_PER_ID = "/getCoreRolePermissionsByPerId/details";
    public static final String GET_PERMISSIONS_BY_TENANT_ID = "/getCoreRolePermissionsByTenantId/details";
    public static final String CHECK_PERMISSION_EXISTS = "/checkCoreRolePermissionExists/details";
    public static final String SEARCH_ROLE_PERMISSIONS = "/searchCoreRolePermissions/details";
    public static final String GET_PERMISSIONS_WITH_ROLE_DETAILS = "/getCoreRolePermissionsWithRoleDetails/details";
    public static final String DELETE_PERMISSIONS_BY_ROLE_ID = "/deleteCoreRolePermissionsByRoleId/details";
    public static final String GET_TOTAL_PERMISSIONS_COUNT = "/getTotalCoreRolePermissionsCount/details";
    
    /**
     * Get all role permissions
     */
    @GetMapping(value = GET_ALL_ROLE_PERMISSIONS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllRolePermissions() {
        try {
            logger.info("Fetching all core role permissions");
            List<CoreRolePermissionsDto> permissions = coreRolePermissionsService.getAllRolePermissions();
            return ResponseEntity.ok(permissions);
        } catch (Exception e) {
            logger.error("Error getting all role permissions", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get role permissions: " + e.getMessage());
        }
    }
    
    /**
     * Get role permission by ID
     */
    @GetMapping(value = GET_ROLE_PERMISSION_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRolePermissionById(@RequestParam Long rpId) {
        try {
            logger.info("Fetching core role permission by ID: {}", rpId);
            Optional<CoreRolePermissionsDto> permission = coreRolePermissionsService.getRolePermissionById(rpId);
            
            if (permission.isPresent()) {
                return ResponseEntity.ok(permission.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error getting role permission by ID: {}", rpId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get role permission: " + e.getMessage());
        }
    }
    
    /**
     * Create a new role permission
     */
    @PostMapping(value = CREATE_ROLE_PERMISSION, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRolePermission(@Valid @RequestBody CoreRolePermissionsDto coreRolePermissionsDto) {
        try {
            logger.info("Creating new core role permission for role ID: {}", coreRolePermissionsDto.getRoleId());
            CoreRolePermissionsDto createdPermission = coreRolePermissionsService.createRolePermission(coreRolePermissionsDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPermission);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input for creating role permission: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error creating role permission", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create role permission: " + e.getMessage());
        }
    }
    
    /**
     * Update role permission
     */
    @PutMapping(value = UPDATE_ROLE_PERMISSION, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRolePermission(@RequestParam Long rpId, @Valid @RequestBody CoreRolePermissionsDto coreRolePermissionsDto) {
        try {
            logger.info("Updating core role permission with ID: {}", rpId);
            CoreRolePermissionsDto updatedPermission = coreRolePermissionsService.updateRolePermission(rpId, coreRolePermissionsDto);
            return ResponseEntity.ok(updatedPermission);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input for updating role permission: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            logger.error("Error updating role permission", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update role permission: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error updating role permission", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update role permission: " + e.getMessage());
        }
    }
    
    /**
     * Delete role permission
     */
    @DeleteMapping(value = DELETE_ROLE_PERMISSION)
    public ResponseEntity<?> deleteRolePermission(@RequestParam Long rpId) {
        try {
            logger.info("Deleting core role permission with ID: {}", rpId);
            coreRolePermissionsService.deleteRolePermission(rpId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            logger.error("Error deleting role permission", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete role permission: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error deleting role permission", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete role permission: " + e.getMessage());
        }
    }
    
    /**
     * Get permissions by role ID
     */
    @GetMapping(value = GET_PERMISSIONS_BY_ROLE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPermissionsByRoleId(@RequestParam Long roleId) {
        try {
            logger.info("Fetching permissions by role ID: {}", roleId);
            List<CoreRolePermissionsDto> permissions = coreRolePermissionsService.getPermissionsByRoleId(roleId);
            return ResponseEntity.ok(permissions);
        } catch (Exception e) {
            logger.error("Error getting permissions by role ID: {}", roleId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get permissions: " + e.getMessage());
        }
    }
    
    /**
     * Get permissions by permission ID
     */
    @GetMapping(value = GET_PERMISSIONS_BY_PER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPermissionsByPerId(@RequestParam Long perId) {
        try {
            logger.info("Fetching permissions by permission ID: {}", perId);
            List<CoreRolePermissionsDto> permissions = coreRolePermissionsService.getPermissionsByPerId(perId);
            return ResponseEntity.ok(permissions);
        } catch (Exception e) {
            logger.error("Error getting permissions by permission ID: {}", perId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get permissions: " + e.getMessage());
        }
    }
    
    /**
     * Get permissions by tenant ID
     */
    @GetMapping(value = GET_PERMISSIONS_BY_TENANT_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPermissionsByTenantId(@RequestParam Integer tenantId) {
        try {
            logger.info("Fetching permissions by tenant ID: {}", tenantId);
            List<CoreRolePermissionsDto> permissions = coreRolePermissionsService.getPermissionsByTenantId(tenantId);
            return ResponseEntity.ok(permissions);
        } catch (Exception e) {
            logger.error("Error getting permissions by tenant ID: {}", tenantId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get permissions: " + e.getMessage());
        }
    }
    
    /**
     * Check if permission exists for role
     */
    @GetMapping(value = CHECK_PERMISSION_EXISTS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkPermissionExists(@RequestParam Long roleId, @RequestParam Long perId) {
        try {
            logger.info("Checking if permission exists for role ID: {} and permission ID: {}", roleId, perId);
            boolean exists = coreRolePermissionsService.permissionExistsForRole(roleId, perId);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            logger.error("Error checking permission existence", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to check permission: " + e.getMessage());
        }
    }
    
    /**
     * Search permissions by multiple criteria
     */
    @GetMapping(value = SEARCH_ROLE_PERMISSIONS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchPermissions(
            @RequestParam(required = false) Long roleId,
            @RequestParam(required = false) Long perId,
            @RequestParam(required = false) Integer tenantId) {
        try {
            logger.info("Searching permissions with criteria");
            List<CoreRolePermissionsDto> permissions = coreRolePermissionsService.searchPermissions(roleId, perId, tenantId);
            return ResponseEntity.ok(permissions);
        } catch (Exception e) {
            logger.error("Error searching permissions", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search permissions: " + e.getMessage());
        }
    }
    
    /**
     * Get permissions with role details
     */
    @GetMapping(value = GET_PERMISSIONS_WITH_ROLE_DETAILS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPermissionsWithRoleDetails(@RequestParam Long roleId) {
        try {
            logger.info("Fetching permissions with role details for role ID: {}", roleId);
            List<CoreRolePermissionsDto> permissions = coreRolePermissionsService.getPermissionsWithRoleDetails(roleId);
            return ResponseEntity.ok(permissions);
        } catch (Exception e) {
            logger.error("Error getting permissions with role details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get permissions: " + e.getMessage());
        }
    }
    
    /**
     * Delete permissions by role ID
     */
    @DeleteMapping(value = DELETE_PERMISSIONS_BY_ROLE_ID)
    public ResponseEntity<?> deletePermissionsByRoleId(@RequestParam Long roleId) {
        try {
            logger.info("Deleting permissions by role ID: {}", roleId);
            coreRolePermissionsService.deletePermissionsByRoleId(roleId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting permissions by role ID: {}", roleId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete permissions: " + e.getMessage());
        }
    }
    
    /**
     * Get total permissions count
     */
    @GetMapping(value = GET_TOTAL_PERMISSIONS_COUNT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTotalPermissionsCount() {
        try {
            logger.info("Getting total permissions count");
            long count = coreRolePermissionsService.getTotalPermissionsCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            logger.error("Error getting total permissions count", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get count: " + e.getMessage());
        }
    }
}
