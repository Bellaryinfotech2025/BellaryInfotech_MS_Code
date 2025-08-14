package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.AlignmentPreFabricatedProductionDTO;
import com.bellaryinfotech.model.AlignmentPreFabricatedProduction;
import com.bellaryinfotech.service.AlignmentPreFabricatedProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class AlignmentPreFabricatedProductionController {

    @Autowired
    private AlignmentPreFabricatedProductionService service;

    @PostMapping("/saveAlignmentPreFabricatedProduction/details")
    public ResponseEntity<List<AlignmentPreFabricatedProduction>> saveAlignmentPreFabricatedProduction(@RequestBody AlignmentPreFabricatedProductionDTO dto) {
        try {
            List<AlignmentPreFabricatedProduction> savedProductions = service.saveAlignmentPreFabricatedProduction(dto);
            return new ResponseEntity<>(savedProductions, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAlignmentPreFabricatedProductionsByWorkOrder/details")
    public ResponseEntity<List<AlignmentPreFabricatedProduction>> getAlignmentPreFabricatedProductionsByWorkOrder(@RequestParam String workOrder) {
        try {
            List<AlignmentPreFabricatedProduction> productions = service.getAlignmentPreFabricatedProductionsByWorkOrder(workOrder);
            return new ResponseEntity<>(productions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAlignmentPreFabricatedProductionsByRaNo/details")
    public ResponseEntity<List<AlignmentPreFabricatedProduction>> getAlignmentPreFabricatedProductionsByRaNo(@RequestParam String raNo) {
        try {
            List<AlignmentPreFabricatedProduction> productions = service.getAlignmentPreFabricatedProductionsByRaNo(raNo);
            return new ResponseEntity<>(productions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getRaNoByWorkOrderAndServiceDescriptionAlignment/details")
    public ResponseEntity<String> getRaNoByWorkOrderAndServiceDescription(
            @RequestParam String workOrder, @RequestParam String serviceDescription) {
        try {
            String raNo = service.getLatestRaNoByWorkOrderAndServiceDescription(workOrder, serviceDescription);
            return new ResponseEntity<>(raNo != null ? raNo : "", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateAlignmentPreFabricatedProduction/details")
    public ResponseEntity<AlignmentPreFabricatedProduction> updateAlignmentPreFabricatedProduction(
            @RequestParam Long id, @RequestBody AlignmentPreFabricatedProduction updatedProduction) {
        try {
            AlignmentPreFabricatedProduction updated = service.updateAlignmentPreFabricatedProduction(id, updatedProduction);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAlignmentPreFabricatedProduction/details")
    public ResponseEntity<String> deleteAlignmentPreFabricatedProduction(@RequestParam Long id) {
        try {
            service.deleteAlignmentPreFabricatedProduction(id);
            return new ResponseEntity<>("Production entry deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete production entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}