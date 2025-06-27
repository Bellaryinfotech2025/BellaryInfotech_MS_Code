package com.bellaryinfotech.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bellaryinfotech.DTO.BitsLinesDto;
import com.bellaryinfotech.service.BitsLinesService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class BitsLinesController {

    @Autowired
    private BitsLinesService linesService;

    // Existing endpoint constants
    public static final String GET_ALL_LINES = "/getAllBitsLines/details";
    public static final String GET_LINE_BY_ID = "/getBitsLineById/details";
    public static final String CREATE_LINE = "/createBitsLine/details";
    public static final String UPDATE_LINE = "/updateBitsLine/details";
    public static final String DELETE_LINE = "/deleteBitsLine/details";
    public static final String SEARCH_BY_SERNO = "/searchBitsLinesBySerNo/details";
    public static final String SEARCH_BY_SERVICECODE = "/searchBitsLinesByServiceCode/details";
    public static final String SEARCH_BY_SERVICEDESC = "/searchBitsLinesByServiceDesc/details";
    public static final String GET_LINES_BY_WORK_ORDER = "/getBitsLinesByWorkOrder/details";
    public static final String DEBUG_LINES = "/debugBitsLines/details";
    
    // Enhanced endpoints using proper foreign key relationships
    public static final String CREATE_LINE_WITH_ORDER = "/createBitsLineWithOrder/details";
    public static final String GET_LINES_BY_ORDER_ID = "/getBitsLinesByOrderId/details";
    public static final String CREATE_MULTIPLE_LINES = "/createMultipleBitsLines/details";
    // to get the lines detaisl for invoice
    public static final String GET_INVOICE_DATA = "/getInvoiceData/details";
    
    // NEW: Endpoint for distinct serial numbers
    public static final String GET_DISTINCT_SERIAL_NUMBERS = "/getDistinctSerialNumbers/details";

    private static final Logger LOG = LoggerFactory.getLogger(BitsLinesController.class);

    @RequestMapping(value = GET_ALL_LINES, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllLines() {
        LOG.info("Fetching all bits lines");
        List<BitsLinesDto> lines = linesService.getAllLines();
        return ResponseEntity.ok(lines);
    }

    @RequestMapping(value = GET_LINE_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getLineById(@RequestParam Long id) {
        LOG.info("Fetching bits line by ID: {}", id);
        Optional<BitsLinesDto> line = linesService.getLineById(id);
        return line.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Legacy create method (maintains backward compatibility)
    @PostMapping(value = CREATE_LINE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createLine(@RequestBody BitsLinesDto lineDto) {
        LOG.info("Creating a new bits line with data: {}", lineDto);
        try {
            BitsLinesDto createdLine = linesService.createLine(lineDto);
            LOG.info("Created bits line: {}", createdLine);
            return ResponseEntity.status(201).body(createdLine);
        } catch (Exception e) {
            LOG.error("Error creating bits line", e);
            return ResponseEntity.badRequest().body("Error creating line: " + e.getMessage());
        }
    }

    // Enhanced create method with explicit order ID
    @PostMapping(value = CREATE_LINE_WITH_ORDER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createLineWithOrder(@RequestParam Long orderId, @RequestBody BitsLinesDto lineDto) {
        LOG.info("Creating a new bits line for orderId: {} with data: {}", orderId, lineDto);
        try {
            BitsLinesDto createdLine = linesService.createLine(lineDto, orderId);
            LOG.info("Created bits line: {}", createdLine);
            return ResponseEntity.status(201).body(createdLine);
        } catch (Exception e) {
            LOG.error("Error creating bits line for orderId: {}", orderId, e);
            return ResponseEntity.badRequest().body("Error creating line: " + e.getMessage());
        }
    }

    // Bulk create method
    @PostMapping(value = CREATE_MULTIPLE_LINES, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMultipleLines(@RequestParam Long orderId, @RequestBody List<BitsLinesDto> lineDtos) {
        LOG.info("Creating {} bits lines for orderId: {}", lineDtos.size(), orderId);
        try {
            List<BitsLinesDto> createdLines = linesService.createMultipleLines(lineDtos, orderId);
            LOG.info("Created {} bits lines", createdLines.size());
            return ResponseEntity.status(201).body(createdLines);
        } catch (Exception e) {
            LOG.error("Error creating multiple bits lines for orderId: {}", orderId, e);
            return ResponseEntity.badRequest().body("Error creating lines: " + e.getMessage());
        }
    }

    @PutMapping(value = UPDATE_LINE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateLine(@RequestParam Long id, @RequestBody BitsLinesDto lineDto) {
        LOG.info("Updating bits line with ID: {}", id);
        try {
            BitsLinesDto updatedLine = linesService.updateLine(id, lineDto);
            return ResponseEntity.ok(updatedLine);
        } catch (RuntimeException e) {
            LOG.error("Bits line not found for update: {}", id, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            LOG.error("Error updating bits line", e);
            return ResponseEntity.badRequest().body("Error updating line: " + e.getMessage());
        }
    }

    @DeleteMapping(value = DELETE_LINE)
    public ResponseEntity<?> deleteLine(@RequestParam Long id) {
        LOG.info("Deleting bits line with ID: {}", id);
        try {
            linesService.deleteLine(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error deleting bits line", e);
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = SEARCH_BY_SERNO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchBySerNo(@RequestParam String serNo) {
        LOG.info("Searching bits lines by serNo: {}", serNo);
        List<BitsLinesDto> lines = linesService.searchBySerNo(serNo);
        return ResponseEntity.ok(lines);
    }

    @RequestMapping(value = SEARCH_BY_SERVICECODE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchByServiceCode(@RequestParam String serviceCode) {
        LOG.info("Searching bits lines by serviceCode: {}", serviceCode);
        List<BitsLinesDto> lines = linesService.searchByServiceCode(serviceCode);
        return ResponseEntity.ok(lines);
    }

    @RequestMapping(value = SEARCH_BY_SERVICEDESC, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchByServiceDesc(@RequestParam String serviceDesc) {
        LOG.info("Searching bits lines by serviceDesc: {}", serviceDesc);
        List<BitsLinesDto> lines = linesService.searchByServiceDesc(serviceDesc);
        return ResponseEntity.ok(lines);
    }

    // Enhanced endpoint to get service orders by work order number
    @RequestMapping(value = GET_LINES_BY_WORK_ORDER, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getLinesByWorkOrder(@RequestParam String workOrder) {
        LOG.info("Fetching bits lines by work order: {}", workOrder);
        try {
            List<BitsLinesDto> lines = linesService.getLinesByWorkOrder(workOrder);
            LOG.info("Found {} lines for work order: {}", lines.size(), workOrder);
            return ResponseEntity.ok(lines);
        } catch (Exception e) {
            LOG.error("Error fetching bits lines by work order: {}", workOrder, e);
            return ResponseEntity.badRequest().body("Error fetching lines: " + e.getMessage());
        }
    }
    
    // Get lines by order ID (proper foreign key relationship)
    @RequestMapping(value = GET_LINES_BY_ORDER_ID, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getLinesByOrderId(@RequestParam Long orderId) {
        LOG.info("Fetching bits lines by orderId: {}", orderId);
        try {
            List<BitsLinesDto> lines = linesService.getLinesByOrderId(orderId);
            LOG.info("Found {} lines for orderId: {}", lines.size(), orderId);
            return ResponseEntity.ok(lines);
        } catch (Exception e) {
            LOG.error("Error fetching bits lines by orderId: {}", orderId, e);
            return ResponseEntity.badRequest().body("Error fetching lines: " + e.getMessage());
        }
    }
    
    // Debug endpoint
    @RequestMapping(value = DEBUG_LINES, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> debugLines() {
        LOG.info("Debug: Fetching all bits lines with attributes");
        try {
            List<BitsLinesDto> lines = linesService.getAllLinesWithAttributes();
            LOG.info("Debug: Found {} total lines", lines.size());
            return ResponseEntity.ok(lines);
        } catch (Exception e) {
            LOG.error("Error in debug endpoint", e);
            return ResponseEntity.badRequest().body("Error in debug: " + e.getMessage());
        }
    }
    
    // Get api to get the lines related for invoice
    @RequestMapping(value = GET_INVOICE_DATA, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getInvoiceData(@RequestParam Long orderId) {
        LOG.info("Fetching invoice data for orderId: {}", orderId);
        
        try {
            // Use the existing service method to get lines by order ID
            List<BitsLinesDto> lines = linesService.getLinesByOrderId(orderId);
            
            // Transform the data to include only the fields needed for invoice
            List<Map<String, Object>> invoiceData = lines.stream()
                    .map(line -> {
                        Map<String, Object> invoiceItem = new HashMap<>();
                        invoiceItem.put("lineId", line.getLineId());
                        invoiceItem.put("serNo", line.getSerNo());
                        invoiceItem.put("serviceCode", line.getServiceCode());
                        invoiceItem.put("serviceDesc", line.getServiceDesc());
                        invoiceItem.put("qty", line.getQty());
                        invoiceItem.put("uom", line.getUom());
                        invoiceItem.put("unitPrice", line.getUnitPrice());
                        invoiceItem.put("totalPrice", line.getTotalPrice());
                        return invoiceItem;
                    })
                    .collect(Collectors.toList());
            
            LOG.info("Found {} invoice items for orderId: {}", invoiceData.size(), orderId);
            return ResponseEntity.ok(invoiceData);
            
        } catch (Exception e) {
            LOG.error("Error fetching invoice data for orderId: {}", orderId, e);
            return ResponseEntity.badRequest().body("Error fetching invoice data: " + e.getMessage());
        }
    }

    /**
     * NEW: Get distinct serial numbers from bits_po_entry_lines table
     */
    @RequestMapping(value = GET_DISTINCT_SERIAL_NUMBERS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDistinctSerialNumbers() {
        LOG.info("Fetching distinct serial numbers from bits_po_entry_lines");
        try {
            List<BitsLinesDto> allLines = linesService.getAllLines();
            
            // Extract distinct serial numbers, filtering out null and empty values
            List<String> distinctSerialNumbers = allLines.stream()
                    .map(BitsLinesDto::getSerNo)
                    .filter(serNo -> serNo != null && !serNo.trim().isEmpty())
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
            
            LOG.info("Found {} distinct serial numbers", distinctSerialNumbers.size());
            return ResponseEntity.ok(distinctSerialNumbers);
            
        } catch (Exception e) {
            LOG.error("Error fetching distinct serial numbers", e);
            return ResponseEntity.badRequest().body("Error fetching serial numbers: " + e.getMessage());
        }
    }
}
