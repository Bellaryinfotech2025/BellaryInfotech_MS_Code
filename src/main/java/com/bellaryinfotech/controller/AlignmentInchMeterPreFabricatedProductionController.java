package com.bellaryinfotech.controller;
 

import com.bellaryinfotech.DTO.AlignmentInchMeterPreFabricatedProductionDTO;
import com.bellaryinfotech.model.AlignmentInchMeterPreFabricatedProduction;
import com.bellaryinfotech.service.AlignmentInchMeterPreFabricatedProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/V2.0")
public class AlignmentInchMeterPreFabricatedProductionController {

    private AlignmentInchMeterPreFabricatedProductionService service;

    @Autowired
    public void setService(AlignmentInchMeterPreFabricatedProductionService service) {
        this.service = service;
    }

    @GetMapping("/getAlignmentInchMeterPreFabricatedModulesByWorkOrder/details")
    public ResponseEntity<List<AlignmentInchMeterPreFabricatedProduction>> getByWorkOrder(
            @RequestParam String workOrder) {
        List<AlignmentInchMeterPreFabricatedProduction> result = service.getByWorkOrder(workOrder);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAlignmentInchMeterPreFabricatedModulesByRaNo/details")
    public ResponseEntity<List<AlignmentInchMeterPreFabricatedProduction>> getByRaNo(
            @RequestParam String raNo) {
        List<AlignmentInchMeterPreFabricatedProduction> result = service.getByRaNo(raNo);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAlignmentInchMeterPreFabricatedModulesByVehicleNumber/details")
    public ResponseEntity<List<AlignmentInchMeterPreFabricatedProduction>> getByVehicleNumber(
            @RequestParam String vehicleNumber) {
        List<AlignmentInchMeterPreFabricatedProduction> result = service.getByVehicleNumber(vehicleNumber);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getDistinctAlignmentInchMeterPreFabricatedWorkOrders/details")
    public ResponseEntity<List<String>> getDistinctWorkOrders() {
        List<String> result = service.getDistinctWorkOrders();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getDistinctVehicleNumbersByWorkOrder/details")
    public ResponseEntity<List<String>> getDistinctVehicleNumbersByWorkOrder(
            @RequestParam String workOrder) {
        List<String> result = service.getDistinctVehicleNumbersByWorkOrder(workOrder);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getDistinctLoadNumbersByWorkOrder/details")
    public ResponseEntity<List<String>> getDistinctLoadNumbersByWorkOrder(
            @RequestParam String workOrder) {
        List<String> result = service.getDistinctLoadNumbersByWorkOrder(workOrder);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getDistinctPlotNumbersByWorkOrder/details")
    public ResponseEntity<List<String>> getDistinctPlotNumbersByWorkOrder(
            @RequestParam String workOrder) {
        List<String> result = service.getDistinctPlotNumbersByWorkOrder(workOrder);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getDistinctRaNosByWorkOrder/details/alignment")
    public ResponseEntity<List<String>> getDistinctRaNosByWorkOrder(
            @RequestParam String workOrder) {
        List<String> result = service.getDistinctRaNosByWorkOrder(workOrder);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/saveAlignmentInchMeterPreFabricatedModule/details")
    public ResponseEntity<List<AlignmentInchMeterPreFabricatedProduction>> save(
            @RequestBody AlignmentInchMeterPreFabricatedProductionDTO dto) {
        List<AlignmentInchMeterPreFabricatedProduction> result = service.save(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/updateAlignmentInchMeterPreFabricatedModule/details")
    public ResponseEntity<AlignmentInchMeterPreFabricatedProduction> update(
            @RequestParam Long id,
            @RequestBody AlignmentInchMeterPreFabricatedProductionDTO.EntryDTO entryDTO) {
        AlignmentInchMeterPreFabricatedProduction result = service.update(id, entryDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/deleteAlignmentInchMeterPreFabricatedModule/details")
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}