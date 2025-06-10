package com.bellaryinfotech.service;
import java.util.List;

import com.bellaryinfotech.DTO.CustomerDTO;

public interface CustomerFormService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);
    void deleteCustomer(Long id);
}

