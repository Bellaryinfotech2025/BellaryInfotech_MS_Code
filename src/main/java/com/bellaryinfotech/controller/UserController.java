package com.bellaryinfotech.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bellaryinfotech.service.UserService;
import com.bellaryinfotech.DTO.UserRegistrationDTO;
import com.bellaryinfotech.DTO.UserLoginDTO;
import com.bellaryinfotech.DTO.UserResponseDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
//change with frontend ip address or domain where frontend is running
//@CrossOrigin(origins = "http://dev.bellaryinfotech.com", allowedHeaders = "*", methods = {org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
public class UserController {
    
    @Autowired
    private UserService userService;

    // Register API
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        try {
            // Check if email already exists
            if (userService.existsByEmail(userRegistrationDTO.getEmail())) {
                return ResponseEntity.badRequest().body("Email already exists");
            }
            
            // Check if username already exists
            if (userService.existsByUsername(userRegistrationDTO.getUsername())) {
                return ResponseEntity.badRequest().body("Username already exists");
            }
            
            UserResponseDTO registeredUser = userService.registerUser(userRegistrationDTO);
            if (registeredUser != null) {
                return ResponseEntity.ok("Registration successful");
            }
            return ResponseEntity.badRequest().body("Registration failed");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    // Login API
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            Optional<UserResponseDTO> user = userService.loginUser(userLoginDTO);
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            }
            return ResponseEntity.status(401).body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }
}
