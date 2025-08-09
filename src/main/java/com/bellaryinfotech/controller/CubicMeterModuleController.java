package com.bellaryinfotech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bellaryinfotech.DTO.CubicMeterModuleDTO;
import com.bellaryinfotech.service.CubicMeterModuleService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class CubicMeterModuleController {

    @Autowired
    private CubicMeterModuleService cubicMeterModuleService;

     
    @PostMapping("/createCubicMeterModule/details")
    public ResponseEntity<?> createCubicMeterModule(@RequestBody CubicMeterModuleDTO cubicMeterModuleDTO) {
        try {
            CubicMeterModuleDTO createdModule = cubicMeterModuleService.createCubicMeterModule(cubicMeterModuleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdModule);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating cubic meter module: " + e.getMessage());
        }
    }

    @PostMapping("/createCubicMeterModules/details")
    public ResponseEntity<?> createCubicMeterModules(@RequestBody List<CubicMeterModuleDTO> cubicMeterModuleDTOs) {
        try {
            List<CubicMeterModuleDTO> createdModules = cubicMeterModuleService.createCubicMeterModules(cubicMeterModuleDTOs);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating cubic meter modules: " + e.getMessage());
        }
    }

     
    @GetMapping("/getAllCubicMeterModules/details")
    public ResponseEntity<?> getAllCubicMeterModules() {
        try {
            List<CubicMeterModuleDTO> cubicMeterModules = cubicMeterModuleService.getAllCubicMeterModules();
            return ResponseEntity.ok(cubicMeterModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching cubic meter modules: " + e.getMessage());
        }
    }

    @GetMapping("/getCubicMeterModuleById/details")
    public ResponseEntity<?> getCubicMeterModuleById(@RequestParam Long id) {
        try {
            Optional<CubicMeterModuleDTO> cubicMeterModule = cubicMeterModuleService.getCubicMeterModuleById(id);
            if (cubicMeterModule.isPresent()) {
                return ResponseEntity.ok(cubicMeterModule.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Cubic meter module not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching cubic meter module: " + e.getMessage());
        }
    }

    @GetMapping("/getCubicMeterModulesByWorkOrder/details")
    public ResponseEntity<?> getCubicMeterModulesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<CubicMeterModuleDTO> cubicMeterModules = cubicMeterModuleService.getCubicMeterModulesByWorkOrder(workOrder);
            return ResponseEntity.ok(cubicMeterModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching cubic meter modules by work order: " + e.getMessage());
        }
    }

    @GetMapping("/searchCubicMeterModulesByMarkNo/details")
    public ResponseEntity<?> searchCubicMeterModulesByMarkNo(@RequestParam String markNo) {
        try {
            List<CubicMeterModuleDTO> cubicMeterModules = cubicMeterModuleService.searchCubicMeterModulesByMarkNo(markNo);
            return ResponseEntity.ok(cubicMeterModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error searching cubic meter modules by mark number: " + e.getMessage());
        }
    }

    @GetMapping("/getCubicMeterModulesByClientName/details")
    public ResponseEntity<?> getCubicMeterModulesByClientName(@RequestParam String clientName) {
        try {
            List<CubicMeterModuleDTO> cubicMeterModules = cubicMeterModuleService.getCubicMeterModulesByClientName(clientName);
            return ResponseEntity.ok(cubicMeterModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching cubic meter modules by client name: " + e.getMessage());
        }
    }

    @GetMapping("/getCubicMeterModulesByBuildingName/details")
    public ResponseEntity<?> getCubicMeterModulesByBuildingName(@RequestParam String buildingName) {
        try {
            List<CubicMeterModuleDTO> cubicMeterModules = cubicMeterModuleService.getCubicMeterModulesByBuildingName(buildingName);
            return ResponseEntity.ok(cubicMeterModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching cubic meter modules by building name: " + e.getMessage());
        }
    }

    @GetMapping("/getCubicMeterModulesByWorkOrderAndBuildingName/details")
    public ResponseEntity<?> getCubicMeterModulesByWorkOrderAndBuildingName(
            @RequestParam String workOrder, @RequestParam String buildingName) {
        try {
            List<CubicMeterModuleDTO> cubicMeterModules = cubicMeterModuleService.getCubicMeterModulesByWorkOrderAndBuildingName(workOrder, buildingName);
            return ResponseEntity.ok(cubicMeterModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching cubic meter modules by work order and building name: " + e.getMessage());
        }
    }

    
    @PutMapping("/updateCubicMeterModule/details")
    public ResponseEntity<?> updateCubicMeterModule(@RequestParam Long id, @RequestBody CubicMeterModuleDTO cubicMeterModuleDTO) {
        try {
            CubicMeterModuleDTO updatedModule = cubicMeterModuleService.updateCubicMeterModule(id, cubicMeterModuleDTO);
            return ResponseEntity.ok(updatedModule);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating cubic meter module: " + e.getMessage());
        }
    }

    @PutMapping("/updateCubicMeterModules/details")
    public ResponseEntity<?> updateCubicMeterModules(@RequestBody List<CubicMeterModuleDTO> cubicMeterModuleDTOs) {
        try {
            List<CubicMeterModuleDTO> updatedModules = cubicMeterModuleService.updateCubicMeterModules(cubicMeterModuleDTOs);
            return ResponseEntity.ok(updatedModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating cubic meter modules: " + e.getMessage());
        }
    }
 
    @DeleteMapping("/deleteCubicMeterModule/details")
    public ResponseEntity<?> deleteCubicMeterModule(@RequestParam Long id) {
        try {
            boolean deleted = cubicMeterModuleService.deleteCubicMeterModule(id);
            if (deleted) {
                return ResponseEntity.ok("Cubic meter module deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Cubic meter module not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting cubic meter module: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteCubicMeterModulesByWorkOrder/details")
    public ResponseEntity<?> deleteCubicMeterModulesByWorkOrder(@RequestParam String workOrder) {
        try {
            boolean deleted = cubicMeterModuleService.deleteCubicMeterModulesByWorkOrder(workOrder);
            if (deleted) {
                return ResponseEntity.ok("Cubic meter modules deleted successfully for work order: " + workOrder);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No cubic meter modules found for work order: " + workOrder);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting cubic meter modules by work order: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteCubicMeterModulesByMarkNo/details")
    public ResponseEntity<?> deleteCubicMeterModulesByMarkNo(@RequestParam String markNo) {
        try {
            boolean deleted = cubicMeterModuleService.deleteCubicMeterModulesByMarkNo(markNo);
            if (deleted) {
                return ResponseEntity.ok("Cubic meter modules deleted successfully for mark number: " + markNo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No cubic meter modules found for mark number: " + markNo);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting cubic meter modules by mark number: " + e.getMessage());
        }
    }

    
    @GetMapping("/searchCubicMeterModules/details")
    public ResponseEntity<?> searchCubicMeterModules(
            @RequestParam(required = false) String workOrder,
            @RequestParam(required = false) String buildingName,
            @RequestParam(required = false) String markNo,
            @RequestParam(required = false) String clientName) {
        try {
            List<CubicMeterModuleDTO> cubicMeterModules = cubicMeterModuleService.searchCubicMeterModules(workOrder, buildingName, markNo, clientName);
            return ResponseEntity.ok(cubicMeterModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error searching cubic meter modules: " + e.getMessage());
        }
    }

     
    @GetMapping("/getDistinctWorkOrdersFromCubicMeterModule/details")
    public ResponseEntity<?> getDistinctWorkOrders() {
        try {
            List<String> workOrders = cubicMeterModuleService.getDistinctWorkOrders();
            return ResponseEntity.ok(workOrders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching distinct work orders: " + e.getMessage());
        }
    }

    @GetMapping("/getDistinctBuildingNamesByWorkOrderFromCubicMeterModule/details")
    public ResponseEntity<?> getDistinctBuildingNamesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> buildingNames = cubicMeterModuleService.getDistinctBuildingNamesByWorkOrder(workOrder);
            return ResponseEntity.ok(buildingNames);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching distinct building names by work order: " + e.getMessage());
        }
    }

    @GetMapping("/getDistinctMarkNosByWorkOrderFromCubicMeterModule/details")
    public ResponseEntity<?> getDistinctMarkNosByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> markNos = cubicMeterModuleService.getDistinctMarkNosByWorkOrder(workOrder);
            return ResponseEntity.ok(markNos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching distinct mark numbers by work order: " + e.getMessage());
        }
    }

    @GetMapping("/countCubicMeterModulesByWorkOrder/details")
    public ResponseEntity<?> countCubicMeterModulesByWorkOrder(@RequestParam String workOrder) {
        try {
            long count = cubicMeterModuleService.countCubicMeterModulesByWorkOrder(workOrder);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error counting cubic meter modules by work order: " + e.getMessage());
        }
    }

    @GetMapping("/getTotalVolumeByWorkOrderFromCubicMeterModule/details")
    public ResponseEntity<?> getTotalVolumeByWorkOrder(@RequestParam String workOrder) {
        try {
            Double totalVolume = cubicMeterModuleService.getTotalVolumeByWorkOrder(workOrder);
            return ResponseEntity.ok(totalVolume);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error getting total volume by work order: " + e.getMessage());
        }
    }

    @GetMapping("/getTotalVolumeByMarkNoFromCubicMeterModule/details")
    public ResponseEntity<?> getTotalVolumeByMarkNo(@RequestParam String markNo) {
        try {
            Double totalVolume = cubicMeterModuleService.getTotalVolumeByMarkNo(markNo);
            return ResponseEntity.ok(totalVolume);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error getting total volume by mark number: " + e.getMessage());
        }
    }

   
    @PostMapping("/bulkInsertCubicMeterModules/details")
    public ResponseEntity<?> bulkInsertCubicMeterModules(@RequestBody List<CubicMeterModuleDTO> cubicMeterModuleDTOs) {
        try {
            List<CubicMeterModuleDTO> insertedModules = cubicMeterModuleService.bulkInsertCubicMeterModules(cubicMeterModuleDTOs);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertedModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error bulk inserting cubic meter modules: " + e.getMessage());
        }
    }

    @DeleteMapping("/bulkDeleteCubicMeterModules/details")
    public ResponseEntity<?> bulkDeleteCubicMeterModules(@RequestBody List<Long> ids) {
        try {
            boolean deleted = cubicMeterModuleService.bulkDeleteCubicMeterModules(ids);
            if (deleted) {
                return ResponseEntity.ok("Cubic meter modules deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No cubic meter modules found for the provided IDs");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error bulk deleting cubic meter modules: " + e.getMessage());
        }
    }

     
    @GetMapping("/existsCubicMeterModuleById/details")
    public ResponseEntity<?> existsById(@RequestParam Long id) {
        try {
            boolean exists = cubicMeterModuleService.existsById(id);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking if cubic meter module exists by ID: " + e.getMessage());
        }
    }

    @GetMapping("/existsCubicMeterModuleByWorkOrder/details")
    public ResponseEntity<?> existsByWorkOrder(@RequestParam String workOrder) {
        try {
            boolean exists = cubicMeterModuleService.existsByWorkOrder(workOrder);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking if cubic meter module exists by work order: " + e.getMessage());
        }
    }

    @GetMapping("/existsCubicMeterModuleByMarkNo/details")
    public ResponseEntity<?> existsByMarkNo(@RequestParam String markNo) {
        try {
            boolean exists = cubicMeterModuleService.existsByMarkNo(markNo);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking if cubic meter module exists by mark number: " + e.getMessage());
        }
    }
}
