package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.CustomerContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerContactRepository extends JpaRepository<CustomerContact, Long> {
    List<CustomerContact> findByCustAccountId(Long custAccountId);
    List<CustomerContact> findByCustAcctSiteId(Long custAcctSiteId);
	CustomerContact findFirstByCustAccountId(Long custAccountId);
}