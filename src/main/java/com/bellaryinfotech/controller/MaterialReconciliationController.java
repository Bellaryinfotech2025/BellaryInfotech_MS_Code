package com.bellaryinfotech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class MaterialReconciliationController {
    
    private static final Logger LOG = LoggerFactory.getLogger(MaterialReconciliationController.class);
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Get company names from vendor_profile table for contractor dropdown
     */
    @GetMapping("/getCompanyNames/details")
    public ResponseEntity<?> getCompanyNames() {
        try {
            LOG.info("Fetching company names from vendor_profile table");
            
            String sql = """
                SELECT DISTINCT company_name 
                FROM vendor_profile 
                WHERE status = 'ACTIVE' AND company_name IS NOT NULL AND company_name != ''
                ORDER BY company_name
                """;
            
            List<Map<String, Object>> companyNames = new ArrayList<>();
            
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> company = new HashMap<>();
                        company.put("companyName", resultSet.getString("company_name"));
                        companyNames.add(company);
                    }
                }
            }
            
            LOG.info("Found {} company names", companyNames.size());
            return ResponseEntity.ok(companyNames);
            
        } catch (Exception e) {
            LOG.error("Error fetching company names", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching company names: " + e.getMessage());
        }
    }
    
    /**
     * Get drawing numbers and mark numbers by order_id from bits_drawing_entry
     */
    @GetMapping("/getDrawingAndMarkByOrderId/details")
    public ResponseEntity<?> getDrawingAndMarkByOrderId(@RequestParam Long orderId) {
        try {
            LOG.info("Fetching drawing and mark numbers for order ID: {}", orderId);
            
            String sql = """
                SELECT DISTINCT drawing_no, mark_no, total_marked_weight
                FROM bits_drawing_entry 
                WHERE order_id = ? AND drawing_no IS NOT NULL AND mark_no IS NOT NULL
                ORDER BY drawing_no, mark_no
                """;
            
            List<Map<String, Object>> drawingData = new ArrayList<>();
            
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                
                statement.setLong(1, orderId);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> data = new HashMap<>();
                        data.put("drawingNo", resultSet.getString("drawing_no"));
                        data.put("markNo", resultSet.getString("mark_no"));
                        data.put("totalMarkedWeight", resultSet.getDouble("total_marked_weight"));
                        drawingData.add(data);
                    }
                }
            }
            
            LOG.info("Found {} drawing/mark entries for order ID: {}", drawingData.size(), orderId);
            return ResponseEntity.ok(drawingData);
            
        } catch (Exception e) {
            LOG.error("Error fetching drawing/mark data for order ID: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching drawing/mark data: " + e.getMessage());
        }
    }
    
    /**
     * Get total marked weight by drawing number and order_id
     */
    @GetMapping("/getTotalMarkedWeightByDrawing/details")
    public ResponseEntity<?> getTotalMarkedWeightByDrawing(
            @RequestParam Long orderId, 
            @RequestParam String drawingNo) {
        try {
            LOG.info("Fetching total marked weight for order ID: {} and drawing: {}", orderId, drawingNo);
            
            String sql = """
                SELECT SUM(total_marked_weight) as total_weight, COUNT(*) as row_count
                FROM bits_drawing_entry 
                WHERE order_id = ? AND drawing_no = ?
                """;
            
            Map<String, Object> result = new HashMap<>();
            
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                
                statement.setLong(1, orderId);
                statement.setString(2, drawingNo);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        result.put("totalWeight", resultSet.getDouble("total_weight"));
                        result.put("rowCount", resultSet.getInt("row_count"));
                    } else {
                        result.put("totalWeight", 0.0);
                        result.put("rowCount", 0);
                    }
                }
            }
            
            LOG.info("Total marked weight for drawing {}: {}", drawingNo, result.get("totalWeight"));
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            LOG.error("Error fetching total marked weight", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching total marked weight: " + e.getMessage());
        }
    }
    
    // Keep all existing methods from previous implementation
    /**
     * Get RA numbers by order_id from fabrication_drawing_entry
     */
    @GetMapping("/getRaNumbersByOrderId/details")
    public ResponseEntity<?> getRaNumbersByOrderId(@RequestParam Long orderId) {
        try {
            LOG.info("Fetching RA numbers for order ID: {}", orderId);
            
            String sql = """
                SELECT DISTINCT ra_no, ra_no_count 
                FROM fabrication_drawing_entry 
                WHERE order_id = ? AND ra_no IS NOT NULL AND ra_no != '' 
                ORDER BY ra_no_count, ra_no
                """;
            
            List<Map<String, Object>> raNumbers = new ArrayList<>();
            
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                
                statement.setLong(1, orderId);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> raData = new HashMap<>();
                        raData.put("raNo", resultSet.getString("ra_no"));
                        raData.put("raNoCount", resultSet.getInt("ra_no_count"));
                        raNumbers.add(raData);
                    }
                }
            }
            
            LOG.info("Found {} RA numbers for order ID: {}", raNumbers.size(), orderId);
            return ResponseEntity.ok(raNumbers);
            
        } catch (Exception e) {
            LOG.error("Error fetching RA numbers for order ID: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching RA numbers: " + e.getMessage());
        }
    }
    
    /**
     * Get fabrication entries by order_id and RA number
     */
    @GetMapping("/getFabricationEntriesByOrderIdAndRaNo/details")
    public ResponseEntity<?> getFabricationEntriesByOrderIdAndRaNo(
            @RequestParam Long orderId, 
            @RequestParam String raNo) {
        try {
            LOG.info("Fetching fabrication entries for order ID: {} and RA: {}", orderId, raNo);
            
            String sql = """
                SELECT id, work_order, building_name, drawing_no, mark_no, ra_no, order_id, 
                       line_id, session_code, session_name, marked_qty, total_marked_wgt, 
                       session_weight, width, length, item_qty, item_weight, total_item_weight,
                       cutting_stage, fit_up_stage, welding_stage, finishing_stage, 
                       created_by, last_updated_by, creation_date, last_update_date, ra_no_count
                FROM fabrication_drawing_entry 
                WHERE order_id = ? AND ra_no = ?
                ORDER BY session_name, creation_date
                """;
            
            List<Map<String, Object>> entries = new ArrayList<>();
            
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                
                statement.setLong(1, orderId);
                statement.setString(2, raNo);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> entry = new HashMap<>();
                        entry.put("id", resultSet.getLong("id"));
                        entry.put("workOrder", resultSet.getString("work_order"));
                        entry.put("buildingName", resultSet.getString("building_name"));
                        entry.put("drawingNo", resultSet.getString("drawing_no"));
                        entry.put("markNo", resultSet.getString("mark_no"));
                        entry.put("raNo", resultSet.getString("ra_no"));
                        entry.put("orderId", resultSet.getLong("order_id"));
                        entry.put("lineId", resultSet.getLong("line_id"));
                        entry.put("sessionCode", resultSet.getString("session_code"));
                        entry.put("sessionName", resultSet.getString("session_name"));
                        entry.put("markedQty", resultSet.getInt("marked_qty"));
                        entry.put("totalMarkedWgt", resultSet.getDouble("total_marked_wgt"));
                        entry.put("sessionWeight", resultSet.getDouble("session_weight"));
                        entry.put("width", resultSet.getDouble("width"));
                        entry.put("length", resultSet.getDouble("length"));
                        entry.put("itemQty", resultSet.getInt("item_qty"));
                        entry.put("itemWeight", resultSet.getDouble("item_weight"));
                        entry.put("totalItemWeight", resultSet.getDouble("total_item_weight"));
                        entry.put("cuttingStage", resultSet.getString("cutting_stage"));
                        entry.put("fitUpStage", resultSet.getString("fit_up_stage"));
                        entry.put("weldingStage", resultSet.getString("welding_stage"));
                        entry.put("finishingStage", resultSet.getString("finishing_stage"));
                        entry.put("createdBy", resultSet.getString("created_by"));
                        entry.put("lastUpdatedBy", resultSet.getString("last_updated_by"));
                        entry.put("creationDate", resultSet.getTimestamp("creation_date"));
                        entry.put("lastUpdateDate", resultSet.getTimestamp("last_update_date"));
                        entry.put("raNoCount", resultSet.getInt("ra_no_count"));
                        entries.add(entry);
                    }
                }
            }
            
            LOG.info("Found {} fabrication entries for order ID: {} and RA: {}", entries.size(), orderId, raNo);
            return ResponseEntity.ok(entries);
            
        } catch (Exception e) {
            LOG.error("Error fetching fabrication entries for order ID: {} and RA: {}", orderId, raNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching fabrication entries: " + e.getMessage());
        }
    }
    
    /**
     * Get raw material data by section name
     */
    @GetMapping("/getRawMaterialBySection/details")
    public ResponseEntity<?> getRawMaterialBySection(@RequestParam String section) {
        try {
            LOG.info("Fetching raw material data for section: {}", section);
            
            String sql = """
                SELECT id, work_order, order_id, section, material_code, width, length, 
                       qty, uom, total_weight, total_received, vehicle_number, document_no, 
                       document_date, received_date, created_by, created_date, 
                       last_updated_by, last_updated_date, tenant_id
                FROM raw_material_entry 
                WHERE section = ?
                ORDER BY created_date DESC
                """;
            
            List<Map<String, Object>> rawMaterials = new ArrayList<>();
            
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                
                statement.setString(1, section);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> material = new HashMap<>();
                        material.put("id", resultSet.getLong("id"));
                        material.put("workOrder", resultSet.getString("work_order"));
                        material.put("orderId", resultSet.getLong("order_id"));
                        material.put("section", resultSet.getString("section"));
                        material.put("materialCode", resultSet.getString("material_code"));
                        material.put("width", resultSet.getBigDecimal("width"));
                        material.put("length", resultSet.getBigDecimal("length"));
                        material.put("qty", resultSet.getBigDecimal("qty"));
                        material.put("uom", resultSet.getString("uom"));
                        material.put("totalWeight", resultSet.getBigDecimal("total_weight"));
                        material.put("totalReceived", resultSet.getBigDecimal("total_received"));
                        material.put("vehicleNumber", resultSet.getString("vehicle_number"));
                        material.put("documentNo", resultSet.getString("document_no"));
                        material.put("documentDate", resultSet.getDate("document_date"));
                        material.put("receivedDate", resultSet.getDate("received_date"));
                        material.put("createdBy", resultSet.getString("created_by"));
                        material.put("createdDate", resultSet.getTimestamp("created_date"));
                        material.put("lastUpdatedBy", resultSet.getString("last_updated_by"));
                        material.put("lastUpdatedDate", resultSet.getTimestamp("last_updated_date"));
                        material.put("tenantId", resultSet.getInt("tenant_id"));
                        rawMaterials.add(material);
                    }
                }
            }
            
            LOG.info("Found {} raw material entries for section: {}", rawMaterials.size(), section);
            return ResponseEntity.ok(rawMaterials);
            
        } catch (Exception e) {
            LOG.error("Error fetching raw material data for section: {}", section, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching raw material data: " + e.getMessage());
        }
    }
    
    /**
     * Get all fabrication entries by order_id (for session names)
     */
    @GetMapping("/getFabricationSessionsByOrderId/details")
    public ResponseEntity<?> getFabricationSessionsByOrderId(@RequestParam Long orderId) {
        try {
            LOG.info("Fetching fabrication sessions for order ID: {}", orderId);
            
            String sql = """
                SELECT DISTINCT session_name, session_code, session_weight
                FROM fabrication_drawing_entry 
                WHERE order_id = ? AND session_name IS NOT NULL AND session_name != ''
                ORDER BY session_name
                """;
            
            List<Map<String, Object>> sessions = new ArrayList<>();
            
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                
                statement.setLong(1, orderId);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> session = new HashMap<>();
                        session.put("sessionName", resultSet.getString("session_name"));
                        session.put("sessionCode", resultSet.getString("session_code"));
                        session.put("sessionWeight", resultSet.getDouble("session_weight"));
                        sessions.add(session);
                    }
                }
            }
            
            LOG.info("Found {} sessions for order ID: {}", sessions.size(), orderId);
            return ResponseEntity.ok(sessions);
            
        } catch (Exception e) {
            LOG.error("Error fetching sessions for order ID: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching sessions: " + e.getMessage());
        }
    }
    
    /**
     * Get consumption data for previous RA numbers
     */
    @GetMapping("/getPreviousConsumptionData/details")
    public ResponseEntity<?> getPreviousConsumptionData(
            @RequestParam Long orderId, 
            @RequestParam String sessionName,
            @RequestParam int currentRaCount) {
        try {
            LOG.info("Fetching previous consumption for order ID: {}, session: {}, current RA count: {}", 
                    orderId, sessionName, currentRaCount);
            
            double totalPreviousConsumption = 0.0;
            
            // Get consumption from all previous RA numbers
            for (int i = 1; i < currentRaCount; i++) {
                String sql = """
                    SELECT SUM(total_marked_wgt) as total_consumption
                    FROM fabrication_drawing_entry 
                    WHERE order_id = ? AND session_name = ? AND ra_no_count = ?
                    """;
                
                try (Connection connection = dataSource.getConnection();
                     PreparedStatement statement = connection.prepareStatement(sql)) {
                    
                    statement.setLong(1, orderId);
                    statement.setString(2, sessionName);
                    statement.setInt(3, i);
                    
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            double consumption = resultSet.getDouble("total_consumption");
                            totalPreviousConsumption += consumption;
                        }
                    }
                }
            }
            
            Map<String, Object> result = new HashMap<>();
            result.put("previousConsumption", totalPreviousConsumption);
            
            LOG.info("Previous consumption for order ID: {}, session: {} = {}", 
                    orderId, sessionName, totalPreviousConsumption);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            LOG.error("Error fetching previous consumption", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching previous consumption: " + e.getMessage());
        }
    }
}
