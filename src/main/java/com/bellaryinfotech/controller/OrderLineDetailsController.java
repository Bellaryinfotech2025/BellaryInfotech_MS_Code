package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.OrderLineDetailsDTO;
import com.bellaryinfotech.service.OrderLineDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/lines/child")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderLineDetailsController {

    @Autowired
    private OrderLineDetailsService service;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderLineDetailsDTO>> getByOrderId(@PathVariable BigDecimal orderId) {
        return ResponseEntity.ok(service.getDetailsByOrderId(orderId));
    }

    @PostMapping("/child")
    public ResponseEntity<OrderLineDetailsDTO> saveChild(@RequestBody OrderLineDetailsDTO dto) {
        OrderLineDetailsDTO saved = service.saveOrderLineDetail(dto);
        return ResponseEntity.ok(saved);
    }
}
