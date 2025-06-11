package com.bellaryinfotech.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bellaryinfotech.DTO.LedgerCreationDTO;
import com.bellaryinfotech.model.LedgerCreation;
import com.bellaryinfotech.repo.LedgerCreationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LedgerCreationServiceImpl implements LedgerCreationService {
    
    @Autowired
    private LedgerCreationRepository ledgerRepository;
    
    @Override
    public LedgerCreationDTO createLedger(LedgerCreationDTO ledgerDTO) {
        LedgerCreation ledger = convertToEntity(ledgerDTO);
        ledger.setStatus("ACTIVE");
        LedgerCreation savedLedger = ledgerRepository.save(ledger);
        return convertToDTO(savedLedger);
    }
    
    @Override
    public LedgerCreationDTO updateLedger(Long id, LedgerCreationDTO ledgerDTO) {
        LedgerCreation existingLedger = ledgerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ledger not found with id: " + id));
        
        updateEntityFromDTO(existingLedger, ledgerDTO);
        LedgerCreation updatedLedger = ledgerRepository.save(existingLedger);
        return convertToDTO(updatedLedger);
    }
    
    @Override
    public LedgerCreationDTO getLedgerById(Long id) {
        LedgerCreation ledger = ledgerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ledger not found with id: " + id));
        return convertToDTO(ledger);
    }
    
    @Override
    public List<LedgerCreationDTO> getAllLedgers() {
        return ledgerRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public void deleteLedger(Long id) {
        if (!ledgerRepository.existsById(id)) {
            throw new RuntimeException("Ledger not found with id: " + id);
        }
        ledgerRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByLedgerName(String ledgerName) {
        return ledgerRepository.existsByLedgerName(ledgerName);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return ledgerRepository.existsByEmail(email);
    }
    
    @Override
    public boolean existsByGstin(String gstin) {
        return ledgerRepository.existsByGstin(gstin);
    }
    
    @Override
    public boolean existsByPan(String pan) {
        return ledgerRepository.existsByPan(pan);
    }
    
    private LedgerCreation convertToEntity(LedgerCreationDTO dto) {
        LedgerCreation entity = new LedgerCreation();
        updateEntityFromDTO(entity, dto);
        return entity;
    }
    
    private void updateEntityFromDTO(LedgerCreation entity, LedgerCreationDTO dto) {
        entity.setLedgerName(dto.getLedgerName());
        entity.setGroupName(dto.getGroupName());
        entity.setDebtorCreditor(dto.getDebtorCreditor());
        entity.setHousePlotNo(dto.getHousePlotNo());
        entity.setStreet(dto.getStreet());
        entity.setVillagePost(dto.getVillagePost());
        entity.setMandalTaluq(dto.getMandalTaluq());
        entity.setDistrict(dto.getDistrict());
        entity.setState(dto.getState());
        entity.setPinCode(dto.getPinCode());
        entity.setContactPersonName(dto.getContactPersonName());
        entity.setMobileNo(dto.getMobileNo());
        entity.setEmail(dto.getEmail());
        entity.setWebsite(dto.getWebsite());
        entity.setGstin(dto.getGstin());
        entity.setPan(dto.getPan());
        entity.setBankAccountNo(dto.getBankAccountNo());
        entity.setIfscCode(dto.getIfscCode());
        entity.setBranchName(dto.getBranchName());
        entity.setServiceType(dto.getServiceType());
    }
    
    private LedgerCreationDTO convertToDTO(LedgerCreation entity) {
        LedgerCreationDTO dto = new LedgerCreationDTO();
        dto.setId(entity.getId());
        dto.setLedgerName(entity.getLedgerName());
        dto.setGroupName(entity.getGroupName());
        dto.setDebtorCreditor(entity.getDebtorCreditor());
        dto.setHousePlotNo(entity.getHousePlotNo());
        dto.setStreet(entity.getStreet());
        dto.setVillagePost(entity.getVillagePost());
        dto.setMandalTaluq(entity.getMandalTaluq());
        dto.setDistrict(entity.getDistrict());
        dto.setState(entity.getState());
        dto.setPinCode(entity.getPinCode());
        dto.setContactPersonName(entity.getContactPersonName());
        dto.setMobileNo(entity.getMobileNo());
        dto.setEmail(entity.getEmail());
        dto.setWebsite(entity.getWebsite());
        dto.setGstin(entity.getGstin());
        dto.setPan(entity.getPan());
        dto.setBankAccountNo(entity.getBankAccountNo());
        dto.setIfscCode(entity.getIfscCode());
        dto.setBranchName(entity.getBranchName());
        dto.setServiceType(entity.getServiceType());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
