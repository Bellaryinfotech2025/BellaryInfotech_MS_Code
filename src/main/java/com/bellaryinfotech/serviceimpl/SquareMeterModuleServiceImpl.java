package com.bellaryinfotech.serviceimpl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bellaryinfotech.DTO.SquareMeterModuleDTO;
import com.bellaryinfotech.model.SquareMeterModule;
import com.bellaryinfotech.repo.SquareMeterModuleRepository;
import com.bellaryinfotech.service.SquareMeterModuleService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SquareMeterModuleServiceImpl implements SquareMeterModuleService {

    @Autowired
    private SquareMeterModuleRepository squareMeterModuleRepository;

    @Override
    public SquareMeterModuleDTO createSquareMeterModule(SquareMeterModuleDTO squareMeterModuleDTO) {
        try {
            SquareMeterModule squareMeterModule = convertToEntity(squareMeterModuleDTO);
            squareMeterModule.setCreatedDate(LocalDateTime.now());
            squareMeterModule.setUpdatedDate(LocalDateTime.now());
            
            SquareMeterModule savedModule = squareMeterModuleRepository.save(squareMeterModule);
            return convertToDTO(savedModule);
        } catch (Exception e) {
            throw new RuntimeException("Error creating square meter module: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SquareMeterModuleDTO> createSquareMeterModules(List<SquareMeterModuleDTO> squareMeterModuleDTOs) {
        try {
            List<SquareMeterModule> squareMeterModules = convertToEntityList(squareMeterModuleDTOs);
            squareMeterModules.forEach(module -> {
                module.setCreatedDate(LocalDateTime.now());
                module.setUpdatedDate(LocalDateTime.now());
            });
            
            List<SquareMeterModule> savedModules = squareMeterModuleRepository.saveAll(squareMeterModules);
            return convertToDTOList(savedModules);
        } catch (Exception e) {
            throw new RuntimeException("Error creating square meter modules: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SquareMeterModuleDTO> getAllSquareMeterModules() {
        try {
            List<SquareMeterModule> squareMeterModules = squareMeterModuleRepository.findAll();
            return convertToDTOList(squareMeterModules);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all square meter modules: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SquareMeterModuleDTO> getSquareMeterModuleById(Long id) {
        try {
            Optional<SquareMeterModule> squareMeterModule = squareMeterModuleRepository.findById(id);
            return squareMeterModule.map(this::convertToDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching square meter module by ID: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SquareMeterModuleDTO> getSquareMeterModulesByWorkOrder(String workOrder) {
        try {
            List<SquareMeterModule> squareMeterModules = squareMeterModuleRepository.findByWorkOrder(workOrder);
            return convertToDTOList(squareMeterModules);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching square meter modules by work order: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SquareMeterModuleDTO> getSquareMeterModulesByMarkNo(String markNo) {
        try {
            List<SquareMeterModule> squareMeterModules = squareMeterModuleRepository.findByMarkNo(markNo);
            return convertToDTOList(squareMeterModules);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching square meter modules by mark number: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SquareMeterModuleDTO> getSquareMeterModulesByClientName(String clientName) {
        try {
            List<SquareMeterModule> squareMeterModules = squareMeterModuleRepository.findByClientName(clientName);
            return convertToDTOList(squareMeterModules);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching square meter modules by client name: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SquareMeterModuleDTO> getSquareMeterModulesByBuildingName(String buildingName) {
        try {
            List<SquareMeterModule> squareMeterModules = squareMeterModuleRepository.findByBuildingName(buildingName);
            return convertToDTOList(squareMeterModules);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching square meter modules by building name: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SquareMeterModuleDTO> getSquareMeterModulesByWorkOrderAndBuildingName(String workOrder, String buildingName) {
        try {
            List<SquareMeterModule> squareMeterModules = squareMeterModuleRepository.findByWorkOrderAndBuildingName(workOrder, buildingName);
            return convertToDTOList(squareMeterModules);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching square meter modules by work order and building name: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SquareMeterModuleDTO> getSquareMeterModulesByWorkOrderAndMarkNo(String workOrder, String markNo) {
        try {
            List<SquareMeterModule> squareMeterModules = squareMeterModuleRepository.findByWorkOrderAndMarkNo(workOrder, markNo);
            return convertToDTOList(squareMeterModules);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching square meter modules by work order and mark number: " + e.getMessage(), e);
        }
    }

    @Override
    public SquareMeterModuleDTO updateSquareMeterModule(Long id, SquareMeterModuleDTO squareMeterModuleDTO) {
        try {
            Optional<SquareMeterModule> existingModuleOpt = squareMeterModuleRepository.findById(id);
            if (existingModuleOpt.isPresent()) {
                SquareMeterModule existingModule = existingModuleOpt.get();
                
                // Update fields
                existingModule.setOrderId(squareMeterModuleDTO.getOrderId());
                existingModule.setClientName(squareMeterModuleDTO.getClientName());
                existingModule.setWorkOrder(squareMeterModuleDTO.getWorkOrder());
                existingModule.setServiceDescription(squareMeterModuleDTO.getServiceDescription());
                existingModule.setUom(squareMeterModuleDTO.getUom());
                existingModule.setDepartment(squareMeterModuleDTO.getDepartment());
                existingModule.setWorkLocation(squareMeterModuleDTO.getWorkLocation());
                existingModule.setDataModule(squareMeterModuleDTO.getDataModule());
                existingModule.setBuildingName(squareMeterModuleDTO.getBuildingName());
                existingModule.setDrawingNo(squareMeterModuleDTO.getDrawingNo());
                existingModule.setDrawingReceivedDate(squareMeterModuleDTO.getDrawingReceivedDate());
                existingModule.setTargetDate(squareMeterModuleDTO.getTargetDate());
                existingModule.setMarkNo(squareMeterModuleDTO.getMarkNo());
                existingModule.setMarkQty(squareMeterModuleDTO.getMarkQty());
                existingModule.setEachMarkLength(squareMeterModuleDTO.getEachMarkLength());
                existingModule.setTotalMarkLength(squareMeterModuleDTO.getTotalMarkLength());
                existingModule.setItemNo(squareMeterModuleDTO.getItemNo());
                existingModule.setSection(squareMeterModuleDTO.getSection());
                existingModule.setLengthMm(squareMeterModuleDTO.getLengthMm());
                existingModule.setWidthMm(squareMeterModuleDTO.getWidthMm());
                existingModule.setItemQty(squareMeterModuleDTO.getItemQty());
                existingModule.setTotalArea(squareMeterModuleDTO.getTotalArea());
                existingModule.setRemarks(squareMeterModuleDTO.getRemarks());
                existingModule.setTenantId(squareMeterModuleDTO.getTenantId());
                existingModule.setUpdatedDate(LocalDateTime.now());
                existingModule.setUpdatedBy(squareMeterModuleDTO.getUpdatedBy());
                
                SquareMeterModule updatedModule = squareMeterModuleRepository.save(existingModule);
                return convertToDTO(updatedModule);
            } else {
                throw new RuntimeException("Square meter module not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating square meter module: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SquareMeterModuleDTO> updateSquareMeterModules(List<SquareMeterModuleDTO> squareMeterModuleDTOs) {
        try {
            List<SquareMeterModule> updatedModules = squareMeterModuleDTOs.stream()
                    .map(dto -> {
                        Optional<SquareMeterModule> existingModuleOpt = squareMeterModuleRepository.findById(dto.getId());
                        if (existingModuleOpt.isPresent()) {
                            SquareMeterModule existingModule = existingModuleOpt.get();
                            // Update fields (same as single update)
                            existingModule.setOrderId(dto.getOrderId());
                            existingModule.setClientName(dto.getClientName());
                            existingModule.setWorkOrder(dto.getWorkOrder());
                            existingModule.setServiceDescription(dto.getServiceDescription());
                            existingModule.setUom(dto.getUom());
                            existingModule.setDepartment(dto.getDepartment());
                            existingModule.setWorkLocation(dto.getWorkLocation());
                            existingModule.setDataModule(dto.getDataModule());
                            existingModule.setBuildingName(dto.getBuildingName());
                            existingModule.setDrawingNo(dto.getDrawingNo());
                            existingModule.setDrawingReceivedDate(dto.getDrawingReceivedDate());
                            existingModule.setTargetDate(dto.getTargetDate());
                            existingModule.setMarkNo(dto.getMarkNo());
                            existingModule.setMarkQty(dto.getMarkQty());
                            existingModule.setEachMarkLength(dto.getEachMarkLength());
                            existingModule.setTotalMarkLength(dto.getTotalMarkLength());
                            existingModule.setItemNo(dto.getItemNo());
                            existingModule.setSection(dto.getSection());
                            existingModule.setLengthMm(dto.getLengthMm());
                            existingModule.setWidthMm(dto.getWidthMm());
                            existingModule.setItemQty(dto.getItemQty());
                            existingModule.setTotalArea(dto.getTotalArea());
                            existingModule.setRemarks(dto.getRemarks());
                            existingModule.setTenantId(dto.getTenantId());
                            existingModule.setUpdatedDate(LocalDateTime.now());
                            existingModule.setUpdatedBy(dto.getUpdatedBy());
                            return existingModule;
                        } else {
                            throw new RuntimeException("Square meter module not found with ID: " + dto.getId());
                        }
                    })
                    .collect(Collectors.toList());
            
            List<SquareMeterModule> savedModules = squareMeterModuleRepository.saveAll(updatedModules);
            return convertToDTOList(savedModules);
        } catch (Exception e) {
            throw new RuntimeException("Error updating square meter modules: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteSquareMeterModule(Long id) {
        try {
            if (squareMeterModuleRepository.existsById(id)) {
                squareMeterModuleRepository.deleteById(id);
                return true;
            } else {
                throw new RuntimeException("Square meter module not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting square meter module: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteSquareMeterModulesByWorkOrder(String workOrder) {
        try {
            squareMeterModuleRepository.deleteByWorkOrder(workOrder);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting square meter modules by work order: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteSquareMeterModulesByMarkNo(String markNo) {
        try {
            squareMeterModuleRepository.deleteByMarkNo(markNo);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting square meter modules by mark number: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SquareMeterModuleDTO> searchSquareMeterModules(String workOrder, String buildingName, String markNo, String clientName) {
        try {
            List<SquareMeterModule> squareMeterModules = squareMeterModuleRepository.findByMultipleCriteria(workOrder, buildingName, markNo, clientName);
            return convertToDTOList(squareMeterModules);
        } catch (Exception e) {
            throw new RuntimeException("Error searching square meter modules: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SquareMeterModuleDTO> searchSquareMeterModulesByMarkNo(String markNo) {
        try {
            List<SquareMeterModule> squareMeterModules = squareMeterModuleRepository.findByMarkNo(markNo);
            return convertToDTOList(squareMeterModules);
        } catch (Exception e) {
            throw new RuntimeException("Error searching square meter modules by mark number: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctWorkOrders() {
        try {
            return squareMeterModuleRepository.findDistinctWorkOrders();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching distinct work orders: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctBuildingNamesByWorkOrder(String workOrder) {
        try {
            return squareMeterModuleRepository.findDistinctBuildingNamesByWorkOrder(workOrder);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching distinct building names by work order: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctMarkNosByWorkOrder(String workOrder) {
        try {
            return squareMeterModuleRepository.findDistinctMarkNosByWorkOrder(workOrder);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching distinct mark numbers by work order: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long countSquareMeterModulesByWorkOrder(String workOrder) {
        try {
            return squareMeterModuleRepository.countByWorkOrder(workOrder);
        } catch (Exception e) {
            throw new RuntimeException("Error counting square meter modules by work order: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalAreaByWorkOrder(String workOrder) {
        try {
            return squareMeterModuleRepository.getTotalAreaByWorkOrder(workOrder);
        } catch (Exception e) {
            throw new RuntimeException("Error getting total area by work order: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalAreaByMarkNo(String markNo) {
        try {
            return squareMeterModuleRepository.getTotalAreaByMarkNo(markNo);
        } catch (Exception e) {
            throw new RuntimeException("Error getting total area by mark number: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        try {
            return squareMeterModuleRepository.existsById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error checking if square meter module exists by ID: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByWorkOrder(String workOrder) {
        try {
            return !squareMeterModuleRepository.findByWorkOrder(workOrder).isEmpty();
        } catch (Exception e) {
            throw new RuntimeException("Error checking if square meter module exists by work order: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByMarkNo(String markNo) {
        try {
            return !squareMeterModuleRepository.findByMarkNo(markNo).isEmpty();
        } catch (Exception e) {
            throw new RuntimeException("Error checking if square meter module exists by mark number: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SquareMeterModuleDTO> bulkInsertSquareMeterModules(List<SquareMeterModuleDTO> squareMeterModuleDTOs) {
        try {
            List<SquareMeterModule> squareMeterModules = convertToEntityList(squareMeterModuleDTOs);
            squareMeterModules.forEach(module -> {
                module.setCreatedDate(LocalDateTime.now());
                module.setUpdatedDate(LocalDateTime.now());
            });
            
            List<SquareMeterModule> savedModules = squareMeterModuleRepository.saveAll(squareMeterModules);
            return convertToDTOList(savedModules);
        } catch (Exception e) {
            throw new RuntimeException("Error bulk inserting square meter modules: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean bulkDeleteSquareMeterModules(List<Long> ids) {
        try {
            squareMeterModuleRepository.deleteAllById(ids);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error bulk deleting square meter modules: " + e.getMessage(), e);
        }
    }

    @Override
    public SquareMeterModuleDTO convertToDTO(SquareMeterModule squareMeterModule) {
        if (squareMeterModule == null) {
            return null;
        }
        
        SquareMeterModuleDTO dto = new SquareMeterModuleDTO();
        dto.setId(squareMeterModule.getId());
        dto.setOrderId(squareMeterModule.getOrderId());
        dto.setClientName(squareMeterModule.getClientName());
        dto.setWorkOrder(squareMeterModule.getWorkOrder());
        dto.setServiceDescription(squareMeterModule.getServiceDescription());
        dto.setUom(squareMeterModule.getUom());
        dto.setDepartment(squareMeterModule.getDepartment());
        dto.setWorkLocation(squareMeterModule.getWorkLocation());
        dto.setDataModule(squareMeterModule.getDataModule());
        dto.setBuildingName(squareMeterModule.getBuildingName());
        dto.setDrawingNo(squareMeterModule.getDrawingNo());
        dto.setDrawingReceivedDate(squareMeterModule.getDrawingReceivedDate());
        dto.setTargetDate(squareMeterModule.getTargetDate());
        dto.setMarkNo(squareMeterModule.getMarkNo());
        dto.setMarkQty(squareMeterModule.getMarkQty());
        dto.setEachMarkLength(squareMeterModule.getEachMarkLength());
        dto.setTotalMarkLength(squareMeterModule.getTotalMarkLength());
        dto.setItemNo(squareMeterModule.getItemNo());
        dto.setSection(squareMeterModule.getSection());
        dto.setLengthMm(squareMeterModule.getLengthMm());
        dto.setWidthMm(squareMeterModule.getWidthMm());
        dto.setItemQty(squareMeterModule.getItemQty());
        dto.setTotalArea(squareMeterModule.getTotalArea());
        dto.setRemarks(squareMeterModule.getRemarks());
        dto.setTenantId(squareMeterModule.getTenantId());
        dto.setCreatedDate(squareMeterModule.getCreatedDate());
        dto.setUpdatedDate(squareMeterModule.getUpdatedDate());
        dto.setCreatedBy(squareMeterModule.getCreatedBy());
        dto.setUpdatedBy(squareMeterModule.getUpdatedBy());
        
        return dto;
    }

    @Override
    public SquareMeterModule convertToEntity(SquareMeterModuleDTO squareMeterModuleDTO) {
        if (squareMeterModuleDTO == null) {
            return null;
        }
        
        SquareMeterModule entity = new SquareMeterModule();
        entity.setId(squareMeterModuleDTO.getId());
        entity.setOrderId(squareMeterModuleDTO.getOrderId());
        entity.setClientName(squareMeterModuleDTO.getClientName());
        entity.setWorkOrder(squareMeterModuleDTO.getWorkOrder());
        entity.setServiceDescription(squareMeterModuleDTO.getServiceDescription());
        entity.setUom(squareMeterModuleDTO.getUom());
        entity.setDepartment(squareMeterModuleDTO.getDepartment());
        entity.setWorkLocation(squareMeterModuleDTO.getWorkLocation());
        entity.setDataModule(squareMeterModuleDTO.getDataModule());
        entity.setBuildingName(squareMeterModuleDTO.getBuildingName());
        entity.setDrawingNo(squareMeterModuleDTO.getDrawingNo());
        entity.setDrawingReceivedDate(squareMeterModuleDTO.getDrawingReceivedDate());
        entity.setTargetDate(squareMeterModuleDTO.getTargetDate());
        entity.setMarkNo(squareMeterModuleDTO.getMarkNo());
        entity.setMarkQty(squareMeterModuleDTO.getMarkQty());
        entity.setEachMarkLength(squareMeterModuleDTO.getEachMarkLength());
        entity.setTotalMarkLength(squareMeterModuleDTO.getTotalMarkLength());
        entity.setItemNo(squareMeterModuleDTO.getItemNo());
        entity.setSection(squareMeterModuleDTO.getSection());
        entity.setLengthMm(squareMeterModuleDTO.getLengthMm());
        entity.setWidthMm(squareMeterModuleDTO.getWidthMm());
        entity.setItemQty(squareMeterModuleDTO.getItemQty());
        entity.setTotalArea(squareMeterModuleDTO.getTotalArea());
        entity.setRemarks(squareMeterModuleDTO.getRemarks());
        entity.setTenantId(squareMeterModuleDTO.getTenantId());
        entity.setCreatedDate(squareMeterModuleDTO.getCreatedDate());
        entity.setUpdatedDate(squareMeterModuleDTO.getUpdatedDate());
        entity.setCreatedBy(squareMeterModuleDTO.getCreatedBy());
        entity.setUpdatedBy(squareMeterModuleDTO.getUpdatedBy());
        
        return entity;
    }

    @Override
    public List<SquareMeterModuleDTO> convertToDTOList(List<SquareMeterModule> squareMeterModules) {
        return squareMeterModules.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SquareMeterModule> convertToEntityList(List<SquareMeterModuleDTO> squareMeterModuleDTOs) {
        return squareMeterModuleDTOs.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }
}
