package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.PostInchMeterPreFabricatedModuleDTO;
import com.bellaryinfotech.model.PostInchMeterPreFabricatedModule;
import com.bellaryinfotech.repo.PostInchMeterPreFabricatedModuleRepository;
import com.bellaryinfotech.service.PostInchMeterPreFabricatedModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostInchMeterPreFabricatedModuleServiceImpl implements PostInchMeterPreFabricatedModuleService {
    
    @Autowired
    private PostInchMeterPreFabricatedModuleRepository repository;
    
    @Override
    public List<PostInchMeterPreFabricatedModule> savePostInchMeterPreFabricatedModule(PostInchMeterPreFabricatedModuleDTO dto) {
        List<PostInchMeterPreFabricatedModule> savedModules = new ArrayList<>();
        
        // Use top-level fields, but fall back to first entry's fields if empty
        String vehicleNumber = dto.getVehicleNumber() != null && !dto.getVehicleNumber().isEmpty() 
            ? dto.getVehicleNumber() 
            : (dto.getEntries() != null && !dto.getEntries().isEmpty() ? dto.getEntries().get(0).getVehicleNumber() : "");
        String loadNumber = dto.getLoadNumber() != null && !dto.getLoadNumber().isEmpty() 
            ? dto.getLoadNumber() 
            : (dto.getEntries() != null && !dto.getEntries().isEmpty() ? dto.getEntries().get(0).getLoadNumber() : "");
        String plotNumber = dto.getPlotNumber() != null && !dto.getPlotNumber().isEmpty() 
            ? dto.getPlotNumber() 
            : (dto.getEntries() != null && !dto.getEntries().isEmpty() ? dto.getEntries().get(0).getPlotNumber() : "");
        
        // Save each entry row
        for (PostInchMeterPreFabricatedModuleDTO.PostInchMeterPreFabricatedEntryRowDTO entryRow : dto.getEntries()) {
            PostInchMeterPreFabricatedModule entity = new PostInchMeterPreFabricatedModule();
            
            // Set work order information (same for all rows)
            entity.setWorkOrder(dto.getWorkOrder());
            entity.setOrderId(dto.getOrderId());
            entity.setClientName(dto.getClientName());
            entity.setServiceDescription(dto.getServiceDescription());
            entity.setUom(dto.getUom());
            entity.setDepartment(dto.getDepartment());
            entity.setWorkLocation(dto.getWorkLocation());
            entity.setVehicleNumber(vehicleNumber);
            entity.setLoadNumber(loadNumber);
            entity.setPlotNumber(plotNumber);
            entity.setRaNo(dto.getRaNo());
            
            // Set entry row specific data
            entity.setDrawingNo(entryRow.getDrawingNo());
            entity.setMarkNo(entryRow.getMarkNo());
            entity.setEachMarkLength(entryRow.getEachMarkLength());
            entity.setMarkQty(entryRow.getMarkQty());
            entity.setTotalMarkLength(entryRow.getTotalMarkLength());
            entity.setRemarks(entryRow.getRemarks());
            
            PostInchMeterPreFabricatedModule savedEntity = repository.save(entity);
            savedModules.add(savedEntity);
        }
        
        return savedModules;
    }
    
    @Override
    public List<PostInchMeterPreFabricatedModule> getPostInchMeterPreFabricatedByWorkOrderAndServiceDesc(String workOrder, String serviceDescription) {
        return repository.findByWorkOrderAndServiceDescription(workOrder, serviceDescription);
    }
    
    @Override
    public List<PostInchMeterPreFabricatedModule> getPostInchMeterPreFabricatedModulesByWorkOrder(String workOrder) {
        return repository.findByWorkOrder(workOrder);
    }
    
    @Override
    public List<PostInchMeterPreFabricatedModule> getPostInchMeterPreFabricatedModulesByWorkOrderAndRaNo(String workOrder, String raNo) {
        return repository.findByWorkOrderAndRaNo(workOrder, raNo);
    }
    
    @Override
    public List<PostInchMeterPreFabricatedModule> getPostInchMeterPreFabricatedModulesByRaNo(String raNo) {
        return repository.findByRaNo(raNo);
    }
    
    @Override
    public List<PostInchMeterPreFabricatedModule> getPostInchMeterPreFabricatedModulesByMarkNo(String markNo) {
        return repository.findByMarkNo(markNo);
    }
    
    @Override
    public List<String> getDistinctWorkOrders() {
        return repository.findDistinctWorkOrders();
    }
    
    @Override
    public List<String> getDistinctRaNosByWorkOrder(String workOrder) {
        return repository.findDistinctRaNosByWorkOrder(workOrder);
    }
    
    @Override
    public List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder) {
        return repository.findDistinctServiceDescriptionsByWorkOrder(workOrder);
    }
    
    @Override
    public List<String> getDistinctVehicleNumbersByWorkOrder(String workOrder) {
        return repository.findDistinctVehicleNumbersByWorkOrder(workOrder);
    }
    
    @Override
    public List<String> getDistinctLoadNumbersByWorkOrder(String workOrder) {
        return repository.findDistinctLoadNumbersByWorkOrder(workOrder);
    }
    
    @Override
    public List<String> getDistinctPlotNumbersByWorkOrder(String workOrder) {
        return repository.findDistinctPlotNumbersByWorkOrder(workOrder);
    }
    
    @Override
    public PostInchMeterPreFabricatedModule updatePostInchMeterPreFabricatedModule(Long id, PostInchMeterPreFabricatedModule updatedModule) {
        Optional<PostInchMeterPreFabricatedModule> existingModule = repository.findById(id);
        if (existingModule.isPresent()) {
            PostInchMeterPreFabricatedModule entity = existingModule.get();
            
            entity.setWorkOrder(updatedModule.getWorkOrder());
            entity.setOrderId(updatedModule.getOrderId());
            entity.setClientName(updatedModule.getClientName());
            entity.setServiceDescription(updatedModule.getServiceDescription());
            entity.setUom(updatedModule.getUom());
            entity.setDepartment(updatedModule.getDepartment());
            entity.setWorkLocation(updatedModule.getWorkLocation());
            entity.setVehicleNumber(updatedModule.getVehicleNumber());
            entity.setLoadNumber(updatedModule.getLoadNumber());
            entity.setPlotNumber(updatedModule.getPlotNumber());
            entity.setRaNo(updatedModule.getRaNo());
            entity.setDrawingNo(updatedModule.getDrawingNo());
            entity.setMarkNo(updatedModule.getMarkNo());
            entity.setEachMarkLength(updatedModule.getEachMarkLength());
            entity.setMarkQty(updatedModule.getMarkQty());
            entity.setTotalMarkLength(updatedModule.getTotalMarkLength());
            entity.setRemarks(updatedModule.getRemarks());
            
            return repository.save(entity);
        }
        throw new RuntimeException("PostInchMeterPreFabricatedModule not found with id: " + id);
    }
    
    @Override
    public void deletePostInchMeterPreFabricatedModule(Long id) {
        repository.deleteById(id);
    }
    
    @Override
    public void deletePostInchMeterPreFabricatedModulesByWorkOrder(String workOrder) {
        repository.deleteByWorkOrder(workOrder);
    }
    
    @Override
    public void deletePostInchMeterPreFabricatedModulesByWorkOrderAndRaNo(String workOrder, String raNo) {
        repository.deleteByWorkOrderAndRaNo(workOrder, raNo);
    }
    
    // NEW: Implementation for search by workOrder, serviceDescription, and optional raNo
    @Override
    public List<PostInchMeterPreFabricatedModule> searchPostInchMeterPreFabricatedModules(String workOrder, String serviceDescription, String raNo) {
        if (raNo != null && !raNo.isEmpty()) {
            return repository.findByWorkOrderAndServiceDescriptionAndRaNo(workOrder, serviceDescription, raNo);
        }
        return repository.findByWorkOrderAndServiceDescription(workOrder, serviceDescription);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public List<PostInchMeterPreFabricatedModule> getAllRecords() {
        return repository.findAll();
    }

    @Override
    public List<String> getDistinctClientNames() {
        return repository.findDistinctClientNames();
    }

    @Override
    public List<String> getDistinctWorkOrdersByClientName(String clientName) {
        return repository.findDistinctWorkOrdersByClientName(clientName);
    }

    @Override
    public List<String> getDistinctServiceDescriptionsByClientAndWorkOrder(String clientName, String workOrder) {
        return repository.findDistinctServiceDescriptionsByClientAndWorkOrder(clientName, workOrder);
    }

    @Override
    public List<String> getDistinctRaNumbersByAllCriteria(String clientName, String workOrder, String serviceDescription) {
        return repository.findDistinctRaNumbersByAllCriteria(clientName, workOrder, serviceDescription);
    }

    @Override
    public List<PostInchMeterPreFabricatedModule> searchByAllCriteria(String clientName, String workOrder, String serviceDescription, String raNumber) {
        return repository.findByAllCriteria(clientName, workOrder, serviceDescription, raNumber);
    }
}