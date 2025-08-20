package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.CubicMeterFabModuleDTO;
import com.bellaryinfotech.service.CubicMeterFabModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class CubicMeterFabModuleController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CubicMeterFabModuleController.class);


    @Autowired
    private CubicMeterFabModuleService cubicMeterFabModuleService;
 
    @PostMapping("/createCubicMeterFabModule/details")
    public ResponseEntity<Map<String, Object>> createCubicMeterFabModule(@RequestBody CubicMeterFabModuleDTO cubicMeterFabModuleDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            CubicMeterFabModuleDTO createdModule = cubicMeterFabModuleService.createCubicMeterFabModule(cubicMeterFabModuleDTO);
            response.put("success", true);
            response.put("message", "CubicMeterFabModule created successfully");
            response.put("data", createdModule);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error creating CubicMeterFabModule: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    
    @PostMapping("/createCubicMeterFabModuleEntries/details")
    public ResponseEntity<Map<String, Object>> createCubicMeterFabModuleEntries(@RequestBody List<CubicMeterFabModuleDTO> cubicMeterFabModuleDTOs) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<CubicMeterFabModuleDTO> createdModules = cubicMeterFabModuleService.createCubicMeterFabModuleEntries(cubicMeterFabModuleDTOs);
            response.put("success", true);
            response.put("message", "Successfully created " + createdModules.size() + " CubicMeterFabModule entries");
            response.put("data", createdModules);
            response.put("count", createdModules.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error creating CubicMeterFabModule entries: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
 
    @GetMapping("/getAllCubicMeterFabModules/details")
    public ResponseEntity<List<CubicMeterFabModuleDTO>> getAllCubicMeterFabModules() {
        try {
            List<CubicMeterFabModuleDTO> modules = cubicMeterFabModuleService.getAllCubicMeterFabModules();
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    
    @GetMapping("/getCubicMeterFabModuleById/details/{id}")
    public ResponseEntity<CubicMeterFabModuleDTO> getCubicMeterFabModuleById(@PathVariable Long id) {
        try {
            Optional<CubicMeterFabModuleDTO> module = cubicMeterFabModuleService.getCubicMeterFabModuleById(id);
            return module.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

     
    @GetMapping("/getDistinctWorkOrdersFromCubicMeterFabModule/details")
    public ResponseEntity<List<String>> getDistinctWorkOrders() {
        try {
            List<String> workOrders = cubicMeterFabModuleService.getDistinctWorkOrders();
            return ResponseEntity.ok(workOrders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    
    @GetMapping("/getDistinctBuildingNamesByWorkOrderFromCubicMeterFabModule/details")
    public ResponseEntity<List<String>> getDistinctBuildingNamesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> buildingNames = cubicMeterFabModuleService.getDistinctBuildingNamesByWorkOrder(workOrder);
            return ResponseEntity.ok(buildingNames);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

   
    @GetMapping("/getCubicMeterFabModulesByWorkOrderAndBuildingName/details")
    public ResponseEntity<List<CubicMeterFabModuleDTO>> getCubicMeterFabModulesByWorkOrderAndBuildingName(
            @RequestParam String workOrder, @RequestParam String buildingName) {
        try {
            List<CubicMeterFabModuleDTO> modules = cubicMeterFabModuleService
                    .getCubicMeterFabModulesByWorkOrderAndBuildingName(workOrder, buildingName);
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    
    @GetMapping("/getCubicMeterFabModulesByWorkOrderAndBuildingNameAndDrawingNoAndMarkNo/details")
    public ResponseEntity<List<CubicMeterFabModuleDTO>> getCubicMeterFabModulesByWorkOrderAndBuildingNameAndDrawingNoAndMarkNo(
            @RequestParam String workOrder, @RequestParam String buildingName,
            @RequestParam String drawingNo, @RequestParam String markNo) {
        try {
            List<CubicMeterFabModuleDTO> modules = cubicMeterFabModuleService
                    .getCubicMeterFabModulesByWorkOrderAndBuildingNameAndDrawingNoAndMarkNo(
                            workOrder, buildingName, drawingNo, markNo);
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

   
    @GetMapping("/getCubicMeterFabModulesByRaNo/details")
    public ResponseEntity<List<CubicMeterFabModuleDTO>> getCubicMeterFabModulesByRaNo(@RequestParam String raNo) {
        try {
            List<CubicMeterFabModuleDTO> modules = cubicMeterFabModuleService.getCubicMeterFabModulesByRaNo(raNo);
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

   
    @GetMapping("/getDistinctRaNosFromCubicMeterFabModule/details")
    public ResponseEntity<List<String>> getDistinctRaNos() {
        try {
            List<String> raNos = cubicMeterFabModuleService.getDistinctRaNos();
            return ResponseEntity.ok(raNos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

  
    @GetMapping("/getCubicMeterFabModulesByWorkOrderOnly/details")
    public ResponseEntity<List<CubicMeterFabModuleDTO>> getCubicMeterFabModulesByWorkOrderOnly(@RequestParam String workOrder) {
        try {
            List<CubicMeterFabModuleDTO> modules = cubicMeterFabModuleService.getCubicMeterFabModulesByWorkOrder(workOrder);
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

     
    @PutMapping("/updateCubicMeterFabModule/details/{id}")
    public ResponseEntity<Map<String, Object>> updateCubicMeterFabModule(
            @PathVariable Long id, @RequestBody CubicMeterFabModuleDTO cubicMeterFabModuleDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            CubicMeterFabModuleDTO updatedModule = cubicMeterFabModuleService.updateCubicMeterFabModule(id, cubicMeterFabModuleDTO);
            response.put("success", true);
            response.put("message", "CubicMeterFabModule updated successfully");
            response.put("data", updatedModule);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error updating CubicMeterFabModule: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    
    @PutMapping("/updateCubicMeterFabModuleStatus/details/{id}")
    public ResponseEntity<Map<String, Object>> updateCubicMeterFabModuleStatus(
            @PathVariable Long id, @RequestParam String status) {
        Map<String, Object> response = new HashMap<>();
        try {
            CubicMeterFabModuleDTO updatedModule = cubicMeterFabModuleService.updateCubicMeterFabModuleStatus(id, status);
            response.put("success", true);
            response.put("message", "CubicMeterFabModule status updated successfully");
            response.put("data", updatedModule);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error updating CubicMeterFabModule status: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    
    @DeleteMapping("/deleteCubicMeterFabModule/details/{id}")
    public ResponseEntity<Map<String, Object>> deleteCubicMeterFabModule(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean deleted = cubicMeterFabModuleService.deleteCubicMeterFabModule(id);
            if (deleted) {
                response.put("success", true);
                response.put("message", "CubicMeterFabModule deleted successfully");
            } else {
                response.put("success", false);
                response.put("message", "Failed to delete CubicMeterFabModule");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error deleting CubicMeterFabModule: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    
    @PutMapping("/softDeleteCubicMeterFabModule/details/{id}")
    public ResponseEntity<Map<String, Object>> softDeleteCubicMeterFabModule(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean deleted = cubicMeterFabModuleService.softDeleteCubicMeterFabModule(id);
            if (deleted) {
                response.put("success", true);
                response.put("message", "CubicMeterFabModule soft deleted successfully");
            } else {
                response.put("success", false);
                response.put("message", "Failed to soft delete CubicMeterFabModule");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error soft deleting CubicMeterFabModule: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

   
    @GetMapping("/searchCubicMeterFabModules/details")
    public ResponseEntity<List<CubicMeterFabModuleDTO>> searchCubicMeterFabModules(
            @RequestParam(required = false) String workOrder,
            @RequestParam(required = false) String buildingName,
            @RequestParam(required = false) String drawingNo,
            @RequestParam(required = false) String markNo,
            @RequestParam(required = false) String raNo) {
        try {
            List<CubicMeterFabModuleDTO> modules = cubicMeterFabModuleService
                    .searchCubicMeterFabModules(workOrder, buildingName, drawingNo, markNo, raNo);
            return ResponseEntity.ok(modules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    
    @GetMapping("/getActiveCubicMeterFabModulesCount/details")
    public ResponseEntity<Map<String, Object>> getActiveCubicMeterFabModulesCount() {
        Map<String, Object> response = new HashMap<>();
        try {
            Long count = cubicMeterFabModuleService.getActiveCubicMeterFabModulesCount();
            response.put("success", true);
            response.put("count", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error getting active CubicMeterFabModules count: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
//new apis gto get the cubic in productionn
  
 @GetMapping(value = "/getDistinctClientNamesFromCubicMeterFab/details", produces = MediaType.APPLICATION_JSON_VALUE)
 public ResponseEntity<?> getDistinctClientNamesFromCubicMeterFab() {
     LOG.info("Fetching distinct client names from CubicMeterFabModule");
     
     try {
         List<String> clientNames = cubicMeterFabModuleService.getDistinctClientNames();
         LOG.info("Found {} distinct client names", clientNames.size());
         
         return ResponseEntity.ok(clientNames);
     } catch (Exception e) {
         LOG.error("Error fetching distinct client names: {}", e.getMessage(), e);
         return ResponseEntity.badRequest().body("Error fetching client names: " + e.getMessage());
     }
 }

 
 @GetMapping(value = "/getDistinctWorkOrdersFromCubicMeterFab/details", produces = MediaType.APPLICATION_JSON_VALUE)
 public ResponseEntity<?> getDistinctWorkOrdersFromCubicMeterFab(@RequestParam String clientName) {
     LOG.info("Fetching distinct work orders for client: {}", clientName);
     
     try {
         List<String> workOrders = cubicMeterFabModuleService.getDistinctWorkOrdersByClientName(clientName);
         LOG.info("Found {} distinct work orders for client: {}", workOrders.size(), clientName);
         
         return ResponseEntity.ok(workOrders);
     } catch (Exception e) {
         LOG.error("Error fetching distinct work orders: {}", e.getMessage(), e);
         return ResponseEntity.badRequest().body("Error fetching work orders: " + e.getMessage());
     }
 }

  
 @GetMapping(value = "/getDistinctServiceDescriptionsFromCubicMeterFab/details", produces = MediaType.APPLICATION_JSON_VALUE)
 public ResponseEntity<?> getDistinctServiceDescriptionsFromCubicMeterFab(
         @RequestParam String clientName, 
         @RequestParam String workOrder) {
     LOG.info("Fetching distinct service descriptions for client: {} and work order: {}", clientName, workOrder);
     
     try {
         List<String> serviceDescriptions = cubicMeterFabModuleService.getDistinctServiceDescriptionsByClientAndWorkOrder(clientName, workOrder);
         LOG.info("Found {} distinct service descriptions", serviceDescriptions.size());
         
         return ResponseEntity.ok(serviceDescriptions);
     } catch (Exception e) {
         LOG.error("Error fetching distinct service descriptions: {}", e.getMessage(), e);
         return ResponseEntity.badRequest().body("Error fetching service descriptions: " + e.getMessage());
     }
 }

  
 @GetMapping(value = "/getDistinctRaNumbersFromCubicMeterFab/details", produces = MediaType.APPLICATION_JSON_VALUE)
 public ResponseEntity<?> getDistinctRaNumbersFromCubicMeterFab(
         @RequestParam String clientName,
         @RequestParam String workOrder,
         @RequestParam String serviceDescription) {
     LOG.info("Fetching distinct RA numbers for client: {}, work order: {}, service: {}", clientName, workOrder, serviceDescription);
     
     try {
         List<String> raNumbers = cubicMeterFabModuleService.getDistinctRaNumbersByClientWorkOrderAndService(clientName, workOrder, serviceDescription);
         LOG.info("Found {} distinct RA numbers", raNumbers.size());
         
         return ResponseEntity.ok(raNumbers);
     } catch (Exception e) {
         LOG.error("Error fetching distinct RA numbers: {}", e.getMessage(), e);
         return ResponseEntity.badRequest().body("Error fetching RA numbers: " + e.getMessage());
     }
 }

  
 @GetMapping(value = "/searchCubicMeterFabData/details", produces = MediaType.APPLICATION_JSON_VALUE)
 public ResponseEntity<?> searchCubicMeterFabData(
         @RequestParam String clientName,
         @RequestParam String workOrder,
         @RequestParam String serviceDescription,
         @RequestParam String raNumber) {
     LOG.info("Searching CubicMeterFab data for client: {}, work order: {}, service: {}, RA: {}", 
              clientName, workOrder, serviceDescription, raNumber);
     
     try {
         List<CubicMeterFabModuleDTO> results = cubicMeterFabModuleService.searchByClientWorkOrderServiceAndRa(
             clientName, workOrder, serviceDescription, raNumber);
         LOG.info("Found {} CubicMeterFab records", results.size());
         
         return ResponseEntity.ok(results);
     } catch (Exception e) {
         LOG.error("Error searching CubicMeterFab data: {}", e.getMessage(), e);
         return ResponseEntity.badRequest().body("Error searching data: " + e.getMessage());
     }
 }

}
