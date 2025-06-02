package com.bellaryinfotech.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bellaryinfotech.DTO.BitsLinesDto;
import com.bellaryinfotech.service.BitsLinesService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class BitsLinesController {

    @Autowired
    private BitsLinesService linesService;

    public static final String GET_ALL_LINES = "/getAllBitsLines/details";
    public static final String GET_LINE_BY_ID = "/getBitsLineById/details";
    public static final String CREATE_LINE = "/createBitsLine/details";
    public static final String UPDATE_LINE = "/updateBitsLine/details";
    public static final String DELETE_LINE = "/deleteBitsLine/details";
    public static final String SEARCH_BY_SERNO = "/searchBitsLinesBySerNo/details";
    public static final String SEARCH_BY_SERVICECODE = "/searchBitsLinesByServiceCode/details";
    public static final String SEARCH_BY_SERVICEDESC = "/searchBitsLinesByServiceDesc/details";
    
    // New endpoint to get service orders by work order
    public static final String GET_LINES_BY_WORK_ORDER = "/getBitsLinesByWorkOrder/details";
    
    // Debug endpoint
    public static final String DEBUG_LINES = "/debugBitsLines/details";

    private static final Logger LOG = LoggerFactory.getLogger(BitsLinesController.class);

    @RequestMapping(value = GET_ALL_LINES, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllLines() {
        LOG.info("Fetching all bits lines");
        List<BitsLinesDto> lines = linesService.getAllLines();
        return ResponseEntity.ok(lines);
    }

    @RequestMapping(value = GET_LINE_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getLineById(@RequestParam Long id) {
        LOG.info("Fetching bits line by ID: {}", id);
        Optional<BitsLinesDto> line = linesService.getLineById(id);
        return line.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = CREATE_LINE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createLine(@RequestBody BitsLinesDto lineDto) {
        LOG.info("Creating a new bits line with data: {}", lineDto);
        try {
            BitsLinesDto createdLine = linesService.createLine(lineDto);
            LOG.info("Created bits line: {}", createdLine);
            return ResponseEntity.status(201).body(createdLine);
        } catch (Exception e) {
            LOG.error("Error creating bits line", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = UPDATE_LINE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateLine(@RequestParam Long id, @RequestBody BitsLinesDto lineDto) {
        LOG.info("Updating bits line with ID: {}", id);
        try {
            BitsLinesDto updatedLine = linesService.updateLine(id, lineDto);
            return ResponseEntity.ok(updatedLine);
        } catch (RuntimeException e) {
            LOG.error("Bits line not found for update: {}", id, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            LOG.error("Error updating bits line", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = DELETE_LINE)
    public ResponseEntity<?> deleteLine(@RequestParam Long id) {
        LOG.info("Deleting bits line with ID: {}", id);
        try {
            linesService.deleteLine(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error deleting bits line", e);
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = SEARCH_BY_SERNO, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchBySerNo(@RequestParam String serNo) {
        LOG.info("Searching bits lines by serNo: {}", serNo);
        List<BitsLinesDto> lines = linesService.searchBySerNo(serNo);
        return ResponseEntity.ok(lines);
    }

    @RequestMapping(value = SEARCH_BY_SERVICECODE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchByServiceCode(@RequestParam String serviceCode) {
        LOG.info("Searching bits lines by serviceCode: {}", serviceCode);
        List<BitsLinesDto> lines = linesService.searchByServiceCode(serviceCode);
        return ResponseEntity.ok(lines);
    }

    @RequestMapping(value = SEARCH_BY_SERVICEDESC, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchByServiceDesc(@RequestParam String serviceDesc) {
        LOG.info("Searching bits lines by serviceDesc: {}", serviceDesc);
        List<BitsLinesDto> lines = linesService.searchByServiceDesc(serviceDesc);
        return ResponseEntity.ok(lines);
    }

    // New endpoint to get service orders by work order
    @RequestMapping(value = GET_LINES_BY_WORK_ORDER, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getLinesByWorkOrder(@RequestParam String workOrder) {
        LOG.info("Fetching bits lines by work order: {}", workOrder);
        try {
            List<BitsLinesDto> lines = linesService.getLinesByWorkOrder(workOrder);
            LOG.info("Found {} lines for work order: {}", lines.size(), workOrder);
            return ResponseEntity.ok(lines);
        } catch (Exception e) {
            LOG.error("Error fetching bits lines by work order: {}", workOrder, e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Debug endpoint to see all lines with their attribute values
    @RequestMapping(value = DEBUG_LINES, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> debugLines() {
        LOG.info("Debug: Fetching all bits lines with attributes");
        try {
            List<BitsLinesDto> lines = linesService.getAllLinesWithAttributes();
            LOG.info("Debug: Found {} total lines", lines.size());
            for (BitsLinesDto line : lines) {
                LOG.info("Debug: Line ID: {}, SerNo: {}, ServiceCode: {}, WorkOrder Ref: {}", 
                    line.getLineId(), line.getSerNo(), line.getServiceCode(), line.getWorkOrderRef());
            }
            return ResponseEntity.ok(lines);
        } catch (Exception e) {
            LOG.error("Error in debug endpoint", e);
            return ResponseEntity.badRequest().build();
        }
    }
}