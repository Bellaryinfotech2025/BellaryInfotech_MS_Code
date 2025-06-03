package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.AlignmentDrawingEntryDto;
import com.bellaryinfotech.service.AlignmentDrawingEntryService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class AlignmentDrawingEntryController {
	
    private static final Logger logger = LoggerFactory.getLogger(AlignmentDrawingEntryController.class);

    @Autowired
    private AlignmentDrawingEntryService alignmentDrawingEntryService;

    // API endpoint constants
    public static final String GET_ALL_ALIGNMENT_ENTRIES = "/getAllAlignmentDrawingEntries/details";
    public static final String GET_UNIQUE_ALIGNMENT_ENTRIES = "/getUniqueAlignmentDrawingEntries/details";
    public static final String GET_ALIGNMENT_ENTRY_BY_ID = "/getAlignmentDrawingEntryById/details";
    public static final String CREATE_ALIGNMENT_ENTRY = "/createAlignmentDrawingEntry/details";
    public static final String CREATE_BULK_ALIGNMENT_ENTRIES = "/createBulkAlignmentDrawingEntries/details";
    public static final String UPDATE_ALIGNMENT_ENTRY = "/updateAlignmentDrawingEntry/details";
    public static final String DELETE_ALIGNMENT_ENTRY = "/deleteAlignmentDrawingEntry/details";
    public static final String DELETE_ALIGNMENT_ENTRIES_BY_DRAWING_NO = "/deleteAlignmentDrawingEntriesByDrawingNo/details";
    public static final String DELETE_ALIGNMENT_ENTRIES_BY_MARK_NO = "/deleteAlignmentDrawingEntriesByMarkNo/details";
    public static final String DELETE_ALIGNMENT_ENTRIES_BY_STATUS = "/deleteAlignmentDrawingEntriesByStatus/details";
    public static final String BULK_DELETE_ALIGNMENT_ENTRIES = "/bulkDeleteAlignmentDrawingEntries/details";
    public static final String SEARCH_BY_DRAWING_NO = "/searchAlignmentDrawingEntriesByDrawingNo/details";
    public static final String SEARCH_BY_MARK_NO = "/searchAlignmentDrawingEntriesByMarkNo/details";
    public static final String SEARCH_BY_SESSION_CODE = "/searchAlignmentDrawingEntriesBySessionCode/details";
    public static final String SEARCH_BY_TENANT_ID = "/searchAlignmentDrawingEntriesByTenantId/details";
    public static final String SEARCH_BY_STATUS = "/searchAlignmentDrawingEntriesByStatus/details";
    public static final String SEARCH_BY_MULTIPLE_CRITERIA = "/searchAlignmentDrawingEntries/details";
    public static final String SEARCH_BY_DATE_RANGE = "/searchAlignmentDrawingEntriesByDateRange/details";
    public static final String SEARCH_BY_MARKED_QTY_GREATER_THAN = "/searchAlignmentDrawingEntriesByMarkedQtyGreaterThan/details";
    public static final String GET_COUNT_BY_DRAWING_NO = "/getAlignmentDrawingEntryCountByDrawingNo/details";
    public static final String GET_COUNT_BY_MARK_NO = "/getAlignmentDrawingEntryCountByMarkNo/details";
    public static final String GET_COUNT_BY_STATUS = "/getAlignmentDrawingEntryCountByStatus/details";
    public static final String GET_SUM_MARKED_QTY_BY_DRAWING_NO = "/getAlignmentDrawingEntrySumMarkedQtyByDrawingNo/details";
    public static final String GET_SUM_TOTAL_MARKED_WGT_BY_DRAWING_NO = "/getAlignmentDrawingEntrySumTotalMarkedWgtByDrawingNo/details";
    public static final String GET_DISTINCT_DRAWING_NUMBERS = "/getDistinctAlignmentDrawingEntryDrawingNumbers/details";
    public static final String GET_DISTINCT_MARK_NUMBERS = "/getDistinctAlignmentDrawingEntryMarkNumbers/details";
    public static final String GET_DISTINCT_SESSION_CODES = "/getDistinctAlignmentDrawingEntrySessionCodes/details";
    public static final String GET_DISTINCT_STATUSES = "/getDistinctAlignmentDrawingEntryStatuses/details";
    public static final String CHECK_EXISTS_BY_ID = "/checkAlignmentDrawingEntryExistsById/details";
    public static final String CHECK_EXISTS_BY_DRAWING_NO_AND_MARK_NO = "/checkAlignmentDrawingEntryExistsByDrawingNoAndMarkNo/details";
    public static final String CHECK_EXISTS_BY_DRAWING_NO_AND_MARK_NO_AND_STATUS = "/checkAlignmentDrawingEntryExistsByDrawingNoAndMarkNoAndStatus/details";
    public static final String GET_TOTAL_COUNT = "/getAlignmentDrawingEntryTotalCount/details";
    public static final String GET_LATEST_BY_DRAWING_NO = "/getLatestAlignmentDrawingEntryByDrawingNo/details";
    public static final String GET_LATEST_BY_MARK_NO = "/getLatestAlignmentDrawingEntryByMarkNo/details";
    public static final String GET_LATEST_BY_STATUS = "/getLatestAlignmentDrawingEntryByStatus/details";

    private static final Logger LOG = LoggerFactory.getLogger(AlignmentDrawingEntryController.class);

    /**
     * Get all alignment entries with pagination
     */
    @RequestMapping(value = GET_ALL_ALIGNMENT_ENTRIES, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllAlignmentEntries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "creationDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            LOG.info("Fetching all alignment drawing entries with pagination");
            
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<AlignmentDrawingEntryDto> alignmentEntries = alignmentDrawingEntryService.getAllAlignmentEntries(pageable);
            return ResponseEntity.ok(alignmentEntries);
        } catch (Exception e) {
            LOG.error("Error getting all alignment entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get alignment entries: " + e.getMessage());
        }
    }

    /**
     * Get unique alignment entries
     */
    @RequestMapping(value = GET_UNIQUE_ALIGNMENT_ENTRIES, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getUniqueAlignmentEntries() {
        try {
            LOG.info("Fetching unique alignment drawing entries");
            List<AlignmentDrawingEntryDto> uniqueEntries = alignmentDrawingEntryService.getUniqueDrawingMarkCombinations();
            return ResponseEntity.ok(uniqueEntries);
        } catch (Exception e) {
            LOG.error("Error getting unique alignment entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get unique alignment entries: " + e.getMessage());
        }
    }

    /**
     * Get alignment entry by line ID
     */
    @RequestMapping(value = GET_ALIGNMENT_ENTRY_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAlignmentEntryById(@RequestParam String lineId) {
        try {
            LOG.info("Fetching alignment entry by line ID: {}", lineId);
            Optional<AlignmentDrawingEntryDto> alignmentEntry = alignmentDrawingEntryService.getAlignmentEntryById(lineId);
            
            if (alignmentEntry.isPresent()) {
                return ResponseEntity.ok(alignmentEntry.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOG.error("Error getting alignment entry by line ID: {}", lineId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get alignment entry: " + e.getMessage());
        }
    }

    /**
     * Create a new alignment entry
     */
    @RequestMapping(value = CREATE_ALIGNMENT_ENTRY, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<?> createAlignmentEntry(@RequestBody AlignmentDrawingEntryDto alignmentEntryDto) {
        try {
            LOG.info("Creating new alignment entry for drawing number: {}", alignmentEntryDto.getDrawingNo());
            List<AlignmentDrawingEntryDto> createdEntries = alignmentDrawingEntryService.createAlignmentEntry(alignmentEntryDto);
            LOG.info("Successfully created {} alignment entries", createdEntries.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEntries);
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid input for creating alignment entry: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error creating alignment entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create alignment entry: " + e.getMessage());
        }
    }

    /**
     * Create multiple alignment entries
     */
    @RequestMapping(value = CREATE_BULK_ALIGNMENT_ENTRIES, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<?> createAlignmentEntries(@RequestBody List<AlignmentDrawingEntryDto> alignmentEntryDtos) {
        try {
            LOG.info("Creating {} alignment entries", alignmentEntryDtos.size());
            List<AlignmentDrawingEntryDto> createdEntries = alignmentDrawingEntryService.createAlignmentEntries(alignmentEntryDtos);
            LOG.info("Successfully created {} total alignment entries", createdEntries.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEntries);
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid input for creating alignment entries: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error creating alignment entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create alignment entries: " + e.getMessage());
        }
    }

    /**
     * Update alignment entry
     */
    @RequestMapping(value = UPDATE_ALIGNMENT_ENTRY, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity<?> updateAlignmentEntry(
            @RequestParam String lineId,
            @Valid @RequestBody AlignmentDrawingEntryDto alignmentEntryDto) {
        try {
            LOG.info("Updating alignment entry with line ID: {}", lineId);
            AlignmentDrawingEntryDto updatedEntry = alignmentDrawingEntryService.updateAlignmentEntry(lineId, alignmentEntryDto);
            return ResponseEntity.ok(updatedEntry);
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid input for updating alignment entry: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            LOG.error("Error updating alignment entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update alignment entry: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error updating alignment entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update alignment entry: " + e.getMessage());
        }
    }

    /**
     * Delete alignment entry by line ID
     */
    @RequestMapping(value = DELETE_ALIGNMENT_ENTRY, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAlignmentEntry(@RequestParam String lineId) {
        try {
            LOG.info("Deleting alignment entry with line ID: {}", lineId);
            alignmentDrawingEntryService.deleteAlignmentEntry(lineId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            LOG.error("Error deleting alignment entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete alignment entry: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error deleting alignment entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete alignment entry: " + e.getMessage());
        }
    }

    /**
     * Delete alignment entries by drawing number
     */
    @RequestMapping(value = DELETE_ALIGNMENT_ENTRIES_BY_DRAWING_NO, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAlignmentEntriesByDrawingNo(@RequestParam String drawingNo) {
        try {
            LOG.info("Deleting alignment entries with drawing number: {}", drawingNo);
            alignmentDrawingEntryService.deleteAlignmentEntriesByDrawingNo(drawingNo);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error deleting alignment entries by drawing number", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete alignment entries: " + e.getMessage());
        }
    }

    /**
     * Delete alignment entries by mark number
     */
    @RequestMapping(value = DELETE_ALIGNMENT_ENTRIES_BY_MARK_NO, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAlignmentEntriesByMarkNo(@RequestParam String markNo) {
        try {
            LOG.info("Deleting alignment entries with mark number: {}", markNo);
            alignmentDrawingEntryService.deleteAlignmentEntriesByMarkNo(markNo);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error deleting alignment entries by mark number", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete alignment entries: " + e.getMessage());
        }
    }

    /**
     * Delete alignment entries by status
     */
    @RequestMapping(value = DELETE_ALIGNMENT_ENTRIES_BY_STATUS, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAlignmentEntriesByStatus(@RequestParam String status) {
        try {
            LOG.info("Deleting alignment entries with status: {}", status);
            alignmentDrawingEntryService.deleteAlignmentEntriesByStatus(status);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error deleting alignment entries by status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete alignment entries: " + e.getMessage());
        }
    }

    /**
     * Bulk delete alignment entries
     */
    @RequestMapping(value = BULK_DELETE_ALIGNMENT_ENTRIES, method = RequestMethod.DELETE)
    public ResponseEntity<?> bulkDeleteAlignmentEntries(@RequestParam String lineIds) {
        try {
            LOG.info("Bulk deleting alignment entries with line IDs: {}", lineIds);
            List<String> lineIdList = Arrays.asList(lineIds.split(","));
            alignmentDrawingEntryService.bulkDeleteAlignmentEntries(lineIdList);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error bulk deleting alignment entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete alignment entries: " + e.getMessage());
        }
    }

    /**
     * Search alignment entries by drawing number
     */
    @RequestMapping(value = SEARCH_BY_DRAWING_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchAlignmentEntriesByDrawingNo(@RequestParam String drawingNo) {
        try {
            LOG.info("Searching alignment entries by drawing number: {}", drawingNo);
            List<AlignmentDrawingEntryDto> alignmentEntries = alignmentDrawingEntryService.getAlignmentEntriesByDrawingNo(drawingNo);
            return ResponseEntity.ok(alignmentEntries);
        } catch (Exception e) {
            LOG.error("Error searching alignment entries by drawing number", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search alignment entries: " + e.getMessage());
        }
    }

    /**
     * Search alignment entries by mark number
     */
    @RequestMapping(value = SEARCH_BY_MARK_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchAlignmentEntriesByMarkNo(@RequestParam String markNo) {
        try {
            LOG.info("Searching alignment entries by mark number: {}", markNo);
            List<AlignmentDrawingEntryDto> alignmentEntries = alignmentDrawingEntryService.getAlignmentEntriesByMarkNo(markNo);
            return ResponseEntity.ok(alignmentEntries);
        } catch (Exception e) {
            LOG.error("Error searching alignment entries by mark number", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search alignment entries: " + e.getMessage());
        }
    }

    /**
     * Search alignment entries by session code
     */
    @RequestMapping(value = SEARCH_BY_SESSION_CODE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchAlignmentEntriesBySessionCode(@RequestParam String sessionCode) {
        try {
            LOG.info("Searching alignment entries by session code: {}", sessionCode);
            List<AlignmentDrawingEntryDto> alignmentEntries = alignmentDrawingEntryService.getAlignmentEntriesBySessionCode(sessionCode);
            return ResponseEntity.ok(alignmentEntries);
        } catch (Exception e) {
            LOG.error("Error searching alignment entries by session code", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search alignment entries: " + e.getMessage());
        }
    }

    /**
     * Search alignment entries by tenant ID
     */
    @RequestMapping(value = SEARCH_BY_TENANT_ID, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchAlignmentEntriesByTenantId(
            @RequestParam String tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "creationDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            LOG.info("Searching alignment entries by tenant ID: {}", tenantId);
            
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<AlignmentDrawingEntryDto> alignmentEntries = alignmentDrawingEntryService.getAlignmentEntriesByTenantId(tenantId, pageable);
            return ResponseEntity.ok(alignmentEntries);
        } catch (Exception e) {
            LOG.error("Error searching alignment entries by tenant ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search alignment entries: " + e.getMessage());
        }
    }

    /**
     * Search alignment entries by status
     */
    @RequestMapping(value = SEARCH_BY_STATUS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchAlignmentEntriesByStatus(
            @RequestParam String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "creationDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            LOG.info("Searching alignment entries by status: {}", status);
            
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<AlignmentDrawingEntryDto> alignmentEntries = alignmentDrawingEntryService.getAlignmentEntriesByStatus(status, pageable);
            return ResponseEntity.ok(alignmentEntries);
        } catch (Exception e) {
            LOG.error("Error searching alignment entries by status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search alignment entries: " + e.getMessage());
        }
    }

    /**
     * Search alignment entries by multiple criteria
     */
    @RequestMapping(value = SEARCH_BY_MULTIPLE_CRITERIA, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchAlignmentEntries(
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
            LOG.info("Searching alignment entries with multiple criteria");
            
            // Convert empty strings to null for proper search
            drawingNo = (drawingNo != null && drawingNo.trim().isEmpty()) ? null : drawingNo;
            markNo = (markNo != null && markNo.trim().isEmpty()) ? null : markNo;
            sessionCode = (sessionCode != null && sessionCode.trim().isEmpty()) ? null : sessionCode;
            tenantId = (tenantId != null && tenantId.trim().isEmpty()) ? null : tenantId;
            status = (status != null && status.trim().isEmpty()) ? null : status;
            
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<AlignmentDrawingEntryDto> alignmentEntries = alignmentDrawingEntryService.searchAlignmentEntries(
                    drawingNo, markNo, sessionCode, tenantId, status, pageable);
            return ResponseEntity.ok(alignmentEntries);
        } catch (Exception e) {
            LOG.error("Error searching alignment entries with multiple criteria", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search alignment entries: " + e.getMessage());
        }
    }

    /**
     * Search alignment entries by date range
     */
    @RequestMapping(value = SEARCH_BY_DATE_RANGE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchAlignmentEntriesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            LOG.info("Searching alignment entries by date range: {} to {}", startDate, endDate);
            List<AlignmentDrawingEntryDto> alignmentEntries = alignmentDrawingEntryService.getAlignmentEntriesByDateRange(startDate, endDate);
            return ResponseEntity.ok(alignmentEntries);
        } catch (Exception e) {
            LOG.error("Error searching alignment entries by date range", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search alignment entries: " + e.getMessage());
        }
    }

    /**
     * Search alignment entries by marked quantity greater than
     */
    @RequestMapping(value = SEARCH_BY_MARKED_QTY_GREATER_THAN, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchAlignmentEntriesByMarkedQtyGreaterThan(@RequestParam BigDecimal markedQty) {
        try {
            LOG.info("Searching alignment entries by marked quantity greater than: {}", markedQty);
            List<AlignmentDrawingEntryDto> alignmentEntries = alignmentDrawingEntryService.getAlignmentEntriesByMarkedQtyGreaterThan(markedQty);
            return ResponseEntity.ok(alignmentEntries);
        } catch (Exception e) {
            LOG.error("Error searching alignment entries by marked quantity", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search alignment entries: " + e.getMessage());
        }
    }

    /**
     * Get count by drawing number
     */
    @RequestMapping(value = GET_COUNT_BY_DRAWING_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getCountByDrawingNo(@RequestParam String drawingNo) {
        try {
            LOG.info("Getting count by drawing number: {}", drawingNo);
            Long count = alignmentDrawingEntryService.getCountByDrawingNo(drawingNo);
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
            Long count = alignmentDrawingEntryService.getCountByMarkNo(markNo);
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
            Long count = alignmentDrawingEntryService.getCountByStatus(status);
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
            BigDecimal sum = alignmentDrawingEntryService.getSumMarkedQtyByDrawingNo(drawingNo);
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
            BigDecimal sum = alignmentDrawingEntryService.getSumTotalMarkedWgtByDrawingNo(drawingNo);
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
            List<String> drawingNumbers = alignmentDrawingEntryService.getDistinctDrawingNumbers();
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
            List<String> markNumbers = alignmentDrawingEntryService.getDistinctMarkNumbers();
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
            List<String> sessionCodes = alignmentDrawingEntryService.getDistinctSessionCodes();
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
            List<String> statuses = alignmentDrawingEntryService.getDistinctStatuses();
            return ResponseEntity.ok(statuses);
        } catch (Exception e) {
            LOG.error("Error getting distinct statuses", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get distinct statuses: " + e.getMessage());
        }
    }

    /**
     * Check if alignment entry exists by ID
     */
    @RequestMapping(value = CHECK_EXISTS_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> checkExistsById(@RequestParam String lineId) {
        try {
            LOG.info("Checking if alignment entry exists by line ID: {}", lineId);
            boolean exists = alignmentDrawingEntryService.existsById(lineId);
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
     * Check if alignment entry exists by drawing number and mark number
     */
    @RequestMapping(value = CHECK_EXISTS_BY_DRAWING_NO_AND_MARK_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> checkExistsByDrawingNoAndMarkNo(
            @RequestParam String drawingNo,
            @RequestParam String markNo) {
        try {
            LOG.info("Checking if alignment entry exists by drawing number: {} and mark number: {}", drawingNo, markNo);
            boolean exists = alignmentDrawingEntryService.existsByDrawingNoAndMarkNo(drawingNo, markNo);
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
     * Check if alignment entry exists by drawing number, mark number, and status
     */
    @RequestMapping(value = CHECK_EXISTS_BY_DRAWING_NO_AND_MARK_NO_AND_STATUS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> checkExistsByDrawingNoAndMarkNoAndStatus(
            @RequestParam String drawingNo,
            @RequestParam String markNo,
            @RequestParam String status) {
        try {
            LOG.info("Checking if alignment entry exists by drawing number: {}, mark number: {}, and status: {}", 
                    drawingNo, markNo, status);
            boolean exists = alignmentDrawingEntryService.existsByDrawingNoAndMarkNoAndStatus(drawingNo, markNo, status);
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
     * Get total count of alignment entries
     */
    @RequestMapping(value = GET_TOTAL_COUNT, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getTotalCount() {
        try {
            LOG.info("Getting total count of alignment entries");
            long count = alignmentDrawingEntryService.getTotalCount();
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
     * Get latest alignment entry by drawing number
     */
    @RequestMapping(value = GET_LATEST_BY_DRAWING_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getLatestByDrawingNo(@RequestParam String drawingNo) {
        try {
            LOG.info("Getting latest alignment entry by drawing number: {}", drawingNo);
            Optional<AlignmentDrawingEntryDto> latestEntry = alignmentDrawingEntryService.getLatestByDrawingNo(drawingNo);
            
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
     * Get latest alignment entry by mark number
     */
    @RequestMapping(value = GET_LATEST_BY_MARK_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getLatestByMarkNo(@RequestParam String markNo) {
        try {
            LOG.info("Getting latest alignment entry by mark number: {}", markNo);
            Optional<AlignmentDrawingEntryDto> latestEntry = alignmentDrawingEntryService.getLatestByMarkNo(markNo);
            
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
     * Get latest alignment entry by status
     */
    @RequestMapping(value = GET_LATEST_BY_STATUS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getLatestByStatus(@RequestParam String status) {
        try {
            LOG.info("Getting latest alignment entry by status: {}", status);
            Optional<AlignmentDrawingEntryDto> latestEntry = alignmentDrawingEntryService.getLatestByStatus(status);
            
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
     * Create bulk completion drawing entries
     */
    @RequestMapping(value = "/createBulkCompletionDrawingEntries/details", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<?> createBulkCompletionDrawingEntries(@RequestBody List<AlignmentDrawingEntryDto> completionEntryDtos) {
        try {
            LOG.info("Creating {} completion entries", completionEntryDtos.size());
            
            // Ensure all entries have status set to "completed"
            completionEntryDtos.forEach(dto -> dto.setStatus("completed"));
            
            // Use the same service method but with "completed" status
            List<AlignmentDrawingEntryDto> createdEntries = alignmentDrawingEntryService.createAlignmentEntries(completionEntryDtos);
            
            LOG.info("Successfully created {} completion entries", createdEntries.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEntries);
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid input for creating completion entries: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error creating completion entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create completion entries: " + e.getMessage());
        }
    }

    /**
     * Get all alignment entries (non-paginated)
     */
    @RequestMapping(value = "/getAllAlignmentDrawingEntriesComplete/details", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllAlignmentEntriesComplete() {
        try {
            LOG.info("Fetching all alignment drawing entries with complete data");
            List<AlignmentDrawingEntryDto> alignmentEntries = alignmentDrawingEntryService.getAllAlignmentEntries();
            return ResponseEntity.ok(alignmentEntries);
        } catch (Exception e) {
            LOG.error("Error getting all alignment entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get alignment entries: " + e.getMessage());
        }
    }

    /**
     * Get alignment entries by drawing number with complete data
     */
    @RequestMapping(value = "/getAlignmentEntriesByDrawingNoComplete/details", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAlignmentEntriesByDrawingNoComplete(@RequestParam String drawingNo) {
        try {
            LOG.info("Fetching complete alignment entries by drawing number: {}", drawingNo);
            List<AlignmentDrawingEntryDto> alignmentEntries = alignmentDrawingEntryService.getAlignmentEntriesByDrawingNo(drawingNo);
            return ResponseEntity.ok(alignmentEntries);
        } catch (Exception e) {
            LOG.error("Error getting alignment entries by drawing number: {}", drawingNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get alignment entries: " + e.getMessage());
        }
    }

    /**
     * Get alignment entries by mark number with complete data
     */
    @RequestMapping(value = "/getAlignmentEntriesByMarkNoComplete/details", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAlignmentEntriesByMarkNoComplete(@RequestParam String markNo) {
        try {
            LOG.info("Fetching complete alignment entries by mark number: {}", markNo);
            List<AlignmentDrawingEntryDto> alignmentEntries = alignmentDrawingEntryService.getAlignmentEntriesByMarkNo(markNo);
            return ResponseEntity.ok(alignmentEntries);
        } catch (Exception e) {
            LOG.error("Error getting alignment entries by mark number: {}", markNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get alignment entries: " + e.getMessage());
        }
    }
}
