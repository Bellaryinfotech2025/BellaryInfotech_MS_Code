package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.CustomerAccountSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerSiteRepository extends JpaRepository<CustomerAccountSite, Long> {
    List<CustomerAccountSite> findByCustAccountId(Long custAccountId);

	CustomerAccountSite findFirstByCustAccountId(Long custAccountId);
}