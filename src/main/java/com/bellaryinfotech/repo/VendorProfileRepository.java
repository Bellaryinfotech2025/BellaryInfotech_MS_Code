package com.bellaryinfotech.repo;

 

 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.Vendor;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    
    Optional<Vendor> findByVendorCode(String vendorCode);
    
    boolean existsByVendorCode(String vendorCode);
    
    boolean existsByEmail(String email);
    
    boolean existsByPanNo(String panNo);
    
    boolean existsByGstNo(String gstNo);
    
    List<Vendor> findByStatus(Vendor.VendorStatus status);
    
    @Query("SELECT v FROM Vendor v WHERE v.name LIKE %:name%")
    List<Vendor> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT v FROM Vendor v WHERE v.vendorCode LIKE %:code% OR v.name LIKE %:name%")
    List<Vendor> searchByCodeOrName(@Param("code") String code, @Param("name") String name);
    
    @Query("SELECT COUNT(v) FROM Vendor v WHERE v.status = :status")
    long countByStatus(@Param("status") Vendor.VendorStatus status);
}
