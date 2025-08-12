package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.MeterPreFabricatedDrawingEntryDTO;
import com.bellaryinfotech.model.MeterPreFabricatedDrawingEntry;
import com.bellaryinfotech.service.MeterPreFabricatedDrawingEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class MeterPreFabricatedDrawingEntryController {
    
    @Autowired
    private MeterPreFabricatedDrawingEntryService service;
    
    @PostMapping("/saveMeterPreFabricatedEntries/details")
    public ResponseEntity<List<MeterPreFabricatedDrawingEntry>> saveMeterPreFabricatedEntries(@RequestBody MeterPreFabricatedDrawingEntryDTO dto) {
        try {
            List<MeterPreFabricatedDrawingEntry> savedEntries = service.saveMeterPreFabricatedEntries(dto);
            return new ResponseEntity<>(savedEntries, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/searchMeterPreFabricatedModulesByMarkNo/details")
    public ResponseEntity<List<MeterPreFabricatedDrawingEntry>> searchMeterPreFabricatedModulesByMarkNo(@RequestParam String markNo) {
        try {
            List<MeterPreFabricatedDrawingEntry> entries = service.getMeterPreFabricatedEntriesByMarkNo(markNo);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getMeterPreFabricatedEntriesByWorkOrder/details")
    public ResponseEntity<List<MeterPreFabricatedDrawingEntry>> getMeterPreFabricatedEntriesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<MeterPreFabricatedDrawingEntry> entries = service.getMeterPreFabricatedEntriesByWorkOrder(workOrder);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber/details")
    public ResponseEntity<List<MeterPreFabricatedDrawingEntry>> getMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber(
            @RequestParam String workOrder, @RequestParam String vehicleNumber) {
        try {
            List<MeterPreFabricatedDrawingEntry> entries = service.getMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber(workOrder, vehicleNumber);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getMeterPreFabricatedEntriesByWorkOrderAndLoadNumber/details")
    public ResponseEntity<List<MeterPreFabricatedDrawingEntry>> getMeterPreFabricatedEntriesByWorkOrderAndLoadNumber(
            @RequestParam String workOrder, @RequestParam String loadNumber) {
        try {
            List<MeterPreFabricatedDrawingEntry> entries = service.getMeterPreFabricatedEntriesByWorkOrderAndLoadNumber(workOrder, loadNumber);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getDistinctMeterPreFabricatedWorkOrders/details")
    public ResponseEntity<List<String>> getDistinctMeterPreFabricatedWorkOrders() {
        try {
            List<String> workOrders = service.getDistinctWorkOrders();
            return new ResponseEntity<>(workOrders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/updateMeterPreFabricatedEntry/details")
    public ResponseEntity<MeterPreFabricatedDrawingEntry> updateMeterPreFabricatedEntry(
            @RequestParam Long id, @RequestBody MeterPreFabricatedDrawingEntry updatedEntry) {
        try {
            MeterPreFabricatedDrawingEntry updated = service.updateMeterPreFabricatedEntry(id, updatedEntry);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deleteMeterPreFabricatedEntry/details")
    public ResponseEntity<String> deleteMeterPreFabricatedEntry(@RequestParam Long id) {
        try {
            service.deleteMeterPreFabricatedEntry(id);
            return new ResponseEntity<>("Entry deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deleteMeterPreFabricatedEntriesByWorkOrder/details")
    public ResponseEntity<String> deleteMeterPreFabricatedEntriesByWorkOrder(@RequestParam String workOrder) {
        try {
            service.deleteMeterPreFabricatedEntriesByWorkOrder(workOrder);
            return new ResponseEntity<>("Entries deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete entries", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
