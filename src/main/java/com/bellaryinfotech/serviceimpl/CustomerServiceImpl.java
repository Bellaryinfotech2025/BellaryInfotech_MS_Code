package com.bellaryinfotech.serviceimpl;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
 
import com.bellaryinfotech.DAO.CustomerDAO;
import com.bellaryinfotech.DTOImpl.CustomerUpdateDTO;
import com.bellaryinfotech.DTOImpl.OrderRequestDTO;
import com.bellaryinfotech.model.CustomerAccount;
import com.bellaryinfotech.model.CustomerAccountSite;
import com.bellaryinfotech.model.CustomerContact;
import com.bellaryinfotech.model.OrderHeader;
import com.bellaryinfotech.service.CustomerService;
 
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{
    
    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);
    
    @Autowired
    private CustomerDAO customerDao;

    @Override
    public ResponseEntity<?> getcustomerDtl(Integer page, Integer size, String search, Long custAccountId,
            String accountNumber, String accountName){
        LOG.info("get Customer details :: ");
        
        List<CustomerAccount> list = customerDao.getcustomerDtl(null, null, search, custAccountId, accountNumber, accountName);
        return ResponseEntity.status(HttpStatus.OK).body(list); 
    }

    @Override
    public ResponseEntity<?> getAllSitesDetails(Integer page, Integer size, String search, Long custAccountId, 
            Long custAcctSiteId, String siteName) {
        LOG.info("get All Sites details :: ");
        
        List<CustomerAccountSite> list = customerDao.getAllSitesDetails(page, size, search, custAccountId, custAcctSiteId, siteName);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    
    @Override
    public ResponseEntity<?> getAllContactsDetails(Integer page, Integer size, String search, Long custAccountId, 
            Long custAcctSiteId, Long contactId, String roleType) {
        LOG.info("get All Contacts details :: ");
        
        List<CustomerContact> list = customerDao.getAllContactsDetails(page, size, search, custAccountId, custAcctSiteId, contactId, roleType);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    
    @Override
    public ResponseEntity<?> fetchCustomerIdAndSave(OrderRequestDTO orderRequestDTO) {
        LOG.info("Fetching customer IDs and saving to OrderHeader :: ");
        
        if (orderRequestDTO.getAccountName() == null || orderRequestDTO.getAccountName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account name is required");
        }
        
        OrderHeader orderHeader = customerDao.fetchCustomerIdAndSave(orderRequestDTO);
        
        if (orderHeader == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer account not found with name: " + orderRequestDTO.getAccountName());
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(orderHeader);
    }
    
    
    
    @Override
    public ResponseEntity<?> updateCustomerDetails(CustomerUpdateDTO customerUpdateDTO) {
        LOG.info("Updating customer details :: ");
        
        if (customerUpdateDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update data cannot be null");
        }
        
        // Validate that at least one entity is provided for update
        if (customerUpdateDTO.getCustomerAccount() == null && 
            customerUpdateDTO.getCustomerSite() == null && 
            customerUpdateDTO.getCustomerContact() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No update data provided");
        }
        
        // Validate IDs are provided
        if ((customerUpdateDTO.getCustomerAccount() != null && customerUpdateDTO.getCustomerAccount().getCustAccountId() == null) ||
            (customerUpdateDTO.getCustomerSite() != null && customerUpdateDTO.getCustomerSite().getCustAcctSiteId() == null) ||
            (customerUpdateDTO.getCustomerContact() != null && customerUpdateDTO.getCustomerContact().getContactId() == null)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID is required for update");
        }
        
        boolean updated = customerDao.updateCustomerDetails(customerUpdateDTO);
        
        if (updated) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Customer details updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No records were updated. Check if the provided IDs exist.");
        }
    }
}