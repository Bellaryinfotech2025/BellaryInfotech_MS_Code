package com.bellaryinfotech.controller;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bellaryinfotech.DTO.WorkOrderOutDetailsDTO;
import com.bellaryinfotech.DTO.WorkOrderOutEntryDTO;
import com.bellaryinfotech.service.WorkOrderOutEntryService;
import com.bellaryinfotech.serviceimpl.WorkOrderOutEntryServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/V2.0/workOrderOut")
@CrossOrigin(origins = "*")
public class WorkOrderOutEntryController {
    
    @Autowired
    private WorkOrderOutEntryService workOrderOutEntryService;
    
    @Autowired
    private WorkOrderOutEntryServiceImpl workOrderOutEntryServiceImpl;
    
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkOrderOutEntryDTO> saveWorkOrderOutEntry(@RequestBody WorkOrderOutEntryDTO workOrderOutEntryDTO) {
        try {
            System.out.println("Received work order out entry data: " + workOrderOutEntryDTO.getClientName());
            System.out.println("Reference work order: " + workOrderOutEntryDTO.getReferenceWorkOrder());
            System.out.println("Service orders count: " + (workOrderOutEntryDTO.getServiceOrders() != null ? workOrderOutEntryDTO.getServiceOrders().size() : 0));
            
            WorkOrderOutEntryDTO savedEntry = workOrderOutEntryService.saveWorkOrderOutEntry(workOrderOutEntryDTO);
            return ResponseEntity.ok(savedEntry);
        } catch (Exception e) {
            System.err.println("Error saving work order out entry: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @PutMapping(value = "/updateByReferenceWorkOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkOrderOutEntryDTO> updateWorkOrderOutEntryByReference(
            @RequestParam String referenceWorkOrder, 
            @RequestBody WorkOrderOutEntryDTO workOrderOutEntryDTO) {
        try {
            System.out.println("Updating work order out entry with reference: " + referenceWorkOrder);
            
            // First delete existing entries for this reference work order
            workOrderOutEntryServiceImpl.deleteByReferenceWorkOrder(referenceWorkOrder);
            
            // Then save the updated data
            WorkOrderOutEntryDTO updatedEntry = workOrderOutEntryService.saveWorkOrderOutEntry(workOrderOutEntryDTO);
            return ResponseEntity.ok(updatedEntry);
        } catch (RuntimeException e) {
            System.err.println("Error updating work order out entry: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.err.println("Error updating work order out entry: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping(value = "/getUniqueWorkOrders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutEntryDTO>> getUniqueWorkOrders() {
        try {
            System.out.println("Fetching unique work order out entries...");
            List<WorkOrderOutEntryDTO> entries = workOrderOutEntryServiceImpl.getUniqueWorkOrders();
            System.out.println("Found " + entries.size() + " unique work order out entries");
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            System.err.println("Error fetching unique work order out entries: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping(value = "/searchUniqueWorkOrders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutEntryDTO>> searchUniqueWorkOrders(@RequestParam String searchTerm) {
        try {
            System.out.println("Searching unique work order out entries with term: " + searchTerm);
            List<WorkOrderOutEntryDTO> entries = workOrderOutEntryServiceImpl.searchUniqueWorkOrders(searchTerm);
            System.out.println("Found " + entries.size() + " matching unique entries");
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            System.err.println("Error searching unique work order out entries: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping(value = "/getWorkOrderDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkOrderOutDetailsDTO> getWorkOrderDetails(@RequestParam String referenceWorkOrder) {
        try {
            System.out.println("Fetching work order details for: " + referenceWorkOrder);
            WorkOrderOutDetailsDTO details = workOrderOutEntryServiceImpl.getWorkOrderDetails(referenceWorkOrder);
            System.out.println("Found work order details with " + details.getServiceLines().size() + " service lines");
            return ResponseEntity.ok(details);
        } catch (Exception e) {
            System.err.println("Error fetching work order details: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @DeleteMapping(value = "/deleteByReferenceWorkOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteByReferenceWorkOrder(@RequestParam String referenceWorkOrder) {
        try {
            System.out.println("Deleting work order out entries for reference: " + referenceWorkOrder);
            workOrderOutEntryServiceImpl.deleteByReferenceWorkOrder(referenceWorkOrder);
            return ResponseEntity.ok("Work order out entries deleted successfully");
        } catch (Exception e) {
            System.err.println("Error deleting work order out entries: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting work order out entries");
        }
    }
    
    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkOrderOutEntryDTO> updateWorkOrderOutEntry(
            @PathVariable Long id, 
            @RequestBody WorkOrderOutEntryDTO workOrderOutEntryDTO) {
        try {
            System.out.println("Updating work order out entry with ID: " + id);
            WorkOrderOutEntryDTO updatedEntry = workOrderOutEntryService.updateWorkOrderOutEntry(id, workOrderOutEntryDTO);
            return ResponseEntity.ok(updatedEntry);
        } catch (RuntimeException e) {
            System.err.println("Error updating work order out entry: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.err.println("Error updating work order out entry: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkOrderOutEntryDTO> getWorkOrderOutEntryById(@PathVariable Long id) {
        try {
            WorkOrderOutEntryDTO entry = workOrderOutEntryService.getWorkOrderOutEntryById(id);
            return ResponseEntity.ok(entry);
        } catch (RuntimeException e) {
            System.err.println("Work order out entry not found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.err.println("Error fetching work order out entry: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutEntryDTO>> getAllWorkOrderOutEntries() {
        try {
            System.out.println("Fetching all work order out entries...");
            List<WorkOrderOutEntryDTO> entries = workOrderOutEntryService.getAllWorkOrderOutEntries();
            System.out.println("Found " + entries.size() + " work order out entries");
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            System.err.println("Error fetching all work order out entries: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteWorkOrderOutEntry(@PathVariable Long id) {
        try {
            System.out.println("Deleting work order out entry with ID: " + id);
            workOrderOutEntryService.deleteWorkOrderOutEntry(id);
            return ResponseEntity.ok("Work order out entry deleted successfully");
        } catch (RuntimeException e) {
            System.err.println("Work order out entry not found for deletion: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Work order out entry not found");
        } catch (Exception e) {
            System.err.println("Error deleting work order out entry: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting work order out entry");
        }
    }
    
    @GetMapping(value = "/searchByClient", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutEntryDTO>> searchByClientName(@RequestParam String clientName) {
        try {
            System.out.println("Searching work order out entries by client name: " + clientName);
            List<WorkOrderOutEntryDTO> entries = workOrderOutEntryService.searchByClientName(clientName);
            System.out.println("Found " + entries.size() + " entries for client: " + clientName);
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            System.err.println("Error searching by client name: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping(value = "/searchByWorkOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutEntryDTO>> searchByReferenceWorkOrder(@RequestParam String referenceWorkOrder) {
        try {
            System.out.println("Searching work order out entries by reference work order: " + referenceWorkOrder);
            List<WorkOrderOutEntryDTO> entries = workOrderOutEntryService.searchByReferenceWorkOrder(referenceWorkOrder);
            System.out.println("Found " + entries.size() + " entries for work order: " + referenceWorkOrder);
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            System.err.println("Error searching by reference work order: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping(value = "/searchByLocation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutEntryDTO>> searchByWorkLocation(@RequestParam String workLocation) {
        try {
            System.out.println("Searching work order out entries by work location: " + workLocation);
            List<WorkOrderOutEntryDTO> entries = workOrderOutEntryService.searchByWorkLocation(workLocation);
            System.out.println("Found " + entries.size() + " entries for location: " + workLocation);
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            System.err.println("Error searching by work location: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping(value = "/searchBySubAgency", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutEntryDTO>> searchBySubAgencyName(@RequestParam String subAgencyName) {
        try {
            System.out.println("Searching work order out entries by sub agency name: " + subAgencyName);
            List<WorkOrderOutEntryDTO> entries = workOrderOutEntryService.searchBySubAgencyName(subAgencyName);
            System.out.println("Found " + entries.size() + " entries for sub agency: " + subAgencyName);
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            System.err.println("Error searching by sub agency name: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping(value = "/getServiceLines/{referenceWorkOrder}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOrderOutEntryDTO>> getServiceLinesByWorkOrder(@PathVariable String referenceWorkOrder) {
        try {
            System.out.println("Fetching service lines for work order: " + referenceWorkOrder);
            List<WorkOrderOutEntryDTO> entries = workOrderOutEntryServiceImpl.getServiceLinesByWorkOrder(referenceWorkOrder);
            System.out.println("Found " + entries.size() + " service lines for work order: " + referenceWorkOrder);
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            System.err.println("Error fetching service lines: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
