package com.bellaryinfotech.controller;

import com.bellaryinfotech.model.BitsHeaderAll;
import com.bellaryinfotech.DTO.BitsHeaderDto;
import com.bellaryinfotech.service.BitsHeaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class BitsHeaderController {

    private static final Logger LOG = LoggerFactory.getLogger(BitsHeaderController.class);

    @Autowired
    private BitsHeaderService headerService;

    @Autowired
    private DataSource dataSource;
    

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    

    // Constants for endpoints
    public static final String GET_ALL_HEADERS = "/getAllBitsHeaders/details";
    public static final String GET_HEADER_BY_ID = "/getBitsHeaderById/details";
    public static final String CREATE_HEADER = "/createBitsHeader/details";
    public static final String UPDATE_HEADER = "/updateBitsHeader/details";
    public static final String DELETE_HEADER = "/deleteBitsHeader/details";
    public static final String SEARCH_BY_WORKORDER = "/searchBitsHeadersByWorkOrder/details";
    public static final String SEARCH_BY_PLANTLOCATION = "/searchBitsHeadersByPlantLocation/details";
    public static final String SEARCH_BY_DEPARTMENT = "/searchBitsHeadersByDepartment/details";
    public static final String SEARCH_BY_WORKLOCATION = "/searchBitsHeadersByWorkLocation/details";
    
    // New endpoints for work order dropdown
    public static final String GET_WORKORDER_NUMBERS = "/getworkorder/number";
    public static final String GET_WORKORDER_DETAILS = "/getworkorder/number/{workOrderNumber}";
    public static final String GET_LINES_BY_WORKORDER = "/getBitsLinesByWorkOrderNumberHere/details";

    // ============ WORKING GET ENDPOINTS (Keep these as they are working) ============

    // Get work order numbers from bits_po_entry_header table - WORKING
    @GetMapping(value = GET_WORKORDER_NUMBERS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getAllWorkOrderNumbers() {
        List<String> workOrders = new ArrayList<>();
        
        try (Connection connection = dataSource.getConnection()) {
            // Query using the correct column name 'work_order' from your entity
            String sql = """
                SELECT DISTINCT work_order 
                FROM bits_po_entry_header 
                WHERE work_order IS NOT NULL 
                AND work_order != ''
                AND work_order != 'null'
                ORDER BY work_order
                """;
            
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                
                while (resultSet.next()) {
                    String workOrderNumber = resultSet.getString("work_order");
                    if (workOrderNumber != null && !workOrderNumber.trim().isEmpty()) {
                        workOrders.add(workOrderNumber.trim());
                    }
                }
                
                LOG.info("Successfully fetched {} work order numbers from bits_po_entry_header table", workOrders.size());
                return ResponseEntity.ok(workOrders);
                
            }
        } catch (Exception e) {
            LOG.error("Error fetching work order numbers from bits_po_entry_header table", e);
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    // MODIFIED: Get work order details from bits_po_entry_header table - UPDATED TO RETURN ORDER_ID
    @GetMapping(value = GET_WORKORDER_DETAILS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getWorkOrderDetails(@PathVariable String workOrderNumber) {
        try (Connection connection = dataSource.getConnection()) {
            // UPDATED: Added order_id to the query - THIS IS THE KEY FIX
            String sql = """
                SELECT order_id, work_order, plant_location, department, work_location,
                       work_order_date, completion_date, ld_applicable,
                       scrap_allowance_visible_percent, scrap_allowance_invisible_percent, material_issue_type,
                       creation_date, created_by, last_update_date, last_updated_by
                FROM bits_po_entry_header 
                WHERE work_order = ?
                LIMIT 1
                """;
            
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, workOrderNumber);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // UPDATED: Create response map to explicitly include orderId
                        Map<String, Object> response = new HashMap<>();
                        response.put("orderId", resultSet.getLong("order_id")); // KEY FIX: Explicitly return orderId
                        response.put("workOrder", resultSet.getString("work_order"));
                        response.put("plantLocation", resultSet.getString("plant_location"));
                        response.put("department", resultSet.getString("department"));
                        response.put("workLocation", resultSet.getString("work_location"));
                        
                        // Handle dates safely
                        try {
                            if (resultSet.getDate("work_order_date") != null) {
                                response.put("workOrderDate", resultSet.getDate("work_order_date").toLocalDate().toString());
                            }
                        } catch (Exception dateEx) {
                            LOG.warn("Error parsing work_order_date for work order {}", workOrderNumber);
                        }
                        
                        try {
                            if (resultSet.getDate("completion_date") != null) {
                                response.put("completionDate", resultSet.getDate("completion_date").toLocalDate().toString());
                            }
                        } catch (Exception dateEx) {
                            LOG.warn("Error parsing completion_date for work order {}", workOrderNumber);
                        }
                        
                        // Handle boolean
                        response.put("ldApplicable", resultSet.getBoolean("ld_applicable"));
                        
                        // Handle the new fields
                        response.put("scrapAllowanceVisiblePercent", resultSet.getString("scrap_allowance_visible_percent"));
                        response.put("scrapAllowanceInvisiblePercent", resultSet.getString("scrap_allowance_invisible_percent"));
                        response.put("materialIssueType", resultSet.getString("material_issue_type"));
                        
                        // Handle timestamps
                        try {
                            if (resultSet.getTimestamp("creation_date") != null) {
                                response.put("creationDate", resultSet.getTimestamp("creation_date").toString());
                            }
                        } catch (Exception dateEx) {
                            LOG.warn("Error parsing creation_date for work order {}", workOrderNumber);
                        }
                        
                        try {
                            if (resultSet.getTimestamp("last_update_date") != null) {
                                response.put("lastUpdateDate", resultSet.getTimestamp("last_update_date").toString());
                            }
                        } catch (Exception dateEx) {
                            LOG.warn("Error parsing last_update_date for work order {}", workOrderNumber);
                        }
                        
                        // Handle Long fields
                        try {
                            response.put("createdBy", resultSet.getLong("created_by"));
                        } catch (Exception ex) {
                            LOG.warn("Error parsing created_by for work order {}", workOrderNumber);
                        }
                        
                        try {
                            response.put("lastUpdatedBy", resultSet.getLong("last_updated_by"));
                        } catch (Exception ex) {
                            LOG.warn("Error parsing last_updated_by for work order {}", workOrderNumber);
                        }
                        
                        LOG.info("Successfully fetched details for work order: {} with order_id: {}", 
                                workOrderNumber, resultSet.getLong("order_id"));
                        return ResponseEntity.ok(response);
                    } else {
                        LOG.warn("No work order found with number: {}", workOrderNumber);
                        return ResponseEntity.notFound().build();
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Error fetching work order details for: {}", workOrderNumber, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error fetching work order details: " + e.getMessage());
        }
    }

    // ============ DIRECT IMPLEMENTATION OF getAllBitsHeaders/details ============
    
    // This is the endpoint that's failing with 500 error - implementing with direct SQL
    @GetMapping(value = GET_ALL_HEADERS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BitsHeaderAll>> getAllHeaders() {
        LOG.info("Fetching all bits headers");
        List<BitsHeaderAll> headers = new ArrayList<>();
        
        try (Connection connection = dataSource.getConnection()) {
            // UPDATED: Added new columns to the query
            String sql = """
                SELECT order_id, work_order, plant_location, department, work_location,
                       work_order_date, completion_date, ld_applicable, tenant_id,
                       scrap_allowance_visible_percent, scrap_allowance_invisible_percent, material_issue_type,
                       creation_date, created_by, last_update_date, last_updated_by,
                       attribute1_v, attribute2_v, attribute3_v, attribute4_v, attribute5_v
                FROM bits_po_entry_header 
                ORDER BY order_id DESC
                """;
            
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        BitsHeaderAll header = new BitsHeaderAll();
                        
                        // Set basic fields
                        header.setOrderId(resultSet.getLong("order_id"));
                        header.setWorkOrder(resultSet.getString("work_order"));
                        header.setPlantLocation(resultSet.getString("plant_location"));
                        header.setDepartment(resultSet.getString("department"));
                        header.setWorkLocation(resultSet.getString("work_location"));
                        
                        // Handle dates safely
                        try {
                            if (resultSet.getDate("work_order_date") != null) {
                                header.setWorkOrderDate(resultSet.getDate("work_order_date").toLocalDate());
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing work_order_date for order ID: {}", header.getOrderId());
                        }
                        
                        try {
                            if (resultSet.getDate("completion_date") != null) {
                                header.setCompletionDate(resultSet.getDate("completion_date").toLocalDate());
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing completion_date for order ID: {}", header.getOrderId());
                        }
                        
                        // Handle boolean
                        header.setLdApplicable(resultSet.getBoolean("ld_applicable"));
                        
                        // ADDED: Handle new fields
                        header.setScrapAllowanceVisiblePercent(resultSet.getString("scrap_allowance_visible_percent"));
                        header.setScrapAllowanceInvisiblePercent(resultSet.getString("scrap_allowance_invisible_percent"));
                        header.setMaterialIssueType(resultSet.getString("material_issue_type"));
                        
                        // Handle tenant ID
                        try {
                            header.setTenantId(resultSet.getInt("tenant_id"));
                        } catch (Exception e) {
                            LOG.warn("Error parsing tenant_id for order ID: {}", header.getOrderId());
                            header.setTenantId(1); // Default tenant ID
                        }
                        
                        // Handle timestamps
                        try {
                            Timestamp creationDate = resultSet.getTimestamp("creation_date");
                            if (creationDate != null) {
                                header.setCreationDate(creationDate);
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing creation_date for order ID: {}", header.getOrderId());
                        }
                        
                        try {
                            Timestamp lastUpdateDate = resultSet.getTimestamp("last_update_date");
                            if (lastUpdateDate != null) {
                                header.setLastUpdateDate(lastUpdateDate);
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing last_update_date for order ID: {}", header.getOrderId());
                        }
                        
                        // Handle created_by and last_updated_by
                        try {
                            header.setCreatedBy(resultSet.getLong("created_by"));
                        } catch (Exception e) {
                            LOG.warn("Error parsing created_by for order ID: {}", header.getOrderId());
                        }
                        
                        try {
                            header.setLastUpdatedBy(resultSet.getLong("last_updated_by"));
                        } catch (Exception e) {
                            LOG.warn("Error parsing last_updated_by for order ID: {}", header.getOrderId());
                        }
                        
                        // Handle attribute fields
                        header.setAttribute1V(resultSet.getString("attribute1_v"));
                        header.setAttribute2V(resultSet.getString("attribute2_v"));
                        header.setAttribute3V(resultSet.getString("attribute3_v"));
                        header.setAttribute4V(resultSet.getString("attribute4_v"));
                        header.setAttribute5V(resultSet.getString("attribute5_v"));
                        
                        headers.add(header);
                    }
                    
                    LOG.info("Found {} headers total: {}", headers.size());
                    return ResponseEntity.ok(headers);
                }
            
        } catch (Exception e) {
            LOG.error("Error searching bits headers by work order", e);
            return ResponseEntity.ok(new ArrayList<>()); // Return empty list instead of error
        }
    }

    @GetMapping(value = SEARCH_BY_PLANTLOCATION, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BitsHeaderAll>> searchByPlantLocation(@RequestParam String plantLocation) {
        LOG.info("Searching bits headers by plant location: {}", plantLocation);
        List<BitsHeaderAll> headers = new ArrayList<>();
        
        try (Connection connection = dataSource.getConnection()) {
            String sql = """
                SELECT order_id, work_order, plant_location, department, work_location,
                       work_order_date, completion_date, ld_applicable, tenant_id,
                       scrap_allowance_visible_percent, scrap_allowance_invisible_percent, material_issue_type,
                       creation_date, created_by, last_update_date, last_updated_by,
                       attribute1_v, attribute2_v, attribute3_v, attribute4_v, attribute5_v
                FROM bits_po_entry_header 
                WHERE plant_location ILIKE ?
                ORDER BY order_id DESC
                """;
            
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, "%" + plantLocation + "%");
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        BitsHeaderAll header = new BitsHeaderAll();
                        
                        // Set basic fields
                        header.setOrderId(resultSet.getLong("order_id"));
                        header.setWorkOrder(resultSet.getString("work_order"));
                        header.setPlantLocation(resultSet.getString("plant_location"));
                        header.setDepartment(resultSet.getString("department"));
                        header.setWorkLocation(resultSet.getString("work_location"));
                        
                        // Handle dates safely
                        try {
                            if (resultSet.getDate("work_order_date") != null) {
                                header.setWorkOrderDate(resultSet.getDate("work_order_date").toLocalDate());
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing work_order_date for order ID: {}", header.getOrderId());
                        }
                        
                        try {
                            if (resultSet.getDate("completion_date") != null) {
                                header.setCompletionDate(resultSet.getDate("completion_date").toLocalDate());
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing completion_date for order ID: {}", header.getOrderId());
                        }
                        
                        // Handle boolean
                        header.setLdApplicable(resultSet.getBoolean("ld_applicable"));
                        
                        // Handle new fields
                        header.setScrapAllowanceVisiblePercent(resultSet.getString("scrap_allowance_visible_percent"));
                        header.setScrapAllowanceInvisiblePercent(resultSet.getString("scrap_allowance_invisible_percent"));
                        header.setMaterialIssueType(resultSet.getString("material_issue_type"));
                        
                        // Handle tenant ID
                        try {
                            header.setTenantId(resultSet.getInt("tenant_id"));
                        } catch (Exception e) {
                            LOG.warn("Error parsing tenant_id for order ID: {}", header.getOrderId());
                            header.setTenantId(1);
                        }
                        
                        // Handle timestamps
                        try {
                            Timestamp creationDate = resultSet.getTimestamp("creation_date");
                            if (creationDate != null) {
                                header.setCreationDate(creationDate);
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing creation_date for order ID: {}", header.getOrderId());
                        }
                        
                        try {
                            Timestamp lastUpdateDate = resultSet.getTimestamp("last_update_date");
                            if (lastUpdateDate != null) {
                                header.setLastUpdateDate(lastUpdateDate);
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing last_update_date for order ID: {}", header.getOrderId());
                        }
                        
                        // Handle created_by and last_updated_by
                        try {
                            header.setCreatedBy(resultSet.getLong("created_by"));
                        } catch (Exception e) {
                            LOG.warn("Error parsing created_by for order ID: {}", header.getOrderId());
                        }
                        
                        try {
                            header.setLastUpdatedBy(resultSet.getLong("last_updated_by"));
                        } catch (Exception e) {
                            LOG.warn("Error parsing last_updated_by for order ID: {}", header.getOrderId());
                        }
                        
                        // Handle attribute fields
                        header.setAttribute1V(resultSet.getString("attribute1_v"));
                        header.setAttribute2V(resultSet.getString("attribute2_v"));
                        header.setAttribute3V(resultSet.getString("attribute3_v"));
                        header.setAttribute4V(resultSet.getString("attribute4_v"));
                        header.setAttribute5V(resultSet.getString("attribute5_v"));
                        
                        headers.add(header);
                    }
                    
                    LOG.info("Found {} headers matching plant location: {}", headers.size(), plantLocation);
                    return ResponseEntity.ok(headers);
                }
            }
        } catch (Exception e) {
            LOG.error("Error searching bits headers by plant location", e);
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping(value = SEARCH_BY_DEPARTMENT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BitsHeaderAll>> searchByDepartment(@RequestParam String department) {
        LOG.info("Searching bits headers by department: {}", department);
        List<BitsHeaderAll> headers = new ArrayList<>();
        
        try (Connection connection = dataSource.getConnection()) {
            String sql = """
                SELECT order_id, work_order, plant_location, department, work_location,
                       work_order_date, completion_date, ld_applicable, tenant_id,
                       scrap_allowance_visible_percent, scrap_allowance_invisible_percent, material_issue_type,
                       creation_date, created_by, last_update_date, last_updated_by,
                       attribute1_v, attribute2_v, attribute3_v, attribute4_v, attribute5_v
                FROM bits_po_entry_header 
                WHERE department ILIKE ?
                ORDER BY order_id DESC
                """;
            
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, "%" + department + "%");
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        BitsHeaderAll header = new BitsHeaderAll();
                        
                        // Set basic fields
                        header.setOrderId(resultSet.getLong("order_id"));
                        header.setWorkOrder(resultSet.getString("work_order"));
                        header.setPlantLocation(resultSet.getString("plant_location"));
                        header.setDepartment(resultSet.getString("department"));
                        header.setWorkLocation(resultSet.getString("work_location"));
                        
                        // Handle dates safely
                        try {
                            if (resultSet.getDate("work_order_date") != null) {
                                header.setWorkOrderDate(resultSet.getDate("work_order_date").toLocalDate());
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing work_order_date for order ID: {}", header.getOrderId());
                        }
                        
                        try {
                            if (resultSet.getDate("completion_date") != null) {
                                header.setCompletionDate(resultSet.getDate("completion_date").toLocalDate());
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing completion_date for order ID: {}", header.getOrderId());
                        }
                        
                        // Handle boolean
                        header.setLdApplicable(resultSet.getBoolean("ld_applicable"));
                        
                        // Handle new fields
                        header.setScrapAllowanceVisiblePercent(resultSet.getString("scrap_allowance_visible_percent"));
                        header.setScrapAllowanceInvisiblePercent(resultSet.getString("scrap_allowance_invisible_percent"));
                        header.setMaterialIssueType(resultSet.getString("material_issue_type"));
                        
                        // Handle tenant ID
                        try {
                            header.setTenantId(resultSet.getInt("tenant_id"));
                        } catch (Exception e) {
                            LOG.warn("Error parsing tenant_id for order ID: {}", header.getOrderId());
                            header.setTenantId(1);
                        }
                        
                        // Handle timestamps
                        try {
                            Timestamp creationDate = resultSet.getTimestamp("creation_date");
                            if (creationDate != null) {
                                header.setCreationDate(creationDate);
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing creation_date for order ID: {}", header.getOrderId());
                        }
                        
                        try {
                            Timestamp lastUpdateDate = resultSet.getTimestamp("last_update_date");
                            if (lastUpdateDate != null) {
                                header.setLastUpdateDate(lastUpdateDate);
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing last_update_date for order ID: {}", header.getOrderId());
                        }
                        
                        // Handle created_by and last_updated_by
                        try {
                            header.setCreatedBy(resultSet.getLong("created_by"));
                        } catch (Exception e) {
                            LOG.warn("Error parsing created_by for order ID: {}", header.getOrderId());
                        }
                        
                        try {
                            header.setLastUpdatedBy(resultSet.getLong("last_updated_by"));
                        } catch (Exception e) {
                            LOG.warn("Error parsing last_updated_by for order ID: {}", header.getOrderId());
                        }
                        
                        // Handle attribute fields
                        header.setAttribute1V(resultSet.getString("attribute1_v"));
                        header.setAttribute2V(resultSet.getString("attribute2_v"));
                        header.setAttribute3V(resultSet.getString("attribute3_v"));
                        header.setAttribute4V(resultSet.getString("attribute4_v"));
                        header.setAttribute5V(resultSet.getString("attribute5_v"));
                        
                        headers.add(header);
                    }
                    
                    LOG.info("Found {} headers matching department: {}", headers.size(), department);
                    return ResponseEntity.ok(headers);
                }
            }
        } catch (Exception e) {
            LOG.error("Error searching bits headers by department", e);
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping(value = SEARCH_BY_WORKLOCATION, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BitsHeaderAll>> searchByWorkLocation(@RequestParam String workLocation) {
        LOG.info("Searching bits headers by work location: {}", workLocation);
        List<BitsHeaderAll> headers = new ArrayList<>();
        
        try (Connection connection = dataSource.getConnection()) {
            String sql = """
                SELECT order_id, work_order, plant_location, department, work_location,
                       work_order_date, completion_date, ld_applicable, tenant_id,
                       scrap_allowance_visible_percent, scrap_allowance_invisible_percent, material_issue_type,
                       creation_date, created_by, last_update_date, last_updated_by,
                       attribute1_v, attribute2_v, attribute3_v, attribute4_v, attribute5_v
                FROM bits_po_entry_header 
                WHERE work_location ILIKE ?
                ORDER BY order_id DESC
                """;
            
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, "%" + workLocation + "%");
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        BitsHeaderAll header = new BitsHeaderAll();
                        
                        // Set basic fields
                        header.setOrderId(resultSet.getLong("order_id"));
                        header.setWorkOrder(resultSet.getString("work_order"));
                        header.setPlantLocation(resultSet.getString("plant_location"));
                        header.setDepartment(resultSet.getString("department"));
                        header.setWorkLocation(resultSet.getString("work_location"));
                        
                        // Handle dates safely
                        try {
                            if (resultSet.getDate("work_order_date") != null) {
                                header.setWorkOrderDate(resultSet.getDate("work_order_date").toLocalDate());
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing work_order_date for order ID: {}", header.getOrderId());
                        }
                        
                        try {
                            if (resultSet.getDate("completion_date") != null) {
                                header.setCompletionDate(resultSet.getDate("completion_date").toLocalDate());
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing completion_date for order ID: {}", header.getOrderId());
                        }
                        
                        // Handle boolean
                        header.setLdApplicable(resultSet.getBoolean("ld_applicable"));
                        
                        // Handle new fields
                        header.setScrapAllowanceVisiblePercent(resultSet.getString("scrap_allowance_visible_percent"));
                        header.setScrapAllowanceInvisiblePercent(resultSet.getString("scrap_allowance_invisible_percent"));
                        header.setMaterialIssueType(resultSet.getString("material_issue_type"));
                        
                        // Handle tenant ID
                        try {
                            header.setTenantId(resultSet.getInt("tenant_id"));
                        } catch (Exception e) {
                            LOG.warn("Error parsing tenant_id for order ID: {}", header.getOrderId());
                            header.setTenantId(1);
                        }
                        
                        // Handle timestamps
                        try {
                            Timestamp creationDate = resultSet.getTimestamp("creation_date");
                            if (creationDate != null) {
                                header.setCreationDate(creationDate);
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing creation_date for order ID: {}", header.getOrderId());
                        }
                        
                        try {
                            Timestamp lastUpdateDate = resultSet.getTimestamp("last_update_date");
                            if (lastUpdateDate != null) {
                                header.setLastUpdateDate(lastUpdateDate);
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing last_update_date for order ID: {}", header.getOrderId());
                        }
                        
                        // Handle created_by and last_updated_by
                        try {
                            header.setCreatedBy(resultSet.getLong("created_by"));
                        } catch (Exception e) {
                            LOG.warn("Error parsing created_by for order ID: {}", header.getOrderId());
                        }
                        
                        try {
                            header.setLastUpdatedBy(resultSet.getLong("last_updated_by"));
                        } catch (Exception e) {
                            LOG.warn("Error parsing last_updated_by for order ID: {}", header.getOrderId());
                        }
                        
                        // Handle attribute fields
                        header.setAttribute1V(resultSet.getString("attribute1_v"));
                        header.setAttribute2V(resultSet.getString("attribute2_v"));
                        header.setAttribute3V(resultSet.getString("attribute3_v"));
                        header.setAttribute4V(resultSet.getString("attribute4_v"));
                        header.setAttribute5V(resultSet.getString("attribute5_v"));
                        
                        headers.add(header);
                    }
                    
                    LOG.info("Found {} headers matching work location: {}", headers.size(), workLocation);
                    return ResponseEntity.ok(headers);
                }
            }
        } catch (Exception e) {
            LOG.error("Error searching bits headers by work location", e);
            return ResponseEntity.ok(new ArrayList<>());
        }
    }
    
    // ============ CREATE, UPDATE, DELETE OPERATIONS ============
    
    // This is the endpoint that's failing with 415 error - implementing with direct JSON handling
    @PostMapping(value = CREATE_HEADER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createHeader(@RequestBody Map<String, Object> headerData) {
        LOG.info("Creating a new bits header with data: {}", headerData);
        
        try (Connection connection = dataSource.getConnection()) {
            // Extract values from the request body
            String workOrder = headerData.get("workOrder") != null ? headerData.get("workOrder").toString() : null;
            String plantLocation = headerData.get("plantLocation") != null ? headerData.get("plantLocation").toString() : null;
            String department = headerData.get("department") != null ? headerData.get("department").toString() : null;
            String workLocation = headerData.get("workLocation") != null ? headerData.get("workLocation").toString() : null;
            
            // ADDED: Extract new field values
            String scrapAllowanceVisiblePercent = headerData.get("scrapAllowanceVisiblePercent") != null ? 
                    headerData.get("scrapAllowanceVisiblePercent").toString() : null;
            String scrapAllowanceInvisiblePercent = headerData.get("scrapAllowanceInvisiblePercent") != null ? 
                    headerData.get("scrapAllowanceInvisiblePercent").toString() : null;
            String materialIssueType = headerData.get("materialIssueType") != null ? 
                    headerData.get("materialIssueType").toString() : null;
            
            // Parse dates
            LocalDate workOrderDate = null;
            if (headerData.get("workOrderDate") != null && !headerData.get("workOrderDate").toString().isEmpty()) {
                try {
                    workOrderDate = LocalDate.parse(headerData.get("workOrderDate").toString());
                } catch (Exception e) {
                    LOG.warn("Error parsing workOrderDate: {}", headerData.get("workOrderDate"));
                }
            }
            
            LocalDate completionDate = null;
            if (headerData.get("completionDate") != null && !headerData.get("completionDate").toString().isEmpty()) {
                try {
                    completionDate = LocalDate.parse(headerData.get("completionDate").toString());
                } catch (Exception e) {
                    LOG.warn("Error parsing completionDate: {}", headerData.get("completionDate"));
                }
            }
            
            // Parse boolean
            Boolean ldApplicable = headerData.get("ldApplicable") != null ? 
                    Boolean.valueOf(headerData.get("ldApplicable").toString()) : false;
            
            // Insert into database
            // UPDATED: Added new columns to the SQL statement
            String sql = """
                INSERT INTO bits_po_entry_header (
                    work_order, plant_location, department, work_location,
                    work_order_date, completion_date, ld_applicable,
                    scrap_allowance_visible_percent, scrap_allowance_invisible_percent, material_issue_type,
                    tenant_id, creation_date, created_by, last_update_date, last_updated_by
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)
                RETURNING order_id
                """;
            
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, workOrder);
                statement.setString(2, plantLocation);
                statement.setString(3, department);
                statement.setString(4, workLocation);
                
                // Set dates
                if (workOrderDate != null) {
                    statement.setObject(5, workOrderDate);
                } else {
                    statement.setNull(5, java.sql.Types.DATE);
                }
                
                if (completionDate != null) {
                    statement.setObject(6, completionDate);
                } else {
                    statement.setNull(6, java.sql.Types.DATE);
                }
                
                // Set boolean
                statement.setBoolean(7, ldApplicable);
                
                // ADDED: Set new fields
                statement.setString(8, scrapAllowanceVisiblePercent);
                statement.setString(9, scrapAllowanceInvisiblePercent);
                statement.setString(10, materialIssueType);
                
                // Set default values
                statement.setInt(11, 1); // Default tenant ID
                statement.setLong(12, 1L); // Default created_by
                statement.setLong(13, 1L); // Default last_updated_by
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Long orderId = resultSet.getLong("order_id");
                        
                        // Create response object
                        Map<String, Object> response = new HashMap<>();
                        response.put("orderId", orderId);
                        response.put("workOrder", workOrder);
                        response.put("plantLocation", plantLocation);
                        response.put("department", department);
                        response.put("workLocation", workLocation);
                        response.put("workOrderDate", workOrderDate != null ? workOrderDate.toString() : null);
                        response.put("completionDate", completionDate != null ? completionDate.toString() : null);
                        response.put("ldApplicable", ldApplicable);
                        
                        // ADDED: Include new fields in response
                        response.put("scrapAllowanceVisiblePercent", scrapAllowanceVisiblePercent);
                        response.put("scrapAllowanceInvisiblePercent", scrapAllowanceInvisiblePercent);
                        response.put("materialIssueType", materialIssueType);
                        
                        LOG.info("Successfully created bits header with ID: {}", orderId);
                        return ResponseEntity.status(201).body(response);
                    } else {
                        LOG.error("Failed to create bits header - no ID returned");
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create header");
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Error creating bits header", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating header: " + e.getMessage());
        }
    }
    
    @PutMapping(value = UPDATE_HEADER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateHeader(@RequestParam Long id, @RequestBody Map<String, Object> headerData) {
        LOG.info("Updating bits header with ID: {}", id);
        
        try (Connection connection = dataSource.getConnection()) {
            // First check if the header exists
            String checkSql = "SELECT order_id FROM bits_po_entry_header WHERE order_id = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
                checkStatement.setLong(1, id);
                try (ResultSet checkResult = checkStatement.executeQuery()) {
                    if (!checkResult.next()) {
                        LOG.warn("Bits header not found with ID: {}", id);
                        return ResponseEntity.notFound().build();
                    }
                }
            }
            
            // Extract values from the request body
            String workOrder = headerData.get("workOrder") != null ? headerData.get("workOrder").toString() : null;
            String plantLocation = headerData.get("plantLocation") != null ? headerData.get("plantLocation").toString() : null;
            String department = headerData.get("department") != null ? headerData.get("department").toString() : null;
            String workLocation = headerData.get("workLocation") != null ? headerData.get("workLocation").toString() : null;
            
            // ADDED: Extract new field values
            String scrapAllowanceVisiblePercent = headerData.get("scrapAllowanceVisiblePercent") != null ? 
                    headerData.get("scrapAllowanceVisiblePercent").toString() : null;
            String scrapAllowanceInvisiblePercent = headerData.get("scrapAllowanceInvisiblePercent") != null ? 
                    headerData.get("scrapAllowanceInvisiblePercent").toString() : null;
            String materialIssueType = headerData.get("materialIssueType") != null ? 
                    headerData.get("materialIssueType").toString() : null;
            
            // Parse dates
            LocalDate workOrderDate = null;
            if (headerData.get("workOrderDate") != null && !headerData.get("workOrderDate").toString().isEmpty()) {
                try {
                    workOrderDate = LocalDate.parse(headerData.get("workOrderDate").toString());
                } catch (Exception e) {
                    LOG.warn("Error parsing workOrderDate: {}", headerData.get("workOrderDate"));
                }
            }
            
            LocalDate completionDate = null;
            if (headerData.get("completionDate") != null && !headerData.get("completionDate").toString().isEmpty()) {
                try {
                    completionDate = LocalDate.parse(headerData.get("completionDate").toString());
                } catch (Exception e) {
                    LOG.warn("Error parsing completionDate: {}", headerData.get("completionDate"));
                }
            }
            
            // Parse boolean
            Boolean ldApplicable = headerData.get("ldApplicable") != null ? 
                    Boolean.valueOf(headerData.get("ldApplicable").toString()) : false;
            
            // Update the header
            // UPDATED: Added new columns to the SQL UPDATE
            String sql = """
                UPDATE bits_po_entry_header SET
                    work_order = ?,
                    plant_location = ?,
                    department = ?,
                    work_location = ?,
                    work_order_date = ?,
                    completion_date = ?,
                    ld_applicable = ?,
                    scrap_allowance_visible_percent = ?,
                    scrap_allowance_invisible_percent = ?,
                    material_issue_type = ?,
                    last_update_date = CURRENT_TIMESTAMP,
                    last_updated_by = ?
                WHERE order_id = ?
                """;
            
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, workOrder);
                statement.setString(2, plantLocation);
                statement.setString(3, department);
                statement.setString(4, workLocation);
                
                // Set dates
                if (workOrderDate != null) {
                    statement.setObject(5, workOrderDate);
                } else {
                    statement.setNull(5, java.sql.Types.DATE);
                }
                
                if (completionDate != null) {
                    statement.setObject(6, completionDate);
                } else {
                    statement.setNull(6, java.sql.Types.DATE);
                }
                
                // Set boolean and default values
                statement.setBoolean(7, ldApplicable);
                
                // ADDED: Set new fields
                statement.setString(8, scrapAllowanceVisiblePercent);
                statement.setString(9, scrapAllowanceInvisiblePercent);
                statement.setString(10, materialIssueType);
                
                statement.setLong(11, 1L); // Default last_updated_by
                
                // Set ID for WHERE clause
                statement.setLong(12, id);
                
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    LOG.info("Successfully updated bits header with ID: {}", id);
                    
                    // Create response object
                    Map<String, Object> response = new HashMap<>();
                    response.put("orderId", id);
                    response.put("workOrder", workOrder);
                    response.put("plantLocation", plantLocation);
                    response.put("department", department);
                    response.put("workLocation", workLocation);
                    response.put("workOrderDate", workOrderDate != null ? workOrderDate.toString() : null);
                    response.put("completionDate", completionDate != null ? completionDate.toString() : null);
                    response.put("ldApplicable", ldApplicable);
                    
                    // ADDED: Include new fields in response
                    response.put("scrapAllowanceVisiblePercent", scrapAllowanceVisiblePercent);
                    response.put("scrapAllowanceInvisiblePercent", scrapAllowanceInvisiblePercent);
                    response.put("materialIssueType", materialIssueType);
                    
                    return ResponseEntity.ok(response);
                } else {
                    LOG.error("Failed to update bits header with ID: {}", id);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update header");
                }
            }
        } catch (Exception e) {
            LOG.error("Error updating bits header with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating header: " + e.getMessage());
        }
    }
    
    @DeleteMapping(value = DELETE_HEADER)
    public ResponseEntity<?> deleteHeader(@RequestParam Long id) {
        LOG.info("Deleting bits header with ID: {}", id);
        
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM bits_po_entry_header WHERE order_id = ?";
            
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    LOG.info("Successfully deleted bits header with ID: {}", id);
                    return ResponseEntity.noContent().build();
                } else {
                    LOG.warn("Bits header not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                }
            }
        } catch (Exception e) {
            LOG.error("Error deleting bits header with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting header: " + e.getMessage());
        }
    }
    
    @GetMapping(value = GET_HEADER_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getHeaderById(@RequestParam Long id) {
        LOG.info("Fetching bits header by ID: {}", id);
        
        try (Connection connection = dataSource.getConnection()) {
            // UPDATED: Added new columns to the query
            String sql = """
                SELECT order_id, work_order, plant_location, department, work_location,
                       work_order_date, completion_date, ld_applicable,
                       scrap_allowance_visible_percent, scrap_allowance_invisible_percent, material_issue_type,
                       tenant_id, creation_date, created_by, last_update_date, last_updated_by,
                       attribute1_v, attribute2_v, attribute3_v, attribute4_v, attribute5_v
                FROM bits_po_entry_header 
                WHERE order_id = ?
                """;
            
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        BitsHeaderAll header = new BitsHeaderAll();
                        
                        // Set basic fields
                        header.setOrderId(resultSet.getLong("order_id"));
                        header.setWorkOrder(resultSet.getString("work_order"));
                        header.setPlantLocation(resultSet.getString("plant_location"));
                        header.setDepartment(resultSet.getString("department"));
                        header.setWorkLocation(resultSet.getString("work_location"));
                        
                        // Handle dates safely
                        try {
                            if (resultSet.getDate("work_order_date") != null) {
                                header.setWorkOrderDate(resultSet.getDate("work_order_date").toLocalDate());
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing work_order_date for order ID: {}", id);
                        }
                        
                        try {
                            if (resultSet.getDate("completion_date") != null) {
                                header.setCompletionDate(resultSet.getDate("completion_date").toLocalDate());
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing completion_date for order ID: {}", id);
                        }
                        
                        // Handle boolean
                        header.setLdApplicable(resultSet.getBoolean("ld_applicable"));
                        
                        // ADDED: Handle new fields
                        header.setScrapAllowanceVisiblePercent(resultSet.getString("scrap_allowance_visible_percent"));
                        header.setScrapAllowanceInvisiblePercent(resultSet.getString("scrap_allowance_invisible_percent"));
                        header.setMaterialIssueType(resultSet.getString("material_issue_type"));
                        
                        // Handle tenant ID
                        try {
                            header.setTenantId(resultSet.getInt("tenant_id"));
                        } catch (Exception e) {
                            LOG.warn("Error parsing tenant_id for order ID: {}", id);
                            header.setTenantId(1); // Default tenant ID
                        }
                        
                        // Handle timestamps
                        try {
                            Timestamp creationDate = resultSet.getTimestamp("creation_date");
                            if (creationDate != null) {
                                header.setCreationDate(creationDate);
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing creation_date for order ID: {}", id);
                        }
                        
                        try {
                            Timestamp lastUpdateDate = resultSet.getTimestamp("last_update_date");
                            if (lastUpdateDate != null) {
                                header.setLastUpdateDate(lastUpdateDate);
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing last_update_date for order ID: {}", id);
                        }
                        
                        // Handle created_by and last_updated_by
                        try {
                            header.setCreatedBy(resultSet.getLong("created_by"));
                        } catch (Exception e) {
                            LOG.warn("Error parsing created_by for order ID: {}", id);
                        }
                        
                        try {
                            header.setLastUpdatedBy(resultSet.getLong("last_updated_by"));
                        } catch (Exception e) {
                            LOG.warn("Error parsing last_updated_by for order ID: {}", id);
                        }
                        
                        // Handle attribute fields
                        header.setAttribute1V(resultSet.getString("attribute1_v"));
                        header.setAttribute2V(resultSet.getString("attribute2_v"));
                        header.setAttribute3V(resultSet.getString("attribute3_v"));
                        header.setAttribute4V(resultSet.getString("attribute4_v"));
                        header.setAttribute5V(resultSet.getString("attribute5_v"));
                        
                        LOG.info("Successfully fetched bits header with ID: {}", id);
                        return ResponseEntity.ok(header);
                    } else {
                        LOG.warn("Bits header not found with ID: {}", id);
                        return ResponseEntity.notFound().build();
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Error fetching bits header by ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching header: " + e.getMessage());
        }
    }
    
    // ADDED: Implementing the endpoint for service lines by work order
    @GetMapping(value = GET_LINES_BY_WORKORDER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> getLinesByWorkOrder(@RequestParam String workOrder) {
        LOG.info("Fetching service lines by work order: {}", workOrder);
        List<Map<String, Object>> lines = new ArrayList<>();
        
        try (Connection connection = dataSource.getConnection()) {
            String sql = """
                SELECT line_id, line_number, ser_no, service_code, service_desc,
                       qty, uom, rate, amount, work_order_ref  
                FROM bits_po_entry_lines 
                WHERE work_order_ref = ?
                ORDER BY line_id
                """;
            
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, workOrder);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> line = new HashMap<>();
                        line.put("lineId", resultSet.getLong("line_id"));
                        line.put("lineNumber", resultSet.getObject("line_number"));
                        line.put("serNo", resultSet.getString("ser_no"));
                        line.put("serviceCode", resultSet.getString("service_code"));
                        line.put("serviceDesc", resultSet.getString("service_desc"));
                        line.put("qty", resultSet.getObject("qty"));
                        line.put("uom", resultSet.getString("uom"));
                        line.put("rate", resultSet.getObject("rate"));
                        line.put("amount", resultSet.getObject("amount"));
                        line.put("workOrderRef", resultSet.getString("work_order_ref"));
                        
                        lines.add(line);
                    }
                    
                    LOG.info("Found {} service lines for work order: {}", lines.size(), workOrder);
                    return ResponseEntity.ok(lines);
                }
            }
        } catch (Exception e) {
            LOG.error("Error fetching service lines by work order: {}", workOrder, e);
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    // ============ NEW ENDPOINTS FOR ORDER_ID MAPPING ============
    
    // NEW: Get work order by order ID
    @GetMapping(value = "/getworkorder/order/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getWorkOrderByOrderId(@PathVariable Long orderId) {
    try {
        LOG.info("Fetching work order by order ID: {}", orderId);
        
        try (Connection connection = dataSource.getConnection()) {
            String sql = """
                SELECT order_id, work_order, plant_location, department, work_location,
                       work_order_date, completion_date, ld_applicable,
                       scrap_allowance_visible_percent, scrap_allowance_invisible_percent, material_issue_type
                FROM bits_po_entry_header 
                WHERE order_id = ?
                """;
            
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, orderId);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("orderId", resultSet.getLong("order_id"));
                        response.put("workOrder", resultSet.getString("work_order"));
                        response.put("plantLocation", resultSet.getString("plant_location"));
                        response.put("department", resultSet.getString("department"));
                        response.put("workLocation", resultSet.getString("work_location"));
                        
                        // Handle dates safely
                        try {
                            if (resultSet.getDate("work_order_date") != null) {
                                response.put("workOrderDate", resultSet.getDate("work_order_date").toLocalDate().toString());
                            }
                        } catch (Exception dateEx) {
                            LOG.warn("Error parsing work_order_date for order ID {}", orderId);
                        }
                        
                        try {
                            if (resultSet.getDate("completion_date") != null) {
                                response.put("completionDate", resultSet.getDate("completion_date").toLocalDate().toString());
                            }
                        } catch (Exception dateEx) {
                            LOG.warn("Error parsing completion_date for order ID {}", orderId);
                        }
                        
                        response.put("ldApplicable", resultSet.getBoolean("ld_applicable"));
                        response.put("scrapAllowanceVisiblePercent", resultSet.getString("scrap_allowance_visible_percent"));
                        response.put("scrapAllowanceInvisiblePercent", resultSet.getString("scrap_allowance_invisible_percent"));
                        response.put("materialIssueType", resultSet.getString("material_issue_type"));
                        
                        return ResponseEntity.ok(response);
                    } else {
                        LOG.warn("No work order found with order ID: {}", orderId);
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Work order not found with order ID: " + orderId);
                    }
                }
            }
        }
        
    } catch (Exception e) {
        LOG.error("Error fetching work order by order ID: {}", orderId, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error fetching work order: " + e.getMessage());
    }
}

    // NEW: Check if work order exists
    @GetMapping(value = "/getworkorder/exists/{workOrder}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkWorkOrderExists(@PathVariable String workOrder) {
    try {
        LOG.info("Checking if work order exists: {}", workOrder);
        
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT COUNT(*) FROM bits_po_entry_header WHERE work_order = ?";
            
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, workOrder);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        boolean exists = resultSet.getInt(1) > 0;
                        
                        Map<String, Object> response = new HashMap<>();
                        response.put("workOrder", workOrder);
                        response.put("exists", exists);
                        
                        return ResponseEntity.ok(response);
                    }
                }
            }
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("workOrder", workOrder);
        response.put("exists", false);
        
        return ResponseEntity.ok(response);
        
    } catch (Exception e) {
        LOG.error("Error checking work order existence: {}", workOrder, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error checking work order: " + e.getMessage());
    }
}

    // NEW: Get all distinct work orders
    @GetMapping(value = "/getworkorder/distinct", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDistinctWorkOrders() {
    try {
        LOG.info("Fetching all distinct work orders");
        
        try (Connection connection = dataSource.getConnection()) {
            String sql = """
                SELECT DISTINCT work_order 
                FROM bits_po_entry_header 
                WHERE work_order IS NOT NULL 
                AND work_order != ''
                ORDER BY work_order
                """;
            
            List<String> distinctWorkOrders = new ArrayList<>();
            
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                
                while (resultSet.next()) {
                    String workOrder = resultSet.getString("work_order");
                    if (workOrder != null && !workOrder.trim().isEmpty()) {
                        distinctWorkOrders.add(workOrder.trim());
                    }
                }
            }
            
            LOG.info("Found {} distinct work orders", distinctWorkOrders.size());
            return ResponseEntity.ok(distinctWorkOrders);
        }
        
    } catch (Exception e) {
        LOG.error("Error fetching distinct work orders", e);
        return ResponseEntity.ok(new ArrayList<>());
    }
}

    // NEW: Validate order ID exists
    @GetMapping(value = "/validateOrderId/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validateOrderId(@PathVariable Long orderId) {
    try {
        LOG.info("Validating order ID: {}", orderId);
        
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT work_order FROM bits_po_entry_header WHERE order_id = ?";
            
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, orderId);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("orderId", orderId);
                    
                    if (resultSet.next()) {
                        response.put("exists", true);
                        response.put("workOrder", resultSet.getString("work_order"));
                    } else {
                        response.put("exists", false);
                    }
                    
                    return ResponseEntity.ok(response);
                }
            }
        }
        
    } catch (Exception e) {
        LOG.error("Error validating order ID: {}", orderId, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error validating order ID: " + e.getMessage());
    }
}

    // ============ ADDITIONAL UTILITY ENDPOINTS ============
    
    // Get headers by LD applicable status
    @GetMapping(value = "/searchBitsHeadersByLdApplicable/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BitsHeaderAll>> searchByLdApplicable(@RequestParam Boolean ldApplicable) {
    try {
        LOG.info("Searching bits headers by LD applicable: {}", ldApplicable);
        
        List<BitsHeaderAll> headers = new ArrayList<>();
        
        try (Connection connection = dataSource.getConnection()) {
            String sql = """
                SELECT order_id, work_order, plant_location, department, work_location,
                       work_order_date, completion_date, ld_applicable, tenant_id,
                       scrap_allowance_visible_percent, scrap_allowance_invisible_percent, material_issue_type,
                       creation_date, created_by, last_update_date, last_updated_by,
                       attribute1_v, attribute2_v, attribute3_v, attribute4_v, attribute5_v
                FROM bits_po_entry_header 
                WHERE ld_applicable = ?
                ORDER BY order_id DESC
                """;
            
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setBoolean(1, ldApplicable);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        BitsHeaderAll header = new BitsHeaderAll();
                        
                        header.setOrderId(resultSet.getLong("order_id"));
                        header.setWorkOrder(resultSet.getString("work_order"));
                        header.setPlantLocation(resultSet.getString("plant_location"));
                        header.setDepartment(resultSet.getString("department"));
                        header.setWorkLocation(resultSet.getString("work_location"));
                        header.setLdApplicable(resultSet.getBoolean("ld_applicable"));
                        
                        // Handle dates safely
                        try {
                            if (resultSet.getDate("work_order_date") != null) {
                                header.setWorkOrderDate(resultSet.getDate("work_order_date").toLocalDate());
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing work_order_date for order ID: {}", header.getOrderId());
                        }
                        
                        try {
                            if (resultSet.getDate("completion_date") != null) {
                                header.setCompletionDate(resultSet.getDate("completion_date").toLocalDate());
                            }
                        } catch (Exception e) {
                            LOG.warn("Error parsing completion_date for order ID: {}", header.getOrderId());
                        }
                        
                        headers.add(header);
                    }
                }
            }
        }
        
        LOG.info("Found {} headers with LD applicable: {}", headers.size(), ldApplicable);
        return ResponseEntity.ok(headers);
        
    } catch (Exception e) {
        LOG.error("Error searching bits headers by LD applicable", e);
        return ResponseEntity.ok(new ArrayList<>());
    }
}

    // Get statistics about headers
    @GetMapping(value = "/getBitsHeadersStats/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getHeadersStats() {
    try {
        LOG.info("Fetching bits headers statistics");
        
        try (Connection connection = dataSource.getConnection()) {
            Map<String, Object> stats = new HashMap<>();
            
            // Get total count
            String countSql = "SELECT COUNT(*) FROM bits_po_entry_header";
            try (PreparedStatement statement = connection.prepareStatement(countSql);
                 ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    stats.put("totalHeaders", resultSet.getInt(1));
                }
            }
            
            // Get distinct work orders count
            String distinctWorkOrdersSql = "SELECT COUNT(DISTINCT work_order) FROM bits_po_entry_header WHERE work_order IS NOT NULL";
            try (PreparedStatement statement = connection.prepareStatement(distinctWorkOrdersSql);
                 ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    stats.put("distinctWorkOrders", resultSet.getInt(1));
                }
            }
            
            // Get distinct plant locations count
            String distinctPlantLocationsSql = "SELECT COUNT(DISTINCT plant_location) FROM bits_po_entry_header WHERE plant_location IS NOT NULL";
            try (PreparedStatement statement = connection.prepareStatement(distinctPlantLocationsSql);
                 ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    stats.put("distinctPlantLocations", resultSet.getInt(1));
                }
            }
            
            // Get distinct departments count
            String distinctDepartmentsSql = "SELECT COUNT(DISTINCT department) FROM bits_po_entry_header WHERE department IS NOT NULL";
            try (PreparedStatement statement = connection.prepareStatement(distinctDepartmentsSql);
                 ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    stats.put("distinctDepartments", resultSet.getInt(1));
                }
            }
            
            // Get headers with LD applicable count
            String ldApplicableSql = "SELECT COUNT(*) FROM bits_po_entry_header WHERE ld_applicable = true";
            try (PreparedStatement statement = connection.prepareStatement(ldApplicableSql);
                 ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    stats.put("headersWithLdApplicable", resultSet.getInt(1));
                }
            }
            
            return ResponseEntity.ok(stats);
        }
        
    } catch (Exception e) {
        LOG.error("Error fetching bits headers statistics", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error fetching statistics: " + e.getMessage());
    }
}

    // Health check endpoint
    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> healthCheck() {
        try {
            Map<String, Object> health = new HashMap<>();
            health.put("status", "UP");
            health.put("timestamp", java.time.LocalDateTime.now().toString());
            health.put("service", "BitsHeaderController");
            
            // Test database connection
            try (Connection connection = dataSource.getConnection()) {
                health.put("database", "CONNECTED");
            } catch (Exception e) {
                health.put("database", "DISCONNECTED");
                health.put("databaseError", e.getMessage());
            }
            
            return ResponseEntity.ok(health);
            
        } catch (Exception e) {
            LOG.error("Error in health check", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Health check failed: " + e.getMessage());
        }
    }
    
    
 // NEW: Update RA.NO for drawing entry
    @PutMapping("/updateDrawingEntryRaNo/details")
    public ResponseEntity<?> updateDrawingEntryRaNo(@RequestParam Long lineId, @RequestParam String raNo) {
        LOG.info("Updating RA.NO for lineId: {} with value: {}", lineId, raNo);
        try {
            String sql = "UPDATE bits_drawing_entry SET ra_no = ?, last_update_date = CURRENT_TIMESTAMP, last_updated_by = 'system' WHERE line_id = ?";

            int rowsUpdated = jdbcTemplate.update(sql, raNo, lineId);

            if (rowsUpdated > 0) {
                LOG.info("Successfully updated RA.NO for lineId: {}", lineId);
                return ResponseEntity.ok(Map.of("success", true, "message", "RA.NO updated successfully"));
            } else {
                LOG.warn("No rows updated for lineId: {}", lineId);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOG.error("Error updating RA.NO for lineId: {}", lineId, e);
            return ResponseEntity.status(500).body("Error updating RA.NO: " + e.getMessage());
        }
    }
    
    // NEW: Get all distinct plant locations
    @GetMapping(value = "/getAllPlantLocations/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getAllPlantLocations() {
        List<String> plantLocations = new ArrayList<>();
        
        try (Connection connection = dataSource.getConnection()) {
            String sql = """
                SELECT DISTINCT plant_location 
                FROM bits_po_entry_header 
                WHERE plant_location IS NOT NULL 
                AND plant_location != ''
                AND plant_location != 'null'
                ORDER BY plant_location
                """;
            
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                
                while (resultSet.next()) {
                    String plantLocation = resultSet.getString("plant_location");
                    if (plantLocation != null && !plantLocation.trim().isEmpty()) {
                        plantLocations.add(plantLocation.trim());
                    }
                }
                
                LOG.info("Successfully fetched {} plant locations from bits_po_entry_header table", plantLocations.size());
                return ResponseEntity.ok(plantLocations);
                
            }
        } catch (Exception e) {
            LOG.error("Error fetching plant locations from bits_po_entry_header table", e);
            return ResponseEntity.ok(new ArrayList<>());
        }
    }
    
    
    
    
    
    
 // ADD THESE NEW ENDPOINTS TO YOUR EXISTING BitsHeaderController.java

 // NEW: Update customer for a work order
 @PutMapping(value = "/updateCustomer/details", produces = MediaType.APPLICATION_JSON_VALUE)
 public ResponseEntity<?> updateCustomerForWorkOrder(@RequestParam Long orderId, @RequestParam Long customerId) {
     LOG.info("Updating customer for order ID: {} with customer ID: {}", orderId, customerId);
     
     try (Connection connection = dataSource.getConnection()) {
         // First verify the order exists
         String checkSql = "SELECT work_order FROM bits_po_entry_header WHERE order_id = ?";
         try (PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
             checkStatement.setLong(1, orderId);
             try (ResultSet checkResult = checkStatement.executeQuery()) {
                 if (!checkResult.next()) {
                     return ResponseEntity.notFound().build();
                 }
             }
         }
         
         // Update the customer_id
         String updateSql = "UPDATE bits_po_entry_header SET customer_id = ?, last_update_date = CURRENT_TIMESTAMP, last_updated_by = 1 WHERE order_id = ?";
         try (PreparedStatement statement = connection.prepareStatement(updateSql)) {
             statement.setLong(1, customerId);
             statement.setLong(2, orderId);
             
             int rowsAffected = statement.executeUpdate();
             if (rowsAffected > 0) {
                 LOG.info("Successfully updated customer for order ID: {}", orderId);
                 
                 Map<String, Object> response = new HashMap<>();
                 response.put("success", true);
                 response.put("message", "Customer updated successfully");
                 response.put("orderId", orderId);
                 response.put("customerId", customerId);
                 
                 return ResponseEntity.ok(response);
             } else {
                 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                     .body("Failed to update customer");
             }
         }
     } catch (Exception e) {
         LOG.error("Error updating customer for order ID: {}", orderId, e);
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
             .body("Error updating customer: " + e.getMessage());
     }
 }

 // NEW: Get work order with customer details
 @GetMapping(value = "/getWorkOrderWithCustomer/details", produces = MediaType.APPLICATION_JSON_VALUE)
 public ResponseEntity<?> getWorkOrderWithCustomer(@RequestParam String workOrder, @RequestParam(required = false) Long customerId) {
     LOG.info("Fetching work order with customer details: {} and customer ID: {}", workOrder, customerId);
     
     try (Connection connection = dataSource.getConnection()) {
         String sql = """
             SELECT h.order_id, h.work_order, h.plant_location, h.department, h.work_location,
                    h.work_order_date, h.completion_date, h.ld_applicable, h.customer_id,
                    l.ledger_name, l.house_plot_no, l.street, l.village_post, l.mandal_taluq,
                    l.district, l.state, l.pin_code, l.contact_person_name, l.mobile_no, l.email
             FROM bits_po_entry_header h
             LEFT JOIN ledger_creation l ON h.customer_id = l.id
             WHERE h.work_order = ?
             """ + (customerId != null ? " AND h.customer_id = ?" : "");
         
         try (PreparedStatement statement = connection.prepareStatement(sql)) {
             statement.setString(1, workOrder);
             if (customerId != null) {
                 statement.setLong(2, customerId);
             }
             
             try (ResultSet resultSet = statement.executeQuery()) {
                 if (resultSet.next()) {
                     Map<String, Object> response = new HashMap<>();
                     
                     // Work order details
                     response.put("orderId", resultSet.getLong("order_id"));
                     response.put("workOrder", resultSet.getString("work_order"));
                     response.put("plantLocation", resultSet.getString("plant_location"));
                     response.put("department", resultSet.getString("department"));
                     response.put("workLocation", resultSet.getString("work_location"));
                     
                     // Handle dates
                     try {
                         if (resultSet.getDate("work_order_date") != null) {
                             response.put("workOrderDate", resultSet.getDate("work_order_date").toLocalDate().toString());
                         }
                     } catch (Exception e) {
                         LOG.warn("Error parsing work_order_date");
                     }
                     
                     try {
                         if (resultSet.getDate("completion_date") != null) {
                             response.put("completionDate", resultSet.getDate("completion_date").toLocalDate().toString());
                         }
                     } catch (Exception e) {
                         LOG.warn("Error parsing completion_date");
                     }
                     
                     response.put("ldApplicable", resultSet.getBoolean("ld_applicable"));
                     response.put("customerId", resultSet.getObject("customer_id"));
                     
                     // Customer details (if available)
                     if (resultSet.getObject("customer_id") != null) {
                         Map<String, Object> customerDetails = new HashMap<>();
                         customerDetails.put("ledgerName", resultSet.getString("ledger_name"));
                         customerDetails.put("housePlotNo", resultSet.getString("house_plot_no"));
                         customerDetails.put("street", resultSet.getString("street"));
                         customerDetails.put("villagePost", resultSet.getString("village_post"));
                         customerDetails.put("mandalTaluq", resultSet.getString("mandal_taluq"));
                         customerDetails.put("district", resultSet.getString("district"));
                         customerDetails.put("state", resultSet.getString("state"));
                         customerDetails.put("pinCode", resultSet.getString("pin_code"));
                         customerDetails.put("contactPersonName", resultSet.getString("contact_person_name"));
                         customerDetails.put("mobileNo", resultSet.getString("mobile_no"));
                         customerDetails.put("email", resultSet.getString("email"));
                         
                         // Format address
                         StringBuilder address = new StringBuilder();
                         if (resultSet.getString("house_plot_no") != null) address.append(resultSet.getString("house_plot_no")).append(", ");
                         if (resultSet.getString("street") != null) address.append(resultSet.getString("street")).append(", ");
                         if (resultSet.getString("village_post") != null) address.append(resultSet.getString("village_post")).append(", ");
                         if (resultSet.getString("mandal_taluq") != null) address.append(resultSet.getString("mandal_taluq")).append(", ");
                         if (resultSet.getString("district") != null) address.append(resultSet.getString("district")).append(", ");
                         if (resultSet.getString("state") != null) address.append(resultSet.getString("state")).append(" ");
                         if (resultSet.getString("pin_code") != null) address.append(resultSet.getString("pin_code"));
                         
                         customerDetails.put("fullAddress", address.toString().replaceAll(", $", ""));
                         response.put("customerDetails", customerDetails);
                     }
                     
                     return ResponseEntity.ok(response);
                 } else {
                     return ResponseEntity.notFound().build();
                 }
             }
         }
     } catch (Exception e) {
         LOG.error("Error fetching work order with customer details", e);
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
             .body("Error fetching data: " + e.getMessage());
     }
 }

 // NEW: Get all customers for dropdown
  
 
 
 

 
 ////
 @GetMapping("/getWorkOrderWithCustomer/details")
 public ResponseEntity<Map<String, Object>> getWorkOrderWithCustomerDetails(
         @RequestParam String workOrder,
         @RequestParam(required = false) Long customerId) {

     Map<String, Object> response = new HashMap<>();

     try {
         // Updated SQL query
         String sql = """
 SELECT h.order_id, h.work_order, h.plant_location, h.department, h.work_location,
        h.work_order_date, h.completion_date, h.ld_applicable, h.customer_id,
        l.ledger_name, l.house_plot_no, l.street, l.village_post, l.mandal_taluq,
        l.district, l.state, l.pin_code, l.gstin, l.pan
 FROM bits_po_entry_header h
 LEFT JOIN ledger_creation l ON h.customer_id = l.id
 WHERE h.work_order = ?
 """ + (customerId != null ? " AND h.customer_id = ?" : "");

         jdbcTemplate.query(sql, ps -> {
             ps.setString(1, workOrder);
             if (customerId != null) {
                 ps.setLong(2, customerId);
             }
         }, resultSet -> {
             response.put("orderId", resultSet.getLong("order_id"));
             response.put("workOrder", resultSet.getString("work_order"));
             response.put("plantLocation", resultSet.getString("plant_location"));
             response.put("department", resultSet.getString("department"));
             response.put("workLocation", resultSet.getString("work_location"));
             response.put("workOrderDate", resultSet.getDate("work_order_date"));
             response.put("completionDate", resultSet.getDate("completion_date"));
             response.put("ldApplicable", resultSet.getBoolean("ld_applicable"));
             response.put("customerId", resultSet.getObject("customer_id"));

             // Customer details (if available)
             if (resultSet.getObject("customer_id") != null) {
                 Map<String, Object> customerDetails = new HashMap<>();
                 customerDetails.put("ledgerName", resultSet.getString("ledger_name"));
                 customerDetails.put("gstin", resultSet.getString("gstin"));
                 customerDetails.put("pan", resultSet.getString("pan"));
                 customerDetails.put("housePlotNo", resultSet.getString("house_plot_no"));
                 customerDetails.put("street", resultSet.getString("street"));
                 customerDetails.put("villagePost", resultSet.getString("village_post"));
                 customerDetails.put("mandalTaluq", resultSet.getString("mandal_taluq"));
                 customerDetails.put("district", resultSet.getString("district"));
                 customerDetails.put("state", resultSet.getString("state"));
                 customerDetails.put("pinCode", resultSet.getString("pin_code"));

                 // Format address
                 StringBuilder address = new StringBuilder();
                 if (resultSet.getString("house_plot_no") != null) address.append(resultSet.getString("house_plot_no")).append(", ");
                 if (resultSet.getString("street") != null) address.append(resultSet.getString("street")).append(", ");
                 if (resultSet.getString("village_post") != null) address.append(resultSet.getString("village_post")).append(", ");
                 if (resultSet.getString("mandal_taluq") != null) address.append(resultSet.getString("mandal_taluq")).append(", ");
                 if (resultSet.getString("district") != null) address.append(resultSet.getString("district")).append(", ");
                 if (resultSet.getString("state") != null) address.append(resultSet.getString("state")).append(" ");
                 if (resultSet.getString("pin_code") != null) address.append(resultSet.getString("pin_code"));

                 customerDetails.put("fullAddress", address.toString().replaceAll(", $", ""));
                 response.put("customerDetails", customerDetails);
             }
         });

         if (response.isEmpty()) {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }

         return new ResponseEntity<>(response, HttpStatus.OK);

     } catch (Exception e) {
         e.printStackTrace();
         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
     }
 }

 @GetMapping("/getAllCustomers/details")
 public ResponseEntity<List<Map<String, Object>>> getAllCustomers() {
     try {
         String sql = """
             SELECT id, ledger_name, gstin, pan, state, district
             FROM ledger_creation 
             WHERE status = 'ACTIVE'
             ORDER BY ledger_name
             """;
         
         // Better approach: Use queryForList with RowMapper
         List<Map<String, Object>> customers = jdbcTemplate.query(sql, 
             (resultSet, rowNum) -> {
                 Map<String, Object> customer = new HashMap<>();
                 customer.put("id", resultSet.getLong("id"));
                 customer.put("ledgerName", resultSet.getString("ledger_name"));
                 customer.put("gstin", resultSet.getString("gstin"));
                 customer.put("pan", resultSet.getString("pan"));
                 customer.put("state", resultSet.getString("state"));
                 customer.put("district", resultSet.getString("district"));
                 return customer;
             });

         return ResponseEntity.ok(customers);

     } catch (Exception e) {
         LOG.error("Error fetching all customers", e);
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
     }
 }
}
 
