package com.bellaryinfotech.service;
 
import com.bellaryinfotech.DTO.OrderFabricationDetailDTO;

import java.util.List;

public interface OrderFabricationDetailService {
    
    List<OrderFabricationDetailDTO> findAll();
    
    OrderFabricationDetailDTO findById(Long id);
    
    OrderFabricationDetailDTO save(OrderFabricationDetailDTO orderFabricationDetailDTO);
    
    OrderFabricationDetailDTO update(Long id, OrderFabricationDetailDTO orderFabricationDetailDTO);
    
    void deleteById(Long id);
    
    List<OrderFabricationDetailDTO> findByOrderId(Long orderId);
    
    List<OrderFabricationDetailDTO> findByBuildingName(String buildingName);
    
    List<OrderFabricationDetailDTO> findByDrawingNo(String drawingNo);
    
    List<OrderFabricationDetailDTO> findByOrderNumber(String orderNumber);
    
    List<OrderFabricationDetailDTO> findByErectionMkd(String erectionMkd);
    
    List<OrderFabricationDetailDTO> findByLineIdAndErectionMkd(Long lineId, String erectionMkd);
    
    List<OrderFabricationDetailDTO> findByLineNumberAndErectionMkd(String lineNumber, String erectionMkd);

    void updateStatusByErectionMkd(String erectionMkd, String status);
    
    
    

    
	 

	
	 
	
}