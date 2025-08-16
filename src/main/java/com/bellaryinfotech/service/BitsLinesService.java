package com.bellaryinfotech.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.bellaryinfotech.DTO.BitsLinesDto;

public interface BitsLinesService {
    
    List<BitsLinesDto> getAllLines();
    Optional<BitsLinesDto> getLineById(Long id);
    
     
    BitsLinesDto createLine(BitsLinesDto lineDto, Long orderId);
    BitsLinesDto createLine(BitsLinesDto lineDto);  
    
    BitsLinesDto updateLine(Long id, BitsLinesDto lineDto);
    void deleteLine(Long id);
    
    // Search methods
    List<BitsLinesDto> searchBySerNo(String serNo);
    List<BitsLinesDto> searchByServiceCode(String serviceCode);
    List<BitsLinesDto> searchByServiceDesc(String serviceDesc);
    
    
    List<BitsLinesDto> getLinesByOrderId(Long orderId);
    List<BitsLinesDto> getLinesByWorkOrder(String workOrder);
    
     
    List<BitsLinesDto> createMultipleLines(List<BitsLinesDto> lineDtos, Long orderId);
    
    
    List<BitsLinesDto> getAllLinesWithAttributes();
    
     
    List<String> getDistinctSerialNumbers();
    
  
    List<BitsLinesDto> saveAssignedLevels(List<Map<String, Object>> assignmentData);
    List<Map<String, Object>> getWorkOrdersWithAssignments();
}
