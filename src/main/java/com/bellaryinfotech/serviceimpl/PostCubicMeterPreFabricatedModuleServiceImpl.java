package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.PostCubicMeterPreFabricatedModuleDTO;
import com.bellaryinfotech.model.PostCubicMeterPreFabricatedModule;
import com.bellaryinfotech.repo.PostCubicMeterPreFabricatedModuleRepository;
import com.bellaryinfotech.service.PostCubicMeterPreFabricatedModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostCubicMeterPreFabricatedModuleServiceImpl implements PostCubicMeterPreFabricatedModuleService {

    @Autowired
    private PostCubicMeterPreFabricatedModuleRepository repository;

    @Override
    public List<PostCubicMeterPreFabricatedModule> savePostCubicMeterPreFabricatedModule(PostCubicMeterPreFabricatedModuleDTO dto) {
        List<PostCubicMeterPreFabricatedModule> savedModules = new ArrayList<>();

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
        for (PostCubicMeterPreFabricatedModuleDTO.PostCubicMeterPreFabricatedEntryRowDTO entryRow : dto.getEntries()) {
            PostCubicMeterPreFabricatedModule entity = new PostCubicMeterPreFabricatedModule();

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

            PostCubicMeterPreFabricatedModule savedEntity = repository.save(entity);
            savedModules.add(savedEntity);
        }

        return savedModules;
    }

    @Override
    public List<PostCubicMeterPreFabricatedModule> getPostCubicMeterPreFabricatedByWorkOrderAndServiceDesc(String workOrder, String serviceDescription) {
        return repository.findByWorkOrderAndServiceDescription(workOrder, serviceDescription);
    }

    @Override
    public List<PostCubicMeterPreFabricatedModule> getPostCubicMeterPreFabricatedModulesByWorkOrder(String workOrder) {
        return repository.findByWorkOrder(workOrder);
    }

    @Override
    public List<PostCubicMeterPreFabricatedModule> getPostCubicMeterPreFabricatedModulesByWorkOrderAndRaNo(String workOrder, String raNo) {
        return repository.findByWorkOrderAndRaNo(workOrder, raNo);
    }

    @Override
    public List<PostCubicMeterPreFabricatedModule> getPostCubicMeterPreFabricatedModulesByRaNo(String raNo) {
        return repository.findByRaNo(raNo);
    }

    @Override
    public List<PostCubicMeterPreFabricatedModule> getPostCubicMeterPreFabricatedModulesByMarkNo(String markNo) {
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
    public PostCubicMeterPreFabricatedModule updatePostCubicMeterPreFabricatedModule(Long id, PostCubicMeterPreFabricatedModule updatedModule) {
        Optional<PostCubicMeterPreFabricatedModule> existingModule = repository.findById(id);
        if (existingModule.isPresent()) {
            PostCubicMeterPreFabricatedModule entity = existingModule.get();

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
        throw new RuntimeException("PostCubicMeterPreFabricatedModule not found with id: " + id);
    }

    @Override
    public void deletePostCubicMeterPreFabricatedModule(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deletePostCubicMeterPreFabricatedModulesByWorkOrder(String workOrder) {
        repository.deleteByWorkOrder(workOrder);
    }

    @Override
    public void deletePostCubicMeterPreFabricatedModulesByWorkOrderAndRaNo(String workOrder, String raNo) {
        repository.deleteByWorkOrderAndRaNo(workOrder, raNo);
    }

    @Override
    public List<PostCubicMeterPreFabricatedModule> searchPostCubicMeterPreFabricatedModules(String workOrder, String serviceDescription, String raNo) {
        if (raNo != null && !raNo.isEmpty()) {
            return repository.findByWorkOrderAndServiceDescriptionAndRaNo(workOrder, serviceDescription, raNo);
        }
        return repository.findByWorkOrderAndServiceDescription(workOrder, serviceDescription);
    }
}