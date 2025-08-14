package com.bellaryinfotech.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bellaryinfotech.DTO.AlignmentSquareMeterPreFabricatedDTO;
import com.bellaryinfotech.model.AlignmentSquareMeterPreFabricatedProduction;
import com.bellaryinfotech.service.AlignmentSquareMeterPreFabricatedProductionService;

import java.util.List;

@RestController
@RequestMapping("/api/V2.0")
public class AlignmentSquareMeterPreFabricatedProductionController {

 @Autowired
 private AlignmentSquareMeterPreFabricatedProductionService service;

 @PostMapping("/saveAlignmentSquareMeterPreFabricatedModule/details")
 public ResponseEntity<AlignmentSquareMeterPreFabricatedDTO> saveAlignmentSquareMeterPreFabricatedModule(@RequestBody AlignmentSquareMeterPreFabricatedDTO dto) {
     try {
         AlignmentSquareMeterPreFabricatedDTO savedDto = service.saveAlignmentSquareMeterPreFabricatedModule(dto);
         return ResponseEntity.ok(savedDto);
     } catch (Exception e) {
         return ResponseEntity.badRequest().body(null);
     }
 }

 @GetMapping("/getAlignmentSquareMeterPreFabricatedByWorkOrderAndServiceDesc/details")
 public ResponseEntity<List<AlignmentSquareMeterPreFabricatedProduction>> getByWorkOrderAndServiceDescription(
         @RequestParam String workOrder,
         @RequestParam String serviceDescription) {
     try {
         List<AlignmentSquareMeterPreFabricatedProduction> entries = service.getByWorkOrderAndServiceDescription(workOrder, serviceDescription);
         return ResponseEntity.ok(entries);
     } catch (Exception e) {
         return ResponseEntity.badRequest().body(null);
     }
 }

 @GetMapping("/getAlignmentSquareMeterPreFabricatedEntriesByWorkOrder/details")
 public ResponseEntity<List<AlignmentSquareMeterPreFabricatedProduction>> getEntriesByWorkOrder(
         @RequestParam String workOrder,
         @RequestParam(required = false) String vehicleNumber,
         @RequestParam(required = false) String loadNumber,
         @RequestParam(required = false) String plotNumber) {
     try {
         List<AlignmentSquareMeterPreFabricatedProduction> entries = service.getEntriesByWorkOrder(workOrder, vehicleNumber, loadNumber, plotNumber);
         return ResponseEntity.ok(entries);
     } catch (Exception e) {
         return ResponseEntity.badRequest().body(null);
     }
 }

 @PutMapping("/updateAlignmentSquareMeterPreFabricatedEntry/details")
 public ResponseEntity<AlignmentSquareMeterPreFabricatedProduction> updateEntry(
         @RequestParam Long id,
         @RequestBody AlignmentSquareMeterPreFabricatedProduction entry) {
     try {
         AlignmentSquareMeterPreFabricatedProduction updatedEntry = service.updateEntry(id, entry);
         return ResponseEntity.ok(updatedEntry);
     } catch (Exception e) {
         return ResponseEntity.badRequest().body(null);
     }
 }

 @DeleteMapping("/deleteAlignmentSquareMeterPreFabricatedEntry/details")
 public ResponseEntity<Void> deleteEntry(@RequestParam Long id) {
     try {
         service.deleteEntry(id);
         return ResponseEntity.ok().build();
     } catch (Exception e) {
         return ResponseEntity.badRequest().build();
     }
 }

 @GetMapping("/getAlignmentSquareMeterPreFabricatedDistinctWorkOrders/details")
 public ResponseEntity<List<String>> getDistinctWorkOrders() {
     try {
         List<String> workOrders = service.getDistinctWorkOrders();
         return ResponseEntity.ok(workOrders);
     } catch (Exception e) {
         return ResponseEntity.badRequest().body(null);
     }
 }
}