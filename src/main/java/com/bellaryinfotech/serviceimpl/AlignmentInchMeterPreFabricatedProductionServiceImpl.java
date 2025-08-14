package com.bellaryinfotech.serviceimpl;
 

import com.bellaryinfotech.DTO.AlignmentInchMeterPreFabricatedProductionDTO;
import com.bellaryinfotech.model.AlignmentInchMeterPreFabricatedProduction;
import com.bellaryinfotech.repo.AlignmentInchMeterPreFabricatedProductionRepository;
import com.bellaryinfotech.service.AlignmentInchMeterPreFabricatedProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlignmentInchMeterPreFabricatedProductionServiceImpl
        implements AlignmentInchMeterPreFabricatedProductionService {

    private AlignmentInchMeterPreFabricatedProductionRepository repository;

    @Autowired
    public void setRepository(AlignmentInchMeterPreFabricatedProductionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AlignmentInchMeterPreFabricatedProduction> getByWorkOrder(String workOrder) {
        return repository.findByWorkOrder(workOrder);
    }

    @Override
    public List<AlignmentInchMeterPreFabricatedProduction> getByRaNo(String raNo) {
        return repository.findByRaNo(raNo);
    }

    @Override
    public List<AlignmentInchMeterPreFabricatedProduction> getByVehicleNumber(String vehicleNumber) {
        return repository.findByVehicleNumber(vehicleNumber);
    }

    @Override
    public List<String> getDistinctWorkOrders() {
        return repository.findDistinctWorkOrders();
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
    public List<String> getDistinctRaNosByWorkOrder(String workOrder) {
        return repository.findDistinctRaNosByWorkOrder(workOrder);
    }

    @Override
    public List<AlignmentInchMeterPreFabricatedProduction> save(AlignmentInchMeterPreFabricatedProductionDTO dto) {
        List<AlignmentInchMeterPreFabricatedProduction> savedEntities = new ArrayList<>();
        for (AlignmentInchMeterPreFabricatedProductionDTO.EntryDTO entry : dto.getEntries()) {
            AlignmentInchMeterPreFabricatedProduction entity = new AlignmentInchMeterPreFabricatedProduction();
            entity.setWorkOrder(dto.getWorkOrder());
            entity.setOrderId(dto.getOrderId());
            entity.setClientName(dto.getClientName());
            entity.setServiceDescription(dto.getServiceDescription());
            entity.setUom(dto.getUom());
            entity.setDataModule(dto.getDataModule());
            entity.setDepartment(dto.getDepartment());
            entity.setWorkLocation(dto.getWorkLocation());
            entity.setVehicleNumber(dto.getVehicleNumber() != null ? dto.getVehicleNumber() : entry.getVehicleNumber());
            entity.setLoadNumber(dto.getLoadNumber() != null ? dto.getLoadNumber() : entry.getLoadNumber());
            entity.setPlotNumber(dto.getPlotNumber() != null ? dto.getPlotNumber() : entry.getPlotNumber());
            entity.setRaNo(dto.getRaNo());
            entity.setDrawingNo(entry.getDrawingNo());
            entity.setMarkNo(entry.getMarkNo());
            entity.setEachMarkLength(entry.getEachMarkLength());
            entity.setMarkQty(entry.getMarkQty());
            entity.setTotalMarkLength(entry.getTotalMarkLength());
            entity.setRemarks(entry.getRemarks());
            entity.setCreatedDate(LocalDateTime.now());
            entity.setUpdatedDate(LocalDateTime.now());
            savedEntities.add(repository.save(entity));
        }
        return savedEntities;
    }

    @Override
    public AlignmentInchMeterPreFabricatedProduction update(Long id, AlignmentInchMeterPreFabricatedProductionDTO.EntryDTO entryDTO) {
        Optional<AlignmentInchMeterPreFabricatedProduction> optional = repository.findById(id);
        if (optional.isPresent()) {
            AlignmentInchMeterPreFabricatedProduction entity = optional.get();
            entity.setDrawingNo(entryDTO.getDrawingNo());
            entity.setMarkNo(entryDTO.getMarkNo());
            entity.setEachMarkLength(entryDTO.getEachMarkLength());
            entity.setMarkQty(entryDTO.getMarkQty());
            entity.setTotalMarkLength(entryDTO.getTotalMarkLength());
            entity.setRemarks(entryDTO.getRemarks());
            entity.setVehicleNumber(entryDTO.getVehicleNumber());
            entity.setLoadNumber(entryDTO.getLoadNumber());
            entity.setPlotNumber(entryDTO.getPlotNumber());
            entity.setUpdatedDate(LocalDateTime.now());
            return repository.save(entity);
        }
        throw new RuntimeException("Entry not found with id: " + id);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}