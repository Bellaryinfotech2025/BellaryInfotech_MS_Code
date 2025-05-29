package com.bellaryinfotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bellaryinfotech.DTO.ServiceCodeEntryDto;
import com.bellaryinfotech.model.ServiceCodeEntry;
import com.bellaryinfotech.repo.ServiceCodeEntryRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceCodeEntryServiceImpl implements ServiceCodeEntryService {

    @Autowired
    private ServiceCodeEntryRepository serviceCodeRepository;
    
    @Override
    public List<ServiceCodeEntryDto> getAllServiceCodes() {
        List<ServiceCodeEntry> serviceCodes = serviceCodeRepository.findAll();
        return serviceCodes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<ServiceCodeEntryDto> getServiceCodeByCode(String code) {
        Optional<ServiceCodeEntry> serviceCode = serviceCodeRepository.findByCode(code);
        return serviceCode.map(this::convertToDto);
    }
    
    @Override
    public List<String> getAllCodes() {
        return serviceCodeRepository.findAll().stream()
                .map(ServiceCodeEntry::getCode)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<String> getAllNames() {
        return serviceCodeRepository.findAll().stream()
                .map(ServiceCodeEntry::getName)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<BigDecimal> getAllWeights() {
        return serviceCodeRepository.findAll().stream()
                .map(ServiceCodeEntry::getWgt)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<BigDecimal> getAllAreas() {
        return serviceCodeRepository.findAll().stream()
                .map(ServiceCodeEntry::getArea)
                .collect(Collectors.toList());
    }
    
    @Override
    public ServiceCodeEntryDto createServiceCode(ServiceCodeEntryDto serviceCodeDto) {
        ServiceCodeEntry serviceCode = convertToEntity(serviceCodeDto);
        ServiceCodeEntry savedServiceCode = serviceCodeRepository.save(serviceCode);
        return convertToDto(savedServiceCode);
    }
    
    @Override
    public ServiceCodeEntryDto updateServiceCode(String code, ServiceCodeEntryDto serviceCodeDto) {
        Optional<ServiceCodeEntry> existingServiceCode = serviceCodeRepository.findById(code);
        if (existingServiceCode.isPresent()) {
            ServiceCodeEntry serviceCode = existingServiceCode.get();
            updateEntityFromDto(serviceCode, serviceCodeDto);
            
            ServiceCodeEntry updatedServiceCode = serviceCodeRepository.save(serviceCode);
            return convertToDto(updatedServiceCode);
        }
        throw new RuntimeException("Service code not found with code: " + code);
    }
    
    @Override
    public void deleteServiceCode(String code) {
        serviceCodeRepository.deleteById(code);
    }
    
    private ServiceCodeEntryDto convertToDto(ServiceCodeEntry entity) {
        ServiceCodeEntryDto dto = new ServiceCodeEntryDto();
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setWgt(entity.getWgt());
        dto.setArea(entity.getArea());
        return dto;
    }
    
    private ServiceCodeEntry convertToEntity(ServiceCodeEntryDto dto) {
        ServiceCodeEntry entity = new ServiceCodeEntry();
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setWgt(dto.getWgt());
        entity.setArea(dto.getArea());
        return entity;
    }
    
    private void updateEntityFromDto(ServiceCodeEntry entity, ServiceCodeEntryDto dto) {
        entity.setName(dto.getName());
        entity.setWgt(dto.getWgt());
        entity.setArea(dto.getArea());
    }
}