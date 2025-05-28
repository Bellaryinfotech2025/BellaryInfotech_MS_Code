package com.bellaryinfotech.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bellaryinfotech.model.LinesModel;
import com.bellaryinfotech.service.LinesService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lines")
 
public class LinesController {

    @Autowired
    private LinesService linesService;

    @GetMapping("/fetchLinesByOrder/{orderId}")
    public ResponseEntity<Map<String, Object>> fetchLinesByOrder(@PathVariable Long orderId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<LinesModel> lines = linesService.getLinesByOrderId(orderId);
            response.put("status", "success");
            response.put("lines", lines);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/createParentLine")
    public ResponseEntity<Map<String, Object>> createParentLine(@RequestBody LinesModel line) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validate line number is provided
            if (line.getLineNumber() == null || line.getLineNumber().isEmpty()) {
                throw new IllegalArgumentException("Line number is required");
            }
            
            // Set isParent to true for parent lines
            line.setIsParent(true);
            LinesModel savedLine = linesService.saveLine(line);
            
            response.put("status", "success");
            response.put("line", savedLine);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/createChildLine")
    public ResponseEntity<Map<String, Object>> createChildLine(@RequestBody LinesModel line) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validate parent line number is selected
            if (line.getParentLineNumber() == null) {
                throw new IllegalArgumentException("Parent line number is required for child lines");
            }
            
            // Validate line number is provided
            if (line.getLineNumber() == null || line.getLineNumber().isEmpty()) {
                throw new IllegalArgumentException("Line number is required");
            }
            
            // Set isParent to false for child lines
            line.setIsParent(false);
            LinesModel savedLine = linesService.saveLine(line);
            
            response.put("status", "success");
            response.put("line", savedLine);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}


