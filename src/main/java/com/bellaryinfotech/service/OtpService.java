package com.bellaryinfotech.service;

public interface OtpService {
    String generateAndSendOtp(String email);
    boolean verifyOtp(String email, String otp);
    String resendOtp(String email);
}
