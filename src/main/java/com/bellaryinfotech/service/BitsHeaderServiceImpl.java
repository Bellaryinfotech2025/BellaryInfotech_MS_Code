package com.bellaryinfotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bellaryinfotech.DTO.BitsHeaderDto;
import com.bellaryinfotech.model.BitsHeaderAll;
import com.bellaryinfotech.repo.BitsHeaderRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BitsHeaderServiceImpl implements BitsHeaderService {

   @Autowired
   private BitsHeaderRepository headerRepository;

   public List<BitsHeaderDto> getAllHeaders() {
       List<BitsHeaderAll> headers = headerRepository.findAll();
       return headers.stream()
               .map(this::convertToDto)
               .collect(Collectors.toList());
   }

   public Optional<BitsHeaderDto> getHeaderById(Long id) {
       Optional<BitsHeaderAll> header = headerRepository.findById(id);
       return header.map(this::convertToDto);
   }

   public BitsHeaderDto createHeader(BitsHeaderDto headerDto) {
       BitsHeaderAll header = convertToEntity(headerDto);
       header.setCreationDate(Timestamp.from(Instant.now()));
       header.setLastUpdateDate(Timestamp.from(Instant.now()));
       header.setCreatedBy(1L); // Default user
       header.setLastUpdatedBy(1L); // Default user
       header.setTenantId(1); // Default tenant
       
       BitsHeaderAll savedHeader = headerRepository.save(header);
       return convertToDto(savedHeader);
   }

   public BitsHeaderDto updateHeader(Long id, BitsHeaderDto headerDto) {
       Optional<BitsHeaderAll> existingHeader = headerRepository.findById(id);
       if (existingHeader.isPresent()) {
           BitsHeaderAll header = existingHeader.get();
           updateEntityFromDto(header, headerDto);
           header.setLastUpdateDate(Timestamp.from(Instant.now()));
           header.setLastUpdatedBy(1L); // Default user
           
           BitsHeaderAll updatedHeader = headerRepository.save(header);
           return convertToDto(updatedHeader);
       }
       throw new RuntimeException("Header not found with id: " + id);
   }

   public void deleteHeader(Long id) {
       headerRepository.deleteById(id);
   }

   public List<BitsHeaderDto> searchByWorkOrder(String workOrder) {
       List<BitsHeaderAll> headers = headerRepository.findByWorkOrderContainingIgnoreCase(workOrder);
       return headers.stream()
               .map(this::convertToDto)
               .collect(Collectors.toList());
   }

   public List<BitsHeaderDto> searchByPlantLocation(String plantLocation) {
       List<BitsHeaderAll> headers = headerRepository.findByPlantLocationContainingIgnoreCase(plantLocation);
       return headers.stream()
               .map(this::convertToDto)
               .collect(Collectors.toList());
   }

   public List<BitsHeaderDto> searchByDepartment(String department) {
       List<BitsHeaderAll> headers = headerRepository.findByDepartmentContainingIgnoreCase(department);
       return headers.stream()
               .map(this::convertToDto)
               .collect(Collectors.toList());
   }

   public List<BitsHeaderDto> searchByWorkLocation(String workLocation) {
       List<BitsHeaderAll> headers = headerRepository.findByWorkLocationContainingIgnoreCase(workLocation);
       return headers.stream()
               .map(this::convertToDto)
               .collect(Collectors.toList());
   }

   // NEW METHODS - ADDED FOR DROPDOWN FUNCTIONALITY
   @Override
   public List<String> getDistinctWorkOrders() {
       return headerRepository.findDistinctWorkOrders();
   }

   @Override
   public List<String> getDistinctPlantLocations() {
       return headerRepository.findDistinctPlantLocations();
   }

   private BitsHeaderDto convertToDto(BitsHeaderAll entity) {
       BitsHeaderDto dto = new BitsHeaderDto();
       dto.setOrderId(entity.getOrderId());
       dto.setWorkOrder(entity.getWorkOrder());
       dto.setPlantLocation(entity.getPlantLocation());
       dto.setDepartment(entity.getDepartment());
       dto.setWorkLocation(entity.getWorkLocation());
       dto.setWorkOrderDate(entity.getWorkOrderDate());
       dto.setCompletionDate(entity.getCompletionDate());
       dto.setLdApplicable(entity.getLdApplicable());
       dto.setScrapAllowanceVisiblePercent(entity.getScrapAllowanceVisiblePercent());
       dto.setScrapAllowanceInvisiblePercent(entity.getScrapAllowanceInvisiblePercent());
       dto.setMaterialIssueType(entity.getMaterialIssueType());
       return dto;
   }

   private BitsHeaderAll convertToEntity(BitsHeaderDto dto) {
       BitsHeaderAll entity = new BitsHeaderAll();
       entity.setWorkOrder(dto.getWorkOrder());
       entity.setPlantLocation(dto.getPlantLocation());
       entity.setDepartment(dto.getDepartment());
       entity.setWorkLocation(dto.getWorkLocation());
       entity.setWorkOrderDate(dto.getWorkOrderDate());
       entity.setCompletionDate(dto.getCompletionDate());
       entity.setLdApplicable(dto.getLdApplicable());
       entity.setScrapAllowanceVisiblePercent(dto.getScrapAllowanceVisiblePercent());
       entity.setScrapAllowanceInvisiblePercent(dto.getScrapAllowanceInvisiblePercent());
       entity.setMaterialIssueType(dto.getMaterialIssueType());
       return entity;
   }

   private void updateEntityFromDto(BitsHeaderAll entity, BitsHeaderDto dto) {
       entity.setWorkOrder(dto.getWorkOrder());
       entity.setPlantLocation(dto.getPlantLocation());
       entity.setDepartment(dto.getDepartment());
       entity.setWorkLocation(dto.getWorkLocation());
       entity.setWorkOrderDate(dto.getWorkOrderDate());
       entity.setCompletionDate(dto.getCompletionDate());
       entity.setLdApplicable(dto.getLdApplicable());
       entity.setScrapAllowanceVisiblePercent(dto.getScrapAllowanceVisiblePercent());
       entity.setScrapAllowanceInvisiblePercent(dto.getScrapAllowanceInvisiblePercent());
       entity.setMaterialIssueType(dto.getMaterialIssueType());
   }
}
