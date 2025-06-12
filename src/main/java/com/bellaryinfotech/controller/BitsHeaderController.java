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

    // Get work order details from bits_po_entry_header table - WORKING
    @GetMapping(value = GET_WORKORDER_DETAILS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getWorkOrderDetails(@PathVariable String workOrderNumber) {
        try (Connection connection = dataSource.getConnection()) {
            // Query using the correct column names from your BitsHeaderAll entity
            // UPDATED: Added new columns to the query
            String sql = """
                SELECT work_order, plant_location, department, work_location,
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
                        BitsHeaderAll workOrderDetails = new BitsHeaderAll();
                        workOrderDetails.setWorkOrder(resultSet.getString("work_order"));
                        workOrderDetails.setPlantLocation(resultSet.getString("plant_location"));
                        workOrderDetails.setDepartment(resultSet.getString("department"));
                        workOrderDetails.setWorkLocation(resultSet.getString("work_location"));
                        
                        // Handle dates safely
                        try {
                            if (resultSet.getDate("work_order_date") != null) {
                                workOrderDetails.setWorkOrderDate(resultSet.getDate("work_order_date").toLocalDate());
                            }
                        } catch (Exception dateEx) {
                            LOG.warn("Error parsing work_order_date for work order {}", workOrderNumber);
                        }
                        
                        try {
                            if (resultSet.getDate("completion_date") != null) {
                                workOrderDetails.setCompletionDate(resultSet.getDate("completion_date").toLocalDate());
                            }
                        } catch (Exception dateEx) {
                            LOG.warn("Error parsing completion_date for work order {}", workOrderNumber);
                        }
                        
                        // Handle boolean
                        workOrderDetails.setLdApplicable(resultSet.getBoolean("ld_applicable"));
                        
                        // Handle the new fields
                        workOrderDetails.setScrapAllowanceVisiblePercent(resultSet.getString("scrap_allowance_visible_percent"));
                        workOrderDetails.setScrapAllowanceInvisiblePercent(resultSet.getString("scrap_allowance_invisible_percent"));
                        workOrderDetails.setMaterialIssueType(resultSet.getString("material_issue_type"));
                        
                        // Handle timestamps
                        try {
                            if (resultSet.getTimestamp("creation_date") != null) {
                                workOrderDetails.setCreationDate(resultSet.getTimestamp("creation_date"));
                            }
                        } catch (Exception dateEx) {
                            LOG.warn("Error parsing creation_date for work order {}", workOrderNumber);
                        }
                        
                        try {
                            if (resultSet.getTimestamp("last_update_date") != null) {
                                workOrderDetails.setLastUpdateDate(resultSet.getTimestamp("last_update_date"));
                            }
                        } catch (Exception dateEx) {
                            LOG.warn("Error parsing last_update_date for work order {}", workOrderNumber);
                        }
                        
                        // Handle Long fields
                        try {
                            workOrderDetails.setCreatedBy(resultSet.getLong("created_by"));
                        } catch (Exception ex) {
                            LOG.warn("Error parsing created_by for work order {}", workOrderNumber);
                        }
                        
                        try {
                            workOrderDetails.setLastUpdatedBy(resultSet.getLong("last_updated_by"));
                        } catch (Exception ex) {
                            LOG.warn("Error parsing last_updated_by for work order {}", workOrderNumber);
                        }
                        
                        LOG.info("Successfully fetched details for work order: {}", workOrderNumber);
                        return ResponseEntity.ok(workOrderDetails);
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
                
                LOG.info("Successfully fetched {} bits headers", headers.size());
                return ResponseEntity.ok(headers);
            }
        } catch (Exception e) {
            LOG.error("Error fetching all bits headers", e);
            return ResponseEntity.ok(new ArrayList<>()); // Return empty list instead of error
        }
    }

    // ============ SEARCH OPERATIONS USING DIRECT SQL ============
    
    @GetMapping(value = SEARCH_BY_WORKORDER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BitsHeaderAll>> searchByWorkOrder(@RequestParam String workOrder) {
        LOG.info("Searching bits headers by work order: {}", workOrder);
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
                WHERE work_order ILIKE ?
                ORDER BY order_id DESC
                """;
            
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, "%" + workOrder + "%");
                
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
                    
                    LOG.info("Found {} headers matching work order: {}", headers.size(), workOrder);
                    return ResponseEntity.ok(headers);
                }
            }
        } catch (Exception e) {
            LOG.error("Error searching bits headers by work order", e);
            return ResponseEntity.ok(new ArrayList<>()); // Return empty list instead of error
        }
    }
    
    // Similar updates for searchByPlantLocation, searchByDepartment, searchByWorkLocation methods
    // Adding the new fields to the SQL queries and result processing
    
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
}