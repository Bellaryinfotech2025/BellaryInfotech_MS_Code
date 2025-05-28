package com.bellaryinfotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bellaryinfotech.model.OrderDetailsHeader;
import com.bellaryinfotech.model.OrderDetailsLookup;
import com.bellaryinfotech.repo.OrderDetailsCorelookupRepository;
import com.bellaryinfotech.repo.OrderDetailsHeaderRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Primary
public class OrderDetailsServiceImpl implements OrderDetailsService {
    
    @Autowired
    private OrderDetailsCorelookupRepository lookupValueRepository;
    
    @Autowired
    private OrderDetailsHeaderRepository orderHeaderRepository;
    
    @Override
    public Map<String, List<OrderDetailsLookup>> getAllLookupValues() {
        Map<String, List<OrderDetailsLookup>> lookupValues = new HashMap<>();
        
        // Get all lookup values for different types
        lookupValues.put("orderTypes", lookupValueRepository.findByLookupTypeAndEnabledFlag("ORDER_TYPE", "Y"));
        lookupValues.put("orderCategories", lookupValueRepository.findByLookupTypeAndEnabledFlag("ORDER_CATEGORY", "Y"));
        lookupValues.put("billingFrequencies", lookupValueRepository.findByLookupTypeAndEnabledFlag("BILLING_FREQUENCY", "Y"));
        lookupValues.put("billingCycles", lookupValueRepository.findByLookupTypeAndEnabledFlag("BILLING_CYCLE", "Y"));
        
        return lookupValues;
    }
    
    @Override
    @Transactional
    public Map<String, Object> saveOrder(OrderDetailsHeader orderData) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // Set required fields if not already set
            if (orderData.getOrderVersion() == null) {
                orderData.setOrderVersion(1L);
            }
            
            if (orderData.getStatus() == null || orderData.getStatus().isEmpty()) {
                orderData.setStatus("Draft");
            }
            
            // Set creation date and other required fields
            Date currentDate = new Date();
            orderData.setCreationDate(currentDate);
            orderData.setLastUpdateDate(currentDate);
            
            // Set created by and last updated by
            Long userId = 1L; // Default user ID
            orderData.setCreatedBy(userId);
            orderData.setLastUpdatedBy(userId);
            
            // Save the order to M_ORDER_HEADER_ALL
            OrderDetailsHeader savedOrder = orderHeaderRepository.save(orderData);
            
            // Now update the order number with the generated ID if not already set
            if (savedOrder.getOrderNumber() == null || savedOrder.getOrderNumber().isEmpty() || 
                savedOrder.getOrderNumber().equals("ORD-TEMP")) {
                savedOrder.setOrderNumber("ORD-" + savedOrder.getOrderId());
                savedOrder = orderHeaderRepository.save(savedOrder);
            }
            
            // Return success response
            result.put("status", "success");
            result.put("orderId", savedOrder.getOrderId());
            result.put("orderNumber", savedOrder.getOrderNumber());
            result.put("message", "Order saved successfully");
            
        } catch (Exception e) {
            e.printStackTrace();
            
            // Return error response with detailed message
            result.put("status", "error");
            result.put("message", "Failed to save order: " + e.getMessage());
        }
        
        return result;
    }
}