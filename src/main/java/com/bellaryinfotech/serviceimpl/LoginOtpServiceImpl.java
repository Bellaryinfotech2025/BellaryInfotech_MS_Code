package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.model.EmailOtp;
import com.bellaryinfotech.repo.EmailOtpRepository;
import com.bellaryinfotech.service.EmailService;
import com.bellaryinfotech.service.LoginOtpService;

import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LoginOtpServiceImpl implements LoginOtpService {

    private static final Logger logger = LoggerFactory.getLogger(LoginOtpServiceImpl.class);

    @Autowired
    private EmailOtpRepository emailOtpRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${otp.expiry.minutes:5}")
    private int otpExpiryMinutes;

    private final SecureRandom random = new SecureRandom();

    @Override
    public String generateAndSendLoginOtp(String email) {
        try {
            logger.info("Generating login OTP for email: {}", email);

            // Generate 6-digit OTP
            String otp = String.format("%06d", random.nextInt(1000000));
            logger.info("Generated login OTP: {} for email: {}", otp, email);

            // Mark previous OTPs as used
            emailOtpRepository.markAllOtpAsUsedByEmail(email);
            logger.info("Marked previous OTPs as used for email: {}", email);

            // Save new OTP
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(otpExpiryMinutes);
            EmailOtp emailOtp = new EmailOtp(email, otp, expiresAt);
            emailOtpRepository.save(emailOtp);
            logger.info("Saved new OTP record for email: {}", email);

            // Send OTP
            sendLoginOtpEmail(email, otp);
            logger.info("OTP sent successfully to: {}", email);

            return "Login OTP sent successfully to " + email;

        } catch (Exception e) {
            logger.error("Failed to generate/send OTP for email {}: {}", email, e.getMessage(), e);
            throw new RuntimeException("Failed to generate/send OTP: " + e.getMessage());
        }
    }

    @Override
    public boolean verifyLoginOtp(String email, String otp) {
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

            logger.warn("Invalid or expired OTP for email: {}", email);
            return false;

        } catch (Exception e) {
            logger.error("OTP verification failed for email {}: {}", email, e.getMessage(), e);
            throw new RuntimeException("OTP verification failed: " + e.getMessage());
        }
    }

    @Override
    public String resendLoginOtp(String email) {
        logger.info("Resending OTP for email: {}", email);
        return generateAndSendLoginOtp(email);
    }

    private void sendLoginOtpEmail(String toEmail, String otp) {
        try {
            logger.info("Sending login OTP email to: {}", toEmail);

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
            	    "  <h2>Your Account Login Requires Verification</h2>" +
            	    "  <p>Dear User,</p>" +
            	    "  <p>We noticed a login attempt to your <strong>Bellary Infotech Solutions</strong> account. To ensure your account security, please use the following One-Time Password (OTP) to complete your sign-in process:</p>" +
            	    "  <div class='otp-box' id='otpValue'>%s</div>" +
            	    "  <div class='copy-btn'>" +
            	    "    <button onclick='copyOTP()'>Congratulations!!</button>" +
            	    "  </div>" +
            	    "  <p>This OTP is valid for <strong>5 minutes</strong>. Do not share this code with anyone. Bellary Infotech Solutions will never ask you for your OTP via email, phone, or chat.</p>" +
            	    "  <p>If you did not initiate this request or believe this was a mistake, please disregard this message or contact our support team immediately.</p>" +
            	    "  <div class='contact'>" +
            	    "    <p>Warm regards,<br><strong>Security Team</strong><br>Bellary Infotech Solutions</p>" +
            	    "    <p>📧 Email: <a href='mailto:info@bellaryinfotech.com'>info@bellaryinfotech.com</a><br>" +
            	    "    🌐 Website: <a href='https://www.bellaryinfotech.com'>www.bellaryinfotech.com</a></p>" +
            	    "  </div>" +
            	    "  <div class='footer'>This is an automated message. Please do not reply directly to this email.</div>" +
            	    "</div>" +

            	    // Copy button script
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


            // Use MimeMessage instead of SimpleMailMessage
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("bellaryinfotechsolutions@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("One-Time Password (OTP) for Your Bellary Infotech Solutions Login");
            helper.setText(emailBody, true); // 'true' means HTML content

            mailSender.send(message);
            logger.info("Login OTP email sent successfully to: {}", toEmail);

        } catch (Exception e) {
            logger.error("Failed to send login OTP email to {}: {}", toEmail, e.getMessage(), e);
            throw new RuntimeException("Failed to send login OTP email: " + e.getMessage());
        }
    }
}
