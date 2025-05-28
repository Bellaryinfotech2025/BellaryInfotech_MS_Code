package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.LookupValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LookupValuesRepository extends JpaRepository<LookupValue, Long> {
    
    @Query("SELECT lv FROM LookupValue lv WHERE lv.lookupType = :lookupType")
    List<LookupValue> findByLookupTypeName(@Param("lookupType") String lookupType);
    
    @Query("SELECT lv FROM LookupValue lv WHERE lv.lookupType = :lookupType AND lv.lookupCode = :lookupCode")
    Optional<LookupValue> findByLookupTypeAndCode(@Param("lookupType") String lookupType, @Param("lookupCode") String lookupCode);
    
    @Query("SELECT lv FROM LookupValue lv WHERE lv.lookupType = :lookupType AND UPPER(lv.meaning) = UPPER(:meaning)")
    Optional<LookupValue> findByLookupTypeAndMeaning(@Param("lookupType") String lookupType, @Param("meaning") String meaning);
}
