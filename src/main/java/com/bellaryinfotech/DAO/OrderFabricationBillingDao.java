package com.bellaryinfotech.DAO;

import com.bellaryinfotech.model.OrderFabricationBilling;
import java.util.List;

public interface OrderFabricationBillingDao {
    List<OrderFabricationBilling> findAll();
    List<OrderFabricationBilling> saveAll(List<OrderFabricationBilling> billings);
}
