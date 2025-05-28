package com.bellaryinfotech.service;

import com.bellaryinfotech.DAO.OrderFabricationBillingDao;
import com.bellaryinfotech.DTO.OrderFabricationBillingDTO;
import com.bellaryinfotech.DTO.OrderFabricationBillingDTOImpl;
import com.bellaryinfotech.model.OrderFabricationAlignment;
import com.bellaryinfotech.model.OrderFabricationBilling;
import com.bellaryinfotech.repo.OrderFabricationAlignmentRepository;
import com.bellaryinfotech.repo.OrderFabricationBillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderFabricationBillingServiceImpl implements OrderFabricationBillingService {

    @Autowired
    private OrderFabricationBillingDao billingDao;

    @Autowired
    private OrderFabricationBillingRepository billingRepository;

    @Autowired
    private OrderFabricationAlignmentRepository alignmentRepository;

    @Override
    public List<OrderFabricationBillingDTO> getAllBillings() {
        List<OrderFabricationBilling> billings = billingDao.findAll();
        return OrderFabricationBillingDTOImpl.toDTOList(billings);
    }

    @Override
    public List<OrderFabricationBillingDTO> getLatestStoredBillings() {
        List<OrderFabricationBilling> latestBillings = billingRepository.findByIsLatestStoredTrueOrderByIdDesc();
        return OrderFabricationBillingDTOImpl.toDTOList(latestBillings);
    }

    @Override
    @Transactional
    public List<OrderFabricationBillingDTO> storeSelectedErectionMkds(List<String> erectionMkds) {
        // First, clear all previous "latest stored" flags
        billingRepository.clearAllLatestStoredFlags();

        // Find alignment records with the specified mkds
        List<OrderFabricationAlignment> alignmentRecords = alignmentRepository.findByErectionMkdIn(erectionMkds);

        if (alignmentRecords.isEmpty()) {
            return List.of(); // Return empty list if no records found
        }

        // Convert alignment records to billing records
        List<OrderFabricationBilling> billingsToSave = new ArrayList<>();
        for (OrderFabricationAlignment alignment : alignmentRecords) {
            OrderFabricationBilling billing = convertAlignmentToBilling(alignment);
            billing.setIsLatestStored(true);
            billing.setStoredAt(LocalDateTime.now());
            billingsToSave.add(billing);
        }

        // Save all records
        List<OrderFabricationBilling> saved = billingRepository.saveAll(billingsToSave);
        return OrderFabricationBillingDTOImpl.toDTOList(saved);
    }

    @Override
    @Transactional
    public void clearLatestStoredFlag() {
        billingRepository.clearAllLatestStoredFlags();
    }

    // Convert alignment record to billing record
    private OrderFabricationBilling convertAlignmentToBilling(OrderFabricationAlignment alignment) {
        OrderFabricationBilling billing = new OrderFabricationBilling();

        // Directly copy fields, as both are BigDecimal or compatible types
        billing.setBuildingName(alignment.getBuildingName());
        billing.setDrawingNo(alignment.getDrawingNo());
        billing.setDrawingDescription(alignment.getDrawingDescription());
        billing.setOrderNumber(alignment.getOrderNumber());
        billing.setOrderId(alignment.getOrderId());
        billing.setOrigLineNumber(alignment.getOrigLineNumber());
        billing.setOrigLineId(alignment.getOrigLineId());
        billing.setLineNumber(alignment.getLineNumber());
        billing.setLineId(alignment.getLineId());
        billing.setErectionMkd(alignment.getErectionMkd());
        billing.setItemNo(alignment.getItemNo());
        billing.setSection(alignment.getSection());
        billing.setLength(alignment.getLength());
        billing.setLengthUom(alignment.getLengthUom());
        billing.setQuantity(alignment.getQuantity());
        billing.setUnitPrice(alignment.getUnitPrice());
        billing.setUnitPriceUom(alignment.getUnitPriceUom());
        billing.setTotalQuantity(alignment.getTotalQuantity());
        billing.setTotalQuantityUom(alignment.getTotalQuantityUom());
        billing.setOriginalQuantity(alignment.getOriginalQuantity());
        billing.setRepeatedQty(alignment.getRepeatedQty());
        billing.setRemark(alignment.getRemark());
        billing.setTenantId(alignment.getTenantId());
        billing.setCreationDate(alignment.getCreationDate());
        billing.setCreatedBy(alignment.getCreatedBy());
        billing.setLastUpdateDate(alignment.getLastUpdateDate());
        billing.setLastUpdatedBy(alignment.getLastUpdatedBy());
        billing.setOrgId(alignment.getOrgId());
        billing.setCreatedDate(LocalDateTime.now());
        billing.setUpdatedBy(alignment.getUpdatedBy());
        billing.setUpdatedDate(alignment.getUpdatedDate());
        billing.setVersion(alignment.getVersion());
        billing.setStatus("billing"); // Set status to billing

        // Billing-specific fields
        billing.setIsLatestStored(true);
        billing.setStoredAt(LocalDateTime.now());

        return billing;
    }
}
