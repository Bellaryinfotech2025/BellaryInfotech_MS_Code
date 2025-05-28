package com.bellaryinfotech.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bellaryinfotech.DTOImpl.CustomerUpdateDTO;
import com.bellaryinfotech.DTOImpl.OrderRequestDTO;
import com.bellaryinfotech.model.CustomerAccount;

@Service
public interface CustomerService {
    public ResponseEntity<?> getcustomerDtl(Integer page, Integer size, String search, Long custAccountId,
            String accountNumber, String accountName);
    
    public ResponseEntity<?> getAllSitesDetails(Integer page, Integer size, String search, Long custAccountId, 
            Long custAcctSiteId, String siteName);
            
    public ResponseEntity<?> getAllContactsDetails(Integer page, Integer size, String search, Long custAccountId, 
            Long custAcctSiteId, Long contactId, String roleType);
            
    public ResponseEntity<?> fetchCustomerIdAndSave(OrderRequestDTO orderRequestDTO);
    
    public ResponseEntity<?> updateCustomerDetails(CustomerUpdateDTO customerUpdateDTO);
}