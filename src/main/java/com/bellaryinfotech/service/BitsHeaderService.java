package com.bellaryinfotech.service;

import java.util.List;
import java.util.Optional;

import com.bellaryinfotech.DTO.BitsHeaderDto;

public interface BitsHeaderService {
    
    List<BitsHeaderDto> getAllHeaders();
    Optional<BitsHeaderDto> getHeaderById(Long id);
    BitsHeaderDto createHeader(BitsHeaderDto headerDto);
    BitsHeaderDto updateHeader(Long id, BitsHeaderDto headerDto);
    void deleteHeader(Long id);
    List<BitsHeaderDto> searchByWorkOrder(String workOrder);
    List<BitsHeaderDto> searchByPlantLocation(String plantLocation);
    List<BitsHeaderDto> searchByDepartment(String department);
    List<BitsHeaderDto> searchByWorkLocation(String workLocation);

   
    List<String> getDistinctWorkOrders();
    List<String> getDistinctPlantLocations();
}


