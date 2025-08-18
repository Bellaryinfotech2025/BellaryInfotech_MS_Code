package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.InchMeterFabModuleDto;
import com.bellaryinfotech.service.InchMeterFabModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.persistence.*;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class InchMeterFabModuleController {

    private static final Logger LOG = LoggerFactory.getLogger(InchMeterFabModuleController.class);

    @Autowired
    private InchMeterFabModuleService inchMeterFabModuleService;

    
    public static final String CREATE_INCH_METER_FAB_MODULE = "/createInchMeterFabModule/details";
    public static final String CREATE_MULTIPLE_INCH_METER_FAB_MODULES = "/createInchMeterFabModuleEntries/details";
    public static final String GET_ALL_INCH_METER_FAB_MODULES = "/getAllInchMeterFabModules/details";
    public static final String GET_INCH_METER_FAB_MODULE_BY_ID = "/getInchMeterFabModuleById/details";
    public static final String UPDATE_INCH_METER_FAB_MODULE = "/updateInchMeterFabModule/details";
    public static final String UPDATE_INCH_METER_FAB_MODULE_STAGES = "/updateInchMeterFabModuleStages/details";
    public static final String DELETE_INCH_METER_FAB_MODULE = "/deleteInchMeterFabModule/details";
    public static final String SOFT_DELETE_INCH_METER_FAB_MODULE = "/softDeleteInchMeterFabModule/details";
        
    // Search endpoints
    public static final String GET_INCH_METER_FAB_MODULES_BY_WORK_ORDER = "/getInchMeterFabModulesByWorkOrder/details";
    public static final String GET_INCH_METER_FAB_MODULES_BY_WORK_ORDER_AND_BUILDING = "/getInchMeterFabModulesByWorkOrderAndBuilding/details";
    public static final String GET_INCH_METER_FAB_MODULES_BY_WORK_ORDER_BUILDING_DRAWING = "/getInchMeterFabModulesByWorkOrderBuildingDrawing/details";
    public static final String GET_INCH_METER_FAB_MODULES_BY_WORK_ORDER_BUILDING_DRAWING_MARK = "/getInchMeterFabModulesByWorkOrderBuildingDrawingMark/details";
    public static final String GET_INCH_METER_FAB_MODULES_BY_RA_NO = "/getInchMeterFabModulesByRaNo/details";
    public static final String SEARCH_INCH_METER_FAB_MODULES = "/searchInchMeterFabModules/details";
        
    // Utility endpoints
    public static final String GET_DISTINCT_WORK_ORDERS_FROM_INCH_METER_FAB_MODULE = "/getDistinctWorkOrdersFromInchMeterFabModule/details";
    public static final String GET_DISTINCT_BUILDING_NAMES_FROM_INCH_METER_FAB_MODULE = "/getDistinctBuildingNamesFromInchMeterFabModule/details";
    public static final String GET_DISTINCT_DRAWING_NOS_FROM_INCH_METER_FAB_MODULE = "/getDistinctDrawingNosFromInchMeterFabModule/details";
    public static final String GET_DISTINCT_MARK_NOS_FROM_INCH_METER_FAB_MODULE = "/getDistinctMarkNosFromInchMeterFabModule/details";
    public static final String GET_DISTINCT_RA_NUMBERS_FROM_INCH_METER_FAB_MODULE = "/getDistinctRaNumbersFromInchMeterFabModule/details";
    public static final String GET_DISTINCT_SERVICE_DESCRIPTIONS_FROM_INCH_METER_FAB_MODULE = "/getDistinctServiceDescriptionsFromInchMeterFabModule/details";
            
    public static final String GET_INCH_METER_FAB_MODULE_STATISTICS = "/getInchMeterFabModuleStatistics/details";
 
    @PostMapping(value = CREATE_INCH_METER_FAB_MODULE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createInchMeterFabModule(@RequestBody InchMeterFabModuleDto inchMeterFabModuleDto) {
        LOG.info("Creating new InchMeterFabModule: {}", inchMeterFabModuleDto);
                
        try {
            InchMeterFabModuleDto createdModule = inchMeterFabModuleService.createInchMeterFabModule(inchMeterFabModuleDto);
            LOG.info("Successfully created InchMeterFabModule with ID: {}", createdModule.getId());
                        
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "InchMeterFabModule created successfully");
            response.put("data", createdModule);
                        
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            LOG.error("Error creating InchMeterFabModule: {}", e.getMessage(), e);
                        
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error creating InchMeterFabModule: " + e.getMessage());
                        
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    
    @PostMapping(value = CREATE_MULTIPLE_INCH_METER_FAB_MODULES, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMultipleInchMeterFabModules(@RequestBody List<InchMeterFabModuleDto> inchMeterFabModuleDtos) {
        LOG.info("Creating {} InchMeterFabModule entries", inchMeterFabModuleDtos.size());
                
        try {
            List<InchMeterFabModuleDto> createdModules = inchMeterFabModuleService.createMultipleInchMeterFabModules(inchMeterFabModuleDtos);
            LOG.info("Successfully created {} InchMeterFabModule entries", createdModules.size());
                        
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Successfully saved " + createdModules.size() + " entries to reports!");
            response.put("data", createdModules);
            response.put("count", createdModules.size());
                        
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            LOG.error("Error creating multiple InchMeterFabModule entries: {}", e.getMessage(), e);
                        
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error saving to reports: " + e.getMessage());
                        
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

     
    @GetMapping(value = GET_ALL_INCH_METER_FAB_MODULES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllInchMeterFabModules() {
        LOG.info("Fetching all InchMeterFabModule records");
                
        try {
            List<InchMeterFabModuleDto> modules = inchMeterFabModuleService.getAllInchMeterFabModules();
            LOG.info("Found {} InchMeterFabModule records", modules.size());
                        
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", modules);
            response.put("count", modules.size());
                        
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOG.error("Error fetching all InchMeterFabModule records: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching records: " + e.getMessage());
        }
    }

    
    @GetMapping(value = GET_INCH_METER_FAB_MODULES_BY_WORK_ORDER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getInchMeterFabModulesByWorkOrder(@RequestParam String workOrder) {
        LOG.info("Fetching InchMeterFabModule records by work order: {}", workOrder);
                
        try {
            List<InchMeterFabModuleDto> modules = inchMeterFabModuleService.getInchMeterFabModulesByWorkOrder(workOrder);
            LOG.info("Found {} InchMeterFabModule records for work order: {}", modules.size(), workOrder);
                        
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            LOG.error("Error fetching InchMeterFabModule records by work order {}: {}", workOrder, e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching records: " + e.getMessage());
        }
    }

     
    @GetMapping(value = GET_INCH_METER_FAB_MODULES_BY_RA_NO, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getInchMeterFabModulesByRaNo(@RequestParam String raNo) {
        LOG.info("Fetching InchMeterFabModule records by RA No: {}", raNo);
                
        try {
            List<InchMeterFabModuleDto> modules = inchMeterFabModuleService.getInchMeterFabModulesByRaNo(raNo);
            LOG.info("Found {} InchMeterFabModule records for RA No: {}", modules.size(), raNo);
                        
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            LOG.error("Error fetching InchMeterFabModule records by RA No {}: {}", raNo, e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching records: " + e.getMessage());
        }
    }

     
    @GetMapping(value = SEARCH_INCH_METER_FAB_MODULES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchInchMeterFabModules(
            @RequestParam(required = false) String workOrder,
            @RequestParam(required = false) String buildingName,
            @RequestParam(required = false) String drawingNo,
            @RequestParam(required = false) String markNo,
            @RequestParam(required = false) String raNo) {
        LOG.info("Searching InchMeterFabModule records with criteria - workOrder: {}, buildingName: {}, drawingNo: {}, markNo: {}, raNo: {}",
                 workOrder, buildingName, drawingNo, markNo, raNo);
                
        try {
            List<InchMeterFabModuleDto> modules = inchMeterFabModuleService.searchInchMeterFabModules(workOrder, buildingName, drawingNo, markNo, raNo);
            LOG.info("Found {} InchMeterFabModule records matching search criteria", modules.size());
                        
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            LOG.error("Error searching InchMeterFabModule records: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error searching records: " + e.getMessage());
        }
    }

     
    @GetMapping(value = GET_DISTINCT_WORK_ORDERS_FROM_INCH_METER_FAB_MODULE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDistinctWorkOrdersFromInchMeterFabModule() {
        LOG.info("Fetching distinct work orders from InchMeterFabModule");
                
        try {
            List<String> workOrders = inchMeterFabModuleService.getDistinctWorkOrders();
            LOG.info("Found {} distinct work orders", workOrders.size());
                        
            return ResponseEntity.ok(workOrders);
        } catch (Exception e) {
            LOG.error("Error fetching distinct work orders: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching work orders: " + e.getMessage());
        }
    }

     
    @GetMapping(value = GET_DISTINCT_RA_NUMBERS_FROM_INCH_METER_FAB_MODULE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDistinctRaNumbersFromInchMeterFabModule() {
        LOG.info("Fetching distinct RA numbers from InchMeterFabModule");
                
        try {
            List<String> raNumbers = inchMeterFabModuleService.getDistinctRaNumbers();
            LOG.info("Found {} distinct RA numbers", raNumbers.size());
                        
            return ResponseEntity.ok(raNumbers);
        } catch (Exception e) {
            LOG.error("Error fetching distinct RA numbers: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching RA numbers: " + e.getMessage());
        }
    }

     
    @GetMapping(value = GET_DISTINCT_SERVICE_DESCRIPTIONS_FROM_INCH_METER_FAB_MODULE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDistinctServiceDescriptionsFromInchMeterFabModule(@RequestParam String workOrder) {
        LOG.info("Fetching distinct service descriptions for work order: {}", workOrder);
                
        try {
            List<String> serviceDescriptions = inchMeterFabModuleService.getDistinctServiceDescriptionsByWorkOrder(workOrder);
            LOG.info("Found {} distinct service descriptions for work order: {}", serviceDescriptions.size(), workOrder);
                        
            return ResponseEntity.ok(serviceDescriptions);
        } catch (Exception e) {
            LOG.error("Error fetching distinct service descriptions: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching service descriptions: " + e.getMessage());
        }
    }
}