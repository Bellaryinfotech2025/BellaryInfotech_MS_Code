package com.bellaryinfotech.service;

import java.math.BigDecimal;
import java.util.List;

import com.bellaryinfotech.DTO.OrderLineDetailsDTO;

public interface OrderLineDetailsService {
    List<OrderLineDetailsDTO> getDetailsByOrderId(BigDecimal orderId);
    OrderLineDetailsDTO saveOrderLineDetail(OrderLineDetailsDTO dto);
}
