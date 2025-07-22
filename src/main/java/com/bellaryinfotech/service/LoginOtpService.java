package com.bellaryinfotech.service;

public interface LoginOtpService {
    String generateAndSendLoginOtp(String email);
    boolean verifyLoginOtp(String email, String otp);
    String resendLoginOtp(String email);
}
