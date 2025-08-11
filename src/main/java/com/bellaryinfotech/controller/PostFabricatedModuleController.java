package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.PostFabricatedModuleDTO;
import com.bellaryinfotech.model.PostFabricatedModule;
import com.bellaryinfotech.service.PostFabricatedModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/V2.0")
@CrossOrigin(origins = "*")
public class PostFabricatedModuleController {
  
  @Autowired
  private PostFabricatedModuleService service;
  
  @PostMapping("/savePostFabricatedModule/details")
  public ResponseEntity<List<PostFabricatedModule>> savePostFabricatedModule(@RequestBody PostFabricatedModuleDTO dto) {
      try {
          List<PostFabricatedModule> savedModules = service.savePostFabricatedModule(dto);
          return new ResponseEntity<>(savedModules, HttpStatus.CREATED);
      } catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }
  
  @GetMapping("/getPostFabricatedModulesByWorkOrder/details")
  public ResponseEntity<List<PostFabricatedModule>> getPostFabricatedModulesByWorkOrder(@RequestParam String workOrder) {
      try {
          List<PostFabricatedModule> modules = service.getPostFabricatedModulesByWorkOrder(workOrder);
          return new ResponseEntity<>(modules, HttpStatus.OK);
      } catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }
  
  @GetMapping("/getPostFabricatedModulesByRaNumber/details")
  public ResponseEntity<List<PostFabricatedModule>> getPostFabricatedModulesByRaNumber(@RequestParam String raNumber) {
      try {
          List<PostFabricatedModule> modules = service.getPostFabricatedModulesByRaNumber(raNumber);
          return new ResponseEntity<>(modules, HttpStatus.OK);
      } catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }
  
  @GetMapping("/getPostFabricatedModulesByWorkOrderAndRaNumber/details")
  public ResponseEntity<List<PostFabricatedModule>> getPostFabricatedModulesByWorkOrderAndRaNumber(
          @RequestParam String workOrder, @RequestParam String raNumber) {
      try {
          List<PostFabricatedModule> modules = service.getPostFabricatedModulesByWorkOrderAndRaNumber(workOrder, raNumber);
          return new ResponseEntity<>(modules, HttpStatus.OK);
      } catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }
  
  // NEW: Get distinct work orders from post_fabricated_module - NEEDED FOR PRODUCTION WRAPPER
  @GetMapping("/getDistinctWorkOrdersFromPostFabricatedModule/details")
  public ResponseEntity<List<String>> getDistinctWorkOrdersFromPostFabricatedModule() {
      try {
          List<String> workOrders = service.getDistinctWorkOrders();
          return new ResponseEntity<>(workOrders, HttpStatus.OK);
      } catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }
  
  @GetMapping("/getDistinctRaNumbersFromPostFabricatedModule/details")
  public ResponseEntity<List<String>> getDistinctRaNumbersFromPostFabricatedModule() {
      try {
          List<String> raNumbers = service.getDistinctRaNumbers();
          return new ResponseEntity<>(raNumbers, HttpStatus.OK);
      } catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }
  
  @GetMapping("/getDistinctRaNumbersByWorkOrderFromPostFabricatedModule/details")
  public ResponseEntity<List<String>> getDistinctRaNumbersByWorkOrderFromPostFabricatedModule(@RequestParam String workOrder) {
      try {
          List<String> raNumbers = service.getDistinctRaNumbersByWorkOrder(workOrder);
          return new ResponseEntity<>(raNumbers, HttpStatus.OK);
      } catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }

  // NEW: Search post fabricated modules - NEEDED FOR PRODUCTION WRAPPER SEARCH
  @GetMapping("/searchPostFabricatedModules/details")
  public ResponseEntity<List<PostFabricatedModule>> searchPostFabricatedModules(
          @RequestParam String workOrder, @RequestParam String raNumber) {
      try {
          List<PostFabricatedModule> modules = service.getPostFabricatedModulesByWorkOrderAndRaNumber(workOrder, raNumber);
          return new ResponseEntity<>(modules, HttpStatus.OK);
      } catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }
  
  @PutMapping("/updatePostFabricatedModule/details")
  public ResponseEntity<PostFabricatedModule> updatePostFabricatedModule(@RequestParam Long id, @RequestBody PostFabricatedModule updatedModule) {
      try {
          PostFabricatedModule updated = service.updatePostFabricatedModule(id, updatedModule);
          return new ResponseEntity<>(updated, HttpStatus.OK);
      } catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }
  
  @DeleteMapping("/deletePostFabricatedModule/details")
  public ResponseEntity<String> deletePostFabricatedModule(@RequestParam Long id) {
      try {
          service.deletePostFabricatedModule(id);
          return new ResponseEntity<>("PostFabricatedModule deleted successfully", HttpStatus.OK);
      } catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>("Error deleting PostFabricatedModule", HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }
  
  @DeleteMapping("/deletePostFabricatedModulesByWorkOrderAndRaNumber/details")
  public ResponseEntity<String> deletePostFabricatedModulesByWorkOrderAndRaNumber(@RequestParam String workOrder, @RequestParam String raNumber) {
      try {
          service.deletePostFabricatedModulesByWorkOrderAndRaNumber(workOrder, raNumber);
          return new ResponseEntity<>("PostFabricatedModules deleted successfully", HttpStatus.OK);
      } catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>("Error deleting PostFabricatedModules", HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }
}
