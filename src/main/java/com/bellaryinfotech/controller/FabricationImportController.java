package com.bellaryinfotech.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.bellaryinfotech.model.OrderFabricationImport;
import com.bellaryinfotech.repo.OrderFabricationImportRepository;
import com.bellaryinfotech.service.ExcelImportService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")  
public class FabricationImportController {

    private static final Logger log = LoggerFactory.getLogger(FabricationImportController.class);

    @Autowired
    private ExcelImportService excelImportService;

    @Autowired
    private OrderFabricationImportRepository repository;

    @PostMapping(value = "/imports", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importExcelFile(@RequestParam("file") MultipartFile file) {
        try {
            // Check if file is empty
            if (file.isEmpty()) {
                log.error("Uploaded file is empty");
                return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Please select a file to upload"
                ));
            }

            // Check if file is an Excel file
            if (!file.getOriginalFilename().endsWith(".xlsx") && !file.getOriginalFilename().endsWith(".xls")) {
                log.error("Uploaded file is not an Excel file: {}", file.getOriginalFilename());
                return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Please upload an Excel file (.xlsx or .xls)"
                ));
            }

            log.info("Processing file: {}, size: {} bytes", file.getOriginalFilename(), file.getSize());
        
            // Process the file
            List<OrderFabricationImport> importedRecords = excelImportService.importExcelToDatabase(file);
            int recordsImported = importedRecords.size();

            // Count success and failure records
            int successCount = (int) importedRecords.stream()
                .filter(record -> "SUCCESS".equals(record.getIfaceStatus()))
                .count();
            
            int failureCount = (int) importedRecords.stream()
                .filter(record -> "ERROR".equals(record.getIfaceStatus()))
                .count();
            
            int pendingCount = (int) importedRecords.stream()
                .filter(record -> "PENDING".equals(record.getIfaceStatus()) || "PROCESSING".equals(record.getIfaceStatus()))
                .count();

            log.info("Import completed. Records imported: {} (Success: {}, Failure: {}, Pending: {})", 
                    recordsImported, successCount, failureCount, pendingCount);
        
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "File imported successfully");
            response.put("recordsImported", recordsImported);
            response.put("successCount", successCount);
            response.put("failureCount", failureCount);
            response.put("pendingCount", pendingCount);
            response.put("data", importedRecords); // Return the imported records

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to import data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                    "status", "error",
                    "message", "Failed to import data: " + e.getMessage(),
                    "details", e.toString()
                ));
        }
    }

    @GetMapping("/imported-data")
    public ResponseEntity<?> getImportedData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ifaceId") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            log.info("Fetching imported data: page={}, size={}, sortBy={}, sortDir={}", page, size, sortBy, sortDir);
            
            Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
            
            Page<OrderFabricationImport> dataPage = repository.findAll(pageable);
            
            // Log the data being returned
            log.info("Found {} records, total {} records", dataPage.getContent().size(), dataPage.getTotalElements());
            
            Map<String, Object> response = new HashMap<>();
            response.put("data", dataPage.getContent());
            response.put("currentPage", dataPage.getNumber());
            response.put("totalItems", dataPage.getTotalElements());
            response.put("totalPages", dataPage.getTotalPages());
            
            // Add import status counts
            long successCount = repository.countByIfaceStatus("SUCCESS");
            long failureCount = repository.countByIfaceStatus("ERROR");
            long pendingCount = repository.countByIfaceStatus("PENDING") + repository.countByIfaceStatus("PROCESSING");
            
            response.put("successCount", successCount);
            response.put("failureCount", failureCount);
            response.put("pendingCount", pendingCount);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to retrieve data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to retrieve data: " + e.getMessage()
                    ));
        }
    }

    @GetMapping("/latest-imported")
    public ResponseEntity<?> getLatestImportedData() {
        try {
            log.info("Fetching latest imported data");
            
            // Get the most recent records (limit to 100 for performance)
            List<OrderFabricationImport> latestRecords = repository.findTop100ByOrderByIfaceIdDesc();
            
            log.info("Found {} latest records", latestRecords.size());
            
            // Count records by status
            long successCount = latestRecords.stream()
                .filter(record -> "SUCCESS".equals(record.getIfaceStatus()))
                .count();
            
            long failureCount = latestRecords.stream()
                .filter(record -> "ERROR".equals(record.getIfaceStatus()))
                .count();
            
            long pendingCount = latestRecords.stream()
                .filter(record -> "PENDING".equals(record.getIfaceStatus()) || "PROCESSING".equals(record.getIfaceStatus()))
                .count();
            
            Map<String, Object> response = new HashMap<>();
            response.put("data", latestRecords);
            response.put("totalItems", latestRecords.size());
            response.put("successCount", successCount);
            response.put("failureCount", failureCount);
            response.put("pendingCount", pendingCount);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to retrieve latest data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to retrieve latest data: " + e.getMessage()
                    ));
        }
    }

    @GetMapping("/import-status")
    public ResponseEntity<?> getImportStatus() {
        try {
            log.info("Fetching import status statistics");
            
            // Count records by status
            long successCount = repository.countByIfaceStatus("SUCCESS");
            long failureCount = repository.countByIfaceStatus("ERROR");
            long pendingCount = repository.countByIfaceStatus("PENDING") + repository.countByIfaceStatus("PROCESSING");
            long totalCount = successCount + failureCount + pendingCount;
            
            Map<String, Object> response = new HashMap<>();
            response.put("successCount", successCount);
            response.put("failureCount", failureCount);
            response.put("pendingCount", pendingCount);
            response.put("totalCount", totalCount);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to retrieve import status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to retrieve import status: " + e.getMessage()
                    ));
        }
    }

    @GetMapping("/template")
    public ResponseEntity<?> getTemplateInfo() {
        try {
            // This endpoint helps users understand what columns are expected
            Map<String, Object> templateInfo = new HashMap<>();
            
            // First sheet columns
            Map<String, String> sheet1Columns = new HashMap<>();
            sheet1Columns.put("ORDER_NUMBER", "Order number (e.g., ORD_321)");
            sheet1Columns.put("ORIG_LINE_NO", "Original line number (e.g., 1)");
            sheet1Columns.put("LINE_NO", "Line number (e.g., 1.1)");
            sheet1Columns.put("DRAWING NO", "Drawing number (e.g., MEC-11-12-Q7NX-DE-41-17362)");
            sheet1Columns.put("DESCRIPTION", "Description of the drawing");
            
            // Second sheet columns
            Map<String, String> sheet2Columns = new HashMap<>();
            sheet2Columns.put("ERECTION MKD.", "Erection marked number (e.g., A-11)");
            sheet2Columns.put("ITEM NO.", "Item number (e.g., 1)");
            sheet2Columns.put("SECTION", "Section information (e.g., PL.300x32)");
            sheet2Columns.put("LENGTH", "Length value (numeric)");
            sheet2Columns.put("qty", "Quantity (numeric)");
            sheet2Columns.put("UNIT", "Unit (numeric)");
            sheet2Columns.put("TOTAL WT.", "Total weight (numeric)");
            sheet2Columns.put("QTY. REQD", "Quantity required (numeric)");
            sheet2Columns.put("EREC. MKD. WT.", "Erection marked weight (numeric)");
            sheet2Columns.put("REMARKS", "Remarks or notes (e.g., Completed)");
            
            templateInfo.put("sheet1", sheet1Columns);
            templateInfo.put("sheet2", sheet2Columns);
            templateInfo.put("instructions", "The Excel file should have two sheets with the columns listed above. " +
                    "The system will match records between sheets based on order number and line number.");
            
            return ResponseEntity.ok(templateInfo);
        } catch (Exception e) {
            log.error("Failed to retrieve template info", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to retrieve template info: " + e.getMessage()
                    ));
        }
    }
    
    //fabrication get api
    @GetMapping("/fabrication-columns")
    public ResponseEntity<?> getFabricationColumns() {
        try {
            log.info("Fetching fabrication table columns");
            
            // Define the columns structure that matches the frontend expectations
            List<Map<String, Object>> columns = new ArrayList<>();
            
            // Order Number column
            Map<String, Object> orderNumberCol = new HashMap<>();
            orderNumberCol.put("id", "orderNumber");
            orderNumberCol.put("label", "Order Number");
            orderNumberCol.put("width", "150px");
            orderNumberCol.put("placeholder", "Enter order");
            orderNumberCol.put("hasIcon", true);
            columns.add(orderNumberCol);
            
            // Original Line No column
            Map<String, Object> origLineNoCol = new HashMap<>();
            origLineNoCol.put("id", "origLineNo");
            orderNumberCol.put("label", "Original Line No");
            origLineNoCol.put("width", "120px");
            origLineNoCol.put("placeholder", "Enter original line");
            origLineNoCol.put("hasIcon", true);
            columns.add(origLineNoCol);
            
            // Line No column
            Map<String, Object> lineNoCol = new HashMap<>();
            lineNoCol.put("id", "lineNo");
            lineNoCol.put("label", "Line No");
            lineNoCol.put("width", "100px");
            lineNoCol.put("placeholder", "Enter line");
            columns.add(lineNoCol);
            
            // Drawing No column
            Map<String, Object> drawingNoCol = new HashMap<>();
            drawingNoCol.put("id", "drawingNo");
            drawingNoCol.put("label", "Drawing No");
            drawingNoCol.put("width", "180px");
            drawingNoCol.put("placeholder", "Enter drawing No");
            drawingNoCol.put("hasIcon", true);
            columns.add(drawingNoCol);
            
            // Description column
            Map<String, Object> descriptionCol = new HashMap<>();
            descriptionCol.put("id", "description");
            descriptionCol.put("label", "Description");
            descriptionCol.put("width", "250px");
            descriptionCol.put("placeholder", "Enter description");
            columns.add(descriptionCol);
            
            // Erection Mkd column
            Map<String, Object> erectionMkdCol = new HashMap<>();
            erectionMkdCol.put("id", "erectionMkd");
            erectionMkdCol.put("label", "Erection Mkd");
            erectionMkdCol.put("width", "150px");
            erectionMkdCol.put("placeholder", "Enter marked");
            columns.add(erectionMkdCol);
            
            // Item No column
            Map<String, Object> itemNoCol = new HashMap<>();
            itemNoCol.put("id", "itemNo");
            itemNoCol.put("label", "Item No");
            itemNoCol.put("width", "100px");
            itemNoCol.put("placeholder", "Enter item");
            columns.add(itemNoCol);
            
            // Section column
            Map<String, Object> sectionCol = new HashMap<>();
            sectionCol.put("id", "section");
            sectionCol.put("label", "Section");
            sectionCol.put("width", "150px");
            sectionCol.put("placeholder", "Enter section");
            columns.add(sectionCol);
            
            // Length column
            Map<String, Object> lengthCol = new HashMap<>();
            lengthCol.put("id", "length");
            lengthCol.put("label", "Length");
            lengthCol.put("width", "100px");
            lengthCol.put("placeholder", "Enter length");
            columns.add(lengthCol);
            
            // Qty column
            Map<String, Object> qtyCol = new HashMap<>();
            qtyCol.put("id", "qty");
            qtyCol.put("label", "Qty");
            qtyCol.put("width", "80px");
            qtyCol.put("placeholder", "Enter qty");
            columns.add(qtyCol);
            
            // Unit column
            Map<String, Object> unitCol = new HashMap<>();
            unitCol.put("id", "unit");
            unitCol.put("label", "Unit");
            unitCol.put("width", "80px");
            unitCol.put("placeholder", "Enter unit");
            columns.add(unitCol);
            
            // Total Wt column
            Map<String, Object> totalWtCol = new HashMap<>();
            totalWtCol.put("id", "totalWt");
            totalWtCol.put("label", "Total Wt");
            totalWtCol.put("width", "120px");
            totalWtCol.put("placeholder", "Enter weight");
            columns.add(totalWtCol);
            
            // Qty Reqd column
            Map<String, Object> qtyReqdCol = new HashMap<>();
            qtyReqdCol.put("id", "qtyReqd");
            qtyReqdCol.put("label", "Qty Reqd");
            qtyReqdCol.put("width", "120px");
            qtyReqdCol.put("placeholder", "Enter qty req.");
            columns.add(qtyReqdCol);
            
            // Erec Mkd Wt column
            Map<String, Object> erecMkdWtCol = new HashMap<>();
            erecMkdWtCol.put("id", "erecMkdWt");
            erecMkdWtCol.put("label", "Erec Mkd Wt");
            erecMkdWtCol.put("width", "150px");
            erecMkdWtCol.put("placeholder", "Enter weight");
            columns.add(erecMkdWtCol);
            
            // Remarks column
            Map<String, Object> remarksCol = new HashMap<>();
            remarksCol.put("id", "remarks");
            remarksCol.put("label", "Remarks");
            remarksCol.put("width", "150px");
            remarksCol.put("placeholder", "Enter remarks");
            columns.add(remarksCol);
            
            // Status column
            Map<String, Object> statusCol = new HashMap<>();
            statusCol.put("id", "status");
            statusCol.put("label", "Status");
            statusCol.put("width", "120px");
            statusCol.put("placeholder", "Status");
            statusCol.put("isStatus", true);
            columns.add(statusCol);
            
            return ResponseEntity.ok(columns);
        } catch (Exception e) {
            log.error("Failed to retrieve fabrication columns", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to retrieve fabrication columns: " + e.getMessage()
                    ));
        }
    }
    
    @GetMapping("/fabrication-by-line")
    public ResponseEntity<?> getFabricationByLine(@RequestParam(required = false) String lineNumber) {
        try {
            log.info("Fetching fabrication data for line number: {}", lineNumber);
            
            List<OrderFabricationImport> records;
            
            if (lineNumber != null && !lineNumber.isEmpty()) {
                // Try to parse the line number as BigDecimal to preserve decimal points
                try {
                    // First try to find by exact string match
                    records = repository.findByLineNumberAsString(lineNumber);
                    
                    // If no records found, try to parse as BigDecimal
                    if (records.isEmpty()) {
                        BigDecimal lineNumberDecimal = new BigDecimal(lineNumber);
                        records = repository.findByLineNumber(lineNumberDecimal);
                        
                        // If still no records, try approximate matching (for floating point precision issues)
                        if (records.isEmpty()) {
                            records = repository.findByApproximateLineNumberAndErectionMkd(lineNumberDecimal, "");
                        }
                    }
                } catch (NumberFormatException e) {
                    // If parsing fails, just use the string version
                    records = repository.findByLineNumberAsString(lineNumber);
                }
            } else {
                // If no line number provided, return latest records
                records = repository.findTop100ByOrderByIfaceIdDesc();
            }
            
            log.info("Found {} records for line number: {}", records.size(), lineNumber);
            
            Map<String, Object> response = new HashMap<>();
            response.put("data", records);
            response.put("totalItems", records.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to retrieve fabrication data by line", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to retrieve fabrication data: " + e.getMessage()
                    ));
        }
    }
    
    @DeleteMapping("/fabrication/{ifaceId}")
    public ResponseEntity<?> deleteFabrication(@PathVariable Long ifaceId) {
        try {
            log.info("Deleting fabrication record with ifaceId: {}", ifaceId);
        
            // Check if the record exists
            boolean exists = repository.existsById(ifaceId);
            log.info("Record with ifaceId {} exists: {}", ifaceId, exists);
        
            if (!exists) {
                log.warn("Fabrication record with ifaceId {} not found", ifaceId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                            "status", "error",
                            "message", "Fabrication record not found with ifaceId: " + ifaceId
                        ));
            }
        
            // Get the record before deleting (for logging purposes)
            Optional<OrderFabricationImport> record = repository.findById(ifaceId);
            if (record.isPresent()) {
                OrderFabricationImport fabrication = record.get();
                log.info("Found record to delete: ifaceId={}, orderNumber={}, lineNumber={}",
                        fabrication.getIfaceId(), fabrication.getOrderNumber(), fabrication.getLineNumber());
            }
        
            // Delete the record
            repository.deleteById(ifaceId);
            log.info("Successfully deleted fabrication record with ifaceId: {}", ifaceId);
        
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Fabrication record deleted successfully",
                "ifaceId", ifaceId
            ));
        } catch (Exception e) {
            log.error("Failed to delete fabrication record with ifaceId {}: {}", ifaceId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to delete fabrication record: " + e.getMessage(),
                        "details", e.toString()
                    ));
        }
    }
    
    // NEW ENDPOINTS FOR ERECTION MKD FILTERING
    
    /**
     * Get fabrication records by erection MKD
     * 
     * @param erectionMkd The erection MKD to filter by
     * @return List of matching fabrication records
     */
    @GetMapping("/fabrication/erection/{erectionMkd}")
    public ResponseEntity<?> getFabricationByErectionMkd(@PathVariable String erectionMkd) {
        try {
            log.info("Fetching fabrication data for erection MKD: {}", erectionMkd);
            
            if (erectionMkd == null || erectionMkd.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Erection MKD parameter is required"
                ));
            }
            
            List<OrderFabricationImport> records = repository.findByErectionMkdContainingIgnoreCase(erectionMkd);
            log.info("Found {} records for erection MKD: {}", records.size(), erectionMkd);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", records);
            response.put("count", records.size());
            response.put("erectionMkd", erectionMkd);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to retrieve fabrication data by erection MKD", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to retrieve fabrication data: " + e.getMessage()
                    ));
        }
    }
    
    /**
     * Get fabrication records by line number and erection MKD
     * 
     * @param lineNumber The line number to filter by
     * @param erectionMkd The erection MKD to filter by
     * @return List of matching fabrication records
     */
    @GetMapping("/fabrication/line/{lineNumber}/erection/{erectionMkd}")
    public ResponseEntity<?> getFabricationByLineNumberAndErectionMkd(
            @PathVariable String lineNumber,
            @PathVariable String erectionMkd) {
        try {
            log.info("Fetching fabrication data for line number: {} and erection MKD: {}", lineNumber, erectionMkd);
            
            if (lineNumber == null || lineNumber.isEmpty() || erectionMkd == null || erectionMkd.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Both line number and erection MKD parameters are required"
                ));
            }
            
            List<OrderFabricationImport> records = new ArrayList<>();
            
            // Try to parse the line number as BigDecimal to preserve decimal points
            try {
                BigDecimal lineNumberDecimal = new BigDecimal(lineNumber);
                records = repository.findByLineNumberAndErectionMkdContainingIgnoreCase(lineNumberDecimal, erectionMkd);
                
                // If no records found, try approximate matching (for floating point precision issues)
                if (records.isEmpty()) {
                    records = repository.findByApproximateLineNumberAndErectionMkd(lineNumberDecimal, erectionMkd);
                }
                
                log.info("Found {} records for line number: {} and erection MKD: {}", 
                        records.size(), lineNumberDecimal, erectionMkd);
            } catch (NumberFormatException e) {
                log.warn("Could not parse line number as BigDecimal: {}", lineNumber);
                // If we can't parse as BigDecimal, try string matching
                records = repository.findByLineNumberAsString(lineNumber);
                // Filter by erection MKD in memory
                records = records.stream()
                        .filter(r -> r.getErectionMkd() != null && 
                                r.getErectionMkd().toLowerCase().contains(erectionMkd.toLowerCase()))
                        .toList();
                log.info("Found {} records for string line number: {} and erection MKD: {}", 
                        records.size(), lineNumber, erectionMkd);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", records);
            response.put("count", records.size());
            response.put("lineNumber", lineNumber);
            response.put("erectionMkd", erectionMkd);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to retrieve fabrication data by line number and erection MKD", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to retrieve fabrication data: " + e.getMessage()
                    ));
        }
    }
    
    /**
     * Get fabrication records by line ID and erection MKD
     * 
     * @param lineId The line ID to filter by
     * @param erectionMkd The erection MKD to filter by
     * @return List of matching fabrication records
     */
    @GetMapping("/fabrication/line-id/{lineId}/erection/{erectionMkd}")
    public ResponseEntity<?> getFabricationByLineIdAndErectionMkd(
            @PathVariable Long lineId,
            @PathVariable String erectionMkd) {
        try {
            log.info("Fetching fabrication data for line ID: {} and erection MKD: {}", lineId, erectionMkd);
            
            if (lineId == null || erectionMkd == null || erectionMkd.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Both line ID and erection MKD parameters are required"
                ));
            }
            
            List<OrderFabricationImport> records = repository.findByLineIdAndErectionMkdContainingIgnoreCase(lineId, erectionMkd);
            log.info("Found {} records for line ID: {} and erection MKD: {}", records.size(), lineId, erectionMkd);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", records);
            response.put("count", records.size());
            response.put("lineId", lineId);
            response.put("erectionMkd", erectionMkd);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to retrieve fabrication data by line ID and erection MKD", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to retrieve fabrication data: " + e.getMessage()
                    ));
        }
    }
}