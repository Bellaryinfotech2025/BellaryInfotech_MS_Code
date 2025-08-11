package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.PreFabricatedDrawingEntryDTO;
import com.bellaryinfotech.model.PreFabricatedDrawingEntry;
import com.bellaryinfotech.repo.PreFabricatedDrawingEntryRepository;
import com.bellaryinfotech.service.PreFabricatedDrawingEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PreFabricatedDrawingEntryServiceImpl implements PreFabricatedDrawingEntryService {
    
    @Autowired
    private PreFabricatedDrawingEntryRepository repository;
    
    @Override
    public List<PreFabricatedDrawingEntry> savePreFabricatedEntries(PreFabricatedDrawingEntryDTO dto) {
        List<PreFabricatedDrawingEntry> savedEntries = new ArrayList<>();
        
        // Save each entry row
        for (PreFabricatedDrawingEntryDTO.PreFabricatedEntryRowDTO entryRow : dto.getEntries()) {
            PreFabricatedDrawingEntry entity = new PreFabricatedDrawingEntry();
            
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
            
            // Set entry row specific data
            entity.setDrawingNo(entryRow.getDrawingNo());
            entity.setMarkNo(entryRow.getMarkNo());
            entity.setMarkWeight(entryRow.getMarkWeight());
            entity.setMarkQty(entryRow.getMarkQty());
            entity.setTotalMarkWeight(entryRow.getTotalMarkWeight());
            entity.setRemarks(entryRow.getRemarks());
            
            PreFabricatedDrawingEntry savedEntity = repository.save(entity);
            savedEntries.add(savedEntity);
        }
        
        return savedEntries;
    }
    
    @Override
    public List<PreFabricatedDrawingEntry> getPreFabricatedEntriesByWorkOrder(String workOrder) {
        return repository.findByWorkOrder(workOrder);
    }
    
    @Override
    public List<PreFabricatedDrawingEntry> getPreFabricatedEntriesByWorkOrderAndVehicleNumber(String workOrder, String vehicleNumber) {
        return repository.findByWorkOrderAndVehicleNumber(workOrder, vehicleNumber);
    }
    
    @Override
    public List<PreFabricatedDrawingEntry> getPreFabricatedEntriesByWorkOrderAndLoadNumber(String workOrder, String loadNumber) {
        return repository.findByWorkOrderAndLoadNumber(workOrder, loadNumber);
    }
    
    @Override
    public List<PreFabricatedDrawingEntry> getPreFabricatedEntriesByWorkOrderAndPlotNumber(String workOrder, String plotNumber) {
        return repository.findByWorkOrderAndPlotNumber(workOrder, plotNumber);
    }
    
    @Override
    public List<PreFabricatedDrawingEntry> getPreFabricatedEntriesByWorkOrderAndVehicleAndLoad(String workOrder, String vehicleNumber, String loadNumber) {
        return repository.findByWorkOrderAndVehicleNumberAndLoadNumber(workOrder, vehicleNumber, loadNumber);
    }
    
    @Override
    public List<PreFabricatedDrawingEntry> getPreFabricatedEntriesByWorkOrderAndVehicleAndPlot(String workOrder, String vehicleNumber, String plotNumber) {
        return repository.findByWorkOrderAndVehicleNumberAndPlotNumber(workOrder, vehicleNumber, plotNumber);
    }
    
    @Override
    public List<PreFabricatedDrawingEntry> getPreFabricatedEntriesByWorkOrderAndLoadAndPlot(String workOrder, String loadNumber, String plotNumber) {
        return repository.findByWorkOrderAndLoadNumberAndPlotNumber(workOrder, loadNumber, plotNumber);
    }
    
    @Override
    public List<PreFabricatedDrawingEntry> getPreFabricatedEntriesByWorkOrderAndVehicleAndLoadAndPlot(String workOrder, String vehicleNumber, String loadNumber, String plotNumber) {
        return repository.findByWorkOrderAndVehicleNumberAndLoadNumberAndPlotNumber(workOrder, vehicleNumber, loadNumber, plotNumber);
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
    public List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder) {
        return repository.findDistinctServiceDescriptionsByWorkOrder(workOrder);
    }
    
    @Override
    public List<String> getDistinctClientNamesByWorkOrder(String workOrder) {
        return repository.findDistinctClientNamesByWorkOrder(workOrder);
    }
    
    @Override
    public List<PreFabricatedDrawingEntry> getPreFabricatedEntriesByMarkNo(String markNo) {
        return repository.findByMarkNo(markNo);
    }
    
    @Override
    public PreFabricatedDrawingEntry updatePreFabricatedEntry(Long id, PreFabricatedDrawingEntry updatedEntry) {
        Optional<PreFabricatedDrawingEntry> existingEntry = repository.findById(id);
        if (existingEntry.isPresent()) {
            PreFabricatedDrawingEntry entity = existingEntry.get();
            
            entity.setWorkOrder(updatedEntry.getWorkOrder());
            entity.setOrderId(updatedEntry.getOrderId());
            entity.setClientName(updatedEntry.getClientName());
            entity.setServiceDescription(updatedEntry.getServiceDescription());
            entity.setUom(updatedEntry.getUom());
            entity.setDepartment(updatedEntry.getDepartment());
            entity.setWorkLocation(updatedEntry.getWorkLocation());
            entity.setVehicleNumber(updatedEntry.getVehicleNumber());
            entity.setLoadNumber(updatedEntry.getLoadNumber());
            entity.setPlotNumber(updatedEntry.getPlotNumber());
            entity.setDrawingNo(updatedEntry.getDrawingNo());
            entity.setMarkNo(updatedEntry.getMarkNo());
            entity.setMarkWeight(updatedEntry.getMarkWeight());
            entity.setMarkQty(updatedEntry.getMarkQty());
            entity.setTotalMarkWeight(updatedEntry.getTotalMarkWeight());
            entity.setRemarks(updatedEntry.getRemarks());
            
            return repository.save(entity);
        }
        throw new RuntimeException("PreFabricatedDrawingEntry not found with id: " + id);
    }
    
    @Override
    public void deletePreFabricatedEntry(Long id) {
        repository.deleteById(id);
    }
    
    @Override
    public void deletePreFabricatedEntriesByWorkOrder(String workOrder) {
        repository.deleteByWorkOrder(workOrder);
    }
    
    @Override
    public void deletePreFabricatedEntriesByWorkOrderAndVehicleAndLoadAndPlot(String workOrder, String vehicleNumber, String loadNumber, String plotNumber) {
        repository.deleteByWorkOrderAndVehicleNumberAndLoadNumberAndPlotNumber(workOrder, vehicleNumber, loadNumber, plotNumber);
    }
}
