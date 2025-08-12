package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.CubicMeterPreFabricatedDrawingEntryDTO;
import com.bellaryinfotech.model.CubicMeterPreFabricatedDrawingEntry;
import com.bellaryinfotech.service.CubicMeterPreFabricatedDrawingEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class CubicMeterPreFabricatedDrawingEntryController {
    
    @Autowired
    private CubicMeterPreFabricatedDrawingEntryService service;
    
    @PostMapping("/saveCubicMeterPreFabricatedEntries/details")
    public ResponseEntity<List<CubicMeterPreFabricatedDrawingEntry>> saveCubicMeterPreFabricatedEntries(@RequestBody CubicMeterPreFabricatedDrawingEntryDTO dto) {
        try {
            List<CubicMeterPreFabricatedDrawingEntry> savedEntries = service.saveCubicMeterPreFabricatedEntries(dto);
            return new ResponseEntity<>(savedEntries, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/searchCubicMeterPreFabricatedModulesByMarkNo/details")
    public ResponseEntity<List<CubicMeterPreFabricatedDrawingEntry>> searchCubicMeterPreFabricatedModulesByMarkNo(@RequestParam String markNo) {
        try {
            List<CubicMeterPreFabricatedDrawingEntry> entries = service.getCubicMeterPreFabricatedEntriesByMarkNo(markNo);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getCubicMeterPreFabricatedEntriesByWorkOrder/details")
    public ResponseEntity<List<CubicMeterPreFabricatedDrawingEntry>> getCubicMeterPreFabricatedEntriesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<CubicMeterPreFabricatedDrawingEntry> entries = service.getCubicMeterPreFabricatedEntriesByWorkOrder(workOrder);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getCubicMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber/details")
    public ResponseEntity<List<CubicMeterPreFabricatedDrawingEntry>> getCubicMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber(
            @RequestParam String workOrder, @RequestParam String vehicleNumber) {
        try {
            List<CubicMeterPreFabricatedDrawingEntry> entries = service.getCubicMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber(workOrder, vehicleNumber);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getCubicMeterPreFabricatedEntriesByWorkOrderAndLoadNumber/details")
    public ResponseEntity<List<CubicMeterPreFabricatedDrawingEntry>> getCubicMeterPreFabricatedEntriesByWorkOrderAndLoadNumber(
            @RequestParam String workOrder, @RequestParam String loadNumber) {
        try {
            List<CubicMeterPreFabricatedDrawingEntry> entries = service.getCubicMeterPreFabricatedEntriesByWorkOrderAndLoadNumber(workOrder, loadNumber);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getAllCubicMeterPreFabricatedEntries/details")
    public ResponseEntity<List<CubicMeterPreFabricatedDrawingEntry>> getAllCubicMeterPreFabricatedEntries() {
        try {
            List<CubicMeterPreFabricatedDrawingEntry> entries = service.getAllCubicMeterPreFabricatedEntries();
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/updateCubicMeterPreFabricatedEntry/details/{id}")
    public ResponseEntity<CubicMeterPreFabricatedDrawingEntry> updateCubicMeterPreFabricatedEntry(@PathVariable Long id, @RequestBody CubicMeterPreFabricatedDrawingEntry entry) {
        try {
            CubicMeterPreFabricatedDrawingEntry updatedEntry = service.updateCubicMeterPreFabricatedEntry(id, entry);
            return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deleteCubicMeterPreFabricatedEntry/details/{id}")
    public ResponseEntity<String> deleteCubicMeterPreFabricatedEntry(@PathVariable Long id) {
        try {
            service.deleteCubicMeterPreFabricatedEntry(id);
            return new ResponseEntity<>("Cubic Meter Pre-Fabricated Drawing Entry deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error deleting entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deleteCubicMeterPreFabricatedEntriesByWorkOrder/details")
    public ResponseEntity<String> deleteCubicMeterPreFabricatedEntriesByWorkOrder(@RequestParam String workOrder) {
        try {
            service.deleteCubicMeterPreFabricatedEntriesByWorkOrder(workOrder);
            return new ResponseEntity<>("Cubic Meter Pre-Fabricated Drawing Entries deleted successfully for work order: " + workOrder, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error deleting entries", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deleteCubicMeterPreFabricatedEntriesByMarkNo/details")
    public ResponseEntity<String> deleteCubicMeterPreFabricatedEntriesByMarkNo(@RequestParam String markNo) {
        try {
            service.deleteCubicMeterPreFabricatedEntriesByMarkNo(markNo);
            return new ResponseEntity<>("Cubic Meter Pre-Fabricated Drawing Entries deleted successfully for mark no: " + markNo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error deleting entries", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getCubicMeterPreFabricatedDistinctWorkOrders/details")
    public ResponseEntity<List<String>> getCubicMeterPreFabricatedDistinctWorkOrders() {
        try {
            List<String> workOrders = service.getDistinctWorkOrders();
            return new ResponseEntity<>(workOrders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctServiceDescriptionsByWorkOrderFromCubicMeterPreFabricated/details")
    public ResponseEntity<List<String>> getDistinctServiceDescriptionsByWorkOrderFromCubicMeterModule(@RequestParam String workOrder) {
        try {
            List<String> serviceDescriptions = service.getDistinctServiceDescriptionsByWorkOrder(workOrder);
            return new ResponseEntity<>(serviceDescriptions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctWorkOrdersFromCubicMeterPreFabricated/details")
    public ResponseEntity<List<String>> getDistinctWorkOrdersFromCubicMeterModule() {
        try {
            List<String> workOrders = service.getDistinctWorkOrders();
            return new ResponseEntity<>(workOrders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCubicMeterPreFabricatedModulesByWorkOrder/details")
    public ResponseEntity<List<CubicMeterPreFabricatedDrawingEntry>> getCubicMeterModulesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<CubicMeterPreFabricatedDrawingEntry> entries = service.getCubicMeterPreFabricatedEntriesByWorkOrder(workOrder);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
