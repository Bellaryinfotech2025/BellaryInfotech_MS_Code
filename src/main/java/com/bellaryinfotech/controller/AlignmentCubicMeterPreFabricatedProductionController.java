package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.AlignmentCubicMeterPreFabricatedProductionDTO;
import com.bellaryinfotech.model.AlignmentCubicMeterPreFabricatedProduction;
import com.bellaryinfotech.service.AlignmentCubicMeterPreFabricatedProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class AlignmentCubicMeterPreFabricatedProductionController {

    @Autowired
    private AlignmentCubicMeterPreFabricatedProductionService service;

    @PostMapping("/saveAlignmentCubicMeterPreFabricatedProduction/details")
    public ResponseEntity<List<AlignmentCubicMeterPreFabricatedProduction>> saveAlignmentCubicMeterPreFabricatedProduction(
            @RequestBody AlignmentCubicMeterPreFabricatedProductionDTO dto) {
        try {
            List<AlignmentCubicMeterPreFabricatedProduction> savedModules = service.saveAlignmentCubicMeterPreFabricatedProduction(dto);
            return new ResponseEntity<>(savedModules, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAlignmentCubicMeterPreFabricatedProductionByWorkOrder/details")
    public ResponseEntity<List<AlignmentCubicMeterPreFabricatedProduction>> getAlignmentCubicMeterPreFabricatedProductionByWorkOrder(
            @RequestParam String workOrder) {
        try {
            List<AlignmentCubicMeterPreFabricatedProduction> modules = service.getAlignmentCubicMeterPreFabricatedProductionByWorkOrder(workOrder);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAlignmentCubicMeterPreFabricatedByWorkOrderAndServiceDesc/details")
    public ResponseEntity<List<AlignmentCubicMeterPreFabricatedProduction>> getAlignmentCubicMeterPreFabricatedByWorkOrderAndServiceDesc(
            @RequestParam String workOrder, @RequestParam String serviceDescription) {
        try {
            List<AlignmentCubicMeterPreFabricatedProduction> modules = service.getAlignmentCubicMeterPreFabricatedByWorkOrderAndServiceDesc(workOrder, serviceDescription);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAlignmentCubicMeterPreFabricatedProductionByWorkOrderAndRaNo/details")
    public ResponseEntity<List<AlignmentCubicMeterPreFabricatedProduction>> getAlignmentCubicMeterPreFabricatedProductionByWorkOrderAndRaNo(
            @RequestParam String workOrder, @RequestParam String raNo) {
        try {
            List<AlignmentCubicMeterPreFabricatedProduction> modules = service.getAlignmentCubicMeterPreFabricatedProductionByWorkOrderAndRaNo(workOrder, raNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAlignmentCubicMeterPreFabricatedProductionByRaNo/details")
    public ResponseEntity<List<AlignmentCubicMeterPreFabricatedProduction>> getAlignmentCubicMeterPreFabricatedProductionByRaNo(
            @RequestParam String raNo) {
        try {
            List<AlignmentCubicMeterPreFabricatedProduction> modules = service.getAlignmentCubicMeterPreFabricatedProductionByRaNo(raNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchAlignmentCubicMeterPreFabricatedProductionByMarkNo/details")
    public ResponseEntity<List<AlignmentCubicMeterPreFabricatedProduction>> searchAlignmentCubicMeterPreFabricatedProductionByMarkNo(
            @RequestParam String markNo) {
        try {
            List<AlignmentCubicMeterPreFabricatedProduction> modules = service.getAlignmentCubicMeterPreFabricatedProductionByMarkNo(markNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctAlignmentCubicMeterPreFabricatedProductionWorkOrders/details")
    public ResponseEntity<List<String>> getDistinctAlignmentCubicMeterPreFabricatedProductionWorkOrders() {
        try {
            List<String> workOrders = service.getDistinctWorkOrders();
            return new ResponseEntity<>(workOrders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctRaNosByWorkOrder/details/cubic/production")
    public ResponseEntity<List<String>> getDistinctRaNosByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> raNumbers = service.getDistinctRaNosByWorkOrder(workOrder);
            return new ResponseEntity<>(raNumbers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctVehicleNumbersByWorkOrder/details/cubic/production")
    public ResponseEntity<List<String>> getDistinctVehicleNumbersByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> vehicleNumbers = service.getDistinctVehicleNumbersByWorkOrder(workOrder);
            return new ResponseEntity<>(vehicleNumbers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctLoadNumbersByWorkOrder/details/cubic/production")
    public ResponseEntity<List<String>> getDistinctLoadNumbersByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> loadNumbers = service.getDistinctLoadNumbersByWorkOrder(workOrder);
            return new ResponseEntity<>(loadNumbers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctPlotNumbersByWorkOrder/details/cubic/production")
    public ResponseEntity<List<String>> getDistinctPlotNumbersByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> plotNumbers = service.getDistinctPlotNumbersByWorkOrder(workOrder);
            return new ResponseEntity<>(plotNumbers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateAlignmentCubicMeterPreFabricatedProduction/details")
    public ResponseEntity<AlignmentCubicMeterPreFabricatedProduction> updateAlignmentCubicMeterPreFabricatedProduction(
            @RequestParam Long id, @RequestBody AlignmentCubicMeterPreFabricatedProduction updatedModule) {
        try {
            AlignmentCubicMeterPreFabricatedProduction updated = service.updateAlignmentCubicMeterPreFabricatedProduction(id, updatedModule);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAlignmentCubicMeterPreFabricatedProduction/details")
    public ResponseEntity<String> deleteAlignmentCubicMeterPreFabricatedProduction(@RequestParam Long id) {
        try {
            service.deleteAlignmentCubicMeterPreFabricatedProduction(id);
            return new ResponseEntity<>("Module deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete module", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAlignmentCubicMeterPreFabricatedProductionByWorkOrder/details")
    public ResponseEntity<String> deleteAlignmentCubicMeterPreFabricatedProductionByWorkOrder(@RequestParam String workOrder) {
        try {
            service.deleteAlignmentCubicMeterPreFabricatedProductionByWorkOrder(workOrder);
            return new ResponseEntity<>("Modules deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete modules", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAlignmentCubicMeterPreFabricatedProductionByWorkOrderAndRaNo/details")
    public ResponseEntity<String> deleteAlignmentCubicMeterPreFabricatedProductionByWorkOrderAndRaNo(
            @RequestParam String workOrder, @RequestParam String raNo) {
        try {
            service.deleteAlignmentCubicMeterPreFabricatedProductionByWorkOrderAndRaNo(workOrder, raNo);
            return new ResponseEntity<>("Modules deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete modules", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchAlignmentCubicMeterPreFabricatedProduction/details")
    public ResponseEntity<List<AlignmentCubicMeterPreFabricatedProduction>> searchAlignmentCubicMeterPreFabricatedProduction(
            @RequestParam String workOrder, 
            @RequestParam String serviceDescription,
            @RequestParam(required = false) String raNo) {
        try {
            List<AlignmentCubicMeterPreFabricatedProduction> modules = service.searchAlignmentCubicMeterPreFabricatedProduction(workOrder, serviceDescription, raNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}