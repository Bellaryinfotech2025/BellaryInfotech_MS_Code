package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.BillingDrawingEntryDto;
import com.bellaryinfotech.service.BillingDrawingEntryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class BillingDrawingEntryController {
	
    private static final Logger logger = LoggerFactory.getLogger(BillingDrawingEntryController.class);

    @Autowired
    private BillingDrawingEntryService billingDrawingEntryService;

    // API endpoint constants
    public static final String GET_ALL_BILLING_ENTRIES = "/getAllBillingDrawingEntries/details";
    public static final String GET_UNIQUE_BILLING_ENTRIES = "/getUniqueBillingDrawingEntries/details";
    public static final String GET_BILLING_ENTRY_BY_ID = "/getBillingDrawingEntryById/details";
    public static final String CREATE_BILLING_ENTRY = "/createBillingDrawingEntry/details";
    public static final String CREATE_BULK_BILLING_ENTRIES = "/createBulkBillingDrawingEntries/details";
    public static final String UPDATE_BILLING_ENTRY = "/updateBillingDrawingEntry/details";
    public static final String DELETE_BILLING_ENTRY = "/deleteBillingDrawingEntry/details";
    public static final String DELETE_BILLING_ENTRIES_BY_DRAWING_NO = "/deleteBillingDrawingEntriesByDrawingNo/details";
    public static final String DELETE_BILLING_ENTRIES_BY_MARK_NO = "/deleteBillingDrawingEntriesByMarkNo/details";
    public static final String DELETE_BILLING_ENTRIES_BY_STATUS = "/deleteBillingDrawingEntriesByStatus/details";
    public static final String BULK_DELETE_BILLING_ENTRIES = "/bulkDeleteBillingDrawingEntries/details";
    public static final String SEARCH_BY_DRAWING_NO = "/searchBillingDrawingEntriesByDrawingNo/details";
    public static final String SEARCH_BY_MARK_NO = "/searchBillingDrawingEntriesByMarkNo/details";
    public static final String SEARCH_BY_SESSION_CODE = "/searchBillingDrawingEntriesBySessionCode/details";
    public static final String SEARCH_BY_TENANT_ID = "/searchBillingDrawingEntriesByTenantId/details";
    public static final String SEARCH_BY_STATUS = "/searchBillingDrawingEntriesByStatus/details";
    public static final String SEARCH_BY_MULTIPLE_CRITERIA = "/searchBillingDrawingEntries/details";
    public static final String SEARCH_BY_DATE_RANGE = "/searchBillingDrawingEntriesByDateRange/details";
    public static final String SEARCH_BY_MARKED_QTY_GREATER_THAN = "/searchBillingDrawingEntriesByMarkedQtyGreaterThan/details";
    public static final String GET_COUNT_BY_DRAWING_NO = "/getBillingDrawingEntryCountByDrawingNo/details";
    public static final String GET_COUNT_BY_MARK_NO = "/getBillingDrawingEntryCountByMarkNo/details";
    public static final String GET_COUNT_BY_STATUS = "/getBillingDrawingEntryCountByStatus/details";
    public static final String GET_SUM_MARKED_QTY_BY_DRAWING_NO = "/getBillingDrawingEntrySumMarkedQtyByDrawingNo/details";
    public static final String GET_SUM_TOTAL_MARKED_WGT_BY_DRAWING_NO = "/getBillingDrawingEntrySumTotalMarkedWgtByDrawingNo/details";
    public static final String GET_DISTINCT_DRAWING_NUMBERS = "/getDistinctBillingDrawingEntryDrawingNumbers/details";
    public static final String GET_DISTINCT_MARK_NUMBERS = "/getDistinctBillingDrawingEntryMarkNumbers/details";
    public static final String GET_DISTINCT_SESSION_CODES = "/getDistinctBillingDrawingEntrySessionCodes/details";
    public static final String GET_DISTINCT_STATUSES = "/getDistinctBillingDrawingEntryStatuses/details";
    public static final String CHECK_EXISTS_BY_ID = "/checkBillingDrawingEntryExistsById/details";
    public static final String CHECK_EXISTS_BY_DRAWING_NO_AND_MARK_NO = "/checkBillingDrawingEntryExistsByDrawingNoAndMarkNo/details";
    public static final String CHECK_EXISTS_BY_DRAWING_NO_AND_MARK_NO_AND_STATUS = "/checkBillingDrawingEntryExistsByDrawingNoAndMarkNoAndStatus/details";
    public static final String GET_TOTAL_COUNT = "/getBillingDrawingEntryTotalCount/details";
    public static final String GET_LATEST_BY_DRAWING_NO = "/getLatestBillingDrawingEntryByDrawingNo/details";
    public static final String GET_LATEST_BY_MARK_NO = "/getLatestBillingDrawingEntryByMarkNo/details";
    public static final String GET_LATEST_BY_STATUS = "/getLatestBillingDrawingEntryByStatus/details";

    private static final Logger LOG = LoggerFactory.getLogger(BillingDrawingEntryController.class);

    /**
     * Get all billing entries with pagination
     */
    @RequestMapping(value = GET_ALL_BILLING_ENTRIES, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllBillingEntries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "creationDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            LOG.info("Fetching all billing drawing entries with pagination");
            
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<BillingDrawingEntryDto> billingEntries = billingDrawingEntryService.getAllBillingEntries(pageable);
            return ResponseEntity.ok(billingEntries);
        } catch (Exception e) {
            LOG.error("Error getting all billing entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get billing entries: " + e.getMessage());
        }
    }

    /**
     * Get unique billing entries
     */
    @RequestMapping(value = GET_UNIQUE_BILLING_ENTRIES, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getUniqueBillingEntries() {
        try {
            LOG.info("Fetching unique billing drawing entries");
            List<BillingDrawingEntryDto> uniqueEntries = billingDrawingEntryService.getUniqueDrawingMarkCombinations();
            return ResponseEntity.ok(uniqueEntries);
        } catch (Exception e) {
            LOG.error("Error getting unique billing entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get unique billing entries: " + e.getMessage());
        }
    }

    /**
     * Get billing entry by line ID
     */
    @RequestMapping(value = GET_BILLING_ENTRY_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getBillingEntryById(@RequestParam String lineId) {
        try {
            LOG.info("Fetching billing entry by line ID: {}", lineId);
            Optional<BillingDrawingEntryDto> billingEntry = billingDrawingEntryService.getBillingEntryById(lineId);
            
            if (billingEntry.isPresent()) {
                return ResponseEntity.ok(billingEntry.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOG.error("Error getting billing entry by line ID: {}", lineId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get billing entry: " + e.getMessage());
        }
    }

    /**
     * Create a new billing entry
     */
    @RequestMapping(value = CREATE_BILLING_ENTRY, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<?> createBillingEntry(@RequestBody BillingDrawingEntryDto billingEntryDto) {
        try {
            LOG.info("Creating new billing entry for drawing number: {}", billingEntryDto.getDrawingNo());
            List<BillingDrawingEntryDto> createdEntries = billingDrawingEntryService.createBillingEntry(billingEntryDto);
            LOG.info("Successfully created {} billing entries", createdEntries.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEntries);
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid input for creating billing entry: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error creating billing entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create billing entry: " + e.getMessage());
        }
    }

    /**
     * Create multiple billing entries
     */
    @RequestMapping(value = CREATE_BULK_BILLING_ENTRIES, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<?> createBillingEntries(@RequestBody List<BillingDrawingEntryDto> billingEntryDtos) {
        try {
            LOG.info("Creating {} billing entries", billingEntryDtos.size());
            List<BillingDrawingEntryDto> createdEntries = billingDrawingEntryService.createBillingEntries(billingEntryDtos);
            LOG.info("Successfully created {} total billing entries", createdEntries.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEntries);
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid input for creating billing entries: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error creating billing entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create billing entries: " + e.getMessage());
        }
    }

    /**
     * Update billing entry
     */
    @RequestMapping(value = UPDATE_BILLING_ENTRY, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity<?> updateBillingEntry(
            @RequestParam String lineId,
            @Valid @RequestBody BillingDrawingEntryDto billingEntryDto) {
        try {
            LOG.info("Updating billing entry with line ID: {}", lineId);
            BillingDrawingEntryDto updatedEntry = billingDrawingEntryService.updateBillingEntry(lineId, billingEntryDto);
            return ResponseEntity.ok(updatedEntry);
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid input for updating billing entry: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            LOG.error("Error updating billing entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update billing entry: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error updating billing entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update billing entry: " + e.getMessage());
        }
    }

    /**
     * Delete billing entry by line ID
     */
    @RequestMapping(value = DELETE_BILLING_ENTRY, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBillingEntry(@RequestParam String lineId) {
        try {
            LOG.info("Deleting billing entry with line ID: {}", lineId);
            billingDrawingEntryService.deleteBillingEntry(lineId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            LOG.error("Error deleting billing entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete billing entry: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error deleting billing entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete billing entry: " + e.getMessage());
        }
    }

    /**
     * Delete billing entries by drawing number
     */
    @RequestMapping(value = DELETE_BILLING_ENTRIES_BY_DRAWING_NO, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBillingEntriesByDrawingNo(@RequestParam String drawingNo) {
        try {
            LOG.info("Deleting billing entries with drawing number: {}", drawingNo);
            billingDrawingEntryService.deleteBillingEntriesByDrawingNo(drawingNo);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error deleting billing entries by drawing number", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete billing entries: " + e.getMessage());
        }
    }

    /**
     * Delete billing entries by mark number
     */
    @RequestMapping(value = DELETE_BILLING_ENTRIES_BY_MARK_NO, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBillingEntriesByMarkNo(@RequestParam String markNo) {
        try {
            LOG.info("Deleting billing entries with mark number: {}", markNo);
            billingDrawingEntryService.deleteBillingEntriesByMarkNo(markNo);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error deleting billing entries by mark number", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete billing entries: " + e.getMessage());
        }
    }

    /**
     * Delete billing entries by status
     */
    @RequestMapping(value = DELETE_BILLING_ENTRIES_BY_STATUS, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBillingEntriesByStatus(@RequestParam String status) {
        try {
            LOG.info("Deleting billing entries with status: {}", status);
            billingDrawingEntryService.deleteBillingEntriesByStatus(status);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error deleting billing entries by status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete billing entries: " + e.getMessage());
        }
    }

    /**
     * Bulk delete billing entries
     */
    @RequestMapping(value = BULK_DELETE_BILLING_ENTRIES, method = RequestMethod.DELETE)
    public ResponseEntity<?> bulkDeleteBillingEntries(@RequestParam String lineIds) {
        try {
            LOG.info("Bulk deleting billing entries with line IDs: {}", lineIds);
            List<String> lineIdList = Arrays.asList(lineIds.split(","));
            billingDrawingEntryService.bulkDeleteBillingEntries(lineIdList);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error bulk deleting billing entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete billing entries: " + e.getMessage());
        }
    }

    /**
     * Search billing entries by drawing number
     */
    @RequestMapping(value = SEARCH_BY_DRAWING_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchBillingEntriesByDrawingNo(@RequestParam String drawingNo) {
        try {
            LOG.info("Searching billing entries by drawing number: {}", drawingNo);
            List<BillingDrawingEntryDto> billingEntries = billingDrawingEntryService.getBillingEntriesByDrawingNo(drawingNo);
            return ResponseEntity.ok(billingEntries);
        } catch (Exception e) {
            LOG.error("Error searching billing entries by drawing number", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search billing entries: " + e.getMessage());
        }
    }

    /**
     * Search billing entries by mark number
     */
    @RequestMapping(value = SEARCH_BY_MARK_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchBillingEntriesByMarkNo(@RequestParam String markNo) {
        try {
            LOG.info("Searching billing entries by mark number: {}", markNo);
            List<BillingDrawingEntryDto> billingEntries = billingDrawingEntryService.getBillingEntriesByMarkNo(markNo);
            return ResponseEntity.ok(billingEntries);
        } catch (Exception e) {
            LOG.error("Error searching billing entries by mark number", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search billing entries: " + e.getMessage());
        }
    }

    /**
     * Search billing entries by session code
     */
    @RequestMapping(value = SEARCH_BY_SESSION_CODE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchBillingEntriesBySessionCode(@RequestParam String sessionCode) {
        try {
            LOG.info("Searching billing entries by session code: {}", sessionCode);
            List<BillingDrawingEntryDto> billingEntries = billingDrawingEntryService.getBillingEntriesBySessionCode(sessionCode);
            return ResponseEntity.ok(billingEntries);
        } catch (Exception e) {
            LOG.error("Error searching billing entries by session code", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search billing entries: " + e.getMessage());
        }
    }

    /**
     * Search billing entries by tenant ID
     */
    @RequestMapping(value = SEARCH_BY_TENANT_ID, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchBillingEntriesByTenantId(
            @RequestParam String tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "creationDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            LOG.info("Searching billing entries by tenant ID: {}", tenantId);
            
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<BillingDrawingEntryDto> billingEntries = billingDrawingEntryService.getBillingEntriesByTenantId(tenantId, pageable);
            return ResponseEntity.ok(billingEntries);
        } catch (Exception e) {
            LOG.error("Error searching billing entries by tenant ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search billing entries: " + e.getMessage());
        }
    }

    /**
     * Search billing entries by status
     */
    @RequestMapping(value = SEARCH_BY_STATUS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchBillingEntriesByStatus(
            @RequestParam String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "creationDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            LOG.info("Searching billing entries by status: {}", status);
            
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<BillingDrawingEntryDto> billingEntries = billingDrawingEntryService.getBillingEntriesByStatus(status, pageable);
            return ResponseEntity.ok(billingEntries);
        } catch (Exception e) {
            LOG.error("Error searching billing entries by status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search billing entries: " + e.getMessage());
        }
    }

    /**
     * Search billing entries by multiple criteria
     */
    @RequestMapping(value = SEARCH_BY_MULTIPLE_CRITERIA, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchBillingEntries(
            @RequestParam(required = false) String drawingNo,
            @RequestParam(required = false) String markNo,
            @RequestParam(required = false) String sessionCode,
            @RequestParam(required = false) String tenantId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "creationDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            LOG.info("Searching billing entries with multiple criteria");
            
            // Convert empty strings to null for proper search
            drawingNo = (drawingNo != null && drawingNo.trim().isEmpty()) ? null : drawingNo;
            markNo = (markNo != null && markNo.trim().isEmpty()) ? null : markNo;
            sessionCode = (sessionCode != null && sessionCode.trim().isEmpty()) ? null : sessionCode;
            tenantId = (tenantId != null && tenantId.trim().isEmpty()) ? null : tenantId;
            status = (status != null && status.trim().isEmpty()) ? null : status;
            
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<BillingDrawingEntryDto> billingEntries = billingDrawingEntryService.searchBillingEntries(
                    drawingNo, markNo, sessionCode, tenantId, status, pageable);
            return ResponseEntity.ok(billingEntries);
        } catch (Exception e) {
            LOG.error("Error searching billing entries with multiple criteria", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search billing entries: " + e.getMessage());
        }
    }

    /**
     * Search billing entries by date range
     */
    @RequestMapping(value = SEARCH_BY_DATE_RANGE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchBillingEntriesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            LOG.info("Searching billing entries by date range: {} to {}", startDate, endDate);
            List<BillingDrawingEntryDto> billingEntries = billingDrawingEntryService.getBillingEntriesByDateRange(startDate, endDate);
            return ResponseEntity.ok(billingEntries);
        } catch (Exception e) {
            LOG.error("Error searching billing entries by date range", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search billing entries: " + e.getMessage());
        }
    }

    /**
     * Search billing entries by marked quantity greater than
     */
    @RequestMapping(value = SEARCH_BY_MARKED_QTY_GREATER_THAN, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchBillingEntriesByMarkedQtyGreaterThan(@RequestParam BigDecimal markedQty) {
        try {
            LOG.info("Searching billing entries by marked quantity greater than: {}", markedQty);
            List<BillingDrawingEntryDto> billingEntries = billingDrawingEntryService.getBillingEntriesByMarkedQtyGreaterThan(markedQty);
            return ResponseEntity.ok(billingEntries);
        } catch (Exception e) {
            LOG.error("Error searching billing entries by marked quantity", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search billing entries: " + e.getMessage());
        }
    }

    /**
     * Get count by drawing number
     */
    @RequestMapping(value = GET_COUNT_BY_DRAWING_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getCountByDrawingNo(@RequestParam String drawingNo) {
        try {
            LOG.info("Getting count by drawing number: {}", drawingNo);
            Long count = billingDrawingEntryService.getCountByDrawingNo(drawingNo);
            Map<String, Long> response = new HashMap<>();
            response.put("count", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOG.error("Error getting count by drawing number", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get count: " + e.getMessage());
        }
    }

    /**
     * Get count by mark number
     */
    @RequestMapping(value = GET_COUNT_BY_MARK_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getCountByMarkNo(@RequestParam String markNo) {
        try {
            LOG.info("Getting count by mark number: {}", markNo);
            Long count = billingDrawingEntryService.getCountByMarkNo(markNo);
            Map<String, Long> response = new HashMap<>();
            response.put("count", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOG.error("Error getting count by mark number", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get count: " + e.getMessage());
        }
    }

    /**
     * Get count by status
     */
    @RequestMapping(value = GET_COUNT_BY_STATUS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getCountByStatus(@RequestParam String status) {
        try {
            LOG.info("Getting count by status: {}", status);
            Long count = billingDrawingEntryService.getCountByStatus(status);
            Map<String, Long> response = new HashMap<>();
            response.put("count", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOG.error("Error getting count by status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get count: " + e.getMessage());
        }
    }

    /**
     * Get sum of marked quantities by drawing number
     */
    @RequestMapping(value = GET_SUM_MARKED_QTY_BY_DRAWING_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getSumMarkedQtyByDrawingNo(@RequestParam String drawingNo) {
        try {
            LOG.info("Getting sum of marked quantities by drawing number: {}", drawingNo);
            BigDecimal sum = billingDrawingEntryService.getSumMarkedQtyByDrawingNo(drawingNo);
            Map<String, BigDecimal> response = new HashMap<>();
            response.put("sum", sum != null ? sum : BigDecimal.ZERO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOG.error("Error getting sum of marked quantities", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get sum: " + e.getMessage());
        }
    }

    /**
     * Get sum of total marked weights by drawing number
     */
    @RequestMapping(value = GET_SUM_TOTAL_MARKED_WGT_BY_DRAWING_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getSumTotalMarkedWgtByDrawingNo(@RequestParam String drawingNo) {
        try {
            LOG.info("Getting sum of total marked weights by drawing number: {}", drawingNo);
            BigDecimal sum = billingDrawingEntryService.getSumTotalMarkedWgtByDrawingNo(drawingNo);
            Map<String, BigDecimal> response = new HashMap<>();
            response.put("sum", sum != null ? sum : BigDecimal.ZERO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOG.error("Error getting sum of total marked weights", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get sum: " + e.getMessage());
        }
    }

    /**
     * Get distinct drawing numbers
     */
    @RequestMapping(value = GET_DISTINCT_DRAWING_NUMBERS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDistinctDrawingNumbers() {
        try {
            LOG.info("Getting distinct drawing numbers");
            List<String> drawingNumbers = billingDrawingEntryService.getDistinctDrawingNumbers();
            return ResponseEntity.ok(drawingNumbers);
        } catch (Exception e) {
            LOG.error("Error getting distinct drawing numbers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get distinct drawing numbers: " + e.getMessage());
        }
    }

    /**
     * Get distinct mark numbers
     */
    @RequestMapping(value = GET_DISTINCT_MARK_NUMBERS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDistinctMarkNumbers() {
        try {
            LOG.info("Getting distinct mark numbers");
            List<String> markNumbers = billingDrawingEntryService.getDistinctMarkNumbers();
            return ResponseEntity.ok(markNumbers);
        } catch (Exception e) {
            LOG.error("Error getting distinct mark numbers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get distinct mark numbers: " + e.getMessage());
        }
    }

    /**
     * Get distinct session codes
     */
    @RequestMapping(value = GET_DISTINCT_SESSION_CODES, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDistinctSessionCodes() {
        try {
            LOG.info("Getting distinct session codes");
            List<String> sessionCodes = billingDrawingEntryService.getDistinctSessionCodes();
            return ResponseEntity.ok(sessionCodes);
        } catch (Exception e) {
            LOG.error("Error getting distinct session codes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get distinct session codes: " + e.getMessage());
        }
    }

    /**
     * Get distinct statuses
     */
    @RequestMapping(value = GET_DISTINCT_STATUSES, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDistinctStatuses() {
        try {
            LOG.info("Getting distinct statuses");
            List<String> statuses = billingDrawingEntryService.getDistinctStatuses();
            return ResponseEntity.ok(statuses);
        } catch (Exception e) {
            LOG.error("Error getting distinct statuses", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get distinct statuses: " + e.getMessage());
        }
    }

    /**
     * Check if billing entry exists by ID
     */
    @RequestMapping(value = CHECK_EXISTS_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> checkExistsById(@RequestParam String lineId) {
        try {
            LOG.info("Checking if billing entry exists by line ID: {}", lineId);
            boolean exists = billingDrawingEntryService.existsById(lineId);
            Map<String, Boolean> response = new HashMap<>();
            response.put("exists", exists);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOG.error("Error checking existence by line ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to check existence: " + e.getMessage());
        }
    }

    /**
     * Check if billing entry exists by drawing number and mark number
     */
    @RequestMapping(value = CHECK_EXISTS_BY_DRAWING_NO_AND_MARK_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> checkExistsByDrawingNoAndMarkNo(
            @RequestParam String drawingNo,
            @RequestParam String markNo) {
        try {
            LOG.info("Checking if billing entry exists by drawing number: {} and mark number: {}", drawingNo, markNo);
            boolean exists = billingDrawingEntryService.existsByDrawingNoAndMarkNo(drawingNo, markNo);
            Map<String, Boolean> response = new HashMap<>();
            response.put("exists", exists);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOG.error("Error checking existence by drawing number and mark number", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to check existence: " + e.getMessage());
        }
    }

    /**
     * Check if billing entry exists by drawing number, mark number, and status
     */
    @RequestMapping(value = CHECK_EXISTS_BY_DRAWING_NO_AND_MARK_NO_AND_STATUS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> checkExistsByDrawingNoAndMarkNoAndStatus(
            @RequestParam String drawingNo,
            @RequestParam String markNo,
            @RequestParam String status) {
        try {
            LOG.info("Checking if billing entry exists by drawing number: {}, mark number: {}, and status: {}", 
                    drawingNo, markNo, status);
            boolean exists = billingDrawingEntryService.existsByDrawingNoAndMarkNoAndStatus(drawingNo, markNo, status);
            Map<String, Boolean> response = new HashMap<>();
            response.put("exists", exists);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOG.error("Error checking existence by drawing number, mark number, and status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to check existence: " + e.getMessage());
        }
    }

    /**
     * Get total count of billing entries
     */
    @RequestMapping(value = GET_TOTAL_COUNT, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getTotalCount() {
        try {
            LOG.info("Getting total count of billing entries");
            long count = billingDrawingEntryService.getTotalCount();
            Map<String, Long> response = new HashMap<>();
            response.put("count", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOG.error("Error getting total count", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get total count: " + e.getMessage());
        }
    }

    /**
     * Get latest billing entry by drawing number
     */
    @RequestMapping(value = GET_LATEST_BY_DRAWING_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getLatestByDrawingNo(@RequestParam String drawingNo) {
        try {
            LOG.info("Getting latest billing entry by drawing number: {}", drawingNo);
            Optional<BillingDrawingEntryDto> latestEntry = billingDrawingEntryService.getLatestByDrawingNo(drawingNo);
            
            if (latestEntry.isPresent()) {
                return ResponseEntity.ok(latestEntry.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOG.error("Error getting latest entry by drawing number", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get latest entry: " + e.getMessage());
        }
    }

    /**
     * Get latest billing entry by mark number
     */
    @RequestMapping(value = GET_LATEST_BY_MARK_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getLatestByMarkNo(@RequestParam String markNo) {
        try {
            LOG.info("Getting latest billing entry by mark number: {}", markNo);
            Optional<BillingDrawingEntryDto> latestEntry = billingDrawingEntryService.getLatestByMarkNo(markNo);
            
            if (latestEntry.isPresent()) {
                return ResponseEntity.ok(latestEntry.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOG.error("Error getting latest entry by mark number", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get latest entry: " + e.getMessage());
        }
    }

    /**
     * Get latest billing entry by status
     */
    @RequestMapping(value = GET_LATEST_BY_STATUS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getLatestByStatus(@RequestParam String status) {
        try {
            LOG.info("Getting latest billing entry by status: {}", status);
            Optional<BillingDrawingEntryDto> latestEntry = billingDrawingEntryService.getLatestByStatus(status);
            
            if (latestEntry.isPresent()) {
                return ResponseEntity.ok(latestEntry.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOG.error("Error getting latest entry by status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get latest entry: " + e.getMessage());
        }
    }

    /**
     * Get all billing entries (non-paginated)
     */
    @RequestMapping(value = "/getAllBillingDrawingEntriesComplete/details", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllBillingEntriesComplete() {
        try {
            LOG.info("Fetching all billing drawing entries with complete data");
            List<BillingDrawingEntryDto> billingEntries = billingDrawingEntryService.getAllBillingEntries();
            return ResponseEntity.ok(billingEntries);
        } catch (Exception e) {
            LOG.error("Error getting all billing entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get billing entries: " + e.getMessage());
        }
    }

    /**
     * Get billing entries by drawing number with complete data
     */
    @RequestMapping(value = "/getBillingEntriesByDrawingNoComplete/details", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getBillingEntriesByDrawingNoComplete(@RequestParam String drawingNo) {
        try {
            LOG.info("Fetching complete billing entries by drawing number: {}", drawingNo);
            List<BillingDrawingEntryDto> billingEntries = billingDrawingEntryService.getBillingEntriesByDrawingNo(drawingNo);
            return ResponseEntity.ok(billingEntries);
        } catch (Exception e) {
            LOG.error("Error getting billing entries by drawing number: {}", drawingNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get billing entries: " + e.getMessage());
        }
    }

    /**
     * Get billing entries by mark number with complete data
     */
    @RequestMapping(value = "/getBillingEntriesByMarkNoComplete/details", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getBillingEntriesByMarkNoComplete(@RequestParam String markNo) {
        try {
            LOG.info("Fetching complete billing entries by mark number: {}", markNo);
            List<BillingDrawingEntryDto> billingEntries = billingDrawingEntryService.getBillingEntriesByMarkNo(markNo);
            return ResponseEntity.ok(billingEntries);
        } catch (Exception e) {
            LOG.error("Error getting billing entries by mark number: {}", markNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get billing entries: " + e.getMessage());
        }
    }
}
