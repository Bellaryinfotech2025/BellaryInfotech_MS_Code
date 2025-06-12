package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.RawMaterialEntryDTO;
import com.bellaryinfotech.model.RawMaterialEntry;
import com.bellaryinfotech.repo.RawMaterialEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    @Override
    public List<RawMaterialEntry> saveRawMaterialEntry(RawMaterialEntryDTO rawMaterialEntryDTO) {
        LOG.info("Saving raw material entry with {} work orders and {} service entries", 
                rawMaterialEntryDTO.getWorkOrders().size(), rawMaterialEntryDTO.getServiceEntries().size());
        
        List<RawMaterialEntry> savedEntries = new ArrayList<>();
        
        // Process each combination of work order and service entry
        for (RawMaterialEntryDTO.WorkOrderDTO workOrderDTO : rawMaterialEntryDTO.getWorkOrders()) {
            for (RawMaterialEntryDTO.ServiceEntryDTO serviceEntryDTO : rawMaterialEntryDTO.getServiceEntries()) {
                RawMaterialEntry entry = new RawMaterialEntry();
                
                // Set work order data
                entry.setWorkOrder(workOrderDTO.getWorkOrder());
                
                // Set service entry data
                entry.setSection(serviceEntryDTO.getSection());
                entry.setWidth(parseBigDecimal(serviceEntryDTO.getWidth()));
                entry.setLength(parseBigDecimal(serviceEntryDTO.getLength()));
                entry.setQty(parseBigDecimal(serviceEntryDTO.getQty()));
                entry.setUom(serviceEntryDTO.getUom());
                entry.setTotalWeight(parseBigDecimal(serviceEntryDTO.getTotalWeight()));
                
                // Set the new fields
                entry.setVehicleNumber(serviceEntryDTO.getVehicleNumber());
                entry.setDocumentNo(serviceEntryDTO.getDocumentNo());

                // Parse and set date fields
                if (serviceEntryDTO.getDocumentDate() != null && !serviceEntryDTO.getDocumentDate().trim().isEmpty()) {
                    try {
                        entry.setDocumentDate(LocalDate.parse(serviceEntryDTO.getDocumentDate()));
                    } catch (Exception e) {
                        LOG.warn("Error parsing document date: {}", serviceEntryDTO.getDocumentDate());
                    }
                }

                if (serviceEntryDTO.getReceivedDate() != null && !serviceEntryDTO.getReceivedDate().trim().isEmpty()) {
                    try {
                        entry.setReceivedDate(LocalDate.parse(serviceEntryDTO.getReceivedDate()));
                    } catch (Exception e) {
                        LOG.warn("Error parsing received date: {}", serviceEntryDTO.getReceivedDate());
                    }
                }
                
                // Set audit fields
                entry.setCreatedBy(rawMaterialEntryDTO.getCreatedBy());
                entry.setCreatedDate(LocalDateTime.now());
                entry.setLastUpdatedBy(rawMaterialEntryDTO.getCreatedBy());
                entry.setLastUpdatedDate(LocalDateTime.now());
                entry.setTenantId(1);
                
                // Store work order ID in attribute field for reference
                entry.setAttribute1V(workOrderDTO.getId());
                
                RawMaterialEntry savedEntry = rawMaterialEntryRepository.save(entry);
                savedEntries.add(savedEntry);
                
                LOG.info("Saved raw material entry with ID: {}", savedEntry.getId());
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
    public RawMaterialEntry updateRawMaterialEntry(Long id, RawMaterialEntry rawMaterialEntry) {
        LOG.info("Updating raw material entry with ID: {}", id);
        Optional<RawMaterialEntry> existingEntry = rawMaterialEntryRepository.findById(id);
        
        if (existingEntry.isPresent()) {
            RawMaterialEntry entry = existingEntry.get();
            
            entry.setWorkOrder(rawMaterialEntry.getWorkOrder());
            entry.setSection(rawMaterialEntry.getSection());
            entry.setWidth(rawMaterialEntry.getWidth());
            entry.setLength(rawMaterialEntry.getLength());
            entry.setQty(rawMaterialEntry.getQty());
            entry.setUom(rawMaterialEntry.getUom());
            entry.setTotalWeight(rawMaterialEntry.getTotalWeight());
            entry.setLastUpdatedBy(rawMaterialEntry.getLastUpdatedBy());
            entry.setLastUpdatedDate(LocalDateTime.now());
            
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
