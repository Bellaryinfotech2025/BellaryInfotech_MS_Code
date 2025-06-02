package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.ErectionDrawingEntryDto;
import com.bellaryinfotech.model.ErectionDrawingEntry;
import com.bellaryinfotech.repo.ErectionDrawingEntryRepository;
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
public class ErectionDrawingEntryServiceImpl implements ErectionDrawingEntryService {

    private static final Logger logger = LoggerFactory.getLogger(ErectionDrawingEntryServiceImpl.class);

    @Autowired
    private ErectionDrawingEntryRepository erectionDrawingEntryRepository;

    // Counter for sequential line IDs
    private static final AtomicLong lineIdCounter = new AtomicLong(1);

    @Override
    public List<ErectionDrawingEntryDto> createErectionEntry(ErectionDrawingEntryDto erectionEntryDto) {
        try {
            logger.info("Creating erection entry with marked quantity: {}", erectionEntryDto.getMarkedQty());
            
            // Set default marked quantity if null
            if (erectionEntryDto.getMarkedQty() == null) {
                erectionEntryDto.setMarkedQty(BigDecimal.ONE);
            }
            
            // Validate input - allow any positive value
            if (erectionEntryDto.getMarkedQty().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Marked quantity must be greater than zero");
            }

            List<ErectionDrawingEntry> entriesToSave = new ArrayList<>();
            int markedQtyInt = erectionEntryDto.getMarkedQty().intValue();

            // Create entries based on marked quantity
            for (int i = 0; i < markedQtyInt; i++) {
                ErectionDrawingEntry entry = convertDtoToEntity(erectionEntryDto);
                
                // Generate sequential line ID
                entry.setLineId(generateSequentialLineId());
                
                // Set creation metadata
                entry.setCreationDate(LocalDateTime.now());
                entry.setLastUpdatingDate(LocalDateTime.now());
                entry.setStatus("erection");
                
                // Set marked quantity to 1 for each individual entry
                entry.setMarkedQty(BigDecimal.ONE);
                
                entriesToSave.add(entry);
            }

            // Save all entries
            List<ErectionDrawingEntry> savedEntries = erectionDrawingEntryRepository.saveAll(entriesToSave);
            
            logger.info("Successfully created {} erection entries", savedEntries.size());
            
            // Convert back to DTOs
            return savedEntries.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
                
        } catch (Exception e) {
            logger.error("Error creating erection entry", e);
            throw new RuntimeException("Failed to create erection entry: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ErectionDrawingEntryDto> createErectionEntries(List<ErectionDrawingEntryDto> erectionEntryDtos) {
        try {
            logger.info("Creating {} erection entries with duplicate checking", erectionEntryDtos.size());
        
            List<ErectionDrawingEntry> allEntriesToSave = new ArrayList<>();
            List<String> skippedDuplicates = new ArrayList<>();
            List<String> successfulEntries = new ArrayList<>();
        
            for (ErectionDrawingEntryDto dto : erectionEntryDtos) {
                // Validate input
                if (dto.getMarkedQty() == null || dto.getMarkedQty().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException("Marked quantity must be greater than zero for drawing: " + dto.getDrawingNo());
                }

                // Check if this drawing+mark combination already exists in erection table
                boolean exists = erectionDrawingEntryRepository.existsByDrawingNoAndMarkNo(dto.getDrawingNo(), dto.getMarkNo());
            
                if (exists) {
                    logger.info("Skipping duplicate entry for Drawing: {}, Mark: {}", dto.getDrawingNo(), dto.getMarkNo());
                    skippedDuplicates.add(dto.getDrawingNo() + " - " + dto.getMarkNo());
                    continue; // Skip this entry as it already exists
                }

                // Create only one entry per mark number (no duplicates based on markedQty)
                ErectionDrawingEntry entry = convertDtoToEntity(dto);
            
                // Generate sequential line ID
                entry.setLineId(generateSequentialLineId());
            
                // Set creation metadata
                entry.setCreationDate(LocalDateTime.now());
                entry.setLastUpdatingDate(LocalDateTime.now());
                entry.setStatus("erection");
            
                // Keep the original marked quantity (don't split into multiple entries)
                entry.setMarkedQty(dto.getMarkedQty());
            
                allEntriesToSave.add(entry);
                successfulEntries.add(dto.getDrawingNo() + " - " + dto.getMarkNo());
            }

            // Save all unique entries
            List<ErectionDrawingEntry> savedEntries = new ArrayList<>();
            if (!allEntriesToSave.isEmpty()) {
                savedEntries = erectionDrawingEntryRepository.saveAll(allEntriesToSave);
            }
        
            logger.info("Successfully created {} erection entries, skipped {} duplicates", 
                       savedEntries.size(), skippedDuplicates.size());
        
            // Log details about what was processed
            if (!skippedDuplicates.isEmpty()) {
                logger.info("Skipped duplicates: {}", String.join(", ", skippedDuplicates));
            }
            if (!successfulEntries.isEmpty()) {
                logger.info("Successfully created: {}", String.join(", ", successfulEntries));
            }
        
            // Convert back to DTOs
            List<ErectionDrawingEntryDto> result = savedEntries.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        
            // Add metadata about the operation to the first DTO if available
            if (!result.isEmpty() && (!skippedDuplicates.isEmpty() || !successfulEntries.isEmpty())) {
                // You could add this info to a response wrapper or log it
                logger.info("Operation summary - Created: {}, Skipped: {}", 
                           successfulEntries.size(), skippedDuplicates.size());
            }
        
            return result;
                
        } catch (Exception e) {
            logger.error("Error creating erection entries", e);
            throw new RuntimeException("Failed to create erection entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<ErectionDrawingEntryDto> getErectionEntryById(String lineId) {
        try {
            logger.info("Fetching erection entry by line ID: {}", lineId);
            Optional<ErectionDrawingEntry> entity = erectionDrawingEntryRepository.findById(lineId);
            return entity.map(this::convertEntityToDto);
        } catch (Exception e) {
            logger.error("Error fetching erection entry by ID: {}", lineId, e);
            throw new RuntimeException("Failed to fetch erection entry: " + e.getMessage(), e);
        }
    }

    @Override
    public Page<ErectionDrawingEntryDto> getAllErectionEntries(Pageable pageable) {
        try {
            logger.info("Fetching all erection entries with pagination");
            Page<ErectionDrawingEntry> entities = erectionDrawingEntryRepository.findAll(pageable);
            List<ErectionDrawingEntryDto> dtos = entities.getContent().stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
            return new PageImpl<>(dtos, pageable, entities.getTotalElements());
        } catch (Exception e) {
            logger.error("Error fetching all erection entries", e);
            throw new RuntimeException("Failed to fetch erection entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ErectionDrawingEntryDto> getAllErectionEntries() {
        try {
            logger.info("Fetching all erection entries");
            List<ErectionDrawingEntry> entities = erectionDrawingEntryRepository.findAll();
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching all erection entries", e);
            throw new RuntimeException("Failed to fetch erection entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ErectionDrawingEntryDto> getErectionEntriesByDrawingNo(String drawingNo) {
        try {
            logger.info("Fetching erection entries by drawing number: {}", drawingNo);
            List<ErectionDrawingEntry> entities = erectionDrawingEntryRepository.findByDrawingNoOrderByCreationDateDesc(drawingNo);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching erection entries by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to fetch erection entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ErectionDrawingEntryDto> getErectionEntriesByMarkNo(String markNo) {
        try {
            logger.info("Fetching erection entries by mark number: {}", markNo);
            List<ErectionDrawingEntry> entities = erectionDrawingEntryRepository.findByMarkNoOrderByCreationDateDesc(markNo);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching erection entries by mark number: {}", markNo, e);
            throw new RuntimeException("Failed to fetch erection entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ErectionDrawingEntryDto> getErectionEntriesBySessionCode(String sessionCode) {
        try {
            logger.info("Fetching erection entries by session code: {}", sessionCode);
            List<ErectionDrawingEntry> entities = erectionDrawingEntryRepository.findBySessionCodeOrderByCreationDateDesc(sessionCode);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching erection entries by session code: {}", sessionCode, e);
            throw new RuntimeException("Failed to fetch erection entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Page<ErectionDrawingEntryDto> getErectionEntriesByTenantId(String tenantId, Pageable pageable) {
        try {
            logger.info("Fetching erection entries by tenant ID: {}", tenantId);
            Page<ErectionDrawingEntry> entities = erectionDrawingEntryRepository.findByTenantIdOrderByCreationDateDesc(tenantId, pageable);
            List<ErectionDrawingEntryDto> dtos = entities.getContent().stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
            return new PageImpl<>(dtos, pageable, entities.getTotalElements());
        } catch (Exception e) {
            logger.error("Error fetching erection entries by tenant ID: {}", tenantId, e);
            throw new RuntimeException("Failed to fetch erection entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ErectionDrawingEntryDto> getErectionEntriesByStatus(String status) {
        try {
            logger.info("Fetching erection entries by status: {}", status);
            List<ErectionDrawingEntry> entities = erectionDrawingEntryRepository.findByStatusOrderByCreationDateDesc(status);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching erection entries by status: {}", status, e);
            throw new RuntimeException("Failed to fetch erection entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Page<ErectionDrawingEntryDto> getErectionEntriesByStatus(String status, Pageable pageable) {
        try {
            logger.info("Fetching erection entries by status with pagination: {}", status);
            Page<ErectionDrawingEntry> entities = erectionDrawingEntryRepository.findByStatusOrderByCreationDateDesc(status, pageable);
            List<ErectionDrawingEntryDto> dtos = entities.getContent().stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
            return new PageImpl<>(dtos, pageable, entities.getTotalElements());
        } catch (Exception e) {
            logger.error("Error fetching erection entries by status: {}", status, e);
            throw new RuntimeException("Failed to fetch erection entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Page<ErectionDrawingEntryDto> searchErectionEntries(String drawingNo, String markNo, String sessionCode, 
                                                              String tenantId, String status, Pageable pageable) {
        try {
            logger.info("Searching erection entries with criteria");
            Page<ErectionDrawingEntry> entities = erectionDrawingEntryRepository.findByMultipleCriteria(
                    drawingNo, markNo, sessionCode, tenantId, status, pageable);
            List<ErectionDrawingEntryDto> dtos = entities.getContent().stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
            return new PageImpl<>(dtos, pageable, entities.getTotalElements());
        } catch (Exception e) {
            logger.error("Error searching erection entries", e);
            throw new RuntimeException("Failed to search erection entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ErectionDrawingEntryDto> getErectionEntriesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            logger.info("Fetching erection entries by date range: {} to {}", startDate, endDate);
            List<ErectionDrawingEntry> entities = erectionDrawingEntryRepository.findByCreationDateBetweenOrderByCreationDateDesc(startDate, endDate);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching erection entries by date range", e);
            throw new RuntimeException("Failed to fetch erection entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ErectionDrawingEntryDto> getErectionEntriesByMarkedQtyGreaterThan(BigDecimal markedQty) {
        try {
            logger.info("Fetching erection entries with marked quantity greater than: {}", markedQty);
            List<ErectionDrawingEntry> entities = erectionDrawingEntryRepository.findByMarkedQtyGreaterThanOrderByMarkedQtyDesc(markedQty);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching erection entries by marked quantity", e);
            throw new RuntimeException("Failed to fetch erection entries: " + e.getMessage(), e);
        }
    }

    @Override
    public ErectionDrawingEntryDto updateErectionEntry(String lineId, ErectionDrawingEntryDto erectionEntryDto) {
        try {
            logger.info("Updating erection entry with line ID: {}", lineId);
            
            Optional<ErectionDrawingEntry> existingEntityOpt = erectionDrawingEntryRepository.findById(lineId);
            if (!existingEntityOpt.isPresent()) {
                throw new RuntimeException("Erection entry not found with line ID: " + lineId);
            }
            
            ErectionDrawingEntry existingEntity = existingEntityOpt.get();
            updateEntityFromDto(existingEntity, erectionEntryDto);
            existingEntity.setLastUpdatingDate(LocalDateTime.now());
            
            ErectionDrawingEntry savedEntity = erectionDrawingEntryRepository.save(existingEntity);
            return convertEntityToDto(savedEntity);
        } catch (Exception e) {
            logger.error("Error updating erection entry: {}", lineId, e);
            throw new RuntimeException("Failed to update erection entry: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteErectionEntry(String lineId) {
        try {
            logger.info("Deleting erection entry with line ID: {}", lineId);
            if (!erectionDrawingEntryRepository.existsById(lineId)) {
                throw new RuntimeException("Erection entry not found with line ID: " + lineId);
            }
            erectionDrawingEntryRepository.deleteById(lineId);
        } catch (Exception e) {
            logger.error("Error deleting erection entry: {}", lineId, e);
            throw new RuntimeException("Failed to delete erection entry: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteErectionEntriesByDrawingNo(String drawingNo) {
        try {
            logger.info("Deleting erection entries by drawing number: {}", drawingNo);
            erectionDrawingEntryRepository.deleteByDrawingNo(drawingNo);
        } catch (Exception e) {
            logger.error("Error deleting erection entries by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to delete erection entries: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteErectionEntriesByMarkNo(String markNo) {
        try {
            logger.info("Deleting erection entries by mark number: {}", markNo);
            erectionDrawingEntryRepository.deleteByMarkNo(markNo);
        } catch (Exception e) {
            logger.error("Error deleting erection entries by mark number: {}", markNo, e);
            throw new RuntimeException("Failed to delete erection entries: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteErectionEntriesByStatus(String status) {
        try {
            logger.info("Deleting erection entries by status: {}", status);
            erectionDrawingEntryRepository.deleteByStatus(status);
        } catch (Exception e) {
            logger.error("Error deleting erection entries by status: {}", status, e);
            throw new RuntimeException("Failed to delete erection entries: " + e.getMessage(), e);
        }
    }

    @Override
    public void bulkDeleteErectionEntries(List<String> lineIds) {
        try {
            logger.info("Bulk deleting {} erection entries", lineIds.size());
            erectionDrawingEntryRepository.deleteAllById(lineIds);
        } catch (Exception e) {
            logger.error("Error bulk deleting erection entries", e);
            throw new RuntimeException("Failed to bulk delete erection entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Long getCountByDrawingNo(String drawingNo) {
        try {
            return erectionDrawingEntryRepository.countByDrawingNo(drawingNo);
        } catch (Exception e) {
            logger.error("Error getting count by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to get count: " + e.getMessage(), e);
        }
    }

    @Override
    public Long getCountByMarkNo(String markNo) {
        try {
            return erectionDrawingEntryRepository.countByMarkNo(markNo);
        } catch (Exception e) {
            logger.error("Error getting count by mark number: {}", markNo, e);
            throw new RuntimeException("Failed to get count: " + e.getMessage(), e);
        }
    }

    @Override
    public Long getCountByStatus(String status) {
        try {
            return erectionDrawingEntryRepository.countByStatus(status);
        } catch (Exception e) {
            logger.error("Error getting count by status: {}", status, e);
            throw new RuntimeException("Failed to get count: " + e.getMessage(), e);
        }
    }

    @Override
    public long getTotalCount() {
        try {
            return erectionDrawingEntryRepository.count();
        } catch (Exception e) {
            logger.error("Error getting total count", e);
            throw new RuntimeException("Failed to get total count: " + e.getMessage(), e);
        }
    }

    @Override
    public BigDecimal getSumMarkedQtyByDrawingNo(String drawingNo) {
        try {
            return erectionDrawingEntryRepository.sumMarkedQtyByDrawingNo(drawingNo);
        } catch (Exception e) {
            logger.error("Error getting sum of marked quantities: {}", drawingNo, e);
            throw new RuntimeException("Failed to get sum: " + e.getMessage(), e);
        }
    }

    @Override
    public BigDecimal getSumTotalMarkedWgtByDrawingNo(String drawingNo) {
        try {
            return erectionDrawingEntryRepository.sumTotalMarkedWgtByDrawingNo(drawingNo);
        } catch (Exception e) {
            logger.error("Error getting sum of total marked weights: {}", drawingNo, e);
            throw new RuntimeException("Failed to get sum: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getDistinctDrawingNumbers() {
        try {
            return erectionDrawingEntryRepository.findDistinctDrawingNumbers();
        } catch (Exception e) {
            logger.error("Error getting distinct drawing numbers", e);
            throw new RuntimeException("Failed to get distinct drawing numbers: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getDistinctMarkNumbers() {
        try {
            return erectionDrawingEntryRepository.findDistinctMarkNumbers();
        } catch (Exception e) {
            logger.error("Error getting distinct mark numbers", e);
            throw new RuntimeException("Failed to get distinct mark numbers: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getDistinctSessionCodes() {
        try {
            return erectionDrawingEntryRepository.findDistinctSessionCodes();
        } catch (Exception e) {
            logger.error("Error getting distinct session codes", e);
            throw new RuntimeException("Failed to get distinct session codes: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getDistinctStatuses() {
        try {
            return erectionDrawingEntryRepository.findDistinctStatuses();
        } catch (Exception e) {
            logger.error("Error getting distinct statuses", e);
            throw new RuntimeException("Failed to get distinct statuses: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsById(String lineId) {
        try {
            return erectionDrawingEntryRepository.existsById(lineId);
        } catch (Exception e) {
            logger.error("Error checking existence by line ID: {}", lineId, e);
            throw new RuntimeException("Failed to check existence: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsByDrawingNoAndMarkNo(String drawingNo, String markNo) {
        try {
            return erectionDrawingEntryRepository.existsByDrawingNoAndMarkNo(drawingNo, markNo);
        } catch (Exception e) {
            logger.error("Error checking existence by drawing and mark number", e);
            throw new RuntimeException("Failed to check existence: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsByDrawingNoAndMarkNoAndStatus(String drawingNo, String markNo, String status) {
        try {
            return erectionDrawingEntryRepository.existsByDrawingNoAndMarkNoAndStatus(drawingNo, markNo, status);
        } catch (Exception e) {
            logger.error("Error checking existence by drawing, mark number and status", e);
            throw new RuntimeException("Failed to check existence: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<ErectionDrawingEntryDto> getLatestByDrawingNo(String drawingNo) {
        try {
            Optional<ErectionDrawingEntry> entity = erectionDrawingEntryRepository.findTopByDrawingNoOrderByCreationDateDesc(drawingNo);
            return entity.map(this::convertEntityToDto);
        } catch (Exception e) {
            logger.error("Error getting latest entry by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to get latest entry: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<ErectionDrawingEntryDto> getLatestByMarkNo(String markNo) {
        try {
            Optional<ErectionDrawingEntry> entity = erectionDrawingEntryRepository.findTopByMarkNoOrderByCreationDateDesc(markNo);
            return entity.map(this::convertEntityToDto);
        } catch (Exception e) {
            logger.error("Error getting latest entry by mark number: {}", markNo, e);
            throw new RuntimeException("Failed to get latest entry: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<ErectionDrawingEntryDto> getLatestByStatus(String status) {
        try {
            Optional<ErectionDrawingEntry> entity = erectionDrawingEntryRepository.findTopByStatusOrderByCreationDateDesc(status);
            return entity.map(this::convertEntityToDto);
        } catch (Exception e) {
            logger.error("Error getting latest entry by status: {}", status, e);
            throw new RuntimeException("Failed to get latest entry: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ErectionDrawingEntryDto> getUniqueDrawingMarkCombinations() {
        try {
            logger.info("Fetching unique drawing-mark combinations");
            List<ErectionDrawingEntry> entities = erectionDrawingEntryRepository.findUniqueDrawingMarkCombinations();
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching unique combinations", e);
            throw new RuntimeException("Failed to fetch unique combinations: " + e.getMessage(), e);
        }
    }

    // Helper methods for conversion between Entity and DTO
    private ErectionDrawingEntry convertDtoToEntity(ErectionDrawingEntryDto dto) {
        ErectionDrawingEntry entity = new ErectionDrawingEntry();
        
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
        if (dto.getAttribute4N() != null) entity.setAttribute4N(dto.getAttribute5N());
        if (dto.getAttribute5N() != null) entity.setAttribute5N(dto.getAttribute5N());
        if (dto.getAttribute1D() != null) entity.setAttribute1D(dto.getAttribute1D());
        if (dto.getAttribute2D() != null) entity.setAttribute2D(dto.getAttribute2D());
        if (dto.getAttribute3D() != null) entity.setAttribute3D(dto.getAttribute3D());
        if (dto.getAttribute4D() != null) entity.setAttribute4D(dto.getAttribute4D());
        if (dto.getAttribute5D() != null) entity.setAttribute5D(dto.getAttribute5D());
        
        return entity;
    }

    private ErectionDrawingEntryDto convertEntityToDto(ErectionDrawingEntry entity) {
        ErectionDrawingEntryDto dto = new ErectionDrawingEntryDto();
        
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
        
        return dto;
    }

    private void updateEntityFromDto(ErectionDrawingEntry entity, ErectionDrawingEntryDto dto) {
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
    }

    /**
     * Generate a sequential line ID in format 1.1, 1.2, 1.3, etc.
     * This ensures unique, sequential serial numbers for erection entries
     */
    private synchronized String generateSequentialLineId() {
        // Get the current maximum line ID from database
        long maxId = getMaxLineIdNumber();
        long nextId = Math.max(maxId + 1, lineIdCounter.get());
        
        // Update the counter
        lineIdCounter.set(nextId + 1);
        
        // Generate format like E1.1, E1.2, etc. (E for Erection)
        long major = (nextId - 1) / 10 + 1;
        long minor = (nextId - 1) % 10 + 1;
        
        return "E" + major + "." + minor;
    }
    
    /**
     * Get the maximum line ID number from existing records
     */
    private long getMaxLineIdNumber() {
        try {
            List<ErectionDrawingEntry> allEntries = erectionDrawingEntryRepository.findAll();
            long maxNumber = 0;
            
            for (ErectionDrawingEntry entry : allEntries) {
                String lineId = entry.getLineId();
                if (lineId != null && lineId.matches("E\\d+\\.\\d+")) {
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
