package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.WorkOrderOutDrawingEntryDto;
import com.bellaryinfotech.service.WorkOrderOutDrawingEntryService;
import com.bellaryinfotech.repo.BitsHeaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class WorkOrderOutDrawingEntryController {
    
    private static final Logger LOG = LoggerFactory.getLogger(WorkOrderOutDrawingEntryController.class);
    
    @Autowired
    private WorkOrderOutDrawingEntryService workOrderOutDrawingEntryService;
    
    @Autowired
    private BitsHeaderRepository bitsHeaderRepository;
    
    // Constants for endpoints
    public static final String GET_ALL_WORK_ORDER_OUT_ENTRIES = "/getAllWorkOrderOutDrawingEntries/details";
    public static final String GET_WORK_ORDER_OUT_ENTRY_BY_ID = "/getWorkOrderOutDrawingEntryById/details";
    public static final String CREATE_BULK_WORK_ORDER_OUT_ENTRIES = "/createBulkWorkOrderOutDrawingEntries/details";
    public static final String UPDATE_WORK_ORDER_OUT_ENTRY = "/updateWorkOrderOutDrawingEntry/details";
    public static final String DELETE_WORK_ORDER_OUT_ENTRY = "/deleteWorkOrderOutDrawingEntry/details";
    public static final String DELETE_BY_DRAWING_AND_MARK = "/deleteWorkOrderOutEntriesByDrawingAndMark/details";
    
    // Search endpoints
    public static final String SEARCH_BY_WORK_ORDER = "/searchWorkOrderOutEntriesByWorkOrder/details";
    public static final String SEARCH_BY_WORK_ORDER_AND_PLANT_LOCATION = "/searchWorkOrderOutEntriesByWorkOrderAndPlantLocation/details";
    public static final String SEARCH_BY_DRAWING_NO = "/searchWorkOrderOutEntriesByDrawingNo/details";
    public static final String SEARCH_BY_MARK_NO = "/searchWorkOrderOutEntriesByMarkNo/details";
    public static final String SEARCH_BY_DRAWING_AND_MARK = "/searchWorkOrderOutEntriesByDrawingAndMark/details";
    public static final String SEARCH_BY_ORDER_ID = "/searchWorkOrderOutEntriesByOrderId/details";
    public static final String SEARCH_BY_MULTIPLE_CRITERIA = "/searchWorkOrderOutEntriesByMultipleCriteria/details";
    
    // Dropdown endpoints
    public static final String GET_DISTINCT_WORK_ORDERS_WOO = "/getDistinctWorkOrdersFromWorkOrderOut/details";
    public static final String GET_DISTINCT_PLANT_LOCATIONS_BY_WORK_ORDER_WOO = "/getDistinctPlantLocationsByWorkOrderFromWorkOrderOut/details";
    public static final String GET_DISTINCT_DRAWING_NUMBERS_BY_WORK_ORDER_AND_PLANT_LOCATION_WOO = "/getDistinctDrawingNumbersByWorkOrderAndPlantLocationFromWorkOrderOut/details";
    public static final String GET_DISTINCT_MARK_NUMBERS_BY_WORK_ORDER_AND_PLANT_LOCATION_WOO = "/getDistinctMarkNumbersByWorkOrderAndPlantLocationFromWorkOrderOut/details";
    
    // Edit endpoints
    public static final String GET_ENTRIES_FOR_EDITING_BY_MARK_NO = "/getWorkOrderOutEntriesForEditingByMarkNo/details";
    public static final String GET_DRAWING_ENTRY_BY_MARK_NO = "/getWorkOrderOutDrawingEntryByMarkNo/details";
    
    // ============ CRUD OPERATIONS ============
    
    @GetMapping(value = GET_ALL_WORK_ORDER_OUT_ENTRIES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutDrawingEntryDto>> getAllEntries() {
        try {
            LOG.info("Fetching all work order out drawing entries");
            List<WorkOrderOutDrawingEntryDto> entries = workOrderOutDrawingEntryService.getAllEntries();
            LOG.info("Successfully fetched {} work order out drawing entries", entries.size());
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            LOG.error("Error fetching all work order out drawing entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = GET_WORK_ORDER_OUT_ENTRY_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkOrderOutDrawingEntryDto> getEntryById(@RequestParam Long id) {
        try {
            LOG.info("Fetching work order out drawing entry by ID: {}", id);
            Optional<WorkOrderOutDrawingEntryDto> entry = workOrderOutDrawingEntryService.getEntryById(id);
            if (entry.isPresent()) {
                return ResponseEntity.ok(entry.get());
            } else {
                LOG.warn("Work order out drawing entry not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOG.error("Error fetching work order out drawing entry by ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping(value = CREATE_BULK_WORK_ORDER_OUT_ENTRIES, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBulkEntries(@RequestBody Map<String, Object> requestData) {
        try {
            LOG.info("Creating bulk work order out drawing entries");
            
            // Extract drawing entry data
            Map<String, Object> drawingEntry = (Map<String, Object>) requestData.get("drawingEntry");
            List<Map<String, Object>> bomEntries = (List<Map<String, Object>>) requestData.get("bomEntries");
            
            if (drawingEntry == null || bomEntries == null || bomEntries.isEmpty()) {
                return ResponseEntity.badRequest().body("Drawing entry and BOM entries are required");
            }
            
            // Get order_id from work order
            String workOrder = (String) drawingEntry.get("workOrder");
            Long orderId = null;
            if (workOrder != null) {
                orderId = bitsHeaderRepository.findOrderIdByWorkOrder(workOrder);
                if (orderId == null) {
                    LOG.warn("No order_id found for work order: {}", workOrder);
                }
            }
            
            // Create entries - one for each BOM row with drawing entry data duplicated
            List<WorkOrderOutDrawingEntryDto> entriesToCreate = new java.util.ArrayList<>();
            
            for (Map<String, Object> bomEntry : bomEntries) {
                WorkOrderOutDrawingEntryDto dto = new WorkOrderOutDrawingEntryDto();
                
                // Set order_id
                dto.setOrderId(orderId);
                
                // Set drawing entry data (duplicated for each BOM row)
                dto.setWorkOrder((String) drawingEntry.get("workOrder"));
                dto.setSubAgencyName((String) drawingEntry.get("subAgencyName"));
                dto.setSubAgencyWorkOrderName((String) drawingEntry.get("subAgencyWorkOrderName"));
                dto.setPlantLocation((String) drawingEntry.get("plantLocation"));
                dto.setDepartment((String) drawingEntry.get("department"));
                dto.setWorkLocation((String) drawingEntry.get("workLocation"));
                dto.setLineNumber((String) drawingEntry.get("lineNumber"));
                dto.setDrawingNo((String) drawingEntry.get("drawingNo"));
                
                // Handle dates
                if (drawingEntry.get("drawingReceivedDate") != null) {
                    dto.setDrawingReceivedDate(java.time.LocalDate.parse((String) drawingEntry.get("drawingReceivedDate")));
                }
                if (drawingEntry.get("targetDate") != null) {
                    dto.setTargetDate(java.time.LocalDate.parse((String) drawingEntry.get("targetDate")));
                }
                
                dto.setMarkNo((String) drawingEntry.get("markNo"));
                
                // Handle BigDecimal fields
                if (drawingEntry.get("markWeight") != null) {
                    dto.setMarkWeight(new java.math.BigDecimal(drawingEntry.get("markWeight").toString()));
                }
                if (drawingEntry.get("markQty") != null) {
                    dto.setMarkQty(Integer.valueOf(drawingEntry.get("markQty").toString()));
                }
                if (drawingEntry.get("totalMarkWeight") != null) {
                    dto.setTotalMarkWeight(new java.math.BigDecimal(drawingEntry.get("totalMarkWeight").toString()));
                }
                
                // Set BOM entry data (unique for each row)
                dto.setItemNo((String) bomEntry.get("itemNo"));
                dto.setSectionCode((String) bomEntry.get("sectionCode"));
                dto.setSectionName((String) bomEntry.get("sectionName"));
                
                if (bomEntry.get("width") != null) {
                    dto.setWidth(new java.math.BigDecimal(bomEntry.get("width").toString()));
                }
                if (bomEntry.get("length") != null) {
                    dto.setLength(new java.math.BigDecimal(bomEntry.get("length").toString()));
                }
                if (bomEntry.get("secWeight") != null) {
                    dto.setSecWeight(new java.math.BigDecimal(bomEntry.get("secWeight").toString()));
                }
                if (bomEntry.get("itemWeight") != null) {
                    dto.setItemWeight(new java.math.BigDecimal(bomEntry.get("itemWeight").toString()));
                }
                if (bomEntry.get("itemQty") != null) {
                    dto.setItemQty(Integer.valueOf(bomEntry.get("itemQty").toString()));
                }
                if (bomEntry.get("totalItemWeight") != null) {
                    dto.setTotalItemWeight(new java.math.BigDecimal(bomEntry.get("totalItemWeight").toString()));
                }
                
                entriesToCreate.add(dto);
            }
            
            // Save all entries
            List<WorkOrderOutDrawingEntryDto> savedEntries = workOrderOutDrawingEntryService.createBulkEntries(entriesToCreate);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Successfully created " + savedEntries.size() + " work order out drawing entries");
            response.put("createdCount", savedEntries.size());
            response.put("entries", savedEntries);
            
            LOG.info("Successfully created {} work order out drawing entries", savedEntries.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            LOG.error("Error creating bulk work order out drawing entries", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error creating entries: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @PutMapping(value = UPDATE_WORK_ORDER_OUT_ENTRY, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateEntry(@RequestParam Long id, @RequestBody WorkOrderOutDrawingEntryDto entryDto) {
        try {
            LOG.info("Updating work order out drawing entry with ID: {}", id);
            WorkOrderOutDrawingEntryDto updatedEntry = workOrderOutDrawingEntryService.updateEntry(id, entryDto);
            LOG.info("Successfully updated work order out drawing entry with ID: {}", id);
            return ResponseEntity.ok(updatedEntry);
        } catch (RuntimeException e) {
            LOG.error("Work order out drawing entry not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            LOG.error("Error updating work order out drawing entry with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating entry: " + e.getMessage());
        }
    }
    
    @DeleteMapping(value = DELETE_WORK_ORDER_OUT_ENTRY)
    public ResponseEntity<?> deleteEntry(@RequestParam Long id) {
        try {
            LOG.info("Deleting work order out drawing entry with ID: {}", id);
            workOrderOutDrawingEntryService.deleteEntry(id);
            LOG.info("Successfully deleted work order out drawing entry with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error deleting work order out drawing entry with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting entry: " + e.getMessage());
        }
    }
    
    @DeleteMapping(value = DELETE_BY_DRAWING_AND_MARK)
    public ResponseEntity<?> deleteByDrawingAndMark(@RequestParam String drawingNo, @RequestParam String markNo) {
        try {
            LOG.info("Deleting work order out drawing entries by drawing: {} and mark: {}", drawingNo, markNo);
            workOrderOutDrawingEntryService.deleteByDrawingAndMark(drawingNo, markNo);
            LOG.info("Successfully deleted work order out drawing entries by drawing: {} and mark: {}", drawingNo, markNo);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error deleting work order out drawing entries by drawing: {} and mark: {}", drawingNo, markNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting entries: " + e.getMessage());
        }
    }
    
    // ============ SEARCH OPERATIONS ============
    
    @GetMapping(value = SEARCH_BY_WORK_ORDER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutDrawingEntryDto>> searchByWorkOrder(@RequestParam String workOrder) {
        try {
            LOG.info("Searching work order out drawing entries by work order: {}", workOrder);
            List<WorkOrderOutDrawingEntryDto> entries = workOrderOutDrawingEntryService.searchByWorkOrder(workOrder);
            LOG.info("Found {} work order out drawing entries for work order: {}", entries.size(), workOrder);
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            LOG.error("Error searching work order out drawing entries by work order: {}", workOrder, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = SEARCH_BY_WORK_ORDER_AND_PLANT_LOCATION, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutDrawingEntryDto>> searchByWorkOrderAndPlantLocation(
            @RequestParam String workOrder, @RequestParam String plantLocation) {
        try {
            LOG.info("Searching work order out drawing entries by work order: {} and plant location: {}", workOrder, plantLocation);
            List<WorkOrderOutDrawingEntryDto> entries = workOrderOutDrawingEntryService.searchByWorkOrderAndPlantLocation(workOrder, plantLocation);
            LOG.info("Found {} work order out drawing entries", entries.size());
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            LOG.error("Error searching work order out drawing entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = SEARCH_BY_DRAWING_NO, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutDrawingEntryDto>> searchByDrawingNo(@RequestParam String drawingNo) {
        try {
            LOG.info("Searching work order out drawing entries by drawing number: {}", drawingNo);
            List<WorkOrderOutDrawingEntryDto> entries = workOrderOutDrawingEntryService.searchByDrawingNo(drawingNo);
            LOG.info("Found {} work order out drawing entries for drawing number: {}", entries.size(), drawingNo);
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            LOG.error("Error searching work order out drawing entries by drawing number: {}", drawingNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = SEARCH_BY_MARK_NO, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutDrawingEntryDto>> searchByMarkNo(@RequestParam String markNo) {
        try {
            LOG.info("Searching work order out drawing entries by mark number: {}", markNo);
            List<WorkOrderOutDrawingEntryDto> entries = workOrderOutDrawingEntryService.searchByMarkNo(markNo);
            LOG.info("Found {} work order out drawing entries for mark number: {}", entries.size(), markNo);
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            LOG.error("Error searching work order out drawing entries by mark number: {}", markNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = SEARCH_BY_DRAWING_AND_MARK, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutDrawingEntryDto>> searchByDrawingAndMark(
            @RequestParam String drawingNo, @RequestParam String markNo) {
        try {
            LOG.info("Searching work order out drawing entries by drawing: {} and mark: {}", drawingNo, markNo);
            List<WorkOrderOutDrawingEntryDto> entries = workOrderOutDrawingEntryService.searchByDrawingAndMark(drawingNo, markNo);
            LOG.info("Found {} work order out drawing entries", entries.size());
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            LOG.error("Error searching work order out drawing entries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = SEARCH_BY_ORDER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutDrawingEntryDto>> searchByOrderId(@RequestParam Long orderId) {
        try {
            LOG.info("Searching work order out drawing entries by order ID: {}", orderId);
            List<WorkOrderOutDrawingEntryDto> entries = workOrderOutDrawingEntryService.searchByOrderId(orderId);
            LOG.info("Found {} work order out drawing entries for order ID: {}", entries.size(), orderId);
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            LOG.error("Error searching work order out drawing entries by order ID: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = SEARCH_BY_MULTIPLE_CRITERIA, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutDrawingEntryDto>> searchByMultipleCriteria(
            @RequestParam(required = false) String workOrder,
            @RequestParam(required = false) String plantLocation,
            @RequestParam(required = false) String drawingNo,
            @RequestParam(required = false) String markNo) {
        try {
            LOG.info("Searching work order out drawing entries by multiple criteria");
            List<WorkOrderOutDrawingEntryDto> entries = workOrderOutDrawingEntryService.searchByMultipleCriteria(workOrder, plantLocation, drawingNo, markNo);
            LOG.info("Found {} work order out drawing entries", entries.size());
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            LOG.error("Error searching work order out drawing entries by multiple criteria", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // ============ DROPDOWN DATA ============
    
    @GetMapping(value = GET_DISTINCT_WORK_ORDERS_WOO, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDistinctWorkOrders() {
        try {
            LOG.info("Fetching distinct work orders from work order out drawing entries");
            List<String> workOrders = workOrderOutDrawingEntryService.getDistinctWorkOrders();
            LOG.info("Found {} distinct work orders", workOrders.size());
            return ResponseEntity.ok(workOrders);
        } catch (Exception e) {
            LOG.error("Error fetching distinct work orders", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = GET_DISTINCT_PLANT_LOCATIONS_BY_WORK_ORDER_WOO, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDistinctPlantLocationsByWorkOrder(@RequestParam String workOrder) {
        try {
            LOG.info("Fetching distinct plant locations for work order: {}", workOrder);
            List<String> plantLocations = workOrderOutDrawingEntryService.getDistinctPlantLocationsByWorkOrder(workOrder);
            LOG.info("Found {} distinct plant locations", plantLocations.size());
            return ResponseEntity.ok(plantLocations);
        } catch (Exception e) {
            LOG.error("Error fetching distinct plant locations for work order: {}", workOrder, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = GET_DISTINCT_DRAWING_NUMBERS_BY_WORK_ORDER_AND_PLANT_LOCATION_WOO, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDistinctDrawingNumbersByWorkOrderAndPlantLocation(
            @RequestParam String workOrder, @RequestParam String plantLocation) {
        try {
            LOG.info("Fetching distinct drawing numbers for work order: {} and plant location: {}", workOrder, plantLocation);
            List<String> drawingNumbers = workOrderOutDrawingEntryService.getDistinctDrawingNumbersByWorkOrderAndPlantLocation(workOrder, plantLocation);
            LOG.info("Found {} distinct drawing numbers", drawingNumbers.size());
            return ResponseEntity.ok(drawingNumbers);
        } catch (Exception e) {
            LOG.error("Error fetching distinct drawing numbers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = GET_DISTINCT_MARK_NUMBERS_BY_WORK_ORDER_AND_PLANT_LOCATION_WOO, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDistinctMarkNumbersByWorkOrderAndPlantLocation(
            @RequestParam String workOrder, @RequestParam String plantLocation) {
        try {
            LOG.info("Fetching distinct mark numbers for work order: {} and plant location: {}", workOrder, plantLocation);
            List<String> markNumbers = workOrderOutDrawingEntryService.getDistinctMarkNumbersByWorkOrderAndPlantLocation(workOrder, plantLocation);
            LOG.info("Found {} distinct mark numbers", markNumbers.size());
            return ResponseEntity.ok(markNumbers);
        } catch (Exception e) {
            LOG.error("Error fetching distinct mark numbers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // ============ EDIT OPERATIONS ============
    
    @GetMapping(value = GET_ENTRIES_FOR_EDITING_BY_MARK_NO, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutDrawingEntryDto>> getEntriesForEditingByMarkNo(@RequestParam String markNo) {
        try {
            LOG.info("Fetching work order out drawing entries for editing by mark number: {}", markNo);
            List<WorkOrderOutDrawingEntryDto> entries = workOrderOutDrawingEntryService.getEntriesForEditingByMarkNo(markNo);
            LOG.info("Found {} work order out drawing entries for editing", entries.size());
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            LOG.error("Error fetching work order out drawing entries for editing by mark number: {}", markNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = GET_DRAWING_ENTRY_BY_MARK_NO, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkOrderOutDrawingEntryDto> getDrawingEntryByMarkNo(@RequestParam String markNo) {
        try {
            LOG.info("Fetching drawing entry by mark number: {}", markNo);
            Optional<WorkOrderOutDrawingEntryDto> entry = workOrderOutDrawingEntryService.getDrawingEntryByMarkNo(markNo);
            if (entry.isPresent()) {
                return ResponseEntity.ok(entry.get());
            } else {
                LOG.warn("Drawing entry not found for mark number: {}", markNo);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOG.error("Error fetching drawing entry by mark number: {}", markNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // ============ UTILITY ENDPOINTS ============
    
    @GetMapping(value = "/checkWorkOrderOutEntryExists/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkEntryExists(@RequestParam String drawingNo, @RequestParam String markNo) {
        try {
            LOG.info("Checking if work order out entry exists for drawing: {} and mark: {}", drawingNo, markNo);
            boolean exists = workOrderOutDrawingEntryService.existsByDrawingAndMark(drawingNo, markNo);
            
            Map<String, Object> response = new HashMap<>();
            response.put("drawingNo", drawingNo);
            response.put("markNo", markNo);
            response.put("exists", exists);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOG.error("Error checking if work order out entry exists", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error checking entry existence: " + e.getMessage());
        }
    }
}
