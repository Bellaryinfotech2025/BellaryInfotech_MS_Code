package com.bellaryinfotech.service;

public interface PasswordResetService {
    String generatePasswordResetToken(String email);
    boolean resetPassword(String email, String token, String newPassword);
}
