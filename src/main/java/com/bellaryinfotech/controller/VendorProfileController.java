package com.bellaryinfotech.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bellaryinfotech.DTO.VendorDTO;
import com.bellaryinfotech.service.VendorService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@CrossOrigin(origins = "*")
public class VendorController {
    
    @Autowired
    private VendorService vendorService;
    
    @PostMapping
    public ResponseEntity<VendorDTO> createVendor(@Valid @RequestBody VendorDTO vendorDTO) {
        try {
            VendorDTO createdVendor = vendorService.createVendor(vendorDTO);
            return new ResponseEntity<>(createdVendor, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<VendorDTO> updateVendor(@PathVariable Long id, @Valid @RequestBody VendorDTO vendorDTO) {
        try {
            VendorDTO updatedVendor = vendorService.updateVendor(id, vendorDTO);
            return new ResponseEntity<>(updatedVendor, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VendorDTO> getVendorById(@PathVariable Long id) {
        try {
            VendorDTO vendor = vendorService.getVendorById(id);
            return new ResponseEntity<>(vendor, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/code/{vendorCode}")
    public ResponseEntity<VendorDTO> getVendorByCode(@PathVariable String vendorCode) {
        try {
            VendorDTO vendor = vendorService.getVendorByCode(vendorCode);
            return new ResponseEntity<>(vendor, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<VendorDTO>> getAllVendors() {
        List<VendorDTO> vendors = vendorService.getAllVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<VendorDTO>> getActiveVendors() {
        List<VendorDTO> vendors = vendorService.getActiveVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<VendorDTO>> searchVendors(@RequestParam String term) {
        List<VendorDTO> vendors = vendorService.searchVendors(term);
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        try {
            vendorService.deleteVendor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateVendor(@PathVariable Long id) {
        try {
            vendorService.deactivateVendor(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activateVendor(@PathVariable Long id) {
        try {
            vendorService.activateVendor(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/exists/code/{vendorCode}")
    public ResponseEntity<Boolean> checkVendorCodeExists(@PathVariable String vendorCode) {
        boolean exists = vendorService.existsByVendorCode(vendorCode);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
    
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = vendorService.existsByEmail(email);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalVendorCount() {
        long count = vendorService.getTotalVendorCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    @GetMapping("/count/active")
    public ResponseEntity<Long> getActiveVendorCount() {
        long count = vendorService.getActiveVendorCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
