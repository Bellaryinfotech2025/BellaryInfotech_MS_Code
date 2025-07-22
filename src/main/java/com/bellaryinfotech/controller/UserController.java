 
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
import com.bellaryinfotech.service.LoginOtpService;
import com.bellaryinfotech.util.JwtUtil;
import com.bellaryinfotech.DTO.UserRegistrationDTO;
import com.bellaryinfotech.DTO.UserResponseDTO;
import com.bellaryinfotech.DTO.EmailVerificationDTO;
import com.bellaryinfotech.DTO.OtpVerificationDTO;
import com.bellaryinfotech.DTO.LoginOtpDTO;
import com.bellaryinfotech.DTO.LoginResponseDTO;
import com.bellaryinfotech.model.User;
import com.bellaryinfotech.repo.UserRepository;
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
    
    @Autowired
    private LoginOtpService loginOtpService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserRepository userRepository;

     
    @PostMapping("/verifyemail/user/auth")
    public ResponseEntity<String> verifyEmail(@Valid @RequestBody EmailVerificationDTO emailVerificationDTO) {
        try {
            logger.info("Email verification request for: {}", emailVerificationDTO.getEmail());
            
            
            if (userService.existsByEmail(emailVerificationDTO.getEmail())) {
                logger.warn("Email already exists: {}", emailVerificationDTO.getEmail());
                return ResponseEntity.badRequest().body("Email already exists");
            }
            
            
            String message = otpService.generateAndSendOtp(emailVerificationDTO.getEmail());
            logger.info("OTP sent successfully for email: {}", emailVerificationDTO.getEmail());
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            logger.error("Failed to send OTP for email {}: {}", emailVerificationDTO.getEmail(), e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to send OTP: " + e.getMessage());
        }
    }

     //registration otp 
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

    // Resend OTP for registration
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

    // Login email verification  
    @PostMapping("/login/verify/email")
    public ResponseEntity<String> loginVerifyEmail(@Valid @RequestBody EmailVerificationDTO emailVerificationDTO) {
        try {
            logger.info("Login email verification request for: {}", emailVerificationDTO.getEmail());
            
            
            Optional<User> userOptional = userRepository.findByEmail(emailVerificationDTO.getEmail());
            if (!userOptional.isPresent()) {
                logger.warn("Email not found: {}", emailVerificationDTO.getEmail());
                return ResponseEntity.badRequest().body("Email not found. Please register first.");
            }
            
            User user = userOptional.get();
            if (!user.getVerified()) {
                logger.warn("Email not verified: {}", emailVerificationDTO.getEmail());
                return ResponseEntity.badRequest().body("Email not verified. Please complete registration first.");
            }
            
            
            String message = loginOtpService.generateAndSendLoginOtp(emailVerificationDTO.getEmail());
            logger.info("Login OTP sent successfully for email: {}", emailVerificationDTO.getEmail());
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            logger.error("Failed to send login OTP for email {}: {}", emailVerificationDTO.getEmail(), e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to send login OTP: " + e.getMessage());
        }
    }

    // Login with OTP verification
    @PostMapping("/login/verify/otp")
    public ResponseEntity<?> loginWithOtp(@Valid @RequestBody LoginOtpDTO loginOtpDTO) {
        try {
            logger.info("Login with OTP request for email: {}", loginOtpDTO.getEmail());
            
            // Verify OTP
            boolean isOtpValid = loginOtpService.verifyLoginOtp(loginOtpDTO.getEmail(), loginOtpDTO.getOtp());
            if (!isOtpValid) {
                logger.warn("Invalid or expired login OTP for email: {}", loginOtpDTO.getEmail());
                return ResponseEntity.badRequest().body("Invalid or expired OTP");
            }
            
            // Get user details
            Optional<User> userOptional = userRepository.findByEmail(loginOtpDTO.getEmail());
            if (!userOptional.isPresent()) {
                logger.warn("User not found for email: {}", loginOtpDTO.getEmail());
                return ResponseEntity.badRequest().body("User not found");
            }
            
            User user = userOptional.get();
            
            // Generate  the JWT token
            String token = jwtUtil.generateToken(user.getEmail(), user.getUsername(), user.getRole());
            
            // response
            LoginResponseDTO response = new LoginResponseDTO(
                token,
                user.getEmail(),
                user.getUsername(),
                user.getFullname(),
                user.getRole(),
                "Login successful"
            );
            
            logger.info("Login successful for email: {}", loginOtpDTO.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Login failed for email {}: {}", loginOtpDTO.getEmail(), e.getMessage(), e);
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }

    // Resend login OTP  
    @PostMapping("/resend/login/otp")
    public ResponseEntity<String> resendLoginOtp(@Valid @RequestBody EmailVerificationDTO emailVerificationDTO) {
        try {
            logger.info("Resend login OTP request for email: {}", emailVerificationDTO.getEmail());
            
            // Check if email exists
            if (!userService.existsByEmail(emailVerificationDTO.getEmail())) {
                logger.warn("Email not found for resend login OTP: {}", emailVerificationDTO.getEmail());
                return ResponseEntity.badRequest().body("Email not found");
            }
            
            String message = loginOtpService.resendLoginOtp(emailVerificationDTO.getEmail());
            logger.info("Login OTP resent successfully for email: {}", emailVerificationDTO.getEmail());
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            logger.error("Failed to resend login OTP for email {}: {}", emailVerificationDTO.getEmail(), e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to resend login OTP: " + e.getMessage());
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

    // Token validation endpoint
    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestBody String token) {
        try {
            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractUsername(token);
                Optional<User> userOptional = userRepository.findByEmail(email);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    LoginResponseDTO response = new LoginResponseDTO(
                        token,
                        user.getEmail(),
                        user.getUsername(),
                        user.getFullname(),
                        user.getRole(),
                        "Token valid"
                    );
                    return ResponseEntity.ok(response);
                }
            }
            return ResponseEntity.status(401).body("Invalid token");
        } catch (Exception e) {
            logger.error("Token validation failed: {}", e.getMessage(), e);
            return ResponseEntity.status(401).body("Invalid token");
        }
    }
}
