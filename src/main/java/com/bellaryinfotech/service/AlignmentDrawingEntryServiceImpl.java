package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.AlignmentDrawingEntryDto;
import com.bellaryinfotech.model.AlignmentDrawingEntry;
import com.bellaryinfotech.repo.AlignmentDrawingEntryRepository;
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
public class AlignmentDrawingEntryServiceImpl implements AlignmentDrawingEntryService {

    private static final Logger logger = LoggerFactory.getLogger(AlignmentDrawingEntryServiceImpl.class);

    @Autowired
    private AlignmentDrawingEntryRepository alignmentDrawingEntryRepository;

    @Override
    public List<AlignmentDrawingEntryDto> createAlignmentEntry(AlignmentDrawingEntryDto alignmentEntryDto) {
        try {
            logger.info("Creating alignment entry with marked quantity: {}", alignmentEntryDto.getMarkedQty());
            
            if (alignmentEntryDto.getMarkedQty() == null) {
                alignmentEntryDto.setMarkedQty(BigDecimal.ONE);
            }
            
            if (alignmentEntryDto.getMarkedQty().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Marked quantity must be greater than zero");
            }

            List<AlignmentDrawingEntry> entriesToSave = new ArrayList<>();
            int markedQtyInt = alignmentEntryDto.getMarkedQty().intValue();

            for (int i = 0; i < markedQtyInt; i++) {
                AlignmentDrawingEntry entry = convertDtoToEntity(alignmentEntryDto);
                
                // DON'T SET LINE_ID - LET DATABASE AUTO-GENERATE IT
                entry.setLineId(null);
                
                entry.setCreationDate(LocalDateTime.now());
                entry.setLastUpdatingDate(LocalDateTime.now());
                entry.setStatus("alignment");
                
                entry.setMarkedQty(BigDecimal.ONE);
                
                entriesToSave.add(entry);
            }

            List<AlignmentDrawingEntry> savedEntries = alignmentDrawingEntryRepository.saveAll(entriesToSave);
            
            logger.info("Successfully created {} alignment entries", savedEntries.size());
            
            return savedEntries.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
                
        } catch (Exception e) {
            logger.error("Error creating alignment entry", e);
            throw new RuntimeException("Failed to create alignment entry: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AlignmentDrawingEntryDto> createAlignmentEntries(List<AlignmentDrawingEntryDto> alignmentEntryDtos) {
        try {
            logger.info("Creating {} alignment entries - NO DUPLICATE CHECKING", alignmentEntryDtos.size());
        
            List<AlignmentDrawingEntry> allEntriesToSave = new ArrayList<>();
        
            for (AlignmentDrawingEntryDto dto : alignmentEntryDtos) {
                if (dto.getMarkedQty() == null || dto.getMarkedQty().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException("Marked quantity must be greater than zero for drawing: " + dto.getDrawingNo());
                }

                // NO DUPLICATE CHECKING - CREATE ALL ENTRIES
                AlignmentDrawingEntry entry = convertDtoToEntity(dto);
            
                // DON'T SET LINE_ID - LET DATABASE AUTO-GENERATE IT
                entry.setLineId(null);
            
                entry.setCreationDate(LocalDateTime.now());
                entry.setLastUpdatingDate(LocalDateTime.now());
                entry.setStatus("alignment");
            
                entry.setMarkedQty(dto.getMarkedQty());
            
                allEntriesToSave.add(entry);
            }

            // SAVE ALL ENTRIES - NO FILTERING
            List<AlignmentDrawingEntry> savedEntries = alignmentDrawingEntryRepository.saveAll(allEntriesToSave);
        
            logger.info("Successfully created {} alignment entries - ALL ENTRIES STORED", savedEntries.size());
        
            return savedEntries.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
                
        } catch (Exception e) {
            logger.error("Error creating alignment entries", e);
            throw new RuntimeException("Failed to create alignment entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<AlignmentDrawingEntryDto> getAlignmentEntryById(Long lineId) {
        try {
            logger.info("Fetching alignment entry by line ID: {}", lineId);
            Optional<AlignmentDrawingEntry> entity = alignmentDrawingEntryRepository.findById(lineId);
            return entity.map(this::convertEntityToDto);
        } catch (Exception e) {
            logger.error("Error fetching alignment entry by ID: {}", lineId, e);
            throw new RuntimeException("Failed to fetch alignment entry: " + e.getMessage(), e);
        }
    }

    @Override
    public Page<AlignmentDrawingEntryDto> getAllAlignmentEntries(Pageable pageable) {
        try {
            logger.info("Fetching all alignment entries with pagination");
            Page<AlignmentDrawingEntry> entities = alignmentDrawingEntryRepository.findAll(pageable);
            List<AlignmentDrawingEntryDto> dtos = entities.getContent().stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
            return new PageImpl<>(dtos, pageable, entities.getTotalElements());
        } catch (Exception e) {
            logger.error("Error fetching all alignment entries", e);
            throw new RuntimeException("Failed to fetch alignment entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AlignmentDrawingEntryDto> getAllAlignmentEntries() {
        try {
            logger.info("Fetching all alignment entries");
            List<AlignmentDrawingEntry> entities = alignmentDrawingEntryRepository.findAll();
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching all alignment entries", e);
            throw new RuntimeException("Failed to fetch alignment entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AlignmentDrawingEntryDto> getAlignmentEntriesByDrawingNo(String drawingNo) {
        try {
            logger.info("Fetching alignment entries by drawing number: {}", drawingNo);
            List<AlignmentDrawingEntry> entities = alignmentDrawingEntryRepository.findByDrawingNoOrderByCreationDateDesc(drawingNo);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching alignment entries by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to fetch alignment entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AlignmentDrawingEntryDto> getAlignmentEntriesByMarkNo(String markNo) {
        try {
            logger.info("Fetching alignment entries by mark number: {}", markNo);
            List<AlignmentDrawingEntry> entities = alignmentDrawingEntryRepository.findByMarkNoOrderByCreationDateDesc(markNo);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching alignment entries by mark number: {}", markNo, e);
            throw new RuntimeException("Failed to fetch alignment entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AlignmentDrawingEntryDto> getAlignmentEntriesBySessionCode(String sessionCode) {
        try {
            logger.info("Fetching alignment entries by session code: {}", sessionCode);
            List<AlignmentDrawingEntry> entities = alignmentDrawingEntryRepository.findBySessionCodeOrderByCreationDateDesc(sessionCode);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching alignment entries by session code: {}", sessionCode, e);
            throw new RuntimeException("Failed to fetch alignment entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Page<AlignmentDrawingEntryDto> getAlignmentEntriesByTenantId(String tenantId, Pageable pageable) {
        try {
            logger.info("Fetching alignment entries by tenant ID: {}", tenantId);
            Page<AlignmentDrawingEntry> entities = alignmentDrawingEntryRepository.findByTenantIdOrderByCreationDateDesc(tenantId, pageable);
            List<AlignmentDrawingEntryDto> dtos = entities.getContent().stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
            return new PageImpl<>(dtos, pageable, entities.getTotalElements());
        } catch (Exception e) {
            logger.error("Error fetching alignment entries by tenant ID: {}", tenantId, e);
            throw new RuntimeException("Failed to fetch alignment entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AlignmentDrawingEntryDto> getAlignmentEntriesByStatus(String status) {
        try {
            logger.info("Fetching alignment entries by status: {}", status);
            List<AlignmentDrawingEntry> entities = alignmentDrawingEntryRepository.findByStatusOrderByCreationDateDesc(status);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching alignment entries by status: {}", status, e);
            throw new RuntimeException("Failed to fetch alignment entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Page<AlignmentDrawingEntryDto> getAlignmentEntriesByStatus(String status, Pageable pageable) {
        try {
            logger.info("Fetching alignment entries by status with pagination: {}", status);
            Page<AlignmentDrawingEntry> entities = alignmentDrawingEntryRepository.findByStatusOrderByCreationDateDesc(status, pageable);
            List<AlignmentDrawingEntryDto> dtos = entities.getContent().stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
            return new PageImpl<>(dtos, pageable, entities.getTotalElements());
        } catch (Exception e) {
            logger.error("Error fetching alignment entries by status: {}", status, e);
            throw new RuntimeException("Failed to fetch alignment entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Page<AlignmentDrawingEntryDto> searchAlignmentEntries(String drawingNo, String markNo, String sessionCode, 
                                                                String tenantId, String status, Pageable pageable) {
        try {
            logger.info("Searching alignment entries with criteria");
            Page<AlignmentDrawingEntry> entities = alignmentDrawingEntryRepository.findByMultipleCriteria(
                    drawingNo, markNo, sessionCode, tenantId, status, pageable);
            List<AlignmentDrawingEntryDto> dtos = entities.getContent().stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
            return new PageImpl<>(dtos, pageable, entities.getTotalElements());
        } catch (Exception e) {
            logger.error("Error searching alignment entries", e);
            throw new RuntimeException("Failed to search alignment entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AlignmentDrawingEntryDto> getAlignmentEntriesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            logger.info("Fetching alignment entries by date range: {} to {}", startDate, endDate);
            List<AlignmentDrawingEntry> entities = alignmentDrawingEntryRepository.findByCreationDateBetweenOrderByCreationDateDesc(startDate, endDate);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching alignment entries by date range", e);
            throw new RuntimeException("Failed to fetch alignment entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AlignmentDrawingEntryDto> getAlignmentEntriesByMarkedQtyGreaterThan(BigDecimal markedQty) {
        try {
            logger.info("Fetching alignment entries with marked quantity greater than: {}", markedQty);
            List<AlignmentDrawingEntry> entities = alignmentDrawingEntryRepository.findByMarkedQtyGreaterThanOrderByMarkedQtyDesc(markedQty);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching alignment entries by marked quantity", e);
            throw new RuntimeException("Failed to fetch alignment entries: " + e.getMessage(), e);
        }
    }

    @Override
    public AlignmentDrawingEntryDto updateAlignmentEntry(Long lineId, AlignmentDrawingEntryDto alignmentEntryDto) {
        try {
            logger.info("Updating alignment entry with line ID: {}", lineId);
            
            Optional<AlignmentDrawingEntry> existingEntityOpt = alignmentDrawingEntryRepository.findById(lineId);
            if (!existingEntityOpt.isPresent()) {
                throw new RuntimeException("Alignment entry not found with line ID: " + lineId);
            }
            
            AlignmentDrawingEntry existingEntity = existingEntityOpt.get();
            updateEntityFromDto(existingEntity, alignmentEntryDto);
            existingEntity.setLastUpdatingDate(LocalDateTime.now());
            
            AlignmentDrawingEntry savedEntity = alignmentDrawingEntryRepository.save(existingEntity);
            return convertEntityToDto(savedEntity);
        } catch (Exception e) {
            logger.error("Error updating alignment entry: {}", lineId, e);
            throw new RuntimeException("Failed to update alignment entry: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteAlignmentEntry(Long lineId) {
        try {
            logger.info("Deleting alignment entry with line ID: {}", lineId);
            if (!alignmentDrawingEntryRepository.existsById(lineId)) {
                throw new RuntimeException("Alignment entry not found with line ID: " + lineId);
            }
            alignmentDrawingEntryRepository.deleteById(lineId);
        } catch (Exception e) {
            logger.error("Error deleting alignment entry: {}", lineId, e);
            throw new RuntimeException("Failed to delete alignment entry: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteAlignmentEntriesByDrawingNo(String drawingNo) {
        try {
            logger.info("Deleting alignment entries by drawing number: {}", drawingNo);
            alignmentDrawingEntryRepository.deleteByDrawingNo(drawingNo);
        } catch (Exception e) {
            logger.error("Error deleting alignment entries by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to delete alignment entries: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteAlignmentEntriesByMarkNo(String markNo) {
        try {
            logger.info("Deleting alignment entries by mark number: {}", markNo);
            alignmentDrawingEntryRepository.deleteByMarkNo(markNo);
        } catch (Exception e) {
            logger.error("Error deleting alignment entries by mark number: {}", markNo, e);
            throw new RuntimeException("Failed to delete alignment entries: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteAlignmentEntriesByStatus(String status) {
        try {
            logger.info("Deleting alignment entries by status: {}", status);
            alignmentDrawingEntryRepository.deleteByStatus(status);
        } catch (Exception e) {
            logger.error("Error deleting alignment entries by status: {}", status, e);
            throw new RuntimeException("Failed to delete alignment entries: " + e.getMessage(), e);
        }
    }

    @Override
    public void bulkDeleteAlignmentEntries(List<Long> lineIds) {
        try {
            logger.info("Bulk deleting {} alignment entries", lineIds.size());
            alignmentDrawingEntryRepository.deleteAllById(lineIds);
        } catch (Exception e) {
            logger.error("Error bulk deleting alignment entries", e);
            throw new RuntimeException("Failed to bulk delete alignment entries: " + e.getMessage(), e);
        }
    }

    @Override
    public Long getCountByDrawingNo(String drawingNo) {
        try {
            return alignmentDrawingEntryRepository.countByDrawingNo(drawingNo);
        } catch (Exception e) {
            logger.error("Error getting count by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to get count: " + e.getMessage(), e);
        }
    }

    @Override
    public Long getCountByMarkNo(String markNo) {
        try {
            return alignmentDrawingEntryRepository.countByMarkNo(markNo);
        } catch (Exception e) {
            logger.error("Error getting count by mark number: {}", markNo, e);
            throw new RuntimeException("Failed to get count: " + e.getMessage(), e);
        }
    }

    @Override
    public Long getCountByStatus(String status) {
        try {
            return alignmentDrawingEntryRepository.countByStatus(status);
        } catch (Exception e) {
            logger.error("Error getting count by status: {}", status, e);
            throw new RuntimeException("Failed to get count: " + e.getMessage(), e);
        }
    }

    @Override
    public long getTotalCount() {
        try {
            return alignmentDrawingEntryRepository.count();
        } catch (Exception e) {
            logger.error("Error getting total count", e);
            throw new RuntimeException("Failed to get total count: " + e.getMessage(), e);
        }
    }

    @Override
    public BigDecimal getSumMarkedQtyByDrawingNo(String drawingNo) {
        try {
            return alignmentDrawingEntryRepository.sumMarkedQtyByDrawingNo(drawingNo);
        } catch (Exception e) {
            logger.error("Error getting sum of marked quantities: {}", drawingNo, e);
            throw new RuntimeException("Failed to get sum: " + e.getMessage(), e);
        }
    }

    @Override
    public BigDecimal getSumTotalMarkedWgtByDrawingNo(String drawingNo) {
        try {
            return alignmentDrawingEntryRepository.sumTotalMarkedWgtByDrawingNo(drawingNo);
        } catch (Exception e) {
            logger.error("Error getting sum of total marked weights: {}", drawingNo, e);
            throw new RuntimeException("Failed to get sum: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getDistinctDrawingNumbers() {
        try {
            return alignmentDrawingEntryRepository.findDistinctDrawingNumbers();
        } catch (Exception e) {
            logger.error("Error getting distinct drawing numbers", e);
            throw new RuntimeException("Failed to get distinct drawing numbers: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getDistinctMarkNumbers() {
        try {
            return alignmentDrawingEntryRepository.findDistinctMarkNumbers();
        } catch (Exception e) {
            logger.error("Error getting distinct mark numbers", e);
            throw new RuntimeException("Failed to get distinct mark numbers: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getDistinctSessionCodes() {
        try {
            return alignmentDrawingEntryRepository.findDistinctSessionCodes();
        } catch (Exception e) {
            logger.error("Error getting distinct session codes", e);
            throw new RuntimeException("Failed to get distinct session codes: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getDistinctStatuses() {
        try {
            return alignmentDrawingEntryRepository.findDistinctStatuses();
        } catch (Exception e) {
            logger.error("Error getting distinct statuses", e);
            throw new RuntimeException("Failed to get distinct statuses: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsById(Long lineId) {
        try {
            return alignmentDrawingEntryRepository.existsById(lineId);
        } catch (Exception e) {
            logger.error("Error checking existence by line ID: {}", lineId, e);
            throw new RuntimeException("Failed to check existence: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<AlignmentDrawingEntryDto> getLatestByDrawingNo(String drawingNo) {
        try {
            Optional<AlignmentDrawingEntry> entity = alignmentDrawingEntryRepository.findTopByDrawingNoOrderByCreationDateDesc(drawingNo);
            return entity.map(this::convertEntityToDto);
        } catch (Exception e) {
            logger.error("Error getting latest entry by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to get latest entry: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<AlignmentDrawingEntryDto> getLatestByMarkNo(String markNo) {
        try {
            Optional<AlignmentDrawingEntry> entity = alignmentDrawingEntryRepository.findTopByMarkNoOrderByCreationDateDesc(markNo);
            return entity.map(this::convertEntityToDto);
        } catch (Exception e) {
            logger.error("Error getting latest entry by mark number: {}", markNo, e);
            throw new RuntimeException("Failed to get latest entry: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<AlignmentDrawingEntryDto> getLatestByStatus(String status) {
        try {
            Optional<AlignmentDrawingEntry> entity = alignmentDrawingEntryRepository.findTopByStatusOrderByCreationDateDesc(status);
            return entity.map(this::convertEntityToDto);
        } catch (Exception e) {
            logger.error("Error getting latest entry by status: {}", status, e);
            throw new RuntimeException("Failed to get latest entry: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AlignmentDrawingEntryDto> getUniqueDrawingMarkCombinations() {
        try {
            logger.info("Fetching unique drawing-mark combinations");
            List<AlignmentDrawingEntry> entities = alignmentDrawingEntryRepository.findUniqueDrawingMarkCombinations();
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching unique combinations", e);
            throw new RuntimeException("Failed to fetch unique combinations: " + e.getMessage(), e);
        }
    }

    // NEW METHODS FOR ORDER_ID AND RA_NO
    @Override
    public List<AlignmentDrawingEntryDto> getAlignmentEntriesByOrderId(Long orderId) {
        try {
            logger.info("Fetching alignment entries by order ID: {}", orderId);
            List<AlignmentDrawingEntry> entities = alignmentDrawingEntryRepository.findByOrderIdOrderByCreationDateDesc(orderId);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching alignment entries by order ID: {}", orderId, e);
            throw new RuntimeException("Failed to fetch alignment entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AlignmentDrawingEntryDto> getAlignmentEntriesByRaNo(String raNo) {
        try {
            logger.info("Fetching alignment entries by RA NO: {}", raNo);
            List<AlignmentDrawingEntry> entities = alignmentDrawingEntryRepository.findByRaNoOrderByCreationDateDesc(raNo);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching alignment entries by RA NO: {}", raNo, e);
            throw new RuntimeException("Failed to fetch alignment entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AlignmentDrawingEntryDto> getAlignmentEntriesByOrderIdAndRaNo(Long orderId, String raNo) {
        try {
            logger.info("Fetching alignment entries by order ID: {} and RA NO: {}", orderId, raNo);
            List<AlignmentDrawingEntry> entities = alignmentDrawingEntryRepository.findByOrderIdAndRaNoOrderByCreationDateDesc(orderId, raNo);
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching alignment entries by order ID and RA NO", e);
            throw new RuntimeException("Failed to fetch alignment entries: " + e.getMessage(), e);
        }
    }

    // Helper methods for conversion between Entity and DTO
    private AlignmentDrawingEntry convertDtoToEntity(AlignmentDrawingEntryDto dto) {
        AlignmentDrawingEntry entity = new AlignmentDrawingEntry();
        
        entity.setLineId(dto.getLineId()); // LONG TYPE
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
        entity.setTotalItemWeight(dto.getTotalItemWeight());
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
        
        // Set the new fields for enhanced data
        if (dto.getDrawingWeight() != null) entity.setDrawingWeight(dto.getDrawingWeight());
        if (dto.getMarkWeight() != null) entity.setMarkWeight(dto.getMarkWeight());
        if (dto.getDrawingReceivedDate() != null) entity.setDrawingReceivedDate(dto.getDrawingReceivedDate());
        if (dto.getTargetDate() != null) entity.setTargetDate(dto.getTargetDate());
        
        // Set fabrication stage fields with default "N" if null
        entity.setCuttingStage(dto.getCuttingStage() != null ? dto.getCuttingStage() : "N");
        entity.setFitUpStage(dto.getFitUpStage() != null ? dto.getFitUpStage() : "N");
        entity.setWeldingStage(dto.getWeldingStage() != null ? dto.getWeldingStage() : "N");
        entity.setFinishingStage(dto.getFinishingStage() != null ? dto.getFinishingStage() : "N");
        
        // SET ORDER_ID AND RA_NO FROM DTO
        entity.setOrderId(dto.getOrderId());
        entity.setRaNo(dto.getRaNo());
        
        return entity;
    }

    private AlignmentDrawingEntryDto convertEntityToDto(AlignmentDrawingEntry entity) {
        AlignmentDrawingEntryDto dto = new AlignmentDrawingEntryDto();
        
        dto.setLineId(entity.getLineId()); // LONG TYPE
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
        dto.setTotalItemWeight(entity.getTotalItemWeight());
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
        
        // Get the new fields for enhanced data
        dto.setDrawingWeight(entity.getDrawingWeight());
        dto.setMarkWeight(entity.getMarkWeight());
        dto.setDrawingReceivedDate(entity.getDrawingReceivedDate());
        dto.setTargetDate(entity.getTargetDate());
        
        // Get fabrication stage fields
        dto.setCuttingStage(entity.getCuttingStage());
        dto.setFitUpStage(entity.getFitUpStage());
        dto.setWeldingStage(entity.getWeldingStage());
        dto.setFinishingStage(entity.getFinishingStage());
        
        // GET ORDER_ID AND RA_NO FROM ENTITY
        dto.setOrderId(entity.getOrderId());
        dto.setRaNo(entity.getRaNo());
        
        return dto;
    }

    private void updateEntityFromDto(AlignmentDrawingEntry entity, AlignmentDrawingEntryDto dto) {
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
        
        // Update the new fields for enhanced data
        if (dto.getDrawingWeight() != null) entity.setDrawingWeight(dto.getDrawingWeight());
        if (dto.getMarkWeight() != null) entity.setMarkWeight(dto.getMarkWeight());
        if (dto.getDrawingReceivedDate() != null) entity.setDrawingReceivedDate(dto.getDrawingReceivedDate());
        if (dto.getTargetDate() != null) entity.setTargetDate(dto.getTargetDate());
        
        // Update fabrication stage fields
        if (dto.getCuttingStage() != null) entity.setCuttingStage(dto.getCuttingStage());
        if (dto.getFitUpStage() != null) entity.setFitUpStage(dto.getFitUpStage());
        if (dto.getWeldingStage() != null) entity.setWeldingStage(dto.getWeldingStage());
        if (dto.getFinishingStage() != null) entity.setFinishingStage(dto.getFinishingStage());
        
        // UPDATE ORDER_ID AND RA_NO
        if (dto.getOrderId() != null) entity.setOrderId(dto.getOrderId());
        if (dto.getRaNo() != null) entity.setRaNo(dto.getRaNo());
    }
}
