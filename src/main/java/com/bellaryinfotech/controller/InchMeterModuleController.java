package com.bellaryinfotech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bellaryinfotech.DTO.InchMeterModuleDTO;
import com.bellaryinfotech.service.InchMeterModuleService;
import java.util.List;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class InchMeterModuleController {

    @Autowired
    private InchMeterModuleService inchMeterModuleService;

    @PostMapping("/createInchMeterModule/details")
    public ResponseEntity<InchMeterModuleDTO> createInchMeterModule(@RequestBody InchMeterModuleDTO inchMeterModuleDTO) {
        try {
            InchMeterModuleDTO createdModule = inchMeterModuleService.createInchMeterModule(inchMeterModuleDTO);
            return new ResponseEntity<>(createdModule, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateInchMeterModule/details")
    public ResponseEntity<InchMeterModuleDTO> updateInchMeterModule(@RequestParam Long id, @RequestBody InchMeterModuleDTO inchMeterModuleDTO) {
        try {
            InchMeterModuleDTO updatedModule = inchMeterModuleService.updateInchMeterModule(id, inchMeterModuleDTO);
            return new ResponseEntity<>(updatedModule, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteInchMeterModule/details")
    public ResponseEntity<String> deleteInchMeterModule(@RequestParam Long id) {
        try {
            inchMeterModuleService.deleteInchMeterModule(id);
            return new ResponseEntity<>("InchMeterModule deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("InchMeterModule not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting InchMeterModule", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getInchMeterModule/details")
    public ResponseEntity<InchMeterModuleDTO> getInchMeterModuleById(@RequestParam Long id) {
        try {
            InchMeterModuleDTO inchMeterModuleDTO = inchMeterModuleService.getInchMeterModuleById(id);
            return new ResponseEntity<>(inchMeterModuleDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllInchMeterModules/details")
    public ResponseEntity<List<InchMeterModuleDTO>> getAllInchMeterModules() {
        try {
            List<InchMeterModuleDTO> modules = inchMeterModuleService.getAllInchMeterModules();
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchInchMeterModulesByMarkNo/details")
    public ResponseEntity<List<InchMeterModuleDTO>> getInchMeterModulesByMarkNo(@RequestParam String markNo) {
        try {
            List<InchMeterModuleDTO> modules = inchMeterModuleService.getInchMeterModulesByMarkNo(markNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getInchMeterModulesByWorkOrder/details")
    public ResponseEntity<List<InchMeterModuleDTO>> getInchMeterModulesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<InchMeterModuleDTO> modules = inchMeterModuleService.getInchMeterModulesByWorkOrder(workOrder);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getInchMeterModulesByWorkOrderAndBuilding/details")
    public ResponseEntity<List<InchMeterModuleDTO>> getInchMeterModulesByWorkOrderAndBuildingName(
            @RequestParam String workOrder, @RequestParam String buildingName) {
        try {
            List<InchMeterModuleDTO> modules = inchMeterModuleService.getInchMeterModulesByWorkOrderAndBuildingName(workOrder, buildingName);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Required endpoints for FabWrapper integration
    @GetMapping("/getDistinctWorkOrdersFromInchMeterModule/details")
    public ResponseEntity<List<String>> getDistinctWorkOrders() {
        try {
            List<String> workOrders = inchMeterModuleService.getDistinctWorkOrders();
            return new ResponseEntity<>(workOrders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctBuildingNamesByWorkOrderFromInchMeterModule/details")
    public ResponseEntity<List<String>> getDistinctBuildingNamesByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> buildingNames = inchMeterModuleService.getDistinctBuildingNamesByWorkOrder(workOrder);
            return new ResponseEntity<>(buildingNames, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctDrawingNosByWorkOrderAndBuildingFromInchMeterModule/details")
    public ResponseEntity<List<String>> getDistinctDrawingNosByWorkOrderAndBuilding(@RequestParam String workOrder, @RequestParam String buildingName) {
        try {
            List<String> drawingNos = inchMeterModuleService.getDistinctDrawingNosByWorkOrderAndBuildingName(workOrder, buildingName);
            return new ResponseEntity<>(drawingNos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctMarkNosByWorkOrderBuildingDrawingFromInchMeterModule/details")
    public ResponseEntity<List<String>> getDistinctMarkNosByWorkOrderBuildingDrawing(@RequestParam String workOrder, @RequestParam String buildingName, @RequestParam String drawingNo) {
        try {
            List<String> markNos = inchMeterModuleService.getDistinctMarkNosByWorkOrderAndBuildingNameAndDrawingNo(workOrder, buildingName, drawingNo);
            return new ResponseEntity<>(markNos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDistinctServiceDescriptionsByWorkOrderFromInchMeterModule/details")
    public ResponseEntity<List<String>> getDistinctServiceDescriptionsByWorkOrder(@RequestParam String workOrder) {
        try {
            List<String> serviceDescriptions = inchMeterModuleService.getDistinctServiceDescriptionsByWorkOrder(workOrder);
            return new ResponseEntity<>(serviceDescriptions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint for InchMeterFabrication to get data by work order, building, drawing, mark
    @GetMapping("/getInchMeterModulesByWorkOrderBuildingDrawingMark/details")
    public ResponseEntity<List<InchMeterModuleDTO>> getInchMeterModulesByWorkOrderBuildingDrawingMark(
            @RequestParam String workOrder,
            @RequestParam String buildingName,
            @RequestParam String drawingNo,
            @RequestParam String markNo) {
        try {
            List<InchMeterModuleDTO> modules = inchMeterModuleService.getInchMeterModulesByWorkOrderBuildingDrawingMark(workOrder, buildingName, drawingNo, markNo);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteInchMeterModulesByMarkNo/details")
    public ResponseEntity<String> deleteInchMeterModulesByMarkNo(@RequestParam String markNo) {
        try {
            inchMeterModuleService.deleteInchMeterModulesByMarkNo(markNo);
            return new ResponseEntity<>("InchMeterModules deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting InchMeterModules", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
