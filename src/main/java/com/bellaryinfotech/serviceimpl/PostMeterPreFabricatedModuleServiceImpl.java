package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.PostMeterPreFabricatedModuleDTO;
import com.bellaryinfotech.model.PostMeterPreFabricatedModule;
import com.bellaryinfotech.repo.PostMeterPreFabricatedModuleRepository;
import com.bellaryinfotech.service.PostMeterPreFabricatedModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostMeterPreFabricatedModuleServiceImpl implements PostMeterPreFabricatedModuleService {
    
    @Autowired
    private PostMeterPreFabricatedModuleRepository repository;
    
    @Override
    public List<PostMeterPreFabricatedModule> savePostMeterPreFabricatedModule(PostMeterPreFabricatedModuleDTO dto) {
        List<PostMeterPreFabricatedModule> savedModules = new ArrayList<>();
        
        // Save each entry row
        for (PostMeterPreFabricatedModuleDTO.PostMeterPreFabricatedEntryRowDTO entryRow : dto.getEntries()) {
            PostMeterPreFabricatedModule entity = new PostMeterPreFabricatedModule();
            
            // Set work order information (same for all rows)
            entity.setWorkOrder(dto.getWorkOrder());
            entity.setOrderId(dto.getOrderId());
            entity.setClientName(dto.getClientName());
            entity.setServiceDescription(dto.getServiceDescription());
            entity.setUom(dto.getUom());
            entity.setDepartment(dto.getDepartment());
            entity.setWorkLocation(dto.getWorkLocation());
            entity.setVehicleNumber(dto.getVehicleNumber());
            entity.setLoadNumber(dto.getLoadNumber());
            entity.setPlotNumber(dto.getPlotNumber());
            entity.setRaNo(dto.getRaNo());
            
            // Set entry row specific data
            entity.setDrawingNo(entryRow.getDrawingNo());
            entity.setMarkNo(entryRow.getMarkNo());
            entity.setEachMarkLength(entryRow.getEachMarkLength());
            entity.setMarkQty(entryRow.getMarkQty());
            entity.setTotalMarkLength(entryRow.getTotalMarkLength());
            entity.setRemarks(entryRow.getRemarks());
            
            PostMeterPreFabricatedModule savedEntity = repository.save(entity);
            savedModules.add(savedEntity);
        }
        
        return savedModules;
    }
    
    @Override
    public List<PostMeterPreFabricatedModule> getPostMeterPreFabricatedModulesByWorkOrder(String workOrder) {
        return repository.findByWorkOrder(workOrder);
    }
    
    @Override
    public List<PostMeterPreFabricatedModule> getPostMeterPreFabricatedModulesByWorkOrderAndRaNo(String workOrder, String raNo) {
        return repository.findByWorkOrderAndRaNo(workOrder, raNo);
    }
    
    @Override
    public List<PostMeterPreFabricatedModule> getPostMeterPreFabricatedModulesByRaNo(String raNo) {
        return repository.findByRaNo(raNo);
    }
    
    @Override
    public List<PostMeterPreFabricatedModule> getPostMeterPreFabricatedModulesByMarkNo(String markNo) {
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
    public PostMeterPreFabricatedModule updatePostMeterPreFabricatedModule(Long id, PostMeterPreFabricatedModule updatedModule) {
        Optional<PostMeterPreFabricatedModule> existingModule = repository.findById(id);
        if (existingModule.isPresent()) {
            PostMeterPreFabricatedModule entity = existingModule.get();
            
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
        throw new RuntimeException("PostMeterPreFabricatedModule not found with id: " + id);
    }
    
    @Override
    public void deletePostMeterPreFabricatedModule(Long id) {
        repository.deleteById(id);
    }
    
    @Override
    public void deletePostMeterPreFabricatedModulesByWorkOrder(String workOrder) {
        repository.deleteByWorkOrder(workOrder);
    }
    
    @Override
    public void deletePostMeterPreFabricatedModulesByWorkOrderAndRaNo(String workOrder, String raNo) {
        repository.deleteByWorkOrderAndRaNo(workOrder, raNo);
    }
}
