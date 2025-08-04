package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.AdminDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminDashboardRepository extends JpaRepository<AdminDashboard, Long> {
    
    Optional<AdminDashboard> findByEmailAndStatus(String email, String status);
    
    List<AdminDashboard> findByStatusOrderByDatetimeDesc(String status);
    
    List<AdminDashboard> findAllByOrderByDatetimeDesc();
    
    @Query("SELECT a FROM AdminDashboard a WHERE a.email = ?1 AND a.status IN ('Approved', 'Rejected')")
    Optional<AdminDashboard> findApprovalStatusByEmail(String email);
    
    boolean existsByEmailAndStatus(String email, String status);
}
