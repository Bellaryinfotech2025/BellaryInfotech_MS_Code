package com.bellaryinfotech.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bellaryinfotech.DTO.SquareMeterModuleDTO;
import com.bellaryinfotech.service.SquareMeterModuleService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class SquareMeterModuleController {

    @Autowired
    private SquareMeterModuleService squareMeterModuleService;

 
    @PostMapping("/createSquareMeterModule/details")
    public ResponseEntity<?> createSquareMeterModule(@RequestBody SquareMeterModuleDTO squareMeterModuleDTO) {
        try {
            SquareMeterModuleDTO createdModule = squareMeterModuleService.createSquareMeterModule(squareMeterModuleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdModule);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating square meter module: " + e.getMessage());
        }
    }

    @PostMapping("/createSquareMeterModules/details")
    public ResponseEntity<?> createSquareMeterModules(@RequestBody List<SquareMeterModuleDTO> squareMeterModuleDTOs) {
        try {
            List<SquareMeterModuleDTO> createdModules = squareMeterModuleService.createSquareMeterModules(squareMeterModuleDTOs);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating square meter modules: " + e.getMessage());
        }
    }

     
    @GetMapping("/getAllSquareMeterModules/details")
    public ResponseEntity<?> getAllSquareMeterModules() {
        try {
            List<SquareMeterModuleDTO> squareMeterModules = squareMeterModuleService.getAllSquareMeterModules();
            return ResponseEntity.ok(squareMeterModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching square meter modules: " + e.getMessage());
        }
    }

    @GetMapping("/getSquareMeterModuleById/details")
    public ResponseEntity<?> getSquareMeterModuleById(@RequestParam Long id) {
        try {
            Optional<SquareMeterModuleDTO> squareMeterModule = squareMeterModuleService.getSquareMeterModuleById(id);
            if (squareMeterModule.isPresent()) {
                return ResponseEntity.ok(squareMeterModule.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Square meter module not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching square meter module: " + e.getMessage());
        }
    }

    @GetMapping("/getSquareMeterModulesByWorkOrder/details")
    public ResponseEntity<?> getSquareMeterModulesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<SquareMeterModuleDTO> squareMeterModules = squareMeterModuleService.getSquareMeterModulesByWorkOrder(workOrder);
            return ResponseEntity.ok(squareMeterModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching square meter modules by work order: " + e.getMessage());
        }
    }

    @GetMapping("/searchSquareMeterModulesByMarkNo/details")
    public ResponseEntity<?> searchSquareMeterModulesByMarkNo(@RequestParam String markNo) {
        try {
            List<SquareMeterModuleDTO> squareMeterModules = squareMeterModuleService.searchSquareMeterModulesByMarkNo(markNo);
            return ResponseEntity.ok(squareMeterModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error searching square meter modules by mark number: " + e.getMessage());
        }
    }

    @GetMapping("/getSquareMeterModulesByClientName/details")
    public ResponseEntity<?> getSquareMeterModulesByClientName(@RequestParam String clientName) {
        try {
            List<SquareMeterModuleDTO> squareMeterModules = squareMeterModuleService.getSquareMeterModulesByClientName(clientName);
            return ResponseEntity.ok(squareMeterModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching square meter modules by client name: " + e.getMessage());
        }
    }

    @GetMapping("/getSquareMeterModulesByBuildingName/details")
    public ResponseEntity<?> getSquareMeterModulesByBuildingName(@RequestParam String buildingName) {
        try {
            List<SquareMeterModuleDTO> squareMeterModules = squareMeterModuleService.getSquareMeterModulesByBuildingName(buildingName);
            return ResponseEntity.ok(squareMeterModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching square meter modules by building name: " + e.getMessage());
        }
    }

    @GetMapping("/getSquareMeterModulesByWorkOrderAndBuildingName/details")
    public ResponseEntity<?> getSquareMeterModulesByWorkOrderAndBuildingName(
            @RequestParam String workOrder, @RequestParam String buildingName) {
        try {
            List<SquareMeterModuleDTO> squareMeterModules = squareMeterModuleService.getSquareMeterModulesByWorkOrderAndBuildingName(workOrder, buildingName);
            return ResponseEntity.ok(squareMeterModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching square meter modules by work order and building name: " + e.getMessage());
        }
    }

   
    @PutMapping("/updateSquareMeterModule/details")
    public ResponseEntity<?> updateSquareMeterModule(@RequestParam Long id, @RequestBody SquareMeterModuleDTO squareMeterModuleDTO) {
        try {
            SquareMeterModuleDTO updatedModule = squareMeterModuleService.updateSquareMeterModule(id, squareMeterModuleDTO);
            return ResponseEntity.ok(updatedModule);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating square meter module: " + e.getMessage());
        }
    }

    @PutMapping("/updateSquareMeterModules/details")
    public ResponseEntity<?> updateSquareMeterModules(@RequestBody List<SquareMeterModuleDTO> squareMeterModuleDTOs) {
        try {
            List<SquareMeterModuleDTO> updatedModules = squareMeterModuleService.updateSquareMeterModules(squareMeterModuleDTOs);
            return ResponseEntity.ok(updatedModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating square meter modules: " + e.getMessage());
        }
    }

    
    @DeleteMapping("/deleteSquareMeterModule/details")
    public ResponseEntity<?> deleteSquareMeterModule(@RequestParam Long id) {
        try {
            boolean deleted = squareMeterModuleService.deleteSquareMeterModule(id);
            if (deleted) {
                return ResponseEntity.ok("Square meter module deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Square meter module not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting square meter module: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteSquareMeterModulesByWorkOrder/details")
    public ResponseEntity<?> deleteSquareMeterModulesByWorkOrder(@RequestParam String workOrder) {
        try {
            boolean deleted = squareMeterModuleService.deleteSquareMeterModulesByWorkOrder(workOrder);
            if (deleted) {
                return ResponseEntity.ok("Square meter modules deleted successfully for work order: " + workOrder);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No square meter modules found for work order: " + workOrder);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting square meter modules by work order: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteSquareMeterModulesByMarkNo/details")
    public ResponseEntity<?> deleteSquareMeterModulesByMarkNo(@RequestParam String markNo) {
        try {
            boolean deleted = squareMeterModuleService.deleteSquareMeterModulesByMarkNo(markNo);
            if (deleted) {
                return ResponseEntity.ok("Square meter modules deleted successfully for mark number: " + markNo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No square meter modules found for mark number: " + markNo);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting square meter modules by mark number: " + e.getMessage());
        }
    }

   
    @GetMapping("/searchSquareMeterModules/details")
    public ResponseEntity<?> searchSquareMeterModules(
            @RequestParam(required = false) String workOrder,
            @RequestParam(required = false) String buildingName,
            @RequestParam(required = false) String markNo,
            @RequestParam(required = false) String clientName) {
        try {
            List<SquareMeterModuleDTO> squareMeterModules = squareMeterModuleService.searchSquareMeterModules(workOrder, buildingName, markNo, clientName);
            return ResponseEntity.ok(squareMeterModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error searching square meter modules: " + e.getMessage());
        }
    }

    
    @GetMapping("/getDistinctWorkOrdersFromSquareMeterModule/details")
    public ResponseEntity<?> getDistinctWorkOrders() {
        try {
            List<String> workOrders = squareMeterModuleService.getDistinctWorkOrders();
            return ResponseEntity.ok(workOrders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching distinct work orders: " + e.getMessage());
        }
    }

    @GetMapping("/getDistinctBuildingNamesByWorkOrderFromSquareMeterModule/details")
    public ResponseEntity<?> getDistinctBuildingNamesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> buildingNames = squareMeterModuleService.getDistinctBuildingNamesByWorkOrder(workOrder);
            return ResponseEntity.ok(buildingNames);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching distinct building names by work order: " + e.getMessage());
        }
    }

    @GetMapping("/getDistinctMarkNosByWorkOrderFromSquareMeterModule/details")
    public ResponseEntity<?> getDistinctMarkNosByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> markNos = squareMeterModuleService.getDistinctMarkNosByWorkOrder(workOrder);
            return ResponseEntity.ok(markNos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching distinct mark numbers by work order: " + e.getMessage());
        }
    }

    @GetMapping("/countSquareMeterModulesByWorkOrder/details")
    public ResponseEntity<?> countSquareMeterModulesByWorkOrder(@RequestParam String workOrder) {
        try {
            long count = squareMeterModuleService.countSquareMeterModulesByWorkOrder(workOrder);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error counting square meter modules by work order: " + e.getMessage());
        }
    }

    @GetMapping("/getTotalAreaByWorkOrderFromSquareMeterModule/details")
    public ResponseEntity<?> getTotalAreaByWorkOrder(@RequestParam String workOrder) {
        try {
            Double totalArea = squareMeterModuleService.getTotalAreaByWorkOrder(workOrder);
            return ResponseEntity.ok(totalArea);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error getting total area by work order: " + e.getMessage());
        }
    }

    @GetMapping("/getTotalAreaByMarkNoFromSquareMeterModule/details")
    public ResponseEntity<?> getTotalAreaByMarkNo(@RequestParam String markNo) {
        try {
            Double totalArea = squareMeterModuleService.getTotalAreaByMarkNo(markNo);
            return ResponseEntity.ok(totalArea);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error getting total area by mark number: " + e.getMessage());
        }
    }

   
    @PostMapping("/bulkInsertSquareMeterModules/details")
    public ResponseEntity<?> bulkInsertSquareMeterModules(@RequestBody List<SquareMeterModuleDTO> squareMeterModuleDTOs) {
        try {
            List<SquareMeterModuleDTO> insertedModules = squareMeterModuleService.bulkInsertSquareMeterModules(squareMeterModuleDTOs);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertedModules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error bulk inserting square meter modules: " + e.getMessage());
        }
    }

    @DeleteMapping("/bulkDeleteSquareMeterModules/details")
    public ResponseEntity<?> bulkDeleteSquareMeterModules(@RequestBody List<Long> ids) {
        try {
            boolean deleted = squareMeterModuleService.bulkDeleteSquareMeterModules(ids);
            if (deleted) {
                return ResponseEntity.ok("Square meter modules deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No square meter modules found for the provided IDs");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error bulk deleting square meter modules: " + e.getMessage());
        }
    }

    
    @GetMapping("/existsSquareMeterModuleById/details")
    public ResponseEntity<?> existsById(@RequestParam Long id) {
        try {
            boolean exists = squareMeterModuleService.existsById(id);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking if square meter module exists by ID: " + e.getMessage());
        }
    }

    @GetMapping("/existsSquareMeterModuleByWorkOrder/details")
    public ResponseEntity<?> existsByWorkOrder(@RequestParam String workOrder) {
        try {
            boolean exists = squareMeterModuleService.existsByWorkOrder(workOrder);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking if square meter module exists by work order: " + e.getMessage());
        }
    }

    @GetMapping("/existsSquareMeterModuleByMarkNo/details")
    public ResponseEntity<?> existsByMarkNo(@RequestParam String markNo) {
        try {
            boolean exists = squareMeterModuleService.existsByMarkNo(markNo);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking if square meter module exists by mark number: " + e.getMessage());
        }
    }
}
