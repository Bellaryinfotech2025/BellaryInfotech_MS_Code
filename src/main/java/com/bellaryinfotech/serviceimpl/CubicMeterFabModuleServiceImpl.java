package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.CubicMeterFabModuleDTO;
import com.bellaryinfotech.model.CubicMeterFabModule;
import com.bellaryinfotech.repo.CubicMeterFabModuleRepository;
import com.bellaryinfotech.service.CubicMeterFabModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CubicMeterFabModuleServiceImpl implements CubicMeterFabModuleService {

    @Autowired
    private CubicMeterFabModuleRepository cubicMeterFabModuleRepository;

    @Override
    public CubicMeterFabModuleDTO createCubicMeterFabModule(CubicMeterFabModuleDTO cubicMeterFabModuleDTO) {
        try {
            CubicMeterFabModule cubicMeterFabModule = convertToEntity(cubicMeterFabModuleDTO);
            cubicMeterFabModule.setCreatedDate(LocalDateTime.now());
            cubicMeterFabModule.setLastUpdatedDate(LocalDateTime.now());
            
            if (cubicMeterFabModule.getStatus() == null || cubicMeterFabModule.getStatus().isEmpty()) {
                cubicMeterFabModule.setStatus("ACTIVE");
            }
            
            CubicMeterFabModule savedEntity = cubicMeterFabModuleRepository.save(cubicMeterFabModule);
            return convertToDTO(savedEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error creating CubicMeterFabModule: " + e.getMessage(), e);
        }
    }

    @Override
    public List<CubicMeterFabModuleDTO> createCubicMeterFabModuleEntries(List<CubicMeterFabModuleDTO> cubicMeterFabModuleDTOs) {
        try {
            List<CubicMeterFabModule> entities = cubicMeterFabModuleDTOs.stream()
                    .map(dto -> {
                        CubicMeterFabModule entity = convertToEntity(dto);
                        entity.setCreatedDate(LocalDateTime.now());
                        entity.setLastUpdatedDate(LocalDateTime.now());
                        
                        if (entity.getStatus() == null || entity.getStatus().isEmpty()) {
                            entity.setStatus("ACTIVE");
                        }
                        
                        return entity;
                    })
                    .collect(Collectors.toList());
            
            List<CubicMeterFabModule> savedEntities = cubicMeterFabModuleRepository.saveAll(entities);
            return savedEntities.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error creating CubicMeterFabModule entries: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CubicMeterFabModuleDTO> getAllCubicMeterFabModules() {
        try {
            List<CubicMeterFabModule> entities = cubicMeterFabModuleRepository.findAll();
            return entities.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all CubicMeterFabModules: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CubicMeterFabModuleDTO> getCubicMeterFabModuleById(Long id) {
        try {
            Optional<CubicMeterFabModule> entity = cubicMeterFabModuleRepository.findById(id);
            return entity.map(this::convertToDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching CubicMeterFabModule by ID: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctWorkOrders() {
        try {
            return cubicMeterFabModuleRepository.findDistinctWorkOrders();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching distinct work orders: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctBuildingNamesByWorkOrder(String workOrder) {
        try {
            return cubicMeterFabModuleRepository.findDistinctBuildingNamesByWorkOrder(workOrder);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching distinct building names: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CubicMeterFabModuleDTO> getCubicMeterFabModulesByWorkOrderAndBuildingName(String workOrder, String buildingName) {
        try {
            List<CubicMeterFabModule> entities = cubicMeterFabModuleRepository.findByWorkOrderAndBuildingName(workOrder, buildingName);
            return entities.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching CubicMeterFabModules by work order and building name: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CubicMeterFabModuleDTO> getCubicMeterFabModulesByWorkOrderAndBuildingNameAndDrawingNoAndMarkNo(
            String workOrder, String buildingName, String drawingNo, String markNo) {
        try {
            List<CubicMeterFabModule> entities = cubicMeterFabModuleRepository
                    .findByWorkOrderAndBuildingNameAndDrawingNoAndMarkNo(workOrder, buildingName, drawingNo, markNo);
            return entities.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching CubicMeterFabModules by criteria: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CubicMeterFabModuleDTO> getCubicMeterFabModulesByRaNo(String raNo) {
        try {
            List<CubicMeterFabModule> entities = cubicMeterFabModuleRepository.findByRaNo(raNo);
            return entities.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching CubicMeterFabModules by RA NO: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctRaNos() {
        try {
            return cubicMeterFabModuleRepository.findDistinctRaNos();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching distinct RA NOs: " + e.getMessage(), e);
        }
    }

    // NEW: Get CubicMeterFabModules by work order only
    @Override
    @Transactional(readOnly = true)
    public List<CubicMeterFabModuleDTO> getCubicMeterFabModulesByWorkOrder(String workOrder) {
        try {
            List<CubicMeterFabModule> entities = cubicMeterFabModuleRepository.findByWorkOrder(workOrder);
            return entities.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching CubicMeterFabModules by work order: " + e.getMessage(), e);
        }
    }

    @Override
    public CubicMeterFabModuleDTO updateCubicMeterFabModule(Long id, CubicMeterFabModuleDTO cubicMeterFabModuleDTO) {
        try {
            Optional<CubicMeterFabModule> existingEntityOpt = cubicMeterFabModuleRepository.findById(id);
            if (existingEntityOpt.isPresent()) {
                CubicMeterFabModule existingEntity = existingEntityOpt.get();
                
                // Update fields
                existingEntity.setWorkOrder(cubicMeterFabModuleDTO.getWorkOrder());
                existingEntity.setBuildingName(cubicMeterFabModuleDTO.getBuildingName());
                existingEntity.setDrawingNo(cubicMeterFabModuleDTO.getDrawingNo());
                existingEntity.setMarkNo(cubicMeterFabModuleDTO.getMarkNo());
                existingEntity.setMarkQty(cubicMeterFabModuleDTO.getMarkQty());
                existingEntity.setEachMarkLength(cubicMeterFabModuleDTO.getEachMarkLength());
                existingEntity.setTotalMarkLength(cubicMeterFabModuleDTO.getTotalMarkLength());
                existingEntity.setRaNo(cubicMeterFabModuleDTO.getRaNo());
                existingEntity.setItemNo(cubicMeterFabModuleDTO.getItemNo());
                existingEntity.setSection(cubicMeterFabModuleDTO.getSection());
                existingEntity.setLengthMm(cubicMeterFabModuleDTO.getLengthMm());
                existingEntity.setWidthMm(cubicMeterFabModuleDTO.getWidthMm());
                existingEntity.setHeightMm(cubicMeterFabModuleDTO.getHeightMm());
                existingEntity.setItemQty(cubicMeterFabModuleDTO.getItemQty());
                existingEntity.setTotalVolume(cubicMeterFabModuleDTO.getTotalVolume());
                existingEntity.setRemarks(cubicMeterFabModuleDTO.getRemarks());
                existingEntity.setCuttingStage(cubicMeterFabModuleDTO.getCuttingStage());
                existingEntity.setFitUpStage(cubicMeterFabModuleDTO.getFitUpStage());
                existingEntity.setWeldingStage(cubicMeterFabModuleDTO.getWeldingStage());
                existingEntity.setFinishingStage(cubicMeterFabModuleDTO.getFinishingStage());
                existingEntity.setServiceDescription(cubicMeterFabModuleDTO.getServiceDescription());
                existingEntity.setUom(cubicMeterFabModuleDTO.getUom());
                existingEntity.setDataModule(cubicMeterFabModuleDTO.getDataModule());
                existingEntity.setLastUpdatedBy(cubicMeterFabModuleDTO.getLastUpdatedBy());
                existingEntity.setLastUpdatedDate(LocalDateTime.now());
                
                CubicMeterFabModule updatedEntity = cubicMeterFabModuleRepository.save(existingEntity);
                return convertToDTO(updatedEntity);
            } else {
                throw new RuntimeException("CubicMeterFabModule not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating CubicMeterFabModule: " + e.getMessage(), e);
        }
    }

    @Override
    public CubicMeterFabModuleDTO updateCubicMeterFabModuleStatus(Long id, String status) {
        try {
            Optional<CubicMeterFabModule> existingEntityOpt = cubicMeterFabModuleRepository.findById(id);
            if (existingEntityOpt.isPresent()) {
                CubicMeterFabModule existingEntity = existingEntityOpt.get();
                existingEntity.setStatus(status);
                existingEntity.setLastUpdatedDate(LocalDateTime.now());
                
                CubicMeterFabModule updatedEntity = cubicMeterFabModuleRepository.save(existingEntity);
                return convertToDTO(updatedEntity);
            } else {
                throw new RuntimeException("CubicMeterFabModule not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating CubicMeterFabModule status: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteCubicMeterFabModule(Long id) {
        try {
            if (cubicMeterFabModuleRepository.existsById(id)) {
                cubicMeterFabModuleRepository.deleteById(id);
                return true;
            } else {
                throw new RuntimeException("CubicMeterFabModule not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting CubicMeterFabModule: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean softDeleteCubicMeterFabModule(Long id) {
        try {
            Optional<CubicMeterFabModule> existingEntityOpt = cubicMeterFabModuleRepository.findById(id);
            if (existingEntityOpt.isPresent()) {
                CubicMeterFabModule existingEntity = existingEntityOpt.get();
                existingEntity.setStatus("DELETED");
                existingEntity.setLastUpdatedDate(LocalDateTime.now());
                cubicMeterFabModuleRepository.save(existingEntity);
                return true;
            } else {
                throw new RuntimeException("CubicMeterFabModule not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error soft deleting CubicMeterFabModule: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CubicMeterFabModuleDTO> searchCubicMeterFabModules(String workOrder, String buildingName, 
                                                                  String drawingNo, String markNo, String raNo) {
        try {
            List<CubicMeterFabModule> entities = cubicMeterFabModuleRepository
                    .searchCubicMeterFabModules(workOrder, buildingName, drawingNo, markNo, raNo);
            return entities.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error searching CubicMeterFabModules: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Long getActiveCubicMeterFabModulesCount() {
        try {
            return cubicMeterFabModuleRepository.countActiveCubicMeterFabModules();
        } catch (Exception e) {
            throw new RuntimeException("Error getting active CubicMeterFabModules count: " + e.getMessage(), e);
        }
    }

    @Override
    public CubicMeterFabModuleDTO convertToDTO(CubicMeterFabModule cubicMeterFabModule) {
        if (cubicMeterFabModule == null) {
            return null;
        }
        
        return new CubicMeterFabModuleDTO(
                cubicMeterFabModule.getId(),
                cubicMeterFabModule.getWorkOrder(),
                cubicMeterFabModule.getBuildingName(),
                cubicMeterFabModule.getDrawingNo(),
                cubicMeterFabModule.getMarkNo(),
                cubicMeterFabModule.getMarkQty(),
                cubicMeterFabModule.getEachMarkLength(),
                cubicMeterFabModule.getTotalMarkLength(),
                cubicMeterFabModule.getRaNo(),
                cubicMeterFabModule.getItemNo(),
                cubicMeterFabModule.getSection(),
                cubicMeterFabModule.getLengthMm(),
                cubicMeterFabModule.getWidthMm(),
                cubicMeterFabModule.getHeightMm(),
                cubicMeterFabModule.getItemQty(),
                cubicMeterFabModule.getTotalVolume(),
                cubicMeterFabModule.getRemarks(),
                cubicMeterFabModule.getCuttingStage(),
                cubicMeterFabModule.getFitUpStage(),
                cubicMeterFabModule.getWeldingStage(),
                cubicMeterFabModule.getFinishingStage(),
                cubicMeterFabModule.getCreatedBy(),
                cubicMeterFabModule.getCreatedDate(),
                cubicMeterFabModule.getLastUpdatedBy(),
                cubicMeterFabModule.getLastUpdatedDate(),
                cubicMeterFabModule.getStatus(),
                cubicMeterFabModule.getServiceDescription(),
                cubicMeterFabModule.getUom(),
                cubicMeterFabModule.getDataModule()
        );
    }

    @Override
    public CubicMeterFabModule convertToEntity(CubicMeterFabModuleDTO cubicMeterFabModuleDTO) {
        if (cubicMeterFabModuleDTO == null) {
            return null;
        }
        
        CubicMeterFabModule cubicMeterFabModule = new CubicMeterFabModule();
        cubicMeterFabModule.setId(cubicMeterFabModuleDTO.getId());
        cubicMeterFabModule.setWorkOrder(cubicMeterFabModuleDTO.getWorkOrder());
        cubicMeterFabModule.setBuildingName(cubicMeterFabModuleDTO.getBuildingName());
        cubicMeterFabModule.setDrawingNo(cubicMeterFabModuleDTO.getDrawingNo());
        cubicMeterFabModule.setMarkNo(cubicMeterFabModuleDTO.getMarkNo());
        cubicMeterFabModule.setMarkQty(cubicMeterFabModuleDTO.getMarkQty());
        cubicMeterFabModule.setEachMarkLength(cubicMeterFabModuleDTO.getEachMarkLength());
        cubicMeterFabModule.setTotalMarkLength(cubicMeterFabModuleDTO.getTotalMarkLength());
        cubicMeterFabModule.setRaNo(cubicMeterFabModuleDTO.getRaNo());
        cubicMeterFabModule.setItemNo(cubicMeterFabModuleDTO.getItemNo());
        cubicMeterFabModule.setSection(cubicMeterFabModuleDTO.getSection());
        cubicMeterFabModule.setLengthMm(cubicMeterFabModuleDTO.getLengthMm());
        cubicMeterFabModule.setWidthMm(cubicMeterFabModuleDTO.getWidthMm());
        cubicMeterFabModule.setHeightMm(cubicMeterFabModuleDTO.getHeightMm());
        cubicMeterFabModule.setItemQty(cubicMeterFabModuleDTO.getItemQty());
        cubicMeterFabModule.setTotalVolume(cubicMeterFabModuleDTO.getTotalVolume());
        cubicMeterFabModule.setRemarks(cubicMeterFabModuleDTO.getRemarks());
        cubicMeterFabModule.setCuttingStage(cubicMeterFabModuleDTO.getCuttingStage());
        cubicMeterFabModule.setFitUpStage(cubicMeterFabModuleDTO.getFitUpStage());
        cubicMeterFabModule.setWeldingStage(cubicMeterFabModuleDTO.getWeldingStage());
        cubicMeterFabModule.setFinishingStage(cubicMeterFabModuleDTO.getFinishingStage());
        cubicMeterFabModule.setCreatedBy(cubicMeterFabModuleDTO.getCreatedBy());
        cubicMeterFabModule.setCreatedDate(cubicMeterFabModuleDTO.getCreatedDate());
        cubicMeterFabModule.setLastUpdatedBy(cubicMeterFabModuleDTO.getLastUpdatedBy());
        cubicMeterFabModule.setLastUpdatedDate(cubicMeterFabModuleDTO.getLastUpdatedDate());
        cubicMeterFabModule.setStatus(cubicMeterFabModuleDTO.getStatus());
        cubicMeterFabModule.setServiceDescription(cubicMeterFabModuleDTO.getServiceDescription());
        cubicMeterFabModule.setUom(cubicMeterFabModuleDTO.getUom());
        cubicMeterFabModule.setDataModule(cubicMeterFabModuleDTO.getDataModule());
        
        return cubicMeterFabModule;
    }
}
