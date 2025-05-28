package com.bellaryinfotech.DAO;

import com.bellaryinfotech.DAO.OrderFabricationBillingDao;
import com.bellaryinfotech.model.OrderFabricationBilling;
import com.bellaryinfotech.repo.OrderFabricationBillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class OrderFabricationBillingDaoImpl implements OrderFabricationBillingDao {

    @Autowired
    private OrderFabricationBillingRepository billingRepository;

    @Override
    public List<OrderFabricationBilling> findAll() {
        return billingRepository.findAll();
    }

    @Override
    public List<OrderFabricationBilling> saveAll(List<OrderFabricationBilling> billings) {
        return billingRepository.saveAll(billings);
    }
}
