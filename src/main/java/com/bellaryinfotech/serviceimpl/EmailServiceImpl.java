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
            message.setSubject("Email Verification - Bellary Infotech Solutions");
            
            String emailBody = String.format(
            	    "Subject: Email Verification - Bellary Infotech Solutions\n\n" +
            	    "Dear User,\n\n" +
            	    "Welcome to **Bellary Infotech Solutions**!\n\n" +
            	    "We’re excited to have you on board. To complete your registration and verify your email address, please use the One-Time Password (OTP) provided below:\n\n" +
            	    "🔐 OTP: %s\n\n" +
            	    "This OTP is valid for the next **5 minutes**.\n" +
            	    "For your security, please do **not share this code** with anyone. Bellary Infotech Solutions will never request this information via email, phone, or message.\n\n" +
            	    "If you did not request this verification, please disregard this email or contact our support team.\n\n" +
            	    "Thank you for choosing Bellary Infotech Solutions — delivering innovation, trust,billing solutions and excellence.\n\n" +
            	    "Warm regards,\n" +
            	    "Bellary Infotech Solutions\n" +
            	    "📧 Email: bellaryinfotechsolutions@gmail.com\n" +
            	    "🌐 Website: www.bellaryinfotech.com\n" +
            	    "---------------------------------------------\n" +
            	    "This is an automated message. Please do not reply directly to this email.",
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
