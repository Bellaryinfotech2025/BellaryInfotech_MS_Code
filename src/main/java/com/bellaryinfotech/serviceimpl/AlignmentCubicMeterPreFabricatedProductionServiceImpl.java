package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.AlignmentCubicMeterPreFabricatedProductionDTO;
import com.bellaryinfotech.model.AlignmentCubicMeterPreFabricatedProduction;
import com.bellaryinfotech.repo.AlignmentCubicMeterPreFabricatedProductionRepository;
import com.bellaryinfotech.service.AlignmentCubicMeterPreFabricatedProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AlignmentCubicMeterPreFabricatedProductionServiceImpl implements AlignmentCubicMeterPreFabricatedProductionService {

    @Autowired
    private AlignmentCubicMeterPreFabricatedProductionRepository repository;

    @Override
    public List<AlignmentCubicMeterPreFabricatedProduction> saveAlignmentCubicMeterPreFabricatedProduction(AlignmentCubicMeterPreFabricatedProductionDTO dto) {
        List<AlignmentCubicMeterPreFabricatedProduction> savedModules = new ArrayList<>();

        String vehicleNumber = dto.getVehicleNumber() != null && !dto.getVehicleNumber().isEmpty() 
            ? dto.getVehicleNumber() 
            : (dto.getEntries() != null && !dto.getEntries().isEmpty() ? dto.getEntries().get(0).getVehicleNumber() : "");
        String loadNumber = dto.getLoadNumber() != null && !dto.getLoadNumber().isEmpty() 
            ? dto.getLoadNumber() 
            : (dto.getEntries() != null && !dto.getEntries().isEmpty() ? dto.getEntries().get(0).getLoadNumber() : "");
        String plotNumber = dto.getPlotNumber() != null && !dto.getPlotNumber().isEmpty() 
            ? dto.getPlotNumber() 
            : (dto.getEntries() != null && !dto.getEntries().isEmpty() ? dto.getEntries().get(0).getPlotNumber() : "");

        for (AlignmentCubicMeterPreFabricatedProductionDTO.EntryRowDTO entryRow : dto.getEntries()) {
            AlignmentCubicMeterPreFabricatedProduction entity = new AlignmentCubicMeterPreFabricatedProduction();

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

            entity.setDrawingNo(entryRow.getDrawingNo());
            entity.setMarkNo(entryRow.getMarkNo());
            entity.setEachMarkLength(entryRow.getEachMarkLength());
            entity.setMarkQty(entryRow.getMarkQty());
            entity.setTotalMarkLength(entryRow.getTotalMarkLength());
            entity.setRemarks(entryRow.getRemarks());

            AlignmentCubicMeterPreFabricatedProduction savedEntity = repository.save(entity);
            savedModules.add(savedEntity);
        }

        return savedModules;
    }

    @Override
    public List<AlignmentCubicMeterPreFabricatedProduction> getAlignmentCubicMeterPreFabricatedProductionByWorkOrder(String workOrder) {
        return repository.findByWorkOrder(workOrder);
    }

    @Override
    public List<AlignmentCubicMeterPreFabricatedProduction> getAlignmentCubicMeterPreFabricatedByWorkOrderAndServiceDesc(String workOrder, String serviceDescription) {
        return repository.findByWorkOrderAndServiceDescription(workOrder, serviceDescription);
    }

    @Override
    public List<AlignmentCubicMeterPreFabricatedProduction> getAlignmentCubicMeterPreFabricatedProductionByWorkOrderAndRaNo(String workOrder, String raNo) {
        return repository.findByWorkOrderAndRaNo(workOrder, raNo);
    }

    @Override
    public List<AlignmentCubicMeterPreFabricatedProduction> getAlignmentCubicMeterPreFabricatedProductionByRaNo(String raNo) {
        return repository.findByRaNo(raNo);
    }

    @Override
    public List<AlignmentCubicMeterPreFabricatedProduction> getAlignmentCubicMeterPreFabricatedProductionByMarkNo(String markNo) {
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
    public AlignmentCubicMeterPreFabricatedProduction updateAlignmentCubicMeterPreFabricatedProduction(Long id, AlignmentCubicMeterPreFabricatedProduction updatedModule) {
        Optional<AlignmentCubicMeterPreFabricatedProduction> existingModule = repository.findById(id);
        if (existingModule.isPresent()) {
            AlignmentCubicMeterPreFabricatedProduction entity = existingModule.get();

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
        throw new RuntimeException("AlignmentCubicMeterPreFabricatedProduction not found with id: " + id);
    }

    @Override
    public void deleteAlignmentCubicMeterPreFabricatedProduction(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAlignmentCubicMeterPreFabricatedProductionByWorkOrder(String workOrder) {
        repository.deleteByWorkOrder(workOrder);
    }

    @Override
    public void deleteAlignmentCubicMeterPreFabricatedProductionByWorkOrderAndRaNo(String workOrder, String raNo) {
        repository.deleteByWorkOrderAndRaNo(workOrder, raNo);
    }

    @Override
    public List<AlignmentCubicMeterPreFabricatedProduction> searchAlignmentCubicMeterPreFabricatedProduction(String workOrder, String serviceDescription, String raNo) {
        if (raNo != null && !raNo.isEmpty()) {
            return repository.findByWorkOrderAndServiceDescriptionAndRaNo(workOrder, serviceDescription, raNo);
        }
        return repository.findByWorkOrderAndServiceDescription(workOrder, serviceDescription);
    }
}