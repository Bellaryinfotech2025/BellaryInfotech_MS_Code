package com.bellaryinfotech.serviceimpl;


import com.bellaryinfotech.DTO.OrderLineDetailsDTO;
import com.bellaryinfotech.model.OrderLineDetails;
import com.bellaryinfotech.repo.OrderLineDetailsRepository;
import com.bellaryinfotech.service.OrderLineDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderLineDetailsServiceImpl implements OrderLineDetailsService {

    @Autowired
    private OrderLineDetailsRepository repository;

    @Override
    public List<OrderLineDetailsDTO> getDetailsByOrderId(BigDecimal orderId) {
        return repository.findByOrderId(orderId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderLineDetailsDTO saveOrderLineDetail(OrderLineDetailsDTO dto) {
        OrderLineDetails entity = convertToEntity(dto);
        OrderLineDetails saved = repository.save(entity);
        return convertToDTO(saved);
    }

    private OrderLineDetailsDTO convertToDTO(OrderLineDetails entity) {
        OrderLineDetailsDTO dto = new OrderLineDetailsDTO();
        dto.setLineId(entity.getLineId());
        dto.setOrderId(entity.getOrderId());
        dto.setOrigLineId(entity.getOrigLineId());
        dto.setBatchId(entity.getBatchId());
        dto.setBuildingName(entity.getBuildingName());
        dto.setDrawingNo(entity.getDrawingNo());
        dto.setDescription(entity.getDescription());
        dto.setErectionMkd(entity.getErectionMkd());
        dto.setItemNo(entity.getItemNo());
        dto.setSection(entity.getSection());
        dto.setLength(entity.getLength());
        dto.setLengthUom(entity.getLengthUom());
        dto.setUnit(entity.getUnit());
        dto.setUnitUom(entity.getUnitUom());
        dto.setTotalWt(entity.getTotalWt());
        dto.setTotalWtUom(entity.getTotalWtUom());
        dto.setQtyReqd(entity.getQtyReqd());
        dto.setErecMkdWt(entity.getErecMkdWt());
        dto.setErecMkdWtUom(entity.getErecMkdWtUom());
        dto.setRemarks(entity.getRemarks());
        dto.setCreationDate(entity.getCreationDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setLastUpdateDate(entity.getLastUpdateDate());
        dto.setLastUpdatedBy(entity.getLastUpdatedBy());
        dto.setSourceId(entity.getSourceId());
        dto.setOrgId(entity.getOrgId());
        dto.setTenantId(entity.getTenantId());
        dto.setAttributeContext(entity.getAttributeContext());
        return dto;
    }

    private OrderLineDetails convertToEntity(OrderLineDetailsDTO dto) {
        OrderLineDetails entity = new OrderLineDetails();
        entity.setLineId(dto.getLineId());
        entity.setOrderId(dto.getOrderId());
        entity.setOrigLineId(dto.getOrigLineId());
        entity.setBatchId(dto.getBatchId());
        entity.setBuildingName(dto.getBuildingName());
        entity.setDrawingNo(dto.getDrawingNo());
        entity.setDescription(dto.getDescription());
        entity.setErectionMkd(dto.getErectionMkd());
        entity.setItemNo(dto.getItemNo());
        entity.setSection(dto.getSection());
        entity.setLength(dto.getLength());
        entity.setLengthUom(dto.getLengthUom());
        entity.setUnit(dto.getUnit());
        entity.setUnitUom(dto.getUnitUom());
        entity.setTotalWt(dto.getTotalWt());
        entity.setTotalWtUom(dto.getTotalWtUom());
        entity.setQtyReqd(dto.getQtyReqd());
        entity.setErecMkdWt(dto.getErecMkdWt());
        entity.setErecMkdWtUom(dto.getErecMkdWtUom());
        entity.setRemarks(dto.getRemarks());
        entity.setCreationDate(dto.getCreationDate());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setLastUpdateDate(dto.getLastUpdateDate());
        entity.setLastUpdatedBy(dto.getLastUpdatedBy());
        entity.setSourceId(dto.getSourceId());
        entity.setOrgId(dto.getOrgId());
        entity.setTenantId(dto.getTenantId());
        entity.setAttributeContext(dto.getAttributeContext());
        return entity;
    }
}
