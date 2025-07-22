package com.bellaryinfotech.serviceimpl;
 

import com.bellaryinfotech.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailServiceImpl implements EmailService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    @Override
    public void sendOtpEmail(String toEmail, String otp) {
        try {
            logger.info("Attempting to send OTP email to: {}", toEmail);
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Email Verification - Bellary Billing Solutions");
            
            String emailBody = String.format(
                "Dear User,\n\n" +
                "Welcome to Bellary Billing Solutions!\n\n" +
                "To complete your registration and verify your email address, please use the following One-Time Password (OTP):\n\n" +
                "OTP: %s\n\n" +
                "This OTP is valid for 5 minutes only. Please do not share this code with anyone.\n\n" +
                "If you did not request this verification, please ignore this email.\n\n" +
                "Thank you for choosing Bellary Billing Solutions for your business needs.\n\n" +
                "Best regards,\n" +
                "Bellary InfoTech Solutions Team\n" +
                "Email: bellaryinfotechsolutions@gmail.com\n" +
                "Website: www.bellaryinfotech.com",
                otp
            );
            
            message.setText(emailBody);
            mailSender.send(message);
            
            logger.info("OTP email sent successfully to: {}", toEmail);
        } catch (Exception e) {
            logger.error("Failed to send OTP email to {}: {}", toEmail, e.getMessage(), e);
            throw new RuntimeException("Failed to send OTP email. Please check your email configuration: " + e.getMessage());
        }
    }
}
