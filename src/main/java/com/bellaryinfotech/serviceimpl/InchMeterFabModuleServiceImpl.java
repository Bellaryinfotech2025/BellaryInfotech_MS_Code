package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.InchMeterFabModuleDto;
import com.bellaryinfotech.model.InchMeterFabModule;
import com.bellaryinfotech.repo.InchMeterFabModuleRepository;
import com.bellaryinfotech.service.InchMeterFabModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class InchMeterFabModuleServiceImpl implements InchMeterFabModuleService {
    
    private static final Logger LOG = LoggerFactory.getLogger(InchMeterFabModuleServiceImpl.class);
    private static final String ACTIVE_STATUS = "ACTIVE";
    private static final String DELETED_STATUS = "DELETED";
    
    @Autowired
    private InchMeterFabModuleRepository inchMeterFabModuleRepository;
    
    @Override
    public InchMeterFabModuleDto createInchMeterFabModule(InchMeterFabModuleDto inchMeterFabModuleDto) {
        LOG.info("Creating new InchMeterFabModule: {}", inchMeterFabModuleDto);
        
        try {
            // Check if record already exists
            if (recordExists(inchMeterFabModuleDto.getWorkOrder(), inchMeterFabModuleDto.getBuildingName(),
                           inchMeterFabModuleDto.getDrawingNo(), inchMeterFabModuleDto.getMarkNo(), inchMeterFabModuleDto.getItemNo())) {
                LOG.warn("Record already exists with same criteria");
                throw new RuntimeException("Record already exists with same work order, building name, drawing no, mark no, and item no");
            }
            
            InchMeterFabModule entity = convertDtoToEntity(inchMeterFabModuleDto);
            entity.setCreatedDate(LocalDateTime.now());
            entity.setLastUpdatedDate(LocalDateTime.now());
            entity.setStatus(ACTIVE_STATUS);
            
            InchMeterFabModule savedEntity = inchMeterFabModuleRepository.save(entity);
            LOG.info("Successfully created InchMeterFabModule with ID: {}", savedEntity.getId());
            
            return convertEntityToDto(savedEntity);
        } catch (Exception e) {
            LOG.error("Error creating InchMeterFabModule: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating InchMeterFabModule: " + e.getMessage());
        }
    }
    
    @Override
    public List<InchMeterFabModuleDto> createMultipleInchMeterFabModules(List<InchMeterFabModuleDto> inchMeterFabModuleDtos) {
        LOG.info("Creating {} InchMeterFabModule records", inchMeterFabModuleDtos.size());
        
        try {
            List<InchMeterFabModule> entities = inchMeterFabModuleDtos.stream()
                    .map(dto -> {
                        InchMeterFabModule entity = convertDtoToEntity(dto);
                        entity.setCreatedDate(LocalDateTime.now());
                        entity.setLastUpdatedDate(LocalDateTime.now());
                        entity.setStatus(ACTIVE_STATUS);
                        return entity;
                    })
                    .collect(Collectors.toList());
            
            List<InchMeterFabModule> savedEntities = inchMeterFabModuleRepository.saveAll(entities);
            LOG.info("Successfully created {} InchMeterFabModule records", savedEntities.size());
            
            return savedEntities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Error creating multiple InchMeterFabModule records: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating multiple InchMeterFabModule records: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InchMeterFabModuleDto> getAllInchMeterFabModules() {
        LOG.info("Fetching all active InchMeterFabModule records");
        
        try {
            List<InchMeterFabModule> entities = inchMeterFabModuleRepository.findByStatusOrderByCreatedDateDesc(ACTIVE_STATUS);
            LOG.info("Found {} active InchMeterFabModule records", entities.size());
            
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Error fetching all InchMeterFabModule records: {}", e.getMessage(), e);
            throw new RuntimeException("Error fetching InchMeterFabModule records: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<InchMeterFabModuleDto> getInchMeterFabModuleById(Long id) {
        LOG.info("Fetching InchMeterFabModule by ID: {}", id);
        
        try {
            Optional<InchMeterFabModule> entity = inchMeterFabModuleRepository.findByIdAndStatus(id, ACTIVE_STATUS);
            
            if (entity.isPresent()) {
                LOG.info("Found InchMeterFabModule with ID: {}", id);
                return Optional.of(convertEntityToDto(entity.get()));
            } else {
                LOG.warn("InchMeterFabModule not found with ID: {}", id);
                return Optional.empty();
            }
        } catch (Exception e) {
            LOG.error("Error fetching InchMeterFabModule by ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error fetching InchMeterFabModule: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InchMeterFabModuleDto> getInchMeterFabModulesByWorkOrder(String workOrder) {
        LOG.info("Fetching InchMeterFabModule records by work order: {}", workOrder);
        
        try {
            List<InchMeterFabModule> entities = inchMeterFabModuleRepository.findByWorkOrderAndStatus(workOrder, ACTIVE_STATUS);
            LOG.info("Found {} InchMeterFabModule records for work order: {}", entities.size(), workOrder);
            
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Error fetching InchMeterFabModule records by work order {}: {}", workOrder, e.getMessage(), e);
            throw new RuntimeException("Error fetching InchMeterFabModule records: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InchMeterFabModuleDto> getInchMeterFabModulesByWorkOrderAndBuildingName(String workOrder, String buildingName) {
        LOG.info("Fetching InchMeterFabModule records by work order: {} and building name: {}", workOrder, buildingName);
        
        try {
            List<InchMeterFabModule> entities = inchMeterFabModuleRepository.findByWorkOrderAndBuildingNameAndStatus(workOrder, buildingName, ACTIVE_STATUS);
            LOG.info("Found {} InchMeterFabModule records", entities.size());
            
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Error fetching InchMeterFabModule records: {}", e.getMessage(), e);
            throw new RuntimeException("Error fetching InchMeterFabModule records: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InchMeterFabModuleDto> getInchMeterFabModulesByWorkOrderAndBuildingNameAndDrawingNo(String workOrder, String buildingName, String drawingNo) {
        LOG.info("Fetching InchMeterFabModule records by work order: {}, building name: {}, drawing no: {}", workOrder, buildingName, drawingNo);
        
        try {
            List<InchMeterFabModule> entities = inchMeterFabModuleRepository.findByWorkOrderAndBuildingNameAndDrawingNoAndStatus(workOrder, buildingName, drawingNo, ACTIVE_STATUS);
            LOG.info("Found {} InchMeterFabModule records", entities.size());
            
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Error fetching InchMeterFabModule records: {}", e.getMessage(), e);
            throw new RuntimeException("Error fetching InchMeterFabModule records: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InchMeterFabModuleDto> getInchMeterFabModulesByWorkOrderAndBuildingNameAndDrawingNoAndMarkNo(String workOrder, String buildingName, String drawingNo, String markNo) {
        LOG.info("Fetching InchMeterFabModule records by work order: {}, building name: {}, drawing no: {}, mark no: {}", workOrder, buildingName, drawingNo, markNo);
        
        try {
            List<InchMeterFabModule> entities = inchMeterFabModuleRepository.findByWorkOrderAndBuildingNameAndDrawingNoAndMarkNoAndStatus(workOrder, buildingName, drawingNo, markNo, ACTIVE_STATUS);
            LOG.info("Found {} InchMeterFabModule records", entities.size());
            
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Error fetching InchMeterFabModule records: {}", e.getMessage(), e);
            throw new RuntimeException("Error fetching InchMeterFabModule records: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InchMeterFabModuleDto> getInchMeterFabModulesByRaNo(String raNo) {
        LOG.info("Fetching InchMeterFabModule records by RA No: {}", raNo);
        
        try {
            List<InchMeterFabModule> entities = inchMeterFabModuleRepository.findByRaNoAndStatus(raNo, ACTIVE_STATUS);
            LOG.info("Found {} InchMeterFabModule records for RA No: {}", entities.size(), raNo);
            
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Error fetching InchMeterFabModule records by RA No {}: {}", raNo, e.getMessage(), e);
            throw new RuntimeException("Error fetching InchMeterFabModule records: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InchMeterFabModuleDto> searchInchMeterFabModules(String workOrder, String buildingName, String drawingNo, String markNo, String raNo) {
        LOG.info("Searching InchMeterFabModule records with criteria - workOrder: {}, buildingName: {}, drawingNo: {}, markNo: {}, raNo: {}",
                 workOrder, buildingName, drawingNo, markNo, raNo);
        
        try {
            List<InchMeterFabModule> entities = inchMeterFabModuleRepository.findByMultipleCriteria(workOrder, buildingName, drawingNo, markNo, raNo, ACTIVE_STATUS);
            LOG.info("Found {} InchMeterFabModule records matching search criteria", entities.size());
            
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Error searching InchMeterFabModule records: {}", e.getMessage(), e);
            throw new RuntimeException("Error searching InchMeterFabModule records: " + e.getMessage());
        }
    }
    
    @Override
    public InchMeterFabModuleDto updateInchMeterFabModule(Long id, InchMeterFabModuleDto inchMeterFabModuleDto) {
        LOG.info("Updating InchMeterFabModule with ID: {}", id);
        
        try {
            Optional<InchMeterFabModule> existingEntityOpt = inchMeterFabModuleRepository.findByIdAndStatus(id, ACTIVE_STATUS);
            
            if (!existingEntityOpt.isPresent()) {
                LOG.error("InchMeterFabModule not found with ID: {}", id);
                throw new RuntimeException("InchMeterFabModule not found with ID: " + id);
            }
            
            InchMeterFabModule existingEntity = existingEntityOpt.get();
            
            // Update fields
            if (inchMeterFabModuleDto.getWorkOrder() != null) existingEntity.setWorkOrder(inchMeterFabModuleDto.getWorkOrder());
            if (inchMeterFabModuleDto.getBuildingName() != null) existingEntity.setBuildingName(inchMeterFabModuleDto.getBuildingName());
            if (inchMeterFabModuleDto.getDrawingNo() != null) existingEntity.setDrawingNo(inchMeterFabModuleDto.getDrawingNo());
            if (inchMeterFabModuleDto.getMarkNo() != null) existingEntity.setMarkNo(inchMeterFabModuleDto.getMarkNo());
            if (inchMeterFabModuleDto.getMarkQty() != null) existingEntity.setMarkQty(inchMeterFabModuleDto.getMarkQty());
            if (inchMeterFabModuleDto.getEachMarkLength() != null) existingEntity.setEachMarkLength(inchMeterFabModuleDto.getEachMarkLength());
            if (inchMeterFabModuleDto.getTotalMarkLength() != null) existingEntity.setTotalMarkLength(inchMeterFabModuleDto.getTotalMarkLength());
            if (inchMeterFabModuleDto.getRaNo() != null) existingEntity.setRaNo(inchMeterFabModuleDto.getRaNo());
            if (inchMeterFabModuleDto.getItemNo() != null) existingEntity.setItemNo(inchMeterFabModuleDto.getItemNo());
            if (inchMeterFabModuleDto.getSection() != null) existingEntity.setSection(inchMeterFabModuleDto.getSection());
            if (inchMeterFabModuleDto.getLengthMm() != null) existingEntity.setLengthMm(inchMeterFabModuleDto.getLengthMm());
            if (inchMeterFabModuleDto.getDiaMm() != null) existingEntity.setDiaMm(inchMeterFabModuleDto.getDiaMm());
            if (inchMeterFabModuleDto.getItemQty() != null) existingEntity.setItemQty(inchMeterFabModuleDto.getItemQty());
            if (inchMeterFabModuleDto.getTotalLength() != null) existingEntity.setTotalLength(inchMeterFabModuleDto.getTotalLength());
            if (inchMeterFabModuleDto.getRemarks() != null) existingEntity.setRemarks(inchMeterFabModuleDto.getRemarks());
            if (inchMeterFabModuleDto.getCuttingStage() != null) existingEntity.setCuttingStage(inchMeterFabModuleDto.getCuttingStage());
            if (inchMeterFabModuleDto.getFitUpStage() != null) existingEntity.setFitUpStage(inchMeterFabModuleDto.getFitUpStage());
            if (inchMeterFabModuleDto.getWeldingStage() != null) existingEntity.setWeldingStage(inchMeterFabModuleDto.getWeldingStage());
            if (inchMeterFabModuleDto.getFinishingStage() != null) existingEntity.setFinishingStage(inchMeterFabModuleDto.getFinishingStage());
            if (inchMeterFabModuleDto.getServiceDescription() != null) existingEntity.setServiceDescription(inchMeterFabModuleDto.getServiceDescription());
            if (inchMeterFabModuleDto.getUom() != null) existingEntity.setUom(inchMeterFabModuleDto.getUom());
            if (inchMeterFabModuleDto.getDataModule() != null) existingEntity.setDataModule(inchMeterFabModuleDto.getDataModule());
            if (inchMeterFabModuleDto.getOrderId() != null) existingEntity.setOrderId(inchMeterFabModuleDto.getOrderId());
            if (inchMeterFabModuleDto.getClientName() != null) existingEntity.setClientName(inchMeterFabModuleDto.getClientName());
            
            existingEntity.setLastUpdatedBy(inchMeterFabModuleDto.getLastUpdatedBy());
            existingEntity.setLastUpdatedDate(LocalDateTime.now());
            
            InchMeterFabModule updatedEntity = inchMeterFabModuleRepository.save(existingEntity);
            LOG.info("Successfully updated InchMeterFabModule with ID: {}", id);
            
            return convertEntityToDto(updatedEntity);
        } catch (Exception e) {
            LOG.error("Error updating InchMeterFabModule with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error updating InchMeterFabModule: " + e.getMessage());
        }
    }
    
    @Override
    public InchMeterFabModuleDto updateInchMeterFabModuleStages(Long id, String cuttingStage, String fitUpStage, String weldingStage, String finishingStage) {
        LOG.info("Updating fabrication stages for InchMeterFabModule with ID: {}", id);
        
        try {
            Optional<InchMeterFabModule> existingEntityOpt = inchMeterFabModuleRepository.findByIdAndStatus(id, ACTIVE_STATUS);
            
            if (!existingEntityOpt.isPresent()) {
                LOG.error("InchMeterFabModule not found with ID: {}", id);
                throw new RuntimeException("InchMeterFabModule not found with ID: " + id);
            }
            
            InchMeterFabModule existingEntity = existingEntityOpt.get();
            
            // Update fabrication stages
            if (cuttingStage != null) existingEntity.setCuttingStage(cuttingStage);
            if (fitUpStage != null) existingEntity.setFitUpStage(fitUpStage);
            if (weldingStage != null) existingEntity.setWeldingStage(weldingStage);
            if (finishingStage != null) existingEntity.setFinishingStage(finishingStage);
            
            existingEntity.setLastUpdatedBy("system");
            existingEntity.setLastUpdatedDate(LocalDateTime.now());
            
            InchMeterFabModule updatedEntity = inchMeterFabModuleRepository.save(existingEntity);
            LOG.info("Successfully updated fabrication stages for InchMeterFabModule with ID: {}", id);
            
            return convertEntityToDto(updatedEntity);
        } catch (Exception e) {
            LOG.error("Error updating fabrication stages for InchMeterFabModule with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error updating fabrication stages: " + e.getMessage());
        }
    }
    
    @Override
    public void deleteInchMeterFabModule(Long id) {
        LOG.info("Hard deleting InchMeterFabModule with ID: {}", id);
        
        try {
            Optional<InchMeterFabModule> existingEntityOpt = inchMeterFabModuleRepository.findByIdAndStatus(id, ACTIVE_STATUS);
            
            if (!existingEntityOpt.isPresent()) {
                LOG.error("InchMeterFabModule not found with ID: {}", id);
                throw new RuntimeException("InchMeterFabModule not found with ID: " + id);
            }
            
            inchMeterFabModuleRepository.deleteById(id);
            LOG.info("Successfully deleted InchMeterFabModule with ID: {}", id);
        } catch (Exception e) {
            LOG.error("Error deleting InchMeterFabModule with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error deleting InchMeterFabModule: " + e.getMessage());
        }
    }
    
    @Override
    public void softDeleteInchMeterFabModule(Long id) {
        LOG.info("Soft deleting InchMeterFabModule with ID: {}", id);
        
        try {
            Optional<InchMeterFabModule> existingEntityOpt = inchMeterFabModuleRepository.findByIdAndStatus(id, ACTIVE_STATUS);
            
            if (!existingEntityOpt.isPresent()) {
                LOG.error("InchMeterFabModule not found with ID: {}", id);
                throw new RuntimeException("InchMeterFabModule not found with ID: " + id);
            }
            
            InchMeterFabModule existingEntity = existingEntityOpt.get();
            existingEntity.setStatus(DELETED_STATUS);
            existingEntity.setLastUpdatedBy("system");
            existingEntity.setLastUpdatedDate(LocalDateTime.now());
            
            inchMeterFabModuleRepository.save(existingEntity);
            LOG.info("Successfully soft deleted InchMeterFabModule with ID: {}", id);
        } catch (Exception e) {
            LOG.error("Error soft deleting InchMeterFabModule with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error soft deleting InchMeterFabModule: " + e.getMessage());
        }
    }
    
    // Utility methods
    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctWorkOrders() {
        LOG.info("Fetching distinct work orders");
        return inchMeterFabModuleRepository.findDistinctWorkOrdersByStatus(ACTIVE_STATUS);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctBuildingNamesByWorkOrder(String workOrder) {
        LOG.info("Fetching distinct building names for work order: {}", workOrder);
        return inchMeterFabModuleRepository.findDistinctBuildingNamesByWorkOrderAndStatus(workOrder, ACTIVE_STATUS);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctDrawingNosByWorkOrderAndBuildingName(String workOrder, String buildingName) {
        LOG.info("Fetching distinct drawing numbers for work order: {} and building name: {}", workOrder, buildingName);
        return inchMeterFabModuleRepository.findDistinctDrawingNosByWorkOrderAndBuildingNameAndStatus(workOrder, buildingName, ACTIVE_STATUS);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctMarkNosByWorkOrderAndBuildingNameAndDrawingNo(String workOrder, String buildingName, String drawingNo) {
        LOG.info("Fetching distinct mark numbers for work order: {}, building name: {}, drawing no: {}", workOrder, buildingName, drawingNo);
        return inchMeterFabModuleRepository.findDistinctMarkNosByWorkOrderAndBuildingNameAndDrawingNoAndStatus(workOrder, buildingName, drawingNo, ACTIVE_STATUS);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctRaNumbers() {
        LOG.info("Fetching distinct RA numbers");
        return inchMeterFabModuleRepository.findDistinctRaNosByStatus(ACTIVE_STATUS);
    }
    
    // NEW: Missing method implementation
    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder) {
        LOG.info("Fetching distinct service descriptions for work order: {}", workOrder);
        return inchMeterFabModuleRepository.findDistinctServiceDescriptionsByWorkOrderAndStatus(workOrder, ACTIVE_STATUS);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getTotalRecordsCount() {
        return inchMeterFabModuleRepository.countByStatus(ACTIVE_STATUS);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getRecordsCountByWorkOrder(String workOrder) {
        return inchMeterFabModuleRepository.countByWorkOrderAndStatus(workOrder, ACTIVE_STATUS);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getRecordsCountByRaNo(String raNo) {
        return inchMeterFabModuleRepository.countByRaNoAndStatus(raNo, ACTIVE_STATUS);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean recordExists(String workOrder, String buildingName, String drawingNo, String markNo, String itemNo) {
        return inchMeterFabModuleRepository.existsByWorkOrderAndBuildingNameAndDrawingNoAndMarkNoAndItemNoAndStatus(
                workOrder, buildingName, drawingNo, markNo, itemNo, ACTIVE_STATUS);
    }
    
    // Conversion methods
    private InchMeterFabModule convertDtoToEntity(InchMeterFabModuleDto dto) {
        InchMeterFabModule entity = new InchMeterFabModule();
        
        entity.setId(dto.getId());
        entity.setWorkOrder(dto.getWorkOrder());
        entity.setBuildingName(dto.getBuildingName());
        entity.setDrawingNo(dto.getDrawingNo());
        entity.setMarkNo(dto.getMarkNo());
        entity.setMarkQty(dto.getMarkQty());
        entity.setEachMarkLength(dto.getEachMarkLength());
        entity.setTotalMarkLength(dto.getTotalMarkLength());
        entity.setRaNo(dto.getRaNo());
        entity.setItemNo(dto.getItemNo());
        entity.setSection(dto.getSection());
        entity.setLengthMm(dto.getLengthMm());
        entity.setDiaMm(dto.getDiaMm());
        entity.setItemQty(dto.getItemQty());
        entity.setTotalLength(dto.getTotalLength());
        entity.setRemarks(dto.getRemarks());
        entity.setCuttingStage(dto.getCuttingStage() != null ? dto.getCuttingStage() : "N");
        entity.setFitUpStage(dto.getFitUpStage() != null ? dto.getFitUpStage() : "N");
        entity.setWeldingStage(dto.getWeldingStage() != null ? dto.getWeldingStage() : "N");
        entity.setFinishingStage(dto.getFinishingStage() != null ? dto.getFinishingStage() : "N");
        entity.setServiceDescription(dto.getServiceDescription());
        entity.setUom(dto.getUom());
        entity.setDataModule(dto.getDataModule());
        entity.setOrderId(dto.getOrderId());
        entity.setClientName(dto.getClientName());
        entity.setCreatedBy(dto.getCreatedBy() != null ? dto.getCreatedBy() : "system");
        entity.setLastUpdatedBy(dto.getLastUpdatedBy() != null ? dto.getLastUpdatedBy() : "system");
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : ACTIVE_STATUS);
        
        return entity;
    }
    
    private InchMeterFabModuleDto convertEntityToDto(InchMeterFabModule entity) {
        InchMeterFabModuleDto dto = new InchMeterFabModuleDto();
        
        dto.setId(entity.getId());
        dto.setWorkOrder(entity.getWorkOrder());
        dto.setBuildingName(entity.getBuildingName());
        dto.setDrawingNo(entity.getDrawingNo());
        dto.setMarkNo(entity.getMarkNo());
        dto.setMarkQty(entity.getMarkQty());
        dto.setEachMarkLength(entity.getEachMarkLength());
        dto.setTotalMarkLength(entity.getTotalMarkLength());
        dto.setRaNo(entity.getRaNo());
        dto.setItemNo(entity.getItemNo());
        dto.setSection(entity.getSection());
        dto.setLengthMm(entity.getLengthMm());
        dto.setDiaMm(entity.getDiaMm());
        dto.setItemQty(entity.getItemQty());
        dto.setTotalLength(entity.getTotalLength());
        dto.setRemarks(entity.getRemarks());
        dto.setCuttingStage(entity.getCuttingStage());
        dto.setFitUpStage(entity.getFitUpStage());
        dto.setWeldingStage(entity.getWeldingStage());
        dto.setFinishingStage(entity.getFinishingStage());
        dto.setServiceDescription(entity.getServiceDescription());
        dto.setUom(entity.getUom());
        dto.setDataModule(entity.getDataModule());
        dto.setOrderId(entity.getOrderId());
        dto.setClientName(entity.getClientName());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastUpdatedBy(entity.getLastUpdatedBy());
        dto.setLastUpdatedDate(entity.getLastUpdatedDate());
        dto.setStatus(entity.getStatus());
        
        return dto;
    }
}