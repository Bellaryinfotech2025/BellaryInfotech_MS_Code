package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.ErectionDrawingEntryDto;
import com.bellaryinfotech.DTO.BitsDrawingEntryDto;
import com.bellaryinfotech.service.ErectionDrawingEntryService;
import com.bellaryinfotech.service.BitsDrawingEntryService;
import com.bellaryinfotech.service.BitsHeaderService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class ErectionDrawingEntryController {
	
    private static final Logger logger = LoggerFactory.getLogger(ErectionDrawingEntryController.class);

    @Autowired
    private ErectionDrawingEntryService erectionDrawingEntryService;

    @Autowired
    private BitsDrawingEntryService bitsDrawingEntryService;

    @Autowired
    private BitsHeaderService bitsHeaderService;

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

    // NEW ENDPOINT FOR ENHANCED DATA WITH FABRICATION STAGES
    public static final String GET_ENHANCED_ERECTION_ENTRIES = "/getEnhancedErectionDrawingEntries/details";

    // NEW ENDPOINTS FOR WORK ORDER AND PLANT LOCATION DROPDOWNS
    public static final String GET_DISTINCT_WORK_ORDERS = "/getDistinctErectionWorkOrders/details";
    public static final String GET_DISTINCT_PLANT_LOCATIONS = "/getDistinctErectionPlantLocations/details";

    private static final Logger LOG = LoggerFactory.getLogger(ErectionDrawingEntryController.class);

    /**
     * Get distinct work orders from erection entries only
     */
    @RequestMapping(value = GET_DISTINCT_WORK_ORDERS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDistinctWorkOrders() {
        try {
            LOG.info("Fetching distinct work orders from erection entries");
        
            // Get all erection entries and extract unique work orders from attribute1V
            List<ErectionDrawingEntryDto> allEntries = erectionDrawingEntryService.getAllErectionEntries();
            List<String> workOrders = allEntries.stream()
                .map(ErectionDrawingEntryDto::getAttribute1V)
                .filter(workOrder -> workOrder != null && !workOrder.trim().isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        
            LOG.info("Found {} distinct work orders from erection entries", workOrders.size());
            return ResponseEntity.ok(workOrders);
        
        } catch (Exception e) {
            LOG.error("Error getting distinct work orders from erection entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to get distinct work orders: " + e.getMessage());
        }
    }

    /**
     * Get distinct plant locations from erection entries only
     */
    @RequestMapping(value = GET_DISTINCT_PLANT_LOCATIONS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDistinctPlantLocations() {
        try {
            LOG.info("Fetching distinct plant locations from erection entries");
        
            // Get all erection entries and extract unique plant locations from attribute2V
            List<ErectionDrawingEntryDto> allEntries = erectionDrawingEntryService.getAllErectionEntries();
            List<String> plantLocations = allEntries.stream()
                .map(ErectionDrawingEntryDto::getAttribute2V)
                .filter(location -> location != null && !location.trim().isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        
            LOG.info("Found {} distinct plant locations from erection entries", plantLocations.size());
            return ResponseEntity.ok(plantLocations);
        
        } catch (Exception e) {
            LOG.error("Error getting distinct plant locations from erection entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to get distinct plant locations: " + e.getMessage());
        }
    }

    /**
     * NEW: Get enhanced erection entries with fabrication stage data synchronized
     */
    @RequestMapping(value = GET_ENHANCED_ERECTION_ENTRIES, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getEnhancedErectionEntries() {
        try {
            LOG.info("Fetching enhanced erection entries with fabrication stage synchronization");
            
            // Get all erection entries
            List<ErectionDrawingEntryDto> erectionEntries = erectionDrawingEntryService.getAllErectionEntries();
            
            // Synchronize fabrication stage data for each entry
            for (ErectionDrawingEntryDto erectionEntry : erectionEntries) {
                try {
                    // Find corresponding fabrication entry by drawing and mark number
                    List<BitsDrawingEntryDto> fabricationEntries = bitsDrawingEntryService.getDrawingEntriesByDrawingNo(erectionEntry.getDrawingNo());
                    
                    // Find the specific entry with matching mark number
                    Optional<BitsDrawingEntryDto> matchingFabEntry = fabricationEntries.stream()
                            .filter(fab -> fab.getMarkNo() != null && fab.getMarkNo().equals(erectionEntry.getMarkNo()))
                            .findFirst();
                    
                    if (matchingFabEntry.isPresent()) {
                        BitsDrawingEntryDto fabEntry = matchingFabEntry.get();
                        
                        // Synchronize fabrication stage data
                        erectionEntry.setCuttingStage(fabEntry.getCuttingStage() != null ? fabEntry.getCuttingStage() : "N");
                        erectionEntry.setFitUpStage(fabEntry.getFitUpStage() != null ? fabEntry.getFitUpStage() : "N");
                        erectionEntry.setWeldingStage(fabEntry.getWeldingStage() != null ? fabEntry.getWeldingStage() : "N");
                        erectionEntry.setFinishingStage(fabEntry.getFinishingStage() != null ? fabEntry.getFinishingStage() : "N");
                        
                        // Synchronize additional fields if available
                        if (fabEntry.getDrawingWeight() != null) {
                            erectionEntry.setDrawingWeight(fabEntry.getDrawingWeight());
                        }
                        if (fabEntry.getMarkWeight() != null) {
                            erectionEntry.setMarkWeight(fabEntry.getMarkWeight());
                        }
                        if (fabEntry.getDrawingReceivedDate() != null) {
                            erectionEntry.setDrawingReceivedDate(fabEntry.getDrawingReceivedDate());
                        }
                        if (fabEntry.getTargetDate() != null) {
                            erectionEntry.setTargetDate(fabEntry.getTargetDate());
                        }
                        if (fabEntry.getRaNo() != null) {
                            erectionEntry.setRaNo(fabEntry.getRaNo());
                        }
                        
                        LOG.debug("Synchronized fabrication data for Drawing: {}, Mark: {}", 
                                erectionEntry.getDrawingNo(), erectionEntry.getMarkNo());
                    } else {
                        // Set default values if no fabrication entry found
                        erectionEntry.setCuttingStage("N");
                        erectionEntry.setFitUpStage("N");
                        erectionEntry.setWeldingStage("N");
                        erectionEntry.setFinishingStage("N");
                    }
                } catch (Exception e) {
                    LOG.warn("Error synchronizing fabrication data for Drawing: {}, Mark: {} - {}",
                            erectionEntry.getDrawingNo(), erectionEntry.getMarkNo(), e.getMessage());
                    // Continue with other entries
                }
            }
            
            LOG.info("Successfully synchronized fabrication data for {} erection entries", erectionEntries.size());
            return ResponseEntity.ok(erectionEntries);
            
        } catch (Exception e) {
            LOG.error("Error getting enhanced erection entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get enhanced erection entries: " + e.getMessage());
        }
    }

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
    public ResponseEntity<?> createErectionEntry(@RequestBody ErectionDrawingEntryDto erectionEntryDto) {
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
    public ResponseEntity<?> createErectionEntries(@RequestBody List<ErectionDrawingEntryDto> erectionEntryDtos) {
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
     * Search erection entries by multiple criteria
     */
    @RequestMapping(value = SEARCH_BY_MULTIPLE_CRITERIA, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchErectionEntries(
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
            LOG.info("Searching erection entries with multiple criteria");
            
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<ErectionDrawingEntryDto> erectionEntries = erectionDrawingEntryService.searchErectionEntries(
                    drawingNo, markNo, sessionCode, tenantId, status, pageable);
            return ResponseEntity.ok(erectionEntries);
        } catch (Exception e) {
            LOG.error("Error searching erection entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search erection entries: " + e.getMessage());
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
    
    /**
     * FIXED: Create bulk erection entries WITHOUT any duplicate checking - Store ALL entries
     */
    @PostMapping("/createBulkErectionDrawingEntriesWithDuplicateCheck/details")
    public ResponseEntity<?> createBulkErectionDrawingEntriesWithDuplicateCheck(@RequestBody List<ErectionDrawingEntryDto> erectionEntryDtos) {
        try {
            logger.info("Creating bulk erection entries WITHOUT duplicate checking for {} entries", erectionEntryDtos.size());

            if (erectionEntryDtos == null || erectionEntryDtos.isEmpty()) {
                return ResponseEntity.badRequest().body("Request body cannot be null or empty");
            }

            // DIRECTLY USE THE SERVICE METHOD THAT DOESN'T CHECK DUPLICATES
            List<ErectionDrawingEntryDto> createdEntries = erectionDrawingEntryService.createErectionEntries(erectionEntryDtos);

            // Create response with summary - ALL ENTRIES CREATED
            Map<String, Object> response = new HashMap<>();
            response.put("createdEntries", createdEntries);
            response.put("createdCount", createdEntries.size());
            response.put("skippedDuplicates", new ArrayList<>()); // Empty list - no duplicates skipped
            response.put("skippedCount", 0); // Zero skipped
            response.put("totalProcessed", erectionEntryDtos.size());
            response.put("message", String.format("Successfully processed %d entries: %d created, 0 skipped - ALL ENTRIES STORED",
                    erectionEntryDtos.size(), createdEntries.size()));

            logger.info("Bulk creation completed - Created: {}, Skipped: 0 (ALL ENTRIES STORED)", createdEntries.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error in bulk creation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating erection entries: " + e.getMessage());
        }
    }

    /**
     * Get all erection entries with complete data (non-paginated)
     */
    @RequestMapping(value = "/getAllErectionDrawingEntriesComplete/details", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllErectionEntriesComplete() {
        try {
            LOG.info("Fetching all erection drawing entries with complete data");
            List<ErectionDrawingEntryDto> erectionEntries = erectionDrawingEntryService.getAllErectionEntries();
            return ResponseEntity.ok(erectionEntries);
        } catch (Exception e) {
            LOG.error("Error getting all erection entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get erection entries: " + e.getMessage());
        }
    }

    /**
     * Get erection entries by drawing number with complete data
     */
    @RequestMapping(value = "/getErectionEntriesByDrawingNoComplete/details", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getErectionEntriesByDrawingNoComplete(@RequestParam String drawingNo) {
        try {
            LOG.info("Fetching complete erection entries by drawing number: {}", drawingNo);
            List<ErectionDrawingEntryDto> erectionEntries = erectionDrawingEntryService.getErectionEntriesByDrawingNo(drawingNo);
            return ResponseEntity.ok(erectionEntries);
        } catch (Exception e) {
            LOG.error("Error getting erection entries by drawing number: {}", drawingNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get erection entries: " + e.getMessage());
        }
    }

    /**
     * Get erection entries by mark number with complete data
     */
    @RequestMapping(value = "/getErectionEntriesByMarkNoComplete/details", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getErectionEntriesByMarkNoComplete(@RequestParam String markNo) {
        try {
            LOG.info("Fetching complete erection entries by mark number: {}", markNo);
            List<ErectionDrawingEntryDto> erectionEntries = erectionDrawingEntryService.getErectionEntriesByMarkNo(markNo);
            return ResponseEntity.ok(erectionEntries);
        } catch (Exception e) {
            LOG.error("Error getting erection entries by mark number: {}", markNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get erection entries: " + e.getMessage());
        }
    }
}
