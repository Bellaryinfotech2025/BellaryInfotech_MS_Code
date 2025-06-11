package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.VendorProfileDTO;
import com.bellaryinfotech.model.VendorProfile;
import com.bellaryinfotech.repo.VendorProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VendorProfileServiceImpl implements VendorProfileService {
    
    @Autowired
    private VendorProfileRepository profileRepository;
    
    @Override
    public VendorProfileDTO createProfile(VendorProfileDTO profileDTO) {
        if (profileRepository.existsByCompanyName(profileDTO.getCompanyName())) {
            throw new RuntimeException("Company name already exists: " + profileDTO.getCompanyName());
        }
        
        if (profileDTO.getEmail() != null && profileRepository.existsByEmail(profileDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + profileDTO.getEmail());
        }
        
        VendorProfile profile = convertToEntity(profileDTO);
        profile = profileRepository.save(profile);
        return convertToDTO(profile);
    }
    
    @Override
    public VendorProfileDTO updateProfile(Long id, VendorProfileDTO profileDTO) {
        VendorProfile existingProfile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
        
        // Check if company name is being changed and if it already exists
        if (!existingProfile.getCompanyName().equals(profileDTO.getCompanyName()) 
            && profileRepository.existsByCompanyName(profileDTO.getCompanyName())) {
            throw new RuntimeException("Company name already exists: " + profileDTO.getCompanyName());
        }
        
        // Check if email is being changed and if it already exists
        if (profileDTO.getEmail() != null 
            && !profileDTO.getEmail().equals(existingProfile.getEmail())
            && profileRepository.existsByEmail(profileDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + profileDTO.getEmail());
        }
        
        updateEntityFromDTO(existingProfile, profileDTO);
        existingProfile = profileRepository.save(existingProfile);
        return convertToDTO(existingProfile);
    }
    
    @Override
    @Transactional(readOnly = true)
    public VendorProfileDTO getProfileById(Long id) {
        VendorProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
        return convertToDTO(profile);
    }
    
    @Override
    @Transactional(readOnly = true)
    public VendorProfileDTO getProfileByCompanyName(String companyName) {
        VendorProfile profile = profileRepository.findByCompanyName(companyName)
                .orElseThrow(() -> new RuntimeException("Profile not found with company name: " + companyName));
        return convertToDTO(profile);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VendorProfileDTO> getAllProfiles() {
        return profileRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VendorProfileDTO> getActiveProfiles() {
        return profileRepository.findByStatus(VendorProfile.ProfileStatus.ACTIVE).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VendorProfileDTO> searchProfiles(String searchTerm) {
        return profileRepository.searchProfiles(searchTerm).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteProfile(Long id) {
        if (!profileRepository.existsById(id)) {
            throw new RuntimeException("Profile not found with id: " + id);
        }
        profileRepository.deleteById(id);
    }
    
    @Override
    public void deactivateProfile(Long id) {
        VendorProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
        profile.setStatus(VendorProfile.ProfileStatus.INACTIVE);
        profileRepository.save(profile);
    }
    
    @Override
    public void activateProfile(Long id) {
        VendorProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
        profile.setStatus(VendorProfile.ProfileStatus.ACTIVE);
        profileRepository.save(profile);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByCompanyName(String companyName) {
        return profileRepository.existsByCompanyName(companyName);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return profileRepository.existsByEmail(email);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getTotalProfileCount() {
        return profileRepository.count();
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getActiveProfileCount() {
        return profileRepository.countByStatus(VendorProfile.ProfileStatus.ACTIVE);
    }
    
    @Override
    public VendorProfileDTO uploadLogo(Long id, MultipartFile logoFile) {
        VendorProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
        
        try {
            profile.setLogoData(logoFile.getBytes());
            profile.setLogoFilename(logoFile.getOriginalFilename());
            profile.setLogoContentType(logoFile.getContentType());
            
            profile = profileRepository.save(profile);
            return convertToDTO(profile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload logo: " + e.getMessage());
        }
    }
    
    @Override
    public VendorProfileDTO uploadLetterHead(Long id, MultipartFile letterHeadFile) {
        VendorProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
        
        try {
            // Convert MultipartFile to byte array
            byte[] letterHeadBytes = letterHeadFile.getBytes();
            profile.setLetterHeadData(letterHeadBytes);
            profile.setLetterHeadFilename(letterHeadFile.getOriginalFilename());
            profile.setLetterHeadContentType(letterHeadFile.getContentType());
        
            profile = profileRepository.save(profile);
            return convertToDTO(profile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload letter head: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public byte[] getLogoData(Long id) {
        VendorProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
        return profile.getLogoData();
    }
    
    @Override
    @Transactional(readOnly = true)
    public byte[] getLetterHeadData(Long id) {
        VendorProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
        return profile.getLetterHeadData();
    }
    
    @Override
    public void deleteLogo(Long id) {
        VendorProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
        
        profile.setLogoData(null);
        profile.setLogoFilename(null);
        profile.setLogoContentType(null);
        profileRepository.save(profile);
    }
    
    @Override
    public void deleteLetterHead(Long id) {
        VendorProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
        
        profile.setLetterHeadData(null);
        profile.setLetterHeadFilename(null);
        profile.setLetterHeadContentType(null);
        profileRepository.save(profile);
    }
    
    // Helper methods for conversion
    private VendorProfileDTO convertToDTO(VendorProfile profile) {
        VendorProfileDTO dto = new VendorProfileDTO();
        dto.setId(profile.getId());
        dto.setCompanyName(profile.getCompanyName());
        dto.setHousePlotNo(profile.getHousePlotNo());
        dto.setFloor(profile.getFloor());
        dto.setBuildingName(profile.getBuildingName());
        dto.setStreet(profile.getStreet());
        dto.setArea(profile.getArea());
        dto.setVillagePost(profile.getVillagePost());
        dto.setMandalTq(profile.getMandalTq());
        dto.setDistrict(profile.getDistrict());
        dto.setState(profile.getState());
        dto.setPinCode(profile.getPinCode());
        dto.setContactPerson(profile.getContactPerson());
        dto.setContactNumber(profile.getContactNumber());
        dto.setEmail(profile.getEmail());
        dto.setLogoFilename(profile.getLogoFilename());
        dto.setLogoContentType(profile.getLogoContentType());
        dto.setHasLogo(profile.getLogoData() != null);
        dto.setLetterHeadFilename(profile.getLetterHeadFilename());
        dto.setLetterHeadContentType(profile.getLetterHeadContentType());
        dto.setHasLetterHead(profile.getLetterHeadData() != null);
        dto.setBankAccount(profile.getBankAccount());
        dto.setIfscCode(profile.getIfscCode());
        dto.setBranchName(profile.getBranchName());
        dto.setGstNo(profile.getGstNo());
        dto.setPanNo(profile.getPanNo());
        dto.setStatus(profile.getStatus().toString());
        dto.setCreatedAt(profile.getCreatedAt());
        dto.setUpdatedAt(profile.getUpdatedAt());
        return dto;
    }
    
    private VendorProfile convertToEntity(VendorProfileDTO dto) {
        VendorProfile profile = new VendorProfile();
        profile.setCompanyName(dto.getCompanyName());
        profile.setHousePlotNo(dto.getHousePlotNo());
        profile.setFloor(dto.getFloor());
        profile.setBuildingName(dto.getBuildingName());
        profile.setStreet(dto.getStreet());
        profile.setArea(dto.getArea());
        profile.setVillagePost(dto.getVillagePost());
        profile.setMandalTq(dto.getMandalTq());
        profile.setDistrict(dto.getDistrict());
        profile.setState(dto.getState());
        profile.setPinCode(dto.getPinCode());
        profile.setContactPerson(dto.getContactPerson());
        profile.setContactNumber(dto.getContactNumber());
        profile.setEmail(dto.getEmail());
        profile.setBankAccount(dto.getBankAccount());
        profile.setIfscCode(dto.getIfscCode());
        profile.setBranchName(dto.getBranchName());
        profile.setGstNo(dto.getGstNo());
        profile.setPanNo(dto.getPanNo());
        
        // Don't set binary data here - it will be handled by separate upload endpoints
        // We should not try to set letterHeadData or logoData from the DTO
        
        if (dto.getStatus() != null) {
            profile.setStatus(VendorProfile.ProfileStatus.valueOf(dto.getStatus()));
        }
        return profile;
    }
    
    private void updateEntityFromDTO(VendorProfile profile, VendorProfileDTO dto) {
        profile.setCompanyName(dto.getCompanyName());
        profile.setHousePlotNo(dto.getHousePlotNo());
        profile.setFloor(dto.getFloor());
        profile.setBuildingName(dto.getBuildingName());
        profile.setStreet(dto.getStreet());
        profile.setArea(dto.getArea());
        profile.setVillagePost(dto.getVillagePost());
        profile.setMandalTq(dto.getMandalTq());
        profile.setDistrict(dto.getDistrict());
        profile.setState(dto.getState());
        profile.setPinCode(dto.getPinCode());
        profile.setContactPerson(dto.getContactPerson());
        profile.setContactNumber(dto.getContactNumber());
        profile.setEmail(dto.getEmail());
        profile.setBankAccount(dto.getBankAccount());
        profile.setIfscCode(dto.getIfscCode());
        profile.setBranchName(dto.getBranchName());
        profile.setGstNo(dto.getGstNo());
        profile.setPanNo(dto.getPanNo());
        if (dto.getStatus() != null) {
            profile.setStatus(VendorProfile.ProfileStatus.valueOf(dto.getStatus()));
        }
    }
}
