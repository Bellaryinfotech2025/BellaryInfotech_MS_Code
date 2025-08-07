package com.bellaryinfotech.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bellaryinfotech.DTO.MtrModuleDTO;
import com.bellaryinfotech.service.MtrModuleService;

import java.util.List;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class MtrModuleController {

    @Autowired
    private MtrModuleService mtrModuleService;

    @PostMapping("/createMeterModule/details")
    public ResponseEntity<MtrModuleDTO> createMeterModule(@RequestBody MtrModuleDTO mtrModuleDTO) {
        try {
            MtrModuleDTO createdModule = mtrModuleService.createMeterModule(mtrModuleDTO);
            return new ResponseEntity<>(createdModule, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateMeterModule/details")
    public ResponseEntity<MtrModuleDTO> updateMeterModule(@RequestParam Long id, @RequestBody MtrModuleDTO mtrModuleDTO) {
        try {
            MtrModuleDTO updatedModule = mtrModuleService.updateMeterModule(id, mtrModuleDTO);
            return new ResponseEntity<>(updatedModule, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteMeterModule/details")
    public ResponseEntity<String> deleteMeterModule(@RequestParam Long id) {
        try {
            mtrModuleService.deleteMeterModule(id);
            return new ResponseEntity<>("MeterModule deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("MeterModule not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting MeterModule", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getMeterModule/details")
    public ResponseEntity<MtrModuleDTO> getMeterModuleById(@RequestParam Long id) {
        try {
            MtrModuleDTO mtrModuleDTO = mtrModuleService.getMeterModuleById(id);
            return new ResponseEntity<>(mtrModuleDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllMeterModules/details")
    public ResponseEntity<List<MtrModuleDTO>> getAllMeterModules() {
        try {
            List<MtrModuleDTO> modules = mtrModuleService.getAllMeterModules();
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchMeterModulesByMarkNo/details")
    public ResponseEntity<List<MtrModuleDTO>> getMeterModulesByMarkNo(@RequestParam String markNo) {
        try {
            List<MtrModuleDTO> modules = mtrModuleService.getMeterModulesByMarkNo(markNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getMeterModulesByWorkOrder/details")
    public ResponseEntity<List<MtrModuleDTO>> getMeterModulesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<MtrModuleDTO> modules = mtrModuleService.getMeterModulesByWorkOrder(workOrder);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getMeterModulesByWorkOrderAndBuilding/details")
    public ResponseEntity<List<MtrModuleDTO>> getMeterModulesByWorkOrderAndBuildingName(
            @RequestParam String workOrder, @RequestParam String buildingName) {
        try {
            List<MtrModuleDTO> modules = mtrModuleService.getMeterModulesByWorkOrderAndBuildingName(workOrder, buildingName);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctWorkOrdersFromMeterModule/details")
    public ResponseEntity<List<String>> getDistinctWorkOrders() {
        try {
            List<String> workOrders = mtrModuleService.getDistinctWorkOrders();
            return new ResponseEntity<>(workOrders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctBuildingNamesByWorkOrderFromMeterModule/details")
    public ResponseEntity<List<String>> getDistinctBuildingNamesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> buildingNames = mtrModuleService.getDistinctBuildingNamesByWorkOrder(workOrder);
            return new ResponseEntity<>(buildingNames, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteMeterModulesByMarkNo/details")
    public ResponseEntity<String> deleteMeterModulesByMarkNo(@RequestParam String markNo) {
        try {
            mtrModuleService.deleteMeterModulesByMarkNo(markNo);
            return new ResponseEntity<>("MeterModules deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting MeterModules", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
     
}

