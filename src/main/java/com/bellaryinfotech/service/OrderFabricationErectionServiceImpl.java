package com.bellaryinfotech.service;

import com.bellaryinfotech.model.OrderFabricationErection;
import com.bellaryinfotech.model.OrderFabricationImport;
import com.bellaryinfotech.DTO.OrderFabricationDetailDTO;
import com.bellaryinfotech.repo.OrderFabricationErectionRepository;
import com.bellaryinfotech.repo.OrderFabricationImportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class OrderFabricationErectionServiceImpl implements OrderFabricationErectionService {

    @Autowired
    private OrderFabricationErectionRepository erectionRepository;
    @Autowired
    private OrderFabricationImportRepository importRepository;
    @Autowired
    private OrderFabricationDetailService orderFabricationDetailService;

    @Override
    public List<String> getAllErectionMkds() {
        return erectionRepository.findAll()
                .stream()
                .map(OrderFabricationErection::getErectionMkd)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllErectionMkdsFromImport() {
        return importRepository.findAll()
                .stream()
                .map(OrderFabricationImport::getErectionMkd)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderFabricationErection> saveErectionDetails(List<OrderFabricationErection> erectionDetails) {
        return erectionRepository.saveAll(erectionDetails);
    }

    @Override
    @Transactional
    public List<OrderFabricationErection> storeErectionMkdsFromImport(List<String> erectionMkds) {
        List<OrderFabricationErection> erectionEntities = new ArrayList<>();
        for (String mkd : erectionMkds) {
            List<OrderFabricationImport> importRows = importRepository.findByErectionMkd(mkd);
            for (OrderFabricationImport imp : importRows) {
                imp.setStatus("erection");
            }
            importRepository.saveAll(importRows);

            for (OrderFabricationImport imp : importRows) {
                OrderFabricationErection entity = new OrderFabricationErection();
                // Map all fields from import to erection entity (adjust field names as needed)
                entity.setBuildingName(imp.getBuildingName());
                entity.setDrawingNo(imp.getDrawingNo());
                entity.setDrawingDescription(imp.getDrawingDescription()); // adjust if needed
                entity.setOrderNumber(imp.getOrderNumber());
                entity.setOrderId(imp.getOrderId());
                entity.setOrigLineNumber(imp.getOrigLineNumber());
                entity.setOrigLineId(imp.getOrigLineId());
                entity.setLineNumber(imp.getLineNumber());
                entity.setLineId(imp.getLineId()); // adjust if needed
                entity.setErectionMkd(imp.getErectionMkd());
                entity.setItemNo(imp.getItemNo());
                entity.setSection(imp.getSection());
                entity.setLength(imp.getLength());
                entity.setLengthUom(imp.getLengthUom());
                entity.setQuantity(imp.getQuantity());
                entity.setUnitPrice(imp.getUnitPrice());
                entity.setUnitPriceUom(imp.getUnitPriceUom());
                entity.setTotalQuantity(imp.getTotalQuantity());
                entity.setTotalQuantityUom(imp.getTotalQuantityUom());
                entity.setOriginalQuantity(imp.getOriginalQuantity()); // adjust if needed
                entity.setRepeatedQty(imp.getRepeatedQty());
                entity.setRemark(imp.getRemark());
                entity.setTenantId(imp.getTenantId());
                entity.setCreationDate(imp.getCreationDate());
                entity.setCreatedBy(imp.getCreatedBy());
                entity.setLastUpdateDate(imp.getLastUpdateDate());
                entity.setLastUpdatedBy(imp.getLastUpdatedBy());
                entity.setOrgId(imp.getOrgId());
                entity.setStatus("erection");
                erectionEntities.add(entity);
            }
        }
        erectionRepository.saveAll(erectionEntities);
        return erectionEntities;
    }

    @Override
    @Transactional
    public List<OrderFabricationErection> storeErectionMkdsFromDetails(List<String> erectionMkds) {
        List<OrderFabricationErection> erectionEntities = new ArrayList<>();
        for (String mkd : erectionMkds) {
            List<OrderFabricationDetailDTO> details = orderFabricationDetailService.findByErectionMkd(mkd);
            // Update status in details table
            orderFabricationDetailService.updateStatusByErectionMkd(mkd, "erection");

            for (OrderFabricationDetailDTO dto : details) {
                OrderFabricationErection entity = new OrderFabricationErection();
                // Map all fields from dto to entity (adjust field names as needed)
                entity.setBuildingName(dto.getBuildingName());
                entity.setDrawingNo(dto.getDrawingNo());
                entity.setDrawingDescription(dto.getDrawingDescription());
                entity.setOrderNumber(dto.getOrderNumber());
                entity.setOrderId(dto.getOrderId());
                entity.setOrigLineNumber(dto.getOrigLineNumber());
                entity.setOrigLineId(dto.getOrigLineId());
                entity.setLineNumber(dto.getLineNumber());
                entity.setLineId(dto.getLineId());
                entity.setErectionMkd(dto.getErectionMkd());
                entity.setItemNo(dto.getItemNo());
                entity.setSection(dto.getSection());
                entity.setLength(dto.getLength());
                entity.setLengthUom(dto.getLengthUom());
                entity.setQuantity(dto.getQuantity());
                entity.setUnitPrice(dto.getUnitPrice());
                entity.setUnitPriceUom(dto.getUnitPriceUom());
                entity.setTotalQuantity(dto.getTotalQuantity());
                entity.setTotalQuantityUom(dto.getTotalQuantityUom());
                entity.setOriginalQuantity(dto.getOriginalQuantity());
                entity.setRepeatedQty(dto.getRepeatedQty());
                entity.setRemark(dto.getRemark());
                entity.setTenantId(dto.getTenantId());
                entity.setCreationDate(dto.getCreationDate());
                entity.setCreatedBy(dto.getCreatedBy());
                entity.setLastUpdateDate(dto.getLastUpdateDate());
                entity.setLastUpdatedBy(dto.getLastUpdatedBy());
                entity.setOrgId(dto.getOrgId());
                entity.setStatus("erection");
                erectionEntities.add(entity);
            }
        }
        erectionRepository.saveAll(erectionEntities);
        return erectionEntities;
    }

    @Override
    public void updateStatusByErectionMkd(String erectionMkd, String status) {
        List<OrderFabricationErection> list = erectionRepository.findByErectionMkd(erectionMkd);
        list.forEach(e -> e.setStatus(status));
        erectionRepository.saveAll(list);
    }

    @Override
    public void updateImportStatusByErectionMkd(String erectionMkd, String status) {
        List<OrderFabricationImport> list = importRepository.findByErectionMkd(erectionMkd);
        list.forEach(imp -> imp.setStatus(status));
         importRepository.saveAll(list);
    }

    @Override
    public List<OrderFabricationErection> getStoredErectionRecords() {
        return erectionRepository.findAll();  
    }

}
