package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.SquareMeterFabModuleDto;
import com.bellaryinfotech.service.SquareMeterFabModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class SquareMeterFabModuleController {
    
    private static final Logger LOG = LoggerFactory.getLogger(SquareMeterFabModuleController.class);
    
    @Autowired
    private SquareMeterFabModuleService squareMeterFabModuleService;
    
    
    public static final String CREATE_SQUARE_METER_FAB_MODULE = "/createSquareMeterFabModule/details";
    public static final String CREATE_MULTIPLE_SQUARE_METER_FAB_MODULES = "/createSquareMeterFabModuleEntries/details";
    public static final String GET_ALL_SQUARE_METER_FAB_MODULES = "/getAllSquareMeterFabModules/details";
    public static final String GET_SQUARE_METER_FAB_MODULE_BY_ID = "/getSquareMeterFabModuleById/details";
    public static final String UPDATE_SQUARE_METER_FAB_MODULE = "/updateSquareMeterFabModule/details";
    public static final String UPDATE_SQUARE_METER_FAB_MODULE_STAGES = "/updateSquareMeterFabModuleStages/details";
    public static final String DELETE_SQUARE_METER_FAB_MODULE = "/deleteSquareMeterFabModule/details";
    public static final String SOFT_DELETE_SQUARE_METER_FAB_MODULE = "/softDeleteSquareMeterFabModule/details";
    
    
    public static final String GET_SQUARE_METER_FAB_MODULES_BY_WORK_ORDER = "/getSquareMeterFabModulesByWorkOrder/details";
    public static final String GET_SQUARE_METER_FAB_MODULES_BY_WORK_ORDER_AND_BUILDING = "/getSquareMeterFabModulesByWorkOrderAndBuilding/details";
    public static final String GET_SQUARE_METER_FAB_MODULES_BY_WORK_ORDER_BUILDING_DRAWING = "/getSquareMeterFabModulesByWorkOrderBuildingDrawing/details";
    public static final String GET_SQUARE_METER_FAB_MODULES_BY_WORK_ORDER_BUILDING_DRAWING_MARK = "/getSquareMeterFabModulesByWorkOrderBuildingDrawingMark/details";
    public static final String GET_SQUARE_METER_FAB_MODULES_BY_RA_NO = "/getSquareMeterFabModulesByRaNo/details";
    public static final String SEARCH_SQUARE_METER_FAB_MODULES = "/searchSquareMeterFabModules/details";
    
     
    public static final String GET_DISTINCT_WORK_ORDERS_FROM_SQUARE_METER_FAB_MODULE = "/getDistinctWorkOrdersFromSquareMeterFabModule/details";
    public static final String GET_DISTINCT_BUILDING_NAMES_FROM_SQUARE_METER_FAB_MODULE = "/getDistinctBuildingNamesFromSquareMeterFabModule/details";
    public static final String GET_DISTINCT_DRAWING_NOS_FROM_SQUARE_METER_FAB_MODULE = "/getDistinctDrawingNosFromSquareMeterFabModule/details";
    public static final String GET_DISTINCT_MARK_NOS_FROM_SQUARE_METER_FAB_MODULE = "/getDistinctMarkNosFromSquareMeterFabModule/details";
    public static final String GET_DISTINCT_RA_NUMBERS_FROM_SQUARE_METER_FAB_MODULE = "/getDistinctRaNumbersFromSquareMeterFabModule/details";
    public static final String GET_DISTINCT_SERVICE_DESCRIPTIONS_FROM_SQUARE_METER_FAB_MODULE = "/getDistinctServiceDescriptionsFromSquareMeterFabModule/details";
    
    public static final String GET_SQUARE_METER_FAB_MODULE_STATISTICS = "/getSquareMeterFabModuleStatistics/details";
    
    @PostMapping(value = CREATE_SQUARE_METER_FAB_MODULE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSquareMeterFabModule(@RequestBody SquareMeterFabModuleDto squareMeterFabModuleDto) {
        LOG.info("Creating new SquareMeterFabModule: {}", squareMeterFabModuleDto);
        
        try {
            SquareMeterFabModuleDto createdModule = squareMeterFabModuleService.createSquareMeterFabModule(squareMeterFabModuleDto);
            LOG.info("Successfully created SquareMeterFabModule with ID: {}", createdModule.getId());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "SquareMeterFabModule created successfully");
            response.put("data", createdModule);
            
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            LOG.error("Error creating SquareMeterFabModule: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error creating SquareMeterFabModule: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @PostMapping(value = CREATE_MULTIPLE_SQUARE_METER_FAB_MODULES, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMultipleSquareMeterFabModules(@RequestBody List<SquareMeterFabModuleDto> squareMeterFabModuleDtos) {
        LOG.info("Creating {} SquareMeterFabModule entries", squareMeterFabModuleDtos.size());
        
        try {
            List<SquareMeterFabModuleDto> createdModules = squareMeterFabModuleService.createMultipleSquareMeterFabModules(squareMeterFabModuleDtos);
            LOG.info("Successfully created {} SquareMeterFabModule entries", createdModules.size());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Successfully saved " + createdModules.size() + " entries to reports!");
            response.put("data", createdModules);
            response.put("count", createdModules.size());
            
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            LOG.error("Error creating multiple SquareMeterFabModule entries: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error saving to reports: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @GetMapping(value = GET_ALL_SQUARE_METER_FAB_MODULES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllSquareMeterFabModules() {
        LOG.info("Fetching all SquareMeterFabModule records");
        
        try {
            List<SquareMeterFabModuleDto> modules = squareMeterFabModuleService.getAllSquareMeterFabModules();
            LOG.info("Found {} SquareMeterFabModule records", modules.size());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", modules);
            response.put("count", modules.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOG.error("Error fetching all SquareMeterFabModule records: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching records: " + e.getMessage());
        }
    }
    
    @GetMapping(value = GET_SQUARE_METER_FAB_MODULES_BY_WORK_ORDER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSquareMeterFabModulesByWorkOrder(@RequestParam String workOrder) {
        LOG.info("Fetching SquareMeterFabModule records by work order: {}", workOrder);
        
        try {
            List<SquareMeterFabModuleDto> modules = squareMeterFabModuleService.getSquareMeterFabModulesByWorkOrder(workOrder);
            LOG.info("Found {} SquareMeterFabModule records for work order: {}", modules.size(), workOrder);
            
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            LOG.error("Error fetching SquareMeterFabModule records by work order {}: {}", workOrder, e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching records: " + e.getMessage());
        }
    }
    
    @GetMapping(value = GET_SQUARE_METER_FAB_MODULES_BY_WORK_ORDER_AND_BUILDING, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSquareMeterFabModulesByWorkOrderAndBuilding(@RequestParam String workOrder, @RequestParam String buildingName) {
        LOG.info("Fetching SquareMeterFabModule records by work order: {} and building: {}", workOrder, buildingName);
        
        try {
            List<SquareMeterFabModuleDto> modules = squareMeterFabModuleService.getSquareMeterFabModulesByWorkOrderAndBuilding(workOrder, buildingName);
            LOG.info("Found {} SquareMeterFabModule records", modules.size());
            
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            LOG.error("Error fetching SquareMeterFabModule records: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching records: " + e.getMessage());
        }
    }
    
    @GetMapping(value = GET_SQUARE_METER_FAB_MODULES_BY_WORK_ORDER_BUILDING_DRAWING, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSquareMeterFabModulesByWorkOrderBuildingDrawing(@RequestParam String workOrder, @RequestParam String buildingName, @RequestParam String drawingNo) {
        LOG.info("Fetching SquareMeterFabModule records by work order: {}, building: {}, drawing: {}", workOrder, buildingName, drawingNo);
        
        try {
            List<SquareMeterFabModuleDto> modules = squareMeterFabModuleService.getSquareMeterFabModulesByWorkOrderBuildingDrawing(workOrder, buildingName, drawingNo);
            LOG.info("Found {} SquareMeterFabModule records", modules.size());
            
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            LOG.error("Error fetching SquareMeterFabModule records: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching records: " + e.getMessage());
        }
    }
    
    @GetMapping(value = GET_SQUARE_METER_FAB_MODULES_BY_WORK_ORDER_BUILDING_DRAWING_MARK, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSquareMeterFabModulesByWorkOrderBuildingDrawingMark(@RequestParam String workOrder, @RequestParam String buildingName, @RequestParam String drawingNo, @RequestParam String markNo) {
        LOG.info("Fetching SquareMeterFabModule records by work order: {}, building: {}, drawing: {}, mark: {}", workOrder, buildingName, drawingNo, markNo);
        
        try {
            List<SquareMeterFabModuleDto> modules = squareMeterFabModuleService.getSquareMeterFabModulesByWorkOrderBuildingDrawingMark(workOrder, buildingName, drawingNo, markNo);
            LOG.info("Found {} SquareMeterFabModule records", modules.size());
            
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            LOG.error("Error fetching SquareMeterFabModule records: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching records: " + e.getMessage());
        }
    }
    
    @GetMapping(value = GET_SQUARE_METER_FAB_MODULES_BY_RA_NO, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSquareMeterFabModulesByRaNo(@RequestParam String raNo) {
        LOG.info("Fetching SquareMeterFabModule records by RA No: {}", raNo);
        
        try {
            List<SquareMeterFabModuleDto> modules = squareMeterFabModuleService.getSquareMeterFabModulesByRaNo(raNo);
            LOG.info("Found {} SquareMeterFabModule records for RA No: {}", modules.size(), raNo);
            
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            LOG.error("Error fetching SquareMeterFabModule records by RA No {}: {}", raNo, e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching records: " + e.getMessage());
        }
    }
    
    @GetMapping(value = SEARCH_SQUARE_METER_FAB_MODULES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchSquareMeterFabModules(
            @RequestParam(required = false) String workOrder,
            @RequestParam(required = false) String buildingName,
            @RequestParam(required = false) String drawingNo,
            @RequestParam(required = false) String markNo,
            @RequestParam(required = false) String raNo) {
        LOG.info("Searching SquareMeterFabModule records with criteria - workOrder: {}, buildingName: {}, drawingNo: {}, markNo: {}, raNo: {}",
                workOrder, buildingName, drawingNo, markNo, raNo);
        
        try {
            List<SquareMeterFabModuleDto> modules = squareMeterFabModuleService.searchSquareMeterFabModules(workOrder, buildingName, drawingNo, markNo, raNo);
            LOG.info("Found {} SquareMeterFabModule records matching search criteria", modules.size());
            
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            LOG.error("Error searching SquareMeterFabModule records: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error searching records: " + e.getMessage());
        }
    }
    
    @PutMapping(value = UPDATE_SQUARE_METER_FAB_MODULE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateSquareMeterFabModule(@RequestParam Long id, @RequestBody SquareMeterFabModuleDto squareMeterFabModuleDto) {
        LOG.info("Updating SquareMeterFabModule with ID: {}", id);
        
        try {
            SquareMeterFabModuleDto updatedModule = squareMeterFabModuleService.updateSquareMeterFabModule(id, squareMeterFabModuleDto);
            if (updatedModule != null) {
                LOG.info("Successfully updated SquareMeterFabModule with ID: {}", id);
                
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "SquareMeterFabModule updated successfully");
                response.put("data", updatedModule);
                
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "SquareMeterFabModule not found with ID: " + id);
                
                return ResponseEntity.badRequest().body(errorResponse);
            }
        } catch (Exception e) {
            LOG.error("Error updating SquareMeterFabModule with ID {}: {}", id, e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error updating SquareMeterFabModule: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @PutMapping(value = UPDATE_SQUARE_METER_FAB_MODULE_STAGES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateSquareMeterFabModuleStages(
            @RequestParam Long id,
            @RequestParam(required = false) String cuttingStage,
            @RequestParam(required = false) String fitUpStage,
            @RequestParam(required = false) String weldingStage,
            @RequestParam(required = false) String finishingStage) {
        LOG.info("Updating SquareMeterFabModule stages for ID: {}", id);
        
        try {
            SquareMeterFabModuleDto updatedModule = squareMeterFabModuleService.updateSquareMeterFabModuleStages(id, cuttingStage, fitUpStage, weldingStage, finishingStage);
            if (updatedModule != null) {
                LOG.info("Successfully updated SquareMeterFabModule stages for ID: {}", id);
                
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "SquareMeterFabModule stages updated successfully");
                response.put("data", updatedModule);
                
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "SquareMeterFabModule not found with ID: " + id);
                
                return ResponseEntity.badRequest().body(errorResponse);
            }
        } catch (Exception e) {
            LOG.error("Error updating SquareMeterFabModule stages for ID {}: {}", id, e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error updating SquareMeterFabModule stages: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @DeleteMapping(value = DELETE_SQUARE_METER_FAB_MODULE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteSquareMeterFabModule(@RequestParam Long id) {
        LOG.info("Deleting SquareMeterFabModule with ID: {}", id);
        
        try {
            boolean deleted = squareMeterFabModuleService.deleteSquareMeterFabModule(id);
            if (deleted) {
                LOG.info("Successfully deleted SquareMeterFabModule with ID: {}", id);
                
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "SquareMeterFabModule deleted successfully");
                
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "SquareMeterFabModule not found with ID: " + id);
                
                return ResponseEntity.badRequest().body(errorResponse);
            }
        } catch (Exception e) {
            LOG.error("Error deleting SquareMeterFabModule with ID {}: {}", id, e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error deleting SquareMeterFabModule: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @DeleteMapping(value = SOFT_DELETE_SQUARE_METER_FAB_MODULE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> softDeleteSquareMeterFabModule(@RequestParam Long id) {
        LOG.info("Soft deleting SquareMeterFabModule with ID: {}", id);
        
        try {
            boolean deleted = squareMeterFabModuleService.softDeleteSquareMeterFabModule(id);
            if (deleted) {
                LOG.info("Successfully soft deleted SquareMeterFabModule with ID: {}", id);
                
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "SquareMeterFabModule soft deleted successfully");
                
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "SquareMeterFabModule not found with ID: " + id);
                
                return ResponseEntity.badRequest().body(errorResponse);
            }
        } catch (Exception e) {
            LOG.error("Error soft deleting SquareMeterFabModule with ID {}: {}", id, e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error soft deleting SquareMeterFabModule: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @GetMapping(value = GET_DISTINCT_WORK_ORDERS_FROM_SQUARE_METER_FAB_MODULE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDistinctWorkOrdersFromSquareMeterFabModule() {
        LOG.info("Fetching distinct work orders from SquareMeterFabModule");
        
        try {
            List<String> workOrders = squareMeterFabModuleService.getDistinctWorkOrders();
            LOG.info("Found {} distinct work orders", workOrders.size());
            
            return ResponseEntity.ok(workOrders);
        } catch (Exception e) {
            LOG.error("Error fetching distinct work orders: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching work orders: " + e.getMessage());
        }
    }
    
    @GetMapping(value = GET_DISTINCT_BUILDING_NAMES_FROM_SQUARE_METER_FAB_MODULE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDistinctBuildingNamesFromSquareMeterFabModule(@RequestParam String workOrder) {
        LOG.info("Fetching distinct building names for work order: {}", workOrder);
        
        try {
            List<String> buildingNames = squareMeterFabModuleService.getDistinctBuildingNamesByWorkOrder(workOrder);
            LOG.info("Found {} distinct building names for work order: {}", buildingNames.size(), workOrder);
            
            return ResponseEntity.ok(buildingNames);
        } catch (Exception e) {
            LOG.error("Error fetching distinct building names: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching building names: " + e.getMessage());
        }
    }
    
    @GetMapping(value = GET_DISTINCT_DRAWING_NOS_FROM_SQUARE_METER_FAB_MODULE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDistinctDrawingNosFromSquareMeterFabModule(@RequestParam String workOrder, @RequestParam String buildingName) {
        LOG.info("Fetching distinct drawing numbers for work order: {} and building: {}", workOrder, buildingName);
        
        try {
            List<String> drawingNos = squareMeterFabModuleService.getDistinctDrawingNosByWorkOrderAndBuildingName(workOrder, buildingName);
            LOG.info("Found {} distinct drawing numbers", drawingNos.size());
            
            return ResponseEntity.ok(drawingNos);
        } catch (Exception e) {
            LOG.error("Error fetching distinct drawing numbers: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching drawing numbers: " + e.getMessage());
        }
    }
    
    @GetMapping(value = GET_DISTINCT_MARK_NOS_FROM_SQUARE_METER_FAB_MODULE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDistinctMarkNosFromSquareMeterFabModule(@RequestParam String workOrder, @RequestParam String buildingName, @RequestParam String drawingNo) {
        LOG.info("Fetching distinct mark numbers for work order: {}, building: {}, drawing: {}", workOrder, buildingName, drawingNo);
        
        try {
            List<String> markNos = squareMeterFabModuleService.getDistinctMarkNosByWorkOrderBuildingNameAndDrawingNo(workOrder, buildingName, drawingNo);
            LOG.info("Found {} distinct mark numbers", markNos.size());
            
            return ResponseEntity.ok(markNos);
        } catch (Exception e) {
            LOG.error("Error fetching distinct mark numbers: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching mark numbers: " + e.getMessage());
        }
    }
    
    @GetMapping(value = GET_DISTINCT_RA_NUMBERS_FROM_SQUARE_METER_FAB_MODULE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDistinctRaNumbersFromSquareMeterFabModule() {
        LOG.info("Fetching distinct RA numbers from SquareMeterFabModule");
        
        try {
            List<String> raNumbers = squareMeterFabModuleService.getDistinctRaNumbers();
            LOG.info("Found {} distinct RA numbers", raNumbers.size());
            
            return ResponseEntity.ok(raNumbers);
        } catch (Exception e) {
            LOG.error("Error fetching distinct RA numbers: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching RA numbers: " + e.getMessage());
        }
    }
    
    @GetMapping(value = GET_DISTINCT_SERVICE_DESCRIPTIONS_FROM_SQUARE_METER_FAB_MODULE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDistinctServiceDescriptionsFromSquareMeterFabModule(@RequestParam String workOrder) {
        LOG.info("Fetching distinct service descriptions for work order: {}", workOrder);
        
        try {
            List<String> serviceDescriptions = squareMeterFabModuleService.getDistinctServiceDescriptionsByWorkOrder(workOrder);
            LOG.info("Found {} distinct service descriptions for work order: {}", serviceDescriptions.size(), workOrder);
            
            return ResponseEntity.ok(serviceDescriptions);
        } catch (Exception e) {
            LOG.error("Error fetching distinct service descriptions: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching service descriptions: " + e.getMessage());
        }
    }
    
    @GetMapping(value = GET_SQUARE_METER_FAB_MODULE_STATISTICS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSquareMeterFabModuleStatistics() {
        LOG.info("Fetching SquareMeterFabModule statistics");
        
        try {
            Map<String, Long> statistics = squareMeterFabModuleService.getSquareMeterFabModuleStatistics();
            LOG.info("Successfully fetched SquareMeterFabModule statistics");
            
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            LOG.error("Error fetching SquareMeterFabModule statistics: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching statistics: " + e.getMessage());
        }
    }
}
