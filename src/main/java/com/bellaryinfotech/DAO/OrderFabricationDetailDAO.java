package com.bellaryinfotech.DAO;
 
import com.bellaryinfotech.model.OrderFabricationDetail;

import java.util.List;
import java.util.Optional;

public interface OrderFabricationDetailDAO {
    
    List<OrderFabricationDetail> findAll();
    
    Optional<OrderFabricationDetail> findById(Long id);
    
    OrderFabricationDetail save(OrderFabricationDetail orderFabricationDetail);
    
    void deleteById(Long id);
    
    List<OrderFabricationDetail> findByOrderId(Long orderId);
    
    List<OrderFabricationDetail> findByBuildingName(String buildingName);
    
    List<OrderFabricationDetail> findByDrawingNo(String drawingNo);
    
    List<OrderFabricationDetail> findByOrderNumber(String orderNumber);
    
    List<OrderFabricationDetail> findByErectionMkd(String erectionMkd);
    
    List<OrderFabricationDetail> findByLineIdAndErectionMkd(Long lineId, String erectionMkd);
}