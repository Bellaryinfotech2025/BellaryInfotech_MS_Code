package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.EmailOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EmailOtpRepository extends JpaRepository<EmailOtp, Long> {
    
    Optional<EmailOtp> findByEmailAndOtpAndUsedFalseAndExpiresAtAfter(
        String email, String otp, LocalDateTime currentTime);
    
    @Modifying
    @Transactional
    @Query("UPDATE EmailOtp e SET e.used = true WHERE e.email = ?1")
    void markAllOtpAsUsedByEmail(String email);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM EmailOtp e WHERE e.expiresAt < ?1")
    void deleteExpiredOtps(LocalDateTime currentTime);
}
