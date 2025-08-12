package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.SquareMeterPreFabricatedDrawingEntryDTO;
import com.bellaryinfotech.model.SquareMeterPreFabricatedDrawingEntry;
import com.bellaryinfotech.repo.SquareMeterPreFabricatedDrawingEntryRepository;
import com.bellaryinfotech.service.SquareMeterPreFabricatedDrawingEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SquareMeterPreFabricatedDrawingEntryServiceImpl implements SquareMeterPreFabricatedDrawingEntryService {
    
    @Autowired
    private SquareMeterPreFabricatedDrawingEntryRepository repository;
    
    @Override
    public List<SquareMeterPreFabricatedDrawingEntry> saveSquareMeterPreFabricatedEntries(SquareMeterPreFabricatedDrawingEntryDTO dto) {
        List<SquareMeterPreFabricatedDrawingEntry> savedEntries = new ArrayList<>();
        
        for (SquareMeterPreFabricatedDrawingEntryDTO.SquareMeterEntryRowDTO entryRow : dto.getEntries()) {
            SquareMeterPreFabricatedDrawingEntry entry = new SquareMeterPreFabricatedDrawingEntry();
            
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
    public List<SquareMeterPreFabricatedDrawingEntry> getSquareMeterPreFabricatedEntriesByWorkOrder(String workOrder) {
        return repository.findByWorkOrder(workOrder);
    }
    
    @Override
    public List<SquareMeterPreFabricatedDrawingEntry> getSquareMeterPreFabricatedEntriesByMarkNo(String markNo) {
        return repository.findByMarkNo(markNo);
    }
    
    @Override
    public List<SquareMeterPreFabricatedDrawingEntry> getSquareMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber(String workOrder, String vehicleNumber) {
        return repository.findByWorkOrderAndVehicleNumber(workOrder, vehicleNumber);
    }
    
    @Override
    public List<SquareMeterPreFabricatedDrawingEntry> getSquareMeterPreFabricatedEntriesByWorkOrderAndLoadNumber(String workOrder, String loadNumber) {
        return repository.findByWorkOrderAndLoadNumber(workOrder, loadNumber);
    }
    
    @Override
    public List<SquareMeterPreFabricatedDrawingEntry> getAllSquareMeterPreFabricatedEntries() {
        return repository.findAll();
    }
    
    @Override
    public SquareMeterPreFabricatedDrawingEntry updateSquareMeterPreFabricatedEntry(Long id, SquareMeterPreFabricatedDrawingEntry entry) {
        Optional<SquareMeterPreFabricatedDrawingEntry> existingEntry = repository.findById(id);
        if (existingEntry.isPresent()) {
            SquareMeterPreFabricatedDrawingEntry existing = existingEntry.get();
            
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
        throw new RuntimeException("Square Meter Pre-Fabricated Drawing Entry not found with id: " + id);
    }
    
    @Override
    public void deleteSquareMeterPreFabricatedEntry(Long id) {
        repository.deleteById(id);
    }
    
    @Override
    public void deleteSquareMeterPreFabricatedEntriesByWorkOrder(String workOrder) {
        repository.deleteByWorkOrder(workOrder);
    }
    
    @Override
    public void deleteSquareMeterPreFabricatedEntriesByMarkNo(String markNo) {
        repository.deleteByMarkNo(markNo);
    }
    
    @Override
    public List<String> getDistinctWorkOrders() {
        return repository.findDistinctWorkOrders();
    }
}
