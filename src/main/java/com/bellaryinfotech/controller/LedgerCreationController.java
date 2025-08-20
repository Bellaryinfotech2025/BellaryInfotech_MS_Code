package com.bellaryinfotech.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.bellaryinfotech.repo.LedgerCreationRepository;
import com.bellaryinfotech.DTO.LedgerCreationDTO;
import com.bellaryinfotech.model.LedgerCreation;
import com.bellaryinfotech.service.LedgerCreationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 

@RestController
@RequestMapping("/api/ledgers")
@CrossOrigin(origins = "*")
public class LedgerCreationController {
	
	private static final Logger logger = LoggerFactory.getLogger(LedgerCreationController.class);
    
    @Autowired
    private LedgerCreationService ledgerService;
    @Autowired
    private LedgerCreationRepository ledgercreationrepo;
    
    @PostMapping
    public ResponseEntity<?> createLedger(@Valid @RequestBody LedgerCreationDTO ledgerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 400);
            errorResponse.put("error", "Validation Failed");
            errorResponse.put("message", "Please check the following fields:");
            
            List<Map<String, String>> fieldErrors = bindingResult.getFieldErrors().stream()
                .map(error -> {
                    Map<String, String> fieldError = new HashMap<>();
                    fieldError.put("field", error.getField());
                    fieldError.put("message", error.getDefaultMessage());
                    fieldError.put("rejectedValue", String.valueOf(error.getRejectedValue()));
                    return fieldError;
                })
                .collect(Collectors.toList());
            
            errorResponse.put("fieldErrors", fieldErrors);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        
        try {
            LedgerCreationDTO createdLedger = ledgerService.createLedger(ledgerDTO);
            return new ResponseEntity<>(createdLedger, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 500);
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", "Failed to create ledger: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLedger(@PathVariable Long id, @Valid @RequestBody LedgerCreationDTO ledgerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 400);
            errorResponse.put("error", "Validation Failed");
            errorResponse.put("message", "Please check the following fields:");
            
            List<Map<String, String>> fieldErrors = bindingResult.getFieldErrors().stream()
                .map(error -> {
                    Map<String, String> fieldError = new HashMap<>();
                    fieldError.put("field", error.getField());
                    fieldError.put("message", error.getDefaultMessage());
                    fieldError.put("rejectedValue", String.valueOf(error.getRejectedValue()));
                    return fieldError;
                })
                .collect(Collectors.toList());
            
            errorResponse.put("fieldErrors", fieldErrors);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        
        try {
            LedgerCreationDTO updatedLedger = ledgerService.updateLedger(id, ledgerDTO);
            return new ResponseEntity<>(updatedLedger, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 404);
            errorResponse.put("error", "Not Found");
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 500);
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", "Failed to update ledger: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getLedgerById(@PathVariable Long id) {
        try {
            LedgerCreationDTO ledger = ledgerService.getLedgerById(id);
            return new ResponseEntity<>(ledger, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 404);
            errorResponse.put("error", "Not Found");
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<LedgerCreationDTO>> getAllLedgers() {
        List<LedgerCreationDTO> ledgers = ledgerService.getAllLedgers();
        return new ResponseEntity<>(ledgers, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLedger(@PathVariable Long id) {
        try {
            ledgerService.deleteLedger(id);
            Map<String, Object> response = new HashMap<>();
            response.put("status", 200);
            response.put("message", "Ledger deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 404);
            errorResponse.put("error", "Not Found");
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/exists/ledger-name/{ledgerName}")
    public ResponseEntity<Boolean> existsByLedgerName(@PathVariable String ledgerName) {
        boolean exists = ledgerService.existsByLedgerName(ledgerName);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
    
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        boolean exists = ledgerService.existsByEmail(email);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
    
    @GetMapping("/exists/gstin/{gstin}")
    public ResponseEntity<Boolean> existsByGstin(@PathVariable String gstin) {
        boolean exists = ledgerService.existsByGstin(gstin);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
    
    @GetMapping("/exists/pan/{pan}")
    public ResponseEntity<Boolean> existsByPan(@PathVariable String pan) {
        boolean exists = ledgerService.existsByPan(pan);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
    
    
  
    
    @GetMapping(value = "/getCreditors/details", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<LedgerCreation>> getCreditors() {
        try {
            List<LedgerCreation> creditors = ledgercreationrepo.findByDebtorCreditor("Creditor");
            return ResponseEntity.ok(creditors);
        } catch (Exception e) {
            logger.error("Error fetching creditors: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
