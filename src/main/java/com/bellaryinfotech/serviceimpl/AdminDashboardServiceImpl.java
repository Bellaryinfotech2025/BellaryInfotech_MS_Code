package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.AdminDashboardDTO;
import com.bellaryinfotech.DTO.ApprovalActionDTO;
import com.bellaryinfotech.model.AdminDashboard;
import com.bellaryinfotech.model.User;
import com.bellaryinfotech.repo.AdminDashboardRepository;
import com.bellaryinfotech.repo.UserRepository;
import com.bellaryinfotech.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {
    
    private static final Logger logger = LoggerFactory.getLogger(AdminDashboardServiceImpl.class);
    private static final String ADMIN_EMAIL = "info@bellaryinfotech.com";
    
    @Autowired
    private AdminDashboardRepository adminDashboardRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public void createLoginRequest(String email) {
        try {
            // Don't create request for admin email
            if (ADMIN_EMAIL.equals(email)) {
                return;
            }
            
            // Check if user already has a pending request
            if (adminDashboardRepository.existsByEmailAndStatus(email, "Pending")) {
                logger.info("Login request already exists for email: {}", email);
                return;
            }
            
            // Check if user is already approved or rejected
            Optional<AdminDashboard> existingStatus = adminDashboardRepository.findApprovalStatusByEmail(email);
            if (existingStatus.isPresent()) {
                logger.info("User {} already has status: {}", email, existingStatus.get().getStatus());
                return;
            }
            
            // Get user details
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                AdminDashboard loginRequest = new AdminDashboard(user.getId(), email, "Pending");
                adminDashboardRepository.save(loginRequest);
                logger.info("Created login request for email: {}", email);
            }
        } catch (Exception e) {
            logger.error("Failed to create login request for email {}: {}", email, e.getMessage(), e);
        }
    }
    
    @Override
    public List<AdminDashboardDTO> getAllRequests() {
        try {
            List<AdminDashboard> requests = adminDashboardRepository.findAllByOrderByDatetimeDesc();
            return requests.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to get all requests: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch requests: " + e.getMessage());
        }
    }
    
    @Override
    public List<AdminDashboardDTO> getPendingRequests() {
        try {
            List<AdminDashboard> requests = adminDashboardRepository.findByStatusOrderByDatetimeDesc("Pending");
            return requests.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to get pending requests: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch pending requests: " + e.getMessage());
        }
    }
    
    @Override
    public List<AdminDashboardDTO> getApprovedRequests() {
        try {
            List<AdminDashboard> requests = adminDashboardRepository.findByStatusOrderByDatetimeDesc("Approved");
            return requests.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to get approved requests: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch approved requests: " + e.getMessage());
        }
    }
    
    @Override
    public List<AdminDashboardDTO> getRejectedRequests() {
        try {
            List<AdminDashboard> requests = adminDashboardRepository.findByStatusOrderByDatetimeDesc("Rejected");
            return requests.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to get rejected requests: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch rejected requests: " + e.getMessage());
        }
    }
    
    @Override
    public String processApprovalAction(ApprovalActionDTO approvalActionDTO) {
        try {
            Optional<AdminDashboard> requestOptional = adminDashboardRepository.findById(approvalActionDTO.getRequestId());
            if (!requestOptional.isPresent()) {
                throw new RuntimeException("Request not found");
            }
            
            AdminDashboard request = requestOptional.get();
            if (!request.getEmail().equals(approvalActionDTO.getEmail())) {
                throw new RuntimeException("Email mismatch");
            }
            
            String newStatus = "approve".equals(approvalActionDTO.getAction()) ? "Approved" : "Rejected";
            request.setStatus(newStatus);
            request.setUpdatedAt(LocalDateTime.now());
            adminDashboardRepository.save(request);
            
            String action = "approve".equals(approvalActionDTO.getAction()) ? "approved" : "rejected";
            logger.info("User {} has been {}", approvalActionDTO.getEmail(), action);
            
            return String.format("User %s has been %s successfully", approvalActionDTO.getEmail(), action);
        } catch (Exception e) {
            logger.error("Failed to process approval action: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process approval action: " + e.getMessage());
        }
    }
    
    @Override
    public boolean isUserApproved(String email) {
        if (ADMIN_EMAIL.equals(email)) {
            return true; // Admin is always approved
        }
        return adminDashboardRepository.existsByEmailAndStatus(email, "Approved");
    }
    
    @Override
    public boolean isUserRejected(String email) {
        if (ADMIN_EMAIL.equals(email)) {
            return false; // Admin is never rejected
        }
        return adminDashboardRepository.existsByEmailAndStatus(email, "Rejected");
    }
    
    @Override
    public boolean hasUserRequestedLogin(String email) {
        if (ADMIN_EMAIL.equals(email)) {
            return false; // Admin doesn't need approval
        }
        Optional<AdminDashboard> request = adminDashboardRepository.findApprovalStatusByEmail(email);
        return request.isPresent();
    }
    
    private AdminDashboardDTO convertToDTO(AdminDashboard request) {
        AdminDashboardDTO dto = new AdminDashboardDTO();
        dto.setId(request.getId());
        dto.setUserId(request.getUserId());
        dto.setEmail(request.getEmail());
        dto.setDatetime(request.getDatetime());
        dto.setStatus(request.getStatus());
        
        // Get user details
        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            dto.setFullname(user.getFullname());
            dto.setUsername(user.getUsername());
            dto.setRole(user.getRole());
        }
        
        return dto;
    }
}
