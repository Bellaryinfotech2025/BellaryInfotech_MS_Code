package com.bellaryinfotech.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bellaryinfotech.DTO.WorkOrderOutFabricationDto;
import com.bellaryinfotech.model.WorkOrderOutFabrication;
import com.bellaryinfotech.repo.WorkOrderOutFabricationRepository;
import com.bellaryinfotech.service.WorkOrderOutFabricationService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkOrderOutFabricationServiceImpl implements WorkOrderOutFabricationService {

    @Autowired
    private WorkOrderOutFabricationRepository repository;

    // ============ EXISTING CRUD OPERATIONS ============
    
    @Override
    public List<WorkOrderOutFabricationDto> getAllFabrications() {
        List<WorkOrderOutFabrication> fabrications = repository.findAll();
        return fabrications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<WorkOrderOutFabricationDto> getFabricationById(Long id) {
        Optional<WorkOrderOutFabrication> fabrication = repository.findById(id);
        return fabrication.map(this::convertToDto);
    }

    @Override
    public List<WorkOrderOutFabricationDto> createBulkFabrications(List<WorkOrderOutFabricationDto> fabricationDtos) {
        List<WorkOrderOutFabrication> fabrications = fabricationDtos.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        
        // Set audit fields
        Timestamp now = Timestamp.from(Instant.now());
        fabrications.forEach(fabrication -> {
            fabrication.setCreationDate(now);
            fabrication.setLastUpdateDate(now);
            fabrication.setCreatedBy("system");
            fabrication.setLastUpdatedBy("system");
            fabrication.setTenantId(1);
        });
        
        List<WorkOrderOutFabrication> savedFabrications = repository.saveAll(fabrications);
        return savedFabrications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public WorkOrderOutFabricationDto updateFabrication(Long id, WorkOrderOutFabricationDto fabricationDto) {
        Optional<WorkOrderOutFabrication> existingFabrication = repository.findById(id);
        if (existingFabrication.isPresent()) {
            WorkOrderOutFabrication fabrication = existingFabrication.get();
            updateEntityFromDto(fabrication, fabricationDto);
            fabrication.setLastUpdateDate(Timestamp.from(Instant.now()));
            fabrication.setLastUpdatedBy("system");
            fabrication.setEditableEnable("edited"); // Mark as edited
            
            WorkOrderOutFabrication updatedFabrication = repository.save(fabrication);
            return convertToDto(updatedFabrication);
        }
        throw new RuntimeException("Work Order Out Fabrication not found with id: " + id);
    }

    @Override
    public void deleteFabrication(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteFabricationsByWorkOrder(String workOrder) {
        repository.deleteByWorkOrder(workOrder);
    }

    // ============ EXISTING SEARCH OPERATIONS ============
    
    @Override
    public List<WorkOrderOutFabricationDto> searchByWorkOrder(String workOrder) {
        List<WorkOrderOutFabrication> fabrications = repository.findByWorkOrderOrderByIdAsc(workOrder);
        return fabrications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkOrderOutFabricationDto> searchByMultipleCriteria(String workOrder, String clientName, String drawingNo, String markNo, String buildingName) {
        List<WorkOrderOutFabrication> fabrications = repository.findByMultipleCriteria(workOrder, clientName, drawingNo, markNo, buildingName);
        return fabrications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // ============ EXISTING RA NO OPERATIONS ============
    
    @Override
    public Map<String, String> getRaNosByWorkOrder(String workOrder) {
        Map<String, String> raNumbers = new HashMap<>();
        
        // Get RA NO
        Optional<WorkOrderOutFabrication> raNoEntry = repository.findRaNoByWorkOrder(workOrder);
        raNumbers.put("raNo", raNoEntry.map(WorkOrderOutFabrication::getRaNo).orElse("Not assigned yet"));
        
        // Get Sub Agency RA NO
        Optional<WorkOrderOutFabrication> subAgencyRaNoEntry = repository.findSubAgencyRaNoByWorkOrder(workOrder);
        raNumbers.put("subAgencyRaNo", subAgencyRaNoEntry.map(WorkOrderOutFabrication::getSubAgencyRaNo).orElse("Not assigned yet"));
        
        return raNumbers;
    }

    @Override
    public void updateRaNoForWorkOrder(String workOrder, String raNo, String subAgencyRaNo) {
        List<WorkOrderOutFabrication> fabrications = repository.findByWorkOrderOrderByIdAsc(workOrder);
        
        Timestamp now = Timestamp.from(Instant.now());
        fabrications.forEach(fabrication -> {
            if (raNo != null && !raNo.trim().isEmpty()) {
                fabrication.setRaNo(raNo.trim());
            }
            if (subAgencyRaNo != null && !subAgencyRaNo.trim().isEmpty()) {
                fabrication.setSubAgencyRaNo(subAgencyRaNo.trim());
            }
            fabrication.setLastUpdateDate(now);
            fabrication.setLastUpdatedBy("system");
        });
        
        repository.saveAll(fabrications);
    }

    // ============ EXISTING UTILITY OPERATIONS ============
    
    @Override
    public boolean existsByWorkOrderAndDrawingAndMark(String workOrder, String drawingNo, String markNo) {
        return repository.existsByWorkOrderAndDrawingNoAndMarkNo(workOrder, drawingNo, markNo);
    }

    // ============ NEW: WORK ORDER OUT RESULT OPERATIONS ============
    
    @Override
    public List<String> getDistinctClientNames() {
        return repository.findDistinctClientNames();
    }

    @Override
    public List<String> getDistinctWorkOrdersByClientName(String clientName) {
        return repository.findDistinctWorkOrdersByClientName(clientName);
    }

    @Override
    public List<String> getDistinctServiceDescriptionsByClientAndWorkOrder(String clientName, String workOrder) {
        return repository.findDistinctServiceDescriptionsByClientAndWorkOrder(clientName, workOrder);
    }

    @Override
    public List<String> getDistinctRaNosByClientWorkOrderAndService(String clientName, String workOrder, String serviceDescription) {
        return repository.findDistinctRaNosByClientWorkOrderAndService(clientName, workOrder, serviceDescription);
    }

    @Override
    public List<WorkOrderOutFabricationDto> searchByClientWorkOrderServiceAndRaNo(String clientName, String workOrder, String serviceDescription, String raNo) {
        List<WorkOrderOutFabrication> fabrications = repository.findByClientWorkOrderServiceAndRaNo(clientName, workOrder, serviceDescription, raNo);
        return fabrications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // ============ NEW: SUB AGENCY NAME OPERATIONS ============
    
    @Override
    public List<String> getDistinctSubAgencyNamesByClientWorkOrderAndService(String clientName, String workOrder, String serviceDescription) {
        return repository.findDistinctSubAgencyNamesByClientWorkOrderAndService(clientName, workOrder, serviceDescription);
    }

    @Override
    public boolean isServiceDescriptionFromWorkOrderOutFabrication(String clientName, String workOrder, String serviceDescription) {
        Long count = repository.countByClientNameAndWorkOrderAndServiceDescription(clientName, workOrder, serviceDescription);
        return count != null && count > 0;
    }

    @Override
    public List<String> getDistinctRaNosByAllFiltersWithSubAgency(String clientName, String workOrder, String serviceDescription, String subAgencyName) {
        return repository.findDistinctRaNosByAllFiltersWithSubAgency(clientName, workOrder, serviceDescription, subAgencyName);
    }

    @Override
    public List<WorkOrderOutFabricationDto> searchByAllFiltersWithSubAgency(String clientName, String workOrder, String serviceDescription, String raNo, String subAgencyName) {
        List<WorkOrderOutFabrication> fabrications = repository.searchByAllFiltersWithSubAgency(clientName, workOrder, serviceDescription, raNo, subAgencyName);
        return fabrications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkOrderOutFabricationDto> searchByAllFiltersWithoutSubAgency(String clientName, String workOrder, String serviceDescription, String raNo) {
        List<WorkOrderOutFabrication> fabrications = repository.searchByAllFiltersWithoutSubAgency(clientName, workOrder, serviceDescription, raNo);
        return fabrications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // ============ CONVERSION METHODS ============
    
    private WorkOrderOutFabricationDto convertToDto(WorkOrderOutFabrication entity) {
        WorkOrderOutFabricationDto dto = new WorkOrderOutFabricationDto();
        dto.setId(entity.getId());
        dto.setOrderId(entity.getOrderId());
        dto.setClientName(entity.getClientName());
        dto.setWorkOrder(entity.getWorkOrder());
        dto.setServiceDescription(entity.getServiceDescription());
        dto.setUom(entity.getUom());
        dto.setDataModule(entity.getDataModule());
        dto.setDrawingNo(entity.getDrawingNo());
        dto.setBuildingName(entity.getBuildingName());
        dto.setMarkNo(entity.getMarkNo());
        dto.setSubAgencyName(entity.getSubAgencyName());
        dto.setSubAgencyWorkOrderName(entity.getSubAgencyWorkOrderName());
        dto.setRaNo(entity.getRaNo());
        dto.setSubAgencyRaNo(entity.getSubAgencyRaNo());
        dto.setOriginalEntryId(entity.getOriginalEntryId());
        dto.setItemNo(entity.getItemNo());
        dto.setSectionCode(entity.getSectionCode());
        dto.setSectionName(entity.getSectionName());
        dto.setWidth(entity.getWidth());
        dto.setLength(entity.getLength());
        dto.setItemQty(entity.getItemQty());
        dto.setItemWeight(entity.getItemWeight());
        dto.setTotalItemWeight(entity.getTotalItemWeight());
        dto.setCuttingStage(entity.getCuttingStage());
        dto.setFitUpStage(entity.getFitUpStage());
        dto.setWeldingStage(entity.getWeldingStage());
        dto.setFinishingStage(entity.getFinishingStage());
        dto.setRemarks(entity.getRemarks());
        dto.setStatus(entity.getStatus());
        dto.setEditableEnable(entity.getEditableEnable());
        dto.setTenantId(entity.getTenantId());
        dto.setCreationDate(entity.getCreationDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setLastUpdateDate(entity.getLastUpdateDate());
        dto.setLastUpdatedBy(entity.getLastUpdatedBy());
        return dto;
    }

    private WorkOrderOutFabrication convertToEntity(WorkOrderOutFabricationDto dto) {
        WorkOrderOutFabrication entity = new WorkOrderOutFabrication();
        entity.setOrderId(dto.getOrderId());
        entity.setClientName(dto.getClientName());
        entity.setWorkOrder(dto.getWorkOrder());
        entity.setServiceDescription(dto.getServiceDescription());
        entity.setUom(dto.getUom());
        entity.setDataModule(dto.getDataModule());
        entity.setDrawingNo(dto.getDrawingNo());
        entity.setBuildingName(dto.getBuildingName());
        entity.setMarkNo(dto.getMarkNo());
        entity.setSubAgencyName(dto.getSubAgencyName());
        entity.setSubAgencyWorkOrderName(dto.getSubAgencyWorkOrderName());
        entity.setRaNo(dto.getRaNo());
        entity.setSubAgencyRaNo(dto.getSubAgencyRaNo());
        entity.setOriginalEntryId(dto.getOriginalEntryId());
        entity.setItemNo(dto.getItemNo());
        entity.setSectionCode(dto.getSectionCode());
        entity.setSectionName(dto.getSectionName());
        entity.setWidth(dto.getWidth());
        entity.setLength(dto.getLength());
        entity.setItemQty(dto.getItemQty());
        entity.setItemWeight(dto.getItemWeight());
        entity.setTotalItemWeight(dto.getTotalItemWeight());
        entity.setCuttingStage(dto.getCuttingStage());
        entity.setFitUpStage(dto.getFitUpStage());
        entity.setWeldingStage(dto.getWeldingStage());
        entity.setFinishingStage(dto.getFinishingStage());
        entity.setRemarks(dto.getRemarks());
        entity.setStatus(dto.getStatus());
        entity.setEditableEnable(dto.getEditableEnable());
        entity.setTenantId(dto.getTenantId());
        entity.setCreationDate(dto.getCreationDate());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setLastUpdateDate(dto.getLastUpdateDate());
        entity.setLastUpdatedBy(dto.getLastUpdatedBy());
        return entity;
    }

    private void updateEntityFromDto(WorkOrderOutFabrication entity, WorkOrderOutFabricationDto dto) {
        entity.setOrderId(dto.getOrderId());
        entity.setClientName(dto.getClientName());
        entity.setWorkOrder(dto.getWorkOrder());
        entity.setServiceDescription(dto.getServiceDescription());
        entity.setUom(dto.getUom());
        entity.setDataModule(dto.getDataModule());
        entity.setDrawingNo(dto.getDrawingNo());
        entity.setBuildingName(dto.getBuildingName());
        entity.setMarkNo(dto.getMarkNo());
        entity.setSubAgencyName(dto.getSubAgencyName());
        entity.setSubAgencyWorkOrderName(dto.getSubAgencyWorkOrderName());
        entity.setRaNo(dto.getRaNo());
        entity.setSubAgencyRaNo(dto.getSubAgencyRaNo());
        entity.setOriginalEntryId(dto.getOriginalEntryId());
        entity.setItemNo(dto.getItemNo());
        entity.setSectionCode(dto.getSectionCode());
        entity.setSectionName(dto.getSectionName());
        entity.setWidth(dto.getWidth());
        entity.setLength(dto.getLength());
        entity.setItemQty(dto.getItemQty());
        entity.setItemWeight(dto.getItemWeight());
        entity.setTotalItemWeight(dto.getTotalItemWeight());
        entity.setCuttingStage(dto.getCuttingStage());
        entity.setFitUpStage(dto.getFitUpStage());
        entity.setWeldingStage(dto.getWeldingStage());
        entity.setFinishingStage(dto.getFinishingStage());
        entity.setRemarks(dto.getRemarks());
        entity.setStatus(dto.getStatus());
        // Don't update editableEnable here, it's set in the service method
    }
}
