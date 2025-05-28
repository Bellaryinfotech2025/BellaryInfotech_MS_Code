package com.bellaryinfotech.DTO;

import com.bellaryinfotech.model.OrderFabricationBilling;
import com.bellaryinfotech.model.OrderFabricationAlignment;
import java.util.List;
import java.util.stream.Collectors;

public class OrderFabricationBillingDTOImpl {

    public static OrderFabricationBillingDTO toDTO(OrderFabricationBilling entity) {
        if (entity == null) return null;
        OrderFabricationBillingDTO dto = new OrderFabricationBillingDTO();
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

    public static List<OrderFabricationBillingDTO> toDTOList(List<OrderFabricationBilling> entities) {
        return entities.stream().map(OrderFabricationBillingDTOImpl::toDTO).collect(Collectors.toList());
    }

    public static OrderFabricationBilling toEntity(OrderFabricationBillingDTO dto) {
        if (dto == null) return null;
        OrderFabricationBilling entity = new OrderFabricationBilling();
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

    // Mapper from Alignment entity to Billing entity
    public static OrderFabricationBilling fromAlignment(OrderFabricationAlignment alignment) {
        OrderFabricationBilling billing = new OrderFabricationBilling();
        billing.setBuildingName(alignment.getBuildingName());
        billing.setDrawingNo(alignment.getDrawingNo());
        billing.setDrawingDescription(alignment.getDrawingDescription());
        billing.setOrderNumber(alignment.getOrderNumber());
        billing.setOrderId(alignment.getOrderId());
        billing.setOrigLineNumber(alignment.getOrigLineNumber());
        billing.setOrigLineId(alignment.getOrigLineId());
        billing.setLineNumber(alignment.getLineNumber());
        billing.setLineId(alignment.getLineId());
        billing.setErectionMkd(alignment.getErectionMkd());
        billing.setItemNo(alignment.getItemNo());
        billing.setSection(alignment.getSection());
        billing.setLength(alignment.getLength());
        billing.setLengthUom(alignment.getLengthUom());
        billing.setQuantity(alignment.getQuantity());
        billing.setUnitPrice(alignment.getUnitPrice());
        billing.setUnitPriceUom(alignment.getUnitPriceUom());
        billing.setTotalQuantity(alignment.getTotalQuantity());
        billing.setTotalQuantityUom(alignment.getTotalQuantityUom());
        billing.setOriginalQuantity(alignment.getOriginalQuantity());
        billing.setRepeatedQty(alignment.getRepeatedQty());
        billing.setRemark(alignment.getRemark());
        billing.setTenantId(alignment.getTenantId());
        billing.setCreationDate(alignment.getCreationDate());
        billing.setCreatedBy(alignment.getCreatedBy());
        billing.setLastUpdateDate(alignment.getLastUpdateDate());
        billing.setLastUpdatedBy(alignment.getLastUpdatedBy());
        billing.setOrgId(alignment.getOrgId());
        billing.setCreatedDate(alignment.getCreatedDate());
        billing.setUpdatedBy(alignment.getUpdatedBy());
        billing.setUpdatedDate(alignment.getUpdatedDate());
        billing.setVersion(alignment.getVersion());
        billing.setStatus("billing");
        return billing;
    }
}
