package com.bellaryinfotech.DAO;

import com.bellaryinfotech.model.OrderLineDetails;
import com.bellaryinfotech.repo.OrderLineDetailsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class OrderLineDetailsDAOImpl implements OrderLineDetailsDAO {

    @Autowired
    private OrderLineDetailsRepository repository;

    @Override
    public List<OrderLineDetails> findByOrderId(BigDecimal orderId) {
        return repository.findByOrderId(orderId);
    }

    @Override
    public OrderLineDetails save(OrderLineDetails entity) {
        return repository.save(entity);
    }
}
