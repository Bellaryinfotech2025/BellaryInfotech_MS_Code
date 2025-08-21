package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.PostCubicMeterPreFabricatedModuleDTO;
import com.bellaryinfotech.model.PostCubicMeterPreFabricatedModule;
import com.bellaryinfotech.service.PostCubicMeterPreFabricatedModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class PostCubicMeterPreFabricatedModuleController {

    @Autowired
    private PostCubicMeterPreFabricatedModuleService service;

    @PostMapping("/savePostCubicMeterPreFabricatedModule/details")
    public ResponseEntity<List<PostCubicMeterPreFabricatedModule>> savePostCubicMeterPreFabricatedModule(@RequestBody PostCubicMeterPreFabricatedModuleDTO dto) {
        try {
            List<PostCubicMeterPreFabricatedModule> savedModules = service.savePostCubicMeterPreFabricatedModule(dto);
            return new ResponseEntity<>(savedModules, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPostCubicMeterPreFabricatedByWorkOrderAndServiceDesc/details")
    public ResponseEntity<List<PostCubicMeterPreFabricatedModule>> getPostCubicMeterPreFabricatedByWorkOrderAndServiceDesc(
            @RequestParam String workOrder, @RequestParam String serviceDescription) {
        try {
            List<PostCubicMeterPreFabricatedModule> modules = service.getPostCubicMeterPreFabricatedByWorkOrderAndServiceDesc(workOrder, serviceDescription);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPostCubicMeterPreFabricatedModulesByWorkOrder/details")
    public ResponseEntity<List<PostCubicMeterPreFabricatedModule>> getPostCubicMeterPreFabricatedModulesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<PostCubicMeterPreFabricatedModule> modules = service.getPostCubicMeterPreFabricatedModulesByWorkOrder(workOrder);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPostCubicMeterPreFabricatedModulesByWorkOrderAndRaNo/details")
    public ResponseEntity<List<PostCubicMeterPreFabricatedModule>> getPostCubicMeterPreFabricatedModulesByWorkOrderAndRaNo(
            @RequestParam String workOrder, @RequestParam String raNo) {
        try {
            List<PostCubicMeterPreFabricatedModule> modules = service.getPostCubicMeterPreFabricatedModulesByWorkOrderAndRaNo(workOrder, raNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPostCubicMeterPreFabricatedModulesByRaNo/details")
    public ResponseEntity<List<PostCubicMeterPreFabricatedModule>> getPostCubicMeterPreFabricatedModulesByRaNo(@RequestParam String raNo) {
        try {
            List<PostCubicMeterPreFabricatedModule> modules = service.getPostCubicMeterPreFabricatedModulesByRaNo(raNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchPostCubicMeterPreFabricatedModulesByMarkNo/details")
    public ResponseEntity<List<PostCubicMeterPreFabricatedModule>> searchPostCubicMeterPreFabricatedModulesByMarkNo(@RequestParam String markNo) {
        try {
            List<PostCubicMeterPreFabricatedModule> modules = service.getPostCubicMeterPreFabricatedModulesByMarkNo(markNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctPostCubicMeterPreFabricatedWorkOrders/details")
    public ResponseEntity<List<String>> getDistinctPostCubicMeterPreFabricatedWorkOrders() {
        try {
            List<String> workOrders = service.getDistinctWorkOrders();
            return new ResponseEntity<>(workOrders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctRaNosByWorkOrder/details/cubic")
    public ResponseEntity<List<String>> getDistinctRaNosByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> raNumbers = service.getDistinctRaNosByWorkOrder(workOrder);
            return new ResponseEntity<>(raNumbers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctVehicleNumbersByWorkOrder/details/cubic")
    public ResponseEntity<List<String>> getDistinctVehicleNumbersByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> vehicleNumbers = service.getDistinctVehicleNumbersByWorkOrder(workOrder);
            return new ResponseEntity<>(vehicleNumbers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctLoadNumbersByWorkOrder/details/cubic")
    public ResponseEntity<List<String>> getDistinctLoadNumbersByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> loadNumbers = service.getDistinctLoadNumbersByWorkOrder(workOrder);
            return new ResponseEntity<>(loadNumbers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctPlotNumbersByWorkOrder/details/cubic")
    public ResponseEntity<List<String>> getDistinctPlotNumbersByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> plotNumbers = service.getDistinctPlotNumbersByWorkOrder(workOrder);
            return new ResponseEntity<>(plotNumbers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatePostCubicMeterPreFabricatedModule/details")
    public ResponseEntity<PostCubicMeterPreFabricatedModule> updatePostCubicMeterPreFabricatedModule(
            @RequestParam Long id, @RequestBody PostCubicMeterPreFabricatedModule updatedModule) {
        try {
            PostCubicMeterPreFabricatedModule updated = service.updatePostCubicMeterPreFabricatedModule(id, updatedModule);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deletePostCubicMeterPreFabricatedModule/details")
    public ResponseEntity<String> deletePostCubicMeterPreFabricatedModule(@RequestParam Long id) {
        try {
            service.deletePostCubicMeterPreFabricatedModule(id);
            return new ResponseEntity<>("Module deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete module", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deletePostCubicMeterPreFabricatedModulesByWorkOrder/details")
    public ResponseEntity<String> deletePostCubicMeterPreFabricatedModulesByWorkOrder(@RequestParam String workOrder) {
        try {
            service.deletePostCubicMeterPreFabricatedModulesByWorkOrder(workOrder);
            return new ResponseEntity<>("Modules deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete modules", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deletePostCubicMeterPreFabricatedModulesByWorkOrderAndRaNo/details")
    public ResponseEntity<String> deletePostCubicMeterPreFabricatedModulesByWorkOrderAndRaNo(
            @RequestParam String workOrder, @RequestParam String raNo) {
        try {
            service.deletePostCubicMeterPreFabricatedModulesByWorkOrderAndRaNo(workOrder, raNo);
            return new ResponseEntity<>("Modules deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete modules", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchPostCubicMeterPreFabricatedModules/details")
    public ResponseEntity<List<PostCubicMeterPreFabricatedModule>> searchPostCubicMeterPreFabricatedModules(
            @RequestParam String workOrder, 
            @RequestParam String serviceDescription,
            @RequestParam(required = false) String raNo) {
        try {
            List<PostCubicMeterPreFabricatedModule> modules = service.searchPostCubicMeterPreFabricatedModules(workOrder, serviceDescription, raNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
 // Get all Post Cubic Meter Pre Fabricated data
    @GetMapping("/getAllPostCubicMeterPreFabData/details")
    public ResponseEntity<List<PostCubicMeterPreFabricatedModule>> getAllPostCubicMeterPreFabData() {
        try {
            List<PostCubicMeterPreFabricatedModule> modules = service.getAllPostCubicMeterPreFabricatedModules();
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     
    @GetMapping("/getDistinctClientNamesFromPostCubicMeterPreFab/details")
    public ResponseEntity<List<String>> getDistinctClientNamesFromPostCubicMeterPreFab() {
        try {
            List<String> clientNames = service.getDistinctClientNames();
            return new ResponseEntity<>(clientNames, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping("/getDistinctWorkOrdersFromPostCubicMeterPreFab/details")
    public ResponseEntity<List<String>> getDistinctWorkOrdersFromPostCubicMeterPreFab(@RequestParam String clientName) {
        try {
            List<String> workOrders = service.getDistinctWorkOrdersByClientName(clientName);
            return new ResponseEntity<>(workOrders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     
    @GetMapping("/getDistinctServiceDescriptionsFromPostCubicMeterPreFab/details")
    public ResponseEntity<List<String>> getDistinctServiceDescriptionsFromPostCubicMeterPreFab(
            @RequestParam String clientName, @RequestParam String workOrder) {
        try {
            List<String> serviceDescriptions = service.getDistinctServiceDescriptionsByClientAndWorkOrder(clientName, workOrder);
            return new ResponseEntity<>(serviceDescriptions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     
    @GetMapping("/getDistinctRaNumbersFromPostCubicMeterPreFab/details")
    public ResponseEntity<List<String>> getDistinctRaNumbersFromPostCubicMeterPreFab(
            @RequestParam String clientName, @RequestParam String workOrder, @RequestParam String serviceDescription) {
        try {
            List<String> raNumbers = service.getDistinctRaNumbersByClientWorkOrderAndService(clientName, workOrder, serviceDescription);
            return new ResponseEntity<>(raNumbers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     
    @GetMapping("/searchPostCubicMeterPreFabData/details")
    public ResponseEntity<List<PostCubicMeterPreFabricatedModule>> searchPostCubicMeterPreFabData(
            @RequestParam String clientName, @RequestParam String workOrder, 
            @RequestParam String serviceDescription, @RequestParam String raNumber) {
        try {
            List<PostCubicMeterPreFabricatedModule> modules = service.searchByAllCriteria(clientName, workOrder, serviceDescription, raNumber);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}