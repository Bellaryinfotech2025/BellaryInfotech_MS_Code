package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.OrderFabricationAlignmentDTO;
import com.bellaryinfotech.model.OrderFabricationAlignment;
import com.bellaryinfotech.repo.OrderFabricationAlignmentRepository;
import com.bellaryinfotech.repo.OrderFabricationErectionRepository;
import com.bellaryinfotech.model.OrderFabricationErection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
public class OrderFabricationAlignmentServiceImpl implements OrderFabricationAlignmentService {

    @Autowired
    private OrderFabricationAlignmentRepository alignmentRepository;

    @Autowired
    private OrderFabricationErectionRepository erectionRepository;

    // Get all alignment records
    public List<OrderFabricationAlignmentDTO> getAllAlignments() {
        List<OrderFabricationAlignment> alignments = alignmentRepository.findAllByOrderByIdDesc();
        return alignments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get only the latest stored alignment records
    public List<OrderFabricationAlignmentDTO> getLatestStoredAlignments() {
        List<OrderFabricationAlignment> latestAlignments = alignmentRepository.findByIsLatestStoredTrueOrderByIdDesc();
        return latestAlignments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // NEW: Get all distinct erection mkds from alignment table
    public List<String> getAllDistinctErectionMkds() {
        try {
            System.out.println("Service: Fetching all distinct erection mkds from alignment table...");
            
            // First, let's get all alignment records to see what we have
            List<OrderFabricationAlignment> allAlignments = alignmentRepository.findAll();
            System.out.println("Service: Total alignment records in database: " + allAlignments.size());
            
            // Log some sample data
            for (int i = 0; i < Math.min(5, allAlignments.size()); i++) {
                OrderFabricationAlignment alignment = allAlignments.get(i);
                System.out.println("Service: Sample record " + (i+1) + " - ID: " + alignment.getId() + 
                                 ", ErectionMkd: '" + alignment.getErectionMkd() + "'");
            }
            
            // Now get distinct erection mkds
            List<String> distinctMkds = alignmentRepository.findDistinctErectionMkds();
            System.out.println("Service: Found " + distinctMkds.size() + " distinct erection mkds");
            System.out.println("Service: Distinct mkds: " + distinctMkds);
            
            // If the query doesn't work, let's try a manual approach
            if (distinctMkds.isEmpty() && !allAlignments.isEmpty()) {
                System.out.println("Service: Query returned empty, trying manual approach...");
                distinctMkds = allAlignments.stream()
                    .map(OrderFabricationAlignment::getErectionMkd)
                    .filter(mkd -> mkd != null && !mkd.trim().isEmpty())
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
                System.out.println("Service: Manual approach found " + distinctMkds.size() + " distinct mkds: " + distinctMkds);
            }
            
            return distinctMkds;
        } catch (Exception e) {
            System.err.println("Service: Error fetching distinct erection mkds: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // Return empty list on error
        }
    }

    // Get alignment records by specific erection mkds
    public List<OrderFabricationAlignmentDTO> getAlignmentsByErectionMkds(List<String> erectionMkds) {
        List<OrderFabricationAlignment> alignments = alignmentRepository.findByErectionMkdInOrderByIdDesc(erectionMkds);
        return alignments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Store selected erection mkds and mark them as latest
    @Transactional
    public List<OrderFabricationAlignmentDTO> storeSelectedErectionMkds(List<String> erectionMkds) {
        // First, clear all previous "latest stored" flags
        alignmentRepository.clearAllLatestStoredFlags();

        // Find erection records with the specified mkds
        List<OrderFabricationErection> erectionRecords = erectionRepository.findByErectionMkdIn(erectionMkds);
        
        if (erectionRecords.isEmpty()) {
            return List.of(); // Return empty list if no records found
        }

        // Convert and store as alignment records
        List<OrderFabricationAlignment> alignmentRecords = erectionRecords.stream()
                .map(this::convertErectionToAlignment)
                .collect(Collectors.toList());

        // Mark these as the latest stored records
        alignmentRecords.forEach(record -> {
            record.setIsLatestStored(true);
            record.setStoredAt(LocalDateTime.now());
        });

        // Save all records
        List<OrderFabricationAlignment> savedRecords = alignmentRepository.saveAll(alignmentRecords);

        // Convert to DTOs and return
        return savedRecords.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Clear latest stored flags (for testing/reset)
    @Transactional
    public void clearLatestStoredFlag() {
        alignmentRepository.clearAllLatestStoredFlags();
    }

    // Convert ErectionRecord to AlignmentRecord
    private OrderFabricationAlignment convertErectionToAlignment(OrderFabricationErection erection) {
        OrderFabricationAlignment alignment = new OrderFabricationAlignment();
        
        // Copy all fields from erection to alignment
        alignment.setErectionMkd(erection.getErectionMkd());
        alignment.setItemNo(erection.getItemNo());
        alignment.setSection(erection.getSection());
        alignment.setLength(erection.getLength());
        alignment.setLengthUom(erection.getLengthUom());
        alignment.setQuantity(erection.getQuantity());
        alignment.setUnitPrice(erection.getUnitPrice());
        alignment.setUnitPriceUom(erection.getUnitPriceUom());
        alignment.setTotalQuantity(erection.getTotalQuantity());
        alignment.setTotalQuantityUom(erection.getTotalQuantityUom());
        alignment.setRepeatedQty(erection.getRepeatedQty());
        alignment.setRemark(erection.getRemark());
        alignment.setStatus("alignment"); // Set status to alignment
        alignment.setLineNumber(erection.getLineNumber());
        alignment.setLineId(erection.getLineId());
        alignment.setOrigLineNumber(erection.getOrigLineNumber());
        alignment.setOrigLineId(erection.getOrigLineId());
        
        // Set alignment-specific fields
        alignment.setIsLatestStored(true);
        alignment.setStoredAt(LocalDateTime.now());
        
        return alignment;
    }

    // Convert entity to DTO
    private OrderFabricationAlignmentDTO convertToDTO(OrderFabricationAlignment alignment) {
        OrderFabricationAlignmentDTO dto = new OrderFabricationAlignmentDTO();
        
        dto.setId(alignment.getId());
        dto.setErectionMkd(alignment.getErectionMkd());
        dto.setItemNo(alignment.getItemNo());
        dto.setSection(alignment.getSection());
        dto.setLength(alignment.getLength());
        dto.setLengthUom(alignment.getLengthUom());
        dto.setQuantity(alignment.getQuantity());
        dto.setUnitPrice(alignment.getUnitPrice());
        dto.setUnitPriceUom(alignment.getUnitPriceUom());
        dto.setTotalQuantity(alignment.getTotalQuantity());
        dto.setTotalQuantityUom(alignment.getTotalQuantityUom());
        dto.setRepeatedQty(alignment.getRepeatedQty());
        dto.setRemark(alignment.getRemark());
        dto.setStatus(alignment.getStatus());
        dto.setLineNumber(alignment.getLineNumber());
        dto.setLineId(alignment.getLineId());
        dto.setOrigLineNumber(alignment.getOrigLineNumber());
        dto.setOrigLineId(alignment.getOrigLineId());
        dto.setIsLatestStored(alignment.getIsLatestStored());
        dto.setStoredAt(alignment.getStoredAt());
        
        return dto;
    }
}
