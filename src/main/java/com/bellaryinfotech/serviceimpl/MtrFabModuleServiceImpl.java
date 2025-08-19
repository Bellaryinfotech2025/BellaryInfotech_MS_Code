package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.MtrFabModuleDto;
import com.bellaryinfotech.model.MtrFabModule;
import com.bellaryinfotech.repo.MtrFabModuleRepository;
import com.bellaryinfotech.service.MtrFabModuleService;
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
public class MtrFabModuleServiceImpl implements MtrFabModuleService {

    private static final Logger LOG = LoggerFactory.getLogger(MtrFabModuleServiceImpl.class);
    private static final String ACTIVE_STATUS = "ACTIVE";
    private static final String DELETED_STATUS = "DELETED";

    @Autowired
    private MtrFabModuleRepository mtrFabModuleRepository;

    @Override
    public MtrFabModuleDto createMtrFabModule(MtrFabModuleDto mtrFabModuleDto) {
        LOG.info("Creating new MtrFabModule: {}", mtrFabModuleDto);
        
        try {
            // Check if record already exists
            if (recordExists(mtrFabModuleDto.getWorkOrder(), mtrFabModuleDto.getBuildingName(), 
                           mtrFabModuleDto.getDrawingNo(), mtrFabModuleDto.getMarkNo(), mtrFabModuleDto.getItemNo())) {
                LOG.warn("Record already exists with same criteria");
                throw new RuntimeException("Record already exists with same work order, building name, drawing no, mark no, and item no");
            }

            MtrFabModule entity = convertDtoToEntity(mtrFabModuleDto);
            entity.setCreatedDate(LocalDateTime.now());
            entity.setLastUpdatedDate(LocalDateTime.now());
            entity.setStatus(ACTIVE_STATUS);
            
            MtrFabModule savedEntity = mtrFabModuleRepository.save(entity);
            LOG.info("Successfully created MtrFabModule with ID: {}", savedEntity.getId());
            
            return convertEntityToDto(savedEntity);
        } catch (Exception e) {
            LOG.error("Error creating MtrFabModule: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating MtrFabModule: " + e.getMessage());
        }
    }

    @Override
    public List<MtrFabModuleDto> createMultipleMtrFabModules(List<MtrFabModuleDto> mtrFabModuleDtos) {
        LOG.info("Creating {} MtrFabModule records", mtrFabModuleDtos.size());
        
        try {
            List<MtrFabModule> entities = mtrFabModuleDtos.stream()
                    .map(dto -> {
                        MtrFabModule entity = convertDtoToEntity(dto);
                        entity.setCreatedDate(LocalDateTime.now());
                        entity.setLastUpdatedDate(LocalDateTime.now());
                        entity.setStatus(ACTIVE_STATUS);
                        return entity;
                    })
                    .collect(Collectors.toList());
            
            List<MtrFabModule> savedEntities = mtrFabModuleRepository.saveAll(entities);
            LOG.info("Successfully created {} MtrFabModule records", savedEntities.size());
            
            return savedEntities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Error creating multiple MtrFabModule records: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating multiple MtrFabModule records: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MtrFabModuleDto> getAllMtrFabModules() {
        LOG.info("Fetching all active MtrFabModule records");
        
        try {
            List<MtrFabModule> entities = mtrFabModuleRepository.findByStatusOrderByCreatedDateDesc(ACTIVE_STATUS);
            LOG.info("Found {} active MtrFabModule records", entities.size());
            
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Error fetching all MtrFabModule records: {}", e.getMessage(), e);
            throw new RuntimeException("Error fetching MtrFabModule records: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MtrFabModuleDto> getMtrFabModuleById(Long id) {
        LOG.info("Fetching MtrFabModule by ID: {}", id);
        
        try {
            Optional<MtrFabModule> entity = mtrFabModuleRepository.findByIdAndStatus(id, ACTIVE_STATUS);
            
            if (entity.isPresent()) {
                LOG.info("Found MtrFabModule with ID: {}", id);
                return Optional.of(convertEntityToDto(entity.get()));
            } else {
                LOG.warn("MtrFabModule not found with ID: {}", id);
                return Optional.empty();
            }
        } catch (Exception e) {
            LOG.error("Error fetching MtrFabModule by ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error fetching MtrFabModule: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MtrFabModuleDto> getMtrFabModulesByWorkOrder(String workOrder) {
        LOG.info("Fetching MtrFabModule records by work order: {}", workOrder);
        
        try {
            List<MtrFabModule> entities = mtrFabModuleRepository.findByWorkOrderAndStatus(workOrder, ACTIVE_STATUS);
            LOG.info("Found {} MtrFabModule records for work order: {}", entities.size(), workOrder);
            
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Error fetching MtrFabModule records by work order {}: {}", workOrder, e.getMessage(), e);
            throw new RuntimeException("Error fetching MtrFabModule records: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MtrFabModuleDto> getMtrFabModulesByWorkOrderAndBuildingName(String workOrder, String buildingName) {
        LOG.info("Fetching MtrFabModule records by work order: {} and building name: {}", workOrder, buildingName);
        
        try {
            List<MtrFabModule> entities = mtrFabModuleRepository.findByWorkOrderAndBuildingNameAndStatus(workOrder, buildingName, ACTIVE_STATUS);
            LOG.info("Found {} MtrFabModule records", entities.size());
            
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Error fetching MtrFabModule records: {}", e.getMessage(), e);
            throw new RuntimeException("Error fetching MtrFabModule records: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MtrFabModuleDto> getMtrFabModulesByWorkOrderAndBuildingNameAndDrawingNo(String workOrder, String buildingName, String drawingNo) {
        LOG.info("Fetching MtrFabModule records by work order: {}, building name: {}, drawing no: {}", workOrder, buildingName, drawingNo);
        
        try {
            List<MtrFabModule> entities = mtrFabModuleRepository.findByWorkOrderAndBuildingNameAndDrawingNoAndStatus(workOrder, buildingName, drawingNo, ACTIVE_STATUS);
            LOG.info("Found {} MtrFabModule records", entities.size());
            
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Error fetching MtrFabModule records: {}", e.getMessage(), e);
            throw new RuntimeException("Error fetching MtrFabModule records: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MtrFabModuleDto> getMtrFabModulesByWorkOrderAndBuildingNameAndDrawingNoAndMarkNo(String workOrder, String buildingName, String drawingNo, String markNo) {
        LOG.info("Fetching MtrFabModule records by work order: {}, building name: {}, drawing no: {}, mark no: {}", workOrder, buildingName, drawingNo, markNo);
        
        try {
            List<MtrFabModule> entities = mtrFabModuleRepository.findByWorkOrderAndBuildingNameAndDrawingNoAndMarkNoAndStatus(workOrder, buildingName, drawingNo, markNo, ACTIVE_STATUS);
            LOG.info("Found {} MtrFabModule records", entities.size());
            
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Error fetching MtrFabModule records: {}", e.getMessage(), e);
            throw new RuntimeException("Error fetching MtrFabModule records: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MtrFabModuleDto> getMtrFabModulesByRaNo(String raNo) {
        LOG.info("Fetching MtrFabModule records by RA No: {}", raNo);
        
        try {
            List<MtrFabModule> entities = mtrFabModuleRepository.findByRaNoAndStatus(raNo, ACTIVE_STATUS);
            LOG.info("Found {} MtrFabModule records for RA No: {}", entities.size(), raNo);
            
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Error fetching MtrFabModule records by RA No {}: {}", raNo, e.getMessage(), e);
            throw new RuntimeException("Error fetching MtrFabModule records: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MtrFabModuleDto> searchMtrFabModules(String workOrder, String buildingName, String drawingNo, String markNo, String raNo) {
        LOG.info("Searching MtrFabModule records with criteria - workOrder: {}, buildingName: {}, drawingNo: {}, markNo: {}, raNo: {}", 
                workOrder, buildingName, drawingNo, markNo, raNo);
        
        try {
            List<MtrFabModule> entities = mtrFabModuleRepository.findByMultipleCriteria(workOrder, buildingName, drawingNo, markNo, raNo, ACTIVE_STATUS);
            LOG.info("Found {} MtrFabModule records matching search criteria", entities.size());
            
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Error searching MtrFabModule records: {}", e.getMessage(), e);
            throw new RuntimeException("Error searching MtrFabModule records: " + e.getMessage());
        }
    }

    @Override
    public MtrFabModuleDto updateMtrFabModule(Long id, MtrFabModuleDto mtrFabModuleDto) {
        LOG.info("Updating MtrFabModule with ID: {}", id);
        
        try {
            Optional<MtrFabModule> existingEntityOpt = mtrFabModuleRepository.findByIdAndStatus(id, ACTIVE_STATUS);
            
            if (!existingEntityOpt.isPresent()) {
                LOG.error("MtrFabModule not found with ID: {}", id);
                throw new RuntimeException("MtrFabModule not found with ID: " + id);
            }
            
            MtrFabModule existingEntity = existingEntityOpt.get();
            
            // Update fields
            if (mtrFabModuleDto.getWorkOrder() != null) existingEntity.setWorkOrder(mtrFabModuleDto.getWorkOrder());
            if (mtrFabModuleDto.getBuildingName() != null) existingEntity.setBuildingName(mtrFabModuleDto.getBuildingName());
            if (mtrFabModuleDto.getDrawingNo() != null) existingEntity.setDrawingNo(mtrFabModuleDto.getDrawingNo());
            if (mtrFabModuleDto.getMarkNo() != null) existingEntity.setMarkNo(mtrFabModuleDto.getMarkNo());
            if (mtrFabModuleDto.getMarkQty() != null) existingEntity.setMarkQty(mtrFabModuleDto.getMarkQty());
            if (mtrFabModuleDto.getEachMarkLength() != null) existingEntity.setEachMarkLength(mtrFabModuleDto.getEachMarkLength());
            if (mtrFabModuleDto.getTotalMarkLength() != null) existingEntity.setTotalMarkLength(mtrFabModuleDto.getTotalMarkLength());
            if (mtrFabModuleDto.getRaNo() != null) existingEntity.setRaNo(mtrFabModuleDto.getRaNo());
            if (mtrFabModuleDto.getItemNo() != null) existingEntity.setItemNo(mtrFabModuleDto.getItemNo());
            if (mtrFabModuleDto.getSection() != null) existingEntity.setSection(mtrFabModuleDto.getSection());
            if (mtrFabModuleDto.getLengthMm() != null) existingEntity.setLengthMm(mtrFabModuleDto.getLengthMm());
            if (mtrFabModuleDto.getItemQty() != null) existingEntity.setItemQty(mtrFabModuleDto.getItemQty());
            if (mtrFabModuleDto.getTotalLength() != null) existingEntity.setTotalLength(mtrFabModuleDto.getTotalLength());
            if (mtrFabModuleDto.getRemarks() != null) existingEntity.setRemarks(mtrFabModuleDto.getRemarks());
            if (mtrFabModuleDto.getCuttingStage() != null) existingEntity.setCuttingStage(mtrFabModuleDto.getCuttingStage());
            if (mtrFabModuleDto.getFitUpStage() != null) existingEntity.setFitUpStage(mtrFabModuleDto.getFitUpStage());
            if (mtrFabModuleDto.getWeldingStage() != null) existingEntity.setWeldingStage(mtrFabModuleDto.getWeldingStage());
            if (mtrFabModuleDto.getFinishingStage() != null) existingEntity.setFinishingStage(mtrFabModuleDto.getFinishingStage());
            // NEW: Update serviceDescription, uom, dataModule
            if (mtrFabModuleDto.getServiceDescription() != null) existingEntity.setServiceDescription(mtrFabModuleDto.getServiceDescription());
            if (mtrFabModuleDto.getUom() != null) existingEntity.setUom(mtrFabModuleDto.getUom());
            if (mtrFabModuleDto.getDataModule() != null) existingEntity.setDataModule(mtrFabModuleDto.getDataModule());
            
            existingEntity.setLastUpdatedBy(mtrFabModuleDto.getLastUpdatedBy());
            existingEntity.setLastUpdatedDate(LocalDateTime.now());
            
            MtrFabModule updatedEntity = mtrFabModuleRepository.save(existingEntity);
            LOG.info("Successfully updated MtrFabModule with ID: {}", id);
            
            return convertEntityToDto(updatedEntity);
        } catch (Exception e) {
            LOG.error("Error updating MtrFabModule with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error updating MtrFabModule: " + e.getMessage());
        }
    }

    @Override
    public MtrFabModuleDto updateMtrFabModuleStages(Long id, String cuttingStage, String fitUpStage, String weldingStage, String finishingStage) {
        LOG.info("Updating fabrication stages for MtrFabModule with ID: {}", id);
        
        try {
            Optional<MtrFabModule> existingEntityOpt = mtrFabModuleRepository.findByIdAndStatus(id, ACTIVE_STATUS);
            
            if (!existingEntityOpt.isPresent()) {
                LOG.error("MtrFabModule not found with ID: {}", id);
                throw new RuntimeException("MtrFabModule not found with ID: " + id);
            }
            
            MtrFabModule existingEntity = existingEntityOpt.get();
            
            // Update fabrication stages
            if (cuttingStage != null) existingEntity.setCuttingStage(cuttingStage);
            if (fitUpStage != null) existingEntity.setFitUpStage(fitUpStage);
            if (weldingStage != null) existingEntity.setWeldingStage(weldingStage);
            if (finishingStage != null) existingEntity.setFinishingStage(finishingStage);
            
            existingEntity.setLastUpdatedBy("system");
            existingEntity.setLastUpdatedDate(LocalDateTime.now());
            
            MtrFabModule updatedEntity = mtrFabModuleRepository.save(existingEntity);
            LOG.info("Successfully updated fabrication stages for MtrFabModule with ID: {}", id);
            
            return convertEntityToDto(updatedEntity);
        } catch (Exception e) {
            LOG.error("Error updating fabrication stages for MtrFabModule with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error updating fabrication stages: " + e.getMessage());
        }
    }

    @Override
    public void deleteMtrFabModule(Long id) {
        LOG.info("Hard deleting MtrFabModule with ID: {}", id);
        
        try {
            Optional<MtrFabModule> existingEntityOpt = mtrFabModuleRepository.findByIdAndStatus(id, ACTIVE_STATUS);
            
            if (!existingEntityOpt.isPresent()) {
                LOG.error("MtrFabModule not found with ID: {}", id);
                throw new RuntimeException("MtrFabModule not found with ID: " + id);
            }
            
            mtrFabModuleRepository.deleteById(id);
            LOG.info("Successfully deleted MtrFabModule with ID: {}", id);
        } catch (Exception e) {
            LOG.error("Error deleting MtrFabModule with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error deleting MtrFabModule: " + e.getMessage());
        }
    }

    @Override
    public void softDeleteMtrFabModule(Long id) {
        LOG.info("Soft deleting MtrFabModule with ID: {}", id);
        
        try {
            Optional<MtrFabModule> existingEntityOpt = mtrFabModuleRepository.findByIdAndStatus(id, ACTIVE_STATUS);
            
            if (!existingEntityOpt.isPresent()) {
                LOG.error("MtrFabModule not found with ID: {}", id);
                throw new RuntimeException("MtrFabModule not found with ID: " + id);
            }
            
            MtrFabModule existingEntity = existingEntityOpt.get();
            existingEntity.setStatus(DELETED_STATUS);
            existingEntity.setLastUpdatedBy("system");
            existingEntity.setLastUpdatedDate(LocalDateTime.now());
            
            mtrFabModuleRepository.save(existingEntity);
            LOG.info("Successfully soft deleted MtrFabModule with ID: {}", id);
        } catch (Exception e) {
            LOG.error("Error soft deleting MtrFabModule with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error soft deleting MtrFabModule: " + e.getMessage());
        }
    }

    // Utility methods
    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctWorkOrders() {
        LOG.info("Fetching distinct work orders");
        return mtrFabModuleRepository.findDistinctWorkOrdersByStatus(ACTIVE_STATUS);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctBuildingNamesByWorkOrder(String workOrder) {
        LOG.info("Fetching distinct building names for work order: {}", workOrder);
        return mtrFabModuleRepository.findDistinctBuildingNamesByWorkOrderAndStatus(workOrder, ACTIVE_STATUS);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctDrawingNosByWorkOrderAndBuildingName(String workOrder, String buildingName) {
        LOG.info("Fetching distinct drawing numbers for work order: {} and building name: {}", workOrder, buildingName);
        return mtrFabModuleRepository.findDistinctDrawingNosByWorkOrderAndBuildingNameAndStatus(workOrder, buildingName, ACTIVE_STATUS);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctMarkNosByWorkOrderAndBuildingNameAndDrawingNo(String workOrder, String buildingName, String drawingNo) {
        LOG.info("Fetching distinct mark numbers for work order: {}, building name: {}, drawing no: {}", workOrder, buildingName, drawingNo);
        return mtrFabModuleRepository.findDistinctMarkNosByWorkOrderAndBuildingNameAndDrawingNoAndStatus(workOrder, buildingName, drawingNo, ACTIVE_STATUS);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctRaNumbers() {
        LOG.info("Fetching distinct RA numbers");
        return mtrFabModuleRepository.findDistinctRaNosByStatus(ACTIVE_STATUS);
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalRecordsCount() {
        return mtrFabModuleRepository.countByStatus(ACTIVE_STATUS);
    }

    @Override
    @Transactional(readOnly = true)
    public long getRecordsCountByWorkOrder(String workOrder) {
        return mtrFabModuleRepository.countByWorkOrderAndStatus(workOrder, ACTIVE_STATUS);
    }

    @Override
    @Transactional(readOnly = true)
    public long getRecordsCountByRaNo(String raNo) {
        return mtrFabModuleRepository.countByRaNoAndStatus(raNo, ACTIVE_STATUS);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean recordExists(String workOrder, String buildingName, String drawingNo, String markNo, String itemNo) {
        return mtrFabModuleRepository.existsByWorkOrderAndBuildingNameAndDrawingNoAndMarkNoAndItemNoAndStatus(
                workOrder, buildingName, drawingNo, markNo, itemNo, ACTIVE_STATUS);
    }

    // Conversion methods
    private MtrFabModule convertDtoToEntity(MtrFabModuleDto dto) {
        MtrFabModule entity = new MtrFabModule();
        
        entity.setId(dto.getId());
        entity.setOrderId(dto.getOrderId());
        entity.setClientName(dto.getClientName());
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
        entity.setItemQty(dto.getItemQty());
        entity.setTotalLength(dto.getTotalLength());
        entity.setRemarks(dto.getRemarks());
        entity.setCuttingStage(dto.getCuttingStage() != null ? dto.getCuttingStage() : "N");
        entity.setFitUpStage(dto.getFitUpStage() != null ? dto.getFitUpStage() : "N");
        entity.setWeldingStage(dto.getWeldingStage() != null ? dto.getWeldingStage() : "N");
        entity.setFinishingStage(dto.getFinishingStage() != null ? dto.getFinishingStage() : "N");
        // NEW: Set serviceDescription, uom, dataModule
        entity.setServiceDescription(dto.getServiceDescription());
        entity.setUom(dto.getUom());
        entity.setDataModule(dto.getDataModule());

        entity.setCreatedBy(dto.getCreatedBy() != null ? dto.getCreatedBy() : "system");
        entity.setLastUpdatedBy(dto.getLastUpdatedBy() != null ? dto.getLastUpdatedBy() : "system");
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : ACTIVE_STATUS);
        
        return entity;
    }

    private MtrFabModuleDto convertEntityToDto(MtrFabModule entity) {
        MtrFabModuleDto dto = new MtrFabModuleDto();
        
        dto.setId(entity.getId());
        dto.setOrderId(entity.getOrderId());
        dto.setClientName(entity.getClientName());
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
        dto.setItemQty(entity.getItemQty());
        dto.setTotalLength(entity.getTotalLength());
        dto.setRemarks(entity.getRemarks());
        dto.setCuttingStage(entity.getCuttingStage());
        dto.setFitUpStage(entity.getFitUpStage());
        dto.setWeldingStage(entity.getWeldingStage());
        dto.setFinishingStage(entity.getFinishingStage());
        // NEW: Get serviceDescription, uom, dataModule
        dto.setServiceDescription(entity.getServiceDescription());
        dto.setUom(entity.getUom());
        dto.setDataModule(entity.getDataModule());

        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastUpdatedBy(entity.getLastUpdatedBy());
        dto.setLastUpdatedDate(entity.getLastUpdatedDate());
        dto.setStatus(entity.getStatus());
        
        return dto;
    }
    
    
    
    
    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctClientNames() {
        LOG.info("Fetching distinct client names from MTR Fab Module");
        return mtrFabModuleRepository.findDistinctClientNamesByStatus(ACTIVE_STATUS);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctWorkOrdersByClientName(String clientName) {
        LOG.info("Fetching distinct work orders for client: {}", clientName);
        return mtrFabModuleRepository.findDistinctWorkOrdersByClientNameAndStatus(clientName, ACTIVE_STATUS);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctServiceDescriptionsByClientNameAndWorkOrder(String clientName, String workOrder) {
        LOG.info("Fetching distinct service descriptions for client: {} and work order: {}", clientName, workOrder);
        return mtrFabModuleRepository.findDistinctServiceDescriptionsByClientNameAndWorkOrderAndStatus(clientName, workOrder, ACTIVE_STATUS);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctRaNumbersByClientNameAndWorkOrderAndServiceDescription(String clientName, String workOrder, String serviceDescription) {
        LOG.info("Fetching distinct RA numbers for client: {}, work order: {}, service description: {}", clientName, workOrder, serviceDescription);
        return mtrFabModuleRepository.findDistinctRaNumbersByClientNameAndWorkOrderAndServiceDescriptionAndStatus(clientName, workOrder, serviceDescription, ACTIVE_STATUS);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MtrFabModuleDto> searchMtrFabDataByFilters(String clientName, String workOrder, String serviceDescription, String raNumber) {
        LOG.info("Searching MTR Fab data with filters - client: {}, work order: {}, service description: {}, RA number: {}", 
                clientName, workOrder, serviceDescription, raNumber);
        
        try {
            List<MtrFabModule> entities = mtrFabModuleRepository.findByClientNameAndWorkOrderAndServiceDescriptionAndRaNoAndStatus(
                    clientName, workOrder, serviceDescription, raNumber, ACTIVE_STATUS);
            LOG.info("Found {} MTR Fab records matching search criteria", entities.size());
            
            return entities.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Error searching MTR Fab data: {}", e.getMessage(), e);
            throw new RuntimeException("Error searching MTR Fab data: " + e.getMessage());
        }
    }

    
}