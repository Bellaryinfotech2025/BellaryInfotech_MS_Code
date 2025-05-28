package com.bellaryinfotech.DAO;

 

import java.util.List;
import java.util.Optional;

import com.bellaryinfotech.model.LookupValue;

public interface LookupDAO {
    
    List<LookupValue> findByLookupType(String lookupType);
    
    Optional<LookupValue> findByLookupTypeAndCode(String lookupType, String lookupCode);
    
    Optional<LookupValue> findByLookupTypeAndMeaning(String lookupType, String meaning);
    
    String getMeaningByTypeAndCode(String lookupType, String lookupCode);
    
    String getCodeByTypeAndMeaning(String lookupType, String meaning);
}
