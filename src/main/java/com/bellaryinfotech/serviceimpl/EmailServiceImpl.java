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
            	    "  body { margin: 0; padding: 20px; background-color: #f9f9f9; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', sans-serif; }" +
            	    "  .card { max-width: 500px; background: #ffffff; margin: 20px auto; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05); }" +
            	    "  .logo { text-align: center; font-size: 32px; margin-bottom: 20px; }" +
            	    "  h2 { text-align: center; color: #111827; font-weight: 600; font-size: 20px; margin-bottom: 20px; }" +
            	    "  p { color: #333; font-size: 14px; line-height: 1.6; }" +
            	    "  .otp-box { background-color: #f3f4f6; padding: 12px; text-align: center; font-size: 24px; font-weight: bold; border-radius: 6px; letter-spacing: 2px; margin: 20px 0; }" +
            	    "  .congrats { text-align: center; font-size: 18px; font-weight: bold; color: #0b2b5c; margin-top: 10px; }" +
            	    "  .footer { font-size: 12px; color: #888; margin-top: 30px; border-top: 1px solid #eee; padding-top: 15px; text-align: center; }" +
            	    "  .contact { font-size: 13px; margin-top: 20px; color: #444; }" +
            	    "  a { color: #2563eb; text-decoration: none; word-break: break-word; }" +
            	    "</style>" +
            	    "</head>" +
            	    "<body>" +
            	    "<div class='card'>" +
            	    "  <div class='logo'>🎉</div>" +
            	    "  <h2>Welcome to Bellary Infotech Solutions!</h2>" +
            	    "  <p>Dear User,</p>" +
            	    "  <p>Thank you for registering with <strong>Bellary Infotech Solutions</strong>. To verify your email address and complete your registration, please use the OTP provided below:</p>" +
            	    "  <div class='otp-box'>%s</div>" +
            	    "  <div class='congrats'>🎉 Congratulations!!</div>" +
            	    "  <p>This OTP is valid for <strong>5 minutes</strong>. Please do not share this code with anyone. Bellary Infotech will never ask for it via email, phone, or chat.</p>" +
            	    "  <p>If you did not request this verification, you may safely ignore this message.</p>" +
            	    "  <div class='contact'>" +
            	    "    <p>Warm regards,<br><strong>Bellary Infotech Solutions Team</strong></p>" +
            	    "    <p>📧 Email: <a href='mailto:bellaryinfotechsolutions@gmail.com'>bellaryinfotechsolutions@gmail.com</a><br>" +
            	    "    🌐 Website: <a href='https://www.bellaryinfotech.com'>www.bellaryinfotech.com</a></p>" +
            	    "  </div>" +
            	    "  <div class='footer'>This is an automated message. Please do not reply directly to this email.</div>" +
            	    "</div>" +
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
