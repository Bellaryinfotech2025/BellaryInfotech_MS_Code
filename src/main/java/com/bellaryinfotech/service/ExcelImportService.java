package com.bellaryinfotech.service;

import com.bellaryinfotech.model.OrderFabricationImport;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExcelImportService {
    
    /**
     * Import Excel file to database
     * 
     * @param file The Excel file to import
     * @return List of imported records
     * @throws Exception If an error occurs during import
     */
    List<OrderFabricationImport> importExcelToDatabase(MultipartFile file) throws Exception;
}