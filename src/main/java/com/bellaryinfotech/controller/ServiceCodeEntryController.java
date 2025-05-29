package com.bellaryinfotech.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bellaryinfotech.DTO.ServiceCodeEntryDto;
import com.bellaryinfotech.service.ServiceCodeEntryService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class ServiceCodeEntryController {

    @Autowired
    private ServiceCodeEntryService serviceCodeService;
    
    private static final Logger LOG = LoggerFactory.getLogger(ServiceCodeEntryController.class);
    
    @GetMapping(value = "/service_code_entry/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllServiceCodes() {
        LOG.info("Fetching all service codes");
        List<ServiceCodeEntryDto> serviceCodes = serviceCodeService.getAllServiceCodes();
        return ResponseEntity.ok(serviceCodes);
    }
    
    @GetMapping(value = "/service_code_entry/codes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCodes() {
        LOG.info("Fetching all service code values");
        List<String> codes = serviceCodeService.getAllCodes();
        return ResponseEntity.ok(codes);
    }
    
    @GetMapping(value = "/service_code_entry/names", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllNames() {
        LOG.info("Fetching all service name values");
        List<String> names = serviceCodeService.getAllNames();
        return ResponseEntity.ok(names);
    }
    
    @GetMapping(value = "/service_code_entry/wgts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllWeights() {
        LOG.info("Fetching all service weight values");
        List<BigDecimal> weights = serviceCodeService.getAllWeights();
        return ResponseEntity.ok(weights);
    }
    
    @GetMapping(value = "/service_code_entry/areas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllAreas() {
        LOG.info("Fetching all service area values");
        List<BigDecimal> areas = serviceCodeService.getAllAreas();
        return ResponseEntity.ok(areas);
    }
    
    @GetMapping(value = "/service_code_entry/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getServiceCodeByCode(@PathVariable String code) {
        LOG.info("Fetching service code details for code: {}", code);
        Optional<ServiceCodeEntryDto> serviceCode = serviceCodeService.getServiceCodeByCode(code);
        return serviceCode.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping(value = "/service_code_entry", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createServiceCode(@RequestBody ServiceCodeEntryDto serviceCodeDto) {
        LOG.info("Creating a new service code");
        try {
            ServiceCodeEntryDto createdServiceCode = serviceCodeService.createServiceCode(serviceCodeDto);
            return ResponseEntity.status(201).body(createdServiceCode);
        } catch (Exception e) {
            LOG.error("Error creating service code", e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping(value = "/service_code_entry/{code}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateServiceCode(@PathVariable String code, @RequestBody ServiceCodeEntryDto serviceCodeDto) {
        LOG.info("Updating service code with code: {}", code);
        try {
            ServiceCodeEntryDto updatedServiceCode = serviceCodeService.updateServiceCode(code, serviceCodeDto);
            return ResponseEntity.ok(updatedServiceCode);
        } catch (RuntimeException e) {
            LOG.error("Service code not found for update: {}", code, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            LOG.error("Error updating service code", e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping(value = "/service_code_entry/{code}")
    public ResponseEntity<?> deleteServiceCode(@PathVariable String code) {
        LOG.info("Deleting service code with code: {}", code);
        try {
            serviceCodeService.deleteServiceCode(code);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error deleting service code", e);
            return ResponseEntity.notFound().build();
        }
    }
}