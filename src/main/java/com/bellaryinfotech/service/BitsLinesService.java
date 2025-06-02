package com.bellaryinfotech.service;

import java.util.List;
import java.util.Optional;

import com.bellaryinfotech.DTO.BitsLinesDto;

public interface BitsLinesService {
    
    List<BitsLinesDto> getAllLines();
    Optional<BitsLinesDto> getLineById(Long id);
    BitsLinesDto createLine(BitsLinesDto lineDto);
    BitsLinesDto updateLine(Long id, BitsLinesDto lineDto);
    void deleteLine(Long id);
    List<BitsLinesDto> searchBySerNo(String serNo);
    List<BitsLinesDto> searchByServiceCode(String serviceCode);
    List<BitsLinesDto> searchByServiceDesc(String serviceDesc);
    
    // New method to get service orders by work order
    List<BitsLinesDto> getLinesByWorkOrder(String workOrder);
    
    // Debug method
    List<BitsLinesDto> getAllLinesWithAttributes();
}