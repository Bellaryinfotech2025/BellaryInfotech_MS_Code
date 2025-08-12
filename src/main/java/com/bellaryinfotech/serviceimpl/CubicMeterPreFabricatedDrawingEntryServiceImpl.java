package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.CubicMeterPreFabricatedDrawingEntryDTO;
import com.bellaryinfotech.model.CubicMeterPreFabricatedDrawingEntry;
import com.bellaryinfotech.repo.CubicMeterPreFabricatedDrawingEntryRepository;
import com.bellaryinfotech.service.CubicMeterPreFabricatedDrawingEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CubicMeterPreFabricatedDrawingEntryServiceImpl implements CubicMeterPreFabricatedDrawingEntryService {
    
    @Autowired
    private CubicMeterPreFabricatedDrawingEntryRepository repository;
    
    @Override
    public List<CubicMeterPreFabricatedDrawingEntry> saveCubicMeterPreFabricatedEntries(CubicMeterPreFabricatedDrawingEntryDTO dto) {
        List<CubicMeterPreFabricatedDrawingEntry> savedEntries = new ArrayList<>();
        
        for (CubicMeterPreFabricatedDrawingEntryDTO.CubicMeterEntryRowDTO entryRow : dto.getEntries()) {
            CubicMeterPreFabricatedDrawingEntry entry = new CubicMeterPreFabricatedDrawingEntry();
            
            // Set header information for each row
            entry.setWorkOrder(dto.getWorkOrder());
            entry.setClientName(dto.getClientName());
            entry.setServiceDescription(dto.getServiceDescription());
            entry.setUom(dto.getUom());
            entry.setDepartment(dto.getDepartment());
            entry.setWorkLocation(dto.getWorkLocation());
            entry.setVehicleNumber(dto.getVehicleNumber());
            entry.setLoadNumber(dto.getLoadNumber());
            entry.setPlotNumber(dto.getPlotNumber());
            
            // Set row-specific information
            entry.setDrawingNo(entryRow.getDrawingNo());
            entry.setMarkNo(entryRow.getMarkNo());
            entry.setEachMarkLength(entryRow.getEachMarkLength());
            entry.setMarkQty(entryRow.getMarkQty());
            entry.setTotalMarkLength(entryRow.getTotalMarkLength());
            entry.setRemarks(entryRow.getRemarks());
            
            savedEntries.add(repository.save(entry));
        }
        
        return savedEntries;
    }
    
    @Override
    public List<CubicMeterPreFabricatedDrawingEntry> getCubicMeterPreFabricatedEntriesByWorkOrder(String workOrder) {
        return repository.findByWorkOrder(workOrder);
    }
    
    @Override
    public List<CubicMeterPreFabricatedDrawingEntry> getCubicMeterPreFabricatedEntriesByMarkNo(String markNo) {
        return repository.findByMarkNo(markNo);
    }
    
    @Override
    public List<CubicMeterPreFabricatedDrawingEntry> getCubicMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber(String workOrder, String vehicleNumber) {
        return repository.findByWorkOrderAndVehicleNumber(workOrder, vehicleNumber);
    }
    
    @Override
    public List<CubicMeterPreFabricatedDrawingEntry> getCubicMeterPreFabricatedEntriesByWorkOrderAndLoadNumber(String workOrder, String loadNumber) {
        return repository.findByWorkOrderAndLoadNumber(workOrder, loadNumber);
    }
    
    @Override
    public List<CubicMeterPreFabricatedDrawingEntry> getAllCubicMeterPreFabricatedEntries() {
        return repository.findAll();
    }
    
    @Override
    public CubicMeterPreFabricatedDrawingEntry updateCubicMeterPreFabricatedEntry(Long id, CubicMeterPreFabricatedDrawingEntry entry) {
        Optional<CubicMeterPreFabricatedDrawingEntry> existingEntry = repository.findById(id);
        if (existingEntry.isPresent()) {
            CubicMeterPreFabricatedDrawingEntry existing = existingEntry.get();
            
            existing.setWorkOrder(entry.getWorkOrder());
            existing.setClientName(entry.getClientName());
            existing.setServiceDescription(entry.getServiceDescription());
            existing.setUom(entry.getUom());
            existing.setDepartment(entry.getDepartment());
            existing.setWorkLocation(entry.getWorkLocation());
            existing.setVehicleNumber(entry.getVehicleNumber());
            existing.setLoadNumber(entry.getLoadNumber());
            existing.setPlotNumber(entry.getPlotNumber());
            existing.setDrawingNo(entry.getDrawingNo());
            existing.setMarkNo(entry.getMarkNo());
            existing.setEachMarkLength(entry.getEachMarkLength());
            existing.setMarkQty(entry.getMarkQty());
            existing.setTotalMarkLength(entry.getTotalMarkLength());
            existing.setRemarks(entry.getRemarks());
            
            return repository.save(existing);
        }
        throw new RuntimeException("Cubic Meter Pre-Fabricated Drawing Entry not found with id: " + id);
    }
    
    @Override
    public void deleteCubicMeterPreFabricatedEntry(Long id) {
        repository.deleteById(id);
    }
    
    @Override
    public void deleteCubicMeterPreFabricatedEntriesByWorkOrder(String workOrder) {
        repository.deleteByWorkOrder(workOrder);
    }
    
    @Override
    public void deleteCubicMeterPreFabricatedEntriesByMarkNo(String markNo) {
        repository.deleteByMarkNo(markNo);
    }
    
    @Override
    public List<String> getDistinctWorkOrders() {
        return repository.findDistinctWorkOrders();
    }

    @Override
    public List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder) {
        return repository.findDistinctServiceDescriptionsByWorkOrder(workOrder);
    }
}
