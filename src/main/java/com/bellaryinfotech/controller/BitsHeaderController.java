package com.bellaryinfotech.controller;

 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bellaryinfotech.DTO.BitsHeaderDto;
import com.bellaryinfotech.service.BitsHeaderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class BitsHeaderController {

    @Autowired
    private BitsHeaderService headerService;

    public static final String GET_ALL_HEADERS = "/getAllBitsHeaders/details";
    public static final String GET_HEADER_BY_ID = "/getBitsHeaderById/details";
    public static final String CREATE_HEADER = "/createBitsHeader/details";
    public static final String UPDATE_HEADER = "/updateBitsHeader/details";
    public static final String DELETE_HEADER = "/deleteBitsHeader/details";
    public static final String SEARCH_BY_WORKORDER = "/searchBitsHeadersByWorkOrder/details";
    public static final String SEARCH_BY_PLANTLOCATION = "/searchBitsHeadersByPlantLocation/details";
    public static final String SEARCH_BY_DEPARTMENT = "/searchBitsHeadersByDepartment/details";
    public static final String SEARCH_BY_WORKLOCATION = "/searchBitsHeadersByWorkLocation/details";

    private static final Logger LOG = LoggerFactory.getLogger(BitsHeaderController.class);

    @RequestMapping(value = GET_ALL_HEADERS, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllHeaders() {
        LOG.info("Fetching all bits headers");
        List<BitsHeaderDto> headers = headerService.getAllHeaders();
        return ResponseEntity.ok(headers);
    }

    @RequestMapping(value = GET_HEADER_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getHeaderById(@RequestParam Long id) {
        LOG.info("Fetching bits header by ID: {}", id);
        Optional<BitsHeaderDto> header = headerService.getHeaderById(id);
        return header.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = CREATE_HEADER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createHeader(@RequestBody BitsHeaderDto headerDto) {
        LOG.info("Creating a new bits header");
        try {
            BitsHeaderDto createdHeader = headerService.createHeader(headerDto);
            return ResponseEntity.status(201).body(createdHeader);
        } catch (Exception e) {
            LOG.error("Error creating bits header", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = UPDATE_HEADER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateHeader(@RequestParam Long id, @RequestBody BitsHeaderDto headerDto) {
        LOG.info("Updating bits header with ID: {}", id);
        try {
            BitsHeaderDto updatedHeader = headerService.updateHeader(id, headerDto);
            return ResponseEntity.ok(updatedHeader);
        } catch (RuntimeException e) {
            LOG.error("Bits header not found for update: {}", id, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            LOG.error("Error updating bits header", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = DELETE_HEADER)
    public ResponseEntity<?> deleteHeader(@RequestParam Long id) {
        LOG.info("Deleting bits header with ID: {}", id);
        try {
            headerService.deleteHeader(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error deleting bits header", e);
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = SEARCH_BY_WORKORDER, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchByWorkOrder(@RequestParam String workOrder) {
        LOG.info("Searching bits headers by work order: {}", workOrder);
        List<BitsHeaderDto> headers = headerService.searchByWorkOrder(workOrder);
        return ResponseEntity.ok(headers);
    }

    @RequestMapping(value = SEARCH_BY_PLANTLOCATION, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchByPlantLocation(@RequestParam String plantLocation) {
        LOG.info("Searching bits headers by plant location: {}", plantLocation);
        List<BitsHeaderDto> headers = headerService.searchByPlantLocation(plantLocation);
        return ResponseEntity.ok(headers);
    }

    @RequestMapping(value = SEARCH_BY_DEPARTMENT, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchByDepartment(@RequestParam String department) {
        LOG.info("Searching bits headers by department: {}", department);
        List<BitsHeaderDto> headers = headerService.searchByDepartment(department);
        return ResponseEntity.ok(headers);
    }

    @RequestMapping(value = SEARCH_BY_WORKLOCATION, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> searchByWorkLocation(@RequestParam String workLocation) {
        LOG.info("Searching bits headers by work location: {}", workLocation);
        List<BitsHeaderDto> headers = headerService.searchByWorkLocation(workLocation);
        return ResponseEntity.ok(headers);
    }
}
