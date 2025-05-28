package com.bellaryinfotech.repo;

 

import com.bellaryinfotech.model.OrderLineDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface OrderLineDetailsRepository extends JpaRepository<OrderLineDetails, Integer> {
    List<OrderLineDetails> findByOrderId(BigDecimal orderId);
}
