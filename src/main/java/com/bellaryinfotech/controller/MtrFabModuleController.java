package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.MtrFabModuleDto;
import com.bellaryinfotech.service.MtrFabModuleService;
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
public class MtrFabModuleController {

    private static final Logger LOG = LoggerFactory.getLogger(MtrFabModuleController.class);

    @Autowired
    private MtrFabModuleService mtrFabModuleService;

    
    public static final String CREATE_MTR_FAB_MODULE = "/createMtrFabModule/details";
    public static final String CREATE_MULTIPLE_MTR_FAB_MODULES = "/createMtrFabModuleEntries/details";
    public static final String GET_ALL_MTR_FAB_MODULES = "/getAllMtrFabModules/details";
    public static final String GET_MTR_FAB_MODULE_BY_ID = "/getMtrFabModuleById/details";
    public static final String UPDATE_MTR_FAB_MODULE = "/updateMtrFabModule/details";
    public static final String UPDATE_MTR_FAB_MODULE_STAGES = "/updateMtrFabModuleStages/details";
    public static final String DELETE_MTR_FAB_MODULE = "/deleteMtrFabModule/details";
    public static final String SOFT_DELETE_MTR_FAB_MODULE = "/softDeleteMtrFabModule/details";
    
     
    public static final String GET_MTR_FAB_MODULES_BY_WORK_ORDER = "/getMtrFabModulesByWorkOrder/details";
    public static final String GET_MTR_FAB_MODULES_BY_WORK_ORDER_AND_BUILDING = "/getMtrFabModulesByWorkOrderAndBuilding/details";
    public static final String GET_MTR_FAB_MODULES_BY_WORK_ORDER_BUILDING_DRAWING = "/getMtrFabModulesByWorkOrderBuildingDrawing/details";
    public static final String GET_MTR_FAB_MODULES_BY_WORK_ORDER_BUILDING_DRAWING_MARK = "/getMtrFabModulesByWorkOrderBuildingDrawingMark/details";
    public static final String GET_MTR_FAB_MODULES_BY_RA_NO = "/getMtrFabModulesByRaNo/details";
    public static final String SEARCH_MTR_FAB_MODULES = "/searchMtrFabModules/details";
    
     
    public static final String GET_DISTINCT_WORK_ORDERS_FROM_FAB_MODULE = "/getDistinctWorkOrdersFromFabModule/details";
    public static final String GET_DISTINCT_BUILDING_NAMES_FROM_FAB_MODULE = "/getDistinctBuildingNamesFromFabModule/details";
    public static final String GET_DISTINCT_DRAWING_NOS_FROM_FAB_MODULE = "/getDistinctDrawingNosFromFabModule/details";
    public static final String GET_DISTINCT_MARK_NOS_FROM_FAB_MODULE = "/getDistinctMarkNosFromFabModule/details";
    public static final String GET_DISTINCT_RA_NUMBERS_FROM_FAB_MODULE = "/getDistinctRaNumbersFromFabModule/details";
    
    
    public static final String GET_FAB_MODULE_STATISTICS = "/getFabModuleStatistics/details";

     
    @PostMapping(value = CREATE_MTR_FAB_MODULE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMtrFabModule(@RequestBody MtrFabModuleDto mtrFabModuleDto) {
        LOG.info("Creating new MtrFabModule: {}", mtrFabModuleDto);
        
        try {
            MtrFabModuleDto createdModule = mtrFabModuleService.createMtrFabModule(mtrFabModuleDto);
            LOG.info("Successfully created MtrFabModule with ID: {}", createdModule.getId());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "MtrFabModule created successfully");
            response.put("data", createdModule);
            
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            LOG.error("Error creating MtrFabModule: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error creating MtrFabModule: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

     
    @PostMapping(value = CREATE_MULTIPLE_MTR_FAB_MODULES, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMultipleMtrFabModules(@RequestBody List<MtrFabModuleDto> mtrFabModuleDtos) {
        LOG.info("Creating {} MtrFabModule entries", mtrFabModuleDtos.size());
        
        try {
            List<MtrFabModuleDto> createdModules = mtrFabModuleService.createMultipleMtrFabModules(mtrFabModuleDtos);
            LOG.info("Successfully created {} MtrFabModule entries", createdModules.size());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Successfully saved " + createdModules.size() + " entries to reports!");
            response.put("data", createdModules);
            response.put("count", createdModules.size());
            
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            LOG.error("Error creating multiple MtrFabModule entries: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error saving to reports: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    
    @GetMapping(value = GET_ALL_MTR_FAB_MODULES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllMtrFabModules() {
        LOG.info("Fetching all MtrFabModule records");
        
        try {
            List<MtrFabModuleDto> modules = mtrFabModuleService.getAllMtrFabModules();
            LOG.info("Found {} MtrFabModule records", modules.size());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", modules);
            response.put("count", modules.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOG.error("Error fetching all MtrFabModule records: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching records: " + e.getMessage());
        }
    }

     
    @GetMapping(value = GET_MTR_FAB_MODULE_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMtrFabModuleById(@RequestParam Long id) {
        LOG.info("Fetching MtrFabModule by ID: {}", id);
        
        try {
            Optional<MtrFabModuleDto> module = mtrFabModuleService.getMtrFabModuleById(id);
            
            if (module.isPresent()) {
                LOG.info("Found MtrFabModule with ID: {}", id);
                return ResponseEntity.ok(module.get());
            } else {
                LOG.warn("MtrFabModule not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOG.error("Error fetching MtrFabModule by ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching record: " + e.getMessage());
        }
    }

    
    @GetMapping(value = GET_MTR_FAB_MODULES_BY_WORK_ORDER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMtrFabModulesByWorkOrder(@RequestParam String workOrder) {
        LOG.info("Fetching MtrFabModule records by work order: {}", workOrder);
        
        try {
            List<MtrFabModuleDto> modules = mtrFabModuleService.getMtrFabModulesByWorkOrder(workOrder);
            LOG.info("Found {} MtrFabModule records for work order: {}", modules.size(), workOrder);
            
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            LOG.error("Error fetching MtrFabModule records by work order {}: {}", workOrder, e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching records: " + e.getMessage());
        }
    }

     
    @GetMapping(value = GET_MTR_FAB_MODULES_BY_WORK_ORDER_AND_BUILDING, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMtrFabModulesByWorkOrderAndBuilding(@RequestParam String workOrder, @RequestParam String buildingName) {
        LOG.info("Fetching MtrFabModule records by work order: {} and building name: {}", workOrder, buildingName);
        
        try {
            List<MtrFabModuleDto> modules = mtrFabModuleService.getMtrFabModulesByWorkOrderAndBuildingName(workOrder, buildingName);
            LOG.info("Found {} MtrFabModule records", modules.size());
            
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            LOG.error("Error fetching MtrFabModule records: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching records: " + e.getMessage());
        }
    }

     
    @GetMapping(value = GET_MTR_FAB_MODULES_BY_WORK_ORDER_BUILDING_DRAWING, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMtrFabModulesByWorkOrderBuildingDrawing(@RequestParam String workOrder, @RequestParam String buildingName, @RequestParam String drawingNo) {
        LOG.info("Fetching MtrFabModule records by work order: {}, building name: {}, drawing no: {}", workOrder, buildingName, drawingNo);
        
        try {
            List<MtrFabModuleDto> modules = mtrFabModuleService.getMtrFabModulesByWorkOrderAndBuildingNameAndDrawingNo(workOrder, buildingName, drawingNo);
            LOG.info("Found {} MtrFabModule records", modules.size());
            
            return ResponseEntity.ok(modules);
        }
        catch (Exception e) {
            LOG.error("Error fetching MtrFabModule records: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching records: " + e.getMessage());
        }
    }

     
    @GetMapping(value = GET_MTR_FAB_MODULES_BY_WORK_ORDER_BUILDING_DRAWING_MARK, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMtrFabModulesByWorkOrderBuildingDrawingMark(@RequestParam String workOrder, @RequestParam String buildingName, @RequestParam String drawingNo, @RequestParam String markNo) {
        LOG.info("Fetching MtrFabModule records by work order: {}, building name: {}, drawing no: {}, mark no: {}", workOrder, buildingName, drawingNo, markNo);
        
        try {
            List<MtrFabModuleDto> modules = mtrFabModuleService.getMtrFabModulesByWorkOrderAndBuildingNameAndDrawingNoAndMarkNo(workOrder, buildingName, drawingNo, markNo);
            LOG.info("Found {} MtrFabModule records", modules.size());
            
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            LOG.error("Error fetching MtrFabModule records: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching records: " + e.getMessage());
        }
    }

  
    @GetMapping(value = GET_MTR_FAB_MODULES_BY_RA_NO, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMtrFabModulesByRaNo(@RequestParam String raNo) {
        LOG.info("Fetching MtrFabModule records by RA No: {}", raNo);
        
        try {
            List<MtrFabModuleDto> modules = mtrFabModuleService.getMtrFabModulesByRaNo(raNo);
            LOG.info("Found {} MtrFabModule records for RA No: {}", modules.size(), raNo);
            
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            LOG.error("Error fetching MtrFabModule records by RA No {}: {}", raNo, e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching records: " + e.getMessage());
        }
    }

    
    @GetMapping(value = SEARCH_MTR_FAB_MODULES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchMtrFabModules(
            @RequestParam(required = false) String workOrder,
            @RequestParam(required = false) String buildingName,
            @RequestParam(required = false) String drawingNo,
            @RequestParam(required = false) String markNo,
            @RequestParam(required = false) String raNo) {
        LOG.info("Searching MtrFabModule records with criteria - workOrder: {}, buildingName: {}, drawingNo: {}, markNo: {}, raNo: {}", 
                workOrder, buildingName, drawingNo, markNo, raNo);
        
        try {
            List<MtrFabModuleDto> modules = mtrFabModuleService.searchMtrFabModules(workOrder, buildingName, drawingNo, markNo, raNo);
            LOG.info("Found {} MtrFabModule records matching search criteria", modules.size());
            
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            LOG.error("Error searching MtrFabModule records: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error searching records: " + e.getMessage());
        }
    }

    
    @PutMapping(value = UPDATE_MTR_FAB_MODULE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateMtrFabModule(@RequestParam Long id, @RequestBody MtrFabModuleDto mtrFabModuleDto) {
        LOG.info("Updating MtrFabModule with ID: {}", id);
        
        try {
            MtrFabModuleDto updatedModule = mtrFabModuleService.updateMtrFabModule(id, mtrFabModuleDto);
            LOG.info("Successfully updated MtrFabModule with ID: {}", id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "MtrFabModule updated successfully");
            response.put("data", updatedModule);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            LOG.error("MtrFabModule not found for update: {}", id, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            LOG.error("Error updating MtrFabModule with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error updating record: " + e.getMessage());
        }
    }
 
    @PutMapping(value = UPDATE_MTR_FAB_MODULE_STAGES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateMtrFabModuleStages(
            @RequestParam Long id,
            @RequestParam(required = false) String cuttingStage,
            @RequestParam(required = false) String fitUpStage,
            @RequestParam(required = false) String weldingStage,
            @RequestParam(required = false) String finishingStage) {
        LOG.info("Updating fabrication stages for MtrFabModule with ID: {}", id);
        
        try {
            MtrFabModuleDto updatedModule = mtrFabModuleService.updateMtrFabModuleStages(id, cuttingStage, fitUpStage, weldingStage, finishingStage);
            LOG.info("Successfully updated fabrication stages for MtrFabModule with ID: {}", id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Fabrication stages updated successfully");
            response.put("data", updatedModule);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            LOG.error("MtrFabModule not found for stage update: {}", id, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            LOG.error("Error updating fabrication stages for MtrFabModule with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error updating stages: " + e.getMessage());
        }
    }

    
    @DeleteMapping(value = DELETE_MTR_FAB_MODULE)
    public ResponseEntity<?> deleteMtrFabModule(@RequestParam Long id) {
        LOG.info("Deleting MtrFabModule with ID: {}", id);
        
        try {
            mtrFabModuleService.deleteMtrFabModule(id);
            LOG.info("Successfully deleted MtrFabModule with ID: {}", id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "MtrFabModule deleted successfully");
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            LOG.error("MtrFabModule not found for deletion: {}", id, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            LOG.error("Error deleting MtrFabModule with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error deleting record: " + e.getMessage());
        }
    }

     
    @PutMapping(value = SOFT_DELETE_MTR_FAB_MODULE)
    public ResponseEntity<?> softDeleteMtrFabModule(@RequestParam Long id) {
        LOG.info("Soft deleting MtrFabModule with ID: {}", id);
        
        try {
            mtrFabModuleService.softDeleteMtrFabModule(id);
            LOG.info("Successfully soft deleted MtrFabModule with ID: {}", id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "MtrFabModule soft deleted successfully");
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            LOG.error("MtrFabModule not found for soft deletion: {}", id, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            LOG.error("Error soft deleting MtrFabModule with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error soft deleting record: " + e.getMessage());
        }
    }

     
    @GetMapping(value = GET_DISTINCT_WORK_ORDERS_FROM_FAB_MODULE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDistinctWorkOrdersFromFabModule() {
        LOG.info("Fetching distinct work orders from MtrFabModule");
        
        try {
            List<String> workOrders = mtrFabModuleService.getDistinctWorkOrders();
            LOG.info("Found {} distinct work orders", workOrders.size());
            
            return ResponseEntity.ok(workOrders);
        } catch (Exception e) {
            LOG.error("Error fetching distinct work orders: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching work orders: " + e.getMessage());
        }
    }

     
    @GetMapping(value = GET_DISTINCT_BUILDING_NAMES_FROM_FAB_MODULE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDistinctBuildingNamesFromFabModule(@RequestParam String workOrder) {
        LOG.info("Fetching distinct building names for work order: {}", workOrder);
        
        try {
            List<String> buildingNames = mtrFabModuleService.getDistinctBuildingNamesByWorkOrder(workOrder);
            LOG.info("Found {} distinct building names for work order: {}", buildingNames.size(), workOrder);
            
            return ResponseEntity.ok(buildingNames);
        } catch (Exception e) {
            LOG.error("Error fetching distinct building names: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching building names: " + e.getMessage());
        }
    }

    
    @GetMapping(value = GET_DISTINCT_DRAWING_NOS_FROM_FAB_MODULE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDistinctDrawingNosFromFabModule(@RequestParam String workOrder, @RequestParam String buildingName) {
        LOG.info("Fetching distinct drawing numbers for work order: {} and building name: {}", workOrder, buildingName);
        
        try {
            List<String> drawingNos = mtrFabModuleService.getDistinctDrawingNosByWorkOrderAndBuildingName(workOrder, buildingName);
            LOG.info("Found {} distinct drawing numbers", drawingNos.size());
            
            return ResponseEntity.ok(drawingNos);
        } catch (Exception e) {
            LOG.error("Error fetching distinct drawing numbers: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching drawing numbers: " + e.getMessage());
        }
    }

     
    @GetMapping(value = GET_DISTINCT_MARK_NOS_FROM_FAB_MODULE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDistinctMarkNosFromFabModule(@RequestParam String workOrder, @RequestParam String buildingName, @RequestParam String drawingNo) {
        LOG.info("Fetching distinct mark numbers for work order: {}, building name: {}, drawing no: {}", workOrder, buildingName, drawingNo);
        
        try {
            List<String> markNos = mtrFabModuleService.getDistinctMarkNosByWorkOrderAndBuildingNameAndDrawingNo(workOrder, buildingName, drawingNo);
            LOG.info("Found {} distinct mark numbers", markNos.size());
            
            return ResponseEntity.ok(markNos);
        } catch (Exception e) {
            LOG.error("Error fetching distinct mark numbers: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching mark numbers: " + e.getMessage());
        }
    }
 
    @GetMapping(value = GET_DISTINCT_RA_NUMBERS_FROM_FAB_MODULE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDistinctRaNumbersFromFabModule() {
        LOG.info("Fetching distinct RA numbers from MtrFabModule");
        
        try {
            List<String> raNumbers = mtrFabModuleService.getDistinctRaNumbers();
            LOG.info("Found {} distinct RA numbers", raNumbers.size());
            
            return ResponseEntity.ok(raNumbers);
        } catch (Exception e) {
            LOG.error("Error fetching distinct RA numbers: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching RA numbers: " + e.getMessage());
        }
    }

     
    @GetMapping(value = GET_FAB_MODULE_STATISTICS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFabModuleStatistics(
            @RequestParam(required = false) String workOrder,
            @RequestParam(required = false) String raNo) {
        LOG.info("Fetching MtrFabModule statistics");
        
        try {
            Map<String, Object> statistics = new HashMap<>();
            
            // Total records count
            long totalRecords = mtrFabModuleService.getTotalRecordsCount();
            statistics.put("totalRecords", totalRecords);
            
            // Work order specific count
            if (workOrder != null && !workOrder.trim().isEmpty()) {
                long workOrderCount = mtrFabModuleService.getRecordsCountByWorkOrder(workOrder);
                statistics.put("workOrderRecords", workOrderCount);
                statistics.put("workOrder", workOrder);
            }
            
            // RA No specific count
            if (raNo != null && !raNo.trim().isEmpty()) {
                long raNoCount = mtrFabModuleService.getRecordsCountByRaNo(raNo);
                statistics.put("raNoRecords", raNoCount);
                statistics.put("raNo", raNo);
            }
            
            // Distinct counts
            statistics.put("distinctWorkOrders", mtrFabModuleService.getDistinctWorkOrders().size());
            statistics.put("distinctRaNumbers", mtrFabModuleService.getDistinctRaNumbers().size());
            
            LOG.info("Generated MtrFabModule statistics: {}", statistics);
            
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            LOG.error("Error generating MtrFabModule statistics: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error generating statistics: " + e.getMessage());
        }
    }
}
