package com.bellaryinfotech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bellaryinfotech.DTO.FabricationDrawingEntryDto;
import com.bellaryinfotech.service.FabricationDrawingEntryService;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class FabricationDrawingEntryController {

    @Autowired
    private FabricationDrawingEntryService fabricationDrawingEntryService;

    @PostMapping("/createFabricationDrawingEntries/details")
    public ResponseEntity<?> createFabricationDrawingEntries(@RequestBody List<FabricationDrawingEntryDto> fabricationEntries) {
        try {
            List<FabricationDrawingEntryDto> createdEntries = fabricationDrawingEntryService.createFabricationDrawingEntries(fabricationEntries);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Fabrication drawing entries created successfully");
            response.put("createdCount", createdEntries.size());
            response.put("data", createdEntries);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error creating fabrication drawing entries: " + e.getMessage());
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/getFabricationEntriesByWorkOrderAndBuilding/details")
    public ResponseEntity<?> getFabricationEntriesByWorkOrderAndBuilding(
            @RequestParam String workOrder,
            @RequestParam String buildingName) {
        try {
            List<FabricationDrawingEntryDto> entries = fabricationDrawingEntryService.getFabricationEntriesByWorkOrderAndBuilding(workOrder, buildingName);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Fabrication entries retrieved successfully");
            response.put("count", entries.size());
            response.put("data", entries);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error retrieving fabrication entries: " + e.getMessage());
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/getFabricationEntriesByDrawingAndMark/details")
    public ResponseEntity<?> getFabricationEntriesByDrawingAndMark(
            @RequestParam String drawingNo,
            @RequestParam String markNo) {
        try {
            List<FabricationDrawingEntryDto> entries = fabricationDrawingEntryService.getFabricationEntriesByDrawingAndMark(drawingNo, markNo);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Fabrication entries retrieved successfully");
            response.put("count", entries.size());
            response.put("data", entries);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error retrieving fabrication entries: " + e.getMessage());
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/getFabricationEntriesByOrderId/details")
    public ResponseEntity<?> getFabricationEntriesByOrderId(@RequestParam Long orderId) {
        try {
            List<FabricationDrawingEntryDto> entries = fabricationDrawingEntryService.getFabricationEntriesByOrderId(orderId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Fabrication entries retrieved successfully");
            response.put("count", entries.size());
            response.put("data", entries);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error retrieving fabrication entries: " + e.getMessage());
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/getAllFabricationEntries/details")
    public ResponseEntity<?> getAllFabricationEntries() {
        try {
            List<FabricationDrawingEntryDto> entries = fabricationDrawingEntryService.getAllFabricationEntries();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "All fabrication entries retrieved successfully");
            response.put("count", entries.size());
            response.put("data", entries);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error retrieving all fabrication entries: " + e.getMessage());
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/getFabricationEntryById/details")
    public ResponseEntity<?> getFabricationEntryById(@RequestParam Long id) {
        try {
            FabricationDrawingEntryDto entry = fabricationDrawingEntryService.getFabricationEntryById(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Fabrication entry retrieved successfully");
            response.put("data", entry);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error retrieving fabrication entry: " + e.getMessage());
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/getFabricationEntriesByMultipleFilters/details")
    public ResponseEntity<?> getFabricationEntriesByMultipleFilters(
            @RequestParam(required = false) String workOrder,
            @RequestParam(required = false) String buildingName,
            @RequestParam(required = false) String drawingNo,
            @RequestParam(required = false) String markNo) {
        try {
            List<FabricationDrawingEntryDto> entries = fabricationDrawingEntryService.getFabricationEntriesByMultipleFilters(workOrder, buildingName, drawingNo, markNo);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Fabrication entries retrieved successfully");
            response.put("count", entries.size());
            response.put("data", entries);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error retrieving fabrication entries: " + e.getMessage());
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/getCountByWorkOrder/details")
    public ResponseEntity<?> getCountByWorkOrder(@RequestParam String workOrder) {
        try {
            Long count = fabricationDrawingEntryService.getCountByWorkOrder(workOrder);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Count retrieved successfully");
            response.put("count", count);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error getting count: " + e.getMessage());
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/getCountByBuildingName/details")
    public ResponseEntity<?> getCountByBuildingName(@RequestParam String buildingName) {
        try {
            Long count = fabricationDrawingEntryService.getCountByBuildingName(buildingName);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Count retrieved successfully");
            response.put("count", count);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error getting count: " + e.getMessage());
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/getCountByDrawingAndMark/details")
    public ResponseEntity<?> getCountByDrawingAndMark(
            @RequestParam String drawingNo,
            @RequestParam String markNo) {
        try {
            Long count = fabricationDrawingEntryService.getCountByDrawingAndMark(drawingNo, markNo);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Count retrieved successfully");
            response.put("count", count);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error getting count: " + e.getMessage());
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/deleteFabricationEntry/details")
    public ResponseEntity<?> deleteFabricationEntry(@RequestParam Long id) {
        try {
            fabricationDrawingEntryService.deleteFabricationEntry(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Fabrication entry deleted successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error deleting fabrication entry: " + e.getMessage());
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/deleteFabricationEntryByLineId/details")
    public ResponseEntity<?> deleteFabricationEntryByLineId(@RequestParam Long lineId) {
        try {
            fabricationDrawingEntryService.deleteByLineId(lineId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Fabrication entry deleted successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error deleting fabrication entry: " + e.getMessage());
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/checkFabricationEntryExists/details")
    public ResponseEntity<?> checkFabricationEntryExists(@RequestParam Long lineId) {
        try {
            boolean exists = fabricationDrawingEntryService.existsByLineId(lineId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Check completed successfully");
            response.put("exists", exists);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error checking existence: " + e.getMessage());
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // NEW: Endpoint to get distinct RA numbers
    @GetMapping("/getDistinctFabricationRaNumbers/details")
    public ResponseEntity<?> getDistinctFabricationRaNumbers() {
        try {
            List<String> raNumbers = fabricationDrawingEntryService.getDistinctRaNumbers();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Distinct RA numbers retrieved successfully");
            response.put("count", raNumbers.size());
            response.put("data", raNumbers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error retrieving distinct RA numbers: " + e.getMessage());
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // NEW: Endpoint to get distinct drawing numbers by order ID
    @GetMapping("/getDrawingNumbersByOrderId/details")
    public ResponseEntity<?> getDrawingNumbersByOrderId(@RequestParam Long orderId) {
        try {
            List<String> drawingNumbers = fabricationDrawingEntryService.getDistinctDrawingNumbersByOrderId(orderId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Distinct drawing numbers retrieved successfully");
            response.put("count", drawingNumbers.size());
            response.put("data", drawingNumbers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error retrieving distinct drawing numbers: " + e.getMessage());
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // NEW: Endpoint to get distinct mark numbers by order ID
    @GetMapping("/getMarkNumbersByOrderId/details")
    public ResponseEntity<?> getMarkNumbersByOrderId(@RequestParam Long orderId) {
        try {
            List<String> markNumbers = fabricationDrawingEntryService.getDistinctMarkNumbersByOrderId(orderId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Distinct mark numbers retrieved successfully");
            response.put("count", markNumbers.size());
            response.put("data", markNumbers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error retrieving distinct mark numbers: " + e.getMessage());
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // NEW: Endpoint to get total marked weight by drawing number and mark number
    @GetMapping("/getTotalMarkedWeightByDrawingAndMark/details")
    public ResponseEntity<?> getTotalMarkedWeightByDrawingAndMark(
            @RequestParam Long orderId,
            @RequestParam String drawingNo,
            @RequestParam String markNo) {
        try {
            Double totalWeight = fabricationDrawingEntryService.getTotalMarkedWeightByDrawingAndMark(orderId, drawingNo, markNo);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Total marked weight retrieved successfully");
            response.put("totalWeight", totalWeight != null ? totalWeight : 0.0);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error fetching total marked weight: " + e.getMessage());
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
