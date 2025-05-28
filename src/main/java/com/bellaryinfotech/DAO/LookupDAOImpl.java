package com.bellaryinfotech.DAO;
 
 

import com.bellaryinfotech.DAO.LookupDAO;
import com.bellaryinfotech.model.LookupValue;
import com.bellaryinfotech.repo.LookupValuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LookupDAOImpl implements LookupDAO {
    
    private final LookupValuesRepository lookupValuesRepository;
    
    @Autowired
    public LookupDAOImpl(LookupValuesRepository lookupValuesRepository) {
        this.lookupValuesRepository = lookupValuesRepository;
    }
    
    @Override
    public List<LookupValue> findByLookupType(String lookupType) {
        return lookupValuesRepository.findByLookupTypeName(lookupType);
    }
    
    @Override
    public Optional<LookupValue> findByLookupTypeAndCode(String lookupType, String lookupCode) {
        return lookupValuesRepository.findByLookupTypeAndCode(lookupType, lookupCode);
    }
    
    @Override
    public Optional<LookupValue> findByLookupTypeAndMeaning(String lookupType, String meaning) {
        return lookupValuesRepository.findByLookupTypeAndMeaning(lookupType, meaning);
    }
    
    @Override
    public String getMeaningByTypeAndCode(String lookupType, String lookupCode) {
        Optional<LookupValue> lookupValue = findByLookupTypeAndCode(lookupType, lookupCode);
        return lookupValue.map(LookupValue::getMeaning).orElse(lookupCode);
    }
    
    @Override
    public String getCodeByTypeAndMeaning(String lookupType, String meaning) {
        // Convert meaning to uppercase before lookup
        String uppercaseMeaning = meaning != null ? meaning.toUpperCase() : null;
        Optional<LookupValue> lookupValue = findByLookupTypeAndMeaning(lookupType, uppercaseMeaning);
        
        // If not found with uppercase, try the original meaning as fallback
        if (!lookupValue.isPresent() && !uppercaseMeaning.equals(meaning)) {
            lookupValue = findByLookupTypeAndMeaning(lookupType, meaning);
        }
        
        return lookupValue.map(LookupValue::getLookupCode).orElse(uppercaseMeaning != null ? uppercaseMeaning : meaning);
    }
}
