package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.CubicMeterModuleDTO;
import com.bellaryinfotech.model.CubicMeterModule;
import com.bellaryinfotech.repo.CubicMeterModuleRepository;
import com.bellaryinfotech.service.CubicMeterModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CubicMeterModuleServiceImpl implements CubicMeterModuleService {

    @Autowired
    private CubicMeterModuleRepository cubicMeterModuleRepository;

    // Helper method to convert Entity to DTO
    private CubicMeterModuleDTO convertToDTO(CubicMeterModule entity) {
        if (entity == null) {
            return null;
        }
        
        CubicMeterModuleDTO dto = new CubicMeterModuleDTO();
        dto.setId(entity.getId());
        dto.setOrderId(entity.getOrderId());
        dto.setClientName(entity.getClientName());
        dto.setWorkOrder(entity.getWorkOrder());
        dto.setServiceDescription(entity.getServiceDescription());
        dto.setUom(entity.getUom());
        dto.setDepartment(entity.getDepartment());
        dto.setWorkLocation(entity.getWorkLocation());
        dto.setDataModule(entity.getDataModule());
        dto.setBuildingName(entity.getBuildingName());
        dto.setDrawingNo(entity.getDrawingNo());
        dto.setDrawingReceivedDate(entity.getDrawingReceivedDate());
        dto.setTargetDate(entity.getTargetDate());
        dto.setMarkNo(entity.getMarkNo());
        dto.setMarkQty(entity.getMarkQty());
        dto.setEachMarkLength(entity.getEachMarkLength());
        dto.setTotalMarkLength(entity.getTotalMarkLength());
        dto.setItemNo(entity.getItemNo());
        dto.setSection(entity.getSection());
        dto.setLengthMm(entity.getLengthMm());
        dto.setWidthMm(entity.getWidthMm());
        dto.setHeightMm(entity.getHeightMm());
        dto.setItemQty(entity.getItemQty());
        dto.setTotalVolume(entity.getTotalVolume());
        dto.setRemarks(entity.getRemarks());
        dto.setTenantId(entity.getTenantId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        
        return dto;
    }

    // Helper method to convert DTO to Entity
    private CubicMeterModule convertToEntity(CubicMeterModuleDTO dto) {
        if (dto == null) {
            return null;
        }
        
        CubicMeterModule entity = new CubicMeterModule();
        entity.setId(dto.getId());
        entity.setOrderId(dto.getOrderId());
        entity.setClientName(dto.getClientName());
        entity.setWorkOrder(dto.getWorkOrder());
        entity.setServiceDescription(dto.getServiceDescription());
        entity.setUom(dto.getUom());
        entity.setDepartment(dto.getDepartment());
        entity.setWorkLocation(dto.getWorkLocation());
        entity.setDataModule(dto.getDataModule());
        entity.setBuildingName(dto.getBuildingName());
        entity.setDrawingNo(dto.getDrawingNo());
        entity.setDrawingReceivedDate(dto.getDrawingReceivedDate());
        entity.setTargetDate(dto.getTargetDate());
        entity.setMarkNo(dto.getMarkNo());
        entity.setMarkQty(dto.getMarkQty());
        entity.setEachMarkLength(dto.getEachMarkLength());
        entity.setTotalMarkLength(dto.getTotalMarkLength());
        entity.setItemNo(dto.getItemNo());
        entity.setSection(dto.getSection());
        entity.setLengthMm(dto.getLengthMm());
        entity.setWidthMm(dto.getWidthMm());
        entity.setHeightMm(dto.getHeightMm());
        entity.setItemQty(dto.getItemQty());
        entity.setTotalVolume(dto.getTotalVolume());
        entity.setRemarks(dto.getRemarks());
        entity.setTenantId(dto.getTenantId());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setUpdatedDate(dto.getUpdatedDate());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedBy(dto.getUpdatedBy());
        
        return entity;
    }

    // Create operations
    @Override
    public CubicMeterModuleDTO createCubicMeterModule(CubicMeterModuleDTO cubicMeterModuleDTO) {
        try {
            CubicMeterModule entity = convertToEntity(cubicMeterModuleDTO);
            if (entity.getCreatedDate() == null) {
                entity.setCreatedDate(LocalDateTime.now());
            }
            if (entity.getUpdatedDate() == null) {
                entity.setUpdatedDate(LocalDateTime.now());
            }
            if (entity.getCreatedBy() == null) {
                entity.setCreatedBy("system");
            }
            if (entity.getUpdatedBy() == null) {
                entity.setUpdatedBy("system");
            }
            if (entity.getTenantId() == null) {
                entity.setTenantId("DEFAULT");
            }
            
            CubicMeterModule savedEntity = cubicMeterModuleRepository.save(entity);
            return convertToDTO(savedEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error creating cubic meter module: " + e.getMessage(), e);
        }
    }

    @Override
    public List<CubicMeterModuleDTO> createCubicMeterModules(List<CubicMeterModuleDTO> cubicMeterModuleDTOs) {
        try {
            List<CubicMeterModule> entities = cubicMeterModuleDTOs.stream()
                    .map(this::convertToEntity)
                    .peek(entity -> {
                        if (entity.getCreatedDate() == null) {
                            entity.setCreatedDate(LocalDateTime.now());
                        }
                        if (entity.getUpdatedDate() == null) {
                            entity.setUpdatedDate(LocalDateTime.now());
                        }
                        if (entity.getCreatedBy() == null) {
                            entity.setCreatedBy("system");
                        }
                        if (entity.getUpdatedBy() == null) {
                            entity.setUpdatedBy("system");
                        }
                        if (entity.getTenantId() == null) {
                            entity.setTenantId("DEFAULT");
                        }
                    })
                    .collect(Collectors.toList());
            
            List<CubicMeterModule> savedEntities = cubicMeterModuleRepository.saveAll(entities);
            return savedEntities.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error creating cubic meter modules: " + e.getMessage(), e);
        }
    }

    // Read operations
    @Override
    @Transactional(readOnly = true)
    public List<CubicMeterModuleDTO> getAllCubicMeterModules() {
        try {
            List<CubicMeterModule> entities = cubicMeterModuleRepository.findAll();
            return entities.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all cubic meter modules: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CubicMeterModuleDTO> getCubicMeterModuleById(Long id) {
        try {
            Optional<CubicMeterModule> entity = cubicMeterModuleRepository.findById(id);
            return entity.map(this::convertToDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching cubic meter module by ID: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CubicMeterModuleDTO> getCubicMeterModulesByWorkOrder(String workOrder) {
        try {
            List<CubicMeterModule> entities = cubicMeterModuleRepository.findByWorkOrder(workOrder);
            return entities.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching cubic meter modules by work order: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CubicMeterModuleDTO> searchCubicMeterModulesByMarkNo(String markNo) {
        try {
            List<CubicMeterModule> entities = cubicMeterModuleRepository.findByMarkNo(markNo);
            return entities.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error searching cubic meter modules by mark number: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CubicMeterModuleDTO> getCubicMeterModulesByClientName(String clientName) {
        try {
            List<CubicMeterModule> entities = cubicMeterModuleRepository.findByClientName(clientName);
            return entities.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching cubic meter modules by client name: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CubicMeterModuleDTO> getCubicMeterModulesByBuildingName(String buildingName) {
        try {
            List<CubicMeterModule> entities = cubicMeterModuleRepository.findByBuildingName(buildingName);
            return entities.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching cubic meter modules by building name: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CubicMeterModuleDTO> getCubicMeterModulesByWorkOrderAndBuildingName(String workOrder, String buildingName) {
        try {
            List<CubicMeterModule> entities = cubicMeterModuleRepository.findByWorkOrderAndBuildingName(workOrder, buildingName);
            return entities.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching cubic meter modules by work order and building name: " + e.getMessage(), e);
        }
    }

    // Update operations
    @Override
    public CubicMeterModuleDTO updateCubicMeterModule(Long id, CubicMeterModuleDTO cubicMeterModuleDTO) {
        try {
            Optional<CubicMeterModule> existingEntityOpt = cubicMeterModuleRepository.findById(id);
            if (existingEntityOpt.isPresent()) {
                CubicMeterModule existingEntity = existingEntityOpt.get();
                
                // Update fields from DTO
                if (cubicMeterModuleDTO.getOrderId() != null) {
                    existingEntity.setOrderId(cubicMeterModuleDTO.getOrderId());
                }
                if (cubicMeterModuleDTO.getClientName() != null) {
                    existingEntity.setClientName(cubicMeterModuleDTO.getClientName());
                }
                if (cubicMeterModuleDTO.getWorkOrder() != null) {
                    existingEntity.setWorkOrder(cubicMeterModuleDTO.getWorkOrder());
                }
                if (cubicMeterModuleDTO.getServiceDescription() != null) {
                    existingEntity.setServiceDescription(cubicMeterModuleDTO.getServiceDescription());
                }
                if (cubicMeterModuleDTO.getUom() != null) {
                    existingEntity.setUom(cubicMeterModuleDTO.getUom());
                }
                if (cubicMeterModuleDTO.getDepartment() != null) {
                    existingEntity.setDepartment(cubicMeterModuleDTO.getDepartment());
                }
                if (cubicMeterModuleDTO.getWorkLocation() != null) {
                    existingEntity.setWorkLocation(cubicMeterModuleDTO.getWorkLocation());
                }
                if (cubicMeterModuleDTO.getDataModule() != null) {
                    existingEntity.setDataModule(cubicMeterModuleDTO.getDataModule());
                }
                if (cubicMeterModuleDTO.getBuildingName() != null) {
                    existingEntity.setBuildingName(cubicMeterModuleDTO.getBuildingName());
                }
                if (cubicMeterModuleDTO.getDrawingNo() != null) {
                    existingEntity.setDrawingNo(cubicMeterModuleDTO.getDrawingNo());
                }
                if (cubicMeterModuleDTO.getDrawingReceivedDate() != null) {
                    existingEntity.setDrawingReceivedDate(cubicMeterModuleDTO.getDrawingReceivedDate());
                }
                if (cubicMeterModuleDTO.getTargetDate() != null) {
                    existingEntity.setTargetDate(cubicMeterModuleDTO.getTargetDate());
                }
                if (cubicMeterModuleDTO.getMarkNo() != null) {
                    existingEntity.setMarkNo(cubicMeterModuleDTO.getMarkNo());
                }
                if (cubicMeterModuleDTO.getMarkQty() != null) {
                    existingEntity.setMarkQty(cubicMeterModuleDTO.getMarkQty());
                }
                if (cubicMeterModuleDTO.getEachMarkLength() != null) {
                    existingEntity.setEachMarkLength(cubicMeterModuleDTO.getEachMarkLength());
                }
                if (cubicMeterModuleDTO.getTotalMarkLength() != null) {
                    existingEntity.setTotalMarkLength(cubicMeterModuleDTO.getTotalMarkLength());
                }
                if (cubicMeterModuleDTO.getItemNo() != null) {
                    existingEntity.setItemNo(cubicMeterModuleDTO.getItemNo());
                }
                if (cubicMeterModuleDTO.getSection() != null) {
                    existingEntity.setSection(cubicMeterModuleDTO.getSection());
                }
                if (cubicMeterModuleDTO.getLengthMm() != null) {
                    existingEntity.setLengthMm(cubicMeterModuleDTO.getLengthMm());
                }
                if (cubicMeterModuleDTO.getWidthMm() != null) {
                    existingEntity.setWidthMm(cubicMeterModuleDTO.getWidthMm());
                }
                if (cubicMeterModuleDTO.getHeightMm() != null) {
                    existingEntity.setHeightMm(cubicMeterModuleDTO.getHeightMm());
                }
                if (cubicMeterModuleDTO.getItemQty() != null) {
                    existingEntity.setItemQty(cubicMeterModuleDTO.getItemQty());
                }
                if (cubicMeterModuleDTO.getTotalVolume() != null) {
                    existingEntity.setTotalVolume(cubicMeterModuleDTO.getTotalVolume());
                }
                if (cubicMeterModuleDTO.getRemarks() != null) {
                    existingEntity.setRemarks(cubicMeterModuleDTO.getRemarks());
                }
                if (cubicMeterModuleDTO.getTenantId() != null) {
                    existingEntity.setTenantId(cubicMeterModuleDTO.getTenantId());
                }
                if (cubicMeterModuleDTO.getUpdatedBy() != null) {
                    existingEntity.setUpdatedBy(cubicMeterModuleDTO.getUpdatedBy());
                }
                
                existingEntity.setUpdatedDate(LocalDateTime.now());
                
                CubicMeterModule updatedEntity = cubicMeterModuleRepository.save(existingEntity);
                return convertToDTO(updatedEntity);
            } else {
                throw new RuntimeException("Cubic meter module not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating cubic meter module: " + e.getMessage(), e);
        }
    }

    @Override
    public List<CubicMeterModuleDTO> updateCubicMeterModules(List<CubicMeterModuleDTO> cubicMeterModuleDTOs) {
        try {
            List<CubicMeterModuleDTO> updatedDTOs = cubicMeterModuleDTOs.stream()
                    .map(dto -> updateCubicMeterModule(dto.getId(), dto))
                    .collect(Collectors.toList());
            return updatedDTOs;
        } catch (Exception e) {
            throw new RuntimeException("Error updating cubic meter modules: " + e.getMessage(), e);
        }
    }

    // Delete operations
    @Override
    public boolean deleteCubicMeterModule(Long id) {
        try {
            if (cubicMeterModuleRepository.existsById(id)) {
                cubicMeterModuleRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting cubic meter module: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteCubicMeterModulesByWorkOrder(String workOrder) {
        try {
            List<CubicMeterModule> entities = cubicMeterModuleRepository.findByWorkOrder(workOrder);
            if (!entities.isEmpty()) {
                cubicMeterModuleRepository.deleteByWorkOrder(workOrder);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting cubic meter modules by work order: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteCubicMeterModulesByMarkNo(String markNo) {
        try {
            List<CubicMeterModule> entities = cubicMeterModuleRepository.findByMarkNo(markNo);
            if (!entities.isEmpty()) {
                cubicMeterModuleRepository.deleteByMarkNo(markNo);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting cubic meter modules by mark number: " + e.getMessage(), e);
        }
    }

    // Search operations
    @Override
    @Transactional(readOnly = true)
    public List<CubicMeterModuleDTO> searchCubicMeterModules(String workOrder, String buildingName, String markNo, String clientName) {
        try {
            List<CubicMeterModule> entities = cubicMeterModuleRepository.findByMultipleCriteria(workOrder, buildingName, markNo, clientName);
            return entities.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error searching cubic meter modules: " + e.getMessage(), e);
        }
    }

    // Utility operations
    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctWorkOrders() {
        try {
            return cubicMeterModuleRepository.findDistinctWorkOrders();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching distinct work orders: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctBuildingNamesByWorkOrder(String workOrder) {
        try {
            return cubicMeterModuleRepository.findDistinctBuildingNamesByWorkOrder(workOrder);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching distinct building names by work order: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctMarkNosByWorkOrder(String workOrder) {
        try {
            return cubicMeterModuleRepository.findDistinctMarkNosByWorkOrder(workOrder);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching distinct mark numbers by work order: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long countCubicMeterModulesByWorkOrder(String workOrder) {
        try {
            return cubicMeterModuleRepository.countByWorkOrder(workOrder);
        } catch (Exception e) {
            throw new RuntimeException("Error counting cubic meter modules by work order: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalVolumeByWorkOrder(String workOrder) {
        try {
            Double totalVolume = cubicMeterModuleRepository.getTotalVolumeByWorkOrder(workOrder);
            return totalVolume != null ? totalVolume : 0.0;
        } catch (Exception e) {
            throw new RuntimeException("Error getting total volume by work order: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalVolumeByMarkNo(String markNo) {
        try {
            Double totalVolume = cubicMeterModuleRepository.getTotalVolumeByMarkNo(markNo);
            return totalVolume != null ? totalVolume : 0.0;
        } catch (Exception e) {
            throw new RuntimeException("Error getting total volume by mark number: " + e.getMessage(), e);
        }
    }

    // Bulk operations
    @Override
    public List<CubicMeterModuleDTO> bulkInsertCubicMeterModules(List<CubicMeterModuleDTO> cubicMeterModuleDTOs) {
        try {
            return createCubicMeterModules(cubicMeterModuleDTOs);
        } catch (Exception e) {
            throw new RuntimeException("Error bulk inserting cubic meter modules: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean bulkDeleteCubicMeterModules(List<Long> ids) {
        try {
            List<CubicMeterModule> entities = cubicMeterModuleRepository.findAllById(ids);
            if (!entities.isEmpty()) {
                cubicMeterModuleRepository.deleteAllById(ids);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error bulk deleting cubic meter modules: " + e.getMessage(), e);
        }
    }

    // Validation operations
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        try {
            return cubicMeterModuleRepository.existsById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error checking if cubic meter module exists by ID: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByWorkOrder(String workOrder) {
        try {
            return !cubicMeterModuleRepository.findByWorkOrder(workOrder).isEmpty();
        } catch (Exception e) {
            throw new RuntimeException("Error checking if cubic meter module exists by work order: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByMarkNo(String markNo) {
        try {
            return !cubicMeterModuleRepository.findByMarkNo(markNo).isEmpty();
        } catch (Exception e) {
            throw new RuntimeException("Error checking if cubic meter module exists by mark number: " + e.getMessage(), e);
        }
    }
}
