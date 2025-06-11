package com.bellaryinfotech.service;
 
import java.util.List;

import com.bellaryinfotech.DTO.LedgerCreationDTO;

public interface LedgerCreationService {
    
    LedgerCreationDTO createLedger(LedgerCreationDTO ledgerDTO);
    
    LedgerCreationDTO updateLedger(Long id, LedgerCreationDTO ledgerDTO);
    
    LedgerCreationDTO getLedgerById(Long id);
    
    List<LedgerCreationDTO> getAllLedgers();
    
    void deleteLedger(Long id);
    
    boolean existsByLedgerName(String ledgerName);
    
    boolean existsByEmail(String email);
    
    boolean existsByGstin(String gstin);
    
    boolean existsByPan(String pan);
}

