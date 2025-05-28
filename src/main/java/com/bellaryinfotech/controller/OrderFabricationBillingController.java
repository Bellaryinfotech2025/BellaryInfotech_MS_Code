package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.OrderFabricationBillingDTO;
import com.bellaryinfotech.service.OrderFabricationBillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v3.0/billing")
public class OrderFabricationBillingController {

    @Autowired
    private OrderFabricationBillingService billingService;

    
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<OrderFabricationBillingDTO>> getAllBillings() {
        List<OrderFabricationBillingDTO> billings = billingService.getAllBillings();
        return ResponseEntity.ok(billings);
    }

     
    @GetMapping(value = "/latest", produces = "application/json")
    public ResponseEntity<List<OrderFabricationBillingDTO>> getLatestBillings() {
        List<OrderFabricationBillingDTO> latestBillings = billingService.getLatestStoredBillings();
        return ResponseEntity.ok(latestBillings);
    }

     
    @PostMapping(value = "/store", produces = "application/json")
    public ResponseEntity<StoreResponse> storeSelectedErectionMkds(@RequestBody ErectionMkdRequest request) {
        if (request.getErectionMkds() == null || request.getErectionMkds().isEmpty()) {
            return ResponseEntity.badRequest().body(new StoreResponse("error", "No erection mkds provided", null));
        }
        
        try {
            List<OrderFabricationBillingDTO> stored = billingService.storeSelectedErectionMkds(request.getErectionMkds());
            
            if (stored.isEmpty()) {
                return ResponseEntity.ok(new StoreResponse("warning", "No matching records found for the provided erection mkds", stored));
            }
            
            return ResponseEntity.ok(new StoreResponse("success", 
                "Successfully stored " + stored.size() + " records to billing", stored));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(new StoreResponse("error", "Failed to store records: " + e.getMessage(), null));
        }
    }

    
    @DeleteMapping(value = "/clear-latest", produces = "application/json")
    public ResponseEntity<String> clearLatestStoredRecords() {
        billingService.clearLatestStoredFlag();
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
        private List<OrderFabricationBillingDTO> data;

        public StoreResponse(String status, String message, List<OrderFabricationBillingDTO> data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

        // Getters and setters
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public List<OrderFabricationBillingDTO> getData() { return data; }
        public void setData(List<OrderFabricationBillingDTO> data) { this.data = data; }
    }
}
