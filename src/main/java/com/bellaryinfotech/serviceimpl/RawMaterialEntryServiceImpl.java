package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.DTO.RawMaterialEntryDTO;
import com.bellaryinfotech.model.RawMaterialEntry;
import com.bellaryinfotech.repo.RawMaterialEntryRepository;
import com.bellaryinfotech.service.RawMaterialEntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RawMaterialEntryServiceImpl implements RawMaterialEntryService {

    private static final Logger LOG = LoggerFactory.getLogger(RawMaterialEntryServiceImpl.class);

    @Autowired
    private RawMaterialEntryRepository rawMaterialEntryRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${workorder.api.base.url:http://195.35.45.56:5522}")
    private String workOrderApiBaseUrl;

    private Long getOrderIdByWorkOrder(String workOrder) {
        if (workOrder == null || workOrder.trim().isEmpty()) {
            LOG.warn("Work order is null or empty");
            return null;
        }
        try {
            String url = workOrderApiBaseUrl + "/api/V2.0/getworkorder/number/" + workOrder;
            Object response = restTemplate.getForObject(url, Object.class);
            if (response != null && response instanceof java.util.Map) {
                java.util.Map<String, Object> responseMap = (java.util.Map<String, Object>) response;
                Object orderIdObj = responseMap.get("orderId");
                if (orderIdObj != null) {
                    if (orderIdObj instanceof Number) {
                        return ((Number) orderIdObj).longValue();
                    } else if (orderIdObj instanceof String) {
                        return Long.parseLong((String) orderIdObj);
                    }
                }
            }
            LOG.warn("Could not find orderId for work order: {}", workOrder);
            return null;
        } catch (Exception e) {
            LOG.error("Error fetching orderId for work order: {}", workOrder, e);
            return null;
        }
    }

    @Override
    public List<RawMaterialEntry> saveRawMaterialEntry(RawMaterialEntryDTO rawMaterialEntryDTO) {
        LOG.info("Saving raw material entry with {} work orders and {} service entries",
                rawMaterialEntryDTO.getWorkOrders().size(), rawMaterialEntryDTO.getServiceEntries().size());

        // Validate input
        if (rawMaterialEntryDTO.getWorkOrders() == null || rawMaterialEntryDTO.getWorkOrders().isEmpty()) {
            throw new IllegalArgumentException("At least one work order is required");
        }
        if (rawMaterialEntryDTO.getServiceEntries() == null || rawMaterialEntryDTO.getServiceEntries().isEmpty()) {
            throw new IllegalArgumentException("At least one service entry is required");
        }

        List<RawMaterialEntry> savedEntries = new ArrayList<>();
        for (RawMaterialEntryDTO.WorkOrderDTO workOrderDTO : rawMaterialEntryDTO.getWorkOrders()) {
            if (workOrderDTO.getWorkOrder() == null || workOrderDTO.getWorkOrder().trim().isEmpty()) {
                throw new IllegalArgumentException("Work order cannot be null or empty");
            }
            Long orderId = getOrderIdByWorkOrder(workOrderDTO.getWorkOrder());
            for (RawMaterialEntryDTO.ServiceEntryDTO serviceEntryDTO : rawMaterialEntryDTO.getServiceEntries()) {
                if (serviceEntryDTO.getSectionCode() == null || serviceEntryDTO.getSectionCode().trim().isEmpty()) {
                    throw new IllegalArgumentException("Section code cannot be null or empty");
                }
                if (serviceEntryDTO.getQty() == null || serviceEntryDTO.getQty().trim().isEmpty()) {
                    throw new IllegalArgumentException("Quantity cannot be null or empty");
                }
                if (serviceEntryDTO.getVehicleNumber() == null || serviceEntryDTO.getVehicleNumber().trim().isEmpty()) {
                    throw new IllegalArgumentException("Vehicle number cannot be null or empty");
                }
                RawMaterialEntry entry = new RawMaterialEntry();
                entry.setWorkOrder(workOrderDTO.getWorkOrder());
                entry.setOrderId(orderId);
                entry.setSection(serviceEntryDTO.getSection());
                entry.setSectionCode(serviceEntryDTO.getSectionCode());
                entry.setMaterialCode(serviceEntryDTO.getMaterialCode());
                entry.setWidth(parseBigDecimal(serviceEntryDTO.getWidth()));
                entry.setLength(parseBigDecimal(serviceEntryDTO.getLength()));
                entry.setQty(parseBigDecimal(serviceEntryDTO.getQty()));
                entry.setUom(serviceEntryDTO.getUom() != null ? serviceEntryDTO.getUom() : "KG");
                entry.setTotalWeight(parseBigDecimal(serviceEntryDTO.getTotalWeight()));
                entry.setTotalReceived(parseBigDecimal(serviceEntryDTO.getTotalReceived()));
                entry.setReceiverName(serviceEntryDTO.getReceiverName() != null && !serviceEntryDTO.getReceiverName().trim().isEmpty() ? serviceEntryDTO.getReceiverName().trim() : "No data exists");
                entry.setTotalTransferredToOther(serviceEntryDTO.getTotalTransferredToOther() != null && !serviceEntryDTO.getTotalTransferredToOther().trim().isEmpty() ? serviceEntryDTO.getTotalTransferredToOther().trim() : "No data exists");
                entry.setRemarks(serviceEntryDTO.getRemarks() != null && !serviceEntryDTO.getRemarks().trim().isEmpty() ? serviceEntryDTO.getRemarks().trim() : "");
                entry.setVehicleNumber(serviceEntryDTO.getVehicleNumber().trim().isEmpty() ? "Not provided" : serviceEntryDTO.getVehicleNumber().trim());
                entry.setDocumentNo(serviceEntryDTO.getDocumentNo());
                if (serviceEntryDTO.getDocumentDate() != null && !serviceEntryDTO.getDocumentDate().trim().isEmpty()) {
                    try {
                        entry.setDocumentDate(LocalDate.parse(serviceEntryDTO.getDocumentDate()));
                    } catch (Exception e) {
                        LOG.warn("Error parsing document date: {}", serviceEntryDTO.getDocumentDate());
                        entry.setDocumentDate(null);
                    }
                }
                if (serviceEntryDTO.getReceivedDate() != null && !serviceEntryDTO.getReceivedDate().trim().isEmpty()) {
                    try {
                        entry.setReceivedDate(LocalDate.parse(serviceEntryDTO.getReceivedDate()));
                    } catch (Exception e) {
                        LOG.warn("Error parsing received date: {}", serviceEntryDTO.getReceivedDate());
                        entry.setReceivedDate(null);
                    }
                }
                entry.setReceiptType(serviceEntryDTO.getReceiptType() != null ? serviceEntryDTO.getReceiptType() : "");
                entry.setCreatedBy(rawMaterialEntryDTO.getCreatedBy() != null ? rawMaterialEntryDTO.getCreatedBy() : "system");
                entry.setCreatedDate(LocalDateTime.now());
                entry.setLastUpdatedBy(rawMaterialEntryDTO.getCreatedBy() != null ? rawMaterialEntryDTO.getCreatedBy() : "system");
                entry.setLastUpdatedDate(LocalDateTime.now());
                entry.setTenantId(1);
                RawMaterialEntry savedEntry = rawMaterialEntryRepository.save(entry);
                savedEntries.add(savedEntry);
                LOG.info("Saved raw material entry with ID: {} and orderId: {}", savedEntry.getId(), savedEntry.getOrderId());
            }
        }
        LOG.info("Successfully saved {} raw material entries", savedEntries.size());
        return savedEntries;
    }

    @Override
    public List<RawMaterialEntry> getAllRawMaterialEntries() {
        LOG.info("Fetching all raw material entries");
        List<RawMaterialEntry> entries = rawMaterialEntryRepository.findAllOrderByCreatedDateDesc();
        LOG.info("Found {} raw material entries", entries.size());
        return entries;
    }

    @Override
    public RawMaterialEntry getRawMaterialEntryById(Long id) {
        LOG.info("Fetching raw material entry by ID: {}", id);
        Optional<RawMaterialEntry> entry = rawMaterialEntryRepository.findById(id);
        if (entry.isPresent()) {
            LOG.info("Found raw material entry with ID: {}", id);
            return entry.get();
        } else {
            LOG.warn("Raw material entry not found with ID: {}", id);
            return null;
        }
    }

    @Override
    public List<RawMaterialEntry> getRawMaterialEntriesByWorkOrder(String workOrder) {
        LOG.info("Fetching raw material entries by work order: {}", workOrder);
        List<RawMaterialEntry> entries = rawMaterialEntryRepository.findByWorkOrder(workOrder);
        LOG.info("Found {} raw material entries for work order: {}", entries.size(), workOrder);
        return entries;
    }

    @Override
    public List<RawMaterialEntry> getRawMaterialEntriesBySection(String section) {
        LOG.info("Fetching raw material entries by section: {}", section);
        List<RawMaterialEntry> entries = rawMaterialEntryRepository.findBySection(section);
        LOG.info("Found {} raw material entries for section: {}", entries.size(), section);
        return entries;
    }

    @Override
    public List<RawMaterialEntry> getRawMaterialEntriesBySectionCode(String sectionCode) {
        LOG.info("Fetching raw material entries by section code: {}", sectionCode);
        List<RawMaterialEntry> entries = rawMaterialEntryRepository.findBySectionCode(sectionCode);
        LOG.info("Found {} raw material entries for section code: {}", entries.size(), sectionCode);
        return entries;
    }

    @Override
    public List<RawMaterialEntry> getRawMaterialEntriesByReceiptType(String receiptType) {
        LOG.info("Fetching raw material entries by receipt type: {}", receiptType);
        List<RawMaterialEntry> entries = rawMaterialEntryRepository.findByReceiptType(receiptType);
        LOG.info("Found {} raw material entries for receipt type: {}", entries.size(), receiptType);
        return entries;
    }

    @Override
    public RawMaterialEntry updateRawMaterialEntry(Long id, RawMaterialEntry rawMaterialEntry) {
        LOG.info("Updating raw material entry with ID: {}", id);
        Optional<RawMaterialEntry> existingEntry = rawMaterialEntryRepository.findById(id);

        if (existingEntry.isPresent()) {
            RawMaterialEntry entry = existingEntry.get();
            entry.setWorkOrder(rawMaterialEntry.getWorkOrder());
            if (!entry.getWorkOrder().equals(rawMaterialEntry.getWorkOrder())) {
                Long newOrderId = getOrderIdByWorkOrder(rawMaterialEntry.getWorkOrder());
                entry.setOrderId(newOrderId);
            }
            entry.setSection(rawMaterialEntry.getSection());
            entry.setSectionCode(rawMaterialEntry.getSectionCode());
            entry.setMaterialCode(rawMaterialEntry.getMaterialCode());
            entry.setWidth(rawMaterialEntry.getWidth());
            entry.setLength(rawMaterialEntry.getLength());
            entry.setQty(rawMaterialEntry.getQty());
            entry.setUom(rawMaterialEntry.getUom());
            entry.setTotalWeight(rawMaterialEntry.getTotalWeight());
            entry.setTotalReceived(rawMaterialEntry.getTotalReceived());
            entry.setReceiverName(rawMaterialEntry.getReceiverName() != null && !rawMaterialEntry.getReceiverName().trim().isEmpty() ? rawMaterialEntry.getReceiverName().trim() : "No data exists");
            entry.setTotalTransferredToOther(rawMaterialEntry.getTotalTransferredToOther() != null && !rawMaterialEntry.getTotalTransferredToOther().trim().isEmpty() ? rawMaterialEntry.getTotalTransferredToOther().trim() : "No data exists");
            entry.setRemarks(rawMaterialEntry.getRemarks() != null && !rawMaterialEntry.getRemarks().trim().isEmpty() ? rawMaterialEntry.getRemarks().trim() : "");
            entry.setVehicleNumber(rawMaterialEntry.getVehicleNumber() != null && !rawMaterialEntry.getVehicleNumber().trim().isEmpty() ? rawMaterialEntry.getVehicleNumber().trim() : "Not provided");
            entry.setDocumentNo(rawMaterialEntry.getDocumentNo());
            entry.setDocumentDate(rawMaterialEntry.getDocumentDate());
            entry.setReceivedDate(rawMaterialEntry.getReceivedDate());
            entry.setLastUpdatedBy(rawMaterialEntry.getLastUpdatedBy());
            entry.setLastUpdatedDate(LocalDateTime.now());
            entry.setReceiptType(rawMaterialEntry.getReceiptType());
            RawMaterialEntry updatedEntry = rawMaterialEntryRepository.save(entry);
            LOG.info("Successfully updated raw material entry with ID: {}", id);
            return updatedEntry;
        } else {
            LOG.warn("Raw material entry not found with ID: {}", id);
            return null;
        }
    }

    @Override
    public void deleteRawMaterialEntry(Long id) {
        LOG.info("Deleting raw material entry with ID: {}", id);
        rawMaterialEntryRepository.deleteById(id);
        LOG.info("Successfully deleted raw material entry with ID: {}", id);
    }

    @Override
    public List<String> getDistinctWorkOrders() {
        LOG.info("Fetching distinct work orders");
        List<String> workOrders = rawMaterialEntryRepository.findDistinctWorkOrders();
        LOG.info("Found {} distinct work orders", workOrders.size());
        return workOrders;
    }

    @Override
    public List<String> getDistinctSections() {
        LOG.info("Fetching distinct sections");
        List<String> sections = rawMaterialEntryRepository.findDistinctSections();
        LOG.info("Found {} distinct sections", sections.size());
        return sections;
    }

    @Override
    public List<String> getDistinctSectionCodes() {
        LOG.info("Fetching distinct section codes");
        List<String> sectionCodes = rawMaterialEntryRepository.findDistinctSectionCodes();
        LOG.info("Found {} distinct section codes", sectionCodes.size());
        return sectionCodes;
    }

    @Override
    public List<String> getDistinctUoms() {
        LOG.info("Fetching distinct UOMs");
        List<String> uoms = rawMaterialEntryRepository.findDistinctUoms();
        LOG.info("Found {} distinct UOMs", uoms.size());
        return uoms;
    }

    private BigDecimal parseBigDecimal(String value) {
        try {
            return value != null && !value.trim().isEmpty() ? new BigDecimal(value) : BigDecimal.ZERO;
        } catch (NumberFormatException e) {
            LOG.warn("Error parsing BigDecimal value: {}", value);
            return BigDecimal.ZERO;
        }
    }
}