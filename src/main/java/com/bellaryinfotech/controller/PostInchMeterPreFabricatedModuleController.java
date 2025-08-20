package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.PostInchMeterPreFabricatedModuleDTO;
import com.bellaryinfotech.model.PostInchMeterPreFabricatedModule;
import com.bellaryinfotech.service.PostInchMeterPreFabricatedModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class PostInchMeterPreFabricatedModuleController {
    
    @Autowired
    private PostInchMeterPreFabricatedModuleService service;
    
    @PostMapping("/savePostInchMeterPreFabricatedModule/details")
    public ResponseEntity<List<PostInchMeterPreFabricatedModule>> savePostInchMeterPreFabricatedModule(@RequestBody PostInchMeterPreFabricatedModuleDTO dto) {
        try {
            List<PostInchMeterPreFabricatedModule> savedModules = service.savePostInchMeterPreFabricatedModule(dto);
            return new ResponseEntity<>(savedModules, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getPostInchMeterPreFabricatedByWorkOrderAndServiceDesc/details")
    public ResponseEntity<List<PostInchMeterPreFabricatedModule>> getPostInchMeterPreFabricatedByWorkOrderAndServiceDesc(
            @RequestParam String workOrder, @RequestParam String serviceDescription) {
        try {
            List<PostInchMeterPreFabricatedModule> modules = service.getPostInchMeterPreFabricatedByWorkOrderAndServiceDesc(workOrder, serviceDescription);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getPostInchMeterPreFabricatedModulesByWorkOrder/details")
    public ResponseEntity<List<PostInchMeterPreFabricatedModule>> getPostInchMeterPreFabricatedModulesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<PostInchMeterPreFabricatedModule> modules = service.getPostInchMeterPreFabricatedModulesByWorkOrder(workOrder);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getPostInchMeterPreFabricatedModulesByWorkOrderAndRaNo/details")
    public ResponseEntity<List<PostInchMeterPreFabricatedModule>> getPostInchMeterPreFabricatedModulesByWorkOrderAndRaNo(
            @RequestParam String workOrder, @RequestParam String raNo) {
        try {
            List<PostInchMeterPreFabricatedModule> modules = service.getPostInchMeterPreFabricatedModulesByWorkOrderAndRaNo(workOrder, raNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getPostInchMeterPreFabricatedModulesByRaNo/details")
    public ResponseEntity<List<PostInchMeterPreFabricatedModule>> getPostInchMeterPreFabricatedModulesByRaNo(@RequestParam String raNo) {
        try {
            List<PostInchMeterPreFabricatedModule> modules = service.getPostInchMeterPreFabricatedModulesByRaNo(raNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/searchPostInchMeterPreFabricatedModulesByMarkNo/details")
    public ResponseEntity<List<PostInchMeterPreFabricatedModule>> searchPostInchMeterPreFabricatedModulesByMarkNo(@RequestParam String markNo) {
        try {
            List<PostInchMeterPreFabricatedModule> modules = service.getPostInchMeterPreFabricatedModulesByMarkNo(markNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getDistinctPostInchMeterPreFabricatedWorkOrders/details")
    public ResponseEntity<List<String>> getDistinctPostInchMeterPreFabricatedWorkOrders() {
        try {
            List<String> workOrders = service.getDistinctWorkOrders();
            return new ResponseEntity<>(workOrders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getDistinctRaNosByWorkOrder/details/inch")
    public ResponseEntity<List<String>> getDistinctRaNosByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> raNumbers = service.getDistinctRaNosByWorkOrder(workOrder);
            return new ResponseEntity<>(raNumbers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/updatePostInchMeterPreFabricatedModule/details")
    public ResponseEntity<PostInchMeterPreFabricatedModule> updatePostInchMeterPreFabricatedModule(
            @RequestParam Long id, @RequestBody PostInchMeterPreFabricatedModule updatedModule) {
        try {
            PostInchMeterPreFabricatedModule updated = service.updatePostInchMeterPreFabricatedModule(id, updatedModule);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deletePostInchMeterPreFabricatedModule/details")
    public ResponseEntity<String> deletePostInchMeterPreFabricatedModule(@RequestParam Long id) {
        try {
            service.deletePostInchMeterPreFabricatedModule(id);
            return new ResponseEntity<>("Module deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete module", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deletePostInchMeterPreFabricatedModulesByWorkOrder/details")
    public ResponseEntity<String> deletePostInchMeterPreFabricatedModulesByWorkOrder(@RequestParam String workOrder) {
        try {
            service.deletePostInchMeterPreFabricatedModulesByWorkOrder(workOrder);
            return new ResponseEntity<>("Modules deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete modules", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deletePostInchMeterPreFabricatedModulesByWorkOrderAndRaNo/details")
    public ResponseEntity<String> deletePostInchMeterPreFabricatedModulesByWorkOrderAndRaNo(
            @RequestParam String workOrder, @RequestParam String raNo) {
        try {
            service.deletePostInchMeterPreFabricatedModulesByWorkOrderAndRaNo(workOrder, raNo);
            return new ResponseEntity<>("Modules deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete modules", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // NEW: Search endpoint for workOrder, serviceDescription, and optional raNo
    @GetMapping("/searchPostInchMeterPreFabricatedModules/details")
    public ResponseEntity<List<PostInchMeterPreFabricatedModule>> searchPostInchMeterPreFabricatedModules(
            @RequestParam String workOrder, 
            @RequestParam String serviceDescription,
            @RequestParam(required = false) String raNo) {
        try {
            List<PostInchMeterPreFabricatedModule> modules = service.searchPostInchMeterPreFabricatedModules(workOrder, serviceDescription, raNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    
    
    
    
    @GetMapping("/getAllPostInchMeterPreFabData/details")
    public ResponseEntity<List<PostInchMeterPreFabricatedModule>> getAllPostInchMeterPreFabData() {
        try {
            List<PostInchMeterPreFabricatedModule> results = service.getAllRecords();
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctClientNamesFromPostInchMeterPreFab/details")
    public ResponseEntity<List<String>> getDistinctClientNamesFromPostInchMeterPreFab() {
        try {
            List<String> clientNames = service.getDistinctClientNames();
            return new ResponseEntity<>(clientNames, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctWorkOrdersFromPostInchMeterPreFab/details")
    public ResponseEntity<List<String>> getDistinctWorkOrdersFromPostInchMeterPreFab(@RequestParam String clientName) {
        try {
            List<String> workOrders = service.getDistinctWorkOrdersByClientName(clientName);
            return new ResponseEntity<>(workOrders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctServiceDescriptionsFromPostInchMeterPreFab/details")
    public ResponseEntity<List<String>> getDistinctServiceDescriptionsFromPostInchMeterPreFab(
            @RequestParam String clientName, @RequestParam String workOrder) {
        try {
            List<String> serviceDescriptions = service.getDistinctServiceDescriptionsByClientAndWorkOrder(clientName, workOrder);
            return new ResponseEntity<>(serviceDescriptions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctRaNumbersFromPostInchMeterPreFab/details")
    public ResponseEntity<List<String>> getDistinctRaNumbersFromPostInchMeterPreFab(
            @RequestParam String clientName, @RequestParam String workOrder, @RequestParam String serviceDescription) {
        try {
            List<String> raNumbers = service.getDistinctRaNumbersByAllCriteria(clientName, workOrder, serviceDescription);
            return new ResponseEntity<>(raNumbers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchPostInchMeterPreFabData/details")
    public ResponseEntity<List<PostInchMeterPreFabricatedModule>> searchPostInchMeterPreFabData(
            @RequestParam String clientName, 
            @RequestParam String workOrder,
            @RequestParam String serviceDescription,
            @RequestParam String raNumber) {
        try {
            List<PostInchMeterPreFabricatedModule> results = service.searchByAllCriteria(clientName, workOrder, serviceDescription, raNumber);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    
    
    
}