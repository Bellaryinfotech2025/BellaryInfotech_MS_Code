package com.bellaryinfotech.DAO;

import com.bellaryinfotech.model.OrderLineDetails;

import java.math.BigDecimal;
import java.util.List;

public interface OrderLineDetailsDAO {
    List<OrderLineDetails> findByOrderId(BigDecimal orderId);
    OrderLineDetails save(OrderLineDetails entity);
}

