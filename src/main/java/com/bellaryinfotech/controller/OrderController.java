package com.bellaryinfotech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bellaryinfotech.model.OrderDetailsHeader;
import com.bellaryinfotech.model.OrderDetailsLookup;
import com.bellaryinfotech.model.OrderHeader;
import com.bellaryinfotech.repo.OrderDetailsHeaderRepository;
import com.bellaryinfotech.security.OrderHeaderService;
import com.bellaryinfotech.service.OrderDetailsService;

import java.util.*;

@RestController
@RequestMapping("/api/V2.0")
 
public class OrderController {

    // --- From OrderDatabaseSearchController ---
    @Autowired
    private OrderDetailsHeaderRepository orderHeaderRepository;

    @GetMapping("/fetchorderdata")
    public ResponseEntity<List<OrderDetailsHeader>> getAllOrders() {
        List<OrderDetailsHeader> orders = orderHeaderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/ordersearchapi")
    public ResponseEntity<List<OrderDetailsHeader>> searchOrders(@RequestParam String query) {
        List<OrderDetailsHeader> orders = orderHeaderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/update-order")
    public ResponseEntity<Map<String, Object>> updateOrder(@RequestBody OrderDetailsHeader order) {
        Map<String, Object> response = new HashMap<>();

        try {
            OrderDetailsHeader existingOrder = orderHeaderRepository.findById(order.getOrderId())
                .orElse(null);

            if (existingOrder == null) {
                response.put("status", "error");
                response.put("message", "Order not found");
                return ResponseEntity.badRequest().body(response);
            }

            // Validate required fields
            if (order.getOrderNumber() == null || order.getOrderNumber().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Order number is required");
                return ResponseEntity.badRequest().body(response);
            }

            OrderDetailsHeader updatedOrder = orderHeaderRepository.save(order);

            response.put("status", "success");
            response.put("message", "Order updated successfully");
            response.put("data", updatedOrder);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to update order: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // --- From OrderHeaderController ---
    @Autowired
    private OrderHeaderService orderHeaderService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderHeader>> getAllOrderHeaders() {
        List<OrderHeader> orders = orderHeaderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderHeader> getOrderById(@PathVariable Long id) {
        Optional<OrderHeader> order = orderHeaderService.getOrderById(id);
        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderHeader> createOrder(@RequestBody OrderHeader orderHeader) {
        OrderHeader savedOrder = orderHeaderService.saveOrder(orderHeader);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    // --- From OrderDetailsController ---
    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping("/order-lookup-values")
    public ResponseEntity<Map<String, List<OrderDetailsLookup>>> getAllLookupValues() {
        Map<String, List<OrderDetailsLookup>> lookupValues = orderDetailsService.getAllLookupValues();
        return ResponseEntity.ok(lookupValues);
    }

    @PostMapping("/post-orders")
    public ResponseEntity<Map<String, Object>> saveOrder(@RequestBody OrderDetailsHeader orderData) {
        try {
            Map<String, Object> result = orderDetailsService.saveOrder(orderData);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("status", "error");
            errorResult.put("message", "Failed to save order: " + e.getMessage());
            return ResponseEntity.ok(errorResult);
        }
    }
}