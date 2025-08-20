package com.bellaryinfotech.serviceimpl;

 
 
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bellaryinfotech.DTO.WorkOrderOutEntryDTO;
import com.bellaryinfotech.model.WorkOrderOutEntry;
import com.bellaryinfotech.repo.BitsHeaderRepository;
import com.bellaryinfotech.repo.WorkOrderOutEntryRepository;
import com.bellaryinfotech.service.WorkOrderOutEntryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkOrderOutEntryServiceImpl implements WorkOrderOutEntryService {
    
    @Autowired
    private WorkOrderOutEntryRepository workOrderOutEntryRepository;
    
    @Autowired
    private BitsHeaderRepository bitsPoEntryHeaderRepository;
    
    @Override
    public WorkOrderOutEntryDTO saveWorkOrderOutEntry(WorkOrderOutEntryDTO workOrderOutEntryDTO) {
        try {
            List<WorkOrderOutEntry> entriesToSave = new ArrayList<>();
            
            // Get order_id from bits_po_entry_header using the selected work order
            Long orderId = getOrderIdFromWorkOrder(workOrderOutEntryDTO.getReferenceWorkOrder());
            System.out.println("Retrieved order_id: " + orderId + " for work order: " + workOrderOutEntryDTO.getReferenceWorkOrder());
            
            // Create one entry for each service order row
            if (workOrderOutEntryDTO.getServiceOrders() != null && !workOrderOutEntryDTO.getServiceOrders().isEmpty()) {
                for (Object serviceData : workOrderOutEntryDTO.getServiceOrders()) {
                    WorkOrderOutEntry entry = new WorkOrderOutEntry();
                    
                    // Copy header information to each row
                    entry.setOrderId(orderId);
                    entry.setClientName(workOrderOutEntryDTO.getClientName());
                    entry.setReferenceWorkOrder(workOrderOutEntryDTO.getReferenceWorkOrder());
                    entry.setWorkLocation(workOrderOutEntryDTO.getWorkLocation());
                    entry.setSubAgencyName(workOrderOutEntryDTO.getSubAgencyName());
                    entry.setSubAgencyNumber(workOrderOutEntryDTO.getSubAgencyNumber());
                    entry.setSubAgencyWorkOrderName(workOrderOutEntryDTO.getSubAgencyWorkOrderName());
                    entry.setWorkOrderDate(workOrderOutEntryDTO.getWorkOrderDate());
                    entry.setCompletionDate(workOrderOutEntryDTO.getCompletionDate());
                    entry.setMaterialType(workOrderOutEntryDTO.getMaterialType());
                    entry.setScrapAllowanceVisiblePercent(workOrderOutEntryDTO.getScrapAllowanceVisiblePercent());
                    entry.setScrapAllowanceInvisiblePercent(workOrderOutEntryDTO.getScrapAllowanceInvisiblePercent());
                    entry.setLdApplicable(workOrderOutEntryDTO.getLdApplicable());
                    entry.setGstSelection(workOrderOutEntryDTO.getGstSelection());
                    entry.setRcmApplicable(workOrderOutEntryDTO.getRcmApplicable());
                    
                    // Copy GST calculation information to each row
                    entry.setSubtotal(workOrderOutEntryDTO.getSubtotal());
                    entry.setCgstRate(workOrderOutEntryDTO.getCgstRate());
                    entry.setSgstRate(workOrderOutEntryDTO.getSgstRate());
                    entry.setIgstRate(workOrderOutEntryDTO.getIgstRate());
                    entry.setCgstAmount(workOrderOutEntryDTO.getCgstAmount());
                    entry.setSgstAmount(workOrderOutEntryDTO.getSgstAmount());
                    entry.setIgstAmount(workOrderOutEntryDTO.getIgstAmount());
                    entry.setTotalAmount(workOrderOutEntryDTO.getTotalAmount());
                    
                    // Handle service data with proper type conversion
                    if (serviceData instanceof Map) {
                        Map<String, Object> serviceMap = (Map<String, Object>) serviceData;
                        
                        // Safe string conversion with null checks
                        entry.setSerNo(convertToString(serviceMap.get("serNo")));
                        entry.setServiceCode(convertToString(serviceMap.get("serviceCode")));
                        entry.setServiceDesc(convertToString(serviceMap.get("serviceDesc")));
                        entry.setQty(convertToString(serviceMap.get("qty")));
                        entry.setUom(convertToString(serviceMap.get("uom")));
                        entry.setUnitPrice(convertToString(serviceMap.get("rate")));
                        entry.setTotalPrice(convertToString(serviceMap.get("amount")));
                        
                        System.out.println("Processing service: " + entry.getServiceCode() + " - " + entry.getServiceDesc());
                    }
                    
                    entriesToSave.add(entry);
                }
            } else {
                // If no service orders, create a single entry with header data only
                WorkOrderOutEntry entry = new WorkOrderOutEntry();
                BeanUtils.copyProperties(workOrderOutEntryDTO, entry, "id", "serviceOrders");
                entry.setOrderId(orderId);
                entriesToSave.add(entry);
            }
            
            // Save all entries
            System.out.println("Saving " + entriesToSave.size() + " work order out entries");
            List<WorkOrderOutEntry> savedEntries = workOrderOutEntryRepository.saveAll(entriesToSave);
            System.out.println("Successfully saved " + savedEntries.size() + " entries");
            
            // Return the first entry as DTO (for response)
            if (!savedEntries.isEmpty()) {
                return convertEntityToDTO(savedEntries.get(0));
            }
            
            return workOrderOutEntryDTO;
        } catch (Exception e) {
            System.err.println("Error in saveWorkOrderOutEntry: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error saving work order out entry: " + e.getMessage(), e);
        }
    }
    
    // Helper method to safely convert any object to String
    private String convertToString(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }
    
    @Override
    public WorkOrderOutEntryDTO updateWorkOrderOutEntry(Long id, WorkOrderOutEntryDTO workOrderOutEntryDTO) {
        try {
            // First, delete all existing entries for this work order
            workOrderOutEntryRepository.deleteByReferenceWorkOrder(workOrderOutEntryDTO.getReferenceWorkOrder());
            
            // Then save new entries (same as save operation)
            return saveWorkOrderOutEntry(workOrderOutEntryDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error updating work order out entry: " + e.getMessage(), e);
        }
    }
    
    @Override
    public WorkOrderOutEntryDTO getWorkOrderOutEntryById(Long id) {
        try {
            WorkOrderOutEntry workOrderOutEntry = workOrderOutEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Work order out entry not found with id: " + id));
            return convertEntityToDTO(workOrderOutEntry);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching work order out entry: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<WorkOrderOutEntryDTO> getAllWorkOrderOutEntries() {
        try {
            // Get unique work orders with their totals for summary view
            List<Object[]> uniqueWorkOrders = workOrderOutEntryRepository.findUniqueWorkOrdersWithTotals();
            
            List<WorkOrderOutEntryDTO> result = new ArrayList<>();
            for (Object[] row : uniqueWorkOrders) {
                WorkOrderOutEntryDTO dto = new WorkOrderOutEntryDTO();
                dto.setReferenceWorkOrder((String) row[0]);
                dto.setClientName((String) row[1]);
                dto.setWorkLocation((String) row[2]);
                dto.setCompletionDate((java.time.LocalDate) row[3]);
                dto.setLdApplicable((Boolean) row[4]);
                dto.setTotalAmount((Double) row[5]);
                dto.setOrderId((Long) row[6]);
                dto.setCreatedDate((java.time.LocalDateTime) row[7]);
                result.add(dto);
            }
            
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all work order out entries: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void deleteWorkOrderOutEntry(Long id) {
        try {
            // Get the work order entry to find the reference work order
            WorkOrderOutEntry entry = workOrderOutEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Work order out entry not found with id: " + id));
            
            // Delete all entries with the same reference work order
            workOrderOutEntryRepository.deleteByReferenceWorkOrder(entry.getReferenceWorkOrder());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting work order out entry: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<WorkOrderOutEntryDTO> searchByClientName(String clientName) {
        try {
            List<WorkOrderOutEntry> entries = workOrderOutEntryRepository.findByClientNameContainingIgnoreCase(clientName);
            return entries.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error searching by client name: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<WorkOrderOutEntryDTO> searchByReferenceWorkOrder(String referenceWorkOrder) {
        try {
            List<WorkOrderOutEntry> entries = workOrderOutEntryRepository.findByReferenceWorkOrderContainingIgnoreCase(referenceWorkOrder);
            return entries.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error searching by reference work order: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<WorkOrderOutEntryDTO> searchByWorkLocation(String workLocation) {
        try {
            List<WorkOrderOutEntry> entries = workOrderOutEntryRepository.findByWorkLocationContainingIgnoreCase(workLocation);
            return entries.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error searching by work location: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<WorkOrderOutEntryDTO> searchBySubAgencyName(String subAgencyName) {
        try {
            List<WorkOrderOutEntry> entries = workOrderOutEntryRepository.findBySubAgencyNameContainingIgnoreCase(subAgencyName);
            return entries.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error searching by sub agency name: " + e.getMessage(), e);
        }
    }
    
    // Get service lines for a specific work order (for editing)
    public List<WorkOrderOutEntryDTO> getServiceLinesByWorkOrder(String referenceWorkOrder) {
        try {
            List<WorkOrderOutEntry> entries = workOrderOutEntryRepository.findByReferenceWorkOrder(referenceWorkOrder);
            return entries.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching service lines by work order: " + e.getMessage(), e);
        }
    }
    
    // Helper method to get order_id from bits_po_entry_header
    private Long getOrderIdFromWorkOrder(String workOrder) {
        try {
            System.out.println("Fetching order_id for work order: " + workOrder);
            
            // Try the main query first
            Long orderId = bitsPoEntryHeaderRepository.findOrderIdByWorkOrder(workOrder);
            
            if (orderId == null) {
                System.out.println("Main query returned null, trying native query...");
                // Try native query as fallback
                orderId = bitsPoEntryHeaderRepository.findOrderIdByWorkOrderNative(workOrder);
            }
            
            if (orderId == null) {
                System.out.println("Both queries returned null for work order: " + workOrder);
                // Return a default value or handle as needed
                return 0L;
            }
            
            System.out.println("Successfully retrieved order_id: " + orderId);
            return orderId;
        } catch (Exception e) {
            System.err.println("Error fetching order_id for work order: " + workOrder + " - " + e.getMessage());
            e.printStackTrace();
            return 0L; // Return default value instead of null
        }
    }
    
    // Helper method for conversion
    private WorkOrderOutEntryDTO convertEntityToDTO(WorkOrderOutEntry entity) {
        WorkOrderOutEntryDTO dto = new WorkOrderOutEntryDTO();
        BeanUtils.copyProperties(entity, dto, "serviceOrders");
        return dto;
    }
}
