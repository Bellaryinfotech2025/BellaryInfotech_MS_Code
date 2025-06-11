package com.bellaryinfotech.service;

 

 
import java.util.List;

import com.bellaryinfotech.DTO.VendorDTO;

public interface VendorService {
    
    VendorDTO createVendor(VendorDTO vendorDTO);
    
    VendorDTO updateVendor(Long id, VendorDTO vendorDTO);
    
    VendorDTO getVendorById(Long id);
    
    VendorDTO getVendorByCode(String vendorCode);
    
    List<VendorDTO> getAllVendors();
    
    List<VendorDTO> getActiveVendors();
    
    List<VendorDTO> searchVendors(String searchTerm);
    
    void deleteVendor(Long id);
    
    void deactivateVendor(Long id);
    
    void activateVendor(Long id);
    
    boolean existsByVendorCode(String vendorCode);
    
    boolean existsByEmail(String email);
    
    long getTotalVendorCount();
    
    long getActiveVendorCount();
}
