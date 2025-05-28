package com.bellaryinfotech.security;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bellaryinfotech.model.OrderHeader;
import com.bellaryinfotech.repo.OrderHeaderRepository;

@Service
public class OrderHeaderService {

    @Autowired
    private OrderHeaderRepository orderHeaderRepository;
    
    public List<OrderHeader> getAllOrders() {
        return orderHeaderRepository.findAll();
    }
    
    public Optional<OrderHeader> getOrderById(Long id) {
        return orderHeaderRepository.findById(id);
    }
    
    public OrderHeader saveOrder(OrderHeader orderHeader) {
        // Set creation date if it's a new order
        if (orderHeader.getCreationDate() == null) {
            orderHeader.setCreationDate(LocalDateTime.now());
        }
        
        // Always update the last update date
        orderHeader.setLastUpdateDate(LocalDateTime.now());
        
        return orderHeaderRepository.save(orderHeader);
    }
    
    public void deleteOrder(Long id) {
        orderHeaderRepository.deleteById(id);
    }
}

