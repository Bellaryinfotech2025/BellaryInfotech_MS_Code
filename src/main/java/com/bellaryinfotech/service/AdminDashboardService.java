package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.AdminDashboardDTO;
import com.bellaryinfotech.DTO.ApprovalActionDTO;
import java.util.List;

public interface AdminDashboardService {
    void createLoginRequest(String email);
    List<AdminDashboardDTO> getAllRequests();
    List<AdminDashboardDTO> getPendingRequests();
    List<AdminDashboardDTO> getApprovedRequests();
    List<AdminDashboardDTO> getRejectedRequests();
    String processApprovalAction(ApprovalActionDTO approvalActionDTO);
    boolean isUserApproved(String email);
    boolean isUserRejected(String email);
    boolean hasUserRequestedLogin(String email);
}
