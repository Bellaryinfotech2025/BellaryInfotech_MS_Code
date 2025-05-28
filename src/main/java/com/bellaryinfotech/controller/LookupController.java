package com.bellaryinfotech.controller;
 

import com.bellaryinfotech.DAO.LookupDAO;
import com.bellaryinfotech.model.LookupValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lookups")
public class LookupController {
    
    private final LookupDAO lookupDAO;
    
    @Autowired
    public LookupController(LookupDAO lookupDAO) {
        this.lookupDAO = lookupDAO;
    }
    
    @GetMapping("/{lookupType}")
    public ResponseEntity<List<LookupValue>> getLookupValuesByType(@PathVariable String lookupType) {
        List<LookupValue> lookupValues = lookupDAO.findByLookupType(lookupType);
        return ResponseEntity.ok(lookupValues);
    }
    
    @GetMapping("/{lookupType}/code/{lookupCode}")
    public ResponseEntity<String> getMeaningByTypeAndCode(
            @PathVariable String lookupType,
            @PathVariable String lookupCode) {
        String meaning = lookupDAO.getMeaningByTypeAndCode(lookupType, lookupCode);
        return ResponseEntity.ok(meaning);
    }
    
    @GetMapping("/{lookupType}/meaning/{meaning}")
    public ResponseEntity<String> getCodeByTypeAndMeaning(
            @PathVariable String lookupType,
            @PathVariable String meaning) {
        String code = lookupDAO.getCodeByTypeAndMeaning(lookupType, meaning);
        return ResponseEntity.ok(code);
    }
}
