package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.RawMaterialEntryDTO;
import com.bellaryinfotech.model.RawMaterialEntry;
import com.bellaryinfotech.service.RawMaterialEntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class RawMaterialEntryController {

        private static final Logger LOG = LoggerFactory.getLogger(RawMaterialEntryController.class);

        @Autowired
    private RawMaterialEntryService rawMaterialEntryService;

        @PostMapping(value = "/rawmaterialentry", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RawMaterialEntry>> createRawMaterialEntry(@RequestBody RawMaterialEntryDTO rawMaterialEntryDTO) {
        try {
            LOG.info("Creating raw material entry with data: {}", rawMaterialEntryDTO);
            List<RawMaterialEntry> savedEntries = rawMaterialEntryService.saveRawMaterialEntry(rawMaterialEntryDTO);
            LOG.info("Successfully created {} raw material entries", savedEntries.size());
            return new ResponseEntity<>(savedEntries, HttpStatus.CREATED);
        } catch (Exception e) {
            LOG.error("Error creating raw material entry", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @GetMapping(value = "/rawmaterialentry", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RawMaterialEntry>> getAllRawMaterialEntries() {
        try {
            LOG.info("Fetching all raw material entries");
            List<RawMaterialEntry> entries = rawMaterialEntryService.getAllRawMaterialEntries();
            LOG.info("Successfully fetched {} raw material entries", entries.size());
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error fetching raw material entries", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @GetMapping(value = "/rawmaterialentry/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RawMaterialEntry> getRawMaterialEntryById(@PathVariable Long id) {
        try {
            LOG.info("Fetching raw material entry by ID: {}", id);
            RawMaterialEntry entry = rawMaterialEntryService.getRawMaterialEntryById(id);
            if (entry != null) {
                LOG.info("Successfully fetched raw material entry with ID: {}", id);
                return new ResponseEntity<>(entry, HttpStatus.OK);
            } else {
                LOG.warn("Raw material entry not found with ID: {}", id);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.error("Error fetching raw material entry by ID: {}", id, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @GetMapping(value = "/rawmaterialentry/workorder/{workOrder}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RawMaterialEntry>> getRawMaterialEntriesByWorkOrder(@PathVariable String workOrder) {
        try {
            LOG.info("Fetching raw material entries by work order: {}", workOrder);
            List<RawMaterialEntry> entries = rawMaterialEntryService.getRawMaterialEntriesByWorkOrder(workOrder);
            LOG.info("Successfully fetched {} raw material entries for work order: {}", entries.size(), workOrder);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error fetching raw material entries by work order: {}", workOrder, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @GetMapping(value = "/rawmaterialentry/section/{section}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RawMaterialEntry>> getRawMaterialEntriesBySection(@PathVariable String section) {
        try {
            LOG.info("Fetching raw material entries by section: {}", section);
            List<RawMaterialEntry> entries = rawMaterialEntryService.getRawMaterialEntriesBySection(section);
            LOG.info("Successfully fetched {} raw material entries for section: {}", entries.size(), section);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error fetching raw material entries by section: {}", section, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        // NEW: Get entries by section code
    @GetMapping(value = "/rawmaterialentry/sectioncode/{sectionCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RawMaterialEntry>> getRawMaterialEntriesBySectionCode(@PathVariable String sectionCode) {
        try {
            LOG.info("Fetching raw material entries by section code: {}", sectionCode);
            List<RawMaterialEntry> entries = rawMaterialEntryService.getRawMaterialEntriesBySectionCode(sectionCode);
            LOG.info("Successfully fetched {} raw material entries for section code: {}", entries.size(), sectionCode);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error fetching raw material entries by section code: {}", sectionCode, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        // NEW: Get entries by receipt type
    @GetMapping(value = "/rawmaterialentry/receipttype/{receiptType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RawMaterialEntry>> getRawMaterialEntriesByReceiptType(@PathVariable String receiptType) {
        try {
            LOG.info("Fetching raw material entries by receipt type: {}", receiptType);
            List<RawMaterialEntry> entries = rawMaterialEntryService.getRawMaterialEntriesByReceiptType(receiptType);
            LOG.info("Successfully fetched {} raw material entries for receipt type: {}", entries.size(), receiptType);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error fetching raw material entries by receipt type: {}", receiptType, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @PutMapping(value = "/rawmaterialentry/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RawMaterialEntry> updateRawMaterialEntry(@PathVariable Long id, @RequestBody RawMaterialEntry rawMaterialEntry) {
        try {
            LOG.info("Updating raw material entry with ID: {}", id);
            RawMaterialEntry updatedEntry = rawMaterialEntryService.updateRawMaterialEntry(id, rawMaterialEntry);
            if (updatedEntry != null) {
                LOG.info("Successfully updated raw material entry with ID: {}", id);
                return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
            } else {
                LOG.warn("Raw material entry not found with ID: {}", id);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.error("Error updating raw material entry with ID: {}", id, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @DeleteMapping(value = "/rawmaterialentry/{id}")
    public ResponseEntity<HttpStatus> deleteRawMaterialEntry(@PathVariable Long id) {
        try {
            LOG.info("Deleting raw material entry with ID: {}", id);
            rawMaterialEntryService.deleteRawMaterialEntry(id);
            LOG.info("Successfully deleted raw material entry with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            LOG.error("Error deleting raw material entry with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @GetMapping(value = "/rawmaterialentry/workorders/distinct", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDistinctWorkOrders() {
        try {
            LOG.info("Fetching distinct work orders");
            List<String> workOrders = rawMaterialEntryService.getDistinctWorkOrders();
            LOG.info("Successfully fetched {} distinct work orders", workOrders.size());
            return new ResponseEntity<>(workOrders, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error fetching distinct work orders", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @GetMapping(value = "/rawmaterialentry/sections/distinct", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDistinctSections() {
        try {
            LOG.info("Fetching distinct sections");
            List<String> sections = rawMaterialEntryService.getDistinctSections();
            LOG.info("Successfully fetched {} distinct sections", sections.size());
            return new ResponseEntity<>(sections, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error fetching distinct sections", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        // NEW: Get distinct section codes
    @GetMapping(value = "/rawmaterialentry/sectioncodes/distinct", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDistinctSectionCodes() {
        try {
            LOG.info("Fetching distinct section codes");
            List<String> sectionCodes = rawMaterialEntryService.getDistinctSectionCodes();
            LOG.info("Successfully fetched {} distinct section codes", sectionCodes.size());
            return new ResponseEntity<>(sectionCodes, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error fetching distinct section codes", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @GetMapping(value = "/rawmaterialentry/uoms/distinct", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDistinctUoms() {
        try {
            LOG.info("Fetching distinct UOMs");
            List<String> uoms = rawMaterialEntryService.getDistinctUoms();
            LOG.info("Successfully fetched {} distinct UOMs", uoms.size());
            return new ResponseEntity<>(uoms, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error fetching distinct UOMs", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}