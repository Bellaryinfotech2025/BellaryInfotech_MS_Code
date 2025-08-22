package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.WorkOrderOutFabricationDto;
import com.bellaryinfotech.service.WorkOrderOutFabricationService;
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
public class WorkOrderOutFabricationController {
    
    private static final Logger LOG = LoggerFactory.getLogger(WorkOrderOutFabricationController.class);
    
    @Autowired
    private WorkOrderOutFabricationService workOrderOutFabricationService;
    
     
    public static final String GET_ALL_FABRICATIONS = "/getAllWorkOrderOutFabrications/details";
    public static final String GET_FABRICATION_BY_ID = "/getWorkOrderOutFabricationById/details";
    public static final String CREATE_BULK_FABRICATIONS = "/createBulkWorkOrderOutFabrications/details";
    public static final String UPDATE_FABRICATION = "/updateWorkOrderOutFabrication/details";
    public static final String DELETE_FABRICATION = "/deleteWorkOrderOutFabrication/details";
    public static final String DELETE_FABRICATIONS_BY_WORK_ORDER = "/deleteWorkOrderOutFabricationsByWorkOrder/details";
    
    public static final String SEARCH_BY_WORK_ORDER = "/searchWorkOrderOutFabricationsByWorkOrder/details";
    public static final String SEARCH_BY_MULTIPLE_CRITERIA = "/searchWorkOrderOutFabricationsByMultipleCriteria/details";
    
    public static final String GET_RA_NOS_BY_WORK_ORDER = "/getRaNosByWorkOrder/details";
    public static final String UPDATE_RA_NOS_FOR_WORK_ORDER = "/updateRaNoForWorkOrder/details";
    
    // ============ NEW: WORK ORDER OUT RESULT ENDPOINT CONSTANTS ============
    public static final String GET_DISTINCT_CLIENT_NAMES_FROM_FABRICATION = "/getDistinctClientNamesFromWorkOrderOutFabrication/details";
    public static final String GET_DISTINCT_WORK_ORDERS_BY_CLIENT_FROM_FABRICATION = "/getDistinctWorkOrdersByClientFromWorkOrderOutFabrication/details";
    public static final String GET_DISTINCT_SERVICE_DESCRIPTIONS_BY_CLIENT_AND_WORK_ORDER_FROM_FABRICATION = "/getDistinctServiceDescriptionsByClientAndWorkOrderFromWorkOrderOutFabrication/details";
    public static final String GET_DISTINCT_SUB_AGENCY_NAMES_BY_CLIENT_WORK_ORDER_AND_SERVICE_FROM_FABRICATION = "/getDistinctSubAgencyNamesByClientWorkOrderAndServiceFromWorkOrderOutFabrication/details";
    public static final String CHECK_SERVICE_DESCRIPTION_FROM_FABRICATION = "/checkServiceDescriptionFromWorkOrderOutFabrication/details";
    public static final String GET_DISTINCT_RA_NOS_BY_CLIENT_WORK_ORDER_AND_SERVICE_FROM_FABRICATION = "/getDistinctRaNosByClientWorkOrderAndServiceFromWorkOrderOutFabrication/details";
    public static final String GET_DISTINCT_RA_NOS_BY_ALL_FILTERS_WITH_SUB_AGENCY_FROM_FABRICATION = "/getDistinctRaNosByAllFiltersWithSubAgencyFromWorkOrderOutFabrication/details";
    public static final String SEARCH_BY_CLIENT_WORK_ORDER_SERVICE_AND_RA_NO = "/searchWorkOrderOutFabricationsByClientWorkOrderServiceAndRaNo/details";
    public static final String SEARCH_BY_ALL_FILTERS_WITH_SUB_AGENCY = "/searchWorkOrderOutFabricationsByAllFiltersWithSubAgency/details";
    public static final String SEARCH_BY_ALL_FILTERS_WITHOUT_SUB_AGENCY = "/searchWorkOrderOutFabricationsByAllFiltersWithoutSubAgency/details";
    
    // ============ EXISTING CRUD OPERATIONS ============
    
    @GetMapping(value = GET_ALL_FABRICATIONS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutFabricationDto>> getAllFabrications() {
        try {
            LOG.info("Fetching all work order out fabrications");
            List<WorkOrderOutFabricationDto> fabrications = workOrderOutFabricationService.getAllFabrications();
            LOG.info("Successfully fetched {} work order out fabrications", fabrications.size());
            return ResponseEntity.ok(fabrications);
        } catch (Exception e) {
            LOG.error("Error fetching all work order out fabrications", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = GET_FABRICATION_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkOrderOutFabricationDto> getFabricationById(@RequestParam Long id) {
        try {
            LOG.info("Fetching work order out fabrication by ID: {}", id);
            Optional<WorkOrderOutFabricationDto> fabrication = workOrderOutFabricationService.getFabricationById(id);
            if (fabrication.isPresent()) {
                return ResponseEntity.ok(fabrication.get());
            } else {
                LOG.warn("Work order out fabrication not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOG.error("Error fetching work order out fabrication by ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping(value = CREATE_BULK_FABRICATIONS, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBulkFabrications(@RequestBody Map<String, Object> requestData) {
        try {
            LOG.info("Creating bulk work order out fabrications");
            
            // Extract fabrication entries
            List<Map<String, Object>> fabricationEntries = (List<Map<String, Object>>) requestData.get("fabricationEntries");
            
            if (fabricationEntries == null || fabricationEntries.isEmpty()) {
                return ResponseEntity.badRequest().body("Fabrication entries are required");
            }
            
            // Convert to DTOs
            List<WorkOrderOutFabricationDto> fabricationDtos = new java.util.ArrayList<>();
            
            for (Map<String, Object> entry : fabricationEntries) {
                WorkOrderOutFabricationDto dto = new WorkOrderOutFabricationDto();
                
                // Set filter information
                dto.setClientName((String) entry.get("clientName"));
                dto.setWorkOrder((String) entry.get("workOrder"));
                dto.setServiceDescription((String) entry.get("serviceDescription"));
                dto.setUom((String) entry.get("uom"));
                dto.setDataModule((String) entry.get("dataModule"));
                dto.setDrawingNo((String) entry.get("drawingNo"));
                dto.setBuildingName((String) entry.get("buildingName"));
                dto.setMarkNo((String) entry.get("markNo"));
                dto.setSubAgencyName((String) entry.get("subAgencyName"));
                dto.setSubAgencyWorkOrderName((String) entry.get("subAgencyWorkOrderName"));
                
                // Set RA NO information
                dto.setRaNo((String) entry.get("raNo"));
                dto.setSubAgencyRaNo((String) entry.get("subAgencyRaNo"));
                
                // Set original entry ID
                if (entry.get("originalEntryId") != null) {
                    dto.setOriginalEntryId(Long.valueOf(entry.get("originalEntryId").toString()));
                }
                
                // Set order ID
                if (entry.get("orderId") != null) {
                    dto.setOrderId(Long.valueOf(entry.get("orderId").toString()));
                }
                
                // Set item details
                dto.setItemNo((String) entry.get("itemNo"));
                dto.setSectionCode((String) entry.get("sectionCode"));
                dto.setSectionName((String) entry.get("sectionName"));
                
                // Handle BigDecimal fields
                if (entry.get("width") != null) {
                    dto.setWidth(new java.math.BigDecimal(entry.get("width").toString()));
                }
                if (entry.get("length") != null) {
                    dto.setLength(new java.math.BigDecimal(entry.get("length").toString()));
                }
                if (entry.get("itemWeight") != null) {
                    dto.setItemWeight(new java.math.BigDecimal(entry.get("itemWeight").toString()));
                }
                if (entry.get("totalItemWeight") != null) {
                    dto.setTotalItemWeight(new java.math.BigDecimal(entry.get("totalItemWeight").toString()));
                }
                if (entry.get("itemQty") != null) {
                    dto.setItemQty(Integer.valueOf(entry.get("itemQty").toString()));
                }
                
                // Set fabrication stages
                dto.setCuttingStage((Boolean) entry.getOrDefault("cuttingStage", false));
                dto.setFitUpStage((Boolean) entry.getOrDefault("fitUpStage", false));
                dto.setWeldingStage((Boolean) entry.getOrDefault("weldingStage", false));
                dto.setFinishingStage((Boolean) entry.getOrDefault("finishingStage", false));
                
                // Set remarks and status
                dto.setRemarks((String) entry.get("remarks"));
                dto.setStatus((String) entry.getOrDefault("status", "In Progress"));
                dto.setEditableEnable("original");
                
                fabricationDtos.add(dto);
            }
            
            // Save all fabrications
            List<WorkOrderOutFabricationDto> savedFabrications = workOrderOutFabricationService.createBulkFabrications(fabricationDtos);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Successfully created " + savedFabrications.size() + " work order out fabrication entries");
            response.put("createdCount", savedFabrications.size());
            response.put("entries", savedFabrications);
            
            LOG.info("Successfully created {} work order out fabrication entries", savedFabrications.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            LOG.error("Error creating bulk work order out fabrications", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error creating fabrications: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @PutMapping(value = UPDATE_FABRICATION, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateFabrication(@RequestParam Long id, @RequestBody WorkOrderOutFabricationDto fabricationDto) {
        try {
            LOG.info("Updating work order out fabrication with ID: {}", id);
            WorkOrderOutFabricationDto updatedFabrication = workOrderOutFabricationService.updateFabrication(id, fabricationDto);
            LOG.info("Successfully updated work order out fabrication with ID: {}", id);
            return ResponseEntity.ok(updatedFabrication);
        } catch (RuntimeException e) {
            LOG.error("Work order out fabrication not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            LOG.error("Error updating work order out fabrication with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating fabrication: " + e.getMessage());
        }
    }
    
    @DeleteMapping(value = DELETE_FABRICATION)
    public ResponseEntity<?> deleteFabrication(@RequestParam Long id) {
        try {
            LOG.info("Deleting work order out fabrication with ID: {}", id);
            workOrderOutFabricationService.deleteFabrication(id);
            LOG.info("Successfully deleted work order out fabrication with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error deleting work order out fabrication with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting fabrication: " + e.getMessage());
        }
    }
    
    @DeleteMapping(value = DELETE_FABRICATIONS_BY_WORK_ORDER)
    public ResponseEntity<?> deleteFabricationsByWorkOrder(@RequestParam String workOrder) {
        try {
            LOG.info("Deleting work order out fabrications by work order: {}", workOrder);
            workOrderOutFabricationService.deleteFabricationsByWorkOrder(workOrder);
            LOG.info("Successfully deleted work order out fabrications by work order: {}", workOrder);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error deleting work order out fabrications by work order: {}", workOrder, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting fabrications: " + e.getMessage());
        }
    }
    
    // ============ EXISTING SEARCH OPERATIONS ============
    
    @GetMapping(value = SEARCH_BY_WORK_ORDER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutFabricationDto>> searchByWorkOrder(@RequestParam String workOrder) {
        try {
            LOG.info("Searching work order out fabrications by work order: {}", workOrder);
            List<WorkOrderOutFabricationDto> fabrications = workOrderOutFabricationService.searchByWorkOrder(workOrder);
            LOG.info("Found {} work order out fabrications for work order: {}", fabrications.size(), workOrder);
            return ResponseEntity.ok(fabrications);
        } catch (Exception e) {
            LOG.error("Error searching work order out fabrications by work order: {}", workOrder, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = SEARCH_BY_MULTIPLE_CRITERIA, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutFabricationDto>> searchByMultipleCriteria(
            @RequestParam(required = false) String workOrder,
            @RequestParam(required = false) String clientName,
            @RequestParam(required = false) String drawingNo,
            @RequestParam(required = false) String markNo,
            @RequestParam(required = false) String buildingName) {
        try {
            LOG.info("Searching work order out fabrications by multiple criteria");
            List<WorkOrderOutFabricationDto> fabrications = workOrderOutFabricationService.searchByMultipleCriteria(workOrder, clientName, drawingNo, markNo, buildingName);
            LOG.info("Found {} work order out fabrications", fabrications.size());
            return ResponseEntity.ok(fabrications);
        } catch (Exception e) {
            LOG.error("Error searching work order out fabrications by multiple criteria", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // ============ EXISTING RA NO OPERATIONS ============
    
    @GetMapping(value = GET_RA_NOS_BY_WORK_ORDER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getRaNosByWorkOrder(@RequestParam String workOrder) {
        try {
            LOG.info("Fetching RA NOs for work order: {}", workOrder);
            Map<String, String> raNumbers = workOrderOutFabricationService.getRaNosByWorkOrder(workOrder);
            LOG.info("Found RA NOs for work order: {}", workOrder);
            return ResponseEntity.ok(raNumbers);
        } catch (Exception e) {
            LOG.error("Error fetching RA NOs for work order: {}", workOrder, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping(value = UPDATE_RA_NOS_FOR_WORK_ORDER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRaNoForWorkOrder(
            @RequestParam String workOrder,
            @RequestParam(required = false) String raNo,
            @RequestParam(required = false) String subAgencyRaNo) {
        try {
            LOG.info("Updating RA NOs for work order: {}", workOrder);
            workOrderOutFabricationService.updateRaNoForWorkOrder(workOrder, raNo, subAgencyRaNo);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Successfully updated RA NOs for work order: " + workOrder);
            response.put("workOrder", workOrder);
            response.put("raNo", raNo);
            response.put("subAgencyRaNo", subAgencyRaNo);
            
            LOG.info("Successfully updated RA NOs for work order: {}", workOrder);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOG.error("Error updating RA NOs for work order: {}", workOrder, e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error updating RA NOs: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    // ============ NEW: WORK ORDER OUT RESULT OPERATIONS ============
    
    @GetMapping(value = GET_DISTINCT_CLIENT_NAMES_FROM_FABRICATION, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDistinctClientNamesFromWorkOrderOutFabrication() {
        try {
            LOG.info("Fetching distinct client names from work order out fabrication");
            List<String> clientNames = workOrderOutFabricationService.getDistinctClientNames();
            LOG.info("Found {} distinct client names", clientNames.size());
            return ResponseEntity.ok(clientNames);
        } catch (Exception e) {
            LOG.error("Error fetching distinct client names from work order out fabrication", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = GET_DISTINCT_WORK_ORDERS_BY_CLIENT_FROM_FABRICATION, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDistinctWorkOrdersByClientFromWorkOrderOutFabrication(@RequestParam String clientName) {
        try {
            LOG.info("Fetching distinct work orders for client: {} from work order out fabrication", clientName);
            List<String> workOrders = workOrderOutFabricationService.getDistinctWorkOrdersByClientName(clientName);
            LOG.info("Found {} distinct work orders for client: {}", workOrders.size(), clientName);
            return ResponseEntity.ok(workOrders);
        } catch (Exception e) {
            LOG.error("Error fetching distinct work orders for client: {} from work order out fabrication", clientName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = GET_DISTINCT_SERVICE_DESCRIPTIONS_BY_CLIENT_AND_WORK_ORDER_FROM_FABRICATION, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDistinctServiceDescriptionsByClientAndWorkOrderFromWorkOrderOutFabrication(
            @RequestParam String clientName, @RequestParam String workOrder) {
        try {
            LOG.info("Fetching distinct service descriptions for client: {} and work order: {} from work order out fabrication", clientName, workOrder);
            List<String> serviceDescriptions = workOrderOutFabricationService.getDistinctServiceDescriptionsByClientAndWorkOrder(clientName, workOrder);
            LOG.info("Found {} distinct service descriptions", serviceDescriptions.size());
            return ResponseEntity.ok(serviceDescriptions);
        } catch (Exception e) {
            LOG.error("Error fetching distinct service descriptions from work order out fabrication", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = GET_DISTINCT_SUB_AGENCY_NAMES_BY_CLIENT_WORK_ORDER_AND_SERVICE_FROM_FABRICATION, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDistinctSubAgencyNamesByClientWorkOrderAndServiceFromWorkOrderOutFabrication(
            @RequestParam String clientName, @RequestParam String workOrder, @RequestParam String serviceDescription) {
        try {
            LOG.info("Fetching distinct sub agency names for client: {}, work order: {}, and service: {} from work order out fabrication", clientName, workOrder, serviceDescription);
            List<String> subAgencyNames = workOrderOutFabricationService.getDistinctSubAgencyNamesByClientWorkOrderAndService(clientName, workOrder, serviceDescription);
            LOG.info("Found {} distinct sub agency names", subAgencyNames.size());
            return ResponseEntity.ok(subAgencyNames);
        } catch (Exception e) {
            LOG.error("Error fetching distinct sub agency names from work order out fabrication", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = CHECK_SERVICE_DESCRIPTION_FROM_FABRICATION, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> checkServiceDescriptionFromWorkOrderOutFabrication(
            @RequestParam String clientName, @RequestParam String workOrder, @RequestParam String serviceDescription) {
        try {
            LOG.info("Checking if service description exists in work order out fabrication for client: {}, work order: {}, service: {}", clientName, workOrder, serviceDescription);
            boolean exists = workOrderOutFabricationService.isServiceDescriptionFromWorkOrderOutFabrication(clientName, workOrder, serviceDescription);
            
            Map<String, Boolean> response = new HashMap<>();
            response.put("existsInFabrication", exists);
            
            LOG.info("Service description exists in fabrication: {}", exists);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOG.error("Error checking service description from work order out fabrication", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = GET_DISTINCT_RA_NOS_BY_CLIENT_WORK_ORDER_AND_SERVICE_FROM_FABRICATION, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDistinctRaNosByClientWorkOrderAndServiceFromWorkOrderOutFabrication(
            @RequestParam String clientName, @RequestParam String workOrder, @RequestParam String serviceDescription) {
        try {
            LOG.info("Fetching distinct RA NOs for client: {}, work order: {}, and service: {} from work order out fabrication", clientName, workOrder, serviceDescription);
            List<String> raNumbers = workOrderOutFabricationService.getDistinctRaNosByClientWorkOrderAndService(clientName, workOrder, serviceDescription);
            LOG.info("Found {} distinct RA NOs", raNumbers.size());
            return ResponseEntity.ok(raNumbers);
        } catch (Exception e) {
            LOG.error("Error fetching distinct RA NOs from work order out fabrication", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = GET_DISTINCT_RA_NOS_BY_ALL_FILTERS_WITH_SUB_AGENCY_FROM_FABRICATION, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDistinctRaNosByAllFiltersWithSubAgencyFromWorkOrderOutFabrication(
            @RequestParam String clientName, @RequestParam String workOrder, 
            @RequestParam String serviceDescription, @RequestParam String subAgencyName) {
        try {
            LOG.info("Fetching distinct RA NOs with sub agency filter for client: {}, work order: {}, service: {}, sub agency: {}", clientName, workOrder, serviceDescription, subAgencyName);
            List<String> raNumbers = workOrderOutFabricationService.getDistinctRaNosByAllFiltersWithSubAgency(clientName, workOrder, serviceDescription, subAgencyName);
            LOG.info("Found {} distinct RA NOs with sub agency filter", raNumbers.size());
            return ResponseEntity.ok(raNumbers);
        } catch (Exception e) {
            LOG.error("Error fetching distinct RA NOs with sub agency filter from work order out fabrication", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = SEARCH_BY_CLIENT_WORK_ORDER_SERVICE_AND_RA_NO, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutFabricationDto>> searchWorkOrderOutFabricationsByClientWorkOrderServiceAndRaNo(
            @RequestParam String clientName, @RequestParam String workOrder, 
            @RequestParam String serviceDescription, @RequestParam String raNo) {
        try {
            LOG.info("Searching work order out fabrications by client: {}, work order: {}, service: {}, and RA NO: {}", clientName, workOrder, serviceDescription, raNo);
            List<WorkOrderOutFabricationDto> fabrications = workOrderOutFabricationService.searchByClientWorkOrderServiceAndRaNo(clientName, workOrder, serviceDescription, raNo);
            LOG.info("Found {} work order out fabrications", fabrications.size());
            return ResponseEntity.ok(fabrications);
        } catch (Exception e) {
            LOG.error("Error searching work order out fabrications by client, work order, service and RA NO", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = SEARCH_BY_ALL_FILTERS_WITH_SUB_AGENCY, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutFabricationDto>> searchWorkOrderOutFabricationsByAllFiltersWithSubAgency(
            @RequestParam String clientName, @RequestParam String workOrder, 
            @RequestParam String serviceDescription, @RequestParam String raNo, @RequestParam String subAgencyName) {
        try {
            LOG.info("Searching work order out fabrications with sub agency by client: {}, work order: {}, service: {}, RA NO: {}, sub agency: {}", clientName, workOrder, serviceDescription, raNo, subAgencyName);
            List<WorkOrderOutFabricationDto> fabrications = workOrderOutFabricationService.searchByAllFiltersWithSubAgency(clientName, workOrder, serviceDescription, raNo, subAgencyName);
            LOG.info("Found {} work order out fabrications with sub agency filter", fabrications.size());
            return ResponseEntity.ok(fabrications);
        } catch (Exception e) {
            LOG.error("Error searching work order out fabrications with sub agency filter", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = SEARCH_BY_ALL_FILTERS_WITHOUT_SUB_AGENCY, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutFabricationDto>> searchWorkOrderOutFabricationsByAllFiltersWithoutSubAgency(
            @RequestParam String clientName, @RequestParam String workOrder, 
            @RequestParam String serviceDescription, @RequestParam String raNo) {
        try {
            LOG.info("Searching work order out fabrications without sub agency by client: {}, work order: {}, service: {}, RA NO: {}", clientName, workOrder, serviceDescription, raNo);
            List<WorkOrderOutFabricationDto> fabrications = workOrderOutFabricationService.searchByAllFiltersWithoutSubAgency(clientName, workOrder, serviceDescription, raNo);
            LOG.info("Found {} work order out fabrications without sub agency filter", fabrications.size());
            return ResponseEntity.ok(fabrications);
        } catch (Exception e) {
            LOG.error("Error searching work order out fabrications without sub agency filter", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // ============ EXISTING UTILITY ENDPOINTS ============
    
    @GetMapping(value = "/checkWorkOrderOutFabricationExists/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkFabricationExists(@RequestParam String workOrder, @RequestParam String drawingNo, @RequestParam String markNo) {
        try {
            LOG.info("Checking if work order out fabrication exists for work order: {}, drawing: {} and mark: {}", workOrder, drawingNo, markNo);
            boolean exists = workOrderOutFabricationService.existsByWorkOrderAndDrawingAndMark(workOrder, drawingNo, markNo);
            
            Map<String, Object> response = new HashMap<>();
            response.put("workOrder", workOrder);
            response.put("drawingNo", drawingNo);
            response.put("markNo", markNo);
            response.put("exists", exists);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOG.error("Error checking if work order out fabrication exists", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error checking fabrication existence: " + e.getMessage());
        }
    }
}
