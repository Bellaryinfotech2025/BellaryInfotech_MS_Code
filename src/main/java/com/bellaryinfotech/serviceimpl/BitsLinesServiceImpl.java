package com.bellaryinfotech.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bellaryinfotech.DTO.BitsLinesDto;
import com.bellaryinfotech.model.BitsLinesAll;
import com.bellaryinfotech.repo.BitsLinesRepository;
import com.bellaryinfotech.service.BitsLinesService;
import com.bellaryinfotech.repo.BitsHeaderRepository;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class BitsLinesServiceImpl implements BitsLinesService {
    private static final Logger LOG = LoggerFactory.getLogger(BitsLinesServiceImpl.class);

    @Autowired
    private BitsLinesRepository linesRepository;
        
    @Autowired
    private BitsHeaderRepository headerRepository;

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

    @Transactional
    public BitsLinesDto createLine(BitsLinesDto lineDto, Long orderId) {
        LOG.info("Creating line with DTO: {} for orderId: {}", lineDto, orderId);
                
        if (!headerRepository.existsById(orderId)) {
            throw new RuntimeException("Work order not found with id: " + orderId);
        }
                
        BitsLinesAll line = convertToEntity(lineDto);
        line.setOrderId(orderId);
        BigDecimal nextLineNumber = linesRepository.getNextLineNumber(orderId);
        line.setLineNumber(nextLineNumber);
        line.setCreationDate(Timestamp.from(Instant.now()));
        line.setLastUpdateDate(Timestamp.from(Instant.now()));
        line.setCreatedBy(1L);
        line.setLastUpdatedBy(1L);
        line.setTenantId(1);
                
        if (line.getTotalPrice() == null && line.getQty() != null && line.getUnitPrice() != null) {
            BigDecimal totalPrice = line.getQty().multiply(line.getUnitPrice());
            line.setTotalPrice(totalPrice);
        }
                
        if (lineDto.getGstType() != null && !lineDto.getGstType().isEmpty()) {
            BigDecimal subTotal = line.getTotalPrice() != null ? line.getTotalPrice() : BigDecimal.ZERO;
            line.setSubTotal(subTotal);
            BigDecimal gstRate = parseGSTRate(lineDto.getGstType());
                        
            if (lineDto.getGstType().contains("CGST") && lineDto.getGstType().contains("SGST")) {
                BigDecimal halfRate = gstRate.divide(BigDecimal.valueOf(2), 4, BigDecimal.ROUND_HALF_UP);
                BigDecimal cgstAmount = subTotal.multiply(halfRate).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
                BigDecimal sgstAmount = subTotal.multiply(halfRate).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
                line.setCgstTotal(cgstAmount);
                line.setSgstTotal(sgstAmount);
                line.setTotalIncGst(subTotal.add(cgstAmount).add(sgstAmount));
            } else {
                BigDecimal gstAmount = subTotal.multiply(gstRate).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
                line.setCgstTotal(BigDecimal.ZERO);
                line.setSgstTotal(BigDecimal.ZERO);
                line.setTotalIncGst(subTotal.add(gstAmount));
            }
        } else {
            line.setSubTotal(line.getTotalPrice());
            line.setCgstTotal(BigDecimal.ZERO);
            line.setSgstTotal(BigDecimal.ZERO);
            line.setTotalIncGst(line.getTotalPrice());
        }
                
        LOG.info("Saving line entity with orderId: {}, lineNumber: {}", line.getOrderId(), line.getLineNumber());
        BitsLinesAll savedLine = linesRepository.save(line);
        LOG.info("Saved line with ID: {}, orderId: {}, lineNumber: {}",
                savedLine.getLineId(), savedLine.getOrderId(), savedLine.getLineNumber());
        return convertToDto(savedLine);
    }

    public BitsLinesDto createLine(BitsLinesDto lineDto) {
        LOG.info("Creating line with legacy method - DTO: {}", lineDto);
        BitsLinesAll line = convertToEntity(lineDto);
        line.setCreationDate(Timestamp.from(Instant.now()));
        line.setLastUpdateDate(Timestamp.from(Instant.now()));
        line.setCreatedBy(1L);
        line.setLastUpdatedBy(1L);
        line.setTenantId(1);
                
        if (line.getTotalPrice() == null && line.getQty() != null && line.getUnitPrice() != null) {
            BigDecimal totalPrice = line.getQty().multiply(line.getUnitPrice());
            line.setTotalPrice(totalPrice);
        }
                
        if (lineDto.getOrderId() != null) {
            if (!headerRepository.existsById(lineDto.getOrderId())) {
                throw new RuntimeException("Work order not found with id: " + lineDto.getOrderId());
            }
            BigDecimal nextLineNumber = linesRepository.getNextLineNumber(lineDto.getOrderId());
            line.setLineNumber(nextLineNumber);
        }
                
        LOG.info("Saving line entity with attribute1V: {}", line.getAttribute1V());
        BitsLinesAll savedLine = linesRepository.save(line);
        LOG.info("Saved line with ID: {}, attribute1V: {}", savedLine.getLineId(), savedLine.getAttribute1V());
        return convertToDto(savedLine);
    }

    @Transactional
    public BitsLinesDto updateLine(Long id, BitsLinesDto lineDto) {
        LOG.info("Updating line with ID: {} and data: {}", id, lineDto);
        
        Optional<BitsLinesAll> existingLine = linesRepository.findById(id);
        if (existingLine.isPresent()) {
            BitsLinesAll line = existingLine.get();
            
            if (lineDto.getSerNo() != null) {
                line.setSerNo(lineDto.getSerNo());
            }
            if (lineDto.getServiceCode() != null) {
                line.setServiceCode(lineDto.getServiceCode());
            }
            if (lineDto.getServiceDesc() != null) {
                line.setServiceDesc(lineDto.getServiceDesc());
            }
            if (lineDto.getQty() != null) {
                line.setQty(lineDto.getQty());
            }
            if (lineDto.getUom() != null) {
                line.setUom(lineDto.getUom());
            }
            if (lineDto.getUnitPrice() != null) {
                line.setUnitPrice(lineDto.getUnitPrice());
            }
            if (lineDto.getTotalPrice() != null) {
                line.setTotalPrice(lineDto.getTotalPrice());
            }
            
            // NEW: Update assigned level if provided
            if (lineDto.getAssignedLevel() != null) {
                line.setAssignedLevel(lineDto.getAssignedLevel());
            }
            
            if (lineDto.getQty() != null || lineDto.getUnitPrice() != null) {
                if (line.getQty() != null && line.getUnitPrice() != null) {
                    BigDecimal calculatedTotal = line.getQty().multiply(line.getUnitPrice());
                    line.setTotalPrice(calculatedTotal);
                    LOG.info("Recalculated total price: {}", calculatedTotal);
                }
            }
            
            if (lineDto.getGstType() != null) {
                line.setGstType(lineDto.getGstType());
            }
            if (lineDto.getSubTotal() != null) {
                line.setSubTotal(lineDto.getSubTotal());
            }
            if (lineDto.getCgstTotal() != null) {
                line.setCgstTotal(lineDto.getCgstTotal());
            }
            if (lineDto.getSgstTotal() != null) {
                line.setSgstTotal(lineDto.getSgstTotal());
            }
            if (lineDto.getTotalIncGst() != null) {
                line.setTotalIncGst(lineDto.getTotalIncGst());
            }
            
            line.setLastUpdateDate(Timestamp.from(Instant.now()));
            line.setLastUpdatedBy(1L);
                        
            BitsLinesAll updatedLine = linesRepository.save(line);
            LOG.info("Successfully updated line with ID: {}, unitPrice: {}, totalPrice: {}", 
                    id, updatedLine.getUnitPrice(), updatedLine.getTotalPrice());
            
            return convertToDto(updatedLine);
        }
        throw new RuntimeException("Line not found with id: " + id);
    }

    @Transactional
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

    public List<BitsLinesDto> getLinesByOrderId(Long orderId) {
        LOG.info("Fetching lines for orderId: {}", orderId);
        List<BitsLinesAll> lines = linesRepository.findByOrderIdOrderByLineNumber(orderId);
        LOG.info("Found {} lines for orderId: {}", lines.size(), orderId);
        return lines.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<BitsLinesDto> getLinesByWorkOrder(String workOrder) {
        LOG.info("Searching for lines with work order: '{}'", workOrder);
        List<BitsLinesAll> lines = linesRepository.findByWorkOrderNumber(workOrder);
        LOG.info("Found {} lines for work order: {}", lines.size(), workOrder);
                
        if (lines.isEmpty()) {
            LOG.info("No lines found with new method, trying legacy method");
            lines = linesRepository.findByAttribute1V(workOrder);
            LOG.info("Legacy method found {} lines", lines.size());
        }
                
        return lines.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
        
    @Transactional
    public List<BitsLinesDto> createMultipleLines(List<BitsLinesDto> lineDtos, Long orderId) {
        LOG.info("Creating {} lines for orderId: {}", lineDtos.size(), orderId);
                
        if (!headerRepository.existsById(orderId)) {
            throw new RuntimeException("Work order not found with id: " + orderId);
        }
                
        BigDecimal currentLineNumber = linesRepository.getNextLineNumber(orderId);
                
        List<BitsLinesAll> linesToSave = lineDtos.stream()
                .map(dto -> {
                    BitsLinesAll line = convertToEntity(dto);
                    line.setOrderId(orderId);
                    line.setLineNumber(currentLineNumber);
                    line.setCreationDate(Timestamp.from(Instant.now()));
                    line.setLastUpdateDate(Timestamp.from(Instant.now()));
                    line.setCreatedBy(1L);
                    line.setLastUpdatedBy(1L);
                    line.setTenantId(1);
                                        
                    if (line.getTotalPrice() == null && line.getQty() != null && line.getUnitPrice() != null) {
                        BigDecimal totalPrice = line.getQty().multiply(line.getUnitPrice());
                        line.setTotalPrice(totalPrice);
                    }
                                        
                    return line;
                })
                .collect(Collectors.toList());
                
        for (int i = 1; i < linesToSave.size(); i++) {
            linesToSave.get(i).setLineNumber(currentLineNumber.add(BigDecimal.valueOf(i)));
        }
                
        List<BitsLinesAll> savedLines = linesRepository.saveAll(linesToSave);
        LOG.info("Successfully saved {} lines for orderId: {}", savedLines.size(), orderId);
                
        return savedLines.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
        
    public List<BitsLinesDto> getAllLinesWithAttributes() {
        List<BitsLinesAll> lines = linesRepository.findAll();
        LOG.info("Debug: Total lines in database: {}", lines.size());
                
        for (BitsLinesAll line : lines) {
            LOG.info("Debug: Line ID: {}, orderId: {}, lineNumber: {}, attribute1V: '{}'",
                    line.getLineId(), line.getOrderId(), line.getLineNumber(), line.getAttribute1V());
        }
                
        return lines.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<String> getDistinctSerialNumbers() {
        LOG.info("Fetching distinct serial numbers from repository");
        List<String> serialNumbers = linesRepository.findDistinctSerialNumbers();
        LOG.info("Found {} distinct serial numbers", serialNumbers.size());
        return serialNumbers;
    }

    /**
     * NEW: Save assigned levels for multiple service lines
     */
    @Transactional
    public List<BitsLinesDto> saveAssignedLevels(List<Map<String, Object>> assignmentData) {
        LOG.info("Saving assigned levels for {} services", assignmentData.size());
        
        List<BitsLinesDto> updatedLines = new ArrayList<>();
        
        for (Map<String, Object> assignment : assignmentData) {
            Long lineId = Long.valueOf(assignment.get("lineId").toString());
            String assignedLevel = assignment.get("assignedLevel").toString();
            
            LOG.info("Updating line {} with assigned level: {}", lineId, assignedLevel);
            
            Optional<BitsLinesAll> existingLine = linesRepository.findById(lineId);
            if (existingLine.isPresent()) {
                BitsLinesAll line = existingLine.get();
                line.setAssignedLevel(assignedLevel);
                line.setLastUpdateDate(Timestamp.from(Instant.now()));
                line.setLastUpdatedBy(1L);
                
                BitsLinesAll savedLine = linesRepository.save(line);
                updatedLines.add(convertToDto(savedLine));
                
                LOG.info("Successfully updated line {} with assigned level: {}", lineId, assignedLevel);
            } else {
                LOG.warn("Line not found with ID: {}", lineId);
            }
        }
        
        LOG.info("Successfully saved assigned levels for {} services", updatedLines.size());
        return updatedLines;
    }

    /**
     * NEW: Get work orders that have assigned levels
     */
    public List<Map<String, Object>> getWorkOrdersWithAssignments() {
        LOG.info("Fetching work orders with assigned levels");
        
        List<BitsLinesAll> linesWithAssignments = linesRepository.findLinesWithAssignedLevels();
        
        // Group by order ID and get unique work orders
        Set<Long> orderIdsWithAssignments = linesWithAssignments.stream()
                .map(BitsLinesAll::getOrderId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        
        List<Map<String, Object>> result = orderIdsWithAssignments.stream()
                .map(orderId -> {
                    Map<String, Object> workOrder = new HashMap<>();
                    workOrder.put("orderId", orderId);
                    return workOrder;
                })
                .collect(Collectors.toList());
        
        LOG.info("Found {} work orders with assignments", result.size());
        return result;
    }

    private BitsLinesDto convertToDto(BitsLinesAll entity) {
        BitsLinesDto dto = new BitsLinesDto();
        dto.setLineId(entity.getLineId());
        dto.setOrderId(entity.getOrderId());
        dto.setLineNumber(entity.getLineNumber());
        dto.setSerNo(entity.getSerNo());
        dto.setServiceCode(entity.getServiceCode());
        dto.setServiceDesc(entity.getServiceDesc());
        dto.setQty(entity.getQty());
        dto.setUom(entity.getUom());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setTotalPrice(entity.getTotalPrice());
        
        // NEW: Map assigned level
        dto.setAssignedLevel(entity.getAssignedLevel());
                
        dto.setGstType(entity.getGstType());
        dto.setSubTotal(entity.getSubTotal());
        dto.setCgstTotal(entity.getCgstTotal());
        dto.setSgstTotal(entity.getSgstTotal());
        dto.setTotalIncGst(entity.getTotalIncGst());
                
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
        entity.setOrderId(dto.getOrderId());
        entity.setLineNumber(dto.getLineNumber());
        entity.setSerNo(dto.getSerNo());
        entity.setServiceCode(dto.getServiceCode());
        entity.setServiceDesc(dto.getServiceDesc());
        entity.setQty(dto.getQty());
        entity.setUom(dto.getUom());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setTotalPrice(dto.getTotalPrice());
        
        // NEW: Map assigned level
        entity.setAssignedLevel(dto.getAssignedLevel());
                
        entity.setGstType(dto.getGstType());
        entity.setSubTotal(dto.getSubTotal());
        entity.setCgstTotal(dto.getCgstTotal());
        entity.setSgstTotal(dto.getSgstTotal());
        entity.setTotalIncGst(dto.getTotalIncGst());
                
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
        
    private BigDecimal parseGSTRate(String gstSelection) {
        if (gstSelection == null || gstSelection.isEmpty()) {
            return BigDecimal.ZERO;
        }
                
        try {
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\d+(?:\\.\\d+)?)");
            java.util.regex.Matcher matcher = pattern.matcher(gstSelection);
                        
            if (gstSelection.contains("CGST") && gstSelection.contains("SGST")) {
                java.util.List<String> matches = new java.util.ArrayList<>();
                while (matcher.find()) {
                    matches.add(matcher.group(1));
                }
                if (matches.size() >= 2) {
                    return new BigDecimal(matches.get(0)).add(new BigDecimal(matches.get(1)));
                } else if (matches.size() == 1) {
                    return new BigDecimal(matches.get(0)).multiply(BigDecimal.valueOf(2));
                }
            } else if (matcher.find()) {
                return new BigDecimal(matcher.group(1));
            }
        } catch (Exception e) {
            LOG.warn("Error parsing GST rate from: {}", gstSelection, e);
        }
                
        return BigDecimal.ZERO;
    }
}
