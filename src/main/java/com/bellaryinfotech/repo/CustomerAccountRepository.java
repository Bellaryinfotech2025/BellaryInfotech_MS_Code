package com.bellaryinfotech.repo;

import com.bellaryinfotech.model.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Long> {
    List<CustomerAccount> findByAccountNameIsNotNull();

	CustomerAccount findByAccountName(String accountName);
}