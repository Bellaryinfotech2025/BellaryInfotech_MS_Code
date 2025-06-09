package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.BillingDrawingEntryDto;
import com.bellaryinfotech.model.BillingDrawingEntry;
import com.bellaryinfotech.repo.BillingDrawingEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Transactional
public class BillingDrawingEntryServiceImpl implements BillingDrawingEntryService {

    private static final Logger logger = LoggerFactory.getLogger(BillingDrawingEntryServiceImpl.class);

    @Autowired
    private BillingDrawingEntryRepository billingDrawingEntryRepository;

    // Counter for sequential line IDs
    private static final AtomicLong lineIdCounter = new AtomicLong(1);

    @Override
    public List<BillingDrawingEntryDto> createBillingEntry(BillingDrawingEntryDto billingEntryDto) {
        try {
            logger.info("Creating billing entry with marked quantity: {}", billingEntryDto.getMarkedQty());
            
            // Set default marked quantity if null
            if (billingEntryDto.getMarkedQty() == null) {
                billingEntryDto.setMarkedQty(BigDecimal.ONE);
            }
            
            // Validate input - allow any positive value
            if (billingEntryDto.getMarkedQty().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Marked quantity must be greater than zero");
            }

            List<BillingDrawingEntry> entriesToSave = new ArrayList<>();
            int markedQtyInt = billingEntryDto.getMarkedQty().intValue();

            // Create entries based on marked quantity
            for (int i = 0; i < markedQtyInt; i++) {
                BillingDrawingEntry entry = convertDtoToEntity(billingEntryDto);
                
                // Generate sequential line ID
                entry.setLineId(generateSequentialLineId());
                
                // Set creation metadata
                entry.setCreationDate(LocalDateTime.now());
                entry.setLastUpdatingDate(LocalDateTime.now());
                entry.setStatus("billing");
                
                // Set marked quantity to 1 for each individual entry
                entry.setMarkedQty(BigDecimal.ONE);
                
                entriesToSave.add(entry);
            }

            // Save all entries
            List<BillingDrawingEntry> savedEntries = billingDrawingEntryRepository.saveAll(entriesToSave);
            
            logger.info("Successfully created {} billing entries", savedEntries.size());
            
            // Convert back to DTOs
            return savedEntries.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
                
        } catch (Exception e) {
            logger.error("Error creating billing entry", e);
            throw new RuntimeException("Failed to create billing entry: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BillingDrawingEntryDto> createBillingEntries(List<BillingDrawingEntryDto> billingEntryDtos) {
        try {
            logger.info("Creating {} billing entries with duplicate checking", billingEntryDtos.size());
        
            List<BillingDrawingEntry> allEntriesToSave = new ArrayList<>();
            List<String> skippedDuplicates = new ArrayList<>();
            List<String> successfulEntries = new ArrayList<>();
        
            for (BillingDrawingEntryDto dto : billingEntryDtos) {
                // Validate input
                if (dto.getMarkedQty() == null || dto.getMarkedQty().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException("Marked quantity must be greater than zero for drawing: " + dto.getDrawingNo());
                }

                // Check if this drawing+mark combination already exists in billing table
                boolean exists = billingDrawingEntryRepository.existsByDrawingNoAndMarkNo(dto.getDrawingNo(), dto.getMarkNo());
            
                if (exists) {
                    logger.info("Skipping duplicate entry for Drawing: {}, Mark: {}", dto.getDrawingNo(), dto.getMarkNo());
                    skippedDuplicates.add(dto.getDrawingNo() + " - " + dto.getMarkNo());
                    continue; // Skip this entry as it already exists
                }

                // Create only one entry per mark number (no duplicates based on markedQty)
                BillingDrawingEntry entry = convertDtoToEntity(dto);
            
                // Generate sequential line ID
                entry.setLineId(generateSequentialLineId());
            
                // Set creation metadata
                entry.setCreationDate(LocalDateTime.now());
                entry.setLastUpdatingDate(LocalDateTime.now());
                entry.setStatus("billing");
            
                // Keep the original marked quantity (don't split into multiple entries)
                entry.setMarkedQty(dto.getMarkedQty());
            
                allEntriesToSave.add(entry);
                successfulEntries.add(dto.getDrawingNo() + " - " + dto.getMarkNo());
            }

            // Save all unique entries
            List<BillingDrawingEntry> savedEntries = new ArrayList<>();
            if (!allEntriesToSave.isEmpty()) {
                savedEntries = billingDrawingEntryRepository.saveAll(allEntriesToSave);
            }
        
            logger.info("Successfully created {} billing entries, skipped {} duplicates", 
                       savedEntries.size(), skippedDuplicates.size());
        
            // Convert back to DTOs
            return savedEntries.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
                
        } catch (Exception e) {
            logger.error("Error creating billing entries", e);
            throw new RuntimeException("Failed to create billing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<BillingDrawingEntryDto> getBillingEntryById(String lineId) {
        try {
            logger.info("Fetching billing entry by line ID: {}", lineId);
            Optional<BillingDrawingEntry> entity = billingDrawingEntryRepository.findById(lineId);
            return entity.map(this::convertEntityToDto);
        } catch (Exception e) {
            logger.error("Error fetching billing entry by ID: {}", lineId, e);
            throw new RuntimeException("Failed to fetch billing entry: " + e.getMessage(), e);
        }
    }

    @Override
    public Page<BillingDrawingEntryDto> getAllBillingEntries(Pageable pageable) {
        try {
            logger.info("Fetching all billing entries with pagination");
            Page<BillingDrawingEntry> entities = billingDrawingEntryRepository.findAll(pageable);
            List<BillingDrawingEntryDto> dtos = entities.getContent().stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
            return new PageImpl<>(dtos, pageable, entities.getTotalElements());
        } catch (Exception e) {
            logger.error("Error fetching all billing entries", e);
            throw new RuntimeException("Failed to fetch billing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BillingDrawingEntryDto> getAllBillingEntries() {
        try {
            logger.info("Fetching all billing entries");
            List<BillingDrawingEntry> entities = billingDrawingEntryRepository.findAll();
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching all billing entries", e);
            throw new RuntimeException("Failed to fetch billing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BillingDrawingEntryDto> getBillingEntriesByDrawingNo(String drawingNo) {
        try {
            logger.info("Fetching billing entries by drawing number: {}", drawingNo);
            List<BillingDrawingEntry> entities = billingDrawingEntryRepository.findByDrawingNoOrderByCreationDateDesc(drawingNo);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching billing entries by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to fetch billing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BillingDrawingEntryDto> getBillingEntriesByMarkNo(String markNo) {
        try {
            logger.info("Fetching billing entries by mark number: {}", markNo);
            List<BillingDrawingEntry> entities = billingDrawingEntryRepository.findByMarkNoOrderByCreationDateDesc(markNo);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching billing entries by mark number: {}", markNo, e);
            throw new RuntimeException("Failed to fetch billing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BillingDrawingEntryDto> getBillingEntriesBySessionCode(String sessionCode) {
        try {
            logger.info("Fetching billing entries by session code: {}", sessionCode);
            List<BillingDrawingEntry> entities = billingDrawingEntryRepository.findBySessionCodeOrderByCreationDateDesc(sessionCode);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching billing entries by session code: {}", sessionCode, e);
            throw new RuntimeException("Failed to fetch billing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Page<BillingDrawingEntryDto> getBillingEntriesByTenantId(String tenantId, Pageable pageable) {
        try {
            logger.info("Fetching billing entries by tenant ID: {}", tenantId);
            Page<BillingDrawingEntry> entities = billingDrawingEntryRepository.findByTenantIdOrderByCreationDateDesc(tenantId, pageable);
            List<BillingDrawingEntryDto> dtos = entities.getContent().stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
            return new PageImpl<>(dtos, pageable, entities.getTotalElements());
        } catch (Exception e) {
            logger.error("Error fetching billing entries by tenant ID: {}", tenantId, e);
            throw new RuntimeException("Failed to fetch billing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BillingDrawingEntryDto> getBillingEntriesByStatus(String status) {
        try {
            logger.info("Fetching billing entries by status: {}", status);
            List<BillingDrawingEntry> entities = billingDrawingEntryRepository.findByStatusOrderByCreationDateDesc(status);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching billing entries by status: {}", status, e);
            throw new RuntimeException("Failed to fetch billing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Page<BillingDrawingEntryDto> getBillingEntriesByStatus(String status, Pageable pageable) {
        try {
            logger.info("Fetching billing entries by status with pagination: {}", status);
            Page<BillingDrawingEntry> entities = billingDrawingEntryRepository.findByStatusOrderByCreationDateDesc(status, pageable);
            List<BillingDrawingEntryDto> dtos = entities.getContent().stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
            return new PageImpl<>(dtos, pageable, entities.getTotalElements());
        } catch (Exception e) {
            logger.error("Error fetching billing entries by status: {}", status, e);
            throw new RuntimeException("Failed to fetch billing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Page<BillingDrawingEntryDto> searchBillingEntries(String drawingNo, String markNo, String sessionCode, 
                                                            String tenantId, String status, Pageable pageable) {
        try {
            logger.info("Searching billing entries with criteria");
            Page<BillingDrawingEntry> entities = billingDrawingEntryRepository.findByMultipleCriteria(
                    drawingNo, markNo, sessionCode, tenantId, status, pageable);
            List<BillingDrawingEntryDto> dtos = entities.getContent().stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
            return new PageImpl<>(dtos, pageable, entities.getTotalElements());
        } catch (Exception e) {
            logger.error("Error searching billing entries", e);
            throw new RuntimeException("Failed to search billing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BillingDrawingEntryDto> getBillingEntriesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            logger.info("Fetching billing entries by date range: {} to {}", startDate, endDate);
            List<BillingDrawingEntry> entities = billingDrawingEntryRepository.findByCreationDateBetweenOrderByCreationDateDesc(startDate, endDate);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching billing entries by date range", e);
            throw new RuntimeException("Failed to fetch billing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BillingDrawingEntryDto> getBillingEntriesByMarkedQtyGreaterThan(BigDecimal markedQty) {
        try {
            logger.info("Fetching billing entries with marked quantity greater than: {}", markedQty);
            List<BillingDrawingEntry> entities = billingDrawingEntryRepository.findByMarkedQtyGreaterThanOrderByMarkedQtyDesc(markedQty);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching billing entries by marked quantity", e);
            throw new RuntimeException("Failed to fetch billing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public BillingDrawingEntryDto updateBillingEntry(String lineId, BillingDrawingEntryDto billingEntryDto) {
        try {
            logger.info("Updating billing entry with line ID: {}", lineId);
            
            Optional<BillingDrawingEntry> existingEntityOpt = billingDrawingEntryRepository.findById(lineId);
            if (!existingEntityOpt.isPresent()) {
                throw new RuntimeException("Billing entry not found with line ID: " + lineId);
            }
            
            BillingDrawingEntry existingEntity = existingEntityOpt.get();
            updateEntityFromDto(existingEntity, billingEntryDto);
            existingEntity.setLastUpdatingDate(LocalDateTime.now());
            
            BillingDrawingEntry savedEntity = billingDrawingEntryRepository.save(existingEntity);
            return convertEntityToDto(savedEntity);
        } catch (Exception e) {
            logger.error("Error updating billing entry: {}", lineId, e);
            throw new RuntimeException("Failed to update billing entry: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteBillingEntry(String lineId) {
        try {
            logger.info("Deleting billing entry with line ID: {}", lineId);
            if (!billingDrawingEntryRepository.existsById(lineId)) {
                throw new RuntimeException("Billing entry not found with line ID: " + lineId);
            }
            billingDrawingEntryRepository.deleteById(lineId);
        } catch (Exception e) {
            logger.error("Error deleting billing entry: {}", lineId, e);
            throw new RuntimeException("Failed to delete billing entry: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteBillingEntriesByDrawingNo(String drawingNo) {
        try {
            logger.info("Deleting billing entries by drawing number: {}", drawingNo);
            billingDrawingEntryRepository.deleteByDrawingNo(drawingNo);
        } catch (Exception e) {
            logger.error("Error deleting billing entries by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to delete billing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteBillingEntriesByMarkNo(String markNo) {
        try {
            logger.info("Deleting billing entries by mark number: {}", markNo);
            billingDrawingEntryRepository.deleteByMarkNo(markNo);
        } catch (Exception e) {
            logger.error("Error deleting billing entries by mark number: {}", markNo, e);
            throw new RuntimeException("Failed to delete billing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteBillingEntriesByStatus(String status) {
        try {
            logger.info("Deleting billing entries by status: {}", status);
            billingDrawingEntryRepository.deleteByStatus(status);
        } catch (Exception e) {
            logger.error("Error deleting billing entries by status: {}", status, e);
            throw new RuntimeException("Failed to delete billing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public void bulkDeleteBillingEntries(List<String> lineIds) {
        try {
            logger.info("Bulk deleting {} billing entries", lineIds.size());
            billingDrawingEntryRepository.deleteAllById(lineIds);
        } catch (Exception e) {
            logger.error("Error bulk deleting billing entries", e);
            throw new RuntimeException("Failed to bulk delete billing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Long getCountByDrawingNo(String drawingNo) {
        try {
            return billingDrawingEntryRepository.countByDrawingNo(drawingNo);
        } catch (Exception e) {
            logger.error("Error getting count by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to get count: " + e.getMessage(), e);
        }
    }

    @Override
    public Long getCountByMarkNo(String markNo) {
        try {
            return billingDrawingEntryRepository.countByMarkNo(markNo);
        } catch (Exception e) {
            logger.error("Error getting count by mark number: {}", markNo, e);
            throw new RuntimeException("Failed to get count: " + e.getMessage(), e);
        }
    }

    @Override
    public Long getCountByStatus(String status) {
        try {
            return billingDrawingEntryRepository.countByStatus(status);
        } catch (Exception e) {
            logger.error("Error getting count by status: {}", status, e);
            throw new RuntimeException("Failed to get count: " + e.getMessage(), e);
        }
    }

    @Override
    public long getTotalCount() {
        try {
            return billingDrawingEntryRepository.count();
        } catch (Exception e) {
            logger.error("Error getting total count", e);
            throw new RuntimeException("Failed to get total count: " + e.getMessage(), e);
        }
    }

    @Override
    public BigDecimal getSumMarkedQtyByDrawingNo(String drawingNo) {
        try {
            return billingDrawingEntryRepository.sumMarkedQtyByDrawingNo(drawingNo);
        } catch (Exception e) {
            logger.error("Error getting sum of marked quantities: {}", drawingNo, e);
            throw new RuntimeException("Failed to get sum: " + e.getMessage(), e);
        }
    }

    @Override
    public BigDecimal getSumTotalMarkedWgtByDrawingNo(String drawingNo) {
        try {
            return billingDrawingEntryRepository.sumTotalMarkedWgtByDrawingNo(drawingNo);
        } catch (Exception e) {
            logger.error("Error getting sum of total marked weights: {}", drawingNo, e);
            throw new RuntimeException("Failed to get sum: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getDistinctDrawingNumbers() {
        try {
            return billingDrawingEntryRepository.findDistinctDrawingNumbers();
        } catch (Exception e) {
            logger.error("Error getting distinct drawing numbers", e);
            throw new RuntimeException("Failed to get distinct drawing numbers: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getDistinctMarkNumbers() {
        try {
            return billingDrawingEntryRepository.findDistinctMarkNumbers();
        } catch (Exception e) {
            logger.error("Error getting distinct mark numbers", e);
            throw new RuntimeException("Failed to get distinct mark numbers: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getDistinctSessionCodes() {
        try {
            return billingDrawingEntryRepository.findDistinctSessionCodes();
        } catch (Exception e) {
            logger.error("Error getting distinct session codes", e);
            throw new RuntimeException("Failed to get distinct session codes: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getDistinctStatuses() {
        try {
            return billingDrawingEntryRepository.findDistinctStatuses();
        } catch (Exception e) {
            logger.error("Error getting distinct statuses", e);
            throw new RuntimeException("Failed to get distinct statuses: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsById(String lineId) {
        try {
            return billingDrawingEntryRepository.existsById(lineId);
        } catch (Exception e) {
            logger.error("Error checking existence by line ID: {}", lineId, e);
            throw new RuntimeException("Failed to check existence: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsByDrawingNoAndMarkNo(String drawingNo, String markNo) {
        try {
            return billingDrawingEntryRepository.existsByDrawingNoAndMarkNo(drawingNo, markNo);
        } catch (Exception e) {
            logger.error("Error checking existence by drawing and mark number", e);
            throw new RuntimeException("Failed to check existence: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsByDrawingNoAndMarkNoAndStatus(String drawingNo, String markNo, String status) {
        try {
            return billingDrawingEntryRepository.existsByDrawingNoAndMarkNoAndStatus(drawingNo, markNo, status);
        } catch (Exception e) {
            logger.error("Error checking existence by drawing, mark number and status", e);
            throw new RuntimeException("Failed to check existence: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<BillingDrawingEntryDto> getLatestByDrawingNo(String drawingNo) {
        try {
            Optional<BillingDrawingEntry> entity = billingDrawingEntryRepository.findTopByDrawingNoOrderByCreationDateDesc(drawingNo);
            return entity.map(this::convertEntityToDto);
        } catch (Exception e) {
            logger.error("Error getting latest entry by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to get latest entry: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<BillingDrawingEntryDto> getLatestByMarkNo(String markNo) {
        try {
            Optional<BillingDrawingEntry> entity = billingDrawingEntryRepository.findTopByMarkNoOrderByCreationDateDesc(markNo);
            return entity.map(this::convertEntityToDto);
        } catch (Exception e) {
            logger.error("Error getting latest entry by mark number: {}", markNo, e);
            throw new RuntimeException("Failed to get latest entry: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<BillingDrawingEntryDto> getLatestByStatus(String status) {
        try {
            Optional<BillingDrawingEntry> entity = billingDrawingEntryRepository.findTopByStatusOrderByCreationDateDesc(status);
            return entity.map(this::convertEntityToDto);
        } catch (Exception e) {
            logger.error("Error getting latest entry by status: {}", status, e);
            throw new RuntimeException("Failed to get latest entry: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BillingDrawingEntryDto> getUniqueDrawingMarkCombinations() {
        try {
            logger.info("Fetching unique drawing-mark combinations");
            List<BillingDrawingEntry> entities = billingDrawingEntryRepository.findUniqueDrawingMarkCombinations();
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching unique combinations", e);
            throw new RuntimeException("Failed to fetch unique combinations: " + e.getMessage(), e);
        }
    }

    // Helper methods for conversion between Entity and DTO
    private BillingDrawingEntry convertDtoToEntity(BillingDrawingEntryDto dto) {
        BillingDrawingEntry entity = new BillingDrawingEntry();
        
        entity.setLineId(dto.getLineId());
        entity.setVersion(dto.getVersion());
        entity.setDrawingNo(dto.getDrawingNo());
        entity.setMarkNo(dto.getMarkNo());
        entity.setMarkedQty(dto.getMarkedQty());
        entity.setTotalMarkedWgt(dto.getTotalMarkedWgt());
        entity.setSessionCode(dto.getSessionCode());
        entity.setSessionName(dto.getSessionName());
        entity.setSessionWeight(dto.getSessionWeight());
        entity.setWidth(dto.getWidth());
        entity.setLength(dto.getLength());
        entity.setItemQty(dto.getItemQty());
        entity.setItemWeight(dto.getItemWeight());
        entity.setTenantId(dto.getTenantId());
        entity.setCreationDate(dto.getCreationDate());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setLastUpdatingDate(dto.getLastUpdatingDate());
        entity.setLastUpdatedBy(dto.getLastUpdatedBy());
        entity.setStatus(dto.getStatus());
        
        // Set PO Line Reference ID if provided
        if (dto.getPoLineReferenceId() != null) {
            entity.setPoLineReferenceId(dto.getPoLineReferenceId());
        }
        
        // Set attributes if not null
        if (dto.getAttribute1V() != null) entity.setAttribute1V(dto.getAttribute1V());
        if (dto.getAttribute2V() != null) entity.setAttribute2V(dto.getAttribute2V());
        if (dto.getAttribute3V() != null) entity.setAttribute3V(dto.getAttribute3V());
        if (dto.getAttribute4V() != null) entity.setAttribute4V(dto.getAttribute4V());
        if (dto.getAttribute5V() != null) entity.setAttribute5V(dto.getAttribute5V());
        if (dto.getAttribute1N() != null) entity.setAttribute1N(dto.getAttribute1N());
        if (dto.getAttribute2N() != null) entity.setAttribute2N(dto.getAttribute2N());
        if (dto.getAttribute3N() != null) entity.setAttribute3N(dto.getAttribute3N());
        if (dto.getAttribute4N() != null) entity.setAttribute4N(dto.getAttribute4N());
        if (dto.getAttribute5N() != null) entity.setAttribute5N(dto.getAttribute5N());
        if (dto.getAttribute1D() != null) entity.setAttribute1D(dto.getAttribute1D());
        if (dto.getAttribute2D() != null) entity.setAttribute2D(dto.getAttribute2D());
        if (dto.getAttribute3D() != null) entity.setAttribute3D(dto.getAttribute3D());
        if (dto.getAttribute4D() != null) entity.setAttribute4D(dto.getAttribute4D());
        if (dto.getAttribute5D() != null) entity.setAttribute5D(dto.getAttribute5D());
        
        // NEW FIELDS - Set the new fields for enhanced data
        if (dto.getDrawingWeight() != null) entity.setDrawingWeight(dto.getDrawingWeight());
        if (dto.getMarkWeight() != null) entity.setMarkWeight(dto.getMarkWeight());
        if (dto.getDrawingReceivedDate() != null) entity.setDrawingReceivedDate(dto.getDrawingReceivedDate());
        if (dto.getTargetDate() != null) entity.setTargetDate(dto.getTargetDate());
        
        // NEW FIELDS - Set fabrication stage fields with default "N" if null
        entity.setCuttingStage(dto.getCuttingStage() != null ? dto.getCuttingStage() : "N");
        entity.setFitUpStage(dto.getFitUpStage() != null ? dto.getFitUpStage() : "N");
        entity.setWeldingStage(dto.getWeldingStage() != null ? dto.getWeldingStage() : "N");
        entity.setFinishingStage(dto.getFinishingStage() != null ? dto.getFinishingStage() : "N");
        
        return entity;
    }

    private BillingDrawingEntryDto convertEntityToDto(BillingDrawingEntry entity) {
        BillingDrawingEntryDto dto = new BillingDrawingEntryDto();
        
        dto.setLineId(entity.getLineId());
        dto.setVersion(entity.getVersion());
        dto.setDrawingNo(entity.getDrawingNo());
        dto.setMarkNo(entity.getMarkNo());
        dto.setMarkedQty(entity.getMarkedQty());
        dto.setTotalMarkedWgt(entity.getTotalMarkedWgt());
        dto.setSessionCode(entity.getSessionCode());
        dto.setSessionName(entity.getSessionName());
        dto.setSessionWeight(entity.getSessionWeight());
        dto.setWidth(entity.getWidth());
        dto.setLength(entity.getLength());
        dto.setItemQty(entity.getItemQty());
        dto.setItemWeight(entity.getItemWeight());
        dto.setTenantId(entity.getTenantId());
        dto.setCreationDate(entity.getCreationDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setLastUpdatingDate(entity.getLastUpdatingDate());
        dto.setLastUpdatedBy(entity.getLastUpdatedBy());
        dto.setStatus(entity.getStatus());
        
        // Set PO Line Reference ID if available
        dto.setPoLineReferenceId(entity.getPoLineReferenceId());
        
        // Set attributes
        dto.setAttribute1V(entity.getAttribute1V());
        dto.setAttribute2V(entity.getAttribute2V());
        dto.setAttribute3V(entity.getAttribute3V());
        dto.setAttribute4V(entity.getAttribute4V());
        dto.setAttribute5V(entity.getAttribute5V());
        dto.setAttribute1N(entity.getAttribute1N());
        dto.setAttribute2N(entity.getAttribute2N());
        dto.setAttribute3N(entity.getAttribute3N());
        dto.setAttribute4N(entity.getAttribute4N());
        dto.setAttribute5N(entity.getAttribute5N());
        dto.setAttribute1D(entity.getAttribute1D());
        dto.setAttribute2D(entity.getAttribute2D());
        dto.setAttribute3D(entity.getAttribute3D());
        dto.setAttribute4D(entity.getAttribute4D());
        dto.setAttribute5D(entity.getAttribute5D());
        
        // NEW FIELDS - Get the new fields for enhanced data
        dto.setDrawingWeight(entity.getDrawingWeight());
        dto.setMarkWeight(entity.getMarkWeight());
        dto.setDrawingReceivedDate(entity.getDrawingReceivedDate());
        dto.setTargetDate(entity.getTargetDate());
        
        // NEW FIELDS - Get fabrication stage fields
        dto.setCuttingStage(entity.getCuttingStage());
        dto.setFitUpStage(entity.getFitUpStage());
        dto.setWeldingStage(entity.getWeldingStage());
        dto.setFinishingStage(entity.getFinishingStage());
        
        return dto;
    }

    private void updateEntityFromDto(BillingDrawingEntry entity, BillingDrawingEntryDto dto) {
        if (dto.getDrawingNo() != null) entity.setDrawingNo(dto.getDrawingNo());
        if (dto.getMarkNo() != null) entity.setMarkNo(dto.getMarkNo());
        if (dto.getMarkedQty() != null) entity.setMarkedQty(dto.getMarkedQty());
        if (dto.getTotalMarkedWgt() != null) entity.setTotalMarkedWgt(dto.getTotalMarkedWgt());
        if (dto.getSessionCode() != null) entity.setSessionCode(dto.getSessionCode());
        if (dto.getSessionName() != null) entity.setSessionName(dto.getSessionName());
        if (dto.getSessionWeight() != null) entity.setSessionWeight(dto.getSessionWeight());
        if (dto.getWidth() != null) entity.setWidth(dto.getWidth());
        if (dto.getLength() != null) entity.setLength(dto.getLength());
        if (dto.getItemQty() != null) entity.setItemQty(dto.getItemQty());
        if (dto.getItemWeight() != null) entity.setItemWeight(dto.getItemWeight());
        if (dto.getTenantId() != null) entity.setTenantId(dto.getTenantId());
        if (dto.getCreatedBy() != null) entity.setCreatedBy(dto.getCreatedBy());
        if (dto.getLastUpdatedBy() != null) entity.setLastUpdatedBy(dto.getLastUpdatedBy());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
        
        // Update PO Line Reference ID if provided
        if (dto.getPoLineReferenceId() != null) entity.setPoLineReferenceId(dto.getPoLineReferenceId());
        
        // Update attributes if not null
        if (dto.getAttribute1V() != null) entity.setAttribute1V(dto.getAttribute1V());
        if (dto.getAttribute2V() != null) entity.setAttribute2V(dto.getAttribute2V());
        if (dto.getAttribute3V() != null) entity.setAttribute3V(dto.getAttribute3V());
        if (dto.getAttribute4V() != null) entity.setAttribute4V(dto.getAttribute4V());
        if (dto.getAttribute5V() != null) entity.setAttribute5V(dto.getAttribute5V());
        if (dto.getAttribute1N() != null) entity.setAttribute1N(dto.getAttribute1N());
        if (dto.getAttribute2N() != null) entity.setAttribute2N(dto.getAttribute2N());
        if (dto.getAttribute3N() != null) entity.setAttribute3N(dto.getAttribute3N());
        if (dto.getAttribute4N() != null) entity.setAttribute4N(dto.getAttribute4N());
        if (dto.getAttribute5N() != null) entity.setAttribute5N(dto.getAttribute5N());
        if (dto.getAttribute1D() != null) entity.setAttribute1D(dto.getAttribute1D());
        if (dto.getAttribute2D() != null) entity.setAttribute2D(dto.getAttribute2D());
        if (dto.getAttribute3D() != null) entity.setAttribute3D(dto.getAttribute3D());
        if (dto.getAttribute4D() != null) entity.setAttribute4D(dto.getAttribute4D());
        if (dto.getAttribute5D() != null) entity.setAttribute5D(dto.getAttribute5D());
        
        // NEW FIELDS - Update the new fields for enhanced data
        if (dto.getDrawingWeight() != null) entity.setDrawingWeight(dto.getDrawingWeight());
        if (dto.getMarkWeight() != null) entity.setMarkWeight(dto.getMarkWeight());
        if (dto.getDrawingReceivedDate() != null) entity.setDrawingReceivedDate(dto.getDrawingReceivedDate());
        if (dto.getTargetDate() != null) entity.setTargetDate(dto.getTargetDate());
        
        // NEW FIELDS - Update fabrication stage fields
        if (dto.getCuttingStage() != null) entity.setCuttingStage(dto.getCuttingStage());
        if (dto.getFitUpStage() != null) entity.setFitUpStage(dto.getFitUpStage());
        if (dto.getWeldingStage() != null) entity.setWeldingStage(dto.getWeldingStage());
        if (dto.getFinishingStage() != null) entity.setFinishingStage(dto.getFinishingStage());
    }

    /**
     * Generate a sequential line ID in format B1.1, B1.2, B1.3, etc.
     * This ensures unique, sequential serial numbers for billing entries
     */
    private synchronized String generateSequentialLineId() {
        // Get the current maximum line ID from database
        long maxId = getMaxLineIdNumber();
        long nextId = Math.max(maxId + 1, lineIdCounter.get());
        
        // Update the counter
        lineIdCounter.set(nextId + 1);
        
        // Generate format like B1.1, B1.2, etc. (B for Billing)
        long major = (nextId - 1) / 10 + 1;
        long minor = (nextId - 1) % 10 + 1;
        
        return "B" + major + "." + minor;
    }
    
    /**
     * Get the maximum line ID number from existing records
     */
    private long getMaxLineIdNumber() {
        try {
            List<BillingDrawingEntry> allEntries = billingDrawingEntryRepository.findAll();
            long maxNumber = 0;
            
            for (BillingDrawingEntry entry : allEntries) {
                String lineId = entry.getLineId();
                if (lineId != null && lineId.matches("B\\d+\\.\\d+")) {
                    try {
                        String[] parts = lineId.substring(1).split("\\.");
                        long major = Long.parseLong(parts[0]);
                        long minor = Long.parseLong(parts[1]);
                        long number = (major - 1) * 10 + minor;
                        maxNumber = Math.max(maxNumber, number);
                    } catch (NumberFormatException e) {
                        // Skip invalid format
                    }
                }
            }
            
            return maxNumber;
        } catch (Exception e) {
            logger.warn("Error getting max line ID number, starting from 0", e);
            return 0;
        }
    }
}
