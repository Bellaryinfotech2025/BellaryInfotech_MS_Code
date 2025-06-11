package com.bellaryinfotech.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/V2.0/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private static final Logger LOG = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Get distinct work order numbers (attribute_1_v)
     */
    @RequestMapping(value = "/getDistinctWorkOrderNumbers", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDistinctWorkOrderNumbers() {
        try {
            LOG.info("Fetching distinct work order numbers");
            String sql = "SELECT DISTINCT attribute_1_v FROM bits_drawing_entry WHERE attribute_1_v IS NOT NULL AND attribute_1_v != '' ORDER BY attribute_1_v";
            List<String> workOrderNumbers = jdbcTemplate.queryForList(sql, String.class);
            LOG.info("Found {} distinct work order numbers", workOrderNumbers.size());
            return ResponseEntity.ok(workOrderNumbers);
        } catch (Exception e) {
            LOG.error("Error fetching work order numbers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get work order numbers: " + e.getMessage());
        }
    }

    /**
     * Get distinct building names (attribute_2_v)
     */
    @RequestMapping(value = "/getDistinctBuildingNames", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDistinctBuildingNames() {
        try {
            LOG.info("Fetching distinct building names");
            String sql = "SELECT DISTINCT attribute_2_v FROM bits_drawing_entry WHERE attribute_2_v IS NOT NULL AND attribute_2_v != '' ORDER BY attribute_2_v";
            List<String> buildingNames = jdbcTemplate.queryForList(sql, String.class);
            LOG.info("Found {} distinct building names", buildingNames.size());
            return ResponseEntity.ok(buildingNames);
        } catch (Exception e) {
            LOG.error("Error fetching building names", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get building names: " + e.getMessage());
        }
    }

    /**
     * Get section name and total weight data filtered by selected criteria
     */
    @RequestMapping(value = "/getMaterialRequirementData", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getMaterialRequirementData(
            @RequestParam(required = false) List<String> workOrders,
            @RequestParam(required = false) List<String> buildingNames,
            @RequestParam(required = false) List<String> drawingNumbers,
            @RequestParam(required = false) List<String> markNumbers) {
        
        try {
            LOG.info("Fetching material requirement data with filters");
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT session_name, SUM(COALESCE(item_weight, 0)) as total_weight ");
            sql.append("FROM bits_drawing_entry WHERE 1=1 ");
            
            List<Object> params = new ArrayList<>();
            
            if (workOrders != null && !workOrders.isEmpty()) {
                sql.append("AND attribute_1_v IN (");
                for (int i = 0; i < workOrders.size(); i++) {
                    sql.append("?");
                    if (i < workOrders.size() - 1) sql.append(",");
                    params.add(workOrders.get(i));
                }
                sql.append(") ");
            }
            
            if (buildingNames != null && !buildingNames.isEmpty()) {
                sql.append("AND attribute_2_v IN (");
                for (int i = 0; i < buildingNames.size(); i++) {
                    sql.append("?");
                    if (i < buildingNames.size() - 1) sql.append(",");
                    params.add(buildingNames.get(i));
                }
                sql.append(") ");
            }
            
            if (drawingNumbers != null && !drawingNumbers.isEmpty()) {
                sql.append("AND drawing_no IN (");
                for (int i = 0; i < drawingNumbers.size(); i++) {
                    sql.append("?");
                    if (i < drawingNumbers.size() - 1) sql.append(",");
                    params.add(drawingNumbers.get(i));
                }
                sql.append(") ");
            }
            
            if (markNumbers != null && !markNumbers.isEmpty()) {
                sql.append("AND mark_no IN (");
                for (int i = 0; i < markNumbers.size(); i++) {
                    sql.append("?");
                    if (i < markNumbers.size() - 1) sql.append(",");
                    params.add(markNumbers.get(i));
                }
                sql.append(") ");
            }
            
            sql.append("AND session_name IS NOT NULL ");
            sql.append("GROUP BY session_name ");
            sql.append("ORDER BY session_name");
            
            LOG.info("Executing SQL: {}", sql.toString());
            LOG.info("With parameters: {}", params);
            
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql.toString(), params.toArray());
            
            LOG.info("Found {} material requirement records", results.size());
            return ResponseEntity.ok(results);
            
        } catch (Exception e) {
            LOG.error("Error fetching material requirement data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get material requirement data: " + e.getMessage());
        }
    }

    /**
     * Health check endpoint
     */
    @RequestMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> healthCheck() {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "OK");
            response.put("message", "Report service is running");
            response.put("timestamp", System.currentTimeMillis());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOG.error("Health check failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Health check failed: " + e.getMessage());
        }
    }
}
