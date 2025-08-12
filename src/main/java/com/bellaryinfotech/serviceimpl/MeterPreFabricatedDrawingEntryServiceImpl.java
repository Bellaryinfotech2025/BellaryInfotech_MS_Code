package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.MeterPreFabricatedDrawingEntryDTO;
import com.bellaryinfotech.model.MeterPreFabricatedDrawingEntry;
import com.bellaryinfotech.repo.MeterPreFabricatedDrawingEntryRepository;
import com.bellaryinfotech.service.MeterPreFabricatedDrawingEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MeterPreFabricatedDrawingEntryServiceImpl implements MeterPreFabricatedDrawingEntryService {
    
    @Autowired
    private MeterPreFabricatedDrawingEntryRepository repository;
    
    @Override
    public List<MeterPreFabricatedDrawingEntry> saveMeterPreFabricatedEntries(MeterPreFabricatedDrawingEntryDTO dto) {
        List<MeterPreFabricatedDrawingEntry> savedEntries = new ArrayList<>();
        
        // Save each entry row
        for (MeterPreFabricatedDrawingEntryDTO.MeterPreFabricatedEntryRowDTO entryRow : dto.getEntries()) {
            MeterPreFabricatedDrawingEntry entity = new MeterPreFabricatedDrawingEntry();
            
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
            
            MeterPreFabricatedDrawingEntry savedEntity = repository.save(entity);
            savedEntries.add(savedEntity);
        }
        
        return savedEntries;
    }
    
    @Override
    public List<MeterPreFabricatedDrawingEntry> getMeterPreFabricatedEntriesByWorkOrder(String workOrder) {
        return repository.findByWorkOrder(workOrder);
    }
    
    @Override
    public List<MeterPreFabricatedDrawingEntry> getMeterPreFabricatedEntriesByWorkOrderAndVehicleNumber(String workOrder, String vehicleNumber) {
        return repository.findByWorkOrderAndVehicleNumber(workOrder, vehicleNumber);
    }
    
    @Override
    public List<MeterPreFabricatedDrawingEntry> getMeterPreFabricatedEntriesByWorkOrderAndLoadNumber(String workOrder, String loadNumber) {
        return repository.findByWorkOrderAndLoadNumber(workOrder, loadNumber);
    }
    
    @Override
    public List<MeterPreFabricatedDrawingEntry> getMeterPreFabricatedEntriesByWorkOrderAndPlotNumber(String workOrder, String plotNumber) {
        return repository.findByWorkOrderAndPlotNumber(workOrder, plotNumber);
    }
    
    @Override
    public List<MeterPreFabricatedDrawingEntry> getMeterPreFabricatedEntriesByWorkOrderAndVehicleAndLoad(String workOrder, String vehicleNumber, String loadNumber) {
        return repository.findByWorkOrderAndVehicleNumberAndLoadNumber(workOrder, vehicleNumber, loadNumber);
    }
    
    @Override
    public List<MeterPreFabricatedDrawingEntry> getMeterPreFabricatedEntriesByWorkOrderAndVehicleAndPlot(String workOrder, String vehicleNumber, String plotNumber) {
        return repository.findByWorkOrderAndVehicleNumberAndPlotNumber(workOrder, vehicleNumber, plotNumber);
    }
    
    @Override
    public List<MeterPreFabricatedDrawingEntry> getMeterPreFabricatedEntriesByWorkOrderAndLoadAndPlot(String workOrder, String loadNumber, String plotNumber) {
        return repository.findByWorkOrderAndLoadNumberAndPlotNumber(workOrder, loadNumber, plotNumber);
    }
    
    @Override
    public List<MeterPreFabricatedDrawingEntry> getMeterPreFabricatedEntriesByWorkOrderAndVehicleAndLoadAndPlot(String workOrder, String vehicleNumber, String loadNumber, String plotNumber) {
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
    public List<MeterPreFabricatedDrawingEntry> getMeterPreFabricatedEntriesByMarkNo(String markNo) {
        return repository.findByMarkNo(markNo);
    }
    
    @Override
    public MeterPreFabricatedDrawingEntry updateMeterPreFabricatedEntry(Long id, MeterPreFabricatedDrawingEntry updatedEntry) {
        Optional<MeterPreFabricatedDrawingEntry> existingEntry = repository.findById(id);
        if (existingEntry.isPresent()) {
            MeterPreFabricatedDrawingEntry entity = existingEntry.get();
            
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
        throw new RuntimeException("MeterPreFabricatedDrawingEntry not found with id: " + id);
    }
    
    @Override
    public void deleteMeterPreFabricatedEntry(Long id) {
        repository.deleteById(id);
    }
    
    @Override
    public void deleteMeterPreFabricatedEntriesByWorkOrder(String workOrder) {
        repository.deleteByWorkOrder(workOrder);
    }
    
    @Override
    public void deleteMeterPreFabricatedEntriesByWorkOrderAndVehicleAndLoadAndPlot(String workOrder, String vehicleNumber, String loadNumber, String plotNumber) {
        repository.deleteByWorkOrderAndVehicleNumberAndLoadNumberAndPlotNumber(workOrder, vehicleNumber, loadNumber, plotNumber);
    }
}
