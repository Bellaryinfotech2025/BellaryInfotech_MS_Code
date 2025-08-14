package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.PostSquareMeterPreFabricatedModuleDTO;
import com.bellaryinfotech.model.PostSquareMeterPreFabricatedModule;
import com.bellaryinfotech.service.PostSquareMeterPreFabricatedModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class PostSquareMeterPreFabricatedModuleController {

    @Autowired
    private PostSquareMeterPreFabricatedModuleService service;

    @PostMapping("/savePostSquareMeterPreFabricatedModule/details")
    public ResponseEntity<List<PostSquareMeterPreFabricatedModule>> savePostSquareMeterPreFabricatedModule(@RequestBody PostSquareMeterPreFabricatedModuleDTO dto) {
        try {
            List<PostSquareMeterPreFabricatedModule> savedModules = service.savePostSquareMeterPreFabricatedModule(dto);
            return new ResponseEntity<>(savedModules, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPostSquareMeterPreFabricatedByWorkOrderAndServiceDesc/details")
    public ResponseEntity<List<PostSquareMeterPreFabricatedModule>> getPostSquareMeterPreFabricatedByWorkOrderAndServiceDesc(
            @RequestParam String workOrder, @RequestParam String serviceDescription) {
        try {
            List<PostSquareMeterPreFabricatedModule> modules = service.getPostSquareMeterPreFabricatedByWorkOrderAndServiceDesc(workOrder, serviceDescription);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPostSquareMeterPreFabricatedModulesByWorkOrder/details")
    public ResponseEntity<List<PostSquareMeterPreFabricatedModule>> getPostSquareMeterPreFabricatedModulesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<PostSquareMeterPreFabricatedModule> modules = service.getPostSquareMeterPreFabricatedModulesByWorkOrder(workOrder);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPostSquareMeterPreFabricatedModulesByWorkOrderAndRaNo/details")
    public ResponseEntity<List<PostSquareMeterPreFabricatedModule>> getPostSquareMeterPreFabricatedModulesByWorkOrderAndRaNo(
            @RequestParam String workOrder, @RequestParam String raNo) {
        try {
            List<PostSquareMeterPreFabricatedModule> modules = service.getPostSquareMeterPreFabricatedModulesByWorkOrderAndRaNo(workOrder, raNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPostSquareMeterPreFabricatedModulesByRaNo/details")
    public ResponseEntity<List<PostSquareMeterPreFabricatedModule>> getPostSquareMeterPreFabricatedModulesByRaNo(@RequestParam String raNo) {
        try {
            List<PostSquareMeterPreFabricatedModule> modules = service.getPostSquareMeterPreFabricatedModulesByRaNo(raNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchPostSquareMeterPreFabricatedModulesByMarkNo/details")
    public ResponseEntity<List<PostSquareMeterPreFabricatedModule>> searchPostSquareMeterPreFabricatedModulesByMarkNo(@RequestParam String markNo) {
        try {
            List<PostSquareMeterPreFabricatedModule> modules = service.getPostSquareMeterPreFabricatedModulesByMarkNo(markNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctPostSquareMeterPreFabricatedWorkOrders/details")
    public ResponseEntity<List<String>> getDistinctPostSquareMeterPreFabricatedWorkOrders() {
        try {
            List<String> workOrders = service.getDistinctWorkOrders();
            return new ResponseEntity<>(workOrders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctRaNosByWorkOrder/details/square")
    public ResponseEntity<List<String>> getDistinctRaNosByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> raNumbers = service.getDistinctRaNosByWorkOrder(workOrder);
            return new ResponseEntity<>(raNumbers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatePostSquareMeterPreFabricatedModule/details")
    public ResponseEntity<PostSquareMeterPreFabricatedModule> updatePostSquareMeterPreFabricatedModule(
            @RequestParam Long id, @RequestBody PostSquareMeterPreFabricatedModule updatedModule) {
        try {
            PostSquareMeterPreFabricatedModule updated = service.updatePostSquareMeterPreFabricatedModule(id, updatedModule);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deletePostSquareMeterPreFabricatedModule/details")
    public ResponseEntity<String> deletePostSquareMeterPreFabricatedModule(@RequestParam Long id) {
        try {
            service.deletePostSquareMeterPreFabricatedModule(id);
            return new ResponseEntity<>("Module deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete module", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deletePostSquareMeterPreFabricatedModulesByWorkOrder/details")
    public ResponseEntity<String> deletePostSquareMeterPreFabricatedModulesByWorkOrder(@RequestParam String workOrder) {
        try {
            service.deletePostSquareMeterPreFabricatedModulesByWorkOrder(workOrder);
            return new ResponseEntity<>("Modules deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete modules", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deletePostSquareMeterPreFabricatedModulesByWorkOrderAndRaNo/details")
    public ResponseEntity<String> deletePostSquareMeterPreFabricatedModulesByWorkOrderAndRaNo(
            @RequestParam String workOrder, @RequestParam String raNo) {
        try {
            service.deletePostSquareMeterPreFabricatedModulesByWorkOrderAndRaNo(workOrder, raNo);
            return new ResponseEntity<>("Modules deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete modules", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchPostSquareMeterPreFabricatedModules/details")
    public ResponseEntity<List<PostSquareMeterPreFabricatedModule>> searchPostSquareMeterPreFabricatedModules(
            @RequestParam String workOrder, 
            @RequestParam String serviceDescription,
            @RequestParam(required = false) String raNo) {
        try {
            List<PostSquareMeterPreFabricatedModule> modules = service.searchPostSquareMeterPreFabricatedModules(workOrder, serviceDescription, raNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // New API to fetch distinct vehicleNumber, loadNumber, plotNumber, and raNo for a workOrder
    @GetMapping("/getDistinctFiltersByWorkOrder/details")
    public ResponseEntity<Map<String, List<String>>> getDistinctFiltersByWorkOrder(@RequestParam String workOrder) {
        try {
            Map<String, List<String>> filters = new HashMap<>();
            filters.put("vehicleNumbers", service.getDistinctVehicleNumbersByWorkOrder(workOrder));
            filters.put("loadNumbers", service.getDistinctLoadNumbersByWorkOrder(workOrder));
            filters.put("plotNumbers", service.getDistinctPlotNumbersByWorkOrder(workOrder));
            filters.put("raNos", service.getDistinctRaNosByWorkOrder(workOrder));
            return new ResponseEntity<>(filters, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}