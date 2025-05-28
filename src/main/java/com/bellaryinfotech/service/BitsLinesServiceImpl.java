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

@Service
public class BitsLinesServiceImpl implements BitsLinesService {

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
        
        BitsLinesAll savedLine = linesRepository.save(line);
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

    private BitsLinesDto convertToDto(BitsLinesAll entity) {
        BitsLinesDto dto = new BitsLinesDto();
        dto.setLineId(entity.getLineId());
        dto.setSerNo(entity.getSerNo());
        dto.setServiceCode(entity.getServiceCode());
        dto.setServiceDesc(entity.getServiceDesc());
        dto.setQty(entity.getQty());
        dto.setUom(entity.getUom());
        dto.setRate(entity.getUnitPrice()); // Map unitPrice to rate
        dto.setAmount(entity.getTotalPrice()); // Map totalPrice to amount
        return dto;
    }

    private BitsLinesAll convertToEntity(BitsLinesDto dto) {
        BitsLinesAll entity = new BitsLinesAll();
        entity.setSerNo(dto.getSerNo());
        entity.setServiceCode(dto.getServiceCode());
        entity.setServiceDesc(dto.getServiceDesc());
        entity.setQty(dto.getQty());
        entity.setUom(dto.getUom());
        entity.setUnitPrice(dto.getRate()); // Map rate to unitPrice
        entity.setTotalPrice(dto.getAmount()); // Map amount to totalPrice
        return entity;
    }

    private void updateEntityFromDto(BitsLinesAll entity, BitsLinesDto dto) {
        entity.setSerNo(dto.getSerNo());
        entity.setServiceCode(dto.getServiceCode());
        entity.setServiceDesc(dto.getServiceDesc());
        entity.setQty(dto.getQty());
        entity.setUom(dto.getUom());
        entity.setUnitPrice(dto.getRate()); // Map rate to unitPrice
        entity.setTotalPrice(dto.getAmount()); // Map amount to totalPrice
    }
}
