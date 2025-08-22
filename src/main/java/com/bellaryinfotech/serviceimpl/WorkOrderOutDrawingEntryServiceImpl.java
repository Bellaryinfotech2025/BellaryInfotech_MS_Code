package com.bellaryinfotech.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bellaryinfotech.DTO.WorkOrderOutDrawingEntryDto;
import com.bellaryinfotech.model.WorkOrderOutDrawingEntry;
import com.bellaryinfotech.repo.WorkOrderOutDrawingEntryRepository;
import com.bellaryinfotech.service.WorkOrderOutDrawingEntryService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkOrderOutDrawingEntryServiceImpl implements WorkOrderOutDrawingEntryService {

    @Autowired
    private WorkOrderOutDrawingEntryRepository repository;

    @Override
    public List<WorkOrderOutDrawingEntryDto> getAllEntries() {
        List<WorkOrderOutDrawingEntry> entries = repository.findAll();
        return entries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<WorkOrderOutDrawingEntryDto> getEntryById(Long id) {
        Optional<WorkOrderOutDrawingEntry> entry = repository.findById(id);
        return entry.map(this::convertToDto);
    }

    @Override
    public List<WorkOrderOutDrawingEntryDto> createBulkEntries(List<WorkOrderOutDrawingEntryDto> entryDtos) {
        List<WorkOrderOutDrawingEntry> entries = entryDtos.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        
        // Set audit fields
        Timestamp now = Timestamp.from(Instant.now());
        entries.forEach(entry -> {
            entry.setCreationDate(now);
            entry.setLastUpdateDate(now);
            entry.setCreatedBy("system");
            entry.setLastUpdatedBy("system");
            entry.setTenantId(1);
            entry.setStatus("Work Order Out Entry"); // Set status for each entry
        });
        
        List<WorkOrderOutDrawingEntry> savedEntries = repository.saveAll(entries);
        return savedEntries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public WorkOrderOutDrawingEntryDto updateEntry(Long id, WorkOrderOutDrawingEntryDto entryDto) {
        Optional<WorkOrderOutDrawingEntry> existingEntry = repository.findById(id);
        if (existingEntry.isPresent()) {
            WorkOrderOutDrawingEntry entry = existingEntry.get();
            updateEntityFromDto(entry, entryDto);
            entry.setLastUpdateDate(Timestamp.from(Instant.now()));
            entry.setLastUpdatedBy("system");
            
            WorkOrderOutDrawingEntry updatedEntry = repository.save(entry);
            return convertToDto(updatedEntry);
        }
        throw new RuntimeException("Work Order Out Drawing Entry not found with id: " + id);
    }

    @Override
    public void deleteEntry(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByDrawingAndMark(String drawingNo, String markNo) {
        repository.deleteByDrawingNoAndMarkNo(drawingNo, markNo);
    }

    @Override
    public List<WorkOrderOutDrawingEntryDto> searchByWorkOrder(String workOrder) {
        List<WorkOrderOutDrawingEntry> entries = repository.findByWorkOrderOrderByIdAsc(workOrder);
        return entries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkOrderOutDrawingEntryDto> searchByWorkOrderAndPlantLocation(String workOrder, String plantLocation) {
        List<WorkOrderOutDrawingEntry> entries = repository.findByWorkOrderAndPlantLocationOrderByIdAsc(workOrder, plantLocation);
        return entries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkOrderOutDrawingEntryDto> searchByDrawingNo(String drawingNo) {
        List<WorkOrderOutDrawingEntry> entries = repository.findByDrawingNoOrderByIdAsc(drawingNo);
        return entries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkOrderOutDrawingEntryDto> searchByMarkNo(String markNo) {
        List<WorkOrderOutDrawingEntry> entries = repository.findByMarkNoOrderByIdAsc(markNo);
        return entries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkOrderOutDrawingEntryDto> searchByDrawingAndMark(String drawingNo, String markNo) {
        List<WorkOrderOutDrawingEntry> entries = repository.findByDrawingNoAndMarkNoOrderByIdAsc(drawingNo, markNo);
        return entries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkOrderOutDrawingEntryDto> searchByOrderId(Long orderId) {
        List<WorkOrderOutDrawingEntry> entries = repository.findByOrderIdOrderByIdAsc(orderId);
        return entries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkOrderOutDrawingEntryDto> searchByMultipleCriteria(String workOrder, String plantLocation, String drawingNo, String markNo) {
        List<WorkOrderOutDrawingEntry> entries = repository.findByMultipleCriteria(workOrder, plantLocation, drawingNo, markNo);
        return entries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Work Order Out Fabrication dropdown operations
    @Override
    public List<String> getDistinctClientNames() {
        return repository.findDistinctClientNames();
    }

    @Override
    public List<String> getDistinctWorkOrdersByClient(String clientName) {
        return repository.findDistinctWorkOrdersByClient(clientName);
    }

    @Override
    public List<String> getDistinctServiceDescByWorkOrder(String workOrder) {
        return repository.findDistinctServiceDescByWorkOrder(workOrder);
    }

    @Override
    public List<String> getDistinctUOMByWorkOrderAndService(String workOrder, String serviceDescription) {
        return repository.findDistinctUOMByWorkOrderAndService(workOrder, serviceDescription);
    }

    @Override
    public List<String> getDistinctDataModulesByWorkOrder(String workOrder) {
        return repository.findDistinctDataModulesByWorkOrder(workOrder);
    }

    @Override
    public Map<String, String> getSubAgencyDetailsByWorkOrder(String workOrder) {
        List<Object[]> results = repository.findSubAgencyDetailsByWorkOrder(workOrder);
        Map<String, String> subAgencyDetails = new HashMap<>();
        
        if (!results.isEmpty()) {
            Object[] result = results.get(0);
            subAgencyDetails.put("subAgencyName", result[0] != null ? result[0].toString() : "");
            subAgencyDetails.put("subAgencyWorkOrderName", result[1] != null ? result[1].toString() : "");
        } else {
            subAgencyDetails.put("subAgencyName", "");
            subAgencyDetails.put("subAgencyWorkOrderName", "");
        }
        
        return subAgencyDetails;
    }

    @Override
    public List<String> getDistinctWorkOrders() {
        return repository.findDistinctWorkOrders();
    }

    @Override
    public List<String> getDistinctPlantLocationsByWorkOrder(String workOrder) {
        return repository.findDistinctPlantLocationsByWorkOrder(workOrder);
    }

    @Override
    public List<String> getDistinctDrawingNumbersByWorkOrderAndPlantLocation(String workOrder, String plantLocation) {
        return repository.findDistinctDrawingNumbersByWorkOrderAndPlantLocation(workOrder, plantLocation);
    }

    @Override
    public List<String> getDistinctMarkNumbersByWorkOrderAndPlantLocation(String workOrder, String plantLocation) {
        return repository.findDistinctMarkNumbersByWorkOrderAndPlantLocation(workOrder, plantLocation);
    }

    @Override
    public List<WorkOrderOutDrawingEntryDto> getEntriesForEditingByMarkNo(String markNo) {
        List<WorkOrderOutDrawingEntry> entries = repository.findEntriesForEditingByMarkNo(markNo);
        return entries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<WorkOrderOutDrawingEntryDto> getDrawingEntryByMarkNo(String markNo) {
        Optional<WorkOrderOutDrawingEntry> entry = repository.findDrawingEntryByMarkNo(markNo);
        return entry.map(this::convertToDto);
    }

    @Override
    public boolean existsByDrawingAndMark(String drawingNo, String markNo) {
        return repository.existsByDrawingNoAndMarkNo(drawingNo, markNo);
    }

    // Enhanced conversion methods with additional fields
    private WorkOrderOutDrawingEntryDto convertToDto(WorkOrderOutDrawingEntry entity) {
        WorkOrderOutDrawingEntryDto dto = new WorkOrderOutDrawingEntryDto();
        dto.setId(entity.getId());
        dto.setOrderId(entity.getOrderId());
        dto.setWorkOrder(entity.getWorkOrder());
        dto.setClientName(entity.getClientName());
        dto.setSubAgencyName(entity.getSubAgencyName());
        dto.setSubAgencyWorkOrderName(entity.getSubAgencyWorkOrderName());
        dto.setPlantLocation(entity.getPlantLocation());
        dto.setDepartment(entity.getDepartment());
        dto.setWorkLocation(entity.getWorkLocation());
        dto.setLineNumber(entity.getLineNumber());
        dto.setDrawingNo(entity.getDrawingNo());
        dto.setDrawingReceivedDate(entity.getDrawingReceivedDate());
        dto.setTargetDate(entity.getTargetDate());
        dto.setMarkNo(entity.getMarkNo());
        dto.setMarkWeight(entity.getMarkWeight());
        dto.setMarkQty(entity.getMarkQty());
        dto.setTotalMarkWeight(entity.getTotalMarkWeight());
        
        // NEW: Set additional fields
        dto.setServiceDescription(entity.getServiceDescription());
        dto.setUom(entity.getUom());
        dto.setDataModule(entity.getDataModule());
        
        dto.setItemNo(entity.getItemNo());
        dto.setSectionCode(entity.getSectionCode());
        dto.setSectionName(entity.getSectionName());
        dto.setWidth(entity.getWidth());
        dto.setLength(entity.getLength());
        dto.setSecWeight(entity.getSecWeight());
        dto.setItemWeight(entity.getItemWeight());
        dto.setItemQty(entity.getItemQty());
        dto.setTotalItemWeight(entity.getTotalItemWeight());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    private WorkOrderOutDrawingEntry convertToEntity(WorkOrderOutDrawingEntryDto dto) {
        WorkOrderOutDrawingEntry entity = new WorkOrderOutDrawingEntry();
        entity.setOrderId(dto.getOrderId());
        entity.setWorkOrder(dto.getWorkOrder());
        entity.setClientName(dto.getClientName());
        entity.setSubAgencyName(dto.getSubAgencyName());
        entity.setSubAgencyWorkOrderName(dto.getSubAgencyWorkOrderName());
        entity.setPlantLocation(dto.getPlantLocation());
        entity.setDepartment(dto.getDepartment());
        entity.setWorkLocation(dto.getWorkLocation());
        entity.setLineNumber(dto.getLineNumber());
        entity.setDrawingNo(dto.getDrawingNo());
        entity.setDrawingReceivedDate(dto.getDrawingReceivedDate());
        entity.setTargetDate(dto.getTargetDate());
        entity.setMarkNo(dto.getMarkNo());
        entity.setMarkWeight(dto.getMarkWeight());
        entity.setMarkQty(dto.getMarkQty());
        entity.setTotalMarkWeight(dto.getTotalMarkWeight());
        
        // NEW: Set additional fields
        entity.setServiceDescription(dto.getServiceDescription());
        entity.setUom(dto.getUom());
        entity.setDataModule(dto.getDataModule());
        
        entity.setItemNo(dto.getItemNo());
        entity.setSectionCode(dto.getSectionCode());
        entity.setSectionName(dto.getSectionName());
        entity.setWidth(dto.getWidth());
        entity.setLength(dto.getLength());
        entity.setSecWeight(dto.getSecWeight());
        entity.setItemWeight(dto.getItemWeight());
        entity.setItemQty(dto.getItemQty());
        entity.setTotalItemWeight(dto.getTotalItemWeight());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    private void updateEntityFromDto(WorkOrderOutDrawingEntry entity, WorkOrderOutDrawingEntryDto dto) {
        entity.setOrderId(dto.getOrderId());
        entity.setWorkOrder(dto.getWorkOrder());
        entity.setClientName(dto.getClientName());
        entity.setSubAgencyName(dto.getSubAgencyName());
        entity.setSubAgencyWorkOrderName(dto.getSubAgencyWorkOrderName());
        entity.setPlantLocation(dto.getPlantLocation());
        entity.setDepartment(dto.getDepartment());
        entity.setWorkLocation(dto.getWorkLocation());
        entity.setLineNumber(dto.getLineNumber());
        entity.setDrawingNo(dto.getDrawingNo());
        entity.setDrawingReceivedDate(dto.getDrawingReceivedDate());
        entity.setTargetDate(dto.getTargetDate());
        entity.setMarkNo(dto.getMarkNo());
        entity.setMarkWeight(dto.getMarkWeight());
        entity.setMarkQty(dto.getMarkQty());
        entity.setTotalMarkWeight(dto.getTotalMarkWeight());
        
        // NEW: Set additional fields
        entity.setServiceDescription(dto.getServiceDescription());
        entity.setUom(dto.getUom());
        entity.setDataModule(dto.getDataModule());
        
        entity.setItemNo(dto.getItemNo());
        entity.setSectionCode(dto.getSectionCode());
        entity.setSectionName(dto.getSectionName());
        entity.setWidth(dto.getWidth());
        entity.setLength(dto.getLength());
        entity.setSecWeight(dto.getSecWeight());
        entity.setItemWeight(dto.getItemWeight());
        entity.setItemQty(dto.getItemQty());
        entity.setTotalItemWeight(dto.getTotalItemWeight());
        entity.setStatus(dto.getStatus());
    }
}
