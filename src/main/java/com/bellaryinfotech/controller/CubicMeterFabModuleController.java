package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.CubicMeterFabModuleDTO;
import com.bellaryinfotech.service.CubicMeterFabModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class CubicMeterFabModuleController {

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
}
