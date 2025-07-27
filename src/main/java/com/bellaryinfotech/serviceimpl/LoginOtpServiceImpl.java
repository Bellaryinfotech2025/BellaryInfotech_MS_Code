package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.model.EmailOtp;
import com.bellaryinfotech.repo.EmailOtpRepository;
import com.bellaryinfotech.service.EmailService;
import com.bellaryinfotech.service.LoginOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
            	    "Dear User,\n\n" +
            	    "We noticed a login attempt to your Bellary Infotech Solutions account. To ensure the security of your account, " +
            	    "please use the following One-Time Password (OTP) to complete your sign-in process:\n\n" +
            	    "🔐 OTP: %s\n\n" +
            	    "This OTP is valid for 5 minutes only.\n" +
            	    "Please do NOT share this code with anyone. Bellary Infotech Solutions will never ask you for your OTP via email, phone, or chat.\n\n" +
            	    "If you did not initiate this request or believe this was a mistake, please disregard this email or contact our support team immediately.\n\n" +
            	    "Thank you for choosing Bellary Infotech Solutions — powering innovation with trust and security.\n\n" +
            	    "Warm regards,\n" +
            	    "Security Team\n" +
            	    "Bellary Infotech Solutions\n" +
            	    "📧 info@bellaryinfotech.com\n" +
            	    "🌐 www.bellaryinfotech.com\n" +
            	    "---------------------------------------------\n" +
            	    "This is an automated message. Please do not reply directly to this email.",
            	    otp
            	);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("bellaryinfotechsolutions@gmail.com");
            message.setTo(toEmail);
            message.setSubject("One-Time Password (OTP) for Your Bellary Infotech Solutions Login");
            message.setText(emailBody);

            mailSender.send(message);
            logger.info("Login OTP email sent successfully to: {}", toEmail);

        } catch (Exception e) {
            logger.error("Failed to send login OTP email to {}: {}", toEmail, e.getMessage(), e);
            throw new RuntimeException("Failed to send login OTP email: " + e.getMessage());
        }
    }
}
