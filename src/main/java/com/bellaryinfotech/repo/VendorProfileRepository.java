package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.VendorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorProfileRepository extends JpaRepository<VendorProfile, Long> {
    
    Optional<VendorProfile> findByCompanyName(String companyName);
    
    boolean existsByCompanyName(String companyName);
    
    boolean existsByEmail(String email);
    
    boolean existsByPanNo(String panNo);
    
    boolean existsByGstNo(String gstNo);
    
    List<VendorProfile> findByStatus(VendorProfile.ProfileStatus status);
    
    @Query("SELECT v FROM VendorProfile v WHERE v.companyName LIKE %:name%")
    List<VendorProfile> findByCompanyNameContaining(@Param("name") String name);
    
    @Query("SELECT v FROM VendorProfile v WHERE v.district = :district")
    List<VendorProfile> findByDistrict(@Param("district") String district);
    
    @Query("SELECT v FROM VendorProfile v WHERE v.state = :state")
    List<VendorProfile> findByState(@Param("state") String state);
    
    @Query("SELECT v FROM VendorProfile v WHERE v.pinCode = :pinCode")
    List<VendorProfile> findByPinCode(@Param("pinCode") String pinCode);
    
    @Query("SELECT COUNT(v) FROM VendorProfile v WHERE v.status = :status")
    long countByStatus(@Param("status") VendorProfile.ProfileStatus status);
    
    @Query("SELECT v FROM VendorProfile v WHERE v.companyName LIKE %:searchTerm% OR v.contactPerson LIKE %:searchTerm% OR v.email LIKE %:searchTerm%")
    List<VendorProfile> searchProfiles(@Param("searchTerm") String searchTerm);
}
