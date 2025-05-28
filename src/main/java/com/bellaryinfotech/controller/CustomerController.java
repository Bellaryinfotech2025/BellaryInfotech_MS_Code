package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTOImpl.CustomerUpdateDTO;
import com.bellaryinfotech.DTOImpl.OrderRequestDTO;
import com.bellaryinfotech.service.CustomerService;
import com.bellaryinfotech.service.CustomerServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/V2.0")
 
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    CustomerServiceImpl customerServiceImpl;
    
    public final String GET_CUSTOMER = "/getallcustomeraccount/details";
    public final String GET_ALL_SITES_DETAILS = "/getallaccountsitesall/details";
    public final String GET_ALL_CONTACTS_DETAILS = "/getallcustomercontacts/details";
    public final String FETCH_CUSTOMER_NAME = "/fetchcustomernames/details";
    public final String UPDATE_CUSTOMER = "/updatecustomer/details";
    
    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);
    
    @RequestMapping(value = GET_CUSTOMER, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getInvoiceRulesDetails(
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "direction", required = false) String direction,
            @RequestParam(value = "sort", required = false) String[] sort,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "customerId", required = false) Long custAccountId,
            @RequestParam(value = "customerName", required = false) String customerName,
            @RequestParam(value = "accountNumber", required = false) String accountNumber) {
        
        LOG.info("get customer");
        return customerServiceImpl.getcustomerDtl(page, size, search, custAccountId, accountNumber, customerName);
    }
    
    @RequestMapping(value = GET_ALL_SITES_DETAILS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllSitesDetails(
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "direction", required = false) String direction,
            @RequestParam(value = "sort", required = false) String[] sort,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "customerId", required = false) Long custAccountId,
            @RequestParam(value = "siteId", required = false) Long custAcctSiteId,
            @RequestParam(value = "siteName", required = false) String siteName) {
        
        LOG.info("get all sites details");
        return customerServiceImpl.getAllSitesDetails(page, size, search, custAccountId, custAcctSiteId, siteName);
    }
    
    @RequestMapping(value = GET_ALL_CONTACTS_DETAILS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllContactsDetails(
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "direction", required = false) String direction,
            @RequestParam(value = "sort", required = false) String[] sort,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "customerId", required = false) Long custAccountId,
            @RequestParam(value = "siteId", required = false) Long custAcctSiteId,
            @RequestParam(value = "contactId", required = false) Long contactId,
            @RequestParam(value = "roleType", required = false) String roleType) {
        
        LOG.info("get all contacts details");
        return customerServiceImpl.getAllContactsDetails(page, size, search, custAccountId, custAcctSiteId, contactId, roleType);
    }
    
    @PostMapping(value = FETCH_CUSTOMER_NAME, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchCustomerIdAndSave(@RequestBody OrderRequestDTO orderRequestDTO) {
        LOG.info("Fetching customer Name and saving to the Id in respective column in OrderHeader");
        return customerServiceImpl.fetchCustomerIdAndSave(orderRequestDTO);
    }
    
    @PutMapping(value = UPDATE_CUSTOMER, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCustomerDetails(@RequestBody CustomerUpdateDTO customerUpdateDTO) {
        LOG.info("Updating customer details");
        return customerServiceImpl.updateCustomerDetails(customerUpdateDTO);
    }
}