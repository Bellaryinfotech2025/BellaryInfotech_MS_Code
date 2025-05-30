package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.BitsDrawingEntryDto;
import com.bellaryinfotech.DTO.BitsDrawingEntryStatsDto;
import com.bellaryinfotech.DAO.BitsDrawingEntryDao;
import com.bellaryinfotech.model.BitsDrawingEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Transactional
public class BitsDrawingEntryServiceImpl implements BitsDrawingEntryService {

    private static final Logger logger = LoggerFactory.getLogger(BitsDrawingEntryServiceImpl.class);
    
    // Counter for generating sequential line IDs
    private static final AtomicLong lineIdCounter = new AtomicLong(1);
    private static long currentDrawingGroup = 1;
    private static final Object lockObject = new Object();

    @Autowired
    private BitsDrawingEntryDao bitsDrawingEntryDao;

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
            
            // Get the next drawing group number for this batch
            long drawingGroup;
            synchronized (lockObject) {
                drawingGroup = currentDrawingGroup++;
            }

            // Create entries based on marked quantity
            for (int i = 0; i < markedQtyInt; i++) {
                BitsDrawingEntry entry = convertDtoToEntity(drawingEntryDto);
                
                // Generate sequential line ID in format "group.sequence"
                entry.setLineId(generateSequentialLineId(drawingGroup, i + 1));
                
                // Set creation metadata
                entry.setCreationDate(LocalDateTime.now());
                entry.setLastUpdatingDate(LocalDateTime.now());
                
                // Set marked quantity to 1 for each individual entry
                entry.setMarkedQty(BigDecimal.ONE);
                
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

    // Rest of the service implementation remains the same...

    // Helper methods for conversion between Entity and DTO
    private BitsDrawingEntry convertDtoToEntity(BitsDrawingEntryDto dto) {
        BitsDrawingEntry entity = new BitsDrawingEntry();
        
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
        
        // Fix for attributes - only set if not null
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
        
        // Only set attributes if they are not null
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
        if (dto.getTenantId() != null) entity.setTenantId(dto.getTenantId());
        if (dto.getCreatedBy() != null) entity.setCreatedBy(dto.getCreatedBy());
        if (dto.getLastUpdatedBy() != null) entity.setLastUpdatedBy(dto.getLastUpdatedBy());
        
        // Fix for attributes - only update if not null
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
     * Generate a sequential line ID in the format "group.sequence"
     * For example: "1.1", "1.2", "1.3" for the first group
     * And "2.1", "2.2", "2.3" for the second group
     */
    private String generateSequentialLineId(long groupNumber, long sequenceNumber) {
        return groupNumber + "." + sequenceNumber;
    }

    /**
     * Old method - kept for reference
     * Generate a unique line ID with UUID
     */
    private String generateUniqueLineId() {
        return "BDE-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

	@Override
	public List<BitsDrawingEntryDto> createDrawingEntries(List<BitsDrawingEntryDto> drawingEntryDtos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<BitsDrawingEntryDto> getDrawingEntryById(String lineId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Page<BitsDrawingEntryDto> getAllDrawingEntries(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BitsDrawingEntryDto> getAllDrawingEntries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BitsDrawingEntryDto> getDrawingEntriesByDrawingNo(String drawingNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BitsDrawingEntryDto> getDrawingEntriesByMarkNo(String markNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BitsDrawingEntryDto> getDrawingEntriesBySessionCode(String sessionCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<BitsDrawingEntryDto> getDrawingEntriesByTenantId(String tenantId, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<BitsDrawingEntryDto> searchDrawingEntries(String drawingNo, String markNo, String sessionCode,
			String tenantId, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BitsDrawingEntryDto> getDrawingEntriesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BitsDrawingEntryDto> getDrawingEntriesByMarkedQtyGreaterThan(BigDecimal markedQty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BitsDrawingEntryDto updateDrawingEntry(String lineId, BitsDrawingEntryDto drawingEntryDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteDrawingEntry(String lineId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteDrawingEntriesByDrawingNo(String drawingNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteDrawingEntriesByMarkNo(String markNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getCountByDrawingNo(String drawingNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getSumMarkedQtyByDrawingNo(String drawingNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getSumTotalMarkedWgtByDrawingNo(String drawingNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getDistinctDrawingNumbers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getDistinctMarkNumbers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getDistinctSessionCodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(String lineId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsByDrawingNoAndMarkNo(String drawingNo, String markNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getTotalCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Optional<BitsDrawingEntryDto> getLatestByDrawingNo(String drawingNo) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<BitsDrawingEntryDto> getLatestByMarkNo(String markNo) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void bulkDeleteDrawingEntries(List<String> lineIds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BitsDrawingEntryStatsDto getDrawingEntryStats(String drawingNo) {
		// TODO Auto-generated method stub
		return null;
	}
}