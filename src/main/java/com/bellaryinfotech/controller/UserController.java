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
import com.bellaryinfotech.service.OtpService;
import com.bellaryinfotech.DTO.UserRegistrationDTO;
import com.bellaryinfotech.DTO.UserLoginDTO;
import com.bellaryinfotech.DTO.UserResponseDTO;
import com.bellaryinfotech.DTO.EmailVerificationDTO;
import com.bellaryinfotech.DTO.OtpVerificationDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private OtpService otpService;

    
    @PostMapping("/verifyemail/user/auth")
    public ResponseEntity<String> verifyEmail(@Valid @RequestBody EmailVerificationDTO emailVerificationDTO) {
        try {
            logger.info("Email verification request for: {}", emailVerificationDTO.getEmail());
            
            // Check if email already exists
            if (userService.existsByEmail(emailVerificationDTO.getEmail())) {
                logger.warn("Email already exists: {}", emailVerificationDTO.getEmail());
                return ResponseEntity.badRequest().body("Email already exists");
            }
            
            // Generate and send OTP
            String message = otpService.generateAndSendOtp(emailVerificationDTO.getEmail());
            logger.info("OTP sent successfully for email: {}", emailVerificationDTO.getEmail());
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            logger.error("Failed to send OTP for email {}: {}", emailVerificationDTO.getEmail(), e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to send OTP: " + e.getMessage());
        }
    }

    // OTP verification endpoint
    @PostMapping("/verifyotp/user/auth")
    public ResponseEntity<String> verifyOtp(@Valid @RequestBody OtpVerificationDTO otpVerificationDTO) {
        try {
            logger.info("OTP verification request for email: {}", otpVerificationDTO.getEmail());
            
            boolean isValid = otpService.verifyOtp(otpVerificationDTO.getEmail(), otpVerificationDTO.getOtp());
            if (isValid) {
                logger.info("OTP verified successfully for email: {}", otpVerificationDTO.getEmail());
                return ResponseEntity.ok("OTP verified successfully");
            } else {
                logger.warn("Invalid or expired OTP for email: {}", otpVerificationDTO.getEmail());
                return ResponseEntity.badRequest().body("Invalid or expired OTP");
            }
        } catch (Exception e) {
            logger.error("OTP verification failed for email {}: {}", otpVerificationDTO.getEmail(), e.getMessage(), e);
            return ResponseEntity.badRequest().body("OTP verification failed: " + e.getMessage());
        }
    }

    // Resend OTP endpoint
    @PostMapping("/resendotp/user/auth")
    public ResponseEntity<String> resendOtp(@Valid @RequestBody EmailVerificationDTO emailVerificationDTO) {
        try {
            logger.info("Resend OTP request for email: {}", emailVerificationDTO.getEmail());
            
            String message = otpService.resendOtp(emailVerificationDTO.getEmail());
            logger.info("OTP resent successfully for email: {}", emailVerificationDTO.getEmail());
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            logger.error("Failed to resend OTP for email {}: {}", emailVerificationDTO.getEmail(), e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to resend OTP: " + e.getMessage());
        }
    }

    // Register API
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        try {
            logger.info("Registration request for email: {}", userRegistrationDTO.getEmail());
            
            // Check if email already exists
            if (userService.existsByEmail(userRegistrationDTO.getEmail())) {
                logger.warn("Registration failed - email already exists: {}", userRegistrationDTO.getEmail());
                return ResponseEntity.badRequest().body("Email already exists");
            }
            
            // Check if username already exists
            if (userService.existsByUsername(userRegistrationDTO.getUsername())) {
                logger.warn("Registration failed - username already exists: {}", userRegistrationDTO.getUsername());
                return ResponseEntity.badRequest().body("Username already exists");
            }
            
            UserResponseDTO registeredUser = userService.registerUser(userRegistrationDTO);
            if (registeredUser != null) {
                logger.info("User registered successfully: {}", userRegistrationDTO.getEmail());
                return ResponseEntity.ok("Registration successful");
            }
            return ResponseEntity.badRequest().body("Registration failed");
        } catch (Exception e) {
            logger.error("Registration failed for email {}: {}", userRegistrationDTO.getEmail(), e.getMessage(), e);
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    // Login API
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            logger.info("Login request for email: {}", userLoginDTO.getEmail());
            
            Optional<UserResponseDTO> user = userService.loginUser(userLoginDTO);
            if (user.isPresent()) {
                logger.info("Login successful for email: {}", userLoginDTO.getEmail());
                return ResponseEntity.ok(user.get());
            }
            logger.warn("Login failed - invalid credentials for email: {}", userLoginDTO.getEmail());
            return ResponseEntity.status(401).body("Invalid credentials");
        } catch (Exception e) {
            logger.error("Login failed for email {}: {}", userLoginDTO.getEmail(), e.getMessage(), e);
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }
}
