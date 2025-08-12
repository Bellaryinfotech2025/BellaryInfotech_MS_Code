package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.InchMeterPreFabricatedDrawingEntryDTO;
import com.bellaryinfotech.model.InchMeterPreFabricatedDrawingEntry;
import com.bellaryinfotech.service.InchMeterPreFabricatedDrawingEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class InchMeterPreFabricatedDrawingEntryController {
    
    @Autowired
    private InchMeterPreFabricatedDrawingEntryService service;
    
    @PostMapping("/saveInchMeterPreFabricatedEntries/details")
    public ResponseEntity<List<InchMeterPreFabricatedDrawingEntry>> saveInchMeterPreFabricatedEntries(@RequestBody InchMeterPreFabricatedDrawingEntryDTO dto) {
        try {
            List<InchMeterPreFabricatedDrawingEntry> savedEntries = service.saveInchMeterPreFabricatedEntries(dto);
            return new ResponseEntity<>(savedEntries, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/searchInchMeterPreFabricatedModulesByMarkNo/details")
    public ResponseEntity<List<InchMeterPreFabricatedDrawingEntry>> searchInchMeterPreFabricatedModulesByMarkNo(@RequestParam String markNo) {
        try {
            List<InchMeterPreFabricatedDrawingEntry> entries = service.getInchMeterPreFabricatedEntriesByMarkNo(markNo);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getInchMeterPreFabricatedEntriesByWorkOrder/details")
    public ResponseEntity<List<InchMeterPreFabricatedDrawingEntry>> getInchMeterPreFabricatedEntriesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<InchMeterPreFabricatedDrawingEntry> entries = service.getInchMeterPreFabricatedEntriesByWorkOrder(workOrder);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getInchMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber/details")
    public ResponseEntity<List<InchMeterPreFabricatedDrawingEntry>> getInchMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber(
            @RequestParam String workOrder, @RequestParam String vehicleNumber) {
        try {
            List<InchMeterPreFabricatedDrawingEntry> entries = service.getInchMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber(workOrder, vehicleNumber);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getInchMeterPreFabricatedEntriesByWorkOrderAndLoadNumber/details")
    public ResponseEntity<List<InchMeterPreFabricatedDrawingEntry>> getInchMeterPreFabricatedEntriesByWorkOrderAndLoadNumber(
            @RequestParam String workOrder, @RequestParam String loadNumber) {
        try {
            List<InchMeterPreFabricatedDrawingEntry> entries = service.getInchMeterPreFabricatedEntriesByWorkOrderAndLoadNumber(workOrder, loadNumber);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getDistinctInchMeterPreFabricatedWorkOrders/details")
    public ResponseEntity<List<String>> getDistinctInchMeterPreFabricatedWorkOrders() {
        try {
            List<String> workOrders = service.getDistinctWorkOrders();
            return new ResponseEntity<>(workOrders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/updateInchMeterPreFabricatedEntry/details")
    public ResponseEntity<InchMeterPreFabricatedDrawingEntry> updateInchMeterPreFabricatedEntry(
            @RequestParam Long id, @RequestBody InchMeterPreFabricatedDrawingEntry updatedEntry) {
        try {
            InchMeterPreFabricatedDrawingEntry updated = service.updateInchMeterPreFabricatedEntry(id, updatedEntry);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deleteInchMeterPreFabricatedEntry/details")
    public ResponseEntity<String> deleteInchMeterPreFabricatedEntry(@RequestParam Long id) {
        try {
            service.deleteInchMeterPreFabricatedEntry(id);
            return new ResponseEntity<>("Entry deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deleteInchMeterPreFabricatedEntriesByWorkOrder/details")
    public ResponseEntity<String> deleteInchMeterPreFabricatedEntriesByWorkOrder(@RequestParam String workOrder) {
        try {
            service.deleteInchMeterPreFabricatedEntriesByWorkOrder(workOrder);
            return new ResponseEntity<>("Entries deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete entries", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
