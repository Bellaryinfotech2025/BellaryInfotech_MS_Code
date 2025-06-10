package com.bellaryinfotech.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bellaryinfotech.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

