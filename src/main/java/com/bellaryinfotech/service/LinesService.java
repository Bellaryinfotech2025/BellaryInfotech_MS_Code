package com.bellaryinfotech.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bellaryinfotech.model.LinesModel;
import com.bellaryinfotech.repo.LinesRepository;

import java.util.List;
import java.util.Date;

@Service
public class LinesService {

    @Autowired
    private LinesRepository orderLineRepository;

    public List<LinesModel> getLinesByOrderId(Long orderId) {
        return orderLineRepository.findByOrderIdOrderByIsParentDescLineNumber(orderId);
    }

    public LinesModel saveLine(LinesModel line) {
        // Set creation date if not set
        if (line.getCreationDate() == null) {
            line.setCreationDate(new Date());
        }
        
        // Set last update date
        line.setLastUpdateDate(new Date());
        
        return orderLineRepository.save(line);
    }

    public List<LinesModel> getParentLines(Long orderId) {
        return orderLineRepository.findByOrderIdAndIsParentTrue(orderId);
    }
}
