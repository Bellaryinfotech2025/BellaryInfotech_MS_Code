package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.InchMeterPreFabricatedDrawingEntryDTO;
import com.bellaryinfotech.model.InchMeterPreFabricatedDrawingEntry;
import com.bellaryinfotech.repo.InchMeterPreFabricatedDrawingEntryRepository;
import com.bellaryinfotech.service.InchMeterPreFabricatedDrawingEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InchMeterPreFabricatedDrawingEntryServiceImpl implements InchMeterPreFabricatedDrawingEntryService {
    
    @Autowired
    private InchMeterPreFabricatedDrawingEntryRepository repository;
    
    @Override
    public List<InchMeterPreFabricatedDrawingEntry> saveInchMeterPreFabricatedEntries(InchMeterPreFabricatedDrawingEntryDTO dto) {
        List<InchMeterPreFabricatedDrawingEntry> savedEntries = new ArrayList<>();
        
        // Save each entry row
        for (InchMeterPreFabricatedDrawingEntryDTO.InchMeterPreFabricatedEntryRowDTO entryRow : dto.getEntries()) {
            InchMeterPreFabricatedDrawingEntry entity = new InchMeterPreFabricatedDrawingEntry();
            
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
            entity.setEachMarkLength(entryRow.getEachMarkLength());
            entity.setMarkQty(entryRow.getMarkQty());
            entity.setTotalMarkLength(entryRow.getTotalMarkLength());
            entity.setRemarks(entryRow.getRemarks());
            
            InchMeterPreFabricatedDrawingEntry savedEntity = repository.save(entity);
            savedEntries.add(savedEntity);
        }
        
        return savedEntries;
    }
    
    @Override
    public List<InchMeterPreFabricatedDrawingEntry> getInchMeterPreFabricatedEntriesByWorkOrder(String workOrder) {
        return repository.findByWorkOrder(workOrder);
    }
    
    @Override
    public List<InchMeterPreFabricatedDrawingEntry> getInchMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber(String workOrder, String vehicleNumber) {
        return repository.findByWorkOrderAndVehicleNumber(workOrder, vehicleNumber);
    }
    
    @Override
    public List<InchMeterPreFabricatedDrawingEntry> getInchMeterPreFabricatedEntriesByWorkOrderAndLoadNumber(String workOrder, String loadNumber) {
        return repository.findByWorkOrderAndLoadNumber(workOrder, loadNumber);
    }
    
    @Override
    public List<InchMeterPreFabricatedDrawingEntry> getInchMeterPreFabricatedEntriesByWorkOrderAndPlotNumber(String workOrder, String plotNumber) {
        return repository.findByWorkOrderAndPlotNumber(workOrder, plotNumber);
    }
    
    @Override
    public List<InchMeterPreFabricatedDrawingEntry> getInchMeterPreFabricatedEntriesByWorkOrderAndVehicleAndLoad(String workOrder, String vehicleNumber, String loadNumber) {
        return repository.findByWorkOrderAndVehicleNumberAndLoadNumber(workOrder, vehicleNumber, loadNumber);
    }
    
    @Override
    public List<InchMeterPreFabricatedDrawingEntry> getInchMeterPreFabricatedEntriesByWorkOrderAndVehicleAndPlot(String workOrder, String vehicleNumber, String plotNumber) {
        return repository.findByWorkOrderAndVehicleNumberAndPlotNumber(workOrder, vehicleNumber, plotNumber);
    }
    
    @Override
    public List<InchMeterPreFabricatedDrawingEntry> getInchMeterPreFabricatedEntriesByWorkOrderAndLoadAndPlot(String workOrder, String loadNumber, String plotNumber) {
        return repository.findByWorkOrderAndLoadNumberAndPlotNumber(workOrder, loadNumber, plotNumber);
    }
    
    @Override
    public List<InchMeterPreFabricatedDrawingEntry> getInchMeterPreFabricatedEntriesByWorkOrderAndVehicleAndLoadAndPlot(String workOrder, String vehicleNumber, String loadNumber, String plotNumber) {
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
    public List<InchMeterPreFabricatedDrawingEntry> getInchMeterPreFabricatedEntriesByMarkNo(String markNo) {
        return repository.findByMarkNo(markNo);
    }
    
    @Override
    public InchMeterPreFabricatedDrawingEntry updateInchMeterPreFabricatedEntry(Long id, InchMeterPreFabricatedDrawingEntry updatedEntry) {
        Optional<InchMeterPreFabricatedDrawingEntry> existingEntry = repository.findById(id);
        if (existingEntry.isPresent()) {
            InchMeterPreFabricatedDrawingEntry entity = existingEntry.get();
            
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
            entity.setEachMarkLength(updatedEntry.getEachMarkLength());
            entity.setMarkQty(updatedEntry.getMarkQty());
            entity.setTotalMarkLength(updatedEntry.getTotalMarkLength());
            entity.setRemarks(updatedEntry.getRemarks());
            
            return repository.save(entity);
        }
        throw new RuntimeException("InchMeterPreFabricatedDrawingEntry not found with id: " + id);
    }
    
    @Override
    public void deleteInchMeterPreFabricatedEntry(Long id) {
        repository.deleteById(id);
    }
    
    @Override
    public void deleteInchMeterPreFabricatedEntriesByWorkOrder(String workOrder) {
        repository.deleteByWorkOrder(workOrder);
    }
    
    @Override
    public void deleteInchMeterPreFabricatedEntriesByWorkOrderAndVehicleAndLoadAndPlot(String workOrder, String vehicleNumber, String loadNumber, String plotNumber) {
        repository.deleteByWorkOrderAndVehicleNumberAndLoadNumberAndPlotNumber(workOrder, vehicleNumber, loadNumber, plotNumber);
    }
}
