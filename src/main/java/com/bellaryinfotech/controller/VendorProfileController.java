package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.VendorProfileDTO;
import com.bellaryinfotech.service.VendorProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/vendor-profile")
@CrossOrigin(origins = "*")
public class VendorProfileController {
    
    @Autowired
    private VendorProfileService profileService;
    
    @PostMapping
    public ResponseEntity<?> createProfile(@Valid @RequestBody VendorProfileDTO profileDTO) {
        try {
            VendorProfileDTO createdProfile = profileService.createProfile(profileDTO);
            return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Return more detailed error message
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            errorResponse.put("error", "Bad Request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @Valid @RequestBody VendorProfileDTO profileDTO) {
        try {
            VendorProfileDTO updatedProfile = profileService.updateProfile(id, profileDTO);
            return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Return more detailed error message
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            errorResponse.put("error", "Bad Request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VendorProfileDTO> getProfileById(@PathVariable Long id) {
        try {
            VendorProfileDTO profile = profileService.getProfileById(id);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/company/{companyName}")
    public ResponseEntity<VendorProfileDTO> getProfileByCompanyName(@PathVariable String companyName) {
        try {
            VendorProfileDTO profile = profileService.getProfileByCompanyName(companyName);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<VendorProfileDTO>> getAllProfiles() {
        List<VendorProfileDTO> profiles = profileService.getAllProfiles();
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<VendorProfileDTO>> getActiveProfiles() {
        List<VendorProfileDTO> profiles = profileService.getActiveProfiles();
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<VendorProfileDTO>> searchProfiles(@RequestParam String term) {
        List<VendorProfileDTO> profiles = profileService.searchProfiles(term);
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        try {
            profileService.deleteProfile(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateProfile(@PathVariable Long id) {
        try {
            profileService.deactivateProfile(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activateProfile(@PathVariable Long id) {
        try {
            profileService.activateProfile(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/exists/company/{companyName}")
    public ResponseEntity<Boolean> checkCompanyNameExists(@PathVariable String companyName) {
        boolean exists = profileService.existsByCompanyName(companyName);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
    
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = profileService.existsByEmail(email);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalProfileCount() {
        long count = profileService.getTotalProfileCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    @GetMapping("/count/active")
    public ResponseEntity<Long> getActiveProfileCount() {
        long count = profileService.getActiveProfileCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    // Image upload endpoints
    @PostMapping("/{id}/logo")
    public ResponseEntity<VendorProfileDTO> uploadLogo(@PathVariable Long id, @RequestParam("logo") MultipartFile logoFile) {
        try {
            VendorProfileDTO updatedProfile = profileService.uploadLogo(id, logoFile);
            return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/{id}/letterhead")
    public ResponseEntity<VendorProfileDTO> uploadLetterHead(@PathVariable Long id, @RequestParam("letterhead") MultipartFile letterHeadFile) {
        try {
            VendorProfileDTO updatedProfile = profileService.uploadLetterHead(id, letterHeadFile);
            return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/{id}/logo")
    public ResponseEntity<byte[]> getLogo(@PathVariable Long id) {
        try {
            byte[] logoData = profileService.getLogoData(id);
            if (logoData != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // You might want to store and use the actual content type
                return new ResponseEntity<>(logoData, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{id}/letterhead")
    public ResponseEntity<byte[]> getLetterHead(@PathVariable Long id) {
        try {
            byte[] letterHeadData = profileService.getLetterHeadData(id);
            if (letterHeadData != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // You might want to store and use the actual content type
                return new ResponseEntity<>(letterHeadData, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}/logo")
    public ResponseEntity<Void> deleteLogo(@PathVariable Long id) {
        try {
            profileService.deleteLogo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}/letterhead")
    public ResponseEntity<Void> deleteLetterHead(@PathVariable Long id) {
        try {
            profileService.deleteLetterHead(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
