package com.bellaryinfotech.controller;

import com.bellaryinfotech.service.XmlExportService;
import com.bellaryinfotech.service.BitsHeaderService;
import com.bellaryinfotech.service.BitsLinesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class XmlExportController {
    
    private static final Logger LOG = LoggerFactory.getLogger(XmlExportController.class);
    
    @Autowired
    private XmlExportService xmlExportService;
    
    @Autowired
    private BitsHeaderService bitsHeaderService;
    
    @Autowired
    private BitsLinesService bitsLinesService;
    
    /**
     * TEST ENDPOINT - Check if controller is working
     */
    @GetMapping(value = "/export/test", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> testEndpoint() {
        LOG.info("Test endpoint called");
        return ResponseEntity.ok("XML Export Controller is working! Current time: " + LocalDateTime.now());
    }
    
    /**
     * DEBUG ENDPOINT - Check services and data availability
     */
    @GetMapping(value = "/export/debug", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> debugDataAvailability() {
        StringBuilder debug = new StringBuilder();
        debug.append("=== XML Export Debug Information ===\n");
        debug.append("Timestamp: ").append(LocalDateTime.now()).append("\n\n");
        
        try {
            // Check service injection
            debug.append("Service Injection Status:\n");
            debug.append("- xmlExportService: ").append(xmlExportService != null ? "✓ Injected" : "✗ NULL").append("\n");
            debug.append("- bitsHeaderService: ").append(bitsHeaderService != null ? "✓ Injected" : "✗ NULL").append("\n");
            debug.append("- bitsLinesService: ").append(bitsLinesService != null ? "✓ Injected" : "✗ NULL").append("\n\n");
            
            // Test header service
            if (bitsHeaderService != null) {
                try {
                    var headers = bitsHeaderService.getAllHeaders();
                    debug.append("Header Service Test:\n");
                    debug.append("- getAllBitsHeaders() result: ").append(headers == null ? "NULL" : "List with " + headers.size() + " items").append("\n");
                    
                    if (headers != null && !headers.isEmpty()) {
                        debug.append("- First header work order: ").append(headers.get(0).getWorkOrder()).append("\n");
                        debug.append("- First header order ID: ").append(headers.get(0).getOrderId()).append("\n");
                    }
                } catch (Exception e) {
                    debug.append("- getAllBitsHeaders() ERROR: ").append(e.getMessage()).append("\n");
                }
            }
            
            debug.append("\n");
            
            // Test lines service
            if (bitsLinesService != null && bitsHeaderService != null) {
                try {
                    var headers = bitsHeaderService.getAllHeaders();
                    if (headers != null && !headers.isEmpty()) {
                        var firstOrderId = headers.get(0).getOrderId();
                        var lines = bitsLinesService.getLinesByOrderId(firstOrderId);
                        debug.append("Lines Service Test:\n");
                        debug.append("- getLinesByOrderId(").append(firstOrderId).append(") result: ")
                              .append(lines == null ? "NULL" : "List with " + lines.size() + " items").append("\n");
                    }
                } catch (Exception e) {
                    debug.append("Lines Service Test ERROR: ").append(e.getMessage()).append("\n");
                }
            }
            
        } catch (Exception e) {
            debug.append("DEBUG ERROR: ").append(e.getMessage()).append("\n");
            debug.append("Stack trace: ").append(getStackTraceAsString(e));
        }
        
        return ResponseEntity.ok(debug.toString());
    }
    
    /**
     * CHECK DATABASE - Simple endpoint to check if data exists
     */
    @GetMapping(value = "/export/check-data", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> checkDatabaseData() {
        StringBuilder result = new StringBuilder();
        result.append("=== Database Data Check ===\n");
        
        try {
            if (bitsHeaderService != null) {
                var headers = bitsHeaderService.getAllHeaders();
                result.append("Headers in database: ").append(headers == null ? "NULL" : headers.size()).append("\n");
                
                if (headers != null && !headers.isEmpty()) {
                    result.append("Sample header data:\n");
                    var firstHeader = headers.get(0);
                    result.append("- Order ID: ").append(firstHeader.getOrderId()).append("\n");
                    result.append("- Work Order: ").append(firstHeader.getWorkOrder()).append("\n");
                    result.append("- Plant Location: ").append(firstHeader.getPlantLocation()).append("\n");
                }
            } else {
                result.append("BitsHeaderService is NULL\n");
            }
        } catch (Exception e) {
            result.append("Error checking data: ").append(e.getMessage()).append("\n");
        }
        
        return ResponseEntity.ok(result.toString());
    }
    
    /**
     * Preview XML content (returns XML as text for easy viewing)
     */
    @GetMapping(value = "/export/xml/preview/all", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> previewAllPurchaseOrdersXml() {
        try {
            LOG.info("Request received to preview all purchase orders XML");
            
            String xmlContent = xmlExportService.exportAllPurchaseOrdersToXml();
            
            LOG.info("Successfully generated XML preview with {} characters", xmlContent.length());
            return ResponseEntity.ok(xmlContent);
            
        } catch (Exception e) {
            LOG.error("Error generating XML preview", e);
            String errorResponse = "ERROR: " + e.getMessage() + "\n\n" +
                "Stack trace:\n" + getStackTraceAsString(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Export all purchase orders to XML (for download)
     */
    @GetMapping(value = "/export/xml/all", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> exportAllPurchaseOrdersToXml() {
        try {
            LOG.info("Request received to export all purchase orders to XML");
            
            String xmlContent = xmlExportService.exportAllPurchaseOrdersToXml();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            headers.setContentDispositionFormData("attachment", generateFileName("all_purchase_orders"));
            
            LOG.info("Successfully exported all purchase orders to XML");
            return new ResponseEntity<>(xmlContent, headers, HttpStatus.OK);
            
        } catch (Exception e) {
            LOG.error("Error exporting all purchase orders to XML", e);
            return new ResponseEntity<>("<?xml version=\"1.0\" encoding=\"UTF-8\"?><error>" + 
                e.getMessage() + "</error>", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Generate filename with timestamp
     */
    private String generateFileName(String prefix) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return prefix + "_" + timestamp + ".xml";
    }
    
    /**
     * Helper method to get stack trace as string
     */
    private String getStackTraceAsString(Exception e) {
        java.io.StringWriter sw = new java.io.StringWriter();
        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
