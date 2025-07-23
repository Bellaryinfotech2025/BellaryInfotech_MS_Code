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
import java.util.List;
import java.util.Optional;
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

    // Enhanced create method with automatic order_id and line_number assignment
    @Transactional
    public BitsLinesDto createLine(BitsLinesDto lineDto, Long orderId) {
        LOG.info("Creating line with DTO: {} for orderId: {}", lineDto, orderId);
        
        // Validate that the order exists
        if (!headerRepository.existsById(orderId)) {
            throw new RuntimeException("Work order not found with id: " + orderId);
        }
        
        BitsLinesAll line = convertToEntity(lineDto);
        
        // Set the order ID (foreign key)
        line.setOrderId(orderId);
        
        // Get the next line number for this work order
        BigDecimal nextLineNumber = linesRepository.getNextLineNumber(orderId);
        line.setLineNumber(nextLineNumber);
        
        // Set audit fields
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
        
        // NEW: Calculate and set GST values if GST selection is provided
        if (lineDto.getGstType() != null && !lineDto.getGstType().isEmpty()) {
            BigDecimal subTotal = line.getTotalPrice() != null ? line.getTotalPrice() : BigDecimal.ZERO;
            line.setSubTotal(subTotal);
            
            // Parse GST rate and calculate GST amounts
            BigDecimal gstRate = parseGSTRate(lineDto.getGstType());
            
            if (lineDto.getGstType().contains("CGST") && lineDto.getGstType().contains("SGST")) {
                // For CGST & SGST, split the rate
                BigDecimal halfRate = gstRate.divide(BigDecimal.valueOf(2), 4, BigDecimal.ROUND_HALF_UP);
                BigDecimal cgstAmount = subTotal.multiply(halfRate).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
                BigDecimal sgstAmount = subTotal.multiply(halfRate).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
                
                line.setCgstTotal(cgstAmount);
                line.setSgstTotal(sgstAmount);
                line.setTotalIncGst(subTotal.add(cgstAmount).add(sgstAmount));
            } else {
                // For IGST or single GST rate
                BigDecimal gstAmount = subTotal.multiply(gstRate).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
                line.setCgstTotal(BigDecimal.ZERO);
                line.setSgstTotal(BigDecimal.ZERO);
                line.setTotalIncGst(subTotal.add(gstAmount));
            }
        } else {
            // No GST applied
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

    // Legacy method for backward compatibility
    public BitsLinesDto createLine(BitsLinesDto lineDto) {
        LOG.info("Creating line with legacy method - DTO: {}", lineDto);
        
        BitsLinesAll line = convertToEntity(lineDto);
        line.setCreationDate(Timestamp.from(Instant.now()));
        line.setLastUpdateDate(Timestamp.from(Instant.now()));
        line.setCreatedBy(1L);
        line.setLastUpdatedBy(1L);
        line.setTenantId(1);
        
        // Calculate total price if not provided
        if (line.getTotalPrice() == null && line.getQty() != null && line.getUnitPrice() != null) {
            BigDecimal totalPrice = line.getQty().multiply(line.getUnitPrice());
            line.setTotalPrice(totalPrice);
        }
        
        // If orderId is provided in DTO, use it and get next line number
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
        Optional<BitsLinesAll> existingLine = linesRepository.findById(id);
        if (existingLine.isPresent()) {
            BitsLinesAll line = existingLine.get();
            updateEntityFromDto(line, lineDto);
            line.setLastUpdateDate(Timestamp.from(Instant.now()));
            line.setLastUpdatedBy(1L);
            
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

    // Get lines by order ID (proper foreign key relationship)
    public List<BitsLinesDto> getLinesByOrderId(Long orderId) {
        LOG.info("Fetching lines for orderId: {}", orderId);
        List<BitsLinesAll> lines = linesRepository.findByOrderIdOrderByLineNumber(orderId);
        LOG.info("Found {} lines for orderId: {}", lines.size(), orderId);
        return lines.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Enhanced method to get service orders by work order number
    public List<BitsLinesDto> getLinesByWorkOrder(String workOrder) {
        LOG.info("Searching for lines with work order: '{}'", workOrder);
        
        // Use the new query method that joins with header table
        List<BitsLinesAll> lines = linesRepository.findByWorkOrderNumber(workOrder);
        LOG.info("Found {} lines for work order: {}", lines.size(), workOrder);
        
        // If no results with new method, try legacy method for backward compatibility
        if (lines.isEmpty()) {
            LOG.info("No lines found with new method, trying legacy method");
            lines = linesRepository.findByAttribute1V(workOrder);
            LOG.info("Legacy method found {} lines", lines.size());
        }
        
        return lines.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // Bulk create method for better performance
    @Transactional
    public List<BitsLinesDto> createMultipleLines(List<BitsLinesDto> lineDtos, Long orderId) {
        LOG.info("Creating {} lines for orderId: {}", lineDtos.size(), orderId);
        
        // Validate that the order exists
        if (!headerRepository.existsById(orderId)) {
            throw new RuntimeException("Work order not found with id: " + orderId);
        }
        
        // Get the starting line number
        BigDecimal currentLineNumber = linesRepository.getNextLineNumber(orderId);
        
        List<BitsLinesAll> linesToSave = lineDtos.stream()
                .map(dto -> {
                    BitsLinesAll line = convertToEntity(dto);
                    line.setOrderId(orderId);
                    line.setLineNumber(currentLineNumber);
                    
                    // Set audit fields
                    line.setCreationDate(Timestamp.from(Instant.now()));
                    line.setLastUpdateDate(Timestamp.from(Instant.now()));
                    line.setCreatedBy(1L);
                    line.setLastUpdatedBy(1L);
                    line.setTenantId(1);
                    
                    // Calculate total price
                    if (line.getTotalPrice() == null && line.getQty() != null && line.getUnitPrice() != null) {
                        BigDecimal totalPrice = line.getQty().multiply(line.getUnitPrice());
                        line.setTotalPrice(totalPrice);
                    }
                    
                    return line;
                })
                .collect(Collectors.toList());
        
        // Increment line numbers for each subsequent line
        for (int i = 1; i < linesToSave.size(); i++) {
            linesToSave.get(i).setLineNumber(currentLineNumber.add(BigDecimal.valueOf(i)));
        }
        
        List<BitsLinesAll> savedLines = linesRepository.saveAll(linesToSave);
        
        LOG.info("Successfully saved {} lines for orderId: {}", savedLines.size(), orderId);
        
        return savedLines.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // Debug method
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

    // Get distinct serial numbers
    public List<String> getDistinctSerialNumbers() {
        LOG.info("Fetching distinct serial numbers from repository");
        List<String> serialNumbers = linesRepository.findDistinctSerialNumbers();
        LOG.info("Found {} distinct serial numbers", serialNumbers.size());
        return serialNumbers;
    }

    private BitsLinesDto convertToDto(BitsLinesAll entity) {
        BitsLinesDto dto = new BitsLinesDto();
        // Core fields
        dto.setLineId(entity.getLineId());
        dto.setOrderId(entity.getOrderId()); // Include order ID
        dto.setLineNumber(entity.getLineNumber());
        dto.setSerNo(entity.getSerNo());
        dto.setServiceCode(entity.getServiceCode());
        dto.setServiceDesc(entity.getServiceDesc());
        dto.setQty(entity.getQty());
        dto.setUom(entity.getUom());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setTotalPrice(entity.getTotalPrice());
        
        // NEW: Map GST fields
        dto.setGstType(entity.getGstType());
        dto.setSubTotal(entity.getSubTotal());
        dto.setCgstTotal(entity.getCgstTotal());
        dto.setSgstTotal(entity.getSgstTotal());
        dto.setTotalIncGst(entity.getTotalIncGst());
        
        // Attribute mappings (maintain backward compatibility)
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
        entity.setOrderId(dto.getOrderId()); // Set order ID if provided
        entity.setLineNumber(dto.getLineNumber());
        entity.setSerNo(dto.getSerNo());
        entity.setServiceCode(dto.getServiceCode());
        entity.setServiceDesc(dto.getServiceDesc());
        entity.setQty(dto.getQty());
        entity.setUom(dto.getUom());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setTotalPrice(dto.getTotalPrice());
        
        // NEW: Map GST fields
        entity.setGstType(dto.getGstType());
        entity.setSubTotal(dto.getSubTotal());
        entity.setCgstTotal(dto.getCgstTotal());
        entity.setSgstTotal(dto.getSgstTotal());
        entity.setTotalIncGst(dto.getTotalIncGst());
        
        // Attribute mappings (maintain backward compatibility)
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
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setTotalPrice(dto.getTotalPrice());
        
        // NEW: Update GST fields
        entity.setGstType(dto.getGstType());
        entity.setSubTotal(dto.getSubTotal());
        entity.setCgstTotal(dto.getCgstTotal());
        entity.setSgstTotal(dto.getSgstTotal());
        entity.setTotalIncGst(dto.getTotalIncGst());
        
        // Don't update orderId and lineNumber during updates to maintain integrity
        // Don't update workOrderRef during updates to maintain the link
    }
    
    // Helper method to parse GST rate from selection string
    private BigDecimal parseGSTRate(String gstSelection) {
        if (gstSelection == null || gstSelection.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        try {
            // Extract percentage from strings like "18%", "IGST 18%", "CGST 9% & SGST 9%"
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\d+(?:\\.\\d+)?)");
            java.util.regex.Matcher matcher = pattern.matcher(gstSelection);
            
            if (gstSelection.contains("CGST") && gstSelection.contains("SGST")) {
                // For CGST & SGST, sum both percentages
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
