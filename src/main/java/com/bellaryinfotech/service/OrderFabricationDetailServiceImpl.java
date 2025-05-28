package com.bellaryinfotech.service;
 
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bellaryinfotech.DAO.LookupDAO;
import com.bellaryinfotech.DAO.OrderFabricationDetailDAO;
import com.bellaryinfotech.DTO.OrderFabricationDetailDTO;
import com.bellaryinfotech.model.OrderFabricationDetail;
 
import com.bellaryinfotech.repo.OrderFabricationDetailRepository;
 

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderFabricationDetailServiceImpl implements OrderFabricationDetailService {
    
    private static final String UOM_LOOKUP_TYPE = "fabrication_uom";
    
    private final OrderFabricationDetailDAO orderFabricationDetailDAO;
    private final LookupDAO lookupDAO;
    
    @Autowired
	private OrderFabricationDetailRepository repository;
    
    @Autowired
    public OrderFabricationDetailServiceImpl(OrderFabricationDetailDAO orderFabricationDetailDAO, LookupDAO lookupDAO) {
        this.orderFabricationDetailDAO = orderFabricationDetailDAO;
        this.lookupDAO = lookupDAO;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OrderFabricationDetailDTO> findAll() {
        return orderFabricationDetailDAO.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public OrderFabricationDetailDTO findById(Long id) {
        return orderFabricationDetailDAO.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Order Fabrication Detail not found with id: " + id));
    }
    
    @Override
    @Transactional
    public OrderFabricationDetailDTO save(OrderFabricationDetailDTO orderFabricationDetailDTO) {
        OrderFabricationDetail orderFabricationDetail = convertToEntity(orderFabricationDetailDTO);
        
        // Set creation and update dates
        LocalDateTime now = LocalDateTime.now();
        orderFabricationDetail.setCreatedDate(now);
        orderFabricationDetail.setCreationDate(LocalDate.now());
        
        // Set status to "Fabrication"
        orderFabricationDetail.setStatus("Fabrication");
        
        OrderFabricationDetail savedEntity = orderFabricationDetailDAO.save(orderFabricationDetail);
        return convertToDTO(savedEntity);
    }
    
    @Override
    @Transactional
    public OrderFabricationDetailDTO update(Long id, OrderFabricationDetailDTO orderFabricationDetailDTO) {
        OrderFabricationDetail existingEntity = orderFabricationDetailDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order Fabrication Detail not found with id: " + id));
        
        // Update fields from DTO
        updateEntityFromDTO(existingEntity, orderFabricationDetailDTO);
        
        // Set update dates
        existingEntity.setUpdatedDate(LocalDateTime.now());
        existingEntity.setLastUpdateDate(LocalDate.now());
        
        // Ensure status is set to "Fabrication" even during updates
        existingEntity.setStatus("Fabrication");
        
        OrderFabricationDetail updatedEntity = orderFabricationDetailDAO.save(existingEntity);
        return convertToDTO(updatedEntity);
    }
    
    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!orderFabricationDetailDAO.findById(id).isPresent()) {
            throw new EntityNotFoundException("Order Fabrication Detail not found with id: " + id);
        }
        orderFabricationDetailDAO.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OrderFabricationDetailDTO> findByOrderId(Long orderId) {
        return orderFabricationDetailDAO.findByOrderId(orderId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OrderFabricationDetailDTO> findByBuildingName(String buildingName) {
        return orderFabricationDetailDAO.findByBuildingName(buildingName).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OrderFabricationDetailDTO> findByDrawingNo(String drawingNo) {
        return orderFabricationDetailDAO.findByDrawingNo(drawingNo).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OrderFabricationDetailDTO> findByOrderNumber(String orderNumber) {
        return orderFabricationDetailDAO.findByOrderNumber(orderNumber).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OrderFabricationDetailDTO> findByErectionMkd(String erectionMkd) {
        return orderFabricationDetailDAO.findByErectionMkd(erectionMkd).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OrderFabricationDetailDTO> findByLineIdAndErectionMkd(Long lineId, String erectionMkd) {
        return orderFabricationDetailDAO.findByLineIdAndErectionMkd(lineId, erectionMkd).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OrderFabricationDetailDTO> findByLineNumberAndErectionMkd(String lineNumber, String erectionMkd) {
        // First, find all fabrication details with the given erection code
        List<OrderFabricationDetail> fabricationDetails = orderFabricationDetailDAO.findByErectionMkd(erectionMkd);
        
        // Filter the results to only include those where the line number matches
        // We need to convert the String lineNumber to BigDecimal for comparison
        try {
            BigDecimal lineNumberAsBigDecimal = new BigDecimal(lineNumber);
            return fabricationDetails.stream()
                    .filter(detail -> detail.getLineNumber() != null && 
                                     detail.getLineNumber().compareTo(lineNumberAsBigDecimal) == 0)
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            // If the line number can't be converted to BigDecimal, return empty list
            return Collections.emptyList();
        }
    }
    
    private OrderFabricationDetailDTO convertToDTO(OrderFabricationDetail entity) {
        OrderFabricationDetailDTO dto = new OrderFabricationDetailDTO();
        
        // Copy basic properties
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
        dto.setQuantity(entity.getQuantity());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setTotalQuantity(entity.getTotalQuantity());
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
        
        // Set status field
        dto.setStatus(entity.getStatus());
        
        // Set UOM codes and meanings
        dto.setLengthUom(entity.getLengthUom());
        dto.setLengthUomMeaning(lookupDAO.getMeaningByTypeAndCode(UOM_LOOKUP_TYPE, entity.getLengthUom()));
        
        dto.setUnitPriceUom(entity.getUnitPriceUom());
        dto.setUnitPriceUomMeaning(lookupDAO.getMeaningByTypeAndCode(UOM_LOOKUP_TYPE, entity.getUnitPriceUom()));
        
        dto.setTotalQuantityUom(entity.getTotalQuantityUom());
        dto.setTotalQuantityUomMeaning(lookupDAO.getMeaningByTypeAndCode(UOM_LOOKUP_TYPE, entity.getTotalQuantityUom()));
        
        return dto;
    }
    
    private OrderFabricationDetail convertToEntity(OrderFabricationDetailDTO dto) {
        OrderFabricationDetail entity = new OrderFabricationDetail();
        
        // Copy basic properties
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
        entity.setQuantity(dto.getQuantity());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setTotalQuantity(dto.getTotalQuantity());
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
        
        // Set status from DTO if provided, otherwise it will be set in save/update methods
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        
        // Convert UOM meanings to codes if provided, otherwise use the codes directly
        // Also ensure all UOM codes are stored in uppercase
        if (dto.getLengthUomMeaning() != null && !dto.getLengthUomMeaning().isEmpty()) {
            entity.setLengthUom(lookupDAO.getCodeByTypeAndMeaning(UOM_LOOKUP_TYPE, dto.getLengthUomMeaning()));
        } else if (dto.getLengthUom() != null) {
            entity.setLengthUom(dto.getLengthUom().toUpperCase());
        }
        
        if (dto.getUnitPriceUomMeaning() != null && !dto.getUnitPriceUomMeaning().isEmpty()) {
            entity.setUnitPriceUom(lookupDAO.getCodeByTypeAndMeaning(UOM_LOOKUP_TYPE, dto.getUnitPriceUomMeaning()));
        } else if (dto.getUnitPriceUom() != null) {
            entity.setUnitPriceUom(dto.getUnitPriceUom().toUpperCase());
        }
        
        if (dto.getTotalQuantityUomMeaning() != null && !dto.getTotalQuantityUomMeaning().isEmpty()) {
            entity.setTotalQuantityUom(lookupDAO.getCodeByTypeAndMeaning(UOM_LOOKUP_TYPE, dto.getTotalQuantityUomMeaning()));
        } else if (dto.getTotalQuantityUom() != null) {
            entity.setTotalQuantityUom(dto.getTotalQuantityUom().toUpperCase());
        }
        
        return entity;
    }
    
    private void updateEntityFromDTO(OrderFabricationDetail entity, OrderFabricationDetailDTO dto) {
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
        entity.setQuantity(dto.getQuantity());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setTotalQuantity(dto.getTotalQuantity());
        entity.setOriginalQuantity(dto.getOriginalQuantity());
        entity.setRepeatedQty(dto.getRepeatedQty());
        entity.setRemark(dto.getRemark());
        entity.setTenantId(dto.getTenantId());
        entity.setLastUpdatedBy(dto.getLastUpdatedBy());
        entity.setOrgId(dto.getOrgId());
        entity.setUpdatedBy(dto.getUpdatedBy());
        
        // Status will be set to "Fabrication" in the update method
        
        // Convert UOM meanings to codes if provided, ensure all UOM codes are stored in uppercase
        if (dto.getLengthUomMeaning() != null && !dto.getLengthUomMeaning().isEmpty()) {
            entity.setLengthUom(lookupDAO.getCodeByTypeAndMeaning(UOM_LOOKUP_TYPE, dto.getLengthUomMeaning()));
        } else if (dto.getLengthUom() != null) {
            entity.setLengthUom(dto.getLengthUom().toUpperCase());
        }
        
        if (dto.getUnitPriceUomMeaning() != null && !dto.getUnitPriceUomMeaning().isEmpty()) {
            entity.setUnitPriceUom(lookupDAO.getCodeByTypeAndMeaning(UOM_LOOKUP_TYPE, dto.getUnitPriceUomMeaning()));
        } else if (dto.getUnitPriceUom() != null) {
            entity.setUnitPriceUom(dto.getUnitPriceUom().toUpperCase());
        }
        
        if (dto.getTotalQuantityUomMeaning() != null && !dto.getTotalQuantityUomMeaning().isEmpty()) {
            entity.setTotalQuantityUom(lookupDAO.getCodeByTypeAndMeaning(UOM_LOOKUP_TYPE, dto.getTotalQuantityUomMeaning()));
        } else if (dto.getTotalQuantityUom() != null) {
            entity.setTotalQuantityUom(dto.getTotalQuantityUom().toUpperCase());
        }
    }
    
    
    
    
    public int copyMarkNumber(String sourceMarkNo, String newMarkNo) {
		 List<OrderFabricationDetail> sourceRecords = repository.findByErectionMkd(sourceMarkNo);

	        if (sourceRecords.isEmpty()) {
	            return 0;
	        }

	        for (OrderFabricationDetail source : sourceRecords) {
	        	OrderFabricationDetail copy = new OrderFabricationDetail();

	            
	            copy.setBuildingName(source.getBuildingName());
	            copy.setDrawingNo(source.getDrawingNo());
	            copy.setDrawingDescription(source.getDrawingDescription());
	            copy.setOrderNumber(source.getOrderNumber());
	            copy.setOrderId(source.getOrderId());
	            copy.setOrigLineNumber(source.getOrigLineNumber());
	            copy.setOrigLineId(source.getOrigLineId());
	            copy.setLineNumber(source.getLineNumber());
	            copy.setLineId(source.getLineId());
	            copy.setItemNo(source.getItemNo());
	            copy.setSection(source.getSection());
	            copy.setLength(source.getLength());
	            copy.setLengthUom(source.getLengthUom());
	            copy.setQuantity(source.getQuantity());
	            copy.setUnitPrice(source.getUnitPrice());
	            copy.setUnitPriceUom(source.getUnitPriceUom());
	            copy.setTotalQuantity(source.getTotalQuantity());
	            copy.setTotalQuantityUom(source.getTotalQuantityUom());
	            copy.setOriginalQuantity(source.getOriginalQuantity());
	            copy.setRepeatedQty(source.getRepeatedQty());
	            copy.setRemark(source.getRemark());
	            copy.setTenantId(source.getTenantId());

	            
	            copy.setErectionMkd(newMarkNo);

	            
	            copy.setCreationDate(LocalDate.now());
	            copy.setCreatedBy(source.getCreatedBy());
	            copy.setLastUpdateDate(LocalDate.now());
	            copy.setLastUpdatedBy(source.getLastUpdatedBy());
	            copy.setOrgId(source.getOrgId());

	            
	            copy.setStatus("Fabrication");

	            repository.save(copy);
	        }

	        return sourceRecords.size();
	    }

    @Override
	@Transactional
	public void updateStatusByErectionMkd(String erectionMkd, String status) {
	    List<OrderFabricationDetail> details = repository.findByErectionMkd(erectionMkd);
	    details.forEach(d -> d.setStatus(status));
	    repository.saveAll(details);
	}

	public String storeErectionMkds(List<String> erectionMkds) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 

}