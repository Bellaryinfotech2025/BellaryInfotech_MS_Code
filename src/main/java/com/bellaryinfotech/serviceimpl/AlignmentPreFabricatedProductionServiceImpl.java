package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.AlignmentPreFabricatedProductionDTO;
import com.bellaryinfotech.model.AlignmentPreFabricatedProduction;
import com.bellaryinfotech.repo.AlignmentPreFabricatedProductionRepository;
import com.bellaryinfotech.service.AlignmentPreFabricatedProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AlignmentPreFabricatedProductionServiceImpl implements AlignmentPreFabricatedProductionService {

    @Autowired
    private AlignmentPreFabricatedProductionRepository repository;

    @Override
    public List<AlignmentPreFabricatedProduction> saveAlignmentPreFabricatedProduction(AlignmentPreFabricatedProductionDTO dto) {
        List<AlignmentPreFabricatedProduction> savedProductions = new ArrayList<>();

        for (AlignmentPreFabricatedProductionDTO.Entry entryRow : dto.getEntries()) {
            AlignmentPreFabricatedProduction entity = new AlignmentPreFabricatedProduction();
            entity.setWorkOrder(dto.getWorkOrder());
            entity.setClientName(dto.getClientName());
            entity.setServiceDescription(dto.getServiceDescription());
            entity.setUom(dto.getUom());
            entity.setDataModule(dto.getDataModule());
            entity.setRaNo(dto.getRaNo());
            entity.setDrawingNo(entryRow.getDrawingNo());
            entity.setMarkNo(entryRow.getMarkNo());
            entity.setEachMarkLength(entryRow.getEachMarkLength());
            entity.setMarkQty(entryRow.getMarkQty());
            entity.setTotalMarkLength(entryRow.getTotalMarkLength());
            entity.setRemarks(entryRow.getRemarks());
            entity.setVehicleNumber(entryRow.getVehicleNumber());
            entity.setLoadNumber(entryRow.getLoadNumber());
            entity.setPlotNumber(entryRow.getPlotNumber());
            entity.setRaNo(entryRow.getRaNo() != null ? entryRow.getRaNo() : dto.getRaNo());

            savedProductions.add(repository.save(entity));
        }

        return savedProductions;
    }

    @Override
    public List<AlignmentPreFabricatedProduction> getAlignmentPreFabricatedProductionsByWorkOrder(String workOrder) {
        return repository.findByWorkOrder(workOrder);
    }

    @Override
    public List<AlignmentPreFabricatedProduction> getAlignmentPreFabricatedProductionsByRaNo(String raNo) {
        return repository.findByRaNo(raNo);
    }

    @Override
    public String getLatestRaNoByWorkOrderAndServiceDescription(String workOrder, String serviceDescription) {
        return repository.findLatestRaNoByWorkOrderAndServiceDescription(workOrder, serviceDescription);
    }

    @Override
    public AlignmentPreFabricatedProduction updateAlignmentPreFabricatedProduction(Long id, AlignmentPreFabricatedProduction updatedProduction) {
        Optional<AlignmentPreFabricatedProduction> existing = repository.findById(id);
        if (existing.isPresent()) {
            AlignmentPreFabricatedProduction entity = existing.get();
            entity.setWorkOrder(updatedProduction.getWorkOrder());
            entity.setClientName(updatedProduction.getClientName());
            entity.setServiceDescription(updatedProduction.getServiceDescription());
            entity.setUom(updatedProduction.getUom());
            entity.setDataModule(updatedProduction.getDataModule());
            entity.setDrawingNo(updatedProduction.getDrawingNo());
            entity.setMarkNo(updatedProduction.getMarkNo());
            entity.setEachMarkLength(updatedProduction.getEachMarkLength());
            entity.setMarkQty(updatedProduction.getMarkQty());
            entity.setTotalMarkLength(updatedProduction.getTotalMarkLength());
            entity.setRemarks(updatedProduction.getRemarks());
            entity.setVehicleNumber(updatedProduction.getVehicleNumber());
            entity.setLoadNumber(updatedProduction.getLoadNumber());
            entity.setPlotNumber(updatedProduction.getPlotNumber());
            entity.setRaNo(updatedProduction.getRaNo());
            return repository.save(entity);
        } else {
            throw new RuntimeException("Production entry not found with id: " + id);
        }
    }

    @Override
    public void deleteAlignmentPreFabricatedProduction(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Production entry not found with id: " + id);
        }
    }
}