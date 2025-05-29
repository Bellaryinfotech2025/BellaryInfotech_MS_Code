package com.bellaryinfotech.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.bellaryinfotech.DTO.ServiceCodeEntryDto;

public interface ServiceCodeEntryService {
    
    List<ServiceCodeEntryDto> getAllServiceCodes();
    Optional<ServiceCodeEntryDto> getServiceCodeByCode(String code);
    List<String> getAllCodes();
    List<String> getAllNames();
    List<BigDecimal> getAllWeights();
    List<BigDecimal> getAllAreas();
    ServiceCodeEntryDto createServiceCode(ServiceCodeEntryDto serviceCodeDto);
    ServiceCodeEntryDto updateServiceCode(String code, ServiceCodeEntryDto serviceCodeDto);
    void deleteServiceCode(String code);
}