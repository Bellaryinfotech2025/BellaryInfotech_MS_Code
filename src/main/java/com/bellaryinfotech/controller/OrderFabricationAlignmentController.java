package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.OrderFabricationAlignmentDTO;
import com.bellaryinfotech.service.OrderFabricationAlignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v3.0/alignment")
public class OrderFabricationAlignmentController {

    @Autowired
    private OrderFabricationAlignmentService alignmentService;

     
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<OrderFabricationAlignmentDTO>> getAllAlignments() {
        List<OrderFabricationAlignmentDTO> alignments = alignmentService.getAllAlignments();
        return ResponseEntity.ok(alignments);
    }

     
    @GetMapping(value = "/latest", produces = "application/json")
    public ResponseEntity<List<OrderFabricationAlignmentDTO>> getLatestAlignments() {
        List<OrderFabricationAlignmentDTO> latestAlignments = alignmentService.getLatestStoredAlignments();
        return ResponseEntity.ok(latestAlignments);
    }

     
    @GetMapping(value = "/erection-mkds", produces = "application/json")
    public ResponseEntity<List<String>> getAllErectionMkds() {
        try {
            List<String> erectionMkds = alignmentService.getAllDistinctErectionMkds();
            System.out.println("Controller: Found " + erectionMkds.size() + " distinct erection mkds");
            System.out.println("Controller: Erection mkds: " + erectionMkds);
            return ResponseEntity.ok(erectionMkds);
        } catch (Exception e) {
            System.err.println("Controller: Error fetching erection mkds: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    
    @PostMapping(value = "/by-mkds", produces = "application/json")
    public ResponseEntity<List<OrderFabricationAlignmentDTO>> getAlignmentsByMkds(@RequestBody ErectionMkdRequest request) {
        if (request.getErectionMkds() == null || request.getErectionMkds().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<OrderFabricationAlignmentDTO> alignments = alignmentService.getAlignmentsByErectionMkds(request.getErectionMkds());
        return ResponseEntity.ok(alignments);
    }

     
    @PostMapping(value = "/store", produces = "application/json")
    public ResponseEntity<StoreResponse> storeSelectedErectionMkds(@RequestBody ErectionMkdRequest request) {
        if (request.getErectionMkds() == null || request.getErectionMkds().isEmpty()) {
            return ResponseEntity.badRequest().body(new StoreResponse("error", "No erection mkds provided", null));
        }
        
        try {
            List<OrderFabricationAlignmentDTO> stored = alignmentService.storeSelectedErectionMkds(request.getErectionMkds());
            
            if (stored.isEmpty()) {
                return ResponseEntity.ok(new StoreResponse("warning", "No matching records found for the provided erection mkds", stored));
            }
            
            return ResponseEntity.ok(new StoreResponse("success", 
                "Successfully stored " + stored.size() + " records to alignment", stored));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(new StoreResponse("error", "Failed to store records: " + e.getMessage(), null));
        }
    }

     
    @DeleteMapping(value = "/clear-latest", produces = "application/json")
    public ResponseEntity<String> clearLatestStoredRecords() {
        alignmentService.clearLatestStoredFlag();
        return ResponseEntity.ok("Latest stored flags cleared");
    }

    // Inner class for POST request body
    public static class ErectionMkdRequest {
        private List<String> erectionMkds;
        public List<String> getErectionMkds() { return erectionMkds; }
        public void setErectionMkds(List<String> erectionMkds) { this.erectionMkds = erectionMkds; }
    }

    
    public static class StoreResponse {
        private String status;
        private String message;
        private List<OrderFabricationAlignmentDTO> data;

        public StoreResponse(String status, String message, List<OrderFabricationAlignmentDTO> data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

        // Getters and setters
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public List<OrderFabricationAlignmentDTO> getData() { return data; }
        public void setData(List<OrderFabricationAlignmentDTO> data) { this.data = data; }
    }
}
