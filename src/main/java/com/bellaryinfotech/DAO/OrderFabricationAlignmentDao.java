package com.bellaryinfotech.DAO;

import com.bellaryinfotech.model.OrderFabricationAlignment;
import java.util.List;

public interface OrderFabricationAlignmentDao {
    List<OrderFabricationAlignment> findAll();
    List<OrderFabricationAlignment> saveAll(List<OrderFabricationAlignment> alignments);
}
