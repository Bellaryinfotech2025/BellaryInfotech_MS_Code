package com.bellaryinfotech.controller;

import com.bellaryinfotech.model.OrderFabricationErection;
import com.bellaryinfotech.service.OrderFabricationErectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v3.0/erection")
public class OrderFabricationErectionController {

    @Autowired
    private OrderFabricationErectionService erectionService;

    // API Paths as constants
    public static final String GET_MKDS = "/mkds";
    public static final String POST_MKDS = "/mkds";
    public static final String PUT_IMPORT_UPDATE_STATUS = "/import/update-status/{erectionMkd}";
    public static final String PUT_UPDATE_STATUS = "/update-status/{erectionMkd}";

     
    @GetMapping(value = GET_MKDS, produces = "application/json")
    public ResponseEntity<List<String>> getErectionMkds(
            @RequestParam(required = false, defaultValue = "details") String source) {
        List<String> mkds;
        switch (source.toLowerCase()) {
            case "import":
                mkds = erectionService.getAllErectionMkdsFromImport();
                break;
            case "details":
            default:
                mkds = erectionService.getAllErectionMkds();
                break;
        }
        return ResponseEntity.ok(mkds);
    }
    @GetMapping(value = "/stored-records", produces = "application/json")
    public ResponseEntity<List<OrderFabricationErection>> getStoredErectionRecords() {
        List<OrderFabricationErection> records = erectionService.getStoredErectionRecords();
        return ResponseEntity.ok(records);
    }


     
    @PostMapping(value = POST_MKDS, produces = "application/json")
    public ResponseEntity<String> storeErectionMkds(
            @RequestBody ErectionMkdRequest request) {
        if (request.getErectionMkds() == null || request.getErectionMkds().isEmpty()) {
            return ResponseEntity.badRequest().body("No erection mkd selected.");
        }
        List<OrderFabricationErection> stored;
        switch (request.getSource().toLowerCase()) {
            case "import":
                stored = erectionService.storeErectionMkdsFromImport(request.getErectionMkds());
                if (stored.isEmpty()) {
                    return ResponseEntity.badRequest().body("No matching records found in import table for given erection mkds.");
                }
                break;
            case "details":
            default:
                stored = erectionService.storeErectionMkdsFromDetails(request.getErectionMkds());
                if (stored.isEmpty()) {
                    return ResponseEntity.badRequest().body("No matching records found in details table for given erection mkds.");
                }
                break;
        }
        return ResponseEntity.ok("Selected erection mkds from " + request.getSource() + " table stored, and statuses updated in all tables.");
    }

     
    @PutMapping(value = PUT_IMPORT_UPDATE_STATUS, produces = "application/json")
    public ResponseEntity<String> updateImportStatus(
            @PathVariable String erectionMkd,
            @RequestParam String status) {
        erectionService.updateImportStatusByErectionMkd(erectionMkd, status);
        return ResponseEntity.ok("Status updated to '" + status + "' for erectionMkd: " + erectionMkd + " in import table.");
    }

    @PutMapping(value = PUT_UPDATE_STATUS, produces = "application/json")
    public ResponseEntity<String> updateStatus(
            @PathVariable String erectionMkd,
            @RequestParam String status) {
        erectionService.updateStatusByErectionMkd(erectionMkd, status);
        return ResponseEntity.ok("Status updated to '" + status + "' for erectionMkd: " + erectionMkd + " in erection table.");
    }

    // Inner class for POST request body
    public static class ErectionMkdRequest {
        private String source;
        private List<String> erectionMkds;

        // Getters and setters
        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }
        public List<String> getErectionMkds() { return erectionMkds; }
        public void setErectionMkds(List<String> erectionMkds) { this.erectionMkds = erectionMkds; }
    }
    
    
    
}
