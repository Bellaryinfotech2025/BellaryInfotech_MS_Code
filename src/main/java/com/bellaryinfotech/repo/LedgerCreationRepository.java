package com.bellaryinfotech.repo;

 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.model.LedgerCreation;

import java.util.List;
import java.util.Optional;

@Repository
public interface LedgerCreationRepository extends JpaRepository<LedgerCreation, Long> {
    
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM LedgerCreation l WHERE l.ledgerName = :ledgerName")
    boolean existsByLedgerName(@Param("ledgerName") String ledgerName);
    
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM LedgerCreation l WHERE l.email = :email")
    boolean existsByEmail(@Param("email") String email);
    
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM LedgerCreation l WHERE l.gstin = :gstin")
    boolean existsByGstin(@Param("gstin") String gstin);
    
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM LedgerCreation l WHERE l.pan = :pan")
    boolean existsByPan(@Param("pan") String pan);
    
    Optional<LedgerCreation> findByLedgerName(String ledgerName);
    
 // New method to find creditors only
    List<LedgerCreation> findByDebtorCreditor(String debtorCreditor);
    
    // Alternative with custom query if needed
    @Query("SELECT l FROM LedgerCreation l WHERE l.debtorCreditor = :debtorCreditor ORDER BY l.ledgerName ASC")
    List<LedgerCreation> findCreditorsSorted(@Param("debtorCreditor") String debtorCreditor);
    
    
    
  
    
    // Custom query for creditors with sorting
    @Query("SELECT l FROM LedgerCreation l WHERE l.debtorCreditor = 'Creditor' ORDER BY l.ledgerName ASC")
    List<LedgerCreation> findCreditorsOrdered();
}




