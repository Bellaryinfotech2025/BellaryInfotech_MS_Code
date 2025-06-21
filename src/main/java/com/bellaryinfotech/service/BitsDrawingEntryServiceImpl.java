package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.BitsDrawingEntryDto;
import com.bellaryinfotech.DTO.BitsDrawingEntryStatsDto;
import com.bellaryinfotech.DAO.BitsDrawingEntryDao;
import com.bellaryinfotech.model.BitsDrawingEntry;
import com.bellaryinfotech.repo.BitsDrawingEntryRepository;
import com.bellaryinfotech.repo.BitsHeaderRepository;
import com.bellaryinfotech.model.BitsHeaderAll;
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
import java.util.stream.Collectors;

@Service
@Transactional
public class BitsDrawingEntryServiceImpl implements BitsDrawingEntryService {

    private static final Logger logger = LoggerFactory.getLogger(BitsDrawingEntryServiceImpl.class);

    @Autowired
    private BitsDrawingEntryDao bitsDrawingEntryDao;
    
    @Autowired
    private BitsDrawingEntryRepository bitsDrawingEntryRepository;

    @Autowired
    private BitsHeaderRepository bitsHeaderRepository;

    @Override
    public List<BitsDrawingEntryDto> createDrawingEntry(BitsDrawingEntryDto drawingEntryDto) {
        try {
            logger.info("Creating drawing entry with marked quantity: {}", drawingEntryDto.getMarkedQty());
            
            // Validate input
            if (drawingEntryDto.getMarkedQty() == null || drawingEntryDto.getMarkedQty().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Marked quantity must be greater than zero");
            }

            List<BitsDrawingEntry> entriesToSave = new ArrayList<>();
            int markedQtyInt = drawingEntryDto.getMarkedQty().intValue();

            // Create entries based on marked quantity
            for (int i = 0; i < markedQtyInt; i++) {
                BitsDrawingEntry entry = convertDtoToEntity(drawingEntryDto);
                
                // DO NOT SET lineId - let it auto-generate
                entry.setLineId(null);
                
                // Set creation metadata
                entry.setCreationDate(LocalDateTime.now());
                entry.setLastUpdatingDate(LocalDateTime.now());
                
                // Set marked quantity to 1 for each individual entry
                entry.setMarkedQty(BigDecimal.ONE);
                
                // Set default tenant if not provided
                if (entry.getTenantId() == null || entry.getTenantId().trim().isEmpty()) {
                    entry.setTenantId("DEFAULT");
                }
                
                entriesToSave.add(entry);
            }

            // Save all entries
            List<BitsDrawingEntry> savedEntries = bitsDrawingEntryDao.saveAll(entriesToSave);
            
            logger.info("Successfully created {} drawing entries", savedEntries.size());
            
            // Convert back to DTOs
            return savedEntries.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            logger.error("Error creating drawing entry", e);
            throw new RuntimeException("Failed to create drawing entry: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BitsDrawingEntryDto> createDrawingEntries(List<BitsDrawingEntryDto> drawingEntryDtos) {
        try {
            logger.info("Creating {} drawing entries", drawingEntryDtos.size());
            
            List<BitsDrawingEntry> allEntriesToSave = new ArrayList<>();
            
            for (BitsDrawingEntryDto dto : drawingEntryDtos) {
                // Validate input
                if (dto.getMarkedQty() == null || dto.getMarkedQty().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException("Marked quantity must be greater than zero for drawing: " + dto.getDrawingNo());
                }

                int markedQtyInt = dto.getMarkedQty().intValue();

                // Create entries based on marked quantity
                for (int i = 0; i < markedQtyInt; i++) {
                    BitsDrawingEntry entry = convertDtoToEntity(dto);
                    
                    // DO NOT SET lineId - let it auto-generate
                    entry.setLineId(null);
                    
                    // Set creation metadata
                    entry.setCreationDate(LocalDateTime.now());
                    entry.setLastUpdatingDate(LocalDateTime.now());
                    
                    // Set marked quantity to 1 for each individual entry
                    entry.setMarkedQty(BigDecimal.ONE);
                    
                    // Set default tenant if not provided
                    if (entry.getTenantId() == null || entry.getTenantId().trim().isEmpty()) {
                        entry.setTenantId("DEFAULT");
                    }
                    
                    allEntriesToSave.add(entry);
                }
            }

            // Save all entries
            List<BitsDrawingEntry> savedEntries = bitsDrawingEntryDao.saveAll(allEntriesToSave);
            
            logger.info("Successfully created {} total drawing entries", savedEntries.size());
            
            // Convert back to DTOs
            return savedEntries.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            logger.error("Error creating drawing entries", e);
            throw new RuntimeException("Failed to create drawing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<BitsDrawingEntryDto> getDrawingEntryById(String lineId) {
        try {
            logger.info("Fetching drawing entry by line ID: {}", lineId);
            // Convert String to Long for the new ID type
            Long id = Long.parseLong(lineId);
            Optional<BitsDrawingEntry> entity = bitsDrawingEntryRepository.findById(id);
            return entity.map(this::convertEntityToDto);
        } catch (NumberFormatException e) {
            logger.error("Invalid line ID format: {}", lineId, e);
            throw new RuntimeException("Invalid line ID format: " + lineId, e);
        } catch (Exception e) {
            logger.error("Error fetching drawing entry by ID: {}", lineId, e);
            throw new RuntimeException("Failed to fetch drawing entry: " + e.getMessage(), e);
        }
    }

    @Override
    public Page<BitsDrawingEntryDto> getAllDrawingEntries(Pageable pageable) {
        try {
            logger.info("Fetching all drawing entries with pagination");
            Page<BitsDrawingEntry> entities = bitsDrawingEntryRepository.findAll(pageable);
            List<BitsDrawingEntryDto> dtos = entities.getContent().stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
            return new PageImpl<>(dtos, pageable, entities.getTotalElements());
        } catch (Exception e) {
            logger.error("Error fetching all drawing entries", e);
            throw new RuntimeException("Failed to fetch drawing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BitsDrawingEntryDto> getAllDrawingEntries() {
        try {
            logger.info("Fetching all drawing entries");
            List<BitsDrawingEntry> entities = bitsDrawingEntryRepository.findAll();
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching all drawing entries", e);
            throw new RuntimeException("Failed to fetch drawing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BitsDrawingEntryDto> getDrawingEntriesByDrawingNo(String drawingNo) {
        try {
            logger.info("Fetching drawing entries by drawing number: {}", drawingNo);
            List<BitsDrawingEntry> entities = bitsDrawingEntryRepository.findByDrawingNoOrderByCreationDateDesc(drawingNo);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching drawing entries by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to fetch drawing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BitsDrawingEntryDto> getDrawingEntriesByMarkNo(String markNo) {
        try {
            logger.info("Fetching drawing entries by mark number: {}", markNo);
            List<BitsDrawingEntry> entities = bitsDrawingEntryRepository.findByMarkNoOrderByCreationDateDesc(markNo);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching drawing entries by mark number: {}", markNo, e);
            throw new RuntimeException("Failed to fetch drawing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BitsDrawingEntryDto> getDrawingEntriesBySessionCode(String sessionCode) {
        try {
            logger.info("Fetching drawing entries by session code: {}", sessionCode);
            List<BitsDrawingEntry> entities = bitsDrawingEntryRepository.findBySessionCodeOrderByCreationDateDesc(sessionCode);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching drawing entries by session code: {}", sessionCode, e);
            throw new RuntimeException("Failed to fetch drawing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Page<BitsDrawingEntryDto> getDrawingEntriesByTenantId(String tenantId, Pageable pageable) {
        try {
            logger.info("Fetching drawing entries by tenant ID: {}", tenantId);
            Page<BitsDrawingEntry> entities = bitsDrawingEntryRepository.findByTenantIdOrderByCreationDateDesc(tenantId, pageable);
            List<BitsDrawingEntryDto> dtos = entities.getContent().stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
            return new PageImpl<>(dtos, pageable, entities.getTotalElements());
        } catch (Exception e) {
            logger.error("Error fetching drawing entries by tenant ID: {}", tenantId, e);
            throw new RuntimeException("Failed to fetch drawing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Page<BitsDrawingEntryDto> searchDrawingEntries(String drawingNo, String markNo, String sessionCode, String tenantId, Pageable pageable) {
        try {
            logger.info("Searching drawing entries with criteria");
            Page<BitsDrawingEntry> entities = bitsDrawingEntryRepository.findByMultipleCriteria(drawingNo, markNo, sessionCode, tenantId, null, pageable);
            List<BitsDrawingEntryDto> dtos = entities.getContent().stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
            return new PageImpl<>(dtos, pageable, entities.getTotalElements());
        } catch (Exception e) {
            logger.error("Error searching drawing entries", e);
            throw new RuntimeException("Failed to search drawing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BitsDrawingEntryDto> getDrawingEntriesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            logger.info("Fetching drawing entries by date range: {} to {}", startDate, endDate);
            List<BitsDrawingEntry> entities = bitsDrawingEntryRepository.findByCreationDateBetweenOrderByCreationDateDesc(startDate, endDate);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching drawing entries by date range", e);
            throw new RuntimeException("Failed to fetch drawing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BitsDrawingEntryDto> getDrawingEntriesByMarkedQtyGreaterThan(BigDecimal markedQty) {
        try {
            logger.info("Fetching drawing entries with marked quantity greater than: {}", markedQty);
            List<BitsDrawingEntry> entities = bitsDrawingEntryRepository.findByMarkedQtyGreaterThanOrderByMarkedQtyDesc(markedQty);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching drawing entries by marked quantity", e);
            throw new RuntimeException("Failed to fetch drawing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public BitsDrawingEntryDto updateDrawingEntry(String lineId, BitsDrawingEntryDto drawingEntryDto) {
        try {
            logger.info("Updating drawing entry with line ID: {}", lineId);
            
            // Validate lineId
            if (lineId == null || lineId.trim().isEmpty()) {
                throw new IllegalArgumentException("Line ID cannot be null or empty");
            }
            
            // Convert String to Long for the new ID type
            Long id = Long.parseLong(lineId);
            Optional<BitsDrawingEntry> existingEntityOpt = bitsDrawingEntryRepository.findById(id);
            if (!existingEntityOpt.isPresent()) {
                throw new RuntimeException("Drawing entry not found with line ID: " + lineId);
            }
            
            BitsDrawingEntry existingEntity = existingEntityOpt.get();
            
            // Validate the DTO
            if (drawingEntryDto == null) {
                throw new IllegalArgumentException("Drawing entry data cannot be null");
            }
            
            updateEntityFromDto(existingEntity, drawingEntryDto);
            existingEntity.setLastUpdatingDate(LocalDateTime.now());
            
            BitsDrawingEntry savedEntity = bitsDrawingEntryRepository.save(existingEntity);
            logger.info("Successfully updated drawing entry with line ID: {}", lineId);
            return convertEntityToDto(savedEntity);
        } catch (NumberFormatException e) {
            logger.error("Invalid line ID format: {}", lineId, e);
            throw new RuntimeException("Invalid line ID format: " + lineId, e);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument for updating drawing entry: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error updating drawing entry: {}", lineId, e);
            throw new RuntimeException("Failed to update drawing entry: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteDrawingEntry(String lineId) {
        try {
            logger.info("Deleting drawing entry with line ID: {}", lineId);
            // Convert String to Long for the new ID type
            Long id = Long.parseLong(lineId);
            if (!bitsDrawingEntryRepository.existsById(id)) {
                throw new RuntimeException("Drawing entry not found with line ID: " + lineId);
            }
            bitsDrawingEntryRepository.deleteById(id);
        } catch (NumberFormatException e) {
            logger.error("Invalid line ID format: {}", lineId, e);
            throw new RuntimeException("Invalid line ID format: " + lineId, e);
        } catch (Exception e) {
            logger.error("Error deleting drawing entry: {}", lineId, e);
            throw new RuntimeException("Failed to delete drawing entry: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteDrawingEntriesByDrawingNo(String drawingNo) {
        try {
            logger.info("Deleting drawing entries by drawing number: {}", drawingNo);
            bitsDrawingEntryRepository.deleteByDrawingNo(drawingNo);
        } catch (Exception e) {
            logger.error("Error deleting drawing entries by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to delete drawing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteDrawingEntriesByMarkNo(String markNo) {
        try {
            logger.info("Deleting drawing entries by mark number: {}", markNo);
            bitsDrawingEntryRepository.deleteByMarkNo(markNo);
        } catch (Exception e) {
            logger.error("Error deleting drawing entries by mark number: {}", markNo, e);
            throw new RuntimeException("Failed to delete drawing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Long getCountByDrawingNo(String drawingNo) {
        try {
            return bitsDrawingEntryRepository.countByDrawingNo(drawingNo);
        } catch (Exception e) {
            logger.error("Error getting count by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to get count: " + e.getMessage(), e);
        }
    }

    @Override
    public BigDecimal getSumMarkedQtyByDrawingNo(String drawingNo) {
        try {
            return bitsDrawingEntryRepository.sumMarkedQtyByDrawingNo(drawingNo);
        } catch (Exception e) {
            logger.error("Error getting sum of marked quantities: {}", drawingNo, e);
            throw new RuntimeException("Failed to get sum: " + e.getMessage(), e);
        }
    }

    @Override
    public BigDecimal getSumTotalMarkedWgtByDrawingNo(String drawingNo) {
        try {
            return bitsDrawingEntryRepository.sumTotalMarkedWgtByDrawingNo(drawingNo);
        } catch (Exception e) {
            logger.error("Error getting sum of total marked weights: {}", drawingNo, e);
            throw new RuntimeException("Failed to get sum: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getDistinctDrawingNumbers() {
        try {
            return bitsDrawingEntryRepository.findDistinctDrawingNumbers();
        } catch (Exception e) {
            logger.error("Error getting distinct drawing numbers", e);
            throw new RuntimeException("Failed to get distinct drawing numbers: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getDistinctMarkNumbers() {
        try {
            return bitsDrawingEntryRepository.findDistinctMarkNumbers();
        } catch (Exception e) {
            logger.error("Error getting distinct mark numbers", e);
            throw new RuntimeException("Failed to get distinct mark numbers: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getDistinctSessionCodes() {
        try {
            return bitsDrawingEntryRepository.findDistinctSessionCodes();
        } catch (Exception e) {
            logger.error("Error getting distinct session codes", e);
            throw new RuntimeException("Failed to get distinct session codes: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsById(String lineId) {
        try {
            // Convert String to Long for the new ID type
            Long id = Long.parseLong(lineId);
            return bitsDrawingEntryRepository.existsById(id);
        } catch (NumberFormatException e) {
            logger.error("Invalid line ID format: {}", lineId, e);
            return false;
        } catch (Exception e) {
            logger.error("Error checking existence by line ID: {}", lineId, e);
            throw new RuntimeException("Failed to check existence: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsByDrawingNoAndMarkNo(String drawingNo, String markNo) {
        try {
            return bitsDrawingEntryRepository.existsByDrawingNoAndMarkNo(drawingNo, markNo);
        } catch (Exception e) {
            logger.error("Error checking existence by drawing and mark number", e);
            throw new RuntimeException("Failed to check existence: " + e.getMessage(), e);
        }
    }

    @Override
    public long getTotalCount() {
        try {
            return bitsDrawingEntryRepository.count();
        } catch (Exception e) {
            logger.error("Error getting total count", e);
            throw new RuntimeException("Failed to get total count: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<BitsDrawingEntryDto> getLatestByDrawingNo(String drawingNo) {
        try {
            Optional<BitsDrawingEntry> entity = bitsDrawingEntryRepository.findTopByDrawingNoOrderByCreationDateDesc(drawingNo);
            return entity.map(this::convertEntityToDto);
        } catch (Exception e) {
            logger.error("Error getting latest entry by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to get latest entry: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<BitsDrawingEntryDto> getLatestByMarkNo(String markNo) {
        try {
            Optional<BitsDrawingEntry> entity = bitsDrawingEntryRepository.findTopByMarkNoOrderByCreationDateDesc(markNo);
            return entity.map(this::convertEntityToDto);
        } catch (Exception e) {
            logger.error("Error getting latest entry by mark number: {}", markNo, e);
            throw new RuntimeException("Failed to get latest entry: " + e.getMessage(), e);
        }
    }

    @Override
    public void bulkDeleteDrawingEntries(List<String> lineIds) {
        try {
            logger.info("Bulk deleting {} drawing entries", lineIds.size());
            // Convert String IDs to Long IDs
            List<Long> longIds = lineIds.stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            bitsDrawingEntryRepository.deleteAllById(longIds);
        } catch (NumberFormatException e) {
            logger.error("Invalid line ID format in bulk delete", e);
            throw new RuntimeException("Invalid line ID format in bulk delete", e);
        } catch (Exception e) {
            logger.error("Error bulk deleting drawing entries", e);
            throw new RuntimeException("Failed to bulk delete drawing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public BitsDrawingEntryStatsDto getDrawingEntryStats(String drawingNo) {
        try {
            logger.info("Getting drawing entry statistics for: {}", drawingNo);
            
            Long count = getCountByDrawingNo(drawingNo);
            BigDecimal sumMarkedQty = getSumMarkedQtyByDrawingNo(drawingNo);
            BigDecimal sumTotalMarkedWgt = getSumTotalMarkedWgtByDrawingNo(drawingNo);
            
            BitsDrawingEntryStatsDto stats = new BitsDrawingEntryStatsDto();
            stats.setDrawingNo(drawingNo);
            stats.setTotalEntries(count);
            stats.setTotalMarkedQty(sumMarkedQty);
            stats.setTotalMarkedWeight(sumTotalMarkedWgt);
            
            return stats;
        } catch (Exception e) {
            logger.error("Error getting drawing entry statistics: {}", drawingNo, e);
            throw new RuntimeException("Failed to get statistics: " + e.getMessage(), e);
        }
    }

    // Helper methods for conversion between Entity and DTO
    private BitsDrawingEntry convertDtoToEntity(BitsDrawingEntryDto dto) {
        BitsDrawingEntry entity = new BitsDrawingEntry();
        
        // DO NOT SET lineId from DTO - let it auto-generate
        // entity.setLineId(dto.getLineId()); // REMOVED THIS LINE
        
        entity.setVersion(dto.getVersion());
        entity.setOrderId(dto.getOrderId()); // NEW FIELD
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
        entity.setTotalItemWeight(dto.getTotalItemWeight());
        
        // Set default tenant if not provided
        String tenantId = dto.getTenantId();
        if (tenantId == null || tenantId.trim().isEmpty()) {
            tenantId = "DEFAULT";
        }
        entity.setTenantId(tenantId);
        
        entity.setCreationDate(dto.getCreationDate());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setLastUpdatingDate(dto.getLastUpdatingDate());
        entity.setLastUpdatedBy(dto.getLastUpdatedBy());

        // Set new fields
        entity.setDrawingWeight(dto.getDrawingWeight());
        entity.setMarkWeight(dto.getMarkWeight());
        entity.setDrawingReceivedDate(dto.getDrawingReceivedDate());
        entity.setTargetDate(dto.getTargetDate());
        
        // Set PO Line Reference ID if provided
        if (dto.getPoLineReferenceId() != null) {
            entity.setPoLineReferenceId(dto.getPoLineReferenceId());
        }
        
        // Set fabrication stages
        entity.setCuttingStage(dto.getCuttingStage() != null ? dto.getCuttingStage() : "N");
        entity.setFitUpStage(dto.getFitUpStage() != null ? dto.getFitUpStage() : "N");
        entity.setWeldingStage(dto.getWeldingStage() != null ? dto.getWeldingStage() : "N");
        entity.setFinishingStage(dto.getFinishingStage() != null ? dto.getFinishingStage() : "N");
        
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
        
        return entity;
    }

    private BitsDrawingEntryDto convertEntityToDto(BitsDrawingEntry entity) {
        BitsDrawingEntryDto dto = new BitsDrawingEntryDto();
        
        dto.setLineId(entity.getLineId());
        dto.setVersion(entity.getVersion());
        dto.setOrderId(entity.getOrderId()); // NEW FIELD
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
        dto.setTotalItemWeight(entity.getTotalItemWeight());
        dto.setTenantId(entity.getTenantId());
        dto.setCreationDate(entity.getCreationDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setLastUpdatingDate(entity.getLastUpdatingDate());
        dto.setLastUpdatedBy(entity.getLastUpdatedBy());
        
        // Set new fields
        dto.setDrawingWeight(entity.getDrawingWeight());
        dto.setMarkWeight(entity.getMarkWeight());
        dto.setDrawingReceivedDate(entity.getDrawingReceivedDate());
        dto.setTargetDate(entity.getTargetDate());
        
        // Set PO Line Reference ID if available
        dto.setPoLineReferenceId(entity.getPoLineReferenceId());
        
        // Set fabrication stages
        dto.setCuttingStage(entity.getCuttingStage() != null ? entity.getCuttingStage() : "N");
        dto.setFitUpStage(entity.getFitUpStage() != null ? entity.getFitUpStage() : "N");
        dto.setWeldingStage(entity.getWeldingStage() != null ? entity.getWeldingStage() : "N");
        dto.setFinishingStage(entity.getFinishingStage() != null ? entity.getFinishingStage() : "N");
        
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

    private void updateEntityFromDto(BitsDrawingEntry entity, BitsDrawingEntryDto dto) {
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
        if (dto.getTotalItemWeight() != null) entity.setTotalItemWeight(dto.getTotalItemWeight());
        if (dto.getOrderId() != null) entity.setOrderId(dto.getOrderId()); // NEW FIELD
        
        // Handle tenant ID with default
        if (dto.getTenantId() != null) {
            entity.setTenantId(dto.getTenantId());
        } else if (entity.getTenantId() == null || entity.getTenantId().trim().isEmpty()) {
            entity.setTenantId("DEFAULT");
        }
        
        if (dto.getCreatedBy() != null) entity.setCreatedBy(dto.getCreatedBy());
        if (dto.getLastUpdatedBy() != null) entity.setLastUpdatedBy(dto.getLastUpdatedBy());
        
        // Update new fields
        if (dto.getDrawingWeight() != null) entity.setDrawingWeight(dto.getDrawingWeight());
        if (dto.getMarkWeight() != null) entity.setMarkWeight(dto.getMarkWeight());
        if (dto.getDrawingReceivedDate() != null) entity.setDrawingReceivedDate(dto.getDrawingReceivedDate());
        if (dto.getTargetDate() != null) entity.setTargetDate(dto.getTargetDate());
        
        // Update PO Line Reference ID if provided
        if (dto.getPoLineReferenceId() != null) entity.setPoLineReferenceId(dto.getPoLineReferenceId());
        
        // Update fabrication stages
        if (dto.getCuttingStage() != null) entity.setCuttingStage(dto.getCuttingStage());
        if (dto.getFitUpStage() != null) entity.setFitUpStage(dto.getFitUpStage());
        if (dto.getWeldingStage() != null) entity.setWeldingStage(dto.getWeldingStage());
        if (dto.getFinishingStage() != null) entity.setFinishingStage(dto.getFinishingStage());
        
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

    // NEW METHOD: Get order ID from work order
    private Long getOrderIdFromWorkOrder(String workOrder) {
        try {
            if (workOrder == null || workOrder.trim().isEmpty()) {
                return null;
            }
            
            Optional<BitsHeaderAll> headerOpt = bitsHeaderRepository.findByWorkOrder(workOrder);
            if (headerOpt.isPresent()) {
                return headerOpt.get().getOrderId();
            }
            
            logger.warn("No order found for work order: {}", workOrder);
            return null;
        } catch (Exception e) {
            logger.error("Error getting order ID for work order: {}", workOrder, e);
            return null;
        }
    }
}
