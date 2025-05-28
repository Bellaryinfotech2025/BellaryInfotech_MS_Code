package com.bellaryinfotech.service;

import java.util.List;
import java.util.Map;

import com.bellaryinfotech.model.OrderDetailsHeader;
import com.bellaryinfotech.model.OrderDetailsLookup;

public interface OrderDetailsService {
    
    Map<String, List<OrderDetailsLookup>> getAllLookupValues();
    
    Map<String, Object> saveOrder(OrderDetailsHeader orderData);
}