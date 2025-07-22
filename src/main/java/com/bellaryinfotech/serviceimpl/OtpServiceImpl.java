package com.bellaryinfotech.serviceimpl;
 

import com.bellaryinfotech.model.EmailOtp;
import com.bellaryinfotech.repo.EmailOtpRepository;
import com.bellaryinfotech.service.EmailService;
import com.bellaryinfotech.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpServiceImpl implements OtpService {
    
    private static final Logger logger = LoggerFactory.getLogger(OtpServiceImpl.class);
    
    @Autowired
    private EmailOtpRepository emailOtpRepository;
    
    @Autowired
    private EmailService emailService;
    
    @Value("${otp.expiry.minutes:5}")
    private int otpExpiryMinutes;
    
    private final SecureRandom random = new SecureRandom();
    
    @Override
    public String generateAndSendOtp(String email) {
        try {
            logger.info("Generating OTP for email: {}", email);
            
            // Generate 6-digit OTP
            String otp = String.format("%06d", random.nextInt(1000000));
            logger.info("Generated OTP for email: {}", email);
            
            // Mark all previous OTPs as used
            emailOtpRepository.markAllOtpAsUsedByEmail(email);
            logger.info("Marked previous OTPs as used for email: {}", email);
            
            // Create new OTP record
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(otpExpiryMinutes);
            EmailOtp emailOtp = new EmailOtp(email, otp, expiresAt);
            emailOtpRepository.save(emailOtp);
            logger.info("Saved new OTP record for email: {}", email);
            
            // Send OTP via email
            emailService.sendOtpEmail(email, otp);
            logger.info("OTP sent successfully to email: {}", email);
            
            return "OTP sent successfully to " + email;
        } catch (Exception e) {
            logger.error("Failed to generate and send OTP for email {}: {}", email, e.getMessage(), e);
            throw new RuntimeException("Failed to generate and send OTP: " + e.getMessage());
        }
    }
    
    @Override
    public boolean verifyOtp(String email, String otp) {
        try {
            logger.info("Verifying OTP for email: {}", email);
            
            Optional<EmailOtp> emailOtpOptional = emailOtpRepository
                .findByEmailAndOtpAndUsedFalseAndExpiresAtAfter(email, otp, LocalDateTime.now());
            
            if (emailOtpOptional.isPresent()) {
                EmailOtp emailOtp = emailOtpOptional.get();
                emailOtp.setUsed(true);
                emailOtpRepository.save(emailOtp);
                logger.info("OTP verified successfully for email: {}", email);
                return true;
            }
            logger.warn("OTP verification failed for email: {} - Invalid or expired OTP", email);
            return false;
        } catch (Exception e) {
            logger.error("Failed to verify OTP for email {}: {}", email, e.getMessage(), e);
            throw new RuntimeException("Failed to verify OTP: " + e.getMessage());
        }
    }
    
    @Override
    public String resendOtp(String email) {
        logger.info("Resending OTP for email: {}", email);
        return generateAndSendOtp(email);
    }
}
