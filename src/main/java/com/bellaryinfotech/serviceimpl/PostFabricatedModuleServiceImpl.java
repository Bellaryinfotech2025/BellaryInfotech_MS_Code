package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.PostFabricatedModuleDTO;
import com.bellaryinfotech.model.PostFabricatedModule;
import com.bellaryinfotech.repo.PostFabricatedModuleRepository;
import com.bellaryinfotech.service.PostFabricatedModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostFabricatedModuleServiceImpl implements PostFabricatedModuleService {
    
    @Autowired
    private PostFabricatedModuleRepository repository;
    
    @Override
    public List<PostFabricatedModule> savePostFabricatedModule(PostFabricatedModuleDTO dto) {
        List<PostFabricatedModule> savedModules = new ArrayList<>();
        
        // Save each entry row
        for (PostFabricatedModuleDTO.PostFabricatedEntryRowDTO entryRow : dto.getEntries()) {
            PostFabricatedModule entity = new PostFabricatedModule();
            
            // Set work order information (same for all rows)
            entity.setWorkOrder(dto.getWorkOrder());
            entity.setClientName(dto.getClientName());
            entity.setServiceDescription(dto.getServiceDescription());
            entity.setUom(dto.getUom());
            entity.setDataModule(dto.getDataModule());
            entity.setBuildingName(dto.getBuildingName());
            entity.setRaNumber(dto.getRaNumber());
            
            // Set entry row specific data
            entity.setVehicleNumber(entryRow.getVehicleNumber());
            entity.setLoadNumber(entryRow.getLoadNumber());
            entity.setPlotNumber(entryRow.getPlotNumber());
            entity.setDrawingNo(entryRow.getDrawingNo());
            entity.setMarkNo(entryRow.getMarkNo());
            entity.setMarkWeight(entryRow.getMarkWeight());
            entity.setMarkQty(entryRow.getMarkQty());
            entity.setTotalMarkWeight(entryRow.getTotalMarkWeight());
            entity.setRemarks(entryRow.getRemarks());
            
            PostFabricatedModule savedEntity = repository.save(entity);
            savedModules.add(savedEntity);
        }
        
        return savedModules;
    }
    
    @Override
    public List<PostFabricatedModule> getPostFabricatedModulesByWorkOrder(String workOrder) {
        return repository.findByWorkOrder(workOrder);
    }
    
    @Override
    public List<PostFabricatedModule> getPostFabricatedModulesByRaNumber(String raNumber) {
        return repository.findByRaNumber(raNumber);
    }
    
    @Override
    public List<PostFabricatedModule> getPostFabricatedModulesByWorkOrderAndRaNumber(String workOrder, String raNumber) {
        return repository.findByWorkOrderAndRaNumberDetails(workOrder, raNumber);
    }
    
    @Override
    public List<String> getDistinctWorkOrders() {
        return repository.findDistinctWorkOrders();
    }
    
    @Override
    public List<String> getDistinctRaNumbers() {
        return repository.findDistinctRaNumbers();
    }
    
    @Override
    public List<String> getDistinctRaNumbersByWorkOrder(String workOrder) {
        return repository.findDistinctRaNumbersByWorkOrder(workOrder);
    }
    
    @Override
    public PostFabricatedModule updatePostFabricatedModule(Long id, PostFabricatedModule updatedModule) {
        Optional<PostFabricatedModule> existingModule = repository.findById(id);
        if (existingModule.isPresent()) {
            PostFabricatedModule entity = existingModule.get();
            
            entity.setWorkOrder(updatedModule.getWorkOrder());
            entity.setClientName(updatedModule.getClientName());
            entity.setServiceDescription(updatedModule.getServiceDescription());
            entity.setUom(updatedModule.getUom());
            entity.setDataModule(updatedModule.getDataModule());
            entity.setBuildingName(updatedModule.getBuildingName());
            entity.setRaNumber(updatedModule.getRaNumber());
            entity.setVehicleNumber(updatedModule.getVehicleNumber());
            entity.setLoadNumber(updatedModule.getLoadNumber());
            entity.setPlotNumber(updatedModule.getPlotNumber());
            entity.setDrawingNo(updatedModule.getDrawingNo());
            entity.setMarkNo(updatedModule.getMarkNo());
            entity.setMarkWeight(updatedModule.getMarkWeight());
            entity.setMarkQty(updatedModule.getMarkQty());
            entity.setTotalMarkWeight(updatedModule.getTotalMarkWeight());
            entity.setRemarks(updatedModule.getRemarks());
            
            return repository.save(entity);
        }
        throw new RuntimeException("PostFabricatedModule not found with id: " + id);
    }
    
    @Override
    public void deletePostFabricatedModule(Long id) {
        repository.deleteById(id);
    }
    
    @Override
    public void deletePostFabricatedModulesByWorkOrderAndRaNumber(String workOrder, String raNumber) {
        repository.deleteByWorkOrderAndRaNumber(workOrder, raNumber);
    }
}
