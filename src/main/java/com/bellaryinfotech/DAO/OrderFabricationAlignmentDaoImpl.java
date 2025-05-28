package com.bellaryinfotech.DAO;

import com.bellaryinfotech.DAO.OrderFabricationAlignmentDao;
import com.bellaryinfotech.model.OrderFabricationAlignment;
import com.bellaryinfotech.repo.OrderFabricationAlignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class OrderFabricationAlignmentDaoImpl implements OrderFabricationAlignmentDao {

    @Autowired
    private OrderFabricationAlignmentRepository alignmentRepository;

    @Override
    public List<OrderFabricationAlignment> findAll() {
        return alignmentRepository.findAll();
    }

    @Override
    public List<OrderFabricationAlignment> saveAll(List<OrderFabricationAlignment> alignments) {
        return alignmentRepository.saveAll(alignments);
    }
}
