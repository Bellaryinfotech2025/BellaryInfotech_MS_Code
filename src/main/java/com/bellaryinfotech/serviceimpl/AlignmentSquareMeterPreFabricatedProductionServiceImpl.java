package com.bellaryinfotech.serviceimpl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bellaryinfotech.DTO.AlignmentSquareMeterPreFabricatedDTO;
import com.bellaryinfotech.model.AlignmentSquareMeterPreFabricatedProduction;
import com.bellaryinfotech.repo.AlignmentSquareMeterPreFabricatedProductionRepository;
import com.bellaryinfotech.service.AlignmentSquareMeterPreFabricatedProductionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlignmentSquareMeterPreFabricatedProductionServiceImpl implements AlignmentSquareMeterPreFabricatedProductionService {

 @Autowired
 private AlignmentSquareMeterPreFabricatedProductionRepository repository;

 @Override
 public AlignmentSquareMeterPreFabricatedDTO saveAlignmentSquareMeterPreFabricatedModule(AlignmentSquareMeterPreFabricatedDTO dto) {
     List<AlignmentSquareMeterPreFabricatedProduction> entities = dto.getEntries().stream().map(entry -> {
         AlignmentSquareMeterPreFabricatedProduction entity = new AlignmentSquareMeterPreFabricatedProduction();
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
         entity.setDrawingNo(entry.getDrawingNo());
         entity.setMarkNo(entry.getMarkNo());
         entity.setEachMarkLength(entry.getEachMarkLength());
         entity.setMarkQty(entry.getMarkQty());
         entity.setTotalMarkLength(entry.getTotalMarkLength());
         entity.setRemarks(entry.getRemarks());
         entity.setVehicleNumber(entry.getVehicleNumber());
         entity.setLoadNumber(entry.getLoadNumber());
         entity.setPlotNumber(entry.getPlotNumber());
         return entity;
     }).collect(Collectors.toList());

     repository.saveAll(entities);
     return dto;
 }

 @Override
 public List<AlignmentSquareMeterPreFabricatedProduction> getByWorkOrderAndServiceDescription(String workOrder, String serviceDescription) {
     return repository.findByWorkOrderAndServiceDescription(workOrder, serviceDescription);
 }

 @Override
 public List<AlignmentSquareMeterPreFabricatedProduction> getEntriesByWorkOrder(String workOrder, String vehicleNumber, String loadNumber, String plotNumber) {
     return repository.findByWorkOrderAndFilters(workOrder, vehicleNumber, loadNumber, plotNumber);
 }

 @Override
 public AlignmentSquareMeterPreFabricatedProduction updateEntry(Long id, AlignmentSquareMeterPreFabricatedProduction entry) {
     if (!repository.existsById(id)) {
         throw new RuntimeException("Entry not found with id: " + id);
     }
     entry.setId(id);
     return repository.save(entry);
 }

 @Override
 public void deleteEntry(Long id) {
     if (!repository.existsById(id)) {
         throw new RuntimeException("Entry not found with id: " + id);
     }
     repository.deleteById(id);
 }

 @Override
 public List<String> getDistinctWorkOrders() {
     return repository.findDistinctWorkOrders();
 }
}