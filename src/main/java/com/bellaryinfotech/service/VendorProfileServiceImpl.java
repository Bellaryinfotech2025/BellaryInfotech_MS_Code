package com.bellaryinfotech.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bellaryinfotech.DTO.VendorDTO;
import com.bellaryinfotech.model.Vendor;
import com.bellaryinfotech.repo.VendorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VendorServiceImpl implements VendorService {
    
    @Autowired
    private VendorRepository vendorRepository;
    
    @Override
    public VendorDTO createVendor(VendorDTO vendorDTO) {
        if (vendorRepository.existsByVendorCode(vendorDTO.getVendorCode())) {
            throw new RuntimeException("Vendor code already exists: " + vendorDTO.getVendorCode());
        }
        
        if (vendorDTO.getEmail() != null && vendorRepository.existsByEmail(vendorDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + vendorDTO.getEmail());
        }
        
        Vendor vendor = convertToEntity(vendorDTO);
        vendor = vendorRepository.save(vendor);
        return convertToDTO(vendor);
    }
    
    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        Vendor existingVendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + id));
        
        // Check if vendor code is being changed and if it already exists
        if (!existingVendor.getVendorCode().equals(vendorDTO.getVendorCode()) 
            && vendorRepository.existsByVendorCode(vendorDTO.getVendorCode())) {
            throw new RuntimeException("Vendor code already exists: " + vendorDTO.getVendorCode());
        }
        
        // Check if email is being changed and if it already exists
        if (vendorDTO.getEmail() != null 
            && !vendorDTO.getEmail().equals(existingVendor.getEmail())
            && vendorRepository.existsByEmail(vendorDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + vendorDTO.getEmail());
        }
        
        updateEntityFromDTO(existingVendor, vendorDTO);
        existingVendor = vendorRepository.save(existingVendor);
        return convertToDTO(existingVendor);
    }
    
    @Override
    @Transactional(readOnly = true)
    public VendorDTO getVendorById(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + id));
        return convertToDTO(vendor);
    }
    
    @Override
    @Transactional(readOnly = true)
    public VendorDTO getVendorByCode(String vendorCode) {
        Vendor vendor = vendorRepository.findByVendorCode(vendorCode)
                .orElseThrow(() -> new RuntimeException("Vendor not found with code: " + vendorCode));
        return convertToDTO(vendor);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VendorDTO> getActiveVendors() {
        return vendorRepository.findByStatus(Vendor.VendorStatus.ACTIVE).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VendorDTO> searchVendors(String searchTerm) {
        return vendorRepository.searchByCodeOrName(searchTerm, searchTerm).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteVendor(Long id) {
        if (!vendorRepository.existsById(id)) {
            throw new RuntimeException("Vendor not found with id: " + id);
        }
        vendorRepository.deleteById(id);
    }
    
    @Override
    public void deactivateVendor(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + id));
        vendor.setStatus(Vendor.VendorStatus.INACTIVE);
        vendorRepository.save(vendor);
    }
    
    @Override
    public void activateVendor(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + id));
        vendor.setStatus(Vendor.VendorStatus.ACTIVE);
        vendorRepository.save(vendor);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByVendorCode(String vendorCode) {
        return vendorRepository.existsByVendorCode(vendorCode);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return vendorRepository.existsByEmail(email);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getTotalVendorCount() {
        return vendorRepository.count();
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getActiveVendorCount() {
        return vendorRepository.countByStatus(Vendor.VendorStatus.ACTIVE);
    }
    
    // Helper methods for conversion
    private VendorDTO convertToDTO(Vendor vendor) {
        VendorDTO dto = new VendorDTO();
        dto.setId(vendor.getId());
        dto.setVendorCode(vendor.getVendorCode());
        dto.setName(vendor.getName());
        dto.setAddress(vendor.getAddress());
        dto.setPhone(vendor.getPhone());
        dto.setEmail(vendor.getEmail());
        dto.setPanNo(vendor.getPanNo());
        dto.setGstNo(vendor.getGstNo());
        dto.setBankAccount(vendor.getBankAccount());
        dto.setStatus(vendor.getStatus().toString());
        dto.setCreatedAt(vendor.getCreatedAt());
        dto.setUpdatedAt(vendor.getUpdatedAt());
        return dto;
    }
    
    private Vendor convertToEntity(VendorDTO dto) {
        Vendor vendor = new Vendor();
        vendor.setVendorCode(dto.getVendorCode());
        vendor.setName(dto.getName());
        vendor.setAddress(dto.getAddress());
        vendor.setPhone(dto.getPhone());
        vendor.setEmail(dto.getEmail());
        vendor.setPanNo(dto.getPanNo());
        vendor.setGstNo(dto.getGstNo());
        vendor.setBankAccount(dto.getBankAccount());
        if (dto.getStatus() != null) {
            vendor.setStatus(Vendor.VendorStatus.valueOf(dto.getStatus()));
        }
        return vendor;
    }
    
    private void updateEntityFromDTO(Vendor vendor, VendorDTO dto) {
        vendor.setVendorCode(dto.getVendorCode());
        vendor.setName(dto.getName());
        vendor.setAddress(dto.getAddress());
        vendor.setPhone(dto.getPhone());
        vendor.setEmail(dto.getEmail());
        vendor.setPanNo(dto.getPanNo());
        vendor.setGstNo(dto.getGstNo());
        vendor.setBankAccount(dto.getBankAccount());
        if (dto.getStatus() != null) {
            vendor.setStatus(Vendor.VendorStatus.valueOf(dto.getStatus()));
        }
    }
}
