package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.OrderFabricationErection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderFabricationErectionRepository extends JpaRepository<OrderFabricationErection, Long> {
    List<OrderFabricationErection> findByErectionMkd(String erectionMkd);
    List<OrderFabricationErection> findByErectionMkdIn(List<String> erectionMkds);
}
