package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.SquareMeterPreFabricatedDrawingEntryDTO;
import com.bellaryinfotech.model.SquareMeterPreFabricatedDrawingEntry;
import com.bellaryinfotech.service.SquareMeterPreFabricatedDrawingEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class SquareMeterPreFabricatedDrawingEntryController {
    
    @Autowired
    private SquareMeterPreFabricatedDrawingEntryService service;
    
    @PostMapping("/saveSquareMeterPreFabricatedEntries/details")
    public ResponseEntity<List<SquareMeterPreFabricatedDrawingEntry>> saveSquareMeterPreFabricatedEntries(@RequestBody SquareMeterPreFabricatedDrawingEntryDTO dto) {
        try {
            List<SquareMeterPreFabricatedDrawingEntry> savedEntries = service.saveSquareMeterPreFabricatedEntries(dto);
            return new ResponseEntity<>(savedEntries, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/searchSquareMeterPreFabricatedModulesByMarkNo/details")
    public ResponseEntity<List<SquareMeterPreFabricatedDrawingEntry>> searchSquareMeterPreFabricatedModulesByMarkNo(@RequestParam String markNo) {
        try {
            List<SquareMeterPreFabricatedDrawingEntry> entries = service.getSquareMeterPreFabricatedEntriesByMarkNo(markNo);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getSquareMeterPreFabricatedEntriesByWorkOrder/details")
    public ResponseEntity<List<SquareMeterPreFabricatedDrawingEntry>> getSquareMeterPreFabricatedEntriesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<SquareMeterPreFabricatedDrawingEntry> entries = service.getSquareMeterPreFabricatedEntriesByWorkOrder(workOrder);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getSquareMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber/details")
    public ResponseEntity<List<SquareMeterPreFabricatedDrawingEntry>> getSquareMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber(
            @RequestParam String workOrder, @RequestParam String vehicleNumber) {
        try {
            List<SquareMeterPreFabricatedDrawingEntry> entries = service.getSquareMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber(workOrder, vehicleNumber);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getSquareMeterPreFabricatedEntriesByWorkOrderAndLoadNumber/details")
    public ResponseEntity<List<SquareMeterPreFabricatedDrawingEntry>> getSquareMeterPreFabricatedEntriesByWorkOrderAndLoadNumber(
            @RequestParam String workOrder, @RequestParam String loadNumber) {
        try {
            List<SquareMeterPreFabricatedDrawingEntry> entries = service.getSquareMeterPreFabricatedEntriesByWorkOrderAndLoadNumber(workOrder, loadNumber);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getAllSquareMeterPreFabricatedEntries/details")
    public ResponseEntity<List<SquareMeterPreFabricatedDrawingEntry>> getAllSquareMeterPreFabricatedEntries() {
        try {
            List<SquareMeterPreFabricatedDrawingEntry> entries = service.getAllSquareMeterPreFabricatedEntries();
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/updateSquareMeterPreFabricatedEntry/details/{id}")
    public ResponseEntity<SquareMeterPreFabricatedDrawingEntry> updateSquareMeterPreFabricatedEntry(@PathVariable Long id, @RequestBody SquareMeterPreFabricatedDrawingEntry entry) {
        try {
            SquareMeterPreFabricatedDrawingEntry updatedEntry = service.updateSquareMeterPreFabricatedEntry(id, entry);
            return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deleteSquareMeterPreFabricatedEntry/details/{id}")
    public ResponseEntity<String> deleteSquareMeterPreFabricatedEntry(@PathVariable Long id) {
        try {
            service.deleteSquareMeterPreFabricatedEntry(id);
            return new ResponseEntity<>("Square Meter Pre-Fabricated Drawing Entry deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error deleting entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deleteSquareMeterPreFabricatedEntriesByWorkOrder/details")
    public ResponseEntity<String> deleteSquareMeterPreFabricatedEntriesByWorkOrder(@RequestParam String workOrder) {
        try {
            service.deleteSquareMeterPreFabricatedEntriesByWorkOrder(workOrder);
            return new ResponseEntity<>("Square Meter Pre-Fabricated Drawing Entries deleted successfully for work order: " + workOrder, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error deleting entries", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deleteSquareMeterPreFabricatedEntriesByMarkNo/details")
    public ResponseEntity<String> deleteSquareMeterPreFabricatedEntriesByMarkNo(@RequestParam String markNo) {
        try {
            service.deleteSquareMeterPreFabricatedEntriesByMarkNo(markNo);
            return new ResponseEntity<>("Square Meter Pre-Fabricated Drawing Entries deleted successfully for mark no: " + markNo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error deleting entries", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getSquareMeterPreFabricatedDistinctWorkOrders/details")
    public ResponseEntity<List<String>> getSquareMeterPreFabricatedDistinctWorkOrders() {
        try {
            List<String> workOrders = service.getDistinctWorkOrders();
            return new ResponseEntity<>(workOrders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
