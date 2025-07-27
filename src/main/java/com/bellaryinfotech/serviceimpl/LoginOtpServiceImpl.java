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
            	    "  body { margin: 0; padding: 20px; background-color: #f9f9f9; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', sans-serif; }" +
            	    "  .card { max-width: 500px; background: #ffffff; margin: 20px auto; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05); }" +
            	    "  .logo { text-align: center; font-size: 32px; margin-bottom: 20px; }" +
            	    "  h2 { text-align: center; color: #111827; font-weight: 600; font-size: 20px; margin-bottom: 20px; }" +
            	    "  p { color: #333; font-size: 14px; line-height: 1.6; }" +
            	    "  .otp-box { background-color: #f3f4f6; padding: 12px; text-align: center; font-size: 24px; font-weight: bold; border-radius: 6px; letter-spacing: 2px; margin: 20px 0; }" +
            	    "  .footer { font-size: 12px; color: #888; margin-top: 30px; border-top: 1px solid #eee; padding-top: 15px; text-align: center; }" +
            	    "  .contact { font-size: 13px; margin-top: 20px; color: #444; }" +
            	    "  a { color: #2563eb; text-decoration: none; word-break: break-word; }" +
            	    "</style>" +
            	    "</head>" +
            	    "<body>" +
            	    "<div class='card'>" +
            	    "  <div class='logo'>🔐</div>" +
            	    "  <h2>Verify your email to sign in to Bellary Infotech Solutions</h2>" +
            	    "  <p>Dear User,</p>" +
            	    "  <p>We noticed a login attempt to your <strong>Bellary Infotech Solutions</strong> account. To ensure your account's security, please use the following One-Time Password (OTP) to complete your sign-in process:</p>" +
            	    "  <div class='otp-box'>%s</div>" +
            	    "  <p>This OTP is valid for <strong>5 minutes</strong>. Do not share this code with anyone. Bellary Infotech Solutions will never ask for your OTP via email, phone, or chat.</p>" +
            	    "  <p>If you did not request this login or believe this was a mistake, please disregard this message or contact our support team.</p>" +
            	    "  <div class='contact'>" +
            	    "    <p>Warm regards,<br><strong>Security Team</strong><br>Bellary Infotech Solutions</p>" +
            	    "    <p>📧 Email: <a href='mailto:info@bellaryinfotech.com'>info@bellaryinfotech.com</a><br>" +
            	    "    🌐 Website: <a href='https://www.bellaryinfotech.com'>www.bellaryinfotech.com</a></p>" +
            	    "  </div>" +
            	    "  <div class='footer'>This is an automated message. Please do not reply directly to this email.</div>" +
            	    "</div>" +
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
