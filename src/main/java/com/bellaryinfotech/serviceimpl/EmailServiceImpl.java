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
            	    "<!DOCTYPE html>" +
            	    "<html lang='en'>" +
            	    "<head>" +
            	    "<meta charset='UTF-8'>" +
            	    "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
            	    "<style>" +
            	    "  body { margin: 0; padding: 20px; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f2f4f8; }" +
            	    "  .container { max-width: 600px; margin: auto; background: #ffffff; padding: 30px; border-radius: 10px; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }" +
            	    "  h2 { color: #1f2e44; }" +
            	    "  p { color: #333; line-height: 1.6; font-size: 16px; }" +
            	    "  .otp-box { background-color: #0b2b5c; padding: 20px; text-align: center; border-radius: 8px; color: #ffffff; font-size: 28px; font-weight: bold; letter-spacing: 3px; }" +
            	    "  .copy-btn { margin-top: 15px; text-align: center; }" +
            	    "  .copy-btn button { background-color: #0b2b5c; color: #ffffff; border: none; padding: 10px 20px; border-radius: 5px; font-size: 14px; cursor: pointer; }" +
            	    "  .copy-btn button:active { background-color: #092246; }" +
            	    "  .footer { margin-top: 30px; font-size: 12px; color: #888; text-align: center; }" +
            	    "  .contact { margin-top: 20px; font-size: 14px; }" +
            	    "</style>" +
            	    "</head>" +
            	    "<body>" +
            	    "<div class='container'>" +
            	    "  <h2>Welcome to Bellary Infotech Solutions!</h2>" +
            	    "  <p>Dear User,</p>" +
            	    "  <p>Thank you for registering with <strong>Bellary Infotech Solutions</strong>. To verify your email address and complete your registration, please use the One-Time Password (OTP) provided below:</p>" +
            	    "  <div class='otp-box' id='otpValue'>%s</div>" +
            	    "  <div class='copy-btn'>" +
            	    "    <button onclick='copyOTP()'>Congratulations</button>" +
            	    "  </div>" +
            	    "  <p>This OTP is valid for <strong>5 minutes</strong>. Please do not share this code with anyone. Bellary Infotech will never ask for it via email, phone, or chat.</p>" +
            	    "  <p>If you did not request this verification, you may safely ignore this message.</p>" +
            	    "  <div class='contact'>" +
            	    "    <p>Warm regards,<br><strong>Bellary Infotech Solutions Team</strong></p>" +
            	    "    <p>Email: <a href='mailto:bellaryinfotechsolutions@gmail.com'>bellaryinfotechsolutions@gmail.com</a><br>" +
            	    "    Website: <a href='https://www.bellaryinfotech.com'>www.bellaryinfotech.com</a><br>" +
            	    "  </div>" +
            	    "  <div class='footer'>This is an automated message. Please do not reply directly to this email.</div>" +
            	    "</div>" +

            	    // Optional: JS block for browsers
            	    "<script>" +
            	    "  function copyOTP() {" +
            	    "    var otpText = document.getElementById('otpValue').innerText;" +
            	    "    navigator.clipboard.writeText(otpText).then(function() {" +
            	    "      alert('OTP copied to clipboard ✅');" +
            	    "    }, function(err) {" +
            	    "      alert('Failed to copy OTP');" +
            	    "    });" +
            	    "  }" +
            	    "</script>" +

            	    "</body>" +
            	    "</html>", otp
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
