package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.SquareMeterFabModuleDto;
import com.bellaryinfotech.model.SquareMeterFabModule;
import com.bellaryinfotech.repo.SquareMeterFabModuleRepository;
import com.bellaryinfotech.service.SquareMeterFabModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SquareMeterFabModuleServiceImpl implements SquareMeterFabModuleService {
    
    private static final Logger LOG = LoggerFactory.getLogger(SquareMeterFabModuleServiceImpl.class);
    
    @Autowired
    private SquareMeterFabModuleRepository squareMeterFabModuleRepository;
    
    @Override
    public SquareMeterFabModuleDto createSquareMeterFabModule(SquareMeterFabModuleDto squareMeterFabModuleDto) {
        LOG.info("Creating SquareMeterFabModule: {}", squareMeterFabModuleDto);
        
        SquareMeterFabModule entity = convertToEntity(squareMeterFabModuleDto);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdatedDate(LocalDateTime.now());
        entity.setStatus("ACTIVE");
        
        SquareMeterFabModule savedEntity = squareMeterFabModuleRepository.save(entity);
        LOG.info("Successfully created SquareMeterFabModule with ID: {}", savedEntity.getId());
        
        return convertToDto(savedEntity);
    }
    
    @Override
    public List<SquareMeterFabModuleDto> createMultipleSquareMeterFabModules(List<SquareMeterFabModuleDto> squareMeterFabModuleDtos) {
        LOG.info("Creating {} SquareMeterFabModule entries", squareMeterFabModuleDtos.size());
        
        List<SquareMeterFabModule> entities = squareMeterFabModuleDtos.stream()
                .map(dto -> {
                    SquareMeterFabModule entity = convertToEntity(dto);
                    entity.setCreatedDate(LocalDateTime.now());
                    entity.setLastUpdatedDate(LocalDateTime.now());
                    entity.setStatus("ACTIVE");
                    return entity;
                })
                .collect(Collectors.toList());
        
        List<SquareMeterFabModule> savedEntities = squareMeterFabModuleRepository.saveAll(entities);
        LOG.info("Successfully created {} SquareMeterFabModule entries", savedEntities.size());
        
        return savedEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SquareMeterFabModuleDto> getAllSquareMeterFabModules() {
        LOG.info("Fetching all SquareMeterFabModule records");
        
        List<SquareMeterFabModule> entities = squareMeterFabModuleRepository.findByStatus("ACTIVE");
        LOG.info("Found {} SquareMeterFabModule records", entities.size());
        
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<SquareMeterFabModuleDto> getSquareMeterFabModuleById(Long id) {
        LOG.info("Fetching SquareMeterFabModule by ID: {}", id);
        
        Optional<SquareMeterFabModule> entity = squareMeterFabModuleRepository.findById(id);
        if (entity.isPresent() && "ACTIVE".equals(entity.get().getStatus())) {
            return Optional.of(convertToDto(entity.get()));
        }
        return Optional.empty();
    }
    
    @Override
    public List<SquareMeterFabModuleDto> getSquareMeterFabModulesByWorkOrder(String workOrder) {
        LOG.info("Fetching SquareMeterFabModule records by work order: {}", workOrder);
        
        List<SquareMeterFabModule> entities = squareMeterFabModuleRepository.findByWorkOrderAndStatus(workOrder, "ACTIVE");
        LOG.info("Found {} SquareMeterFabModule records for work order: {}", entities.size(), workOrder);
        
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SquareMeterFabModuleDto> getSquareMeterFabModulesByWorkOrderAndBuilding(String workOrder, String buildingName) {
        LOG.info("Fetching SquareMeterFabModule records by work order: {} and building: {}", workOrder, buildingName);
        
        List<SquareMeterFabModule> entities = squareMeterFabModuleRepository.findByWorkOrderAndBuildingNameAndStatus(workOrder, buildingName, "ACTIVE");
        LOG.info("Found {} SquareMeterFabModule records", entities.size());
        
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SquareMeterFabModuleDto> getSquareMeterFabModulesByWorkOrderBuildingDrawing(String workOrder, String buildingName, String drawingNo) {
        LOG.info("Fetching SquareMeterFabModule records by work order: {}, building: {}, drawing: {}", workOrder, buildingName, drawingNo);
        
        List<SquareMeterFabModule> entities = squareMeterFabModuleRepository.findByWorkOrderAndBuildingNameAndDrawingNoAndStatus(workOrder, buildingName, drawingNo, "ACTIVE");
        LOG.info("Found {} SquareMeterFabModule records", entities.size());
        
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SquareMeterFabModuleDto> getSquareMeterFabModulesByWorkOrderBuildingDrawingMark(String workOrder, String buildingName, String drawingNo, String markNo) {
        LOG.info("Fetching SquareMeterFabModule records by work order: {}, building: {}, drawing: {}, mark: {}", workOrder, buildingName, drawingNo, markNo);
        
        List<SquareMeterFabModule> entities = squareMeterFabModuleRepository.findByWorkOrderAndBuildingNameAndDrawingNoAndMarkNoAndStatus(workOrder, buildingName, drawingNo, markNo, "ACTIVE");
        LOG.info("Found {} SquareMeterFabModule records", entities.size());
        
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SquareMeterFabModuleDto> getSquareMeterFabModulesByRaNo(String raNo) {
        LOG.info("Fetching SquareMeterFabModule records by RA No: {}", raNo);
        
        List<SquareMeterFabModule> entities = squareMeterFabModuleRepository.findByRaNoAndStatus(raNo, "ACTIVE");
        LOG.info("Found {} SquareMeterFabModule records for RA No: {}", entities.size(), raNo);
        
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SquareMeterFabModuleDto> searchSquareMeterFabModules(String workOrder, String buildingName, String drawingNo, String markNo, String raNo) {
        LOG.info("Searching SquareMeterFabModule records with criteria - workOrder: {}, buildingName: {}, drawingNo: {}, markNo: {}, raNo: {}",
                workOrder, buildingName, drawingNo, markNo, raNo);
        
        List<SquareMeterFabModule> entities = squareMeterFabModuleRepository.searchSquareMeterFabModules(workOrder, buildingName, drawingNo, markNo, raNo);
        LOG.info("Found {} SquareMeterFabModule records matching search criteria", entities.size());
        
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public SquareMeterFabModuleDto updateSquareMeterFabModule(Long id, SquareMeterFabModuleDto squareMeterFabModuleDto) {
        LOG.info("Updating SquareMeterFabModule with ID: {}", id);
        
        Optional<SquareMeterFabModule> existingEntity = squareMeterFabModuleRepository.findById(id);
        if (existingEntity.isPresent() && "ACTIVE".equals(existingEntity.get().getStatus())) {
            SquareMeterFabModule entity = existingEntity.get();
            updateEntityFromDto(entity, squareMeterFabModuleDto);
            entity.setLastUpdatedDate(LocalDateTime.now());
            
            SquareMeterFabModule savedEntity = squareMeterFabModuleRepository.save(entity);
            LOG.info("Successfully updated SquareMeterFabModule with ID: {}", savedEntity.getId());
            
            return convertToDto(savedEntity);
        }
        return null;
    }
    
    @Override
    public SquareMeterFabModuleDto updateSquareMeterFabModuleStages(Long id, String cuttingStage, String fitUpStage, String weldingStage, String finishingStage) {
        LOG.info("Updating SquareMeterFabModule stages for ID: {}", id);
        
        Optional<SquareMeterFabModule> existingEntity = squareMeterFabModuleRepository.findById(id);
        if (existingEntity.isPresent() && "ACTIVE".equals(existingEntity.get().getStatus())) {
            SquareMeterFabModule entity = existingEntity.get();
            
            if (cuttingStage != null) entity.setCuttingStage(cuttingStage);
            if (fitUpStage != null) entity.setFitUpStage(fitUpStage);
            if (weldingStage != null) entity.setWeldingStage(weldingStage);
            if (finishingStage != null) entity.setFinishingStage(finishingStage);
            
            entity.setLastUpdatedDate(LocalDateTime.now());
            
            SquareMeterFabModule savedEntity = squareMeterFabModuleRepository.save(entity);
            LOG.info("Successfully updated SquareMeterFabModule stages for ID: {}", savedEntity.getId());
            
            return convertToDto(savedEntity);
        }
        return null;
    }
    
    @Override
    public boolean deleteSquareMeterFabModule(Long id) {
        LOG.info("Deleting SquareMeterFabModule with ID: {}", id);
        
        if (squareMeterFabModuleRepository.existsById(id)) {
            squareMeterFabModuleRepository.deleteById(id);
            LOG.info("Successfully deleted SquareMeterFabModule with ID: {}", id);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean softDeleteSquareMeterFabModule(Long id) {
        LOG.info("Soft deleting SquareMeterFabModule with ID: {}", id);
        
        Optional<SquareMeterFabModule> existingEntity = squareMeterFabModuleRepository.findById(id);
        if (existingEntity.isPresent()) {
            SquareMeterFabModule entity = existingEntity.get();
            entity.setStatus("DELETED");
            entity.setLastUpdatedDate(LocalDateTime.now());
            
            squareMeterFabModuleRepository.save(entity);
            LOG.info("Successfully soft deleted SquareMeterFabModule with ID: {}", id);
            return true;
        }
        return false;
    }
    
    @Override
    public List<String> getDistinctWorkOrders() {
        LOG.info("Fetching distinct work orders");
        return squareMeterFabModuleRepository.findDistinctWorkOrders();
    }
    
    @Override
    public List<String> getDistinctBuildingNamesByWorkOrder(String workOrder) {
        LOG.info("Fetching distinct building names for work order: {}", workOrder);
        return squareMeterFabModuleRepository.findDistinctBuildingNamesByWorkOrder(workOrder);
    }
    
    @Override
    public List<String> getDistinctDrawingNosByWorkOrderAndBuildingName(String workOrder, String buildingName) {
        LOG.info("Fetching distinct drawing numbers for work order: {} and building: {}", workOrder, buildingName);
        return squareMeterFabModuleRepository.findDistinctDrawingNosByWorkOrderAndBuildingName(workOrder, buildingName);
    }
    
    @Override
    public List<String> getDistinctMarkNosByWorkOrderBuildingNameAndDrawingNo(String workOrder, String buildingName, String drawingNo) {
        LOG.info("Fetching distinct mark numbers for work order: {}, building: {}, drawing: {}", workOrder, buildingName, drawingNo);
        return squareMeterFabModuleRepository.findDistinctMarkNosByWorkOrderBuildingNameAndDrawingNo(workOrder, buildingName, drawingNo);
    }
    
    @Override
    public List<String> getDistinctRaNumbers() {
        LOG.info("Fetching distinct RA numbers");
        return squareMeterFabModuleRepository.findDistinctRaNumbers();
    }
    
    @Override
    public List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder) {
        LOG.info("Fetching distinct service descriptions for work order: {}", workOrder);
        return squareMeterFabModuleRepository.findDistinctServiceDescriptionsByWorkOrder(workOrder);
    }
    
    @Override
    public Map<String, Long> getSquareMeterFabModuleStatistics() {
        LOG.info("Fetching SquareMeterFabModule statistics");
        
        Map<String, Long> statistics = new HashMap<>();
        statistics.put("totalRecords", squareMeterFabModuleRepository.countActiveRecords());
        statistics.put("cuttingCompleted", squareMeterFabModuleRepository.countCuttingCompleted());
        statistics.put("fitUpCompleted", squareMeterFabModuleRepository.countFitUpCompleted());
        statistics.put("weldingCompleted", squareMeterFabModuleRepository.countWeldingCompleted());
        statistics.put("finishingCompleted", squareMeterFabModuleRepository.countFinishingCompleted());
        
        return statistics;
    }
    
    private SquareMeterFabModuleDto convertToDto(SquareMeterFabModule entity) {
        return new SquareMeterFabModuleDto(
                entity.getId(),
                entity.getOrderId(),
                entity.getClientName(),
                entity.getWorkOrder(),
                entity.getBuildingName(),
                entity.getDrawingNo(),
                entity.getMarkNo(),
                entity.getMarkQty(),
                entity.getEachMarkLength(),
                entity.getTotalMarkLength(),
                entity.getRaNo(),
                entity.getItemNo(),
                entity.getSection(),
                entity.getLengthMm(),
                entity.getWidthMm(),
                entity.getItemQty(),
                entity.getTotalArea(),
                entity.getRemarks(),
                entity.getCuttingStage(),
                entity.getFitUpStage(),
                entity.getWeldingStage(),
                entity.getFinishingStage(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getLastUpdatedBy(),
                entity.getLastUpdatedDate(),
                entity.getStatus(),
                entity.getServiceDescription(),
                entity.getUom(),
                entity.getDataModule()
        );
    }

    
    private SquareMeterFabModule convertToEntity(SquareMeterFabModuleDto dto) {
        SquareMeterFabModule entity = new SquareMeterFabModule();
        updateEntityFromDto(entity, dto);
        return entity;
    }
    
    private void updateEntityFromDto(SquareMeterFabModule entity, SquareMeterFabModuleDto dto) {
        if (dto.getOrderId() != null) entity.setOrderId(dto.getOrderId());
        if (dto.getClientName() != null) entity.setClientName(dto.getClientName());
        if (dto.getWorkOrder() != null) entity.setWorkOrder(dto.getWorkOrder());
        if (dto.getBuildingName() != null) entity.setBuildingName(dto.getBuildingName());
        if (dto.getDrawingNo() != null) entity.setDrawingNo(dto.getDrawingNo());
        if (dto.getMarkNo() != null) entity.setMarkNo(dto.getMarkNo());
        if (dto.getMarkQty() != null) entity.setMarkQty(dto.getMarkQty());
        if (dto.getEachMarkLength() != null) entity.setEachMarkLength(dto.getEachMarkLength());
        if (dto.getTotalMarkLength() != null) entity.setTotalMarkLength(dto.getTotalMarkLength());
        if (dto.getRaNo() != null) entity.setRaNo(dto.getRaNo());
        if (dto.getItemNo() != null) entity.setItemNo(dto.getItemNo());
        if (dto.getSection() != null) entity.setSection(dto.getSection());
        if (dto.getLengthMm() != null) entity.setLengthMm(dto.getLengthMm());
        if (dto.getWidthMm() != null) entity.setWidthMm(dto.getWidthMm());
        if (dto.getItemQty() != null) entity.setItemQty(dto.getItemQty());
        if (dto.getTotalArea() != null) entity.setTotalArea(dto.getTotalArea());
        if (dto.getRemarks() != null) entity.setRemarks(dto.getRemarks());
        if (dto.getCuttingStage() != null) entity.setCuttingStage(dto.getCuttingStage());
        if (dto.getFitUpStage() != null) entity.setFitUpStage(dto.getFitUpStage());
        if (dto.getWeldingStage() != null) entity.setWeldingStage(dto.getWeldingStage());
        if (dto.getFinishingStage() != null) entity.setFinishingStage(dto.getFinishingStage());
        if (dto.getCreatedBy() != null) entity.setCreatedBy(dto.getCreatedBy());
        if (dto.getLastUpdatedBy() != null) entity.setLastUpdatedBy(dto.getLastUpdatedBy());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
        if (dto.getServiceDescription() != null) entity.setServiceDescription(dto.getServiceDescription());
        if (dto.getUom() != null) entity.setUom(dto.getUom());
        if (dto.getDataModule() != null) entity.setDataModule(dto.getDataModule());
    }

}
