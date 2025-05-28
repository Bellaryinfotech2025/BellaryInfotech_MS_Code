package com.bellaryinfotech.DAO;
 
import com.bellaryinfotech.model.OrderFabricationDetail;
import com.bellaryinfotech.repo.OrderFabricationDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderFabricationDetailDAOImpl implements OrderFabricationDetailDAO {
    
    private final OrderFabricationDetailRepository orderFabricationDetailRepository;
    
    @Autowired
    public OrderFabricationDetailDAOImpl(OrderFabricationDetailRepository orderFabricationDetailRepository) {
        this.orderFabricationDetailRepository = orderFabricationDetailRepository;
    }
    
    @Override
    public List<OrderFabricationDetail> findAll() {
        return orderFabricationDetailRepository.findAll();
    }
    
    @Override
    public Optional<OrderFabricationDetail> findById(Long id) {
        return orderFabricationDetailRepository.findById(id);
    }
    
    @Override
    public OrderFabricationDetail save(OrderFabricationDetail orderFabricationDetail) {
        return orderFabricationDetailRepository.save(orderFabricationDetail);
    }
    
    @Override
    public void deleteById(Long id) {
        orderFabricationDetailRepository.deleteById(id);
    }
    
    @Override
    public List<OrderFabricationDetail> findByOrderId(Long orderId) {
        return orderFabricationDetailRepository.findByOrderId(orderId);
    }
    
    @Override
    public List<OrderFabricationDetail> findByBuildingName(String buildingName) {
        return orderFabricationDetailRepository.findByBuildingNameContainingIgnoreCase(buildingName);
    }
    
    @Override
    public List<OrderFabricationDetail> findByDrawingNo(String drawingNo) {
        return orderFabricationDetailRepository.findByDrawingNoContainingIgnoreCase(drawingNo);
    }
    
    @Override
    public List<OrderFabricationDetail> findByOrderNumber(String orderNumber) {
        return orderFabricationDetailRepository.findByOrderNumber(orderNumber);
    }
    
    @Override
    public List<OrderFabricationDetail> findByErectionMkd(String erectionMkd) {
        return orderFabricationDetailRepository.findByErectionMkdContainingIgnoreCase(erectionMkd);
    }
    
    @Override
    public List<OrderFabricationDetail> findByLineIdAndErectionMkd(Long lineId, String erectionMkd) {
        return orderFabricationDetailRepository.findByLineIdAndErectionMkdContainingIgnoreCase(lineId, erectionMkd);
    }
}