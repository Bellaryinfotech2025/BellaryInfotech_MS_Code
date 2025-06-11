package com.bellaryinfotech.controller;

  
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bellaryinfotech.DTO.LedgerCreationDTO;
import com.bellaryinfotech.service.LedgerCreationService;

import java.util.List;

@RestController
@RequestMapping("/api/ledgers")
@CrossOrigin(origins = "*")
public class LedgerCreationController {
    
    @Autowired
    private LedgerCreationService ledgerService;
    
    @PostMapping
    public ResponseEntity<LedgerCreationDTO> createLedger(@Valid @RequestBody LedgerCreationDTO ledgerDTO) {
        try {
            LedgerCreationDTO createdLedger = ledgerService.createLedger(ledgerDTO);
            return new ResponseEntity<>(createdLedger, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<LedgerCreationDTO> updateLedger(@PathVariable Long id, @Valid @RequestBody LedgerCreationDTO ledgerDTO) {
        try {
            LedgerCreationDTO updatedLedger = ledgerService.updateLedger(id, ledgerDTO);
            return new ResponseEntity<>(updatedLedger, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<LedgerCreationDTO> getLedgerById(@PathVariable Long id) {
        try {
            LedgerCreationDTO ledger = ledgerService.getLedgerById(id);
            return new ResponseEntity<>(ledger, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<LedgerCreationDTO>> getAllLedgers() {
        List<LedgerCreationDTO> ledgers = ledgerService.getAllLedgers();
        return new ResponseEntity<>(ledgers, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLedger(@PathVariable Long id) {
        try {
            ledgerService.deleteLedger(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
}
