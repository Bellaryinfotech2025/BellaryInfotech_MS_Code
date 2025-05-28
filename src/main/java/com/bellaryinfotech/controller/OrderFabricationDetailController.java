package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.OrderFabricationDetailDTO;
import com.bellaryinfotech.service.OrderFabricationDetailService;
import com.bellaryinfotech.service.OrderFabricationDetailServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v3.0")
public class OrderFabricationDetailController {

    // API Paths as constants
    public static final String GET_ALL_FABRICATION_DETAILS = "/loadingfabricatiodetail";
    public static final String GET_FABRICATION_DETAIL_BY_ID = "/loadingfabricatiodetail/{id}";
    public static final String POST_FABRICATION_DETAILS = "/sendfabricationdetails";
    public static final String PUT_UPDATE_FABRICATION_DETAILS = "/updatefabricationdetails/{id}";
    public static final String DELETE_FABRICATION_DETAILS = "/deletefabricationdetails/{id}";
    public static final String GET_FABRICATION_DETAILS_BY_ORDER_ID = "/loadingfabricatiodetail/order/{orderId}";
    public static final String GET_FABRICATION_DETAILS_BY_BUILDING = "/loadingfabricatiodetail/building/{buildingName}";
    public static final String GET_FABRICATION_DETAILS_BY_DRAWING = "/loadingfabricatiodetail/drawing/{drawingNo}";
    public static final String GET_FABRICATION_DETAILS_BY_ORDER_NUMBER = "/loadingfabricatiodetail/order-number/{orderNumber}";
    public static final String GET_FABRICATION_DETAILS_BY_ERECTION_MKD = "/loadingfabricatiodetail/erection/{erectionMkd}";
    public static final String GET_FABRICATION_DETAILS_BY_LINE_AND_ERECTION = "/loadingfabricatiodetail/line/{lineNumber}/erection/{erectionMkd}";
    public static final String GET_FABRICATION_DETAILS_BY_LINE_ID_AND_ERECTION = "/loadingfabricatiodetail/line-id/{lineId}/erection/{erectionMkd}";
    public static final String POST_COPY_ERECTION_MKD = "/copyerectionmkd";

    @Autowired
    private final OrderFabricationDetailService orderFabricationDetailService;
    @Autowired
    public OrderFabricationDetailController(OrderFabricationDetailService orderFabricationDetailService) {
        this.orderFabricationDetailService = orderFabricationDetailService;
    }
    @Autowired
    public OrderFabricationDetailServiceImpl service;

    @GetMapping(GET_ALL_FABRICATION_DETAILS)
    public ResponseEntity<List<OrderFabricationDetailDTO>> getAllOrderFabricationDetails() {
        List<OrderFabricationDetailDTO> orderFabricationDetails = orderFabricationDetailService.findAll();
        return ResponseEntity.ok(orderFabricationDetails);
    }

    @GetMapping(GET_FABRICATION_DETAIL_BY_ID)
    public ResponseEntity<OrderFabricationDetailDTO> getOrderFabricationDetailById(@PathVariable Long id) {
        try {
            OrderFabricationDetailDTO orderFabricationDetail = orderFabricationDetailService.findById(id);
            return ResponseEntity.ok(orderFabricationDetail);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(POST_FABRICATION_DETAILS)
    public ResponseEntity<OrderFabricationDetailDTO> createOrderFabricationDetail(@RequestBody OrderFabricationDetailDTO orderFabricationDetailDTO) {
        OrderFabricationDetailDTO createdOrderFabricationDetail = orderFabricationDetailService.save(orderFabricationDetailDTO);
        return new ResponseEntity<>(createdOrderFabricationDetail, HttpStatus.CREATED);
    }

    @PutMapping(PUT_UPDATE_FABRICATION_DETAILS)
    public ResponseEntity<OrderFabricationDetailDTO> updateOrderFabricationDetail(@PathVariable Long id, @RequestBody OrderFabricationDetailDTO orderFabricationDetailDTO) {
        try {
            OrderFabricationDetailDTO updatedOrderFabricationDetail = orderFabricationDetailService.update(id, orderFabricationDetailDTO);
            return ResponseEntity.ok(updatedOrderFabricationDetail);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(DELETE_FABRICATION_DETAILS)
    public ResponseEntity<Void> deleteOrderFabricationDetail(@PathVariable Long id) {
        try {
            orderFabricationDetailService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(GET_FABRICATION_DETAILS_BY_ORDER_ID)
    public ResponseEntity<List<OrderFabricationDetailDTO>> getOrderFabricationDetailsByOrderId(@PathVariable Long orderId) {
        List<OrderFabricationDetailDTO> orderFabricationDetails = orderFabricationDetailService.findByOrderId(orderId);
        return ResponseEntity.ok(orderFabricationDetails);
    }

    @GetMapping(GET_FABRICATION_DETAILS_BY_BUILDING)
    public ResponseEntity<List<OrderFabricationDetailDTO>> getOrderFabricationDetailsByBuildingName(@PathVariable String buildingName) {
        List<OrderFabricationDetailDTO> orderFabricationDetails = orderFabricationDetailService.findByBuildingName(buildingName);
        return ResponseEntity.ok(orderFabricationDetails);
    }

    @GetMapping(GET_FABRICATION_DETAILS_BY_DRAWING)
    public ResponseEntity<List<OrderFabricationDetailDTO>> getOrderFabricationDetailsByDrawingNo(@PathVariable String drawingNo) {
        List<OrderFabricationDetailDTO> orderFabricationDetails = orderFabricationDetailService.findByDrawingNo(drawingNo);
        return ResponseEntity.ok(orderFabricationDetails);
    }

    @GetMapping(GET_FABRICATION_DETAILS_BY_ORDER_NUMBER)
    public ResponseEntity<List<OrderFabricationDetailDTO>> getOrderFabricationDetailsByOrderNumber(@PathVariable String orderNumber) {
        List<OrderFabricationDetailDTO> orderFabricationDetails = orderFabricationDetailService.findByOrderNumber(orderNumber);
        return ResponseEntity.ok(orderFabricationDetails);
    }

    @GetMapping(GET_FABRICATION_DETAILS_BY_ERECTION_MKD)
    public ResponseEntity<List<OrderFabricationDetailDTO>> getOrderFabricationDetailsByErectionMkd(@PathVariable String erectionMkd) {
        List<OrderFabricationDetailDTO> orderFabricationDetails = orderFabricationDetailService.findByErectionMkd(erectionMkd);
        return ResponseEntity.ok(orderFabricationDetails);
    }

    @GetMapping(GET_FABRICATION_DETAILS_BY_LINE_AND_ERECTION)
    public ResponseEntity<Map<String, Object>> getOrderFabricationDetailsByLineNumberAndErectionMkd(
            @PathVariable String lineNumber, @PathVariable String erectionMkd) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<OrderFabricationDetailDTO> fabricationDetails = 
                    orderFabricationDetailService.findByLineNumberAndErectionMkd(lineNumber, erectionMkd);
            response.put("status", "success");
            response.put("fabricationDetails", fabricationDetails);
            response.put("count", fabricationDetails.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping(GET_FABRICATION_DETAILS_BY_LINE_ID_AND_ERECTION)
    public ResponseEntity<Map<String, Object>> getOrderFabricationDetailsByLineIdAndErectionMkd(
            @PathVariable Long lineId, @PathVariable String erectionMkd) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<OrderFabricationDetailDTO> fabricationDetails = 
                    orderFabricationDetailService.findByLineIdAndErectionMkd(lineId, erectionMkd);
            response.put("status", "success");
            response.put("fabricationDetails", fabricationDetails);
            response.put("count", fabricationDetails.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping(POST_COPY_ERECTION_MKD)
    public String copyMark(@RequestParam String sourceMarkNo, @RequestParam String newMarkNo) {
        int copiedCount = service.copyMarkNumber(sourceMarkNo, newMarkNo);
        if (copiedCount == 0) {
            return "No records found for mark number:" + sourceMarkNo;
        }
        return "Successfully copied " + copiedCount + " records from " + sourceMarkNo + " to " + newMarkNo;
    }
}
