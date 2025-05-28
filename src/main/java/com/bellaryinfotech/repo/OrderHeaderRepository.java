package com.bellaryinfotech.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.OrderHeader;

@Repository
public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
    }
