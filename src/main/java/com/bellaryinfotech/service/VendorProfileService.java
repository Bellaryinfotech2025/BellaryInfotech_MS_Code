package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.VendorProfileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VendorProfileService {
    
    VendorProfileDTO createProfile(VendorProfileDTO profileDTO);
    
    VendorProfileDTO updateProfile(Long id, VendorProfileDTO profileDTO);
    
    VendorProfileDTO getProfileById(Long id);
    
    VendorProfileDTO getProfileByCompanyName(String companyName);
    
    List<VendorProfileDTO> getAllProfiles();
    
    List<VendorProfileDTO> getActiveProfiles();
    
    List<VendorProfileDTO> searchProfiles(String searchTerm);
    
    void deleteProfile(Long id);
    
    void deactivateProfile(Long id);
    
    void activateProfile(Long id);
    
    boolean existsByCompanyName(String companyName);
    
    boolean existsByEmail(String email);
    
    long getTotalProfileCount();
    
    long getActiveProfileCount();
    
    // NEW METHOD: Get latest vendor profile
    VendorProfileDTO getLatestVendorProfile();
    
    // Image handling methods
    VendorProfileDTO uploadLogo(Long id, MultipartFile logoFile);
    
    VendorProfileDTO uploadLetterHead(Long id, MultipartFile letterHeadFile);
    
    byte[] getLogoData(Long id);
    
    byte[] getLetterHeadData(Long id);
    
    void deleteLogo(Long id);
    
    void deleteLetterHead(Long id);
    
    
    
    
    
}
