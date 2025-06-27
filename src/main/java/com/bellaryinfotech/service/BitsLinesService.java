package com.bellaryinfotech.service;

import java.util.List;
import java.util.Optional;

import com.bellaryinfotech.DTO.BitsLinesDto;

public interface BitsLinesService {
    
    List<BitsLinesDto> getAllLines();
    Optional<BitsLinesDto> getLineById(Long id);
    
    // ENHANCED: Create line with automatic order_id and line_number assignment
    BitsLinesDto createLine(BitsLinesDto lineDto, Long orderId);
    BitsLinesDto createLine(BitsLinesDto lineDto); // Legacy method for backward compatibility
    
    BitsLinesDto updateLine(Long id, BitsLinesDto lineDto);
    void deleteLine(Long id);
    
    // Search methods
    List<BitsLinesDto> searchBySerNo(String serNo);
    List<BitsLinesDto> searchByServiceCode(String serviceCode);
    List<BitsLinesDto> searchByServiceDesc(String serviceDesc);
    
    // Enhanced methods using proper foreign key relationship
    List<BitsLinesDto> getLinesByOrderId(Long orderId);
    List<BitsLinesDto> getLinesByWorkOrder(String workOrder);
    
    // Bulk operations for better performance
    List<BitsLinesDto> createMultipleLines(List<BitsLinesDto> lineDtos, Long orderId);
    
    // Debug method
    List<BitsLinesDto> getAllLinesWithAttributes();
    
    // NEW: Method to get distinct serial numbers
    List<String> getDistinctSerialNumbers();
}
