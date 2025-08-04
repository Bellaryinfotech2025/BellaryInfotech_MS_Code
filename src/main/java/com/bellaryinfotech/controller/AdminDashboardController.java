package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.AdminDashboardDTO;
import com.bellaryinfotech.DTO.ApprovalActionDTO;
import com.bellaryinfotech.service.AdminDashboardService;
import com.bellaryinfotech.util.JwtUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin") // Make sure this matches your API_URL + /admin
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminDashboardController {
    
    private static final Logger logger = LoggerFactory.getLogger(AdminDashboardController.class);
    private static final String ADMIN_EMAIL = "info@bellaryinfotech.com";
    
    @Autowired
    private AdminDashboardService adminDashboardService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    // Verify admin access
    private boolean isAdminUser(String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                logger.warn("No token provided");
                return false;
            }
            
            // Remove Bearer prefix if present
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            if (!jwtUtil.validateToken(token)) {
                logger.warn("Invalid token");
                return false;
            }
            
            String email = jwtUtil.extractUsername(token);
            logger.info("Token validated for email: {}", email);
            boolean isAdmin = ADMIN_EMAIL.equals(email);
            logger.info("Is admin user: {}", isAdmin);
            
            return isAdmin;
        } catch (Exception e) {
            logger.error("Admin verification failed: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @GetMapping("/dashboard/requests")
    public ResponseEntity<?> getAllRequests(@RequestHeader("Authorization") String authHeader) {
        try {
            logger.info("Received request for all admin requests");
            logger.info("Authorization header: {}", authHeader != null ? "Present" : "Missing");
            
            if (authHeader == null) {
                return ResponseEntity.status(401).body("Authorization header missing");
            }
            
            String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
            
            if (!isAdminUser(token)) {
                logger.warn("Access denied for token");
                return ResponseEntity.status(403).body("Access denied. Admin privileges required.");
            }
            
            List<AdminDashboardDTO> requests = adminDashboardService.getAllRequests();
            logger.info("Returning {} requests", requests.size());
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            logger.error("Failed to get all requests: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Failed to fetch requests: " + e.getMessage());
        }
    }
    
    @GetMapping("/dashboard/requests/pending")
    public ResponseEntity<?> getPendingRequests(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            if (!isAdminUser(token)) {
                return ResponseEntity.status(403).body("Access denied. Admin privileges required.");
            }
            
            List<AdminDashboardDTO> requests = adminDashboardService.getPendingRequests();
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            logger.error("Failed to get pending requests: {}", e.getMessage());
            return ResponseEntity.status(500).body("Failed to fetch pending requests: " + e.getMessage());
        }
    }
    
    @GetMapping("/dashboard/requests/approved")
    public ResponseEntity<?> getApprovedRequests(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            if (!isAdminUser(token)) {
                return ResponseEntity.status(403).body("Access denied. Admin privileges required.");
            }
            
            List<AdminDashboardDTO> requests = adminDashboardService.getApprovedRequests();
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            logger.error("Failed to get approved requests: {}", e.getMessage());
            return ResponseEntity.status(500).body("Failed to fetch approved requests: " + e.getMessage());
        }
    }
    
    @GetMapping("/dashboard/requests/rejected")
    public ResponseEntity<?> getRejectedRequests(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            if (!isAdminUser(token)) {
                return ResponseEntity.status(403).body("Access denied. Admin privileges required.");
            }
            
            List<AdminDashboardDTO> requests = adminDashboardService.getRejectedRequests();
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            logger.error("Failed to get rejected requests: {}", e.getMessage());
            return ResponseEntity.status(500).body("Failed to fetch rejected requests: " + e.getMessage());
        }
    }
    
    @PostMapping("/dashboard/action")
    public ResponseEntity<String> processApprovalAction(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody ApprovalActionDTO approvalActionDTO) {
        try {
            String token = authHeader.replace("Bearer ", "");
            if (!isAdminUser(token)) {
                return ResponseEntity.status(403).body("Access denied. Admin privileges required.");
            }
            
            String result = adminDashboardService.processApprovalAction(approvalActionDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Failed to process approval action: {}", e.getMessage());
            return ResponseEntity.status(500).body("Failed to process action: " + e.getMessage());
        }
    }
}
