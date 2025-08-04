package com.bellaryinfotech.controller;

import com.bellaryinfotech.DTO.*;
import com.bellaryinfotech.model.User;
import com.bellaryinfotech.repo.UserRepository;
import com.bellaryinfotech.service.AdminDashboardService;
import com.bellaryinfotech.service.LoginOtpService;
import com.bellaryinfotech.service.OtpService;
import com.bellaryinfotech.service.PasswordResetService;
import com.bellaryinfotech.service.UserService;
import com.bellaryinfotech.util.JwtUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(UserController.BASE_URL)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@Autowired 
	private PasswordResetService passwordResetService;
	
	
	@Autowired
	private AdminDashboardService adminDashboardService;
	
	
	

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public static final String BASE_URL = "/api";
    public static final String VERIFY_EMAIL = "/verify/user/email";
    public static final String VERIFY_OTP = "/verify/user/otp";
    public static final String RESEND_OTP = "/resend/user/otp";
    public static final String LOGIN_VERIFY_EMAIL = "/login/verify/email";
    public static final String LOGIN_USER_OTP = "/login/user/otp";
    public static final String RESEND_LOGIN_OTP = "/resend/login/otp";
    public static final String REGISTER_USER = "/register";
    public static final String VALIDATE_TOKEN = "/validate/token";
    
    //password reset backend code
    public static final String LOGIN_PASSWORD = "/login/password";
    public static final String FORGOT_PASSWORD = "/forgot-password";
    public static final String RESET_PASSWORD = "/reset-password";

    @Autowired private UserService userService;
    @Autowired private OtpService otpService;
    @Autowired private LoginOtpService loginOtpService;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserRepository userRepository;

    @PostMapping(value = VERIFY_EMAIL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> verifyEmail(@Valid @RequestBody EmailVerificationDTO emailVerificationDTO) {
        try {
            logger.info("Email verification request for: {}", emailVerificationDTO.getEmail());

            if (userService.existsByEmail(emailVerificationDTO.getEmail())) {
                return ResponseEntity.badRequest().body("Email already exists");
            }

            String message = otpService.generateAndSendOtp(emailVerificationDTO.getEmail());
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send OTP: " + e.getMessage());
        }
    }

    @PostMapping(value = VERIFY_OTP, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> verifyOtp(@Valid @RequestBody OtpVerificationDTO otpVerificationDTO) {
        try {
            boolean isValid = otpService.verifyOtp(otpVerificationDTO.getEmail(), otpVerificationDTO.getOtp());
            return isValid ? ResponseEntity.ok("OTP verified successfully")
                           : ResponseEntity.badRequest().body("Invalid or expired OTP");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("OTP verification failed: " + e.getMessage());
        }
    }

    @PostMapping(value = RESEND_OTP, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> resendOtp(@Valid @RequestBody EmailVerificationDTO emailVerificationDTO) {
        try {
            String message = otpService.resendOtp(emailVerificationDTO.getEmail());
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to resend OTP: " + e.getMessage());
        }
    }

    @PostMapping(value = LOGIN_VERIFY_EMAIL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> loginVerifyEmail(@Valid @RequestBody EmailVerificationDTO emailVerificationDTO) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(emailVerificationDTO.getEmail());
            if (!userOptional.isPresent()) {
                return ResponseEntity.badRequest().body("Email not found. Please register first.");
            }

            if (!userOptional.get().getVerified()) {
                return ResponseEntity.badRequest().body("Email not verified. Please complete registration first.");
            }

            String message = loginOtpService.generateAndSendLoginOtp(emailVerificationDTO.getEmail());
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send login OTP: " + e.getMessage());
        }
    }

 // Updated LOGIN_USER_OTP method for admin dashboard 
    @PostMapping(value = LOGIN_USER_OTP, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginWithOtp(@Valid @RequestBody LoginOtpDTO loginOtpDTO) {
        try {
            if (!loginOtpService.verifyLoginOtp(loginOtpDTO.getEmail(), loginOtpDTO.getOtp())) {
                return ResponseEntity.badRequest().body("Invalid or expired OTP");
            }

            Optional<User> userOptional = userRepository.findByEmail(loginOtpDTO.getEmail());
            if (!userOptional.isPresent()) {
                return ResponseEntity.badRequest().body("User not found");
            }

            User user = userOptional.get();
            
            // Check admin approval for non-admin users
            if (!"info@bellaryinfotech.com".equals(loginOtpDTO.getEmail())) {
                if (adminDashboardService.isUserRejected(loginOtpDTO.getEmail())) {
                    return ResponseEntity.status(403).body("Access denied. Your login request has been rejected by the administrator.");
                }
                
                if (!adminDashboardService.isUserApproved(loginOtpDTO.getEmail())) {
                    // Create login request if it doesn't exist
                    if (!adminDashboardService.hasUserRequestedLogin(loginOtpDTO.getEmail())) {
                        adminDashboardService.createLoginRequest(loginOtpDTO.getEmail());
                    }
                    return ResponseEntity.status(403).body("Your login request is pending admin approval. Please wait for administrator confirmation.");
                }
            }

            String token = jwtUtil.generateToken(user.getEmail(), user.getUsername(), user.getRole());
            LoginResponseDTO response = new LoginResponseDTO(
                    token, user.getEmail(), user.getUsername(), user.getFullname(), user.getRole(), "Login successful"
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }

    @PostMapping(value = RESEND_LOGIN_OTP, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> resendLoginOtp(@Valid @RequestBody EmailVerificationDTO emailVerificationDTO) {
        try {
            if (!userService.existsByEmail(emailVerificationDTO.getEmail())) {
                return ResponseEntity.badRequest().body("Email not found");
            }
            String message = loginOtpService.resendLoginOtp(emailVerificationDTO.getEmail());
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to resend login OTP: " + e.getMessage());
        }
    }

    @PostMapping(value = REGISTER_USER, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        try {
            if (userService.existsByEmail(userRegistrationDTO.getEmail())) {
                return ResponseEntity.badRequest().body("Email already exists");
            }
            if (userService.existsByUsername(userRegistrationDTO.getUsername())) {
                return ResponseEntity.badRequest().body("Username already exists");
            }
            UserResponseDTO registeredUser = userService.registerUser(userRegistrationDTO);
            return registeredUser != null ? ResponseEntity.ok("Registration successful")
                                          : ResponseEntity.badRequest().body("Registration failed");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping(value = VALIDATE_TOKEN, consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validateToken(@RequestBody String token) {
        try {
            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractUsername(token);
                Optional<User> userOptional = userRepository.findByEmail(email);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    LoginResponseDTO response = new LoginResponseDTO(
                            token, user.getEmail(), user.getUsername(), user.getFullname(), user.getRole(), "Token valid"
                    );
                    return ResponseEntity.ok(response);
                }
            }
            return ResponseEntity.status(401).body("Invalid token");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token validation failed: " + e.getMessage());
        }
    }
    
    
    
    
    //Backend code for the reset password and confirm password
    
 // Updated LOGIN_PASSWORD method for admin dashboard
    @PostMapping(value = LOGIN_PASSWORD, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginWithPassword(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(userLoginDTO.getEmail());
            if (!userOptional.isPresent()) {
                return ResponseEntity.badRequest().body("Email not found. Please register first.");
            }
                        
            User user = userOptional.get();
            if (!user.getVerified()) {
                return ResponseEntity.badRequest().body("Email not verified. Please complete registration first.");
            }
                        
            // Check password
            if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
                return ResponseEntity.badRequest().body("Invalid email or password.");
            }
            
            // Check admin approval for non-admin users
            if (!"info@bellaryinfotech.com".equals(userLoginDTO.getEmail())) {
                if (adminDashboardService.isUserRejected(userLoginDTO.getEmail())) {
                    return ResponseEntity.status(403).body("Access denied. Your login request has been rejected by the administrator.");
                }
                
                if (!adminDashboardService.isUserApproved(userLoginDTO.getEmail())) {
                    // Create login request if it doesn't exist
                    if (!adminDashboardService.hasUserRequestedLogin(userLoginDTO.getEmail())) {
                        adminDashboardService.createLoginRequest(userLoginDTO.getEmail());
                    }
                    return ResponseEntity.status(403).body("Your login request is pending admin approval. Please wait for administrator confirmation.");
                }
            }
                        
            String token = jwtUtil.generateToken(user.getEmail(), user.getUsername(), user.getRole());
            LoginResponseDTO response = new LoginResponseDTO(
                    token, user.getEmail(), user.getUsername(), user.getFullname(), user.getRole(), "Login successful"
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }

    @PostMapping(value = FORGOT_PASSWORD, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody EmailVerificationDTO emailVerificationDTO) {
        try {
            String message = passwordResetService.generatePasswordResetToken(emailVerificationDTO.getEmail());
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send reset link: " + e.getMessage());
        }
    }

    @PostMapping(value = RESET_PASSWORD, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String token = request.get("token");
            String newPassword = request.get("newPassword");
            
            if (email == null || token == null || newPassword == null) {
                return ResponseEntity.badRequest().body("Missing required fields");
            }
            
            if (newPassword.length() < 8) {
                return ResponseEntity.badRequest().body("Password must be at least 8 characters");
            }
            
            boolean success = passwordResetService.resetPassword(email, token, newPassword);
            return success ? ResponseEntity.ok("Password reset successfully")
                           : ResponseEntity.badRequest().body("Invalid or expired reset token");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Password reset failed: " + e.getMessage());
        }
    
    
    
    }
    }
