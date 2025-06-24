package com.bellaryinfotech.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class DatabaseHealthCheckController {
    
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseHealthCheckController.class);
    
    @Autowired
    private DataSource dataSource;
    
    /**
     * Check tenant_id data integrity
     */
    @GetMapping(value = "/db/check-tenant-id", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> checkTenantIdData() {
        StringBuilder result = new StringBuilder();
        result.append("=== Tenant ID Data Check ===\n");
        
        try (Connection conn = dataSource.getConnection()) {
            
            // Check bits_po_entry_header table
            result.append("\n--- bits_po_entry_header table ---\n");
            String headerSql = "SELECT tenant_id, COUNT(*) as count FROM bits_po_entry_header GROUP BY tenant_id ORDER BY tenant_id";
            
            try (PreparedStatement stmt = conn.prepareStatement(headerSql);
                 ResultSet rs = stmt.executeQuery()) {
                
                result.append("tenant_id values and counts:\n");
                while (rs.next()) {
                    String tenantId = rs.getString("tenant_id");
                    int count = rs.getInt("count");
                    result.append("- '").append(tenantId).append("': ").append(count).append(" records\n");
                }
            }
            
            // Check for problematic values
            String problemSql = "SELECT COUNT(*) as bad_count FROM bits_po_entry_header WHERE tenant_id IS NULL OR tenant_id::text = 'No tenant_id found in preferences' OR NOT (tenant_id::text ~ '^[0-9]+$')";
            
            try (PreparedStatement stmt = conn.prepareStatement(problemSql);
                 ResultSet rs = stmt.executeQuery()) {
                
                if (rs.next()) {
                    int badCount = rs.getInt("bad_count");
                    result.append("Problematic tenant_id records: ").append(badCount).append("\n");
                    
                    if (badCount > 0) {
                        result.append("\n⚠️  ISSUE FOUND: There are ").append(badCount).append(" records with invalid tenant_id values.\n");
                        result.append("Please run the SQL script to fix this issue.\n");
                    } else {
                        result.append("\n✅ All tenant_id values are valid.\n");
                    }
                }
            }
            
            // Check bits_po_entry_lines table
            result.append("\n--- bits_po_entry_lines table ---\n");
            String linesSql = "SELECT tenant_id, COUNT(*) as count FROM bits_po_entry_lines GROUP BY tenant_id ORDER BY tenant_id";
            
            try (PreparedStatement stmt = conn.prepareStatement(linesSql);
                 ResultSet rs = stmt.executeQuery()) {
                
                result.append("tenant_id values and counts:\n");
                while (rs.next()) {
                    String tenantId = rs.getString("tenant_id");
                    int count = rs.getInt("count");
                    result.append("- '").append(tenantId).append("': ").append(count).append(" records\n");
                }
            }
            
        } catch (SQLException e) {
            LOG.error("Error checking tenant_id data", e);
            result.append("Error checking database: ").append(e.getMessage()).append("\n");
        }
        
        return ResponseEntity.ok(result.toString());
    }
    
    /**
     * Fix tenant_id data issues
     */
    @PostMapping(value = "/db/fix-tenant-id", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> fixTenantIdData() {
        StringBuilder result = new StringBuilder();
        result.append("=== Fixing Tenant ID Data ===\n");
        
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            
            try {
                // Fix bits_po_entry_header table
                result.append("\n--- Fixing bits_po_entry_header table ---\n");
                String fixHeaderSql = "UPDATE bits_po_entry_header SET tenant_id = 1 WHERE tenant_id IS NULL OR tenant_id::text = 'No tenant_id found in preferences' OR NOT (tenant_id::text ~ '^[0-9]+$')";
                
                try (PreparedStatement stmt = conn.prepareStatement(fixHeaderSql)) {
                    int updatedRows = stmt.executeUpdate();
                    result.append("Updated ").append(updatedRows).append(" header records\n");
                }
                
                // Fix bits_po_entry_lines table
                result.append("\n--- Fixing bits_po_entry_lines table ---\n");
                String fixLinesSql = "UPDATE bits_po_entry_lines SET tenant_id = 1 WHERE tenant_id IS NULL OR tenant_id::text = 'No tenant_id found in preferences' OR NOT (tenant_id::text ~ '^[0-9]+$')";
                
                try (PreparedStatement stmt = conn.prepareStatement(fixLinesSql)) {
                    int updatedRows = stmt.executeUpdate();
                    result.append("Updated ").append(updatedRows).append(" lines records\n");
                }
                
                conn.commit();
                result.append("\n✅ Successfully fixed tenant_id data issues!\n");
                result.append("You can now try the XML export again.\n");
                
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
            
        } catch (SQLException e) {
            LOG.error("Error fixing tenant_id data", e);
            result.append("Error fixing database: ").append(e.getMessage()).append("\n");
        }
        
        return ResponseEntity.ok(result.toString());
    }
}
