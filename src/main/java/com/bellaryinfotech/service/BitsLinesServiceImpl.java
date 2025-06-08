package com.bellaryinfotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bellaryinfotech.DTO.BitsLinesDto;
import com.bellaryinfotech.model.BitsLinesAll;
import com.bellaryinfotech.repo.BitsLinesRepository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BitsLinesServiceImpl implements BitsLinesService {

    private static final Logger LOG = LoggerFactory.getLogger(BitsLinesServiceImpl.class);

    @Autowired
    private BitsLinesRepository linesRepository;

    public List<BitsLinesDto> getAllLines() {
        List<BitsLinesAll> lines = linesRepository.findAll();
        return lines.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<BitsLinesDto> getLineById(Long id) {
        Optional<BitsLinesAll> line = linesRepository.findById(id);
        return line.map(this::convertToDto);
    }

    public BitsLinesDto createLine(BitsLinesDto lineDto) {
        LOG.info("Creating line with DTO: {}", lineDto);
        
        BitsLinesAll line = convertToEntity(lineDto);
        line.setCreationDate(Timestamp.from(Instant.now()));
        line.setLastUpdateDate(Timestamp.from(Instant.now()));
        line.setCreatedBy(1L); // Default user
        line.setLastUpdatedBy(1L); // Default user
        line.setTenantId(1); // Default tenant
        
        // Calculate total price if not provided
        if (line.getTotalPrice() == null && line.getQty() != null && line.getUnitPrice() != null) {
            BigDecimal totalPrice = line.getQty().multiply(line.getUnitPrice());
            line.setTotalPrice(totalPrice);
        }
        
        LOG.info("Saving line entity with attribute1V: {}", line.getAttribute1V());
        
        BitsLinesAll savedLine = linesRepository.save(line);
        
        LOG.info("Saved line with ID: {}, attribute1V: {}", savedLine.getLineId(), savedLine.getAttribute1V());
        
        return convertToDto(savedLine);
    }

    public BitsLinesDto updateLine(Long id, BitsLinesDto lineDto) {
        Optional<BitsLinesAll> existingLine = linesRepository.findById(id);
        if (existingLine.isPresent()) {
            BitsLinesAll line = existingLine.get();
            updateEntityFromDto(line, lineDto);
            line.setLastUpdateDate(Timestamp.from(Instant.now()));
            line.setLastUpdatedBy(1L); // Default user
            
            // Recalculate total price
            if (line.getQty() != null && line.getUnitPrice() != null) {
                BigDecimal totalPrice = line.getQty().multiply(line.getUnitPrice());
                line.setTotalPrice(totalPrice);
            }
            
            BitsLinesAll updatedLine = linesRepository.save(line);
            return convertToDto(updatedLine);
        }
        throw new RuntimeException("Line not found with id: " + id);
    }

    public void deleteLine(Long id) {
        linesRepository.deleteById(id);
    }

    public List<BitsLinesDto> searchBySerNo(String serNo) {
        List<BitsLinesAll> lines = linesRepository.findBySerNoContainingIgnoreCase(serNo);
        return lines.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<BitsLinesDto> searchByServiceCode(String serviceCode) {
        List<BitsLinesAll> lines = linesRepository.findByServiceCodeContainingIgnoreCase(serviceCode);
        return lines.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<BitsLinesDto> searchByServiceDesc(String serviceDesc) {
        List<BitsLinesAll> lines = linesRepository.findByServiceDescContainingIgnoreCase(serviceDesc);
        return lines.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // New method to get service orders by work order
    public List<BitsLinesDto> getLinesByWorkOrder(String workOrder) {
        LOG.info("Searching for lines with work order: '{}'", workOrder);
        
        // Try exact match first
        List<BitsLinesAll> lines = linesRepository.findByAttribute1V(workOrder);
        LOG.info("Exact match found {} lines", lines.size());
        
        // If no exact match, try case-insensitive contains
        if (lines.isEmpty()) {
            lines = linesRepository.findByAttribute1VContainingIgnoreCase(workOrder);
            LOG.info("Case-insensitive search found {} lines", lines.size());
        }
        
        // Log what we found
        for (BitsLinesAll line : lines) {
            LOG.info("Found line: ID={}, SerNo={}, ServiceCode={}, attribute1V='{}'", 
                line.getLineId(), line.getSerNo(), line.getServiceCode(), line.getAttribute1V());
        }
        
        return lines.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // Debug method
    public List<BitsLinesDto> getAllLinesWithAttributes() {
        List<BitsLinesAll> lines = linesRepository.findAll();
        LOG.info("Debug: Total lines in database: {}", lines.size());
        
        for (BitsLinesAll line : lines) {
            LOG.info("Debug: Line ID: {}, attribute1V: '{}', attribute1N: {}", 
                line.getLineId(), line.getAttribute1V(), line.getAttribute1N());
        }
        
        return lines.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private BitsLinesDto convertToDto(BitsLinesAll entity) {
        BitsLinesDto dto = new BitsLinesDto();
        // Core fields
        dto.setLineId(entity.getLineId());
        dto.setLineNumber(entity.getLineNumber());
        dto.setSerNo(entity.getSerNo());
        dto.setServiceCode(entity.getServiceCode());
        dto.setServiceDesc(entity.getServiceDesc());
        dto.setQty(entity.getQty());
        dto.setUom(entity.getUom());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setTotalPrice(entity.getTotalPrice());
        
        // Attribute mappings
        dto.setWorkOrderRef(entity.getAttribute1V());
        dto.setAttribute2V(entity.getAttribute2V());
        dto.setAttribute3V(entity.getAttribute3V());
        dto.setAttribute4V(entity.getAttribute4V());
        dto.setAttribute5V(entity.getAttribute5V());
        dto.setAttribute1N(entity.getAttribute1N());
        dto.setAttribute2N(entity.getAttribute2N());
        dto.setAttribute3N(entity.getAttribute3N());
        dto.setAttribute4N(entity.getAttribute4N());
        dto.setAttribute5N(entity.getAttribute5N());
        dto.setAttribute1D(entity.getAttribute1D());
        dto.setAttribute2D(entity.getAttribute2D());
        dto.setAttribute3D(entity.getAttribute3D());
        dto.setAttribute4D(entity.getAttribute4D());
        dto.setAttribute5D(entity.getAttribute5D());
        
        return dto;
    }

    private BitsLinesAll convertToEntity(BitsLinesDto dto) {
        BitsLinesAll entity = new BitsLinesAll();
        // Core fields
        entity.setLineNumber(dto.getLineNumber());
        entity.setSerNo(dto.getSerNo());
        entity.setServiceCode(dto.getServiceCode());
        entity.setServiceDesc(dto.getServiceDesc());
        entity.setQty(dto.getQty());
        entity.setUom(dto.getUom());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setTotalPrice(dto.getTotalPrice());
        
        // Attribute mappings
        entity.setAttribute1V(dto.getWorkOrderRef());
        entity.setAttribute2V(dto.getAttribute2V());
        entity.setAttribute3V(dto.getAttribute3V());
        entity.setAttribute4V(dto.getAttribute4V());
        entity.setAttribute5V(dto.getAttribute5V());
        entity.setAttribute1N(dto.getAttribute1N());
        entity.setAttribute2N(dto.getAttribute2N());
        entity.setAttribute3N(dto.getAttribute3N());
        entity.setAttribute4N(dto.getAttribute4N());
        entity.setAttribute5N(dto.getAttribute5N());
        entity.setAttribute1D(dto.getAttribute1D());
        entity.setAttribute2D(dto.getAttribute2D());
        entity.setAttribute3D(dto.getAttribute3D());
        entity.setAttribute4D(dto.getAttribute4D());
        entity.setAttribute5D(dto.getAttribute5D());
        
        return entity;
    }

    private void updateEntityFromDto(BitsLinesAll entity, BitsLinesDto dto) {
        entity.setSerNo(dto.getSerNo());
        entity.setServiceCode(dto.getServiceCode());
        entity.setServiceDesc(dto.getServiceDesc());
        entity.setQty(dto.getQty());
        entity.setUom(dto.getUom());
        entity.setUnitPrice(dto.getUnitPrice());     // Correct getter
        entity.setTotalPrice(dto.getTotalPrice());   // Correct getter
        // Don't update workOrderRef during updates to maintain the link
    }
}