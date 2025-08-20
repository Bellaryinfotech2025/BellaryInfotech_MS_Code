package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.PostMeterPreFabricatedModuleDTO;
import com.bellaryinfotech.model.PostMeterPreFabricatedModule;
import com.bellaryinfotech.service.PostMeterPreFabricatedModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class PostMeterPreFabricatedModuleController {
    
    @Autowired
    private PostMeterPreFabricatedModuleService service;
    
    @PostMapping("/savePostMeterPreFabricatedModule/details")
    public ResponseEntity<List<PostMeterPreFabricatedModule>> savePostMeterPreFabricatedModule(@RequestBody PostMeterPreFabricatedModuleDTO dto) {
        try {
            List<PostMeterPreFabricatedModule> savedModules = service.savePostMeterPreFabricatedModule(dto);
            return new ResponseEntity<>(savedModules, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getPostMeterPreFabricatedModulesByWorkOrder/details")
    public ResponseEntity<List<PostMeterPreFabricatedModule>> getPostMeterPreFabricatedModulesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<PostMeterPreFabricatedModule> modules = service.getPostMeterPreFabricatedModulesByWorkOrder(workOrder);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getPostMeterPreFabricatedModulesByWorkOrderAndRaNo/details")
    public ResponseEntity<List<PostMeterPreFabricatedModule>> getPostMeterPreFabricatedModulesByWorkOrderAndRaNo(
            @RequestParam String workOrder, @RequestParam String raNo) {
        try {
            List<PostMeterPreFabricatedModule> modules = service.getPostMeterPreFabricatedModulesByWorkOrderAndRaNo(workOrder, raNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getPostMeterPreFabricatedModulesByRaNo/details")
    public ResponseEntity<List<PostMeterPreFabricatedModule>> getPostMeterPreFabricatedModulesByRaNo(@RequestParam String raNo) {
        try {
            List<PostMeterPreFabricatedModule> modules = service.getPostMeterPreFabricatedModulesByRaNo(raNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/searchPostMeterPreFabricatedModulesByMarkNo/details")
    public ResponseEntity<List<PostMeterPreFabricatedModule>> searchPostMeterPreFabricatedModulesByMarkNo(@RequestParam String markNo) {
        try {
            List<PostMeterPreFabricatedModule> modules = service.getPostMeterPreFabricatedModulesByMarkNo(markNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getDistinctPostMeterPreFabricatedWorkOrders/details")
    public ResponseEntity<List<String>> getDistinctPostMeterPreFabricatedWorkOrders() {
        try {
            List<String> workOrders = service.getDistinctWorkOrders();
            return new ResponseEntity<>(workOrders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getDistinctRaNosByWorkOrder/details")
    public ResponseEntity<List<String>> getDistinctRaNosByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> raNumbers = service.getDistinctRaNosByWorkOrder(workOrder);
            return new ResponseEntity<>(raNumbers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getRaNoByWorkOrderAndServiceDescription/details")
    public ResponseEntity<String> getRaNoByWorkOrderAndServiceDescription(
            @RequestParam String workOrder, @RequestParam String serviceDescription) {
        try {
            List<PostMeterPreFabricatedModule> modules = service.getPostMeterPreFabricatedModulesByWorkOrder(workOrder);
            Optional<String> raNo = modules.stream()
                    .filter(module -> module.getServiceDescription().equals(serviceDescription) && module.getRaNo() != null)
                    .map(PostMeterPreFabricatedModule::getRaNo)
                    .findFirst();
            return raNo.map(no -> new ResponseEntity<>(no, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>("", HttpStatus.OK));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/updatePostMeterPreFabricatedModule/details")
    public ResponseEntity<PostMeterPreFabricatedModule> updatePostMeterPreFabricatedModule(
            @RequestParam Long id, @RequestBody PostMeterPreFabricatedModule updatedModule) {
        try {
            PostMeterPreFabricatedModule updated = service.updatePostMeterPreFabricatedModule(id, updatedModule);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deletePostMeterPreFabricatedModule/details")
    public ResponseEntity<String> deletePostMeterPreFabricatedModule(@RequestParam Long id) {
        try {
            service.deletePostMeterPreFabricatedModule(id);
            return new ResponseEntity<>("Module deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete module", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deletePostMeterPreFabricatedModulesByWorkOrder/details")
    public ResponseEntity<String> deletePostMeterPreFabricatedModulesByWorkOrder(@RequestParam String workOrder) {
        try {
            service.deletePostMeterPreFabricatedModulesByWorkOrder(workOrder);
            return new ResponseEntity<>("Modules deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete modules", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deletePostMeterPreFabricatedModulesByWorkOrderAndRaNo/details")
    public ResponseEntity<String> deletePostMeterPreFabricatedModulesByWorkOrderAndRaNo(
            @RequestParam String workOrder, @RequestParam String raNo) {
        try {
            service.deletePostMeterPreFabricatedModulesByWorkOrderAndRaNo(workOrder, raNo);
            return new ResponseEntity<>("Modules deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete modules", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    
    
    


    
    
    
    
    
    
    
    
    
    @GetMapping("/getAllPostMeterPreFabData/details")
    public ResponseEntity<List<PostMeterPreFabricatedModule>> getAllPostMeterPreFabData() {
        try {
            List<PostMeterPreFabricatedModule> results = service.getAllRecords();
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctClientNamesFromPostMeterPreFab/details")
    public ResponseEntity<List<String>> getDistinctClientNamesFromPostMeterPreFab() {
        try {
            List<String> clientNames = service.getDistinctClientNames();
            return new ResponseEntity<>(clientNames, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctWorkOrdersFromPostMeterPreFab/details")
    public ResponseEntity<List<String>> getDistinctWorkOrdersFromPostMeterPreFab(@RequestParam String clientName) {
        try {
            List<String> workOrders = service.getDistinctWorkOrdersByClientName(clientName);
            return new ResponseEntity<>(workOrders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctServiceDescriptionsFromPostMeterPreFab/details")
    public ResponseEntity<List<String>> getDistinctServiceDescriptionsFromPostMeterPreFab(
            @RequestParam String clientName, @RequestParam String workOrder) {
        try {
            List<String> serviceDescriptions = service.getDistinctServiceDescriptionsByClientAndWorkOrder(clientName, workOrder);
            return new ResponseEntity<>(serviceDescriptions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctRaNumbersFromPostMeterPreFab/details")
    public ResponseEntity<List<String>> getDistinctRaNumbersFromPostMeterPreFab(
            @RequestParam String clientName, @RequestParam String workOrder, @RequestParam String serviceDescription) {
        try {
            List<String> raNumbers = service.getDistinctRaNumbersByAllCriteria(clientName, workOrder, serviceDescription);
            return new ResponseEntity<>(raNumbers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchPostMeterPreFabData/details")
    public ResponseEntity<List<PostMeterPreFabricatedModule>> searchPostMeterPreFabData(
            @RequestParam String clientName, 
            @RequestParam String workOrder,
            @RequestParam String serviceDescription,
            @RequestParam String raNumber) {
        try {
            List<PostMeterPreFabricatedModule> results = service.searchByAllCriteria(clientName, workOrder, serviceDescription, raNumber);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
}