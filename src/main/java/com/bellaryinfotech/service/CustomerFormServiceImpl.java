package com.bellaryinfotech.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bellaryinfotech.DTO.CustomerDTO;
import com.bellaryinfotech.model.Customer;
import com.bellaryinfotech.repo.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerFormServiceImpl implements CustomerFormService {

    @Autowired
    private CustomerRepository customerRepository;

    // Map Entity to DTO
    private CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setPurchaseOrder(customer.getPurchaseOrder());
        dto.setTelNo(customer.getTelNo());
        dto.setFaxNo(customer.getFaxNo());
        dto.setPoNo(customer.getPoNo());
        dto.setPoDate(customer.getPoDate());
        dto.setType(customer.getType());
        dto.setLdApplicable(customer.getLdApplicable());
        return dto;
    }

    // Map DTO to Entity
    private Customer mapToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setPurchaseOrder(dto.getPurchaseOrder());
        customer.setTelNo(dto.getTelNo());
        customer.setFaxNo(dto.getFaxNo());
        customer.setPoNo(dto.getPoNo());
        customer.setPoDate(dto.getPoDate());
        customer.setType(dto.getType());
        customer.setLdApplicable(dto.getLdApplicable()); // CORRECTED LINE
        return customer;
    }


    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = mapToEntity(customerDTO);
        Customer saved = customerRepository.save(customer);
        return mapToDTO(saved);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
