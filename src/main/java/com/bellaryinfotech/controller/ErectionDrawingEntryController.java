package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.ErectionDrawingEntryDto;
import com.bellaryinfotech.service.ErectionDrawingEntryService;
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
public class ErectionDrawingEntryController {
	
	 private static final Logger logger = LoggerFactory.getLogger(ErectionDrawingEntryController.class);

	   

    @Autowired
    private ErectionDrawingEntryService erectionDrawingEntryService;

    // API endpoint constants
    public static final String GET_ALL_ERECTION_ENTRIES = "/getAllErectionDrawingEntries/details";
    public static final String GET_UNIQUE_ERECTION_ENTRIES = "/getUniqueErectionDrawingEntries/details";
    public static final String GET_ERECTION_ENTRY_BY_ID = "/getErectionDrawingEntryById/details";
    public static final String CREATE_ERECTION_ENTRY = "/createErectionDrawingEntry/details";
    public static final String CREATE_BULK_ERECTION_ENTRIES = "/createBulkErectionDrawingEntries/details";
    public static final String UPDATE_ERECTION_ENTRY = "/updateErectionDrawingEntry/details";
    public static final String DELETE_ERECTION_ENTRY = "/deleteErectionDrawingEntry/details";
    public static final String DELETE_ERECTION_ENTRIES_BY_DRAWING_NO = "/deleteErectionDrawingEntriesByDrawingNo/details";
    public static final String DELETE_ERECTION_ENTRIES_BY_MARK_NO = "/deleteErectionDrawingEntriesByMarkNo/details";
    public static final String DELETE_ERECTION_ENTRIES_BY_STATUS = "/deleteErectionDrawingEntriesByStatus/details";
    public static final String BULK_DELETE_ERECTION_ENTRIES = "/bulkDeleteErectionDrawingEntries/details";
    public static final String SEARCH_BY_DRAWING_NO = "/searchErectionDrawingEntriesByDrawingNo/details";
    public static final String SEARCH_BY_MARK_NO = "/searchErectionDrawingEntriesByMarkNo/details";
    public static final String SEARCH_BY_SESSION_CODE = "/searchErectionDrawingEntriesBySessionCode/details";
    public static final String SEARCH_BY_TENANT_ID = "/searchErectionDrawingEntriesByTenantId/details";
    public static final String SEARCH_BY_STATUS = "/searchErectionDrawingEntriesByStatus/details";
    public static final String SEARCH_BY_MULTIPLE_CRITERIA = "/searchErectionDrawingEntries/details";
    public static final String SEARCH_BY_DATE_RANGE = "/searchErectionDrawingEntriesByDateRange/details";
    public static final String SEARCH_BY_MARKED_QTY_GREATER_THAN = "/searchErectionDrawingEntriesByMarkedQtyGreaterThan/details";
    public static final String GET_COUNT_BY_DRAWING_NO = "/getErectionDrawingEntryCountByDrawingNo/details";
    public static final String GET_COUNT_BY_MARK_NO = "/getErectionDrawingEntryCountByMarkNo/details";
    public static final String GET_COUNT_BY_STATUS = "/getErectionDrawingEntryCountByStatus/details";
    public static final String GET_SUM_MARKED_QTY_BY_DRAWING_NO = "/getErectionDrawingEntrySumMarkedQtyByDrawingNo/details";
    public static final String GET_SUM_TOTAL_MARKED_WGT_BY_DRAWING_NO = "/getErectionDrawingEntrySumTotalMarkedWgtByDrawingNo/details";
    public static final String GET_DISTINCT_DRAWING_NUMBERS = "/getDistinctErectionDrawingEntryDrawingNumbers/details";
    public static final String GET_DISTINCT_MARK_NUMBERS = "/getDistinctErectionDrawingEntryMarkNumbers/details";
    public static final String GET_DISTINCT_SESSION_CODES = "/getDistinctErectionDrawingEntrySessionCodes/details";
    public static final String GET_DISTINCT_STATUSES = "/getDistinctErectionDrawingEntryStatuses/details";
    public static final String CHECK_EXISTS_BY_ID = "/checkErectionDrawingEntryExistsById/details";
    public static final String CHECK_EXISTS_BY_DRAWING_NO_AND_MARK_NO = "/checkErectionDrawingEntryExistsByDrawingNoAndMarkNo/details";
    public static final String CHECK_EXISTS_BY_DRAWING_NO_AND_MARK_NO_AND_STATUS = "/checkErectionDrawingEntryExistsByDrawingNoAndMarkNoAndStatus/details";
    public static final String GET_TOTAL_COUNT = "/getErectionDrawingEntryTotalCount/details";
    public static final String GET_LATEST_BY_DRAWING_NO = "/getLatestErectionDrawingEntryByDrawingNo/details";
    public static final String GET_LATEST_BY_MARK_NO = "/getLatestErectionDrawingEntryByMarkNo/details";
    public static final String GET_LATEST_BY_STATUS = "/getLatestErectionDrawingEntryByStatus/details";

    private static final Logger LOG = LoggerFactory.getLogger(ErectionDrawingEntryController.class);

    /**
     * Get all erection entries with pagination
     */
    @RequestMapping(value = GET_ALL_ERECTION_ENTRIES, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllErectionEntries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "creationDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            LOG.info("Fetching all erection drawing entries with pagination");
            
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<ErectionDrawingEntryDto> erectionEntries = erectionDrawingEntryService.getAllErectionEntries(pageable);
            return ResponseEntity.ok(erectionEntries);
        } catch (Exception e) {
            LOG.error("Error getting all erection entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get erection entries: " + e.getMessage());
        }
    }

    /**
     * Get unique erection entries
     */
    @RequestMapping(value = GET_UNIQUE_ERECTION_ENTRIES, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getUniqueErectionEntries() {
        try {
            LOG.info("Fetching unique erection drawing entries");
            List<ErectionDrawingEntryDto> uniqueEntries = erectionDrawingEntryService.getUniqueDrawingMarkCombinations();
            return ResponseEntity.ok(uniqueEntries);
        } catch (Exception e) {
            LOG.error("Error getting unique erection entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get unique erection entries: " + e.getMessage());
        }
    }

    /**
     * Get erection entry by line ID
     */
    @RequestMapping(value = GET_ERECTION_ENTRY_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getErectionEntryById(@RequestParam String lineId) {
        try {
            LOG.info("Fetching erection entry by line ID: {}", lineId);
            Optional<ErectionDrawingEntryDto> erectionEntry = erectionDrawingEntryService.getErectionEntryById(lineId);
            
            if (erectionEntry.isPresent()) {
                return ResponseEntity.ok(erectionEntry.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOG.error("Error getting erection entry by line ID: {}", lineId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get erection entry: " + e.getMessage());
        }
    }

    /**
     * Create a new erection entry
     */
    @RequestMapping(value = CREATE_ERECTION_ENTRY, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<?> createErectionEntry(@RequestBody ErectionDrawingEntryDto erectionEntryDto) {  // Removed @Valid
        try {
            LOG.info("Creating new erection entry for drawing number: {}", erectionEntryDto.getDrawingNo());
            List<ErectionDrawingEntryDto> createdEntries = erectionDrawingEntryService.createErectionEntry(erectionEntryDto);
            LOG.info("Successfully created {} erection entries", createdEntries.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEntries);
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid input for creating erection entry: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error creating erection entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create erection entry: " + e.getMessage());
        }
    }

    /**
     * Create multiple erection entries
     */
    @RequestMapping(value = CREATE_BULK_ERECTION_ENTRIES, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<?> createErectionEntries(@RequestBody List<ErectionDrawingEntryDto> erectionEntryDtos) {  // Removed @Valid
        try {
            LOG.info("Creating {} erection entries", erectionEntryDtos.size());
            List<ErectionDrawingEntryDto> createdEntries = erectionDrawingEntryService.createErectionEntries(erectionEntryDtos);
            LOG.info("Successfully created {} total erection entries", createdEntries.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEntries);
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid input for creating erection entries: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error creating erection entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create erection entries: " + e.getMessage());
        }
    }

    /**
     * Update erection entry
     */
    @RequestMapping(value = UPDATE_ERECTION_ENTRY, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity<?> updateErectionEntry(
            @RequestParam String lineId,
            @Valid @RequestBody ErectionDrawingEntryDto erectionEntryDto) {
        try {
            LOG.info("Updating erection entry with line ID: {}", lineId);
            ErectionDrawingEntryDto updatedEntry = erectionDrawingEntryService.updateErectionEntry(lineId, erectionEntryDto);
            return ResponseEntity.ok(updatedEntry);
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid input for updating erection entry: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            LOG.error("Error updating erection entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update erection entry: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error updating erection entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update erection entry: " + e.getMessage());
        }
    }

    /**
     * Delete erection entry by line ID
     */
    @RequestMapping(value = DELETE_ERECTION_ENTRY, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteErectionEntry(@RequestParam String lineId) {
        try {
            LOG.info("Deleting erection entry with line ID: {}", lineId);
            erectionDrawingEntryService.deleteErectionEntry(lineId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            LOG.error("Error deleting erection entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete erection entry: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error deleting erection entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete erection entry: " + e.getMessage());
        }
    }

    /**
     * Get erection entries by drawing number
     */
    @RequestMapping(value = SEARCH_BY_DRAWING_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getErectionEntriesByDrawingNo(@RequestParam String drawingNo) {
        try {
            LOG.info("Searching erection entries by drawing number: {}", drawingNo);
            List<ErectionDrawingEntryDto> erectionEntries = erectionDrawingEntryService.getErectionEntriesByDrawingNo(drawingNo);
            return ResponseEntity.ok(erectionEntries);
        } catch (Exception e) {
            LOG.error("Error getting erection entries by drawing number: {}", drawingNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get erection entries: " + e.getMessage());
        }
    }

    /**
     * Get erection entries by mark number
     */
    @RequestMapping(value = SEARCH_BY_MARK_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getErectionEntriesByMarkNo(@RequestParam String markNo) {
        try {
            LOG.info("Searching erection entries by mark number: {}", markNo);
            List<ErectionDrawingEntryDto> erectionEntries = erectionDrawingEntryService.getErectionEntriesByMarkNo(markNo);
            return ResponseEntity.ok(erectionEntries);
        } catch (Exception e) {
            LOG.error("Error getting erection entries by mark number: {}", markNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get erection entries: " + e.getMessage());
        }
    }

    /**
     * Get erection entries by status
     */
    @RequestMapping(value = SEARCH_BY_STATUS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getErectionEntriesByStatus(
            @RequestParam String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "creationDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            LOG.info("Searching erection entries by status: {}", status);
            
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<ErectionDrawingEntryDto> erectionEntries = erectionDrawingEntryService.getErectionEntriesByStatus(status, pageable);
            return ResponseEntity.ok(erectionEntries);
        } catch (Exception e) {
            LOG.error("Error getting erection entries by status: {}", status, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get erection entries: " + e.getMessage());
        }
    }

    /**
     * Get distinct drawing numbers
     */
    @RequestMapping(value = GET_DISTINCT_DRAWING_NUMBERS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDistinctDrawingNumbers() {
        try {
            LOG.info("Getting distinct drawing numbers");
            List<String> drawingNumbers = erectionDrawingEntryService.getDistinctDrawingNumbers();
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
            List<String> markNumbers = erectionDrawingEntryService.getDistinctMarkNumbers();
            return ResponseEntity.ok(markNumbers);
        } catch (Exception e) {
            LOG.error("Error getting distinct mark numbers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get distinct mark numbers: " + e.getMessage());
        }
    }

    /**
     * Get total count of all entries
     */
    @RequestMapping(value = GET_TOTAL_COUNT, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getTotalCount() {
        try {
            LOG.info("Getting total count of erection entries");
            long count = erectionDrawingEntryService.getTotalCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            LOG.error("Error getting total count", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get total count: " + e.getMessage());
        }
    }
    
    
    
    
    
    
    
    
    
    
    @PostMapping("/createBulkErectionDrawingEntriesWithDuplicateCheck/details")
    public ResponseEntity<?> createBulkErectionDrawingEntriesWithDuplicateCheck(@RequestBody List<ErectionDrawingEntryDto> erectionEntryDtos) {
        try {
            logger.info("Creating bulk erection entries with duplicate checking for {} entries", erectionEntryDtos.size());

            if (erectionEntryDtos == null || erectionEntryDtos.isEmpty()) {
                return ResponseEntity.badRequest().body("Request body cannot be null or empty");
            }

            // Track what happens to each entry
            List<ErectionDrawingEntryDto> createdEntries = new ArrayList<>();
            List<String> skippedDuplicates = new ArrayList<>();

            for (ErectionDrawingEntryDto dto : erectionEntryDtos) {
                try {
                    // Check if this combination already exists
                    boolean exists = erectionDrawingEntryService.existsByDrawingNoAndMarkNo(dto.getDrawingNo(), dto.getMarkNo());

                    if (exists) {
                        skippedDuplicates.add(dto.getDrawingNo() + " - " + dto.getMarkNo());
                        logger.info("Skipped duplicate: Drawing {} - Mark {}", dto.getDrawingNo(), dto.getMarkNo());
                    } else {
                        // Create single entry (not multiple based on quantity)
                        List<ErectionDrawingEntryDto> singleEntryList = Arrays.asList(dto);
                        List<ErectionDrawingEntryDto> created = erectionDrawingEntryService.createErectionEntries(singleEntryList);
                        createdEntries.addAll(created);
                        logger.info("Created entry: Drawing {} - Mark {}", dto.getDrawingNo(), dto.getMarkNo());
                    }
                } catch (Exception e) {
                    logger.error("Error processing entry for Drawing {} - Mark {}: {}",
                            dto.getDrawingNo(), dto.getMarkNo(), e.getMessage());
                    // Continue with other entries
                }
            }

            // Create response with summary
            Map<String, Object> response = new HashMap<>();
            response.put("createdEntries", createdEntries);
            response.put("createdCount", createdEntries.size());
            response.put("skippedDuplicates", skippedDuplicates);
            response.put("skippedCount", skippedDuplicates.size());
            response.put("totalProcessed", erectionEntryDtos.size());
            response.put("message", String.format("Processed %d entries: %d created, %d skipped as duplicates",
                    erectionEntryDtos.size(), createdEntries.size(), skippedDuplicates.size()));

            logger.info("Bulk creation completed - Created: {}, Skipped: {}", createdEntries.size(), skippedDuplicates.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error in bulk creation with duplicate checking", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating erection entries: " + e.getMessage());
        }
    }
    
    
    
    
    
    
}
