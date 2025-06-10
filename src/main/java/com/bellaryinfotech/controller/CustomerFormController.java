package com.bellaryinfotech.controller;

 
import com.bellaryinfotech.DTO.CustomerDTO;
import com.bellaryinfotech.service.CustomerFormService;
import com.bellaryinfotech.service.CustomerFormServiceImpl;
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
@CrossOrigin // Enable CORS for frontend integration
public class CustomerFormController {

    @Autowired
    private CustomerFormService customerService;

    @Autowired
    private CustomerFormServiceImpl customerServiceImpl;

    private static final Logger LOG = LoggerFactory.getLogger(CustomerFormController.class);

    public final String GET_ALL_CUSTOMERS = "/getallcustomer/details";
    public final String GET_CUSTOMER_BY_ID = "/getcustomer/details";
    public final String SAVE_CUSTOMER = "/savecustomer/details";
    public final String DELETE_CUSTOMER = "/deletecustomer/details";

    @RequestMapping(value = GET_ALL_CUSTOMERS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllCustomers() {
        LOG.info("Fetching all customers");
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @RequestMapping(value = GET_CUSTOMER_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getCustomerById(@RequestParam("id") Long id) {
        LOG.info("Fetching customer by id: {}", id);
        CustomerDTO customer = customerService.getCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = SAVE_CUSTOMER, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        LOG.info("Saving customer details");
        CustomerDTO saved = customerService.saveCustomer(customerDTO);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping(value = DELETE_CUSTOMER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCustomer(@RequestParam("id") Long id) {
        LOG.info("Deleting customer with id: {}", id);
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }
}

