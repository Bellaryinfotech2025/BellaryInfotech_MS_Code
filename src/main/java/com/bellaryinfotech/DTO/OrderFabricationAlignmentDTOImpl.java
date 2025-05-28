package com.bellaryinfotech.DTO;

import com.bellaryinfotech.model.OrderFabricationAlignment;
import java.util.List;
import java.util.stream.Collectors;

public class OrderFabricationAlignmentDTOImpl {

    public static OrderFabricationAlignmentDTO toDTO(OrderFabricationAlignment entity) {
        if (entity == null) return null;
        OrderFabricationAlignmentDTO dto = new OrderFabricationAlignmentDTO();
        dto.setId(entity.getId());
        dto.setBuildingName(entity.getBuildingName());
        dto.setDrawingNo(entity.getDrawingNo());
        dto.setDrawingDescription(entity.getDrawingDescription());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setOrderId(entity.getOrderId());
        dto.setOrigLineNumber(entity.getOrigLineNumber());
        dto.setOrigLineId(entity.getOrigLineId());
        dto.setLineNumber(entity.getLineNumber());
        dto.setLineId(entity.getLineId());
        dto.setErectionMkd(entity.getErectionMkd());
        dto.setItemNo(entity.getItemNo());
        dto.setSection(entity.getSection());
        dto.setLength(entity.getLength());
        dto.setLengthUom(entity.getLengthUom());
        dto.setQuantity(entity.getQuantity());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setUnitPriceUom(entity.getUnitPriceUom());
        dto.setTotalQuantity(entity.getTotalQuantity());
        dto.setTotalQuantityUom(entity.getTotalQuantityUom());
        dto.setOriginalQuantity(entity.getOriginalQuantity());
        dto.setRepeatedQty(entity.getRepeatedQty());
        dto.setRemark(entity.getRemark());
        dto.setTenantId(entity.getTenantId());
        dto.setCreationDate(entity.getCreationDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setLastUpdateDate(entity.getLastUpdateDate());
        dto.setLastUpdatedBy(entity.getLastUpdatedBy());
        dto.setOrgId(entity.getOrgId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setVersion(entity.getVersion());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public static List<OrderFabricationAlignmentDTO> toDTOList(List<OrderFabricationAlignment> entities) {
        return entities.stream().map(OrderFabricationAlignmentDTOImpl::toDTO).collect(Collectors.toList());
    }

    public static OrderFabricationAlignment toEntity(OrderFabricationAlignmentDTO dto) {
        if (dto == null) return null;
        OrderFabricationAlignment entity = new OrderFabricationAlignment();
        entity.setId(dto.getId());
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
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedDate(dto.getUpdatedDate());
        entity.setVersion(dto.getVersion());
        entity.setStatus(dto.getStatus());
        return entity;
    }
}
