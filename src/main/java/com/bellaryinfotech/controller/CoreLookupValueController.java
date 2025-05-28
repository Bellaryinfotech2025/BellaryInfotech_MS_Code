package com.bellaryinfotech.controller;

import com.bellaryinfotech.model.CoreLookupValue;
import com.bellaryinfotech.repo.CoreLookupValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lookup-values")
 
public class CoreLookupValueController {

    @Autowired
    private CoreLookupValueRepository repository;

    @GetMapping
    public Page<CoreLookupValue> getLookupValues(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByLookupTypeContainingIgnoreCase(search, pageable);
    }
    
    
}

