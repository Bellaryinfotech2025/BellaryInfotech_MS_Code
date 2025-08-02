package com.bellaryinfotech.serviceimpl;

import com.bellaryinfotech.model.PasswordResetToken;
import com.bellaryinfotech.model.User;
import com.bellaryinfotech.repo.PasswordResetTokenRepository;
import com.bellaryinfotech.repo.UserRepository;
import com.bellaryinfotech.service.PasswordResetService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {
    
    private static final Logger logger = LoggerFactory.getLogger(PasswordResetServiceImpl.class);
    
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Value("${app.frontend.url:http://test.bellaryinfotech.com}")
    private String frontendUrl;
    
    @Value("${password.reset.expiry.hours:1}")
    private int resetExpiryHours;
    
    private final SecureRandom random = new SecureRandom();
    
    @Override
    public String generatePasswordResetToken(String email) {
        try {
            logger.info("Generating password reset token for email: {}", email);
            
            // Check if user exists
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (!userOptional.isPresent()) {
                throw new RuntimeException("Email not found. Please register first.");
            }
            
            // Generate secure token
            String token = generateSecureToken();
            logger.info("Generated password reset token for email: {}", email);
            
            // Mark previous tokens as used
            passwordResetTokenRepository.markAllTokensAsUsedByEmail(email);
            logger.info("Marked previous tokens as used for email: {}", email);
            
            // Save new token
            LocalDateTime expiresAt = LocalDateTime.now().plusHours(resetExpiryHours);
            PasswordResetToken resetToken = new PasswordResetToken(email, token, expiresAt);
            passwordResetTokenRepository.save(resetToken);
            logger.info("Saved new password reset token for email: {}", email);
            
            // Send reset email
            sendPasswordResetEmail(email, token);
            logger.info("Password reset email sent successfully to: {}", email);
            
            return "Password reset link sent successfully to " + email;
        } catch (Exception e) {
            logger.error("Failed to generate password reset token for email {}: {}", email, e.getMessage(), e);
            throw new RuntimeException("Failed to generate password reset token: " + e.getMessage());
        }
    }
    
    @Override
    public boolean resetPassword(String email, String token, String newPassword) {
        try {
            logger.info("Resetting password for email: {}", email);
            
            // Validate token
            Optional<PasswordResetToken> tokenOptional = passwordResetTokenRepository
                    .findByTokenAndUsedFalseAndExpiresAtAfter(token, LocalDateTime.now());
            
            if (!tokenOptional.isPresent()) {
                logger.warn("Invalid or expired password reset token for email: {}", email);
                return false;
            }
            
            PasswordResetToken resetToken = tokenOptional.get();
            if (!resetToken.getEmail().equals(email)) {
                logger.warn("Token email mismatch for email: {}", email);
                return false;
            }
            
            // Update user password
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (!userOptional.isPresent()) {
                logger.warn("User not found for email: {}", email);
                return false;
            }
            
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            
            // Mark token as used
            resetToken.setUsed(true);
            passwordResetTokenRepository.save(resetToken);
            
            logger.info("Password reset successfully for email: {}", email);
            return true;
        } catch (Exception e) {
            logger.error("Password reset failed for email {}: {}", email, e.getMessage(), e);
            throw new RuntimeException("Password reset failed: " + e.getMessage());
        }
    }
    
    private String generateSecureToken() {
        byte[] tokenBytes = new byte[32];
        random.nextBytes(tokenBytes);
        StringBuilder token = new StringBuilder();
        for (byte b : tokenBytes) {
            token.append(String.format("%02x", b));
        }
        return token.toString();
    }
    
    private void sendPasswordResetEmail(String toEmail, String token) {
        try {
            logger.info("Sending password reset email to: {}", toEmail);
            
            String resetLink = frontendUrl + "/login/billing?reset-token=" + token + "&email=" + toEmail;
            
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
                "  .reset-button { display: inline-block; background-color: #3b82f6; color: white; padding: 12px 24px; text-decoration: none; border-radius: 6px; font-weight: 500; margin: 20px 0; }" +
                "  .reset-button:hover { background-color: #2563eb; }" +
                "  .footer { font-size: 12px; color: #888; margin-top: 30px; border-top: 1px solid #eee; padding-top: 15px; text-align: center; }" +
                "  .contact { font-size: 13px; margin-top: 20px; color: #444; }" +
                "  a { color: #2563eb; text-decoration: none; word-break: break-word; }" +
                "  .warning { background-color: #fef3c7; border: 1px solid #f59e0b; border-radius: 6px; padding: 12px; margin: 20px 0; color: #92400e; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='card'>" +
                "  <div class='logo'>🔐</div>" +
                "  <h2>Reset Your Password - Bellary Infotech Solutions</h2>" +
                "  <p>Dear User,</p>" +
                "  <p>We received a request to reset your password for your <strong>Bellary Infotech Solutions</strong> account. If you made this request, please click the button below to reset your password:</p>" +
                "  <div style='text-align: center;'>" +
                "    <a href='%s' class='reset-button'>Reset My Password</a>" +
                "  </div>" +
                "  <p>Or copy and paste this link into your browser:</p>" +
                "  <p style='word-break: break-all; background-color: #f3f4f6; padding: 10px; border-radius: 4px; font-family: monospace; font-size: 12px;'>%s</p>" +
                "  <div class='warning'>" +
                "    <strong>⚠️ Security Notice:</strong> This link will expire in 1 hour for your security. If you did not request this password reset, please ignore this email and your password will remain unchanged." +
                "  </div>" +
                "  <p>If you're having trouble clicking the button, you can also reset your password by visiting our login page and clicking 'Forgot Password'.</p>" +
                "  <div class='contact'>" +
                "    <p>Best regards,<br><strong>Security Team</strong><br>Bellary Infotech Solutions</p>" +
                "    <p>📧 Email: <a href='mailto:info@bellaryinfotech.com'>info@bellaryinfotech.com</a><br>" +
                "    🌐 Website: <a href='https://www.bellaryinfotech.com'>www.bellaryinfotech.com</a></p>" +
                "  </div>" +
                "  <div class='footer'>This is an automated message. Please do not reply directly to this email.</div>" +
                "</div>" +
                "</body>" +
                "</html>", resetLink, resetLink
            );
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("bellaryinfotechsolutions@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Reset Your Password - Bellary Infotech Solutions");
            helper.setText(emailBody, true);
            
            mailSender.send(message);
            logger.info("Password reset email sent successfully to: {}", toEmail);
        } catch (Exception e) {
            logger.error("Failed to send password reset email to {}: {}", toEmail, e.getMessage(), e);
            throw new RuntimeException("Failed to send password reset email: " + e.getMessage());
        }
    }
}
