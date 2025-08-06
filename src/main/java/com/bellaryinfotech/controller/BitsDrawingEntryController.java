package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.BitsDrawingEntryDto;
import com.bellaryinfotech.DTO.BitsDrawingEntryStatsDto;
import com.bellaryinfotech.service.BitsDrawingEntryService;
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
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class BitsDrawingEntryController {
    @Autowired
    private BitsDrawingEntryService bitsDrawingEntryService;

    // API endpoint constants
    public static final String GET_ALL_DRAWING_ENTRIES = "/getAllBitsDrawingEntries/details";
    public static final String GET_UNIQUE_DRAWING_ENTRIES = "/getUniqueBitsDrawingEntries/details";
    public static final String GET_DRAWING_ENTRY_BY_ID = "/getBitsDrawingEntryById/details";
    public static final String CREATE_DRAWING_ENTRY = "/createBitsDrawingEntry/details";
    public static final String CREATE_BULK_DRAWING_ENTRIES = "/createBulkBitsDrawingEntries/details";
    public static final String UPDATE_DRAWING_ENTRY = "/updateBitsDrawingEntry/details";
    public static final String UPDATE_FABRICATION_STAGES = "/updateFabricationStages/details";
    public static final String UPDATE_DRAWING_ENTRY_RA_NO = "/updateDrawingEntryRaNo/details"; // RA NO endpoint
    public static final String DELETE_DRAWING_ENTRY = "/deleteBitsDrawingEntry/details";
    public static final String DELETE_DRAWING_ENTRIES_BY_DRAWING_NO = "/deleteBitsDrawingEntriesByDrawingNo/details";
    public static final String DELETE_DRAWING_ENTRIES_BY_MARK_NO = "/deleteBitsDrawingEntriesByMarkNo/details";
    public static final String BULK_DELETE_DRAWING_ENTRIES = "/bulkDeleteBitsDrawingEntries/details";
    public static final String SEARCH_BY_DRAWING_NO = "/searchBitsDrawingEntriesByDrawingNo/details";
    public static final String SEARCH_BY_MARK_NO = "/searchBitsDrawingEntriesByMarkNo/details";
    public static final String SEARCH_BY_SESSION_CODE = "/searchBitsDrawingEntriesBySessionCode/details";
    public static final String SEARCH_BY_TENANT_ID = "/searchBitsDrawingEntriesByTenantId/details";
    public static final String SEARCH_BY_MULTIPLE_CRITERIA = "/searchBitsDrawingEntries/details";
    public static final String SEARCH_BY_DATE_RANGE = "/searchBitsDrawingEntriesByDateRange/details";
    public static final String SEARCH_BY_MARKED_QTY_GREATER_THAN = "/searchBitsDrawingEntriesByMarkedQtyGreaterThan/details";
    public static final String GET_COUNT_BY_DRAWING_NO = "/getBitsDrawingEntryCountByDrawingNo/details";
    public static final String GET_SUM_MARKED_QTY_BY_DRAWING_NO = "/getBitsDrawingEntrySumMarkedQtyByDrawingNo/details";
    public static final String GET_SUM_TOTAL_MARKED_WGT_BY_DRAWING_NO = "/getBitsDrawingEntrySumTotalMarkedWgtByDrawingNo/details";
    public static final String GET_DISTINCT_DRAWING_NUMBERS = "/getDistinctBitsDrawingEntryDrawingNumbers/details";
    public static final String GET_DISTINCT_MARK_NUMBERS = "/getDistinctBitsDrawingEntryMarkNumbers/details";
    public static final String GET_DISTINCT_SESSION_CODES = "/getDistinctBitsDrawingEntrySessionCodes/details";
    public static final String CHECK_EXISTS_BY_ID = "/checkBitsDrawingEntryExistsById/details";
    public static final String CHECK_EXISTS_BY_DRAWING_NO_AND_MARK_NO = "/checkBitsDrawingEntryExistsByDrawingNoAndMarkNo/details";
    public static final String GET_TOTAL_COUNT = "/getBitsDrawingEntryTotalCount/details";
    public static final String GET_LATEST_BY_DRAWING_NO = "/getLatestBitsDrawingEntryByDrawingNo/details";
    public static final String GET_LATEST_BY_MARK_NO = "/getLatestBitsDrawingEntryByMarkNo/details";
    public static final String GET_DRAWING_ENTRY_STATS = "/getBitsDrawingEntryStats/details";
    public static final String GET_DRAWING_NUMBERS = "/getDrawingNumbers";
    public static final String GET_DRAWING_DETAILS = "/getDrawingDetails/{drawingNo}";
    /** * Get distinct RA numbers for dropdown */
    public static final String GET_DISTINCT_RA_NUMBERS = "/getDistinctBitsDrawingEntryRaNumbers/details";
    public static final String GET_DRAWING_ENTRY_DETAILS_BY_ATTRIBUTES = "/getDrawingEntryDetailsByAttributes/details"; // NEW API

    private static final Logger LOG = LoggerFactory.getLogger(BitsDrawingEntryController.class);
    private static final Logger logger = LoggerFactory.getLogger(BitsDrawingEntryController.class);

    /**
     * Get all drawing entries with pagination
     */
    @RequestMapping(value = GET_ALL_DRAWING_ENTRIES, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllDrawingEntries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "creationDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            LOG.info("Fetching all bits drawing entries with pagination");
                        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                     Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
                        Page<BitsDrawingEntryDto> drawingEntries = bitsDrawingEntryService.getAllDrawingEntries(pageable);
            return ResponseEntity.ok(drawingEntries);
        } catch (Exception e) {
            LOG.error("Error getting all drawing entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get drawing entries: " + e.getMessage());
        }
    }

    /**
     * Get unique drawing entries (no duplicates based on drawing_no + mark_no)
     * This is specifically for the FabricationDatabasesearch frontend
     */
    @RequestMapping(value = GET_UNIQUE_DRAWING_ENTRIES, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getUniqueDrawingEntries() {
        try {
            LOG.info("Fetching unique bits drawing entries for fabrication search");
                        // Get all drawing entries
            List<BitsDrawingEntryDto> allEntries = bitsDrawingEntryService.getAllDrawingEntries();
                        // Group by drawing_no + mark_no combination and aggregate quantities
            Map<String, BitsDrawingEntryDto> uniqueEntriesMap = new LinkedHashMap<>();
                        for (BitsDrawingEntryDto entry : allEntries) {
                String key = (entry.getDrawingNo() != null ? entry.getDrawingNo() : "") + "_" +
                            (entry.getMarkNo() != null ? entry.getMarkNo() : "");
                                if (uniqueEntriesMap.containsKey(key)) {
                    // Aggregate quantities for existing entry
                    BitsDrawingEntryDto existingEntry = uniqueEntriesMap.get(key);
                    BigDecimal currentQty = existingEntry.getMarkedQty() != null ? existingEntry.getMarkedQty() : BigDecimal.ZERO;
                    BigDecimal newQty = entry.getMarkedQty() != null ? entry.getMarkedQty() : BigDecimal.ZERO;
                    existingEntry.setMarkedQty(currentQty.add(newQty));
                                        // Update total weight if available
                    if (entry.getTotalMarkedWgt() != null) {
                        BigDecimal currentWeight = existingEntry.getTotalMarkedWgt() != null ? existingEntry.getTotalMarkedWgt() : BigDecimal.ZERO;
                        existingEntry.setTotalMarkedWgt(currentWeight.add(entry.getTotalMarkedWgt()));
                    }
                } else {
                    // Add new unique entry
                    uniqueEntriesMap.put(key, entry);
                }
            }
                        List<BitsDrawingEntryDto> uniqueEntries = new ArrayList<>(uniqueEntriesMap.values());
                        LOG.info("Returning {} unique drawing entries out of {} total entries", uniqueEntries.size(), allEntries.size());
            return ResponseEntity.ok(uniqueEntries);
                    } catch (Exception e) {
            LOG.error("Error getting unique drawing entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get unique drawing entries: " + e.getMessage());
        }
    }

    /**
     * Get drawing entry by line ID
     */
    @RequestMapping(value = GET_DRAWING_ENTRY_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDrawingEntryById(@RequestParam String lineId) {
        try {
            LOG.info("Fetching bits drawing entry by line ID: {}", lineId);
            Optional<BitsDrawingEntryDto> drawingEntry = bitsDrawingEntryService.getDrawingEntryById(lineId);
                        if (drawingEntry.isPresent()) {
                return ResponseEntity.ok(drawingEntry.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOG.error("Error getting drawing entry by line ID: {}", lineId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get drawing entry: " + e.getMessage());
        }
    }

    /**
     * Create a new drawing entry
     */
    @RequestMapping(value = CREATE_DRAWING_ENTRY, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<?> createDrawingEntry(@Valid @RequestBody BitsDrawingEntryDto drawingEntryDto) {
        try {
            LOG.info("Creating new drawing entry for drawing number: {}", drawingEntryDto.getDrawingNo());
            List<BitsDrawingEntryDto> createdEntries = bitsDrawingEntryService.createDrawingEntry(drawingEntryDto);
            LOG.info("Successfully created {} drawing entries", createdEntries.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEntries);
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid input for creating drawing entry: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error creating drawing entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create drawing entry: " + e.getMessage());
        }
    }

    /**
     * Create multiple drawing entries
     */
    @RequestMapping(value = CREATE_BULK_DRAWING_ENTRIES, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<?> createDrawingEntries(@Valid @RequestBody List<BitsDrawingEntryDto> drawingEntryDtos) {
        try {
            LOG.info("Creating {} drawing entries", drawingEntryDtos.size());
            List<BitsDrawingEntryDto> createdEntries = bitsDrawingEntryService.createDrawingEntries(drawingEntryDtos);
            LOG.info("Successfully created {} total drawing entries", createdEntries.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEntries);
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid input for creating drawing entries: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error creating drawing entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create drawing entries: " + e.getMessage());
        }
    }

    /**
     * Update drawing entry
     */
    @RequestMapping(value = UPDATE_DRAWING_ENTRY, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity<?> updateDrawingEntry(
            @RequestParam String lineId,
            @Valid @RequestBody BitsDrawingEntryDto drawingEntryDto) {
        try {
            LOG.info("Updating drawing entry with line ID: {}", lineId);
                    // Validate lineId
            if (lineId == null || lineId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Line ID cannot be null or empty");
            }
                    // Validate DTO
            if (drawingEntryDto == null) {
                return ResponseEntity.badRequest().body("Drawing entry data cannot be null");
            }
                    BitsDrawingEntryDto updatedEntry = bitsDrawingEntryService.updateDrawingEntry(lineId, drawingEntryDto);
            return ResponseEntity.ok(updatedEntry);
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid input for updating drawing entry: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            LOG.error("Error updating drawing entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update drawing entry: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error updating drawing entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update drawing entry: " + e.getMessage());
        }
    }

    /**
     * Update fabrication stages for multiple entries
     */
    @RequestMapping(value = UPDATE_FABRICATION_STAGES, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity<?> updateFabricationStages(@RequestBody Map<String, Object> requestBody) {
        try {
            LOG.info("Updating fabrication stages");
                        @SuppressWarnings("unchecked")
            List<Map<String, Object>> fabricationUpdates = (List<Map<String, Object>>) requestBody.get("fabricationStages");
                        if (fabricationUpdates == null || fabricationUpdates.isEmpty()) {
                return ResponseEntity.badRequest().body("No fabrication stage updates provided");
            }
                        List<BitsDrawingEntryDto> updatedEntries = new ArrayList<>();
                        for (Map<String, Object> update : fabricationUpdates) {
                String lineId = (String) update.get("lineId");
                String cuttingStage = (String) update.get("cuttingStage");
                String fitUpStage = (String) update.get("fitUpStage");
                String weldingStage = (String) update.get("weldingStage");
                String finishingStage = (String) update.get("finishingStage");
                                if (lineId == null || lineId.trim().isEmpty()) {
                    continue; // Skip invalid entries
                }
                                // Get existing entry
                Optional<BitsDrawingEntryDto> existingEntryOpt = bitsDrawingEntryService.getDrawingEntryById(lineId);
                if (existingEntryOpt.isPresent()) {
                    BitsDrawingEntryDto existingEntry = existingEntryOpt.get();
                                        // Update only fabrication stages
                    existingEntry.setCuttingStage(cuttingStage != null ? cuttingStage : "N");
                    existingEntry.setFitUpStage(fitUpStage != null ? fitUpStage : "N");
                    existingEntry.setWeldingStage(weldingStage != null ? weldingStage : "N");
                    existingEntry.setFinishingStage(finishingStage != null ? finishingStage : "N");
                    existingEntry.setLastUpdatedBy("fabrication_system");
                                        // Update the entry
                    BitsDrawingEntryDto updatedEntry = bitsDrawingEntryService.updateDrawingEntry(lineId, existingEntry);
                    updatedEntries.add(updatedEntry);
                }
            }
                        LOG.info("Successfully updated fabrication stages for {} entries", updatedEntries.size());
                        Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Fabrication stages updated successfully");
            response.put("updatedCount", updatedEntries.size());
            response.put("updatedEntries", updatedEntries);
                        return ResponseEntity.ok(response);
                    } catch (Exception e) {
            LOG.error("Error updating fabrication stages", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update fabrication stages: " + e.getMessage());
        }
    }

    /**
     * Update RA.NO for a specific drawing entry with proper CORS handling
     */
    @RequestMapping(value = UPDATE_DRAWING_ENTRY_RA_NO, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity<?> updateDrawingEntryRaNo(@RequestBody Map<String, Object> requestBody) {
        try {
            // FIXED: Safe type conversion to handle both String and Integer values
            String lineId = String.valueOf(requestBody.get("lineId"));
            String drawingNo = requestBody.get("drawingNo") != null ? String.valueOf(requestBody.get("drawingNo")) : null;
            String markNo = requestBody.get("markNo") != null ? String.valueOf(requestBody.get("markNo")) : null;
            String raNo = requestBody.get("raNo") != null ? String.valueOf(requestBody.get("raNo")) : null;
                    LOG.info("Updating RA.NO for line ID: {}, drawing: {}, mark: {}, RA.NO: {}", lineId, drawingNo, markNo, raNo);
                    // Validate required fields
            if (lineId == null || lineId.trim().isEmpty() || "null".equals(lineId)) {
                return ResponseEntity.badRequest().body("Line ID is required");
            }
                    if (raNo == null || raNo.trim().isEmpty() || "null".equals(raNo)) {
                return ResponseEntity.badRequest().body("RA.NO value is required");
            }
                    // Get existing entry
            Optional<BitsDrawingEntryDto> existingEntryOpt = bitsDrawingEntryService.getDrawingEntryById(lineId);
            if (!existingEntryOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }
                    BitsDrawingEntryDto existingEntry = existingEntryOpt.get();
                    // Validate that the drawing no and mark no match (if provided)
            if (drawingNo != null && !"null".equals(drawingNo) && !drawingNo.equals(existingEntry.getDrawingNo())) {
                return ResponseEntity.badRequest().body("Drawing number mismatch");
            }
                    if (markNo != null && !"null".equals(markNo) && !markNo.equals(existingEntry.getMarkNo())) {
                return ResponseEntity.badRequest().body("Mark number mismatch");
            }
                    // Update only the RA.NO field
            existingEntry.setRaNo(raNo.trim());
            existingEntry.setLastUpdatedBy("ra_no_system");
                    // Save the updated entry
            BitsDrawingEntryDto updatedEntry = bitsDrawingEntryService.updateDrawingEntry(lineId, existingEntry);
                    LOG.info("Successfully updated RA.NO for line ID: {}", lineId);
                    Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "RA.NO updated successfully");
            response.put("lineId", lineId);
            response.put("raNo", raNo.trim());
            response.put("updatedEntry", updatedEntry);
                    return ResponseEntity.ok(response);
            } catch (Exception e) {
        LOG.error("Error updating RA.NO", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to update RA.NO: " + e.getMessage());
    }}

    /**
     * Handle OPTIONS requests for CORS preflight
     */
    @RequestMapping(value = UPDATE_DRAWING_ENTRY_RA_NO, method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptionsForRaNo() {
        return ResponseEntity.ok().build();
    }

    /**
     * Delete drawing entry by line ID
     */
    @RequestMapping(value = DELETE_DRAWING_ENTRY, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDrawingEntry(@RequestParam String lineId) {
        try {
            LOG.info("Deleting drawing entry with line ID: {}", lineId);
            bitsDrawingEntryService.deleteDrawingEntry(lineId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            LOG.error("Error deleting drawing entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete drawing entry: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error deleting drawing entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete drawing entry: " + e.getMessage());
        }
    }

    /**
     * Delete drawing entries by drawing number
     */
    @RequestMapping(value = DELETE_DRAWING_ENTRIES_BY_DRAWING_NO, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDrawingEntriesByDrawingNo(@RequestParam String drawingNo) {
        try {
            LOG.info("Deleting drawing entries by drawing number: {}", drawingNo);
            bitsDrawingEntryService.deleteDrawingEntriesByDrawingNo(drawingNo);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error deleting drawing entries by drawing number: {}", drawingNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete drawing entries: " + e.getMessage());
        }
    }

    /**
     * Delete drawing entries by mark number
     */
    @RequestMapping(value = DELETE_DRAWING_ENTRIES_BY_MARK_NO, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDrawingEntriesByMarkNo(@RequestParam String markNo) {
        try {
            LOG.info("Deleting drawing entries by mark number: {}", markNo);
            bitsDrawingEntryService.deleteByMarkNo(markNo);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error deleting drawing entries by mark number: {}", markNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete drawing entries: " + e.getMessage());
        }
    }

    /**
     * Bulk delete drawing entries
     */
    @RequestMapping(value = BULK_DELETE_DRAWING_ENTRIES, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public ResponseEntity<?> bulkDeleteDrawingEntries(@RequestBody List<String> lineIds) {
        try {
            LOG.info("Bulk deleting {} drawing entries", lineIds.size());
            bitsDrawingEntryService.bulkDeleteDrawingEntries(lineIds);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error bulk deleting drawing entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to bulk delete drawing entries: " + e.getMessage());
        }
    }

    /**
     * Get drawing entries by drawing number
     */
    @RequestMapping(value = SEARCH_BY_DRAWING_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDrawingEntriesByDrawingNo(@RequestParam String drawingNo) {
        try {
            LOG.info("Searching drawing entries by drawing number: {}", drawingNo);
            List<BitsDrawingEntryDto> drawingEntries = bitsDrawingEntryService.getDrawingEntriesByDrawingNo(drawingNo);
            return ResponseEntity.ok(drawingEntries);
        } catch (Exception e) {
            LOG.error("Error getting drawing entries by drawing number: {}", drawingNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get drawing entries: " + e.getMessage());
        }
    }

    /**
     * Get drawing entries by mark number
     */
    @RequestMapping(value = SEARCH_BY_MARK_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDrawingEntriesByMarkNo(@RequestParam String markNo) {
        try {
            LOG.info("Searching drawing entries by mark number: {}", markNo);
            List<BitsDrawingEntryDto> drawingEntries = bitsDrawingEntryService.getDrawingEntriesByMarkNo(markNo);
            return ResponseEntity.ok(drawingEntries);
        } catch (Exception e) {
            LOG.error("Error getting drawing entries by mark number: {}", markNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get drawing entries: " + e.getMessage());
        }
    }

    /**
     * Get drawing entries by session code
     */
    @RequestMapping(value = SEARCH_BY_SESSION_CODE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDrawingEntriesBySessionCode(@RequestParam String sessionCode) {
        try {
            LOG.info("Searching drawing entries by session code: {}", sessionCode);
            List<BitsDrawingEntryDto> drawingEntries = bitsDrawingEntryService.getDrawingEntriesBySessionCode(sessionCode);
            return ResponseEntity.ok(drawingEntries);
        } catch (Exception e) {
            LOG.error("Error getting drawing entries by session code: {}", sessionCode, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get drawing entries: " + e.getMessage());
        }
    }

    /**
     * Get drawing entries by tenant ID
     */
    @RequestMapping(value = SEARCH_BY_TENANT_ID, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDrawingEntriesByTenantId(
            @RequestParam String tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "creationDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            LOG.info("Searching drawing entries by tenant ID: {}", tenantId);
                        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                     Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
                        Page<BitsDrawingEntryDto> drawingEntries = bitsDrawingEntryService.getDrawingEntriesByTenantId(tenantId, pageable);
            return ResponseEntity.ok(drawingEntries);
        } catch (Exception e) {
            LOG.error("Error getting drawing entries by tenant ID: {}", tenantId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get drawing entries: " + e.getMessage());
        }
    }

    /**
     * Search drawing entries by multiple criteria
     */
    @RequestMapping(value = SEARCH_BY_MULTIPLE_CRITERIA, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchDrawingEntries(
            @RequestParam(required = false) String drawingNo,
            @RequestParam(required = false) String markNo,
            @RequestParam(required = false) String sessionCode,
            @RequestParam(required = false) String tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "creationDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            LOG.info("Searching drawing entries with multiple criteria");
                        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                     Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
                        Page<BitsDrawingEntryDto> drawingEntries = bitsDrawingEntryService.searchDrawingEntries(
                    drawingNo, markNo, sessionCode, tenantId, pageable);
            return ResponseEntity.ok(drawingEntries);
        } catch (Exception e) {
            LOG.error("Error searching drawing entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search drawing entries: " + e.getMessage());
        }
    }

    /**
     * Get drawing entries by date range
     */
    @RequestMapping(value = SEARCH_BY_DATE_RANGE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDrawingEntriesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            LOG.info("Searching drawing entries by date range: {} to {}", startDate, endDate);
            List<BitsDrawingEntryDto> drawingEntries = bitsDrawingEntryService.getDrawingEntriesByDateRange(startDate, endDate);
            return ResponseEntity.ok(drawingEntries);
        } catch (Exception e) {
            LOG.error("Error getting drawing entries by date range", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get drawing entries: " + e.getMessage());
        }
    }

    /**
     * Get drawing entries with marked quantity greater than specified value
     */
    @RequestMapping(value = SEARCH_BY_MARKED_QTY_GREATER_THAN, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDrawingEntriesByMarkedQtyGreaterThan(@RequestParam BigDecimal markedQty) {
        try {
            LOG.info("Searching drawing entries with marked quantity greater than: {}", markedQty);
            List<BitsDrawingEntryDto> drawingEntries = bitsDrawingEntryService.getDrawingEntriesByMarkedQtyGreaterThan(markedQty);
            return ResponseEntity.ok(drawingEntries);
        } catch (Exception e) {
            LOG.error("Error getting drawing entries by marked quantity", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get drawing entries: " + e.getMessage());
        }
    }

    /**
     * Get count of entries by drawing number
     */
    @RequestMapping(value = GET_COUNT_BY_DRAWING_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getCountByDrawingNo(@RequestParam String drawingNo) {
        try {
            LOG.info("Getting count by drawing number: {}", drawingNo);
            Long count = bitsDrawingEntryService.getCountByDrawingNo(drawingNo);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            LOG.error("Error getting count by drawing number: {}", drawingNo, e);
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
            BigDecimal sum = bitsDrawingEntryService.getSumMarkedQtyByDrawingNo(drawingNo);
            return ResponseEntity.ok(sum);
        } catch (Exception e) {
            LOG.error("Error getting sum of marked quantities by drawing number: {}", drawingNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get sum: " + e.getMessage());
        }
    }

    /**
     * Get sum of total marked weight by drawing number
     */
    @RequestMapping(value = GET_SUM_TOTAL_MARKED_WGT_BY_DRAWING_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getSumTotalMarkedWgtByDrawingNo(@RequestParam String drawingNo) {
        try {
            LOG.info("Getting sum of total marked weights by drawing number: {}", drawingNo);
            BigDecimal sum = bitsDrawingEntryService.getSumTotalMarkedWgtByDrawingNo(drawingNo);
            return ResponseEntity.ok(sum);
        } catch (Exception e) {
            LOG.error("Error getting sum of total marked weights by drawing number: {}", drawingNo, e);
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
            List<String> drawingNumbers = bitsDrawingEntryService.getDistinctDrawingNumbers();
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
            List<String> markNumbers = bitsDrawingEntryService.getDistinctMarkNumbers();
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
            List<String> sessionCodes = bitsDrawingEntryService.getDistinctSessionCodes();
            return ResponseEntity.ok(sessionCodes);
        } catch (Exception e) {
            LOG.error("Error getting distinct session codes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get distinct session codes: " + e.getMessage());
        }
    }

    /**
     * Check if drawing entry exists by line ID
     */
    @RequestMapping(value = CHECK_EXISTS_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> existsById(@RequestParam String lineId) {
        try {
            LOG.info("Checking if drawing entry exists by line ID: {}", lineId);
            boolean exists = bitsDrawingEntryService.existsById(lineId);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            LOG.error("Error checking if drawing entry exists by line ID: {}", lineId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to check existence: " + e.getMessage());
        }
    }

    /**
     * Check if drawing entry exists by drawing number and mark number
     */
    @RequestMapping(value = CHECK_EXISTS_BY_DRAWING_NO_AND_MARK_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> existsByDrawingNoAndMarkNo(@RequestParam String drawingNo, @RequestParam String markNo) {
        try {
            LOG.info("Checking if drawing entry exists by drawing number: {} and mark number: {}", drawingNo, markNo);
            boolean exists = bitsDrawingEntryService.existsByDrawingNoAndMarkNo(drawingNo, markNo);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            LOG.error("Error checking if drawing entry exists", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to check existence: " + e.getMessage());
        }
    }

    /**
     * Get total count of all entries
     */
    @RequestMapping(value = GET_TOTAL_COUNT, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getTotalCount() {
        try {
            LOG.info("Getting total count of drawing entries");
            long count = bitsDrawingEntryService.getTotalCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            LOG.error("Error getting total count", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get total count: " + e.getMessage());
        }
    }

    /**
     * Get latest entry by drawing number
     */
    @RequestMapping(value = GET_LATEST_BY_DRAWING_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getLatestByDrawingNo(@RequestParam String drawingNo) {
        try {
            LOG.info("Getting latest drawing entry by drawing number: {}", drawingNo);
            Optional<BitsDrawingEntryDto> drawingEntry = bitsDrawingEntryService.getLatestByDrawingNo(drawingNo);
                        if (drawingEntry.isPresent()) {
                return ResponseEntity.ok(drawingEntry.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOG.error("Error getting latest drawing entry by drawing number: {}", drawingNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get latest entry: " + e.getMessage());
        }
    }

    /**
     * Get latest entry by mark number
     */
    @RequestMapping(value = GET_LATEST_BY_MARK_NO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getLatestByMarkNo(@RequestParam String markNo) {
        try {
            LOG.info("Getting latest drawing entry by mark number: {}", markNo);
            Optional<BitsDrawingEntryDto> drawingEntry = bitsDrawingEntryService.getLatestByMarkNo(markNo);
                        if (drawingEntry.isPresent()) {
                return ResponseEntity.ok(drawingEntry.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOG.error("Error getting latest drawing entry by mark number: {}", markNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get latest entry: " + e.getMessage());
        }
    }

    /**
     * Get drawing entry statistics by drawing number
     */
    @RequestMapping(value = GET_DRAWING_ENTRY_STATS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDrawingEntryStats(@RequestParam String drawingNo) {
        try {
            LOG.info("Getting drawing entry statistics for drawing number: {}", drawingNo);
            BitsDrawingEntryStatsDto stats = bitsDrawingEntryService.getDrawingEntryStats(drawingNo);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            LOG.error("Error getting drawing entry statistics for drawing number: {}", drawingNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get statistics: " + e.getMessage());
        }
    }

    /**
     * Get all drawing numbers for dropdown
     */
    @RequestMapping(value = GET_DRAWING_NUMBERS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllDrawingNumbers() {
        try {
            LOG.info("Fetching all drawing numbers");
            List<String> drawingNumbers = bitsDrawingEntryService.getDistinctDrawingNumbers();
            return ResponseEntity.ok(drawingNumbers);
        } catch (Exception e) {
            LOG.error("Error fetching drawing numbers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get drawing numbers: " + e.getMessage());
        }
    }
        /**
     * Get details for a specific drawing number
     */
    @RequestMapping(value = GET_DRAWING_DETAILS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDrawingDetails(@PathVariable String drawingNo) {
        try {
            LOG.info("Fetching details for drawing number: {}", drawingNo);
            List<BitsDrawingEntryDto> entries = bitsDrawingEntryService.getDrawingEntriesByDrawingNo(drawingNo);
            if (entries.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            // Return the first matching drawing entry's details
            BitsDrawingEntryDto drawingDetails = entries.get(0);
            return ResponseEntity.ok(drawingDetails);
        } catch (Exception e) {
            LOG.error("Error fetching drawing details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get drawing details: " + e.getMessage());
        }
    }
        @RequestMapping(value = GET_DISTINCT_RA_NUMBERS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getDistinctRaNumbers() {
        try {
            LOG.info("Getting distinct RA numbers");
            List<String> raNumbers = bitsDrawingEntryService.getDistinctRaNumbers();
            return ResponseEntity.ok(raNumbers);
        } catch (Exception e) {
            LOG.error("Error getting distinct RA numbers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get distinct RA numbers: " + e.getMessage());
        }
    }

    //Newly added apis that fetch the work order from the  bits_drawing_entry
    /**
     * Get distinct work orders from bits_drawing_entry
     */
    @GetMapping("/getDistinctWorkOrdersFromDrawingEntry/details")
    public ResponseEntity<List<String>> getDistinctWorkOrdersFromDrawingEntry() {
        try {
            List<String> workOrders = bitsDrawingEntryService.getDistinctWorkOrdersFromDrawingEntry();
            return ResponseEntity.ok(workOrders);
        } catch (Exception e) {
            logger.error("Error getting distinct work orders from drawing entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get distinct building names filtered by work order
     */
    @GetMapping("/getDistinctBuildingNamesByWorkOrder/details")
    public ResponseEntity<List<String>> getDistinctBuildingNamesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> buildingNames = bitsDrawingEntryService.getDistinctBuildingNamesByWorkOrder(workOrder);
            return ResponseEntity.ok(buildingNames);
        } catch (Exception e) {
            logger.error("Error getting distinct building names by work order: {}", workOrder, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get distinct drawing numbers filtered by work order and building name
     */
    @GetMapping("/getDistinctDrawingNumbersByAttributes/details")
    public ResponseEntity<List<String>> getDistinctDrawingNumbersByAttributes(
            @RequestParam String workOrder,
            @RequestParam String buildingName) {
        try {
            List<String> drawingNumbers = bitsDrawingEntryService.getDistinctDrawingNumbersByAttributes(workOrder, buildingName);
            return ResponseEntity.ok(drawingNumbers);
        } catch (Exception e) {
            logger.error("Error getting distinct drawing numbers by attributes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get distinct mark numbers filtered by work order, building name, and drawing number
     */
    @GetMapping("/getDistinctMarkNumbersByAttributes/details")
    public ResponseEntity<List<String>> getDistinctMarkNumbersByAttributes(
            @RequestParam String workOrder,
            @RequestParam String buildingName,
            @RequestParam String drawingNo) {
        try {
            List<String> markNumbers = bitsDrawingEntryService.getDistinctMarkNumbersByAttributes(workOrder, buildingName, drawingNo);
            return ResponseEntity.ok(markNumbers);
        } catch (Exception e) {
            logger.error("Error getting distinct mark numbers by attributes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get a single drawing entry's details (including markedQty and length) by work order, building name, drawing number, and mark number
     */
    @GetMapping(GET_DRAWING_ENTRY_DETAILS_BY_ATTRIBUTES)
    public ResponseEntity<?> getDrawingEntryDetailsByAttributes(
            @RequestParam String workOrder,
            @RequestParam String buildingName,
            @RequestParam String drawingNo,
            @RequestParam String markNo) {
        try {
            LOG.info("Fetching drawing entry details by attributes: workOrder={}, buildingName={}, drawingNo={}, markNo={}",
                    workOrder, buildingName, drawingNo, markNo);
            Optional<BitsDrawingEntryDto> entryDto = bitsDrawingEntryService.getDrawingEntryDetailsByAttributes(
                    workOrder, buildingName, drawingNo, markNo);
            if (entryDto.isPresent()) {
                return ResponseEntity.ok(entryDto.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOG.error("Error fetching drawing entry details by attributes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch drawing entry details: " + e.getMessage());
        }
    }
}
