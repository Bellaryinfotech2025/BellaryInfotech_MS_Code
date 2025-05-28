package com.bellaryinfotech.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.OrderDetailsHeader;
 

@Repository
public interface OrderDetailsHeaderRepository extends JpaRepository<OrderDetailsHeader, Long> {
}
