package com.bellaryinfotech.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bellaryinfotech.DTO.FabricationDrawingEntryDto;
import com.bellaryinfotech.model.FabricationDrawingEntry;
import com.bellaryinfotech.repo.FabricationDrawingEntryRepository;
import com.bellaryinfotech.service.FabricationDrawingEntryService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FabricationDrawingEntryServiceImpl implements FabricationDrawingEntryService {

    @Autowired
    private FabricationDrawingEntryRepository fabricationDrawingEntryRepository;

    @Override
    public List<FabricationDrawingEntryDto> createFabricationDrawingEntries(List<FabricationDrawingEntryDto> fabricationEntries) {
        try {
            List<FabricationDrawingEntry> entities = fabricationEntries.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());

            List<FabricationDrawingEntry> savedEntities = fabricationDrawingEntryRepository.saveAll(entities);

            return savedEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error creating fabrication drawing entries: " + e.getMessage(), e);
        }
    }

    @Override
    public List<FabricationDrawingEntryDto> getFabricationEntriesByWorkOrderAndBuilding(String workOrder, String buildingName) {
        try {
            List<FabricationDrawingEntry> entities = fabricationDrawingEntryRepository.findByWorkOrderAndBuildingName(workOrder, buildingName);
            return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching fabrication entries by work order and building: " + e.getMessage(), e);
        }
    }

    @Override
    public List<FabricationDrawingEntryDto> getFabricationEntriesByDrawingAndMark(String drawingNo, String markNo) {
        try {
            List<FabricationDrawingEntry> entities = fabricationDrawingEntryRepository.findByDrawingNoAndMarkNo(drawingNo, markNo);
            return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching fabrication entries by drawing and mark: " + e.getMessage(), e);
        }
    }

    @Override
    public List<FabricationDrawingEntryDto> getFabricationEntriesByOrderId(Long orderId) {
        try {
            List<FabricationDrawingEntry> entities = fabricationDrawingEntryRepository.findByOrderId(orderId);
            return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching fabrication entries by order ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<FabricationDrawingEntryDto> getAllFabricationEntries() {
        try {
            List<FabricationDrawingEntry> entities = fabricationDrawingEntryRepository.findAll();
            return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all fabrication entries: " + e.getMessage(), e);
        }
    }

    @Override
    public FabricationDrawingEntryDto getFabricationEntryById(Long id) {
        try {
            FabricationDrawingEntry entity = fabricationDrawingEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fabrication entry not found with ID: " + id));
            return convertToDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching fabrication entry by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteFabricationEntry(Long id) {
        try {
            if (!fabricationDrawingEntryRepository.existsById(id)) {
                throw new RuntimeException("Fabrication entry not found with ID: " + id);
            }
            fabricationDrawingEntryRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting fabrication entry: " + e.getMessage(), e);
        }
    }

    // Helper methods for conversion
    private FabricationDrawingEntry convertToEntity(FabricationDrawingEntryDto dto) {
        FabricationDrawingEntry entity = new FabricationDrawingEntry();
        entity.setId(dto.getId());
        entity.setWorkOrder(dto.getWorkOrder());
        entity.setBuildingName(dto.getBuildingName());
        entity.setDrawingNo(dto.getDrawingNo());
        entity.setMarkNo(dto.getMarkNo());
        entity.setRaNo(dto.getRaNo());
        entity.setOrderId(dto.getOrderId());
        entity.setLineId(dto.getLineId());
        entity.setSessionCode(dto.getSessionCode());
        entity.setSessionName(dto.getSessionName());
        entity.setMarkedQty(dto.getMarkedQty());
        entity.setTotalMarkedWgt(dto.getTotalMarkedWgt());
        entity.setSessionWeight(dto.getSessionWeight());
        entity.setWidth(dto.getWidth());
        entity.setLength(dto.getLength());
        entity.setItemQty(dto.getItemQty());
        entity.setItemWeight(dto.getItemWeight());
        entity.setTotalItemWeight(dto.getTotalItemWeight());
        entity.setCuttingStage(dto.getCuttingStage());
        entity.setFitUpStage(dto.getFitUpStage());
        entity.setWeldingStage(dto.getWeldingStage());
        entity.setFinishingStage(dto.getFinishingStage());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setLastUpdatedBy(dto.getLastUpdatedBy());
        entity.setItemNo(dto.getItemNo());

        if (dto.getCreationDate() != null) {
            entity.setCreationDate(dto.getCreationDate());
        }
        if (dto.getLastUpdateDate() != null) {
            entity.setLastUpdateDate(dto.getLastUpdateDate());
        }

        return entity;
    }

    private FabricationDrawingEntryDto convertToDto(FabricationDrawingEntry entity) {
        FabricationDrawingEntryDto dto = new FabricationDrawingEntryDto();
        dto.setId(entity.getId());
        dto.setWorkOrder(entity.getWorkOrder());
        dto.setBuildingName(entity.getBuildingName());
        dto.setDrawingNo(entity.getDrawingNo());
        dto.setMarkNo(entity.getMarkNo());
        dto.setRaNo(entity.getRaNo());
        dto.setOrderId(entity.getOrderId());
        dto.setLineId(entity.getLineId());
        dto.setSessionCode(entity.getSessionCode());
        dto.setSessionName(entity.getSessionName());
        dto.setMarkedQty(entity.getMarkedQty());
        dto.setTotalMarkedWgt(entity.getTotalMarkedWgt());
        dto.setSessionWeight(entity.getSessionWeight());
        dto.setWidth(entity.getWidth());
        dto.setLength(entity.getLength());
        dto.setItemQty(entity.getItemQty());
        dto.setItemWeight(entity.getItemWeight());
        dto.setTotalItemWeight(entity.getTotalItemWeight());
        dto.setCuttingStage(entity.getCuttingStage());
        dto.setFitUpStage(entity.getFitUpStage());
        dto.setWeldingStage(entity.getWeldingStage());
        dto.setFinishingStage(entity.getFinishingStage());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setLastUpdatedBy(entity.getLastUpdatedBy());
        dto.setCreationDate(entity.getCreationDate());
        dto.setLastUpdateDate(entity.getLastUpdateDate());
        dto.setItemNo(entity.getItemNo());
        return dto;
    }

    @Override
    public List<FabricationDrawingEntryDto> getFabricationEntriesByMultipleFilters(String workOrder, String buildingName, String drawingNo, String markNo) {
        try {
            List<FabricationDrawingEntry> entities = fabricationDrawingEntryRepository.findByMultipleFilters(workOrder, buildingName, drawingNo, markNo);
            return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching fabrication entries by multiple filters: " + e.getMessage(), e);
        }
    }

    @Override
    public Long getCountByWorkOrder(String workOrder) {
        try {
            return fabricationDrawingEntryRepository.countByWorkOrder(workOrder);
        } catch (Exception e) {
            throw new RuntimeException("Error getting count by work order: " + e.getMessage(), e);
        }
    }

    @Override
    public Long getCountByBuildingName(String buildingName) {
        try {
            return fabricationDrawingEntryRepository.countByBuildingName(buildingName);
        } catch (Exception e) {
            throw new RuntimeException("Error getting count by building name: " + e.getMessage(), e);
        }
    }

    @Override
    public Long getCountByDrawingAndMark(String drawingNo, String markNo) {
        try {
            return fabricationDrawingEntryRepository.countByDrawingNoAndMarkNo(drawingNo, markNo);
        } catch (Exception e) {
            throw new RuntimeException("Error getting count by drawing and mark: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsByLineId(Long lineId) {
        try {
            return fabricationDrawingEntryRepository.existsByLineId(lineId);
        } catch (Exception e) {
            throw new RuntimeException("Error checking existence by line ID: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void deleteByLineId(Long lineId) {
        try {
            fabricationDrawingEntryRepository.deleteByLineId(lineId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting by line ID: " + e.getMessage(), e);
        }
    }

    // NEW: Implementation for getting distinct RA numbers
    @Override
    public List<String> getDistinctRaNumbers() {
        try {
            return fabricationDrawingEntryRepository.findDistinctRaNo();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching distinct RA numbers: " + e.getMessage(), e);
        }
    }

    // NEW: Implementation for getting distinct drawing numbers by order ID
    @Override
    public List<String> getDistinctDrawingNumbersByOrderId(Long orderId) {
        try {
            return fabricationDrawingEntryRepository.findDistinctDrawingNoByOrderId(orderId);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching distinct drawing numbers by order ID: " + e.getMessage(), e);
        }
    }

    // NEW: Implementation for getting distinct mark numbers by order ID
    @Override
    public List<String> getDistinctMarkNumbersByOrderId(Long orderId) {
        try {
            return fabricationDrawingEntryRepository.findDistinctMarkNoByOrderId(orderId);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching distinct mark numbers by order ID: " + e.getMessage(), e);
        }
    }

    // NEW: Implementation for getting total marked weight by drawing number and mark number
    @Override
    public Double getTotalMarkedWeightByDrawingAndMark(Long orderId, String drawingNo, String markNo) {
        try {
            return fabricationDrawingEntryRepository.sumTotalMarkedWgtByOrderIdAndDrawingNoAndMarkNo(orderId, drawingNo, markNo);
        } catch (Exception e) {
            throw new RuntimeException("Error getting total marked weight by drawing and mark: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<String> getDistinctItemNumbers() {
        try {
            return fabricationDrawingEntryRepository.findDistinctItemNo();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching distinct item numbers: " + e.getMessage(), e);
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public List<FabricationDrawingEntryDto> getFabricationEntriesByWorkOrderAndRaNo(String workOrder, String raNo) {
        List<FabricationDrawingEntry> entities = fabricationDrawingEntryRepository.findByWorkOrderAndRaNo(workOrder, raNo);
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<FabricationDrawingEntryDto> getFabricationEntriesByRaNo(String raNo) {
        List<FabricationDrawingEntry> entities = fabricationDrawingEntryRepository.findByRaNo(raNo);
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<String> getRaNumbersByWorkOrder(String workOrder) {
        return fabricationDrawingEntryRepository.findDistinctRaNoByWorkOrder(workOrder);
    }

    public List<String> getServiceDescriptionsByWorkOrder(String workOrder) {
        // This might need to be implemented based on your service description logic
        // For now, returning session names as service descriptions
        return fabricationDrawingEntryRepository.findDistinctSessionNameByWorkOrder(workOrder);
    }

    
}
