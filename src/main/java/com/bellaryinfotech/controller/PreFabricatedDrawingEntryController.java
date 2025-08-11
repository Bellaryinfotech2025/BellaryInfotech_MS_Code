package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.PreFabricatedDrawingEntryDTO;
import com.bellaryinfotech.model.PreFabricatedDrawingEntry;
import com.bellaryinfotech.service.PreFabricatedDrawingEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class PreFabricatedDrawingEntryController {
    
    @Autowired
    private PreFabricatedDrawingEntryService service;
    
    @PostMapping("/savePreFabricatedEntries/details")
    public ResponseEntity<List<PreFabricatedDrawingEntry>> savePreFabricatedEntries(@RequestBody PreFabricatedDrawingEntryDTO dto) {
        try {
            List<PreFabricatedDrawingEntry> savedEntries = service.savePreFabricatedEntries(dto);
            return new ResponseEntity<>(savedEntries, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/searchPreFabricatedModulesByMarkNo/details")
    public ResponseEntity<List<PreFabricatedDrawingEntry>> searchPreFabricatedModulesByMarkNo(@RequestParam String markNo) {
        try {
            List<PreFabricatedDrawingEntry> entries = service.getPreFabricatedEntriesByMarkNo(markNo);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getPreFabricatedEntriesByWorkOrder/details")
    public ResponseEntity<List<PreFabricatedDrawingEntry>> getPreFabricatedEntriesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<PreFabricatedDrawingEntry> entries = service.getPreFabricatedEntriesByWorkOrder(workOrder);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getPreFabricatedEntriesByWorkOrderAndVehicleNumber/details")
    public ResponseEntity<List<PreFabricatedDrawingEntry>> getPreFabricatedEntriesByWorkOrderAndVehicleNumber(
            @RequestParam String workOrder, @RequestParam String vehicleNumber) {
        try {
            List<PreFabricatedDrawingEntry> entries = service.getPreFabricatedEntriesByWorkOrderAndVehicleNumber(workOrder, vehicleNumber);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getPreFabricatedEntriesByWorkOrderAndLoadNumber/details")
    public ResponseEntity<List<PreFabricatedDrawingEntry>> getPreFabricatedEntriesByWorkOrderAndLoadNumber(
            @RequestParam String workOrder, @RequestParam String loadNumber) {
        try {
            List<PreFabricatedDrawingEntry> entries = service.getPreFabricatedEntriesByWorkOrderAndLoadNumber(workOrder, loadNumber);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getPreFabricatedEntriesByWorkOrderAndPlotNumber/details")
    public ResponseEntity<List<PreFabricatedDrawingEntry>> getPreFabricatedEntriesByWorkOrderAndPlotNumber(
            @RequestParam String workOrder, @RequestParam String plotNumber) {
        try {
            List<PreFabricatedDrawingEntry> entries = service.getPreFabricatedEntriesByWorkOrderAndPlotNumber(workOrder, plotNumber);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getPreFabricatedEntriesByWorkOrderAndVehicleAndLoad/details")
    public ResponseEntity<List<PreFabricatedDrawingEntry>> getPreFabricatedEntriesByWorkOrderAndVehicleAndLoad(
            @RequestParam String workOrder, @RequestParam String vehicleNumber, @RequestParam String loadNumber) {
        try {
            List<PreFabricatedDrawingEntry> entries = service.getPreFabricatedEntriesByWorkOrderAndVehicleAndLoad(workOrder, vehicleNumber, loadNumber);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getPreFabricatedEntriesByWorkOrderAndVehicleAndPlot/details")
    public ResponseEntity<List<PreFabricatedDrawingEntry>> getPreFabricatedEntriesByWorkOrderAndVehicleAndPlot(
            @RequestParam String workOrder, @RequestParam String vehicleNumber, @RequestParam String plotNumber) {
        try {
            List<PreFabricatedDrawingEntry> entries = service.getPreFabricatedEntriesByWorkOrderAndVehicleAndPlot(workOrder, vehicleNumber, plotNumber);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getPreFabricatedEntriesByWorkOrderAndLoadAndPlot/details")
    public ResponseEntity<List<PreFabricatedDrawingEntry>> getPreFabricatedEntriesByWorkOrderAndLoadAndPlot(
            @RequestParam String workOrder, @RequestParam String loadNumber, @RequestParam String plotNumber) {
        try {
            List<PreFabricatedDrawingEntry> entries = service.getPreFabricatedEntriesByWorkOrderAndLoadAndPlot(workOrder, loadNumber, plotNumber);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getPreFabricatedEntriesByWorkOrderAndVehicleAndLoadAndPlot/details")
    public ResponseEntity<List<PreFabricatedDrawingEntry>> getPreFabricatedEntriesByWorkOrderAndVehicleAndLoadAndPlot(
            @RequestParam String workOrder, @RequestParam String vehicleNumber, 
            @RequestParam String loadNumber, @RequestParam String plotNumber) {
        try {
            List<PreFabricatedDrawingEntry> entries = service.getPreFabricatedEntriesByWorkOrderAndVehicleAndLoadAndPlot(workOrder, vehicleNumber, loadNumber, plotNumber);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getDistinctWorkOrdersFromPreFabricatedModule/details")
    public ResponseEntity<List<String>> getDistinctWorkOrdersFromPreFabricatedModule() {
        try {
            List<String> workOrders = service.getDistinctWorkOrders();
            return new ResponseEntity<>(workOrders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getDistinctVehicleNumbersByWorkOrderFromPreFabricatedModule/details")
    public ResponseEntity<List<String>> getDistinctVehicleNumbersByWorkOrderFromPreFabricatedModule(@RequestParam String workOrder) {
        try {
            List<String> vehicleNumbers = service.getDistinctVehicleNumbersByWorkOrder(workOrder);
            return new ResponseEntity<>(vehicleNumbers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getDistinctLoadNumbersByWorkOrderFromPreFabricatedModule/details")
    public ResponseEntity<List<String>> getDistinctLoadNumbersByWorkOrderFromPreFabricatedModule(@RequestParam String workOrder) {
        try {
            List<String> loadNumbers = service.getDistinctLoadNumbersByWorkOrder(workOrder);
            return new ResponseEntity<>(loadNumbers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getDistinctPlotNumbersByWorkOrderFromPreFabricatedModule/details")
    public ResponseEntity<List<String>> getDistinctPlotNumbersByWorkOrderFromPreFabricatedModule(@RequestParam String workOrder) {
        try {
            List<String> plotNumbers = service.getDistinctPlotNumbersByWorkOrder(workOrder);
            return new ResponseEntity<>(plotNumbers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getDistinctServiceDescriptionsByWorkOrderFromPreFabricatedModule/details")
    public ResponseEntity<List<String>> getDistinctServiceDescriptionsByWorkOrderFromPreFabricatedModule(@RequestParam String workOrder) {
        try {
            List<String> serviceDescriptions = service.getDistinctServiceDescriptionsByWorkOrder(workOrder);
            return new ResponseEntity<>(serviceDescriptions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getDistinctClientNamesByWorkOrderFromPreFabricatedModule/details")
    public ResponseEntity<List<String>> getDistinctClientNamesByWorkOrderFromPreFabricatedModule(@RequestParam String workOrder) {
        try {
            List<String> clientNames = service.getDistinctClientNamesByWorkOrder(workOrder);
            return new ResponseEntity<>(clientNames, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/updatePreFabricatedEntry/details")
    public ResponseEntity<PreFabricatedDrawingEntry> updatePreFabricatedEntry(@RequestParam Long id, @RequestBody PreFabricatedDrawingEntry updatedEntry) {
        try {
            PreFabricatedDrawingEntry updated = service.updatePreFabricatedEntry(id, updatedEntry);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deletePreFabricatedEntry/details")
    public ResponseEntity<String> deletePreFabricatedEntry(@RequestParam Long id) {
        try {
            service.deletePreFabricatedEntry(id);
            return new ResponseEntity<>("PreFabricatedDrawingEntry deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error deleting PreFabricatedDrawingEntry", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deletePreFabricatedEntriesByWorkOrder/details")
    public ResponseEntity<String> deletePreFabricatedEntriesByWorkOrder(@RequestParam String workOrder) {
        try {
            service.deletePreFabricatedEntriesByWorkOrder(workOrder);
            return new ResponseEntity<>("PreFabricatedDrawingEntries deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error deleting PreFabricatedDrawingEntries", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deletePreFabricatedEntriesByWorkOrderAndVehicleAndLoadAndPlot/details")
    public ResponseEntity<String> deletePreFabricatedEntriesByWorkOrderAndVehicleAndLoadAndPlot(
            @RequestParam String workOrder, @RequestParam String vehicleNumber, 
            @RequestParam String loadNumber, @RequestParam String plotNumber) {
        try {
            service.deletePreFabricatedEntriesByWorkOrderAndVehicleAndLoadAndPlot(workOrder, vehicleNumber, loadNumber, plotNumber);
            return new ResponseEntity<>("PreFabricatedDrawingEntries deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error deleting PreFabricatedDrawingEntries", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
